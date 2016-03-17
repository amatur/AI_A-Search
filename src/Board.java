import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    // construct a board from an N-by-N
    //array of colors	
    // (where colors[i][j] = color in
    //row i, column j)
    public Board(int[][] colors, Board parent, int g) {
        //parent null for first board
        this.board = colors;
        this.parent = parent;
        this.n = board.length;
        this.g = g;
        //printBoard();
        flooded = new boolean[n][n];
        initFlooding();
//        board = new int[n][n];
//        if(D.p) System.out.println(n + " col");
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                board[i][j] = colors[i][j];
//            }
//        }
    }
    public String toString() 
	{
		String str="\ng: "+g+" h:"+heuristic1()+"\n";
		for (int i=0; i<board.length;i++)
		{
			for(int j=0;j<board[i].length;j++)
			{
				str += (board[i][j]+" ");
			}
			str +="\n";
		}
		return str;
//		System.out.println();

    }
    
    public void printBoard(){
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

    public int f() 
    {
            return g+heuristic1();
    }
    public int heuristic1() // returns the estimated distance from current board to final state using heuristic1
    {
        for(int i=0; i<6; i++){
            six[i] = 0;
        }
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(i==j){
                    six[board[i][j]]++;
                }
            }
        }
        return 0;
    }

    public int heuristic2() // returns the estimated distance from current board to final state using heuristic2
    {
        return calcColorsLeft() - 1;
    }

    public boolean isGoal() // is this board the goal board? i.e., all color same. 
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

    public int calcColorsLeft(){
        int count = 0;
        int[] temp = new int[6];
        for (int i=0; i<6; i++)
            temp[i] = 0;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(i==j){
                    temp[board[i][j]-1]++;
                }
            }
        }
        for (int i=0; i<6; i++){
            if(temp[i]!=0)
                count++;
        }
        colorsLeft  = count;
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
    
    
    public void initFlooding(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                flooded[i][j] = false;
            }
        }
        flooded[0][0] = true;
    }
    
    public void startFlood(int color){
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
        for(int i=1; i<=6; i++){
            Board childBoard = new Board(getCopy(board), this, this.g+1); //neighbor's cost is 1 more
            if(childBoard.getBoard()[0][0]!=i){
                childBoard.startFlood(i);
            }
            ba.add(childBoard);
            ba.get(i-1).printBoard();
            BoardGraph bg = new BoardGraph(ba.get(i-1).getBoard());
            int x = bg.connectedComp();
            System.out.println(x + "is the # of conn comp");
        }
        return null;
    }


    public boolean equals(Object y) // does this board equal y?
    {
        Board other = (Board) y;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(other.getBoard()[i][j] != board[i][j]){
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
    public int[][] getBoard() {
        return board;
    }
    /*
    public static void main(String[] args) { // for testing purpose
        try {
            File file = new File("input.txt");
            Scanner sc = new Scanner(file);
            while (true) {
                int n = sc.nextInt();
                if(D.p) System.err.println(n);
                if (n == 0) {
                    break;
                }
                int[][] colors = new int[n][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        colors[i][j] = sc.nextInt();
                        if(D.p) System.err.print(colors[i][j]);
                    }
                    if(D.p) System.err.println();
                }
                Board mainboard = new Board(colors, null);
                mainboard.printBoard();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
    private int[][] board;
    private boolean[][] flooded;
    private Board parent;
    private int g;
    private int n;
    private int moves; //moves from initial state??
    private int[] six = new int[6];
    private int colorsLeft = 6;
}
