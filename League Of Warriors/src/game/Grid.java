package game;

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

    private Grid(int length, int height, Character hero) {
        this.length = length;
        this.height = height;
        this.hero = hero;
    }

    static public Grid generateMap(int length, int height, Character hero) {
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
                    cellToVisit.setCellType(CellEntityType.PLAYER);
                    heroCell.setCellType(CellEntityType.VOID);
                    heroCell = cellToVisit;
                    cellToVisit.setVisiting(false);
                    cellToVisit.setVisited(true);
                    Game.flushScreen();
                    System.out.println("You went " + where + "! " + "The cell you're trying to visit is: " + CellEntityType.VOID);
                    this.printMap();
                }
                case SANCTUARY -> {

                }
                case PORTAL -> {

                }
                case ENEMY -> {

                }
            }
        }
        else {
            System.out.println("The target cell is null. Invalid move!");
        }
    }
}
