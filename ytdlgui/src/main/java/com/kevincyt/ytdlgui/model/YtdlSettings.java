package com.kevincyt.ytdlgui.model;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class YtdlSettings {
	// VARS
	/**
	 * The path to the youtube-dl executable. If null or empty, it is assumed to be in the system path
	 * variable.
	 */
	private final Property<String> youtubedlPathStringProperty;
	// Default => System.getProperty("user.dir")
	/**
	 * The output directory (download target).
	 */
	private final Property<String> downloadDirectoryStringProperty;

	// Youtube-dl settings
	/*
	 * TODO: Possibly provide some file templates out of the box?
	 */
	private final Property<String> fileTemplateProperty;
	private final Property<Boolean> updateOnStartProperty;
	private final Property<Number> maxConcurrentJobsProperty;

	// Constructor
	public YtdlSettings() {
		youtubedlPathStringProperty = new SimpleStringProperty();
		downloadDirectoryStringProperty = new SimpleStringProperty();
		fileTemplateProperty = new SimpleStringProperty();
		updateOnStartProperty = new SimpleBooleanProperty();
		maxConcurrentJobsProperty = new SimpleIntegerProperty();
	}

	public Property<String> getYoutubedlPathStringProperty() {
		return youtubedlPathStringProperty;
	}
	
	public String getYoutubedlPathString(){
		return getYoutubedlPathStringProperty().getValue();
	}

	public Property<String> getDownloadDirectoryStringProperty() {
		return downloadDirectoryStringProperty;
	}

	public String getDownloadDirectoryString(){
		return getDownloadDirectoryStringProperty().getValue();
	}
	
	public Property<String> getFileTemplateProperty() {
		return fileTemplateProperty;
	}
	
	public String getFileTemplate(){
		return getFileTemplateProperty().getValue();
	}

	public Property<Boolean> getUpdateOnStartProperty() {
		return updateOnStartProperty;
	}
	
	public boolean getUpdateOnStart(){
		return getUpdateOnStartProperty().getValue();
	}

	public Property<Number> getMaxConcurrentlJobsProperty() {
		return maxConcurrentJobsProperty;
	}
	
	
}
