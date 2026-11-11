package Panels;

import javax.swing.*;
import Logic.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static Logic.Resources.*;

/**
 * This class is the panel of the gameScreen.
 * Contains gameGrid, scoreLabel, playerName etc.
 * @author Elias Brännström & Annika Chen
 */
public class SnakeGameScreen extends JPanel {
    private Frame frame;
    private final GamePanel gamePanel;
    private int score = 0;
    JLabel scoreLabel;
    JLabel playerName;
    JList<String> highScoreJList;
    JScrollPane highScoreScroll;
    private JPanel leftPanel;

    /**
     * The constructor of SnakeGameScreen.
     * Sets all the components up.
     * @param frame The Frame.
     * @author Elias Brännström & Annika Chen
     */
    public SnakeGameScreen(Frame frame){
        this.frame = frame;
        setBackground(Resources.darkGreen);
        setLayout(null);

        leftPanel = getLeftPanel();
        add(leftPanel);

        gamePanel = new GamePanel(8,8);
        gamePanel.setLocation(150,119);
        leftPanel.add(gamePanel);

        leftPanel.add(getSnakeLabel(150,10,yellow));
        leftPanel.add(getSnakeLabel(155,15,Color.BLACK));
        leftPanel.add(getBackButtonJPanel());

        JPanel leftInfoPanel = setUpLeftInfoPanel();
        leftInfoPanel.add(setUpScoreLabel());

        playerName = setUpPlayerLabel();
        leftInfoPanel.add(playerName);

        leftPanel.add(leftInfoPanel);
        highScoreJList = getHighScoreJList();

        //en highScore JList för att visas upp på right panel
        highScoreScroll = setUpHSView();
        add(highScoreScroll);
    }

    /**
     * Setter of currentPlayer.
     * @param name The name
     * @author Elias Brännström & Annika Chen
     */
    public void setCurrentPlayer(String name){
        playerName.setText("Name: " + name);
    }

    /**
     * Setter of score on scoreLabel.
     * If score is 60 the snake is at its maximum size.
     * @param score The score
     * @author Elias Brännström & Annika Chen
     */
    public void setScore(int score){
        scoreLabel.setText("Score: " + score);
        this.score = score;
        if(score >= 60){
            frame.setCurrentPanel(1);
        }
        revalidate();
        repaint();
    }

    /**
     * Setter of HS-list to update.
     * @param hSList The HS-list from the server
     * @author Elias Brännström & Annika Chen
     */
    public void setHighScoreListData(String[] hSList){
        highScoreJList.setListData(hSList);
        repaint();
    }

    /**
     * Creates the snakeLabel
     * @param x The x coordinate on the frame
     * @param y The y coordinate on the frame
     * @param color The color.
     * @return the snakeLabel
     * @author Elias Brännström
     */
    private JLabel getSnakeLabel(int x, int y, Color color){
        JLabel mainLabel = new JLabel("3D-SNAKE");
        mainLabel.setLocation(x,y);
        mainLabel.setSize(530,100);
        mainLabel.setFont(Resources.mainMenuFont);
        mainLabel.setForeground(color);
        return mainLabel;
    }

    /**
     * Getter of gamePanel.
     * @return The gamePanel.
     * @author Elias Brännström
     */
    public GamePanel getGamePanel(){
        return this.gamePanel;
    }

    /**
     * Creates the panel on the left.
     * @return The LeftPanel.
     * @author Elias Brännström
     */
    private JPanel getLeftPanel(){
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(green);
        leftPanel.setSize(812, 830+19+19);
        leftPanel.setLocation(0, 0);
        leftPanel.setLayout(null);
        leftPanel.setBorder(greenBorder);
        leftPanel.setBorder(blackBorder);

        return leftPanel;
    }
    /**
     * Creates the HS-JList
     * @return JList<String> of HS
     * @author Elias Brännström
     */
    private JList<String> getHighScoreJList() {

        JList<String> highScoreList = new JList<>();

        highScoreList.setSize(688,830);
        highScoreList.setLocation(831,19);
        highScoreList.setFont(hsFont);
        highScoreList.setBackground(Color.lightGray);
        highScoreList.setForeground(Color.black);
        highScoreList.setBorder(blackBorder);
        highScoreList.setEnabled(true);

        return highScoreList;
    }

    /**
     * Setup for playerName-label
     * @return The PlayerName as JLabel
     * @author Elias Brännström
     */
    private JLabel setUpPlayerLabel() {
        JLabel playerName = new JLabel("Name: Unknown");
        playerName.setSize(600,100);
        playerName.setFont(buttonFont);
        playerName.setLocation(20,50);
        playerName.setForeground(yellow);
        return playerName;
    }

    /**
     * Setup of score label
     * @return The score as JLabel
     * @author Elias Brännström
     */
    private JLabel setUpScoreLabel() {
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setSize(500,100);
        scoreLabel.setFont(buttonFont);
        scoreLabel.setLocation(20,0);
        scoreLabel.setForeground(yellow);
        return scoreLabel;
    }

    /**
     * Setup of the panel on the left.
     * @return The LeftPanel
     * @author Elias Brännström
     */
    private JPanel setUpLeftInfoPanel() {
        JPanel leftInfoPanel = new JPanel(null);
        leftInfoPanel.setBorder(blackBorder);
        leftInfoPanel.setBackground(darkGreen);
        leftInfoPanel.setSize(512,130);
        leftInfoPanel.setLocation(150,640);
        return leftInfoPanel;
    }

    /**
     * Setup of HS-view
     * @return JScrollPane with hs
     * @author Elias Brännström
     */
    private JScrollPane setUpHSView() {

        JScrollPane highScoreScroll = new JScrollPane();

        highScoreScroll.add(highScoreJList);
        highScoreScroll.setViewportView(highScoreJList);
        highScoreJList.setLayoutOrientation(JList.VERTICAL);
        highScoreScroll.setSize(688,830);
        highScoreScroll.setLocation(831,19);
        highScoreScroll.setBorder(blackBorder);
        return highScoreScroll;
    }

    /**
     * Creates the backButton.
     * The MouseListener is implemented.
     * When mouseClicked the panel changes to startFrame.
     * When entering: the panel gets a yellow border.
     * When exiting: it changes back to green.
     * @return The BackButton as JPanel.
     * @author Elias Brännström
     */
    private JPanel getBackButtonJPanel() {
        JLabel backButtonText = new JLabel("AVSLUTA");
        backButtonText.setFont(buttonFont);
        JPanel backButton = new JPanel();
        backButton.add(backButtonText);

        backButton.setToolTipText("du backar");
        backButton.setSize(512,60);
        backButton.setLocation(150,780);
        backButton.setBackground(red);
        backButton.setBorder(greenBorder);
        backButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                backButton.setBorder(greenBorder);
                frame.setCurrentPanel(1);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                backButton.setBorder(yellowBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backButton.setBorder(greenBorder);
            }
        });
        return backButton;
    }

}
