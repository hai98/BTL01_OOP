package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import words.Delete;
import words.RunningData;
import words.Search;
import words.Word;

import java.net.URL;
import java.util.ResourceBundle;

public class LookUpController implements Initializable {

	private String key;

	@FXML
	private TextField lookUp;

	@FXML
	private Button btnSearch;

	@FXML
	private TextArea textArea;

	@FXML
	private Button btnClose;

	@FXML
	private Button btnDelete;

	@FXML
	private MenuItem itmClose;

	@FXML
	private ListView<String> historyList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

//		TextFields.bindAutoCompletion(lookUp, RunningData.getSuggestionList());
		ObservableList<String> history = FXCollections.observableArrayList();
		history.addAll("apple", "orange", "pineapple", "grape", "strawberry", "coconut");
		historyList.setItems(history);
		btnDelete.setDisable(true);

		btnSearch.setOnAction(e -> {
			if (!lookUp.getText().isEmpty())
				setTextArea(lookUp.getText());
		});

		btnClose.setOnAction(e -> {
			Stage s = GUIMain.getMainStage();
			s.close();
		});

		btnDelete.setOnAction(e -> {
			boolean conf = ConfirmationBox.show("Are you sure?", "Delete Word", "Yes", "No");
			if (conf){
				Delete.deleteWord(key);
				setTextArea("");
			}
		});

		lookUp.textProperty().addListener((observable, oldValue, newValue) -> {
			setTextArea(newValue);
		});

		lookUp.setOnAction(e -> {
			if (!lookUp.getText().isEmpty() && lookUp.getText().compareTo(history.get(0)) != 0)
				history.add(0, lookUp.getText());
		});

		itmClose.setOnAction(e -> {
			GUIMain.getMainStage().close();
		});

		historyList.getSelectionModel().selectedItemProperty().addListener((ov, oldVal, newVal) -> {
			setTextArea(newVal);
		});
	}
	private void setTextArea(String sKey){
		if(sKey.isEmpty()) {
			textArea.setText("");
		} else {
			Word tmp = Search.searchAll(sKey);
			if(tmp != null){
				btnDelete.setDisable(false);
				textArea.setText(tmp.toString());
				key = tmp.getEn();
			}else {
				textArea.setText("Word not found :((\nYou can add new word");
				btnDelete.setDisable(true);
			}
		}
	}
}
