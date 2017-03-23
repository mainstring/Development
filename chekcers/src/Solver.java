public class Solver {
	
	/*
	 * The starting point of the game.
	 * Instantiates two agents (human/ minimax/ alpha beta pruning/ or other) and pass them to a game object.
	 * Here a TickTackToe game is implemented as an example. You need to extend the abstract class Game to create your own game.
	 * */
	
	public static void main(String[] args) 
	{
		

//		Agent human = new HumanCheckersAgent("Neo");
		Agent human = new MinimaxCheckersAgent("007");
		Agent machine = new MinimaxCheckersAgent("Smith");

		//System.out.println(human.name+" vs. "+machine.name);
		
		Game game = new Checkers(human,machine);
		game.play();
		
	}

}
