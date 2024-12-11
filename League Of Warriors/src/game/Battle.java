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
}
