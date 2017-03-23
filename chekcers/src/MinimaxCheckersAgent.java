import java.util.ArrayList;

/**
 * Example MiniMax agent extending Agent class.
 * Here, for simplicity of understanding min and max functions are written separately. One single function can do the task. 
 * @author Azad
 *
 */
public class MinimaxCheckersAgent extends Agent
{
	
	int dir1[]= {-1,-1,1,1,-2,-2,2,2};
	int dir2[]= {-1,1,-1,1,-2,2,-2,2};
	ArrayList<Checkers> possibilities= new ArrayList<Checkers>();
	
	public MinimaxCheckersAgent(String name) 
	{
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void makeMove(Game game) 
	{
		correctmove=true;
		// TODO Auto-generated method stub
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Checkers tttGame = (Checkers) game;
		CellValueTuple best = alpha_beta_search(tttGame);
		if(best.col>=0)
		{
//			tttGame.board[best.row][best.col] = role;
			System.out.println(best.prev_row + " " + best.prev_col + " " + best.row + " " + best.col);

//			tttGame.showGameState();
			tttGame= moveIt(tttGame, best.prev_row, best.prev_col, best.row, best.col, role);
//			tttGame.showGameState();
		}
		
		
	}
	
	private int heuristic(Checkers game, int temp_role)
	{
		int ans=0;
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				ans+= game.board[i][j];
			}
		}
		if(temp_role==1) ans=-ans;
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				if(temp_role==0 && game.board[i][j]<0) ans+= game.board[i][j];
				if(temp_role==1 && game.board[i][j]>0) ans-= game.board[i][j];
			}
		}
		return ans;
	}
	
	private CellValueTuple alpha_beta_search(Checkers game)
	{
		int alpha=-10000000, beta=10000000;
		int depth=6;
		
		return max(game, alpha, beta, depth);
	}
	
	private void check_for_possibilities(Checkers game, int row, int col, int temp_role)
	{
		boolean fl=true;
		Checkers temp= new Checkers(game);
		for(int i=4; i<8; i++)
		{
			if(temp.isValidCell(row, col, row+dir1[i], col+dir2[i], temp_role))
			{
				fl=false;
				temp= moveIt(temp, row, col, row+dir1[i], col+dir2[i], temp_role);
				check_for_possibilities(temp, row+dir1[i], col+dir2[i], temp_role);
				temp= new Checkers(game);
			}
		}
		if(fl)
		{
			possibilities.add(game);
		}
	}
	
	private CellValueTuple max(Checkers game, int alpha, int beta, int depth)
	{	
		CellValueTuple maxCVT = new CellValueTuple();
		maxCVT.utility = -1000000;
		
		if (game.isBoardFull() || depth==0)
		{
			maxCVT.col=-1;
			maxCVT.row=-1;
			
			maxCVT.utility = heuristic(game, role);
			if(game.isBoardFull()) 
			{
				for(int i=0; i<8; i++)
				{
					for(int j=0; j<8; j++)
					{
						if(role==0 && game.board[i][j]>0)
						{
							maxCVT.utility+=500;
							return maxCVT;
						}
						if(role==1 && game.board[i][j]<0)
						{
							maxCVT.utility+=500;
							return maxCVT;
						}
						if(role==0 && game.board[i][j]<0)
						{
							maxCVT.utility-=500;
							return maxCVT;
						}
						if(role==1 && game.board[i][j]>0)
						{
							maxCVT.utility-=500;
							return maxCVT;
						}
					}
				}
			}
			return maxCVT;  
		}
		
		int v=-10000000;
//		
		Checkers tempGame = new Checkers(game);
		boolean fl=true;
		for (int i = 0; i < 8; i++) 
		{
			for (int j = 0; j<8;j++)
			{
				if(game.board[i][j]==0) continue;
				
//				int x1= game.board[i][j];
				for(int k=0; k<8; k++)
				{
					if(game.isValidCell(i, j, i+dir1[k], j+dir2[k], role))
					{
						fl=false;
						tempGame= moveIt(tempGame, i,j,i+dir1[k], j+dir2[k], role);
						v = min(tempGame, alpha,beta,depth-1).utility;
						if(v>maxCVT.utility)
						{
							maxCVT.utility=v;
							maxCVT.row = i+dir1[k];
							maxCVT.col = j+dir2[k];
							maxCVT.prev_row=i;
							maxCVT.prev_col=j;
						}
						if(v>beta) return maxCVT;
						alpha= Max(alpha,v);
						tempGame = new Checkers(game);
					}
				}
//				game.board[i][j] = -1; // reverting back to original state
				
			}
		}
//		if(fl && depth==8) System.out.println("pai ni");
//		else if(depth==8) System.out.println("depth 8:" +maxCVT.prev_row + " " + maxCVT.prev_col + " " + maxCVT.row + " " + maxCVT.col+ " " + maxCVT.utility);

		if(maxCVT.utility==-1000000) maxCVT.utility=heuristic(game, role);
		return maxCVT;
			
	}
	
	private int abs(int i) {
		// TODO Auto-generated method stub
		if(i<0) i=-i;
		return i;
	}
	
	private Checkers moveIt(Checkers game, int row1,int col1,int row2,int col2, int temp_role)
	{
		game.board[row2][col2]= game.board[row1][col1];
		if(row2==0 && temp_role ==0) 
		{
			game.board[row2][col2]= 2;
		}
		else if(row2==7 && temp_role ==1) 
		{
			game.board[row2][col2]= -2;
		}
		game.board[row1][col1]=0;
		if(abs(row1-row2)==2)
		{
			int x= (row1+ row2)/2;
			int y= (col1+ col2)/2;
			game.board[x][y]=0;
		}
		return game;
	}
	
	private int Min(int v, int utility) {
		// TODO Auto-generated method stub
		if(utility<v) return utility;
		return v;
	}
	private int Max(int v, int utility) {
		// TODO Auto-generated method stub
		if(utility>v) return utility;
		return v;
	}

	private CellValueTuple min(Checkers game, int alpha, int beta, int depth)
	{	
		CellValueTuple maxCVT = new CellValueTuple();
		maxCVT.utility = 1000000;
		
		if (game.isBoardFull() || depth==0)
		{
			maxCVT.col=-1;
			maxCVT.row=-1;
			
			maxCVT.utility = -heuristic(game, role^1);
			if(game.isBoardFull()) 
			{
				for(int i=0; i<8; i++)
				{
					for(int j=0; j<8; j++)
					{
						if(role==0 && game.board[i][j]>0)
						{
							maxCVT.utility+=500;
							return maxCVT;
						}
						if(role==1 && game.board[i][j]<0)
						{
							maxCVT.utility+=500;
							return maxCVT;
						}
						if(role==0 && game.board[i][j]<0)
						{
							maxCVT.utility-=500;
							return maxCVT;
						}
						if(role==1 && game.board[i][j]>0)
						{
							maxCVT.utility-=500;
							return maxCVT;
						}
					}
				}
			}
			return maxCVT;  
		}
		
		int v=10000000;
		Checkers tempGame = new Checkers(game);
		for (int i = 0; i < 8; i++) 
		{
			for (int j = 0; j<8;j++)
			{
				if(game.board[i][j]==0) continue;
				
//				int x1= game.board[i][j];
				for(int k=0; k<8; k++)
				{
					if(game.isValidCell(i, j, i+dir1[k], j+dir2[k], role^1))
					{
						tempGame= moveIt(tempGame, i,j,i+dir1[k], j+dir2[k], role^1);
					
						v = max(tempGame, alpha,beta,depth-1).utility;
						if(v<maxCVT.utility)
						{
							maxCVT.utility=v;
							maxCVT.row = i+dir1[k];
							maxCVT.col = j+dir2[k];
							maxCVT.prev_row=i;
							maxCVT.prev_col=j;
						}
						if(v<alpha) return maxCVT;
						beta= Min(beta,v);
						tempGame = new Checkers(game);
					}
				}
//				game.board[i][j] = -1; // reverting back to original state
				
			}
		}
		if(maxCVT.utility==1000000) maxCVT.utility=-heuristic(game, role^1);
		return maxCVT;
			
	}
	
	private int minRole()
	{
		if(role==0)return 1;
		else return 0;
	}

	class CellValueTuple
	{
		int row,col, utility, prev_row,prev_col;
		public CellValueTuple() {
			// TODO Auto-generated constructor stub
			row =-1;
			col =-1;
			prev_row =-1;
			prev_col =-1;
		}
	}

}
