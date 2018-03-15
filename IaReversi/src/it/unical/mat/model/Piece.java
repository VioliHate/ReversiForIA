package it.unical.mat.model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import javafx.scene.shape.Circle;

//rappresentazione dei pezzi del reversi sono dei cerchi in javafx con dimensione e tipo

@Id("pedinaPresente")
public class Piece extends Circle { 
	
	@Param(0)
	int x;
	@Param(1)
	int y;
	@Param(2)
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
    
}
