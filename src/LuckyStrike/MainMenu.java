package LuckyStrike;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

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

    //Arcade Mode Method
    public void ArcadeMode(){
        try {

            FXMLLoader ld = new FXMLLoader();
            Pane root = ld.load(getClass().getResource("ArcadeMode.fxml").openStream());
            ArcadeMode arcade = (ArcadeMode) ld.getController();

            Scene scene = new Scene(root);
            Stage nStage = new Stage();
            nStage.setTitle("Arcade Mode");
            nStage.setScene(scene);

            p.setDisable(true);
            nStage.showAndWait();
            p.setDisable(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Quick Mode Method
    public void QuickMode(){
        try {

            FXMLLoader ld = new FXMLLoader();
            Pane root = ld.load(getClass().getResource("QuickPlay.fxml").openStream());
            QuickPlay quickplay = (QuickPlay) ld.getController();

            Scene scene = new Scene(root);
            Stage nStage = new Stage();
            nStage.setTitle("Quick Play Mode");
            nStage.setScene(scene);

            p.setDisable(true);
            nStage.showAndWait();
            p.setDisable(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //PVP Mode Method
    public void PVP(){
        try {

            FXMLLoader ld = new FXMLLoader();
            Pane root = ld.load(getClass().getResource("PVP.fxml").openStream());
            PVP PVPs = (PVP) ld.getController();

            Scene scene = new Scene(root);
            Stage nStage = new Stage();
            nStage.setTitle("Player vs Player Mode");
            nStage.setScene(scene);

            p.setDisable(true);
            nStage.showAndWait();
            p.setDisable(false);

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
            CharCreator cccontrol = (CharCreator) ld.getController();

            Scene scene = new Scene(root);
            Stage nStage = new Stage();
            nStage.setTitle("Charactor Creator");
            nStage.setScene(scene);

            p.setDisable(true);
            nStage.showAndWait();
            p.setDisable(false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}


