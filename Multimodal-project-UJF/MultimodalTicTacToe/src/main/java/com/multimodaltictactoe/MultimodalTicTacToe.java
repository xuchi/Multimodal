package com.multimodaltictactoe;

import com.gluonhq.charm.down.common.PlatformFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.multimodaltictactoe.gui.board.BoardGUI;
import com.multimodaltictactoe.gui.infos.InfoPieceTurnGUI;
import com.multimodaltictactoe.gui.infos.InfoTextTurnGUI;
import com.multimodaltictactoe.logic.board.Board;
import com.multimodaltictactoe.logic.checker.Checker;
import com.multimodaltictactoe.logic.computer.Computer;
import com.multimodaltictactoe.logic.controler.Controler;
import com.multimodaltictactoe.logic.rmi.connector.ConnectionResult;
import com.multimodaltictactoe.logic.rmi.connector.Connector;
import com.multimodaltictactoe.platform.PlatformService;
import com.multimodaltictactoe.gui.utils.AdaptativePane;
import com.multimodaltictactoe.gui.utils.ApplicationUtils;
import com.multimodaltictactoe.gui.utils.ButtonUtils;
import com.multimodaltictactoe.gui.utils.icon.IconButton;
import com.multimodaltictactoe.gui.utils.switchbutton.SwitchIconButton;
import com.multimodaltictactoe.logic.speech.SpeechRecognizer;
import com.multimodaltictactoe.logic.utils.Coordinate;
import com.multimodaltictactoe.utils.Constants;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;

public class MultimodalTicTacToe extends Application {
    private Board board;
    private Checker checker;
    private Controler controler;
    private Computer computer;
    private Connector connector;
    private SpeechRecognizer speechRecognizer;
    
    private BoardGUI boardGUI;
    
    private SimpleBooleanProperty microIsOn;
    
    @Override
    public void init() {
	if(Constants.Logic.Others.DEMO_MODE) {
	    System.out.println("===============================");
	    System.out.println("========== DEMO MODE ==========");
	    System.out.println("===============================");
	}
	//=== Logique ===
	connector = new Connector();
	board = new Board(connector.connect());
	checker = new Checker();
	boardGUI = new BoardGUI();
	computer = new Computer(board, connector.getConnectionResult() != ConnectionResult.GET);
	controler = new Controler(board, computer, connector);
	speechRecognizer = PlatformService.getInstance().getSpeechRecognizer();
	
	board.addListener(boardGUI);
	board.addListener(checker);
	checker.addListener(boardGUI);
	checker.addListener(controler);
	boardGUI.addListener(controler);
	computer.addListener(controler);
	controler.addListener(computer);
	connector.addListener(controler);
	speechRecognizer.addListener(controler);
	
	//=== Proprietes ===
	microIsOn = new SimpleBooleanProperty(false);
	microIsOn.addListener(new ChangeListener<Boolean>() {
	    @Override
	    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		switchSpeechRecognition(newValue);
	    }
	});
	
	board.fireInitEvents();
    }
    
    private Button createButtonRestart(boolean isTopButton) {
	IconButton buttonRestart = new IconButton(Constants.Gui.Buttons.LABEL_BUTTON_RESTART, Constants.Gui.Buttons.ICON_BUTTON_RESTART);
	ButtonUtils.paramButton(buttonRestart, isTopButton);
	buttonRestart.setIconColor(Constants.Gui.Colors.COLOR_ICON_QUIT_RESTART);
	buttonRestart.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		restart();
	    }
	});
	return buttonRestart;
    }
    
    private Button createButtonQuit(boolean isTopButton) {
	IconButton buttonQuit = new IconButton(Constants.Gui.Buttons.LABEL_BUTTON_QUIT, Constants.Gui.Buttons.ICON_BUTTON_QUIT);
	ButtonUtils.paramButton(buttonQuit, isTopButton);
	buttonQuit.setIconColor(Constants.Gui.Colors.COLOR_ICON_QUIT_RESTART);
	buttonQuit.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		quit();
		Platform.exit();
	    }
	});
	return buttonQuit;
    }
    
    private Button createButtonMicro(boolean isTopButton) {
	SwitchIconButton buttonMicro = new SwitchIconButton(Constants.Gui.Buttons.LABEL_BUTTON_MICRO,
		Constants.Gui.Buttons.ICON_BUTTON_MICRO_ON, Constants.Gui.Buttons.ICON_BUTTON_MICRO_OFF, microIsOn);
	ButtonUtils.paramButton(buttonMicro, isTopButton);
	buttonMicro.setIconsColors(Constants.Gui.Colors.COLOR_ICON_MICRO_ON, Constants.Gui.Colors.COLOR_ICON_MICRO_OFF);
	return buttonMicro;
    }
    
    private Pane createGamePane() {
	BorderPane gamePane = new BorderPane();
	gamePane.setBackground(Constants.Gui.Colors.BACKGROUND_APP);
	//=== Plateau ===
	gamePane.setCenter(boardGUI);
	//=== Info tour texte ===
	StackPane infoTextTurnGUIPane = new StackPane();
	infoTextTurnGUIPane.setPadding(new Insets(0.0, 0.0, Constants.Gui.Sizes.GAP_BOARD_INFOTEXT, 0.0));
	InfoTextTurnGUI infoTextTurnGUI = new InfoTextTurnGUI(controler.getActualPlayer(), controler.getActualStatus(), controler.getActualStatusPlayer());
	controler.addListener(infoTextTurnGUI);
	checker.addListener(infoTextTurnGUI);
	infoTextTurnGUIPane.getChildren().add(infoTextTurnGUI);
	gamePane.setTop(infoTextTurnGUIPane);	
	return gamePane;
    }
    
    private Pane createTopPane() {
	TilePane topPane = new TilePane(Orientation.HORIZONTAL);
	topPane.setAlignment(Pos.CENTER);
	topPane.setTileAlignment(Pos.CENTER);
	topPane.setHgap(Constants.Gui.Sizes.GAP_TOPBUTTONS);
	topPane.setVgap(Constants.Gui.Sizes.GAP_TOPBUTTONS);
	topPane.setPadding(new Insets(0.0, 0.0, Constants.Gui.Sizes.GAP_INFOTEXT_TOPPANE, 0.0));
	topPane.setBackground(Constants.Gui.Colors.BACKGROUND_APP);
	//=== Boutons ===
	Button buttonRestart = createButtonRestart(true);
	Button buttonQuit = createButtonQuit(true);
	Button buttonMicro = createButtonMicro(true);
	topPane.getChildren().addAll(buttonMicro, buttonRestart, buttonQuit);
	return topPane;
    }
    
    private Pane createRightPane() {
	BorderPane rightPane = new BorderPane();
	rightPane.setBackground(Constants.Gui.Colors.BACKGROUND_APP);
	//=== Boutons ===
	Button buttonRestart = createButtonRestart(false);
	Button buttonQuit = createButtonQuit(false);
	Button buttonMicro = createButtonMicro(false);
	VBox buttons = new VBox();
	buttons.setAlignment(Pos.CENTER);
	buttons.setSpacing(Constants.Gui.Sizes.GAP_RIGHTBUTTONS);
	buttons.getChildren().addAll(buttonMicro, buttonRestart, buttonQuit);	
	rightPane.setCenter(buttons);
	//=== Info tour piece ===
	StackPane infoPieceTurnGUIPane = new StackPane();
	NumberBinding pieceSize = rightPane.widthProperty().subtract(Constants.Gui.Sizes.MARGIN_AROUND_PIECE_RIGHT_PANE);
	NumberBinding strokeWidth = pieceSize.divide(Constants.Gui.Sizes.RATIO_CELL_STROKE_PIECE);
	InfoPieceTurnGUI infoPieceTurnGUI = new InfoPieceTurnGUI(pieceSize, strokeWidth, controler.getActualPlayer());
	controler.addListener(infoPieceTurnGUI);
	infoPieceTurnGUIPane.getChildren().add(infoPieceTurnGUI);
	rightPane.setTop(infoPieceTurnGUIPane);	
	return rightPane;
    }
    
    private void switchSpeechRecognition(boolean activate) {
	if(!Constants.Logic.Others.DEMO_MODE) {
	    if(activate) {
		speechRecognizer.listen();
	    }
	    else {
		speechRecognizer.stop();
	    }
	}
    }
    
    private void restart() {
	controler.restart();
    }
    
    private void quit() {
	controler.quit();
	speechRecognizer.stop();
	speechRecognizer.close();
    }
    
    @Override
    public void start(Stage primaryStage) {
	Pane gamePane = createGamePane();
	Pane topPane = createTopPane();
	Pane rightPane = createRightPane();
	
	AdaptativePane root = new AdaptativePane(gamePane, topPane, rightPane);
	root.setBackground(Constants.Gui.Colors.BACKGROUND_APP);
	root.setPadding(new Insets(Constants.Gui.Sizes.PADDING_APP));
	
	Scene scene = ApplicationUtils.createScene(root);
	scene.getStylesheets().add(MultimodalTicTacToe.class.getResource("constantes.css").toExternalForm());
	scene.getStylesheets().add(MultimodalTicTacToe.class.getResource("style.css").toExternalForm());
	activateKeyboardIfDemoMode(scene);
	if(PlatformFactory.getPlatform().getName().equals(PlatformFactory.DESKTOP)) {
	    scene.getStylesheets().add(MultimodalTicTacToe.class.getResource("styleDesktop.css").toExternalForm());
	}
	ApplicationUtils.configureStageSize(primaryStage, 300, 300, 1100, 700);

	primaryStage.setTitle(Constants.Gui.Strings.WINDOW_TITLE);
	primaryStage.setScene(scene);
	primaryStage.getIcons().addAll(PlatformService.getInstance().getIcons());
	primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	    @Override
	    public void handle(WindowEvent event) {
		quit();
	    }
	});
	primaryStage.show();
    }
    
    public static void main(String[] args) {
	launch(args);
    }
    
    private void activateKeyboardIfDemoMode(Scene scene) {
	if(Constants.Logic.Others.DEMO_MODE) {
	    scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent event) {
		    if(microIsOn.get()) {
			Coordinate coord = null;
			switch(event.getCode()) {
			    case NUMPAD1:
				coord = new Coordinate('A', 3);
				break;
			    case NUMPAD2:
				coord = new Coordinate('B', 3);
				break;
			    case NUMPAD3:
				coord = new Coordinate('C', 3);
				break;
			    case NUMPAD4:
				coord = new Coordinate('A', 2);
				break;
			    case NUMPAD5:
				coord = new Coordinate('B', 2);
				break;
			    case NUMPAD6:
				coord = new Coordinate('C', 2);
				break;
			    case NUMPAD7:
				coord = new Coordinate('A', 1);
				break;
			    case NUMPAD8:
				coord = new Coordinate('B', 1);
				break;
			    case NUMPAD9:
				coord = new Coordinate('C', 1);
				break;
			}
			controler.userClicked(coord);
		    }
		}
	    });
	}
    }
}