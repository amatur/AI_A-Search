import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/*
Main function takes, the input file, constructs a  Solver object. 
In the constructor solve the initial board with A* search.
Save the number of minimum moves and  sequence of solution in member variables.
The functions solution() and moves() return these member functions.
 */

/**/
public class Solver 
{
	ArrayList<Board> solution; //stores the sequence of boards upto solution
	int minMove; //the number of moves	
	
	class MyComparator implements Comparator<Board>
	{
		@Override
		public int compare(Board o1, Board o2) {
			// TODO Auto-generated method stub
			return o1.f()-o2.f();
		}	
	}
        
	public int nodesExpanded=0;
        
        public void printSolution(Board p, int step){
            if(p.getParent()==null){
                //solution.add(p);
                //System.out.println("Step "+ step + ": "  + p.toString());     
                return;
            }
            printSolution(p.getParent(), step-1);
            solution.add(p.getParent());
            //System.out.println("Step "+ step + ": "  + p.toString());            
        }
	
        public Solver(Board initial) // find a solution to the initial
	{							 // board (using the A* algorithm)
		
                //create the open list of nodes (PQ), initially containing only our starting node
                //create the closed list of nodes, initially empty
                PriorityQueue<Board> PQ = new PriorityQueue<>(10, new MyComparator());
		
		//insert initial node into a priority queue PQ
		PQ.add(initial);
		//System.out.println("Root: "+initial);
		//PQ always pops the node with lowest f(s)
		while (!PQ.isEmpty())
		{
			nodesExpanded++;
                        // consider the best node in the open list (the node with the lowest f value)
			Board node = PQ.poll();
			//System.err.println("Pop: "+node);
			
			if(node.isGoal())
			{
				minMove = node.get_g();
				solution = new ArrayList<Board>();
                                //System.out.println("Goal: "+node.toString());
			
                                //populate solution arraylist with the board sequence from initial to goal
                                //task
                                printSolution(node, minMove);
				solution.add(node);
                                return;
			}
                        
                        //if it is not goal, move the current node to the closed list and consider all of its neighbors
			ArrayList<Board> neighbors = node.neighbors();			
			for(int i=0; i<neighbors.size(); i++)
			{
				PQ.add(neighbors.get(i));
				//System.out.println(neighbors.get(i));
			}

		}

	}

	// Returns the minimum number of moves to solve
	public int moves() 			 
	{
		return minMove;
	}
	// sequence of boards in the
	// shortest solution
	public ArrayList<Board> solution() 
	{		
		return solution;
	}
	public static void main(String args[]) 
	{
		Scanner in=null;
		try 
		{
			in = new Scanner(new File("input.txt"));
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(true)
		{

			int N = in.nextInt();
			if(N==0) return;

			int[][] colors = new int[N][N];
			for (int i = 0; i < N; i++)
				for (int j = 0; j < N; j++)
					colors[i][j] = in.nextInt();

			Board initial = new Board(colors,null,0);
			
                       Solver solver = new Solver(initial);
	
                        System.out.println("Nodes Expanded: "+ solver.nodesExpanded);
			System.out.println("Number of moves: " + solver.moves());
                        
			ArrayList<Board> solution = solver.solution();
                        
                        for(int i=1; i<solution.size(); i++){
                            System.out.println(solution.get(i).getBoard()[0][0]);
                        }
			for (int i=0;i<solution.size();i++)
				System.out.println(solution.get(i));
			//for bonus - show_result_in_gui(solution);
		}
	}
	
}
