package com.kevincyt.ytdlgui.presenter.popup;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import com.kevincyt.ytdlgui.model.YtdlService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PopupPresenter implements Initializable {

	@FXML private Label label;
	@FXML private Button button;
	@FXML private TextField field;

	@Inject private YtdlService ytdlService;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@FXML
	public void doButtonAction(ActionEvent event) {
		System.out.println("Button pressed");
		System.out.println(ytdlService.getSettings().getDownloadDirectoryString());
	}

}
