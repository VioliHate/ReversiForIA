package it.unical.mat.model;

import javafx.scene.shape.Circle;

//rappresentazione dei pezzi del reversi sono dei cerchi in javafx con dimensione e tipo


public class Piece extends Circle { //{{{
    private PieceType type = PieceType.NONE;
    private Piece() {} // Force all objects to use my provided constructor.
    public Piece(int size, PieceType type) {
        super();

        int center = size / 2;
        setType(type);
        setCenterX(center);
        setCenterY(center);
        setRadius(center-5);
    }
    public PieceType getType() { return type; }
    
    public void setType(PieceType type) {
        this.type = type;
        setFill(type.getColor());
    }
}
