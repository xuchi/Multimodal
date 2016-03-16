package com.multimodaltictactoe.logic.controler;

import com.multimodaltictactoe.logic.utils.GameStatus;
import com.multimodaltictactoe.logic.utils.Player;

public interface ControlerInterface {
    public boolean addListener(ControlerListener listener);
    public boolean removeListener(ControlerListener listener);
    public void restart();
    public void quit();
    public Player getActualPlayer();
    public GameStatus getActualStatus();
    public Player getActualStatusPlayer();
}
