import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.text.DecimalFormat;
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
    public Label userName;
    public static String name;

    @FXML
    public void initialize()
    {
        userName.setText(name);
        showMovie();
    }

    private void showMovie() {
        if(index>0)
        {
            prev_btn.setDisable(false);
        }
        else
        {
            prev_btn.setDisable(true);
        }
        if(index<8)
        {
            next_btn.setDisable(false);
        }
        else
        {
            next_btn.setDisable(true);
        }


        //image
        int id=moviesID.get(index);
        currMovie=ReadFromDB.movies.get(id);
        String pictureUrl=ReadFromDB.getImg(""+(currMovie.tmdbId));
        Image img=new Image(pictureUrl);
        image_img.setImage(img);

        //title
        movieTitle_txt.setText(currMovie.movieName);

        //rank
        double rank=currMovie.avgRank;
        DecimalFormat df = new DecimalFormat("####0.0");

        movieRank_txt.setText(df.format(rank));
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
