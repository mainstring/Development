import java.util.ArrayList;



/**
 * Extend this abstract class for human/ AI agent
 * @author Azad
 *
 */
public abstract class Agent 
{
	boolean human= false;
	boolean correctmove= false;
	static int INF = 99999999 ;
	String name; // Name of the agent
	int role; 	//0 for white, 1 for black
	public Agent(String name) 
	{
		// TODO Auto-generated constructor stub
		this.name = name;
		
	}
	
	/**
	 * Sets the role of this agent. Typlically will be called by your extended Game class (The  class which extends the Game Class).
	 * @param role
	 */
	public void setRole(int role) 
	{
		this.role = role;
	}

	/**
	 * Implement this method to select a move, and change the game state according to the chosen move.
	 * @param game
	 */
	public abstract void makeMove(Game game);
	
	protected boolean okay( int r , int c )
	{
		return (r>=0 && r<8) && (c>=0 && c<8) ;
	}
	
	//My Utility
	class Position 
	{
		int row,col ;
		int dirID ;
		int dist ;
		Position()
		{
			row = -1 ;
			col = -1 ;
			dirID = -1 ;
			dist = -1 ;
		}
	}
	class Move 
	{
		int symbol ;
		int x , y ;
		ArrayList<Position> affectedPositions ;
		Move()
		{
			x = -1 ;
			y = -1 ;
			symbol = -1 ;
			affectedPositions = new ArrayList<Position>() ;
		}

		
	}
	

}
