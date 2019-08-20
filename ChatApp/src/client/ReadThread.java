package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;

import javafx.application.Platform;
 
/**
 * This thread is responsible for reading server's input and printing it
 * to the console.
 * It runs in an infinite loop until the client disconnects from the server.
 *
 * @author David Woolsey
 */
public class ReadThread extends Thread {
    private BufferedReader reader;
    private Socket socket;
    private ChatClient client;
 
    public ReadThread(Socket socket,ChatClient client) {
        this.socket = socket;
        this.client = client;
 
        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException ex) {
            System.out.println("Error getting input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    public void run() {
        while (true) {
            try {
                String response = reader.readLine();
                
                client.getChatController().receiveMessage(response,
                		new Date().getTime());
                Platform.runLater(new Runnable() {

					@Override
					public void run() {
	                	client.getChatController().refreshMessageDisplay();
						
					}

                }); 

            } catch (IOException ex) {
                System.out.println("Error reading from server: " 
                					+ ex.getMessage());
                ex.printStackTrace();
                break;
            }
        }
    }
}