package view;



public class CommandBoardView implements InterfaceView{
	private InterfaceControl controller;
	
	CommandBoardView(InterfaceControl controller){
	if (controller instanceof CommandBoardControl){
		this.controller = (CommandBoardControl) controller;
	}
	else 
		System.out.println("We've made a terrible mistake");
	}
	
	
	
	