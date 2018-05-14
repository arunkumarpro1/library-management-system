package application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookLoanRow {
	private final StringProperty isbn;
	private final StringProperty title;
	private final StringProperty cardNo;
	private final StringProperty date_out;
	private final StringProperty due_date;
	private final String loanId;

	public BookLoanRow(String loanId, String isbn, String title, String cardNo, String date_out, String due_date) {
		super();
		this.loanId = loanId;
		this.isbn = new SimpleStringProperty(isbn);
		this.title = new SimpleStringProperty(title);
		this.cardNo = new SimpleStringProperty(cardNo);
		this.date_out = new SimpleStringProperty(date_out);
		this.due_date = new SimpleStringProperty(due_date);
	}

	public void setIsbn(String isbn) {
		this.isbn.set(isbn);
	}

	public StringProperty isbn() {
		return isbn;
	}

	public String getIsbn() {
		return isbn.get();
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	public StringProperty title() {
		return title;
	}

	public String getTitle() {
		return title.get();
	}

	public void setCardNo(String cardNo) {
		this.cardNo.set(cardNo);
	}

	public StringProperty cardNo() {
		return cardNo;
	}

	public String getCardNo() {
		return cardNo.get();
	}

	public void setDate_out(String date_out) {
		this.date_out.set(date_out);
	}

	public StringProperty date_out() {
		return date_out;
	}

	public String getDate_out() {
		return date_out.get();
	}

	public void setDue_date(String due_date) {
		this.due_date.set(due_date);
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 != null) {
			BookLoanRow a = (BookLoanRow) arg0;
			return this.loanId.trim().equals(a.loanId.trim());
		}
		return false;
	}

	public StringProperty due_date() {
		return due_date;
	}

	public String getDue_date() {
		return due_date.get();
	}

	public String getLoanId() {
		return loanId;
	}
}
