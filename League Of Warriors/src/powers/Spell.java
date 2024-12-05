package powers;

import characters.Entity;

public abstract class Spell {
    private float damage;
    private float manaCost;

    public Spell(float damage, float manaCost) {
        this.damage = damage;
        this.manaCost = manaCost;
    }

    public float getManaCost() {
        return manaCost;
    }

    public float getDamage() {
        return damage;
    }

    public abstract boolean isEffectiveAgainst(Entity enemy);

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " -> damage: " + this.getDamage() + ", -> mana cost: " + this.getManaCost();
    }
}
