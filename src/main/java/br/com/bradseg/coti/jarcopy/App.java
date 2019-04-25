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

        CommandOptions cmd = new CommandOptions(args);
        if (cmd.size() == 0) {
            System.out.println("parametros invalidos");
            System.exit(-1);
        }
        if (cmd.hasOption("server")) {
            type = "server";
        }
        if (cmd.hasOption("client")) {
            type = "client";
        }
        System.out.println(type);

        if (type.equals("server")) {
            System.out.println("modo servidor");
            Server server = new Server();
            try {
                server.startServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("client")) {
            System.out.println("modo cliente");
            Client client = new Client();
            try {
                client.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}