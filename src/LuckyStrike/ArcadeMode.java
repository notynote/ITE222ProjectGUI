package LuckyStrike;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Objects;
import java.util.Random;

import GameCode.*;
import javafx.stage.Stage;

public class ArcadeMode {

    //GUI Variable
    @FXML
    private TextArea display;
    @FXML
    private TextArea p1stattext;
    @FXML
    private TextArea p2stattext;
    @FXML
    private Pane p;
    @FXML
    private MenuItem StartGame;
    @FXML
    private MenuItem About;
    @FXML
    private ImageView p1img;
    @FXML
    private ImageView p2img;

    //global variable
    private GameCode.Character player1;
    private GameCode.CPU battleai = null;
    private Random r = new Random();

    //Battle variable
    private int skillchoice = 0;
    private GameCode.Character attacker = null,defender = null;
    private int turn = 0;

    //Weapon Array
    private Weapon[] armory;

//    //Auto Start Game Not Working Yet
//    public void initialize() throws InterruptedException {
//        ArcadeMode();
//    }

    public void ArcadeMode() throws InterruptedException {

        //preload weapon
        //Create Array of Weapon
        armory = new Weapon[5];

        armory[0] = new Weapon("Stick", 5);
        armory[1] = new Weapon("Wooden Sword", 10);
        armory[2] = new Weapon("Iron Sword", 20);
        armory[3] = new Weapon("Magic Sword", 30);
        armory[4] = new Weapon("Master Sword", 50);

        //variable
        int whowin,starter;

        //make character
        this.player1 = makeChar();
        display.setText("====================\nYour Character:\n" + player1 + "\n====================");

        //show character status
        p1stattext.setText(player1.toString());

        //setImage
        int p1classtoimg = GUIHelper.getSetimage(player1.getCharclass());
        ArcadeMode.setClassimg(p1classtoimg,p1img);

        for (int i = 1; i <= 10; i++ ) {
            this.battleai = new CPU(i);
            display.appendText("\n====================\nYou are facing " + battleai + "\n====================");
            //Thread.sleep(1000);
            p2stattext.setText(battleai.toString());

            //set ai Image
            int p2classtoimg = GUIHelper.getSetimage(battleai.getCharclass());
            ArcadeMode.setClassimg(p2classtoimg,p2img);

            //random who go first
            starter = getStarter();
            if (starter == 1) {
                //player go first
                whowin = FightingPVC(1);
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
                whowin = FightingPVC(2);
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


    //define Character method
    private GameCode.Character makeChar(){

        try {
            FXMLLoader ld = new FXMLLoader();
            Pane root = ld.load(getClass().getResource("CharnameInput.fxml").openStream());
            CharnameInput nameinput = ld.getController();

            Scene scene = new Scene(root);
            Stage nStage = new Stage();
            nStage.setTitle("Character Name Input");
            nStage.setScene(scene);

            p.setDisable(true);
            nStage.showAndWait();
            p.setDisable(false);

            String charname = nameinput.input.getText();
            return new GameCode.Character(charname);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new GameCode.Character("Test");
    }

    //Random Starter method
    private int getStarter() {

        return r.nextInt((2-1)+1) + 1;
    }

    private  int FightingPVC(int Starter){

        if (Starter == 1){ //player1 start first
            do {
                //player1 turn
                playvscpu(1);
                //if hp fall below + equal to 0 then attacker = winner then return winner and stop the fight
                if (battleai.hp <= 0){
                    return 1;
                }

                //CPU turn
                playvscpu(2);
                if (player1.hp <= 0){
                    return 3;
                }

            } while(true);

        } else { //if player2 start first

            do {
                //CPU turn
                playvscpu(2);
                if (player1.hp <= 0){
                    return 3;
                }

                //player1 turn
                playvscpu(1);
                //if hp fall below + equal to 0 then attacker = winner then return winner and stop the fight
                if (battleai.hp <= 0){
                    return 1;
                }


            } while(true);
        }

    }

    //player vs cpu fight mode
    //player choose then cpu random
    private void playvscpu(int player) {

        //Variable for cpu skill random
        int skillrandom,defendrandom,attackerdmg;

        //ask user to choose attack skill
        if (player == 1) {
            attacker = player1;
            defender = battleai;
        } else if (player == 2) {
            attacker = battleai;
            defender = player1;
        }

        //seperate player and cpu way to attack
        String attackerskill;
        if (attacker == player1){

            //set attack image
            int p1classtoimg = GUIHelper.getSetimage(player1.getCharclass());
            ArcadeMode.setAtkimg(p1classtoimg,p1img);
            do {
                try {
                    //Thread.sleep(500);
                    display.appendText("\n" + attacker.getCharname() + " turn. What skill do you want to use?\n");

                    skillchoice = Integer.parseInt(Objects.requireNonNull(GUIHelper.getSkillchoice(attacker.getOffend(), attacker.getNoffend(), "Check my Status", "Check Enemy Status")));
                    //skillchoice = Integer.parseInt(console.next());
                    if (skillchoice == 3) {
                        display.appendText(attacker+"\n====================");
                    } else if (skillchoice == 4) {
                        display.appendText(defender+"\n====================");
                    }
                } catch (Exception ignore) {

                }
            } while (skillchoice !=1 && skillchoice!=2);
            attackerdmg = GUIHelper.Attacking(skillchoice, attacker);
            attackerskill = GUIHelper.getAtkname(skillchoice, attacker);
        } else {
            //set attack image
            int p2classtoimg = GUIHelper.getSetimage(battleai.getCharclass());
            ArcadeMode.setAtkimg(p2classtoimg,p2img);
            display.appendText("\n" + attacker.getCharname() + " turn. Random skill to use.\n");
            skillrandom = getStarter();
            if (skillrandom == 1){
                display.appendText("\n" + attacker.getCharname() + " will use \"" +attacker.getOffend() + "\"");
            } else {
                display.appendText("\n" + attacker.getCharname() + " will use \"" +attacker.getNoffend() + "\"");
            }
            attackerdmg = GUIHelper.Attacking(skillrandom, attacker);
            attackerskill = GUIHelper.getAtkname(skillrandom, attacker);

        }
        display.appendText("\n========================================");

        //player choose defender skill cpu random
        String defenderskill;
        if (defender == player1){
            int defendchoice;
            do {
                assert defender != null;
                //Thread.sleep(500);
                display.appendText("\n" + defender.getCharname() + " prepare for defend - What skill do you want to use?\n");

                defendchoice = Integer.parseInt(Objects.requireNonNull(GUIHelper.getSkillchoice(defender.getDefend(), defender.getNdefend(), "Check my Status", "Check Enemy Status")));
                //defendchoice = Integer.parseInt(console.next());
                if (defendchoice == 3) {
                    display.appendText("\n" + defender+"\n====================");
                } else if (defendchoice == 4) {
                    display.appendText("\n" + attacker+"\n====================");
                }
            } while (defendchoice !=1 && defendchoice !=2);

            defenderskill = GUIHelper.getDefname(defendchoice,defender);

            //set image back
            int p2classtoimg = GUIHelper.getSetimage(battleai.getCharclass());
            ArcadeMode.setClassimg(p2classtoimg,p2img);

        } else {
            assert defender != null;
            //Thread.sleep(500);
            display.appendText("\n" + defender.getCharname() + " random skill for defend.\n");
            defendrandom = getStarter();
            if (defendrandom == 1) {
                //Thread.sleep(500);
                display.appendText("\n" + defender.getCharname() + " will use \"" + defender.getDefend()+"\"");
            } else {
                //Thread.sleep(500);
                display.appendText("\n" + defender.getCharname() + " will use \"" + defender.getNdefend()+"\"");
            }
            display.appendText("\n========================================");

            defenderskill = GUIHelper.getDefname(defendrandom,defender);

            //set image back
            int p1classtoimg = GUIHelper.getSetimage(player1.getCharclass());
            ArcadeMode.setClassimg(p1classtoimg,p1img);
        }
        //calculation for final damage
        //check the element advantage to determine final damage
        int finaldmg = GUIHelper.ElementCheck(attackerskill, defenderskill, attackerdmg);

        //calculate dodge
        int dodge = Skill.Dodge(defender.getLuck());

        //if dodged
        if (dodge == 1) {

            display.setText("\n********************\n" + attacker.getCharname() + "\n**[" + attackerskill + "]**\n " + defender.getCharname() + "\nfor " + attackerdmg + " damage\n");
            //Thread.sleep(500);
            display.appendText("\n********************\nbut " + defender.getCharname() + " DODGED the attack and take no damage\n********************\n");

        } else {
            //did not dodge

            display.setText("\n********************\n" + attacker.getCharname() + "\n**[" + attackerskill + "]**\n " + defender.getCharname() + "\nfor " + attackerdmg + " damage\n");
            //Thread.sleep(500);
            display.appendText("\nbut " + defender.getCharname() + "\n**<" + defenderskill + ">** the attack and take\n" + finaldmg + " damage\n");
            defender.hp -= finaldmg;
            //Thread.sleep(500);
            display.appendText("\n====================\n" + defender.getCharname() + " now has " + defender.hp + " hp" + "\n====================\n");

        }

        //count turn
        turn++;

        //random found weapon chance (20%)
        int foundweapon = Helper.getRandomNumberInRange(1, 5);

        //if more than 10 turn and found weapon is true
        if (turn >= 10 && foundweapon == 3){

            //random 1 weapon
            Weapon holding = Helper.FoundWeapon(armory);
            display.appendText("\n!!!!!!!!!!!!!!!!!!!!\n\n" + attacker.getCharname() + " found a " + holding.Name + " (" + holding.Damage + " Damage)\n");
            //Thread.sleep(500);
            display.appendText("\n!!!!!!!!!!!!!!!!!!!!\n\n" + attacker.getCharname() + " use " + holding.Name + " to attack " + defender.getCharname());
            defender.hp -= holding.Damage;
            //Thread.sleep(500);
            display.appendText("\n\n====================\n" + defender.getCharname() + " now has " + defender.hp + " hp" + "\n====================\n");

        }

        //update status text
        p1stattext.setText(player1.toString());
        p2stattext.setText(battleai.toString());

    }

    //announcer method
    private void Annoucer(int winner){

        if (winner == 1){
            display.setText("\n********************\n  " + player1.getCharname() + "\n  WON the fight!!\n********************\n");
            //setImage
            int aiclassdie = GUIHelper.getSetimage(battleai.getCharclass());
            setImage(aiclassdie,p2img);
        } else if (winner == 2){
            display.setText("\n********************\n  " + battleai.getCharname() + "\n  WON the fight!!\n********************\n");

            //setImage
            int p1classdie = GUIHelper.getSetimage(player1.getCharclass());
            setImage(p1classdie,p1img);
        } else {
            display.setText("\n********************\n  " + battleai.getCharname() + "\n  WON the fight!!\n********************\n");
            //setImage
            int p1classdie = GUIHelper.getSetimage(player1.getCharclass());
            setImage(p1classdie,p1img);
        }

    }

    //Set Class Image
    static void setClassimg(int x, ImageView iv){
        switch (x){
            case 1:
                iv.setImage(new Image("/Resource/Swordsmen.gif"));
                break;
            case 2:
                iv.setImage(new Image("/Resource/Wizard.gif"));
                break;
            case 3:
                iv.setImage(new Image("/Resource/Archer.gif"));
                break;
            default:
                iv.setImage(new Image("/Resource/defaultava.gif"));
        }
    }

    //Set Dead Image
    static void setImage(int x, ImageView iv) {
        switch (x){
            case 1:
                iv.setImage(new Image("/Resource/FallingKn.gif"));
                break;
            case 2:
                iv.setImage(new Image("/Resource/DeadWiz.gif"));
                break;
            case 3:
                iv.setImage(new Image("/Resource/DownAr.gif"));
                break;
            default:
                iv.setImage(new Image("/Resource/goddead.gif"));
        }
    }

    //Set Atk Image
    static void setAtkimg(int x, ImageView iv){
        switch (x){
            case 1:
                iv.setImage(new Image("/Resource/turnswords.gif"));
                break;
            case 2:
                iv.setImage(new Image("/Resource/castmage.gif"));
                break;
            case 3:
                iv.setImage(new Image("/Resource/shotar.gif"));
                break;
            default:
                iv.setImage(new Image("/Resource/defaultava.gif"));
        }
    }


}
