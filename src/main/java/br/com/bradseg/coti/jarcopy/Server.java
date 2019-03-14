package br.com.bradseg.coti.jarcopy;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Server
 */
public class Server {
    public static void main(String[] args) throws IOException {
        // create socket
        ServerSocket servsock = new ServerSocket(4444);

        while (true) {
            System.out.println("Waiting...");
            Socket sock = servsock.accept();
            System.out.println("Accepted connection : " + sock);

            // sendfile
            File myFile = new File(args[0]);
            FileInputStream in = new FileInputStream(myFile);
            OutputStream out = sock.getOutputStream();
            System.out.println("Sending...");

            byte[] mb = new byte[1024];
            for (int c = in.read(mb); c > -1; c = in.read(mb)) {
                out.write(mb, 0, c);
            }
        }
    }

    private String host;
    private String port;
    private ServerSocket servsock = null;
    private final static Logger LOGGER = Logger.getLogger(Client.class.getName());

    public Server() {
        LOGGER.setLevel(Level.INFO);
    }

    public Server(String host, String port) {
        this.host = host;
        this.port = port;
        LOGGER.setLevel(Level.INFO);
    }

    public void startServer() throws IOException {
        // create socket
        servsock = new ServerSocket(Integer.valueOf(this.port));

        while (true) {
            LOGGER.info("Waiting...");
            Socket sock = servsock.accept();
            LOGGER.info("Accepted connection : " + sock);

            // sendfile
            BufferedInputStream in = new BufferedInputStream(sock.getInputStream());
            DataInputStream d = new DataInputStream(in);
            try {
                String origFileName = d.readUTF();
                File origFile = new File(origFileName);
                String destFileName = "C:/dev/java/jar-copy/sharedlib/" + origFile.getName();
                LOGGER.info("--- origFileName ---");
                LOGGER.info(origFileName);
                LOGGER.info(Paths.get(origFileName).toString());
                LOGGER.info("--- destFileName ---");
                LOGGER.info(destFileName);
                LOGGER.info(Paths.get(destFileName).toString());
                Files.copy(d, Paths.get(destFileName), StandardCopyOption.REPLACE_EXISTING);
                LOGGER.info("arquivo salvo");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                d.close();
            }
        }
    }
}