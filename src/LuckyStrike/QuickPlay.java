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

    //global variable
    private GameCode.Character player1;
    private GameCode.Character player2;
    private GameCode.CPU battleai = null;
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

    public void QuickPlay() throws InterruptedException {

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
        display.setText("Your Character:\n" + player1 + "\n==================");

        int cpudiffselect = 0;
        //Ask what difficulty user want
        do {
            try {
                display.appendText("\n==========\nPlease Choose CPU Difficulty. . .\n0. Random \n1. Noob CPU\n2. Extremely Easy CPU\n3. Very Easy CPU\n4. Easy CPU\n5. Medium CPU" +
                        "\n6. Hard CPU\n7. Insane CPU\n8. Nightmare CPU\n9. Impossible CPU\n10. The End CPU");
                //cpudiffselect = Integer.parseInt(console.next());
                cpudiffselect = Integer.parseInt(getCPUDiff());
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
            display.appendText("\nYou are facing " + battleai + "\n-=-=-=-=-=-=-");
            //Thread.sleep(1000);
            //show character status
            p1stattext.setText(player1.toString());
            p2stattext.setText(battleai.toString());
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

    private  int FightingPVC(int Starter) throws InterruptedException{

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
    private void playvscpu(int player) throws InterruptedException {

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
        if (attacker == player1){
            do {
                try {
                    //Thread.sleep(500);
                    display.appendText("\n" + attacker.getCharname() + " turn. What skill do you want to use?\n1. " + attacker.getOffend() + "\n2. " + attacker.getNoffend()+ "\n3. Check my Status\n4. Check Enemy Status");

                    skillchoice = Integer.parseInt(GUIHelper.getSkillchoice(attacker.getOffend(),attacker.getNoffend(),"Check my Status","Check Enemy Status"));
                    //skillchoice = Integer.parseInt(console.next());
                    if (skillchoice == 3) {
                        display.appendText(attacker+"\n=================");
                    } else if (skillchoice == 4) {
                        display.appendText(defender+"\n=================");
                    }
                } catch (Exception ignore) {

                }
            } while (skillchoice !=1 && skillchoice!=2);
            attackerdmg = Attacking(skillchoice);
        } else {
            //Thread.sleep(500);
            display.appendText("\n" + attacker.getCharname() + " turn. Random skill to use.");
            skillrandom = getStarter();
            if (skillrandom == 1){
                //Thread.sleep(500);
                display.appendText("\n" + attacker.getCharname() + " will use \"" +attacker.getOffend() + "\"");
            } else {
                //Thread.sleep(500);
                display.appendText("\n" + attacker.getCharname() + " will use \"" +attacker.getNoffend() + "\"");
            }
            attackerdmg = Attacking(skillrandom);
        }
        display.appendText("\n========================================");

        //player choose defender skill cpu random
        if (defender == player1){
            do {
                assert defender != null;
                //Thread.sleep(500);
                display.appendText("\n" + defender.getCharname() + " prepare for defend - What skill do you want to use?\n1. " + defender.getDefend() + "\n2. " + defender.getNdefend()+ "\n3. Check my Status\n4. Check Enemy Status");

                defendchoice = Integer.parseInt(GUIHelper.getSkillchoice(defender.getDefend(),defender.getNdefend(),"Check my Status","Check Enemy Status"));
                //defendchoice = Integer.parseInt(console.next());
                if (defendchoice == 3) {
                    display.appendText("\n" + defender+"\n=================");
                } else if (defendchoice == 4) {
                    display.appendText("\n" + attacker+"\n=================");
                }
            } while (defendchoice!=1 && defendchoice!=2);

            defenderskill = Defending(defendchoice);

        } else {
            assert defender != null;
            //Thread.sleep(500);
            display.appendText("\n" + defender.getCharname() + " random skill for defend.");
            defendrandom = getStarter();
            if (defendrandom == 1) {
                //Thread.sleep(500);
                display.appendText("\n" + defender.getCharname() + " will use \"" + defender.getDefend()+"\"");
            } else {
                //Thread.sleep(500);
                display.appendText("\n" + defender.getCharname() + " will use \"" + defender.getNdefend()+"\"");
            }
            display.appendText("\n========================================");

            defenderskill = Defending(defendrandom);
        }
        //calculation for final damage
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
        p2stattext.setText(battleai.toString());

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

    public String getCPUDiff(){

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

            String result = CPUs.choice.getText();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
