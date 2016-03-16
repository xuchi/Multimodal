package com.multimodaltictactoe.test.platform;

import com.gluonhq.charm.down.common.PlatformFactory;
import com.multimodaltictactoe.platform.PlatformService;
import com.multimodaltictactoe.gui.utils.ApplicationUtils;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PlatformTest extends Application {
    @Override
    public void init() {
    }
    
    @Override
    public void start(Stage primaryStage) {
	Label label = new Label("Cliquez pour afficher la plateforme");
	label.setTranslateY(30);
	
	Button button = new Button("Afficher la plateforme");
	button.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		label.setText("Plateforme : " + PlatformFactory.getPlatform().getName());
	    }
	});
	
	StackPane root = new StackPane();
	root.getChildren().addAll(button, label);
	
	Scene scene = ApplicationUtils.createScene(root);
	ApplicationUtils.configureStageSize(primaryStage, 300, 300, 500, 500);
	
	primaryStage.setTitle("Test plateforme");
	primaryStage.setScene(scene);
	primaryStage.getIcons().addAll(PlatformService.getInstance().getIcons());
	
	primaryStage.show();
    }
    
    public static void main(String[] args) {
	launch(args);
    }
}