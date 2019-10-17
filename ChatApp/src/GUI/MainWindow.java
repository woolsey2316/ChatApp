package GUI;

import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import client.ChatClient;
import client.ChatController;
import dao.Transcript;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class MainWindow extends Application {
	public int groupId = 0;
	private static ChatController chatController;
	private ChatClient chatClient;
	private ClassPathXmlApplicationContext context;
	private Parent root;
	
	 @Override
	    public void init() throws Exception {
	        SpringApplicationBuilder builder = 
	        		new SpringApplicationBuilder(MainWindow.class);
			
	        Parameters params = getParameters();
	        List<String> args = params.getRaw();
	        
	        String hostname = args.get(0);
	        int port = Integer.parseInt(args.get(1));
	        ChatClient client = new ChatClient(hostname, port);
	        
	        context = new ClassPathXmlApplicationContext("spring.xml");
	        //context = builder.run(getParameters().getRaw().toArray(new String[0]));
	 
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("../client/ChatGUI.fxml"));
	        root = loader.load();
	        loader.setControllerFactory(context::getBean);
	        
		    ChatController controller = (ChatController)loader.getController();
		    Transcript transcript = context.getBean(Transcript.class);
		    transcript.setUsername(args.get(2));
		    controller.setModel(transcript);
		    loader.setController(chatController);
		    
		    chatController = controller;

	        client.setChatController(chatController);
	        
	        client.execute();
	    }
	 
	@Override
	public void start(Stage primaryStage) {
		try {
	        
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