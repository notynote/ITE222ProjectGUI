package LuckyStrike;

//contain frequency use method in GUI

import GameCode.Skill;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

class GUIHelper {

    static String getSkillchoice(String one, String two){

        try {
            FXMLLoader ld = new FXMLLoader();
            Pane root = ld.load(GUIHelper.class.getResource("SkillChoice.fxml").openStream());
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

}
