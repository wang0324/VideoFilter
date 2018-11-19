public class PixelateSoft implements PixelFilter {

    public final int blockSize = 3;
    private short[][] img;
    public int[] filter(int[] pixels, int width, int height) {


        short[] bwpixels = PixelLib.convertToShortGreyscale(pixels); //convert to bw array
        img = PixelLib.convertTo2dArray(bwpixels, width, height); // convert to 2d array

        int lastRowIndex = img.length - 1; //set max row length

        for (int r = 0; r < img.length - blockSize + 1; r += blockSize) {
            for (int c = 0; c < img[r].length - blockSize + 1; c += blockSize) {
                int num = getAverage(r,c);
                fillImg(num, r, c);
            }
        }

        PixelLib.fill1dArray(img, pixels); //convert back to 1d array
        return pixels; // return statement
    }

    private void fillImg(int num, int r, int c) {
        for (int i = r; i < r + blockSize; i++) {
            for (int j = c; j < c + blockSize; j++) {
                img[i][j] = (short)(num);
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