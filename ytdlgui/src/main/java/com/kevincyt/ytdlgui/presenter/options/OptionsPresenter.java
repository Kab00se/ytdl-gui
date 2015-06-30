package com.kevincyt.ytdlgui.presenter.options;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class OptionsPresenter implements Initializable {

	// FXML Controller / injections
	@FXML public Button saveButton;
	// @Inject private YoutubeDLService service;
	@Inject
	private Object identifier;
	
	@Override
	public void initialize(URL url, ResourceBundle res) {
		saveButton.setOnAction(actionEvent -> {
			System.out.println(identifier);
		});
	
	}

}
