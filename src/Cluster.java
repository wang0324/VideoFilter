import java.util.ArrayList;

public class Cluster {
    private Point center;
    private ArrayList <Point> points;

    public Cluster(Point center, ArrayList <Point> points) {
        this.center = center;
        this.points = points;
    }

    public void addPoint(Point p) {
        points.add(p);
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

    public void reCalculateCenter() {
        int rowTotal = 0, colTotal = 0;
        for (Point p : points) {
            rowTotal += p.getRow();
            colTotal += p.getCol();
        }
        int avgRow = rowTotal/points.size();
        int avgCol = colTotal/points.size();
        Point newCenter = new Point(avgRow, avgCol);
        this.center = newCenter;
    }

    public String toString() {
        return ("Center row " + center.getRow() + " and col " + center.getCol() + " Number of Points " + points.size());
    }
}
