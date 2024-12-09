package characters;

import interfaces.Battle;
import powers.Earth;
import powers.Fire;
import powers.Ice;
import powers.Spell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class Entity implements Battle {
    private ArrayList<Spell> abilities;
    private float currentHP;
    private float maxHP;
    private float currentMana;
    private float maxMana;
    private float normalAttackDamage;

    private boolean fireImmunity;
    private boolean iceImmunity;
    private boolean earthImmunity;

    private static final Random random = new Random();

    public static ArrayList<Spell> generateSpellsArray(int lowerBound, int higherBound) {
        ArrayList<Spell> abilities = new ArrayList<>();
        abilities.add(new Fire());
        abilities.add(new Ice());
        abilities.add(new Earth());
        int numberOfAbilities = random.nextInt(lowerBound, higherBound) - 3;
        while (numberOfAbilities > 0) {
            numberOfAbilities--;
            Spell spell = generateRandomSpell();
            abilities.add(spell);
        }
        return abilities;
    }

    private static Spell generateRandomSpell() {
        Class<?>[] spellClasses = {Fire.class, Ice.class, Earth.class};
        int index = random.nextInt(spellClasses.length);
        try {
            return (Spell) spellClasses[index].getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error while generating spell.");
        }
    }

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

    public float getNormalAttackDamage() {
        return normalAttackDamage;
    }

    public void setNormalAttackDamage(float normalAttackDamage) {
        this.normalAttackDamage = normalAttackDamage;
    }

    public void showImmunities() {
        StringBuilder sb = new StringBuilder();
        if (fireImmunity) {
            sb.append("Fire ");
        }
        if (earthImmunity) {
            sb.append("Earth ");
        }
        if (iceImmunity) {
            sb.append("Ice ");
        }
        if (sb.isEmpty()) {
            System.out.println("Immune to nothing.");
        } else {
            System.out.println("Immune to " + sb);
        }
    }
}
