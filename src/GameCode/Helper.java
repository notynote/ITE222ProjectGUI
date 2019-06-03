package GameCode;

/**+
 * This class contain frequency use function
 */
import java.util.Random;

public class Helper {

    /**+
     * Randomizer for int
     * @param min represent minimum number
     * @param max represent maximum number
     * @return result of a random
     */
    public static int getRandomNumberInRange(int min, int max) {

        //variable
        Random rsd = new Random();

        if (min >= max) {
            throw new IllegalArgumentException("Min cannot be greater than Max");
        }

        return rsd.nextInt((max-min)+1) + min;
    }


    /**+
     * This method random the weapon in weapon[]
     * @param armory array of weapon
     * @return a selected weapon
     */
    //Give weapon to charactor
    public static Weapon FoundWeapon(Weapon[] armory){

        int i = getRandomNumberInRange(0,4);

        return armory[i];

    }

}

