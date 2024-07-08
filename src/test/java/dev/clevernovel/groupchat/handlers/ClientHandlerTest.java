package dev.clevernovel.groupchat.handlers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;

import static org.mockito.Mockito.*;

public class ClientHandlerTest {

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private ClientHandler clientHandler;

    @BeforeEach
    public void setUp() throws IOException {
        socket = mock(Socket.class);
        reader = mock(BufferedReader.class);
        writer = mock(BufferedWriter.class);

        when(socket.getInputStream()).thenReturn(new ByteArrayInputStream("testUser\n".getBytes()));
        when(socket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        clientHandler = new ClientHandler(socket);
    }

    @AfterEach
    public void tearDown() throws IOException {
        clientHandler.closeEverything();
    }

    @Test
    public void testBroadcastMessage() throws IOException {
        ClientHandler.clientHandlers.add(clientHandler);
        clientHandler.broadcastMessage("Test message");

        verify(writer, never()).write(anyString());
    }
}
