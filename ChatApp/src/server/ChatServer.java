package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * This class starts the server, listening on a specific port. 
 * When a new client connects, an instance of 
 * UserThread is created serving the client.
 *
 * @author David Woolsey
 */
public class ChatServer {
    private int port;
    //key = groupId; value = set of users
    private HashMap<Integer,Set<UserThread>> userThreads;
 
    public ChatServer(int port) {
        this.port = port;
        userThreads = new HashMap<>();
    }
 
    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
 
            System.out.println("Chat Server is listening on port " + port);
 
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected");
 
                UserThread newUser = new UserThread(socket, this);
                addNewUser(newUser);
                
                newUser.start();
 
            }
 
        } catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void addNewUser(UserThread usrThread) {
    	Set<UserThread> usrThreadSet = userThreads.get(0);
    	if ( usrThreadSet != null) {
    		usrThreadSet.add(usrThread);
    	} else {
    		usrThreadSet = new HashSet<UserThread>();
    		usrThreadSet.add(usrThread);
    		
    	}
    	userThreads.put(0,usrThreadSet);
    }
 
    /**
     * Delivers a message from one user to all others
     */
    
    void broadcast(String message, UserThread itself) {
    	String[] parts = message.split("-");
        int chatGroupId = Integer.parseInt(parts[1]);
        for (UserThread aUser : userThreads.get(chatGroupId)) {
            if (aUser != itself ) {
                aUser.sendMessage(message);
            }
        }
    }
 
    /**
     * When a client disconnects, the associated username and UserThread
     * is removed
     */
    void removeUser(String userName, UserThread aUser) {
        boolean removedOnce = false;
    	for (Integer index : userThreads.keySet()) {
    		boolean removed = userThreads.get(index).remove(aUser);
            if (removed) {
            	removedOnce = true;
            }
    		
    	}
    	if (removedOnce) {
    		System.out.println("User " + userName + "has left the chat");
    	}
    	
    }
    
    /**
     * Returns the usernames belonging to a particular group chat if there are 
     * other users connected (not including the currently connected user)
     */
 
    Set<String> getUserNames(int groupId) {
    	Set<String> userNames = new HashSet<>();
        for (UserThread elem : userThreads.get(groupId)) {
        	userNames.add(elem.getUsername());
        }
        return userNames;
        		
    }
 
    /**
     * Returns true if there are other users connected (not including the 
     * currently connected user)
     */
    boolean hasUsers(UserThread itself) {
        for (Set<UserThread> elem : userThreads.values()) {
        	if (!elem.isEmpty()) {
        		if (elem.equals(itself)) {
        			return true;
        		}
        	}
        }
        return false;
    }
    
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntax: java ChatServer <port-number>");
            System.exit(0);
        }
 
        int port = Integer.parseInt(args[0]);
 
        ChatServer server = new ChatServer(port);
        server.execute();
    }
}