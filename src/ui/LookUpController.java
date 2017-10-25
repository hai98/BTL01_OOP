package ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import words.Search;

public class LookUpController {
	@FXML
	private TextField lookUp;

	@FXML
	private Button search;

	@FXML
	private TextArea textArea;

	@FXML
	private Label label;

	@FXML
	private Button btn;

//	@FXML
//	private TextField au;

	@FXML
	private void initialize(){
//		String[] possibe = {"hi", "hello", "how are you", "haha"};

//		TextFields.bindAutoCompletion(au, possibe);

		search.setOnAction(e -> {
			label.setText(Search.searchAll(lookUp.getText()).toString());
		});

		btn.setOnAction(e -> {
			Stage s = GUIMain.getTmp();
			s.close();
		});

	}
}
