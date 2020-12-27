package graph.algorithm.scc;

import graph.adjacency.Graph;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Consumer;

// strongly connected components

public class SCC {
    private Graph graph;

    public SCC(Graph g) {
        graph = g;
    }

    private List<Integer> dfs(Graph graph, List<Integer> vertices) {
        return dfs(graph, vertices, l -> {});
    }

    private List<Integer> dfs(Graph graph, List<Integer> vertices, Consumer<List<Integer>> sequencer) {
        HashSet<Integer> visited = new HashSet<>(vertices.size());
        ArrayList<Integer> finished = new ArrayList<>(vertices.size());
        ArrayList<Integer> queue = new ArrayList<>(vertices);

        while (!queue.isEmpty()) {
            Integer curr = queue.remove(0);
            if (!visited.contains(curr)) {
                int size = finished.size();

                dfs_visit(graph, curr, visited, finished);

                if (sequencer != null) {
                    sequencer.accept(finished.subList(size, finished.size()));
                }
            }
        }

        return finished;
    }

    /*private void dfs_visit(Graph graph, Integer curr, Set<Integer> visited, List<Integer> finished) {
        visited.add(curr);

        if (graph.isVertex(curr)) {
            for (Integer next : graph.getOutgoingVertices(curr)) {
                if (!visited.contains(next)) {
                    dfs_visit(graph, next, visited, finished);
                }
            }
        }

        finished.add(curr);
    }*/

    private void dfs_visit(Graph graph, Integer curr, Set<Integer> visited, List<Integer> finished) {
        visited.add(curr);

        if (graph.isVertex(curr)) {
            for (int next : graph.getOutgoingVerticesAsArray(curr)) {
                if (!visited.contains(next)) {
                    dfs_visit(graph, next, visited, finished);
                }
            }
        }

        finished.add(curr);
    }

    private Graph newGraph() {
        try {
            return graph.getClass().getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException ex) {
            throw new RuntimeException();
        }
    }

    /*private Graph reverseGraph(Graph graph) {
        Graph reversed = newGraph();

        for (Integer v : graph.getVertices()) {
            reversed.addVertex(v);

            for (Integer e : graph.getOutgoingVertices(v)) {
                reversed.addVertex(e);

                reversed.addEdge(e, v);
            }
        }

        return reversed;
    }*/

    private Graph reverseGraph(Graph graph) {
        Graph reversed = newGraph();

        int[] arr = new int[0];
        int size = 0;

        for (int v : graph.getVerticesAsArray()) {
            reversed.addVertex(v);

            int count = graph.getOutgoingVerticesCount(v);

            if (count > size) {
                size = count;
                arr = new int[size];
            }

            graph.getOutgoingVerticesToArray(v, arr);

            for (int i = 0; i < count; ++ i) {
                int e = arr[i];

                reversed.addVertex(e);
                reversed.addEdge(e, v);
            }
        }

        return reversed;
    }

    public List<Graph> getSCCs() {
        List<Graph> graphs = new ArrayList<>();

        List<Integer> finished = dfs(graph, new ArrayList<>(graph.getVertices()));

        Collections.reverse(finished);

        Graph reversed = reverseGraph(graph);

        dfs(reversed, finished, (sequence) -> {
            Graph g = newGraph();

            for (Integer v : sequence) {
                g.addVertex(v);

                //Set<Integer> edges = g.getOutgoingVertices(v);
                List<Integer> edges = g.getOutgoingVertices(v);
                edges.retainAll(sequence);

                for (Integer e : edges) {
                    g.addVertex(e);
                    g.addEdge(v, e);
                }
            }

            graphs.add(g);
        });

        return graphs;
    }
}
