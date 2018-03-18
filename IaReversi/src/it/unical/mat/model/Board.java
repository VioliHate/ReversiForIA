package it.unical.mat.model;


import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.OptionDescriptor;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv.desktop.DLVDesktopService;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/*
 *  Questa classe rappresenta la tavola di gioco 8x8 di reversi
 *  dimensione da 0 a 7
 * 
 */


public class Board extends GridPane {
	private String backgroundHexFirst = "#267326";
	private String backgroundHexSecond = "#004d00";
	private int boardSize;

	private int boxSize;
	private Duration flipDuration;

	private int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

	private Piece[][] Pieces;
	private Cell[][] cell;


	// DLV Classes
	private static Handler handler;
	private InputProgram inputProgram;
	private OptionDescriptor od;
	private File file;



	public Board(int boardSize, int boxSize, double flipDuration) {
		super();

		Pieces = new Piece[boardSize][boardSize];
		cell = new Cell[boardSize][boardSize];

		this.boardSize = boardSize;
		this.boxSize = boxSize;
		this.flipDuration = Duration.millis(flipDuration);

		// creazione della board e posizionamento delle 4 pedine di inizio gioco
		for (int i = 0; i < boardSize; i++)
			getRowConstraints().add( new RowConstraints(boxSize));
		for (int i = 0; i < boardSize; i++)
			getColumnConstraints().add( new ColumnConstraints(boxSize));

		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				Cell box = new Cell();
				Piece Piece = new Piece(boxSize, PieceType.NONE);
				if((i+j)%2==0) {
					box.setStyle("-fx-background-color: " + backgroundHexFirst + ";");
				}
				else {
					box.setStyle("-fx-background-color: " + backgroundHexSecond + ";");
				}
				box.getChildren().add(Piece);
				setPiece(i, j, Piece);
				Piece.setX(i);
				Piece.setY(j);
				setBox(i, j, box);
				add(box, j, i);
				box.setX(i);
				box.setY(j);
			}
		}

		int middle = boardSize/2;

		Piece topLeft = getPiece(middle - 1, middle - 1);
		Piece topRight = getPiece(middle - 1, middle);
		Piece bottomLeft = getPiece(middle, middle - 1);
		Piece bottomRight = getPiece(middle, middle);

		topLeft.setType(PieceType.BLACK);
		bottomRight.setType(PieceType.BLACK);
		topRight.setType(PieceType.WHITE);
		bottomLeft.setType(PieceType.WHITE);

		setGridLinesVisible(true);
		setAlignment(Pos.CENTER);

		//DLV set
		if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0)
		{
			handler = new DesktopHandler(new DLVDesktopService("source/dlv.mingw.exe"));
		} else
		{
			handler = new DesktopHandler(new DLVDesktopService("source/dlv.x86-64-linux-elf-static.bin"));
		}

		inputProgram = new ASPInputProgram();
		inputProgram.addFilesPath("source/fatti.dl");
		handler.addProgram(inputProgram);

		file = new File("fatti.dl");
		takeClassDlv();

	}

	public boolean hasGameEnded() {
		boolean containsBlankBox = false;

		for (Piece[] row: Pieces) {
			for (Piece Piece: row) {
				if (Piece.getType() == PieceType.NONE)
					containsBlankBox = true;
			}
		}

		return !containsBlankBox;
	}

	// flip related
	public void updateBoardForFlips(int originalRow, int originalColumn) {
		PieceType[][] PieceTypes = new PieceType[boardSize][boardSize];

		// Setup PieceType board representation of Pieces.
		for (int i = 0; i < boardSize; i++)
			for (int j = 0; j < boardSize; j++)
				PieceTypes[i][j] = getPiece(i, j).getType();

		for (int[] directionGroup: directions) {
			int rowDirection = directionGroup[0];
			int columnDirection = directionGroup[1];
			if (isFlipableDirection(originalRow, originalColumn, rowDirection, columnDirection, null)) {
				flipInDirection(PieceTypes, originalRow, originalColumn, rowDirection, columnDirection);
			}
		}

	}

	//check controllo posizione valida
	public boolean isValidPosition(int row, int column, PieceType type) {
		boolean valid = false;
		Piece Piece = getPiece(row, column);
		for (int[] directionGroup: directions) {

			int rowDirection = directionGroup[0];
			int columnDirection = directionGroup[1];
			if (Piece.getType() == PieceType.NONE && isFlipableDirection(row, column, rowDirection, columnDirection, type)) {
				return true;
			}
		}

		return valid;
	}

	//
	public int numFlips(int originalRow, int originalColumn) {
		PieceType originalPieceType = getPiece(originalRow, originalColumn).getType();

		int highCount = 0;

		for (int[] directionGroup: directions) {
			int rowDirection = directionGroup[0];
			int columnDirection = directionGroup[1];

			int count = 0;

			int row = originalRow + rowDirection;
			int column = originalColumn + columnDirection;

			while (row < boardSize && row >= 0 && column < boardSize && column >= 0) {
				PieceType PieceType = getPiece(row, column).getType();

				if (PieceType == PieceType.NONE || PieceType == originalPieceType) {
					break;
				}

				count++;

				row += rowDirection;
				column += columnDirection;
			}

			if (count > highCount) {
				highCount = count;
			}
		}

		return highCount;
	}

	public void flipInDirection(PieceType[][] PieceTypes, int originalRow, int originalColumn, int rowDirection, int columnDirection) {

		PieceType originalPieceType = PieceTypes[originalRow][originalColumn];

		int row = originalRow + rowDirection;
		int column = originalColumn + columnDirection;

		while (row < boardSize && row >= 0 && column < boardSize && column >= 0) {
			PieceType PieceType = PieceTypes[row][column];

			if (PieceType == PieceType.NONE || PieceType == originalPieceType) {
				break;
			}

			Piece Piece = getPiece(row, column);

			RotateTransition firstRotator = new RotateTransition(flipDuration, Piece);
			firstRotator.setAxis(Rotate.Y_AXIS);
			firstRotator.setFromAngle(0);
			firstRotator.setToAngle(90);
			firstRotator.setInterpolator(Interpolator.LINEAR);
			firstRotator.setOnFinished(e -> Piece.setType(originalPieceType));

			RotateTransition secondRotator = new RotateTransition(flipDuration, Piece);
			secondRotator.setAxis(Rotate.Y_AXIS);
			secondRotator.setFromAngle(90);
			secondRotator.setToAngle(180);
			secondRotator.setInterpolator(Interpolator.LINEAR);

			new SequentialTransition(firstRotator, secondRotator).play();

			row += rowDirection;
			column += columnDirection;
		}
	}

	//suggerimento mossa
	public int hintValidPositions(PieceType type) {
		int countHint=0;
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				Piece Piece = getPiece(i, j);

				Piece.setType(Piece.getType());
				Piece.setRadius(boxSize/2 -5);
				Piece.setOpacity(1);

				for (int[] directionGroup: directions) {
					int rowDirection = directionGroup[0];
					int columnDirection = directionGroup[1];

					if (Piece.getType() == PieceType.NONE && isFlipableDirection(i, j, rowDirection, columnDirection, type)) {
						countHint++;
						Piece.setFill(Color.YELLOW);
						Piece.setRadius(boxSize/2 -10);
						Piece.setOpacity(0.2);
						break;
					}
				}
			}
		}
		return countHint;
	}
	

	public boolean isFlipableDirection(int originalRow, int originalColumn, int rowDirection, int columnDirection, PieceType optionalPieceType) {
	
		PieceType originalPieceType = (optionalPieceType == null) ? getPiece(originalRow, originalColumn).getType() : optionalPieceType;

		int row = originalRow + rowDirection;
		int column = originalColumn + columnDirection;

		int count = 0;
		while (row < boardSize && row >= 0 && column < boardSize && column >= 0) {
			Piece Piece = getPiece(row, column);
			PieceType PieceType = Piece.getType();

			if (PieceType == PieceType.NONE || Piece.getFill() == Color.YELLOW) {
				break;
			} else if (PieceType == originalPieceType) {
				return count > 0;
			}

			row += rowDirection;
			column += columnDirection;
			count++;
		}

		return false;
	}

	public int blackCounter() {
		int black=0;


		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {

				if (getPiece(i, j).getType()==(PieceType.BLACK))
					black++;
			}
		}

		return black;
	}
	public int whiteCounter() {
		int white=0;


		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {

				if (getPiece(i, j).getType()==(PieceType.WHITE))
					white++;
			}
		}

		return white;
	}

	// getters
	public Pane getBox(int row, int column) {
		return cell[row][column];
	}
	public Piece getPiece(int row, int column) {
		return Pieces[row][column];
	}

	// setters
	private void setBox(int row, int column, Cell box) {
		cell[row][column] = box;
	}
	private void setPiece(int row, int column, Piece Piece) {
		Pieces[row][column] = Piece;
	}


	public void stampa() {
		for(int i=0;i<boardSize;i++) {
			for(int j=0;j<boardSize;j++) {
				System.out.print(cell[i][j].getX()+","+cell[i][j].getY()+" | ");
			}
			System.out.println();
		}
		for(int i=0;i<boardSize;i++) {
			for(int j=0;j<boardSize;j++) {
				System.out.print(Pieces[i][j].getX()+","+Pieces[i][j].getY()+","+Pieces[i][j].getType().toString()+" | ");
			}
			System.out.println();
		}
	}
	public int getBoardSize() {
		return boardSize;
	}
	public void takeClassDlv()  {
		// Metodo che registra le classi in DLV
		try {
			ASPMapper.getInstance().registerClass(Piece.class);
			ASPMapper.getInstance().registerClass(Place.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public Point prendereFatti() throws Throwable {

		

		handler.addOption(new OptionDescriptor("-filter=posizionamento "));
		//		handler.addOption(new OptionDescriptor("-n=1 "));


		String fatti=new String();

		for (int x = 0; x < boardSize; x++) {

			for (int y = 0; y < boardSize; y++) {

				if (Pieces[x][y] instanceof Piece) {
					if(Pieces[x][y].getType()!=PieceType.NONE) {
						fatti+="pedinaPresente(" + x + "," + y + ","+ Pieces[x][y].getType().toString() + ").";
					}
				}

			}

		}
		System.out.println(fatti);
		InputProgram inputProgram;
		inputProgram = new ASPInputProgram(fatti);
		inputProgram.addFilesPath("source/regole_movimento.dl");
		handler.addProgram(inputProgram);


		// Lancio dlv
		Output o = handler.startSync();

		// Controlli
		AnswerSets answerSets = (AnswerSets) o;

		if (answerSets.getAnswersets().size() == 0) {
			System.out.println("niente answerset");
			return new Point(-3,-3);
		}

		AnswerSet as = answerSets.getAnswersets().get(0);
		System.out.println(as.toString());
		
		String mossa = as.getAnswerSet().get(0);
		
		String[] valori = mossa.split("[(]")[1].split("[,)]");
		
		System.out.println(valori[0]+","+valori[1]);

		// pulisco l'handler
		handler.removeProgram(inputProgram);
		handler.removeOption(0);
		handler.removeOption(1);

		return new Point(Integer.parseInt(valori[0]),Integer.parseInt(valori[1]));
	}


}