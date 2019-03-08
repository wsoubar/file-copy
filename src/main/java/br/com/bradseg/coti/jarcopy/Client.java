package br.com.bradseg.coti.jarcopy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Client
 */
public class Client {

    public static void main(String[] args) throws IOException {

        // localhost for testing
        Socket sock = new Socket("127.0.0.1", 4444);
        System.out.println("Connecting...");

        // receive file
        InputStream in = sock.getInputStream();
        FileOutputStream out = new FileOutputStream(args[0]);

        byte[] mb = new byte[1024];

        for (int c = in.read(mb, 0, 1024); c > 0; c = in.read(mb, 0, 1024)) {
            out.write(mb, 0, c);
        }

        out.close();
        sock.close();
    }
}