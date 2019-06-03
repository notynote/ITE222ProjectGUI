package GameCode;

//import java util
import java.util.Random;

/**+
 * This class handle a skill damage calculation and dodge chance
 */
public class Skill {

    //Random object

    static Random rsd = new Random();

    /**+
     * Calculate Skill Damage using character status and randomization
     * @param usedskill is a skill that player chose in battle
     * @param cstatus is a status of the character object that attacking
     * @return the attack damage
     */
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

    /**+
     * Calculate Dodge by using character luck to random the number if it match with 2 4 8 then the character dodged successfully
     * @param luck is from the character luck status
     * @return the result of a dodge 1 is dodged and 0 is failed to dodge
     */
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

