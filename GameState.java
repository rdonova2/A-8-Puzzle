import java.util.*;

//Note: this class has a natural ordering that is inconsistent with equals. 
public class GameState implements Comparable<GameState>{
	
	public int[][] state; 
	public GameState parent; 
	public int score;
	public int depth;
	
	public GameState() {
		// initialize state to zeros, parent to null
		this(new int[3][3]);
	}

	public GameState(int[][] state) {
		// initialize this.state to state, parent to null
		this.state = new int[3][3];
		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 3; col++)
				state[row][col] = 0;
		parent = null;
		
		score = 0;
		depth = 0;
	}

	public GameState(int[][] state, GameState parent) {
		// initialize this.state to state, this.parent to parent
		this.state = new int[3][3];
		for (int i = 0; i < 3; i++)
			for (int c = 0; c < 3; c++)
				this.state[i][c] = state[i][c];
		this.parent = parent;
		
		score = 0;
		depth = parent.depth + 1;
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

	public boolean isEnd(GameState end) {
		// helper function to check if the GameState is the end state 
		if (this.equals(end))
			return true;
		else
			return false;
	}

	public ArrayList<GameState> getAdjacent() {
		// Use the swap functions to generate the new vertices of the tree
		// Beware of the boundary conditions, i.e. don’t swap left when you are
		// already on the left edge
		int rowloc = 0;
		int colloc = 0;
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {

				if (state[row][col] == 0) {
					rowloc = row;
					colloc = col;
				}
			}
		}

		ArrayList<GameState> array = new ArrayList<GameState>();

		if (rowloc == 0 && colloc == 0) {
			array.add(swapRight(this, rowloc, colloc));
			array.add(swapDown(this, rowloc, colloc));
		} else if (rowloc == 0 && colloc == 1) {
			array.add(swapRight(this, rowloc, colloc));
			array.add(swapLeft(this, rowloc, colloc));
			array.add(swapDown(this, rowloc, colloc));
		} else if (rowloc == 0 && colloc == 2) {
			array.add(swapLeft(this, rowloc, colloc));
			array.add(swapDown(this, rowloc, colloc));
		} else if (rowloc == 1 && colloc == 0) {
			array.add(swapRight(this, rowloc, colloc));
			array.add(swapUp(this, rowloc, colloc));
			array.add(swapDown(this, rowloc, colloc));
		} else if (rowloc == 1 && colloc == 1) {
			array.add(swapRight(this, rowloc, colloc));
			array.add(swapLeft(this, rowloc, colloc));
			array.add(swapUp(this, rowloc, colloc));
			array.add(swapDown(this, rowloc, colloc));
		} else if (rowloc == 1 && colloc == 2) {
			array.add(swapLeft(this, rowloc, colloc));
			array.add(swapUp(this, rowloc, colloc));
			array.add(swapDown(this, rowloc, colloc));
		} else if (rowloc == 2 && colloc == 2) {
			array.add(swapUp(this, rowloc, colloc));
			array.add(swapLeft(this, rowloc, colloc));
		} else if (rowloc == 2 && colloc == 1) {
			array.add(swapRight(this, rowloc, colloc));
			array.add(swapLeft(this, rowloc, colloc));
			array.add(swapUp(this, rowloc, colloc));
		} else if (rowloc == 2 && colloc == 0) {
			array.add(swapUp(this, rowloc, colloc));
			array.add(swapRight(this, rowloc, colloc));
		}
		return array;

	}
	
	
	@Override
	public int compareTo(GameState x) { 
		if((this.depth+this.score) > (x.depth + x.score)) 
			return 1;
		else
			return -1;
	}

	@Override
	public boolean equals(Object o) {
		// test that 2 GameStates are equal
		if (o == this)
			return true;
		if (!(o instanceof GameState))
			return false;
		GameState s = (GameState) o;
		for (int i = 0; i < 3; i++) {
			for (int c = 0; c < 3; c++) {
				if (state[i][c] != s.state[i][c])
					return false;
			}
		}
		return true;
	}
	

	@Override
	public String toString() {
		// print out the int[][] array in a 3x3 block e.g.
		// 0 1 2
		// 3 4 5
		// 6 7 8
		String string = "";
		for (int row = 0; row < 3; row++) {
			string += "\n";
			for (int col = 0; col < 3; col++)
				string += Integer.toString(state[row][col]) + " ";
		}
		return string;
	}
}

