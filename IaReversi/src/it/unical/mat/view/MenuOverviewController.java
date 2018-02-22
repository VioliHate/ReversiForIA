package it.unical.mat.view;

import it.unical.mat.Main;
import javafx.fxml.FXML;

public class MenuOverviewController {

	private Main main;
	
	@FXML
	public void goToPlay(){
		main.showPlayScene();
	}
	
	
}
