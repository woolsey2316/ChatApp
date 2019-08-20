package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

 
/**
 * This thread handles connection for each connected client, so the server
 * can handle multiple clients at the same time.
 *
 * @author David Woolsey
 */
public class UserThread extends Thread {
    private Socket socket;
    private ChatServer server;
    private PrintWriter writer;
    private String username;
    private Set<Integer> groupMembershipList;
 
    public UserThread(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
        groupMembershipList = new HashSet<Integer>();
        groupMembershipList.add(0);
    }
 
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
 
            username = reader.readLine();
            
            printUsers();
 
            String clientMessage;
 
            do {
                clientMessage = reader.readLine();
                server.broadcast(clientMessage, this);
 
            } while (!clientMessage.equals("bye"));
 
            server.removeUser(username, this);
            socket.close();
 
        } catch (IOException ex) {
            System.out.println("Error in UserThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    /**
     * Sends a list of online users to the newly connected user.
     */
    void printUsers() {
        if (server.hasUsers(this)) {
            writer.println("ONLINE_STATUS-0-Connected users: " + server.getUserNames(0)+ "-server");
        } else {
            writer.println("ONLINE_STATUS-0-No other users connected-server");
        }
    }
 
    /**
     * Sends a message to the client.
     */
    void sendMessage(String message) {
        writer.println(message);
    }
    
    void setUsername(String usrname) {
    	username = usrname;
    }
    
    String getUsername() {
    	return username;
    }
    
    void addGroupId(int id) {
    	groupMembershipList.add(id);
    }
    
    Set<Integer> getGroupIdList() {
    	return groupMembershipList;
    }
    
    boolean isGroupMember(int id) {
    	for (Integer elem : groupMembershipList) {
    		if (elem == id) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public boolean equals(UserThread other)
    {
       return username == other.getUsername();
    }
}