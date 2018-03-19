package it.unical.mat.model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

//gestore del posizionamento della pedina
@Id("posizionamento")
public class Place {


	// Coordinata X della pedina da posizionare
	@Param(0)
	private int pieceX;

	// Coordinata Y della pedina da posizionare
	@Param(1)
	private int pieceY;


	public Place() {

	}

	public Place(int pieceX, int pieceY) {

		this.pieceX = pieceX;
		this.pieceY = pieceY;


	}

	@Override
	public String toString() {

		return "Move(" + getPieceX() + ", " + getPieceY() + ")";

	}

	public int getPieceX() {
		return pieceX;
	}

	public void setPieceX(int pieceX) {
		this.pieceX = pieceX;
	}

	public int getPieceY() {
		return pieceY;
	}



}


