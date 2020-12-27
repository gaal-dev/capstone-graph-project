package graph.adjacency;

import java.util.*;

public class AdjacencyList implements Graph {
    //private Map<Integer, Set<Integer>> front = new HashMap<>();
    //private Map<Integer, Set<Integer>> back = new HashMap<>();
    private Map<Integer, List<Integer>> front = new HashMap<>();
    private Map<Integer, List<Integer>> back = new HashMap<>();
    private int edgeCount;

    @Override
    public void addVertex(int v) {
        if (front.containsKey(v)) return;

        /*front.put(v, new HashSet<>());
        back.put(v, new HashSet<>());*/
        front.put(v, new ArrayList<>());
        back.put(v, new ArrayList<>());
    }

    @Override
    public void removeVertex(int v) {
        if (!front.containsKey(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        for(Integer e : front.get(v)) {
            //back.get(e).remove(v);
            back.get(e).remove(Integer.valueOf(v));
        }

        edgeCount -= front.get(v).size();

        front.remove(v);
    }

    @Override
    public void addEdge(int v, int e) {
        if (!front.containsKey(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));
        if (!back.containsKey(e))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", e));

        front.get(v).add(e);
        back.get(e).add(v);

        ++edgeCount;
    }

    @Override
    public void removeEdge(int v, int e) {
        if (!front.containsKey(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));
        if (!back.containsKey(e))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", e));

        /*front.get(v).remove(e);
        back.get(e).remove(v);*/
        front.get(v).remove(Integer.valueOf(e));
        back.get(e).remove(Integer.valueOf(v));

        --edgeCount;
    }

    @Override
    public boolean isVertex(int v) {
        return front.containsKey(v);
    }

    @Override
    public boolean isEdge(int v, int e) {
        return front.containsKey(v) && front.get(v).contains(e);
    }

    @Override
    //public Set<Integer> getVertices() {
    public List<Integer> getVertices() {
        return new ArrayList<>(front.keySet());
    }

    @Override
    //public Set<Integer> getOutgoingVertices(int v) {
    public List<Integer> getOutgoingVertices(int v) {
        if (!front.containsKey(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        return front.get(v);
    }

    @Override
    //public Set<Integer> getIncomingVertices(int v) {
    public List<Integer> getIncomingVertices(int v) {
        if (!back.containsKey(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        return back.get(v);
    }

    @Override
    public int[] getVerticesAsArray() {
        return front.keySet().stream().mapToInt(Number::intValue).toArray();
    }

    @Override
    public int[] getOutgoingVerticesAsArray(int v) {
        if (!front.containsKey(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        return front.get(v).stream().mapToInt(Number::intValue).toArray();
    }

    @Override
    public int[] getIncomingVerticesAsArray(int v) {
        if (!back.containsKey(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        return back.get(v).stream().mapToInt(Number::intValue).toArray();
    }

    @Override
    public void getVerticesToArray(int[] arr) {
        System.arraycopy(front.keySet().stream().mapToInt(Number::intValue).toArray(), 0, arr, 0, front.keySet().size());
    }

    @Override
    public void getOutgoingVerticesToArray(int v, int[] arr) {
        if (!front.containsKey(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        System.arraycopy(front.get(v).stream().mapToInt(Number::intValue).toArray(), 0, arr, 0, front.get(v).size());
    }

    @Override
    public void getIncomingVerticesToArray(int v, int[] arr) {
        if (!back.containsKey(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        System.arraycopy(back.get(v).stream().mapToInt(Number::intValue).toArray(), 0, arr, 0, back.get(v).size());
    }

    @Override
    public int getVertexCount() {
        return front.keySet().size();
    }

    @Override
    public int getEdgeCount() {
        return edgeCount;
    }

    @Override
    public int getOutgoingVerticesCount(int v) {
        if (!front.containsKey(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        return front.get(v).size();
    }

    @Override
    public int getIncomingVerticesCount(int v) {
        if (!back.containsKey(v))
            throw new IllegalArgumentException(String.format("The vertex %d does not exist", v));

        return back.get(v).size();
    }
}
