package com.multimodaltictactoe.logic.rmi.connector;

import com.multimodaltictactoe.logic.rmi.listeners.ConnectorListener;
import com.multimodaltictactoe.logic.utils.Coordinate;
import com.multimodaltictactoe.logic.utils.Player;

public interface ConnectorInterface {
    public Player[][] connect();
    public void disconnect();
    public boolean addListener(ConnectorListener cl);
    public boolean removeListener(ConnectorListener cl);
    public void setBoard(Coordinate coord, Player player);
    public void clearBoard();
    public ConnectionResult getConnectionResult();
}
