package it.unical.mat.model;

import java.util.Random;

/*
 *  Questa classe rappresenta la tavola di gioco 8x8 di reversi
 *  è una matrice di char che possono essere:
 *  - 'b' per indicare le pedine biance
 *  - 'n' per indicare le pedine nere
 *  - null per indicare le celle vuote della matrice
 * 
 * 
 */


public class Board {

	private Piece board[][];
	Random rand;

	public Board() {
		
		rand=new Random();
		board=new Piece[8][8];
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				board[i][j]=null;
			}
			
		}
		int  n = rand.nextInt(2);
		if(n==0) {
			//se è zero creo una x iniziando dalla pedina bianca
			board[3][3].setColor('b'); 
			board[4][4].setColor('b');
			board[4][3].setColor('n');
			board[3][4].setColor('n');
		}
		else {

			//se è zero creo una x iniziando dalla pedina nera
			board[3][3].setColor('n'); 
			board[4][4].setColor('n');
			board[4][3].setColor('b');
			board[3][4].setColor('b');
		}
		
	}
	
	public Piece getPiece(int i, int j) {
		return board[i][j];
	}
	public void setPiece(int i,int j, Piece p) {
		if(board[i][j].equals(null))
			board[i][j]=p;
			
	}
}