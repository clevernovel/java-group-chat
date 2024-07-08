package dev.clevernovel.groupchat.server;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.mockito.Mockito.*;

public class ServerTest {

    @Test
    public void testStartServer() throws IOException {
        ServerSocket serverSocket = mock(ServerSocket.class);
        Socket socket = mock(Socket.class);

        when(serverSocket.accept()).thenReturn(socket);
        doAnswer(invocation -> {
            serverSocket.close();
            return null;
        }).when(socket).close();

        Server server = new Server(serverSocket);
        Thread serverThread = new Thread(server::startServer);
        serverThread.start();

        verify(serverSocket).accept();
    }

    @Test
    public void testCloseServerSocket() throws IOException {
        ServerSocket serverSocket = mock(ServerSocket.class);

        Server server = new Server(serverSocket);
        server.closeServerSocket();

        verify(serverSocket).close();
    }
}
