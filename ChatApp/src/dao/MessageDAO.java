package dao;

import java.util.List;

import model.Message;

public interface MessageDAO {

	public void save(Message p);
	
	public List<Message> getTranscript(int groupId);
	
}