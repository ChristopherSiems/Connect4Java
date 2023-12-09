import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Connect4Logic {//Connect4 internal game logic
    private static final File file = new File("resources/save.txt");//Stores the file for saves and loads
    public int[][] board = new int[7][6];//The game board
    public int player = 1;//Stores the current player

    public Connect4Logic() {
    }//Game constructor
    public Connect4Logic(int ignored) throws FileNotFoundException {//Game constructor for loaded game
        Scanner scan = new Scanner(file);
        scan.reset();
        player = Integer.parseInt(scan.next());
        for (int[] column : board){
            String[] columnLoad = scan.next().split("");
            for (int space = 0; space <= 5; space++){
                column[space] = Integer.parseInt(columnLoad[space]);
            }
        }
        scan.close();
    }

    private void print(){//Method to print the board to console
        System.out.println();
        for(int row = 5; row >= 0; row--){
            for (int[] column : board) {
                System.out.print(column[row] + " ");
            }
            System.out.println();
        }
    }
    private boolean won(int column, int top){//Method to check and return true if the player who just moved won on that move
        int segment = 1;
        for (int checking = top - 1; checking >= 0; checking--){
            if (player == board[column][checking]){
                segment++;
            }
            else{
                break;
            }
        }
        if (segment >= 4){
            return true;
        }
        segment = 1;
        for (int checking = column + 1; checking <= 6; checking++){
            if (player == board[checking][top]){
                segment++;
            }
            else{
                break;
            }
        }
        for (int checking = column - 1; checking >= 0; checking--){
            if (player == board[checking][top]){
                segment++;
            }
            else{
                break;
            }
        }
        if (segment >= 4){
            return true;
        }
        segment = 1;
        for (int checkingColumn = column + 1, checkingSpace = top + 1; checkingColumn <= 6 && checkingSpace <= 5; checkingColumn++, checkingSpace++){
            if (player == board[checkingColumn][checkingSpace]){
                segment++;
            }
            else{
                break;
            }
        }
        for (int checkingColumn = column - 1, checkingSpace = top - 1; checkingColumn >= 0 && checkingSpace >= 0; checkingColumn--, checkingSpace--){
            if (player == board[checkingColumn][checkingSpace]){
                segment++;
            }
            else{
                break;
            }
        }
        if (segment >= 4){
            return true;
        }
        segment = 1;
        for (int checkingColumn = column - 1, checkingSpace = top + 1; checkingColumn >= 0 && checkingSpace <= 5; checkingColumn--, checkingSpace++){
            if (player == board[checkingColumn][checkingSpace]){
                segment++;
            }
            else{
                break;
            }
        }
        for (int checkingColumn = column + 1, checkingSpace = top - 1; checkingColumn <= 6 && checkingSpace >= 0; checkingColumn++, checkingSpace--){
            if (player == board[checkingColumn][checkingSpace]){
                segment++;
            }
            else{
                break;
            }
        }
        if (segment >= 4){
            return true;
        }
        return false;
    }
    public void move(int column, Connect4GUI window){//Method preforms all actions in a move
        int top = 0;
        for (int space = 0; space <= 6; space++) {
            if (board[column][space] == 0) {
                board[column][space] = player;
                top = space;
                break;
            }
        }
        window.board.repaint();
        if (this.won(column, top)){
            window.status.setText("Player " + player + " Wins!");
            window.save.setEnabled(false);
            for (JButton button : window.buttons){
                button.setEnabled(false);
            }
        }
        else {
            int counter = 0;
            if (player == 1) {
                player = 2;
            } else {
                player = 1;
            }
            for (int col = 0; col <= 6; col++){
                if (board[col][5] != 0){
                    counter++;
                    window.buttons[col].setEnabled(false);
                }
            }
            if (counter == 7){
                window.status.setForeground(Color.BLACK);
                window.status.setText("Draw.");
                window.save.setEnabled(false);
            }
            else {
                window.statusSet();
            }
        }
    }
    public void save() throws IOException {//Method saves the current game
        FileWriter writer = new FileWriter(file);
        file.delete();
        file.createNewFile();
        writer.write(player + "\n");
        writer.flush();
        for (int[] column : board){
            for (int space : column){
                writer.write(Integer.toString(space));
                writer.flush();
            }
            writer.write("\n");
            writer.flush();
        }
        writer.close();
    }
}