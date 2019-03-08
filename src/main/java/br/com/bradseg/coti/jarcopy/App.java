package br.com.bradseg.coti.jarcopy;

import java.io.IOException;

import br.com.bradseg.coti.jarcopy.util.CommandOptions;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        String type = null;
        String host = null;
        String port = null;
        String filename = null;

        CommandOptions cmd = new CommandOptions(args);
        if (cmd.size() == 0) {
            System.out.println("parametros invalidos");
            System.exit(-1);
        }
        if (cmd.hasOption("server")) {
            type = "server";
        }
        if (cmd.hasOption("-h")) {
            host = cmd.valueOf("-h");
        }
        if (cmd.hasOption("-p")) {
            port = cmd.valueOf("-p");
        }
        System.out.println(type + " | " + host + " | " + port);

        if (type.equals("server")) {
            Server serv = new Server(host, port);
            try {
                serv.startServer();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}