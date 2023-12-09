import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class StartListener implements ActionListener {//ActionListener that listens for start button presses
    private final Connect4GUI window;//Store the current game window

    public StartListener(Connect4GUI window){//StartListener constructor
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e){//Override method starts new game
        try {
            window.newGame();
        }
        catch (FileNotFoundException except){
            Connect4.missingSave();
        }
    }
}