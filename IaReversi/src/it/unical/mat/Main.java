package it.unical.mat;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
	
    private Stage primaryStage;
    private static BorderPane root;
	
	@Override
	public void start(Stage primaryStage) {
		
			this.primaryStage = primaryStage;
	        this.primaryStage.setTitle("IaReversi");
	        
	        BorderMenuOverview();
	        showMenuOverview();
	        primaryStage.setResizable(false);
	        
	        
	}

	
    public void BorderMenuOverview() {
        try {
            // carica la menu bar dal file .fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/BorderMenuOverview.fxml"));
            root = (BorderPane) loader.load();
            
            // visualizza
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showMenuOverview() {
        try {
            // carica il menu.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/MenuOverview.fxml"));
            AnchorPane menu = (AnchorPane) loader.load();

            // visualizza
            root.setCenter(menu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public static void showPlayScene() {
    	
    	try {
    		FXMLLoader loader = new FXMLLoader();
        	loader.setLocation(Main.class.getResource("view/SplitPaneGameOverview.fxml"));
			final SplitPane play= (SplitPane) loader.load();

			root.setCenter(play);

			
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    
	public static void main(String[] args) {
		launch(args);
	}
}
