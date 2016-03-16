package com.multimodaltictactoe.gui.utils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class AdaptativePane extends BorderPane {
    private static final double GOLDEN_RATIO = 1.6180;
    
    private final Node right;
    private final Node top;
    private Orientation orientation;
    
    public AdaptativePane(Node center, Node top, Node right) {
	super();
	this.right = right;
	this.top = top;
	this.setCenter(center);
	this.setRight(right);
	this.orientation = Orientation.HORIZONTAL;
	this.setEvents();	
    }
    
    private void setEvents() {
	this.widthProperty().addListener(new ChangeListener<Number>() {
	    @Override
	    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		resize();
	    }
	});
	this.heightProperty().addListener(new ChangeListener<Number>() {
	    @Override
	    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		resize();
	    }
	});
    }
    
    private void resize() {
	double width = this.getWidth();
	double height = this.getHeight();
	Orientation resizeOrientation;
	if(width >= height * GOLDEN_RATIO) {
	    resizeOrientation = Orientation.HORIZONTAL;
	}
	else {
	    resizeOrientation = Orientation.VERTICAL;
	}
	if(resizeOrientation != this.orientation) {
	    this.orientation = resizeOrientation;
	    switchOrientation();
	}
    }
    
    private void switchOrientation() {
	if(this.orientation == Orientation.HORIZONTAL) {
	    this.getChildren().remove(this.top);
	    this.setRight(this.right);
	}
	else {
	    this.getChildren().remove(this.right);
	    this.setTop(this.top);
	}	
    }
    
    
    private enum Orientation {
	HORIZONTAL,
	VERTICAL;
    }
}
