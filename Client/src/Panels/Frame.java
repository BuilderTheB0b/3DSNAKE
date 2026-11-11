package Panels;

import Logic.Controller;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Detta är fönstret för klienten och har/visar de olika panelerna.
 * @author Elias Brännström & Annika Chen & Ivan Andersson
 */
public class Frame extends JFrame {
    private Controller controller;
    private JPanel mainMenu;
    private SnakeMenu snakeMenu;
    private SnakeGameScreen snakeGameScreen;
    private JPanel currentPanel;
    private FourInARowDifficultyMenu fourInARowDifficultyMenu;
    private ConnectFourGameScreen connectFourGameScreen;

    /**
     * Constructor of Frame.
     * Creates a menuPanel with the different components.
     * @author Elias Brännström & Annika Chen & Ivan Andersson
     */
    public Frame(Controller controller) {
        this.controller = controller;

        mainMenu = new MainMenu(this);
        snakeMenu = new SnakeMenu(this);
        fourInARowDifficultyMenu = new FourInARowDifficultyMenu(this);
        connectFourGameScreen = new ConnectFourGameScreen(this);
        snakeGameScreen = new SnakeGameScreen(this);
        addKeyListener(new MyKeyAdapter(controller));

        currentPanel = mainMenu;
        add(currentPanel);

        setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    /**
     * This method is used to set what panel that should be shown.
     * @param i The panel corresponds to an int.
     * @author Elias Brännström & Annika Chen & Ivan Andersson
     */
    public void setCurrentPanel(int i){
        remove(currentPanel);
        switch(i){
            case 0: currentPanel = mainMenu;
                break;
            case 1:
                if(currentPanel == mainMenu){
                    snakeMenu.setCurrentPlayerDefault();
                }
                if(currentPanel == snakeGameScreen){
                    controller.sendCommand(404); // send end game
                }
                currentPanel = snakeMenu;
                if(controller.getIsConnected()) {
                    controller.sendCommand(200);
                }
                break;
            case 2:
                if(controller.sendCommand(201)){
                    currentPanel = snakeGameScreen;
                    System.out.println("startCommand");
                }
                break;
            case 3:
                System.out.println("3");
                if(currentPanel == mainMenu);
                {
                    currentPanel = fourInARowDifficultyMenu;
                }
                break;

            case 4:
                if (controller.sendConnectFourStart(0)) {
                    currentPanel = connectFourGameScreen;
                    connectFourGameScreen.clearBoard();
                    System.out.println("Started Connect Four Easy");
                }
                break;
            case 5:
                if (controller.sendConnectFourStart(1)) {
                    currentPanel = connectFourGameScreen;
                    connectFourGameScreen.clearBoard();
                    System.out.println("Started Connect Four Medium");
                }
                break;
            case 6:
                if (controller.sendConnectFourStart(2)) {
                    currentPanel = connectFourGameScreen;
                    connectFourGameScreen.clearBoard();
                    System.out.println("Started Connect Four Hard");
                }
                break;
        }
        snakeGameScreen.setScore(0);
        this.add(currentPanel);
        repaint();
        revalidate();
    }

    /**
     * Getter of GameGUI.
     * Is used because the Controller need to control it.
     * @return The GamePanel
     * @author Elias Brännström
     */
    public GamePanel getGameGUI(){
        return snakeGameScreen.getGamePanel();
    }

    /**
     * Getter of ConnectFourGameScreen.
     * Is used because the Controller need to control it.
     * @return The ConnectFourGameScreen
     * @author Ivan Andersson
     */
    public ConnectFourGameScreen getConnectFourGameScreen()
    {
        return connectFourGameScreen;
    }

    /**
     * Getter of CurrentPanel.
     * Is used in Controller to only switch panel if currentPanel is gamePanel.
     * @return The current panel.
     * @author Annika Chen
     */
    public JPanel getCurrentPanel() {
        return currentPanel;
    }

    /**
     * Getter of GamePanel.
     * Is used in Controller to compare currentPanel.
     * @return The GamePanel.
     * @author Annika Chen
     */
    public JPanel getGamePanel() {
        return snakeGameScreen;
    }

    /**
     * Getter of currentPlayers name.
     * Get it from the snakeGameScreen with the JTextField.
     * @return The name.
     * @author Elias Brännström & Annika Chen
     */
    public String getCurrentPlayerName() {
        String name = snakeMenu.getCurrentPlayerName();
        snakeGameScreen.setCurrentPlayer(name);
        return snakeMenu.getCurrentPlayerName();
    }

    /**
     * Setter of HS list to update the HS in the gameScreen.
     * @param hsData The array of HS-data.
     * @author Elias Brännström & Annika Chen
     */
    public void setHSData(String[] hsData){
        snakeMenu.setHighScoreListData(hsData);
        snakeGameScreen.setHighScoreListData(hsData);
    }

    /**
     * Setter of current score.
     * This method is called in controller when server sends a new score.
     * @param score The score.
     * @author Elias Brännström
     */
    public void setCurrentScore(int score){
        snakeGameScreen.setScore(score);
    }


    /**
     * This class is used to be able to play with the keyboard.
     * It extends KeyAdapter.
     * This class and the code are only temporarily.
     * @author Elias Brännström
     */
    public static class MyKeyAdapter extends KeyAdapter {
        Controller controller;

        /**
         * Constructor of MyKeyAdapter
         * @param controller The controller; to send the direction.
         * @author Elias Brännström
         */
        public MyKeyAdapter(Controller controller){
            this.controller = controller;
        }

        /**
         * This method is used when a key is pressed and then
         * calls the method that sends the direction in the controller
         * @param e The event to be processed
         * @author Elias Brännström
         */
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    controller.sendKeyStroke(4);
                    break;
                case KeyEvent.VK_RIGHT:
                    controller.sendKeyStroke(2);
                    break;
                case KeyEvent.VK_UP:
                    controller.sendKeyStroke(1);
                    break;
                case KeyEvent.VK_DOWN:
                    controller.sendKeyStroke(3);
                    break;
            }
        }
    }
}
