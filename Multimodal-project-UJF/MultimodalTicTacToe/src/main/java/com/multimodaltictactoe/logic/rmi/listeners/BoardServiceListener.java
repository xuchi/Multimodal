package com.multimodaltictactoe.logic.rmi.listeners;

import com.multimodaltictactoe.logic.utils.Coordinate;
import com.multimodaltictactoe.logic.utils.Player;
import java.io.Serializable;

public interface BoardServiceListener extends Serializable {
    public void pieceChanged(Coordinate coord, Player[][] board);
    public void boardCleared();
}
