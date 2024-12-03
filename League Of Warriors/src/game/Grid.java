package game;

import characters.Character;

import java.util.ArrayList;
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
        for (int i = 0; i < length; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < height; j++) {
                row.add(new Cell(i, j, CellEntityType.VOID, false));
            }
            map.add(row);
        }

        int heroCellOx = rand.nextInt(0, length);
        int heroCellOy = rand.nextInt(0, height);
        map.heroCell = map.get(heroCellOx).get(heroCellOy);
        map.hero = hero;
        map.heroCell.setVisited(true);
        map.heroCell.setCellType(CellEntityType.PLAYER);

        ArrayList<Cell> emptyCells = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
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
        for (int i = 0; i < this.length; i++) {
            for (int j = 0; j < this.height; j++) {
                Cell cell = this.get(i).get(j);
                switch (cell.getCellType()) {
                    case PLAYER -> System.out.print("H ");
                    case VOID -> {
                        if (cell.isVisited()) {
                            System.out.print("V ");
                        } else {
                            System.out.print("* ");
                        }
                    }
                    case ENEMY -> {
                        if (cell.isVisited()) {
                            System.out.print("E ");
                        } else {
                            System.out.print("* ");
                        }
                    }
                    case SANCTUARY -> {
                        if (cell.isVisited()) {
                            System.out.print("S ");
                        } else {
                            System.out.print("* ");
                        }
                    }
                    case PORTAL -> {
                        if (cell.isVisited()) {
                            System.out.print("P ");
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

    void goNorth() {

    }

    void goSouth() {

    }

    void goWest() {

    }

    void goEast() {

    }
}
