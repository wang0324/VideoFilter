public class DarkenFilter implements PixelFilter {
    @Override
    public int[] filter(int[] pixels, int width, int height) {
        short[] bwpixels = PixelLib.convertToShortGreyscale(pixels);

        for (int i = 0; i < bwpixels.length; i++) {
            bwpixels[i] = (short) (bwpixels[i] / 2);
        }

        PixelLib.fill1dArray(bwpixels, pixels);
        return pixels;

    }

}