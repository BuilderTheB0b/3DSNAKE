package Logic;

import Panels.GamePanel;
import Panels.ConnectFourGameScreen;

import Panels.Frame;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * This class is used to control the client.
 * It communicates with the server with a TCP socket.
 * @author Elias Brännström & Annika Chen & Ivan Andersson
 */

public class Controller implements Runnable{
    private final GamePanel gui;
    private final ConnectFourGameScreen connectFourGameScreen;
    private final String host;
    private final int port;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean isRunning = true;
    private final Frame frame;

    private boolean isConnected;

    // Om tappad anslutning gå till startskärm

    /**
     * Constructor of Controller.
     * Start GUI with 8x8.
     * @author Elias Brännström & Annika Chen
     */
    public Controller(String host, int port){
        this.host = host;
        this.port = port;
        frame = new Frame(this);
        gui = frame.getGameGUI();
        connectFourGameScreen = frame.getConnectFourGameScreen();
        System.out.println("gui skapat");

    }

    /**
     * This method starts a socket in port 6002 (the same as the server).
     * It then starts to read from the stream and get different values.
     * If -1: the game is over and switch back to SnakeMenu.
     * If -2: getHSList() is called to store the HS-list.
     * If -5: setting panel values for snake.
     * If -6: setting panel values for connect four.
     * If -7: Game over for connect four
     * When IO exception is caught; the connection reconnects.
     * @author Elias Brännström & Annika Chen & Ivan Andersson
     */
    @Override
    public void run() {
        startSocket();

        while(isRunning) {
            try {
                if(in == null) break;
                int command = in.readInt();
                switch (command){
                    case -1:
                        System.out.println("The end!");
                        frame.setCurrentPanel(1);
                        gui.clearScreen();
                        break;
                    case -2:
                        getHSList();
                        break;
                    case -5:
                        int x = in.readInt();
                        int y = in.readInt();
                        int type = in.readInt();
                        gui.setPanelValues(x,y,type);
                        break;
                    case -6:
                        try {
                            int row = in.readInt();
                            int col = in.readInt();
                            int color = in.readInt();

                            System.out.printf("[Controller] Updating CONNECT FOUR cell at (%d, %d) with color %d%n", row, col, color);
                            connectFourGameScreen.updateCell(col, row, color);

                        } catch (IOException | ArrayIndexOutOfBoundsException ex) {
                            System.err.println("[Controller] Invalid Connect Four update:");
                            ex.printStackTrace();
                        }
                        break;

                    case -7:
                        System.out.println("GAME OVER!!");
                        frame.setCurrentPanel(3);
                        gui.clearScreen();
                        break;
                    default:
                        System.out.println("Okänt kommando togs emot från server: " + command);
                        System.out.println("i default på run metoden i conrtoller: testar med branch när man gjort en ändring");
                }

            } catch (IOException ioe) {
                closeConnection();
                startSocket();
            }
        }
    }

    /**
     * This method starts a socket on the host and port.
     * If IO exception get caught isConnected sets as false
     *  to stop receive from server and the panel sets to mainMenu.
     * @author Elias Brännström & Annika Chen
     */
    public void startSocket() {

        while(socket == null || socket.isClosed()){
            System.out.println("startar socket");
            try {
                Thread.sleep(100);
                socket = new Socket(host, port);  // Skapa en socketanslutning
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                isConnected = true;
                System.out.println("Connected to server");
            } catch (IOException ioe) {
                System.out.println("1. " + ioe.getMessage());
                isConnected = false;

                if(frame.getCurrentPanel() == frame.getGamePanel()) {
                    frame.setCurrentPanel(0);
                    gui.clearScreen();
                    JOptionPane.showMessageDialog(null,"Disconnected to server.");
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * This method close the connection between server and client.
     * @author Elias Brännström & Annika Chen
     */
    public void closeConnection(){
        while (socket != null) {
            try {
                out.close();
                in.close();
                socket.close();
                socket = null;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Sending commando to server.
     * If commando is 201: players name will be sent.
     * @param command The command
     * @return Boolean if sending correct.
     * @author Elias Brännström & Annika Chen
     */
    public boolean sendCommand(int command){
        try{
            if(out != null) {
                out.writeInt(command);
                out.flush();

                if(command == 201)sendPlayerNameToServer();

                return true;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            closeConnection();
            startSocket();
        }
        return false;
    }
    /**
     * Sends commands to the server coresponding to the difficulty level
     * @param difficultyLevel The level of difficulty the player has chosen
     * @return  Boolean if sending correct
     * @author Ivan Andersson
     */
    public boolean sendConnectFourStart(int difficultyLevel)
    {
        try
        {
            if (out != null)
            {
                switch (difficultyLevel)
                {
                    case 0: out.writeInt(204); break;
                    case 1: out.writeInt(205); break;
                    case 2: out.writeInt(206); break;
                    default: throw new IllegalArgumentException("Invalid difficulty level");
                }
                out.flush();
                return true;
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            closeConnection();
            startSocket();
        }
        return false;
    }

    /**
     * Metoden hämtar hs listan från servern.
     * Läser in längden på listan och sedan namnet och score.
     * @throws IOException Throws to getServerInput() in case -2.
     * @author Elias Brännström & Annika Chen
     */
    public void getHSList() throws IOException{

        int lengthOfHSList = in.readInt();
        String[] highScores = new String[lengthOfHSList];
        int counter = 0;

        for(int i = 0; i < lengthOfHSList; i++) {
            String name = in.readUTF();
            int score = in.readInt();
            counter++;

            highScores[i] = String.format("%-4s%-20s%5d",i+1 + ".",name, score);
            System.out.println(highScores[i]);
        }
        try {
            if (new InputStreamReader(in).ready()) {
                frame.setCurrentScore(in.readInt());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        String[] test = new String[counter];
        for (int i = 0; i < counter; i++) {
            test[i] = highScores[i];
        }
        frame.setHSData(test);

    }

    /**
     * This method sends players name to server when starting the game.
     * @author Elias Brännström & Annika Chen
     */
    public void sendPlayerNameToServer() {
        try {
            out.writeUTF(frame.getCurrentPlayerName());
            out.flush();
        } catch (IOException ioe) {
            System.out.println("ioe caught: " + ioe.getMessage());
        }
    }

    /**
     * Sends the direction to the server.
     * Is only temporarily because the direction is sent by the Arduino.
     * @param direction The direction.
     * @author Elias Brännström
     */
    public void sendKeyStroke(int direction){
        try {
            out.writeInt(202);
            out.writeInt(direction);
            out.flush();
        } catch (IOException ioe) {
            System.out.println("ioe caught: " + ioe.getMessage());
        }
    }

    /**
     * Getter of isConnected
     * @return Boolean isConnected.
     * @author Elias Brännström & Annika Chen
     */
    public boolean getIsConnected() {
        return isConnected;
    }

}
