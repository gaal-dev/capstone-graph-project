package graph.algorithm.lpa;

import graph.adjacency.AdjacencyArray;
import graph.adjacency.AdjacencyList;
import graph.adjacency.AdjacencyMatrix;
import graph.adjacency.Graph;
import graph.loader.GraphLoader;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertEquals;

public class TestLPA {
    private GraphLoader graphLoader = new GraphLoader();
    private String filepath = getClass().getClassLoader().getResource("small_test_graph2.txt").getPath();
    // private String filepath = getClass().getClassLoader().getResource("facebook_1000.txt").getPath();
    // private String filepath = getClass().getClassLoader().getResource("facebook_2000.txt").getPath();
    // private String filepath = getClass().getClassLoader().getResource("facebook_ucsd.txt").getPath();

    private void assertGraph_community(Graph graph) {
        long timestamp = System.nanoTime();

        GraphLoader.loadGraph(graph, filepath);

        System.out.println("loadGraph " + (System.nanoTime() -  timestamp));

        timestamp = System.nanoTime();

        LPA lpa = new LPA(graph);
        //Map<Integer, List<Integer>> communities = lpa.getCommunities(graph.getVertexCount());
        Map<Integer, List<Integer>> communities = lpa.getCommunities(10);

        System.out.println("communities " + (System.nanoTime() -  timestamp));

        System.out.println(communities);
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
