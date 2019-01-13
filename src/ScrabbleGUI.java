import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.LinkedList;

public class ScrabbleGUI implements ActionListener {

    private Dictionary dico;
    private JTextField letterTextField;
    private JButton btnSearch;
    private JTextArea wordlistTextArea;

    private JPanel topPanel = new JPanel();// container du haut
    private JPanel centerPanel = new JPanel();// container du bas;
    JScrollPane scroll;

    public ScrabbleGUI(){
        JFrame frame = new JFrame("Scrabble GUI"); //nouvelle fenêtre principale
        frame.setResizable(true);
        //frame.setMinimumSize(new Dimension(640,480)); //taille de la fenêtre
        frame.setSize(new Dimension(400,400)); //taille de la fenêtre
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// fermeture via bouton close
        //frame.setLayout(new GridLayout(2,1)); //disposition en 2 lignes 1 colonne
        //frame.setLayout(new FlowLayout());
        frame.setLayout(new BorderLayout());

        topPanel.setBackground(Color.DARK_GRAY);
        letterTextField = new JTextField(20);
        letterTextField.setHorizontalAlignment(JTextField.LEFT);
        btnSearch = new JButton("Search");
        btnSearch.setActionCommand("search");
        btnSearch.addActionListener(this);
        topPanel.add(letterTextField);//on place le champ de saisie et le boutons search
        topPanel.add(btnSearch);
        frame.add(topPanel,BorderLayout.NORTH);//on ajoute le container a la frame

        //centerPanel.setBorder(BorderFactory.createTitledBorder("Possibles words"));
        wordlistTextArea = new JTextArea(14,52);
        scroll = new JScrollPane(wordlistTextArea);
        centerPanel.add(scroll);
        frame.add(centerPanel,BorderLayout.CENTER);//on ajoute le container a la frame
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
                if (letterTextField.getText().length() > 0){
                    String userInput = letterTextField.getText().toLowerCase();
                    char[] letters = userInput.toCharArray();
                    LinkedList<String> result = dico.getWordsThatCanBeComposed(letters);
                    for (String s: result) {
                        wordlistTextArea.append(s+"\n");
                    }
                    wordlistTextArea.setCaretPosition(0);// retabli le curseur en haut afin d'afficher les premier resultats
                    centerPanel.updateUI();
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
