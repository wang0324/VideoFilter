public class Monochrome implements PixelFilter {

    @Override
    public int[] filter(int[] pixels, int width, int height) {
        short[] bwpixels = PixelLib.convertToShortGreyscale(pixels);

        for (int i = 0; i < bwpixels.length; i++) {
            if (bwpixels[i] < (short) (127)) {
                bwpixels[i] = 0;
            } else {
                bwpixels[i] = 255;
            }
        }

        PixelLib.fill1dArray(bwpixels, pixels);
        return pixels;

    }

}
