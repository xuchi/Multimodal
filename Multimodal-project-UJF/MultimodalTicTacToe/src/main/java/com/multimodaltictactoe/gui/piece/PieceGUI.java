package com.multimodaltictactoe.gui.piece;

import com.multimodaltictactoe.gui.piece.shape.CrossShape;
import com.multimodaltictactoe.gui.piece.shape.MultiShape;
import com.multimodaltictactoe.gui.piece.shape.CircleShape;
import com.multimodaltictactoe.logic.utils.Player;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class PieceGUI extends Group {
    private final double transitionColorPieceTime;
    private final double opacityPieceHover;
    private final Color color1;
    private final Color color2;
    
    private final MultiShape shape1;
    private final MultiShape shape2;
    
    private State state;
    
    public PieceGUI(NumberBinding size, NumberBinding strokeWidth, double transitionColorPieceTime, double opacityPieceHover,
	    Color color1, Color color2) {
	super();
	this.transitionColorPieceTime = transitionColorPieceTime;
	this.opacityPieceHover = opacityPieceHover;
	this.color1 = color1;
	this.color2 = color2;

	NumberBinding pieceSize = size.subtract(strokeWidth.multiply(2));
	
	this.shape1 = new CircleShape(pieceSize, strokeWidth);
	this.shape2 = new CrossShape(pieceSize, strokeWidth);
	
	this.state = State.TRANSPARENT;
	
	List<Shape> shapes = new ArrayList<>(shape1.getShapes());
	shapes.addAll(shape2.getShapes());
	this.getChildren().addAll(shapes);
    }
    
    private void setColor(Color color, boolean animation, Player player) {
	Color endColor = color;
	if(animation) {
	    Timeline timeline = new Timeline();
	    Color startColor = (Color) getStroke(player);
	    if(startColor.getOpacity() == 0.0) {
		setStroke(Color.color(endColor.getRed(), endColor.getGreen(), endColor.getBlue(), 0.0), player);
	    }
	    KeyValue kv = new KeyValue(getStrokeProperty(player), endColor);
	    KeyFrame kf = new KeyFrame(new Duration(this.transitionColorPieceTime), kv);
	    timeline.getKeyFrames().add(kf);
	    timeline.play();
	}
	else {
	    setStroke(endColor, player);
	}
    }
    
    private void makeTransparent() {
	setStroke(Color.TRANSPARENT, Player.PLAYER_1);
	setStroke(Color.TRANSPARENT, Player.PLAYER_2);
    }
    
    public void setPlayer(Player player) {
	if(this.state != State.OPAQUE) {
	    this.makeTransparent();
	    this.setColor(toColor(player), true, player);
	    this.state = State.OPAQUE;
	}
    }
    
    public void hover(Player player) {
	if(this.state != State.OPAQUE && this.state != State.HOVER) {
	    Color hoverColor = toColor(player);
	    hoverColor = Color.color(hoverColor.getRed(), hoverColor.getGreen(), hoverColor.getBlue(), this.opacityPieceHover);
	    this.setColor(hoverColor, false, player);
	    this.state = State.HOVER;
	}	
    }
    
    public void unhover() {
	if(this.state == State.HOVER) {
	    this.makeTransparent();
	    this.state = State.TRANSPARENT;
	}	
    }
    
    public void clear() {
	this.makeTransparent();
	this.state = State.TRANSPARENT;
    }
    
    private Color toColor(Player player) {
	if(player == Player.PLAYER_1) {
	    return this.color1;
	}
	else if(player == Player.PLAYER_2) {
	    return this.color2;
	}
	else {
	    return Color.TRANSPARENT;
	}
    }
    
    @Override
    public double minWidth(double height) {
	return 0.0;
    }
    
    @Override
    public double minHeight(double height) {
	return 0.0;
    }
    
    @Override
    public boolean isResizable() {
	return true;
    }
    
    private void setStroke(Color color, Player player) {
	switch(player) {
	    case PLAYER_1:
		shape1.setStroke(color);
		break;
	    case PLAYER_2:
		shape2.setStroke(color);
		break;
	}
    }
    
    private Paint getStroke(Player player) {
	switch(player) {
	    case PLAYER_1:
		return shape1.getStroke();
	    case PLAYER_2:
		return shape2.getStroke();
	}
	return null;
    }
    
    private ObjectProperty<Paint> getStrokeProperty(Player player) {
	switch(player) {
	    case PLAYER_1:
		return shape1.strokeProperty();
	    case PLAYER_2:
		return shape2.strokeProperty();
	}
	return null;
    }
    
    private enum State {
	TRANSPARENT,
	HOVER,
	OPAQUE;
    }
}
