import java.util.HashMap;

public class User {

    public int userID;
    public double avgRank;
    public HashMap<Integer, Double> MoviesRanks; //key-MovieID , val-movieRank

    public User(int userID, double avgRank) {
        this.userID = userID;
        this.avgRank = avgRank;
    }
    public void addMovie(int movieID, double rank){
        MoviesRanks.put(movieID,rank);
    }
}
