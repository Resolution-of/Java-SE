package Admin;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import sample.Controller;
import sample.Rez_of_Input;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.event.ChangeListener;
import java.net.Socket;

public class Admin implements Rez_of_Input{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Product> Table;

    @FXML
    private TableColumn<Product, String> nameProduct;

    @FXML
    private TableColumn<Product, Double> price;

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
    private Button Add;

    @FXML
    private Button Delete;

    @FXML
    private Button Red;

    @FXML
    private FontAwesomeIcon BackToLogin;

    @FXML
    void Esc(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            Controller ob = new Controller();
            ob.scene("sample.fxml");
        }
    }

    @FXML
    void back(MouseEvent event) {
        Controller ob = new Controller();
        ob.scene("sample.fxml");
    }

    static public int c;
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
            nameProduct.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
            price.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
            weight.setCellValueFactory(new PropertyValueFactory<Product, Double>("weight"));
            protein.setCellValueFactory(new PropertyValueFactory<Product, Double>("protein"));
            fats.setCellValueFactory(new PropertyValueFactory<Product, Double>("fats"));
            carbohydrates.setCellValueFactory(new PropertyValueFactory<Product, Double>("carbohydrates"));
            nutritionalValue.setCellValueFactory(new PropertyValueFactory<Product, Double>("nutritionalValue"));
            composition.setCellValueFactory(new PropertyValueFactory<Product, String>("composition"));
            ObservableList<Product> list = FXCollections.observableArrayList(ob);
            Table.getItems().addAll(list);
            //Table.setItems(list);
        }
        catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        catch(Exception e1) {
            e1.printStackTrace();
        }



        Add.setOnAction(actionEvent -> {
            try {
                c = 0;
                Controller.CurrentStage.close();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Add.fxml"));
                Parent page = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(page));
                stage.setTitle("Admin");
                Controller.CurrentStage = stage;
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        Delete.setOnAction(actionEvent -> {
            try {
                Controller.getCoos().writeObject("3");
                Controller.getCoos().writeObject(Table.getSelectionModel().getSelectedItem().getProductName());
                Table.getItems().removeAll(Table.getSelectionModel().getSelectedItem());
                Controller.CurrentStage.close();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("AdminChoice.fxml"));
                Parent page = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(page));
                stage.setTitle("Admin");
                Controller.CurrentStage = stage;
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
            }
            catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        });
        Red.setOnAction(actionEvent -> {
            try {
                c = 1;
                Controller.getCoos().writeObject("2");
                Controller.getCoos().writeObject(Table.getSelectionModel().getSelectedItem().getProductName());
                AddProduct.RedProductName = Table.getSelectionModel().getSelectedItem().getProductName();
                AddProduct.RedPrice = Table.getSelectionModel().getSelectedItem().getPrice();
                AddProduct.RedWeight = Table.getSelectionModel().getSelectedItem().getWeight();
                AddProduct.RedProtein = Table.getSelectionModel().getSelectedItem().getProtein();
                AddProduct.RedFats = Table.getSelectionModel().getSelectedItem().getFats();
                AddProduct.RedCarbohydrates = Table.getSelectionModel().getSelectedItem().getCarbohydrates();
                AddProduct.RedNutritionalValue = Table.getSelectionModel().getSelectedItem().getNutritionalValue();
                AddProduct.RedComposition = Table.getSelectionModel().getSelectedItem().getComposition();
                Table.getItems().removeAll(Table.getSelectionModel().getSelectedItem());
                Controller.CurrentStage.close();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Add.fxml"));
                Parent page = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(page));
                stage.setTitle("Red");
                Controller.CurrentStage = stage;
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    @Override
    public boolean display() {
        try {
            //String mes = "Admin";
            //Controller.getCoos().writeObject(mes);
            Controller.CurrentStage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AdminChoice.fxml"));
            Parent page = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(page));
            stage.setTitle("Admin");
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
