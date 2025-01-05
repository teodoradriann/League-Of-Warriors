package powers;

import characters.Entity;
import interfaces.Visitor;

public abstract class Spell implements Visitor<Entity> {
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
    public void visit(Entity entity, float damage) {
        if (isEffectiveAgainst(entity)) {
            dealDamage(entity, damage);
        } else {
            System.out.println("Spell was not effective.");
        }
    }

    protected void dealDamage(Entity entity, float damage) {
        entity.setCurrentHP(entity.getCurrentHP() - damage);
        System.out.println("Dealt " + damage + " damage to " + entity.getClass().getSimpleName());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " -> damage: " + this.getDamage() + " -> mana cost: " + this.getManaCost();
    }
}
