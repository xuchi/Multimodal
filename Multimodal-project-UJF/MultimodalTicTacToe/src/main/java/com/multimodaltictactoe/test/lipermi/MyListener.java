package com.multimodaltictactoe.test.lipermi;

import java.io.Serializable;


public interface MyListener extends Serializable {
    public void messageReceived(String message);
}
