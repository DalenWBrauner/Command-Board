package view;

import javafx.scene.layout.GridPane;

public class BoardSkin extends GridPane{
   BoardSkin(Board board){
   getStyleClass().add("board");
   
   for (int i = 0; i < 10; i++){
	   for (int j = 0; j < 10; j++){
		   add(board.getTile(i,j).getSkin(), i, j);
	   }
   }
   }
}
     