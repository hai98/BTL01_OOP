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
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import words.RunningData;
import words.Word;
import words.WordCollection;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Class tạo & điều khiển giao diện sửa từ
 */
public class EditWordController implements Initializable {
	private static String key;
	private static Stage stage;
	static WordCollection t;

	static void setKey(String key) {
		EditWordController.key = key;
	}

	@FXML
	private JFXTextField en;

	@FXML
	private JFXTextArea vi;

	@FXML
	private Label warning;

	@FXML
	private JFXButton btnOk;

	@FXML
	private JFXButton btnCancel;

	@FXML
	private JFXButton btnChon;

	@FXML
	private JFXTextField imgPath;

	@FXML
	private JFXComboBox<WordCollection> topicBox;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		en.setText(key);
		ObservableList<WordCollection> topics = FXCollections.observableArrayList();
		if (t == null) {
			for (WordCollection i : RunningData.getCollectionList()) {
				if (i.containsWord(key))
					topics.add(i);
			}

			topicBox.setItems(topics);
			if (topics.size() == 1) {
				topicBox.getSelectionModel().select(0);
				vi.setText(topics.get(0).getWord(key).getVi());
				imgPath.setText(topics.get(0).getWord(key).getImgPath());
			}
		}else {
			topics.add(t);
			topicBox.setItems(topics);
			topicBox.getSelectionModel().select(t);
			topicBox.setDisable(true);
			vi.setText(t.getWord(key).getVi());
			imgPath.setText(t.getWord(key).getImgPath());
		}
		topicBox.setOnAction(event -> {
			vi.setText(topicBox.getValue().getWord(key).getVi());
			imgPath.setText(topicBox.getValue().getWord(key).getImgPath());
		});

		vi.setOnKeyTyped(event -> {
			btnOk.setDisable(false);
		});

		btnOk.setOnAction(event -> {
			if(vi.getText().trim().isEmpty()){
				warning.setText("Nghĩa tiếng việt trống?");
				btnOk.setDisable(true);
			}else {
				Word w = topicBox.getValue().getWord(key);
				w.setVi(vi.getText().trim());
				w.setImgPath(imgPath.getText());
//				topicBox.getValue().putWord(w);
				t = null;
				stage.close();
			}
		});

		btnChon.setOnAction(event -> {
			FileChooser fc = new FileChooser();
			fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image File (*.jpg, *.png, *.gif)", "*.jpg", "*.png", "*.gif"));
			File selectedFile = fc.showOpenDialog(null);
			if(selectedFile != null)
				imgPath.setText(selectedFile.getAbsolutePath());
		});

		btnCancel.setOnAction(e-> {
			t = null;
			stage.close();
		});

	}

	static void show(){
		try {
			VBox pane = FXMLLoader.load(EditWordController.class.getResource("view/edit_word_view.fxml"));
			Scene scene = new Scene(pane);
			stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Sửa từ");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.showAndWait();
		}catch (IOException ex){
			throw new RuntimeException(ex);
		}
	}
}
