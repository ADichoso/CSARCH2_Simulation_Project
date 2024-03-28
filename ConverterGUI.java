import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class ConverterGUI extends JFrame implements ActionListener{


    JTextField inputArea;
    JCheckBox decOrBin;
    JButton convertButton;
    
    

    ConverterGUI() {
        
        //Creating JFrame
        JFrame frame = new JFrame();
        frame.setTitle("CSARCH2 Binary Converter");
        frame.setSize(500,500);
        frame.setVisible(true);
        frame.setResizable(false);

        inputArea = new JTextField();
        inputArea.addActionListener(this);


    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

    public static void main(String[] args) {
        
        //Creating JFrame

        //Error Handling
        //JOptionPane.showMessageDialog(null, "Invalid Input", "Error", JOptionPane.ERROR_MESSAGE);



    }

    
}
