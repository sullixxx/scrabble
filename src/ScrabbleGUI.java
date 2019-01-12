import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.LinkedList;

public class ScrabbleGUI implements ActionListener {

    private Dictionary dico;
    private JTextField inputTextField;
    private JButton btnSearch;
    private JTextArea searchResult;

    private JPanel bottomPanel= new JPanel();// container du bas;
    private JPanel topPanel = new JPanel();// container du haut
    JScrollPane scroll;

    public ScrabbleGUI(){
        JFrame frame = new JFrame("Scrabble GUI"); //nouvelle fenêtre principale
        //frame.setLocationRelativeTo(null);//positionnement au centre
        frame.setResizable(false);
        frame.setMinimumSize(new Dimension(640,480)); //taille de la fenêtre
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// fermeture via bouton close
        frame.setLayout(new GridLayout(2,1)); //disposition en 2 lignes 1 colonne

        topPanel.setBackground(Color.CYAN);
        inputTextField = new JTextField(20);
        btnSearch = new JButton("Search");
        btnSearch.setActionCommand("search");
        btnSearch.addActionListener(this);
        topPanel.add(inputTextField);//on place le champ de saisie et le boutons search
        topPanel.add(btnSearch);
        frame.add(topPanel);//on ajoute le container a la frame

        //bottomPanel.setBorder(BorderFactory.createTitledBorder("Possibles words"));
        searchResult = new JTextArea(14,52);
        //searchResult.setSelectionStart(0);
        scroll = new JScrollPane(searchResult);
        bottomPanel.add(scroll);
        frame.add(bottomPanel);//on ajoute le container a la frame
        frame.pack();
        frame.setVisible(true);

        try {
            dico = new Dictionary("fr_FR_utf8.dico");
        }
        catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "fr_FR_utf8.dico (aucun fichier trouvé) ", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("search")){
            try {
                if (inputTextField.getText().length() > 0){
                    String userInput = inputTextField.getText().toLowerCase();
                    char[] letters = userInput.toCharArray();
                    LinkedList<String> result = dico.getWordsThatCanBeComposed(letters);
                    for (String s: result) {
                        searchResult.append(s+"\n");
                    }
                    //scroll.getVerticalScrollBar().setValue(0);
                    bottomPanel.updateUI();
                }
                else{
                    JOptionPane.showMessageDialog(null, "textfield is empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(-1);
            }
        }
    }
    public static void main(String[] args) {
        new ScrabbleGUI();
    }

//    Exception
//    try { /* ... */ } catch (Exception ex) {
//        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//        System.exit(-1);
//    }
}
