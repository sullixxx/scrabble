import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.LinkedList;

public class ScrabbleGUI implements ActionListener {

    private Dictionary dico;
    private JTextField tfInputUser;
    private JButton btnSearch;
    private JTextArea taResult;

    private JPanel bottomPanel;

    public ScrabbleGUI(){
        JFrame frame = new JFrame("Scrabble GUI");
        frame.setMinimumSize(new Dimension(640,480));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(2,1));

        JPanel topPanel = new JPanel();
        tfInputUser = new JTextField(20);

        btnSearch = new JButton("Search");
        btnSearch.setActionCommand("search");
        btnSearch.addActionListener(this);

        topPanel.add(tfInputUser);
        topPanel.add(btnSearch);

        frame.add(topPanel);

        bottomPanel = new JPanel();
        taResult = new JTextArea(14,52);
        JScrollPane scroll = new JScrollPane(taResult);
        bottomPanel.add(scroll);
        // bottomPanel.add(taResult);

        frame.add(bottomPanel);

        frame.pack();
        frame.setVisible(true);

        try {
            dico = new Dictionary("src/fr_FR_utf8.dico");
        }
        catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }

    }

    public static void main(String[] args) {
        new ScrabbleGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("search")){
            try {
                if (tfInputUser.getText().length() > 0){
                    String inputUser = tfInputUser.getText().toLowerCase();
                    char[] letters = inputUser.toCharArray();
                    LinkedList<String> result = dico.getWordsThatCanBeComposed(letters);
                    for (String s: result) {
                        taResult.append(s+"\n");
                        // System.out.println(s);
                    }
                    bottomPanel.updateUI();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Input User is empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(-1);
            }
        }
    }
//    Exception
//    try { /* ... */ } catch (Exception ex) {
//        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//        System.exit(-1);
//    }
}
