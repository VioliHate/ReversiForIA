package it.unical.mat;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Main extends Application {
	
    private static Stage primaryStage;
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
    
    public static void showRulesScene() {
    	try {
    		// carica il Pane delle regole.
    		FXMLLoader loader = new FXMLLoader();
        	loader.setLocation(Main.class.getResource("view/RulesOverview.fxml"));
			final Pane play= (Pane) loader.load();
			
			//visualizza il Pane inn una finestra secondaria
	        Scene secondaryScene = new Scene(play, 250, 390);
	        Stage secondaryStage = new Stage();
	        secondaryStage.setTitle("Rules");
	        secondaryStage.setScene(secondaryScene);
	        
	        //inserisce la priorità sulla finestra secondaria
	        secondaryStage.initModality(Modality.WINDOW_MODAL);
	        secondaryStage.initOwner(primaryStage);
	        secondaryStage.setResizable(false);

	        
	        secondaryStage.show();	

    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    
    
    public static void showAboutScene() {
    	try {
    		// carica il Pane delle regole.
    		FXMLLoader loader = new FXMLLoader();
        	loader.setLocation(Main.class.getResource("view/AboutOverview.fxml"));
			final Pane play= (Pane) loader.load();
			
			//visualizza il Pane inn una finestra secondaria
	        Scene secondaryScene = new Scene(play, 250, 210);
	        Stage secondaryStage = new Stage();
	        secondaryStage.setTitle("About");
	        secondaryStage.setScene(secondaryScene);
	        
	      //inserisce la priorità sulla finestra secondaria
	        secondaryStage.initModality(Modality.WINDOW_MODAL);
	        secondaryStage.initOwner(primaryStage);
	        secondaryStage.setResizable(false);

	        
	        secondaryStage.show();	
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    
	public static void main(String[] args) {
		launch(args);
	}
}
