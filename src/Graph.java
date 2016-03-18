import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Graph {
    public Graph(int[][] colors) {
        board = colors;
        n = board.length;
        whichVertex = new int[n][n];
        
        //we start counting V
        int V = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] > 0) { //valid color that is not checked yet
                    V++;    //found a new component
                    testFloodCC(i, j, board[i][j], V - 1);
                    whichVertex[i][j] = V - 1;
                }
            }
        }
        //finished counting, got the V
        this.V = V;
        
        adj = (HashSet<Integer>[]) new HashSet[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new HashSet<Integer>();
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                //check left and up neighbour
                if (i > 0) {
                    if (whichVertex[i][j] != whichVertex[i - 1][j]) {
                        addEdge(whichVertex[i][j], whichVertex[i - 1][j]);
                    }
                }
                if (j > 0) {
                    if (whichVertex[i][j] != whichVertex[i][j - 1]) {
                        addEdge(whichVertex[i][j], whichVertex[i][j - 1]);
                    }
                }
            }
        }
        
        //added for debugging
        if(D.testGraphIsCorrect){
            System.out.println("--Showing Graph Construction--");
            printBoard();
        }

        //for BFS
        marked = new boolean[V];
        distFromSource = new int[V];
        //color = new int[V];
    }

    public void testFloodCC(int row, int col, int color, int vertex) {
        if (board[row][col] == color) {
            board[row][col] = -board[row][col];
            whichVertex[row][col] = vertex;
            floodNeighborsCC(row, col, color, vertex);  //now recursively flooding the neighbors
        }
    }
    public void floodNeighborsCC(int row, int col, int color, int vertex) {
        if (row < n - 1) {
            testFloodCC(row + 1, col, color, vertex);
        }
        if (row > 0) {
            testFloodCC(row - 1, col, color, vertex);
        }
        if (col < n - 1) {
            testFloodCC(row, col + 1, color, vertex);
        }
        if (col > 0) {
            testFloodCC(row, col - 1, color, vertex);
        }
    }
    
    public int countEdges() {
        int E = 0;
        
        //added for debugging
        if (D.testGraphAdjList) {
            //print adj list
            System.out.println("Adj List: ");
            for (int i = 0; i < V; i++) {
                System.out.print(i + ": ");
                for (int w : adj(i)) {
                    System.out.print(w + " ");
                }
                System.out.println();
            }
        }
        
        for (int i = 0; i < V; i++) {
            for (int w : adj(i)) {
                E++;
            }
        }
        E = E / 2;
        //System.out.println("Edges: " + E);
        return E;
    }

    /***
     * Warning: Calling this function destroys the graph structure 
     * @return estimate for the number of steps to merge the whole graph into 1 component only, i.e 1 vertex, 0 edge
     * makes the relaxation that all the neighboring vertices are of same color
     */
    public int heur() {
        int count = 0;
        ///*
        while (countEdges() > 0) {
            HashSet<Integer> s = new HashSet<>();
            LinkedList<Integer> zero = new LinkedList<>();
            for (int w : adj(0)) {
                zero.add(w);
            }
            for (int u : zero) {
                for (int v : adj(u)) {
                    if (v != 0) {
                        s.add(v);
                    }
                }
                adj[u].clear();
            }
            adj[0].clear();
            for (int w : s) {
                addEdge(0, w);
            }
            //end merge
            //*/
            count++;
        }
        return count;
    }
    /***
     * Undirected edge, no duplicate edge will be added
     * @param u
     * @param v 
     */
    public void addEdge(int u, int v) {
        adj[u].add(v);
        adj[v].add(u);
    }

    /***
     * iterator for vertices adjacent to v
     * @param v
     * @return 
     */
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public int bfs(int source) {
        distFromSource[source] = 0;
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(source);
        for (int i = 0; i < V; i++) {
            marked[i] = false;
            distFromSource[i] = 0;
        }
        marked[source] = true;
        while (!q.isEmpty()) {
            int v = q.poll();
            for (int w : adj(v)) {
                if (!marked[w]) {
                    q.add(w);
                    marked[w] = true;
                    distFromSource[w] = distFromSource[v] + 1;
                }
            }
        }
        int max = 0;
        for (int i : distFromSource) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }
    
    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(whichVertex[i][j]);
            }
            System.out.println();
        }
        System.out.println("-----------------");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(this.board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("-----------------");
    }
    
    public int getNumConnComp(){
        return V;
    }
    
    /**
     * Number of vertices in the graph representation of flood-it board
     */
    private final int V;
    /**
     * Adjacency list of graph
     */
    private HashSet<Integer>[] adj; 
    private boolean[] marked;
    private int[] distFromSource;
    //private int[] color;
    private int[][] board;
    /**
     * this 2d array stores corresponding vertex number the position maps to
     */
    private int[][] whichVertex;  
    private int n;  //board size n*n

}
