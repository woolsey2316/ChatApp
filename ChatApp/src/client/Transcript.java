package client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;

public class Transcript {
	private HashMap<Integer,List<Message>> messageList;
	private Queue<String> messageQueue;
	private String username;
	
	public Transcript() {
		this.messageList = new HashMap<Integer,List<Message>>();
		this.messageQueue = new LinkedList<String>();
				
	}
	
	public Transcript(String usrname) {
		this.username =  usrname;
		this.messageList = new HashMap<Integer,List<Message>>();
		this.messageQueue = new LinkedList<String>();
				
	}
	
	public void addMessage(Message msg, int groupId) {
		List<Message> transcript = messageList.get(groupId);
		if (transcript == null) {
			List<Message> list = new ArrayList<Message>();
			list.add(msg);
			messageList.put(groupId, list);
		} else {
			messageList.get(groupId).add(msg);
		}
	}
	
	public List<Message> getTranscript(int ind) {
		return messageList.get(ind);
		
	}

	public void addMessage(String ownMessage) {
		messageQueue.add(ownMessage);
		
	}
	
	Queue<String> getMessageQueue() {
		return messageQueue;
	}

	public void setUsername(String text) {
		username = text;
		
	}

	public String getUsername() {
		return username;
	}
	
	

}
