package com.multimodaltictactoe.gui.piece.shape;

import java.util.Arrays;
import java.util.List;
import javafx.beans.binding.NumberBinding;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

public class CircleShape extends MultiShape {
    public CircleShape(NumberBinding size, NumberBinding strokeWidth) {
	super(size, strokeWidth);
    }
    
    @Override
    protected List<Shape> createShapes(NumberBinding size, NumberBinding strokeWidth) {
	Circle circle = new Circle();
	circle.setFill(Color.TRANSPARENT);
	circle.setStroke(Color.TRANSPARENT);
	circle.setStrokeType(StrokeType.INSIDE);
	circle.strokeWidthProperty().bind(strokeWidth);
	circle.radiusProperty().bind(size.divide(2.0));
	return Arrays.asList(circle);
    }
}
