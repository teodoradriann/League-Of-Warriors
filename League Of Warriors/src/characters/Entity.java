package characters;

import interfaces.Battle;
import powers.Spell;

import java.util.ArrayList;

public abstract class Entity implements Battle {
    private ArrayList<Spell> abilities;
    private float currentHP;
    private float maxHP;
    private float currentMana;
    private float maxMana;

    private boolean fireImmunity;
    private boolean iceImmunity;
    private boolean earthImmunity;

    public void regenerateHealth(float hp) {
        this.currentHP += hp;
        if (this.currentHP > this.maxHP) {
            this.currentHP = this.maxHP;
        }
    }

    public void regenerateMana(float mana) {
        this.currentMana += mana;
        if (this.currentMana > this.maxMana) {
            this.currentMana = this.maxMana;
        }
    }

    public boolean tryToUseAbility(Spell ability, Entity enemy) {
        if (this.currentMana < ability.getManaCost()) {
            return false;
        }
        return ability.isEffectiveAgainst(enemy);
    }

    public ArrayList<Spell> getAbilities() {
        return abilities;
    }

    public void setAbilities(ArrayList<Spell> abilities) {
        this.abilities = abilities;
    }

    public float getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(float currentHP) {
        this.currentHP = currentHP;
    }

    public float getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(float maxHP) {
        this.maxHP = maxHP;
    }

    public float getCurrentMana() {
        return currentMana;
    }

    public void setCurrentMana(float currentMana) {
        this.currentMana = currentMana;
    }

    public float getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(float maxMana) {
        this.maxMana = maxMana;
    }

    public boolean isFireImmunity() {
        return fireImmunity;
    }

    public void setFireImmunity(boolean fireImmunity) {
        this.fireImmunity = fireImmunity;
    }

    public boolean isIceImmunity() {
        return iceImmunity;
    }

    public void setIceImmunity(boolean iceImmunity) {
        this.iceImmunity = iceImmunity;
    }

    public boolean isEarthImmunity() {
        return earthImmunity;
    }

    public void setEarthImmunity(boolean earthImmunity) {
        this.earthImmunity = earthImmunity;
    }
}
