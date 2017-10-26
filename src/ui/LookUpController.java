package ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import words.RunningData;
import words.Search;

public class LookUpController {
	@FXML
	private TextField lookUp;

	@FXML
	private Button btnSearch;

	@FXML
	private TextArea textArea;

	@FXML
	private Button btnClose;

	@FXML
	private Button btnEdit;

	@FXML
	private MenuBar menuBar;

	@FXML
	private MenuItem close;

	@FXML
	private void initialize(){

		TextFields.bindAutoCompletion(lookUp, RunningData.getSuggestionList());

		btnSearch.setOnAction(e -> {
//			label.setText(Search.searchAll(lookUp.getText()).toString());
			textArea.setText(Search.searchAll(lookUp.getText()).toString());
		});

		btnClose.setOnAction(e -> {
			Stage s = GUIMain.getMainStage();
			s.close();
		});

		lookUp.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.isEmpty())
				textArea.setText("");
			else
				textArea.setText(Search.searchAll(newValue).toString());
		});

		close.setOnAction(e -> {
			GUIMain.getMainStage().close();
		});
	}

}
