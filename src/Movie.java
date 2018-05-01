import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Movie {

    public int movieID;
    public String movieName;
    public List<User> users= new ArrayList<User>();
    public double avgRank;

    public Movie(int movieID, String movieName) {
        this.movieID = movieID;
        this.movieName = movieName;
    }

    public void addUser(User u){
        users.add(u);
    }

}
