package sockets;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by C.Lucas on 08/10/2015.
 */
public class SocketSendMessage {

    private static final String EXTRA_INFO = "livro";
    private Socket socket;
    private OutputStream out;
    private InputStream in;

    public SocketSendMessage(String local, int door) throws UnknownHostException, IOException {
        this.socket = new Socket(local, door);
        this.out = socket.getOutputStream();
        this.in = socket.getInputStream();
    }

    public void down() {
        try {
            this.socket.close();
            this.out.close();
            this.in.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String sendMessage(String message) throws IOException {
        DataOutputStream outData = new DataOutputStream(this.out);
        DataInputStream inData = new DataInputStream(this.in);
        outData.write(message.getBytes());
        outData.flush();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inData));
        String in = buffer.readLine();
        Log.d(EXTRA_INFO, in);
        down();
        return in;
    }
}
