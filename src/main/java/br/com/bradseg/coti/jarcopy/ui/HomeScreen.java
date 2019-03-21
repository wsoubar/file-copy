package br.com.bradseg.coti.jarcopy.ui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * HomeScreen
 */
public class HomeScreen extends JFrame {

    JButton btnGravar = new JButton("Confirmar");
    JButton btnCancelar = new JButton("Cancelar");

    public HomeScreen () {
        setLayout(new FlowLayout());
        getContentPane().add(btnGravar);
        getContentPane().add(btnCancelar);
        //setSize(100, 100);
        setVisible(true);
        pack();
    }
}