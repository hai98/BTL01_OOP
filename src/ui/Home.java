package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class tạo & điều khiển giao diện chào mừng
 */
public class Home implements Initializable{

	@FXML
	private HBox mainPane;

	@FXML
	private JFXButton btnHome;

	@FXML
	private JFXButton btnStudy;

	@FXML
	private JFXButton btnLookUp;

	@FXML
	private JFXButton btnManage;

	@FXML
	private JFXButton btnStatistics;

	@FXML
	private JFXButton btnSettings;

	@FXML
	private GridPane gridPane;

	private JFXButton lastBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		VBox lookUp;
		lastBtn = btnHome;
		VBox welcome;
		VBox manage;
		HBox study;
		VBox statistics;
		VBox settings;

		try {
			lookUp = FXMLLoader.load(getClass().getResource("view/look_up_view.fxml"));
			welcome = FXMLLoader.load(getClass().getResource("view/home_welcome.fxml"));
			manage = FXMLLoader.load(getClass().getResource("view/manage_view.fxml"));
			study = FXMLLoader.load(getClass().getResource("view/study_view.fxml"));
			settings = FXMLLoader.load(getClass().getResource("view/settings_view.fxml"));

			setMainPane(welcome);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		btnLookUp.setOnAction(event -> {
			setMainPane(lookUp);
			setButtonBackground(btnLookUp);
		});

		btnHome.setOnAction(event -> {
//			setMainPane(welcome);
			setMainPane(loadWelcome());
			setButtonBackground(btnHome);
		});

		btnManage.setOnAction(event -> {
			setMainPane(manage);
			setButtonBackground(btnManage);
		});

		btnStudy.setOnAction(event -> {
			setMainPane(study);
			setButtonBackground(btnStudy);
		});

		btnStatistics.setOnAction(event -> {
//			setMainPane(statistics);
			setMainPane(loadStatistic());
			setButtonBackground(btnStatistics);
		});

		btnSettings.setOnAction(event -> {
			setMainPane(settings);
			setButtonBackground(btnSettings);
		});
	}

	private VBox loadStatistic(){
		try {
			return FXMLLoader.load(getClass().getResource("view/statistics_view.fxml"));
		}catch (IOException e){
			throw new RuntimeException(e);
		}
	}

	private VBox loadWelcome(){
		try {
			return FXMLLoader.load(getClass().getResource("view/home_welcome.fxml"));
		} catch (IOException e){
			throw new RuntimeException(e);
		}
	}

	private void setMainPane(Node node){
		mainPane.getChildren().clear();
		mainPane.getChildren().add(node);
		HBox.setHgrow(node, Priority.ALWAYS);
	}

	private void setButtonBackground(JFXButton btn){
		lastBtn.setStyle("");
		btn.setStyle("-fx-background-color: #787878;");
		lastBtn = btn;
	}

	@FXML
	private void setOnMoEn(MouseEvent event){
		if (event.getSource() != lastBtn)
			((JFXButton) event.getSource()).setBlendMode(BlendMode.GREEN);
	}

	@FXML
	private void setOnMoEx(MouseEvent event){
		((JFXButton) event.getSource()).setBlendMode(null);
	}
}
