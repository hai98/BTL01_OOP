package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIMain extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private static Stage mainStage;

	static Stage getMainStage() {
		return mainStage;
	}

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("E-V_app_beta");
		mainStage = primaryStage;

		try {
//			FXMLLoader loader = new FXMLLoader(GUIMain.class.getResource("look_up_view.fxml"));
//			VBox pane = loader.load();
			VBox pane = (VBox) FXMLLoader.load(getClass().getResource("look_up_view.fxml"));
			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
