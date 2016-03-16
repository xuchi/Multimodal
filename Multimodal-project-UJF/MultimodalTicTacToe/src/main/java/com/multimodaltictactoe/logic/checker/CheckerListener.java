package com.multimodaltictactoe.logic.checker;

import com.multimodaltictactoe.logic.utils.GameStatus;
import com.multimodaltictactoe.logic.utils.Player;

public interface CheckerListener {
    public void statusChanged(GameStatus gameStatus, Player player);
}
