package GameCode;

public class Player extends Character {

    /**+
     * Default constructor for player character this also check the name input if it match the secreat name that lock onto a class
     * @param name come from the input window for player to decide their own character name
     */
    public Player(String name){
        super(name);

        //this function allow user to choose the class if they know a keyword
        //check if user put the keyword for class
        switch (name.toLowerCase()) {
            case "mage":
            case "wizard":
            case "boomboomboom":
                this.charclass = "Mage";
                break;
            case "warrior":
            case "swordsmen":
            case "shubshubshub":
                this.charclass = "Warrior";
                break;
            case "archer":
            case "hunter":
            case "pewpewpew":
                this.charclass = "Archer";
                break;
            case "notynotethehandsomeguy":
                this.charclass = "God";
                this.hp = 999999;
                this.str = 8888;
                this.wis = 7777;
                this.dex = 6666;
                break;

        }
    }

}

