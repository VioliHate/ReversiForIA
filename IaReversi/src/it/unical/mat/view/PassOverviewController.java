package it.unical.mat.view;

import it.unical.mat.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PassOverviewController {
	private Main main;
	
	@FXML
	Button passaTurno;
	
	public void passTurn() {
		Stage stage = (Stage) passaTurno.getScene().getWindow();
	    // do what you have to do
		main.howToPass();
	    stage.close();
		//System.exit(0);
	}
}
