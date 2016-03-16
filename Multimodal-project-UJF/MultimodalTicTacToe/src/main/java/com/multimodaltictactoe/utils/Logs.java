package com.multimodaltictactoe.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Logs {
    public static Logger getLogger(Class clazz) {
	Logger logger = Logger.getLogger(clazz.getName());
	logger.setUseParentHandlers(false);
	MyFormatter formatter = new MyFormatter();
	ConsoleHandler handler = new ConsoleHandler();
	handler.setFormatter(formatter);
	logger.addHandler(handler);
	return logger;
    }
    
    public static class MyFormatter extends Formatter {
	private final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
	
	@Override
	public String format(LogRecord record) {
	    StringBuilder builder = new StringBuilder(1000);
	    builder.append(df.format(new Date(record.getMillis()))).append(" - ");
	    builder.append("[").append(record.getSourceClassName()).append(".");
	    builder.append(record.getSourceMethodName()).append("] - ");
	    builder.append("[").append(record.getLevel()).append("] - ");
	    builder.append(formatMessage(record));
	    builder.append("\n");
	    return builder.toString();
	}
	
	@Override
	public String getHead(Handler h) {
	    return super.getHead(h);
	}
	
	@Override
	public String getTail(Handler h) {
	    return super.getTail(h);
	}
    }
}
