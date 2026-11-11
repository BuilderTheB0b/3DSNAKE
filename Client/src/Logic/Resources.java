package Logic;

import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Klass som innehåller diverse färger, kantlinjer och bilder.
 * Alla är static, alltså behövs ingen instans av klassen för att komma åt en bild eller färg
 * @author Elias Brännström & Annika Chen & Ivan Andersson
 */
public class Resources {
    /**
     * Färger
     */
    public static final Color brown = new Color(92, 57, 31);
    public static final Color red = new Color(203, 44, 44);
    public static final Color lightGreen = new Color(60, 143, 101);
    public static final Color green = new Color(34, 96, 58);
    public static final Color darkGreen = new Color(20, 65, 31);
    public static final Color lightBlue = new Color(153, 198, 204);
    public static final Color yellow = new Color(255, 236, 112);
    public static final Color beige = new Color(255, 207, 142);
    public static final Color transparentBlack = new Color(0, 0, 0,75);

    /**
     * Kanter
     */
    public static final LineBorder greenBorder = new LineBorder(darkGreen, 3);
    public static final LineBorder lightBlueBorder = new LineBorder(lightBlue,5);
    public static final LineBorder brownBorder = new LineBorder(brown,5);
    public static final LineBorder redBorder = new LineBorder(red, 5);
    public static final LineBorder yellowBorder = new LineBorder(yellow,5);
    public static final LineBorder blackBorder = new LineBorder(Color.BLACK,2);
    /**
     * Fonter
     */
    public static final Font mainMenuFont = new Font("Arial", Font.BOLD,100);
    public static final Font buttonFont = new Font("Arial", Font.BOLD,40);
    public static final Font hsFont = new Font("monospaced", Font.BOLD, 34);
    public static final Font infoTextFont = new Font("Algerian", Font.PLAIN,25);
    /**
     * Bilder
     * @return bild
     */
    public static BufferedImage getAppleImage() {
        return getBufferedImage("images/apple_3dsnake.png");
    }
    public static BufferedImage getSnakeStartImage() {
        return getBufferedImage("images/snake_start.png");
    }
    public static BufferedImage getCoolerSnakeImage(){return getBufferedImage("images/snek.jpg");}
    public static BufferedImage getFourInARowImage(){return getBufferedImage("images/4InARow_with_border.jpg");}
    public static BufferedImage getEasyImage(){return getBufferedImage("images/SmileyHappy.jpg");}
    public static BufferedImage getMediumImage(){return getBufferedImage("images/SmileyNeutral.jpg");}
    public static BufferedImage getHardImage(){return getBufferedImage("images/SmileyAngry.jpg");}


    /**
     * Denna metod används för att skapa en BufferedImage av jpg:n.
     * @param name The path of the image.
     * @return The BufferedImage
     */
    private static BufferedImage getBufferedImage(String name) {
        try {
            return ImageIO.read(new File(name));
        } catch (IOException ioe) {
            System.out.println("Image wrong: " + ioe.getMessage());
            return null;
        }
    }

}
