package ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import words.RunningData;

import java.net.URL;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable{
	@FXML
	VBox pane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<Integer> yValue = RunningData.getStatistics();
		System.out.println(RunningData.getLearned());
		yValue.add(9, RunningData.getLearned());
		final NumberAxis xAxis = new NumberAxis(1, 10, 1);
		final NumberAxis yAxis = new NumberAxis(0, 50, 5);
//		xAxis.setLabel("Day");
		//tạo biểu đồ
		final LineChart<Number,Number> lineChart = new LineChart<>(xAxis, yAxis);
		lineChart.setTitle("Statistics of learning new words");

		XYChart.Series<Number, Number> series = new XYChart.Series<>();

		for(int i=0; i<10; ++i){
			int x= i+1, y = yValue.get(i);
			XYChart.Data<Number, Number> point = new XYChart.Data<>(x, y);
			Tooltip t = new Tooltip(String.valueOf(y));
			t.setFont(Font.font(14));
			Label lbl = new Label();
			lbl.setFont(Font.font(1.0));
			Tooltip.install(lbl, t);
			point.setNode(lbl);
			series.getData().add(point);
		}

		series.setName("Last 10 days");
		lineChart.getData().add(series);

		pane.getChildren().add(lineChart);
		pane.setVgrow(lineChart, Priority.ALWAYS);
	}
}
