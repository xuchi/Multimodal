package com.multimodaltictactoe.test.lipermi;

import com.multimodaltictactoe.logic.board.BoardListener;
import com.multimodaltictactoe.logic.rmi.listeners.ConnectorListener;
import com.multimodaltictactoe.logic.rmi.service.BoardService;
import com.multimodaltictactoe.logic.rmi.service.BoardServiceInterface;
import com.multimodaltictactoe.logic.rmi.listeners.BoardServiceListener;
import com.multimodaltictactoe.logic.utils.Coordinate;
import com.multimodaltictactoe.logic.utils.Player;
import com.multimodaltictactoe.platform.PlatformService;
import com.multimodaltictactoe.gui.utils.ApplicationUtils;
import com.multimodaltictactoe.utils.Constants;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.sf.lipermi.exception.LipeRMIException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;
import net.sf.lipermi.net.Server;

public class LipeRmiTest extends Application {
    private static final String IP = Constants.Logic.Others.IP;
    private static final int PORT = 1234;
    
    private CallHandler callHandler;
    private Server server;
    private Client client;
    
    private TextField tf;
    private Player p = Player.PLAYER_2;
    
    private int id = -1;
    
    private static String getIp() {
	try {
	    return InetAddress.getLocalHost().getHostAddress();
	} catch (UnknownHostException ex) {
	    return "";
	}
    }
    
    @Override
    public void init() {	
    }
    
    @Override
    public void start(Stage primaryStage) {
	this.callHandler = new CallHandler();
	this.server = null;
	this.client = null;
	
	tf = new TextField(IP);
	
	Button bCreate = new Button("Creer des données RMI");
	Label lCreate = new Label("...");
	bCreate.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		createRMI(lCreate);
	    }
	});
	
	Button bGet = new Button("Récupérer des données RMI");
	Label lGet = new Label("...");
	bGet.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		getRMI(lGet);
	    }
	});
	
	Button bSub = new Button("Abonnement");
	Label lSub = new Label(getIp() + "...");
	Label lMess = new Label("...");
	bSub.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		subscribe(lSub, lMess);
	    }
	});
	
	Button bUnsub = new Button("Désabonnement");
	Label lUnsub = new Label("...");
	bUnsub.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		unsubscribe(lUnsub);
	    }
	});
	
	Button bMess = new Button("Message");
	bMess.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		sendMessage();
	    }
	});
	
	VBox vbox = new VBox(tf, bCreate, lCreate, bGet, lGet, bSub, lSub, bUnsub, lUnsub, bMess, lMess);
	vbox.setAlignment(Pos.CENTER);
	
	Scene scene = ApplicationUtils.createScene(vbox);
	ApplicationUtils.configureStageSize(primaryStage, 200, 400, 200, 400);
	
	primaryStage.setTitle("Test Lipe RMI");
	primaryStage.setScene(scene);
	primaryStage.getIcons().addAll(PlatformService.getInstance().getIcons());
	
	primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	    @Override
	    public void handle(WindowEvent event) {
		if(server != null) {
		    System.out.println("Fermeture du serveur");
		    server.close();
		}
	    }
	});
	
	primaryStage.show();
    }
    
    public static void main(String[] args) {
	launch(args);
    }
    
    private void createRMI(Label label) {
	try {
	    BoardServiceInterface bsi = new BoardService();
	    callHandler.registerGlobal(BoardServiceInterface.class, bsi);
	    server = new Server();
	    server.bind(PORT, callHandler);
	    label.setText("Service créé");
	} catch (LipeRMIException | IOException ex) {
	    Logger.getLogger(LipeRmiTest.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    private void getRMI(Label label) {
	try {
	    client = new Client(tf.getText(), PORT, callHandler);
	    BoardServiceInterface bsi = (BoardServiceInterface) client.getGlobal(BoardServiceInterface.class);
	    label.setText("Service ok");
	} catch (IOException ex) {
	    Logger.getLogger(LipeRmiTest.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    private void subscribe(Label lAbo, Label lMess) {
	try {
	    client = new Client(tf.getText(), PORT, callHandler);
	    BoardServiceInterface bsi = (BoardServiceInterface) client.getGlobal(BoardServiceInterface.class);
	    BoardServiceListener listener = new BoardServiceListener() {
		@Override
		public void pieceChanged(Coordinate coord, Player[][] board) {
		    System.out.println("piece changed : " + coord);
		    Platform.runLater(new Runnable() {
			@Override
			public void run() {
			    lMess.setText(lMess.getText() + "\n" + "pieceChanged " + coord);
			}
		    });
		}

		@Override
		public void boardCleared() {
		    System.out.println("board cleared");
		    Platform.runLater(new Runnable() {
			@Override
			public void run() {
			    lMess.setText(lMess.getText() + "\n" + "boardCleared");
			}
		    });
		}
	    };
	    callHandler.exportObject(BoardServiceListener.class, listener);
	    this.id = bsi.addListener(listener);
	    lAbo.setText("Ok");
	} catch (IOException | LipeRMIException ex) {
	    Logger.getLogger(LipeRmiTest.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    private void unsubscribe(Label lAbo) {
	try {
	    client = new Client(tf.getText(), PORT, callHandler);
	    BoardServiceInterface bsi = (BoardServiceInterface) client.getGlobal(BoardServiceInterface.class);
	    bsi.removeListener(this.id);
	    lAbo.setText("Ok");
	} catch (IOException ex) {
	    Logger.getLogger(LipeRmiTest.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    private void sendMessage() {
	try {
	    client = new Client(tf.getText(), PORT, callHandler);
	    BoardServiceInterface bsi = (BoardServiceInterface) client.getGlobal(BoardServiceInterface.class);
	    bsi.set(new Coordinate(0, 0), p);
	    if(p == Player.PLAYER_1) p = Player.PLAYER_2;
	    else p = Player.PLAYER_1;
	} catch (IOException ex) {
	    Logger.getLogger(LipeRmiTest.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
}
