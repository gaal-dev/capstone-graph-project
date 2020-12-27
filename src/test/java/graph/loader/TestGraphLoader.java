package graph.loader;

import graph.adjacency.AdjacencyArray;
import graph.adjacency.AdjacencyList;
import graph.adjacency.AdjacencyMatrix;
import graph.adjacency.Graph;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestGraphLoader {
    private GraphLoader graphLoader = new GraphLoader();
    private String filepath = getClass().getClassLoader().getResource("small_test_graph.txt").getPath();

    private void assertGraph(Graph graph) {
        GraphLoader.loadGraph(graph, filepath);

        assertThat(graph.getVertices(), contains(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14));

        int[][] edges = {{1, 2}, {2, 1}, {2, 3}, {3, 2}, {1, 3}, {3, 1}, {3, 7}, {7, 3}, {4, 5}, {5, 4}, {4, 6},
                {6, 4}, {5, 6}, {6, 5}, {6, 7}, {7, 6}, {7, 8}, {8, 7}, {8, 9}, {9, 8}, {9, 10}, {10, 9}, {10, 11},
                {11, 10}, {9, 11}, {11, 9}, {8, 12}, {12, 8}, {12, 13}, {13, 12}, {12, 14}, {14, 12}, {13, 14}, {14, 13}};

        for (int[] edge : edges) {
            assertTrue(graph.isEdge(edge[0], edge[1]));
        }

        int[][] outgoingEdges = {{2, 3}, {1, 3}, {1, 2, 7}, {5, 6}, {4, 6}, {4, 5, 7}, {3, 6, 8}, {7, 9, 12},
                {8, 10, 11}, {9, 11}, {9, 10}, {8, 13, 14}, {12, 14}, {12, 13}};

        for (int i = 0; i < outgoingEdges.length; ++i) {
            Set<Integer> outgoingEdge = Arrays.stream(outgoingEdges[i]).boxed().collect(Collectors.toSet());

            assertEquals(new HashSet<>(graph.getOutgoingVertices(i + 1)), outgoingEdge);
        }

        int[][] incomingEdges = {{2, 3}, {1, 3}, {1, 2, 7}, {5, 6}, {4, 6}, {4, 5, 7}, {3, 6, 8}, {7, 9, 12},
                {8, 10, 11}, {9, 11}, {9, 10}, {8, 13, 14}, {12, 14}, {12, 13}};

        for (int i = 0; i < incomingEdges.length; ++i) {
            Set<Integer> incomingEdge = Arrays.stream(incomingEdges[i]).boxed().collect(Collectors.toSet());

            assertEquals(new HashSet<>(graph.getIncomingVertices(i + 1)), incomingEdge);
        }
    }

    @Test
    public void testAdjacencyList() {
        AdjacencyList list = new AdjacencyList();
        assertGraph(list);
    }

    @Test
    public void testAdjacencyMatrix() {
        AdjacencyMatrix matrix = new AdjacencyMatrix();
        assertGraph(matrix);
    }

    @Test
    public void testAdjacencyArray() {
        AdjacencyArray array = new AdjacencyArray();
        assertGraph(array);
    }
}
