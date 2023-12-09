import java.io.FileNotFoundException;

public class Connect4 {//Main class for running the whole game
    public static void missingSave(){//Prints an error message and kills the program if "save.txt" is missing
        System.out.println("error: save.txt missing");
        System.exit(0);
    }

    public static void main(String[] args){
        try {
            Connect4GUI game = new Connect4GUI();
            game.show();
        }
        catch (FileNotFoundException except){
            missingSave();
        }
    }
}