package characters;

import powers.Spell;


public class Mage extends Character {
    public Mage(String name, Integer xp, Integer level) {
        super(null, 50, 70, 100, 120, 7.5F, false,
                false, true, 10, 50, 30, name, xp, level);
    }

    @Override
    protected float getSpellDamageMultiplier(Spell spellCasted) {
        return spellCasted.getDamage() + spellCasted.getDamage() * this.charisma * 0.008f;
    }

    @Override
    protected float getNormalAttackMultiplier() {
        return this.getNormalAttackDamage();
    }

    @Override
    public String getProfession() {
        return "Mage";
    }
}
