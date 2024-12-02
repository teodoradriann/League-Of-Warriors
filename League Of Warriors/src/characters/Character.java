package characters;

import interfaces.Battle;

public abstract class Character implements Battle {
    protected String name;
    protected Integer xp;
    protected Integer level;

    protected Integer strength;
    protected Integer charisma;
    protected Integer dexterity;

    public Character(String name, Integer xp, Integer level) {
        this.name = name;
        this.xp = xp;
        this.level = level;
    }

    public abstract String getProfession();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getCharisma() {
        return charisma;
    }

    public void setCharisma(Integer charisma) {
        this.charisma = charisma;
    }

    public Integer getDexterity() {
        return dexterity;
    }

    public void setDexterity(Integer dexterity) {
        this.dexterity = dexterity;
    }

    @Override
    public void receiveDamage(int damage) {

    }

    @Override
    public int getDamage() {
        return 0;
    }
}
