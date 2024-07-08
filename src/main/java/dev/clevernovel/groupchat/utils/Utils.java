package dev.clevernovel.groupchat.utils;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {
    private static final Logger logger = Logger.getLogger(Utils.class.getName());

    public static void closeEverything(Socket socket, Closeable... closeables) {
        try {
            if (closeables != null) {
                for (Closeable closeable : closeables) {
                    if (closeable != null) {
                        try {
                            closeable.close();
                        } catch (IOException e) {
                            logger.log(Level.SEVERE, "Error closing resource: " + closeable, e);
                        }
                    }
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Error closing socket", e);
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error closing resources", e);
        }
    }
}