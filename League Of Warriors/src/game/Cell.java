package game;

public class Cell {
    private int Ox;
    private int Oy;
    private CellEntityType cellType;
    private boolean visited;
    private boolean isVisiting;

    public int getOx() {
        return Ox;
    }

    public void setOx(int ox) {
        Ox = ox;
    }

    public int getOy() {
        return Oy;
    }

    public void setOy(int oy) {
        Oy = oy;
    }

    public CellEntityType getCellType() {
        return cellType;
    }

    public void setCellType(CellEntityType cellType) {
        this.cellType = cellType;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isVisiting() { return isVisiting; }

    public void setVisiting(boolean isVisiting) { this.isVisiting = isVisiting; }

    public Cell(int ox, int oy, CellEntityType cellType, boolean visited) {
        this.Ox = ox;
        this.Oy = oy;
        this.cellType = cellType;
        this.visited = visited;
    }
}
