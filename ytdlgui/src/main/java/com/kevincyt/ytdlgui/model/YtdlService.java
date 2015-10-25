package com.kevincyt.ytdlgui.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;

import com.kevincyt.ytdlgui.io.YtdlSettingsProperties;
import com.kevincyt.ytdlgui.model.jobs.AbstractYtdlJob;
import com.kevincyt.ytdlgui.model.jobs.YtdlDownload;
import com.kevincyt.ytdlgui.model.jobs.YtdlUpdate;

public class YtdlService {
	// VARS
	// TODO: Highly TEMP. Should be in a factory. (Perhaps YtdlQueue should be responsible for spawning them).
	@Deprecated public static int nextID = 1;
	private final YtdlDownloadConfiguration downloadConfig;
	private final YtdlSettings settings;
	private final YtdlJobQueue jobQueue;

	// CONS
	public YtdlService(YtdlSettings settings, YtdlDownloadConfiguration config, YtdlJobQueue jobQueue) {
		this.settings = settings;
		this.downloadConfig = config;
		this.jobQueue = jobQueue;
	}

	// METHODS
	public void download(String url) {
		LogManager.getLogger().info("Download added for: " + url);
		List<String> args = new ArrayList<String>();
		args.add(url);
		args.addAll(getDownloadConfig().getParameters());
		AbstractYtdlJob job = new YtdlDownload(nextID++, getSettings().getYoutubedlPathString(), args);
		getJobQueue().addYtdlJob(job);
	}

	public void update() {
		LogManager.getLogger().info("Update added.");
		AbstractYtdlJob job = new YtdlUpdate(nextID++, getSettings().getYoutubedlPathString());
		getJobQueue().addYtdlJob(job);
	}

	public void saveSettings() {
		new YtdlSettingsProperties().writeSettings(getSettings());
	}

	// GETS & SETS
	public YtdlDownloadConfiguration getDownloadConfig() {
		return downloadConfig;
	}

	public YtdlSettings getSettings() {
		return settings;
	}

	public YtdlJobQueue getJobQueue() {
		return jobQueue;
	}
}
