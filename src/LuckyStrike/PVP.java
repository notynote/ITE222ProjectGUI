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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Random;

public class PVP {

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

    //global variable
    private GameCode.Character player1;
    private GameCode.Character player2;
    private CPU battleai = null;
    private Random r = new Random();

    //Battle variable
    private int skillchoice = 0,defendchoice = 0;
    private String attackerskill = "";
    private String defenderskill = "";
    private int attackerstatus = 0;
    private int finaldmg;
    private GameCode.Character attacker = null,defender = null;
    private int dodge = 0;
    private int turn = 0;
    private int foundweapon = 0;
    private Weapon holding;

    //Weapon Array
    private Weapon[] armory;

    public void PVP() throws InterruptedException {

        //variable
        int whowin,starter;

        //make character
        this.player1 = makeChar();
        display.setText("\nPlayer 1 Character:\n" + player1 + "\n==================");
        this.player2 = makeChar();
        display.appendText("\nPlayer 2 Character:\n" + player2 + "\n==================");


        p1stattext.setText(player1.toString());
        p2stattext.setText(player2.toString());
        //random who go first
        starter = getStarter();

        if (starter == 1){
            //player 1 go first
            display.appendText("\n" + player1.getCharname() + " go first!!");
            //Thread.sleep(500);
            whowin = FightingPvP(1);
            Annoucer(whowin);
            //reset turn count
            turn = 0;
        } else {
            //player 2 go first
            display.appendText("\n" + player2.getCharname() + " go first!!");
            //Thread.sleep(500);
            whowin = FightingPvP(2);
            Annoucer(whowin);
            //reset turn count
            turn = 0;
        }

    }


    //define Character method
    public GameCode.Character makeChar(){

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

    //Fighting Method for Player vs Player
    private int FightingPvP(int Starter) throws InterruptedException{
        //variable
        //int hasWinner = 0;
        int modeselect = 0;

        //ask for the mode to play
        do {
            try {
                display.appendText("\n==========\nPlease Choose your game mode. . .\n1. Normal Mode\n2. Attack Only Mode\n3. Auto Mode\n==========\n");
                modeselect = Integer.parseInt(PVPModeSelect());
                //modeselect = Integer.parseInt(console.next());
            } catch (Exception ignore) {

            }
        } while (modeselect!=1 && modeselect!=2 && modeselect!=3);

        if (Starter == 1){ //player1 start first
            do {
                //player1 turn
                if (modeselect == 1) {
                    fightturn(1);
                } else if (modeselect == 2){
                    AttackOnly(1);
                } else {
                    Automode(1);
                }
                //if hp fall below + equal to 0 then attacker = winner then return winner and stop the fight
                if (player2.hp <= 0){
                    return 1;
                }

                //player2 turn
                if (modeselect == 1) {
                    fightturn(2);
                } else if (modeselect == 2){
                    AttackOnly(2);
                } else {
                    Automode(2);
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
                } else if (modeselect == 2){
                    AttackOnly(2);
                } else {
                    Automode(2);
                }
                if (player1.hp <= 0){
                    return 2;
                }

                //player1 turn
                if (modeselect == 1) {
                    fightturn(1);
                } else if (modeselect == 2){
                    AttackOnly(1);
                } else {
                    Automode(1);
                }
                if (player2.hp <= 0){
                    return 1;
                }

            } while(true);
        }

    }

    //announcer method
    private void Annoucer(int winner) throws InterruptedException{

        if (winner == 1){
            display.setText("\n**********\n" + player1.getCharname() + " WON the fight!!\n**********");
            //Thread.sleep(500);
        } else if (winner == 2){
            display.setText("\n**********\n" + player2.getCharname() + " WON the fight!!\n**********");
            //Thread.sleep(500);
        } else {
            display.setText("\n**********\n" + battleai.getCharname() + " WON the fight!!\n**********");
            //Thread.sleep(300);
        }

    }

    private void fightturn(int player) throws InterruptedException{

        //ask user to choose attack skill
        do {
            if (player == 1) {
                attacker = player1;
                defender = player2;
            } else {
                attacker = player2;
                defender = player1;
            }

            try {
                //Thread.sleep(500);
                display.appendText("\n" + attacker.getCharname() + " turn. What skill do you want to use?\n1. " + attacker.getOffend() + "\n2. " + attacker.getNoffend() + "\n3. Check my Status\n4. Check Enemy Status");

                skillchoice = Integer.parseInt(getSkillchoice(attacker.getOffend(),attacker.getNoffend(),"Check my Status","Check Enemy Status"));
                //skillchoice = Integer.parseInt(console.next());
                if (skillchoice == 3) {
                    display.appendText("\n" + attacker+"\n=================");
                } else if (skillchoice == 4) {
                    display.appendText("\n" + defender+"\n=================");
                }
            } catch (Exception ignore) {

            }
        } while (skillchoice != 1 && skillchoice != 2);

        //defender select skill
        do {
            try {
                //Thread.sleep(500);
                display.appendText("\n" + defender.getCharname() + " prepare for defend - What skill do you want to use?\n1. " + defender.getDefend() + "\n2. " + defender.getNdefend()+ "\n3. Check my Status\n4. Check Enemy Status");

                defendchoice = Integer.parseInt(getSkillchoice(defender.getDefend(),defender.getNdefend(),"Check my Status","Check Enemy Status"));
                //defendchoice = Integer.parseInt(console.next());
                if (defendchoice == 3) {
                    display.appendText("\n" + defender+"\n=================");
                } else if (defendchoice == 4) {
                    display.appendText("\n" + attacker+"\n=================");
                }
            } catch (Exception ignore) {

            }
        } while (defendchoice != 1 && defendchoice != 2);

        defenderskill = Defending(defendchoice);

        //calculation for final damage
        //get attack damage
        int attackerdmg = Attacking(skillchoice);
        //check the element advantage to determine final damage
        finaldmg = ElementCheck(attackerskill, defenderskill, attackerdmg);

        //calculate dodge
        this.dodge = Skill.Dodge(defender.getLuck());

        //if dodged
        if (this.dodge == 1) {

            display.appendText("\n***************\n" + attacker.getCharname() + " " + attackerskill + " " + defender.getCharname() + " for " + attackerdmg + " damage");
            //Thread.sleep(500);
            display.appendText("\n***************\n" + "but " + defender.getCharname() + " DODGED the attack and take no damage\n***************");

        } else {
            //did not dodge

            display.appendText("\n" + attacker.getCharname() + " " + attackerskill + " " + defender.getCharname() + " for " + attackerdmg + " damage");
            //Thread.sleep(500);
            display.appendText("\nbut " + defender.getCharname() + " " + defenderskill + " the attack and take " + finaldmg + " damage");
            defender.hp -= finaldmg;
            //Thread.sleep(500);
            display.appendText("\n==============\n" + defender.getCharname() + " now has " + defender.hp + " hp" + "\n==============");

        }

        //count turn
        turn++;

        //random found weapon chance (20%)
        foundweapon = Helper.getRandomNumberInRange(1,5);

        //if more than 5 turn and found weapon is true
        if (turn >= 10 && foundweapon == 3){

            //random 1 weapon
            holding = Helper.FoundWeapon(armory);
            display.appendText("\n!!!!!!!!!!\n\n" + attacker.getCharname() + " found a " + holding.Name + " (" + holding.Damage + " Damage)");
            //Thread.sleep(500);
            display.appendText("\n!!!!!!!!!!\n\n" + attacker.getCharname() + " use " + holding.Name + " to attack " + defender.getCharname());
            defender.hp -= holding.Damage;
            //Thread.sleep(500);
            display.appendText("\n==============\n" + defender.getCharname() + " now has " + defender.hp + " hp" + "\n==============");

        }

        //update status text
        p1stattext.setText(player1.toString());
        p2stattext.setText(player2.toString());

    }

    //attack only method
    private void AttackOnly(int player) throws InterruptedException {

        if (player == 1) {
            attacker = player1;
            defender = player2;
        } else if (player == 2) {
            attacker = player2;
            defender = player1;
        }

        do {
            try {
                display.appendText("\n" + attacker.getCharname() + " turn. What skill do you want to use?\n1. " + attacker.getOffend() + "\n2. " + attacker.getNoffend()+ "\n3. Check my Status\n4. Check Enemy Status");

                skillchoice = Integer.parseInt(getSkillchoice(attacker.getOffend(),attacker.getNoffend(),"Check my Status","Check Enemy Status"));
                //skillchoice = Integer.parseInt(console.next());
                if (skillchoice == 3) {
                    display.appendText("\n" + attacker+"\n=================");
                } else if (skillchoice == 4) {
                    display.appendText("\n" + defender+"\n=================");
                }
            } catch (Exception ignore) {

            }
        } while (skillchoice !=1 && skillchoice !=2);

        //get attack damage
        int attackerdmg = Attacking(skillchoice);

        //calculate dodge
        this.dodge = Skill.Dodge(defender.getLuck());

        //if dodged
        if (this.dodge == 1) {

            display.appendText("\n***************\n" + attacker.getCharname() + " " + attackerskill + " " + defender.getCharname() + " for " + attackerdmg + " damage");
            Thread.sleep(500);
            display.appendText("\n***************\n" + "but " + defender.getCharname() + " DODGED the attack and take no damage\n***************");

        } else {
            //if not dodge
            display.appendText("\n" + attacker.getCharname() + " " + attackerskill + " " + defender.getCharname() + " for " + attackerdmg + " damage");
            defender.hp -= attackerdmg;
            display.appendText("\n==============\n" + defender.getCharname() + " now has " + defender.hp + " hp" + "\n==============");
        }

        //count turn
        turn++;

        //random found weapon chance (20%)
        foundweapon = Helper.getRandomNumberInRange(1,5);

        //if more than 10 turn and found weapon is true
        if (turn >= 10 && foundweapon == 3){

            //random 1 weapon
            holding = Helper.FoundWeapon(armory);
            display.appendText("\n!!!!!!!!!!\n\n" + attacker.getCharname() + " found a " + holding.Name + " (" + holding.Damage + " Damage)");
            //Thread.sleep(500);
            display.appendText("\n!!!!!!!!!!\n\n" + attacker.getCharname() + " use " + holding.Name + " to attack " + defender.getCharname());
            defender.hp -= holding.Damage;
            //Thread.sleep(500);
            display.appendText("\n==============\n" + defender.getCharname() + " now has " + defender.hp + " hp" + "\n==============");

        }

        //update status text
        p1stattext.setText(player1.toString());
        p2stattext.setText(player2.toString());

    }

    //Auto Mode
    private void Automode(int player) throws InterruptedException{

        //ask user to choose attack skill
        if (player == 1) {
            attacker = player1;
            defender = player2;
        } else if (player == 2) {
            attacker = player2;
            defender = player1;
        }

        //Thread.sleep(500);
        display.appendText("\n" + attacker.getCharname() + " turn. Random skill to use.");
        skillchoice = getStarter();
        if (skillchoice == 1){
            //Thread.sleep(700);
            display.appendText("\n" + attacker.getCharname() + " will use \"" +attacker.getOffend() + "\"");
        } else {
            //Thread.sleep(700);
            display.appendText("\n" + attacker.getCharname() + " will use \"" +attacker.getNoffend() + "\"");
        }
        display.appendText("\n========================================");

        //random defender skill
        //Thread.sleep(500);
        display.appendText("\n" + defender.getCharname() + " random skill for defend.");
        defendchoice = getStarter();
        if (defendchoice == 1){
            //Thread.sleep(700);
            display.appendText("\n" + defender.getCharname() + " will use \"" +defender.getDefend() + "\"");
        } else {
            //Thread.sleep(700);
            display.appendText("\n" + defender.getCharname() + " will use \"" +defender.getNdefend() + "\"");
        }
        display.appendText("\n========================================");

        //calculation for final damage
        //get attack damage
        int attackerdmg = Attacking(skillchoice);
        //check the element advantage to determine final damage
        finaldmg = ElementCheck(attackerskill, defenderskill, attackerdmg);

        //calculate dodge
        this.dodge = Skill.Dodge(defender.getLuck());

        //if dodged
        if (this.dodge == 1) {

            display.appendText("\n***************\n" + attacker.getCharname() + " " + attackerskill + " " + defender.getCharname() + " for " + attackerdmg + " damage");
            //Thread.sleep(500);
            display.appendText("\n***************\n" + "but " + defender.getCharname() + " DODGED the attack and take no damage\n***************");

        } else {
            //did not dodge

            display.appendText("\n" + attacker.getCharname() + " " + attackerskill + " " + defender.getCharname() + " for " + attackerdmg + " damage");
            //Thread.sleep(500);
            display.appendText("\nbut " + defender.getCharname() + " " + defenderskill + " the attack and take " + finaldmg + " damage");
            defender.hp -= finaldmg;
            //Thread.sleep(500);
            display.appendText("\n==============\n" + defender.getCharname() + " now has " + defender.hp + " hp" + "\n==============");

        }

        //update status text
        p1stattext.setText(player1.toString());
        p2stattext.setText(player2.toString());

    }

    //Attacking Method
    private int Attacking(int skillchoice) {

        switch (skillchoice) {
            case 1:
                attackerskill = attacker.getOffend();
                switch (attacker.getCharclass()) {
                    case "Warrior":
                        attackerstatus = attacker.getStr();
                        break;
                    case "Mage":
                        attackerstatus = attacker.getWis();
                        break;
                    case "Archer":
                        attackerstatus = attacker.getDex();
                        break;
                    //secret class
                    default:
                        attackerstatus = 9999;
                }
                break;

            case 2:
                attackerskill = attacker.getNoffend();
                attackerstatus = attacker.getStr() + attacker.getDex() + attacker.getWis();
                break;

            default:
                display.appendText("\n\n\nerror");
        }

        return Skill.OffendDamage(attackerskill, attackerstatus);

    }

    //Defending Method
    private String Defending(int defendchoice){
        switch (defendchoice) {
            case 1:
                defenderskill = defender.getDefend();
                break;

            case 2:
                defenderskill = defender.getNdefend();
                break;

            default:
                display.appendText("\n\n\nerror");
        }

        return defenderskill;
    }

    //Element Checker for final damage
    private int ElementCheck(String attackerskill,String defenderskill,int attackerdmg){

        switch (attackerskill){
            case "Stab": //warrior
                switch (defenderskill){
                    case "Shield Block": //warrior
                    case "Normal Defend":
                        finaldmg = attackerdmg;
                        break;
                    case "Ice Wall":
                        finaldmg = attackerdmg * 2;
                        break;
                    case "Dodge":
                        finaldmg = attackerdmg / 2;
                        break;
                    default:
                        finaldmg = 0;
                }
                break;
            case "Cast Spell": //mage
                switch (defenderskill){
                    case "Shield Block":
                        finaldmg = attackerdmg / 2;
                        break;
                    case "Ice Wall":
                    case "Normal Defend":
                        finaldmg = attackerdmg;
                        break;
                    case "Dodge":
                        finaldmg = attackerdmg * 2;
                        break;
                    default:
                        finaldmg = 0;
                }
                break;
            case "Shoot": //archer
                switch (defenderskill){
                    case "Shield Block":
                        finaldmg = attackerdmg * 2;
                        break;
                    case "Ice Wall":
                        finaldmg = attackerdmg / 2;
                        break;
                    case "Dodge":
                    case "Normal Defend":
                        finaldmg = attackerdmg;
                        break;
                    default:
                        finaldmg = 0;
                }
                break;
            case "Normal Attack":
                switch (defenderskill){
                    case "Shield Block":
                    case "Ice Wall":
                    case "Dodge":
                        finaldmg = attackerdmg;
                        break;
                    case "Normal Defend":
                        finaldmg = attackerdmg*2;
                        break;
                    default:
                        finaldmg = 0;
                }
                break;
            default:
                finaldmg = attackerdmg;
        }

        return finaldmg;
    }

    public String getSkillchoice(String one, String two, String three, String four){

        //SkillChoice.first.setText(one);
        //SkillChoice.two.setText(two);
        //SkillChoice.three.setText(three);
        //SkillChoice.four.setText(four);

        try {
            FXMLLoader ld = new FXMLLoader();
            Pane root = ld.load(getClass().getResource("SkillChoice.fxml").openStream());
            SkillChoice choose = ld.getController();

            Scene scene = new Scene(root);
            Stage nStage = new Stage();
            nStage.setTitle("Choose");
            nStage.setScene(scene);

            //p.setDisable(true);
            nStage.showAndWait();
            //p.setDisable(false);

            String result = choose.choice.getText();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public String PVPModeSelect(){

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

            String result = pvpmode.choice.getText();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
