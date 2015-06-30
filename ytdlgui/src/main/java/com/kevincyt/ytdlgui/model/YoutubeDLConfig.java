package com.kevincyt.ytdlgui.model;

import java.util.List;
import java.util.ArrayList;

/**
 * Keeps track of all the configurations. Save to file?
 */
public class YoutubeDLConfig {
	// VARS
	// Playlist options
	public static final int DOWNLOAD_PLAYLIST_COMPLETE = -1;
	public int playlistStart = DOWNLOAD_PLAYLIST_COMPLETE;
	public int playlistEnd = DOWNLOAD_PLAYLIST_COMPLETE;

	public boolean matchTitleOn = false;
	public boolean rejectTitleOn = false;
	public String matchTitle;
	public String rejectTitle;

	public final static int DATE_IGNORE = 0;
	public final static int DATE_BEFORE = 1;
	public final static int DATE_ONLY = 2;
	public final static int DATE_AFTER = 3;
	public int dateMode = DATE_IGNORE;
	public String dateString;

	public boolean playlistReverse = false;

	// Download options
	public boolean limitRate = false;
	public String rateLimitString; // Bytes per second; Eg. 50K or 4.2M

	public boolean extractAudio = false;

	// Filesystem options
	public static final String DEFAULT_OUTPUT_DIRECTORY = System
			.getProperty("user.dir");
	public String outputDirectory;
	public String outputFileTemplate;
	public int autonumberSize;

	// Verbosity options
	public boolean ignoreErrors = false;
	public boolean verbose = false;

	// METHODS
	public List<String> getParameters() {
		List<String> parameters = new ArrayList<String>();
		// Load up parameters
		getPlaylistParameters(parameters);
		getDownloadParameters(parameters);
		getFileSystemOptions(parameters);
		getOtherParameters(parameters);
		return parameters;
	}

	protected void getPlaylistParameters(List<String> parameters) {
		if (playlistStart > 1) {
			parameters.add("--playlist-start");
			parameters.add(Integer.toString(playlistStart));
		}
		if (playlistEnd > 1) {
			parameters.add("--playlist-end");
			parameters.add(Integer.toString(playlistEnd));
		}
		if (matchTitleOn) {
			parameters.add("--match-title");
			parameters.add(matchTitle);
		}
		if (rejectTitleOn) {
			parameters.add("--reject-title");
			parameters.add(rejectTitle);
		}

		switch (dateMode) {
		case DATE_IGNORE:
			break;
		case DATE_BEFORE:
			parameters.add("--datebefore");
			parameters.add(dateString);
			break;
		case DATE_ONLY:
			parameters.add("--date");
			parameters.add(dateString);
			break;
		case DATE_AFTER:
			parameters.add("--dateafter");
			parameters.add(dateString);
			break;
		}

		if (playlistReverse) {
			parameters.add("--playlist-reverse");
		}
	}

	protected void getDownloadParameters(List<String> parameters) {
		if (limitRate) {
			parameters.add("--rate-limit");
			parameters.add(rateLimitString);
		}
		if (extractAudio) {
			parameters.add("--extract-audio");
		}
	}

	protected void getFileSystemOptions(List<String> parameters) {
		String option = "";
		if (outputDirectory != null && !outputDirectory.equals("")) {
			option += outputDirectory;
		} else {
			option += DEFAULT_OUTPUT_DIRECTORY;
		}
		if (outputFileTemplate != null && !outputFileTemplate.equals("")) {
			option += outputFileTemplate;
		}
		parameters.add("--output");
		parameters.add(option);

		if (autonumberSize > 0) {
			parameters.add("--autonumber-size");
			parameters.add(Integer.toString(autonumberSize));
		}
	}

	protected void getOtherParameters(List<String> parameters) {
		if (ignoreErrors) {
			parameters.add("--ignore-errors");
		}
		if (verbose) {
			parameters.add("--verbose");
		}
	}

	@Override
	public String toString() {
		String result = "";
		for (String param : getParameters()) {
			if (param.startsWith("-")) {
				result += "\n";
			}
			result += param + " ";
		}
		return result;
	}

}
