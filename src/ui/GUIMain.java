package ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import words.Search;

public class GUIMain extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private Button btn, btn1;
	private Label lbl;
	@Override
	public void start(Stage primaryStage) {
		btn = new Button();
		btn.setText("Tra tu");
		btn.setOnAction(e -> lbl.setText(Search.searchAll("mango").toString()));
		lbl = new Label("English");
		btn1 = new Button("Close");
		btn1.setOnAction(e -> primaryStage.close());

		BorderPane pane  = new BorderPane();
		pane.setCenter(btn);
		pane.setTop(lbl);
		pane.setAlignment(lbl, Pos.TOP_CENTER);
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
		primaryStage.show();
	}
}
