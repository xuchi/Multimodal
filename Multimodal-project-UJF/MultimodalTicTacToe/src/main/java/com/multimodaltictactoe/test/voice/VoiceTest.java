package com.multimodaltictactoe.test.voice;

import com.multimodaltictactoe.logic.speech.SpeechRecognizer;
import com.multimodaltictactoe.logic.speech.SpeechRecognizerListener;
import com.multimodaltictactoe.logic.speech.SpeechTranslator;
import com.multimodaltictactoe.logic.utils.Coordinate;
import com.multimodaltictactoe.platform.PlatformService;
import com.multimodaltictactoe.gui.utils.ApplicationUtils;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class VoiceTest extends Application {
    private TextArea area;
    private SpeechRecognizer recognizer;
    
    @Override
    public void init() {	
	this.recognizer = PlatformService.getInstance().getSpeechRecognizer();
	this.recognizer.addListener(new SpeechRecognizerListener() {
	    @Override
	    public void onResult(Coordinate result) {
		addText(result.toString());
	    }
	});
    }
    
    @Override
    public void start(Stage primaryStage) {
	BorderPane root = new BorderPane();
	area = new TextArea();
	area.setEditable(false);
        
	Button bStart = new Button("Listen");
	bStart.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
                area.appendText("démarrage de l'écoute");
		recognizer.listen();
	    }
	});
	
	Button bEnd = new Button("Stop");
	bEnd.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		recognizer.stop();
		recognizer.close();
                area.appendText("Arrêt de l'écoute");
	    }
	});
	HBox buttons = new HBox(10.0, bStart, bEnd);
	buttons.setAlignment(Pos.CENTER);
	
	root.setBottom(buttons);
	root.setCenter(area);
	
	Scene scene = ApplicationUtils.createScene(root);
	ApplicationUtils.configureStageSize(primaryStage, 300, 300, 500, 500);
	
	primaryStage.setTitle("Test reconnaissance vocale");
	primaryStage.setScene(scene);
	primaryStage.getIcons().addAll(PlatformService.getInstance().getIcons());
	
	primaryStage.show();
    }
    
    public static void main(String[] args) {
	launch(args);
    }
    
    private void addText(String s) {
	Platform.runLater(new Runnable() {
	    @Override
	    public void run() {
		area.appendText(s + "-->" +SpeechTranslator.hypothesisToCoordinate(s)+ "\n");
	    }
	});
    }
}
