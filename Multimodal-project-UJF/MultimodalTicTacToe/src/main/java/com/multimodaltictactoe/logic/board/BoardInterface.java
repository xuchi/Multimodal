package com.multimodaltictactoe.logic.board;

import com.multimodaltictactoe.logic.utils.Coordinate;
import com.multimodaltictactoe.logic.utils.Player;

public interface BoardInterface {
    public boolean addListener(BoardListener listener);
    public boolean removeListener(BoardListener listener);
    public Player get(Coordinate coord);
    public void set(Coordinate coord, Player player);
    public void clear();
    public Player[][] getCopy();
    public void fireInitEvents();
}
