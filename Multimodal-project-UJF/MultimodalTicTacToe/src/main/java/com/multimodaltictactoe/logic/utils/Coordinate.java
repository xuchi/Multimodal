package com.multimodaltictactoe.logic.utils;

import java.io.Serializable;

/**
 * Coordonnée représentant les positions des cases sur le plateau de jeu.
 * Voici les coordonnées du plateau de jeu :
 *	A1 B1 C1
 *	A2 B2 C2
 *	A3 B3 C3
 */
public final class Coordinate implements Comparable<Coordinate>, Serializable {
    /**
     * Ligne de la coordonnée.
     */
    private int row;
    /**
     * Colonne de la coordonnée.
     */
    private char column;   
    
    /**
     * Le constructeur.
     * Vérifie la cohérence des paramètres (renvoie une exception si invalide).
     * @param row	La ligne.
     * @param column	La colonne.
     */
    public Coordinate(int row, char column) {
	this.setRow(row);
	this.setColumn(column);
    }
    
    public Coordinate(char column, int row) {
	this.setRow(row);
	this.setColumn(column);
    }
    
    public Coordinate(int row, int column) {
	if(!(row >= 0 && row <= 2 && column >= 0 && column <= 2)) {
	    throw new RuntimeException("Erreur dans les coordonnées : " +
		    row + ";" + column);
	}
	this.row = row + 1;
	this.column = (column == 0) ? 'A' : ((column == 1) ? 'B' : 'C');
    }
    
    public int getRow() {
	return this.row;
    }
    
    public char getColumn() {
	return this.column;
    }
    
    public void setRow(int row) {
	if(!checkRow(row)) {
	    throw new RuntimeException("Le numéro de ligne est invalide : "
		    + row);
	}
	this.row = row;
    }
    
    public void setColumn(char column) {
	if(!checkColumn(column)) {
	    throw new RuntimeException("Le caractère de colonne est invalide : "
		    + column);
	}
	this.column = Character.toUpperCase(column);
    }
    
    private static boolean checkRow(int row) {
	return (row >= 1) && (row <= 3);
    }
    
    private static boolean checkColumn(char column) {
	return ((Character.compare(column, 'A') >= 0)
	    && (Character.compare(column, 'C') <= 0))
	    || ((Character.compare(column, 'a') >= 0)
	    && (Character.compare(column, 'c') <= 0));
    }
    
    public boolean equals(Coordinate coord) {
	if(coord == null) {
	    return false;
	}
	else {
	    return this.row == coord.row && this.column == coord.column;
	}
    }
    
    public int getTabRow() {
	return this.row - 1;
    }
    
    public int getTabColumn() {
	return (this.column == 'A') ? 0 : ((this.column == 'B') ? 1 : 2);
    }
    
    @Override
    public String toString() {
	return "(" + this.column + ";" + this.row + ")";
    }
    
    public String toStringForBoard() {
	return "" + this.column + this.row;
    }

    @Override
    public int compareTo(Coordinate coord) {
	if(this.row < coord.row) {
	    return -1;
	}
	else if(this.row > coord.row) {
	    return 1;
	}
	else {
	    return Character.compare(this.column, coord.column);
	}	
    }
    
}
