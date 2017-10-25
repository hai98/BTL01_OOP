package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import words.Search;

import java.io.IOException;

public class GUIMain extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private static Stage tmp;

	public static Stage getTmp() {
		return tmp;
	}

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("E-V_app_beta");
		tmp = primaryStage;

		try {
			FXMLLoader loader = new FXMLLoader(GUIMain.class.getResource("LookUp.fxml"));
			AnchorPane page = loader.load();
			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
