package com.kevincyt.ytdlgui.presenter;

import java.util.HashMap;
import java.util.Map;

import com.airhacks.afterburner.injection.Injector;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.kevincyt.ytdlgui.presenter.options.OptionsView;

// REFERENCE: 
// https://wimdeblauwe.wordpress.com/2015/03/24/introduction-to-using-javafx-with-afterburner-fx/
public class App extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Setup injection
		Map<Object, Object> dataContext = new HashMap<Object, Object>();
		dataContext.put("identifier", new Object());
		// More injection stuff here
		Injector.setConfigurationSource(dataContext::get);
		
		OptionsView mainView = new OptionsView();
		Scene primaryScene = new Scene(mainView.getView());
		primaryStage.setTitle("Afterburner test");
		primaryStage.setScene(primaryScene);
		primaryStage.show();
	}
	
	public static void main(String[] args){
		launch(args);
	}
}
