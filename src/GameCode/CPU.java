package GameCode;

public class CPU extends Character {

    //variable
    int level;

    //default constructor (random cpu level)
    public CPU(){
        super(Helper.getRandomNumberInRange(1,10));
    }

    public CPU(int level){
        super(level);
    }

}

