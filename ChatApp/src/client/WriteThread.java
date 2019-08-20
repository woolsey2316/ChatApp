package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Queue;
 
/**
 * This thread is responsible for reading user's input and sending it
 * to the server.
 * It runs in an infinite loop until the user types 'exit' to quit.
 *
 * @author David Woolsey
 */
public class WriteThread extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private ChatClient client;
 
    public WriteThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;
 
        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    public void run() {
    	writer.println(client.getUsername());
 
        do {
        	Queue<String> messageQueue = client.getChatController().
        			getChatHistory().getMessageQueue();
        	
    		while (!messageQueue.isEmpty()) {
    			
    			String message = messageQueue.poll();
        		writer.println(message);
	
    		}
    		try {
				sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        		
        } while (!client.hasExited());
        
        try {
            socket.close();
        } catch (IOException ex) {
 
            System.out.println("Error writing to server: " + ex.getMessage());
        }
    }
}