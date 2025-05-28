package com.datnh;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author datnh0710
 * @created 16/09/2021 - 1:17 PM
 * @packege com.datnh
 * @project Assigment_1
 */
public class Client {
    private String hostName ="";
    private int port  =0;
    private String userName="";

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public Client(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    public void execute() {
        try {
            Socket socket = new Socket(hostName, port);

            System.out.println("Connected to the chat server");

            new Client_R(socket, this).start();
            new Client_W(socket, this).start();
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Syntax: java client <host-name> <port-number>");
            System.exit(0);

        }
        String hostname = args[0];
        int port = Integer.parseInt(args[1]);

        Client client = new Client(hostname, port);
        client.execute();
    }
}
