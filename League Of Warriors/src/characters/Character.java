package characters;

import exceptions.InvalidCommandException;
import game.Game;
import interfaces.Battle;
import powers.Spell;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public abstract class Character extends Entity implements Battle {
    protected String name;
    protected Integer xp;
    protected Integer level;

    protected Integer strength;
    protected Integer charisma;
    protected Integer dexterity;

    public Character(ArrayList<Spell> abilities, float currentHP, float maxHP, float currentMana, float maxMana,
                     float normalAttackDamage, boolean fireImmunity, boolean iceImmunity, boolean earthImmunity,
                     Integer strength, Integer charisma, Integer dexterity, String name, Integer xp, Integer level) {

        super(abilities, currentHP, maxHP, currentMana, maxMana, normalAttackDamage, fireImmunity, iceImmunity, earthImmunity);
        this.strength = strength;
        this.charisma = charisma;
        this.dexterity = dexterity;
        this.name = name;
        this.xp = xp;
        this.level = level;
    }

    public abstract String getProfession();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getCharisma() {
        return charisma;
    }

    public void setCharisma(Integer charisma) {
        this.charisma = charisma;
    }

    public Integer getDexterity() {
        return dexterity;
    }

    public void setDexterity(Integer dexterity) {
        this.dexterity = dexterity;
    }

    @Override
    public void attack(Entity enemy) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        float attackDamage;
        int choice = 0;
        if (this.getAbilities().isEmpty()) {
            System.out.println("1: Normal Attack");
            while (choice != 1) {
                try {
                    if (!scanner.hasNextInt()) {
                        throw new InvalidCommandException("Invalid command: Input must be an integer.");
                    }
                    choice = scanner.nextInt();
                    scanner.nextLine();
                } catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                    scanner.nextLine();
                }
            }
            attackDamage = calculateDamage(true, null);
            enemy.receiveDamage(attackDamage, false);
            this.regenerateMana(random.nextFloat(5.0F, 15.0F));
            Game.showEnemyStats(enemy);
        } else {
            int i = 2;
            System.out.println("1: Normal Attack");
            for (Spell spell: this.getAbilities()) {
                System.out.println(i + ": " + spell);
                i++;
            }
            while (true) {
                try {
                    if (!scanner.hasNextInt()) {
                        throw new InvalidCommandException("Invalid command: Input must be an integer.");
                    }
                    choice = scanner.nextInt();
                    scanner.nextLine();
                } catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                    scanner.nextLine();
                    continue;
                }
                if (choice == 1) {
                    attackDamage = calculateDamage(true, null);
                    enemy.receiveDamage(attackDamage, false);
                    this.regenerateMana(random.nextFloat(5.0F, 15.0F));
                    Game.showEnemyStats(enemy);
                    return;
                }
                if (choice > 1 && choice <= this.getAbilities().size() + 1) {
                    Spell spellCasted = this.getAbilities().get(choice - 2);
                    if (this.tryToUseAbility(spellCasted, enemy)) {
                        attackDamage = calculateDamage(false, spellCasted);
                        enemy.receiveDamage(attackDamage, true);
                        this.getAbilities().remove(spellCasted);
                        this.setCurrentMana(this.getCurrentMana() - spellCasted.getManaCost());
                        Game.showEnemyStats(enemy);
                        return;
                    } else {
                        System.out.println("Not enough mana to cast or the enemy is immune to your spell.");
                    }
                } else {
                    System.out.println("Please choose a valid attack!");
                }
            }
        }

    }
}
