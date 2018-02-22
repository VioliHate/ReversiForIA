package it.unical.mat.model;

public class Piece {

	
	private char color; //il colore è rappresentato da un carattere (bianco=='b' || nero=='n')
	

	//costruttore
	public Piece(char color) {
		
		setColor(color);
		
	}
	
	//imposta il colore
	public void setColor(char color) {
		if(color=='b' || color=='n')
			this.color=color;
		System.out.println("colore non valido");
	}
	
	
	//ritorna colore
	public char getColor() {
		return color;
	}
	
	//funzione flip pezzo
	public Piece flip(Piece p) {
		
		if(p.getColor()=='b') {
			p.setColor('n');
		}
		else {
			p.setColor('b');
		}
		
		return p;
	}
}
