package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ResourceBundle;

public class TestResultController implements Initializable {
	@FXML
	private Label rate;

	@FXML
	private Label rank;

	@FXML
	private TableView<Result> resultTableView;

	@FXML
	private TableColumn<Result, Integer> colN;

	@FXML
	private TableColumn<Result, Boolean> colRes;

	@FXML
	private TableColumn<Result, String> colWord;

	@FXML
	private TableColumn<Result, String> colUser;

	@FXML
	private TableColumn<Result, String> colRight;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colN.setCellValueFactory(new PropertyValueFactory<>("n"));
		colRes.setCellValueFactory(new PropertyValueFactory<>("result"));
		colWord.setCellValueFactory(new PropertyValueFactory<>("word"));
		colUser.setCellValueFactory(new PropertyValueFactory<>("userAns"));
		colRight.setCellValueFactory(new PropertyValueFactory<>("rightAns"));
		ObservableList<Result> results = TestController.results;
		resultTableView.setItems(results);
		rate.setText(String.format("%d/%d", Result.getRate(), results.size()));
		double d = (double) Result.getRate()/results.size();
		if(d < 0.3) {
			rank.setText("(Very Poor)");
			rank.setTextFill(Paint.valueOf("RED"));
			rate.setTextFill(Paint.valueOf("RED"));
		}else if(d < 0.5){
			rank.setText("(Poor)");
			rank.setTextFill(Paint.valueOf("RED"));
			rate.setTextFill(Paint.valueOf("RED"));
		}else if(d < 0.7){
			rank.setText("(Average)");
			rank.setTextFill(Paint.valueOf("ORANGE"));
			rate.setTextFill(Paint.valueOf("ORANGE"));
		}else if(d < 0.9){
			rank.setText("(Good)");
			rank.setTextFill(Paint.valueOf("BLUE"));
			rate.setTextFill(Paint.valueOf("BLUE"));
		}else {
			rank.setText("(Excellent)");
			rank.setTextFill(Paint.valueOf("GREEN"));
			rate.setTextFill(Paint.valueOf("GREEN"));
		}
	}
}
