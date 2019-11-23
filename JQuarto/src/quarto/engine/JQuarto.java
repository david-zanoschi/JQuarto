package quarto.engine;

import quarto.engine.board.Board;
import quarto.engine.board.Board.Builder;

public class JQuarto {

	public static void main(String[] args) 
	{
		Builder builder = new Builder();
		Board board = builder.build();
		
		StateManager stateManager = new StateManager(board);
		stateManager.run();
	}

}
















