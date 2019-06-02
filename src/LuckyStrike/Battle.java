package LuckyStrike;

import GameCode.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public abstract class Battle {

    //Battle variable
    int skillchoice = 0,defendchoice = 0;
    String attackerskill = "";
    GameCode.Character attacker = null,defender = null;
    protected int dodge = 0;
    int turn = 0;
    protected int foundweapon = 0;
    protected Weapon holding;

    abstract void Annoucer(int winner);

    //Weapon Array
    private Weapon[] armory;

    Weapon[] loadWeapon(){
        //preload weapon
        //Create Array of Weapon
        armory = new Weapon[5];

        armory[0] = new Weapon("Stick", 5);
        armory[1] = new Weapon("Wooden Sword", 10);
        armory[2] = new Weapon("Iron Sword", 20);
        armory[3] = new Weapon("Magic Sword", 30);
        armory[4] = new Weapon("Master Sword", 50);

        return this.armory;
    }



    //Random Starter method
    int getStarter() {

        return GameCode.Helper.getRandomNumberInRange(1,2);
    }

    //define Character method
    GameCode.Player makePlayer(Pane p){

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
            return new GameCode.Player(charname);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new GameCode.Player("Test");
    }

    int FightingPVC(int Starter, Player player1, CPU battleai, ImageView p1img, ImageView p2img, TextArea display, TextArea p1stattext, TextArea p2stattext){

        if (Starter == 1){ //player1 start first
            do {
                //player1 turn
                playvscpu(1,player1,battleai,p1img,p2img,display,p1stattext,p2stattext);
                //if hp fall below + equal to 0 then attacker = winner then return winner and stop the fight
                if (battleai.hp <= 0){
                    return 1;
                }

                //CPU turn
                playvscpu(2,player1,battleai,p1img,p2img,display,p1stattext,p2stattext);
                if (player1.hp <= 0){
                    return 2;
                }

            } while(true);

        } else { //if player2 start first

            do {
                //CPU turn
                playvscpu(2,player1,battleai,p1img,p2img,display,p1stattext,p2stattext);
                if (player1.hp <= 0){
                    return 2;
                }

                //player1 turn
                playvscpu(1,player1,battleai,p1img,p2img,display,p1stattext,p2stattext);
                //if hp fall below + equal to 0 then attacker = winner then return winner and stop the fight
                if (battleai.hp <= 0){
                    return 1;
                }


            } while(true);
        }

    }

    //player vs cpu fight mode
    //player choose then cpu random
    private void playvscpu(int player,Player player1,CPU battleai,ImageView p1img,ImageView p2img,TextArea display,TextArea p1stattext,TextArea p2stattext) {

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
            int p1classtoimg = getSetimage(player1.getCharclass());
            setAtkimg(p1classtoimg,p1img);
            do {
                ChooseAttackSkill(display);
            } while (skillchoice !=1 && skillchoice!=2);
            attackerdmg = Attacking(skillchoice, attacker);
            attackerskill = getAtkname(skillchoice, attacker);
        } else {
            //set attack image
            int p2classtoimg = getSetimage(battleai.getCharclass());
            setAtkimg(p2classtoimg,p2img);
            display.appendText("\n" + attacker.getCharname() + " turn. Random skill to use.\n");
            skillrandom = getStarter();
            if (skillrandom == 1){
                display.appendText("\n" + attacker.getCharname() + " will use \"" +attacker.getOffend() + "\"");
            } else {
                display.appendText("\n" + attacker.getCharname() + " will use \"" +attacker.getNoffend() + "\"");
            }
            attackerdmg = Attacking(skillrandom, attacker);
            attackerskill = getAtkname(skillrandom, attacker);

        }
        display.appendText("\n========================================");

        //player choose defender skill cpu random
        String defenderskill;
        if (defender == player1){
            do {
                ChooseDefendSkill(display);
            } while (defendchoice !=1 && defendchoice !=2);

            defenderskill = getDefname(defendchoice,defender);

            //set image back
            int p2classtoimg = getSetimage(battleai.getCharclass());
            setClassimg(p2classtoimg,p2img);

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

            defenderskill = getDefname(defendrandom,defender);

            //set image back
            int p1classtoimg = getSetimage(player1.getCharclass());
            setClassimg(p1classtoimg,p1img);
        }
        //calculation for final damage
        //check the element advantage to determine final damage
        int finaldmg = ElementCheck(attackerskill, defenderskill, attackerdmg);

        //Calculate Dodge
        checkDodge(display,attackerskill,defenderskill,attackerdmg,finaldmg,1);

        //count turn
        turn++;

        //Calculate Weapon
        weaponUsage(display);

        //update status text
        p1stattext.setText(player1.toString());
        p2stattext.setText(battleai.toString());

    }


    private static String getSkillchoice(String one, String two){

        try {
            FXMLLoader ld = new FXMLLoader();
            Pane root = ld.load(Battle.class.getResource("SkillChoice.fxml").openStream());
            SkillChoice choose = ld.getController();

            Scene scene = new Scene(root);
            Stage nStage = new Stage();
            nStage.setTitle("Choose");
            nStage.setScene(scene);

            //Set button text
            choose.setButtonName(one,two);

            //p.setDisable(true);
            nStage.showAndWait();
            //p.setDisable(false);

            return choose.sendBack;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //get class to set image
    static int getSetimage(String charclass){

        if (charclass.equalsIgnoreCase("Warrior")){
            return 1;
        } else if (charclass.equalsIgnoreCase("Mage")){
            return 2;
        } else if (charclass.equalsIgnoreCase("Archer")){
            return 3;
        } else {
            return 0;
        }

    }

    //Element Checker for final damage
    static int ElementCheck(String attackerskill, String defenderskill, int attackerdmg){

        int finaldmg;
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

    //Attacking Method
    static int Attacking(int skillchoice, GameCode.Character attacker) {

        String attackerskill = "";
        int attackerstatus = 0;

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

        }

        return Skill.OffendDamage(attackerskill, attackerstatus);
    }

    void ChooseAttackSkill(TextArea display) {
        try {
            //Thread.sleep(500);
            display.appendText("\n" + attacker.getCharname() + " turn. What skill do you want to use?\n");

            skillchoice = Integer.parseInt(Objects.requireNonNull(getSkillchoice(attacker.getOffend(), attacker.getNoffend())));
            if (skillchoice == 3) {
                display.appendText("\n" + attacker+"\n=================");
            } else if (skillchoice == 4) {
                display.appendText("\n" + defender+"\n=================");
            }
        } catch (Exception ignore) {

        }
    }

    void ChooseDefendSkill(TextArea display) {
        try {
            //Thread.sleep(500);
            display.appendText("\n" + defender.getCharname() + " prepare for defend - What skill do you want to use?\n");

            defendchoice = Integer.parseInt(Objects.requireNonNull(getSkillchoice(defender.getDefend(), defender.getNdefend())));
            //defendchoice = Integer.parseInt(console.next());
            if (defendchoice == 3) {
                display.appendText("\n" + defender+"\n====================");
            } else if (defendchoice == 4) {
                display.appendText("\n" + attacker+"\n====================");
            }
        } catch (Exception ignore) {

        }
    }

    void checkDodge(TextArea display,String attackerskill,String defenderskill,int attackerdmg, int finaldmg,int mode){

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
            if (mode == 1) {
                display.appendText("\nbut " + defender.getCharname() + "\n**<" + defenderskill + ">** the attack and take\n" + finaldmg + " damage\n");
            }
            defender.hp -= finaldmg;
            display.appendText("\n====================\n" + defender.getCharname() + " now has " + defender.hp + " hp" + "\n====================\n");

        }
    }

    void weaponUsage(TextArea display){
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
    }

    //get attack name
    static String getAtkname(int skillchoice,GameCode.Character attacker){
        if (skillchoice == 1) {
            return attacker.getOffend();
        } else {
            return attacker.getNoffend();
        }
    }

    //get defend name
    static String getDefname(int skillchoice,GameCode.Character defender){
        if (skillchoice == 1) {
            return defender.getDefend();
        } else {
            return defender.getNdefend();
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


    //open about
    void About(Pane p){

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
