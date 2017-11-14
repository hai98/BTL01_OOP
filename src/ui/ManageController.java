package ui;

import javafx.animation.RotateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import words.RunningData;
import words.Word;
import words.WordCollection;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageController implements Initializable{
	@FXML
	private Button btnNew;

	@FXML
	private Button btnDel;

	@FXML
	private Button btnMerge;

	@FXML
	private MenuButton btnMore;

	@FXML
	private MenuItem itmImport;

	@FXML
	private MenuItem itmExport;

	@FXML
	private MenuItem itmDel;

	@FXML
	private MenuItem itmDetails;

	@FXML
	private TableView<WordCollection> tableView;

	@FXML
	private TableColumn colTopics;

	@FXML
	private TableColumn colWords;

	@FXML
	private Button btnRefresh;

	private ObservableList<WordCollection> topics;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		topics = FXCollections.observableArrayList(RunningData.getCollectionList());
		colTopics.setCellValueFactory(new PropertyValueFactory<WordCollection, String>("topic"));
		colWords.setCellValueFactory(new PropertyValueFactory<WordCollection, Integer>("size"));
		tableView.setItems(topics);


		btnNew.setOnAction(event -> {
			AddTopicController.show();
			refresh();
		});

		btnRefresh.setOnAction(event -> refresh());

		btnDel.setOnAction(event -> {
			WordCollection t = tableView.getSelectionModel().getSelectedItem();
			if(t!=null){
				RunningData.deleteCollection(t);
			}
			refresh();
		});

		btnMerge.setOnAction(event -> {
			MergeController.show();
			refresh();
		});

		colTopics.setCellFactory(TextFieldTableCell.forTableColumn());
		colTopics.setOnEditCommit(event -> {
			TableColumn.CellEditEvent<WordCollection, String> ce;
			ce = (TableColumn.CellEditEvent<WordCollection, String>) event;
			WordCollection t = ce.getRowValue();
			t.setTopic(ce.getNewValue());
		});

		itmDel.setOnAction(event -> {
			WordCollection t = tableView.getSelectionModel().getSelectedItem();
			if(t!=null){
				RunningData.deleteCollection(t);
			}
			refresh();
		});

		itmDetails.setOnAction(event -> {
			WordCollection t = tableView.getSelectionModel().getSelectedItem();
			if(t!= null){
				DetailsController.t = t;
				DetailsController.show();

				refresh();
			}
		});

		itmImport.setOnAction(event -> {
			FileChooser fc = new FileChooser();
			fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel file *.xlsx", "*.xlsx"));
			File selectedFile = fc.showOpenDialog(null);
			if(selectedFile != null){
				RunningData.addCollection(selectedFile);
				MessageBox.show("Added new collection :))", "Info");
			}
		});

		itmExport.setOnAction(event -> {
			WordCollection t = tableView.getSelectionModel().getSelectedItem();
			if(t!=null) {
				FileChooser fc = new FileChooser();
				fc.setTitle("Export to");
				fc.setInitialFileName("exported.xlsx");
				File file = fc.showSaveDialog(null);
				if (file != null) {
					RunningData.export(file, t);
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText("Done");
					alert.setTitle("Info");
					alert.showAndWait();
				}
			}else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Info");
				alert.setHeaderText("Please choose a collection");
				alert.showAndWait();
			}
		});
	}

	private void refresh(){
		topics.clear();
		topics.addAll(RunningData.getCollectionList());
	}
}
//todo details windows for a wordcollection