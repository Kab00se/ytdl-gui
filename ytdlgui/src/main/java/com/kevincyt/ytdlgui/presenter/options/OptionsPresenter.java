package com.kevincyt.ytdlgui.presenter.options;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import com.kevincyt.ytdlgui.model.YtdlDownloadConfiguration;
import com.kevincyt.ytdlgui.model.YtdlService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

public class OptionsPresenter implements Initializable {

	@FXML private CheckBox extractAudioCheckBox, limitRateCheckBox, matchRegexCheckBox, rejectRegexCheckBox;
	@FXML private TextField outputFileTemplateField, limitRateField, rangeFromField, rangeToField,
			matchRegexField, rejectRegexField;

	@Inject private YtdlService ytdlService;

	@Override
	public void initialize(URL url, ResourceBundle res) {
		// Setup
		YtdlDownloadConfiguration cfg = ytdlService.getDownloadConfig();
		NumberStringConverter numberStringConv = new NumberStringConverter();
		// CheckBox bindings
		extractAudioCheckBox.selectedProperty().bindBidirectional(cfg.extractAudioOnlyProperty());
		limitRateCheckBox.selectedProperty().bindBidirectional(cfg.withCappedRateProperty());
		matchRegexCheckBox.selectedProperty().bindBidirectional(cfg.matchTitleProperty());
		rejectRegexCheckBox.selectedProperty().bindBidirectional(cfg.rejectTitleProperty());
		// TextField bindings
		outputFileTemplateField.textProperty().bindBidirectional(ytdlService.getSettings().fileTemplateProperty());
		limitRateField.textProperty().bindBidirectional(cfg.rateLimitStringProperty());
		rangeFromField.textProperty().bindBidirectional(cfg.playlistStartProperty(), numberStringConv);
		rangeToField.textProperty().bindBidirectional(cfg.playlistEndProperty(), numberStringConv);
		matchRegexField.textProperty().bindBidirectional(cfg.matchTitleStringProperty());
		rejectRegexField.textProperty().bindBidirectional(cfg.rejectTitleStringProperty());
	}

}
