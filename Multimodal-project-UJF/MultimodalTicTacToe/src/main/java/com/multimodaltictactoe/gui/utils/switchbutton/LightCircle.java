package com.multimodaltictactoe.gui.utils.switchbutton;

import com.multimodaltictactoe.utils.Constants;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class LightCircle extends Circle {
    
    public LightCircle(double radius, SimpleBooleanProperty on) {
	super(radius);
	switchLight(on.getValue());
	on.addListener(new ChangeListener<Boolean>() {
	    @Override
	    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		switchLight(newValue);
	    }
	});
	this.setStrokeWidth(Constants.Gui.Sizes.LIGHT_STROKE_BUTTON);
	this.setStroke(Constants.Gui.Colors.COLOR_STROKE_LIGHT_BUTTON);
    }
    
    private void switchLight(boolean on) {
	Color mainColor;
	if(on) {
	    mainColor = Constants.Gui.Colors.COLOR_ICON_MICRO_ON;
	}
	else {
	    mainColor = Constants.Gui.Colors.COLOR_ICON_MICRO_OFF;
	}
	this.setFill(mainColor);
    }
    
}
