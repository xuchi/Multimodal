package com.multimodaltictactoe.logic.utils;

/**
 * Etat du jeu.
 */
public enum GameStatus {
    /**
     * Jeu terminé : Victoire.
     */
    VICTORY(null, null),
    /**
     * Jeu terminé : match nul.
     */
    DRAW(null, null);
    
    private Coordinate start, end;
    
    private GameStatus(Coordinate start, Coordinate end) {
	this.start = start;
	this.end = end;
    }
    
    public void setCoordinates(Coordinate start, Coordinate end) {
	if (start.compareTo(end) <= 0) {
	    this.start = start;
	    this.end = end;
	} else {
	    this.start = end;
	    this.end = start;
	}
    }
    
    public Coordinate getStart() {
	return this.start;
    }
    
    public Coordinate getEnd() {
	return this.end;
    }
}
