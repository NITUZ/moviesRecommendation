import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javax.swing.text.html.ImageView;

public class GUIController {

    public ComboBox rate;
   // public ImageView photo;
    public Movie currentMovie;
    public int ratedCount;
    public Label numOfRated;
    public boolean currRated=false;
    ObservableList<String> rateValues= FXCollections.observableArrayList( "1","1.5","2","2.5","3","3.5","4","4.5","5");

    @FXML
    public void initialize() {
        ratedCount=0;
        rate.setItems(rateValues);
        rate.setValue("");
        setNewMovie();
    }

    private void setNewMovie() {
        currRated=false;
        numOfRated.setText("rated "+ratedCount+"/10");
        rate.setValue("");
    }

    public void nextMovie(ActionEvent actionEvent) {
        if(ratedCount<10)
        {
            setNewMovie();
        }
        else
        {

        }
    }

    public void saveMovieRate(ActionEvent actionEvent) {
        if(rate.getValue()!="")
        {
            double mRate=Double.parseDouble(String.valueOf(rate.getValue()));
            if(!currRated)
            {
                ratedCount++;
                currRated=true;
            }
        }
        else
        {
            showAlertError("in order to rate this movie - choose rate first");
        }
    }

    private void showAlertError(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(alertMessage);
        alert.show();
    }
}
