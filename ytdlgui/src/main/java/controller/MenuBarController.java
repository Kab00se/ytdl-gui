package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MenuBarController {
	protected YtdlGuiController mainController;

	@FXML
	protected void handleSelectDirectory(){
		mainController.selectOutputFolder();
	}
	
	@FXML
	protected void handleOpen(ActionEvent event) {
		mainController.openOutputFolder();
	}

	@FXML
	protected void handlePreferences(ActionEvent event) {
		// TODO: Handle prefs
	}

	@FXML
	protected void handleQuit(ActionEvent event) {
		System.exit(0);
	}
	
	@FXML
	protected void handleUpdate(ActionEvent event){
		try {
			mainController.runYTDL("--update");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	protected void handleCommand(ActionEvent event){
		Stage popup = new Stage();
		
		HBox root = new HBox();
		Label label = new Label("youtube-dl");
		TextField field = new TextField();
		field.setPromptText("--help");
		Button button = new Button("OK");
		button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try {
					mainController.runYTDL(field.getText().split(" "));
					popup.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		root.getChildren().addAll(label, field, button);
		
		Scene popupScene = new Scene(root);
		// Create windows/stage.
		
		popup.setTitle("Enter custom youtube-dl command");
		popup.setScene(popupScene);
		popup.show();
	}

	@FXML
	protected void handleAbout(ActionEvent event) {
		// TODO: Open About windows.
	}
	
	// G&S
	public void setGUIController(YtdlGuiController controller){
		this.mainController = controller;
	}

}
