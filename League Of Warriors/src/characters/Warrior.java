package characters;

public class Warrior extends Character {

    public Warrior(String name, Integer xp, Integer level) {
        super(name, xp, level);
    }

    @Override
    public String getProfession() {
        return "Warrior";
    }

    @Override
    public void receiveDamage(float damage) {

    }

    @Override
    public float getDamage() {
        return 0;
    }

}
