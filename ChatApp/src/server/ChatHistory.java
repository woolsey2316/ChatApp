package server;

import java.util.HashMap;
import java.util.List;

import model.Message;

public class ChatHistory {
	
	public int acknowledgement;
	public HashMap<Integer,List<Message>> messageList;
	
	public List<Message> getMessageList(int index) {
		return messageList.get(index);
		
	}
	
	public void addMessage(Message msg, int groupId) {
		List<Message> transcript = messageList.get(groupId);
		transcript.add(msg);
		messageList.put(groupId,transcript);
		
	}
	

}
