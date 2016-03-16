package com.multimodaltictactoe.test.logic;


import com.multimodaltictactoe.logic.board.Board;
import com.multimodaltictactoe.gui.board.BoardGUI;
import com.multimodaltictactoe.logic.checker.Checker;
import com.multimodaltictactoe.logic.computer.Computer;
import com.multimodaltictactoe.logic.controler.Controler;
import com.multimodaltictactoe.logic.rmi.connector.ConnectionResult;
import com.multimodaltictactoe.logic.rmi.connector.Connector;
import com.multimodaltictactoe.platform.PlatformService;
import com.multimodaltictactoe.gui.utils.ApplicationUtils;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class TestLogic extends Application {
    private Board board;
    private BoardGUI boardGUI;
    private Checker checker;
    private Controler controler;
    private Computer computer;
    private Connector connector;
    
    @Override
    public void init() {
	connector = new Connector();
	board = new Board(connector.connect());
	checker = new Checker();
	boardGUI = new BoardGUI();
	computer = new Computer(board, connector.getConnectionResult() != ConnectionResult.GET);
	controler = new Controler(board, computer, connector);

	board.addListener(boardGUI);
	board.addListener(checker);
	checker.addListener(boardGUI);
	checker.addListener(controler);
	boardGUI.addListener(controler);
	computer.addListener(controler);
	controler.addListener(computer);
	connector.addListener(controler);
	
	board.fireInitEvents();	
    }
    
    @Override
    public void start(Stage primaryStage) {
	BorderPane root = new BorderPane();
	root.setPadding(new Insets(50.0));
	root.setCenter(boardGUI);
	
	Button bRestart = new Button("Restart");
	bRestart.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		controler.restart();
	    }
	});
	
	VBox buttons = new VBox(10.0);
	buttons.setPadding(new Insets(10.0));
	buttons.getChildren().addAll(bRestart);
	
	root.setRight(buttons);
	
	Scene scene = ApplicationUtils.createScene(root);
	ApplicationUtils.configureStageSize(primaryStage, 300, 300, 500, 500);
	
	primaryStage.setTitle("Test logic");
	primaryStage.setScene(scene);
	primaryStage.getIcons().addAll(PlatformService.getInstance().getIcons());
	primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	    @Override
	    public void handle(WindowEvent event) {
		onClose();
	    }
	});
	
	primaryStage.show();
    }
    
    public static void main(String[] args) {
	launch(args);
    }
    
    private void onClose() {
	connector.disconnect();
    }
    
}