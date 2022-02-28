package boundary;

import java.util.ArrayList;

import control.LoginControl;
import entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Login {
	
	 @FXML
	 private TextField userNameField;

	 @FXML
	 private PasswordField passwordField;
	 
	 @FXML
	 private Button loginBtn;
	 
	 @FXML
	 public void login(ActionEvent event) throws Exception {
			if(isCanLogin()) {
				Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/MenuScreen.fxml"));
				Stage primaryStage = (Stage) loginBtn.getScene().getWindow();
				primaryStage.getScene().setRoot(newRoot);
				primaryStage.show();
			}
			else {
				Alert alert = new Alert(AlertType.ERROR,"One or More Fields are incoorect");
				alert.setHeaderText("Login Error");
				alert.setTitle("Login Error");
				alert.showAndWait();
				userNameField.clear();
				passwordField.clear();
			}	
		}
		
		public boolean isCanLogin()
		{
			User user = new User(userNameField.getText(), passwordField.getText());
			ArrayList<User> allCustumers = LoginControl.getInstance().getAllUsers();
			for(User us: allCustumers) {
				if(us.equals(user)) {
					LoginControl.getInstance().setLoginUser(us); //save the login member
					return true;
				}
			}
				return false;
		}
		
}
