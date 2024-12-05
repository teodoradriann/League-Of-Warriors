package characters;

import game.Game;
import interfaces.Battle;
import powers.Spell;
import characters.EnemyTypes;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Entity implements Battle {
    float normalAttackDamage;
    boolean isNormalAttack;
    EnemyTypes[] enemyTypes = EnemyTypes.values();

    public boolean isNormalAttack() {
        return isNormalAttack;
    }

    public void setNormalAttack(boolean normalAttack) {
        isNormalAttack = normalAttack;
    }

    private Enemy(ArrayList<Spell> abilities, float currentHP, float maxHP, float currentMana, float maxMana,
                 boolean fireImmunity, boolean iceImmunity, boolean earthImmunity, float normalAttackDamage) {
        this.setAbilities(abilities);
        this.setCurrentHP(currentHP);
        this.setMaxHP(maxHP);
        this.setCurrentMana(currentMana);
        this.setMaxMana(maxMana);
        this.setFireImmunity(fireImmunity);
        this.setIceImmunity(iceImmunity);
        this.setEarthImmunity(earthImmunity);
        this.normalAttackDamage = normalAttackDamage;
    }

    public Enemy createEnemy() {
        Random random = new Random();
        int index = random.nextInt(enemyTypes.length);
        EnemyTypes type = enemyTypes[index];
        Enemy enemy = null;
        switch (type) {
            case WEAK -> {
                enemy = new Enemy();
            }
            case NORMAL -> {
                enemy = new Enemy();
            }
            case STRONG -> {
                enemy = new Enemy();
            }
            case BOSS -> {
                enemy = new Enemy();
            }
        }
        return enemy;
    }

    @Override
    public void receiveDamage(float damage) {
        Random random = new Random();
        double chance = random.nextDouble();
        if (chance < 0.5) {
            return;
        }
        this.setCurrentHP(this.getCurrentHP() - damage);
    }

    @Override
    public float getDamage() {
        Random random = new Random();
        double chance = random.nextDouble();
        if (chance < 0.5) {
            if (this.isNormalAttack) {
                return normalAttackDamage * 2;
            }
        }
        return normalAttackDamage;
    }

    public void attack(Character character) {
        Random random = new Random();
        double chance = random.nextDouble();
        if (chance < 0.6) {
            int randomIndex = random.nextInt(this.getAbilities().size());
            Spell spellToCast = this.getAbilities().get(randomIndex);
            if (this.tryToUseAbility(spellToCast, character)) {
                // TODO: implement attackDmg...

                Game.showStats(character);
                return;
            }
        }
        float attackDamage = getDamage();
        character.receiveDamage(attackDamage);
        System.out.println("The enemy dealt " + attackDamage + " damage to you.");
        Game.showStats(character);
    }
}
