package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import words.RunningData;
import words.Word;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LookUpController implements Initializable {

	private String key;
	private boolean seenSelect;

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
	private Button btnEdit;

	@FXML
	private Button btnAdd;

	@FXML
	private ListView<String> historyList;

	@FXML
	private CheckBox cBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

//		TextFields.bindAutoCompletion(lookUp, RunningData.getSuggestionList());
		ObservableList<String> history = FXCollections.observableArrayList();
		history.addAll("apple", "orange", "pineapple", "grape", "strawberry", "coconut");
		historyList.setItems(history);
		btnDelete.setDisable(true);
		btnEdit.setDisable(true);

		btnSearch.setOnAction(e -> {
			if (!lookUp.getText().isEmpty()) {
				setTextArea(lookUp.getText());
				if (lookUp.getText().compareTo(history.get(0)) != 0)
					history.add(0, lookUp.getText());
			}

		});

		btnClose.setOnAction(e -> {
			Stage s = GUIMain.getMainStage();
			s.close();
		});

		btnDelete.setOnAction(e -> {
			boolean conf = ConfirmationBox.show("Are you sure?", "Delete Word", "Yes", "No");
			if (conf){
				RunningData.deleteWord(key);
				setTextArea("");
			}
		});

//		btnEdit.setOnAction(e-> {
//
//		});

		btnAdd.setOnAction(e-> {
			AddWordController t = new AddWordController();
			t.show("Thêm từ mới");
//			t.setEn(lookUp.getText().trim().toLowerCase());
		});

		lookUp.textProperty().addListener((observable, oldValue, newValue) -> {
			setTextArea(newValue);
		});

		lookUp.setOnAction(e -> {
			setTextArea(lookUp.getText());
			if (!lookUp.getText().isEmpty() && lookUp.getText().compareTo(history.get(0)) != 0)
				history.add(0, lookUp.getText());
		});

		historyList.getSelectionModel().selectedItemProperty().addListener((ov, oldVal, newVal) -> {
			setTextArea(newVal);
		});

		cBox.setOnAction(e -> {
			seenSelect = cBox.isSelected();
			setTextArea(lookUp.getText());
		});
	}
	private void setTextArea(String sKey){
		sKey = sKey.trim().toLowerCase();
		if(sKey.isEmpty()) {
			textArea.setText("");
		} else {
			List<Word> tmp = RunningData.searchAll(sKey);
			if(!tmp.isEmpty()){
				btnDelete.setDisable(false);
				btnEdit.setDisable(false);
				textArea.setText(tmp.get(0).getEn());
				textArea.appendText("\n-------------------("+tmp.size()+")---------------------\n");
				for (Word i : tmp) {
					if(seenSelect) {
						if (i.isSeen())
							textArea.appendText(String.format("%n+    %s    (%s)", i.getVi(), i.getTopic()));
						else textArea.appendText("\nBạn chưa học từ này");
					} else {
						textArea.appendText(String.format("%n+    %s    (%s)", i.getVi(), i.getTopic()));
					}
				}
				key = tmp.get(0).getEn();
			}else {
				textArea.setText("Word not found :((\nYou can add new word");
				btnDelete.setDisable(true);
				btnEdit.setDisable(true);
			}
		}
	}
}
