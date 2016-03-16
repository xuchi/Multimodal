package com.multimodaltictactoe.logic.speech;

import com.multimodaltictactoe.logic.utils.Coordinate;
import java.util.ArrayList;
import java.util.List;

public abstract class SpeechRecognizer {
    private final List<SpeechRecognizerListener> listeners = new ArrayList<>();
    
    public abstract void listen();
    
    public abstract void stop();
    
    public abstract void close();
    
    public boolean addListener(SpeechRecognizerListener srl) {
	if(this.listeners.contains(srl)) {
	    return false;
	}
	else return this.listeners.add(srl);
    }
    
    public boolean removeListener(SpeechRecognizerListener srl) {
	return this.listeners.remove(srl);
    }
    
    protected void fireResult(Coordinate coord) {
	for(SpeechRecognizerListener srl : listeners) {
	    srl.onResult(coord);
	}
    }
    
    
}
