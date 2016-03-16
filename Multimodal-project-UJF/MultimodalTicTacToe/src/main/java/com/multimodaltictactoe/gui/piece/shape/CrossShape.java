package com.multimodaltictactoe.gui.piece.shape;

import java.util.Arrays;
import java.util.List;
import javafx.beans.binding.NumberBinding;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;

public class CrossShape extends MultiShape {
    public CrossShape(NumberBinding size, NumberBinding strokeWidth) {
	super(size, strokeWidth);
    }

    @Override
    protected List<Shape> createShapes(NumberBinding size, NumberBinding strokeWidth) {
	NumberBinding gap = size.divide(Math.sqrt(8.0));
	
	Line line1 = new Line();
	line1.setFill(Color.TRANSPARENT);
	line1.setStroke(Color.TRANSPARENT);
	line1.setStrokeLineCap(StrokeLineCap.ROUND);
	line1.strokeWidthProperty().bind(strokeWidth);
	line1.startXProperty().bind(gap.negate());
	line1.startYProperty().bind(gap.negate());
	line1.endXProperty().bind(gap);
	line1.endYProperty().bind(gap);
	
	Line line2 = new Line();
	line2.setFill(Color.TRANSPARENT);
	line2.setStroke(Color.TRANSPARENT);
	line2.setStrokeLineCap(StrokeLineCap.ROUND);
	line2.strokeWidthProperty().bind(strokeWidth);
	line2.startXProperty().bind(gap);
	line2.startYProperty().bind(gap.negate());
	line2.endXProperty().bind(gap.negate());
	line2.endYProperty().bind(gap);
	
	return Arrays.asList(line1, line2);
    }
    
}
