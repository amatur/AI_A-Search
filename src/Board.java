
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


public class Board {
    /***
     * construct a board from an N-by-N array of colors
     * @param colors[i][j] = color in row i, column j
     * @param parent remember to set parent as null for first board
     * @param g actual cost from initial board to this one
     */
    public Board(int[][] colors, Board parent, int g) { //
        this.board = colors;
        this.parent = parent;
        this.n = board.length;
        this.g = g;
        if (parent == null) {
            flooded = new boolean[n][n];
            initFlooding();
        } else {
            flooded = parent.getCopyFlooded();
        }
    }

    public String toString() {
        //String str = "\ng: " + g + " h:" + heuristic1() + " col left: " + calcColorsLeft() + "\n";
        String str = "\ng: " + g + " h:" + heuristic1() + "\n";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                str += (board[i][j] + " ");
            }
            str += "\n";
        }
        
        //added for debugging
        if(D.testFlooding){
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    str += (flooded[i][j] + "\t");
                }
                str += "\n";
            }
        }
        return str;
    }

    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
        System.out.println("-----------------");
    }

    public int get_g() {
        return g;
    }

    public Board getParent() {
        return parent;
    }

    public int f() {
        return g + heuristic12();
    }

    /***
     * Heuristic: Calculate remaining colors (Admissible)
     * @return returns the estimated distance from current board to final state using heuristic1
     */
    public int heuristic1()
    {
        return calcColorsLeft()-1;
    }

  
    /***
     * Heuristic: Edge Count (Admissible)
     * @return returns the estimated distance from current board to final state using heuristic2
     */
    public int heuristic2() 
    {
        Graph g = new Graph(getCopy(board));
        return g.heur();
    }
    
     /***
     * Heuristic: BFS (Admissible)
     * @return returns the estimated distance from current board to final state using heuristic3
     */
    public int heuristic3() 
    {
        Graph g = new Graph(getCopy(board));
        return g.bfs(0);
    }

    
    /***
     * Heuristic: Max of all 3 admissible
     * @return returns the estimated distance from current board to final state using heuristic123
     */
    public int heuristic123() 
    {
        Graph g = new Graph(getCopy(board));
        int bfsres = g.bfs(0);
        //System.out.println(bfsres + " vs " + (calcColorsLeft() - 1) + " vs " + g.heur());
        return Math.max(bfsres,Math.max((calcColorsLeft()-1),g.heur()) );
        //return bfsres;
        //return Math.max(bfsres ,(calcColorsLeft()-1));
        //return calcColorsLeft()-1;
    }
    
        /***
     * Heuristic: Max of 2 admissible
     * @return returns the estimated distance from current board to final state 
     */
    public int heuristic12() 
    {
        Graph g = new Graph(getCopy(board));
        return Math.max((calcColorsLeft()-1),g.heur() );
        //return bfsres;
        //return Math.max(bfsres ,(calcColorsLeft()-1));
        //return calcColorsLeft()-1;
    }
    
    public int heuristic13() 
    {
        Graph g = new Graph(getCopy(board));
        return Math.max(g.bfs(0) ,(calcColorsLeft()-1));
    }
    public int heuristic23() 
    {
        Graph g = new Graph(getCopy(board));
        return Math.max(g.bfs(0),g.heur() );
    }
    
    /***
     * Heuristic: Counting the number of connected components (Not Admissible or optimal, but fast)
     * @return returns the estimated distance from current board to final state using heuristi4
     */
    public int heuristic4() 
    {
//        BoardGraph bg = new BoardGraph(getCopy(board));
//        int prevConnC = bg.connectedComp();
//        return prevConnC - 1;
//        
        Graph g = new Graph(getCopy(board));
        return g.getNumConnComp() - 1;
    }
    

    /***
     * 
     * @return is this board the goal board? i.e., all color same. 
     */
    public boolean isGoal() // 
    {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                if (!flooded[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    public int calcColorsLeft() {
        int count = 0;
        try {
            for (int i = 0; i < 6; i++) {
                six[i] = 0;
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    //if(i==j){
                    six[board[i][j] - 1] = six[board[i][j] - 1] + 1;
                    //}
                }
            }
            for (int i = 0; i < 6; i++) {
                if (six[i] != 0) {
                    count++;
                }
            }
        } catch (Exception e) {
            return 6;
        }
        int colorsLeft = count;
        return colorsLeft;
    }

    public void floodNeighbors(int row, int col, int color) {
        if (row < n - 1) {
            testFlood(row + 1, col, color);
        }
        if (row > 0) {
            testFlood(row - 1, col, color);
        }
        if (col < n - 1) {
            testFlood(row, col + 1, color);
        }
        if (col > 0) {
            testFlood(row, col - 1, color);
        }
    }

    public void testFlood(int row, int col, int color) {
        if (flooded[row][col]) {
            return;
        }
        if (board[row][col] == color) {
            flooded[row][col] = true;
            /* Recurse to make sure that we get any connected neighbours. */
            floodNeighbors(row, col, color);
        }
    }

    public void initFlooding() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                flooded[i][j] = false;
            }
        }
        flooded[0][0] = true;
        startFlood(board[0][0]);
    }

    public void startFlood(int color) {
        //color those that are already flooded
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                if (flooded[row][col]) {
                    board[row][col] = color;
                }
            }
        }

        //find new candidates to flood (neighbours of already flooded)
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                if (flooded[row][col]) {
                    floodNeighbors(row, col, color);
                }
            }
        }

    }

    public ArrayList<Board> neighbors() // all neighboring boards
    {
        ArrayList<Board> ba = new ArrayList<Board>();
        calcColorsLeft(); //to update six color availability list => "six"

        BoardGraph bg = new BoardGraph(getCopy(board));
        int prevConnC = bg.connectedComp();

        for (int i = 0; i < 6; i++) {
            if (six[i] != 0) {
                Board childBoard = new Board(getCopy(board), this, this.g + 1); //neighbor's cost is 1 more

                if (childBoard.getBoard()[0][0] != i + 1) {
                    childBoard.startFlood(i + 1);
                }
                BoardGraph bgChild = new BoardGraph(getCopy(childBoard.board));
                int childConnC = bgChild.connectedComp();

                if (childConnC < prevConnC) {
                    ba.add(childBoard);
                }
                //if (D.p) {
//                    System.err.println(ba.get(i));
                //}
            }
        }
        return ba;
    }

    
    public int[][] getBoard() {
        return board;
    }
    
    
    public boolean equals(Object y) // does this board equal y?
    {
        Board other = (Board) y;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (other.getBoard()[i][j] != board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] getCopy(int board[][]) {
        int[][] nxtBoard = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                nxtBoard[i][j] = board[i][j];
            }
        }
        return nxtBoard;
    }

    public boolean[][] getCopyFlooded() {
        boolean[][] nxtBoard = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                nxtBoard[i][j] = flooded[i][j];
            }
        }
        return nxtBoard;
    }

    private int[][] board;
    private boolean[][] flooded;
    private int[] six = new int[6];
    private Board parent;
    private int g;
    private int n;
}
