package utilities;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class FileLoader {

    //loads an image from given filename and returns it as a BufferedImage
    public static BufferedImage loadImage(String fileName) {
        BufferedImage buffimg;

        try {
            buffimg = ImageIO.read(new FileLoader().getClass().getResourceAsStream(fileName));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was an error loading your image.");
            System.err.println("Error in FileLoader: BufferedImage(String)");
            buffimg = null;
        }

        return buffimg;
    }
}
