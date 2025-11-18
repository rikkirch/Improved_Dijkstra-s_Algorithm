/********************************************************************
 *** NAME       : Rikesh Budhathoki                                ***
 *** CLASS      : CSC 492                                          ***
 *** ASSIGNMENT : Dijkstra's Algorithm (Improved Version)          ***
 *** DUE DATE   : TBD                                              ***
 *** INSTRUCTOR : Jun Huang                                        ***
 *********************************************************************
 *** DESCRIPTION : Implements an improved Dijkstra shortest-path
 ***               algorithm that combines:
 ***                 1) A min-priority queue (binary heap) to pick
 ***                    the next closest vertex, and
 ***                 2) A frontier-reduction step inspired by the
 ***                    research paper, which periodically shrinks
 ***                    the active frontier using a bounded set of
 ***                    vertices and several Bellman-Ford style
 ***                    relaxation passes.
 ***
 ***               The idea is to reduce the size of the frontier S
 ***               (the set of "incomplete" vertices) so that the
 ***               priority queue does not need to maintain a large
 ***               total order over Θ(n) vertices. Instead, we try
 ***               to keep the frontier on the order of |U| / k,
 ***               where U is the set of vertices with distances
 ***               below an upper bound B and k ≈ log n.            ***
 ********************************************************************/

package dijkstra.improved;

import java.util.*;

public class ImprovedDijkstra {

    /****************************************************************
     *** NESTED CLASS DistanceNode                                 ***
     *****************************************************************
     *** DESCRIPTION : Helper class used inside the priority queue
     ***               to store a vertex index and its current
     ***               tentative distance from the source. The
     ***               natural ordering is by distance value.       ***
     ****************************************************************/
    private static class DistanceNode implements Comparable<DistanceNode> {
        int vertex;
        int distance;

        DistanceNode(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(DistanceNode other) {
            return Integer.compare(this.distance, other.distance);
        }
    }

    /****************************************************************
     *** METHOD shortestPath                                       ***
     *****************************************************************
     *** DESCRIPTION : Computes the shortest-path distances from a
     ***               single source vertex to all other vertices
     ***               in the given graph using a priority-queue-
     ***               based Dijkstra algorithm. In addition to the
     ***               standard algorithm, this version periodically
     ***               applies a frontier-reduction step inspired by
     ***               the research paper:
     ***
     ***                 - It defines an upper bound B on distances.
     ***                 - It builds the set U of vertices with
     ***                   dist[u] < B.
     ***                 - If U is not "too large" compared to the
     ***                   frontier size, it runs k rounds of local
     ***                   Bellman-Ford-like relaxation on U and
     ***                   rebuilds the priority queue only from U.
     ***
     ***               This tends to shrink the active frontier and
     ***               reduce the amount of work done by the heap.  ***
     *** INPUT ARGS : Graph graph - directed weighted graph
     ***              int   source - source vertex index            ***
     *** OUTPUT ARGS: None                                          ***
     *** IN/OUT ARGS: None                                          ***
     *** RETURN     : int[] - array of shortest distances where
     ***                       result[v] is the cost of the
     ***                       shortest path from source to v.
     ***                       Integer.MAX_VALUE indicates that
     ***                       the vertex is unreachable.           ***
     ****************************************************************/
    public int[] shortestPath(Graph graph, int source) {
        int V = graph.getVertices();
        List<List<Graph.Node>> adj = graph.getAdjList();

        int[] dist = new int[V];
        boolean[] finalized = new boolean[V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        PriorityQueue<DistanceNode> pq = new PriorityQueue<>();
        pq.offer(new DistanceNode(source, 0));

        // Parameter k ~ log2(V+1). This controls how aggressively we shrink the frontier.
        final int k = Math.max(2, (int) Math.round(Math.log(V + 1) / Math.log(2)));
        int relaxSteps = 0;

        while (!pq.isEmpty()) {
            DistanceNode current = pq.poll();
            int u = current.vertex;

            // Skip if we have already finalized a better distance
            if (finalized[u]) {
                continue;
            }

            finalized[u] = true;

            // Relax outgoing edges from u
            for (Graph.Node neighbor : adj.get(u)) {
                int v = neighbor.vertex;
                int weight = neighbor.weight;

                if (!finalized[v] && dist[u] != Integer.MAX_VALUE
                        && dist[u] + weight < dist[v]) {

                    dist[v] = dist[u] + weight;
                    pq.offer(new DistanceNode(v, dist[v]));
                }
            }

            relaxSteps++;

            // Periodically trigger frontier reduction
            if (relaxSteps % k == 0 && !pq.isEmpty()) {
                shrinkFrontier(graph, dist, finalized, pq, k);
            }
        }

        return dist;
    }

    /****************************************************************
     *** METHOD shrinkFrontier                                     ***
     *****************************************************************
     *** DESCRIPTION : Frontier-reduction step inspired by the
     ***               research paper. The goal is to reduce the
     ***               size of the active frontier S maintained by
     ***               the priority queue.
     ***
     ***               1) Compute an upper bound B based on the
     ***                  current smallest distance in the frontier.
     ***               2) Build the set U of vertices with dist[u] < B.
     ***               3) If U is not too large compared to the
     ***                  frontier, run k rounds of local relaxation
     ***                  (Bellman-Ford style) over vertices in U.
     ***               4) Rebuild the priority queue so that it only
     ***                  contains vertices from U that are not yet
     ***                  finalized. This shrinks the frontier.      ***
     *** INPUT ARGS : Graph graph        - graph being processed
     ***              int[] dist         - current distance estimates
     ***              boolean[] finalized- finalized flags for vertices
     ***              PriorityQueue pq   - current priority queue
     ***              int k              - number of BF-style passes  ***
     *** OUTPUT ARGS: None                                          ***
     *** IN/OUT ARGS: dist[] may be updated                          ***
     *** RETURN     : void                                          ***
     ****************************************************************/
    private void shrinkFrontier(
            Graph graph,
            int[] dist,
            boolean[] finalized,
            PriorityQueue<DistanceNode> pq,
            int k) {

        if (pq.isEmpty()) {
            return;
        }

        // Step 1: find smallest distance in the current frontier
        int minFrontierDist = Integer.MAX_VALUE;
        for (DistanceNode node : pq) {
            if (node.distance < minFrontierDist) {
                minFrontierDist = node.distance;
            }
        }

        if (minFrontierDist == Integer.MAX_VALUE) {
            return;
        }

        // Simple upper bound: B = minFrontierDist + "window"
        // In the paper this is more sophisticated, but here we
        // choose a practical constant factor window.
        int B = minFrontierDist + 10;   // 10 is a tunable parameter

        int V = graph.getVertices();
        List<List<Graph.Node>> adj = graph.getAdjList();

        // Step 2: build U = { u | dist[u] < B and not finalized }
        List<Integer> U = new ArrayList<>();
        for (int u = 0; u < V; u++) {
            if (!finalized[u] && dist[u] != Integer.MAX_VALUE && dist[u] < B) {
                U.add(u);
            }
        }

        // If U is empty or clearly larger than the frontier, skip reduction
        int frontierSize = pq.size();
        if (U.isEmpty() || U.size() > k * frontierSize) {
            return;
        }

        // Step 3: Run k rounds of local Bellman-Ford style relaxation over U
        for (int iter = 0; iter < k; iter++) {
            boolean changed = false;

            for (int u : U) {
                if (dist[u] == Integer.MAX_VALUE) {
                    continue;
                }

                for (Graph.Node neighbor : adj.get(u)) {
                    int v = neighbor.vertex;
                    int weight = neighbor.weight;

                    if (!finalized[v] && dist[u] + weight < dist[v]) {
                        dist[v] = dist[u] + weight;
                        changed = true;
                    }
                }
            }

            // Optional early exit: if an iteration makes no changes, we can stop
            if (!changed) {
                break;
            }
        }

        // Step 4: Rebuild the priority queue from the reduced set U
        pq.clear();
        for (int u : U) {
            if (!finalized[u] && dist[u] != Integer.MAX_VALUE) {
                pq.offer(new DistanceNode(u, dist[u]));
            }
        }
    }
}
