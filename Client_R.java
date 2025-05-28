package com.datnh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author datnh0710
 * @created 16/09/2021 - 10:12 PM
 * @packege com.datnh
 * @project Assigment_1
 */
public class Client_R extends Thread {
    private BufferedReader reader = null;
    private final Socket socket;
    private final Client client;

    public Client_R(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
        try {
            InputStream inputStream = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
        } catch (IOException ex) {
            System.out.println("Error getting input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Receiving messages from the Server side
     */
    @Override
    public void run() {
        while (true) {
            try {
                String res = reader.readLine();
                System.out.println("\n" + res);

                // prints the username after displaying the server's message
                if (client.getUserName() != null) {
                    System.out.print("[" + client.getUserName() + "]: ");
                }
            } catch (IOException ex) {
                System.out.println("Error reading from server: " + ex.getMessage());
                ex.printStackTrace();
                break;
            }

        }
    }
}
