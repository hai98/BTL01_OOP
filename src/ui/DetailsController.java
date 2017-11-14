package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import words.Word;
import words.WordCollection;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailsController implements Initializable{
	static WordCollection t;
	private ObservableList<Word> list;
	private static Stage stage;

	@FXML
	private TableColumn colEn;

	@FXML
	private TableColumn colVi;

	@FXML
	private TableColumn colImg;

	@FXML
	private Button btnAdd;

	@FXML
	private Button btnDel;

	@FXML
	private Button btnEdit;

	@FXML
	private TableView<Word> tableView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		list = FXCollections.observableArrayList(t.getWordList().values());
		colEn.setCellValueFactory(new PropertyValueFactory<Word, String>("en"));
		colVi.setCellValueFactory(new PropertyValueFactory<Word, String>("vi"));
		colImg.setCellValueFactory(new PropertyValueFactory<Word, String>("imgPath"));
		tableView.setItems(list);
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		btnDel.setOnAction(event -> {
			ObservableList<Word> list = tableView.getSelectionModel().getSelectedItems();
			for (Word i : list) {
				t.deleteWord(i.getEn());
			}
			refresh();
		});

		btnAdd.setOnAction(event -> {
			AddWordController.t = t;
			AddWordController.show();
			refresh();
		});

		btnEdit.setOnAction(event -> {
			Word w = tableView.getSelectionModel().getSelectedItem();
			EditWordController.setKey(w.getEn());
			EditWordController.t = t;
			EditWordController.show();
			refresh();
		});
	}

	static void show(){
		try {
			VBox pane = FXMLLoader.load(DetailsController.class.getResource("view/details_view.fxml"));
			Scene scene = new Scene(pane);
			stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Details: " + t.getTopic());
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private void refresh(){
		list.clear();
		list.addAll(t.getWordList().values());
	}
}
