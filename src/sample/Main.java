package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Scene1.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Bills tracker");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        System.out.println(root);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
