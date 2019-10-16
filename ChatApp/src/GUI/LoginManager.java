package GUI;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.*;

import client.ChatController;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;

public class LoginManager {
  private Scene scene;

  public LoginManager(Scene scene) {
    this.scene = scene;
  }

  /**
   * Callback method that confirms user has been authenticated.
   * shows the main application screen.
   */ 
  public void authenticated(String sessionID) {
    showMainAppScreen(sessionID);
  }

  /**
   * Callback method Shows the login application screen once again.
   * after
   */ 
  public void logout() {
    showLoginScreen();
  }
  
  public void showLoginScreen() {
    try {
      FXMLLoader loader = new FXMLLoader(
        getClass().getResource("Login.fxml")
      );
      scene.setRoot((Parent) loader.load());
      LoginController controller = loader.<LoginController>getController();
      controller.initManager(this);
    } catch (IOException ex) {
      Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void showMainAppScreen(String sessionID) {
    try {
      FXMLLoader loader = new FXMLLoader(
        getClass().getResource("ChatGUI.fxml")
      );
      scene.setRoot((Parent) loader.load());
      ChatController controller = loader.<ChatController>getController();
      controller.initSession(this, sessionID);
    } catch (IOException ex) {
      Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  /**
   * opens the MySQL password database to authenticate user
   */
  
  public static boolean verifyPassword(String username, String password) throws Exception {
		String url = "jdbc:mysql://localhost:3306/user";
		String uname = "root";
		String pass = "";
		String query = "SELECT * FROM password";
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(url, uname, pass);
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		while(rs.next()) {
			String name = rs.getString("username");
			if (name.equals(username)) {
				if (password.equals(rs.getString("password"))) {
					return true;
				}
			}
			
		}
		
		stmt.close();
		con.close();
		
		return false;
	
	}
  
}