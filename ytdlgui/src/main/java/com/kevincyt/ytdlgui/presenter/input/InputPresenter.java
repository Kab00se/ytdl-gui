package com.kevincyt.ytdlgui.presenter.input;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;

import com.kevincyt.ytdlgui.model.YtdlService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

public class InputPresenter implements Initializable {
	// FXML Nodes
	@FXML private TextField urlField, directoryField;

	@FXML private Button downloadButton, openDirectoryButton, selectDirectoryButton;

	@Inject private YtdlService ytdlService;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Data bindings
		directoryField.textProperty().bindBidirectional(
				ytdlService.getSettings().downloadDirectoryStringProperty());
	}

	// BUTTON LOGIC
	@FXML
	protected void downloadAction() {
		ytdlService.download(urlField.getText());
	}

	/**
	 * Opens the directory that is currently in the openDirectoryAction. If the directory does not exist or
	 * can otherwise not be opened an error popup is shown.
	 */
	@FXML
	protected void openDirectoryAction(ActionEvent event) {
		try {
			String currentDir = directoryField.getText();
			if(currentDir == null || currentDir.isEmpty()) {
				currentDir = System.getProperty("user.dir");
			}
			Desktop.getDesktop().open(new File(currentDir));
		} catch (IllegalArgumentException iae) {
			// TODO: Throw an error dialog (Given Directory does not exist, would you like to create?)
			LogManager.getLogger().error("openDirectory encountered IllegalArgumentException.");
		} catch (IOException e) {
			LogManager.getLogger().error("openDirectory encountered IOException.");
		}
	}

	@FXML
	protected void selectDirectoryAction(ActionEvent event) {
		DirectoryChooser dir = new DirectoryChooser();
		dir.setTitle("Choose output directory");
		String initialDir = directoryField.getText();
		if(initialDir == null || initialDir.isEmpty()) {
			initialDir = System.getProperty("user.dir");
		}
		dir.setInitialDirectory(new File(initialDir));
		File directory = dir.showDialog(directoryField.getParent().getScene().getWindow());
		if(directory != null) {
			String dirString = directory.toString().trim();
			if(!dirString.endsWith("/") || !dirString.endsWith("\\")) {
				dirString += "\\";
			}
			directoryField.setText(dirString); // Will also update the model due to bidirectional bind.
		}
	}

}
