public class Point {

    private int row;
    private int col;

    public Point(int r, int c) {
        this.row = r;
        this.col = c;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean equals(Point a) {
        if (a.getCol() == this.col && a.getRow() == this.row) {
            return true;
        }
        return false;
    }

    public String toString() {
        String s = "";
        return s;
    }
}
