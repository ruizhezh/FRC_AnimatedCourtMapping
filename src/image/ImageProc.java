package image;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class ImageProc {
	
	public static BufferedImage rotateImage(final BufferedImage bufferedimage, final int degree) {  
        BufferedImage tempImg;  
        Graphics2D graphics2d;  
        (graphics2d = (tempImg = new BufferedImage(bufferedimage.getWidth(),bufferedimage.getHeight(), bufferedimage.getColorModel().getTransparency()))
        		.createGraphics()).setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
        graphics2d.rotate(Math.toRadians(degree),bufferedimage.getWidth()/2,bufferedimage.getHeight()/2);  
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();  
        return tempImg;  
    }
	
	
}
