package graph.algorithm.lpa;

import graph.adjacency.Graph;

import java.util.*;

// label propagation

public class LPA {
    private Graph graph;

    public LPA(Graph g) {
        graph = g;
    }

    private static void randomizeArray(int[] arr, int size){
        Random rnd = new Random();

        for (int i = 0; i < size; ++i) {
            int pos = rnd.nextInt(size);

            int tmp = arr[i];
            arr[i] = arr[pos];
            arr[pos] = tmp;
        }
    }

    /*public Map<Integer, List<Integer>> getCommunities(int iterations) {
        Map<Integer, Integer> labels = new HashMap<>();
        graph.getVertices().forEach((v) -> labels.put(v, v));

        List<Integer> vertices = new ArrayList<>(graph.getVertices());
        List<Integer> neighbors = new ArrayList<>();
        Map<Integer, Integer> freq = new HashMap<>();

        Integer label, max;
        boolean changed = true;

        while (--iterations >= 0 && changed) {
            Collections.shuffle(vertices);

            changed = false;

            for (Integer v : vertices) {
                neighbors.clear();
                neighbors.addAll(graph.getOutgoingVertices(v));

                Collections.shuffle(neighbors); // vertices are shuffled because several vertices can have the same the highest frequency so the last is chosen

                freq.clear();
                max = 0;

                for (Integer neighbor : neighbors) {
                    label = labels.get(neighbor);
                    freq.put(label, freq.getOrDefault(label, 0) + 1);

                    if (freq.get(label) >= max) {
                        max = freq.get(label);

                        if (labels.get(v).compareTo(label) != 0) {
                            labels.put(v, label);
                            changed = true;
                        }
                    }
                }
            }
        }

        Map<Integer, List<Integer>> communities = new HashMap<>();

        labels.forEach((v, l) -> {
            if (!communities.containsKey(l)) {
                communities.put(l, new ArrayList<>());
            }

            communities.get(l).add(v);
        });

        return communities;
    }*/

    public Map<Integer, List<Integer>> getCommunities(int iterations) {
        Map<Integer, Integer> labels = new HashMap<>();
        graph.getVertices().forEach((v) -> labels.put(v, v));

        int[] vertices = graph.getVerticesAsArray(), neighbors = new int[0];
        int size = 0;
        Map<Integer, Integer> freq = new HashMap<>();

        Integer label, max;
        boolean changed = true;

        while (--iterations >= 0 && changed) {
            randomizeArray(vertices, vertices.length);

            changed = false;

            for (int v : vertices) {
                int count = graph.getOutgoingVerticesCount(v);

                if (count > size) {
                    size = count;
                    neighbors = new int[count];
                }

                graph.getOutgoingVerticesToArray(v, neighbors);

                randomizeArray(neighbors, count); // vertices are shuffled because several vertices can have the same the highest frequency so the last is chosen

                freq.clear();
                max = 0;

                for (int i = 0; i < count; ++i) {
                    int neighbor = neighbors[i];

                    label = labels.get(neighbor);
                    freq.put(label, freq.getOrDefault(label, 0) + 1);

                    if (freq.get(label) >= max) {
                        max = freq.get(label);

                        if (labels.get(v).compareTo(label) != 0) {
                            labels.put(v, label);
                            changed = true;
                        }
                    }
                }
            }
        }

        Map<Integer, List<Integer>> communities = new HashMap<>();

        labels.forEach((v, l) -> {
            if (!communities.containsKey(l)) {
                communities.put(l, new ArrayList<>());
            }

            communities.get(l).add(v);
        });

        return communities;
    }
}
