package br.com.bradseg.coti.jarcopy;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Manifest;

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
        if (cmd.hasOption("-version") || cmd.hasOption("-v")) {
            System.out.println("SSV File Copy versão 1.0");
            return;
        }
        if (cmd.hasOption("server") || cmd.hasOption("-server") || cmd.hasOption("-s")) {
            String porta = cmd.valueOf("-porta");
            String caminho = cmd.valueOf("-caminho");
            //System.out.println("porta: [" + porta+"]");
            //System.out.println("caminho: [" + caminho+"]");
            if (!cmd.hasOption("-porta")) {
                System.out.println("Falta parametro '-porta'. Ex: -porta 12000");
                return;
            }
            if (!cmd.hasOption("-caminho")) {
                System.out.println("Falta parametro '-caminho'. Ex: -caminho /mnt/sharedlib/");
                return;
            }
            try {
                // inicia servidor
                Server server = new Server(porta, caminho);
                server.startServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (cmd.hasOption("client") || cmd.hasOption("-client") || cmd.hasOption("-c")) {
            if (!cmd.hasOption("-porta")) {
                System.out.println("Falta parametro '-porta'. Ex: -porta 12000");
                return;
            }

            if (!cmd.hasOption("-servidores")) {
                System.out.println("Falta parametro '-servidores'. Ex: -servidores c:\\temp\\servidores.txt");
                return;
            }
            String porta = cmd.valueOf("-porta");
            String arquivo = cmd.valueOf("-servidores");
            Client client = new Client(porta, arquivo);
            try {
                client.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}