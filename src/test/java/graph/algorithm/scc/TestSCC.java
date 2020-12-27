package graph.algorithm.scc;

import graph.adjacency.AdjacencyArray;
import graph.adjacency.AdjacencyList;
import graph.adjacency.AdjacencyMatrix;
import graph.adjacency.Graph;
import graph.loader.GraphLoader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@RunWith(Parameterized.class)
public class TestSCC {
    private GraphLoader graphLoader = new GraphLoader();

    @Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10}
        });
    }

    private int index;
    private String testFilePath, answerFilePath;

    public TestSCC(int index) {
        this.index = index;

        ClassLoader classLoader = getClass().getClassLoader();

        testFilePath = classLoader.getResource("scc/test_" + index + ".txt").getPath();
        answerFilePath = classLoader.getResource("scc_answers/scc_" + index + ".txt").getPath();
    }

    private void assertGraph(Graph graph) throws IOException {
        long timestamp = System.nanoTime();

        GraphLoader.loadGraph(graph, testFilePath);

        System.out.println("loadGraph " + index + " " + (System.nanoTime() -  timestamp));

        timestamp = System.nanoTime();

        List<Graph> sccs = new SCC(graph).getSCCs();

        System.out.println("getSCCs " + index + " " + (System.nanoTime() -  timestamp));

        BufferedReader br = new BufferedReader(new FileReader(answerFilePath));

        List<Set<Integer>> answers = new ArrayList<>();
        Set<Integer> vertices;
        String line;

        while ((line = br.readLine()) != null) {
            Scanner sc = new Scanner(line);

            vertices = new TreeSet<>();
            while (sc.hasNextInt()) {
                vertices.add(sc.nextInt());
            }
            answers.add(vertices);

            sc.close();
        }

        for (Graph scc : sccs) {
            //vertices = scc.getVertices();
            vertices = new HashSet<>(scc.getVertices());
            assertTrue(answers.contains(vertices));
        }

        assertEquals(sccs.size(), answers.size());
    }

    @Test
    public void testAdjacencyList() throws IOException {
        long timestamp = System.nanoTime();

        AdjacencyList list = new AdjacencyList();
        assertGraph(list);

        System.out.println("adjacency list " + index + " " + (System.nanoTime() -  timestamp));
    }

    @Test
    public void testAdjacencyMatrix() throws IOException {
        long timestamp = System.nanoTime();

        AdjacencyMatrix matrix = new AdjacencyMatrix();
        assertGraph(matrix);

        System.out.println("adjacency matrix " + index + " " + (System.nanoTime() -  timestamp));
    }

    @Test
    public void testAdjacencyArray() throws IOException {
        long timestamp = System.nanoTime();

        AdjacencyArray array = new AdjacencyArray();
        assertGraph(array);

        System.out.println("adjacency array " + index + " " + (System.nanoTime() -  timestamp));
    }
}
