package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * Class trung tâm phần giao diện
 */
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

		primaryStage.setTitle("Learning English App");
		mainStage = primaryStage;

		try {
			HBox pane = FXMLLoader.load(getClass().getResource("view/home_view.fxml"));
			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest(event -> {
				event.consume();
				/*boolean ok = ConfirmationBox.show("Are you sure want to quit?", "Quit?", "Yes", "No");
				if(ok) primaryStage.close();*/

				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Exit?");
				alert.setHeaderText("Exit App?");
//				alert.setContentText("Are you sure?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK)
					primaryStage.close();
			});
			primaryStage.getIcons().add(new Image("icon.png"));
			primaryStage.show();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
//todo complete welcome screen
