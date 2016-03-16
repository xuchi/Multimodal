package com.multimodaltictactoe.test.screenAdapt;

import com.multimodaltictactoe.gui.utils.AdaptativePane;
import com.multimodaltictactoe.platform.PlatformService;
import com.multimodaltictactoe.gui.utils.ApplicationUtils;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ScreenAdaptTest extends Application {
    @Override
    public void init() {
    }
    
    @Override
    public void start(Stage primaryStage) {
	StackPane mainPane = new StackPane();
	mainPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
	
	Button b1 = new Button("Bouton 1");
	Button b2 = new Button("Bouton 2");
	b1.setMaxWidth(Double.MAX_VALUE);
	b2.setMaxWidth(Double.MAX_VALUE);
	b1.setPrefWidth(500);
	b2.setPrefWidth(500);
	HBox top = new HBox(30.0, b1, b2);
	top.setAlignment(Pos.CENTER);
	top.setPadding(new Insets(10.0));
	top.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));	
	
	BorderPane right = new BorderPane();
	Button ba = new Button("Bouton A");
	Button bb = new Button("Bouton B");
	ba.setPrefWidth(150.0);
	bb.setPrefWidth(150.0);
	ba.setMaxWidth(Double.MAX_VALUE);
	bb.setMaxWidth(Double.MAX_VALUE);
	VBox buttons = new VBox(30.0, ba, bb);
	buttons.setAlignment(Pos.CENTER);
	right.setPadding(new Insets(10.0));
	
	//buttons.setMaxWidth(300.0);
	right.setBottom(buttons);
	right.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
	
	AdaptativePane root = new AdaptativePane(mainPane, top, right);
	root.setPadding(new Insets(10.0));
	
	Scene scene = ApplicationUtils.createScene(root);
	ApplicationUtils.configureStageSize(primaryStage, 300, 300, 1000, 600);
	
	primaryStage.setTitle("Test adaptation ecran");
	primaryStage.setScene(scene);
	primaryStage.getIcons().addAll(PlatformService.getInstance().getIcons());
	
	primaryStage.show();
    }
    
    public static void main(String[] args) {
	launch(args);
    }
}