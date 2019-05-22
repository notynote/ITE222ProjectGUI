package GameCode;
import java.util.Random;
import java.util.Scanner;

class Battle {

    //global variable
    private Character player1;
    private Character player2;
    private CPU battleai = null;
    private Random r = new Random();

    //Battle variable
    private int skillchoice = 0,defendchoice = 0;
    private String attackerskill = "";
    private String defenderskill = "";
    private int attackerstatus = 0;
    private int finaldmg;
    private Character attacker = null,defender = null;
    private int dodge = 0;
    private int turn = 0;
    private int foundweapon = 0;
    private Weapon holding;

    //Scanner
    private Scanner console = new Scanner(System.in);

    //Weapon Array
    private Weapon[] armory;

    //constructor Battle for 1 player vs cpu
    Battle(Character player1,String modeselect) throws InterruptedException{

        //variable
        int whowin,starter;
        this.player1 = player1;
        //int finish = 0;

        //preload weapon
        //Create Array of Weapon
        armory = new Weapon[5];

        armory[0] = new Weapon("Stick", 5);
        armory[1] = new Weapon("Wooden Sword", 10);
        armory[2] = new Weapon("Iron Sword", 20);
        armory[3] = new Weapon("Magic Sword", 30);
        armory[4] = new Weapon("Master Sword", 50);

        //start
        if (modeselect.equalsIgnoreCase("1")){
            for (int i = 1; i <= 10; i++ ) {
                this.battleai = new CPU(i);
                System.out.println("You are facing " + battleai.getCharname() + "\nDetail:\n" + battleai +"\n-=-=-=-=-=-=-");
                Thread.sleep(1000);
                //random who go first
                starter = getStarter();
                if (starter == 1) {
                    //player go first
                    whowin = FightingPVC(1);
                    Annoucer(whowin);
                    //reset turn count
                    turn = 0;
                    if (player1.hp <= 0) {
                        break;
                    }
                    int healamount = i*100; //recover stage multiply by hundred to hp
                    player1.hp += healamount;
                    System.out.println("*****\nYou recover " + healamount + " for winning\nNow you have "+ player1.hp +"\n*****\n\n");
                    Thread.sleep(500);

                } else if (starter == 2){
                    //CPU go first
                    whowin = FightingPVC(2);
                    Annoucer(whowin);
                    //reset turn count
                    turn = 0;
                    if (player1.hp <= 0) {
                        break;
                    }
                    int healamount = i*100;
                    player1.hp += healamount;
                    System.out.println("*****\nYour hp recovers by " + healamount + " for winning\nNow you have "+ player1.hp +"\n*****\n\n");
                    Thread.sleep(500);

                }
            }



            //} while (player1.hp > 0 || finish == 0);

            if (player1.hp > 0) {
                System.out.println("**********");
                Thread.sleep(50);
                System.out.println("!!YOU-WON!!");
                Thread.sleep(50);
                System.out.println("-CONGRATZ-");
                Thread.sleep(50);
                System.out.println("**********\n");
                Thread.sleep(1000);
            } else {
                System.out.println("!!GAME OVER!!\nxxxxxxxxxxx\n");
                Thread.sleep(1000);
            }

        } else {

            int cpudiffselect = 0;
            //Ask what difficulty user want
            do {
                try {
                    System.out.println("==========\nPlease Choose CPU Difficulty. . .\n0. Random \n1. Noob CPU\n2. Extremely Easy CPU\n3. Very Easy CPU\n4. Easy CPU\n5. Medium CPU" +
                            "\n6. Hard CPU\n7. Insane CPU\n8. Nightmare CPU\n9. Impossible CPU\n10. The End CPU");
                    cpudiffselect = Integer.parseInt(console.next());
                } catch (Exception ignore){

                }
            }
            while (cpudiffselect!=0 && cpudiffselect!=1 && cpudiffselect!=2 && cpudiffselect!=3 && cpudiffselect!=4 && cpudiffselect!=5 && cpudiffselect!=6
                    && cpudiffselect!=7 && cpudiffselect!=8 && cpudiffselect!=9 && cpudiffselect!=10);

            if (cpudiffselect==0) {
                //create random ai
                this.battleai = new CPU();
            } else {
                //create selected ai
                this.battleai = new CPU(cpudiffselect);

            }

            System.out.println("You are facing " + battleai.getCharname() + "\nDetail:\n" + battleai + "\n-=-=-=-=-=-=-=-=-");
            Thread.sleep(1000);
            //random who go first
            starter = getStarter();
            if (starter == 1) {
                //player go first
                whowin = FightingPVC(1);
                Annoucer(whowin);
                //reset turn count
                turn = 0;
            } else {
                //CPU go first
                whowin = FightingPVC(2);
                Annoucer(whowin);
                //reset turn count
                turn = 0;
            }
        }

    }

    //constructor Battle for 2 player
    Battle(Character player1, Character player2) throws InterruptedException{

        //variable
        int whowin,starter;
        this.player1 = player1;
        this.player2 = player2;

        //Random who go first
        starter = getStarter();
        if (starter == 1){
            //player 1 go first
            System.out.println(player1.getCharname() + " go first!!");
            Thread.sleep(500);
            whowin = FightingPvP(1);
            Annoucer(whowin);
            //reset turn count
            turn = 0;
        } else {
            //player 2 go first
            System.out.println(player2.getCharname() + " go first!!");
            Thread.sleep(500);
            whowin = FightingPvP(2);
            Annoucer(whowin);
            //reset turn count
            turn = 0;
        }
    }

    private  int FightingPVC(int Starter) throws InterruptedException{

        if (Starter == 1){ //player1 start first
            do {
                //player1 turn
                playvscpu(1);
                //if hp fall below + equal to 0 then attacker = winner then return winner and stop the fight
                if (battleai.hp <= 0){
                    return 1;
                }

                //CPU turn
                playvscpu(2);
                if (player1.hp <= 0){
                    return 3;
                }

            } while(true);

        } else { //if player2 start first

            do {
                //CPU turn
                playvscpu(2);
                if (player1.hp <= 0){
                    return 3;
                }

                //player1 turn
                playvscpu(1);
                //if hp fall below + equal to 0 then attacker = winner then return winner and stop the fight
                if (battleai.hp <= 0){
                    return 1;
                }

            } while(true);
        }

    }

    //Fighting Method Player vs Player
//    private int FightingPvP(Character player1,Character player2,int Starter){
    private int FightingPvP(int Starter) throws InterruptedException{
        //variable
        //int hasWinner = 0;
        int modeselect = 0;

        //ask for the mode to play
        do {
            try {
                System.out.println("==========\nPlease Choose your game mode. . .\n1. Normal Mode\n2. Attack Only Mode\n3. Auto Mode");
                modeselect = Integer.parseInt(console.next());
            } catch (Exception ignore) {

            }
        } while (modeselect!=1 && modeselect!=2 && modeselect!=3);

        if (Starter == 1){ //player1 start first
            do {
                //player1 turn
                if (modeselect == 1) {
                    fightturn(1);
                } else if (modeselect == 2){
                    AttackOnly(1);
                } else {
                    Automode(1);
                }
                //if hp fall below + equal to 0 then attacker = winner then return winner and stop the fight
                if (player2.hp <= 0){
                    return 1;
                }

                //player2 turn
                if (modeselect == 1) {
                    fightturn(2);
                } else if (modeselect == 2){
                    AttackOnly(2);
                } else {
                    Automode(2);
                }
                if (player1.hp <= 0){
                    return 2;
                }

            } while(true);

        } else { //if player2 start first

            do {
                //player2 turn
                if (modeselect == 1) {
                    fightturn(2);
                } else if (modeselect == 2){
                    AttackOnly(2);
                } else {
                    Automode(2);
                }
                if (player1.hp <= 0){
                    return 2;
                }

                //player1 turn
                if (modeselect == 1) {
                    fightturn(1);
                } else if (modeselect == 2){
                    AttackOnly(1);
                } else {
                    Automode(1);
                }
                if (player2.hp <= 0){
                    return 1;
                }

            } while(true);
        }

    }

    private void fightturn(int player) throws InterruptedException{

        //ask user to choose attack skill
        do {
            if (player == 1) {
                attacker = player1;
                defender = player2;
            } else {
                attacker = player2;
                defender = player1;
            }

            try {
                Thread.sleep(500);
                System.out.println(attacker.getCharname() + " turn. What skill do you want to use?\n1. " + attacker.getOffend() + "\n2. " + attacker.getNoffend() + "\n3. Check my Status\n4. Check Enemy Status");
                skillchoice = Integer.parseInt(console.next());
                if (skillchoice == 3) {
                    System.out.println(attacker+"\n=================");
                } else if (skillchoice == 4) {
                    System.out.println(defender+"\n=================");
                }
            } catch (Exception ignore) {

            }
        } while (skillchoice != 1 && skillchoice != 2);

        //defender select skill
        do {
            try {
                Thread.sleep(500);
                System.out.println(defender.getCharname() + " prepare for defend - What skill do you want to use?\n1. " + defender.getDefend() + "\n2. " + defender.getNdefend()+ "\n3. Check my Status\n4. Check Enemy Status");
                defendchoice = Integer.parseInt(console.next());
                if (defendchoice == 3) {
                    System.out.println(defender+"\n=================");
                } else if (defendchoice == 4) {
                    System.out.println(attacker+"\n=================");
                }
            } catch (Exception ignore) {

            }
        } while (defendchoice != 1 && defendchoice != 2);

        defenderskill = Defending(defendchoice);

        //calculation for final damage
        //get attack damage
        int attackerdmg = Attacking(skillchoice);
        //check the element advantage to determine final damage
        finaldmg = ElementCheck(attackerskill, defenderskill, attackerdmg);

        //calculate dodge
        this.dodge = Skill.Dodge(defender.getLuck());

        //if dodged
        if (this.dodge == 1) {

            System.out.println("***************\n" + attacker.getCharname() + " " + attackerskill + " " + defender.getCharname() + " for " + attackerdmg + " damage");
            Thread.sleep(500);
            System.out.println("***************\n" + "but " + defender.getCharname() + " DODGED the attack and take no damage\n***************");

        } else {
            //did not dodge

            System.out.println(attacker.getCharname() + " " + attackerskill + " " + defender.getCharname() + " for " + attackerdmg + " damage");
            Thread.sleep(500);
            System.out.println("but " + defender.getCharname() + " " + defenderskill + " the attack and take " + finaldmg + " damage");
            defender.hp -= finaldmg;
            Thread.sleep(500);
            System.out.println("==============\n" + defender.getCharname() + " now has " + defender.hp + " hp" + "\n==============");

        }

        //count turn
        turn++;

        //random found weapon chance (20%)
        foundweapon = Helper.getRandomNumberInRange(1,5);

        //if more than 5 turn and found weapon is true
        if (turn >= 10 && foundweapon == 3){

            //random 1 weapon
            holding = Helper.FoundWeapon(armory);
            System.out.println("\n!!!!!!!!!!\n\n" + attacker.getCharname() + " found a " + holding.Name + " (" + holding.Damage + " Damage)");
            Thread.sleep(500);
            System.out.println("!!!!!!!!!!\n\n" + attacker.getCharname() + " use " + holding.Name + " to attack " + defender.getCharname());
            defender.hp -= holding.Damage;
            Thread.sleep(500);
            System.out.println("==============\n" + defender.getCharname() + " now has " + defender.hp + " hp" + "\n==============");

        }

    }

    //attack only method
    private void AttackOnly(int player) throws InterruptedException {

        if (player == 1) {
            attacker = player1;
            defender = player2;
        } else if (player == 2) {
            attacker = player2;
            defender = player1;
        }

        do {
            try {
                System.out.println(attacker.getCharname() + " turn. What skill do you want to use?\n1. " + attacker.getOffend() + "\n2. " + attacker.getNoffend()+ "\n3. Check my Status\n4. Check Enemy Status");
                skillchoice = Integer.parseInt(console.next());
                if (skillchoice == 3) {
                    System.out.println(attacker+"\n=================");
                } else if (skillchoice == 4) {
                    System.out.println(defender+"\n=================");
                }
            } catch (Exception ignore) {

            }
        } while (skillchoice !=1 && skillchoice !=2);

        //get attack damage
        int attackerdmg = Attacking(skillchoice);

        //calculate dodge
        this.dodge = Skill.Dodge(defender.getLuck());

        //if dodged
        if (this.dodge == 1) {

            System.out.println("***************\n" + attacker.getCharname() + " " + attackerskill + " " + defender.getCharname() + " for " + attackerdmg + " damage");
            Thread.sleep(500);
            System.out.println("***************\n" + "but " + defender.getCharname() + " DODGED the attack and take no damage\n***************");

        } else {
            //if not dodge
            System.out.println(attacker.getCharname() + " " + attacker.getOffend() + " " + defender.getCharname() + " for " + attackerdmg + " damage");
            defender.hp -= attackerdmg;
            System.out.println("==============\n" + defender.getCharname() + " now has " + defender.hp + " hp" + "\n==============");
        }
        //count turn
        turn++;

        //random found weapon chance (20%)
        foundweapon = Helper.getRandomNumberInRange(1,5);

        //if more than 10 turn and found weapon is true
        if (turn >= 10 && foundweapon == 3){

            //random 1 weapon
            holding = Helper.FoundWeapon(armory);
            System.out.println("\n!!!!!!!!!!\n\n" + attacker.getCharname() + " found a " + holding.Name + " (" + holding.Damage + " Damage)");
            Thread.sleep(500);
            System.out.println("!!!!!!!!!!\n\n" + attacker.getCharname() + " use " + holding.Name + " to attack " + defender.getCharname());
            defender.hp -= holding.Damage;
            Thread.sleep(500);
            System.out.println("==============\n" + defender.getCharname() + " now has " + defender.hp + " hp" + "\n==============");

        }

    }

    //Auto Mode
    private void Automode(int player) throws InterruptedException{

        //ask user to choose attack skill
        if (player == 1) {
            attacker = player1;
            defender = player2;
        } else if (player == 2) {
            attacker = player2;
            defender = player1;
        }

        Thread.sleep(500);
        System.out.println(attacker.getCharname() + " turn. Random skill to use.");
        skillchoice = getStarter();
        if (skillchoice == 1){
            Thread.sleep(700);
            System.out.println(attacker.getCharname() + " will use \"" +attacker.getOffend() + "\"");
        } else {
            Thread.sleep(700);
            System.out.println(attacker.getCharname() + " will use \"" +attacker.getNoffend() + "\"");
        }
        System.out.println("========================================");

        //random defender skill
        Thread.sleep(500);
        System.out.println(defender.getCharname() + " random skill for defend.");
        defendchoice = getStarter();
        if (defendchoice == 1){
            Thread.sleep(700);
            System.out.println(defender.getCharname() + " will use \"" +defender.getDefend() + "\"");
        } else {
            Thread.sleep(700);
            System.out.println(defender.getCharname() + " will use \"" +defender.getNdefend() + "\"");
        }
        System.out.println("========================================");

        //calculation for final damage
        //get attack damage
        int attackerdmg = Attacking(skillchoice);
        //check the element advantage to determine final damage
        finaldmg = ElementCheck(attackerskill, defenderskill, attackerdmg);

        //calculate dodge
        this.dodge = Skill.Dodge(defender.getLuck());

        //if dodged
        if (this.dodge == 1) {

            System.out.println("***************\n" + attacker.getCharname() + " " + attackerskill + " " + defender.getCharname() + " for " + attackerdmg + " damage");
            Thread.sleep(500);
            System.out.println("***************\n" + "but " + defender.getCharname() + " DODGED the attack and take no damage\n***************");

        } else {
            //did not dodge

            System.out.println(attacker.getCharname() + " " + attackerskill + " " + defender.getCharname() + " for " + attackerdmg + " damage");
            Thread.sleep(500);
            System.out.println("but " + defender.getCharname() + " " + defenderskill + " the attack and take " + finaldmg + " damage");
            defender.hp -= finaldmg;
            Thread.sleep(500);
            System.out.println("==============\n" + defender.getCharname() + " now has " + defender.hp + " hp" + "\n==============");

        }

    }

    //player vs cpu fight mode
    //player choose then cpu random
    private void playvscpu(int player) throws InterruptedException{

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
        if (attacker == player1){
            do {
                try {
                    Thread.sleep(500);
                    System.out.println(attacker.getCharname() + " turn. What skill do you want to use?\n1. " + attacker.getOffend() + "\n2. " + attacker.getNoffend()+ "\n3. Check my Status\n4. Check Enemy Status");
                    skillchoice = Integer.parseInt(console.next());
                    if (skillchoice == 3) {
                        System.out.println(attacker+"\n=================");
                    } else if (skillchoice == 4) {
                        System.out.println(defender+"\n=================");
                    }
                } catch (Exception ignore) {

                }
            } while (skillchoice !=1 && skillchoice!=2);
            attackerdmg = Attacking(skillchoice);
        } else {
            Thread.sleep(500);
            System.out.println(attacker.getCharname() + " turn. Random skill to use.");
            skillrandom = getStarter();
            if (skillrandom == 1){
                Thread.sleep(500);
                System.out.println(attacker.getCharname() + " will use \"" +attacker.getOffend() + "\"");
            } else {
                Thread.sleep(500);
                System.out.println(attacker.getCharname() + " will use \"" +attacker.getNoffend() + "\"");
            }
            attackerdmg = Attacking(skillrandom);
        }
        System.out.println("========================================");

        //player choose defender skill cpu random
        if (defender == player1){
            do {
                assert defender != null;
                Thread.sleep(500);
                System.out.println(defender.getCharname() + " prepare for defend - What skill do you want to use?\n1. " + defender.getDefend() + "\n2. " + defender.getNdefend()+ "\n3. Check my Status\n4. Check Enemy Status");
                defendchoice = Integer.parseInt(console.next());
                if (defendchoice == 3) {
                    System.out.println(defender+"\n=================");
                } else if (defendchoice == 4) {
                    System.out.println(attacker+"\n=================");
                }
            } while (defendchoice!=1 && defendchoice!=2);

            defenderskill = Defending(defendchoice);

        } else {
            assert defender != null;
            Thread.sleep(500);
            System.out.println(defender.getCharname() + " random skill for defend.");
            defendrandom = getStarter();
            if (defendrandom == 1) {
                Thread.sleep(500);
                System.out.println(defender.getCharname() + " will use \"" + defender.getDefend()+"\"");
            } else {
                Thread.sleep(500);
                System.out.println(defender.getCharname() + " will use \"" + defender.getNdefend()+"\"");
            }
            System.out.println("========================================");

            defenderskill = Defending(defendrandom);
        }
        //calculation for final damage
        //check the element advantage to determine final damage
        finaldmg = ElementCheck(attackerskill, defenderskill, attackerdmg);

        //calculate dodge
        this.dodge = Skill.Dodge(defender.getLuck());

        //if dodged
        if (this.dodge == 1) {

            System.out.println("***************\n" + attacker.getCharname() + " " + attackerskill + " " + defender.getCharname() + " for " + attackerdmg + " damage");
            Thread.sleep(500);
            System.out.println("***************\n" + "but " + defender.getCharname() + " DODGED the attack and take no damage\n***************");

        } else {
            //did not dodge

            System.out.println(attacker.getCharname() + " " + attackerskill + " " + defender.getCharname() + " for " + attackerdmg + " damage");
            Thread.sleep(500);
            System.out.println("but " + defender.getCharname() + " " + defenderskill + " the attack and take " + finaldmg + " damage");
            defender.hp -= finaldmg;
            Thread.sleep(500);
            System.out.println("==============\n" + defender.getCharname() + " now has " + defender.hp + " hp" + "\n==============");

        }

        //count turn
        turn++;

        //random found weapon chance (20%)
        foundweapon = Helper.getRandomNumberInRange(1,5);

        //if more than 5 turn and found weapon is true
        if (turn >= 10 && foundweapon == 3){

            //random 1 weapon
            holding = Helper.FoundWeapon(armory);
            System.out.println("\n!!!!!!!!!!\n\n" + attacker.getCharname() + " found a " + holding.Name + " (" + holding.Damage + " Damage)");
            Thread.sleep(500);
            System.out.println("!!!!!!!!!!\n\n" + attacker.getCharname() + " use " + holding.Name + " to attack " + defender.getCharname());
            defender.hp -= holding.Damage;
            Thread.sleep(500);
            System.out.println("==============\n" + defender.getCharname() + " now has " + defender.hp + " hp" + "\n==============");

        }
    }

    //announcer method
    private void Annoucer(int winner) throws InterruptedException{

        if (winner == 1){
            System.out.println("**********\n" + player1.getCharname() + " WON the fight!!\n**********");
            Thread.sleep(500);
        } else if (winner == 2){
            System.out.println("**********\n" + player2.getCharname() + " WON the fight!!\n**********");
            Thread.sleep(500);
        } else {
            System.out.println("**********\n" + battleai.getCharname() + " WON the fight!!\n**********");
            Thread.sleep(300);
        }

    }



    //Random Starter method
    private int getStarter() {

        return r.nextInt((2-1)+1) + 1;
    }

    //Attacking Method
    private int Attacking(int skillchoice) {

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

            default:
                System.out.println("error");
        }

        return Skill.OffendDamage(attackerskill, attackerstatus);

    }

    //Defending Method
    private String Defending(int defendchoice){
        switch (defendchoice) {
            case 1:
                defenderskill = defender.getDefend();
                break;

            case 2:
                defenderskill = defender.getNdefend();
                break;

            default:
                System.out.println("error");
        }

        return defenderskill;
    }

    //Element Checker for final damage
    private int ElementCheck(String attackerskill,String defenderskill,int attackerdmg){

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


}
