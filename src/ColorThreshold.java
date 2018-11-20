
import javax.swing.*;

public class ColorThreshold implements PixelFilter {
    private short red, green, blue, threshhold;

    public ColorThreshold() {
        String input = JOptionPane.showInputDialog("Choose the amount of red");
        red = Short.parseShort(input);

        input = JOptionPane.showInputDialog("Choose the amount of green");
        green = Short.parseShort(input);

        input = JOptionPane.showInputDialog("Choose the amount of blue");
        blue = Short.parseShort(input);

        input = JOptionPane.showInputDialog("Choose the threshold");
        threshhold = Short.parseShort(input);
    }

    @Override
    public int[] filter(int[] pixels, int width, int height) {
        PixelLib.ColorComponents2d vals = PixelLib.getColorComponents2d(pixels, width, height);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                double distance = distanceBetweenPoints(i, i, vals);
                //System.out.println(distance);
                if (distance < threshhold) {
                    vals.red[i][j] = 255;
                    vals.green[i][j] = 255;
                    vals.blue[i][j] = 255;
                } else {
                    vals.red[i][j] = 0;
                    vals.green[i][j] = 0;
                    vals.blue[i][j] = 0;
                }
            }
        }
        return PixelLib.combineColorComponents(vals);
    }

    private double distanceBetweenPoints(int i, int j, PixelLib.ColorComponents2d vals) {
        return Math.sqrt(Math.pow((red - vals.red[i][j]), 2) + Math.pow((green - vals.green[i][j]), 2) + Math.pow((blue - vals.blue[i][j]), 2));
    }
}
