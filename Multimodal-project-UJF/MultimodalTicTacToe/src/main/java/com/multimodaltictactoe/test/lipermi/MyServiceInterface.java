package com.multimodaltictactoe.test.lipermi;

import java.io.Serializable;


public interface MyServiceInterface extends Serializable {
    
    public void sendMessage();
    
    public void addListener(MyListener l);
    
}
