package com.multimodaltictactoe.gui.utils.icon;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class IconButton extends Button {
    private final Label iconLabel;
    
    public IconButton(String label, FontAwesomeIcon icon) {
	super(label);
	Font iconFont = Font.font(icon.getFontFamily(), super.getFont().getSize());
	this.iconLabel = new Label(icon.characterToString());
	this.iconLabel.setFont(iconFont);
	super.setGraphic(this.iconLabel);
    }
    
    public void setLabelSize(double size) {
	this.setFont(Font.font(this.getFont().getFamily(), size));
    }
    
    public void setIconSize(double size) {
	this.iconLabel.setFont(Font.font(this.iconLabel.getFont().getFamily(), size));
    }
    
    public void setIcon(FontAwesomeIcon icon) {
	this.iconLabel.setText(icon.characterToString());
    }
    
    public void setIconColor(Color color) {
	this.iconLabel.setTextFill(color);
    }
    
    
}
