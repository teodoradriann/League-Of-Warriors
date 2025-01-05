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
        Game.showStats(hero);
        System.out.println("Current level: " + hero.getLevel());
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

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }

    public Cell getHeroCell() {
        return heroCell;
    }

    public void setHeroCell(Cell heroCell) {
        this.heroCell = heroCell;
    }

    public static Grid generateTestGrid(Character hero) {
        Grid map = new Grid(5, 5, hero);

        for (int i = 0; i < 5; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                row.add(new Cell(i, j, CellEntityType.VOID, false));
            }
            map.add(row);
        }

        // P N N S N
        map.get(0).set(0, new Cell(0, 0, CellEntityType.PLAYER, true));
        map.get(0).set(1, new Cell(0, 1, CellEntityType.VOID, false));
        map.get(0).set(2, new Cell(0, 2, CellEntityType.VOID, false));
        map.get(0).set(3, new Cell(0, 3, CellEntityType.SANCTUARY, false));
        map.get(0).set(4, new Cell(0, 4, CellEntityType.VOID, false));

        // N N N S N
        map.get(1).set(0, new Cell(1, 0, CellEntityType.VOID, false));
        map.get(1).set(1, new Cell(1, 1, CellEntityType.VOID, false));
        map.get(1).set(2, new Cell(1, 2, CellEntityType.VOID, false));
        map.get(1).set(3, new Cell(1, 3, CellEntityType.SANCTUARY, false));
        map.get(1).set(4, new Cell(1, 4, CellEntityType.VOID, false));

        // S N N N N
        map.get(2).set(0, new Cell(2, 0, CellEntityType.SANCTUARY, false));
        map.get(2).set(1, new Cell(2, 1, CellEntityType.VOID, false));
        map.get(2).set(2, new Cell(2, 2, CellEntityType.VOID, false));
        map.get(2).set(3, new Cell(2, 3, CellEntityType.VOID, false));
        map.get(2).set(4, new Cell(2, 4, CellEntityType.VOID, false));

        // N N N N E
        map.get(3).set(0, new Cell(3, 0, CellEntityType.VOID, false));
        map.get(3).set(1, new Cell(3, 1, CellEntityType.VOID, false));
        map.get(3).set(2, new Cell(3, 2, CellEntityType.VOID, false));
        map.get(3).set(3, new Cell(3, 3, CellEntityType.VOID, false));
        map.get(3).set(4, new Cell(3, 4, CellEntityType.ENEMY, false));

        // N N N S F
        map.get(4).set(0, new Cell(4, 0, CellEntityType.VOID, false));
        map.get(4).set(1, new Cell(4, 1, CellEntityType.VOID, false));
        map.get(4).set(2, new Cell(4, 2, CellEntityType.VOID, false));
        map.get(4).set(3, new Cell(4, 3, CellEntityType.SANCTUARY, false));
        map.get(4).set(4, new Cell(4, 4, CellEntityType.PORTAL, false));

        map.heroCell = map.get(0).get(0);
        map.hero = hero;

        return map;
    }
}
