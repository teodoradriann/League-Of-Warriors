package characters;

import powers.Earth;
import powers.Fire;
import powers.Ice;
import powers.Spell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class EnemySpawner {
    private final Random random = new Random();
    private final EnemyTypes[] enemyTypes = EnemyTypes.values();

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
                float normalAttackDamage = 10.0F;
                enemy = new Enemy(abilities, EnemyTypes.WEAK, currentHP, maxHP, currentMana, maxMana, fireImmunity,
                        iceImmunity, earthImmunity, normalAttackDamage);
            }
            case NORMAL -> {
                ArrayList<Spell> abilities = generateSpellsArray(1, 3);
                float currentHP = 50;
                float maxHP = 50;
                float currentMana = 30;
                float maxMana = 30;
                boolean fireImmunity = false;
                boolean iceImmunity = random.nextBoolean();
                boolean earthImmunity = random.nextBoolean();
                float normalAttackDamage = 15.0F;
                enemy = new Enemy(abilities, EnemyTypes.NORMAL, currentHP, maxHP, currentMana, maxMana, fireImmunity,
                        iceImmunity, earthImmunity, normalAttackDamage);
            }
            case STRONG -> {
                ArrayList<Spell> abilities = generateSpellsArray(3, 6);
                float currentHP = 75;
                float maxHP = 75;
                float currentMana = 50;
                float maxMana = 50;
                boolean fireImmunity = random.nextBoolean();
                boolean iceImmunity = random.nextBoolean();
                boolean earthImmunity = random.nextBoolean();
                float normalAttackDamage = 20.0F;
                enemy = new Enemy(abilities, EnemyTypes.STRONG, currentHP, maxHP, currentMana, maxMana, fireImmunity,
                        iceImmunity, earthImmunity, normalAttackDamage);
            }
            case BOSS -> {
                ArrayList<Spell> abilities = generateSpellsArray(4, 7);
                float currentHP = 100;
                float maxHP = 100;
                float currentMana = 70;
                float maxMana = 70;
                boolean fireImmunity = random.nextBoolean();
                boolean iceImmunity = random.nextBoolean();
                boolean earthImmunity = random.nextBoolean();
                float normalAttackDamage = 25.0F;
                enemy = new Enemy(abilities, EnemyTypes.BOSS, currentHP, maxHP, currentMana, maxMana, fireImmunity,
                        iceImmunity, earthImmunity, normalAttackDamage);
            }
        }
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

    private ArrayList<Spell> generateSpellsArray(int lowerBound, int higherBound) {
        ArrayList<Spell> abilities = new ArrayList<>();
        int numberOfAbilities = random.nextInt(lowerBound, higherBound);
        while (numberOfAbilities > 0) {
            numberOfAbilities--;
            Spell spell = generateRandomSpell();
            abilities.add(spell);
        }
        return abilities;
    }

    private Spell generateRandomSpell() {
        Class<?>[] spellClasses = {Fire.class, Ice.class, Earth.class};
        int index = random.nextInt(spellClasses.length);
        try {
            return (Spell) spellClasses[index].getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error while generating spell.");
        }
    }
}
