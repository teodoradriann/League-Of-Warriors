package characters;

public class Rogue extends Character {

    public Rogue(String name, Integer xp, Integer level) {
        super(name, xp, level);
    }

    @Override
    public String getProfession() {
        return "Rogue";
    }

    @Override
    public void receiveDamage(float damage) {

    }

    @Override
    public float getDamage() {
        return 0;
    }
}
