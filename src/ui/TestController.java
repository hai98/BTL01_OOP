package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import words.RunningData;
import words.Word;
import words.WordCollection;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class TestController implements Initializable{
	private static Stage stage;
	static WordCollection t;
	private List<Word> quest;
	private List<Word> ans;
	private List<Word> userAns;
	private Iterator<Word> questIt;
	private Iterator<Boolean> randIt;
	private Word[] ar;
	static ObservableList<Result> results = FXCollections.observableArrayList();
	private List<Boolean> rand;

	@FXML
	private Label lblQues;

	@FXML
	private Label lblPro;

	@FXML
	private JFXRadioButton ans1;

	@FXML
	private JFXRadioButton ans2;

	@FXML
	private JFXRadioButton ans3;

	@FXML
	private JFXRadioButton ans4;

	@FXML
	private JFXButton btnNext;

	@FXML
	private JFXProgressBar prog;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		quest = RunningData.prepareForTest(t);
		ans = new ArrayList<>(t.getWordList().values());
		ans.removeAll(quest);
		userAns = new LinkedList<>();
		results.clear();
		ar = new Word[4];
		questIt = quest.iterator();
		rand = new LinkedList<>();
		for(int i=0; i<quest.size()/2; ++i){
			rand.add(true);
		}
		while (rand.size() < quest.size()){
			rand.add(false);
		}
		Collections.shuffle(rand);
		randIt = rand.iterator();


		ToggleGroup group = new ToggleGroup();
		ans1.setToggleGroup(group);
		ans2.setToggleGroup(group);
		ans3.setToggleGroup(group);
		ans4.setToggleGroup(group);

		ans1.setOnAction(event -> enableNext());
		ans2.setOnAction(event -> enableNext());
		ans3.setOnAction(event -> enableNext());
		ans4.setOnAction(event -> enableNext());

		next();


		btnNext.setOnAction(event -> next());


	}
	private void enableNext(){
		btnNext.setDisable(false);
	}

	static void show(){
		try {
			VBox pane = FXMLLoader.load(TestController.class.getResource("view/test_view.fxml"));
			Scene scene = new Scene(pane);
			stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Test");
			stage.setScene(scene);
			stage.showAndWait();
		}catch (IOException ex){
			throw new RuntimeException(ex);
		}
	}

	private void next(){
		btnNext.setDisable(true);
		if(ans1.isSelected()){
			userAns.add(ar[0]);
			ans1.setSelected(false);
		}else if(ans2.isSelected()){
			userAns.add(ar[1]);
			ans2.setSelected(false);
		}else if(ans3.isSelected()){
			userAns.add(ar[2]);
			ans3.setSelected(false);
		}else if(ans4.isSelected()){
			userAns.add(ar[3]);
			ans4.setSelected(false);
		}

		if (questIt.hasNext()) {
			Word w = questIt.next();
			if(randIt.next()) {
				lblQues.setText(w.getEn());
				Set<Word> set = new HashSet<>(4);
				while (set.size() < 4) {
					set.add(ans.get((int) (Math.random() * ans.size())));
				}
				set.toArray(ar);
				int n = (int) (Math.random() * 4);
				ar[n] = w;
				ans1.setText(ar[0].getVi());
				ans2.setText(ar[1].getVi());
				ans3.setText(ar[2].getVi());
				ans4.setText(ar[3].getVi());
			}else {
				lblQues.setText(w.getVi());
				Set<Word> set = new HashSet<>(4);
				while (set.size() < 4) {
					set.add(ans.get((int) (Math.random() * ans.size())));
				}
				set.toArray(ar);
				int n = (int) (Math.random() * 4);
				ar[n] = w;
				ans1.setText(ar[0].getEn());
				ans2.setText(ar[1].getEn());
				ans3.setText(ar[2].getEn());
				ans4.setText(ar[3].getEn());
			}
			prog.setProgress((userAns.size()+1.0)/quest.size());
			lblPro.setText(String.format("%d/%d", userAns.size()+1, quest.size()));
		} else {
			Result.setRate(0);
			for (int i=0; i<userAns.size(); ++i){
				Word q = quest.get(i);
				Word u = userAns.get(i);
				if(rand.get(i))
					results.add(new Result(i+1, q==u, q.getEn(), u.getVi(), q.getVi()));
				else results.add(new Result(i+1, q==u, q.getVi(), u.getEn(), q.getEn()));
			}
			try {
				VBox pane  = FXMLLoader.load(TestController.class.getResource("view/result_view.fxml"));
				Scene scene = new Scene(pane);
				stage.setScene(scene);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
//			for (Result r : results){
//				System.out.println(r);
//			}
		}
	}
}