package com.multimodaltictactoe.logic.controler;

import com.multimodaltictactoe.logic.utils.Player;

public interface ControlerListener {
    public void playerChanged(Player player);
    public void gameRestarted(Player player);
}
