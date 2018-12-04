import javax.swing.*;

public class ConvolutionFilter implements PixelFilter {

    private int[][] mask;
    private int maskSize;

    public ConvolutionFilter() {
        String input = JOptionPane.showInputDialog("Enter the size of the mask");
        maskSize = Integer.parseInt(input);
        mask = new int[maskSize][maskSize];

        for (int i = 0; i < mask.length; i++) {
            for (int j = 0; j < mask[i].length; j++) {
                String in = JOptionPane.showInputDialog("Enter the mask value for row " + i +" and col " + j);
                mask[i][j] = Integer.parseInt(in);
            }
        }
    }

    @Override
    public int[] filter(int[] pixels, int width, int height) {
        short[] bwpixels = PixelLib.convertToShortGreyscale(pixels);
        short[][] im = PixelLib.convertTo2dArray(bwpixels, width, height);
        short[][] newim = im;

        for (int r = (maskSize-1)/2; r < im.length-(maskSize-1)/2; r++) {
            for (int c = (maskSize-1)/2; c < im[0].length-(maskSize-1)/2; c++) {

                int output = 0;
                for (int rowOffSet = -(maskSize-1)/2; rowOffSet <= (maskSize-1)/2; rowOffSet++) {
                    for (int colOffSet = -(maskSize-1)/2; colOffSet <= (maskSize-1)/2; colOffSet++) {

                        int maskValue = mask[rowOffSet+(maskSize-1)/2][colOffSet+(maskSize-1)/2];
                        int pixelVal = im[r+rowOffSet][c+colOffSet];

                        output += maskValue*pixelVal;
                    }
                }
                output = output / getKernalWeight();

                newim[r][c] = (short)output;
            }
        }


        PixelLib.fill1dArray(newim, pixels);
        return pixels;

    }

    private int getKernalWeight() {
        int sum = 0;
        for (int i = 0; i < mask.length; i++) {
            for (int j = 0; j < mask[i].length; j++) {
                sum += mask[i][j];
            }
        }
        if (sum <= 0) {
            sum = 1;
        }
        return sum;
    }

}


