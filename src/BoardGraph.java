/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tasnim
 */
public class BoardGraph {
    public BoardGraph(int[][] colors){
        board = colors;
        n = colors.length;
    }
    
    public void floodNeighborsCC(int row, int col, int color) {
        if (row < n - 1) {
            testFloodCC(row + 1, col, color);
        }
        if (row > 0) {
            testFloodCC(row - 1, col, color);
        }
        if (col < n - 1) {
            testFloodCC(row, col + 1, color);
        }
        if (col > 0) {
            testFloodCC(row, col - 1, color);
        }
    }

    public void testFloodCC(int row, int col, int color) {
        //if (flooded[row][col]) {
        //    return;
        //}
        if (board[row][col] == color ) {
            flooded[row][col] = true;
            board[row][col] = -1;
            /* Recurse to make sure that we get any connected neighbours. */
            floodNeighborsCC(row, col, color);
        }
    }
    
    public int connectedComp(){
        int count = 0;
        initFlooding();
        for(int i = 0; i<n ; i++){
            for(int j = 0; j<n; j++){
                if( board[i][j] != -1 ){
                    testFloodCC(i, j, board[i][j]);
                    count++;
                }
            }
        }        
        return count;
    }
        
    
    public void initFlooding(){
        flooded = new boolean[n][n];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                flooded[i][j] = false;
            }
        }
    }
   
    private boolean[][] flooded;
    private int[][] board;
    private int n;
}
