import javax.swing.*;
import java.util.ArrayList;


public class SkinFilter implements PixelFilter {
    final short[][] kernel = {{1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}};

    private static short[][] out;
    private static short[][] out2;

    private int kernelWeight;

    // Originally 186 108 73
    private short red = 120;
    private short green = 92;
    private short blue = 80;

    private double THRESHOLD = 100; //Originally 80
    private static final int THRESHOLD2 = 254;

    private ArrayList<Cluster> clusters;
    private int numClusters;

    public SkinFilter() {
        numClusters = Integer.parseInt(JOptionPane.showInputDialog("enter a number"));
        clusters = new ArrayList<>();
    }

    @Override
    public int[] filter(int[] pixels, int width, int height) {
        int[][] pixels2d = PixelLib.convertTo2dArray(pixels, width, height);
        PixelLib.ColorComponents2d img = PixelLib.getColorComponents2d(pixels2d);

        kernelWeight = sumOf(kernel);
        if (out == null) {  // initialize to start, then re-use
            out = new short[height][width];
            out2 = new short[height][width];
        }

        performThreshold(img, out);
        performBlur(out, out2);
        performSecondThreshold(out2);

        // TODO:  Start your k-means code here
        System.out.println("Initializing Clusters");
        initializeClusters(); //re-set existing clusters

        System.out.println("Get all points");
        ArrayList<Point> allPoints = getAllPoints(out2);

        for (int l = 0; l < 2; l++) {
            //Assign points to clusters
            System.out.println("Assigning to cluster");
            assignToCluster(allPoints);

        }


        displayClusters();

        // as last step, loop over all points in all your clusters
        //   change color values in img depending on what cluster each
        //   point is part of.
        // -----------------------------------------

        System.out.println("Updating Colors");
        updateColor(img);

        pixels = PixelLib.combineColorComponents(img);
        return pixels;
    }

    private void displayClusters() {
        System.out.println("Started displaying clusters");
        for (Cluster c: clusters){
            ArrayList <Point> arr = c.getPoints();
            for (Point p: arr) {
                System.out.println("Cluster " + p.getRow() + " " + p.getCol());
            }
        }
    }

    private void assignToCluster(ArrayList<Point> allPoints) {
        for (Point p : allPoints) {
            int index = -1; //If does not run, should give out of bounds exception -1
            double smallestDist = Integer.MAX_VALUE;
            index = findClosestCluster(p);

            Cluster updatedCluster = clusters.get(index);
            updatedCluster.addPoint(p);

            for (Cluster c : clusters) {
                c.reCalculateCenter();
            }
        }
    }

    private int findClosestCluster(Point p) {
        int index = -1;
        double smallestDist = Double.MAX_VALUE;
        for (int i = 0; i < clusters.size(); i++) {
            Cluster c = clusters.get(i);
            double dist = p.distance(c.getCenter());
            if (dist < smallestDist) {
                smallestDist = dist;
                index = i;
            }
        }
        return index;
    }

    private void updateColor(PixelLib.ColorComponents2d img) {
        for (Cluster c:clusters) {
            ArrayList <Point> points = c.getPoints();
            for (Point p:points) {

                img.red[p.getRow()][p.getCol()] = red;
                img.blue[p.getRow()][p.getCol()] = blue;
                img.green[p.getRow()][p.getCol()] = green;
            }
        }
    }

    private boolean areCentersDifferent() {
        for (Cluster c: clusters) {
            if (c.didCenterChange()) return false;
        }
        return true;
    }


    private ArrayList<Point>  getAllPoints(short[][] out2) {
        ArrayList<Point> output = new ArrayList<Point>();
        for (int i = 0; i < out2.length; i++) {
            for (int j = 0; j < out2[0].length; j++) {
                if (out2[i][j] == 255) {
                    output.add(new Point(i, j));
                }
            }
        }
        return output;
    }

    private void initializeClusters() {
        clusters.clear();
        for (int i = 0; i < numClusters; i++) {
            Point randomCenter = getRandomPoint();
            ArrayList<Point> p = new ArrayList<Point>();
            Cluster tmp = new Cluster(randomCenter, p);
            clusters.add(tmp);
        }
    }

    private Point getRandomPoint() {
        int r = (int) (Math.random() * out.length);
        int c = (int) (Math.random() * out[0].length);
        Point randPoint = new Point(r, c);
        return randPoint;
    }

    private void performSecondThreshold(short[][] out2) {
        for (int r = 0; r < out2.length; r++) {
            for (int c = 0; c < out2[0].length; c++) {
                int dist = out2[r][c];
                if (dist > THRESHOLD2) {
                    out2[r][c] = 255;
                } else {
                    out2[r][c] = 0;
                }
            }
        }
    }

    private int sumOf(short[][] kernal) {
        int sum = 0;
        for (int i = 0; i < kernal.length; i++) {
            for (int j = 0; j < kernal[i].length; j++) {
                sum += kernal[i][j];
            }
        }

        if (sum == 0) return 1;
        return sum;
    }

    private void performBlur(short[][] out, short[][] out2) {
        for (int r = 0; r < out.length - kernel.length - 1; r++) {
            for (int c = 0; c < out[0].length - kernel.length - 1; c++) {

                int outputColor = calculateOutputFrom(r, c, out, kernel);
                out2[r][c] = (short) (outputColor / kernelWeight);
                if (out2[r][c] < 0) out2[r][c] = 0;
                if (out2[r][c] > 255) out2[r][c] = 255;
            }
        }
    }

    private int calculateOutputFrom(int r, int c, short[][] im, short[][] kernal) {
        int out = 0;
        for (int i = 0; i < kernal.length; i++) {
            for (int j = 0; j < kernal[i].length; j++) {
                out += im[r + i][c + j] * kernal[i][j];
            }
        }

        return out;
    }

    private void performThreshold(PixelLib.ColorComponents2d img, short[][] out) {
        for (int r = 0; r < out.length; r++) {
            for (int c = 0; c < out[0].length; c++) {
                double dist = distance(red, img.red[r][c], green, img.green[r][c], blue, img.blue[r][c]);
                if (dist > THRESHOLD) {
                    out[r][c] = 0;
                } else {
                    out[r][c] = 255;
                }
            }
        }
    }

    public double distance(short r1, short r2, short g1, short g2, short b1, short b2) {
        int dr = r2 - r1;
        int dg = g2 - g1;
        int db = b2 - b1;

        return Math.sqrt(dr * dr + dg * dg + db * db);
    }
}




