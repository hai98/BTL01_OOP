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

public class AddWordController implements Initializable {

	private static Stage stage;
	private ObservableList<String> topics = FXCollections.observableArrayList(RunningData.getTopics());


	@FXML
	private TextField en;

	@FXML
	private TextArea vi;

	@FXML
	private Label warning;

	@FXML
	private Label message;

	@FXML
	private Button btnOk;

	@FXML
	private Button btnCancel;

	@FXML
	private ComboBox<String> topicBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		topicBox.setItems(topics);
		btnOk.setDisable(true);

		en.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.isEmpty()) btnOk.setDisable(true);
			else {
				List<Word> t = RunningData.searchAll(newValue);
				if (!t.isEmpty()) {
					warning.setText("Từ đã tồn tại");
					btnOk.setDisable(true);
				} else {
					warning.setText("");
					btnOk.setDisable(false);
				}
			}
		});

		/*vi.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.isEmpty()) btnOk.setDisable(true);
			else btnOk.setDisable(false);
		});*/

		btnOk.setOnAction(event -> {
			if(vi.getText().isEmpty()){
				warning.setText("Nghĩa tiếng việt trống?");
			}else if (topicBox.getSelectionModel().isEmpty()){
				warning.setText("Chưa chọn chủ đề?");
			}else {
				warning.setText("");
				Word t = new Word(en.getText(), vi.getText(), topicBox.getSelectionModel().getSelectedItem(), false);
				RunningData.addWord(t);
				message.setText("Đã thêm từ");
			}
		});

		btnCancel.setOnAction(e-> {
			stage.close();
		});
	}

	void show(String title){
		try {
			VBox pane = FXMLLoader.load(getClass().getResource("add_word_view.fxml"));
			Scene scene = new Scene(pane);
			stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(title);
			stage.setScene(scene);
			stage.showAndWait();
		}catch (IOException ex){
			throw new RuntimeException(ex);
		}
	}

}
