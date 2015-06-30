package controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.kevincyt.ytdlgui.model.YoutubeDL;
import com.kevincyt.ytdlgui.model.YoutubeDLConfig;

import application.UpdateTask;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

public class YtdlGuiController {
	// MODEL VARS
	private YoutubeDL yt;

	// FXML VARS
	@FXML
	TextField urlField, rangeFromField, rangeToField, matchRegexField,
			rejectRegexField, outputDirectoryField, outputFileTemplateField,
			limitRateField;

	@FXML
	TextArea outputTextArea;

	@FXML
	CheckBox matchRegexCheckBox, rejectRegexCheckBox, limitRateCheckBox,
			ignoreErrorsCheckBox, verboseCheckBox, extractAudioCheckBox;

	@FXML
	MenuBarController menubarController;

	@FXML
	protected void initialize() {
		menubarController.setGUIController(this);
		yt = new YoutubeDL();
	}

	// METHODS
	protected void updateConfig() {
		YoutubeDLConfig cfg = getConfig();
		// Playlist Options
		int from, to;
		try {
			if ((from = Integer.parseInt(rangeFromField.getText())) > 0)
				;
			cfg.playlistStart = from;
		} catch (NumberFormatException nfe) {
			// TODO: Handle NFE
		}
		try {
			if ((to = Integer.parseInt(rangeToField.getText())) > 0)
				;
			cfg.playlistEnd = to;
		} catch (NumberFormatException nfe) {
			// TODO: Handle NFE
		}
		cfg.matchTitleOn = matchRegexCheckBox.isSelected();
		cfg.matchTitle = matchRegexField.getText();
		cfg.rejectTitleOn = rejectRegexCheckBox.isSelected();
		cfg.rejectTitle = rejectRegexField.getText();
		// TODO: Date mode & Date string?

		// Download Options
		cfg.limitRate = limitRateCheckBox.isSelected();
		cfg.rateLimitString = limitRateField.getText();

		cfg.extractAudio = extractAudioCheckBox.isSelected();

		// Filesystem Options
		cfg.outputDirectory = outputDirectoryField.getText();
		cfg.outputFileTemplate = outputFileTemplateField.getText();
		// TODO: Autonumber field?

		// Verbosity Options
		cfg.verbose = verboseCheckBox.isSelected();
		cfg.ignoreErrors = ignoreErrorsCheckBox.isSelected();
	}

	protected void runYTDL(String... parameters) throws IOException {
		// Diagnostic
		String command = "";
		for (String s : parameters) {
			command += " " + s;
		}
		outputTextArea.appendText("\nyoutube-dl" + command + "\n");
		// Run process
		try {
			Process p = yt.runYTDL(parameters);
			routeProcessStreams(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void routeProcessStreams(Process process) {
		// TODO: Is there a way to concatenate streams? There must be.
		Thread outputThread = new Thread(new UpdateTask(
				process.getInputStream(), outputTextArea));
		outputThread.setDaemon(true);
		outputThread.start();

		Thread errorThread = new Thread(new UpdateTask(
				process.getErrorStream(), outputTextArea));
		errorThread.setDaemon(true);
		errorThread.start();
	}

	// FXML - Button Actions
	@FXML
	protected void download() {
		updateConfig();
		// Diagnostic output
		String command = getConfig().toString();
		outputTextArea.appendText("\nyoutube-dl" + command + "\n");
		// Run process
		try {
			Process p = yt.download(urlField.getText());
			routeProcessStreams(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	protected void selectOutputFolder() {
		DirectoryChooser dir = new DirectoryChooser();
		dir.setTitle("Choose output directory");
		String initialDir = getConfig().outputDirectory;
		if (initialDir == null || initialDir.equals("")) {
			initialDir = YoutubeDLConfig.DEFAULT_OUTPUT_DIRECTORY;
		}
		dir.setInitialDirectory(new File(initialDir));
		File directory = dir.showDialog(outputTextArea.getScene().getWindow());
		if (directory != null) {
			String dirString = directory.toString().trim();
			System.out.println(dirString);
			// TODO: Not working properly. Needs to work on UNIX systems too.
			if (!dirString.endsWith("/") || !dirString.endsWith("\\")) {
				dirString += "\\";
			}
			outputDirectoryField.setText(dirString);
			getConfig().outputDirectory = dirString;
		}
	}

	@FXML
	protected void openOutputFolder() {
		try {
			Desktop.getDesktop().open(new File(getConfig().outputDirectory));
		} catch (NullPointerException npe) {
			try {
				Desktop.getDesktop().open(
						new File(YoutubeDLConfig.DEFAULT_OUTPUT_DIRECTORY));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// GETS & SETS
	protected YoutubeDLConfig getConfig() {
		return yt.config;
	}
}
