public class VerticalReflectFilter implements PixelFilter {
    @Override
    public int[] filter(int[] pixels, int width, int height) {

        short[] bwpixels = PixelLib.convertToShortGreyscale(pixels); //convert to bw array
        short[][] img = PixelLib.convertTo2dArray(bwpixels, width, height); // convert to 2d array

        int lastRowIndex = img.length - 1;
        for (int r = 0; r < img.length/2; r++){
            for (int c = 0; c < img[r].length; c++){
                short temp = img[r][c];
                img[r][c] = img[lastRowIndex-r][c];
                img[lastRowIndex-r][c] = temp;
            }
        }
        PixelLib.fill1dArray(img,pixels);
        return pixels;
    }
}