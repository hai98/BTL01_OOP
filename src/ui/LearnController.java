package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import words.RunningData;
import words.Word;
import words.WordCollection;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.ResourceBundle;

public class LearnController implements Initializable {
	static Stage stage;
	static boolean exit = false;
	static boolean reviewMode = false;
	static WordCollection t = null;
	private Queue<Word> words;
	private Word cur;
	private PriorityQueue<Word> pQueue;
	private int cE,cG,cH;

	@FXML
	private JFXProgressBar proBar;

	@FXML
	private Label en;

	@FXML
	private Label vi;

	@FXML
	private HBox congrat;

	@FXML
	private JFXButton btnShow;

	@FXML
	private JFXButton btnHard;

	@FXML
	private JFXButton btnEasy;

	@FXML
	private JFXButton btnGood;

	@FXML
	private ImageView imgView;

	@FXML
	private Label percent;

	@FXML
	private Label cEasy;

	@FXML
	private Label cHard;

	@FXML
	private Label cGood;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		congrat.setOpacity(0.0);
		vi.setOpacity(0.0);
		if (t!=null) {
			words = RunningData.prepareForLearn(t);
			reviewMode = false;
		}
		else {
			words = RunningData.prepareForReview();
			reviewMode = true;
		}
		if (words == null){
			MessageBox.show("No words for review", "Info");
			exit = true;
		} else {
			exit = false;
			pQueue = new PriorityQueue<>(words.size());
			Queue<String> queueReview = RunningData.getQueueReview();
			cur = next();
			cE = 0;
			cG = 0;
			cH = 0;

			btnShow.setOnAction(event -> {
				vi.setOpacity(1.0);
				btnGood.setDisable(false);
				btnEasy.setDisable(false);
				btnHard.setDisable(false);
			});

			btnEasy.setOnAction(event -> {
				if (cur.getLevel() == 2) --cG;
				else if (cur.getLevel() == 3) --cH;
				++cE;
				if (!reviewMode) {
					cur.setSeen(true);
					queueReview.offer(cur.getEn());
					t.incLearned();
				}
				cur = next();
			});

			btnHard.setOnAction(event -> {
				cur.setLevel(3);
				++cH;
				pQueue.add(cur);
				cur = next();
			});

			btnGood.setOnAction(event -> {
				cur.setLevel(2);
				++cG;
				pQueue.add(cur);
				cur = next();
			});
		}
	}

	static void show(){
		try {
			VBox pane = FXMLLoader.load(LearnController.class.getResource("view/learn_view.fxml"));
			if(exit) return;
			stage = new Stage();
			Scene scene = new Scene(pane);
			stage.initModality(Modality.APPLICATION_MODAL);
			if (reviewMode) stage.setTitle("Review");
			else stage.setTitle("Learn");
//			stage.setResizable(false);
			stage.setScene(scene);
			stage.showAndWait();
		}catch (IOException ex){
			throw new RuntimeException(ex);
		}
	}

	private void setImg(String imgPath){
		File f;
		if(imgPath != null){
			if(imgPath.contains("/") || imgPath.contains("\\")) {
				f = new File(imgPath);
			} else {
				f = new File("res/img/"+imgPath);
			}
		}else f = new File("res/img/no_image.png");
		imgView.setImage(new Image(f.toURI().toString()));
	}

	private Word next(){
		vi.setOpacity(0.0);
		btnGood.setDisable(true);
		btnEasy.setDisable(true);
		btnHard.setDisable(true);
		cHard.setText(String.valueOf(cH));
		cGood.setText(String.valueOf(cG));
		cEasy.setText(String.valueOf(cE));
		Word w;
		if(!words.isEmpty()) {
			w = words.poll();

		}else if(!pQueue.isEmpty()) {
			w = pQueue.poll();
		}else {
			congrat.setOpacity(1.0);
			btnShow.setDisable(true);
			return null;
		}
		en.setText(w.getEn());
		vi.setText(w.getVi());
		setImg(w.getImgPath());
		proBar.setProgress((5 - words.size() - pQueue.size()) / 5.0);
		percent.setText(String.format("%.1f%%", proBar.getProgress()*100));
		return w;
	}
}
