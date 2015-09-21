package com.kevincyt.ytdlgui.model;

import java.util.List;
import java.util.ArrayList;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Keeps track of all configurations for the next download job.
 */
public class YtdlDownloadConfiguration {
	// VARS
	// Playlist options
	public static final int DOWNLOAD_PLAYLIST_COMPLETE = -1;
	private final Property<Number> playlistStartProperty;
	private final Property<Number> playlistEndProperty;

	private final Property<Boolean> matchTitleOnProperty;
	private final Property<Boolean> rejectTitleOnProperty;
	private final Property<String> matchTitleProperty;
	private final Property<String> rejectTitleProperty;

	public final static int DATE_IGNORE = 0;
	public final static int DATE_BEFORE = 1;
	public final static int DATE_ONLY = 2;
	public final static int DATE_AFTER = 3;
	private final Property<Number> dateModeProperty;
	private final Property<String> dateStringProperty;

	private final Property<Boolean> playlistReverseProperty;

	// Download options
	private final Property<Boolean> withCappedRateProperty;
	private final Property<String> rateLimitStringProperty; // Bytes per second; Eg. 50K or 4.2M

	private final Property<Boolean> extractAudioOnlyProperty;

	// Verbosity options
	private final Property<Boolean> ignoreErrorsProperty;
	private final Property<Boolean> isVerboseProperty;

	// CONSTRUCTORS
	public YtdlDownloadConfiguration() {
		playlistStartProperty = new SimpleIntegerProperty(DOWNLOAD_PLAYLIST_COMPLETE);
		playlistEndProperty = new SimpleIntegerProperty(DOWNLOAD_PLAYLIST_COMPLETE);

		matchTitleOnProperty = new SimpleBooleanProperty(false);
		matchTitleProperty = new SimpleStringProperty();
		rejectTitleOnProperty = new SimpleBooleanProperty(false);
		rejectTitleProperty = new SimpleStringProperty();

		dateModeProperty = new SimpleIntegerProperty(DATE_IGNORE);
		dateStringProperty = new SimpleStringProperty();

		playlistReverseProperty = new SimpleBooleanProperty(false);
		withCappedRateProperty = new SimpleBooleanProperty(false);
		rateLimitStringProperty = new SimpleStringProperty();

		extractAudioOnlyProperty = new SimpleBooleanProperty(false);
		ignoreErrorsProperty = new SimpleBooleanProperty(false);
		isVerboseProperty = new SimpleBooleanProperty(false);
	}

	// METHODS
	public List<String> getParameters() {
		List<String> parameters = new ArrayList<String>();
		// Load up parameters
		getPlaylistParameters(parameters);
		getDownloadParameters(parameters);
		getOtherParameters(parameters);
		return parameters;
	}

	protected void getPlaylistParameters(final List<String> parameters) {
		int startValue, endValue;
		if((startValue = playlistStartProperty.getValue().intValue()) > 1) {
			parameters.add("--playlist-start");
			parameters.add(Integer.toString(startValue));
		}
		if((endValue = playlistEndProperty.getValue().intValue()) > 1) {
			parameters.add("--playlist-end");
			parameters.add(Integer.toString(endValue));
		}
		if(matchTitleOnProperty.getValue()) {
			parameters.add("--match-title");
			parameters.add(matchTitleProperty.getValue());
		}
		if(rejectTitleOnProperty.getValue()) {
			parameters.add("--reject-title");
			parameters.add(rejectTitleProperty.getValue());
		}

		switch (dateModeProperty.getValue().intValue()) {
			case DATE_IGNORE:
				break;
			case DATE_BEFORE:
				parameters.add("--datebefore");
				parameters.add(dateStringProperty.getValue());
				break;
			case DATE_ONLY:
				parameters.add("--date");
				parameters.add(dateStringProperty.getValue());
				break;
			case DATE_AFTER:
				parameters.add("--dateafter");
				parameters.add(dateStringProperty.getValue());
				break;
		}

		if(playlistReverseProperty.getValue()) {
			parameters.add("--playlist-reverse");
		}
	}

	protected void getDownloadParameters(final List<String> parameters) {
		if(withCappedRateProperty.getValue()) {
			parameters.add("--rate-limit");
			parameters.add(rateLimitStringProperty.getValue());
		}
		if(extractAudioOnlyProperty.getValue()) {
			parameters.add("--extract-audio");
		}
	}
	
	protected void getOtherParameters(final List<String> parameters) {
		if(ignoreErrorsProperty.getValue()) {
			parameters.add("--ignore-errors");
		}
		if(isVerboseProperty.getValue()) {
			parameters.add("--verbose");
		}
	}

	// Getters
	public Property<Number> getPlaylistStartProperty() {
		return playlistStartProperty;
	}

	public Property<Number> getPlaylistEndProperty() {
		return playlistEndProperty;
	}

	public Property<Boolean> getMatchTitleOnProperty() {
		return matchTitleOnProperty;
	}

	public Property<Boolean> getRejectTitleOnProperty() {
		return rejectTitleOnProperty;
	}

	public Property<String> getMatchTitleProperty() {
		return matchTitleProperty;
	}

	public Property<String> getRejectTitleProperty() {
		return rejectTitleProperty;
	}

	public Property<Number> getDateModeProperty() {
		return dateModeProperty;
	}

	public Property<String> getDateStringProperty() {
		return dateStringProperty;
	}

	public Property<Boolean> getPlaylistReverseProperty() {
		return playlistReverseProperty;
	}

	public Property<Boolean> getWithCappedRateProperty() {
		return withCappedRateProperty;
	}

	public Property<String> getRateLimitStringProperty() {
		return rateLimitStringProperty;
	}

	public Property<Boolean> getExtractAudioOnlyProperty() {
		return extractAudioOnlyProperty;
	}

	public Property<Boolean> getIgnoreErrorsProperty() {
		return ignoreErrorsProperty;
	}

	public Property<Boolean> getIsVerboseProperty() {
		return isVerboseProperty;
	}

	// Object override
	@Override
	public String toString() {
		String result = "";
		for (String param : getParameters()) {
			if(param.startsWith("-")) {
				result += "\n";
			}
			result += param + " ";
		}
		return result;
	}

}
