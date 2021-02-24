package sample;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Admin.Admin;
import Expert.Expert;
import Manager.Manager;
import User.User;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Controller {
    public static Stage CurrentStage;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField LoginButton;

    @FXML
    private PasswordField PasswordButton;

    @FXML
    private Button SignIn;

    @FXML
    private Button Registration;

    @FXML
    void Enter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if(validateFields()) {
                try {
                    coos.writeObject("log");
                    coos.writeObject(LoginButton.getText());
                    coos.writeObject(PasswordButton.getText());
                    String role = (String) cois.readObject();
                    switch (role) {
                        case "admin": {
                            Admin obj = new Admin();
                            Log adm = new Log(obj);
                            break;
                        }
                        case "manager": {
                            Manager obj = new Manager();
                            Log manager = new Log(obj);
                            break;
                        }
                        case "expert": {
                            Expert obj = new Expert();
                            Log expert = new Log(obj);
                            break;
                        }
                        case "user": {
                            User obj = new User();
                            Log user = new Log(obj);
                            break;
                        }
                        case "mistake": {
                            Log mistake = new Log();
                            break;
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace(System.out);
                } catch (Exception e1) {
                    e1.printStackTrace();
                } finally {
                    LoginButton.setText("");
                    PasswordButton.setText("");
                }
            }
        }
    }

    @FXML
    private Button Exit;

    private static ObjectOutputStream coos;
    private static ObjectInputStream cois;
    private static Socket clientSocket;
    public void connection(int k)
    {
        if(k==1)
        {
            try
            {
            System.out.println("server connecting....");
            clientSocket = new Socket("127.0.0.1", 2525);
            System.out.println("connection established....");
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            coos = new ObjectOutputStream(clientSocket.getOutputStream());
            cois = new ObjectInputStream(clientSocket.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }
    static int q = 1;
    @FXML
    void initialize() {
        connection(q);
        q++;
        SignIn.setOnAction(e ->
        {
            if(validateFields()) {
                try {
                    coos.writeObject("log");
                    coos.writeObject(LoginButton.getText());
                    coos.writeObject(PasswordButton.getText());
                    String role = (String) cois.readObject();
                    switch (role) {
                        case "admin": {
                            Admin obj = new Admin();
                            Log adm = new Log(obj);
                            break;
                        }
                        case "manager": {
                            Manager obj = new Manager();
                            Log manager = new Log(obj);
                            break;
                        }
                        case "expert": {
                            Expert obj = new Expert();
                            Log expert = new Log(obj);
                            break;
                        }
                        case "user": {
                            User obj = new User();
                            Log user = new Log(obj);
                            break;
                        }
                        case "mistake": {
                            Log mistake = new Log();
                            break;
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace(System.out);
                } catch (Exception e1) {
                    e1.printStackTrace();
                } finally {
                    LoginButton.setText("");
                    PasswordButton.setText("");
                }
            }
        });
        Registration.setOnAction(actionEvent -> {
            try {
                coos.writeObject("reg");
                CurrentStage.close();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Reg.fxml"));
                Parent page = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(page));
                stage.setTitle("Registration");
                CurrentStage = stage;
                /*stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent windowEvent) {
                        try {
                            coos.writeObject("end");
                            clientSocket.close();
                            cois.close();
                            coos.close();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });*/
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();


            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        Exit.setOnAction(actionEvent -> {
            try {
                coos.writeObject("exit");
                clientSocket.close();
                coos.close();
                cois.close();
                CurrentStage.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public static ObjectInputStream getCois() {
        return cois;
    }

    public static ObjectOutputStream getCoos() {
        return coos;
    }
    private boolean validateFields()
    {
        if(LoginButton.getText().isEmpty() | PasswordButton.getText().isEmpty())
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
    public void scene(String way)
    {
        try {
            Controller.getCoos().writeObject("back");
            Controller.CurrentStage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(way));
            Parent page = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(page));
            CurrentStage = stage;
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}