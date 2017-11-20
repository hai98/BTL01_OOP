package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import words.RunningData;
import words.Word;
import words.WordCollection;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class StudyController implements Initializable{
	@FXML
	private JFXButton btnLearn;

	@FXML
	private JFXButton btnReview;

	@FXML
	private JFXButton btnTest;

	@FXML
	private JFXComboBox<WordCollection> topicsBox;

	@FXML
	private JFXButton btnRand;

	private ObservableList<WordCollection> topics;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		topics = FXCollections.observableArrayList(RunningData.getCollectionList());
		topicsBox.setItems(topics);
		btnReview.setDisable(false);

		btnRand.setOnAction(event -> {
			int i = (int) (Math.random()*topics.size());
			topicsBox.getSelectionModel().select(i);
			btnLearn.setDisable(false);
			btnTest.setDisable(false);
			btnReview.setDisable(false);
		});

		btnLearn.setOnAction(event -> {
			LearnController.t = topicsBox.getValue();
			LearnController.show();
		});

		btnReview.setOnAction(event -> {
			LearnController.t = null;
			LearnController.show();
		});

		btnTest.setOnAction(event -> {
			TestController.t = topicsBox.getValue();
			TestController.show();
		});

		topicsBox.setOnAction(event -> {
			btnLearn.setDisable(false);
			btnTest.setDisable(false);
			btnReview.setDisable(false);
		});

		topicsBox.setOnMouseClicked(event -> {
			topics.clear();
			topics.addAll(RunningData.getCollectionList());
		});
	}
}
