import processing.core.PImage;

import javax.swing.*;

public class PixelateGold implements PixelFilter {

    private int blockSize = 0;
    private short[][] img;
    private boolean flagAskedQuestion = false;
    private int startLoop;

    public PixelateGold() {
        userInput();
    }

    public int[] filter(int[] pixels, int width, int height) {
        short[] bwpixels = PixelLib.convertToShortGreyscale(pixels); //convert to bw array
        img = PixelLib.convertTo2dArray(bwpixels, width, height); // convert to 2d array

        int lastRowIndex = img.length - 1; //set max row length

        for (int r = startLoop; r < img.length - blockSize + 1; r += blockSize) {
            for (int c = startLoop; c < img[r].length - blockSize + 1; c += blockSize) {
                if (startLoop == 0) {
                    int num = getAverage(r, c);
                    fillImg(num, r, c);
                } else {
                    int num = img[r][c];
                    fillImg(num, r, c);
                }
            }
        }

        PixelLib.fill1dArray(img, pixels); //convert back to 1d array
        return pixels; // return statement
    }

    private void userInput() {
        String input = JOptionPane.showInputDialog("How large would you like the block?");
        blockSize = Integer.parseInt(input);

        input = JOptionPane.showInputDialog("Would you like a hard or soft filter?");

        if (input.equals("hard")) {
            startLoop = 1;
        } else if (input.equals("soft")) {
            startLoop = 0;
        } else {
            JOptionPane.showMessageDialog(null, "Please type hard or soft");
            userInput();
        }
    }

    private void fillImg(int num, int r, int c) {
        for (int i = r; i < r + blockSize; i++) {
            for (int j = c; j < c + blockSize; j++) {
                img[i][j] = (short) (num);
            }
        }
    }

    private short getAverage(int r, int c) {
        short color = 0;
        for (int i = r; i < r + blockSize; i++) {
            for (int j = c; j < c + blockSize; j++) {
                color += img[i][j];
            }
        }
        return ((short) (color / 9));
    }
}