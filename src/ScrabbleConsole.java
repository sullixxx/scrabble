import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class ScrabbleConsole {

    private ScrabbleConsole(){
        System.out.println("Welcome to the Scrabble assistant!");
        try {
            Dictionary dictionary = new Dictionary("fr_FR_utf8.dico");//fait appel au dico par le biais de son chemin
            String word = "cap";
            dictionary.isValidWord(word.substring(0,1).toUpperCase() + word.substring(1).toLowerCase());//vérifie la présence du mot ou non dans le dictionnaire avec la bonne mise en forme des casses
            System.out.println("Please enter a letter list : ");
            //Récupère les lettres entrées et transforme la chaine en tableau de caractères
            Scanner scan = new Scanner(System.in);
            String inputUser = scan.next();
            inputUser=inputUser.toLowerCase();
            char[] letters = inputUser.toCharArray();

            LinkedList<String> result = dictionary.getWordsThatCanBeComposed(letters);
            if (result.size() >= 1) {
                System.out.println("There are " + result.size() + " matching words found : " + result);
            }else {
                System.out.println("No word matches with the following letter list : " + inputUser + ".");
            }
        }catch (FileNotFoundException e){
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args){
        new ScrabbleConsole();
    }

}


