package characters;

import powers.Spell;

public class Rogue extends Character {

    public Rogue(String name, Integer xp, Integer level) {
        super(null, 80, 80, 60, 60, 10.0F, false, false,
                true, 30, 10, 50, name, xp, level);
    }

    @Override
    public String getProfession() {
        return "Rogue";
    }

    @Override
    public float calculateDamage(boolean isNormalAttack, Spell spellCasted) {
        return 0;
    }
}
