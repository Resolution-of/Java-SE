package User;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Admin.Product;
import Manager.NewAdmin;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

public class Basket {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Purchase> Table;

    @FXML
    private TableColumn<Purchase, String> ProductColumn;

    @FXML
    private TableColumn<Purchase, Double> PriceColumn;

    @FXML
    private TableColumn<Purchase, String> Status;

    @FXML
    private TableColumn<Purchase, String> Date;

    @FXML
    private TextField Login;

    @FXML
    private PasswordField Password;

    @FXML
    private Button SignAccount;

    @FXML
    private Button Survey;

    @FXML
    void Esc(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            try {
                Controller.getCoos().writeObject("back");
                Controller.CurrentStage.close();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("UserChoice.fxml"));
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
        }
    }

    @FXML
    void deleteProduct(ActionEvent event) {
        Purchase ob = Table.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation dialog");
        alert.setHeaderText(null);
        alert.setContentText("Вы уверены, что хотите удалить данный товар из корзины?");
        Optional<ButtonType> action = alert.showAndWait();

        if(action.get() == ButtonType.OK)
        {
            try {
                Controller.getCoos().writeObject("delete");
                Controller.getCoos().writeObject(ob.getProduct());
                Controller.getCoos().writeObject(ob.getDate());
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
                ex.printStackTrace(System.out);
            }
        }
    }
    @FXML
    private FontAwesomeIcon BackToLogin;

    @FXML
    void back(MouseEvent event) {
        try {
            Controller.getCoos().writeObject("back");
            Controller.CurrentStage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("UserChoice.fxml"));
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
    }

    @FXML
    void initialize() {
        try {
            String kol = (String) Controller.getCois().readObject();
            Purchase[] ob = new Purchase[Integer.parseInt(kol)];
            String PuStatus = "";
            for(int i = 0; i < Integer.parseInt(kol); i++) {
                String Product = (String) Controller.getCois().readObject();
                String Price = (String) Controller.getCois().readObject();
                String Date = (String) Controller.getCois().readObject();
                String status = (String) Controller.getCois().readObject();
                if(status.equals("Paid"))
                {
                    PuStatus = "Оплачено";
                }
                else if(status.equals("NotPaid"))
                {
                    PuStatus = "Не оплачено";
                }
                ob[i] = new Purchase(Product, Double.parseDouble(Price), PuStatus, Date);
            }
            ProductColumn.setCellValueFactory(new PropertyValueFactory<Purchase, String>("Product"));
            PriceColumn.setCellValueFactory(new PropertyValueFactory<Purchase, Double>("Price"));
            Status.setCellValueFactory(new PropertyValueFactory<Purchase, String>("Status"));
            Date.setCellValueFactory(new PropertyValueFactory<Purchase, String>("Date"));
            ObservableList<Purchase> list = FXCollections.observableArrayList(ob);
            Table.getItems().addAll(list);
            //Table.setItems(list);
        }
        catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        catch(Exception e1) {
            e1.printStackTrace();
        }
        SignAccount.setOnAction(actionEvent -> {
            try {
                Controller.getCoos().writeObject("sign");
                Controller.getCoos().writeObject(Login.getText());
                Controller.getCoos().writeObject(Password.getText());

                String rez = (String)Controller.getCois().readObject();
                if (rez.equals("ok"))
                {
                    String Price = (String) Controller.getCois().readObject();
                    confirmation_of_pay(Double.parseDouble(Price));
                }
                else if (rez.equals("no"))
                {
                    mistake_of_InputLogin();
                }
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
            catch(Exception e1) {
                e1.printStackTrace();
            }
        });
        Survey.setOnAction(actionEvent -> {
            UserSurvey();
            try {
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
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });

    }
    private void mistake_of_InputLogin()
    {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Mistake");
        window.setMinWidth(300);

        Button closeButton = new Button("Закрыть");
        closeButton.setOnAction(e-> window.close());
        Label label = new Label();
        label.setText("Введённые логин или пароль ошибочны");

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 500,300);
        window.setScene(scene);
        window.showAndWait();
    }
    private void confirmation_of_pay(double price)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bank");
        alert.setHeaderText("Оплата покупок");
        alert.setContentText("Сумма к оплате: " + price);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            try {
                Controller.getCoos().writeObject("pay");
                String mes = (String) Controller.getCois().readObject();
                if (mes.equals("not enough")) {
                    notPaid();
                } else if (mes.equals("enough")) {
                    Paid();
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        else
        {
            try {
                Controller.getCoos().writeObject("no pay");
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    private void UserSurvey()
    {
        try {
            Controller.getCoos().writeObject("survey");
            Controller.getCoos().writeObject(Table.getSelectionModel().getSelectedItem().getProduct());
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Survey");
            window.setMinWidth(300);

            Label label = new Label();
            label.setText("Поставьте оценку товару: " + Table.getSelectionModel().getSelectedItem().getProduct());

            RadioButton mark_1 = new RadioButton("1");
            RadioButton mark_2 = new RadioButton("2");
            RadioButton mark_3 = new RadioButton("3");
            RadioButton mark_4 = new RadioButton("4");
            RadioButton mark_5 = new RadioButton("5");

            ToggleGroup group = new ToggleGroup();
            mark_1.setToggleGroup(group);
            mark_2.setToggleGroup(group);
            mark_3.setToggleGroup(group);
            mark_4.setToggleGroup(group);
            mark_5.setToggleGroup(group);

            Button closeButton = new Button("Оценить");
            closeButton.setOnAction(e-> {
                try {
                    RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
                    String toogleGroupValue = selectedRadioButton.getText();
                    Controller.getCoos().writeObject(toogleGroupValue);
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
                window.close();
            });


            VBox layout = new VBox(10);
            layout.getChildren().addAll(label, mark_1, mark_2, mark_3, mark_4, mark_5, closeButton);
            layout.setAlignment(Pos.CENTER);

            Scene scene = new Scene(layout, 500,300);
            window.setScene(scene);
            window.showAndWait();
        }
        catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
    private void notPaid()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("На вашем счету недостаточно средств");
        alert.showAndWait();

    }
    private void Paid()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Paid");
        alert.setHeaderText("Оплата была произведена");
        alert.showAndWait();
    }

}
