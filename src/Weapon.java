
public class Weapon
{
    String name;
    int min_dmg;
    int max_dmg;
    int type; // 0 - biała,   1 - zasięgowa

    public Weapon(String name, int min_dmg, int max_dmg, int type)
    {
        this.name = name;
        this.min_dmg = min_dmg;
        this.max_dmg = max_dmg;
        this.type = type;
    }
}
