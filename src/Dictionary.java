import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.*;

public class Dictionary {

    private List<String> wordList;

    public Dictionary(String filename) throws FileNotFoundException {

        URL dictionaryURL = Dictionary.class.getClassLoader().getResource(filename);
        if(dictionaryURL == null){
            throw new FileNotFoundException();
        }
        Path path = toPath(dictionaryURL); //chemin du dictionaire
        try{
            List<String> lines = Files.readAllLines(path);
            System.out.println(lines.size());
            this.wordList = lines.subList(1, lines.size());
        }catch (IOException e){
            throw new IllegalStateException("cannot read file ["+ path +"]");
        }

    }

    private Path toPath(URL dictionaryURL){
        try{
            return Paths.get(dictionaryURL.toURI());
        }catch (URISyntaxException e){
            throw new IllegalArgumentException("cannot convert [" + dictionaryURL + "] to Path",e);
        }
    }

    public boolean isValidWord(String word){
        return wordList.contains(word);
    }

    public boolean mayBeComposed(String word, char[] letters) {
        word = replaceFrenchCharacter(word);
        LinkedList<Character> listLetters = new LinkedList<Character>();
        for (char c : letters)
            listLetters.add(c);

        char[] wordSplit = word.toCharArray();

        // Buffer pour parcourir le tableau contenant les lettres du mots à valider
        int buffLetter = 0;
        int buffWordSplit;
        for (buffWordSplit = 0; buffWordSplit < wordSplit.length; buffWordSplit++) {
            if (listLetters.contains(wordSplit[buffWordSplit])) {
                listLetters.remove(listLetters.indexOf(wordSplit[buffWordSplit]));
                buffLetter++;
            }
        }
        if (buffLetter == word.length())
            return true;
        else if (findValueInShortTable(letters,'*')){
            int nbJoker = numberOfJoker(letters);
            if (buffLetter == word.length() - nbJoker)
                return true;
        }
        return false;
    }

    public String replaceFrenchCharacter(String s){
        s = s.toLowerCase();
        s = s.replaceAll("[àâä]","a");
        s = s.replace('ç','c');
        s = s.replaceAll("[éèëê]","e");
        s = s.replaceAll("[ïî]","i");
        s = s.replaceAll("[ôö]","o");
        s = s.replaceAll("[ùûü]","u");
        s = s.replace("œ","oe");
        s = s.replace("æ","ae");
        return s;
    }

    public Boolean findValueInShortTable(char[] table, char value){
        for (char c : table)
            if (c == value) {
                return true;
            }
        return false;
    }
    public int numberOfJoker(char[] table){
        int count = 0;
        for (char c : table)
            if (c == '*')
                count = count + 1;
        return count;
    }

    public LinkedList<String> getWordsThatCanBeComposed(char[] letters){
        LinkedList<String> possibleWord = new LinkedList<String>();

        for (String word : wordList) {
            if (mayBeComposed(word, letters)) {
                if(word.length()>=2)
                    possibleWord.add(word);
            }
        }

        return possibleWord;
    }

    @Override
    public String toString() {
      return "" + wordList.toString();

    }
}
