package LuckyStrike;

import GameCode.Weapon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

/**+
 * PVP controller for pvp mode that allows two user to fight with each other
 * this utilize a lot of method from battle.java but also have a pvp method of it own
 * since the mode require two player instead of one player and a cpu
 */
public class PVP extends Battle {

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
    private GameCode.Player player2;

    /**+
     * Allow user to start the pvp mode
     */
    public void PVPStart() throws InterruptedException {

        //preload weapon
        Weapon[] armory = super.loadWeapon();

        //variable
        int whowin,starter;

        //make character
        this.player1 = makePlayer(p);
        display.setText("\n====================\nPlayer 1 Character:\n" + player1 + "\n====================");
        this.player2 = makePlayer(p);
        display.appendText("\n====================\nPlayer 2 Character:\n" + player2 + "\n====================");


        p1stattext.setText(player1.toString());
        p2stattext.setText(player2.toString());

        //setImage
        int p1classtoimg = getSetimage(player1.getCharclass());
        setClassimg(p1classtoimg,p1img);
        int p2classtoimg = getSetimage(player2.getCharclass());
        setClassimg(p2classtoimg,p2img);

        //random who go first
        starter = GameCode.Helper.getRandomNumberInRange(1,2);

        if (starter == 1){
            //player 1 go first
            display.appendText("\n" + player1.getCharname() + " go first!!\n");
            //Thread.sleep(500);
            whowin = FightingPvP(1);
            Annoucer(whowin);
            //reset turn count
            turn = 0;
        } else {
            //player 2 go first
            display.appendText("\n" + player2.getCharname() + " go first!!\n");
            //Thread.sleep(500);
            whowin = FightingPvP(2);
            Annoucer(whowin);
            //reset turn count
            turn = 0;
        }

    }

    /**+
     * Fighting Method for Player vs Player which allow user to choose a game mode
     * Method also to keep track of the turn and a turn owner
     * @param Starter the one who goes first
     * @return the winner of the fight
     */
    private int FightingPvP(int Starter){
        //variable
        //int hasWinner = 0;
        int modeselect = 0;

        //ask for the mode to play
        do {
            try {
                display.appendText("\n====================\n Please Choose your\n     Game mode\n====================\n");
                modeselect = Integer.parseInt(Objects.requireNonNull(PVPModeSelect()));
            } catch (Exception ignore) {

            }
        } while (modeselect!=1 && modeselect!=2);

        if (Starter == 1){ //player1 start first
            do {
                //player1 turn
                if (modeselect == 1) {
                    fightturn(1);
                } else {
                    AttackOnly(1);
                }
                //if hp fall below + equal to 0 then attacker = winner then return winner and stop the fight
                if (player2.hp <= 0){
                    return 1;
                }

                //player2 turn
                if (modeselect == 1) {
                    fightturn(2);
                } else {
                    AttackOnly(2);
                }
                if (player1.hp <= 0){
                    return 2;
                }

            } while(true);

        } else { //if player2 start first

            do {
                //player2 turn
                if (modeselect == 1) {
                    fightturn(2);
                } else {
                    AttackOnly(2);
                }
                if (player1.hp <= 0){
                    return 2;
                }

                //player1 turn
                if (modeselect == 1) {
                    fightturn(1);
                } else {
                    AttackOnly(1);
                }
                if (player2.hp <= 0){
                    return 1;
                }

            } while(true);
        }

    }

    /**+
     * Annouce who win the fight
     * @param winner
     */
    //Abstract method
    void Annoucer(int winner){

        if (winner == 1){
            display.setText("\n********************\n  " + player1.getCharname() + "\n  WON the fight!!\n********************\n");
            //setImage
            int p2classdie = getSetimage(player2.getCharclass());
            setImage(p2classdie, p2img);
        } else if (winner == 2){
            display.setText("\n********************\n  " + player2.getCharname() + "\n  WON the fight!!\n********************\n");
            //setImage
            int p1classdie = getSetimage(player1.getCharclass());
            setImage(p1classdie, p1img);
        }

    }

    /**+
     * The battle phase for pvp in normal mode
     * @param player turn owner
     */
    private void fightturn(int player) {

        //ask user to choose attack skill
        do {
            if (player == 1) {
                attacker = player1;
                defender = player2;
            } else {
                attacker = player2;
                defender = player1;
            }


            //set attack image
            int atkclasstoimg = getSetimage(attacker.getCharclass());

            if (attacker == player1) {
                setAtkimg(atkclasstoimg, p1img);
            } else {
                setAtkimg(atkclasstoimg, p2img);
            }

            ChooseAttackSkill(display);

        } while (skillchoice != 1 && skillchoice != 2);

        //defender select skill

        do {
            ChooseDefendSkill(display);
        } while (defendchoice != 1 && defendchoice != 2);

        String defenderskill = getDefname(defendchoice, defender);

        //calculation for final damage
        //get attack damage
        int attackerdmg = Attacking(skillchoice, attacker);
        attackerskill = getAtkname(skillchoice, attacker);
        //check the element advantage to determine final damage
        int finaldmg = ElementCheck(attackerskill, defenderskill, attackerdmg);

        //calculate dodge
        checkDodge(display,attackerskill,defenderskill,attackerdmg,finaldmg,1);

        //count turn
        turn++;

        //Calculate Weapon
        weaponUsage(display);

        //update status text
        p1stattext.setText(player1.toString());
        p2stattext.setText(player2.toString());

        //set image back
        int atkclasstoimg = getSetimage(attacker.getCharclass());
        if (defender == player1) {
            setClassimg(atkclasstoimg, p2img);
        } else {
            setClassimg(atkclasstoimg, p1img);
        }
    }

    /**+
     * Battle phases for attackonly mode
     * @param player turn owner
     */
    private void AttackOnly(int player) {

        if (player == 1) {
            attacker = player1;
            defender = player2;
        } else if (player == 2) {
            attacker = player2;
            defender = player1;
        }

        //set attack image
        int atkclasstoimg = getSetimage(attacker.getCharclass());

        if (attacker == player1) {
            setAtkimg(atkclasstoimg, p1img);
        } else {
            setAtkimg(atkclasstoimg, p2img);
        }

        do {
            ChooseAttackSkill(display);
        } while (skillchoice !=1 && skillchoice !=2);

        //get attack damage
        int attackerdmg = Attacking(skillchoice, attacker);
        attackerskill = getAtkname(skillchoice, attacker);

        //Calculate Dodge
        checkDodge(display,attackerskill,"",attackerdmg,attackerdmg,2);

        //count turn
        turn++;

        //Calculate Weapon
        weaponUsage(display);

        //update status text
        p1stattext.setText(player1.toString());
        p2stattext.setText(player2.toString());

        //set image back
        atkclasstoimg = getSetimage(attacker.getCharclass());
        if (defender == player1) {
            setClassimg(atkclasstoimg, p2img);
        } else {
            setClassimg(atkclasstoimg, p1img);
        }

    }

    /**+
     * Popup windows that ask user what mode they want to play
     * @return play mode
     */
    private String PVPModeSelect(){

        try {
            FXMLLoader ld = new FXMLLoader();
            Pane root = ld.load(getClass().getResource("PVPModeChoice.fxml").openStream());
            PVPModeChoice pvpmode = ld.getController();

            Scene scene = new Scene(root);
            Stage nStage = new Stage();
            nStage.setTitle("PVP Mode");
            nStage.setScene(scene);

            //p.setDisable(true);
            nStage.showAndWait();
            //p.setDisable(false);

            return pvpmode.sendBack;

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
