import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Connect4GUI {//Class for the GUI of the game
    private static final Dimension dimension1 = new Dimension(770, 33), dimension2 = new Dimension(152, 33);//Definition of some dimensions used multiple times
    private static final Font font1 = new Font("Times New Roman", Font.BOLD, 23), font2 = new Font("Times New Roman", Font.BOLD, 14);//Definitions of some fonts used multiple times
    private static final String startTip = "Press to start a new game.", loadTip = "Press to loadMenu the saved game.";//Definition of some tool tips used multiple times
    private final StartListener startListener = new StartListener(this);//Definition of the ActionListener for start button presses
    private final LoadListener loadListener = new LoadListener(this);//Definition of the ActionListener for load button presses
    private JMenuItem loadMenu;//Definition for the JMenuItems for the load and save buttons
    public JFrame window;//JFrame window to be displayed
    public JMenuItem save;
    public JPanel buffer, selectorStart, selector, info;//JPanels for window. buffer for spacing in the start window, selectorStart for te buttons on the start menu, selector for game buttons, info for game status
    public JLabel status;//JLabel for game status
    public Connect4Logic game;//Current Connect4 game
    public BoardGUI board;//Graphical representation of game
    public JButton button1, button2, button3, button4, button5, button6, button7;//Buttons for selecting columns in game
    public JButton[] buttons = {button1, button2, button3, button4, button5, button6, button7};//Array of buttons button1-button7

    public Connect4GUI() throws FileNotFoundException {//Connect4GUI constructors
        this.start();
    }

    private static void saved(AbstractButton button) throws FileNotFoundException {//Enables and disables the load button based on whether loadable information is present in "save.txt"
        Scanner scan = new Scanner(new File("resources/save.txt"));
        scan.reset();
        try {
            scan.next();
            button.setEnabled(true);
        }
        catch(Exception except){
            button.setEnabled(false);
        }
        scan.close();
    }
    private void start() throws FileNotFoundException {//Sets up the window and displays the main menu
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("Game");
        JMenuItem startMenu = new JMenuItem("New Game", KeyEvent.VK_E);
        JButton start = new JButton("New Game");
        JButton load = new JButton("Load Game");
        window = new JFrame("Connect 4!");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        size(window, new Dimension(770, 805));
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("Connect4Icon.png"))).getImage());
        window.setLayout(new BorderLayout(10, 10));
        menu.setFont(font2);
        menu.setMnemonic(KeyEvent.VK_G);
        menu.setToolTipText("Press for options.");
        startMenu.setFont(font2);
        startMenu.setToolTipText(startTip);
        startMenu.addActionListener(startListener);
        menu.add(startMenu);
        loadMenu = new JMenuItem("Load Game", KeyEvent.VK_O);
        loadMenu.setFont(font2);
        loadMenu.setToolTipText(loadTip);
        loadMenu.addActionListener(loadListener);
        saved(loadMenu);
        menu.add(loadMenu);
        menu.addSeparator();
        save = new JMenuItem("Save Game", KeyEvent.VK_S);
        save.setFont(font2);
        save.setToolTipText("Press to save the current game.");
        save.setEnabled(false);
        menu.add(save);
        bar.add(menu);
        window.setJMenuBar(bar);
        buffer = new JPanel();
        size(buffer, new Dimension(770, 33));
        window.add(buffer, BorderLayout.NORTH);
        selectorStart = new JPanel();
        selectorStart.setLayout(new BoxLayout(selectorStart, BoxLayout.Y_AXIS));
        size(selectorStart, new Dimension(100, 76));
        start.setMnemonic(KeyEvent.VK_N);
        start.setToolTipText(startTip);
        start.setFont(font1);
        size(start, dimension2);
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        start.addActionListener(startListener);
        selectorStart.add(start);
        selectorStart.add(Box.createRigidArea(new Dimension(152, 10)));
        load.setMnemonic(KeyEvent.VK_L);
        load.setToolTipText(loadTip);
        load.setFont(font1);
        size(load, dimension2);
        load.addActionListener(loadListener);
        load.setAlignmentX(Component.CENTER_ALIGNMENT);
        saved(load);
        selectorStart.add(load);
        window.add(selectorStart, BorderLayout.CENTER);
    }
    private void initialize() throws FileNotFoundException {//Begins initialization of game window
        saved(loadMenu);
        info = new JPanel();
        size(info, dimension1);
        status = new JLabel();
        status.setFont(font1);
        info.add(status);
        window.add(info, BorderLayout.NORTH);
        selector = new JPanel(new FlowLayout(FlowLayout.CENTER, 7, 0));
        size(selector, dimension1);
        for (int column = 0; column <= 6; column++){
            int[] keys = {KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7};
            buttons[column] = new JButton(Integer.toString(column + 1));
            size(buttons[column], new Dimension(100,33));
            buttons[column].setMnemonic(keys[column]);
            buttons[column].setToolTipText("Press to select column " + (column + 1) + ".");
            buttons[column].setFont(font1);
            buttons[column].addActionListener(new SelectionListener(column, this));
            selector.add(buttons[column]);
        }
        window.add(selector, BorderLayout.SOUTH);
    }
    private void finish(){//Finishes initialization
        save.addActionListener(actionListener -> {
            try {
                game.save();
                saved(loadMenu);
            } catch (IOException except) {
                System.out.println("error: could not save");
            }
        });
        save.setEnabled(true);
        statusSet();
        board = new BoardGUI(game);
        window.add(board, BorderLayout.CENTER);
        window.revalidate();
    }
    public static void size(Container container, Dimension dimension){//Sets only allowable size for a Container
        container.setPreferredSize(dimension);
        container.setMinimumSize(dimension);
        container.setMaximumSize(dimension);
    }
    public void reset(){//Removes all panels from the window
        window.remove(buffer);
        window.remove(selectorStart);
        try {
            window.remove(info);
            window.remove(board);
            window.remove(selector);
        }
        catch(Exception ignored){}
    }
    public void statusSet(){//Detects status of the game and sets status to reflect the status of the game
        status.setText("Player " + game.player + "'s turn.");
        if (game.player == 1){
            status.setForeground(Color.RED);
        }
        else{
            status.setForeground(Color.BLUE);
        }
    }
    public void newGame() throws FileNotFoundException {//Stars a new game
        reset();
        initialize();
        game = new Connect4Logic();
        finish();
    }
    public void loadGame() throws FileNotFoundException {//Loads an existing game
        reset();
        initialize();
        game = new Connect4Logic(0);
        finish();
    }
    public void show(){//Shows window
        window.setVisible(true);
    }
}