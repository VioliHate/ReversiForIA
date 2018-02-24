package it.unical.mat.view;

import java.io.IOException;

import it.unical.mat.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

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
		main.showRulesScene();
	}
	
	@FXML
	public void goToAbout() {
		main.showAboutScene();
	}
}
