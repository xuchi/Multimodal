package com.multimodaltictactoe.gui.piece.shape;

import java.util.List;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.ObjectProperty;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

public abstract class MultiShape {
    private final List<Shape> shapes;
    
    protected abstract List<Shape> createShapes(NumberBinding size, NumberBinding strokeWidth);
    
    public MultiShape(NumberBinding size, NumberBinding strokeWidth) {
	this.shapes = createShapes(size, strokeWidth);
	bindProperties();
    }
    
    private void bindProperties() {
	ObjectProperty<Paint> reference = this.shapes.get(0).strokeProperty();
	for(int i = 1; i < this.shapes.size(); i++) {
	    this.shapes.get(i).strokeProperty().bind(reference);
	}
    }
    
    public List<Shape> getShapes() {
	return this.shapes;
    }
    
    public void setStroke(Paint paint) {
	this.shapes.get(0).setStroke(paint);
    }
    
    public Paint getStroke() {
	return this.shapes.get(0).getStroke();
    }
    
    public ObjectProperty<Paint> strokeProperty() {
	return this.shapes.get(0).strokeProperty();
    }
}
