
public class Main
{
    public static void main(String [] args)
    {
        // tworzenie dostępnych broni białych
        Weapon m1 = new Weapon("bare hands", 5, 10, 0);
        Weapon m2 = new Weapon("military knife", 35, 55, 0);
        Weapon m3 = new Weapon("katana", 40, 70, 0);
        Weapon m4 = new Weapon("hammer", 25, 40, 0);
        Weapon m5 = new Weapon("axe", 55, 60, 0);

        // tworzenie dostępnych broni zasięgowych
        Weapon r1 = new Weapon("none", 0, 0, 1);
        Weapon r2 = new Weapon("glock 19", 25, 50, 1);
        Weapon r3 = new Weapon("hunting rifile", 45, 60, 1);
        Weapon r4 = new Weapon("revolver", 35, 65, 1);
        Weapon r5 = new Weapon("AK-47", 45, 65, 1);

        // tworzenie dostępnych hełmów dla graczy
        Armor h1 = new Armor("none", 1, 0);
        Armor h2 = new Armor("skater helmet", 0.85, 0);
        Armor h3 = new Armor("protective helmet", 0.7, 0);
        Armor h4 = new Armor("military helmet", 0.55, 0);
        Armor h5 = new Armor("tachanka helmet", 0.4, 0);

        // tworzenie dostępnych strojów dla graczy
        Armor t1 = new Armor("t-shirt", 1, 1);
        Armor t2 = new Armor("thick sweatshirt", 0.85, 1);
        Armor t3 = new Armor("leather jacket", 0.7, 1);
        Armor t4 = new Armor("bulletproof vest", 0.55, 1);
        Armor t5 = new Armor("heavy military armor", 0.4, 1);

        // tworzenie dostępnych spodni dla graczy
        Armor p1 = new Armor("track pants", 1, 2);
        Armor p2 = new Armor("jeans", 0.9, 2);
        Armor p3 = new Armor("biker pants", 0.8, 2);
        Armor p4 = new Armor("reinforced pants", 0.9, 2);
        Armor p5 = new Armor("tough military pants", 0.6, 2);

        // tworzenie dostępnych ochraniaczy dla graczy
        Armor g1 = new Armor("none", 1, 3);
        Armor g2 = new Armor("cycling protectors", 0.95, 3);
        Armor g3 = new Armor("protective construction gear", 0.9, 3);
        Armor g4 = new Armor("hardened protectors", 0.85, 3);
        Armor g5 = new Armor("set of military protectors", 0.80, 3);

        // tworzenie postaci
        Character ch1 = new Character("Mark", 7, 6, 4, 7, 5, 9, m1, r1, h1, t1, p1, g1);
        Character ch2 = new Character("Tom",  5, 8, 5, 4, 7, 8, m1, r1, h1, t1, p1, g1);
        Character ch3 = new Character("Ben",  6, 6, 2, 3, 6, 4, m1, r1, h1, t1, p1, g1);
        Character ch4 = new Character("Matt", 2, 4, 1, 8, 8, 3, m1, r1, h1, t1, p1, g1);
        Character ch5 = new Character("Andy", 9, 2, 7, 7, 2, 1, m1, r1, h1, t1, p1, g1);
        Character ch6 = new Character("Mike", 6, 6, 3, 9, 8, 7, m1, r1, h1, t1, p1, g1);
        Character ch7 = new Character("Frank", 5, 10, 6, 5, 9, 9, m1, r1, h1, t1, p1, g1);
        Character ch8 = new Character("Penny", 2, 10, 7, 1, 8, 7, m1, r1, h1, t1, p1, g1);
        Character ch9 = new Character("Danny", 8, 5, 1, 1, 6, 1, m1, r1, h1, t1, p1, g1);
        Character ch10 = new Character("Pain", 10, 10, 10, 10, 10, 10, m1, r1, h1, t1, p1, g1);


        //tablice informacji o przedmiotach do znalezienia i o graczach

        Weapon weapons[] = {m2, m3, m4, m5, r2, r3, r4, r5};

        Armor armors[] = {h2, h3, h4, h5, t2, t3, t4, t5, p2, p3, p4, p5, g2, g3, g4, g5};

        Character characters[] = {ch1, ch2,  ch3, ch4, ch5, ch6, ch7, ch8, ch9, ch10};

        Action action = new Action(characters, weapons, armors);


        action.simulateFight();

        System.out.println("END");

    }


}
