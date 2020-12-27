package graph.adjacency;

import java.util.*;
import java.util.stream.Collectors;

// experimental

public class AdjacencyArray implements Graph {
    private static class GraphArray {
        private int[] pstart = new int[0], psize = new int[0];
        private int[] edges = new int[0];
        private int vertexSize, edgeSize;

        void addVertex(int v) {
            int size = v + 1;
            if (size > pstart.length) {
                int newSize = (size / 16 + 1) * 16;

                int[] resized = new int[newSize];
                System.arraycopy(pstart, 0, resized, 0, pstart.length);
                pstart = resized;

                resized = new int[newSize];
                System.arraycopy(psize, 0, resized, 0, psize.length);
                psize = resized;
            }

            if (size > vertexSize) {
                int prevVertex = vertexSize - 1;
                vertexSize = size;

                if (prevVertex > 0) {
                    int pos = pstart[prevVertex] + psize[prevVertex];
                    for (int i = prevVertex + 1; i < vertexSize; ++i) {
                        pstart[i] = pos;
                    }
                }
            }

            if (vertexSize > pstart.length) {
                throw new ArrayIndexOutOfBoundsException();
            }
        }

        void removeVertex(int v) {
            if (psize[v] > 0) {
                if (v + 1 < vertexSize) {
                    System.arraycopy(edges, pstart[v + 1], edges, pstart[v], edgeSize - pstart[v] - psize[v]);

                    edgeSize -= psize[v];
                    psize[v] = 0;

                    System.arraycopy(pstart, v, pstart, v + 1, vertexSize - v - 1);
                }
            }
        }

        void addEdge(int v, int e) {
            int index = Arrays.binarySearch(edges, pstart[v], pstart[v] + psize[v], e);
            if (index >= 0) return;
            index = - (index + 1);

            ++edgeSize;

            if (edgeSize > edges.length) {
                int[] resized = new int[edges.length + 16];
                System.arraycopy(edges, 0, resized, 0, edges.length);
                edges = resized;
            }

            if (v + 1 < vertexSize) {
                System.arraycopy(edges, pstart[v + 1], edges, pstart[v + 1] + 1, edgeSize - pstart[v + 1] - 1);

                for (int i = v + 1; i < vertexSize; ++i) {
                    ++pstart[i];
                }
            }

            System.arraycopy(edges, index, edges, index + 1, pstart[v] + psize[v] - index);

            ++psize[v];

            edges[index] = e;
        }

        void removeEdge(int v, int e) {
            int index = Arrays.binarySearch(edges, pstart[v], pstart[v] + psize[v], e);
            if (index < 0) return;

            --psize[v];

            System.arraycopy(edges, index + 1, edges, index, pstart[v] + psize[v] - index);

            if (v + 1 < vertexSize) {
                System.arraycopy(edges, pstart[v + 1], edges, pstart[v + 1] - 1, edgeSize - pstart[v + 1] - 1);

                for (int i = v + 1; i < vertexSize; ++i) {
                    --pstart[i];
                }
            }

            --edgeSize;
        }

        boolean isEdge(int v, int e) {
            return Arrays.binarySearch(edges, pstart[v], pstart[v] + psize[v], e) >= 0;
        }

        int[] getEdges(int v) {
            return Arrays.copyOfRange(edges, pstart[v], pstart[v] + psize[v]);
        }

        void getEdges(int v, int[] arr) {
            System.arraycopy(edges, pstart[v], arr, 0, psize[v]);
        }

        int getEdgeCount(int v) {
            return psize[v];
        }
    }

    private GraphArray front = new GraphArray();
    private GraphArray back = new GraphArray();
    private Set<Integer> vertices = new HashSet<>();  // vertex presence

    @Override
    public void addVertex(int v) {
        if (vertices.contains(v)) return;

        vertices.add(v);

        front.addVertex(v);
        back.addVertex(v);
    }

    @Override
    public void removeVertex(int v) {
        if (!vertices.contains(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));
        vertices.remove(v);

        front.removeVertex(v);
        back.removeVertex(v);
    }

    @Override
    public void addEdge(int v, int e) {
        if (!vertices.contains(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));
        if (!vertices.contains(e))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", e));

        front.addEdge(v, e);
        back.addEdge(e, v);
    }

    @Override
    public void removeEdge(int v, int e) {
        if (!vertices.contains(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));
        if (!vertices.contains(e))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", e));

        front.removeEdge(v, e);
        back.removeEdge(e, v);
    }

    @Override
    public boolean isVertex(int v) {
        return vertices.contains(v);
    }

    @Override
    public boolean isEdge(int v, int e) {
        return vertices.contains(v) && front.isEdge(v, e);
    }

    @Override
    /*public Set<Integer> getVertices() {
        return vertices;
    }*/
    public List<Integer> getVertices() {
        return new ArrayList<>(vertices);
    }

    @Override
    //public Set<Integer> getOutgoingVertices(int v) {
    public List<Integer> getOutgoingVertices(int v) {
        if (!vertices.contains(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        //return Arrays.stream(front.getEdges(v)).boxed().collect(Collectors.toSet());
        return Arrays.stream(front.getEdges(v)).boxed().collect(Collectors.toList());
    }

    @Override
    //public Set<Integer> getIncomingVertices(int v) {
    public List<Integer> getIncomingVertices(int v) {
        if (!vertices.contains(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        //return Arrays.stream(back.getEdges(v)).boxed().collect(Collectors.toSet());
        return Arrays.stream(back.getEdges(v)).boxed().collect(Collectors.toList());
    }

    @Override
    public int[] getVerticesAsArray() {
        return vertices.stream().mapToInt(Number::intValue).toArray();
    }

    @Override
    public int[] getOutgoingVerticesAsArray(int v) {
        if (!vertices.contains(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        return front.getEdges(v);
    }

    @Override
    public int[] getIncomingVerticesAsArray(int v) {
        if (!vertices.contains(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        return back.getEdges(v);
    }

    @Override
    public void getVerticesToArray(int[] arr) {
        System.arraycopy(vertices.stream().mapToInt(Number::intValue).toArray(), 0, arr, 0, vertices.size());
    }

    @Override
    public void getOutgoingVerticesToArray(int v, int[] arr) {
        if (!vertices.contains(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        front.getEdges(v, arr);
    }

    @Override
    public void getIncomingVerticesToArray(int v, int[] arr) {
        if (!vertices.contains(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        back.getEdges(v, arr);
    }

    @Override
    public int getVertexCount() {
        return vertices.size();
    }

    @Override
    public int getEdgeCount() {
        return front.edgeSize;
    }

    @Override
    public int getOutgoingVerticesCount(int v) {
        if (!vertices.contains(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        return front.getEdgeCount(v);
    }

    @Override
    public int getIncomingVerticesCount(int v) {
        if (!vertices.contains(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        return back.getEdgeCount(v);
    }
}
