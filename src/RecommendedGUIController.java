import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class RecommendedGUIController
{
    public Button prev_btn;
    public Button next_btn;
    public Label movieTitle_txt;
    public Label movieRank_txt;
    public ImageView image_img;
    public static List<Integer> moviesID=new ArrayList<>();
    public int index=0;
    public Movie currMovie;

    @FXML
    public void initialize()
    {
        showMovie();
    }

    private void showMovie() {
        //image
        int id=moviesID.get(index);
        currMovie=ReadFromDB.movies.get(id);
        String pictureUrl=ReadFromDB.getImg(""+(currMovie.tmdbId));
        Image img=new Image(pictureUrl);
        image_img.setImage(img);

        //title
        movieTitle_txt.setText(currMovie.movieName);

        //rank
        movieRank_txt.setText(currMovie.avgRank+"");
    }

    public void prevMovie(ActionEvent actionEvent) {
        index--;
        showMovie();
    }

    public void nextMovie(ActionEvent actionEvent) {
        index++;
        showMovie();
    }
}
