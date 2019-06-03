package GameCode;

/**+
 * This class determine a CPU character
 */
public class CPU extends Character {

    //variable
    int level;

    /**+
     * default constructor (random cpu level)
     */
    public CPU(){
        super(Helper.getRandomNumberInRange(1,10));
    }

    /**+
     * constructor if player choosen cpu level in quick play mode
     * @param level determine a level to pass onto a constructor
     */
    public CPU(int level){
        super(level);
    }

}

