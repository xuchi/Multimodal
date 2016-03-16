package com.multimodaltictactoe.logic.computer;

public interface ComputerInterface {
    public boolean addListener(ComputerListener listener);
    public boolean removeListener(ComputerListener listener);
    public void stop();
}
