package com.kevincyt.ytdlgui.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YoutubeDL {
	// VARS
	protected final String filePath = System.getProperty("user.dir")
			+ "/lib/ffmpeg/bin/youtube-dl";	// TODO: Needs to be in settings

	public YoutubeDLConfig config;

	// CONS
	public YoutubeDL(YoutubeDLConfig config) {
		this.config = config;
	}

	public YoutubeDL() {
		this(new YoutubeDLConfig());
	}

	// METHODS
	public Process runYTDL(List<String> parameters) throws IOException {
		// Building argument list
		List<String> commandList = new ArrayList<String>();
		commandList.add(filePath);
		commandList.addAll(parameters);
		// Create and start process
		Process process = new ProcessBuilder().command(commandList).start();
		return process;
	}

	public Process runYTDL(String... parameters) throws IOException {
		return runYTDL(Arrays.asList(parameters));
	}

	// Convenience
	/**
	 * Convenience implementation of runYTLD for downloading.
	 */
	public Process download(String url) throws IOException {
		List<String> parameters = config.getParameters();
		parameters.add(url);
		return runYTDL(parameters);
	}

	public Process getVersion() throws IOException {
		return runYTDL("--version");
	}

	public Process update() throws IOException {
		return runYTDL("--update");
	}

}