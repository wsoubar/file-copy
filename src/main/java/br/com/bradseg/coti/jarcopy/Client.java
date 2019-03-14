package br.com.bradseg.coti.jarcopy;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client
 */
public class Client {

    private final static Logger LOGGER = Logger.getLogger(Client.class.getName());

    public static void main(String[] args) throws IOException {
        LOGGER.setLevel(Level.INFO);
        LOGGER.info("main method");
        String filename ="C:/dev/java/BSAD-PdfBox-1.2.jar";
        File file = new File(filename);
        // localhost for testing
        //Socket socket = new Socket("localhost", 4444);
        Socket socket = new Socket();
        //socket.setSoTimeout(5000);
        socket.connect(new InetSocketAddress("localhost", 4445), 5000);
        LOGGER.info("Connecting...");

        // receive file
        //InputStream in = sock.getInputStream();
        BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
        try {
            DataOutputStream d = new DataOutputStream(out);
            d.writeUTF(filename);
            Files.copy(file.toPath(), d);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            out.close();
            socket.close();
            LOGGER.info("Fim client"); 
        }
    }
}