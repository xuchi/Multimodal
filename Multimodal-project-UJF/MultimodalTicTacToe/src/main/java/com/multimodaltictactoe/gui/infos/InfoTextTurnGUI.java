package com.multimodaltictactoe.gui.infos;

import com.multimodaltictactoe.logic.checker.CheckerListener;
import com.multimodaltictactoe.logic.controler.ControlerListener;
import com.multimodaltictactoe.logic.utils.GameStatus;
import com.multimodaltictactoe.logic.utils.Player;
import com.multimodaltictactoe.utils.Constants;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class InfoTextTurnGUI extends Group implements ControlerListener, CheckerListener {
    private static final Font FONT = Constants.Gui.Fonts.FONT_INFO_TURN;
    private static final Font FONT_BOLD = Constants.Gui.Fonts.FONT_INFO_TURN_BOLD;
    
    private static final Color COLOR_PLAYER_1 = Constants.Gui.Colors.COLOR_PLAYER_1;
    private static final Color COLOR_PLAYER_2 = Constants.Gui.Colors.COLOR_PLAYER_2;
    private static final Color COLOR_STRING_TURN = Constants.Gui.Colors.COLOR_STRING_TURN;
    
    private final TextFlow messageTurnP1;
    private final TextFlow messageTurnP2;
    private final TextFlow messageP1Win;
    private final TextFlow messageP2Win;
    private final TextFlow messageDrawGame;
    
    private boolean isEnd;
    
    public InfoTextTurnGUI(Player actualPlayer, GameStatus actualStatus, Player actualStatusPlayer) {
	super();
	this.isEnd = false;
	this.messageTurnP1 = createTextFlow(Constants.Gui.Strings.MESSAGE_TURN_PLAYER_1, Constants.Gui.Strings.INDICE_TURN_PLAYER_1, true);
	this.messageTurnP2 = createTextFlow(Constants.Gui.Strings.MESSAGE_TURN_PLAYER_2, Constants.Gui.Strings.INDICE_TURN_PLAYER_2, false);
	this.messageP1Win = createTextFlow(Constants.Gui.Strings.MESSAGE_PLAYER_1_WIN, Constants.Gui.Strings.INDICE_PLAYER_1_WIN, true);
	this.messageP2Win = createTextFlow(Constants.Gui.Strings.MESSAGE_PLAYER_2_WIN, Constants.Gui.Strings.INDICE_PLAYER_2_WIN, false);
	this.messageDrawGame = createTextFlow(Constants.Gui.Strings.MESSAGE_DRAW_GAME, Constants.Gui.Strings.INDICE_DRAW_GAME, true);
	if(actualStatus != null) {
	    setStatus(actualStatus, actualStatusPlayer);
	}
	else {
	    setTurn(actualPlayer == Player.PLAYER_1);
	}
    }
    
    private synchronized void setTurn(boolean isPlayer1Turn) {
	if(!this.isEnd) {
	    this.getChildren().clear();
	    if(isPlayer1Turn) {
		this.getChildren().add(messageTurnP1);
	    }
	    else {
		this.getChildren().add(messageTurnP2);
	    }
	}
    }
    
    private static TextFlow createTextFlow(String[] message, int indiceBold, boolean isPlayer1) {
	TextFlow tf = new TextFlow();
	for(int i = 0; i < message.length; i++) {
	    Text t = new Text(message[i]);
	    if(i == indiceBold) {
		t.setFont(FONT_BOLD);
		t.setFill(isPlayer1?COLOR_PLAYER_1:COLOR_PLAYER_2);
	    }
	    else {
		t.setFont(FONT);
		t.setFill(COLOR_STRING_TURN);
	    }
	    tf.getChildren().add(t);
	}
	return tf;
    }

    @Override
    public void playerChanged(Player player) {
	Platform.runLater(new Runnable() {
	    @Override
	    public void run() {
		setTurn(player == Player.PLAYER_1);
	    }
	});
    }
    
    private synchronized void setStatus(GameStatus gameStatus, Player player) {
	this.isEnd = true;
	this.getChildren().clear();
	if(gameStatus == GameStatus.VICTORY) {
	    this.getChildren().add((player == Player.PLAYER_1)?messageP1Win:messageP2Win);
	}
	else {
	    this.getChildren().add(messageDrawGame);
	}
    }

    @Override
    public void statusChanged(GameStatus gameStatus, Player player) {
	Platform.runLater(new Runnable() {
	    @Override
	    public void run() {
		setStatus(gameStatus, player);
	    }
	});
    }

    @Override
    public void gameRestarted(Player player) {
	Platform.runLater(new Runnable() {
	    @Override
	    public void run() {
		isEnd = false;
		setTurn(player == Player.PLAYER_1);
	    }
	});
    }
    
}