package za.wtc.swingy.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * ParseImages
 */
public class ParseImages {

    public static BufferedImage loadImage(String path) {
        BufferedImage myPicture = null;

        try {
            myPicture = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.exit(0);
        }
        return (myPicture);
    }
}