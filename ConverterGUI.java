import java.awt.event.ActionListener;
import java.text.ParseException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;



public class ConverterGUI extends JFrame implements ActionListener{

    binary32converter binaryConverter;

    JFrame frame;
    
    JLabel converterTitle, instructionLabel, exampleLabel,
            originalValueLabel, normalizedValueLabel, signValueLabel, 
            exponentValueLabel, mantissaValueLabel, binaryRepresentationLabel,
            hexRepresentationLabel, caseValueLabel;

    JTextField inputArea, originalValueText, normalizedValueText, signValueText, 
               exponentValueText, mantissaValueText, binaryRepresentationText, 
               hexRepresentationText, caseValueText;

    JCheckBox decimalCheckBox, binaryCheckbox, saveCheckbox;

    JButton convertButton, resetButton, convertAgainButton;

    JPanel startPanel, convertPanel;

    Border border = BorderFactory.createLineBorder(Color.GREEN, 3);

    String numInput;

    RealNumber realNumber;
    BinaryNumber binaryNumber;
    Binary32Number binary32Number;

    Boolean makeTextOutput = false;

    ConverterGUI() {
        
        //Creating JFrame
        frame = new JFrame();
        frame.setTitle("CSARCH2 Binary Converter");
        frame.setSize(750,375);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        //Creating Start JPanel
        startPanel = new JPanel();
        startPanel.setSize(750, 375);
        startPanel.setBackground(Color.BLACK);



        //Creating Buttons and Text Fields for startPanel
        converterTitle = new JLabel("Binary-32 Floating Point Converter");
        converterTitle.setForeground(Color.GREEN);
        converterTitle.setFont(new Font("Consolas", Font.PLAIN, 25));
        converterTitle.setAlignmentY(JLabel.TOP_ALIGNMENT);


        decimalCheckBox = new JCheckBox("DECIMAL");
        decimalCheckBox.setForeground(Color.GREEN);
        decimalCheckBox.setFont(new Font("Consolas", Font.BOLD, 25));
        decimalCheckBox.setOpaque(false);
        decimalCheckBox.setContentAreaFilled(false);
        decimalCheckBox.setBorderPainted(false);
        decimalCheckBox.addActionListener(this);
     

        binaryCheckbox = new JCheckBox("BINARY");
        binaryCheckbox.setForeground(Color.GREEN);
        binaryCheckbox.setFont(new Font("Consolas", Font.BOLD, 25));
        binaryCheckbox.setOpaque(false);
        binaryCheckbox.setContentAreaFilled(false);
        binaryCheckbox.setBorderPainted(false);
        binaryCheckbox.addActionListener(this);

        saveCheckbox = new JCheckBox("SAVE TO TEXT");
        saveCheckbox.setForeground(Color.GREEN);
        saveCheckbox.setFont(new Font("Consolas", Font.BOLD, 25));
        saveCheckbox.setOpaque(false);
        saveCheckbox.setContentAreaFilled(false);
        saveCheckbox.setBorderPainted(false);
        saveCheckbox.addActionListener(this);

        instructionLabel = new JLabel("<html>Input the floating point number<br/> in the chosen format (With Optional Exponent)</html>");
        instructionLabel.setForeground(Color.GREEN);
        instructionLabel.setFont(new Font("Consolas", Font.BOLD, 20));
        instructionLabel.setAlignmentX(0);


        exampleLabel = new JLabel("Examples: 65.0x10^3 / 101101.011x2^6");
        exampleLabel.setForeground(Color.GREEN);
        exampleLabel.setFont(new Font("Consolas", Font.BOLD, 15));

        inputArea = new JTextField();
        inputArea.setPreferredSize(new Dimension(700, 70));
        inputArea.setFont(new Font("Consolas", Font.BOLD, 40));
        inputArea.setForeground(Color.GREEN);
        inputArea.setBackground(Color.BLACK);
        inputArea.setBorder(border);


        convertButton = new JButton("convert :)");
        convertButton.setFont(new Font("Consolas", Font.BOLD, 40));
        convertButton.setForeground(Color.GREEN);
        convertButton.setBackground(Color.BLACK);
        convertButton.setBorder(border);
        convertButton.setPreferredSize(new Dimension(250, 70));
        convertButton.addActionListener(this);


        resetButton = new JButton("reset :(");
        resetButton.setFont(new Font("Consolas", Font.BOLD, 40));
        resetButton.setForeground(Color.GREEN);
        resetButton.setBackground(Color.BLACK);
        resetButton.setBorder(border);
        resetButton.setPreferredSize(new Dimension(250, 70));
        resetButton.addActionListener(this);

        
        
        startPanel.add(converterTitle);
        startPanel.add(decimalCheckBox);
        startPanel.add(binaryCheckbox);
        startPanel.add(saveCheckbox);
        startPanel.add(instructionLabel);
        startPanel.add(exampleLabel);
        startPanel.add(inputArea);
        startPanel.add(convertButton);
        startPanel.add(resetButton);
       

        frame.add(startPanel);
        startPanel.revalidate();

        

    }
    

    public void showConvertPanel() {
        
        startPanel.setVisible(false);
        frame.setSize(750, 640);
        

        convertPanel = new JPanel();
        convertPanel.setSize(750, 375);
        convertPanel.setBackground(Color.BLACK);
        
        //Original Value
        originalValueLabel = new JLabel("Original Value");
        originalValueLabel.setPreferredSize(new Dimension(700, 11));
        originalValueLabel.setForeground(Color.GREEN);
        originalValueLabel.setFont(new Font("Consolas", Font.BOLD, 10));

        originalValueText = new JTextField();
        originalValueText.setFont(new Font("Consolas", Font.PLAIN, 30));
        originalValueText.setForeground(Color.GREEN);
        originalValueText.setBackground(Color.BLACK);
        originalValueText.setPreferredSize(new Dimension(700, 40));
        originalValueText.setBorder(border);

        

        //Normalized Value
        normalizedValueLabel = new JLabel("Normalized Value");
        normalizedValueLabel.setPreferredSize(new Dimension(700, 11));
        normalizedValueLabel.setForeground(Color.GREEN);
        normalizedValueLabel.setFont(new Font("Consolas", Font.BOLD, 10));

        normalizedValueText = new JTextField();
        normalizedValueText.setFont(new Font("Consolas", Font.PLAIN, 30));
        normalizedValueText.setForeground(Color.GREEN);
        normalizedValueText.setBackground(Color.BLACK);
        normalizedValueText.setPreferredSize(new Dimension(700, 40));
        normalizedValueText.setBorder(border);



        //Sign Value
        signValueLabel = new JLabel("Sign Value");
        signValueLabel.setPreferredSize(new Dimension(700, 11));
        signValueLabel.setForeground(Color.GREEN);
        signValueLabel.setFont(new Font("Consolas", Font.BOLD, 10));

        signValueText = new JTextField();
        signValueText.setFont(new Font("Consolas", Font.PLAIN, 30));
        signValueText.setForeground(Color.GREEN);
        signValueText.setBackground(Color.BLACK);
        signValueText.setPreferredSize(new Dimension(700, 40));
        signValueText.setBorder(border);

        //Exponent Value
        exponentValueLabel = new JLabel("Exponent");
        exponentValueLabel.setPreferredSize(new Dimension(700, 11));
        exponentValueLabel.setForeground(Color.GREEN);
        exponentValueLabel.setFont(new Font("Consolas", Font.BOLD, 10));

        exponentValueText = new JTextField();
        exponentValueText.setFont(new Font("Consolas", Font.PLAIN, 30));
        exponentValueText.setForeground(Color.GREEN);
        exponentValueText.setBackground(Color.BLACK);
        exponentValueText.setPreferredSize(new Dimension(700, 40));
        exponentValueText.setBorder(border);

        //Mantissa Value
        mantissaValueLabel = new JLabel("Mantissa");
        mantissaValueLabel.setPreferredSize(new Dimension(700, 11));
        mantissaValueLabel.setForeground(Color.GREEN);
        mantissaValueLabel.setFont(new Font("Consolas", Font.BOLD, 10));

        mantissaValueText = new JTextField();
        mantissaValueText.setFont(new Font("Consolas", Font.PLAIN, 30));
        mantissaValueText.setForeground(Color.GREEN);
        mantissaValueText.setBackground(Color.BLACK);
        mantissaValueText.setPreferredSize(new Dimension(700, 40));
        mantissaValueText.setBorder(border);

        //Binary Representation
        binaryRepresentationLabel = new JLabel("Binary Representation");
        binaryRepresentationLabel.setPreferredSize(new Dimension(700, 11));
        binaryRepresentationLabel.setForeground(Color.GREEN);
        binaryRepresentationLabel.setFont(new Font("Consolas", Font.BOLD, 10));


        binaryRepresentationText = new JTextField();
        binaryRepresentationText.setFont(new Font("Consolas", Font.PLAIN, 25));
        binaryRepresentationText.setForeground(Color.GREEN);
        binaryRepresentationText.setBackground(Color.BLACK);
        binaryRepresentationText.setPreferredSize(new Dimension(700, 40));
        binaryRepresentationText.setBorder(border);


        //Hexadecimal Representation
        hexRepresentationLabel = new JLabel("Hexadecimal Representation");
        hexRepresentationLabel.setPreferredSize(new Dimension(700, 11));
        hexRepresentationLabel.setForeground(Color.GREEN);
        hexRepresentationLabel.setFont(new Font("Consolas", Font.BOLD, 10));

        hexRepresentationText = new JTextField();
        hexRepresentationText.setFont(new Font("Consolas", Font.PLAIN, 30));
        hexRepresentationText.setForeground(Color.GREEN);
        hexRepresentationText.setBackground(Color.BLACK);
        hexRepresentationText.setPreferredSize(new Dimension(700, 40));
        hexRepresentationText.setBorder(border);


        //Case Value
        caseValueLabel = new JLabel("Case Value");
        caseValueLabel.setPreferredSize(new Dimension(700, 11));
        caseValueLabel.setForeground(Color.GREEN);
        caseValueLabel.setFont(new Font("Consolas", Font.BOLD, 10));

        caseValueText = new JTextField();
        caseValueText.setFont(new Font("Consolas", Font.PLAIN, 30));
        caseValueText.setForeground(Color.GREEN);
        caseValueText.setBackground(Color.BLACK);
        caseValueText.setPreferredSize(new Dimension(700, 40));
        caseValueText.setBorder(border);


        //Convert Again Button
        convertAgainButton = new JButton("Convert Again?");
        convertAgainButton.setFont(new Font("Consolas", Font.BOLD, 25));
        convertAgainButton.setPreferredSize(new Dimension(500, 50));
        convertAgainButton.setForeground(Color.GREEN);
        convertAgainButton.setBackground(Color.BLACK);
        convertAgainButton.setBorder(border);
        convertAgainButton.addActionListener(this);


        //Adding Labels, Text Fields, and Button to JPanel
        convertPanel.add(originalValueLabel);
        convertPanel.add(originalValueText);
        convertPanel.add(normalizedValueLabel);
        convertPanel.add(normalizedValueText);
        convertPanel.add(signValueLabel);
        convertPanel.add(signValueText);
        convertPanel.add(exponentValueLabel);
        convertPanel.add(exponentValueText);
        convertPanel.add(mantissaValueLabel);
        convertPanel.add(mantissaValueText);
        convertPanel.add(binaryRepresentationLabel);
        convertPanel.add(binaryRepresentationText);
        convertPanel.add(hexRepresentationLabel);
        convertPanel.add(hexRepresentationText);
        convertPanel.add(caseValueLabel);
        convertPanel.add(caseValueText);
        convertPanel.add(convertAgainButton);

        frame.add(convertPanel);
        convertPanel.setVisible(true);
        convertPanel.revalidate();
 
        //Checks if Decimal or Binary Conversion
        if (decimalCheckBox.isSelected()) {

           realNumber = new RealNumber(numInput);
           binaryNumber = BinaryNumber.valueOf(realNumber);
           binary32Number = new Binary32Number(binaryNumber);
           if(makeTextOutput) binary32Number.outputToFile(binaryNumber, binary32Number);
           originalValueText.setText(" " + numInput + " / " + binaryNumber.getOriginalValue());

        } else {

            binaryNumber = new BinaryNumber(numInput);
            binary32Number = new Binary32Number(binaryNumber);
            if(makeTextOutput) binary32Number.outputToFile(binaryNumber, binary32Number);
            originalValueText.setText(binaryNumber.getOriginalValue());
        }

        //Inserting the values into the text fields

        originalValueText.setEditable(false);


        normalizedValueText.setText(binaryNumber.getNormalizedValue());
        normalizedValueText.setEditable(false);

        signValueText.setText(" " + binary32Number.getSignString());
        signValueText.setEditable(false);

        exponentValueText.setText(" " + binary32Number.getExponentString());
        exponentValueText.setEditable(false);

        mantissaValueText.setText(" " + binary32Number.getMantissa());
        mantissaValueText.setEditable(false);

        binaryRepresentationText.setText(" 0b" + binary32Number.toString());
        binaryRepresentationText.setEditable(false);

        hexRepresentationText.setText(" 0x" + binary32Number.toStringHex());
        hexRepresentationText.setEditable(false);

        caseValueText.setText(" " + binary32Number.getCase());
        caseValueText.setEditable(false);

        convertPanel.revalidate();

    
    }

    public boolean containsNonAllowedCharacters(String input) {
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (!(ch >= '0' && ch <= '9') && ch != 'x' && ch != 'X' && ch != '^' && ch != '.' && ch != '-') {
                return true;
            }
        }
        return false;
    }

    public boolean containsNonAllowedDigitsBinary(String input) {
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (ch != '0' && ch != '1' && ch != '.' && ch != '-') {
                return true;
            }
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == saveCheckbox)
        {
            makeTextOutput = !makeTextOutput;
        }

        //So only one box can be selected
        if (e.getSource() == decimalCheckBox) {
            binaryCheckbox.setSelected(false);
        }

        if (e.getSource() == binaryCheckbox) {
            decimalCheckBox.setSelected(false);
        }


        //For convertion confirmation
        if (e.getSource() == convertButton) {

            if (decimalCheckBox.isSelected() || binaryCheckbox.isSelected()) {

             
                if (inputArea.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Missing a value in the input field!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {

                    //error detection for input
                    numInput = inputArea.getText();

                    if (containsNonAllowedCharacters(numInput)) {
                        JOptionPane.showMessageDialog(null, "Invalid Format", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {


                        String[] parts = numInput.split("x"); //Split into significand and exponent
                        String baseValue = parts[1].split("\\^")[0]; 
                        String exponent = parts[1].split("\\^")[1];
                        //Too big to fit here turn to string
                        try
                        {    
                            int bValue = Integer.parseInt(baseValue); 
                            int expValue = Integer.parseInt(exponent);

                            if(decimalCheckBox.isSelected()) 
                            {

                                //check if base is 10, if not show error and clear
                                if(bValue != 10) 
                                {
                                    JOptionPane.showMessageDialog(null, "Incorrect base value. Should be in base 10.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                else 
                                {
                                    try 
                                    {
                                        showConvertPanel();
                                    } 
                                    catch (Exception a) 
                                    {
                                        a.printStackTrace();
                                    }
                                }
                            } 
                            else 
                            {
                                if(bValue != 2) 
                                {
                                    JOptionPane.showMessageDialog(null, "Incorrect base value. Should be in base 2.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                else
                                {
    
                                    if(containsNonAllowedDigitsBinary(parts[0]))
                                    {
                                        System.out.println(parts[0]);
                                        JOptionPane.showMessageDialog(null, "Contains digits that are neither 1 or 0", "Error", JOptionPane.ERROR_MESSAGE);
                                    } 
                                    else 
                                    {
                                        try 
                                        {
                                            showConvertPanel();
                                        } 
                                        catch (Exception b) 
                                        {
                                            b.printStackTrace();
                                        }
                                    }
                                }
            
                            }    

                        } 
                        catch (Exception pe)
                        {
                            JOptionPane.showMessageDialog(null, "Exponent values are not valid", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                
                }

            } else {

                JOptionPane.showMessageDialog(null, "Missing a choice on either Decimal or Binary", "Error", JOptionPane.ERROR_MESSAGE);

            }



            //check if a checkbox has been checked, show error if not
            //change JFrame dimension to accomodate new JPanel
            //create and add the text fields

            //see which conversion was chosen, apply appropriate method
            //input the values in the fields 


        }



        //For resetting
        if (e.getSource() == resetButton) {
            decimalCheckBox.setSelected(false);
            binaryCheckbox.setSelected(false);
            inputArea.setText("");
        }

        if (e.getSource() == convertAgainButton) {
            convertPanel.setVisible(false);
            frame.setSize(750,375);
            startPanel.setVisible(true);
            inputArea.setText("");
            decimalCheckBox.setSelected(false);
            binaryCheckbox.setSelected(false);
        }
    }

    public static void main(String[] args) {

        //Initializing the JFrame
        ConverterGUI test = new ConverterGUI();

    }

    
}
