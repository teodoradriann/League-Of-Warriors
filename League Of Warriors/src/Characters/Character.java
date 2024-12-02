package Characters;

import Interfaces.Battle;

public abstract class Character implements Battle {
    String name;
    Integer xp;
    Integer level;

    Integer Strength;
    Integer Charisma;
    Integer Dexterity;

    public Character(String name, Integer xp, Integer level) {
        this.name = name;
        this.xp = xp;
        this.level = level;
    }

    @Override
    public void receiveDamage(int damage) {

    }

    @Override
    public int getDamage() {
        return 0;
    }
}
