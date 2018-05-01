import java.sql.*;
import java.util.HashMap;

public class ReadFromDB {

    public HashMap<Integer, Movie> movies= new HashMap<Integer, Movie>();
    public HashMap<Integer, Double> users= new HashMap<Integer, Double>();

    public void LoadMovies() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:movies.db");

        //movies
        PreparedStatement prep = conn.prepareStatement("SELECT movieId,title FROM movies");
        ResultSet rs = prep.executeQuery();
        while (rs.next()) {
            int id=rs.getInt("movieId");
            String title=rs.getString("title");
            Movie movie = new Movie(id,title);
            movies.put(id, movie);
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
            users.put(id, rate);
        }

        //ratings
        prep = conn.prepareStatement(
                "SELECT userId, movieId, rating FROM ratings");
        rs = prep.executeQuery();
        while (rs.next()) {
            int user=rs.getInt("userId");
            int movie=rs.getInt("movieId");
            double rate=rs.getDouble("rating");
            User user
        }
        conn.close();
    }


}
