package it.unical.mat.model;


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
 * 
 * 
 */


public class Board extends GridPane { //{{{
    private String backgroundInHex = "#654321";
    private int boardSize;
    private int boxSize;
    private Duration flipDuration;
    private int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

    private Piece[][] Pieces;
    private Pane[][] boxes;

    public Board(int boardSize, int boxSize, double flipDuration) {
        super();

        Pieces = new Piece[boardSize][boardSize];
        boxes = new Pane[boardSize][boardSize];

        this.boardSize = boardSize;
        this.boxSize = boxSize;
        this.flipDuration = Duration.millis(flipDuration);

        // setup grid constaints
        for (int i = 0; i < boardSize; i++)
            getRowConstraints().add( new RowConstraints(boxSize));
        for (int i = 0; i < boardSize; i++)
            getColumnConstraints().add( new ColumnConstraints(boxSize));

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Pane box = new Pane();
                Piece Piece = new Piece(boxSize, PieceType.NONE);
                box.setStyle("-fx-background-color: " + backgroundInHex + ";");
                box.getChildren().add(Piece);
                setPiece(i, j, Piece);
                setBox(i, j, box);
                add(box, j, i);
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
        // Contract: Will not modify the contents of 'PieceTypes', because
        // all flips will act upon the 'Pieces' array.
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

    public void highlightValidPositions(PieceType type) {
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
                        Piece.setFill(Color.YELLOW);
                        Piece.setRadius(boxSize/2 -10);
                        Piece.setOpacity(0.2);
                        break;
                    }
                }
            }
        }
    }

    public boolean isFlipableDirection(int originalRow, int originalColumn, int rowDirection, int columnDirection, PieceType optionalPieceType) {
        // hacky way to do an optional parameter
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

    // getters
    public Pane getBox(int row, int column) {
        return boxes[row][column];
    }
    public Piece getPiece(int row, int column) {
        return Pieces[row][column];
    }

    // setters
    private void setBox(int row, int column, Pane box) {
        boxes[row][column] = box;
    }
    private void setPiece(int row, int column, Piece Piece) {
        Pieces[row][column] = Piece;
    }
}