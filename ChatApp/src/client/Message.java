package client;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Message {
	private String messageType;
	private String sender;
	private String content;
	private long time;
	
	public Message(String sender, String content, long time,String messageType) {
		this.sender = sender;
		this.content = content;
		this.time = time;
		this.messageType = messageType;
	}
	
	public Message(String serialisedMessage) {
		String[] parts = serialisedMessage.split("-");
        String messageType = parts[0];
        String message = parts[2];
        String username = parts[3];
        Date date = null;

        try {
			date=new SimpleDateFormat("dd/MM/yyyy").parse(parts[4]);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
        
		this.sender = username;
		this.content = message;
		this.time = date.getTime();
		this.messageType = messageType;
	}
	
	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
