package application;

import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

public class WriteTask extends Task<String> {
	// VARS
	protected String text;
	protected TextArea target;
	
	// CONSTRUCTOR
	public WriteTask(String text, TextArea target){
		this.text = text;
		this.target = target;
	}

	@Override
	protected String call() throws Exception {
		target.appendText(text + "\n");
		// TODO: What's purpose of return?
		return text;
	}

}
