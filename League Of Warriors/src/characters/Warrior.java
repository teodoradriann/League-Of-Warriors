package characters;

import powers.Spell;

public class Warrior extends Character {

    public Warrior(String name, Integer xp, Integer level) {
        super(null, 100, 100, 40, 40, 15.0F, true, false,
                false, 50, 5, 25, name, xp, level);
    }

    @Override
    public String getProfession() {
        return "Warrior";
    }

    @Override
    public void receiveDamage(float damage, boolean fromSpell) {

    }

    @Override
    public float calculateDamage(boolean isNormalAttack, Spell spellCasted) {
        return 0;
    }
}
