package dev.clevernovel.groupchat.utils;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

import static org.mockito.Mockito.*;

public class UtilsTest {

    @Test
    public void testCloseEverything() throws IOException {
        Socket socket = mock(Socket.class);
        BufferedReader reader = mock(BufferedReader.class);
        BufferedWriter writer = mock(BufferedWriter.class);

        Utils.closeEverything(socket, reader, writer);

        verify(reader).close();
        verify(writer).close();
        verify(socket).close();
    }

    @Test
    public void testCloseEverythingWithException() throws IOException {
        Socket socket = mock(Socket.class);
        Closeable closeable = mock(Closeable.class);

        doThrow(new IOException()).when(closeable).close();

        Utils.closeEverything(socket, closeable);

        verify(socket).close();
    }
}
