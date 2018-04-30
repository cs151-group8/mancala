package mancala;

public class Mancala {
	  
	  public static void main(String[] args){
		MancalaModel model = new MancalaModel();
		View view = new View(model);
		model.attach(view);
	  }
	}