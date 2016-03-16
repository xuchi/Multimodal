package com.multimodaltictactoe.logic.board;

import com.multimodaltictactoe.logic.utils.Coordinate;
import com.multimodaltictactoe.logic.utils.Player;
import java.io.Serializable;

public interface BoardListener extends Serializable {
    public void pieceChanged(Coordinate coord, Player[][] board);
    public void boardCleared();
}
