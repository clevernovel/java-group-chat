package dev.clevernovel.groupchat.client;

import dev.clevernovel.groupchat.utils.Utils;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private static final Logger logger = Logger.getLogger(Client.class.getName());

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String userName;

    public Client(Socket socket, String userName) {
        try {
            initClient(socket, userName);
        } catch (IOException e) {
            closeEverything();
            logger.log(Level.SEVERE, "Error initializing client", e);
        }
    }

    public void sendMessage() {
        try {
            writer.write(userName);
            writer.newLine();
            writer.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String messageToSend = scanner.nextLine();
                writer.write(userName + ": " + messageToSend);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            closeEverything();
            logger.log(Level.SEVERE, "Error sending message", e);
        }
    }

    public void listenToMessage() {
        new Thread(() -> {
            String messageFromGroupChat;

            while (socket.isConnected()) {
                try {
                    messageFromGroupChat = reader.readLine();
                    System.out.println(messageFromGroupChat);
                } catch (IOException e) {
                    closeEverything();
                    logger.log(Level.SEVERE, "Error listening to message", e);
                }
            }
        }).start();
    }

    public void closeEverything() {
        Utils.closeEverything(socket, reader, writer);
    }

    private void initClient(Socket socket, String userName) throws IOException {
        this.socket = socket;
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.userName = userName;
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your user name for the group chat: ");
        String userName = scanner.nextLine();
        Socket socket = new Socket("localhost", 1234);
        Client client = new Client(socket, userName);
        client.listenToMessage();
        client.sendMessage();
    }
}
