package br.com.bradseg.coti.jarcopy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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

    public Server() {}
    public Server(String host, String port) {
        this.host = host;
        this.port = port;
    } 

    public void startServer() throws IOException {
        // create socket
        servsock = new ServerSocket(Integer.valueOf(this.port));

        while (true) {
            System.out.println("Waiting...");
            Socket sock = servsock.accept();
            System.out.println("Accepted connection : " + sock);

            /*
            // sendfile
            File myFile = new File(args[0]);
            FileInputStream in = new FileInputStream(myFile);
            OutputStream out = sock.getOutputStream();
            System.out.println("Sending...");

            byte[] mb = new byte[1024];
            for (int c = in.read(mb); c > -1; c = in.read(mb)) {
                out.write(mb, 0, c);
            }
            */
        }        
    }
}