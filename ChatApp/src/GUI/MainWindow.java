package GUI;

import java.util.List;

import client.ChatClient;
import client.ChatController;
import client.Transcript;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class MainWindow extends Application {
	public int groupId = 0;
	private static ChatController chatController;
	private ChatClient chatClient;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parameters params = getParameters();
	        List<String> args = params.getRaw();
	        
	        String hostname = args.get(0);
	        int port = Integer.parseInt(args.get(1));
	        ChatClient client = new ChatClient(hostname, port);
	        client.setUserName(args.get(2));
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../client/ChatGUI.fxml"));
		    Parent root = loader.load();
		    
		    ChatController controller = (ChatController)loader.getController();
		    controller.setModel(new Transcript(args.get(2)));
		    loader.setController(chatController);
		    
		    chatController = controller;

	        client.setChatController(chatController);
	        
	        client.execute();
	        
			Scene scene = new Scene(root,765,590);
			scene.getStylesheets().add("client/styling.css");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getGroupId() {
		return groupId;
	}

	public ChatController getChatController() {
		return chatController;
	}

	public static void setChatController(ChatController chatController) {
		MainWindow.chatController = chatController;
	}
	
    public static void main(String[] args) {
        if (args.length < 2) return;
        
        Application.launch(MainWindow.class, args);


    }

	public ChatClient getChatClient() {
		return chatClient;
	}

	public void setChatClient(ChatClient chatClient) {
		this.chatClient = chatClient;
	}

}