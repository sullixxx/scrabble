import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class ScrabbleConsole {
    private ScrabbleConsole(){
        System.out.println("Welcome to the Scrabble assistant!");
        try {
            Dictionary dictionary = new Dictionary("fr_FR_utf8.dico");//création d'un objet dico
            System.out.println("Please enter a letter list : ");
            //Récupération de la saisie et transformation de la chaine en tableau de caractères
            Scanner scan = new Scanner(System.in);
            String inputUser = scan.next();
            inputUser=inputUser.toLowerCase();
            char[] letters = inputUser.toCharArray();

            LinkedList<String> result = dictionary.getWordsThatCanBeComposed(letters);
            if (result.size() >= 1) {
                System.out.println(result.size() + " matching word(s) found : " + result);
            }else {
                System.out.println("0 matching word(s) found : []");
            }
        }catch (FileNotFoundException e){
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args){
        new ScrabbleConsole();
    }

}


