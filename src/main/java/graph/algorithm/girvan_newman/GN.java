package graph.algorithm.girvan_newman;

import graph.adjacency.Graph;

import java.util.*;
import java.util.function.BiConsumer;

public class GN {
    private Graph graph;

    public GN(Graph g) {
        graph = g;
    }

    private List<Integer> constructPath(int start, int goal, Map<Integer, Integer> parentMap, BiConsumer<Integer, Integer> consumer) {
        LinkedList<Integer> path = new LinkedList<>();

        int curr = goal, prev;
        while (curr != start) {
            path.add(0, curr);
            prev = curr;
            curr = parentMap.get(curr);

            consumer.accept(curr, prev);
        }

        path.add(0, start);

        return path;
    }

    public List<Integer> bfs(int start, int goal) {
        return bfs(start, goal, (v, e) -> {
        });
    }

    /*private List<Integer> bfs(int start, int goal, BiConsumer<Integer, Integer> consumer) {
        List<Integer> queue = new LinkedList<>();
        Set<Integer> discovered = new HashSet<>();
        Map<Integer, Integer> parentMap = new HashMap<>();

        queue.add(start);
        discovered.add(start);

        while (!queue.isEmpty()) {
            int curr = queue.remove(0);

            if (curr == goal) {
                return constructPath(start, goal, parentMap, consumer);
            }

            for (Integer v : graph.getOutgoingVertices(curr)) {
                if (!discovered.contains(v)) {
                    discovered.add(v);
                    queue.add(v);
                    parentMap.put(v, curr);
                }
            }
        }

        return new LinkedList<>();
    }*/

    private List<Integer> bfs(int start, int goal, BiConsumer<Integer, Integer> consumer) {
        List<Integer> queue = new LinkedList<>();
        Set<Integer> discovered = new HashSet<>();
        Map<Integer, Integer> parentMap = new HashMap<>();

        queue.add(start);
        discovered.add(start);

        while (!queue.isEmpty()) {
            int curr = queue.remove(0);

            if (curr == goal) {
                return constructPath(start, goal, parentMap, consumer);
            }

            for (int v : graph.getOutgoingVerticesAsArray(curr)) {
                if (!discovered.contains(v)) {
                    discovered.add(v);
                    queue.add(v);
                    parentMap.put(v, curr);
                }
            }
        }

        return new LinkedList<>();
    }

    public List<Edge> getCommunityBridges() {
        List<Integer> vertices = new ArrayList<>(graph.getVertices());
        List<Edge> bridges = new ArrayList<>();

        Map<Edge, Integer> freq = new HashMap<>();

        while (graph.getEdgeCount() > 0) {
            freq.clear();

            for (int i = 0; i < vertices.size() - 1; ++i) {
                for (int j = i + 1; j < vertices.size(); ++j) {
                    bfs(vertices.get(i), vertices.get(j), (v, e) -> {
                        if (v > e) { // xor swap algorithm
                            v = v ^ e;
                            e = e ^ v;
                            v = v ^ e;
                        }

                        Edge edge = new Edge(v, e);
                        freq.put(edge, freq.getOrDefault(edge, 0) + 1);
                    });
                }
            }

            Optional<Map.Entry<Edge, Integer>> maxEntry = freq.entrySet().stream().max((entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue()));

            if (maxEntry.isPresent()) { // maximal betweenness
                Edge edge = maxEntry.get().getKey();

                if (graph.isEdge(edge.getV(), edge.getE())) {
                    graph.removeEdge(edge.getV(), edge.getE());
                }
                if (graph.isEdge(edge.getE(), edge.getV())) {
                    graph.removeEdge(edge.getE(), edge.getV());
                }

                bridges.add(edge);
            } else {
                break;
            }
        }

        return bridges;
    }
}
