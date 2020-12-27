package graph.loader;

import graph.adjacency.Graph;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class GraphLoader {
    public static void loadGraph(Graph graph, String filename) {
        Set<Integer> seen = new HashSet<Integer>();

        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        while (sc.hasNextInt()) {
            int v1 = sc.nextInt();
            int v2 = sc.nextInt();

            if (!seen.contains(v1)) {
                graph.addVertex(v1);
                seen.add(v1);
            }

            if (!seen.contains(v2)) {
                graph.addVertex(v2);
                seen.add(v2);
            }

            graph.addEdge(v1, v2);
        }

        sc.close();
    }
}
