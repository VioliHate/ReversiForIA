package it.unical.mat.model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import javafx.scene.layout.Pane;

@Id("cell")

public class Cell extends Pane{

	@Param(0)
	int x;
	@Param(1)
	int y;
	
	
	
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	
	
	
	
	
}
