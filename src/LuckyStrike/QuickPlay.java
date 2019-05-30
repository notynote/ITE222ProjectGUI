package LuckyStrike;

import GameCode.CPU;
import GameCode.Helper;
import GameCode.Skill;
import GameCode.Weapon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Random;

public class QuickPlay {

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

    public void QuickPlayStart() {

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
            } else {
                //CPU go first
                whowin = FightingPVC(2);
                Annoucer(whowin);
                //reset turn count
                turn = 0;
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

                    skillchoice = Integer.parseInt(Objects.requireNonNull(GUIHelper.getSkillchoice(attacker.getOffend(), attacker.getNoffend())));

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
                display.appendText("\n" + defender.getCharname() + " prepare for defend - What skill do you want to use?\n");

                defendchoice = Integer.parseInt(Objects.requireNonNull(GUIHelper.getSkillchoice(defender.getDefend(), defender.getNdefend())));
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

        //if more than 5 turn and found weapon is true
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
    private void Annoucer(int winner) {

        if (winner == 1){
            display.setText("\n********************\n  " + player1.getCharname() + "\n  WON the fight!!\n********************\n");
            //Thread.sleep(500);
            //setImage
            int aiclassdie = GUIHelper.getSetimage(battleai.getCharclass());
            ArcadeMode.setImage(aiclassdie, p2img);
        } else if (winner == 2){
            display.setText("\n********************\n  " + battleai.getCharname() + "\n  WON the fight!!\n********************\n");
            //Thread.sleep(500);
            //setImage
            int p1classdie = GUIHelper.getSetimage(player1.getCharclass());
            ArcadeMode.setImage(p1classdie, p1img);
        } else {
            display.setText("\n********************\n  " + battleai.getCharname() + "\n  WON the fight!!\n********************\n");
            //Thread.sleep(300);
            //setImage
            int p1classdie = GUIHelper.getSetimage(player1.getCharclass());
            ArcadeMode.setImage(p1classdie, p1img);
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

        try {

            FXMLLoader ld = new FXMLLoader();
            Pane root = ld.load(getClass().getResource("About.fxml").openStream());

            Scene scene = new Scene(root);
            Stage nStage = new Stage();
            nStage.setTitle("About");
            nStage.setScene(scene);

            p.setDisable(true);
            nStage.showAndWait();
            p.setDisable(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
