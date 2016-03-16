package com.multimodaltictactoe.gui.utils.switchbutton;

import com.multimodaltictactoe.utils.Constants;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;

public class SwitchButton extends Button {
    private final SimpleBooleanProperty on;
    
    public SwitchButton(String label, SimpleBooleanProperty on) {
	super(label);
	this.on = on;
	this.setGraphic(new LightCircle(Constants.Gui.Sizes.LIGHT_RADIUS_BUTTON, on));
    }
    
    @Override
    public void fire() {
	on.setValue(!on.getValue());
	super.fire();
    }
}
