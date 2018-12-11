import java.util.ArrayList;

public class Cluster {
    private Point center;
    private ArrayList <Point> points;
    private Point previousCenter;
    private int red, green, blue;
    private int rowTotal, colTotal;

    public Cluster(Point center, ArrayList <Point> points) {

        this.center = center;
        this.points = points;
        this.previousCenter = new Point(0,0);
        this.rowTotal = 0;
        this.colTotal = 0;
    }

    public void setColor(int r, int g, int b) {
        this.red = r;
        this.green = g;
        this.blue = b;
    }

    public short getRed() {
        return (short)red;
    }

    public short getGreen() {
        return (short)green;
    }

    public short getBlue() {
        return (short)blue;
    }

    public void addPoint(Point p) {
        points.add(p);
        rowTotal += p.getRow();
        rowTotal += p.getCol();
    }

    public ArrayList <Point> getPoints() {
        return points;
    }

    public void clearPoints() {
        points.clear();
    }

    public Point getCenter() {
        return this.center;
    }

    public boolean didCenterChange() {
        if (previousCenter.equals(this.center)) {
            return true;
        }
        else {
            return false;
        }
    }

    public void reCalculateCenter() {
        if (points.size() == 0) {
            return;
        }
        this.previousCenter = center;
        int rowTotal = 0, colTotal = 0;

        int avgRow = rowTotal/points.size();
        int avgCol = colTotal/points.size();
        Point newCenter = new Point(avgRow, avgCol);
        this.center = newCenter;
    }

    public String toString() {
        return ("Center row " + center.getRow() + " and col " + center.getCol() + " Number of Points " + points.size());
    }
}
