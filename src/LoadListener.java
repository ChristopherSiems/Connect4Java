import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class LoadListener implements ActionListener {//ActionListener that listens for game loads
    private final Connect4GUI window;//Stores the current window

    public LoadListener(Connect4GUI window){//Constructs the LoadListener
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {//Override method loads game saved
        try {
            window.loadGame();
        }
        catch(FileNotFoundException except){
            Connect4.missingSave();
        }
    }
}
