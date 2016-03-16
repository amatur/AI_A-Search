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
	
        public Solver(Board initial) // find a solution to the initial
	{							 // board (using the A* algorithm)
		PriorityQueue<Board> PQ = new PriorityQueue<>(10, new MyComparator());
		
		//insert initial node into a priority queue PQ
		PQ.add(initial);
		//System.out.println("Root: "+initial);
		//PQ always pops the node with lowest f(s)
		while (!PQ.isEmpty())
		{
			nodesExpanded++;
			Board node = PQ.poll();
			//System.out.println("Pop: "+node);
			
			if(node.isGoal())
			{
				minMove = node.get_g();
				//System.out.println("Goal: "+node.toString());
				solution = new ArrayList<Board>();
				//populate solution arraylist with the board sequence from initial to goal
				return;
			}
			
			ArrayList<Board> neighbors = node.neighbors();
			
			for(int i=0;i<neighbors.size();i++)
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

			Board initial = new Board(colors,null);
			//System.out.println(initial.toString());

                        initial.neighbors();
                        
			//Solver solver = new Solver(initial);
	
                        //System.out.println("Nodes Expanded: "+ solver.nodesExpanded);
			//System.out.println("Minimum number of moves = " + solver.moves());
			//ArrayList<Board> solution = solver.solution();
			//for (int i=0;i<solution.size();i++)
			//	System.out.println(solution.get(i));
			//for bonus - show_result_in_gui(solution);
		}
	}
	
}

//create the open list of nodes, initially containing only our starting node
//create the closed list of nodes, initially empty
//
//while (we have not reached our goal) 
//{
//        consider the best node in the open list (the node with the lowest f value)
//
//        if (this node is the goal) 
//        {
//                then we're done
//        }
//        else 
//        {
//                move the current node to the closed list and consider all of its neighbors
//
//                for (each neighbor) 
//                {
//                        if (this neighbor is in the closed list and our current g value is lower) 
//                        {
//                                update the neighbor with the new, lower, g value 
//                                change the neighbor's parent to our current node
//                        }
//                        else if (this neighbor is in the open list and our current g value is lower) 
//                        {
//                                update the neighbor with the new, lower, g value 
//                                change the neighbor's parent to our current node
//                        }
//                        else this neighbor is not in either the open or closed list 
//                        {
//                                add the neighbor to the open list and set its g value
//                        }
//                }
//        }
//}