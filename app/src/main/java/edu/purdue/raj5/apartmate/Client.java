package edu.purdue.raj5.apartmate;

import android.util.Log;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Adrian Gerard Raj on 30-09-2018.
 */

public class Client{
    private Socket socket;
    private OutputStream socketOutput;
    private BufferedReader socketInput;

    private String ip;
    private int port;
    private ClientCallback listener = null;

	/*constructor for the Client class*/
    public Client(String ip, int port)
    {
        this.ip = ip;
        this.port = port;
    }

	/*On start, connects the app to the server at ip and port.*/
    public void connect()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                socket = new Socket();
                InetSocketAddress socketAddress = new InetSocketAddress(ip,port);
                try {
                    socket.connect(socketAddress);
                    socketOutput = socket.getOutputStream();
                    socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    new ReceiveThread().start();
                    if(listener!=null)
                    {
                        listener.onConnect(socket);
                    }
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

	/*Disconnects from server.*/
    public void disconnect() throws IOException {

        try
        {
            listener.onDisconnect(socket, "Disconnecting");
         //   socket.close();
        }
        catch (Exception e){
            if(listener!=null){
                listener.onDisconnect(socket, e.getMessage());
            }
        }
    }

	/*Sends the message to the server on the global socket object*/
    public void send(String message){
        try{
            Log.e("Checking: ", message);
            socketOutput.write(message.getBytes());
        }catch(Exception e) {
            Log.e("OOPS","ah");
            e.printStackTrace();
        }
    }

	/*Starts a new thread when there is a connection to the server.*/
    private class ReceiveThread extends Thread implements Runnable{
        public void run(){
            String message;
            try
            {
                while((message =socketInput.readLine()) != null){
                    if(listener!=null)
                        listener.onMessage(message);
                }
            }catch(Exception e){
                if(listener != null)
                {
                    try {
                        listener.onDisconnect(socket, e.getMessage());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

	
    public void setClientCallback(ClientCallback listener){
        this.listener = listener;
    }

    public void removeClientCallback(){
        this.listener = null;
    }

	/*Called when the client recieves something.*/
    public interface ClientCallback{
        void onMessage(String message);
        void onConnect(Socket socket) throws IOException;
        void onDisconnect(Socket socket, String message) throws IOException;
        void onConnectError(Socket socket, String message);

    }

}