package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import words.RunningData;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class tạo & điều khiển giao diện thêm chủ đề
 */
public class AddTopicController implements Initializable {
	private static Stage stage;

	@FXML
	private JFXTextField topic;

	@FXML
	private JFXButton btnOk;

	@FXML
	private JFXButton btnClose;

	@FXML
	private Label warning;

	@FXML
	private Label message;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnOk.setDisable(true);

		btnClose.setOnAction(event -> {
			stage.close();
		});

		topic.textProperty().addListener(((observable, oldValue, newValue) -> {
			message.setText("");
			if(newValue.isEmpty()){
				btnOk.setDisable(true);
			} else if(RunningData.checkTopicName(newValue.trim())){
				warning.setText("Tên chủ đề đã tồn tại");
				btnOk.setDisable(true);
			}else {
				warning.setText("");
				btnOk.setDisable(false);
			}
		}));

		btnOk.setOnAction(event -> {
			RunningData.createTopic(topic.getText());
			message.setText("Đã thêm chủ đề mới");
			btnOk.setDisable(true);
		});
	}

	public static void show() {
		try {
			VBox pane = FXMLLoader.load(AddTopicController.class.getResource("view/add_topic_view.fxml"));
			Scene scene = new Scene(pane);
			stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Thêm chủ đề mới");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.showAndWait();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
