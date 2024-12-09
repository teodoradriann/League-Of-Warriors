package characters;

import game.Game;
import interfaces.Battle;
import powers.Spell;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Entity implements Battle {
    private final Random random = new Random();
    private EnemyTypes type;

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
        this.setNormalAttackDamage(normalAttackDamage);
    }

    public EnemyTypes getType() {
        return type;
    }

    public void setType(EnemyTypes type) {
        this.type = type;
    }

    @Override
    public void receiveDamage(float damage, boolean fromSpell) {
        System.out.println("You dealt: " + damage + " to your enemy.");
        this.setCurrentHP(this.getCurrentHP() - damage);
    }

    @Override
    public float calculateDamage(boolean normalAttack, Spell spellCasted) {
        double chance = random.nextDouble();
        if (chance < 0.5) {
            System.out.println("The enemy hit a CRIT hit on you!");
            if (normalAttack) {
                return this.getNormalAttackDamage() * 2;
            } else {
                return spellCasted.getDamage() * 2;
            }
        } else {
            if (normalAttack) {
                return this.getNormalAttackDamage();
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
                    hero.receiveDamage(attackDamage, true);
                    System.out.println("The enemy used: " + spellToCast);
                    this.getAbilities().remove(spellToCast);
                    this.setCurrentMana(this.getCurrentMana() - spellToCast.getManaCost());
                    Game.showStats((Character) hero);
                    return;
                }
            }
        }
        float attackDamage = calculateDamage(true, null);
        hero.receiveDamage(attackDamage, false);
        this.regenerateMana(random.nextFloat(5.0F, 15.0F));
        Game.showStats((Character) hero);
    }

    @Override
    public String toString() {
        return "Your enemy " + "is immune to fire: " + this.isFireImmunity() + ", immune to ice: " + this.isIceImmunity() + ", immune to earth: " + this.isEarthImmunity();
    }
}
