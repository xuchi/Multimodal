package com.multimodaltictactoe.gui.utils;

import javafx.scene.Group;

/**
 * Groupe redimensionnable.
 * Permet à un groupe de se redimensionner si il n'a pas assez de place.
 */
public class ResizableGroup extends Group {
    public ResizableGroup() {
	super();
    }
    
    @Override
    public double minWidth(double height) {
	return 0.0;
    }
    
    @Override
    public double minHeight(double height) {
	return 0.0;
    }
}
