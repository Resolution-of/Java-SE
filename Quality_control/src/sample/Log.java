package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Log {
    Log(Rez_of_Input ob)
    {
        ob.display();
    }
    Log()
    {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Ошибка");
        window.setMinWidth(300);


        Button closeButton = new Button("Close this window");
        closeButton.setOnAction(e-> window.close());
        Label label = new Label();
        label.setText("Введённый логин или пароль ошибочны. Проверьте введённые данные.");

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 500,300);
        window.setScene(scene);
        window.showAndWait();
    }
}
