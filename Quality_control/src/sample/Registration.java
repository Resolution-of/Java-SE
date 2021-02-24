package sample;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Registration {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button RegButton;

    @FXML
    private TextField NewLogin;

    @FXML
    private PasswordField NewPassword;

    @FXML
    private FontAwesomeIcon BackToLogin;

    @FXML
    void Enter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (validateFields() && validatePassword() && validateUserName()) {
                try {
                    Controller.getCoos().writeObject(NewLogin.getText());
                    Controller.getCoos().writeObject(NewPassword.getText());

                } catch (IOException ex) {
                    ex.printStackTrace(System.out);
                } finally {
                    try {
                        Controller.CurrentStage.close();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("sample.fxml"));
                        Parent page = loader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(page));
                        Controller.CurrentStage = stage;
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    @FXML
    void Esc(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            try {
                Controller.getCoos().writeObject("back");
                Controller.CurrentStage.close();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("sample.fxml"));
                Parent page = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(page));
                Controller.CurrentStage = stage;
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    void back(MouseEvent event) {
        try {
            Controller.getCoos().writeObject("back");
            Controller.CurrentStage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("sample.fxml"));
            Parent page = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(page));
            Controller.CurrentStage = stage;
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    void initialize() {
        RegButton.setOnAction(actionEvent -> {
            if(validateFields() && validatePassword() && validateUserName()) {
                try {
                    Controller.getCoos().writeObject(NewLogin.getText());
                    Controller.getCoos().writeObject(NewPassword.getText());

                }
                catch (IOException ex) {
                    ex.printStackTrace(System.out);
                }
                finally {
                    try {
                        Controller.CurrentStage.close();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("sample.fxml"));
                        Parent page = loader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(page));
                        Controller.CurrentStage = stage;
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private boolean validatePassword()
    {
        Pattern p = Pattern.compile("(?=^.{5,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*");
        Matcher m = p.matcher(NewPassword.getText());
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
    private boolean validateUserName()
    {
        Pattern p = Pattern.compile("^[a-zA-Z]+$");
        Matcher m = p.matcher(NewLogin.getText());
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
    private boolean validateFields()
    {
        if(NewLogin.getText().isEmpty() | NewPassword.getText().isEmpty())
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

