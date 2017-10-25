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

	/*private Button btn, btn1;
	private Label lbl;*/

	private static Stage tmp;

	public static Stage getTmp() {
		return tmp;
	}

	@Override
	public void start(Stage primaryStage) {
		/*btn = new Button();
		btn.setText("Tra tu");
		btn.setOnAction(e -> lbl.setText(Search.searchAll("mango").toString()));
		lbl = new Label("English");
		btn1 = new Button("Close");
		btn1.setOnAction(e -> primaryStage.close());

		BorderPane pane  = new BorderPane();
		pane.setCenter(btn);
		pane.setAlignment(btn, Pos.TOP_RIGHT);
		pane.setTop(lbl);
		pane.setAlignment(lbl, Pos.TOP_LEFT);
		pane.setBottom(btn1);
		pane.setAlignment(btn1, Pos.BOTTOM_CENTER);

		Scene scene = new Scene(pane, 500, 250);
		primaryStage.setMinHeight(250);
		primaryStage.setMinWidth(500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("E->V_app_oop_beta");
		primaryStage.setOnCloseRequest(e-> {
			e.consume();
			primaryStage.close();});
		primaryStage.show();*/

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
