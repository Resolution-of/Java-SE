package Manager;

import Admin.Product;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Controller;
import sample.Rez_of_Input;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Manager implements Rez_of_Input {

    @FXML
    private TextField Login;

    @FXML
    private PasswordField Password;

    @FXML
    private Button Add;

    @FXML
    private TableView<NewAdmin> Table;

    @FXML
    private TableColumn<NewAdmin, String> LoginColumn;

    @FXML
    private TableColumn<NewAdmin, String> PasswordColumn;

    @FXML
    private Button Delete;

    @FXML
    private Button BackButton;

    @FXML
    private FontAwesomeIcon BackToLogin;

    @FXML
    void Esc(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            Controller ob = new Controller();
            ob.scene("sample.fxml");
        }
    }

    @FXML
    void back(MouseEvent event) {
        Controller ob = new Controller();
        ob.scene("sample.fxml");
    }

    @FXML
    void initialize() {
        try {
            String kol = (String) Controller.getCois().readObject();
            NewAdmin[] ob = new NewAdmin[Integer.parseInt(kol)];
            for(int i = 0; i < Integer.parseInt(kol); i++) {
                String account = (String) Controller.getCois().readObject();
                String password = (String) Controller.getCois().readObject();
                ob[i] = new NewAdmin(account, password);
            }
            LoginColumn.setCellValueFactory(new PropertyValueFactory<NewAdmin, String>("account"));
            PasswordColumn.setCellValueFactory(new PropertyValueFactory<NewAdmin, String>("password"));
            ObservableList<NewAdmin> list = FXCollections.observableArrayList(ob);
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
            if(validateFields() && validateLogin() && validatePassword())
            {
                try {
                    Controller.getCoos().writeObject("1");
                    Controller.getCoos().writeObject(Login.getText());
                    Controller.getCoos().writeObject(Password.getText());
                    display();
                }
                catch (IOException ex) {
                    ex.printStackTrace(System.out);
                }

            }
        });
        Delete.setOnAction(actionEvent -> {
            try {
                Controller.getCoos().writeObject("2");
                Controller.getCoos().writeObject(Table.getSelectionModel().getSelectedItem().getAccount());
                Table.getItems().removeAll(Table.getSelectionModel().getSelectedItem());
                display();
            }
            catch (IOException ex) {
                ex.printStackTrace(System.out);
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
            loader.setLocation(getClass().getResource("ManagerChoice.fxml"));
            Parent page = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(page));
            stage.setTitle("Manager");
            Controller.CurrentStage = stage;
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    private boolean validateLogin()
    {
        Pattern p = Pattern.compile("^[a-zA-Z]+$");
        Matcher m = p.matcher(Login.getText());
        if(m.matches())
        {
            return true;
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Login");
            alert.setHeaderText(null);
            alert.setContentText("Логин должен быть написан на латинице");
            alert.showAndWait();
            return false;
        }
    }
    private boolean validatePassword()
    {
        Pattern p = Pattern.compile("(?=^.{5,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*");
        Matcher m = p.matcher(Password.getText());
        if(m.matches())
        {
            return true;
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate password");
            alert.setHeaderText(null);
            alert.setContentText("Введите пароль правильно\nПароль должен содержать:\n1. Маленькую и большую буквы латинского алфивата\n2. Минимум одну цифру\n3. Количество символов должно быть от 5 до 15");
            alert.showAndWait();
            return false;
        }
    }
    private boolean validateFields()
    {
        if(Login.getText().isEmpty() | Password.getText().isEmpty())
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
