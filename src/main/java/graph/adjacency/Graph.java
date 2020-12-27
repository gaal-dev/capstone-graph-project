package graph.adjacency;

import java.util.List;
//import java.util.Set;

public interface Graph {
    void addVertex(int v);

    void removeVertex(int v);

    void addEdge(int v, int e);

    void removeEdge(int v, int e);

    boolean isVertex(int v);

    boolean isEdge(int v, int e);

    /*Set<Integer> getVertices();

    Set<Integer> getOutgoingVertices(int v);

    Set<Integer> getIncomingVertices(int v);*/

    List<Integer> getVertices();

    List<Integer> getOutgoingVertices(int v);

    List<Integer> getIncomingVertices(int v);

    int[] getVerticesAsArray();

    int[] getOutgoingVerticesAsArray(int v);

    int[] getIncomingVerticesAsArray(int v);

    void getVerticesToArray(int[] arr);

    void getOutgoingVerticesToArray(int v, int[] arr);

    void getIncomingVerticesToArray(int v, int[] arr);

    int getVertexCount();

    int getEdgeCount();

    int getOutgoingVerticesCount(int v);

    int getIncomingVerticesCount(int v);
}
