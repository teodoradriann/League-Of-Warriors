package game;

import account.Account;
import characters.Character;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import characters.Enemy;
import characters.EnemySpawner;
import characters.Entity;
import exceptions.ImpossibleMove;
import exceptions.InvalidCommandException;
import utils.JsonInput;

public class Game {
    private ArrayList<Account> existingAccounts;
    private Grid map;
    private Account loggedAccount;
    private Character hero;
    Scanner scanner = new Scanner(System.in);

    public void init() throws InterruptedException {
        existingAccounts = JsonInput.deserializeAccounts();
        loginScreen();
        flushScreen();
        selectCharacterAndStartGame();
    }

    public void testInit() {
        existingAccounts = JsonInput.deserializeAccounts();
    }

    public void run() throws InterruptedException {
        while (true) {
            String key = scanner.nextLine();
            switch (key.toLowerCase()) {
                case "w":
                    try {
                        this.moveHero("north");
                    } catch (ImpossibleMove e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "s":
                    try {
                        this.moveHero("south");
                    } catch (ImpossibleMove e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "a":
                    try {
                        this.moveHero("west");
                    } catch (ImpossibleMove e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "d":
                    try {
                        this.moveHero("east");
                    } catch (ImpossibleMove e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "q":
                    flushScreen();
                    selectCharacterAndStartGame();
                    return;
            }
        }
    }

    private void loginScreen() {
        boolean accountFound = false;
        while (true) {
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            for (Account account : existingAccounts) {
                if (account.getEmail().equals(email) && account.getPassword().equals(password)) {
                    accountFound = true;
                    this.loggedAccount = account;
                    break;
                }
            }

            if (accountFound) break;
            else System.out.println("Account not found!");
        }
    }

    private void selectCharacterAndStartGame() throws InterruptedException, InvalidCommandException {
        System.out.println("Hello " + this.loggedAccount.getName() + "!");
        System.out.println("Please choose your hero :)");
        int i = 1;
        System.out.println("-1: Go back to login screen.");
        for (Character character : this.loggedAccount.getOwnedCharacters()) {
            System.out.println(i + ": (" + character.getName() + ") -> type: " + character.getProfession() + ", level: " + character.getLevel() + ", xp: " + character.getXp());
            i++;
        }

        while (true) {
            int choice;
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
            if (choice == -1) {
                flushScreen();
                this.init();
                break;
            }

            if (choice > 0 && choice <= this.loggedAccount.getOwnedCharacters().size()) {
                this.hero = this.loggedAccount.getOwnedCharacters().get(choice - 1);
                break;
            } else {
                System.out.println("Please choose a valid character!");
            }
        }
        this.startGame();
    }

    private void startGame() throws InterruptedException {
        flushScreen();
        Random rand = new Random();
        int length = rand.nextInt(3, 11);
        int height = rand.nextInt(3, 11);
        map = Grid.generateMap(length, height, hero);
        map.printMap();
        this.run();
    }

    public void moveHero(String where) throws InterruptedException {
        Cell cellToVisit = getCellToVisit(where);
        Game.flushScreen();
        map.printMap();
        if (cellToVisit != null) {
            switch (cellToVisit.getCellType()) {
                case VOID -> {
                    visitCell(CellEntityType.VOID, cellToVisit, where);
                }
                case SANCTUARY -> {
                    Random random = new Random();
                    float hpToAdd = random.nextFloat(1.0F, 99.0F);
                    hero.setCurrentHP(hero.getCurrentHP() + hpToAdd);
                    if (hero.getCurrentHP() > hero.getMaxHP()) {
                        hero.setCurrentHP(hero.getMaxHP());
                    }
                    float manaToAdd = random.nextFloat(1.0F, 49.0F);
                    hero.setCurrentMana(hero.getCurrentMana() + manaToAdd);
                    if (hero.getCurrentMana() > hero.getMaxMana()) {
                        hero.setCurrentMana(hero.getMaxMana());
                    }
                    visitCell(CellEntityType.SANCTUARY, cellToVisit, where);
                }
                case PORTAL -> {
                    int xpEarned = loggedAccount.getGamesPlayed();
                    int gamesPlayed = loggedAccount.getGamesPlayed();
                    gamesPlayed++;
                    loggedAccount.setGamesPlayed(gamesPlayed);
                    int heroLevel = hero.getLevel();
                    heroLevel++;
                    hero.setLevel(heroLevel);
                    xpEarned *= 5;
                    hero.setXp(hero.getXp() + xpEarned);
                    while (hero.getXp() > 99) {
                        hero.setLevel(hero.getLevel() + 1);
                        hero.setXp(hero.getXp() - 100);
                    }
                    visitCell(CellEntityType.PORTAL, cellToVisit, where);
                    System.out.println("CONGRATS! You have found the portal. You're going to the next level.");
                    TimeUnit.SECONDS.sleep(3);
                    this.startGame();
                }
                case ENEMY -> {
                    EnemySpawner spawner = new EnemySpawner();
                    Enemy enemy = spawner.createEnemy();
                    System.out.println(enemy);
                    if (Battle.startBattle(hero, enemy)) {
                        System.out.println("YOU HAVE DEFEATED YOUR ENEMY.");
                        TimeUnit.SECONDS.sleep(1);
                        visitCell(CellEntityType.ENEMY, cellToVisit, where);
                    } else {
                        flushScreen();
                        System.out.println("YOU DIED.");
                        TimeUnit.SECONDS.sleep(3);
                        Game.flushScreen();
                        this.selectCharacterAndStartGame();
                    }
                }
            }
        }
        else {
            System.out.println("The target cell is null. Invalid move!");
        }
    }

    private Cell getCellToVisit(String where) throws ImpossibleMove {
        Cell cellToVisit = null;
        switch (where.toLowerCase()) {
            case "north" -> {
                int newOx = map.getHeroCell().getOx() - 1;
                if (newOx < 0) {
                    throw new ImpossibleMove("You can't go north!");
                } else {
                    cellToVisit = map.get(newOx).get(map.getHeroCell().getOy());
                    cellToVisit.setVisiting(true);
                }
            }
            case "south" -> {
                int newOx = map.getHeroCell().getOx() + 1;
                if (newOx >= map.getHeight()) {
                    throw new ImpossibleMove("You can't go south!");
                } else {
                    cellToVisit = map.get(newOx).get(map.getHeroCell().getOy());
                    cellToVisit.setVisiting(true);
                }
            }
            case "west" -> {
                int newOy = map.getHeroCell().getOy() - 1;
                if (newOy < 0) {
                    throw new ImpossibleMove("You can't go west!");
                } else {
                    cellToVisit = map.get(map.getHeroCell().getOx()).get(newOy);
                    cellToVisit.setVisiting(true);
                }
            }
            case "east" -> {
                int newOy = map.getHeroCell().getOy() + 1;
                if (newOy >= map.getLength()) {
                    throw new ImpossibleMove("You can't go east!");
                } else {
                    cellToVisit = map.get(map.getHeroCell().getOx()).get(newOy);
                    cellToVisit.setVisiting(true);
                }
            }
        }
        return cellToVisit;
    }

    private void visitCell(CellEntityType type, Cell cell, String where) {
        cell.setCellType(CellEntityType.PLAYER);
        map.getHeroCell().setCellType(CellEntityType.VOID);
        map.setHeroCell(cell);
        cell.setVisiting(false);
        cell.setVisited(true);
        Game.flushScreen();
        System.out.println("You went " + where + "! " + "The cell you just visited is: " + type);
        map.printMap();
    }

    public static void flushScreen() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    public static void showStats(Character hero) {
        float hp = Math.max(hero.getCurrentHP(), 0.0F);
        System.out.println("Current HP: " + hp);
        System.out.println("Current mana: " + hero.getCurrentMana());
    }

    public static void showEnemyStats(Entity enemy) {
        float hp = Math.max(enemy.getCurrentHP(), 0.0F);
        System.out.println("Current enemy HP: " + hp);
    }
}
