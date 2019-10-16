package GUI;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {
  @FXML private TextField username;
  @FXML private TextField password;
  @FXML private Button loginButton;
  
  private static int sessionID = 0;
  
  public void initialize() {}
  
  public void initManager(final LoginManager loginManager) {
    loginButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        String sessionID = null;
		try {
			sessionID = authorize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (sessionID != null) {
          loginManager.authenticated(sessionID);
        }
      }
    });
  }

  /**
   * Check authorization credentials.
   * 
   * If accepted, return a sessionID for the authorized session
   * otherwise, return null.
 * @throws Exception 
   */   
  private String authorize() throws Exception {
    return 
    		LoginManager.verifyPassword(username.getText(), password.getText())
            ? generateSessionID() 
            : null;
  }

  private String generateSessionID() {
    sessionID++;
    return "session " + sessionID;
  }
  
}