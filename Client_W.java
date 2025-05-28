package com.datnh;

import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author datnh0710
 * @created 16/09/2021 - 10:12 PM
 * @packege com.datnh
 * @project Assigment_1
 */
public class Client_W extends Thread {
    private PrintWriter printWriter = null;
    private final Socket socket;
    private final Client client;

    public Client_W(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream outputStream = socket.getOutputStream();
            printWriter = new PrintWriter(outputStream, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    @Override
    public void run() {
        Console console = System.console();
        String userName = console.readLine("\n Enter your name: ");
        client.setUserName(userName);
        printWriter.println(userName);

        String text = "";
        do {
            text = console.readLine("[" + userName + "]: ");
            printWriter.println(text);
        } while (!text.equals("bye"));

        try {
            socket.close();
        } catch (IOException ex) {
            System.out.println("Error writing to server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
