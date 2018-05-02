import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Nogah on 01/05/2018.
 */
public class RecommendationGUIController {
    public ScrollPane scrollPane;
    public int index=0;
    public Button prev_btn;
    public Button next_btn;
    public static List<Integer> moviesID=new ArrayList<>();
    public javafx.scene.image.ImageView moviePhoto=new javafx.scene.image.ImageView();
    public Label movieTitle_txt;
    public Label movieRank_txt;
    public Movie currentMovie;
    @FXML
    public void initialize()
    {
       /* for (Integer id:recommended.keySet())
        {
            moviesID.add(id);
        }*/
        showMovie();
    }

    public void prevMovie(ActionEvent actionEvent)
    {
        index--;
        showMovie();
    }

    public void nextMovie(ActionEvent actionEvent)
    {
        index++;
        showMovie();
    }

    private void showMovie()
    {
      /*  if(index>0)
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
        }*/
        currentMovie=ReadFromDB.movies.get(moviesID.get(index));
        String pictureUrl=ReadFromDB.getImg(""+(currentMovie.tmdbId));
        Image img=new Image(pictureUrl);
        moviePhoto.setImage(img);
       // movieTitle_txt.setText(currentMovie.movieName);
       // movieRank_txt.setText(""+currentMovie.avgRank);
    }
}
