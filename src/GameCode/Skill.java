package GameCode;

//import java util
import java.util.Random;

public class Skill {

    //Random object

    static Random rsd = new Random();

    //Calculate Skill Damage
    public static int OffendDamage(String usedskill, int cstatus){

        //variable
        int offdamage = 0;
        if (!usedskill.equals("Normal Attack")) {
            if (cstatus <= 3) {
                offdamage = Helper.getRandomNumberInRange(10,30);
            } else if (cstatus <= 6) {
                offdamage = Helper.getRandomNumberInRange(40,60);
            } else if (cstatus == 9999) { //for secret class
                offdamage = 9999;
            } else {
                offdamage = Helper.getRandomNumberInRange(70,100);
            }
        } else {
            if (cstatus <= 9) {
                offdamage = Helper.getRandomNumberInRange(10, 30);
            } else if (cstatus <= 18) {
                offdamage = Helper.getRandomNumberInRange(40, 60);
            } else {
                offdamage = Helper.getRandomNumberInRange(70, 100);
            }
        }


        return offdamage;
    }

    //Calculate Dodge
    public static int Dodge(int luck){
        int chance = 0;

        chance = Helper.getRandomNumberInRange(0,luck);

        switch (chance){
            case 1:
            case 3:
            case 5:
            case 7:
            case 9:
            case 0:
            case 6:
                return 0;
            case 2:
            case 4:
            case 8:
                return 1;
        }

        return 0;

    }

}

