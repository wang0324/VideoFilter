//import javax.swing.*;
//
//public class EdgeDetection implements PixelFilter {
//
//    private int[][] maskx = {{-1,0,1}, {-2, 0, 2}, {-1, 0, 1}}, masky = {{1,2,1}, {0,0,0}, {-1, -2, -1}};
//    private int maskSize = 3;
//    private int threshold;
//
//    public EdgeDetection() {
//        String in = JOptionPane.showInputDialog("Enter the threshold");
//        threshold = Integer.parseInt(in);
//    }
//
//    @Override
//    public int[] filter(int[] pixels, int width, int height) {
//        short[] bwpixels = PixelLib.convertToShortGreyscale(pixels);
//        short[][] im = PixelLib.convertTo2dArray(bwpixels, width, height);
//        short[][] newim = im;
//
//        for (int r = (maskSize-1)/2; r < im.length-(maskSize-1)/2; r++) {
//            for (int c = (maskSize-1)/2; c < im[0].length-(maskSize-1)/2; c++) {
//                int output1 = 0, output2 = 0;
//
//                for (int rowOffSet = -(maskSize-1)/2; rowOffSet <= (maskSize-1)/2; rowOffSet++) {
//                    for (int colOffSet = -(maskSize-1)/2; colOffSet <= (maskSize-1)/2; colOffSet++) {
//
//                        int maskValueX = maskx[rowOffSet+(maskSize-1)/2][colOffSet+(maskSize-1)/2];
//                        int maskValueY = masky[rowOffSet+(maskSize-1)/2][colOffSet+(maskSize-1)/2];
//
//                        int maskValue = (int)(Math.sqrt(Math.pow(maskValueX, 2) + Math.pow(maskValueY, 2)));
//                        int pixelVal = im[r+rowOffSet][c+colOffSet];
//
//                        output1 += maskValue*pixelVal;
//                        output2 += maskValue*pixelVal;
//                    }
//                }
//                //output = output / getKernalWeight();
//
//                newim[r][c] = (short)output;
//            }
//        }
//
//
//        PixelLib.fill1dArray(newim, pixels);
//        return pixels;
//
//    }
//
//    private short getNewPixelVal() {
//        int output = 0;
//        for (int rowOffSet = -(maskSize-1)/2; rowOffSet <= (maskSize-1)/2; rowOffSet++) {
//            for (int colOffSet = -(maskSize-1)/2; colOffSet <= (maskSize-1)/2; colOffSet++) {
//
//                int maskValueX = maskx[rowOffSet+(maskSize-1)/2][colOffSet+(maskSize-1)/2];
//                int maskValueY = masky[rowOffSet+(maskSize-1)/2][colOffSet+(maskSize-1)/2];
//
//                int maskValue = (int)(Math.sqrt(Math.pow(maskValueX, 2) + Math.pow(maskValueY, 2)));
//                int pixelVal = im[r+rowOffSet][c+colOffSet];
//
//                output += maskValue*pixelVal;
//            }
//        }
//        return output;
//    }
//
//    private int getKernalWeight(int[][] mask) {
//        int sum = 0;
//        for (int i = 0; i < mask.length; i++) {
//            for (int j = 0; j < mask[i].length; j++) {
//                sum += mask[i][j];
//            }
//        }
//        if (sum <= 0) {
//            sum = 1;
//        }
//        return sum;
//    }
//
//}
//
//
