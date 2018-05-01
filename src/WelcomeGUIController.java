import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class WelcomeGUIController {
    public TextField name_txt;
    public Button startRate_btn;
    public String name="";

    public void goToRate(ActionEvent actionEvent) throws IOException {
        name=name_txt.getText();
        Stage stage = new Stage();
        stage.setTitle("add items");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("GUI.fxml").openStream());
        Scene scene = new Scene(root, 700, 600);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
        stage.showAndWait();

    }
}
