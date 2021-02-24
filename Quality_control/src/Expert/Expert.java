package Expert;

import Admin.Product;
import com.sun.glass.ui.View;
import javafx.animation.RotateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import sample.Controller;
import sample.Rez_of_Input;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;

public class Expert implements Rez_of_Input {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Product> Table;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private TableColumn<Product, Double> Price;

    @FXML
    private TableColumn<Product, Double> weight;

    @FXML
    private TableColumn<Product, Double> protein;

    @FXML
    private TableColumn<Product, Double> fats;

    @FXML
    private TableColumn<Product, Double> carbohydrates;

    @FXML
    private TableColumn<Product, Double> nutritionalValue;

    @FXML
    private TableColumn<Product, String> composition;

    @FXML
    private Button Expertise;

    @FXML
    void RightClickExpertize(ActionEvent event) {
        start_expertize(event);
    }
    @FXML
    private Label lbl1;

    @FXML
    private Label lbl2;

    @FXML
    private Label lbl3;

    @FXML
    private Label lbl4;

    @FXML
    private Text text1;

    @FXML
    private Text text2;

    @FXML
    private Text text3;

    @FXML
    private Text text4;

    @FXML
    private Text text5;


    @FXML
    private ImageView img1;

    @FXML
    private ImageView img2;

    @FXML
    private ImageView img3;

    @FXML
    private ImageView img4;

    @FXML
    private ImageView img5;

    private RotateTransition rotateTransition1 , rotateTransition2, rotateTransition3,
    rotateTransition4,rotateTransition5;
    void measure()
    {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Measures");
            window.setMinWidth(300);


            PieChart pieChart = new PieChart();
            PieChart.Data slice1 = new PieChart.Data("Белки", Table.getSelectionModel().getSelectedItem().getProtein());
            PieChart.Data slice2 = new PieChart.Data("Жиры", Table.getSelectionModel().getSelectedItem().getFats());
            PieChart.Data slice3 = new PieChart.Data("Углеводы", Table.getSelectionModel().getSelectedItem().getCarbohydrates());
            pieChart.getData().add(slice1);
            pieChart.getData().add(slice2);
            pieChart.getData().add(slice3);
            pieChart.setLegendSide(Side.LEFT);

            Label caption = new Label("");
            caption.setTextFill(Color.BLACK);
            caption.setStyle("-fx-font: 12 arial;");

            for (PieChart.Data data : pieChart.getData()) {
                data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        caption.setTranslateX(e.getSceneX());
                        caption.setTranslateY(e.getSceneY());
                        caption.setText(String.valueOf(data.getPieValue()));
                    }
                });
            }


            Label label = new Label();
            label.setText("Введите базисные свойства продукту: " + Table.getSelectionModel().getSelectedItem().getProductName());

            TextField text1 = new TextField();
            text1.setPromptText("Белки");
            TextField text2 = new TextField();
            text2.setPromptText("Жиры");
            TextField text3 = new TextField();
            text3.setPromptText("Углеводы");
            TextField text4 = new TextField();
            text4.setPromptText("Энергетическая ценность");

            Button closeButton = new Button("Провести экспертизу");
            closeButton.setOnAction(e-> {
                try {
                    Controller.getCoos().writeObject("expertize");
                    Controller.getCoos().writeObject(Table.getSelectionModel().getSelectedItem().getProductName());
                    Controller.getCoos().writeObject(text1.getText());
                    Controller.getCoos().writeObject(text2.getText());
                    Controller.getCoos().writeObject(text3.getText());
                    Controller.getCoos().writeObject(text4.getText());
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
                window.close();
            });
            VBox layout = new VBox(10);
            layout.getChildren().addAll(pieChart, caption, label, text1, text2, text3, text4, closeButton);
            layout.setAlignment(Pos.CENTER);

            Scene scene = new Scene(layout, 800,500);
            window.setScene(scene);
            window.showAndWait();

    }
    @FXML
    void Esc(KeyEvent event) {
        Controller ob = new Controller();
        ob.scene("sample.fxml");
    }
    @FXML
    void back(MouseEvent event) {
        Controller ob = new Controller();
        ob.scene("sample.fxml");
    }
    @FXML
    void start_expertize(ActionEvent event) {
        measure();
        img1.setImage(new Image("Expert/loading-56.gif"));
        text1.setText("Физическая экспертиза");
        rotateTransition1 = new RotateTransition(Duration.seconds(5), img1);
        rotateTransition2 = new RotateTransition(Duration.seconds(5), img2);
        rotateTransition3 = new RotateTransition(Duration.seconds(3), img3);
        rotateTransition4 = new RotateTransition(Duration.seconds(5), img4);
        rotateTransition5 = new RotateTransition(Duration.seconds(3), img5);
        RotateTransition[] transition = {rotateTransition1, rotateTransition2, rotateTransition3, rotateTransition4, rotateTransition5};
        for(RotateTransition rTransition : transition)
        {
            rTransition.setCycleCount(1);
            rTransition.setAutoReverse(false);
            rTransition.setFromAngle(0);
            rTransition.setToAngle(0);
        }
        rotateTransition1.play();
        rotateTransition1.setOnFinished((e) -> {
            img1.setImage(new Image("Expert/1.png"));
            lbl1.setStyle("-fx-background-color: #45A563");
            img2.setImage(new Image("Expert/loading-56.gif"));
            text2.setText("Химическая экспертиза");
            rotateTransition2.play();
        });
        rotateTransition2.setOnFinished((e) -> {
            img2.setImage(new Image("Expert/1.png"));
            lbl2.setStyle("-fx-background-color: #45A563");
            img3.setImage(new Image("Expert/loading-56.gif"));
            text3.setText("Выборка основных показателей");
            rotateTransition3.play();
        });
        rotateTransition3.setOnFinished((e) -> {
            img3.setImage(new Image("Expert/1.png"));
            lbl3.setStyle("-fx-background-color: #45A563");
            img4.setImage(new Image("Expert/loading-56.gif"));
            text4.setText("Сравнение с базовыми образцами");
            rotateTransition4.play();
        });
        rotateTransition4.setOnFinished((e) -> {
            img4.setImage(new Image("Expert/1.png"));
            lbl4.setStyle("-fx-background-color: #45A563");
            img5.setImage(new Image("Expert/loading-56.gif"));
            text5.setText("Оформление отчёта");
            rotateTransition5.play();
        });
        rotateTransition5.setOnFinished((e) -> {
            img5.setImage(new Image("Expert/1.png"));
            Expertise.setVisible(true);
        });
    }
    private void expertise()
    {
        try {
            String mes = (String) Controller.getCois().readObject();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Result");
            alert.setContentText("Результат экспертизы: " + mes);
            alert.showAndWait();
            //сделать Alert с сообщением о результатах экспертизы
            Controller.CurrentStage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ExpertChoice.fxml"));
            Parent page = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(page));
            Controller.CurrentStage = stage;
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

        }
        catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        catch(Exception e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        try {
            Expertise.setVisible(false);
            String kol = (String) Controller.getCois().readObject();
            Product[] ob = new Product[Integer.parseInt(kol)];
            for(int i = 0; i < Integer.parseInt(kol); i++)
            {
                String productName = (String) Controller.getCois().readObject();
                String price = (String) Controller.getCois().readObject();
                String weight = (String) Controller.getCois().readObject();
                String protein = (String) Controller.getCois().readObject();
                String fats = (String) Controller.getCois().readObject();
                String carbohydrates = (String) Controller.getCois().readObject();
                String nutritionalValue = (String) Controller.getCois().readObject();
                String composition = (String) Controller.getCois().readObject();
                ob[i] = new Product(productName, Double.parseDouble(price), Double.parseDouble(weight), Double.parseDouble(protein),
                        Double.parseDouble(fats),Double.parseDouble(carbohydrates),Double.parseDouble(nutritionalValue),composition);
            }
            productName.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
            Price.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
            weight.setCellValueFactory(new PropertyValueFactory<Product, Double>("weight"));
            protein.setCellValueFactory(new PropertyValueFactory<Product, Double>("protein"));
            fats.setCellValueFactory(new PropertyValueFactory<Product, Double>("fats"));
            carbohydrates.setCellValueFactory(new PropertyValueFactory<Product, Double>("carbohydrates"));
            nutritionalValue.setCellValueFactory(new PropertyValueFactory<Product, Double>("nutritionalValue"));
            composition.setCellValueFactory(new PropertyValueFactory<Product, String>("composition"));
            ObservableList<Product> list = FXCollections.observableArrayList(ob);
            Table.getItems().addAll(list);

        }
        catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        catch(Exception e1) {
            e1.printStackTrace();
        }
        Expertise.setOnAction(actionEvent -> {
            expertise();
        });
    }
    @Override
    public boolean display() {
        try {
            //String mes = "Admin";
            //Controller.getCoos().writeObject(mes);
            Controller.CurrentStage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ExpertChoice.fxml"));
            Parent page = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(page));
            Controller.CurrentStage = stage;
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
