package dev.clevernovel.groupchat.handlers;


import dev.clevernovel.groupchat.utils.Utils;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {

    private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());
    public static List<ClientHandler> clientHandlers = new ArrayList<>();

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String clientUserName;

    public ClientHandler(Socket socket) {
        try {
            initClientHandler(socket);
            clientHandlers.add(this);
            broadcastMessage("SERVER: " + clientUserName + " has entered the chat...");
        } catch (IOException e) {
            closeEverything();
            logger.log(Level.SEVERE, "Error initializing ClientHandler", e);
        }
    }

    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()) {
            try {
                messageFromClient = reader.readLine();
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                closeEverything();
                logger.log(Level.SEVERE, "Error reading message from client", e);
                break;
            }
        }
    }

    public void closeEverything() {
        removeClientHandler();
        Utils.closeEverything(socket, reader, writer);
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage("SERVER: " + clientUserName + " has left the chat...");
    }

    private void initClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.clientUserName = reader.readLine();
    }

    protected void broadcastMessage(String messageToSend) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientUserName.equals(clientUserName)) {
                    clientHandler.writer.write(messageToSend);
                    clientHandler.writer.newLine();
                    clientHandler.writer.flush();
                }
            } catch (IOException e) {
                closeEverything();
                logger.log(Level.SEVERE, "Error broadcasting message", e);
            }
        }
    }
}
