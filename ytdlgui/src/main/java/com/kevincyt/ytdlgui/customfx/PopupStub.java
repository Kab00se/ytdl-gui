package com.kevincyt.ytdlgui.customfx;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PopupStub {

	// TODO: Placeholder for code.
	public static void createPopup() {
		Stage popup = new Stage();

		// Content
		HBox root = new HBox();
		Label label = new Label("youtube-dl");
		TextField field = new TextField();
		field.setPromptText("--help");
		Button button = new Button("OK");
		button.setOnAction(event -> {
			System.out.println("OK BUTTON PRESSED");
			popup.close();
		});
		root.getChildren().addAll(label, field, button);

		// Set scene/stage
		Scene popupScene = new Scene(root);
		// Create windows/stage.

		popup.setTitle("Enter custom youtube-dl command");
		popup.setScene(popupScene);
		popup.show();
	}
}
