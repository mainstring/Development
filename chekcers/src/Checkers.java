import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Example class extending Game class
 * @author Azad
 *
 */
public class Checkers extends Game 
{


	/**
	 * The actual game board
	 * -1 empty, 0 -> W, 1 -> B
	 */
	public int[][] board;
	public int guti[]= new int[2];
	public int king_guti[]= new int[2];
	
	/**
	 * First agent starts with O
	 * @param a
	 * @param b
	 */
	public Checkers(Agent a, Agent b) 
	{
		super(a, b);
		// TODO Auto-generated constructor stub
		
		a.setRole(0); // The first argument/agent is always assigned O (0)
		b.setRole(1); // The second argument/agent is always assigned X (1)
					  // NOtice that, here first dows not mean that afent a will make the first move of the game.
					  // Here, first means the first argument of the constructor
					  // Which of a and b will actually give the first move is chosen randomly. See Game class
		
		name = "Checkers";
		
		board = new int[8][8];
		
	}

	public Checkers(Checkers game) {
		board= new int[8][8];
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				board[i][j]= game.board[i][j];
			}
		}
		guti[1]= game.guti[1];
		guti[0]= game.guti[0];
		king_guti[1]= game.king_guti[1];
		king_guti[0]= game.king_guti[0];
		// TODO Auto-generated constructor stub
	}

	/**
	 * Called by the play method of Game class. It must update the winner variable. 
	 * In this implementation, it is done inside checkForWin() function.
	 */
	@Override
	boolean isFinished() 
	{
		// TODO Auto-generated method stub
		if(checkForWin() != -1)
			return true;
		else if(isBoardFull())
			return true;
		else return false;
	}
	
	public void setGrid()
	{
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				r.grid[i][j]= board[i][j];
			}
		}
	}

	@Override
	void initialize(boolean fromFile) 
	{
		// TODO Auto-generated method stub
		for (int i = 0; i < board.length; i++) 
		{
			for (int j = 0; j < board[i].length; j++) 
			{
				board[i][j] = 0;
				if(i<3)
				{
					if(i%2 == j%2) board[i][j]=-1;
				}
				else if(i>4)
				{
					if(i%2 == j%2) board[i][j]=1;
				}
			}
		}
		guti[0]= guti[1]= 12;
		king_guti[0]= king_guti[1]= 0;
	}

	/**
	 * Prints the current board (may be replaced/appended with by GUI)
	 */
	@Override
	void showGameState() 
	{
		setGrid();
		r.repaint();
		// TODO Auto-generated method stub
		
//		for(int i=0; i<8; i++)
//		{
//			for(int j=0; j<8; j++)
//			{
//				System.out.print(board[i][j]+ " ");
//			}
//			System.out.println();
//		}
		 
        System.out.println("-------------------------------------------");
		
        for (int i = 0; i < 8; i++) 
        {
            System.out.print("| ");
            for (int j = 0; j < 8; j++) 
            {
            	if(board[i][j]==0)
            		System.out.print("  " + " | ");
            	else if	(board[i][j]==1)
            		System.out.print( "O  | ");
            	else if	(board[i][j]==2)
            		System.out.print( "OO | ");
            	else if	(board[i][j]==-2)
            		System.out.print( "XX | ");
            	else
            		System.out.print( "X  | ");
            }
            System.out.println();
            System.out.println("-------------------------------------------");
        }
    }
	
    /** Loop through all cells of the board and if one is found to be empty (contains -1) then return false.
    	Otherwise the board is full.
    */
    public boolean isBoardFull() 
    {

		boolean player1=false, player2= false;
        for (int i = 0; i < 8; i++) 
        {
            for (int j = 0; j < 8; j++) 
            {
                if (board[i][j] >0) 
                {
                	player1=true;
                }
                if(board[i][j]<0)
                {
                	player2=true;
                }
            }
        }
		if(player1== player2) return false;
        return true;
    }
	
	
    /** Returns role of the winner, if no winner/ still game is going on -1.
     * @return role of the winner, if no winner/ still game is going on -1.
     */
    public int checkForWin() 
    {
    	winner = null;
    	int winRole=-1;
    	boolean player1=false, player2= false;
        
    	for (int i = 0; i < 8; i++) 
        {
            for (int j = 0; j < 8; j++) 
            {
                if (board[i][j] >0) 
                {
                	player1=true;
                }
                if(board[i][j]<0)
                {
                	player2=true;
                }
            }
        }
    	if(player1==false) winRole=1;
    	if(player2==false) winRole=0;

    	if(winRole!=-1)
    	{
    		winner = agent[winRole];
    	}
		return winRole;
    }
	
	
	
    // Check to see if all three values are the same (and not empty) indicating a win.
//    private boolean checkRowCol(int c1, int c2, int c3) 
//    {
//        return ((c1 != -1) && (c1 == c2) && (c2 == c3));
//    }
	
	public boolean isValidCell(int row1, int col1, int row2,int col2, int plr)
	{
//		System.out.println(plr);
		if(row1<0 || row1>7 ||col1<0 ||col1>7) return false;
		if(row2<0 || row2>7 ||col2<0 ||col2>7) return false;
		if(plr==0 && board[row1][col1]<=0) return false;
		if(plr==1 && board[row1][col1]>=0) return false;
//		if(plr==0) System.out.println("c "+ board[row2][col2]);
		if(board[row2][col2]!=0) return false;
		if(abs(row1-row2) != abs(col1-col2)) return false;
		if(abs(board[row1][col1])<2)
		{
			if(plr==0 && (row1-row2)<0) return false;
			if(plr==1 && (row1-row2)>0) return false;
		}
		if(abs(row1-row2)==1) return true;
		if(abs(row1-row2)==2)
		{
			int x= (row1+ row2)/2;
			int y= (col1+ col2)/2;
			if(plr==0 && board[x][y]<0) return true;
			if(plr==1 && board[x][y]>0) return true;
		}
		
		return false;
	}
	
	public void moveIt(int row1,int col1,int row2,int col2, int temp_role)
	{
		board[row2][col2]= board[row1][col1];
		if(row2==0 && temp_role ==0) 
		{
			board[row2][col2]= 2;
		}
		else if(row2==7 && temp_role ==1) 
		{
			board[row2][col2]= -2;
		}
		board[row1][col1]=0;
		if(abs(row1-row2)==2)
		{
			int x= (row1+ row2)/2;
			int y= (col1+ col2)/2;
			board[x][y]=0;
		}
	}

	private int abs(int i) {
		// TODO Auto-generated method stub
		if(i<0) i=-i;
		return i;
	}

	@Override
	void updateMessage(String msg) 
	{
		// TODO Auto-generated method stub
		System.out.println(msg);
	}
	
}
