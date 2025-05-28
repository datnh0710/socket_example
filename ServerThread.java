package com.datnh;

import java.io.*;
import java.net.Socket;

/**
 * @author datnh0710
 * @created 16/09/2021 - 1:22 PM
 * @packege com.datnh
 * @project Assigment_1
 */
public class ServerThread extends Thread {
    private final Socket socket;
    private final Server server;
    private PrintWriter printerWriter = null;

    public ServerThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    /**
     * create the new thread for the new User connection
     */
    @Override
    public void run() {
        try {

            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            OutputStream outputStream = socket.getOutputStream();
            printerWriter = new PrintWriter(outputStream, true);

            String userName = reader.readLine();
            server.addUserName(userName);

            String serverMessages = "Welcome:  " + userName;
            System.out.println("Welcome: " + userName);
            server.broadcastMessages(serverMessages, this);

            String clientMessages = "";
            boolean check = false;
            do {
                clientMessages = reader.readLine();

                serverMessages = "[" + userName + "]: " + clientMessages;

                System.out.println(serverMessages);
                if (clientMessages.equals("AllUsers")) {
                    getAllUser();
                    continue;
                }
                server.broadcastMessages(serverMessages, this);
            } while (!clientMessages.equals("bye"));

            serverMessages = "Good Bye:" + userName;
            server.removeUser(userName, this);
            socket.close();
            server.broadcastMessages(serverMessages, this);

        } catch (IOException ex) {
            System.out.println("Error in ServerThread: " + ex.getMessage());
            ex.printStackTrace();
        }
        super.run();
    }

    /**
     * sent list of all online users to the user
     */
    void getAllUser() {
        if (server.hasUsers()) {
            printerWriter.println("Connected users: " + server.getUserName());
        } else {
            printerWriter.println("No other users connected");
        }
    }

    /**
     * send the messages to the user
     *
     * @param messages
     */
    public void sendMessage(String messages) {
        printerWriter.println(messages);
    }
}
