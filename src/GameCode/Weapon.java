package GameCode;

/**+
 * Weapon class contain a constructor for each weapon
 */
public class Weapon {

    //data variable
    public int Damage;
    public String Name;

    //constructor
    public Weapon(String name, int dmg){

        this.Name = name;
        //Random Damage
        this.Damage = dmg;

    }

}

