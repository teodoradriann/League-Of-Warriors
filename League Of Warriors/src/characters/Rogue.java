package characters;

import powers.Spell;

import java.util.Arrays;


public class Rogue extends Character {

    public Rogue(String name, Integer xp, Integer level) {
        super(null, 80, 100, 50, 70, 10.0F, false, false,
                true, 30, 10, 50, name, xp, level,
                AttributeTypes.DEXTERITY, Arrays.asList(AttributeTypes.STRENGTH, AttributeTypes.CHARISMA));;
    }

    @Override
    protected float getSpellDamageMultiplier(Spell spellCasted) {
        return spellCasted.getDamage() + spellCasted.getDamage() * this.dexterity * 0.008f;
    }

    @Override
    protected float getNormalAttackMultiplier() {
        return this.getNormalAttackDamage();
    }

    @Override
    public String getProfession() {
        return "Rogue";
    }

}
