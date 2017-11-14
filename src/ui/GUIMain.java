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

		primaryStage.setTitle("learning_e_app_beta");
		mainStage = primaryStage;
//		primaryStage.initStyle(StageStyle.TRANSPARENT);

		try {
//			FXMLLoader loader = new FXMLLoader(GUIMain.class.getResource("look_up_view.fxml"));
//			VBox pane = loader.load();
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
