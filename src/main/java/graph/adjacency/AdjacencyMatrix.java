package graph.adjacency;

import java.util.*;

public class AdjacencyMatrix implements Graph {
    private boolean[][] matrix = new boolean[0][0];
    private Set<Integer> vertices = new HashSet<>(); // vertex presence
    private int edgeCount;

    @Override
    public void addVertex(int v) {
        if (vertices.contains(v)) return;

        vertices.add(v);

        int size = v + 1;
        if (size > matrix.length) {
            boolean[][] resized = new boolean[size][size];

            for(int i = 0; i < matrix.length; ++i) {
                System.arraycopy(matrix[i], 0, resized[i], 0, matrix[i].length);
            };

            matrix = resized;
        }
    }

    @Override
    public void removeVertex(int v) {
        if (!vertices.contains(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        vertices.remove(v);

        for(int i = 0; i < matrix[v].length; ++i) {
            if (matrix[v][i]) --edgeCount;
            matrix[v][i] = false;
        }
    }

    @Override
    public void addEdge(int v, int e) {
        if (!vertices.contains(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));
        if (!vertices.contains(e))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", e));

        if (!matrix[v][e]) ++edgeCount;
        matrix[v][e] = true;
    }

    @Override
    public void removeEdge(int v, int e) {
        if (!vertices.contains(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));
        if (!vertices.contains(e))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", e));

        if (matrix[v][e]) --edgeCount;
        matrix[v][e] = false;
    }

    @Override
    public boolean isVertex(int v) {
        return vertices.contains(v);
    }

    @Override
    public boolean isEdge(int v, int e) {
        return vertices.contains(v) && vertices.contains(e) && matrix[v][e];
    }

    @Override
    //public Set<Integer> getVertices() {
    public List<Integer> getVertices() {
        return new ArrayList<>(vertices);
    }

    @Override
    //public Set<Integer> getOutgoingVertices(int v) {
    public List<Integer> getOutgoingVertices(int v) {
        if (!vertices.contains(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        //Set<Integer> vertices = new HashSet<>();
        List<Integer> vertices = new ArrayList<>();

        for(int i = 0; i < matrix[v].length; ++i) {
            if (matrix[v][i]) {
                vertices.add(i);
            }
        }

        return vertices;
    }

    @Override
    //public Set<Integer> getIncomingVertices(int v) {
    public List<Integer> getIncomingVertices(int v) {
        if (!vertices.contains(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        //Set<Integer> vertices = new HashSet<>();
        List<Integer> vertices = new ArrayList<>();

        for(int i = 0; i < matrix.length; ++i) {
            if (matrix[i][v]) {
                vertices.add(i);
            }
        }

        return vertices;
    }

    @Override
    public int[] getVerticesAsArray() {
        return vertices.stream().mapToInt(Number::intValue).toArray();
    }

    @Override
    public int[] getOutgoingVerticesAsArray(int v) {
        if (!vertices.contains(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        int[] res = new int[matrix[v].length];
        int pos = 0;

        for(int i = 0; i < matrix[v].length; ++i) {
            if (matrix[v][i]) {
                res[pos++] = i;
            }
        }

        return Arrays.copyOf(res, pos);
    }

    @Override
    public int[] getIncomingVerticesAsArray(int v) {
        if (!vertices.contains(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        int[] res = new int[matrix[v].length];
        int pos = 0;

        for(int i = 0; i < matrix.length; ++i) {
            if (matrix[i][v]) {
                res[pos++] = i;
            }
        }

        return Arrays.copyOf(res, pos);
    }

    @Override
    public void getVerticesToArray(int[] arr) {
        System.arraycopy(vertices.stream().mapToInt(Number::intValue).toArray(), 0, arr, 0, vertices.size());
    }

    @Override
    public void getOutgoingVerticesToArray(int v, int[] arr) {
        if (!vertices.contains(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        int pos = 0;

        for(int i = 0; i < matrix[v].length; ++i) {
            if (matrix[v][i]) {
                arr[pos++] = i;
            }
        }
    }

    @Override
    public void getIncomingVerticesToArray(int v, int[] arr) {
        if (!vertices.contains(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        int pos = 0;

        for(int i = 0; i < matrix.length; ++i) {
            if (matrix[i][v]) {
                arr[pos++] = i;
            }
        }
    }

    @Override
    public int getVertexCount() {
        return vertices.size();
    }

    @Override
    public int getEdgeCount() {
        return edgeCount;
    }

    @Override
    public int getOutgoingVerticesCount(int v) {
        if (!vertices.contains(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        int edgeCount = 0;

        for(int i = 0; i < matrix[v].length; ++i) {
            if (matrix[v][i]) {
                ++edgeCount;
            }
        }

        return edgeCount;
    }

    @Override
    public int getIncomingVerticesCount(int v) {
        if (!vertices.contains(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        int edgeCount = 0;

        for(int i = 0; i < matrix.length; ++i) {
            if (matrix[i][v]) {
                ++edgeCount;
            }
        }

        return edgeCount;
    }
}
