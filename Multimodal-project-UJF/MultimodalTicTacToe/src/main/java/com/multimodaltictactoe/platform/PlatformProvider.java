package com.multimodaltictactoe.platform;

import com.multimodaltictactoe.logic.speech.SpeechRecognizer;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public interface PlatformProvider {
    public ObservableList<Image> getIcons();
    public BooleanProperty stopProperty();
    public BooleanProperty pauseProperty();
    
    public SpeechRecognizer getSpeechRecognizer();
}
