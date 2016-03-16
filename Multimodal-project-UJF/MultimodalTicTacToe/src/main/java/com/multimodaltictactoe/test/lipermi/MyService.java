package com.multimodaltictactoe.test.lipermi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MyService implements MyServiceInterface {
    private final List<MyListener> list;
    
    public MyService() {
	this.list = new ArrayList<>();
    }

    @Override
    public void sendMessage() {
	Random r = new Random();
	int i = 1;
	for(MyListener l : list) {
	    l.messageReceived("Message (" + r.nextInt() + ") : " + i);
	    i++;
	}
    }

    @Override
    public void addListener(MyListener l) {
	this.list.add(l);
    }
    
}
