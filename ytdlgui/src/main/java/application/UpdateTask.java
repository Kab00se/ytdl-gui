package application;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

public class UpdateTask extends Task<String> {
	// VARS
	protected BufferedReader outputStream;
	protected TextArea target;
	
	// CONS
	public UpdateTask(InputStream output, TextArea target) {
		outputStream = new BufferedReader(new InputStreamReader(output));
		this.target = target;
	}

	// TASK
	@Override
	protected String call() throws Exception {
		String out;
		while((out = outputStream.readLine()) != null){
			Platform.runLater(new WriteTask(out, target));
		}
		return "COMPLETE";
	}

}
