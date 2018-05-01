import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
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
                "SELECT movieId, tmdbId FROM links");
        rs = prep.executeQuery();
        while (rs.next()) {
            int movie=rs.getInt("movieId");
            int tmdb=rs.getInt("tmdbId");
            movies.get(movie).tmdbId=tmdb;
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

    public static String getImg(String s) {
        boolean flag = false;
        try {
            Document doc = Jsoup.connect("https://www.themoviedb.org/movie/" + s).get();
            Element body = doc.body();

            StringBuilder s1 = new StringBuilder();
            StringBuilder s2 = new StringBuilder();
            int start=body.html().indexOf("<img class=\"poster\" src=\"")+25;
            int end=body.html().indexOf("srcset",start)-2;
            String url=body.html().substring(start,end);
            return url;

        } catch (IOException e) {
        } catch (StringIndexOutOfBoundsException e2) {
        }
        return "https://78.media.tumblr.com/tumblr_mcmnmjYjt21ri9p4oo1_250.jpg";
    }


}
