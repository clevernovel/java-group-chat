package dev.clevernovel.groupchat.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;

import static org.mockito.Mockito.*;

public class ClientTest {

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private Client client;

    @BeforeEach
    public void setUp() throws IOException {
        socket = mock(Socket.class);
        reader = mock(BufferedReader.class);
        writer = mock(BufferedWriter.class);

        when(socket.getInputStream()).thenReturn(new ByteArrayInputStream("testUser\n".getBytes()));
        when(socket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        client = new Client(socket, "testUser");
    }

    @AfterEach
    public void tearDown() throws IOException {
        client.closeEverything();
    }

    @Test
    public void testSendMessage() throws IOException {
        client.sendMessage();
        verify(writer, atLeastOnce()).write(anyString());
    }

    @Test
    public void testListenToMessage() throws IOException {
        Thread thread = new Thread(client::listenToMessage);
        thread.start();
        verify(reader, atLeastOnce()).readLine();
    }
}