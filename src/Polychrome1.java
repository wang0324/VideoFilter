import javax.swing.*;

public class Polychrome1 implements PixelFilter {
    private int colors;
    private short spectrum;
    public Polychrome1(){
        String input = JOptionPane.showInputDialog("Choose the number of colors");
        colors = Integer.parseInt(input);
        spectrum = (short)(255/(colors-1));
    }

    @Override
    public int[] filter(int[] pixels, int width, int height) {
        short[] bwpixels = PixelLib.convertToShortGreyscale(pixels);

        for (int i = 0; i < bwpixels.length; i++) {

            for(int j =0; j<=colors; j++){
                if (bwpixels[i] <= spectrum*j){
                    bwpixels[i]=(short)(spectrum*j);
                    break;
                }
            }
        }

        PixelLib.fill1dArray(bwpixels, pixels);
        return pixels;

    }

}
