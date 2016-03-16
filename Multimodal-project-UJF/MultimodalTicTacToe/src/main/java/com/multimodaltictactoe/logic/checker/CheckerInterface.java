package com.multimodaltictactoe.logic.checker;

public interface CheckerInterface {
    public boolean addListener(CheckerListener listener);
    public boolean removeListener(CheckerListener listener);
}
