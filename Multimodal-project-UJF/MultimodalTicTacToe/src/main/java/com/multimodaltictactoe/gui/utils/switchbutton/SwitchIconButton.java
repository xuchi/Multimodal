package com.multimodaltictactoe.gui.utils.switchbutton;

import com.multimodaltictactoe.gui.utils.icon.FontAwesomeIcon;
import com.multimodaltictactoe.gui.utils.icon.IconButton;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;

public class SwitchIconButton extends IconButton {
    private final SimpleBooleanProperty on;
    private final FontAwesomeIcon onIcon;
    private final FontAwesomeIcon offIcon;
    private Color onColor;
    private Color offColor;
    
    public SwitchIconButton(String label, FontAwesomeIcon onIcon, FontAwesomeIcon offIcon, SimpleBooleanProperty on) {
	super(label, on.getValue()?onIcon:offIcon);
	this.on = on;
	this.onIcon = onIcon;
	this.offIcon = offIcon;
	this.onColor = Color.BLACK;
	this.offColor = Color.BLACK;
	this.on.addListener(new ChangeListener<Boolean>() {
	    @Override
	    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		switchIcon(newValue);
	    }
	});
    }
    
    public void setIconsColors(Color onColor, Color offColor) {
	this.onColor = onColor;
	this.offColor = offColor;
	switchIcon(this.on.getValue());
    }
    
    private void switchIcon(boolean isOn) {
	if(isOn) {
	    this.setIcon(onIcon);
	    this.setIconColor(this.onColor);
	}
	else {
	    this.setIcon(offIcon);
	    this.setIconColor(this.offColor);
	}
    }
    
    @Override
    public void fire() {
	on.setValue(!on.getValue());
	super.fire();
    }
    
}
