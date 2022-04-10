import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Character // postać, jej cechy i akcje wykonywalne przez nią
{

    String name;
    int health;
    int max_health;
    int kills; // zabójstwa

    int dmg_taken; // obrażenia zadane przez gracza
    int dmg_dealt; // obrażenia otrzymane przez gracza

    double long_shot = 0; //dystans najdłuższego trafienia

    boolean alive; // czy postać jest dalej w grze

    Weapon r_weapon;//broń zasięgowa
    Weapon m_weapon;//broń białą
    Armor head;     //hełm
    Armor torso;    //korpus
    Armor pants;    //spodnie
    Armor prot;     //ochraniacze

    int ammunition; //ilość amunicji

    String deathTime = "--------||--------";

    List<Character> killed_players = new ArrayList<Character>();

    Random rand = new Random();

    //cechy gracza
    int strength;
    int vitality;
    int speed;
    int perception;
    int shooting_skills;
    int survival;

    // współczynnik mówiący ile obrażen dostała dana część ciałą
    int head_damages = 0;
    int left_leg_damages = 0;
    int right_leg_damages = 0;
    int left_arm_damages = 0;
    int right_arm_damages = 0;
    int torso_damages = 0;

    public Character(String name, int strength, int vitality, int speed, int perception, int shooting_skills, int survival, Weapon m, Weapon r, Armor head, Armor torso, Armor pants, Armor prot)
    {
        this.name = name;
        this.strength = strength;
        this.vitality = vitality;
        this.perception = perception;
        this.shooting_skills = shooting_skills;
        this.survival = survival;

        alive = true;

        ammunition = 0;

        kills = 0;

        health = 80 + (5 * vitality);
        max_health = health;
        r_weapon = r;
        m_weapon = m;
        this.head = head;
        this.torso = torso;
        this.pants = pants;
        this.prot = prot;


        dmg_taken = 0;
        dmg_dealt = 0;
    }

    public void setWeapon(Weapon w) //ustawia broń
    {
        if (w.type == 0)
            this.m_weapon = w;
        if (w.type == 1)
            this.r_weapon = w;
    }

    public void setArmor(Armor a) //ustawia ochronę
    {
        if (a.type == 0)
            this.head = a;
        if (a.type == 1)
            this.torso = a;
        if (a.type == 2)
            this.pants = a;
        if (a.type == 3)
            this.prot = a;
    }

    public void exchangeGear(Character ch, boolean kill) //wymiana ekwipunku z innym zawodnikiem
    {

        String finds[] = {" finds "+ ch.name +" lying dead among the grass.", " notices "+ch.name+" lying with no signs of life.",
        " sneaking through the woods finds " + ch.name+" dead with multiple wounds.", " comes across " + ch.name +"'s body on path."};

        String killed[] = {" after the fight chooses to check dead opponent's equipment.", ", after emotions have subsided, approaches dead enemy.", " searches " + ch.name + "'s body."};

        List<String> find;

        if (kill)
            find = Arrays.asList(killed);
        else
            find = Arrays.asList(finds);

        Weapon r = ch.r_weapon;
        Weapon m = ch.m_weapon;
        Armor a1 = ch.head;
        Armor a2 = ch.torso;
        Armor a3 = ch.pants;
        Armor a4 = ch.prot;

        int items_taken = 0;

        String message = (name + find.get(rand.nextInt(find.size())) + " Decides to take");

        if (((r.min_dmg + r.max_dmg)/2) > ((r_weapon.min_dmg + r_weapon.max_dmg)/2))
        {
            if (items_taken == 0)
                message += (" " + r.name);
            ch.r_weapon = new Weapon("none", 0, 0, 1);
            r_weapon = r;
            items_taken++;
        }
        if (((m.min_dmg + m.max_dmg)/2) > ((m_weapon.min_dmg + m_weapon.max_dmg)/2))
        {
            if (items_taken == 0)
                message += (" " + m.name);
            if (items_taken != 0)
                message += (", " + m.name);
            ch.m_weapon = new Weapon("bare hands", 5, 10, 0);
            m_weapon = m;
            items_taken++;
        }

        if (a1.dmg_protection < head.dmg_protection)
        {
            if (items_taken == 0)
                message += (" " + a1.name);
            if (items_taken != 0)
                message += (", " + a1.name);
            ch.head = new Armor("none", 1, 0);
            head = a1;
            items_taken++;
        }
        if (a2.dmg_protection < head.dmg_protection)
        {
            if (items_taken == 0)
                message += (" " + a2.name);
            if (items_taken != 0)
                message += (", " + a2.name);
            ch.torso = new Armor("t-shirt", 1, 1);
            torso = a2;
            items_taken++;

        }
        if (a3.dmg_protection < head.dmg_protection)
        {

            if (items_taken == 0)
                message += (" " + a3.name);
            if (items_taken != 0)
                message += (", " + a3.name);
            ch.pants = new Armor("track pants", 1, 2);
            pants = a3;
            items_taken++;
        }
        if (a4.dmg_protection < head.dmg_protection)
        {
            if (items_taken == 0)
                message += (" " + a4.name);
            if (items_taken != 0)
                message += (", " + a4.name);
            ch.prot = new Armor("none", 1, 3);
            pants = a4;
            items_taken++;
        }
        if (ch.ammunition > 0)
        {
            message += (" " + ch.ammunition + " bullets");
            this.ammunition += ch.ammunition;
            ch.ammunition = 0;
            items_taken++;
        }

        if(items_taken == 0)
        {
            message += " nothing";
        }


        message += " and keep going.";

        System.out.println(message);

    }

    public void exchangeGear(Weapon w)  //decyzja w sprawie znalezionej broni
    {
        String finds[] = {" finds " + w.name + " in a chest hidden under a tree", " finds the " + w.name + " by searching through the weapons containers",
        " finds a bag with the" + w.name +" inside", " notices the " + w.name+ " lying in the tall grass"};

        String mess_take[] = {" and decides to take it with him.", " and swaps his old weapon with it.", " and takes it without hesitation.", " and after a thorough inspection decides to take it."};
        String mess_not_take[] = {" but decides to leave that weapon behind.", " but pays no attention to it and leaves without it.", " but that weapon appears to be weaker than the weapon in the inventory."};

        List<String> find = Arrays.asList(finds);
        List<String> take = Arrays.asList(mess_take);
        List<String> not_take = Arrays.asList(mess_not_take);

        String message = (name + find.get(rand.nextInt(find.size())));

        if (w.type == 1 &&((w.min_dmg + w.max_dmg)/2) > ((r_weapon.min_dmg + r_weapon.max_dmg)/2)) //bierz broń
        {
            message += take.get(rand.nextInt(take.size()));
            setWeapon(w);
        }

        if (w.type == 0 && ((w.min_dmg + w.max_dmg)/2) > ((m_weapon.min_dmg + m_weapon.max_dmg)/2)) //bierz broń
        {
            message += take.get(rand.nextInt(take.size()));
            setWeapon(w);
        }

        if (w.type == 1 &&((w.min_dmg + w.max_dmg)/2) <= ((r_weapon.min_dmg + r_weapon.max_dmg)/2)) //nie bierz broni
            message += not_take.get(rand.nextInt(not_take.size()));


        if (w.type == 0 && ((w.min_dmg + w.max_dmg)/2) <= ((m_weapon.min_dmg + m_weapon.max_dmg)/2)) //nie bierz broni
            message += not_take.get(rand.nextInt(not_take.size()));


        System.out.println(message);
    }

    public void exchangeGear(Armor a) //decyzja w sprawie znalezionego pancerza
    {
        String finds[] = {" finds " + a.name + " in a chest hidden under a tree", " finds the " + a.name + " by searching through the containers",
                " finds a bag with the" + a.name +" inside", " notices the " + a.name+ " lying in the tall grass"};

        String mess_take[] = {" and decides to take it with him.", " and swaps his old pice of armor with it.", " and takes it without hesitation.", " and after a thorough inspection decides to take it."};
        String mess_not_take[] = {" but decides to leave that pice of armor behind.", " but pays no attention to it and leaves without it.", " but that pice of armor appears to be weaker than the armor in the inventory."};

        List<String> find = Arrays.asList(finds);
        List<String> take = Arrays.asList(mess_take);
        List<String> not_take = Arrays.asList(mess_not_take);

        String message = (name + find.get(rand.nextInt(find.size())));

        if (a.type == 0 && a.dmg_protection < head.dmg_protection)
        {
            message += take.get(rand.nextInt(take.size()));
            setArmor(a);
        }

        if (a.type == 1 && a.dmg_protection < head.dmg_protection)
        {
            message += take.get(rand.nextInt(take.size()));
            setArmor(a);
        }
        if (a.type == 2 && a.dmg_protection < head.dmg_protection)
        {
            message += take.get(rand.nextInt(take.size()));
            setArmor(a);
        }
        if (a.type == 3 && a.dmg_protection < head.dmg_protection)
        {
            message += take.get(rand.nextInt(take.size()));
            setArmor(a);
        }

        if (a.type == 0 && a.dmg_protection >= head.dmg_protection)
            message += not_take.get(rand.nextInt(not_take.size()));

        if (a.type == 1 && a.dmg_protection >= head.dmg_protection)
            message += not_take.get(rand.nextInt(not_take.size()));

        if (a.type == 2 && a.dmg_protection >= head.dmg_protection)
            message += not_take.get(rand.nextInt(not_take.size()));

        if (a.type == 3 && a.dmg_protection >= head.dmg_protection)
            message += not_take.get(rand.nextInt(not_take.size()));

        System.out.println(message);
    }

    public void statsOfPlayer()
    {
        System.out.println("");
        System.out.println("Name: " + name);
        System.out.println("Health: " + health + "/" + max_health);
        System.out.println("Kills: " + kills);
        System.out.println("Ammunition: " + ammunition);
        System.out.println("Ranged weapon: " + r_weapon.name);
        System.out.println("Melee weapon: " + m_weapon.name);
        System.out.println("Helmet: " + head.name);
        System.out.println("Torso:  " + torso.name);
        System.out.println("Pants: " + pants.name);
        System.out.println("Protective gear: " + prot.name);
        if (head_damages == 0)
            System.out.println("Head injury status: healthy");
        if (head_damages == 1)
            System.out.println("Head injury status: injured");
        if (head_damages == 2)
            System.out.println("Head injury status: seriously injured");

        if (left_leg_damages == 0)
            System.out.println("Left leg injury status: healthy");
        if (left_leg_damages == 1)
            System.out.println("Left leg injury status: injured");
        if (left_leg_damages == 2)
            System.out.println("Left leg injury status: seriously injured");

        if (right_leg_damages == 0)
            System.out.println("Right leg injury status: healthy");
        if (right_leg_damages == 1)
            System.out.println("Right leg injury status: injured");
        if (right_leg_damages == 2)
            System.out.println("Right leg injury status: seriously injured");

        if (left_arm_damages == 0)
            System.out.println("Left arm injury status: healthy");
        if (left_arm_damages == 1)
            System.out.println("Left arm injury status: injured");
        if (left_arm_damages == 2)
            System.out.println("Left arm injury status: seriously injured");

        if (right_arm_damages == 0)
            System.out.println("Right arm injury status: healthy");
        if (right_arm_damages == 1)
            System.out.println("Right arm injury status: injured");
        if (right_arm_damages == 2)
            System.out.println("Right arm injury status: seriously injured");

        if (torso_damages == 0)
            System.out.println("Torso injury status: healthy");
        if (torso_damages == 1)
            System.out.println("Torso injury status: injured");
        if (torso_damages == 2)
            System.out.println("Torso injury status: seriously injured");
        System.out.println("");
        try {Thread.sleep(1500);} catch (InterruptedException e) {e.printStackTrace();}
    }

    public void characterAccident(int day, int hour, int min) //wypadek w trakcie poszukiwań
    {
        String deaths[] = {" dies of poisoning.", " dies of dehydration.", " dies attacked by wild animals.",
                " dies from a fall from a great height.", " bleeds to death.", " dies from drowning.",
                " chills to death.", " dies from a venomous snake bite.",
                " in desperation commits suicide.", " dies in strange circumstances. The cause of death is unknown."};

        String time = "";

        if(min < 10)
            time = (String)("Day: " + day + " Time: " + hour + ":0" + min);
        else
            time = (String)("Day: " + day + " Time: " + hour + ":" + min);


        List<String> death = Arrays.asList(deaths);

        int dmg_type = rand.nextInt(6)+1;

        int dmg_head =   (int) (head.dmg_protection * ((1 + head_damages/3)*rand.nextInt(20 + 1) + 10));
        int dmg_l_arm =  (int) (torso.dmg_protection * prot.dmg_protection * ((1 + left_arm_damages/3)*rand.nextInt(20 + 1) + 10));
        int dmg_r_arm =  (int) (torso.dmg_protection * prot.dmg_protection * ((1 + right_arm_damages/3)*rand.nextInt(20 + 1) + 10));
        int dmg_l_leg =  (int) (pants.dmg_protection * prot.dmg_protection * ((1 + left_leg_damages/3)*rand.nextInt(20 + 1) + 10));
        int dmg_r_leg =  (int) (pants.dmg_protection * prot.dmg_protection * ((1 + right_leg_damages/3)*rand.nextInt(20 + 1) + 10));
        int dmg_torso =  (int) (torso.dmg_protection * ((1 + torso_damages/3)*rand.nextInt(20 + 1) + 10));

        switch (dmg_type)
        {
            case 1: //odrażenia głowy
            {
                String accidents[] = {" falls over and hits hard on the head", " falls down unhappily and dmages head", " hits head on a branch while running through the forest",
                        " injures head in a fight with animals from the forest", " falls from a tree and damages head"};

                List<String> acc = Arrays.asList(accidents);

                health -= dmg_head;

                dmg_taken += dmg_head;

                if (head_damages < 2)
                    head_damages += 1;

                if (health > 0)
                    System.out.println(name + acc.get(rand.nextInt(acc.size())) + " and takes " + dmg_head + " dmg.");
                if (health <= 0)
                {
                    dmg_taken += health;
                    health = 0;
                    alive = false;
                    deathTime = time;
                    System.out.println(name + death.get(rand.nextInt(death.size())));
                }
                break;
            }

            case 2: //odrażenia lewej ręki
            {
                String accidents[] = {" falls over and hits hard on the left arm", " falls down unhappily and dmages left arm", " hits the left arm on a branch while running through the forest",
                        " injures left arm in a fight with animals from the forest", " falls from a tree and damages left arm"};

                List<String> acc = Arrays.asList(accidents);

                health -= dmg_l_arm;

                dmg_taken += dmg_l_arm;

                if (left_arm_damages < 2)
                    left_arm_damages += 1;

                if (health > 0)
                    System.out.println(name + acc.get(rand.nextInt(acc.size())) + " and takes " + dmg_l_arm + " dmg.");
                if (health <= 0)
                {
                    dmg_taken += health;
                    health = 0;
                    alive = false;
                    deathTime = time;
                    System.out.println(name + death.get(rand.nextInt(death.size())));
                }
                break;
            }

            case 3: //odrażenia prawej ręki
            {
                String accidents[] = {" falls over and hits hard on the right arm", " falls down unhappily and dmages right arm", " hits the right arm on a branch while running through the forest",
                        " injures right arm in a fight with animals from the forest", " falls from a tree and damages right arm"};

                List<String> acc = Arrays.asList(accidents);

                health -= dmg_r_arm;

                dmg_taken += dmg_r_arm;

                if (right_arm_damages < 2)
                    right_arm_damages += 1;

                if (health > 0)
                    System.out.println(name + acc.get(rand.nextInt(acc.size())) + " and takes " + dmg_r_arm + " dmg.");
                if (health <= 0)
                {
                    dmg_taken += health;
                    health = 0;
                    alive = false;
                    deathTime = time;
                    System.out.println(name + death.get(rand.nextInt(death.size())));
                }
                break;
            }

            case 4: //odrażenia lewej nogi
            {
                String accidents[] = {" falls over and hits hard on the left leg", " falls down unhappily and dmages left leg", " hits left leg on a branch while running through the forest",
                        " injures left leg in a fight with animals from the forest", " falls from a tree and damages left leg"};

                List<String> acc = Arrays.asList(accidents);

                health -= dmg_l_leg;

                dmg_taken += dmg_l_leg;

                if (left_leg_damages < 2)
                    left_leg_damages += 1;

                if (health > 0)
                    System.out.println(name + acc.get(rand.nextInt(acc.size())) + " and takes " + dmg_l_leg + " dmg.");
                if (health <= 0)
                {
                    dmg_taken += health;
                    health = 0;
                    alive = false;
                    deathTime = time;
                    System.out.println(name + death.get(rand.nextInt(death.size())));
                }
                break;
            }

            case 5: //odrażenia prawej nogi
            {
                String accidents[] = {" falls over and hits hard on the right leg", " falls down unhappily and dmages right leg", " hits right leg on a branch while running through the forest",
                        " injures right leg in a fight with animals from the forest", " falls from a tree and damages right leg"};

                List<String> acc = Arrays.asList(accidents);

                health -= dmg_r_leg;

                dmg_taken += dmg_r_leg;

                if (right_leg_damages < 2)
                    right_leg_damages += 1;

                if (health > 0)
                    System.out.println(name + acc.get(rand.nextInt(acc.size())) + " and takes " + dmg_r_leg + " dmg.");
                if (health <= 0)
                {
                    dmg_taken += health;
                    health = 0;
                    alive = false;
                    deathTime = time;
                    System.out.println(name + death.get(rand.nextInt(death.size())));
                }
                break;
            }

            case 6: //odrażenia prawej nogi
            {
                String accidents[] = {" falls over and hits hard on the ribcage", " falls down unhappily and dmages the torso", " hits stomach on a branch while running through the forest",
                        " injures stomach in a fight with animals from the forest", " falls from a tree and damages ribcage"};

                List<String> acc = Arrays.asList(accidents);

                health -= dmg_torso;

                dmg_taken += dmg_torso;

                if (torso_damages < 2)
                    torso_damages += 1;

                if (health > 0)
                    System.out.println(name + acc.get(rand.nextInt(acc.size())) + " and takes " + dmg_torso + " dmg.");
                if (health <= 0)
                {
                    dmg_taken += health;
                    health = 0;
                    alive = false;
                    deathTime = time;
                    System.out.println(name + death.get(rand.nextInt(death.size())));

                }
                break;
            }
        }

    }

    public void findLoot(List<Character> dead, Weapon weapons[], Armor armors[]) //leczenie//broń/armor/ammo/trup
    {
        List<Weapon> weps = Arrays.asList(weapons);
        List<Armor> arms = Arrays.asList(armors);

        Weapon w = weps.get(rand.nextInt(weps.size()));
        Armor a = arms.get(rand.nextInt(arms.size()));

        int finding_type = 0;
        Character x = null;

        if(dead.size() == 0)
            finding_type = rand.nextInt(4)+1;
        else
        {
            finding_type = rand.nextInt(5)+1;
            x = dead.get(rand.nextInt(dead.size()));
        }


        if (finding_type == 1) // leczenie
        {
            String heals[] = {" finds a first aid kit and restores ", " looks through the crates finding adrenaline and restoring ", "  finds powerful painkillers and anti-inflammatories and restores  ",
                    " cleans their wounds and restores ", " rests and bandages wounds. Restores ", " transfuses blood under field conditions and restores "};

            List<String> heal = Arrays.asList(heals);


            int amount = (int) ((vitality) * (rand.nextInt(5 + 1)+1) + rand.nextInt(10 + 1)+5);

            this.health += amount;
            if (this.health + amount >= this.max_health)
                health = max_health;

            System.out.println(name + heal.get(rand.nextInt(heal.size())) + amount + " hp and now has " + health + "/" + max_health + "health");
        }

        if (finding_type == 2) // weapon
            exchangeGear(w);

        if (finding_type == 3) // armor
            exchangeGear(a);

        if (finding_type == 4) // ammo
        {
            int bullets_find = rand.nextInt(10+1)+1;

            String finds[] = {" finds " + bullets_find + " bullets in an ammmo crate.", "finds only " + bullets_find + " bullets by searching through the containers.",
                    "  finds a small bag with " + bullets_find +" bullets inside. ", " notices the " + bullets_find + "bullets lying on the ground."};

            List<String> find = Arrays.asList(finds);

            ammunition += bullets_find;

            System.out.println(name + find.get(rand.nextInt(find.size())));
        }

        if (finding_type == 5) // trup
            exchangeGear(x, false);

    }

    public void storyLines(int hour, int alive_people, Character ch1, Character ch2) //opowiada co robił bohater
    {
        String day_alone[] = {" wanders around the woods.", " sets up a camp.", " recalls the old days.",
                " patrolls the neighborhood.", " finds an empty crate.",
                " checks equipment.", " looks for other players.", " looks for food.",
                " finds the entrance to the cave.", " encounters the river.",
                " loses orientation in the field.", " cooks their food before the fire is extinguished.",
                " lights the fire.", " thinks of victory.", " looks for firewood.",
                " screams for help.", " sees smoke rising in the distance, but decides not to check it.",
                " sees smoke rising in the distance and decides to check it.",
                " picks up fruit.", " watches a group of wild animals.", " looks for food.",
                " satisfies thirst with water from the stream.", " hunts wild animals.",
                " is looking for equipment.", " plans ahead with their trap.",
                "  escapes from a spotted opponent to avoid a fight.",
                " searches the remains of an abandoned camp.", " stops to gain strength.",
                " runs aimlessly between the trees.", " observes the surroundings, hidden on a tree.",
                " hides their stuff in a tree trunk.", " finds supplies that someone hid in a tree.",
                " develops a plan for the next few hours."};
        String night_alone[] = {" tries to rest after a hard day but feels being watched from the darkness.",
                " decides to take a nap on a tree.", "  tries to fall asleep but nightmares and noises from the forest make it difficult.",
                " decides not to rest and changes position in the dark.", " looks out for sources of light that may give away the positions of other players.",
                " is resting by the bonfire.", " is putting out campfire because there is danger of being noticed."};

        String one[] = {" follows the unconscious " + ch1.name + ", hiding in the grass.", " and " + ch1.name + " pass each other in the forest.",
                " sees " + ch1.name + " in the distance.", " destroys the trap set by " + ch1.name + ".", " tries to track down " + ch1.name + ".",
                " and " + ch1.name + " confess their hatred.", " threatens " + ch1.name + " with brutal death."};
        String two[] = {" sees " + ch1.name + "and " + ch2.name +" in the distance.",
                " tries to close the distance to " + ch1.name + " but sees " + ch2.name + " patrolling the area.",
                " notices " + ch1.name + " whom he suspects of conspiring with " + ch2.name+ ".",
                " plans to eliminate " + ch1.name + " and " + ch2.name + " because he considers them a serious threat."};

        List<String> day = Arrays.asList(day_alone);
        List<String> night = Arrays.asList(night_alone);
        List<String> one_player = Arrays.asList(one);
        List<String> two_players = Arrays.asList(two);

        int scenario = 0;

        if(alive_people > 2)
            scenario = rand.nextInt(3)+1;
        if(alive_people == 2)
            scenario = rand.nextInt(2)+1;

        if (hour > 23 || hour < 6)//noc
        {
            if(scenario == 1)
                System.out.println(name + night.get(rand.nextInt(night.size())));
            if(scenario == 2)
                System.out.println(name + one_player.get(rand.nextInt(one_player.size())));
            if(scenario == 3)
                System.out.println(name + two_players.get(rand.nextInt(two_players.size())));
        }
        if (hour <= 23 && hour >= 6)//dzień
        {
            if(scenario == 1)
                System.out.println(name + day.get(rand.nextInt(day.size())));
            if(scenario == 2)
                System.out.println(name + one_player.get(rand.nextInt(one_player.size())));
            if(scenario == 3)
                System.out.println(name + two_players.get(rand.nextInt(two_players.size())));
        }
    }

    public void fightEnemy(Character actor, Character enemy, int day, int hour, int min) // walka z jednym wrogiem
    {
        boolean error = true;
        String time = "";

        if(min < 10)
            time = (String)("Day: " + day + " Time: " + hour + ":0" + min);
        else
            time = (String)("Day: " + day + " Time: " + hour + ":" + min);

        //sprawdzam kto jest uzbrojony
        boolean r_me_armed = false;
        boolean r_enemy_armed = false;
        boolean m_me_armed = false;
        boolean m_enemy_armed = false;

        boolean me_attack_will = false;//czy  walczyć
        boolean enemy_attack_will = false;//czy  walczyć

        double distance = (200)*rand.nextDouble()+2;

        if (m_weapon.name != "bare hand")
            m_me_armed = true;

        if (enemy.m_weapon.name != "bare hand")
            m_enemy_armed = true;

        if (m_weapon.name != "bare hand" && ammunition != 0)
            r_me_armed = true;

        if (enemy.m_weapon.name != "bare hand" && enemy.ammunition != 0)
            r_enemy_armed = true;

        if (!r_me_armed && !m_me_armed && !r_enemy_armed && !m_enemy_armed)
            me_attack_will = true;
        if (r_me_armed && !m_me_armed && !r_enemy_armed && !m_enemy_armed)
            me_attack_will = true;
        if (!r_me_armed && m_me_armed && !r_enemy_armed && !m_enemy_armed)
            me_attack_will = true;
        if (r_me_armed && m_me_armed && !r_enemy_armed && !m_enemy_armed)
            me_attack_will = true;
        if (r_me_armed && m_me_armed && r_enemy_armed && !m_enemy_armed)
            me_attack_will = true;
        if (r_me_armed && m_me_armed && !r_enemy_armed && m_enemy_armed)
            me_attack_will = true;
        if (r_me_armed && m_me_armed && r_enemy_armed && m_enemy_armed)
            me_attack_will = true;

        if (!r_me_armed && !m_me_armed && !r_enemy_armed && !m_enemy_armed)
            enemy_attack_will = true;
        if (!r_me_armed && !m_me_armed && r_enemy_armed && !m_enemy_armed)
            enemy_attack_will = true;
        if (!r_me_armed && !m_me_armed && !r_enemy_armed && m_enemy_armed)
            enemy_attack_will = true;
        if (!r_me_armed && !m_me_armed && r_enemy_armed && m_enemy_armed)
            enemy_attack_will = true;
        if (r_me_armed && !m_me_armed && r_enemy_armed && m_enemy_armed)
            enemy_attack_will = true;
        if (!r_me_armed && m_me_armed && r_enemy_armed && m_enemy_armed)
            enemy_attack_will = true;
        if (r_me_armed && m_me_armed && r_enemy_armed && m_enemy_armed)
            enemy_attack_will = true;

        if (!enemy_attack_will)
            enemy_attack_will = rand.nextBoolean();
        if (!me_attack_will)
            me_attack_will = rand.nextBoolean();

        int flee_chance = rand.nextInt(101)+1;
        int enemy_flee = 20 + 5 * enemy.speed - (6 * enemy.left_leg_damages) - (6 * enemy.left_leg_damages);

        if (!enemy_attack_will && me_attack_will && flee_chance < enemy_flee)// wróg ucieka
        {
            error = false;
            String scenario[] = {name + " tries to attack but " + enemy.name + " disappears into the trees.",
                                 name + " tried to attack " + enemy.name + ", but " + enemy.name + " escaped alive.",
                                 name + " was detected by " + enemy.name +", who escaped before " + name + " could attack."};

            List<String> say = Arrays.asList(scenario);

            System.out.println(say.get(rand.nextInt(say.size())));
        }

        else if (enemy_attack_will && !me_attack_will && flee_chance < enemy_flee)// gracz ucieka
        {
            error = false;
            String scenario[] = {name + " detected " + enemy.name + " but was afraid to attack.",
                                 name + " spotted " + enemy.name + " and after watching for a while decided not to attack.",
                                 name + " does not attack  " + enemy.name +", who is much better armed."};

            List<String> say = Arrays.asList(scenario);

            System.out.println(say.get(rand.nextInt(say.size())));
        }

        else if ((enemy_attack_will && me_attack_will) || (!enemy_attack_will && me_attack_will && flee_chance < enemy_flee))// oboje walczymy
        {
            error = false;
            int enemy_dmg;
            int me_dmg;
            int part_damaged = rand.nextInt(6) + 1;
            int en_part_damaged = rand.nextInt(6) + 1;

            int enemy_bullets_used = rand.nextInt(10 + 1) + 1;
            int me_bullets_used = rand.nextInt(10 + 1) + 1;

            if (enemy.ammunition < enemy_bullets_used)
                enemy_bullets_used = enemy.ammunition;

            if (ammunition < me_bullets_used)
                me_bullets_used = ammunition;

            if (distance < 10) //broń biała
            {
                int enemy_hit_chance = rand.nextInt(101) + 1;
                int me_hit_chance = rand.nextInt(101) + 1;
                enemy_dmg = enemy.strength * 2 + rand.nextInt(enemy.m_weapon.max_dmg) + enemy.m_weapon.min_dmg;
                me_dmg = strength * 2 + rand.nextInt(m_weapon.max_dmg) + m_weapon.min_dmg;

                int en_chance = 60 + 6 * enemy.speed - (6 * enemy.left_arm_damages) - (6 * enemy.left_arm_damages);
                int me_chance = 60 + 6 * speed - (6 * left_arm_damages) - (6 * left_arm_damages);

                System.out.println(enemy.name + " failed to escape from " + name + "'s attack.");

                if (me_hit_chance <= me_chance) // trafiamy
                {
                    switch (part_damaged) {
                        case 1: {
                            System.out.println(name + " hits " + enemy.name + " with the " + m_weapon.name + " on the head and inflicts " + ((int) (enemy.head.dmg_protection * ((1 + enemy.head_damages / 3) * me_dmg))) + " damage.");
                            enemy.health -= (int) (enemy.head.dmg_protection * ((1 + enemy.head_damages / 3) * me_dmg));
                            enemy.dmg_taken += (int) (enemy.head.dmg_protection * ((1 + enemy.head_damages / 3) * me_dmg));
                            dmg_dealt += (int) (enemy.head.dmg_protection * ((1 + enemy.head_damages / 3) * me_dmg));
                            if (enemy.head_damages < 2)
                                enemy.head_damages += 1;
                            break;
                        }
                        case 2: {
                            System.out.println(name + " hits " + enemy.name + " with the " + m_weapon.name + " in the left leg and inflicts " + ((int) (enemy.pants.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.left_leg_damages / 3) * me_dmg))) + " damage.");
                            enemy.health -= (int) (enemy.pants.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.left_leg_damages / 3) * me_dmg));
                            enemy.dmg_taken += (int) (enemy.pants.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.left_leg_damages / 3) * me_dmg));
                            dmg_dealt += (int) (enemy.pants.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.left_leg_damages / 3) * me_dmg));
                            if (enemy.left_leg_damages < 2)
                                enemy.left_leg_damages += 1;
                            break;
                        }
                        case 3: {
                            System.out.println(name + " hits " + enemy.name + " with the " + m_weapon.name + " in the right leg and inflicts " + ((int) (enemy.pants.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.right_leg_damages / 3) * me_dmg))) + " damage.");
                            enemy.health -= (int) (enemy.pants.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.right_leg_damages / 3) * me_dmg));
                            enemy.dmg_taken += (int) (enemy.pants.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.right_leg_damages / 3) * me_dmg));
                            dmg_dealt += (int) (enemy.pants.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.right_leg_damages / 3) * me_dmg));
                            if (enemy.right_leg_damages < 2) {
                                enemy.right_leg_damages += 1;
                            }
                            break;
                        }
                        case 4: {
                            System.out.println(name + " hits " + enemy.name + " with the " + m_weapon.name + " on the left arm and inflicts " + ((int) (enemy.torso.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.left_arm_damages / 3) * me_dmg))) + " damage.");
                            enemy.health -= (int) (enemy.torso.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.left_arm_damages / 3) * me_dmg));
                            enemy.dmg_taken += (int) (enemy.torso.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.left_arm_damages / 3) * me_dmg));
                            dmg_dealt += (int) (enemy.torso.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.left_arm_damages / 3) * me_dmg));
                            if (enemy.left_arm_damages < 2) {
                                enemy.left_arm_damages += 1;
                            }
                            break;
                        }
                        case 5: {
                            System.out.println(name + " hits " + enemy.name + " with the " + m_weapon.name + " on the right arm and inflicts " + ((int) (enemy.torso.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.right_arm_damages / 3) * me_dmg))) + " damage.");
                            enemy.health -= (int) (enemy.torso.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.right_arm_damages / 3) * me_dmg));
                            enemy.dmg_taken += (int) (enemy.torso.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.right_arm_damages / 3) * me_dmg));
                            dmg_dealt += (int) (enemy.torso.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.right_arm_damages / 3) * me_dmg));
                            if (enemy.right_arm_damages < 2) {
                                enemy.right_arm_damages += 1;
                            }
                            break;
                        }
                        case 6: {
                            System.out.println(name + " hits " + enemy.name + " with the " + m_weapon.name + " on the torso and inflicts " + ((int) (enemy.torso.dmg_protection * ((1 + enemy.torso_damages / 3) * me_dmg))) + " damage.");
                            enemy.health -= (int) (enemy.torso.dmg_protection * ((1 + enemy.torso_damages / 3) * me_dmg));
                            enemy.dmg_taken += (int) (enemy.torso.dmg_protection * ((1 + enemy.torso_damages / 3) * me_dmg));
                            dmg_dealt += (int) (enemy.torso.dmg_protection * ((1 + enemy.torso_damages / 3) * me_dmg));
                            if (enemy.torso_damages < 2) {
                                enemy.torso_damages += 1;
                            }
                            break;
                        }
                    }
                } else //my nie trafiamy
                {
                    error = false;
                    System.out.println(name + " tries to hit " + enemy.name + " with " + m_weapon.name + ", but misses.");
                }

                if (enemy.health > 0) // wróg atakuje
                {
                    error = false;
                    if (enemy_hit_chance <= en_chance) // wróg trafia
                    {
                        switch (en_part_damaged) {
                            case 1: {
                                System.out.println(enemy.name + " hits " + name + " with the " + enemy.m_weapon.name + " on the head and inflicts " + ((int) (head.dmg_protection * ((1 + head_damages / 3) * enemy_dmg))) + " damage.");
                                health -= (int) (head.dmg_protection * ((1 + head_damages / 3) * enemy_dmg));
                                dmg_taken += (int) (head.dmg_protection * ((1 + head_damages / 3) * enemy_dmg));
                                enemy.dmg_dealt += (int) (head.dmg_protection * ((1 + head_damages / 3) * enemy_dmg));
                                if (head_damages < 2)
                                    head_damages += 1;
                                break;
                            }
                            case 2: {
                                System.out.println(enemy.name + " hits " + name + " with the " + enemy.m_weapon.name + " in the left leg and inflicts " + ((int) (pants.dmg_protection * prot.dmg_protection * ((1 + left_leg_damages / 3) * enemy_dmg))) + " damage.");
                                health -= (int) (pants.dmg_protection * prot.dmg_protection * ((1 + left_leg_damages / 3) * enemy_dmg));
                                dmg_taken += (int) (pants.dmg_protection * prot.dmg_protection * ((1 + left_leg_damages / 3) * enemy_dmg));
                                enemy.dmg_dealt += (int) (pants.dmg_protection * prot.dmg_protection * ((1 + left_leg_damages / 3) * enemy_dmg));
                                if (left_leg_damages < 2)
                                    left_leg_damages += 1;

                                break;
                            }
                            case 3: {
                                System.out.println(enemy.name + " hits " + name + " with the " + enemy.m_weapon.name + " in the right leg and inflicts " + ((int) (pants.dmg_protection * prot.dmg_protection * ((1 + right_leg_damages / 3) * enemy_dmg))) + " damage.");
                                health -= (int) (pants.dmg_protection * prot.dmg_protection * ((1 + right_leg_damages / 3) * enemy_dmg));
                                dmg_taken += (int) (pants.dmg_protection * prot.dmg_protection * ((1 + right_leg_damages / 3) * enemy_dmg));
                                enemy.dmg_dealt += (int) (pants.dmg_protection * prot.dmg_protection * ((1 + right_leg_damages / 3) * enemy_dmg));
                                if (right_leg_damages < 2) {
                                    enemy.right_leg_damages += 1;
                                }
                                break;
                            }
                            case 4: {
                                System.out.println(enemy.name + " hits " + name + " with the " + enemy.m_weapon.name + " on the left arm and inflicts " + ((int) (torso.dmg_protection * prot.dmg_protection * ((1 + left_arm_damages / 3) * enemy_dmg))) + " damage.");
                                health -= (int) (torso.dmg_protection * prot.dmg_protection * ((1 + left_arm_damages / 3) * enemy_dmg));
                                dmg_taken += (int) (torso.dmg_protection * prot.dmg_protection * ((1 + left_arm_damages / 3) * enemy_dmg));
                                enemy.dmg_dealt += (int) (torso.dmg_protection * prot.dmg_protection * ((1 + left_arm_damages / 3) * enemy_dmg));
                                if (left_arm_damages < 2) {
                                    left_arm_damages += 1;
                                }
                                break;
                            }
                            case 5: {
                                System.out.println(enemy.name + " hits " + name + " with the " + enemy.m_weapon.name + " on the right arm and inflicts " + ((int) (torso.dmg_protection * prot.dmg_protection * ((1 + right_arm_damages / 3) * enemy_dmg))) + " damage.");
                                enemy.health -= (int) (torso.dmg_protection * prot.dmg_protection * ((1 + right_arm_damages / 3) * enemy_dmg));
                                enemy.dmg_taken += (int) (torso.dmg_protection * prot.dmg_protection * ((1 + right_arm_damages / 3) * enemy_dmg));
                                enemy.dmg_dealt += (int) (torso.dmg_protection * prot.dmg_protection * ((1 + right_arm_damages / 3) * enemy_dmg));
                                if (right_arm_damages < 2) {
                                    right_arm_damages += 1;
                                }
                                break;
                            }
                            case 6: {
                                System.out.println(enemy.name + " hits " + name + " with the " + enemy.m_weapon.name + " on the torso and inflicts " + ((int) (torso.dmg_protection * ((1 + torso_damages / 3) * enemy_dmg))) + " damage.");
                                health -= (int) (torso.dmg_protection * ((1 + torso_damages / 3) * enemy_dmg));
                                dmg_taken += (int) (torso.dmg_protection * ((1 + torso_damages / 3) * enemy_dmg));
                                enemy.dmg_dealt += (int) (torso.dmg_protection * ((1 + torso_damages / 3) * enemy_dmg));
                                if (torso_damages < 2) {
                                    torso_damages += 1;
                                }
                                break;
                            }
                        }
                    } else //my nie trafiamy
                    {
                        error = false;
                        System.out.println(enemy.name + " tries to hit " + name + " with " + enemy.m_weapon.name + ", but misses.");
                    }
                }
            }
            else if (enemy.r_weapon.max_dmg != 0 && actor.r_weapon.max_dmg != 0)//walka na odległość
            {
                error = false;
                int enemy_hit_chance = rand.nextInt(101) + 1;
                int me_hit_chance = rand.nextInt(101) + 1;
                int e_max = enemy.r_weapon.max_dmg;
                int p_max = actor.r_weapon.max_dmg;
                enemy_dmg = rand.nextInt(e_max) + enemy.r_weapon.min_dmg;
                me_dmg = rand.nextInt(p_max) + r_weapon.min_dmg;

                int en_chance = 30 + 6 * enemy.shooting_skills - (int)(distance/2);
                int me_chance = 30 + 6 * shooting_skills - (int)(distance/2);

                System.out.println(enemy.name + " failed to escape from " + name + "'s attack.");
                System.out.println(name + " takes " + me_bullets_used + " shots.");
                ammunition -= me_bullets_used;

                if (me_hit_chance <= me_chance && ammunition + me_bullets_used > 0) // trafiamy
                {
                    if (distance > long_shot)
                        long_shot = distance;
                    switch (part_damaged) {
                        case 1: {
                            System.out.println(name + " hits " + enemy.name + " with the " + r_weapon.name + " on the head and inflicts " + ((int) (enemy.head.dmg_protection * ((1 + enemy.head_damages / 3) * me_dmg))) + " damage, from " + String.format("%.2f", distance) + "meters.");
                            enemy.health -= (int) (enemy.head.dmg_protection * ((1 + enemy.head_damages / 3) * me_dmg));
                            enemy.dmg_taken += (int) (enemy.head.dmg_protection * ((1 + enemy.head_damages / 3) * me_dmg));
                            dmg_dealt += (int) (enemy.head.dmg_protection * ((1 + enemy.head_damages / 3) * me_dmg));
                            if (enemy.head_damages < 2)
                                enemy.head_damages += 1;
                            break;
                        }
                        case 2: {
                            System.out.println(name + " hits " + enemy.name + " with the " + r_weapon.name + " in the left leg and inflicts " + ((int) (enemy.pants.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.left_leg_damages / 3) * me_dmg))) + " damage, from " + String.format("%.2f", distance) + "meters.");
                            enemy.health -= (int) (enemy.pants.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.left_leg_damages / 3) * me_dmg));
                            enemy.dmg_taken += (int) (enemy.pants.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.left_leg_damages / 3) * me_dmg));
                            dmg_dealt += (int) (enemy.pants.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.left_leg_damages / 3) * me_dmg));
                            if (enemy.left_leg_damages < 2)
                                enemy.left_leg_damages += 1;

                            break;
                        }
                        case 3: {
                            System.out.println(name + " hits " + enemy.name + " with the " + r_weapon.name + " in the right leg and inflicts " + ((int) (enemy.pants.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.right_leg_damages / 3) * me_dmg))) + " damage, from " + String.format("%.2f", distance) + "meters.");
                            enemy.health -= (int) (enemy.pants.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.right_leg_damages / 3) * me_dmg));
                            enemy.dmg_taken += (int) (enemy.pants.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.right_leg_damages / 3) * me_dmg));
                            dmg_dealt += (int) (enemy.pants.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.right_leg_damages / 3) * me_dmg));
                            if (enemy.right_leg_damages < 2) {
                                enemy.right_leg_damages += 1;
                            }
                            break;
                        }
                        case 4: {
                            System.out.println(name + " hits " + enemy.name + " with the " + r_weapon.name + " on the left arm and inflicts " + ((int) (enemy.torso.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.left_arm_damages / 3) * me_dmg))) + " damage, from " + String.format("%.2f", distance) + "meters.");
                            enemy.health -= (int) (enemy.torso.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.left_arm_damages / 3) * me_dmg));
                            enemy.dmg_taken += (int) (enemy.torso.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.left_arm_damages / 3) * me_dmg));
                            dmg_dealt += (int) (enemy.torso.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.left_arm_damages / 3) * me_dmg));
                            if (enemy.left_arm_damages < 2) {
                                enemy.left_arm_damages += 1;
                            }
                            break;
                        }
                        case 5: {
                            System.out.println(name + " hits " + enemy.name + " with the " + r_weapon.name + " on the right arm and inflicts " + ((int) (enemy.torso.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.right_arm_damages / 3) * me_dmg))) +" damage, from " + String.format("%.2f", distance) + "meters.");
                            enemy.health -= (int) (enemy.torso.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.right_arm_damages / 3) * me_dmg));
                            enemy.dmg_taken += (int) (enemy.torso.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.right_arm_damages / 3) * me_dmg));
                            dmg_dealt += (int) (enemy.torso.dmg_protection * enemy.prot.dmg_protection * ((1 + enemy.right_arm_damages / 3) * me_dmg));
                            if (enemy.right_arm_damages < 2) {
                                enemy.right_arm_damages += 1;
                            }
                            break;
                        }
                        case 6: {
                            System.out.println(name + " hits " + enemy.name + " with the " + r_weapon.name + " on the torso and inflicts " + ((int) (enemy.torso.dmg_protection * ((1 + enemy.torso_damages / 3) * me_dmg))) + " damage, from " + String.format("%.2f", distance) + "meters.");
                            enemy.health -= (int) (enemy.torso.dmg_protection * ((1 + enemy.torso_damages / 3) * me_dmg));
                            enemy.dmg_taken += (int) (enemy.torso.dmg_protection * ((1 + enemy.torso_damages / 3) * me_dmg));
                            dmg_dealt += (int) (enemy.torso.dmg_protection * ((1 + enemy.torso_damages / 3) * me_dmg));
                            if (enemy.torso_damages < 2) {
                                enemy.torso_damages += 1;
                            }
                            break;
                        }
                    }
                }else //my nie trafiamy
                {
                    error = false;
                    System.out.println(name + " tries to shoot " + enemy.name + " with " + m_weapon.name + ", from " + String.format("%.2f", distance) +" meters but misses.");
                }
                if (enemy_hit_chance <= en_chance  && enemy.ammunition + enemy_bullets_used > 0) // wróg trafia
                {
                    System.out.println(enemy.name + " takes " + enemy_bullets_used + " shots.");
                    enemy.ammunition -= enemy_bullets_used;

                    if (distance > enemy.long_shot)
                        enemy.long_shot = distance;
                    switch (en_part_damaged) {
                        case 1: {
                            System.out.println(enemy.name + " hits " + name + " with the " + enemy.r_weapon.name + " on the head and inflicts " + ((int) (head.dmg_protection * ((1 + head_damages / 3) * enemy_dmg))) + " damage, from " + String.format("%.2f", distance) + "meters.");
                            health -= (int) (head.dmg_protection * ((1 + head_damages / 3) * enemy_dmg));
                            dmg_taken += (int) (head.dmg_protection * ((1 + head_damages / 3) * enemy_dmg));
                            enemy.dmg_dealt += (int) (head.dmg_protection * ((1 + head_damages / 3) * enemy_dmg));
                            if (head_damages < 2)
                                head_damages += 1;
                            break;
                        }
                        case 2: {
                            System.out.println(enemy.name + " hits " + name + " with the " + enemy.r_weapon.name + " in the left leg and inflicts " + ((int) (pants.dmg_protection * prot.dmg_protection * ((1 + left_leg_damages / 3) * enemy_dmg))) + " damage, from " + String.format("%.2f", distance) + "meters.");
                            health -= (int) (pants.dmg_protection * prot.dmg_protection * ((1 + left_leg_damages / 3) * enemy_dmg));
                            dmg_taken += (int) (pants.dmg_protection * prot.dmg_protection * ((1 + left_leg_damages / 3) * enemy_dmg));
                            enemy.dmg_dealt += (int) (pants.dmg_protection * prot.dmg_protection * ((1 + left_leg_damages / 3) * enemy_dmg));
                            if (left_leg_damages < 2)
                                left_leg_damages += 1;

                            break;
                        }
                        case 3: {
                            System.out.println(enemy.name + " hits " + name + " with the " + enemy.r_weapon.name + " in the right leg and inflicts " + ((int) (pants.dmg_protection * prot.dmg_protection * ((1 + right_leg_damages / 3) * enemy_dmg))) + " damage, from " + String.format("%.2f", distance) + "meters.");
                            health -= (int) (pants.dmg_protection * prot.dmg_protection * ((1 + right_leg_damages / 3) * enemy_dmg));
                            dmg_taken += (int) (pants.dmg_protection * prot.dmg_protection * ((1 + right_leg_damages / 3) * enemy_dmg));
                            enemy.dmg_dealt += (int) (pants.dmg_protection * prot.dmg_protection * ((1 + right_leg_damages / 3) * enemy_dmg));
                            if (right_leg_damages < 2) {
                                enemy.right_leg_damages += 1;
                            }
                            break;
                        }
                        case 4: {
                            System.out.println(enemy.name + " hits " + name + " with the " + enemy.r_weapon.name + " on the left arm and inflicts " + ((int) (torso.dmg_protection * prot.dmg_protection * ((1 + left_arm_damages / 3) * enemy_dmg))) + " damage, from " + String.format("%.2f", distance) + "meters.");
                            health -= (int) (torso.dmg_protection * prot.dmg_protection * ((1 + left_arm_damages / 3) * enemy_dmg));
                            dmg_taken += (int) (torso.dmg_protection * prot.dmg_protection * ((1 + left_arm_damages / 3) * enemy_dmg));
                            enemy.dmg_dealt += (int) (torso.dmg_protection * prot.dmg_protection * ((1 + left_arm_damages / 3) * enemy_dmg));
                            if (left_arm_damages < 2) {
                                left_arm_damages += 1;
                            }
                            break;
                        }
                        case 5: {
                            System.out.println(enemy.name + " hits " + name + " with the " + enemy.r_weapon.name + " on the right arm and inflicts " + ((int) (torso.dmg_protection * prot.dmg_protection * ((1 + right_arm_damages / 3) * enemy_dmg))) + " damage, from " + String.format("%.2f", distance) + "meters.");
                            enemy.health -= (int) (torso.dmg_protection * prot.dmg_protection * ((1 + right_arm_damages / 3) * enemy_dmg));
                            enemy.dmg_taken += (int) (torso.dmg_protection * prot.dmg_protection * ((1 + right_arm_damages / 3) * enemy_dmg));
                            enemy.dmg_dealt += (int) (torso.dmg_protection * prot.dmg_protection * ((1 + right_arm_damages / 3) * enemy_dmg));
                            if (right_arm_damages < 2) {
                                right_arm_damages += 1;
                            }
                            break;
                        }
                        case 6: {
                            System.out.println(enemy.name + " hits " + name + " with the " + enemy.r_weapon.name + " on the torso and inflicts " + ((int) (torso.dmg_protection * ((1 + torso_damages / 3) * enemy_dmg))) + " damage, from " + String.format("%.2f", distance) + "meters.");
                            health -= (int) (torso.dmg_protection * ((1 + torso_damages / 3) * enemy_dmg));
                            dmg_taken += (int) (torso.dmg_protection * ((1 + torso_damages / 3) * enemy_dmg));
                            enemy.dmg_dealt += (int) (torso.dmg_protection * ((1 + torso_damages / 3) * enemy_dmg));
                            if (torso_damages < 2) {
                                torso_damages += 1;
                            }
                            break;
                        }
                    }
                }
                else //wróg nie trafia
                {
                    error = false;
                    System.out.println(enemy.name + " tries to shoot " + name + " with " + enemy.m_weapon.name + ", from " + String.format("%.2f", distance) +" meters but misses.");
                }
            }
            else {
                error = false;
                String scenario[] = {name + " and " + enemy.name+ " decided not to fight each other and fled in opposite directions.",
                        name +" and " + enemy.name +" avoid confrontation. They both stated that they are not ready for this fight."};

                List<String> say = Arrays.asList(scenario);

                System.out.println(say.get(rand.nextInt(say.size())));
            }
        }
        else {
            error = false;
            String scenario[] = {name + " and " + enemy.name+ " decided not to fight each other and fled in opposite directions.",
                        name +" and " + enemy.name +" avoid confrontation. They both stated that they are not ready for this fight."};

            List<String> say = Arrays.asList(scenario);

            System.out.println(say.get(rand.nextInt(say.size())));
        }
        if (health <= 0 && enemy.health > 0) {
            enemy.kills+=1;
            health = 0;
            enemy.killed_players.add(actor);
            alive = false;
            deathTime = time;
            System.out.println(enemy.name + " menaged to kill " + name + ".");
            enemy.exchangeGear(actor, true);
        }
        if (enemy.health <= 0 && health > 0) {
            kills+=1;
            enemy.health = 0;
            killed_players.add(enemy);
            enemy.alive = false;
            enemy.deathTime = time;
            System.out.println(name + " menaged to kill " + enemy.name + ".");
            actor.exchangeGear(enemy, true);
        }
        if (enemy.health <= 0 && health <= 0) {
            enemy.kills+=1;
            kills+=1;
            enemy.health = 0;
            killed_players.add(actor);
            enemy.alive = false;
            enemy.deathTime = time;
            health = 0;
            enemy.killed_players.add(actor);
            alive = false;
            deathTime = time;
            System.out.println(enemy.name + " and " + name + " died in the fight.");
        }

        //System.out.println(error);
        if (error)
        {
            String scenario[] = {name + " and " + enemy.name+ " decided not to fight each other and fled in opposite directions.",
                    name +" and " + enemy.name +" avoid confrontation. They both stated that they are not ready for this fight."};

            List<String> say = Arrays.asList(scenario);

            System.out.println(say.get(rand.nextInt(say.size())));
        }
        //String.format("%.2f", value)
    }


    public void actionsPlayer(int alive, int day, int hour, int min,Character actor, Character ch1, Character ch2, List<Character> dead, Weapon weapons[], Armor armors[])
    {
        //szanse na akcje
        //nic
        //wypadek
        //znalezisko (nic nie znaleziono, znaleziono bron, znaleziono pancerz, znaleziono jedzenie, ammo medykamenty)
        //walka

        int nic = 45 - (2 * vitality);
        int wypadek = 30 - (3 * survival);
        int znalezisko = 20 + (3 * perception);
        int walka = 20 + (2 * strength);
        int sum = nic + wypadek + znalezisko + walka;
        int fate = rand.nextInt(sum + 1) + 1; // losuje liczbe ktora okresla co zostanie wykonane
        int fightChoice = rand.nextInt(1 + 1);

        //System.out.println("Fate: " + fate);
        //System.out.println("Nic: " + nic + " Wyp: " + wypadek + " Zna: " + znalezisko + " Wal: " + walka);

        if (fate < nic)
            storyLines(hour, alive, ch1, ch2);
        else if (fate >= nic && fate < (wypadek + nic))
            characterAccident(day, hour, min);
        else if (fate >= (wypadek + nic) && fate < (wypadek + nic + znalezisko))
            findLoot(dead, weapons, armors);
        else if (fate >= (wypadek + nic + znalezisko))
        {
            if (alive == 2)
                fightEnemy(actor, ch1, day, hour, min);
            if (alive > 2 && fightChoice == 0)
                fightEnemy(actor, ch1, day, hour, min);
            if (alive > 2 && fightChoice == 1)
                fightEnemy(actor, ch1, day, hour, min);
        }
        else
            storyLines(hour, alive, ch1, ch2);

    }
}
