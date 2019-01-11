import java.io.FileNotFoundException;

public class ScrabbleConsole {

    private ScrabbleConsole(){
        System.out.println("Welcome to the Scrabble assistant");
        try {
            Dictionary dictionary = new Dictionary("fr_FR_utf8.dico");
            if (dictionary.isValidWord("Carole")){
                System.out.println("contient Carole");
            }
        }catch (FileNotFoundException e){
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args){
        new ScrabbleConsole();
    }

}


