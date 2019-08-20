package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import GUI.MainWindow;
import javafx.application.Application;

/**
 * This is the chat client program.
 * Type 'bye' to terminate the program.
 *
 * @author David Woolsey
 */
public class ChatClient {
    private String hostname;
    private int port;
	private String userName;
    private List<Integer> chatGroupMemberships;
    private int chatGroupId;
    private boolean hasExited;
    private ChatController chatController;
 
    public ChatClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        chatGroupId = 0;
        hasExited = false;
    }
 
    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);
  
            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();
            
 
        } catch (UnknownHostException ex) {
            //System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            //System.out.println("I/O Error: " + ex.getMessage());
        }
 
    }
    
    public void refreshMessageDisplay() {
    	
	}
    
    List<Integer> getChatGroupMembershipList() {
    	return chatGroupMemberships;
    }
 
    public void setUserName(String userName) {
        this.userName = userName;
    }
 
    public String getUsername() {
        return userName;
    }
    
    public int getChatGroupId() {
    	return chatGroupId; 
    }
    
    public void setChatGroupId(int cgId) {
    	chatGroupId = cgId;
    }
    
    boolean hasExited() {
    	return hasExited;
    }
 
    void setExited(boolean hasExit) {
    	hasExited = hasExit;
    }
    
    public ChatController getChatController() {
		return chatController;
	}

	public void setChatController(ChatController chatController) {
		this.chatController = chatController;
	}
 
}