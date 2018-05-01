import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class GUIController {

    public ChoiceBox rate;

    ObservableList<String> rateValues= FXCollections.observableArrayList( "1","2","3","4","5");

    @FXML
    public void initialize() {
        rate.setItems(rateValues);
        rate.setValue("choose rate");
    }
}
