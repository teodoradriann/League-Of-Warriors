package characters;
import powers.Spell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class EnemySpawner {
    private final EnemyTypes[] enemyTypes = EnemyTypes.values();
    private Random random = new Random();
    // 20 - weak, 30 - normal, 30 - strong, 20 - boss
    private final int[] probabilities = {20, 30, 30, 20};
    private final int totalProbability = Arrays.stream(probabilities).sum();

    public Enemy createEnemy() {
        int randomValue = random.nextInt(totalProbability);
        EnemyTypes type = getEnemyType(randomValue);
        Enemy enemy = null;
        switch (type) {
            case WEAK -> {
                ArrayList<Spell> abilities = new ArrayList<>();
                float currentHP = 30;
                float maxHP = 30;
                float currentMana = 0;
                float maxMana = 0;
                boolean fireImmunity = false;
                boolean iceImmunity = false;
                boolean earthImmunity = false;
                float normalAttackDamage = 5.0F;
                enemy = new Enemy(abilities, EnemyTypes.WEAK, currentHP, maxHP, currentMana, maxMana, fireImmunity,
                        iceImmunity, earthImmunity, normalAttackDamage);
            }
            case NORMAL -> {
                ArrayList<Spell> abilities = Entity.generateSpellsArray(1, 3);
                float currentHP = 80;
                float maxHP = 80;
                float currentMana = 30;
                float maxMana = 30;
                boolean fireImmunity = false;
                boolean iceImmunity = random.nextBoolean();
                boolean earthImmunity = random.nextBoolean();
                float normalAttackDamage = 9.0F;
                enemy = new Enemy(abilities, EnemyTypes.NORMAL, currentHP, maxHP, currentMana, maxMana, fireImmunity,
                        iceImmunity, earthImmunity, normalAttackDamage);
            }
            case STRONG -> {
                ArrayList<Spell> abilities = Entity.generateSpellsArray(3, 6);
                float currentHP = 120;
                float maxHP = 120;
                float currentMana = 50;
                float maxMana = 50;
                boolean fireImmunity = random.nextBoolean();
                boolean iceImmunity = random.nextBoolean();
                boolean earthImmunity = random.nextBoolean();
                float normalAttackDamage = 12.5F;
                enemy = new Enemy(abilities, EnemyTypes.STRONG, currentHP, maxHP, currentMana, maxMana, fireImmunity,
                        iceImmunity, earthImmunity, normalAttackDamage);
            }
            case BOSS -> {
                ArrayList<Spell> abilities = Entity.generateSpellsArray(4, 7);
                float currentHP = 200;
                float maxHP = 200;
                float currentMana = 70;
                float maxMana = 70;
                boolean fireImmunity = random.nextBoolean();
                boolean iceImmunity = random.nextBoolean();
                boolean earthImmunity = random.nextBoolean();
                float normalAttackDamage = 20.0F;
                enemy = new Enemy(abilities, EnemyTypes.BOSS, currentHP, maxHP, currentMana, maxMana, fireImmunity,
                        iceImmunity, earthImmunity, normalAttackDamage);
            }
        }
        return enemy;
    }

    public Enemy createTestEnemy() {
        Enemy enemy;
        ArrayList<Spell> abilities = new ArrayList<>();
        float currentHP = 500;
        float maxHP = 500;
        float currentMana = 1;
        float maxMana = 1;
        boolean fireImmunity = false;
        boolean iceImmunity = false;
        boolean earthImmunity = false;
        float normalAttackDamage = 1.0F;
        enemy = new Enemy(abilities, EnemyTypes.WEAK, currentHP, maxHP, currentMana, maxMana, fireImmunity,
                iceImmunity, earthImmunity, normalAttackDamage);
        return enemy;
    }

    private EnemyTypes getEnemyType(int probability) {
        int cumulativeProbability = 0;
        for (int i = 0; i < probabilities.length; i++) {
            cumulativeProbability += probabilities[i];
            if (probability < cumulativeProbability) {
                return enemyTypes[i];
            }
        }
        return enemyTypes[0];
    }
}
