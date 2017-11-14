package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
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
import words.WordCollection;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddWordController implements Initializable {

	private static Stage stage;
	static WordCollection t = null;
	private ObservableList<WordCollection> topics;


	@FXML
	private JFXTextField en;

	@FXML
	private JFXTextArea vi;

	@FXML
	private Label warning;

	@FXML
	private Label message;

	@FXML
	private JFXButton btnAddTopic;

	@FXML
	private JFXButton btnOk;

	@FXML
	private JFXButton btnCancel;

	@FXML
	private JFXComboBox<WordCollection> topicBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		topics = FXCollections.observableArrayList(RunningData.getCollectionList());
		topicBox.setItems(topics);
		if (en.getText().isEmpty()) btnOk.setDisable(true);

		en.textProperty().addListener((observable, oldValue, newValue) -> {
			warning.setText("");
			message.setText("");
			if (newValue.isEmpty()) btnOk.setDisable(true);
			else {
				btnOk.setDisable(false);
			}
		});

		/*vi.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.isEmpty()) btnOk.setDisable(true);
			else btnOk.setDisable(false);
		});*/

		btnOk.setOnAction(event -> {
			message.setText("");
			if(vi.getText().trim().isEmpty()){
				warning.setText("Nghĩa tiếng việt trống?");
			}else if (topicBox.getSelectionModel().isEmpty()){
				warning.setText("Chưa chọn chủ đề?");
			}else if(topicBox.getSelectionModel().getSelectedItem().containsWord(en.getText())){
				warning.setText("Từ đã tồn tại trong chủ đề này");
			}
			else {
				warning.setText("");
				WordCollection t = topicBox.getValue();
				Word w = new Word(en.getText(), vi.getText(), t.getTopic(), false);
				t.putWord(w);
				message.setText("Đã thêm từ");
				btnOk.setDisable(true);
			}
		});

		btnCancel.setOnAction(e-> {
			t = null;
			stage.close();
		});

		btnAddTopic.setOnAction(event -> {
			AddTopicController.show();
			topics.clear();
			topics.addAll(RunningData.getCollectionList());
		});

		topicBox.setOnAction(event -> {
			warning.setText("");
			message.setText("");
		});

		if (t!=null){
			topicBox.getSelectionModel().select(t);
			topicBox.setDisable(true);
			btnAddTopic.setDisable(true);
		}
	}

	static void show(){
		try {
			VBox pane = FXMLLoader.load(AddWordController.class.getResource("view/add_word_view.fxml"));
			Scene scene = new Scene(pane);
			stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Thêm từ mới");
			stage.setResizable(false);
			stage.setScene(scene);
			stage.showAndWait();
		}catch (IOException ex){
			throw new RuntimeException(ex);
		}
	}

}
