package com.multimodaltictactoe.gui.utils;

import com.gluonhq.charm.down.common.PlatformFactory;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ApplicationUtils {
    public static Scene createScene(Parent root) {
	Scene scene;
	if(PlatformFactory.getPlatform().getName().equals(PlatformFactory.ANDROID)){
	    Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
	    scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
	} else {
	    scene = new Scene(root);
	}
	if (Platform.isSupported(ConditionalFeature.INPUT_TOUCH)) {
	    scene.setCursor(Cursor.NONE);
	}
	scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
	    @Override
	    public void handle(KeyEvent event) {
		KeyCode kc = event.getCode();
		if(kc.equals(KeyCode.ESCAPE)) {
		    PlatformFactory.getPlatform().finish();
		}
	    }
	});
	return scene;
    }
    
    public static void configureStageSize(Stage primaryStage, int minWidthForDesktop, int minHeightForDesktop,
	    int widthForDesktop, int heightForDesktop) {
	if (PlatformFactory.getPlatform().getName().equals(PlatformFactory.DESKTOP) && isARMDevice()) {
	    primaryStage.setFullScreen(true);
	    primaryStage.setFullScreenExitHint("");
	}
	if(PlatformFactory.getPlatform().getName().equals(PlatformFactory.DESKTOP)){
	    primaryStage.setMinWidth(minWidthForDesktop);
	    primaryStage.setMinHeight(minHeightForDesktop);
	    primaryStage.setWidth(widthForDesktop);
	    primaryStage.setHeight(heightForDesktop);
	}
    }
    
    public static Color setOpacity(Color color, double opacity) {
	return Color.color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
    }
    
    private static boolean isARMDevice() {
	return System.getProperty("os.arch").toUpperCase().contains("ARM");
    }    
}
