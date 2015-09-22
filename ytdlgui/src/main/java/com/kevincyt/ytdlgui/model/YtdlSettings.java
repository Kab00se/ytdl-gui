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

	// Properties / Gets / Sets
	public Property<String> youtubedlPathStringProperty() {
		return youtubedlPathStringProperty;
	}
	
	public String getYoutubedlPathString(){
		return youtubedlPathStringProperty().getValue();
	}
	
	public void setYoutubedlPathString(String path){
		youtubedlPathStringProperty().setValue(path);
	}

	public Property<String> downloadDirectoryStringProperty() {
		return downloadDirectoryStringProperty;
	}

	public String getDownloadDirectoryString(){
		return downloadDirectoryStringProperty().getValue();
	}
	
	public void setDownloadDirectoryString(String path){
		downloadDirectoryStringProperty().setValue(path);
	}
	
	public Property<String> fileTemplateProperty() {
		return fileTemplateProperty;
	}
	
	public String getFileTemplate(){
		return fileTemplateProperty().getValue();
	}
	
	public void setFileTemplate(String template){
		fileTemplateProperty().setValue(template);
	}

	public Property<Boolean> updateOnStartProperty() {
		return updateOnStartProperty;
	}
	
	public boolean getUpdateOnStart(){
		return updateOnStartProperty().getValue();
	}
	
	public void setUpdateOnStart(boolean b){
		updateOnStartProperty().setValue(b);
	}

	public Property<Number> maxConcurrentJobsProperty() {
		return maxConcurrentJobsProperty;
	}
	
	public int getMaxConcurrentJobs(){
		return maxConcurrentJobsProperty().getValue().intValue();
	}
	
	public void setMaxConcurrentJobs(int value){
		maxConcurrentJobsProperty().setValue(value);
	}
	
	
}
