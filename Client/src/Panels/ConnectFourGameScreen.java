package Panels;

import javax.swing.*;
import java.awt.*;

import static Logic.Resources.*;

/**
 * This class is the panel of the game screen
 * Contains the game grid
 * @author Ivan Andersson
 */
public class ConnectFourGameScreen extends JPanel
{
    private Frame frame;
    private final JPanel[][] grid = new JPanel[8][8];
    private final JPanel gridPanel;

    /**
     * The constructor of the game screen
     * Sets up the grid and text
     * @param frame
     * @author Ivan Andersson
     */
    public ConnectFourGameScreen(Frame frame)
    {
        this.frame = frame;

        setLayout(null);
        setBackground(darkGreen);

        gridPanel = new JPanel(new GridLayout(8, 8, 2, 2));
        gridPanel.setSize(512, 512);
        gridPanel.setLocation(384, 134);
        gridPanel.setBackground(darkGreen);
        gridPanel.setBorder(blackBorder);

        for (int row = 0; row < 8; row++)
        {
            for (int col = 0; col < 8; col++)
            {
                JPanel cell = new JPanel();
                cell.setBackground(brown);
                cell.setBorder(blackBorder);
                grid[row][col] = cell;
                gridPanel.add(cell);
            }
        }

        JLabel title = new JLabel("CONNECT FOUR");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(yellow);
        title.setSize(400, 40);
        title.setLocation(440, 50);
        add(title);

        add(gridPanel);
        setVisible(true);
    }

    /**
     * This method updates the cells in the grid to show which player has placed their tile there
     * @param row which row the corresponding tile is on
     * @param col which column the corresponding tile is on
     * @param playerId a number representing which color to make the tile
     * @author Ivan Andersson
     */
    public void updateCell(int row, int col, int playerId)
    {
        if (row < 0 || row >= 8 || col < 0 || col >= 8) return;

        Color discColor = switch (playerId)
        {
            case 1 -> Color.RED;
            case 2 -> Color.YELLOW;
            default -> brown;
        };

        grid[row][col].setBackground(discColor);
        repaint();
    }

    /**
     * This method clears the board and sets every tile to the background color
     * @author Ivan Andersson
     */
    public void clearBoard()
    {
        for (int row = 0; row < 8; row++)
        {
            for (int col = 0; col < 8; col++)
            {
                grid[row][col].setBackground(brown);
            }
        }
        repaint();
    }
}
