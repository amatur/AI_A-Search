
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import javax.management.Query;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tasnim
 */
public class Graph {
    private final int V;
    private HashSet<Integer>[] adj;
    private boolean[] marked;
    private int[]  distFromSource;
     private int[]  color;
    private final int source;
     private int[][] board;
     private int[][] whichVertex;
     private int n;
    
    public Graph(int[][] colors)
    {
        board = colors;
        n = board.length;
        whichVertex = new int[n][n];
        //counting V
        int V = 0;
        for(int i = 0; i<n ; i++){
            for(int j = 0; j<n; j++){
                if( board[i][j] > 0 ){ //valid color that is not checked yet
                    V++;
                    testFloodCC(i, j, board[i][j], V-1);
                    whichVertex[i][j] = V-1;
                }
            }
        }   
         this.V = V;
        adj = (HashSet<Integer>[]) new HashSet[V];
        for(int v=0; v<V; v++){
            adj[v] = new HashSet<Integer>();
        }
        
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                //check left and up neighbour
                if (i > 0) {
                    if(whichVertex[i][j] != whichVertex[i-1][j]){
                        addEdge(whichVertex[i][j], whichVertex[i-1][j]);
                    }
                }
                if (j > 0) {
                    if(whichVertex[i][j] != whichVertex[i][j-1]){
                        addEdge(whichVertex[i][j], whichVertex[i][j-1]);
                    }
                }
            }
        }
        System.out.println("WHICH V");
        printBoard(whichVertex);
        marked = new boolean[V];
        distFromSource = new int[V];
        color = new int[V];
        source = 0;
    }
    
    
    public int countEdges(){
        int E=0;
        for(int i=0 ; i<V; i++){
            E+= adj[i].size();
        }
        printBoard(board);
        System.err.println("Edges: "+ E);
        return E;
    }
    
    public void addEdge(int u, int v){
        adj[u].add(v);
        adj[v].add(u);
    }
    
    //iterator for vertices adjacent to v
    public Iterable<Integer> adj(int v){
        return adj[v];
    }
    
    public int bfs(int source){
        distFromSource[source] = 0;
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(source);
        
        for(int i=0; i<V; i++){
            marked[i] = false;
            distFromSource[i] = 0;
        }
        
        marked[source] = true;
        while(!q.isEmpty()){
            int v = q.poll();
            for(int w: adj(v)){
                if(!marked[w]){
                    q.add(w);
                    marked[w] = true;
                    distFromSource[w] = distFromSource[v] + 1;
                }
            }    
        }
       
        int max = 0;
        for(int i: distFromSource){
            if(i > max){
                max = i;
            }
        }
        
        return max;
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

    public void testFloodCC(int row, int col, int color, int vertex) {
        //if (flooded[row][col]) {
        //    return;
        //}
        if (board[row][col] == color ) {
            board[row][col] = -board[row][col];
            whichVertex[row][col]= vertex;
            /* Recurse to make sure that we get any connected neighbours. */
            floodNeighborsCC(row, col, color, vertex);
        }
    }
    
     public void printBoard(int[][] board){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
        System.out.println("-----------------");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(this.board[i][j]);
            }
            System.out.println();
        }
        System.out.println("-----------------");
    }
    
}
