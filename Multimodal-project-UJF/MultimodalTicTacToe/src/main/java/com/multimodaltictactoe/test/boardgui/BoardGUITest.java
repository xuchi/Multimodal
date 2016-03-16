//package com.multimodaltictactoe.test.boardgui;
//
//import com.multimodaltictactoe.gui.board.BoardGUI;
//import com.multimodaltictactoe.gui.board.BoardGUIListener;
//import com.multimodaltictactoe.logic.board.BoardInterface;
//import com.multimodaltictactoe.logic.board.BoardListener;
//import com.multimodaltictactoe.logic.utils.Coordinate;
//import com.multimodaltictactoe.logic.utils.GameStatus;
//import com.multimodaltictactoe.logic.utils.Player;
//import com.multimodaltictactoe.platform.PlatformService;
//import com.multimodaltictactoe.utils.ApplicationUtils;
//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//public class BoardGUITest extends Application {
//    private BoardGUI bGUI;
//    
//    @Override
//    public void init() {
//    }
//    
//    @Override
//    public void start(Stage primaryStage) {
//	FakeBoard fBoard = new FakeBoard();
//	this.bGUI = new BoardGUI();
//	fBoard.addListener(bGUI);
//	bGUI.addListener(fBoard);
//	
//	BorderPane root = new BorderPane(this.bGUI);
//	root.setPadding(new Insets(50));
//	
//	Button bVictory = new Button("Victoire");
//	bVictory.setOnAction(new EventHandler<ActionEvent>() {
//	    @Override
//	    public void handle(ActionEvent event) {
//		fireVictory();
//	    }
//	});
//	
//	Button bClear = new Button("Clear");
//	bClear.setOnAction(new EventHandler<ActionEvent>() {
//	    @Override
//	    public void handle(ActionEvent event) {
//		bGUI.clear();
//	    }
//	});
//	
//	VBox buttons = new VBox(bVictory, bClear);
//	buttons.setPadding(new Insets(20.0));
//	root.setRight(buttons);
//	
//	Scene scene = ApplicationUtils.createScene(root);
//	ApplicationUtils.configureStageSize(primaryStage, 300, 300, 500, 500);
//	
//	primaryStage.setTitle("Test board GUI");
//	primaryStage.setScene(scene);
//	primaryStage.getIcons().addAll(PlatformService.getInstance().getIcons());
//	
//	primaryStage.show();
//    }
//    
//    public static void main(String[] args) {
//	launch(args);
//    }
//    
//    private void fireVictory() {
//	GameStatus gs = GameStatus.VICTORY;
//	gs.setCoordinates(new Coordinate('A', 1), new Coordinate('C', 3));
//	this.bGUI.gameEnded(gs, Player.PLAYER_1);
//    }
//    
//    public class FakeBoard implements BoardInterface, BoardGUIListener {
//	private BoardListener bl;
//	private Player acPlayer = Player.PLAYER_1;
//	
//	private final Player board[][] = {
//	    {null, null, null},
//	    {null, null, null},
//	    {null, null, null}
//	};
//	
//	public FakeBoard() {
//	    this.bl = null;
//	}
//	
//	@Override
//	public Player getPlayerAt(Coordinate coord) {
//	    return this.board[coord.getTabRow()][coord.getTabColumn()];
//	}
//	
//	@Override
//	public boolean addListener(BoardListener bl) {
//	    this.bl = bl;
//	    return true;
//	}
//	
//	@Override
//	public boolean removeListener(BoardListener bl) {
//	    if(this.bl == bl) this.bl = null;
//	    return true;
//	}
//
//	@Override
//	public void play(Player player, Coordinate coord) {
//	    this.board[coord.getTabRow()][coord.getTabColumn()] = player;
//	    if(this.bl != null) this.bl.playerPlayed(player, coord);
//	}
//
//	@Override
//	public Player[][] getBoard() {
//	    return this.board;
//	}
//
//	@Override
//	public void clic(Coordinate coord) {
//	    play(acPlayer, coord);
//	    if(acPlayer == Player.PLAYER_1) acPlayer = Player.PLAYER_2;
//	    else acPlayer = Player.PLAYER_1;
//	}
//    }
//}
