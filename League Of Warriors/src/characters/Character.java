package characters;

import exceptions.InvalidCommandException;
import game.Game;
import interfaces.Battle;
import powers.Spell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public abstract class Character extends Entity implements Battle {
    protected String name;
    protected Integer xp;
    protected Integer level;

    protected Integer strength;
    protected Integer charisma;
    protected Integer dexterity;

    protected AttributeTypes mainAttribute;
    protected List<AttributeTypes> secondaryAttributes;

    public Character(ArrayList<Spell> abilities, float currentHP, float maxHP, float currentMana, float maxMana,
                     float normalAttackDamage, boolean fireImmunity, boolean iceImmunity, boolean earthImmunity,
                     Integer strength, Integer charisma, Integer dexterity, String name, Integer xp, Integer level,
                     AttributeTypes mainAttribute, List<AttributeTypes> secondaryAttributes) {

        super(abilities, currentHP, maxHP, currentMana, maxMana, normalAttackDamage, fireImmunity, iceImmunity, earthImmunity);
        this.strength = strength;
        this.charisma = charisma;
        this.dexterity = dexterity;
        this.name = name;
        this.xp = xp;
        this.level = level;
        this.mainAttribute = mainAttribute;
        this.secondaryAttributes = secondaryAttributes;
    }

    protected abstract float getSpellDamageMultiplier(Spell spellCasted);
    protected abstract float getNormalAttackMultiplier();

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

    public AttributeTypes getMainAttribute() {
        return mainAttribute;
    }

    public List<AttributeTypes> getSecondaryAttributes() {
        return secondaryAttributes;
    }

    public Integer getAttributeValue(AttributeTypes attribute) {
        return switch (attribute) {
            case STRENGTH -> strength;
            case CHARISMA -> charisma;
            case DEXTERITY -> dexterity;
        };
    }

    @Override
    public float getDamage(boolean isNormalAttack, Spell spellCasted) {
        Random random = new Random();
        double chance = random.nextDouble();
        boolean critHit = chance < 0.5;

        float damage;
        if (isNormalAttack) {
            damage = this.getNormalAttackMultiplier();
        } else {
            damage = this.getSpellDamageMultiplier(spellCasted);
        }
        if (critHit && getAttributeValue(mainAttribute) >= 50) {
            System.out.println("Given your will to beat your enemy you successfully hit a CRIT hit.");
            return 2 * damage;
        } else {
            return damage;
        }
    }

    @Override
    public void receiveDamage(float damage, boolean fromSpell) {
        Random random = new Random();
        double chance = random.nextDouble();
        if (!fromSpell) {
            if (getAttributeValue(secondaryAttributes.get(0)) >= 10 && getAttributeValue(secondaryAttributes.get(1)) >= 30) {
                if (chance < 0.5) {
                    System.out.println("Given your extraordinary skills you managed to partially dodge a hit and got reduced damage!");
                    System.out.println("The enemy dealt " + damage / 2 + " damage to you.");
                    this.setCurrentHP(this.getCurrentHP() - damage / 2);
                    return;
                }
            }
            System.out.println("The enemy dealt " + damage + " damage to you.");
            this.setCurrentHP(this.getCurrentHP() - damage);
            return;
        } else {
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
            attackDamage = getDamage(true, null);
            enemy.receiveDamage(attackDamage, false);
            regenerateMana(random.nextFloat(5.0F, 15.0F));
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
                    attackDamage = getDamage(true, null);
                    enemy.receiveDamage(attackDamage, false);
                    this.regenerateMana(random.nextFloat(5.0F, 15.0F));
                    Game.showEnemyStats(enemy);
                    return;
                }
                if (choice > 1 && choice <= this.getAbilities().size() + 1) {
                    Spell spellCasted = this.getAbilities().get(choice - 2);
                    if (this.tryToUseAbility(spellCasted, enemy)) {
                        attackDamage = getDamage(false, spellCasted);
                        enemy.accept(spellCasted, attackDamage);
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
