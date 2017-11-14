package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import words.RunningData;
import words.Word;
import words.WordCollection;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MergeController implements Initializable{
	private ObservableList<WordCollection> topics;
	private static Stage stage;

	@FXML
	private JFXComboBox<WordCollection> coll1;

	@FXML
	private JFXComboBox<WordCollection> coll2;

	@FXML
	private JFXTextField nameF;

	@FXML
	private JFXButton btnMerge;

	@FXML
	private JFXButton btnCancel;

	@FXML
	private Label warning;

	@FXML
	private Label message;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		topics = FXCollections.observableArrayList(RunningData.getCollectionList());
		coll1.setItems(topics);
		coll2.setItems(topics);

		btnCancel.setOnAction(event -> stage.close());
		btnMerge.setOnAction(event -> {
			message.setText("");
			WordCollection tp1 = coll1.getValue();
			WordCollection tp2 = coll2.getValue();
			String name = nameF.getText();
			if(tp1==null){
				warning.setText("Collection1 is missing");
			}else if(tp2==null){
				warning.setText("Collection2 is missing");
			}else if(tp1 == tp2){
				warning.setText("Choose same collection are not allowed");
			} else if(name.trim().isEmpty()){
				warning.setText("Collection name not valid");
			}else if(RunningData.checkTopicName(name.trim())) {
				warning.setText("Collection name existed");
			} else {
				warning.setText("");
				RunningData.merge(tp1, tp2, name.trim());
				message.setText("Merged");
			}
		});

		coll2.setOnAction(event -> {
			WordCollection t = coll1.getValue();
			if(t!=null && nameF.getText().trim().isEmpty()){
				nameF.setText(t.getTopic() + " & " + coll2.getValue().getTopic());
			}
		});
	}

	public static void show(){
		try {
			VBox pane = FXMLLoader.load(MergeController.class.getResource("view/merge_view.fxml"));
			Scene scene = new Scene(pane);
			stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Merge");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.showAndWait();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
