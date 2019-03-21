
import java.util.*;
import javax.swing.*;

public class Play {
	public static List<GameState> explored; // explored stores our marked vertices
	public static Deque<GameState> frontier; // frontier is our queue for BFS

	public static void main(String[] args) {
				
		int[][] tArr = null;  // start state, i.e. tree root		
	    int[][] state = null; // end state
		String startState = "Please enter a starting state for 8 puzzle. ( eg: 1 2 3 4 5 6 7 8 0 )";
		String goalState = "Please enter the goal state for 8 puzzle. ( eg: 1 2 3 4 5 6 7 8 0 )";
		
        // Ask User for start and end states
		while(true)  {			
			tArr = getinput(startState);
			if(tArr == null)
				System.exit(0); // User pressed close button || cancel button || empty string.
			else
				break; // Data was input correctly.
		}		
		while(true)  {			
			state = getinput(goalState);
			if(state == null)
				System.exit(0); 
			else
				break; 
		}
        	
		GameState start = new GameState(tArr, null); // state with tArr state, null parent		
		GameState end = new GameState(state, null);
		explored = new ArrayList<GameState>();  
		frontier = new ArrayDeque<GameState>(); 
		
		// Perform a Breadth First Search here
		
		boolean found = false;
		GameState current = null;
		explored.add(start);
		frontier.add(start);

		while (!frontier.isEmpty() && !found) {
			current = frontier.removeFirst();

			if (current.isEnd(end)) {
				found = true;
				System.out.println(current);
				GameState temp = current.parent;
				while (temp != null) {
					System.out.println(temp);
					temp = temp.parent;
				}
			}
			else {
				ArrayList<GameState> templist = new ArrayList<GameState>();
				templist = current.getAdjacent();
				for (int i = 0; i < templist.size(); i++)  {

					if (!explored.contains(templist.get(i)) && !frontier.contains(templist.get(i))) {
						explored.add(templist.get(i));
						frontier.add(templist.get(i));
					}
				}
			}
		}
	}
	// Note: We will not create the graph or enumerate the edges
	// The graph is too large to store in memory
	// We will create the vertices of the graph and edge connections on the fly
	
	public static int[][] getinput(String windowMsg)  {
		Set<String> givenSet;
		String[] inputArr;
		int[][] output = null;		 
		String input;
		String error1 = "The input must be in the form of nine digits seperated by spaces.";
		String error2 = "All numbers must be unique";
		boolean finished = false;		
		
		while(!finished)  {
			input = JOptionPane.showInputDialog(null, windowMsg);
			
			if(input == null)
				System.exit(0); // User pressed close button || cancel button || empty string.			
			
			input = input.trim();
			if(isValid(input))  {
				inputArr = input.split(" ");
				givenSet = new HashSet<String>(Arrays.asList(inputArr));
				
				if(givenSet.size() != 9)  {
					System.out.println(error2);
				}
				else  {
					output = new int[3][3];
					int index = 0;
				
					for(int i=0;i<3;i++)  {
						for(int j=0;j<3;j++)  {
							output[i][j] = Integer.parseInt(inputArr[index]);
							index++;
						}
					}
					finished = true;
				}
			}
			else  {
				System.out.println(error1);
			}
		}
		return output;
	}
	
	public static boolean isValid(String x) {
		String pattern = "\\d\\s\\d\\s\\d\\s\\d\\s\\d\\s\\d\\s\\d\\s\\d\\s\\d"; // 9 digits, each seperated by a space
		return x.matches(pattern);
	}
}

