package com.multimodaltictactoe.test.lipermi2;

import com.multimodaltictactoe.logic.board.Board;
import com.multimodaltictactoe.logic.rmi.connector.Connector;
import com.multimodaltictactoe.logic.rmi.listeners.ConnectorListener;
import com.multimodaltictactoe.logic.utils.Coordinate;
import com.multimodaltictactoe.logic.utils.Player;
import com.multimodaltictactoe.platform.PlatformService;
import com.multimodaltictactoe.gui.utils.ApplicationUtils;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LipeRmi2Test extends Application {
    private Board board;
    private Connector connector;
    
    @Override
    public void init() {
	Player[][] b;
	connector = new Connector();
	b = connector.connect();
	board = new Board(b);
    }
    
    @Override
    public void start(Stage primaryStage) {
	StackPane root = new StackPane();
	
	Label lBoard = new Label(board.toString());

	Button bDeco = new Button("Déconnexion");
	bDeco.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		connector.disconnect();
	    }
	});
	
	TextField tfr = new TextField();
	TextField tfc = new TextField();
	Button bSet = new Button("SetP1");
	bSet.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		int r = Integer.parseInt(tfr.getText());
		int c = Integer.parseInt(tfc.getText());
		connector.setBoard(new Coordinate(r, c), Player.PLAYER_1);
	    }
	});
	HBox set = new HBox(10.0);
	set.getChildren().addAll(tfr, tfc, bSet);
	
	Button bClear = new Button("Clear");
	bClear.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		connector.clearBoard();
	    }
	});
	
	connector.addListener(new ConnectorListener() {
	    @Override
	    public void pieceChanged(Coordinate coord, Player[][] b) {
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
			board.set(coord, b[coord.getTabRow()][coord.getTabColumn()]);
			lBoard.setText(board.toString());
		    }
		});
	    }
	    @Override
	    public void boardCleared() {
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
			board.clear();
			lBoard.setText(board.toString());
		    }
		});
	    }
	});
	
	VBox components = new VBox(10.0);
	components.getChildren().addAll(lBoard, bDeco, set, bClear);
	root.getChildren().add(components);
	
	Scene scene = ApplicationUtils.createScene(root);
	ApplicationUtils.configureStageSize(primaryStage, 300, 300, 500, 500);
	
	primaryStage.setTitle("Test Lipermi 2");
	primaryStage.setScene(scene);
	primaryStage.getIcons().addAll(PlatformService.getInstance().getIcons());
	
	primaryStage.show();
    }
    
    public static void main(String[] args) {
	launch(args);
    }
}
