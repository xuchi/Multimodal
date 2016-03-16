package com.multimodaltictactoe.logic.rmi.service;

import com.multimodaltictactoe.logic.rmi.listeners.BoardServiceListener;
import com.multimodaltictactoe.logic.utils.Coordinate;
import com.multimodaltictactoe.logic.utils.Player;
import java.io.Serializable;

public interface BoardServiceInterface extends Serializable {
    public int addListener(BoardServiceListener listener);
    public boolean removeListener(int id);
    public void set(Coordinate coord, Player player);
    public void clear();
    public String getBoard();
}
