package it.unical.mat.model;

import javafx.scene.paint.Color;

//enum per disctinguere i colori delle 3 tipologie di pedine
// bianco nnero e vuoto

public enum PieceType { // {{{
    NONE,
    WHITE,
    BLACK;

    public Color getColor() {
        switch (this) {
            case WHITE: return Color.WHITE;
            case BLACK: return Color.BLACK;
            default: return null;
        }
    }

    public String toString() {
        switch (this) {
            case WHITE: return "White";
            case BLACK: return "Black";
            default: return "None";
        }
    }
}
