package LuckyStrike;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URISyntaxException;

/**+
 * A controller of main menu scene which act as a hub of the game
 * this window allows user to choose what mode they want to play
 * this window also handle the music in all the game mode
 */
public class MainMenu {

    //GUI Variable
    @FXML
    private Button arcade;
    @FXML
    private Button quickplay;
    @FXML
    private Button pvp;
    @FXML
    private Button charcreator;
    @FXML
    private Button exit;
    @FXML
    private Pane p;
    @FXML
    private CheckBox musictoggle;
    @FXML
    private Label textname;

    private int musicstatus = 1;


    //Arcade Mode Method
    public void ArcadeMode(){

        if (musicstatus == 1) {
            //Replace a song if in arcade mode
            try {
                SongMainmenu(new Media(getClass().getResource("/Resource/Red Carpet Wooden Floor.mp3").toURI().toString()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Main.player.stop();
        }

        try {

            FXMLLoader ld = new FXMLLoader();
            Pane root = ld.load(getClass().getResource("ArcadeMode.fxml").openStream());

            Scene scene = new Scene(root);
            Stage nStage = new Stage();
            nStage.setTitle("Arcade Mode");
            nStage.setScene(scene);

            p.setDisable(true);
            nStage.showAndWait();
            p.setDisable(false);

            //Change song back
            SongMainmenu(new Media(getClass().getResource("/Resource/Celestial.mp3").toURI().toString()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Quick Mode Method
    public void QuickMode(){
        if (musicstatus == 1) {
            //Change song in Quick Play
            try {
                SongMainmenu(new Media(getClass().getResource("/Resource/Nocturnal Mysteries.mp3").toURI().toString()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Main.player.stop();
        }

        try {

            FXMLLoader ld = new FXMLLoader();
            Pane root = ld.load(getClass().getResource("QuickPlay.fxml").openStream());

            Scene scene = new Scene(root);
            Stage nStage = new Stage();
            nStage.setTitle("Quick Play Mode");
            nStage.setScene(scene);

            p.setDisable(true);
            nStage.showAndWait();
            p.setDisable(false);

            //Change song back
            SongMainmenu(new Media(getClass().getResource("/Resource/Celestial.mp3").toURI().toString()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //PVP Mode Method
    public void PVP(){

        if (musicstatus == 1) {
            //Change song in PVP
            try {
                SongMainmenu(new Media(getClass().getResource("/Resource/The Arrival (BATTLE II).mp3").toURI().toString()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Main.player.stop();
        }

        try {

            FXMLLoader ld = new FXMLLoader();
            Pane root = ld.load(getClass().getResource("PVP.fxml").openStream());

            Scene scene = new Scene(root);
            Stage nStage = new Stage();
            nStage.setTitle("Player vs Player Mode");
            nStage.setScene(scene);

            p.setDisable(true);
            nStage.showAndWait();
            p.setDisable(false);

            //Change song back
            SongMainmenu(new Media(getClass().getResource("/Resource/Celestial.mp3").toURI().toString()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //exit method
    public void ExitProgram(){
        System.exit(0);
    }

    //Char Creator Method
    public void charCreator(){

        try {

            FXMLLoader ld = new FXMLLoader();
            Pane root = ld.load(getClass().getResource("CharCreator.fxml").openStream());

            Scene scene = new Scene(root);
            Stage nStage = new Stage();
            nStage.setTitle("Character Creator");
            nStage.setScene(scene);

            p.setDisable(true);
            nStage.showAndWait();
            p.setDisable(false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Replace a song if Back on main Menu
    private void SongMainmenu(Media media){
        //stop to make sure the music is not carry over
        Main.player.stop();
        if (musicstatus == 1) {
            Main.player = new MediaPlayer(media);
            //loop music
            Main.player.setOnEndOfMedia(() -> Main.player.seek(Duration.ZERO));
            Main.player.play();
        }
    }

    //turn on/off music
    public void MusicToggle(){
        if (musictoggle.isSelected()){
            Main.player.stop();
            musicstatus = 1;
            //loop music
            Main.player.setOnEndOfMedia(() -> Main.player.seek(Duration.ZERO));
            Main.player.play();
        } else {
            Main.player.stop();
            musicstatus = 0;
        }
    }

}


