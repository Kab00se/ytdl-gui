package com.kevincyt.ytdlgui;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;

import com.airhacks.afterburner.injection.Injector;
import com.airhacks.afterburner.views.FXMLView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.kevincyt.ytdlgui.io.YtdlSettingsProperties;
import com.kevincyt.ytdlgui.model.YtdlDownloadConfiguration;
import com.kevincyt.ytdlgui.model.YtdlJobQueue;
import com.kevincyt.ytdlgui.model.YtdlService;
import com.kevincyt.ytdlgui.model.YtdlSettings;
import com.kevincyt.ytdlgui.presenter.main.MainView;

public class Launch extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		YtdlSettings settings = new YtdlSettingsProperties().readSettings();
		YtdlDownloadConfiguration config = new YtdlDownloadConfiguration(settings);
		YtdlJobQueue jobQueue = new YtdlJobQueue();
		jobQueue.maxConcurrentJobsProperty().bind(settings.maxConcurrentJobsProperty());
		YtdlService service = new YtdlService(settings, config, jobQueue);
		LogManager.getLogger().info("YtdlService initialized.");
		// Setup injection
		Map<Object, Object> dataContext = new HashMap<Object, Object>();
		dataContext.put("ytdlService", service);
		// Set injector source
		Injector.setConfigurationSource(dataContext::get);
		// Create main scene for stage
		FXMLView mainView = new MainView();
		Scene primaryScene = new Scene(mainView.getView());
		primaryStage.setTitle("Youtube-dl GUI");
		primaryStage.setScene(primaryScene);
		primaryStage.show();
		LogManager.getLogger().info("Stage shown");
	}

	public static void main(String[] args) {
		launch(args);
	}
}