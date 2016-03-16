import java.util.ArrayList;

public class Board 
{
         // construct a board from an N-by-N
								 //array of colors	
								 // (where colors[i][j] = color in
								 //row i, column j)
	public Board(int[][] colors, Board parent)
        {
            //=null
        }
	public int heuristic1() // returns the estimated distance from current board to final state using heuristic1
        {
            return 0;
        }
                
        public int heuristic2()  // returns the estimated distance from current board to final state using heuristic2
        {
            return 0;
        }
                
        public boolean isGoal() // is this board the goal board? i.e., all color same. 
        {
            return false;
        }
        
	public ArrayList<Board> neighbors() // all neighboring boards
        {
            return null;
        }
                
        public boolean equals(Object y) // does this board equal y?
        {
            return false;
        }
                
	public String toString() // string representation of the board (in the output format specified below)
        {
            return "";
        }
	public static void main(String[] args){ // for testing purpose
                
        }
}

