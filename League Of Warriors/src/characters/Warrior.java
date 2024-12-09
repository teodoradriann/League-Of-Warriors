package characters;

import powers.Spell;

public class Warrior extends Character {

    public Warrior(String name, Integer xp, Integer level) {
        super(name, xp, level);
        this.setCurrentHP(100);
        this.setMaxHP(100);
        this.setCurrentMana(40);
        this.setMaxMana(40);
        this.setEarthImmunity(false);
        this.setFireImmunity(true);
        this.setIceImmunity(false);
        this.setStrength(50);
        this.setDexterity(25);
        this.setCharisma(5);
        this.setNormalAttackDamage(15.0F);
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
