import javax.swing.*;
import java.awt.*;

public class BoardGUI extends JPanel{//BoardGUI draws graphics for the game board
    private final Connect4Logic game;//Stores the current game

    public BoardGUI(Connect4Logic game){//Constructor for BoardGUI
        super();
        this.game = game;
        Connect4GUI.size(this, new Dimension(770, 660));
    }

    @Override
    public void paint(Graphics g){//Override method for JPanel.paint() that draws graphics
        Graphics2D g2D = (Graphics2D) g;
        g2D.setStroke(new BasicStroke(3));
        g2D.setColor(new Color(238, 238, 238));
        g2D.fillRect(0, 0, 770, 700);
        for (int column = 0; column <= 6; column++){
            for (int space = 0; space <= 5; space++){
                if (game.board[column][space] == 1) {
                    g2D.setColor(Color.RED);
                    g2D.fillOval(2 + (108 * column), 555 - (110 * space), 100, 100);
                } else if (game.board[column][space] == 2) {
                    g2D.setColor(Color.BLUE);
                    g2D.fillOval(2 + (108 * column), 555 - (110 * space), 100, 100);
                }
                else{
                    g2D.setColor(Color.BLACK);
                    g2D.drawOval(2 + (108 * column), 555 - (110 * space), 100, 100);
                }
            }
        }
    }
}