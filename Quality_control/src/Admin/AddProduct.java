package Admin;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Controller;

public class AddProduct {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField Name;

    @FXML
    private TextField Price;

    @FXML
    private TextField weight;

    @FXML
    private TextField protein;

    @FXML
    private TextField fats;

    @FXML
    private TextField carbohydrates;

    @FXML
    private TextField nutritionalValue;

    @FXML
    private TextField composition;

    @FXML
    private Button AddProd;
    public static String RedProductName;
    public static double RedPrice;
    public static double RedWeight;
    public static double RedProtein;
    public static double RedFats;
    public static double RedCarbohydrates;
    public static double RedNutritionalValue;
    public static String RedComposition;

    @FXML
    void Enter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (validateFields() && validateProductName() && validateProductCarbohydrates() && validateProductComposition()
                    && validateProductFats() && validateProductNutritionalValue() && validateProductPrice() && validateProductProtein()
                    && validateProductWeight()) {
                try {
                    Controller.getCoos().writeObject("1");

                    Controller.getCoos().writeObject(Name.getText());
                    Controller.getCoos().writeObject(Price.getText());
                    Controller.getCoos().writeObject(weight.getText());
                    Controller.getCoos().writeObject(protein.getText());
                    Controller.getCoos().writeObject(fats.getText());
                    Controller.getCoos().writeObject(carbohydrates.getText());
                    Controller.getCoos().writeObject(nutritionalValue.getText());
                    Controller.getCoos().writeObject(composition.getText());

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


                } catch (IOException ex) {
                    ex.printStackTrace(System.out);
                }
            }
        }

    }
    @FXML
    void initialize() {
        if(Admin.c == 0) {
            AddProd.setOnAction(actionEvent -> {
                if (validateFields() && validateProductName() && validateProductCarbohydrates() && validateProductComposition()
                && validateProductFats() && validateProductNutritionalValue() && validateProductPrice() && validateProductProtein()
                && validateProductWeight()) {
                    try {
                        Controller.getCoos().writeObject("1");

                        Controller.getCoos().writeObject(Name.getText());
                        Controller.getCoos().writeObject(Price.getText());
                        Controller.getCoos().writeObject(weight.getText());
                        Controller.getCoos().writeObject(protein.getText());
                        Controller.getCoos().writeObject(fats.getText());
                        Controller.getCoos().writeObject(carbohydrates.getText());
                        Controller.getCoos().writeObject(nutritionalValue.getText());
                        Controller.getCoos().writeObject(composition.getText());

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


                    } catch (IOException ex) {
                        ex.printStackTrace(System.out);
                    }
                }
            });
        }
        else if(Admin.c == 1)
        {
            Name.setText(RedProductName);
            Price.setText(String.valueOf(RedPrice));
            weight.setText(String.valueOf(RedWeight));
            protein.setText(String.valueOf(RedProtein));
            fats.setText(String.valueOf(RedFats));
            carbohydrates.setText(String.valueOf(RedCarbohydrates));
            nutritionalValue.setText(String.valueOf(RedNutritionalValue));
            composition.setText(RedComposition);
            AddProd.setText("Редактировать");
            AddProd.setOnAction(actionEvent -> {
                if (validateFields() && validateProductName() && validateProductCarbohydrates() && validateProductComposition()
                        && validateProductFats() && validateProductNutritionalValue() && validateProductPrice() && validateProductProtein()
                        && validateProductWeight())
                {
                    try {
                        Controller.getCoos().writeObject(Name.getText());
                        Controller.getCoos().writeObject(Price.getText());
                        Controller.getCoos().writeObject(weight.getText());
                        Controller.getCoos().writeObject(protein.getText());
                        Controller.getCoos().writeObject(fats.getText());
                        Controller.getCoos().writeObject(carbohydrates.getText());
                        Controller.getCoos().writeObject(nutritionalValue.getText());
                        Controller.getCoos().writeObject(composition.getText());

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


                    } catch (IOException ex) {
                        ex.printStackTrace(System.out);
                    }
                }
            });
        }
    }
    private boolean validateProductName()
    {
        Pattern p = Pattern.compile("^[А-Яа-яЁё\\s]+$");
        Matcher m = p.matcher(Name.getText());
        if(m.matches())
        {
            return true;
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Product");
            alert.setHeaderText(null);
            alert.setContentText("Название продукта должно содержать исключительно буквы русского алфавита");
            alert.showAndWait();
            return false;
        }
    }
    private boolean validateProductPrice()
    {
        Pattern p = Pattern.compile("^[0-9]*[.,]?[0-9]+$");
        Matcher m = p.matcher(Price.getText());
        if(m.matches() && Double.parseDouble(Price.getText()) < 100000 && Double.parseDouble(Price.getText()) > 0)
        {
            return true;
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Price");
            alert.setHeaderText(null);
            alert.setContentText("Стоимость товара должна быть рационально положительна и содержать только цифры");
            alert.showAndWait();
            return false;
        }
    }
    private boolean validateProductWeight()
    {
        Pattern p = Pattern.compile("^[0-9]*[.,]?[0-9]+$");
        Matcher m = p.matcher(weight.getText());
        if(m.matches() && Double.parseDouble(weight.getText()) < 10000 && Double.parseDouble(weight.getText()) > 0)
        {
            return true;
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Price");
            alert.setHeaderText(null);
            alert.setContentText("Масса товара должна быть рационально положительна и содержать только цифры");
            alert.showAndWait();
            return false;
        }
    }
    private boolean validateProductProtein()
    {
        Pattern p = Pattern.compile("^[0-9]*[.,]?[0-9]+$");
        Matcher m = p.matcher(protein.getText());
        if(m.matches() && Double.parseDouble(protein.getText()) < 1000 && Double.parseDouble(protein.getText()) > 0)
        {
            return true;
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Price");
            alert.setHeaderText(null);
            alert.setContentText("Масса товара должна быть рационально положительна и содержать только цифры");
            alert.showAndWait();
            return false;
        }
    }
    private boolean validateProductFats()
    {
        Pattern p = Pattern.compile("^[0-9]*[.,]?[0-9]+$");
        Matcher m = p.matcher(fats.getText());
        if(m.matches() && Double.parseDouble(fats.getText()) < 1000 && Double.parseDouble(fats.getText()) > 0)
        {
            return true;
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Price");
            alert.setHeaderText(null);
            alert.setContentText("Количество жиров в товаре должно быть рационально положительно и содержать только цифры");
            alert.showAndWait();
            return false;
        }
    }
    private boolean validateProductCarbohydrates()
    {
        Pattern p = Pattern.compile("^[0-9]*[.,]?[0-9]+$");
        Matcher m = p.matcher(carbohydrates.getText());
        if(m.matches() && Double.parseDouble(carbohydrates.getText()) < 1000 && Double.parseDouble(carbohydrates.getText()) > 0)
        {
            return true;
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Price");
            alert.setHeaderText(null);
            alert.setContentText("Количество углеводов в товаре должно быть рационально положительно и содержать только цифры");
            alert.showAndWait();
            return false;
        }
    }
    private boolean validateProductNutritionalValue()
    {
        Pattern p = Pattern.compile("^[0-9]*[.,]?[0-9]+$");
        Matcher m = p.matcher(nutritionalValue.getText());
        if(m.matches() && Double.parseDouble(nutritionalValue.getText()) < 10000 && Double.parseDouble(nutritionalValue.getText()) > 0)
        {
            return true;
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Price");
            alert.setHeaderText(null);
            alert.setContentText("Энергетическая ценность товара должна быть рационально положительна и содержать только цифры");
            alert.showAndWait();
            return false;
        }
    }
    private boolean validateProductComposition()
    {
        Pattern p = Pattern.compile("^[А-Яа-яЁё\\s]+$");
        Matcher m = p.matcher(composition.getText());
        if(m.matches())
        {
            return true;
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Price");
            alert.setHeaderText(null);
            alert.setContentText("Состав продукта должен содержать исключительно буквы русского алфавита");
            alert.showAndWait();
            return false;
        }
    }
    private boolean validateFields()
    {
        if(Name.getText().isEmpty() | Price.getText().isEmpty() | weight.getText().isEmpty() | protein.getText().isEmpty()
        |fats.getText().isEmpty() | carbohydrates.getText().isEmpty() | nutritionalValue.getText().isEmpty()
        |composition.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Пожалуйста, заполните все поля");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
