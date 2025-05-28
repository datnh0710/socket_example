package com.datnh;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;


/**
 * @author datnh0710
 * @created 16/09/2021 - 1:16 PM
 * @packege com.datnh
 * @project Assigment_1
 */
public class Server {
    private int port = 0;
    private final Set<String> userName = new HashSet<>();
    private final Set<ServerThread> serverThreads = new HashSet<>();

    /**
     * Constructor of Server
     * @param port
     */
    public Server(int port) {
        this.port = port;
    }

    /**
     * executed the new thread to handle the new User connection
     *
     */
    public void executed() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Chat Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                ServerThread newUser = new ServerThread(socket, this);
                serverThreads.add(newUser);
                newUser.start();
            }
        } catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * broadcast: deliver messages from a user to other user in the chat room
     *
     * @param messages mess from a user to other user
     * @param executeUser   this is the user that want to message to other user
     */
    void broadcastMessages(String messages, ServerThread executeUser) {
        for (ServerThread oUser : serverThreads) {
            if (oUser != executeUser) {
                oUser.sendMessage(messages);
            }
        }
    }

    /**
     * Stores the new username
     *
     * @param newUserName
     */
    void addUserName(String newUserName) {
        userName.add(newUserName);
    }

    /**
     * return the list of activate User in the thread pool
     *
     * @return
     */
    public Set<String> getUserName() {
        return this.userName;
    }

    /**
     * Returns true if there are other users connected (not count the currently connected user)
     *
     * @return
     */
    public boolean hasUsers() {
        return !this.userName.isEmpty();
    }

    /**
     * When a client is disconnected, removes the associated username and ServerThread
     *
     * @param username
     * @param exUser
     */
    void removeUser(String username, ServerThread exUser) {
        boolean removed = userName.remove(username);
        if (removed) {
            serverThreads.remove(exUser);
            System.out.println("The user " + username + " quited!");
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Syntax: java Server <port-number>");
            System.exit(0);

        }
        int port = Integer.parseInt(args[0]);

        Server server = new Server(port);
        server.executed();

    }
}
