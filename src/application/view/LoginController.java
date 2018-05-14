package application.view;

import application.Main;
import application.model.Users;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class LoginController {

	@FXML
	private TextField username;
	@FXML
	private TextField password;

	private Main main;

	Users user;

	@FXML
	private void initialize() {
		main = new Main();
	}

	@FXML
	public void login() {
		if (isValid(username.getText()) && isValid(password.getText())) {
			// user = main.getUser(username.getText());
			user = new Users();
			user.setUserName("arun");
			user.setPassword("arun");
			user.setRole(0L);
			if (user == null)
				throwInvalidFieldsError("Invalid Username!");
			else if (!user.getPassword().equals(password.getText()))
				throwInvalidFieldsError("Incorrect password!");
			else {
				SampleController.user = user;
				main.showHomePage();
			}
		} else {
			throwInvalidFieldsError("Enter a valid username and a password!");
		}
	}

	private void throwInvalidFieldsError(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Fields");
		alert.setHeaderText("Please correct invalid fields");
		alert.setContentText(message);

		alert.showAndWait();
	}

	private boolean isValid(String text) {
		if (text == null || text.trim().length() == 0)
			return false;
		return true;
	}
}
