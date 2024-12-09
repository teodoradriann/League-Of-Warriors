package characters;

import game.Game;
import powers.Spell;

import java.util.Random;

public class Mage extends Character {
    private final Random random = new Random();

    public Mage(String name, Integer xp, Integer level) {
        super(null, 50, 50, 100, 100, 7.5F, false,
                false, true, 5, 25, 50, name, xp, level);
    }

    @Override
    public String getProfession() {
        return "Mage";
    }

    @Override
    public void receiveDamage(float damage, boolean fromSpell) {
        if (!fromSpell) {
            if (this.dexterity > 75) {
                double chance = random.nextDouble();
                if (chance < 0.5) {
                    System.out.println("Given your extraordinary dexterity you managed to partially dodge a hit and got reduced damage!");
                    System.out.println("The enemy dealt " + damage / 2 + " damage to you.");
                    this.setCurrentHP(this.getCurrentHP() - damage / 2);
                    return;
                }
            }
            System.out.println("The enemy dealt " + damage + " damage to you.");
            this.setCurrentHP(this.getCurrentHP() - damage);
            return;
        } else {
            double chance = random.nextDouble();
            if (chance < 0.5) {
                System.out.println("You managed to partially dodge the attack and got reduced damage!");
                System.out.println("The enemy dealt " + damage / 2 + " damage to you.");
                this.setCurrentHP(this.getCurrentHP() - damage / 2);
                return;
            }
        }
        System.out.println("The enemy dealt " + damage + " damage to you.");
        this.setCurrentHP(this.getCurrentHP() - damage);
    }

    @Override
    public float calculateDamage(boolean isNormalAttack, Spell spellCasted) {
        if (isNormalAttack) {
            return this.getNormalAttackDamage();
        }
        else {
            return (float) (spellCasted.getDamage() + spellCasted.getDamage() * this.charisma * 0.008);
        }
    }
}
