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
        super(abilities, currentHP, maxHP, currentMana, maxMana, normalAttackDamage, fireImmunity, iceImmunity, earthImmunity);
        this.type = type;
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
    public float getDamage(boolean isNormalAttack, Spell spellCasted) {
        Random random = new Random();
        double chance = random.nextDouble();
        boolean critHit = chance < 0.5;
        float damage;

        if (isNormalAttack) {
            damage = this.getNormalAttackDamage();
        } else {
            damage = spellCasted.getDamage();
        }
        if (critHit) {
            System.out.println("The enemy hit a CRIT hit on you!");
            return 2 * damage;
        } else {
            return damage;
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
                    float attackDamage = getDamage(false, spellToCast);
                    hero.accept(spellToCast, attackDamage);
                    this.getAbilities().remove(spellToCast);
                    this.setCurrentMana(this.getCurrentMana() - spellToCast.getManaCost());
                    Game.showStats((Character) hero);
                    return;
                }
            }
        }
        float attackDamage = getDamage(true, null);
        hero.receiveDamage(attackDamage, false);
        this.regenerateMana(random.nextFloat(5.0F, 15.0F));
        Game.showStats((Character) hero);
    }

    @Override
    public String toString() {
        return "Your enemy " + "is immune to fire: " + this.isFireImmunity() + ", immune to ice: " + this.isIceImmunity() + ", immune to earth: " + this.isEarthImmunity();
    }
}
