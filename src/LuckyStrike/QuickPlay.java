package LuckyStrike;

import GameCode.CPU;
import GameCode.Helper;
import GameCode.Skill;
import GameCode.Weapon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class QuickPlay extends Battle{

    //GUI Variable
    @FXML
    private TextArea display;
    @FXML
    private TextArea p1stattext;
    @FXML
    private TextArea p2stattext;
    @FXML
    private Pane p;
//    @FXML
//    private MenuItem StartGame;
//    @FXML
//    private MenuItem About;
    @FXML
    private ImageView p1img;
    @FXML
    private ImageView p2img;

    //global variable
    private GameCode.Player player1;
    private GameCode.CPU battleai = null;

    public void QuickPlayStart() {

        //preload weapon
        //Weapon Array
        Weapon[] armory = super.loadWeapon();

        //variable
        int whowin,starter;

        //make character
        this.player1 = makePlayer(p);
        display.setText("====================\nYour Character:\n" + player1 + "\n====================");

        //show character status
        p1stattext.setText(player1.toString());

        //setImage
        int p1classtoimg = getSetimage(player1.getCharclass());
        setClassimg(p1classtoimg,p1img);


        int cpudiffselect = 0;
        //Ask what difficulty user want
        do {
            try {
                display.appendText("\n====================\nPlease Choose CPU Difficulty. . .\n====================\n");
                //cpudiffselect = Integer.parseInt(console.next());
                cpudiffselect = Integer.parseInt(Objects.requireNonNull(getCPUDiff()));
            } catch (Exception ignore){

            }
        }
        while (cpudiffselect!=0 && cpudiffselect!=1 && cpudiffselect!=2 && cpudiffselect!=3 && cpudiffselect!=4 && cpudiffselect!=5 && cpudiffselect!=6
                && cpudiffselect!=7 && cpudiffselect!=8 && cpudiffselect!=9 && cpudiffselect!=10);

        if (cpudiffselect==0) {
            //create random ai
            this.battleai = new CPU();
        } else {
            //create selected ai
            this.battleai = new CPU(cpudiffselect);

        }
            display.appendText("\n====================\nYou are facing " + battleai + "\n====================");

            //show ai status
            p2stattext.setText(battleai.toString());

        //set ai Image
        int p2classtoimg = getSetimage(battleai.getCharclass());
        setClassimg(p2classtoimg,p2img);

            //random who go first
            starter = super.getStarter();
            if (starter == 1) {
                //player go first
                whowin = FightingPVC(1,player1,battleai,p1img,p2img,display,p1stattext,p2stattext);
                Annoucer(whowin);
                //reset turn count
                turn = 0;
            } else {
                //CPU go first
                whowin = FightingPVC(2,player1,battleai,p1img,p2img,display,p1stattext,p2stattext);
                Annoucer(whowin);
                //reset turn count
                turn = 0;
            }

    }

    //Abstract method
    void Annoucer(int winner) {

        if (winner == 1){
            display.setText("\n********************\n  " + player1.getCharname() + "\n  WON the fight!!\n********************\n");
            //Thread.sleep(500);
            //setImage
            int aiclassdie = getSetimage(battleai.getCharclass());
            setImage(aiclassdie, p2img);
        } else {
            display.setText("\n********************\n  " + battleai.getCharname() + "\n  WON the fight!!\n********************\n");
            //Thread.sleep(300);
            //setImage
            int p1classdie = getSetimage(player1.getCharclass());
            setImage(p1classdie, p1img);
        }

    }


    private String getCPUDiff(){

        try {
            FXMLLoader ld = new FXMLLoader();
            Pane root = ld.load(getClass().getResource("CPUChoice.fxml").openStream());
            CPUChoice CPUs = ld.getController();

            Scene scene = new Scene(root);
            Stage nStage = new Stage();
            nStage.setTitle("CPU Difficulty");
            nStage.setScene(scene);

            //p.setDisable(true);
            nStage.showAndWait();
            //p.setDisable(false);

            return CPUs.sendBack;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //open about
    public void About(){
        About(p);
    }

}
