package GameCode;

import java.util.Random;

public class Character {

    //variable
    public int hp;
    private int str;
    private int wis;
    private int dex;
    private int luck;
    private String offend,defend,noffend,ndefend = "";
    private String charclass,charname;

    //Create new character
    public Character(String name) {

        //Name
        this.charname = name;
        //System.out.println("What the name of your Character?");
        //this.charname = console.nextLine();

        //Random attribute
        this.hp = Helper.getRandomNumberInRange(100,500);
        this.str = Helper.getRandomNumberInRange(1,9);
        this.wis = Helper.getRandomNumberInRange(1,9);
        this.dex = Helper.getRandomNumberInRange(1,9);
        this.luck = Helper.getRandomNumberInRange(1,9);

        //this function allow user to choose the class if they know a keyword
        //check if user put the keyword for class
        switch (name.toLowerCase()){
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
            default: //no class selected
                //Random Class
                this.charclass = getRandomClass(1,3);

        }

        //Skill
        Skill(charclass);
    }

    //Default for CPU
    Character(int level){

        //determine cpu level as name
        switch (level){
            case 1:
                this.charname = "Noob CPU";
                this.hp = Helper.getRandomNumberInRange(50,100);
                this.str = 1;
                this.wis = 1;
                this.dex = 1;
                this.luck = 1;
                break;
            case 2:
                this.charname = "Extremely Easy CPU";
                this.hp = Helper.getRandomNumberInRange(50,300);
                this.str = Helper.getRandomNumberInRange(1,3);
                this.wis = Helper.getRandomNumberInRange(1,3);
                this.dex = Helper.getRandomNumberInRange(1,3);
                this.luck = 1;
                break;
            case 3:
                this.charname = "Very Easy CPU";
                this.hp = Helper.getRandomNumberInRange(50,500);
                this.str = Helper.getRandomNumberInRange(1,5);
                this.wis = Helper.getRandomNumberInRange(1,5);
                this.dex = Helper.getRandomNumberInRange(1,5);
                this.luck = 1;
                break;
            case 4:
                this.charname = "Easy CPU";
                this.hp = Helper.getRandomNumberInRange(100,300);
                this.str = Helper.getRandomNumberInRange(1,7);
                this.wis = Helper.getRandomNumberInRange(1,7);
                this.dex = Helper.getRandomNumberInRange(1,7);
                this.luck = 1;
                break;
            case 5:
                this.charname = "Medium CPU";
                this.hp = Helper.getRandomNumberInRange(100,500);
                this.str = Helper.getRandomNumberInRange(1,9);
                this.wis = Helper.getRandomNumberInRange(1,9);
                this.dex = Helper.getRandomNumberInRange(1,9);
                this.luck = 1;
                break;
            case 6:
                this.charname = "Hard CPU";
                this.hp = Helper.getRandomNumberInRange(300,500);
                this.str = Helper.getRandomNumberInRange(3,9);
                this.wis = Helper.getRandomNumberInRange(3,9);
                this.dex = Helper.getRandomNumberInRange(3,9);
                this.luck = 1;
                break;
            case 7:
                this.charname = "Insane CPU";
                this.hp = Helper.getRandomNumberInRange(300,800);
                this.str = Helper.getRandomNumberInRange(6,9);
                this.wis = Helper.getRandomNumberInRange(6,9);
                this.dex = Helper.getRandomNumberInRange(6,9);
                this.luck = 1;
                break;
            case 8:
                this.charname = "Nightmare CPU";
                this.hp = Helper.getRandomNumberInRange(500,800);
                this.str = 9;
                this.wis = 9;
                this.dex = 9;
                this.luck = 1;
                break;
            case 9:
                this.charname = "Impossible CPU";
                this.hp = Helper.getRandomNumberInRange(800,1500);
                this.str = Helper.getRandomNumberInRange(9,15);
                this.wis = Helper.getRandomNumberInRange(9,15);
                this.dex = Helper.getRandomNumberInRange(9,15);
                break;
            case 10:
                this.charname = "The End CPU";
                this.hp = Helper.getRandomNumberInRange(1000,1500);
                this.str = Helper.getRandomNumberInRange(9,20);
                this.wis = Helper.getRandomNumberInRange(9,20);
                this.dex = Helper.getRandomNumberInRange(9,20);
                this.luck = 1;
                break;
            default:
                this.charname = "You Just Cannot Win This . . .";
                this.hp = 9999;
                this.str = 9999;
                this.wis = 9999;
                this.dex = 9999;
                this.luck = 1;

        }


        //Random class
        this.charclass = getRandomClass(1,3);

        //Skill
        Skill(charclass);
    }

    //Random Class Method
    private String getRandomClass(int x, int y){

        Random r = new Random();

        int classint;

        classint = r.nextInt((y-x)+1) + x;
        switch (classint){
            case 1:
                return "Warrior";
            case 2:
                return "Mage";
            case 3:
                return "Archer";
            default:
                return "God";
        }

    }

    //Assign Skill
    private void Skill(String charclass){
        switch (charclass){
            case "Warrior":
                offend = "Stab";
                defend = "Shield Block";
                break;
            case "Mage":
                offend = "Cast Spell";
                defend = "Ice Wall";
                break;
            case "Archer":
                offend = "Shoot";
                defend = "Dodge";
                break;
            default:
                offend = "Kill Command";
                defend = "Disappear";
//                offend2 = "Fire";
//                defend2 = "Ice Wall";
//                offend3 = "Shoot";
//                defend3 = "Dodge";
        }
        //put normal attack
        noffend = "Normal Attack";
        ndefend = "Normal Defend";
    }

    public String toString(){

        String charskill = offend + ", " + defend + ", " + noffend + ", " + ndefend;
        return "Character Name is " + this.charname + "\nClass is " + this.charclass +
                "\nHP: " + this.hp + "\nSTR: " + this.str + "\nWIS: " + this.wis + "\nDEX: " + this.dex +
                "\nSkills: " + charskill;
    }

    //get methods

    public String getCharname() {
        return charname;
    }

    public String getCharclass() {
        return charclass;
    }

//    public int getHp(){
//        return this.hp;
//    }

    public int getStr(){
        return this.str;
    }

    public int getWis(){
        return this.wis;
    }

    public int getDex(){
        return this.dex;
    }

    public String getOffend(){
        return this.offend;
    }

    public String getDefend(){
        return this.defend;
    }

    public String getNoffend() {
        return noffend;
    }

    public String getNdefend() {
        return ndefend;
    }

    public int getLuck() {
        return luck;
    }
}

