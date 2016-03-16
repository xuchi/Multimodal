package com.multimodaltictactoe.logic.checker;

import com.multimodaltictactoe.logic.board.BoardListener;
import com.multimodaltictactoe.logic.utils.Coordinate;
import com.multimodaltictactoe.logic.utils.GameStatus;
import com.multimodaltictactoe.logic.utils.Player;
import java.util.HashSet;
import java.util.Set;

public class Checker implements CheckerInterface, BoardListener {
    private final Set<CheckerListener> listeners;
    
    public Checker() {
	this.listeners = new HashSet<>();
    }

    @Override
    public void pieceChanged(Coordinate coord, Player[][] board) {
	boolean isFull = true;
	//colonnes
	for(int c = 0; c < 3; c++) {
	    Player[] column = new Player[3];
	    for(int r = 0; r < 3; r++) {
		if(board[r][c] == null) isFull = false;
		column[r] = board[r][c];
	    }
	    Player winner = getWinner(column);
	    if(winner != null) {
		GameStatus status = GameStatus.VICTORY;
		status.setCoordinates(new Coordinate(0, c), new Coordinate(2, c));
		fireStatusChanged(status, winner);
		return;
	    }
	}
	//lignes
	for(int r = 0; r <3; r++) {
	    Player winner = getWinner(board[r]);
	    if(winner != null) {
		GameStatus status = GameStatus.VICTORY;
		status.setCoordinates(new Coordinate(r, 0), new Coordinate(r, 2));
		fireStatusChanged(status, winner);
		return;
	    }
	}
	//diagonales
	Player[] diag1 = {board[0][0], board[1][1], board[2][2]};
	Player winner = getWinner(diag1);
	if(winner != null) {
	    GameStatus status = GameStatus.VICTORY;
	    status.setCoordinates(new Coordinate(0, 0), new Coordinate(2, 2));
	    fireStatusChanged(status, winner);
	    return;
	}
	Player[] diag2 = {board[0][2], board[1][1], board[2][0]};
	winner = getWinner(diag2);
	if(winner != null) {
	    GameStatus status = GameStatus.VICTORY;
	    status.setCoordinates(new Coordinate(0, 2), new Coordinate(2, 0));
	    fireStatusChanged(status, winner);
	    return;
	}
	//test plein
	if(isFull) {
	    fireStatusChanged(GameStatus.DRAW, null);
	}
    }
    
    @Override
    public void boardCleared() {
	//ne rien faire
    }
    
    private Player getWinner(Player[] trio) {
	Player player = trio[0];
	for(Player p : trio) {
	    if(p != player) {
		return null;
	    }
	}
	return player;
    }
    
    private void fireStatusChanged(GameStatus gameStatus, Player player) {
	for(CheckerListener cl : this.listeners) {
	    cl.statusChanged(gameStatus, player);
	}
    }

    @Override
    public boolean addListener(CheckerListener listener) {
	return this.listeners.add(listener);
    }

    @Override
    public boolean removeListener(CheckerListener listener) {
	return this.listeners.remove(listener);
    }
}
