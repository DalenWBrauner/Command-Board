package view;

import javafx.scene.Node;

class Board{
	private final BoardSkin skin;
	
	private final Tile[][] tiles = new Tile[10][10];
	
	//Constructor, makes 10x10 board.
	//Doesn't have to be square
	public Board (Game game){
		for (int i=0; i < 10; i++){
			for (int j=0; j <10; j++){
				tiles[i][j] = new Tile(game);
			}
		}
		skin = new BoardSkin(this);
	}
	
	public Tile getTile(int i, int j){
		return tiles[i][j];
	}
	
	public Node getSkin(){
		return skin;
	}
}
	

	
	