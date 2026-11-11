package Panels;
import Logic.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static Logic.Resources.*;

/**
 * This class is the menu where a player chooses the difficulty they want to play
 * @author Ivan Andersson
 */
public class FourInARowDifficultyMenu extends JPanel
{
    private final JLabel easyButton;
    private final JLabel mediumButton;
    private final JLabel hardButton;
    private final Frame mainFrame;

    /**
     * The constructor which sets up every button on the menu screen
     * @param mainFrame
     * @author Ivan Andersson
     */
    public FourInARowDifficultyMenu(Frame mainFrame)
    {
        this.mainFrame = mainFrame;
        setLayout(null);
        setVisible(true);
        setBackground(Resources.darkGreen);

        String title = "DIFFICULTIES";
        JLabel mainLabel = new JLabel(title);
        mainLabel.setLocation(480,20);
        mainLabel.setSize(300,75);
        mainLabel.setFont(Resources.mainMenuFont);
        mainLabel.setForeground(Resources.yellow);
        add(mainLabel);

        JLabel mainLabelShadow = new JLabel(title);
        mainLabelShadow.setLocation(485,25);
        mainLabelShadow.setSize(560,100);
        mainLabelShadow.setFont(Resources.mainMenuFont);
        mainLabelShadow.setForeground(Color.BLACK);
        add(mainLabelShadow);

        easyButton = new JLabel(new ImageIcon(Resources.getEasyImage()));
        easyButton.setLocation(180,300);
        easyButton.setSize(275,275);
        easyButton.setBorder(Resources.greenBorder);
        add(easyButton);

        mediumButton = new JLabel(new ImageIcon(Resources.getMediumImage()));
        mediumButton.setLocation(583, 300);
        mediumButton.setSize(275, 275);
        mediumButton.setBorder(Resources.greenBorder);
        add(mediumButton);

        hardButton = new JLabel(new ImageIcon(Resources.getHardImage()));
        hardButton.setLocation(986, 300);
        hardButton.setSize(275, 275);
        hardButton.setBorder(Resources.greenBorder);
        add(hardButton);

        JPanel tillbakaButton = getTillbakaButtonJPanel();
        add(tillbakaButton);

        addAL();
    }

    /**
     * An actionlistener which is responsible for what happens when your mouse interacts with the buttons
     * @author Ivan Andersson
     */
    private void addAL()
    {
        easyButton.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                easyButton.setBorder(Resources.greenBorder);
                mainFrame.setCurrentPanel(4);
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e){};

            @Override
            public void mouseEntered(MouseEvent e) {
                easyButton.setBorder(Resources.yellowBorder);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                easyButton.setBorder(Resources.greenBorder);
            }
        });

        mediumButton.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                mediumButton.setBorder(Resources.greenBorder);
                mainFrame.setCurrentPanel(5);
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e){};

            @Override
            public void mouseEntered(MouseEvent e) {
                mediumButton.setBorder(Resources.yellowBorder);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                mediumButton.setBorder(Resources.greenBorder);
            }
        });

        hardButton.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                hardButton.setBorder(Resources.greenBorder);
                mainFrame.setCurrentPanel(6);
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e){};

            @Override
            public void mouseEntered(MouseEvent e) {
                hardButton.setBorder(Resources.yellowBorder);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                hardButton.setBorder(Resources.greenBorder);
            }
        });
    }

    /**
     * Handles everything regarding the back button that takes you back to the main menu
     * @author Ivan Andersson
     */
    private JPanel getTillbakaButtonJPanel()
    {
        JLabel tillbakaButtonText = new JLabel("TILLBAKA");
        tillbakaButtonText.setFont(buttonFont);
        JPanel tillbakaButton = new JPanel();
        tillbakaButton.add(tillbakaButtonText);

        tillbakaButton.setSize(300,60);
        tillbakaButton.setLocation(570,750);
        tillbakaButton.setBackground(red);
        tillbakaButton.setBorder(greenBorder);
        tillbakaButton.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                tillbakaButton.setBorder(greenBorder);
                mainFrame.setCurrentPanel(0);
            }

            @Override
            public void mousePressed(MouseEvent e)
            {

            }

            @Override
            public void mouseReleased(MouseEvent e)
            {

            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                tillbakaButton.setBorder(yellowBorder);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                tillbakaButton.setBorder(greenBorder);
            }
        });
        return tillbakaButton;
    }
}
