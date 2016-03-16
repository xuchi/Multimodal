package com.multimodaltictactoe.logic.speech;

import com.multimodaltictactoe.logic.utils.Coordinate;

public interface SpeechRecognizerListener {
    public void onResult(Coordinate coord);    
}
