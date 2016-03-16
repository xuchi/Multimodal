package com.multimodaltictactoe.speech;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import com.multimodaltictactoe.logic.speech.SpeechRecognizer;
import com.multimodaltictactoe.logic.speech.SpeechTranslator;
import com.multimodaltictactoe.logic.utils.Coordinate;
import com.multimodaltictactoe.utils.Logs;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafxports.android.FXActivity;

public class AndroidSpeechRecognizer extends SpeechRecognizer {
    private static final Logger logger = Logs.getLogger(AndroidSpeechRecognizer.class);
    private static final int MAX_RESULTS = 5;
    private static final Intent INTENT = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
	    .putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
	    .putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "voice.recognition.test")
	    .putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, MAX_RESULTS);
    private static AndroidSpeechRecognizer instance = null;
    
    private android.speech.SpeechRecognizer sr;
    
    private AtomicBoolean isActivated;
    
    private AndroidSpeechRecognizer() {
	FXActivity.getInstance().runOnUiThread(new Runnable() {
	    @Override
	    public void run() {
		isActivated = new AtomicBoolean(false);
		sr = android.speech.SpeechRecognizer.createSpeechRecognizer(FXActivity.getInstance());
		sr.setRecognitionListener(new Listener());
	    }
	});
    }
    
    public static synchronized AndroidSpeechRecognizer getInstance() {
	if(instance == null) {
	    instance = new AndroidSpeechRecognizer();
	}
	return instance;
    }
    
    @Override
    public void listen() {
	FXActivity.getInstance().runOnUiThread(new Runnable() {
	    @Override
	    public void run() {
		logger.info("Reconnaissance vocale : listen");
		isActivated.set(true);
		sr.startListening(INTENT);
	    }
	});
    }
    
    @Override
    public void stop() {
	FXActivity.getInstance().runOnUiThread(new Runnable() {
	    @Override
	    public void run() {
		logger.info("Reconnaissance vocale : stop");
		isActivated.set(false);
		sr.stopListening();
	    }
	});
    }
    
    @Override
    public void close() {
	FXActivity.getInstance().runOnUiThread(new Runnable() {
	    @Override
	    public void run() {
		logger.info("Reconnaissance vocale : close");
		sr.destroy();
	    }
	});
    }
    
    private class Listener implements RecognitionListener {
	@Override
	public void onReadyForSpeech(Bundle bundle) {
	}
	@Override
	public void onBeginningOfSpeech() {
	}
	@Override
	public void onRmsChanged(float f) {
	}
	@Override
	public void onBufferReceived(byte[] bytes) {
	}
	@Override
	public void onEndOfSpeech() {
	}
	@Override
	public void onError(int i) {
	}
	@Override
	public void onResults(Bundle bundle) {
	    List<String> results = bundle.getStringArrayList(android.speech.SpeechRecognizer.RESULTS_RECOGNITION);
	    logger.log(Level.INFO, "Resultat brut de la reconnaissance vocale : {0}", results);
	    Coordinate coord = SpeechTranslator.hypothesisToCoordinate(results);
	    logger.log(Level.INFO, "Resultat coordonnées de la reconnaissance vocale : {0}", coord);
	    if(coord != null && isActivated.get()) {
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
			fireResult(coord);
		    }
		});
	    }
	    if(isActivated.get()) {
		listen();
	    }
	}
	@Override
	public void onPartialResults(Bundle bundle) {
	}
	@Override
	public void onEvent(int i, Bundle bundle) {
	}
    }
}
