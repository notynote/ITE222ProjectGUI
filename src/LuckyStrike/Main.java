package LuckyStrike;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class Main extends Application {

    //Make it Static so GC don't clear player and cause music to stop
    public static MediaPlayer player;

    @Override
    public void start(Stage primaryStage) throws Exception{

        //String musicpath = "C:\\Users\\notyn\\IdeaProjects\\ITE222ProjectGUI\\src\\Resource\\Celestial.mp3";
        //Media media = new Media(new File(musicpath).toURI().toString());

        //use this for portable file (file in .jar)
        Media media = new Media(getClass().getResource("/Resource/Celestial.mp3").toURI().toString());
        player = new MediaPlayer(media);
        //loop music
        player.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                player.seek(Duration.ZERO);
            }
        });
        player.play();



        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        primaryStage.setTitle("Lucky Strike");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
