package characters;

import powers.Spell;

import java.util.Arrays;
import java.util.Random;

public class Warrior extends Character {

    public Warrior(String name, Integer xp, Integer level) {
        super(null, 100, 120, 40, 50, 15.0F, true, false,
                false, 50, 10, 30, name, xp, level,
                AttributeTypes.STRENGTH, Arrays.asList(AttributeTypes.CHARISMA, AttributeTypes.DEXTERITY));
    }

    @Override
    protected float getSpellDamageMultiplier(Spell spellCasted) {
        return spellCasted.getDamage() + spellCasted.getDamage() * this.dexterity * 0.005f;
    }

    @Override
    protected float getNormalAttackMultiplier() {
        return this.getNormalAttackDamage() + this.getNormalAttackDamage() * this.strength * 0.05f;
    }

    @Override
    public String getProfession() {
        return "Warrior";
    }

}
