package client;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import GUI.LoginManager;
import dao.Transcript;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import model.Message;

public class ChatController implements Initializable{
	Transcript model;
	@FXML
	public TextArea messageInput;
	@FXML
	public VBox messagePane;
	@FXML
	public TextArea editUsername;
	@FXML
	public ImageView sendimage;
	@FXML
	public Pane chatroomPane;
	public int groupId = 0;
	
	public ChatController() {

	}
	
	public ChatController(Transcript model) {
		this.model = model;
	}
	
	public void setModel(Transcript model) {
		this.model = model;
	}

	public void receiveMessage(String response, long date) {
        String[] parts = response.split("-");
        Message message = new Message();
        message.setMessageType(parts[0]);
        message.setChatroomId(Integer.parseInt(parts[1]));
        message.setContent(parts[2]);
        message.setSender(parts[3]);
        message.setDate(date);
        model.save(message);
		
	}

	public void refreshMessageDisplay() {
		//get transcript list from client-side storage
		List<Message> transcript = model.getTranscript(groupId);
		
		for (Message message : transcript) {
		    if (message.getMessageType().equals("MESSAGE")) {
	    		displayConversation(message);
	    	} else if (message.getMessageType().equals("ONLINE_STATUS")) {
	    		displayStatusMessage(message);
	    	}
		}
		
		
	}
	
	public void displayMessage(final String message) {

		String[] parts = message.split("-");
    	
    	if (parts[0] == "MESSAGE") {
    		displayConversation(message);
    	} else if (parts[0] == "STATUS") {
    		displayStatusMessage(message);
    	}

	}
	
	public void displayOwnMessage(String message) {
		
    	FlowPane flowPane = new FlowPane();
    	flowPane.setAlignment(Pos.TOP_LEFT);
    	flowPane.setPadding(new Insets(10,0,10,0));
    	Text text = new Text();
        text.setText(message);
        text.getStyleClass().add("sent-message");
        
        Font font = Font.font("verdana", FontWeight.NORMAL, 12);
        text.setFont(font);
        
        final TextFlow textFlow = new TextFlow(text);
        textFlow.setPrefWidth(300);
        textFlow.setPrefHeight(30);
        textFlow.setPadding(new Insets(10,10,10,10));
    	flowPane.setMargin(textFlow, new Insets(0,0,0,15));
        
        Background background = new Background(new BackgroundFill(Color.rgb(255, 255, 255), 
        		new CornerRadii(20, 20, 20, 0, false), Insets.EMPTY));
        textFlow.setBackground(background);
    	
    	flowPane.getChildren().add(textFlow);

    	messagePane.getChildren().add(flowPane);
		
	}
	
	// normal message sent
	public void displayConversation(String message) {
		String[] parts = message.split("-");
		
    	FlowPane flowPane = new FlowPane();
    	flowPane.setAlignment(Pos.TOP_RIGHT);
    	flowPane.setPadding(new Insets(10,0,10,0));
    	Text text = new Text();
        text.setText(message);
        
        Font font = Font.font("verdana", FontWeight.BOLD, 11);
        text.setFont(font);
        
        final TextFlow textFlow = new TextFlow(text);
        textFlow.setPrefWidth(300);
        textFlow.setPrefHeight(30);
        textFlow.setPadding(new Insets(10,10,10,10));
    	
    	Image img = new Image("client/images/avatar1.png");
    	ImageView imgview = new ImageView(img);
    	imgview.setFitHeight(60);
    	imgview.setFitWidth(60);
    	
    	flowPane.getChildren().addAll(imgview,textFlow);
    	messagePane.getChildren().add(flowPane);
		
	}
	
	//normal message sent
	public void displayConversation(Message message) {
    	FlowPane flowPane = new FlowPane();
    	flowPane.setAlignment(Pos.TOP_RIGHT);
    	flowPane.setPadding(new Insets(10,0,10,0));
    	
    	Text text = new Text();
        text.setText(message.getContent());
        text.getStyleClass().add("received-message");
        
        Font font = Font.font("verdana", FontWeight.BOLD, 11);
        text.setFont(font);
        text.setFill(Color.WHITE);
        
        final TextFlow textFlow = new TextFlow(text);
        textFlow.setPrefWidth(300);
        textFlow.setPrefHeight(30);
        textFlow.setPadding(new Insets(10,10,10,10));
        
        Background background = new Background(new BackgroundFill(Color.rgb(0, 151, 193),
        		new CornerRadii(20, 20, 0, 20, false), Insets.EMPTY));
        textFlow.setBackground(background);
    	
    	Image img = new Image("client/images/avatar1.png");
    	ImageView imgview = new ImageView(img);
    	imgview.setFitHeight(60);
    	imgview.setFitWidth(60);
    	
    	flowPane.getChildren().addAll(textFlow,imgview);
    	messagePane.getChildren().add(flowPane);
		
	}
	
	//status message (eg joined or left chat)
	public void displayStatusMessage(Message message) {
		FlowPane flowPane = new FlowPane();
    	flowPane.setAlignment(Pos.CENTER);
    	flowPane.setPadding(new Insets(10,0,10,0));
    	
    	Text text = new Text();
        text.setText(message.getContent());
        
        Font font = Font.font("verdana", FontWeight.BOLD, 11);
        text.setFont(font);
        
        final TextFlow textFlow = new TextFlow(text);
             
        //textFlow.setPrefWidth(100);
        textFlow.setMaxWidth(200);
        textFlow.setPrefHeight(15);
        textFlow.setTextAlignment(TextAlignment.CENTER);
        Border border = new Border(new BorderStroke(Color.WHITE, 
        		BorderStrokeStyle.SOLID, new CornerRadii(5),
        		new BorderWidths(1)));
        textFlow.setBorder(border);
        
        Background background = new Background(new BackgroundFill(Color.WHITE, 
        		new CornerRadii(5), Insets.EMPTY));
        textFlow.setBackground(background);
    	
    	flowPane.getChildren().add(textFlow);
    	messagePane.getChildren().add(flowPane);
		
	}
		
	//status message (eg joined or left chat)
	public void displayStatusMessage(String message) {
		String[] parts = message.split("-");
		
		FlowPane flowPane = new FlowPane();
    	ImageView imageview= new ImageView();
    	Pane pane = new Pane();
    	Text text = new Text(parts[2] + parts[3]);
    	pane.getChildren().add(text);
    	
    	flowPane.getChildren().addAll(imageview,pane);
    	messagePane.getChildren().add(flowPane);
	}
	
	public void changeUsername() {
		model.setUsername(editUsername.getText());
		
	}
 
    public String getUsername() {
    	return model.getUsername();
    }
	
	public String getText() {
		return messageInput.getText() ;
	}
	
	@FXML
	private void handleSendAction(ActionEvent event) {
		String ownMessage = messageInput.getText();
		String groupID = Integer.toString(groupId);
		displayOwnMessage(ownMessage);
	    model.addMessage("MESSAGE-" + groupID + "-" + ownMessage 
	    		+ "-" + model.getUsername());
	    messageInput.setText("");
	}
	
	@FXML
	private void mouseEntered(MouseEvent e) {
		((Pane) e.getSource()).setBackground(new Background(
				new BackgroundFill(Color.web("#333c47"), CornerRadii.EMPTY, Insets.EMPTY)));
	}
	
	@FXML
	private void mouseExited(MouseEvent e) {
		((Pane) e.getSource()).setBackground(new Background(
				new BackgroundFill(Color.web("#272E36"), CornerRadii.EMPTY, Insets.EMPTY)));
	}
	
	public void mouseDrag() {
		
	}
	
	public void mouseClicked() {
		
	}
	
	public void addGroupChat() {
		
		
	}
	
	public Transcript getChatHistory() {
		return model;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		this.model = new Transcript();
		
	}

	public void setUsername(String string) {
		model.setUsername(string);
		
	}

	public void initSession(LoginManager loginManager, String sessionID) {
		// TODO Auto-generated method stub
		
	}
}
