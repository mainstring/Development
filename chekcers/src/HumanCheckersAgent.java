import java.util.Scanner;

/**
 * An example class implementing Agent class for human players. 
 * The implementation is coupled with the actual game (here, TickTackToe) the agent is playing.
 * @author Azad
 *
 */
public class HumanCheckersAgent extends Agent
{

	static Scanner in = new Scanner(System.in);
	public HumanCheckersAgent(String name) 
	{
		super(name);
		human= true;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void makeMove(Game game) 
	{
		// TODO Auto-generated method stub
		
		int row1,col1,row2,col2;
		Checkers tttGame = (Checkers) game;
		
		boolean first = true;

			correctmove= false;
			System.out.println("Insert target (row,column) and destination (row, column) ([0,7])");

			row1 = tttGame.x1;
			col1 = tttGame.y1;
			row2 = tttGame.x2;
			col2 = tttGame.y2;
			System.out.println(row1 + " " + col1 + " " + row2 + " " + col2 + "     " + role);
			
			first=false;
		correctmove= tttGame.isValidCell(row1, col1, row2, col2,role);
		System.out.println(correctmove);

		if(correctmove)
		{
			tttGame.board[row2][col2]= tttGame.board[row1][col1];
			if(row2==0 && role==0) 
			{
				if(role==0) tttGame.board[row2][col2]= 2;
				else tttGame.board[row2][col2]= -2;
			}
			else if(row2==7 && role==1) 
			{
				if(role==0) tttGame.board[row2][col2]= 2;
				else tttGame.board[row2][col2]= -2;
			}
			tttGame.board[row1][col1]=0;
			if(abs(row1-row2)==2)
			{
				int x= (row1+ row2)/2;
				int y= (col1+ col2)/2;
				tttGame.board[x][y]=0;
			}
		}
		else
		{
			System.out.println("invalid move!");
		}
		
//		tttGame.board[row][col] = role;
	}

	private int abs(int i) {
		// TODO Auto-generated method stub
		if(i<0) i=-i;
		return i;
	}


	

}
