package br.com.bradseg.coti.jarcopy;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Client
 */
public class Client {

    private Properties clientProps = new Properties();
    private Properties targetsProps = new Properties();

    public Client() {
    }

    private void readProperties() throws IOException {

        this.clientProps = new Properties();
        this.targetsProps = new Properties();

        try {
            clientProps.load(new FileInputStream("client.properties"));
        } catch (IOException e) {
            System.out.println("Erro ao carregar client.properties");
            System.out.println(e);
            throw new IOException("Erro ao carregar client.properties", e);
        }
        try {
            targetsProps.load(new FileInputStream("targets.properties"));
        } catch (IOException e) {
            System.out.println("Erro ao carregar targets.properties");
            System.out.println(e);
            throw new IOException("Erro ao carregar targets.properties", e);
        }
    }

    public void execute() throws IOException {
        JFileChooser jfc = new JFileChooser(new File(System.getProperty("user.dir"))); // FileSystemView.getFileSystemView().getHomeDirectory()

        int returnValue = jfc.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog(null);
        if (returnValue != JFileChooser.APPROVE_OPTION) {
            System.out.println("Operacao cancelada...");
            return;
        }

        File selectedFile = jfc.getSelectedFile();
        System.out.println("===============================================");
        System.out.println("Preparando para enviar arquivo : " + selectedFile.getAbsolutePath());

        int input = JOptionPane.showConfirmDialog(null, "Confirma envio do arquivo " + selectedFile.getName() + "?");
        // 0=yes, 1=no, 2=cancel
        System.out.println(input == 0 ? "Envio confirmado" : "Envio cancelado");
        if (input != 0) {
            System.out.println("===============================================");
            return;
        }
        this.readProperties();
        String porta = clientProps.getProperty("porta");
        List lista = Collections.list(targetsProps.propertyNames());
        System.out.println("Quantidade de servidores : " + lista.size());
        Iterator i = lista.iterator();
        String servidor = null;
        int erros = 0;
        List<String> aErros = new ArrayList<String>();
        System.out.println("-----");
        while (i.hasNext()) {
            try {
                servidor = (String) i.next();
                Socket socket = new Socket();
                // System.out.println("Conectando servidor : " + servidor);
                socket.connect(new InetSocketAddress(servidor, Integer.valueOf(porta)));

                // receive file // InputStream in = sock.getInputStream();
                BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
                DataOutputStream dos = new DataOutputStream(out);
                dos.writeUTF(selectedFile.getName());
                Files.copy(selectedFile.toPath(), dos);
                System.out.println(servidor + " : SUCESSO.");
                // fechar conexão??
                try {
                    out.close();
                    socket.close();
                } catch (Exception e) {
                }

            } catch (Exception ex) {
                System.out.println(servidor + " : FALHA. " + ex.getMessage());
                erros++;
                aErros.add(servidor);
            }
        }
        if (erros > 0) {
            System.out.println("-----------------------------------------------");
            System.out.println("Erros : " + erros);
        }
        System.out.println("===============================================");
    }

}