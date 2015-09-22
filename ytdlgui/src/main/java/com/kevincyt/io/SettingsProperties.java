package com.kevincyt.io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;

import com.kevincyt.ytdlgui.model.YtdlSettings;

public class SettingsProperties {
	// VARS
	private final Path settingsPath;

	// CONS
	public SettingsProperties(Path settingsPath) {
		this.settingsPath = settingsPath;
	}

	public SettingsProperties() {
		this(Paths.get(System.getProperty("user.dir"), "settings.properties"));
	}

	// METHODS
	public YtdlSettings readSettings() {
		YtdlSettings settings = new YtdlSettings();
		Properties prop = new Properties();
		try {
			prop.load(new FileReader(settingsPath.toFile()));
			settings.setDownloadDirectoryString(prop.getProperty("directory", System.getProperty("user.dir")));
			settings.setFileTemplate(prop.getProperty("fileTemplate", "%(title)s.%(ext)s"));
			settings.setMaxConcurrentJobs(Integer.parseInt(prop.getProperty("concurrentJobs", "4")));
			settings.setUpdateOnStart(Boolean.parseBoolean(prop.getProperty("updateOnStart", "false")));
			settings.setYoutubedlPathString(prop.getProperty("youtubedlPath", ""));
		} catch (FileNotFoundException e) {
			LogManager.getLogger()
					.info("Settings File not found, creating settings file and using defaults.");
			try {
				settingsPath.toFile().createNewFile();
			} catch (IOException e1) {
				LogManager.getLogger().error("Could not create settings file.");
			}
			return readSettings();
		} catch (IOException e) {
			LogManager.getLogger().error("IOException when reading from settings file @ " + settingsPath);
		}
		return settings;
	}

	public void writeSettings(YtdlSettings settings) {
		Properties prop = new Properties();
		prop.setProperty("directory", settings.getDownloadDirectoryString());
		prop.setProperty("fileTemplate", settings.getFileTemplate());
		prop.setProperty("concurrentJobs", Integer.toString(settings.getMaxConcurrentJobs()));
		prop.setProperty("updateOnStart", Boolean.toString(settings.getUpdateOnStart()));
		prop.setProperty("youtubedlPath", settings.getYoutubedlPathString());
		try {
			prop.store(new FileWriter(settingsPath.toFile()), "Settings");
		} catch (IOException e) {
			LogManager.getLogger().error("IOException when writing to settings file @ " + settingsPath);
		}
	}
}
