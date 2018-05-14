package application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookRow {
	private final StringProperty isbn;
	private final StringProperty title;
	private final StringProperty author;
	private final StringProperty availability;
	private final StringProperty publisher;

	public BookRow(String isbn, String title, String author, int availability, String publisher) {
		super();
		this.isbn = new SimpleStringProperty(isbn);
		this.title = new SimpleStringProperty(title);
		this.author = new SimpleStringProperty(author);
		this.availability = (availability == 1) ? new SimpleStringProperty("Available") : new SimpleStringProperty("Not Available");
		this.publisher = new SimpleStringProperty(publisher);
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

	public void setAuthor(String author) {
		this.author.set(author);
	}

	public StringProperty author() {
		return author;
	}

	public String getAuthor() {
		return author.get();
	}

	public void setAvailability(String availability) {
		this.availability.set(availability);
	}

	public StringProperty availability() {
		return availability;
	}

	public String getAvailability() {
		return availability.get();
	}

	public void setPublisher(String publisher) {
		this.publisher.set(publisher);
	}

	public StringProperty publisher() {
		return publisher;
	}

	public String getPublisher() {
		return publisher.get();
	}
}
