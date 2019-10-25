import java.util.*;
import javax.swing.*;

/**
 * Final Submission
 * 8 and 15 Puzzle Solver Using A-Star
 *
 * @authors EOGHAN.HODNETT 15174522, JONATHAN.RYLEY 17244501, ALANA.JANNIELLO 18288316, RYAN.DONOVAN 18288308 
 */

public class fs17244501 {
	public static List<GameState> closed; 
	public static List<GameState> open; 
	public static Set<String> firstGivenSet;

	public static void main(String[] args) {
				
		int[][] startState = null;  
	    int[][] endState = null; 
		firstGivenSet = null;

		String startStateTxt = "Please enter a starting state for 8 or 15 puzzle. ( eg: 1 2 3 4 5 6 7 8 0 )\nNote: \" 0 \" digit represents blank square and must be included.";
		String goalStateTxt = "Please enter the goal state for 8 or 15 puzzle. ( eg: 1 2 3 4 5 6 7 8 0 )\nNote: \" 0 \" digit represents blank square and must be included.";	
				
        // Ask User for start and end states.		
		startState = getinput(startStateTxt);				
		endState = getinput(goalStateTxt);

		GameState start = new GameState(startState, null); 	
		GameState end = new GameState(endState, null);
		
		closed = new ArrayList<GameState>();  
		open = new ArrayList<GameState>(); 
		
		// Compute A* 
		boolean found = false;
		GameState current = null;
		open.add(start);

		while (!open.isEmpty() && !found) {
			// Sort our open list based on depth + manhattan score. 
			Collections.sort(open);		
			current = open.get(0);
			open.remove(0);
			
			if (current.equals(end)) {
				found = true;
				String output = "\nFinal state:\n" + current;
				
				// System.out.println(current); // Output needs to be changed. 
				GameState temp = current.parent;
				while (temp != null) {
					// System.out.println(temp);  // Output needs to be changed.
					output = temp + "\n" + output;					
					temp = temp.parent;
				}
				
				output = "\nStarting state:" + output;				
				System.out.println(output);				
				System.out.println("\nOpen Set Size: "+ open.size());
				System.out.println("Closed Set Size: "+ closed.size());
			}
			else {
				// Add the current state to closed list and its children to the open list (if they aren't already in closed/open lists).
				ArrayList<GameState> templist = new ArrayList<GameState>();
				templist = current.getAdjacent();
				closed.add(current);
				
				for (int i = 0; i < templist.size(); i++)  {
					if (!closed.contains(templist.get(i)) && !open.contains(templist.get(i))) {						
						templist.get(i).score = ManhattenInt(templist.get(i), end);
						open.add(templist.get(i));
					}
				}
			}
		}
		
	}
	
	public static int ManhattenInt(GameState startState, GameState endState) { 
		//Take in 2 arrays of start/end positions return the cost of getting there
		int[][] start = startState.state; 
		int[][] end = endState.state; 
	  
	  int steps []=new int[start.length*start.length]; 
	  //how many steps each incorrect position needs to move, length = number of items to move
	  
	  int total=0;
	  int count=0;
	  //calculating number of steps away each position is
	  for(int i=0;i<start.length;i++) {
		  
		  for(int j=0;j<end.length;j++) {
			  
				if(start[i][j]==(end[i][j])){
					//if the say 0,0 matches 0,0 fine
					count++;
				}
				else {
					// if it doesn't match loop through end matrix to find match 	 
					for(int k=0;k<end.length;k++) { 
						
						for(int l=0;l<end.length;l++) {
							
							if(start[i][j]==(end[k][l]))
							{
							steps[count]=Math.abs((i - k)+(j - l));
							total+=steps[count];
							count++;							
							}
						}
					}
				}
			}
		}

	  //g(n)+h(n)
	  return total;
	 }
	
	public static int[][] getinput(String windowMsg)  {
		Set<String> givenSet;
		String[] inputArr;
		int[][] output = null;		 
		String input;
		String error1 = "The input must be in the form of nine or sixteen numbers seperated by spaces.";
		String error2 = "All numbers must be unique";
		String error3 = "The start set and goal set are not compatable. \n(there are elements in one set do not occur in the other set)";
		boolean finished = false;		
		
		while(!finished)  {
			input = JOptionPane.showInputDialog(null, windowMsg);
			
			if(input == null)
				System.exit(0); // User pressed close button || cancel button || empty string.			
			
			input = input.trim();
			if(isValid(input))  {
				inputArr = input.split("\\s+");
				givenSet = new HashSet<String>(Arrays.asList(inputArr));
					
				int numOfeles = inputArr.length;
				
				if(numOfeles == givenSet.size()) {					
					boolean hasSameValues = true;
					
					if(firstGivenSet == null) {
						firstGivenSet = new HashSet<String>(Arrays.asList(inputArr));
					}
					else {
						if(firstGivenSet.size() == givenSet.size()) {
							// check for duplicate values.
							givenSet.addAll(firstGivenSet);
							if(givenSet.size() != numOfeles)
							{
								hasSameValues = false;
								System.out.print(error3);
							}
						}
						else {
							hasSameValues = false;
							System.out.print(error3);
						}
					}
					
					if(hasSameValues) { 
						int size = (int)Math.sqrt(givenSet.size());
						output = new int[size][size]; 
						int index = 0;
					
						for(int i=0;i<output.length;i++)  {
							for(int j=0;j<output[0].length;j++)  {
								output[i][j] = Integer.parseInt(inputArr[index]);
								index++;
							}
						}
						finished = true;
					}
				}
				else {
					System.out.println(error2);
				}
			}
			else  {
				System.out.println(error1);
			}
		}
		return output;
	}
	
	public static boolean isValid(String x) {
		String pattern1 = "\\d+(\\s+\\d+){8,8}"; // 8 digits + first digit.
		String pattern2 = "\\d+(\\s+\\d+){15,15}"; // 15 digits + first digit.
		
		if(x.matches(pattern1) || x.matches(pattern2)) {
			return true;
		}
		return false;
	}
}


/**
* Note: this class has a natural ordering that is inconsistent with equals. 
*/
class GameState implements Comparable<GameState> {
	
	public int[][] state; 
	public GameState parent; 
	public int score;
	public int depth;
	
	public GameState() {
		this(new int[3][3]);
	}

	public GameState(int[][] state) {
		// initialize this.state to state, parent to null
		this( state, null);
	}

	public GameState(int[][] state, GameState parent) {
		// initialize this.state to state, this.parent to parent
		this.state = new int[state.length][state[0].length];
		
		for (int i = 0; i < state.length; i++)
			for (int c = 0; c < state[0].length; c++)
				this.state[i][c] = state[i][c];
		this.parent = parent;		
		
		score = 0;
		if(parent != null) {
			depth = parent.depth + 1;
		}
		else {
			depth = 0;
		}
	}

	public GameState swapRight(GameState s, int row, int col) {
		// helper function to swap blank space with right block
		GameState newState = new GameState(state, s);
		int rswap1 = newState.state[row][col];
		int rswap2 = newState.state[row][col + 1];
		newState.state[row][col + 1] = rswap1;
		newState.state[row][col] = rswap2;

		return newState;
	}

	public GameState swapLeft(GameState s, int row, int col) {
		// helper function to swap blank space with left block
		GameState newState = new GameState(state, s);
		int lswap1 = newState.state[row][col];
		int lswap2 = newState.state[row][col - 1];
		newState.state[row][col - 1] = lswap1;
		newState.state[row][col] = lswap2;
		
		return newState;
		
	}

	public GameState swapUp(GameState s, int row, int col) {
		// helper function to swap blank space with up block
		GameState newState = new GameState(state, s);
		int upswap1 = newState.state[row][col];
		int upswap2 = newState.state[row - 1][col];
		newState.state[row - 1][col] = upswap1;
		newState.state[row][col] = upswap2;
		
		return newState;

	}

	public GameState swapDown(GameState s, int row, int col) {
		// helper function to swap blank space with down block
		GameState newState = new GameState(state, s);
		int downswap1 = newState.state[row][col];
		int downswap2 = newState.state[row + 1][col];
		newState.state[row + 1][col] = downswap1;
		newState.state[row][col] = downswap2;
		
		return newState;
	}

	public ArrayList<GameState> getAdjacent() {
		// Use the swap functions to generate the new vertices of the tree
		// Beware of the boundary conditions, i.e. don’t swap left when you are
		// already on the left edge
		int rowloc = 0;
		int colloc = 0;
		for (int row = 0; row < state.length; row++) {			
			for (int col = 0; col < state[0].length; col++) {

				if (state[row][col] == 0) {
					rowloc = row;
					colloc = col;
				}
			}
		}

		ArrayList<GameState> array = new ArrayList<GameState>();
	
		if(rowloc > 0) {
			array.add(swapUp(this, rowloc, colloc));
		}
		if(rowloc < (state.length -1)) {
			array.add(swapDown(this, rowloc, colloc));
		}
		if(colloc > 0) {
			array.add(swapLeft(this, rowloc, colloc));
		}
		if(colloc < (state[0].length -1)) {
			array.add(swapRight(this, rowloc, colloc));
		}
			
		return array;
	}
	
	@Override
	public int compareTo(GameState x) { 
		
		if(this.score == x.score) {
			// Should I catch numbers > or < than 1 or -1?
			return this.depth - x.depth;
		}
		else {
			return (this.depth+this.score) - (x.depth + x.score);
		}
	}

	@Override
	public boolean equals(Object o) {
		// test that 2 GameStates are equal
		if (o == this)
			return true;
		if (!(o instanceof GameState))
			return false;
		GameState s = (GameState) o;
		for (int i = 0; i < state.length; i++) {
			for (int c = 0; c < state[0].length; c++) {
				if (state[i][c] != s.state[i][c])
					return false;
			}
		}
		return true;
	}
	
	@Override
	public String toString() {
		// print out the int[][] array in a block 
		String string = ""; 
		for (int row = 0; row < state.length; row++) {
			string += "\n\n";
			for (int col = 0; col < state[0].length; col++)
				string += Integer.toString(state[row][col]) + "\t";
		}
		return string;
	}
}