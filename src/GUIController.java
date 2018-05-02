import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class GUIController {

    public ComboBox rate;
    public Movie currentMovie;
    public int ratedCount;
    public Label numOfRated;
    public boolean currRated=false;
    ObservableList<String> rateValues= FXCollections.observableArrayList( "1","1.5","2","2.5","3","3.5","4","4.5","5");
    public String name;
    public List<Integer> moviesID=new LinkedList<>();
    User currentUser;
    double avgRank=0;
    public javafx.scene.image.ImageView moviePhoto=new javafx.scene.image.ImageView();
    public Label movieTitle;
    public Button next;

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        ReadFromDB.LoadMovies();
        ratedCount=0;
        currentUser=new User(0,0);
        rate.setItems(rateValues);
        rate.setValue("");
        setNewMovie();
    }

    private void setNewMovie(){
        currRated=false;
        numOfRated.setText("rated "+ratedCount+"/10");
        rate.setValue("");
        int id=(int)((Math.random()*9125));
        while(moviesID.contains(id) || !ReadFromDB.movies.containsKey(id))
        {
            id=(int)((Math.random()*9125));
        }
        currentMovie=ReadFromDB.movies.get(id);
        moviesID.add(id);
        String pictureUrl=ReadFromDB.getImg(""+(currentMovie.tmdbId));
        Image img=new Image(pictureUrl);
        moviePhoto.setImage(img);
        movieTitle.setText(currentMovie.movieName);
    }

    public void nextMovie(ActionEvent actionEvent) throws IOException{
        if(ratedCount<10)
        {
            setNewMovie();
        }
        else
        {
            avgRank=avgRank/10;
            currentUser.avgRank=avgRank;
            openRecommendation();
        }
    }

    private void openRecommendation() throws IOException {
        //find recommended movies
        Recommend rec=new Recommend(currentUser);
        rec.calcSimUser2Users();
        rec.findBestRankMovies();
        RecommendationGUIController.moviesID= (List<Integer>) rec.MovieToOffer.keySet();

        Stage stage = new Stage();
        stage.setTitle("Recommended For You");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("recommendationGUI.fxml").openStream());
        Scene scene = new Scene(root, 700, 600);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
        Stage stage2=(Stage)next.getScene().getWindow();
        stage2.close();
        stage.showAndWait();

    }

    public void saveMovieRate(ActionEvent actionEvent) {
        if(rate.getValue()!="")
        {
            double mRate=Double.parseDouble(String.valueOf(rate.getValue()));
            if(!currRated)
            {
                ratedCount++;
                currRated=true;
                currentUser.addMovie(currentMovie.movieID,mRate);
                avgRank+=mRate;
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
