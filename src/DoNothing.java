public class DoNothing implements PixelFilter{
    @Override
    public int[] filter(int[] pixels, int width, int height){
        //don't change the input array at all
        return  pixels;
    }

}