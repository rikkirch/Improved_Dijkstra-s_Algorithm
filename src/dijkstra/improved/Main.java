/********************************************************************
 *** NAME       : Rikesh Budhathoki                                ***
 *** CLASS      : CSC 492                                          ***
 *** ASSIGNMENT : Dijkstra's Algorithm (Improved Version)          ***
 *** DUE DATE   : TBD                                              ***
 *** INSTRUCTOR : Jun Huang                                        ***
 *********************************************************************
 *** DESCRIPTION : Entry point for testing the improved Dijkstra's
 ***               shortest path algorithm implementation in Java.
 ***               Builds a sample directed weighted graph, runs the
 ***               improved algorithm from a chosen source vertex,
 ***               and prints the resulting shortest path distances.
 ***               This version uses a priority queue plus a
 ***               frontier-reduction step inspired by the research
 ***               paper to shrink the active frontier.            ***
 ********************************************************************/

package dijkstra.improved;

import java.util.Arrays;

public class Main {

    /****************************************************************
     *** METHOD main                                               ***
     *****************************************************************
     *** DESCRIPTION : Driver method. Constructs a test graph with
     ***               5 vertices and several directed edges with
     ***               positive weights, then invokes the improved
     ***               Dijkstra algorithm starting from vertex 0 and
     ***               prints the shortest-path distance array.     ***
     *** INPUT ARGS : String[] args  - command-line arguments       ***
     *** OUTPUT ARGS: None                                          ***
     *** IN/OUT ARGS: None                                          ***
     *** RETURN     : void                                          ***
     ****************************************************************/
    public static void main(String[] args) {

        // Create a graph with 5 vertices (0 through 4)
        Graph graph = new Graph(5);

        // Same test graph as the classical version, for comparison
        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 2);
        graph.addEdge(2, 1, 4);
        graph.addEdge(2, 3, 8);
        graph.addEdge(2, 4, 2);
        graph.addEdge(3, 4, 7);
        graph.addEdge(4, 3, 9);

        // Create an instance of the improved Dijkstra algorithm
        ImprovedDijkstra dijkstra = new ImprovedDijkstra();

        // Compute shortest paths from source vertex 0
        int sourceVertex = 0;
        int[] distances = dijkstra.shortestPath(graph, sourceVertex);

        // Display results
        System.out.println("Improved Dijkstra - shortest distances from node " + sourceVertex + ":");
        System.out.println(Arrays.toString(distances));
    }
}
