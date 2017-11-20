package ui;

import com.jfoenix.controls.JFXCheckBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import words.RunningData;
import words.Word;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
	private Button btnDelete;

	@FXML
	private Button btnEdit;

	@FXML
	private Button btnAdd;

	@FXML
	private ListView<String> historyList;

	@FXML
	private JFXCheckBox cBox;

	@FXML
	private ImageView img;

	@FXML
	private MenuItem itmClear;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

//		TextFields.bindAutoCompletion(lookUp, RunningData.getSuggestion
		key = "";
		ObservableList<String> history = RunningData.loadHistoryList();
		if(history.isEmpty()) history.add("");
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

		btnDelete.setOnAction(e -> {
			boolean conf = ConfirmationBox.show("Are you sure?", "Delete Word", "Yes", "No");
			if (conf){
				RunningData.deleteWord(key);
				setTextArea("");
			}
		});

		btnEdit.setOnAction(e-> {
			EditWordController.setKey(key);
			EditWordController.show();
			setTextArea(key);
		});

		btnAdd.setOnAction(e-> {
			AddWordController.show();
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
			if(newVal!= null) setTextArea(newVal);
		});

		cBox.setOnAction(e -> {
			seenSelect = cBox.isSelected();
//			setTextArea(lookUp.getText());
			setTextArea(key);
		});

		/*img.setOnMouseEntered(event -> {
			img.setOpacity(1.0);
		});

		img.setOnMouseExited(event -> {
			img.setOpacity(0.5);
		});*/

		itmClear.setOnAction(event -> {
			history.clear();
		});
	}
	private void setTextArea(String sKey){
		sKey = sKey.trim().toLowerCase();
		if(sKey.isEmpty()) {
			textArea.setText("");
			img.setImage(null);
		} else {
			List<Word> tmp = RunningData.searchAll(sKey);
			if(!tmp.isEmpty()){
				btnDelete.setDisable(false);
				btnEdit.setDisable(false);
				textArea.setText(tmp.get(0).getEn());
				textArea.appendText("\n-------------------(" +tmp.size()+ ")---------------------\n");
				for (Word i : tmp) {
					String imgPath = i.getImgPath();
					if(imgPath != null){
						File f;
						if(imgPath.contains("/") || imgPath.contains("\\")) {
							f = new File(imgPath);
						} else {
							f = new File("res/img/"+imgPath);
						}
//						img.setImage(new Image("file:"+i.getImgPath()));
						img.setImage(new Image(f.toURI().toString()));
					}else img.setImage(null);
					if(seenSelect) {
						if (i.isSeen()) {
							textArea.appendText(String.format("%n+    %s    (%s)", i.getVi(), i.getTopic()));
						}
						else textArea.appendText("\nBạn chưa học từ này");
					} else {
						textArea.appendText(String.format("%n+    %s    (%s)", i.getVi(), i.getTopic()));
					}
				}
				key = tmp.get(0).getEn();
			}else {
				textArea.setText("Không tìm thấy từ vừa nhập :((\nBạn có thể thêm từ mới");
				img.setImage(null);
				btnDelete.setDisable(true);
				btnEdit.setDisable(true);
			}
		}
	}
}
//todo set default image