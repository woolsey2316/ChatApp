package dao;

import java.util.LinkedList;
import java.util.Queue;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.Message;

import java.util.List;

public class Transcript implements MessageDAO {
	private SessionFactory sessionFactory;
	//messages are stored here before they are sent to the server
	private Queue<String> messageQueue;
	private String username;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Transcript() {
		this.messageQueue = new LinkedList<String>();
				
	}
	
	public Transcript(String string) {
		username = string;
		this.messageQueue = new LinkedList<String>();
	}

	@Override
	public void save(Message msg) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(msg);
		tx.commit();
		session.close();
	}

	public void addMessage(String ownMessage) {
		messageQueue.add(ownMessage);
		
	}
	
	public Queue<String> getMessageQueue() {
		return messageQueue;
	}

	public void setUsername(String text) {
		username = text;
		
	}

	public String getUsername() {
		return username;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Message> getTranscript(int groupId) {
		Session session = this.sessionFactory.openSession();
		List<Message> personList = session
				.createQuery("from Message where chatroomID=" + groupId).list();
		session.close();
		return personList;
	}
	
}