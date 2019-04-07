package br.com.bradseg.coti.jarcopy.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CopyFile extends JFrame {

    private JFileChooser fc;
    private JButton copyButton;
    private JButton chooseFileButton;
    private JButton destinationButton;
    private File workingDirectory;
    private JLabel sourceLabel;
    private JLabel destinationLabel;
    private JTextField sourceText;
    private JTextField sourceFileText;
    private JTextField destinationText;

    public static void main(String[] args) {
        CopyFile go = new CopyFile();
        go.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        go.setSize(500, 150);
        go.setVisible(true);
    }

    public CopyFile() {
        super("Copy a text file");
        setLayout(new GridLayout(3, 3, 5, 5));
        fc = new JFileChooser();

        // Open dialog box inside project folder to make easier to find files
        workingDirectory = new File(System.getProperty("user.dir"));
        fc.setCurrentDirectory(workingDirectory);
        // create labels and buttons for window
        chooseFileButton = new JButton("CHOOSE SOURCE FILE");
        destinationButton = new JButton("DESTINATION FOLDER");
        copyButton = new JButton("COPY FILE");
        sourceLabel = new JLabel("SOURCE FILE: ");
        sourceText = new JTextField(10);
        sourceText.setEditable(false);
        destinationLabel = new JLabel("DESTINATION: ");
        destinationText = new JTextField(10);

        // add everything to JFrame
        add(sourceLabel);
        add(sourceText);
        add(chooseFileButton);
        add(destinationLabel);
        add(destinationText);
        add(destinationButton);
        add(copyButton);

        // Create TheHandler object to add action listeners for the buttons.
        TheHandler handler = new TheHandler();
        chooseFileButton.addActionListener(handler);
        destinationButton.addActionListener(handler);
        copyButton.addActionListener(handler);
    }

    // Inner class to create action listeners
    private class TheHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            int returnVal;
            String selectedFilePath;
            File selectedFile;

            // Selecting a source file and displaying what the user is doing.
            if (event.getSource() == chooseFileButton) {
                returnVal = fc.showOpenDialog(null);
                // Set the path for the source file.
                if (returnVal == JFileChooser.APPROVE_OPTION) {

                    /*
                     * The two next lines of code are what I was trying to do to get only the file
                     * name but I get a whole page of errors, mainly I think it's saying no such
                     * file exists
                     */
                    // selectedFile = fc.getSelectedFile();
                    // sourceText.setText(selectedFile.getName());
                    selectedFilePath = fc.getSelectedFile().getAbsolutePath();
                    sourceText.setText(selectedFilePath);
                }
            } // end if

            // Handle destination button.
            if (event.getSource() == destinationButton) {
                returnVal = fc.showSaveDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    selectedFilePath = fc.getSelectedFile().getAbsolutePath();
                    destinationText.setText(selectedFilePath);
                }
            } // end if

            // Handle copy button
            if (event.getSource() == copyButton) {
                File sourceFile = new File(sourceText.getText());
                File destinationFile = new File(destinationText.getText());
                Path sourcePath = sourceFile.toPath();
                Path destinationPath = destinationFile.toPath();
                try {
                    Files.copy(sourcePath, destinationPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } // end if

        }// end actionPerformed
    }// end TheHandler class
}// end class