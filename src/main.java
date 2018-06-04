import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;
import java.util.*;


public class main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("welcomeGUI.fxml"));
        primaryStage.setTitle("Recommended Movies");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
    launch(args);
    }
}

/*
public class main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //launch(args);
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:movies.db");
        //Map<Integer,User> users=new HashMap<>();//<id,user>
        List<Double> pecisionAlgo=new ArrayList<>();
        double avgPreAlgo=0;
        List<Double> pecisionTop10=new ArrayList<>();
        double avgPreTop10=0;

        //find top 10
        List<Integer> top10=new ArrayList<>();
        PreparedStatement prep = conn.prepareStatement("SELECT movieId from movieAvgRating order by avgRating desc limit 10");
        ResultSet rs = prep.executeQuery();
        while(rs.next())
        {
            top10.add(rs.getInt("movieId"));
        }

        //get 100 users
         prep = conn.prepareStatement("SELECT distinct userId from ratings order by random() limit 100");
         rs = prep.executeQuery();
        //go throw users
        while (rs.next()) {
            int currId=rs.getInt("userId");
           // Map<Integer,Double> test10=new HashMap<>();//<id,rank>
            Map<Integer,Double> test=new HashMap<>();//<id,rank>
            User currUser=new User(currId,0);
            //users.put(currId,new User(currId,0));
            //get test, train
            PreparedStatement prep2 = conn.prepareStatement("SELECT movieId, rating, b.count from ratings a join (SELECT count(*) as count from ratings where userId="+currId+") b where userId="+currId+" order by random()");
            ResultSet rs2 = prep2.executeQuery();
            int i=0;
            while(rs2.next()){
                int trainSize=(int)(rs2.getInt("count")*0.8);

                //train
                if(i<trainSize)
                {
                    if(i<10)
                    {
                        currUser.addMovie(rs2.getInt("movieId"), rs2.getDouble("rating"));
                        currUser.avgRank+=rs2.getDouble("rating");
                    }
                    i++;
                }
                //test
                else
                {
                    test.put(rs2.getInt("movieId"), rs2.getDouble("rating"));
                    i++;
                }
            }
            currUser.avgRank=currUser.avgRank/10;
            ReadFromDB.LoadMovies();

            Recommend rec=new Recommend(currUser);
            rec.calcSimUser2Users();
            rec.findBestRankMovies();

            //fint top 10 from test
            List test10 = new LinkedList(test.entrySet());
            // Defined Custom Comparator here
            Collections.sort(test10, new Comparator() {
                public int compare(Object o1, Object o2) {
                    if (((double) ((Map.Entry) (o2)).getValue()) -
                            (((double) ((Map.Entry) (o1)).getValue())) > 0)
                        return 1;
                    if (((double) ((Map.Entry) (o2)).getValue()) -
                            (((double) ((Map.Entry) (o1)).getValue())) < 0)
                        return -1;
                    else return 0;
                }
            });

            //calc precision@10
            double sum1=0;
            double sum2=0;
            int count=0;
            for (Iterator it = test10.iterator(); it.hasNext();){
                Map.Entry entry = (Map.Entry) it.next();
                int id= (int) entry.getKey();
                if(rec.MovieToOffer.keySet().contains(id))
                {
                    sum1++;
                }
                if(top10.contains(id))
                {
                    sum2++;
                }
                count++;
            }
            sum1=sum1/10;
            sum2=sum2/10;
            pecisionAlgo.add(sum1);
            avgPreAlgo+=sum1;
            pecisionTop10.add(sum2);
            avgPreTop10+=sum2;
        }

        avgPreAlgo=avgPreAlgo/100;
        avgPreTop10=avgPreTop10/100;
        System.out.println(avgPreAlgo);
        System.out.println(pecisionAlgo);
        System.out.println(avgPreTop10);
        System.out.println(pecisionTop10);


    }
}*/