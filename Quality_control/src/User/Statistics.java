package User;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Controller;

public class Statistics {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox Box;

    @FXML
    private FontAwesomeIcon BackToLogin;

    @FXML
    void Esc(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                Controller.CurrentStage.close();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("UserChoice.fxml"));
                Parent page = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(page));
                stage.setTitle("Shop");
                Controller.CurrentStage = stage;
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    void back(MouseEvent event) {
        try {
            Controller.CurrentStage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("UserChoice.fxml"));
            Parent page = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(page));
            stage.setTitle("Shop");
            Controller.CurrentStage = stage;
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        try {
            String num = (String) Controller.getCois().readObject();
            //System.out.print(num + "\n");
            CategoryAxis xAxis = new CategoryAxis();
            xAxis.setLabel("Товары");

            NumberAxis yAxis = new NumberAxis();
            yAxis.setLabel("Оценка");

            BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);

            XYChart.Series[] data = new XYChart.Series[Integer.parseInt(num)];
            for(int i = 0;i < Integer.parseInt(num);i++)
            {
                data[i] = new XYChart.Series<String, Number>();
                data[i].setName(String.valueOf(i+1));
                String first = (String) Controller.getCois().readObject();
                String second = (String) Controller.getCois().readObject();
                String third = (String) Controller.getCois().readObject();
                data[i].getData().add(new XYChart.Data<String, Number>("Молоко", Integer.parseInt(first)));
                data[i].getData().add(new XYChart.Data<String, Number>("Творог", Integer.parseInt(second)));
                data[i].getData().add(new XYChart.Data<String, Number>("Сыр", Integer.parseInt(third)));
                barChart.getData().add(data[i]);
            }
            barChart.setTitle("Статистика социологических опросов по сессиям");
            Box.getChildren().addAll(barChart);
        }
        catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        catch(Exception e1) {
            e1.printStackTrace();
        }
    }
}