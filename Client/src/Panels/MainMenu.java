package Panels;
import Logic.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This class is the panel of main menu
 * @author Elias Brännström & Annika Chen & Ivan Andersson
 */
public class MainMenu extends JPanel {
    private final JLabel snakeButton;
    private final JLabel fourInARowButton;
    private final Frame frame;

    /**
     * Constructor of MainMenu
     * Sets all the components up.
     * @param frame The frame.
     * @author Elias Brännström & Annika Chen & Ivan Andersson
     */
    public MainMenu(Frame frame){
        this.frame = frame;
        setLayout(null);
        setVisible(true);
        setBackground(Resources.darkGreen);

        String title = "3D-GAMES";
        JLabel mainLabel = new JLabel(title);
        mainLabel.setLocation(480,20);
        mainLabel.setSize(530,100);
        mainLabel.setFont(Resources.mainMenuFont);
        mainLabel.setForeground(Resources.yellow);
        add(mainLabel);

        JLabel mainLabelShadow = new JLabel(title);
        mainLabelShadow.setLocation(485,25);
        mainLabelShadow.setSize(560,100);
        mainLabelShadow.setFont(Resources.mainMenuFont);
        mainLabelShadow.setForeground(Color.BLACK);

        add(mainLabelShadow);

        snakeButton = new JLabel(new ImageIcon(Resources.getCoolerSnakeImage()));
        snakeButton.setLocation(100,170);
        snakeButton.setSize(512,512);
        snakeButton.setBorder(Resources.greenBorder);
        add(snakeButton);

        fourInARowButton = new JLabel(new ImageIcon(Resources.getFourInARowImage()));
        fourInARowButton.setLocation(800, 170);
        fourInARowButton.setSize(512, 512);
        fourInARowButton.setBorder(Resources.greenBorder);
        add(fourInARowButton);

        addAL();
    }

    /**
     * The MouseListener is implemented.
     * When mouseClicked the panel changes to snakeGameMenu or fourInRow.
     * When entering: the panel gets a yellow border.
     * When exiting: it changes back to green.
     * @author Elias Brännström & Ivan Andersson
     */
    private void addAL(){
        snakeButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                snakeButton.setBorder(Resources.greenBorder);
                frame.setCurrentPanel(1);
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e){}

            @Override
            public void mouseEntered(MouseEvent e) {
                snakeButton.setBorder(Resources.yellowBorder);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                snakeButton.setBorder(Resources.greenBorder);
            }
        });

        fourInARowButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fourInARowButton.setBorder(Resources.greenBorder);
                frame.setCurrentPanel(3);
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e){};

            @Override
            public void mouseEntered(MouseEvent e) {
                fourInARowButton.setBorder(Resources.yellowBorder);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                fourInARowButton.setBorder(Resources.greenBorder);
            }
        });
    }
}
