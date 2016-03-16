package com.multimodaltictactoe.platform;

import com.multimodaltictactoe.MultimodalTicTacToe;
import com.multimodaltictactoe.logic.speech.SpeechRecognizer;
import com.multimodaltictactoe.speech.DesktopSpeechRecognizer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public class DesktopPlatformProvider implements PlatformProvider {
    @Override
    public ObservableList<Image> getIcons() {
        return FXCollections.<Image>observableArrayList(
                new Image(MultimodalTicTacToe.class.getResourceAsStream("icon.png")));
    }

    @Override
    public BooleanProperty stopProperty() {
        return new SimpleBooleanProperty();
    }
    
    @Override
    public BooleanProperty pauseProperty() {
        return new SimpleBooleanProperty();
    }

    @Override
    public SpeechRecognizer getSpeechRecognizer() {
	return DesktopSpeechRecognizer.getInstance();
    }
}
