package ui;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import words.RunningData;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class tạo & điều khiển giao diện cài đặt
 */
public class SettingController implements Initializable{
	@FXML
	Spinner<Integer> newWord;

	@FXML
	Spinner<Integer> revWord;

	@FXML
	Spinner<Integer> testWord;

	@FXML
	private JFXButton btnSave;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		newWord.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 100, RunningData.getNewWordPerDay(), 1));
		revWord.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 100, RunningData.getRevWordPerDay(), 1));
		testWord.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 100, RunningData.getWordsForTest(), 1));

		btnSave.setDisable(true);
		newWord.valueProperty().addListener((observable, oldValue, newValue) -> {
//			RunningData.setNewWordPerDay(newValue);
			btnSave.setDisable(false);
		});

		revWord.valueProperty().addListener((observable, oldValue, newValue) -> {
//			RunningData.setRevWordPerDay(newValue);
			btnSave.setDisable(false);
		});

		testWord.valueProperty().addListener((observable, oldValue, newValue) -> {
//			RunningData.setWordsForTest(newValue);
			btnSave.setDisable(false);
		});

		btnSave.setOnAction(event -> {
			RunningData.setNewWordPerDay(newWord.getValue());
			RunningData.setRevWordPerDay(revWord.getValue());
			RunningData.setWordsForTest(testWord.getValue());
			btnSave.setDisable(true);
		});
	}
}
