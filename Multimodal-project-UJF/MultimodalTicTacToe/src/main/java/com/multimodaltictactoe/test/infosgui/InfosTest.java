//package com.multimodaltictactoe.test.infosgui;
//
//import com.multimodaltictactoe.gui.infos.InfoTurnGUI;
//import com.multimodaltictactoe.platform.PlatformService;
//import com.multimodaltictactoe.utils.ApplicationUtils;
//import javafx.application.Application;
//import static javafx.application.Application.launch;
//import javafx.beans.property.BooleanProperty;
//import javafx.beans.property.SimpleBooleanProperty;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.BorderPane;
//import javafx.stage.Stage;
//
//public class InfosTest extends Application {
//    @Override
//    public void init() {
//    }
//    
//    @Override
//    public void start(Stage primaryStage) {
//	BorderPane root = new BorderPane();
//	
//	BooleanProperty isPlayer1TurnProperty = new SimpleBooleanProperty(true);
//	InfoTurnGUI itg = new InfoTurnGUI(isPlayer1TurnProperty);
//	
//	Button bChangeTurn = new Button("Changer tour");
//	bChangeTurn.setOnAction(new EventHandler<ActionEvent>() {
//	    @Override
//	    public void handle(ActionEvent event) {
//		isPlayer1TurnProperty.set(!isPlayer1TurnProperty.getValue());
//	    }
//	});
//	
//	root.setCenter(itg);
//	root.setBottom(bChangeTurn);
//	
//	
//	
//	
//
//	
//	Scene scene = ApplicationUtils.createScene(root);
//	ApplicationUtils.configureStageSize(primaryStage, 300, 300, 500, 500);
//	
//	primaryStage.setTitle("Test infos");
//	primaryStage.setScene(scene);
//	primaryStage.getIcons().addAll(PlatformService.getInstance().getIcons());
//	
//	primaryStage.show();
//
//    }
//    
//    public static void main(String[] args) {
//	launch(args);
//    }
//}