package game;

import characters.Enemy;
import characters.Character;
import characters.Entity;
import powers.Spell;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Battle {
    public static boolean startBattle(Character hero, Enemy enemy) throws InterruptedException {
        ArrayList<Spell> abilities = Entity.generateSpellsArray(3, 7);
        Random random = new Random();
        hero.setAbilities(abilities);
        Game.flushScreen();
        System.out.println("AN EPIC BATTLE HAS STARTED!");
        System.out.println(hero.getName() + " X " + enemy.getType() + " enemy");
        System.out.println(enemy);
        int i = 1;
        while (true) {
            System.out.println("ROUND: " + i);
            hero.attack(enemy);
            TimeUnit.SECONDS.sleep(3);
            if (enemy.getCurrentHP() <= 0.0F) {
                hero.regenerateHealth(random.nextFloat(10.0F, 30.0F));
                return true;
            }
            enemy.attack(hero);
            TimeUnit.SECONDS.sleep(3);
            if (hero.getCurrentHP() <= 0.0F) {
                return false;
            }
            i++;
        }
    }

    public static boolean startTestBattle(Character hero, Enemy enemy) throws InterruptedException {
        ArrayList<Spell> abilities = Entity.generateSpellsArray(3, 4);
        Random random = new Random();
        hero.setAbilities(abilities);
        Game.flushScreen();
        System.out.println("AN EPIC BATTLE HAS STARTED!");
        System.out.println(hero.getName() + " X " + enemy.getType() + " enemy");
        System.out.println(enemy);
        while (true) {
            float attackDamage;
            if (!abilities.isEmpty()) {
                System.out.println(abilities);
                attackDamage = hero.calculateDamage(false, abilities.getFirst());
                enemy.receiveDamage(attackDamage, true);
                hero.setCurrentMana(hero.getCurrentMana() - abilities.getFirst().getManaCost());
                abilities.remove(abilities.getFirst());
            } else {
                System.out.println("NO ABILITIES. NORMAL ATTACKING.");
                attackDamage = hero.calculateDamage(true, null);
                enemy.receiveDamage(attackDamage, false);

                hero.regenerateMana(random.nextFloat(5.0F, 15.0F));
                Game.showEnemyStats(enemy);
            }
            TimeUnit.SECONDS.sleep(2);
            if (enemy.getCurrentHP() <= 0.0F) {
                hero.regenerateHealth(random.nextFloat(10.0F, 30.0F));
                return true;
            }
            enemy.attack(hero);
            TimeUnit.SECONDS.sleep(2);
            if (hero.getCurrentHP() <= 0.0F) {
                return false;
            }
        }
    }
}
