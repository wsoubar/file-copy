package br.com.bradseg.coti.jarcopy.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * HomeScreen
 */
public class HomeScreen extends JFrame {

    JButton btnGravar = new JButton("Confirmar");
    JButton btnLer = new JButton("Ler");
    JButton btnCancelar = new JButton("Sair");

    public static void main(String[] args) {
        HomeScreen hs = new HomeScreen();
        hs.abrir();
    }

    public HomeScreen() {
        iniciar();
    }

    private void iniciar() {
        setTitle("BRADSEG-Servicos copia de bibliotecas");
        JFrame.setDefaultLookAndFeelDecorated(true);
        setLayout(new GridLayout(0,2));
        getContentPane().add(new JLabel("Teste"));
        getContentPane().add(btnGravar);
        getContentPane().add(btnLer);
        getContentPane().add(btnCancelar);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnCancelar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        addWindowListener(new WindowAdapter(){
        

            @Override
            public void windowClosing(WindowEvent e) {
                //System.exit(0);
            }
        
        });
    }

    public void abrir() {
        setSize(700, 300);
        setVisible(true);
        //pack();
    }

    
}