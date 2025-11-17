/********************************************************************
 *** NAME       : Rikesh Budhathoki                                ***
 *** CLASS      : CSC 492                                          ***
 *** ASSIGNMENT : Dijkstra's Algorithm (Improved Version)          ***
 *** DUE DATE   : TBD                                              ***
 *** INSTRUCTOR : Jun Huang                                        ***
 *********************************************************************
 *** DESCRIPTION : Defines a simple directed weighted graph
 ***               Abstract Data Type (ADT) using an adjacency
 ***               list representation. Provides methods for
 ***               creating a graph and adding weighted edges.
 ***               This ADT is used as input to the improved
 ***               Dijkstra shortest-path algorithm.               ***
 ********************************************************************/

package dijkstra.improved;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    /****************************************************************
     *** NESTED CLASS Node                                         ***
     *****************************************************************
     *** DESCRIPTION : Represents a single outgoing edge from a
     ***               vertex in the adjacency list. Stores the
     ***               destination vertex index and the weight of
     ***               the edge.                                    ***
     ****************************************************************/
    public static class Node {
        public int vertex;
        public int weight;

        public Node(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }
    }

    private int vertices;                 // number of vertices in the graph
    private List<List<Node>> adjList;     // adjacency list: for each vertex, list of outgoing edges

    /****************************************************************
     *** CONSTRUCTOR Graph                                         ***
     *****************************************************************
     *** DESCRIPTION : Initializes a directed graph with the given
     ***               number of vertices and empty adjacency lists
     ***               for each vertex.                             ***
     *** INPUT ARGS : int vertices - number of vertices (0..V-1)    ***
     *** OUTPUT ARGS: None                                          ***
     *** IN/OUT ARGS: None                                          ***
     *** RETURN     : N/A (constructor)                             ***
     ****************************************************************/
    public Graph(int vertices) {
        this.vertices = vertices;
        this.adjList = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    /****************************************************************
     *** METHOD addEdge                                            ***
     *****************************************************************
     *** DESCRIPTION : Adds a directed edge from vertex u to vertex
     ***               v with the specified non-negative weight.
     *** INPUT ARGS : int u      - source vertex index
     ***              int v      - destination vertex index
     ***              int weight - edge weight (must be >= 0)       ***
     *** OUTPUT ARGS: None                                          ***
     *** IN/OUT ARGS: None                                          ***
     *** RETURN     : void                                          ***
     ****************************************************************/
    public void addEdge(int u, int v, int weight) {
        adjList.get(u).add(new Node(v, weight));
    }

    /****************************************************************
     *** METHOD getVertices                                        ***
     *****************************************************************
     *** DESCRIPTION : Returns the number of vertices in the graph. ***
     *** INPUT ARGS : None                                          ***
     *** OUTPUT ARGS: None                                          ***
     *** IN/OUT ARGS: None                                          ***
     *** RETURN     : int - number of vertices                      ***
     ****************************************************************/
    public int getVertices() {
        return vertices;
    }

    /****************************************************************
     *** METHOD getAdjList                                         ***
     *****************************************************************
     *** DESCRIPTION : Returns the adjacency list representing all
     ***               outgoing edges for each vertex in the graph. ***
     *** INPUT ARGS : None                                          ***
     *** OUTPUT ARGS: None                                          ***
     *** IN/OUT ARGS: None                                          ***
     *** RETURN     : List<List<Node>> - adjacency list             ***
     ****************************************************************/
    public List<List<Node>> getAdjList() {
        return adjList;
    }
}
