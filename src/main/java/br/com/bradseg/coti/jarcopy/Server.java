package br.com.bradseg.coti.jarcopy;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Server
 */
public class Server {

    private ServerSocket servsock = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public Server() {
    }

    public void startServer() throws IOException {
        // create socket
        Properties props = new Properties();
        String port = null;
        String pathDestino = null;

        try {
            props.load(new FileInputStream("server.properties"));
            port = props.getProperty("porta");
            pathDestino = props.getProperty("caminho.bradseg.servicos");
            System.out.println("escutando na porta: " + port);
            System.out.println("destino: " + pathDestino);
            servsock = new ServerSocket(Integer.valueOf(port));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e);
            throw new IOException(e);
        }

        while (true) {
            BufferedInputStream bin = null;
            DataInputStream dis = null;
            try {
                System.out.println("Aguardando conexao...");
                Socket sock = servsock.accept();
                System.out.println("Conexao iniciada : " + sdf.format(new Date()));
                System.out.println(sock);
                // sendfile
                bin = new BufferedInputStream(sock.getInputStream());
                dis = new DataInputStream(bin);
                String origFileName = dis.readUTF();
                File origFile = new File(origFileName);
                System.out.println("recebendo : " + origFileName);
                String destFileName = pathDestino + origFile.getName();
                System.out.println("destino : " + destFileName);
                Files.copy(dis, Paths.get(destFileName), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("arquivo salvo com sucesso");
                System.out.println("-----------------------------------------------");
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("-----------------------------------------------");
                try {
                    dis.close();
                    bin.close();
                } catch (Exception ex) {
                    // não faz nada
                }
            }
        }
    }
}