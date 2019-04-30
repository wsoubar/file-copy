package br.com.bradseg.coti.jarcopy;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
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

    private String porta = null;
    private String arquivoServidores = null;

    public Client(String porta, String arquivoServidores) {
        this.porta = porta;
        this.arquivoServidores = arquivoServidores;
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
        
        /*
         *  Ler arquivo de servidores
         */
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(arquivoServidores));
            String line = reader.readLine();
            String servidor = null;
            String porta = null;
            int erros = 0;
            List<String> aErros = new ArrayList<String>();
    
            while (line != null) {
                try {
                    //carrega linha e pega servidor e porta, se tiver
                    URL url = new URL("http://"+line);
                    servidor = url.getHost();
                    porta = this.porta;
                    if (url.getPort() > 0) {
                        porta = String.valueOf(url.getPort());
                    }

                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(servidor, Integer.valueOf(porta)), 3000);
    
                    // receive file // InputStream in = sock.getInputStream();
                    BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
                    DataOutputStream dos = new DataOutputStream(out);
                    dos.writeUTF(selectedFile.getName());
                    Files.copy(selectedFile.toPath(), dos);
                    System.out.println(servidor + (this.porta.equals(porta)? "" : ":" + porta )+ " : SUCESSO.");
                    // fechar conexão??
                    try {
                        out.close();
                        socket.close();
                    } catch (Exception e) {
                    }
    
                } catch (Exception ex) {
                    System.out.println(servidor + ":" + porta + " : FALHA. " + ex.getMessage());
                    erros++;
                    aErros.add(servidor+":"+porta);
                }
    
                line = reader.readLine();
                //Socket socket = new Socket();
                //socket.connect(new InetSocketAddress(servidor, Integer.valueOf(this.porta)));

            }
            if (erros > 0) {
                System.out.println("-----------------------------------------------");
                System.out.println("Erros : " + erros);
            }
            reader.close();
    
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("===============================================");
    }

}