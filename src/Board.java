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
        if(parent==null){
            flooded = new boolean[n][n];
            initFlooding();
        }else{
            flooded = parent.getCopyFlooded();
        }
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
		String str="\ng: "+g+" h:"+heuristic1()+ " col left: "+ calcColorsLeft()+"\n";
		for (int i=0; i<board.length;i++)
		{
			for(int j=0;j<board[i].length;j++)
			{
				str += (board[i][j]+" ");
			}
			str +="\n";
		}
                for (int i=0; i<board.length;i++)
		{
			for(int j=0;j<board[i].length;j++)
			{
				str += (flooded[i][j]+" ");
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
       // return 0;
         return calcColorsLeft() - 1;
    }

    public int heuristic2() // returns the estimated distance from current board to final state using heuristic2
    {
        return 0;
//        int count = 0;
//        try{
//        int[] temp = new int[6];
//        for(int i=0; i<6;i++)
//            temp[i]=0;
//        for(int i=0; i<n; i++){
//            for(int j=0; j<n; j++){
//                if(i==j){
//                    temp[board[i][j]-1]++;
//                }
//            }
//        }
//        for(int i=0; i<6;i++)
//            count+=temp[i];
//        }catch(Exception e){
//            return 0;
//        }
//        return Math.min(calcColorsLeft()-1, count);
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
        try{
//        int[] temp = new int[6];
        for (int i=0; i<6; i++)
            six[i] = 0;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                //if(i==j){
                    six[board[i][j]-1] = six[board[i][j]-1]  + 1;
                //}
            }
        }
        for (int i=0; i<6; i++){
            if(six[i]!=0)
                count++;
        }
        }catch(Exception e){
            return 6;
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
        flooded[0][0]=true;
        startFlood(board[0][0]);
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
        int prevColLeft = calcColorsLeft(); //to update six color availability list
        //BoardGraph bg = new BoardGraph(getCopy(board));
        //int prevConnC =  bg.connectedComp();
        
        for (int i = 0; i < 6; i++) {
            if (six[i] != 0) {
                Board childBoard = new Board(getCopy(board), this, this.g + 1); //neighbor's cost is 1 more
                
                if (childBoard.getBoard()[0][0] != i + 1) {
                    childBoard.startFlood(i + 1);
                }
                ba.add(childBoard);
                //if (D.p) {
//                    System.err.println(ba.get(i));
                //}
            }
          //  int[][] boardGraphColors = new int[n][n];
            // boardGraphColors = ba.get(i-1).getBoard();
            // BoardGraph bg = new BoardGraph(boardGraphColors);
            // int x = bg.connectedComp();
            //if(D.p) System.out.println(x + "is the # of conn comp of board# "+ i);
        }
        return ba;
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
    public boolean[][] getCopyFlooded() {
        boolean[][] nxtBoard = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                nxtBoard[i][j] = flooded[i][j];
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
    private int[] six = new int[6];
    private int colorsLeft;
}
