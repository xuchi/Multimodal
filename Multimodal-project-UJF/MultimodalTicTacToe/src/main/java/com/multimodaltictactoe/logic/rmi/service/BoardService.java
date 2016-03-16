package com.multimodaltictactoe.logic.rmi.service;

import com.multimodaltictactoe.logic.rmi.listeners.BoardServiceListener;
import com.multimodaltictactoe.logic.board.Board;
import com.multimodaltictactoe.logic.board.BoardListener;
import com.multimodaltictactoe.logic.utils.Coordinate;
import com.multimodaltictactoe.logic.utils.Player;
import java.util.HashMap;

public class BoardService implements BoardServiceInterface {
    private static int counter = 0;
    private final Board board;
    private final HashMap<Integer, BoardServiceListener> listeners;
    
    public BoardService() {
	this.board = new Board();
	this.board.addListener(new BoardListener() {
	    @Override
	    public void pieceChanged(Coordinate coord, Player[][] board) {
		firePieceChanged(coord, board);
	    }
	    @Override
	    public void boardCleared() {
		fireBoardCleared();
	    }
	});
	this.listeners = new HashMap<>();
    }

    @Override
    public int addListener(BoardServiceListener listener) {
	int id = getNewId();
	this.listeners.put(id, listener);
	return id;
    }

    @Override
    public boolean removeListener(int id) {
	return this.listeners.remove(id) != null;
    }

    @Override
    public void set(Coordinate coord, Player player) {
	this.board.set(coord, player);
    }

    @Override
    public void clear() {
	this.board.clear();
    }

    @Override
    public String getBoard() {
	Player[][] returnBoard = this.board.getCopy();
	return boardToString(returnBoard);
    }
    
    private void fireBoardCleared() {
	for(BoardServiceListener bl : this.listeners.values()) {
	    bl.boardCleared();
	}
    }
    
    private void firePieceChanged(Coordinate coord, Player[][] board) {
	for(BoardServiceListener bl : this.listeners.values()) {
	    bl.pieceChanged(coord, board);
	}
    }
    
    private static int getNewId() {
	counter++;
	return counter;
    }
    
    public static String boardToString(Player[][] board) {
	StringBuilder sb = new StringBuilder();
	for(int r = 0; r < 3; r++) {
	    for(int c = 0; c < 3; c++) {
		if(board[r][c] == Player.PLAYER_1) {
		    sb.append('1');
		}
		else if(board[r][c] == Player.PLAYER_2) {
		    sb.append('2');
		}
		else {
		    sb.append(' ');
		}
	    }
	}
	return sb.toString();
    }
    
    public static Player[][] stringToBoard(String board) {
	Player[][] returnBoard = new Player[3][3];
	int i = 0;
	for(int r = 0; r < 3; r++) {
	    for(int c = 0; c < 3; c++) {
		char carac = board.charAt(i);
		Player player;
		if(carac == '1') {
		    player = Player.PLAYER_1;
		}
		else if(carac == '2') {
		    player = Player.PLAYER_2;
		}
		else {
		    player = null;
		}
		returnBoard[r][c] = player;
		i++;
	    }
	}
	return returnBoard;
    }
}
