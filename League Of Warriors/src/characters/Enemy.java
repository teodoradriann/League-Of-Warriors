package characters;

import game.Game;
import interfaces.Battle;
import powers.Spell;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Entity implements Battle {
    private float normalAttackDamage;
    private final Random random = new Random();
    EnemyTypes type;

    protected Enemy(ArrayList<Spell> abilities, EnemyTypes type, float currentHP, float maxHP, float currentMana, float maxMana,
                 boolean fireImmunity, boolean iceImmunity, boolean earthImmunity, float normalAttackDamage) {
        this.setAbilities(abilities);
        this.type = type;
        this.setCurrentHP(currentHP);
        this.setMaxHP(maxHP);
        this.setCurrentMana(currentMana);
        this.setMaxMana(maxMana);
        this.setFireImmunity(fireImmunity);
        this.setIceImmunity(iceImmunity);
        this.setEarthImmunity(earthImmunity);
        this.normalAttackDamage = normalAttackDamage;
    }

    @Override
    public void receiveDamage(float damage) {
        this.setCurrentHP(this.getCurrentHP() - damage);
    }

    @Override
    public float calculateDamage(boolean normalAttack, Spell spellCasted) {
        double chance = random.nextDouble();
        if (chance < 0.5) {
            if (normalAttack) {
                return normalAttackDamage * 2;
            } else {
                return spellCasted.getDamage() * 2;
            }
        } else {
            if (normalAttack) {
                return normalAttackDamage;
            } else {
               return spellCasted.getDamage();
            }
        }
    }

    @Override
    public void attack(Entity hero) {
        double chance = random.nextDouble();
        if (chance < 0.6) {
            if (!this.getAbilities().isEmpty()) {
                int randomIndex = random.nextInt(this.getAbilities().size());
                Spell spellToCast = this.getAbilities().get(randomIndex);
                if (this.tryToUseAbility(spellToCast, hero)) {
                    float attackDamage = calculateDamage(false, spellToCast);
                    hero.receiveDamage(attackDamage);
                    System.out.println("The enemy used: " + spellToCast);
                    this.getAbilities().remove(spellToCast);
                    this.setCurrentMana(this.getCurrentMana() - spellToCast.getManaCost());
                    Game.showStats((Character) hero);
                    return;
                }
            }
        }
        float attackDamage = calculateDamage(true, null);
        hero.receiveDamage(attackDamage);
        this.regenerateMana(random.nextFloat(5.0F, 15.0F));
        System.out.println("The enemy dealt " + attackDamage + " damage to you.");
        Game.showStats((Character) hero);
    }

    @Override
    public String toString() {
        return type.toString() + ": " + this.getCurrentHP() + ", " + this.getCurrentMana() + ", " + "is immune to fire: " + this.isFireImmunity() + " , is immune to ice: " + this.isIceImmunity() + " , is immune to earth: " + this.isEarthImmunity();
    }
}
