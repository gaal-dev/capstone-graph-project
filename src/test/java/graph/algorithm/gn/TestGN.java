package graph.algorithm.gn;

import graph.adjacency.AdjacencyArray;
import graph.adjacency.AdjacencyList;
import graph.adjacency.AdjacencyMatrix;
import graph.adjacency.Graph;
import graph.algorithm.girvan_newman.Edge;
import graph.algorithm.girvan_newman.GN;
import graph.loader.GraphLoader;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertEquals;

public class TestGN {
    private GraphLoader graphLoader = new GraphLoader();
    private String filepath = getClass().getClassLoader().getResource("small_test_graph2.txt").getPath();

    private void assertGraph_bfs(Graph graph) {
        long timestamp = System.nanoTime();

        GraphLoader.loadGraph(graph, filepath);

        System.out.println("loadGraph " + (System.nanoTime() -  timestamp));

        timestamp = System.nanoTime();

        GN gn = new GN(graph);
        List<Integer> path = gn.bfs(1, 14);

        System.out.println("bfs 1 " + (System.nanoTime() -  timestamp));

        assertThat(path, contains(1, 3, 7, 8, 12, 14));

        timestamp = System.nanoTime();

        path = gn.bfs(5, 10);

        System.out.println("bfs 2 " + (System.nanoTime() -  timestamp));

        assertThat(path, contains(5, 6, 7, 8, 9, 10));
    }

    @Test
    public void testAdjacencyList_bfs() {
        AdjacencyList list = new AdjacencyList();
        assertGraph_bfs(list);
    }

    @Test
    public void testAdjacencyMatrix_bfs() {
        AdjacencyMatrix matrix = new AdjacencyMatrix();
        assertGraph_bfs(matrix);
    }

    @Test
    public void testAdjacencyArray_bfs() {
        AdjacencyArray array = new AdjacencyArray();
        assertGraph_bfs(array);
    }

    private void assertGraph_community(Graph graph) {
        long timestamp = System.nanoTime();

        GraphLoader.loadGraph(graph, filepath);

        System.out.println("loadGraph " + (System.nanoTime() -  timestamp));

        timestamp = System.nanoTime();

        GN gn = new GN(graph);
        List<Edge> bridges = gn.getCommunityBridges(); // + dendrogram

        System.out.println(bridges);

        System.out.println("bridges " + (System.nanoTime() -  timestamp));

        assertEquals(bridges.size(), 17);
    }

    @Test
    public void testAdjacencyList_community() {
        long timestamp = System.nanoTime();

        AdjacencyList list = new AdjacencyList();
        assertGraph_community(list);

        System.out.println("adjacency list " + (System.nanoTime() -  timestamp));
    }

    @Test
    public void testAdjacencyMatrix_community() {
        long timestamp = System.nanoTime();

        AdjacencyMatrix matrix = new AdjacencyMatrix();
        assertGraph_community(matrix);

        System.out.println("adjacency matrix " + (System.nanoTime() -  timestamp));
    }

    @Test
    public void testAdjacencyArray_community() {
        long timestamp = System.nanoTime();

        AdjacencyArray array = new AdjacencyArray();
        assertGraph_community(array);

        System.out.println("adjacency array "  + (System.nanoTime() -  timestamp));
    }
}
