
public class Armor
{
    String name;
    double dmg_protection;
    int type; // 0 - hełm, 1 - ochrona korpusu, 2 - spodnie, 3 - ochraniacze ręce i nogi

    public Armor(String name, double dmg_protection, int type)
    {
        this.name = name;
        this.dmg_protection = dmg_protection;
        this.type = type;
    }
}
