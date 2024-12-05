package characters;

import powers.Spell;

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
    public float calculateDamage(boolean isNormalAttack, Spell spellCasted) {
        return 0;
    }

    @Override
    public void attack(Entity enemy) {

    }

}
