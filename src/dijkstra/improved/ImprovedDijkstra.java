/********************************************************************
 *** NAME       : Rikesh Budhathoki                                ***
 *** CLASS      : CSC 492                                          ***
 *** ASSIGNMENT : Dijkstra's Algorithm (Improved Version)          ***
 *** DUE DATE   : TBD                                              ***
 *** INSTRUCTOR : Jun Huang                                        ***
 *********************************************************************
 *** DESCRIPTION : Implements an improved Dijkstra shortest-path
 ***               algorithm using a min-priority queue (binary
 ***               heap) from java.util.PriorityQueue. This reduces
 ***               the time complexity to O((V + E) log V) compared
 ***               to the classical O(V^2) array-based approach.
 ***               Computes the minimum cost from a chosen source
 ***               vertex to every other vertex in a directed graph
 ***               with non-negative edge weights.                 ***
 ********************************************************************/

package dijkstra.improved;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

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
     ***               in the given graph using an improved Dijkstra
     ***               algorithm that leverages a priority queue.
     ***               Complexity: O((V + E) log V).                ***
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
        boolean[] visited = new boolean[V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        // Min-priority queue ordered by current distance
        PriorityQueue<DistanceNode> pq = new PriorityQueue<>();
        pq.offer(new DistanceNode(source, 0));

        while (!pq.isEmpty()) {
            DistanceNode current = pq.poll();
            int u = current.vertex;

            // Skip if we have already finalized a better distance
            if (visited[u]) {
                continue;
            }

            visited[u] = true;

            for (Graph.Node neighbor : adj.get(u)) {
                int v = neighbor.vertex;
                int weight = neighbor.weight;

                if (!visited[v] && dist[u] != Integer.MAX_VALUE
                        && dist[u] + weight < dist[v]) {

                    dist[v] = dist[u] + weight;
                    pq.offer(new DistanceNode(v, dist[v]));
                }
            }
        }

        return dist;
    }
}
