package com.multimodaltictactoe.gui.utils;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.value.ObservableNumberValue;

public class BindingUtils {
    
    public static NumberBinding sqrt(ObservableNumberValue onv) {
	return new DoubleBinding() {
	    {
		super.bind(onv);
	    }
	    @Override
	    protected double computeValue() {
		return Math.sqrt(onv.getValue().doubleValue());
	    }
	};
    }
    
    public static NumberBinding pow(ObservableNumberValue onv, double pow) {
	return new DoubleBinding() {
	    {
		super.bind(onv);
	    }
	    @Override
	    protected double computeValue() {
		return Math.pow(onv.getValue().doubleValue(), pow);
	    }
	};
    }
}
