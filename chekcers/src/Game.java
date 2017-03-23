import java.util.Random;

import javax.swing.JFrame;


/**
 * Extend this abstract class for your game implementation
 * @author Azad
 *
 */
public abstract class Game
{
	JFrame jf= new JFrame("watch out!!");
	drawingBoard r;
	int x1,x2,y1,y2;
	Agent agent[]; // Array containing all the agents, here we only consider two player games.
	String name = "A Generic Game"; //A name for the Game object, it will be changed by the actual game class extending it

	Random random = new Random();
	Agent winner = null; // The winning agent will be saved here after the game compeltes, if null the game is drawn.


	public Game(Agent a, Agent b)
	{
		// TODO Auto-generated constructor stub
		r= new drawingBoard();
		jf.setSize(600,600);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		jf.setAlwaysOnTop(true);
		jf.add(r);
		agent = new Agent[2];
		agent[0] = a;
		agent[1] = b;

	}
	//copy constructor
	public Game( )
	{

	}

	/**
	 * The actual game loop, each player takes turn.
	 * The first player is selected randomly
	 */
	public void play()
	{
		updateMessage("Starting " + name + " between "+ agent[0].name+ " and "+ agent[1].name+".");
		//change
		int turn = 0 ; //machine-1// first move =human; //random.nextInt(2);


		initialize(false);
		System.out.println("Initial State");
		showGameState() ;
		System.out.println(agent[turn].name+ " makes the first move.");

		while(!isFinished())
		{
			updateMessage(agent[turn].name+ "'s turn and he sees this> ");
			this.showGameState();
			
			r.paintable= agent[turn].human;
			agent[turn].correctmove=false;
			
			while(!agent[turn].correctmove)
			{
				r.paintable= agent[turn].human;
				if(r.paintable)
				{
					r.flag1=true;
					while(r.flag1)
					{
//						System.out.println(r.flag1);
						try {
							Thread.sleep(5);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					System.out.println(r.flag1);
					r.repaint();
					while(r.flag2)
					{
						try {
							Thread.sleep(5);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					r.repaint();
					x1= r.x1/70; y1= r.y1/70;
					x2= r.x2/70; y2= r.y2/70;
					System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);
				}
				agent[turn].makeMove(this);
				System.out.println("Hoy ni" + r.paintable);
			}
			r.paintable=false;
			this.updateMessage(agent[turn].name+ " : Move Done Success! ");;
			showGameState();

			turn = (turn+1)%2;
		}

		if (winner != null)
			updateMessage(winner.name+ " wins!!!");
		else
			updateMessage("Game drawn!!");

	}

	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		String str = "";
		return str;
	}

	/**
	 *
	 * @return Returns true if the game has finished. Also must update the winner member variable.
	 */
	abstract boolean isFinished();

	/**
	 * Initializes the game as needed. If the game starts with different initial configurations, it can be read from file.
	 * @param fromFile if true loads the initial state from file.
	 */
	abstract void initialize(boolean fromFile);

	/**
	 * Prints the game state in console, or show it in the GUI
	 */
	abstract void showGameState();

	/**
	 * Shows game messages in console, or in the GUI
	 */
	abstract void updateMessage(String msg);
}
