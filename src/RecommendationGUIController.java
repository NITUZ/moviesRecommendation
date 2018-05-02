import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Nogah on 01/05/2018.
 */
public class RecommendationGUIController {
    public HashMap<Integer, Movie> recommended; //<ID,movie>
    public ScrollPane scrollPane;
    public int index=1;
    public Button prev_btn;
    public Button next_btn;
    public List<Integer> moviesID=new ArrayList<>();
    public javafx.scene.image.ImageView moviePhoto=new javafx.scene.image.ImageView();
    public Label movieTitle_txt;
    public Label movieRank_txt;

    @FXML
    public void initialize()
    {
        for (Integer id:recommended.keySet())
        {
            moviesID.add(id);
        }
        showMovie();
    }

    public void prevMovie(ActionEvent actionEvent)
    {
        index--;
        if(index==1)
        {
            prev_btn.setDisable(true);
        }
        if(index==9)
        {
            next_btn.setDisable(false);
        }

        showMovie();
    }

    public void nextMovie(ActionEvent actionEvent)
    {
        index++;
        if(index==2)
        {
            prev_btn.setDisable(false);
        }
        if(index==10)
        {
            next_btn.setDisable(true);
        }
        
        showMovie();
    }

    private void showMovie()
    {
        Movie currentMovie=recommended.get(moviesID.get(index));
        String pictureUrl=ReadFromDB.getImg(""+(currentMovie.tmdbId));
        Image img=new Image(pictureUrl);
        moviePhoto.setImage(img);
        movieTitle_txt.setText(currentMovie.movieName);
        movieRank_txt.setText(""+currentMovie.avgRank);
    }
}
