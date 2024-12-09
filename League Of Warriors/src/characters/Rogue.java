package characters;

import powers.Spell;

public class Rogue extends Character {

    public Rogue(String name, Integer xp, Integer level) {
        super(name, xp, level);
        this.setCurrentHP(80);
        this.setMaxHP(80);
        this.setCurrentMana(60);
        this.setMaxMana(60);
        this.setEarthImmunity(true);
        this.setFireImmunity(false);
        this.setIceImmunity(false);
        this.setStrength(25);
        this.setDexterity(50);
        this.setCharisma(5);
        this.setNormalAttackDamage(10.0F);
    }

    @Override
    public String getProfession() {
        return "Rogue";
    }

    @Override
    public void receiveDamage(float damage, boolean fromSpell) {

    }

    @Override
    public float calculateDamage(boolean isNormalAttack, Spell spellCasted) {
        return 0;
    }
}
