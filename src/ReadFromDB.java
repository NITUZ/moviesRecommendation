import java.sql.*;
import java.util.HashMap;

public class ReadFromDB {

    public static HashMap<Integer, Movie> movies= new HashMap<Integer, Movie>();
    public static HashMap<Integer, User> users= new HashMap<Integer, User>();

    public ReadFromDB(){};

    public static void LoadMovies() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:movies.db");

        //movies
        PreparedStatement prep = conn.prepareStatement("SELECT movieId,title FROM movies");
        ResultSet rs = prep.executeQuery();
        while (rs.next()) {
            int id=rs.getInt("movieId");
            String title=rs.getString("title");
            movies.put(id, new Movie(id,title));
        }

        //links
        prep = conn.prepareStatement(
                "SELECT movieId, imdbId FROM links");
        rs = prep.executeQuery();
        while (rs.next()) {
            int movie=rs.getInt("movieId");
            int imdb=rs.getInt("imdbId");
            movies.get(movie).imdbId=imdb;
        }

        //movieAvgRating
        prep = conn.prepareStatement(
                "SELECT * FROM movieAvgRating");
        rs = prep.executeQuery();
        while (rs.next()) {
            int id=rs.getInt("movieId");
            double rate=rs.getDouble("avgRating");
            movies.get(id).avgRank=rate;
        }

        //userAvgRating
        prep = conn.prepareStatement(
                "SELECT * FROM userAvgRating");
        rs = prep.executeQuery();
        while (rs.next()) {
            int id=rs.getInt("userId");
            double rate=rs.getDouble("avgRating");
            users.put(id, new User(id,rate));
        }

        //ratings
        prep = conn.prepareStatement(
                "SELECT userId, movieId, rating FROM ratings");
        rs = prep.executeQuery();
        while (rs.next()) {
            int user=rs.getInt("userId");
            int movie=rs.getInt("movieId");
            double rate=rs.getDouble("rating");
            users.get(user).MoviesRanks.put(movie,rate);
            movies.get(movie).addUser(users.get(user));
        }
        conn.close();

        System.out.println("finish");
    }


}
