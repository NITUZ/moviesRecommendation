import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;


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

        ReadFromDB.LoadMovies();
    }
}*/