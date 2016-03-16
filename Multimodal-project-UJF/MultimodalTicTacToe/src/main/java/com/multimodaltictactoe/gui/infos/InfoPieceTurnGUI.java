package com.multimodaltictactoe.gui.infos;

import com.multimodaltictactoe.gui.piece.PieceGUI;
import com.multimodaltictactoe.logic.controler.ControlerListener;
import com.multimodaltictactoe.logic.utils.Player;
import com.multimodaltictactoe.utils.Constants;
import javafx.application.Platform;
import javafx.beans.binding.NumberBinding;

public class InfoPieceTurnGUI extends PieceGUI implements ControlerListener {    
    public InfoPieceTurnGUI(NumberBinding pieceSize, NumberBinding strokeWidth, Player actualPlayer) {
	super(pieceSize, strokeWidth, Constants.Gui.Times.TRANSITION_COLOR_PIECE_TIME, 0.0,
		Constants.Gui.Colors.COLOR_PLAYER_1, Constants.Gui.Colors.COLOR_PLAYER_2);
	setTurn(actualPlayer);
    }
    
    private void setTurn(Player player) {
	this.clear();
	this.setPlayer(player);
    }

    @Override
    public void playerChanged(Player player) {
	Platform.runLater(new Runnable() {
	    @Override
	    public void run() {
		setTurn(player);
	    }
	});
    }

    @Override
    public void gameRestarted(Player player) {
	//Ne rien faire
    }
}
