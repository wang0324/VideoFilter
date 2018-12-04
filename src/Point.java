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

    public double distance(Point a) {
        return Math.sqrt(Math.pow(this.row-a.getRow(), 2) + Math.pow(this.col-a.getCol(), 2));
    }

    public String toString() {
        String s = "";
        return s;
    }
}
