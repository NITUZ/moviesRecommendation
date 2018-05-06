import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class WelcomeGUIController {
    public TextField name_txt;
    public Button startRate;
    public String name="";

    public void goToRate(ActionEvent actionEvent) throws IOException {
        if(name_txt.getText().isEmpty())
        {
            name="";
        }
        else {
            name = name_txt.getText();
        }

        GUIController.userName=name;
        Stage stage = new Stage();
        stage.setTitle("Rate Movies");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("GUI.fxml").openStream());
        Scene scene = new Scene(root, 700, 470);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
        Stage stage2=(Stage)startRate.getScene().getWindow();
        stage2.close();
        stage.showAndWait();


    }
}
