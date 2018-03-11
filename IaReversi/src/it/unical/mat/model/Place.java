package it.unical.mat.model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

//gestore del posizionamento della pedina
@Id("place")
public class Place {


	// Coordinata X della pedina da posizionare
	@Param(0)
	private int pieceX;

	// Coordinata Y della pedina da posizionare
	@Param(1)
	private int pieceY;

	// Coordinata X della nuova cella in cui posizionare la pedina
	@Param(2)
	private int placeX;

	// Coordinata Y della nuova cella in cui posizionare la pedina
	@Param(3)
	private int placeY;

	public Place() {

	}

	public Place(int pieceX, int pieceY, int placeX, int placeY) {

		this.pieceX = pieceX;
		this.pieceY = pieceY;
		this.placeX = placeX;
		this.placeY = placeY;

	}

	@Override
	public String toString() {

		return "Move( " + getPieceX() + " , " + getPieceX() + " , " + getPieceX() + " , " + getPieceY() + ")";

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

	public void setPieceY(int pieceY) {
		this.pieceY = pieceY;
	}

	public int getPlaceX() {
		return placeX;
	}

	public void setPlaceX(int placeX) {
		this.placeX =  placeX;
	}

	public int getPlaceY() {
		return placeY;
	}

	public void setPlaceY(int placeY) {
		this.placeY = placeY;
	}

}


