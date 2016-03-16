package com.multimodaltictactoe.logic.board;

import com.multimodaltictactoe.logic.utils.Coordinate;
import com.multimodaltictactoe.logic.utils.Player;
import java.util.ArrayList;
import java.util.List;

public class Board implements BoardInterface {
    private final List<BoardListener> listeners;
    private final Player board[][];
    
    public Board() {
	this(new Player[3][3]);
    }
    
    public Board(Player[][] initBoard) {
	if(initBoard != null) {
	    this.board = initBoard;
	}
	else {
	    this.board = new Player[3][3];
	}
	this.listeners = new ArrayList<>();
    }
    
    @Override
    public boolean addListener(BoardListener listener) {
	return this.listeners.add(listener);
    }
    
    @Override
    public boolean removeListener(BoardListener listener) {
	return this.listeners.remove(listener);
    }
    
    @Override
    public Player get(Coordinate coord) {
	if(coord == null) {
	    return null;
	}
	else {
	    return this.board[coord.getTabRow()][coord.getTabColumn()];
	}
    }
    
    @Override
    public void set(Coordinate coord, Player player) {
	if(player != get(coord)) {
	    this.board[coord.getTabRow()][coord.getTabColumn()] = player;
	    firePieceChanged(coord, copy(this.board));
	}
    }
    
    @Override
    public void clear() {
	for(int r = 0; r < 3; r++) {
	    for(int c = 0; c < 3; c++) {
		this.board[r][c] = null;
	    }
	}
	fireBoardCleared();
    }
    
    private static Player[][] copy(Player[][] board) {
	Player[][] boardCopy = new Player[board.length][];
	for(int i = 0; i < board.length; i++) {
	    boardCopy[i] = new Player[board[i].length];
	    System.arraycopy(board[i], 0, boardCopy[i], 0, board[i].length);
	}
	return boardCopy;
    }
    
    @Override
    public Player[][] getCopy() {
	return copy(this.board);
    }

    private void firePieceChanged(Coordinate coord, Player[][] board) {
	for(BoardListener bl : this.listeners) {
	    bl.pieceChanged(coord, board);
	}
    }
    
    private void fireBoardCleared() {
	for(BoardListener bl : this.listeners) {
	    bl.boardCleared();
	}
    }
    
    @Override
    public String toString() {
	StringBuilder res = new StringBuilder();
	for(int r = 0; r < 3; r++) {
	    for(int c = 0; c < 3; c++) {
		if(this.board[r][c] == Player.PLAYER_1) {
		    res.append("1 ");
		}
		else if(this.board[r][c] == Player.PLAYER_2) {
		    res.append("2 ");
		}
		else {
		    res.append("_ ");
		}
	    }
	    res.append("\n");
	}
	return res.toString();
    }

    @Override
    public void fireInitEvents() {
	for(int r = 0; r < 3; r++) {
	    for(int c = 0; c < 3; c++) {
		if(this.board[r][c] != null) {
		    firePieceChanged(new Coordinate(r, c), board);
		}
	    }
	}
    }

    
}
