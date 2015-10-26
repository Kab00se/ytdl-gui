package com.kevincyt.ytdlgui.presenter.menubar;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import com.kevincyt.ytdlgui.model.YtdlService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class MenubarPresenter implements Initializable {

	@Inject private YtdlService ytdlService;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	
	@FXML
	public void selectDirectory(){
		// TODO: Code already in InputPresenter, how to avoid duplication?
	}

}
