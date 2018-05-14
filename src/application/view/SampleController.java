package application.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import com.mysql.jdbc.StringUtils;

import application.Main;
import application.model.BookLoanRow;
import application.model.BookRow;
import application.model.Borrower;
import application.model.FineRow;
import application.model.Users;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SampleController {

	@FXML
	private TableView<BookRow> bookTable;
	@FXML
	private TableColumn<BookRow, String> isbn;
	@FXML
	private TableColumn<BookRow, String> title;
	@FXML
	private TableColumn<BookRow, String> author;
	@FXML
	private TableColumn<BookRow, String> availability;
	@FXML
	private TextField isbnText;
	@FXML
	private TextField titleText;
	@FXML
	private TextField authorText;
	@FXML
	private TextField isbnTextLoan;
	@FXML
	private TextField cardNoText;
	@FXML
	private TextField borrowerText;
	@FXML
	private TableView<BookLoanRow> bookLoanTable;
	@FXML
	private TableColumn<BookLoanRow, String> isbnLoan;
	@FXML
	private TableColumn<BookLoanRow, String> titleLoan;
	@FXML
	private TableColumn<BookLoanRow, String> cardNoLoan;
	@FXML
	private TableColumn<BookLoanRow, String> dateOut;
	@FXML
	private TableColumn<BookLoanRow, String> dueDate;
	@FXML
	private TextField firstNameBorrower;
	@FXML
	private TextField lastNameBorrower;
	@FXML
	private TextField ssnBorrower;
	@FXML
	private TextField streetBorrower;
	@FXML
	private TextField cityBorrower;
	@FXML
	private TextField stateBorrower;
	@FXML
	private TextField phoneBorrower;
	@FXML
	private TextField emailBorrower;
	@FXML
	private ComboBox<String> fineType;
	@FXML
	private TableView<FineRow> finesTable;
	@FXML
	private TableColumn<FineRow, String> bookTitleFines;
	@FXML
	private TableColumn<FineRow, String> dueDateFines;
	@FXML
	private TableColumn<FineRow, String> dateInFines;
	@FXML
	private TableColumn<FineRow, String> fineAmount;
	@FXML
	private TableColumn<FineRow, String> paid;
	@FXML
	private TextField cardNoFines;
	@FXML
	private TextField totalFine;
	@FXML
	private TabPane tabPane;
	@FXML
	private Tab tabCheckOut, tabCheckIn, tabBorrower, tabFines;

	private Main main;

	private Double filteredFine = 0.0;

	private ObservableList<FineRow> fineRows = FXCollections.observableArrayList();;

	private FilteredList<FineRow> filteredData = new FilteredList<>(fineRows);

	private ObservableList<BookLoanRow> bookLoans;

	static Users user;

	// Wrap the FilteredList in a SortedList.
	SortedList<FineRow> sortedData;

	@FXML
	private void initialize() {
		main = new Main();

		Collection<Tab> tabs = new ArrayList<>();
		tabs.add(tabCheckIn);
		tabs.add(tabBorrower);
		tabs.add(tabFines);

		if (user.getRole() == 1L)
			tabPane.getTabs().removeAll(tabs);

		isbn.setCellValueFactory(cellData -> cellData.getValue().isbn());
		title.setCellValueFactory(cellData -> cellData.getValue().title());
		author.setCellValueFactory(cellData -> cellData.getValue().author());
		availability.setCellValueFactory(cellData -> cellData.getValue().availability());

		isbnLoan.setCellValueFactory(cellData -> cellData.getValue().isbn());
		titleLoan.setCellValueFactory(cellData -> cellData.getValue().title());
		cardNoLoan.setCellValueFactory(cellData -> cellData.getValue().cardNo());
		dateOut.setCellValueFactory(cellData -> cellData.getValue().date_out());
		dueDate.setCellValueFactory(cellData -> cellData.getValue().due_date());

		bookTitleFines.setCellValueFactory(cellData -> cellData.getValue().title());
		dueDateFines.setCellValueFactory(cellData -> cellData.getValue().dueDate());
		dateInFines.setCellValueFactory(cellData -> cellData.getValue().dateIn());
		fineAmount.setCellValueFactory(cellData -> cellData.getValue().fineAmount());
		paid.setCellValueFactory(cellData -> cellData.getValue().paid());

		fineType.getItems().addAll("All", "Already Paid", "Outstanding");

		bookTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (bookTable.getSelectionModel().getSelectedItem() != null) {
				main.showBookCheckoutDialog(newValue);
			}
			Platform.runLater(() -> {
				bookTable.getSelectionModel().clearSelection();
			});
		});

		bookLoanTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (bookLoanTable.getSelectionModel().getSelectedItem() != null) {
				confirmCheckIn(newValue);
			}
			Platform.runLater(() -> {
				bookLoanTable.getSelectionModel().clearSelection();
				bookLoans.remove(newValue);
			});
		});

		fineType.valueProperty().addListener((observable, oldValue, newValue) -> {
			filteredFine = 0.0;
			filteredData.setPredicate(fine -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty() || newValue.equals("All")) {
					filteredFine += Double.parseDouble(fine.getFineAmount());
					return true;
				}

				if (fine.getPaid().equals(newValue)) {
					filteredFine += Double.parseDouble(fine.getFineAmount());
					return true; // Filter matches.
				}
				return false; // Does not match.
			});
			totalFine.setText(filteredFine.toString());
		});

		finesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (finesTable.getSelectionModel().getSelectedItem() != null) {
				confirmPayFine(newValue);
			}
			Platform.runLater(() -> {
				finesTable.getSelectionModel().clearSelection();
			});
		});
	}

	private void confirmPayFine(FineRow newValue) {
		Alert alert;
		if (newValue.dateIn().get().equals("Not yet checked in")) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid Selection.");
			alert.setContentText("This book has not been checked in yet.");
			alert.showAndWait();
		} else if (newValue.paid().get().equals("Already Paid")) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid Selection.");
			alert.setContentText("This fine has already been paid");
			alert.showAndWait();
		} else {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm");
			alert.setContentText("Are you sure you want pay this fine for Book: " + newValue.getTitle());
			Optional<ButtonType> result = alert.showAndWait();
			if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
				main.payFine(newValue);
			}
		}
	}

	private boolean confirmCheckIn(BookLoanRow newValue) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm");
		alert.setContentText("Are you sure you want check-in this book?\n Details : Card No : " + newValue.getCardNo()
				+ "  Book Title: " + newValue.getTitle());
		Optional<ButtonType> result = alert.showAndWait();
		if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
			main.checkInBook(newValue);
			return true;
		}

		return false;
	}

	private void throwInvalidFieldsError(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Fields");
		alert.setHeaderText("Please correct invalid fields");
		alert.setContentText(message);

		alert.showAndWait();
	}

	@FXML
	public void onSearch() {
		if (isInputValid(isbnText.getText(), titleText.getText(), authorText.getText())) {
			bookTable.setItems(main.getBook(isbnText.getText(), titleText.getText(), authorText.getText()));
		} else {
			throwInvalidFieldsError("Enter at least any one of the search criteria!");
		}
	}

	@FXML
	public void onSearchBookLoans() {
		if (isInputValid(isbnTextLoan.getText(), cardNoText.getText(), borrowerText.getText())) {
			bookLoans = main.getBookLoans(isbnTextLoan.getText(), cardNoText.getText(), borrowerText.getText());
			bookLoanTable.setItems(bookLoans);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText("Enter at least any one of the search criteria!");

			alert.showAndWait();
		}
	}

	@FXML
	public void createBorrower() {
		Alert alert;
		if (isValid(firstNameBorrower.getText()) && isValid(lastNameBorrower.getText())
				&& isValidDigits(ssnBorrower.getText(), 9) && isValid(streetBorrower.getText())
				&& isValid(cityBorrower.getText()) && isValid(stateBorrower.getText())
				&& isValidDigits(phoneBorrower.getText(), 10) && isValid(emailBorrower.getText())) {
			String ssn = new StringBuilder(ssnBorrower.getText().trim()).insert(3, "-").insert(6, "-").toString();
			if (main.checkIfAlreadySsnExists(ssn)) {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Invalid SSN");
				alert.setContentText("The given SSN has already	been enrolled.");

				alert.showAndWait();
				return;
			}
			Borrower borrower = new Borrower();
			borrower.setFirstName(firstNameBorrower.getText().trim());
			borrower.setLastName(lastNameBorrower.getText().trim());
			borrower.setSsn(ssn);
			borrower.setAddress(streetBorrower.getText().trim());
			borrower.setCity(cityBorrower.getText().trim());
			borrower.setState(stateBorrower.getText().trim());
			borrower.setPhone(new StringBuilder(phoneBorrower.getText().trim()).insert(0, "(").insert(4, ") ")
					.insert(9, "-").toString());
			borrower.setEmail(emailBorrower.getText().trim());
			main.createBorrower(borrower);
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setContentText("The borrower has been added successfully.");
			alert.showAndWait();
			firstNameBorrower.clear();
			lastNameBorrower.clear();
			streetBorrower.clear();
			cityBorrower.clear();
			stateBorrower.clear();
			phoneBorrower.clear();
			emailBorrower.clear();
			ssnBorrower.clear();
		} else {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("All fields are mandatory.");
			alert.setContentText("Enter valid values for all the fields.");

			alert.showAndWait();
		}
	}

	@FXML
	public void refreshFines() {
		Alert alert;
		if (main.refreshFinesInDB() == true) {
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setContentText("Fines has been updated successfully");
			alert.showAndWait();
		}
	}

	@FXML
	public void getFinesForCardNumber() {
		if (isValid(cardNoFines.getText()) && StringUtils.isStrictlyNumeric(cardNoFines.getText())
				&& cardNoFines.getText().trim().length() == 6) {
			filteredFine = 0.0;
			fineRows.clear();
			fineRows.addAll(main.getFinesForCardNoFromDB(cardNoFines.getText()));
			finesTable.setItems(filteredData);
			sortedData = new SortedList<>(filteredData);
			sortedData.comparatorProperty().bind(finesTable.comparatorProperty());

			// Add sorted (and filtered) data to the table.
			finesTable.setItems(sortedData);
			if (!filteredData.isEmpty())
				filteredFine = filteredData.get(0).total;
			totalFine.setText(filteredFine.toString());
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Card Id must be a six digit number.");
			alert.setContentText("Enter a valid card Number!");

			alert.showAndWait();
		}
	}

	private boolean isValidDigits(String text, int count) {
		if (StringUtils.isStrictlyNumeric(text) && text.trim().length() == count)
			return true;
		return false;
	}

	private boolean isValid(String text) {
		if (text == null || text.trim().length() == 0)
			return false;
		return true;
	}

	private boolean isInputValid(String string1, String string2, String string3) {
		if ((string1 == null || string1.trim().length() == 0) && (string2 == null || string2.trim().length() == 0)
				&& (string3 == null || string3.trim().length() == 0)) {
			return false;
		}
		return true;
	}
}