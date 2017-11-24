package ui;

import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import words.RunningData;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeWelcomeController implements Initializable {
	@FXML
	private Label lblLea;

	@FXML
	private Label lblRev;

	@FXML
	private JFXCheckBox cboxLea;

	@FXML
	private JFXCheckBox cboxRev;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblLea.setText("Learn "+ RunningData.getNewWordPerDay() +" new words");
		lblRev.setText("Review "+ RunningData.getRevWordPerDay() +" words");
		cboxLea.setSelected(RunningData.complete[0]);
		cboxRev.setSelected(RunningData.complete[1]);
	}
}
