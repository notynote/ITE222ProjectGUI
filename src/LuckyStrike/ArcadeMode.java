package LuckyStrike;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


import GameCode.*;

/**+
 * A controller for arcade mode
 * this mode allow user to fight with 10 CPU Character.
 * the higher a level = the harder it get
 */
public class ArcadeMode extends Battle{

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

    /**+
     * Allow user to start the game
     */
    public void ArcadeModeStart() {

        //preload weapon
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

        for (int i = 1; i <= 10; i++ ) {
            this.battleai = new CPU(i);
            display.appendText("\n====================\nYou are facing " + battleai + "\n====================");
            //Thread.sleep(1000);
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
                if (this.player1.hp <= 0) {
                    break;
                }
                int healamount = i * 100; //recover stage multiply by hundred to hp
                this.player1.hp += healamount;
                display.appendText("\n********************\nYour hp recovers by\n        " + healamount + "\n      for winning\n********************\n\nNow you have "+ player1.hp +"\n\n********************\n\n");
                p1stattext.setText(player1.toString());
            } else if (starter == 2){
                //CPU go first
                whowin = FightingPVC(2,player1,battleai,p1img,p2img,display,p1stattext,p2stattext);
                Annoucer(whowin);
                //reset turn count
                turn = 0;
                if (player1.hp <= 0) {
                    break;
                }
                int healamount = i*100;
                this.player1.hp += healamount;
                display.appendText("\n********************\nYour hp recovers by\n        " + healamount + "\n      for winning\n********************\n\nNow you have "+ player1.hp +"\n\n********************\n\n");
                p1stattext.setText(player1.toString());

            }
        }

        if (this.player1.hp >= 1) {
            display.setText("\n\n********************\n   Congratulation\n********************\n   " + player1.getCharname() + "\n Has beaten a game!!\n********************\n\n");
            p1img.setImage(new Image("/Resource/giphy.gif"));
            p2img.setImage(new Image("/Resource/otter2.gif"));
        } else {
            display.setText("\n\n!!!!!!!!!!!!!!!!!!!!\n   Game Over\n!!!!!!!!!!!!!!!!!!!!\n\n");
            p2img.setImage(new Image("/Resource/Gameover.gif"));
        }

    }

    /**+
     * Annouce the winner of the game
     * @param winner a result of the battle
     */
    //Abstract method
    void Annoucer(int winner){

        if (winner == 1){
            display.setText("\n********************\n  " + player1.getCharname() + "\n  WON the fight!!\n********************\n");
            //setImage
            int aiclassdie = getSetimage(battleai.getCharclass());
            setImage(aiclassdie,p2img);
        } else if (winner == 2){
            display.setText("\n********************\n  " + battleai.getCharname() + "\n  WON the fight!!\n********************\n");

            //setImage
            int p1classdie = getSetimage(player1.getCharclass());
            setImage(p1classdie,p1img);
        } else {
            display.setText("\n********************\n  " + battleai.getCharname() + "\n  WON the fight!!\n********************\n");
            //setImage
            int p1classdie = getSetimage(player1.getCharclass());
            setImage(p1classdie,p1img);
        }

    }

    //open about
    public void About(){

        About(p);

    }


}
