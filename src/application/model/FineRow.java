package application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FineRow {
	private final StringProperty title;
	private final StringProperty dueDate;
	private final StringProperty dateIn;
	private final StringProperty fineAmount;
	private final StringProperty paid;
	private int cardId;
	private int loanId;
	public double total;

	public FineRow(String title, String dueDate, String dateIn, String fineAmount, String paid, int cardId,
			int loanId) {
		super();
		this.title = new SimpleStringProperty(title);
		this.dueDate = new SimpleStringProperty(dueDate);
		this.dateIn = new SimpleStringProperty(dateIn);
		this.fineAmount = new SimpleStringProperty(fineAmount);
		this.paid = new SimpleStringProperty(paid);
		this.cardId = cardId;
		this.loanId = loanId;
	}

	public String getTitle() {
		return title.get();
	}

	public String getDueDate() {
		return dueDate.get();
	}

	public String getDateIn() {
		return dateIn.get();
	}

	public String getFineAmount() {
		return fineAmount.get();
	}

	public String getPaid() {
		return paid.get();
	}

	public StringProperty title() {
		return title;
	}

	public StringProperty dueDate() {
		return dueDate;
	}

	public StringProperty dateIn() {
		return dateIn;
	}

	public StringProperty fineAmount() {
		return fineAmount;
	}

	public StringProperty paid() {
		return paid;
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	public void setDueDate(String dueDate) {
		this.dueDate.set(dueDate);
	}

	public void setDateIn(String dateIn) {
		this.dateIn.set(dateIn);
	}

	public void setFineAmount(String fineAmount) {
		this.fineAmount.set(fineAmount);
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public int getCardId() {
		return cardId;
	}

	public int getLoanId() {
		return loanId;
	}

}
