import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectionListener implements ActionListener {//ActionListener that listens for the column selecting button presses
    private final int column;//Stores the column of the button
    private final Connect4GUI window;//Stores the window in use

    public SelectionListener(int column, Connect4GUI window){//SelectionListener constructor
        this.column = column;
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {//Calls the game's move method
        window.game.move(column, window);
    }
}