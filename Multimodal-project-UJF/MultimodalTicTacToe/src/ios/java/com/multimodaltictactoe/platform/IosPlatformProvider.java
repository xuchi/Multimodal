package com.multimodaltictactoe.platform;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import com.gluonhq.charm.down.common.PlatformFactory;
import com.gluonhq.charm.down.common.Platform.LifecycleEvent;
import com.multimodaltictactoe.logic.speech.SpeechRecognizer;

public class IosPlatformProvider implements PlatformProvider {
    private final BooleanProperty stop = new SimpleBooleanProperty();
    private final BooleanProperty pause = new SimpleBooleanProperty();
    
    {
	PlatformFactory.getPlatform().setOnLifecycleEvent((LifecycleEvent param) -> {
	    switch (param) {
		case START:
		    pause.set(false);
		    stop.set(false);
		    break;
		case PAUSE:
		    pause.set(true);
		    break;
		case RESUME:
		    pause.set(false);
		    stop.set(false);
		    break;
		case STOP:
		    stop.set(true);
		    break;
	    }
	    return null;
	});
    }
    
    @Override
    public ObservableList<Image> getIcons() {
	return FXCollections.<Image>observableArrayList();
    }
    
    @Override
    public BooleanProperty stopProperty() {
	return stop;
    }
    
    @Override
    public BooleanProperty pauseProperty() {
	return pause;
    }

    @Override
    public SpeechRecognizer getSpeechRecognizer() {
	throw new UnsupportedOperationException("Not supported yet.");
    }
}
