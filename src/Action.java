import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Action
{
    Character characters[];
    Weapon weapons[];
    Armor armors[];
    List<Character> players;
    Random rand = new Random();

    List<Character> dead = new ArrayList<Character>();

    public Action(Character characters[], Weapon weapons[], Armor armors[])
    {

        this.characters = characters;
        this.weapons = weapons;
        this.armors = armors;
        players = Arrays.asList(characters);
    }

    public Character nonDuplicated(Character actor) // zwraca przeciwnika dla gracza
    {
        Character x = players.get(rand.nextInt(players.size()));

        while(x == actor || !x.alive)
        {
            if (x.alive)
                x = players.get(rand.nextInt(players.size()));
            else
                x = actor;
        }
        return x;
    }

    public Character nonDuplicated(Character actor, Character enemy)// zwraca przeciwnika dla dwóch graczy
    {
        Character x = players.get(rand.nextInt(players.size()));

        boolean stop = false;
        while(!stop)
        {
            x = players.get(rand.nextInt(players.size()));
            if (x.alive && x != actor && x != enemy)
            {
                stop = true;
            }
        }

        return x;
    }

    public String showTime(int day, int h, int m) // zwraca string z godziną
    {
        if(m < 10)
            return ("Day: " + day + " Time: " + h + ":0" + m);
        else
            return ("Day: " + day + " Time: " + h + ":" + m);
    }

    public void registerDeath(Character ch)
    {
        dead.add(ch);
    }

    public void simulateFight()
    {
        int alive = characters.length;
        int day = 1;
        int hour = 12;
        int min = 0;

        while (alive > 1)
        {

            Character enemy1, enemy2 = null;

            //ustalenie kolejności kolejki
            Collections.shuffle(players);

            alive = 0;

            for(Character actors : players)//liczy żywych
                if(actors.alive)
                    alive++;

            for(Character actor : players)
            {
                if(actor.alive) // akcja tylko dla niepokonanych graczy
                {
                    int alive2 = 0;
                    for(Character actorz : players)
                        if(actorz.alive)
                            alive2++;
                    if(alive2 <= 1)
                        break;

                    // zmiana czasu
                    if (hour > 23 || hour < 6)
                    {
                        min += (rand.nextInt(25 + 1) + 30);
                    }
                    if (hour <= 23 && hour >= 6)
                    {
                        min += (rand.nextInt(15 + 1) + 10);
                    }
                    //poprawki dni, godzin i minut
                    if(hour >= 24) {
                        hour -= 24;
                        day++;
                        System.out.println("---------------------");
                        System.out.println("  Summary of day: " + (day -1));
                        System.out.println("  People left: " + alive);
                        System.out.println("---------------------");
                        for(Character actors : players)
                        {
                            if (actors.alive)
                            {
                                actors.statsOfPlayer();
                            }
                        }
                    }

                    if(hour >= 24) {
                        hour -= 24;
                        day++;
                    }
                    if(min >= 60)
                    {
                        hour++;
                        min-=60;
                    }
                    if(min >= 60)// sprawdzam dwa razy bo czasem występują anomalie typu 24:09
                    {
                        hour++;
                        min-=60;
                    }
                    //wybór potencjalnych przeciwników


                    if(alive2 > 2)
                    {
                        enemy1 = nonDuplicated(actor);
                        enemy2 = nonDuplicated(actor, enemy1);
                    }
                    else
                    {
                        enemy1 = nonDuplicated(actor);
                        enemy2 = enemy1;
                    }
                    System.out.println("");
                    System.out.println(showTime(day, hour, min));

                    actor.actionsPlayer(alive, day, hour, min, actor, enemy1, enemy2, dead, weapons, armors);

                    if (actor.health <= 0)
                        registerDeath(actor);

                    if (enemy1.health <= 0)
                        registerDeath(enemy1);

                    if (enemy2.health <= 0 && enemy2 != enemy1)
                        registerDeath(enemy2);


                    try {Thread.sleep(1500);} catch (InterruptedException e) {e.printStackTrace();}
                }
            }
        }

        // podsumowanie

        int max_dmg_dealt = 0;
        int max_dmg_taken = 0;
        int most_kills = 0;
        double long_shots = 0;

        String max_d = "";
        String max_t = "";
        String max_k = "";
        String max_s = "";

        Character ch1 = null;

        System.out.println("--------------");
        if (alive == 1)
            for(Character actor : players)
                if (actor.alive)
                {
                    System.out.println("Winner: " + actor.name);
                    registerDeath(actor);
                }
        if (alive != 1)
            System.out.println("Winner: everyone died, no winner ");

        for(Character actor : players)
        {
            if (actor.dmg_taken >= max_dmg_taken)
            {
                max_t = actor.name;
                max_dmg_taken = actor.dmg_taken;
                ch1 = actor;
            }

            if (actor.dmg_dealt >= max_dmg_dealt)
            {
                max_d = actor.name;
                max_dmg_dealt = actor.dmg_dealt;
            }

            if (actor.kills >= most_kills)
            {
                max_k = actor.name;
                most_kills = actor.kills;
            }

            if (actor.long_shot >= long_shots)
            {
                max_s = actor.name;
                long_shots = actor.long_shot;
            }
        }
        System.out.println("Most dmg caused: " + max_d + " - " + max_dmg_dealt + " dmg");
        System.out.println("Most dmg taken: " + max_t + " - " + ch1.dmg_taken + " dmg");
        System.out.println("Most eliminations: " + max_k + " - " + most_kills + " kills");
        System.out.println("Longest shot: " + max_s + " - " + String.format("%.2f", long_shots) + " m");

        Collections.reverse(dead);

        System.out.println("--------------");
        System.out.println("Ranks:");

        int place = 1;
        for(Character actor : dead)
        {
            System.out.println(place + ". " + actor.name + " - death time: " + actor.deathTime +" | kills: " + actor.kills + " | dmg taken: " + actor.dmg_taken + " | dmg caused: " + actor.dmg_dealt);
            place++;
        }
        System.out.println("--------------");
        System.out.println("Kills:");
        for(Character actor : dead)
        {
            System.out.println("- " + actor.name + "'s kills: ");
            if(actor.kills == 0)
                System.out.println("   - none");
            else
                for(Character actors : actor.killed_players)
                    System.out.println("   - " + actors.name);
        }
        System.out.println("--------------");
    }
}
