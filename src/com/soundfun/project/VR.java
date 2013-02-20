package com.soundfun.project;

import java.util.ArrayList;

import android.os.Bundle;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.TextView;
import android.widget.Toast;

public class VR implements RecognitionListener {
	/** Text display */
	private TextView blurb;

	/** Parameters for recognition */
	private Intent recognizerIntent;

	/** The ear */
	private SpeechRecognizer recognizer;

	public VR() {
		
	}
	public void Resume(){
		startRecognitionListener();
	}

	private void startRecognitionListener() {

		recognizer = SpeechRecognizer.createSpeechRecognizer(null);
		recognizer.setRecognitionListener(this);

		recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);

		// ћакс пауза, после которой запись голоса останавливаетс€, в мс
		// intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS,
		// Long.parseLong("1000"));

		// ”становка €зыка
		// intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
		// Locale.getDefault());

		// ¬ описании указано, что это свойство не об€зательно, но, удивительным
		// образом, если его не установить вылетает исключение "Other client side error".
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
				getClass().getPackage().getName());
		// recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,
		// true);
		recognizer.startListening(recognizerIntent);

	}

	@Override
	public void onBeginningOfSpeech() {
	}

	@Override
	public void onBufferReceived(byte[] arg0) {
	}

	@Override
	public void onEndOfSpeech() {
	}

	@Override
	public void onError(int errCode) {

		if (errCode == 1) {
			showToastMessage("Network operation timed out");
		} else if (errCode == 2) {
			showToastMessage("Other network related errors");
		} else if (errCode == 3) {
			showToastMessage("Audio recording error");
		} else if (errCode == 4) {
			showToastMessage("Server sends error status");
		} else if (errCode == 5) {
			showToastMessage("Other client side errors");
		} else if (errCode == 6) {
			showToastMessage("No speech input");
		} else if (errCode == 7) {
			showToastMessage("No recognition result matched");
		} else if (errCode == 8) {
			showToastMessage("RecognitionService busy");
		} else if (errCode == 9) {
			showToastMessage("Insufficient permissions");
		}

		recognizer.startListening(recognizerIntent);
	}

	@Override
	public void onEvent(int arg0, Bundle arg1) {
	}

	@Override
	public void onPartialResults(Bundle arg0) {
	}

	@Override
	public void onReadyForSpeech(Bundle arg0) {
		blurb.append("> ");
	}

	@Override
	public void onResults(Bundle bundle) {

		ArrayList<String> results = bundle
				.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

		if (results.isEmpty()) {
			return;
		}
		String text = results.get(0);

		TTS.speakOut(text);

	}

	@Override
	public void onRmsChanged(float arg0) {
	}

	// Helper method to show the toast message
	void showToastMessage(String message) {
		Toast.makeText(null, message, Toast.LENGTH_SHORT).show();
	}
}
