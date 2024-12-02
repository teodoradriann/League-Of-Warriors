package game;

import characters.Character;

import java.util.ArrayList;

public class Grid extends ArrayList<ArrayList<Cell>> {
    int length;
    int height;
    Character hero;
    Cell currentCell;

    private Grid(int length, int height, Character hero, Cell currentCell) {
        this.length = length;
        this.height = height;
        this.hero = hero;
        this.currentCell = currentCell;
    }

    static public void generateMap() {
        
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
