package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Entity
@Table(name="Message")
public class Message {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String messageType;
	private String sender;
	private String content;
	private long date;
	private int chatroomId;
	
	public Message() {
		// TODO Auto-generated constructor stub
	}
	
	public Message(String serialisedMessage) {
		String[] parts = serialisedMessage.split("-");
		this.messageType = parts[0];
		this.content = parts[2];
		this.sender = parts[3];
        Date datetime = null;

        try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd HH::mm:ss");
			datetime =  format.parse(parts[4]);
		} catch (ParseException e) {
			e.printStackTrace();
		}  

		this.date = datetime.getTime();
	}
	
	public String convertTime(long time) {
		Date date = new Date(time);
		String datetime;
		SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd HH::mm:ss");
		datetime = format.format(date);  
		return datetime;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public int getChatroomId() {
		return chatroomId;
	}

	public void setChatroomId(int chatroomId) {
		this.chatroomId = chatroomId;
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
	
	@Override
	public String toString() {
		return "Message [id=" + id + ", messageType=" + messageType + ", sender=" + sender + ", content=" + content
				+ ", date=" + date + "]";
	}

}
