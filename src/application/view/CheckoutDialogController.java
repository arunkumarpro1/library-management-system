package application.view;

import com.mysql.jdbc.StringUtils;

import application.Main;
import application.model.BookRow;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Dialog to checkout the book
 *
 * @author Arun
 */
public class CheckoutDialogController {

	@FXML
	private TextField isbnField;
	@FXML
	private TextField titleField;
	@FXML
	private TextField authorsField;
	@FXML
	private TextField publisherField;
	@FXML
	private TextField cardNoField;

	private Main main;
	private Stage dialogStage;
	private BookRow row;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		main = new Main();
	}

	/**
	 * Sets the stage of this dialog.
	 *
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the person to be edited in the dialog.
	 *
	 * @param person
	 */
	public void setPerson(BookRow book) {
		this.row = book;
		isbnField.setText(book.getIsbn());
		titleField.setText(book.getTitle());
		authorsField.setText(book.getAuthor());
		publisherField.setText(book.getPublisher());
	}

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleCheckout() {
		int count;
		Alert alert = new Alert(AlertType.ERROR);
		alert.initOwner(dialogStage);
		alert.setTitle("Invalid Fields");
		alert.setHeaderText("Please correct invalid fields");
		if (isInputValid()) {
			if (!main.checkValidCardNumber(cardNoField.getText())) {
				alert.setHeaderText("The given card id is not registered.");
				alert.setContentText("Enter a valid card number.");
				alert.showAndWait();
			} else if ((count = main.borrowerHasthreeOrMoreLoans(cardNoField.getText())) >= 3) {
				alert.setContentText("The borrower already has " + count + " outstanding book loans.");
				alert.showAndWait();
			} else {
				main.checkoutBook(isbnField.getText(), cardNoField.getText());
				row.setAvailability("Not Available");
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Success");
				alert.setContentText("The book has been checked out successfully.!");
				alert.showAndWait();
				dialogStage.close();
			}
		} else {
			// Show the error message.
			alert.setHeaderText("Card Id must be a six digit number.");
			alert.setContentText("Enter a valid card number.");
			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	/**
	 * Validates the user input in the text fields.
	 *
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		if (cardNoField.getText() == null || cardNoField.getText().trim().length() == 0
				|| !StringUtils.isStrictlyNumeric(cardNoField.getText()) || cardNoField.getText().trim().length() != 6)
			return false;
		return true;
	}
}