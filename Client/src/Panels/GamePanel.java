package Panels;
import Logic.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Detta är klassen för spelplanen (dvs grid där ormen och äpplet syns)
 * @author Elias Brännström & Annika Chen
 */
public class GamePanel extends JPanel {
    private final JPanel[][] panels;
    private int currentAppleCoorX;
    private int currentAppleCoorY;
    private final JLabel appleComponent;
    private int score;

    private final int height, width;

    /**
     * Constructor of GUI.
     * Create the panels/gridSquares
     * @param height The number of vertical gridSquare
     * @param width The number of horizontal gridSquare.
     * @author Elias Brännström & Annika Chen
     */
    public GamePanel(int height, int width){
        super();
        this.width = width;
        this.height = height;
        setLayout(new GridLayout(height,width));
        setLocation(700,0);


        panels = new JPanel[height][width];
        for(int i = 0; i < height; i++){
            for( int j =0; j < width; j++){
                panels[i][j] = new JPanel();
                panels[i][j].setBackground(Resources.brown);
                add(panels[i][j]);
            }

        }
        appleComponent = new JLabel(new ImageIcon(Resources.getAppleImage()));

            //behöver ligga längst ner för att allt ska synas direkt
        setSize(512,512);
        setBorder(Resources.blackBorder);
        setVisible(true);
    }

    /**
     * This method update the gridSquare on a specific coordinate
     * The parameters are from the stream from the server.
     * @param x The x coordinate of the gridSquare.
     * @param y The y coordinate of the gridSquare.
     * @param type The type. Clear = 0, Snake = 1, Apple = 2.
     * @author Elias Brännström & Annika Chen
     */
    public void setPanelValues(int x, int y, int type){

        switch (type){
            case 0: //ska "reset" rutan så att den blir "oanvänd",
                    // äpplet tas bort om det finns ett äpple
                if(x == currentAppleCoorX && y == currentAppleCoorY) {
                    removeApple(x, y);
                    score++;
                }else{
                    panels[y][x].setBackground(Resources.brown);
                    panels[y][x].setBorder(null);
                }
                repaint();
                break;
            case 1: //uppdaterar ormen med färgen blå
                panels[y][x].setBackground(Resources.lightGreen);
                panels[y][x].setBorder(new LineBorder(Color.BLACK, 2));
                break;
            case 2: //skapar ett äpple på den koordinaten
                panels[y][x].setBorder(null);
                createAppleOnPanel(x,y);
                revalidate();
                repaint();
                break;
        }
    }

    /**
     * This method place an Apple on the specific coordinate.
     * The appleComponent is a JLabel and is added to the squarePanel.
     * isEmpty sets to false, thus there is now an apple placed.
     * @param x The column
     * @param y The row
     * @author Elias Brännström & Annika Chen
     */
    public void createAppleOnPanel(int x, int y) {
        panels[y][x].add(appleComponent);
        currentAppleCoorX = x;
        currentAppleCoorY = y;
        repaint();
    }


    /**
     * This method removes the appleComponent on the specific squarePanel.
     * isEmpty is set to true, thus there is no apple anymore
     * @param x The column
     * @param y The row
     * @author Elias Brännström & Annika Chen
     */
    public void removeApple(int x, int y) {
        panels[y][x].remove(appleComponent);
        repaint();
    }

    /**
     * This method clears the screen when game is over
     * @author Elias Brännström & Annika Chen
     */
    public void clearScreen(){
        for(int x = 0; x < height; x++)
            for(int y = 0; y < width; y++){
                panels[x][y].setBorder(null);
                panels[x][y].setBackground(Resources.brown);
        }
        removeApple(currentAppleCoorX,currentAppleCoorY);
        score = 0;
    }

}
