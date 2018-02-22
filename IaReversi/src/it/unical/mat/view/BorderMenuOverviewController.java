package it.unical.mat.view;

import it.unical.mat.Main;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;

public class BorderMenuOverviewController {
	private Main main;
	
	@FXML
	public void goExit() {
		System.exit(0);
	}
	
	@FXML
	public void goToPlay(){
		main.showPlayScene();
	}
	
	@FXML
	public void goToRules() {
		
	}
	
	@FXML
	public void goToAbout() {
		
	}
}
