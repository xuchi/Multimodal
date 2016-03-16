package com.multimodaltictactoe.speech;

import com.multimodaltictactoe.logic.speech.SpeechRecognizer;
import com.multimodaltictactoe.logic.speech.SpeechTranslator;
import com.multimodaltictactoe.logic.utils.Coordinate;
import com.multimodaltictactoe.utils.Logs;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;

public class DesktopSpeechRecognizer extends SpeechRecognizer {
    private static final Logger logger = Logs.getLogger(DesktopSpeechRecognizer.class);
    
    private static DesktopSpeechRecognizer instance = null;
    
    private LiveSpeechRecognizer recognizer;
    private SpeechTask speechTask;
    
    private DesktopSpeechRecognizer() {
	logger.info("Lancement reconnaissance vocale");
	Configuration configuration = new Configuration();
	configuration.setAcousticModelPath("resource:/model-fr/acoustic");
	configuration.setDictionaryPath("resource:/model-fr/dictionary/frenchWords62K.dic");
	configuration.setUseGrammar(true);
	configuration.setGrammarName("voice");
	configuration.setGrammarPath("resource:/model-fr/grammar/");
	this.speechTask = new SpeechTask();
	try {
	    this.recognizer = new LiveSpeechRecognizer(configuration);
	    this.recognizer.startRecognition(true);
	} catch (IOException ex) {
	    this.recognizer = null;
	}
    }
    
    public static synchronized DesktopSpeechRecognizer getInstance() {
	if(instance == null) {
	    instance = new DesktopSpeechRecognizer();
	}
	return instance;
    }

    @Override
    public void listen() {
	if(!this.speechTask.isRunning()) {
	    logger.info("Reconnaissance vocale : listen");
	    this.speechTask = new SpeechTask();
	    new Thread(this.speechTask).start();
	}
    }

    @Override
    public void stop() {
	logger.info("Reconnaissance vocale : stop");
	this.speechTask.cancel(true);
    }

    @Override
    public void close() {
	logger.info("Reconnaissance vocale : close");
    }
    
    private class SpeechTask extends Task<Void> {
	@Override
	protected Void call() throws Exception {
	    if(recognizer != null) {
		SpeechResult result;
		while (!isCancelled() && ((result = recognizer.getResult()) != null)) {
		    if(!isCancelled()) {
			String hyp = result.getHypothesis();
			logger.log(Level.INFO, "Resultat brut de la reconnaissance vocale : {0}", hyp);
			Coordinate coord = SpeechTranslator.hypothesisToCoordinate(hyp);
			logger.log(Level.INFO, "Resultat coordonnées de la reconnaissance vocale : {0}", coord);
			if(coord != null) {
			    fireResult(coord);
			}			
		    }
		}
	    }
	    return null;
	}
    }
}
