package game;

import account.Account;
import characters.Character;
import exceptions.ImpossibleMove;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Grid extends ArrayList<ArrayList<Cell>> {
    private int length;
    private int height;
    private Character hero;
    private Cell heroCell;
    private Account loggedAccount;

    private Grid(int length, int height, Character hero) {
        this.length = length;
        this.height = height;
        this.hero = hero;
    }

    static public Grid generateMap(int length, int height, Character hero, Account loggedAccount) {
        int dimensions = length * height;
        if (dimensions < 8) {
            throw new IllegalArgumentException("Grid dimensions are too small. The grid must have at least 8 cells.");
        }
        int enemiesNumber = dimensions / 2;
        int sanctuariesNumber = dimensions / 4;
        int portalNumber = 1;
        Random rand = new Random();
        Grid map = new Grid(length, height, hero);
        for (int i = 0; i < height; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < length; j++) {
                row.add(new Cell(i, j, CellEntityType.VOID, false));
            }
            map.add(row);
        }

        int heroCellOx = rand.nextInt(0, height);
        int heroCellOy = rand.nextInt(0, length);
        map.heroCell = map.get(heroCellOx).get(heroCellOy);
        map.hero = hero;
        map.heroCell.setVisited(true);
        map.heroCell.setCellType(CellEntityType.PLAYER);
        map.loggedAccount = loggedAccount;

        ArrayList<Cell> emptyCells = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                Cell cell = map.get(i).get(j);
                if (cell.getCellType().equals(CellEntityType.VOID)) {
                    emptyCells.add(cell);
                }
            }
        }

        setCells(enemiesNumber, emptyCells, CellEntityType.ENEMY);
        setCells(sanctuariesNumber, emptyCells, CellEntityType.SANCTUARY);
        setCells(portalNumber, emptyCells, CellEntityType.PORTAL);
        return map;
    }

    private static void setCells(int howMany, ArrayList<Cell> emptyCells, CellEntityType type) {
        Random rand = new Random();
        while (howMany > 0 && !emptyCells.isEmpty()) {
            int index = rand.nextInt(emptyCells.size());
            Cell cell = emptyCells.get(index);
            cell.setCellType(type);
            emptyCells.remove(index);
            howMany--;
        }
    }

    public void printMap() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.length; j++) {
                Cell cell = this.get(i).get(j);
                switch (cell.getCellType()) {
                    case PLAYER -> System.out.print(CellEntityLetter.H + " ");
                    case VOID -> {
                        if (cell.isVisited() || cell.isVisiting()) {
                            System.out.print(CellEntityLetter.V + " ");
                        } else {
                            System.out.print("* ");
                        }
                    }
                    case ENEMY -> {
                        if (cell.isVisited() || cell.isVisiting()) {
                            System.out.print(CellEntityLetter.E + " ");
                        } else {
                            System.out.print("* ");
                        }
                    }
                    case SANCTUARY -> {
                        if (cell.isVisited() || cell.isVisiting()) {
                            System.out.print(CellEntityLetter.S + " ");
                        } else {
                            System.out.print("* ");
                        }
                    }
                    case PORTAL -> {
                        if (cell.isVisited() || cell.isVisiting()) {
                            System.out.print(CellEntityLetter.P + " ");
                        } else {
                            System.out.print("* ");
                        }
                    }
                    default -> System.out.print("? ");
                }
            }
            System.out.println();
        }
    }

    public void moveHero(String where) {
        Cell cellToVisit = null;
        switch (where.toLowerCase()) {
            case "north" -> {
                try {
                    int newOx = heroCell.getOx() - 1;
                    if (newOx < 0) {
                        throw new ImpossibleMove("You can't go north!");
                    }
                    cellToVisit = this.get(newOx).get(heroCell.getOy());
                    cellToVisit.setVisiting(true);
                } catch (ImpossibleMove e) {
                    System.out.println(e.getMessage());
                }
            }
            case "south" -> {
                try {
                    int newOx = heroCell.getOx() + 1;
                    if (newOx >= height) {
                        throw new ImpossibleMove("You can't go south!");
                    }
                    cellToVisit = this.get(newOx).get(heroCell.getOy());
                    cellToVisit.setVisiting(true);
                } catch (ImpossibleMove e) {
                    System.out.println(e.getMessage());
                }
            }
            case "west" -> {
                try {
                    int newOy = heroCell.getOy() - 1;
                    if (newOy < 0) {
                        throw new ImpossibleMove("You can't go west!");
                    }
                    cellToVisit = this.get(heroCell.getOx()).get(newOy);
                    cellToVisit.setVisiting(true);
                } catch (ImpossibleMove e) {
                    System.out.println(e.getMessage());
                }
            }
            case "east" -> {
                try {
                    int newOy = heroCell.getOy() + 1;
                    if (newOy >= length) {
                        throw new ImpossibleMove("You can't go east!");
                    }
                    cellToVisit = this.get(heroCell.getOx()).get(newOy);
                    cellToVisit.setVisiting(true);
                } catch (ImpossibleMove e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        Game.flushScreen();
        this.printMap();
        if (cellToVisit != null) {
            switch (cellToVisit.getCellType()) {
                case VOID -> {
                    visitCell(CellEntityType.VOID, cellToVisit, where);
                }
                case SANCTUARY -> {
                    Random random = new Random();
                    float hpToAdd = random.nextFloat(1.0F, 99.0F);
                    hero.setCurrentHP(hero.getCurrentHP() + hpToAdd);
                    if (hero.getCurrentHP() > 100.0F) {
                        hero.setCurrentHP(100.0F);
                    }
                    float manaToAdd = random.nextFloat(1.0F, 49.0F);
                    hero.setCurrentMana(hero.getCurrentMana() + manaToAdd);
                    if (hero.getCurrentMana() > 50.0F) {
                        hero.setCurrentMana(50.0F);
                    }
                    visitCell(CellEntityType.SANCTUARY, cellToVisit, where);
                    System.out.println("Current HP: " + hero.getCurrentHP());
                    System.out.println("Current mana: " + hero.getCurrentMana());
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
                }
                case ENEMY -> {

                }
            }
        }
        else {
            System.out.println("The target cell is null. Invalid move!");
        }
    }

    private void visitCell(CellEntityType type, Cell cell, String where) {
        cell.setCellType(CellEntityType.PLAYER);
        heroCell.setCellType(CellEntityType.VOID);
        heroCell = cell;
        cell.setVisiting(false);
        cell.setVisited(true);
        Game.flushScreen();
        System.out.println("You went " + where + "! " + "The cell you're trying to visit is: " + type);
        this.printMap();
    }
}
