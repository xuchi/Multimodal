package com.multimodaltictactoe.logic.controler;

import com.multimodaltictactoe.logic.computer.ComputerInterface;
import com.multimodaltictactoe.logic.computer.ComputerListener;
import com.multimodaltictactoe.logic.checker.CheckerListener;
import com.multimodaltictactoe.logic.board.BoardInterface;
import com.multimodaltictactoe.gui.board.BoardGUIListener;
import com.multimodaltictactoe.logic.rmi.connector.ConnectorInterface;
import com.multimodaltictactoe.logic.rmi.listeners.ConnectorListener;
import com.multimodaltictactoe.logic.speech.SpeechRecognizerListener;
import com.multimodaltictactoe.logic.utils.Coordinate;
import com.multimodaltictactoe.logic.utils.GameStatus;
import com.multimodaltictactoe.utils.Logs;
import com.multimodaltictactoe.logic.utils.Player;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controler implements ControlerInterface,
	BoardGUIListener, CheckerListener, ComputerListener, ConnectorListener, SpeechRecognizerListener {
    private static final Logger logger = Logs.getLogger(Controler.class);
    
    private final BoardInterface board;
    private Player actualPlayer;
    private GameStatus actualStatus;
    private Player actualStatusPlayer;
    private boolean isEnd;
    private final ComputerInterface computer;
    private final Set<ControlerListener> listeners;
    private final ConnectorInterface connector;
    
    public Controler(BoardInterface board, ComputerInterface computer, ConnectorInterface connector) {
	this.board = board;
	this.isEnd = false;
	this.computer = computer;
	this.listeners = new HashSet<>();
	this.connector = connector;
	this.actualStatus = null;
	this.actualStatusPlayer = null;
	setPlayer(Player.PLAYER_1);
    }
    
    private void setPlayer(Player player) {
	if(this.actualPlayer != player) {
	    this.actualPlayer = player;
	    firePlayerChanged(player);
	}
    }

    @Override
    public void userClicked(Coordinate coord) {
	logger.log(Level.INFO, "L''utilisateur a clique en : {0}", coord);
	play(coord, Player.PLAYER_1, true);
    }
    
    @Override
    public void onResult(Coordinate coord) {
	logger.log(Level.INFO, "La reconnaissance vocale joue en : {0}", coord);
	play(coord, Player.PLAYER_1, true);
    }
    
    @Override
    public void statusChanged(GameStatus gameStatus, Player player) {
	logger.log(Level.INFO, "Le statut de jeu a changé en : {0} | {1}", new Object[]{gameStatus, player});
	switch(gameStatus) {
	    case VICTORY: case DRAW:
		this.actualStatus = gameStatus;	
		this.actualStatusPlayer = player;
		this.isEnd = true;
		break;
	    default:
		break;
	}
    }
    
    @Override
    public void computerPlayed(Coordinate coord) {
	logger.log(Level.INFO, "L''ordinateur a joue en : {0}", coord);
	play(coord, Player.PLAYER_2, true);
    }
    
    private void play(Coordinate coord, Player player, boolean informConnector) {
	if(!isEnd && isPossible(coord, player)) {
	    this.board.set(coord, player);
	    if(informConnector) {
		this.connector.setBoard(coord, player);
	    }
	    changeTurn();
	}
    }
    
    @Override
    public void restart() {
	logger.info("Le jeu recommence");
	restart(true);
    }
    
    private void restart(boolean informConnector) {
	this.computer.stop();
	this.board.clear();
	if(informConnector) {
	    this.connector.clearBoard();
	}
	setPlayer(Player.PLAYER_1);
	this.actualStatus = null;
	this.actualStatusPlayer = null;
	this.isEnd = false;
	fireGameRestarted(actualPlayer);
    }
    
    private boolean isPossible(Coordinate coord, Player player) {
	return coord != null && this.board.get(coord) == null && this.isTurnOfPlayer(player);
    }
    
    private void changeTurn() {
	if(this.actualPlayer == Player.PLAYER_1) {
	    setPlayer(Player.PLAYER_2);
	}
	else {
	    setPlayer(Player.PLAYER_1);
	}
    }
    
    private boolean isTurnOfPlayer(Player player) {
	if(player == null) {
	    return false;
	}
	else {
	    return this.actualPlayer == player;
	}
    }
    
    private synchronized void firePlayerChanged(Player player) {
	logger.log(Level.INFO, "Envoie evenement joueur change : {0}", player);
	for(ControlerListener cl : this.listeners) {
	    cl.playerChanged(player);
	}
    }
    
    private synchronized void fireGameRestarted(Player player) {
	logger.info("Envoi evenement : le jeu recommence");
	for(ControlerListener cl : this.listeners) {
	    cl.gameRestarted(player);
	}
    }

    @Override
    public boolean addListener(ControlerListener listener) {
	return this.listeners.add(listener);
    }

    @Override
    public boolean removeListener(ControlerListener listener) {
	return this.listeners.remove(listener);
    }

    @Override
    public void pieceChanged(Coordinate coord, Player[][] board) {
	logger.log(Level.INFO, "Une piece du plateau vient de changer : {0} | {1}", new Object[]{coord, board[coord.getTabRow()][coord.getTabColumn()]});
	play(coord, board[coord.getTabRow()][coord.getTabColumn()], false);
    }

    @Override
    public void boardCleared() {
	logger.info("Le plateau vient d'etre vidé");
	restart(false);
    }

    @Override
    public Player getActualPlayer() {
	return this.actualPlayer;
    }
    
    @Override
    public GameStatus getActualStatus() {
	return this.actualStatus;
    }
    
    @Override
    public Player getActualStatusPlayer() {
	return this.actualStatusPlayer;
    }

    @Override
    public void quit() {
	this.computer.stop();
	this.connector.disconnect();
    }
}
