package User;

import Admin.Product;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Controller;
import sample.Rez_of_Input;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class User implements Rez_of_Input {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Product> Table;

    @FXML
    private TableColumn<Product, String> nameProduct;

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
    private Button AddInBasket;

    @FXML
    private Button Basket;

    @FXML
    private Button Statistic;

    @FXML
    private TextField Search;

    @FXML
    private FontAwesomeIcon BackToLogin;

    @FXML
    void back(MouseEvent event) {
        Controller ob = new Controller();
        ob.scene("sample.fxml");
    }

    @FXML
    void Esc(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            Controller ob = new Controller();
            ob.scene("sample.fxml");
        }
    }


    @FXML
    void initialize() {
        try {
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
            nameProduct.setSortable(true);
            composition.setSortable(false);
            fats.setSortable(false);
            protein.setSortable(false);
            nameProduct.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
            Price.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
            weight.setCellValueFactory(new PropertyValueFactory<Product, Double>("weight"));
            protein.setCellValueFactory(new PropertyValueFactory<Product, Double>("protein"));
            fats.setCellValueFactory(new PropertyValueFactory<Product, Double>("fats"));
            carbohydrates.setCellValueFactory(new PropertyValueFactory<Product, Double>("carbohydrates"));
            nutritionalValue.setCellValueFactory(new PropertyValueFactory<Product, Double>("nutritionalValue"));
            composition.setCellValueFactory(new PropertyValueFactory<Product, String>("composition"));
            ObservableList<Product> list = FXCollections.observableArrayList(ob);
            Table.getItems().addAll(list);
            //Table.setItems(list);
            FilteredList<Product> filteredData = new FilteredList<>(list, e->true);
            Search.setOnKeyReleased(e-> {
                Search.textProperty().addListener(((observableValue, oldValue, newValue) -> {
                    filteredData.setPredicate((Predicate<? super Product>) product -> {
                        if(newValue == null || newValue.isEmpty())
                        {
                            return true;
                        }
                        String lowerCaseFilter = newValue.toLowerCase();
                        if(product.getProductName().toLowerCase().contains(lowerCaseFilter))
                        {
                            return true;
                        }
                        return false;
                    });
                }));
                SortedList<Product> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(Table.comparatorProperty());
                Table.setItems(sortedData);
            });
        }
        catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        catch(Exception e1) {
            e1.printStackTrace();
        }
        Statistic.setOnAction(actionEvent -> {
            try {
                Controller.getCoos().writeObject("statistics");
                Controller.CurrentStage.close();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("UsersSurvey.fxml"));
                Parent page = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(page));
                Controller.CurrentStage = stage;
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        AddInBasket.setOnAction(actionEvent -> {
            try {
                Controller.getCoos().writeObject("1");
                Controller.getCoos().writeObject(Table.getSelectionModel().getSelectedItem().getProductName());
                AddedInBasket();
                Controller.CurrentStage.close();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("UserChoice.fxml"));
                Parent page = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(page));
                stage.setTitle("Basket");
                Controller.CurrentStage = stage;
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        Basket.setOnAction(actionEvent -> {
            try {
                Controller.getCoos().writeObject("2");
                Controller.CurrentStage.close();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("UserBasket.fxml"));
                Parent page = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(page));
                stage.setTitle("Basket");
                Controller.CurrentStage = stage;
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    private void AddedInBasket()
    {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("AddedInBasket");
        window.setMinWidth(300);

        Button closeButton = new Button("Закрыть");
        closeButton.setOnAction(e-> window.close());
        Label label = new Label();
        label.setText("Товар был добавлен в корзину");

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 500,300);
        window.setScene(scene);
        window.showAndWait();
    }
    @Override
    public boolean display() {
        try {
            //String mes = "Admin";
            //Controller.getCoos().writeObject(mes);
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
        catch(Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}

