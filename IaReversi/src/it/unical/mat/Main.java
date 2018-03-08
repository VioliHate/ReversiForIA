package it.unical.mat;



import java.io.IOException;

import it.unical.mat.model.Board;
import it.unical.mat.model.Piece;
import it.unical.mat.model.PieceType;
import it.unical.mat.view.TitleLabel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {


	static final int BOARD_SIZE = 8;
	static final int BOX_SIZE = 40;
	static final int FLIP_DURATION = 400;

	static int blackPiecePoints = 0;
	static int whitePiecePoints = 0;

	static final it.unical.mat.model.PieceType startingPiece = PieceType.BLACK;

	static PieceType currentTurn = startingPiece;

	static Board Board;
	private static Stage primaryStage;
	private static BorderPane root;
	static TitleLabel ownerTurnLabel;
	static TitleLabel displayBlackPoint;
	static TitleLabel displayWhitePoint;
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
			scene.getStylesheets().addAll(this.getClass().getResource("application.css").toExternalForm());

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

		ownerTurnLabel = new TitleLabel(22, true);
		displayBlackPoint=new TitleLabel(18,true);
		displayWhitePoint=new TitleLabel(18,true);

		SplitPane play= new SplitPane();
		GridPane point=new GridPane();

		//blocco il divider
		play.setDividerPositions(0.4);
		point.maxWidthProperty().bind(play.widthProperty().multiply(0.4));
		point.minWidthProperty().bind(play.widthProperty().multiply(0.4));

		//view del point
		point.setConstraints(ownerTurnLabel, 1, 1);
		point.setConstraints(displayBlackPoint, 1, 2);
		point.setConstraints(displayWhitePoint, 1, 3);
		point.getStyleClass().add("pane");
		point.getChildren().addAll(ownerTurnLabel,displayBlackPoint,displayWhitePoint);







		FlowPane root2 = new FlowPane(Orientation.VERTICAL);
		root2.setAlignment(Pos.CENTER);
		root2.getStyleClass().add("sfondo");
		Board = new Board(BOARD_SIZE, BOX_SIZE, FLIP_DURATION);
		root2.getChildren().add(Board);
		play.getItems().addAll(point,root2);
		root.setCenter(play);
		updateOwnerTurnTitle();
		setupClickListeners();







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


	public static void setupClickListeners() { // {{{
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {
				final Piece currentOwner = Board.getPiece(row, column);
				final int f_row = row;
				final int f_column = column;
				Board.getBox(row, column).setOnMouseClicked(event->{

					if (currentOwner.getType() == PieceType.NONE && Board.isValidPosition(f_row, f_column, currentTurn)) {

						currentOwner.setType(currentTurn);
						Board.updateBoardForFlips(f_row, f_column);
						nextTurn();
					}
					Timeline timePoint = new Timeline(new KeyFrame(Duration.millis(FLIP_DURATION), ev -> {
						blackPiecePoints=Board.blackCounter();
						whitePiecePoints=Board.whiteCounter();

						updatePoint(blackPiecePoints, whitePiecePoints);


						//                                    Robot.click(Board.getBox(positionRow, positionCol));
					}));

					timePoint.play();

					if (Board.hasGameEnded()) {

						String winner = "";
						boolean haveTied = false;

						if (whitePiecePoints < blackPiecePoints) {
							winner = PieceType.BLACK.toString();
						} else if (whitePiecePoints > blackPiecePoints) {
							winner = PieceType.WHITE.toString();
						} else {
							haveTied = true;
						}

						ownerTurnLabel.setText(haveTied ? "pareggio" : winner + " vince");
						System.out.println(winner);
					} else {
						//Board.highlightValidPositions(currentTurn);
						updateOwnerTurnTitle();

						if (currentTurn == PieceType.WHITE) {

							Timeline timeLine = new Timeline(new KeyFrame(Duration.millis(FLIP_DURATION), ev -> {
								int positionRow = 0;
								int positionCol = 0;
								int count = 0;
								for (int i = 0; i < BOARD_SIZE; i++) {
									for (int j = 0; j < BOARD_SIZE; j++) {
										if (Board.isValidPosition(i, j, PieceType.WHITE)) {
											int tempCount = Board.numFlips(i, j);
											if (tempCount > count) {
												count = tempCount;
												positionRow = i;
												positionCol = j;
											}
										}
									}
								}


								//                                    Robot.click(Board.getBox(positionRow, positionCol));
							}));

							timeLine.play();

						}
					}



				});

			}

		}

	} 
	public static void nextTurn() { 
		currentTurn = (currentTurn == PieceType.BLACK) ? PieceType.WHITE : PieceType.BLACK;
	}
	public static void updateOwnerTurnTitle() { // 
		ownerTurnLabel.setText(currentTurn + " Player's Turn");
	} 

	public static void updatePoint(int blackPoint,int whitePoint) {
		displayBlackPoint.setText("Black point: "+blackPoint);
		displayWhitePoint.setText("White point: "+whitePoint);
	}


	public static void main(String[] args) {
		launch(args);
	}
}
