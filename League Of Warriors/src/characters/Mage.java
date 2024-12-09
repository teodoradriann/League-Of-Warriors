package characters;

import game.Game;
import powers.Spell;

import java.util.Random;

public class Mage extends Character {
    private final Random random = new Random();

    public Mage(String name, Integer xp, Integer level) {
        super(null, 50, 50, 100, 100, 7.5F, false,
                false, true, 10, 50, 30, name, xp, level);
    }

    @Override
    public String getProfession() {
        return "Mage";
    }

    @Override
    public float calculateDamage(boolean isNormalAttack, Spell spellCasted) {
        if (isNormalAttack) {
            return this.getNormalAttackDamage();
        }
        else {
            return (float) (spellCasted.getDamage() + spellCasted.getDamage() * this.charisma * 0.008);
        }
    }
}
