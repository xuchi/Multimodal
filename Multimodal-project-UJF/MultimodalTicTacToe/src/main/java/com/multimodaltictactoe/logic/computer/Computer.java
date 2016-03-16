package com.multimodaltictactoe.logic.computer;

import com.multimodaltictactoe.logic.board.BoardInterface;
import com.multimodaltictactoe.logic.controler.ControlerListener;
import com.multimodaltictactoe.logic.utils.Coordinate;
import com.multimodaltictactoe.logic.utils.Player;
import com.multimodaltictactoe.utils.Constants;
import java.util.HashSet;
import java.util.Set;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class Computer implements ComputerInterface, ControlerListener {
    private static final long THINKING_TIME = Constants.Logic.Times.THINKING_TIME;
    private final Set<ComputerListener> listeners;
    private final BoardInterface board;
    private Task computerTask;
    private final boolean isActivated;
    
    public Computer(BoardInterface board, boolean isActivated) {
	this.board = board;
	this.listeners = new HashSet<>();
	this.computerTask = null;
	this.isActivated = isActivated;
    }
    
    @Override
    public void playerChanged(Player player) {
	if(player == Player.PLAYER_2 && isActivated) {
	    stop();
	    computerTask = createComputerTask();
	    new Thread(computerTask).start();
	}
    }
    
    private Task<Void> createComputerTask() {
	return new Task<Void>() {
	    @Override
	    protected Void call() throws Exception {
		try {
		    Thread.sleep(THINKING_TIME);
		} catch (InterruptedException ie) {
		    return null;
		}
		Coordinate coord = play();
		fireComputerPlayed(coord);
		return null;
	    }
	};
    }
    
    private Coordinate play() {
	int count = 0;
	Player[][] b = this.board.getCopy();
	int victory[][] = {
	    {0, 0, 0, 1, 0, 2},
	    {1, 0, 1, 1, 1, 2},
	    {2, 0, 2, 1, 2, 2},
	    {0, 0, 1, 0, 2, 0},
	    {0, 1, 1, 1, 2, 1},
	    {0, 2, 1, 2, 2, 2},
	    {0, 0, 1, 1, 2, 2},
	    {0, 2, 1, 1, 2, 0}
	};
	
	for (int i = 0; i < 3; i++) {
	    for(int j = 0; j < 3; j++){
		if(b[i][j]== Player.PLAYER_1) count++;
	    }
	}
	if(count == 1&&b[1][1]!=Player.PLAYER_1)return new Coordinate(1,1);
	if(count == 1&&b[1][1]==Player.PLAYER_1)return new Coordinate(0,0);
	
	for (int i = 0; i < 8; i++) {
	    Player p1 = b[(victory[i][0])][(victory[i][1])];
	    Player p2 = b[(victory[i][2])][(victory[i][3])];
	    Player p3 = b[(victory[i][4])][(victory[i][5])];
	    if(p1==Player.PLAYER_2&&p2==Player.PLAYER_2&&p3==null){
		return new Coordinate((victory[i][4]),(victory[i][5]));
	    }
	    else if(p1==Player.PLAYER_2&&p3==Player.PLAYER_2&&p2==null){
		return new Coordinate((victory[i][2]),(victory[i][3]));
	    }
	    else if(p2==Player.PLAYER_2&&p3==Player.PLAYER_2&&p1==null){
		return new Coordinate((victory[i][0]),(victory[i][1]));
	    }
	    
	}
	
	for (int i = 0; i < 8; i++) {
	    Player p1 = b[(victory[i][0])][(victory[i][1])];
	    Player p2 = b[(victory[i][2])][(victory[i][3])];
	    Player p3 = b[(victory[i][4])][(victory[i][5])];
	    if(p1==Player.PLAYER_1&&p2==Player.PLAYER_1&&p3==null){
		return new Coordinate((victory[i][4]),(victory[i][5]));
	    }
	    else if(p1==Player.PLAYER_1&&p3==Player.PLAYER_1&&p2==null){
		return new Coordinate((victory[i][2]),(victory[i][3]));
	    }
	    else if(p2==Player.PLAYER_1&&p3==Player.PLAYER_1&&p1==null){
		return new Coordinate((victory[i][0]),(victory[i][1]));
	    }
	    
	}
	for (int i = 0; i < 8; i++) {
	    Player p1 = b[(victory[i][0])][(victory[i][1])];
	    Player p2 = b[(victory[i][2])][(victory[i][3])];
	    Player p3 = b[(victory[i][4])][(victory[i][5])];
	    
	    if(p1==Player.PLAYER_1&&p2==null&&p3==null){
		return new Coordinate((victory[i][2]),(victory[i][3]));
	    }
	    if(p2==Player.PLAYER_1&&p1==null&&p3==null){
		return new Coordinate((victory[i][0]),(victory[i][1]));
	    }
	    if(p3==Player.PLAYER_1&&p1==null&&p2==null){
		return new Coordinate((victory[i][2]),(victory[i][3]));
	    }
	    
	}
	return null;
    }
    
    @Override
    public void stop() {
	Platform.runLater(new Runnable() {
	    @Override
	    public void run() {
		if(isActivated && computerTask != null && computerTask.isRunning()) {
		    computerTask.cancel();
		}
	    }
	});
    }
    
    private void fireComputerPlayed(Coordinate coord) {
	for(ComputerListener cl : this.listeners) {
	    cl.computerPlayed(coord);
	}
    }

    @Override
    public boolean addListener(ComputerListener listener) {
	return this.listeners.add(listener);
    }

    @Override
    public boolean removeListener(ComputerListener listener) {
	return this.listeners.remove(listener);
    }

    @Override
    public void gameRestarted(Player player) {
	//Ne rien faire
    }
}
