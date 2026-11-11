package Panels;

import Logic.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static Logic.Resources.*;

/**
 * This class is the panel of the snake menu.
 * @author Elias Brännström & Annika Chen
 */
public class SnakeMenu extends JPanel {
    private final Frame frame;
    private JTextField nameInput;
    private String currentPlayerName;

    private JList<String> highScoreJList;
    private JLabel mainLabel;

    /**
     * Constructor of MenuPanel. highscore
     * Calls setup() to set all the components up.
     * @author Elias Brännström & Annika Chen
     */
    public SnakeMenu(Frame frame) {
        this.frame = frame;
        setLayout(null);
        setBackground(darkGreen);

        setup();
    }

    /**
     * This method is the setup method.
     * mainLabel, startImage, gameInfo, backButton, startButton, nameInput etc.
     * Each component calls it owns setup. This is done to sort the method.
     * @author Elias Brännström & Annika Chen
     */
    private void setup() {

        //leftPanel, metodanrop till setup.
        JPanel leftPanel = getLeftPanel();
        mainLabel = getSnakeLabel(150,10,yellow);
        leftPanel.add(mainLabel);
        JLabel mainLabelShadow = getSnakeLabel(155, 15, Color.BLACK);
        leftPanel.add(mainLabelShadow);
        add(leftPanel);

        //bilden på spelet, placerat på leftPanel
        JLabel startImage = getStartImage();
        leftPanel.add(startImage);
        //text om spelinfo, placerad under bilden
        JTextArea gameInfo = getJTextArea();
        leftPanel.add(gameInfo);

        JPanel rightPanel = getRightPanel();

        highScoreJList = getHighScoreJList();
        JScrollPane highScoreScroll = setUpHSView();
        //add(highScoreScroll);

        //tillbaka knapp, placerad under highScore listan
        JPanel backButton = getBackButtonJPanel();
        //add(backButton);
        //start knapp, placerad bredvid backButton
        JPanel startButton = getStartButtonJPanel();
        //add(startButton);

        nameInput = getNameInput();
        rightPanel.add(nameInput);

        //add(NameInput());
        rightPanel.add(highScoreScroll);
        rightPanel.add(backButton);
        rightPanel.add(startButton);
        add(rightPanel);


        repaint();
        revalidate();
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
        JLabel label = new JLabel("3D-SNAKE");
        label.setLocation(x,y);
        label.setSize(530,100);
        label.setFont(Resources.mainMenuFont);
        label.setForeground(color);
        return label;
    }

    /**
     * Creates the panel on the left.
     * @return The LeftPanel.
     * @author Elias Brännström & Annika Chen
     */
    private JPanel getLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(green);
        leftPanel.setSize(812, 1080);
        leftPanel.setLocation(0, 0);
        leftPanel.setLayout(null);
        leftPanel.setBorder(greenBorder);
        leftPanel.setBorder(blackBorder);
        return leftPanel;
    }

    /**
     * Creates the panel on the right.
     * @return The RightPanel
     * @author Elias Brännström & Annika Chen
     */
    private JPanel getRightPanel(){
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(green);
        rightPanel.setSize(688, 830);
        rightPanel.setLocation(831, 19);
        rightPanel.setLayout(null);
        rightPanel.setBorder(greenBorder);
        rightPanel.setBorder(blackBorder);

        return rightPanel;
    }

    /**
     * Creates the start image of the snake.
     * @return The startImage as JLabel
     * @author Elias Brännström & Annika Chen
     */
    private JLabel getStartImage() {
        JLabel startImage = new JLabel(new ImageIcon(getCoolerSnakeImage()));
        startImage.setLocation(150, 119);
        startImage.setSize(512, 512);
        startImage.setBorder(blackBorder);
        return startImage;
    }

    /**
     * Creates the textArea with description and rules of the game.
     * @return The TextArea.
     * @author Elias Brännström & Annika Chen
     */
    private JTextArea getJTextArea() {
        JTextArea testtext = new JTextArea("Snake is a genre of action video games where the player maneuvers the end of a growing line," +
                " often themed as a snake. The player must keep the snake from colliding with both other obstacles and itself, which gets harder" +
                " as the snake lengthens.", 5,1);
        testtext.setLineWrap(true);
        testtext.setEditable(false);
        testtext.setBackground(green);
        testtext.setForeground(Color.black);
        testtext.setSize(774, 180);
        testtext.setLocation(19, 680);
        testtext.setFont(infoTextFont);
        return testtext;
    }

    /**
     * Creates the HS scroll-view.
     * @return The JScrollPane of HS.
     * @author Elias Brännström & Annika Chen
     */
    private JScrollPane setUpHSView() {

        JScrollPane highScoreScroll = new JScrollPane();

        highScoreScroll.add(highScoreJList);
        highScoreScroll.setViewportView(highScoreJList);
        highScoreJList.setLayoutOrientation(JList.VERTICAL);
        highScoreScroll.setSize(650,512+122);
        highScoreScroll.setLocation(735-716,39-20);
        highScoreScroll.setBorder(blackBorder);
        return highScoreScroll;
    }

    /**
     * Creates the HS-JList
     * @return JList<String> of HS
     * @author Elias Brännström & Annika Chen
     */
    private JList<String> getHighScoreJList() {

        JList<String> highScoreList = new JList<>();

        highScoreList.setSize(650,512+122);
        highScoreList.setLocation(735-716,39-20);
        highScoreList.setFont(hsFont);
        highScoreList.setBackground(Color.lightGray);
        highScoreList.setForeground(Color.black);
        //highScoreList.setBorder(blackBorder);
        highScoreList.setEnabled(true);

        return highScoreList;
    }

    /**
     * Creates the backButton.
     * The MouseListener is implemented.
     * When mouseClicked the panel changes to startFrame.
     * When entering: the panel gets a yellow border.
     * When exiting: it changes back to green.
     * @return The BackButton as JPanel.
     * @author Elias Brännström & Annika Chen
     */
    private JPanel getBackButtonJPanel() {
        JLabel backButtonText = new JLabel("TILLBAKA");
        backButtonText.setFont(buttonFont);
        JPanel backButton = new JPanel();
        backButton.add(backButtonText);

        backButton.setToolTipText("du backar");
        backButton.setSize(300,60);
        backButton.setLocation(735-716,750);
        backButton.setBackground(red);
        backButton.setBorder(greenBorder);
        backButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                backButton.setBorder(greenBorder);
                frame.setCurrentPanel(0);
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
    /**
     * Creates the StartButton.
     * The MouseListener is implemented.
     * When mouseClicked the panel changes to startFrame.
     * When entering: the panel gets a yellow border.
     * When exiting: it changes back to green.
     * @return The StartButton as JPanel.
     * @author Elias Brännström & Annika Chen
     */
    private JPanel getStartButtonJPanel() {
        JLabel startButtonText = new JLabel("STARTA");
        startButtonText.setFont(buttonFont);
        JPanel startButton = new JPanel();
        startButton.add(startButtonText);

        startButton.setToolTipText("du startar");
        startButton.setSize(300,60);
        startButton.setLocation(1085-716,750);
        startButton.setBackground(green);
        startButton.setBorder(greenBorder);
        startButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startButton.setBorder(greenBorder);
                setCurrentPlayerName(nameInput.getText());
                frame.setCurrentPanel(2);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                startButton.setBorder(yellowBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startButton.setBorder(greenBorder);
            }
        });
        return startButton;
    }

    /**
     * Creates a JTextField for name-input.
     * @return The nameInput as JTextField.
     * @author Elias Brännström & Annika Chen
     */
    public JTextField getNameInput() {
        JTextField nameInput = new JTextField("player");
        nameInput.setFont(buttonFont);
        nameInput.setSize(650,60);
        nameInput.setLocation(735-716,600-50+122);
        nameInput.setBackground(Color.lightGray);
        nameInput.setBorder(blackBorder);
        return nameInput;
    }

    /**
     * This method is used to set the text and
     * the attribute currentPlayerName to player.
     * @author Elias Brännström & Annika Chen
     */
    public void setCurrentPlayerDefault(){
        currentPlayerName = "player";
        nameInput.setText("player");
    }

    /**
     * Getter of currentPlayerName.
     * @return The name
     * @author Elias Brännström & Annika Chen
     */
    public String getCurrentPlayerName() {
        return currentPlayerName;
    }

    /**
     * Setter of currentPlayerName.
     * If the length is greater than 20, the name sets to only 17 characters
     * If it's bland, the name sets to Unknown.
     * @param name The name from the JTextField.
     * @author Annika Chen
     */
    public void setCurrentPlayerName(String name) {
        if(name.length() > 20) {
            currentPlayerName = String.format("%.17s...",name);
            return;
        }
        if(currentPlayerName.isBlank()) {
            currentPlayerName = "Unknown";
            return;
        }
        currentPlayerName = name;
    }

    /**
     * Setters of HS list on the scrollbar.
     * @param hSList The HS-list.
     * @author Elias Brännström & Annika Chen
     */
    public void setHighScoreListData(String[] hSList){
        highScoreJList.setListData(hSList);
        repaint();
    }

}
