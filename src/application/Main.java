package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import org.hibernate.HibernateException;

import application.model.Authors;
import application.model.Authors_;
import application.model.Book;
import application.model.BookLoanRow;
import application.model.BookLoans;
import application.model.BookLoans_;
import application.model.BookRow;
import application.model.Book_;
import application.model.Borrower;
import application.model.Borrower_;
import application.model.FineRow;
import application.model.Fines;
import application.model.Users;
import application.view.CheckoutDialogController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {
	static Stage primaryStage;
	AnchorPane rootLayout;

	EntityManagerFactory entityManagerFactory;
	EntityManager entityManager;

	public Main() {
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("sample");
		} catch (Throwable ex) {
			System.err.println("Failed to create entityManagerFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public void showHomePage() {
		try {
			// Load root layout from fxml file.SS
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Sample.fxml"));
			rootLayout = (AnchorPane) loader.load();
			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void start(Stage primaryStage1) {
		try {
			primaryStage = primaryStage1;
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("library.png")));
			primaryStage.setTitle("Library Management System");
			initRootLayout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Opens a dialog to add Card number for checkout.
	 *
	 * @param book
	 *            details
	 * @return true if the user clicked OK, false otherwise.
	 */
	public void showBookCheckoutDialog(BookRow book) {
		if (book.getAvailability().equals("Not Available")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid Fields");
			alert.setContentText("The book has already been checked out.");
			alert.showAndWait();
			return;
		}
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/CheckoutDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Card Number");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			CheckoutDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPerson(book);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			// loader.setLocation(Main.class.getResource("view/Sample.fxml"));
			loader.setLocation(Main.class.getResource("view/Login.fxml"));
			rootLayout = (AnchorPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public ObservableList<BookRow> getBook(String isbn, String title, String author) {
		ObservableList<BookRow> books = FXCollections.observableArrayList();
		entityManager = entityManagerFactory.createEntityManager();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		try {
			entityManager.getTransaction().begin();
			// Create CriteriaQuery
			CriteriaQuery<Book> criteria = builder.createQuery(Book.class);
			Root<Book> root = criteria.from(Book.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			if (isbn != null && isbn.trim().length() != 0) {
				isbn = "%" + isbn + "%";
				predicates.add(builder.like(root.get(Book_.isbn), isbn));
			}
			if (title != null && title.trim().length() != 0) {
				title = "%" + title + "%";
				predicates.add(builder.like(root.get(Book_.title), title));
			}
			if (author != null && author.trim().length() != 0) {
				author = "%" + author + "%";
				SetJoin<Book, Authors> join = root.join(Book_.authorses);
				Expression<String> name = join.get(Authors_.name);
				predicates.add(builder.like(name, author));
			}
			criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
			List<Book> bookList = entityManager.createQuery(criteria).getResultList();
			for (Iterator<Book> iterator = bookList.iterator(); iterator.hasNext();) {
				Book book = iterator.next();
				author = "";
				for (Authors authorEntity : book.getAuthorses())
					author += authorEntity.getName() + " , ";
				author = author.substring(0, author.length() - 3);
				books.add(new BookRow(book.getIsbn(), book.getTitle(), author, book.getAvailability(),
						book.getPublisher().toString()));
			}
			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
		return books;
	}

	public boolean checkValidCardNumber(String cardNo) {
		entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			Borrower borrower = entityManager.find(Borrower.class, Integer.parseInt(cardNo.trim()));
			if (borrower != null)
				return true;
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			entityManager.getTransaction().commit();
			entityManager.close();
		}
		return false;
	}

	public int borrowerHasthreeOrMoreLoans(String cardNo) {
		int count = 0;
		entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			Borrower borrower = entityManager.find(Borrower.class, Integer.parseInt(cardNo.trim()));
			for (BookLoans bookLoan : borrower.getBookLoanses())
				if (bookLoan.getDatein() == null)
					count += 1;
			return count;
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			entityManager.getTransaction().commit();
			entityManager.close();
		}
		return 0;
	}

	public void checkoutBook(String isbn, String cardId) {
		entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			// Create CriteriaQuery
			Book book = entityManager.find(Book.class, isbn.trim());
			book.setAvailability(0);
			Borrower borrower = entityManager.find(Borrower.class, Integer.parseInt(cardId.trim()));
			BookLoans loan = new BookLoans();
			loan.setBook(book);
			loan.setBorrower(borrower);
			entityManager.persist(loan);
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			entityManager.getTransaction().commit();
			entityManager.close();
		}
	}

	public ObservableList<BookLoanRow> getBookLoans(String isbn, String cardNo, String borrower) {
		ObservableList<BookLoanRow> bookLoanss = FXCollections.observableArrayList();
		entityManager = entityManagerFactory.createEntityManager();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		try {
			entityManager.getTransaction().begin();
			CriteriaQuery<BookLoans> criteria = builder.createQuery(BookLoans.class);
			Root<BookLoans> root = criteria.from(BookLoans.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			if (isbn != null && isbn.trim().length() != 0) {
				isbn = "%" + isbn + "%";
				Join<BookLoans, Book> join = root.join(BookLoans_.book);
				Expression<String> isbnExp = join.get(Book_.isbn);
				predicates.add(builder.like(isbnExp, isbn));
			}
			if (cardNo != null && cardNo.trim().length() != 0) {
				Join<BookLoans, Borrower> join = root.join(BookLoans_.borrower);
				Expression<Integer> cardIdExp = join.get(Borrower_.cardId);
				predicates.add(builder.equal(cardIdExp, Integer.parseInt(cardNo)));
			}
			if (borrower != null && borrower.trim().length() != 0) {
				borrower = "%" + borrower + "%";
				Join<BookLoans, Borrower> join = root.join(BookLoans_.borrower);
				Expression<String> firstNameExp = join.get(Borrower_.firstName);
				Expression<String> lastNameExp = join.get(Borrower_.lastName);
				predicates.add(builder.or(builder.like(firstNameExp, borrower), builder.like(lastNameExp, borrower)));
			}
			predicates.add(builder.isNull(root.get(BookLoans_.datein)));
			criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
			List<BookLoans> bookLoanList = entityManager.createQuery(criteria).getResultList();
			for (Iterator<BookLoans> iterator = bookLoanList.iterator(); iterator.hasNext();) {
				BookLoans bookLoan = iterator.next();
				bookLoanss.add(new BookLoanRow(bookLoan.getLoanId().toString(), bookLoan.getBook().getIsbn(),
						bookLoan.getBook().getTitle(),
						String.format("%6s", bookLoan.getBorrower().getCardId().toString()).replace(' ', '0'),
						bookLoan.getDateOut().getTime().toString(), bookLoan.getDueDate().getTime().toString()));
			}
			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
		return bookLoanss;
	}

	public void checkInBook(BookLoanRow loanRow) {
		entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			Book book = entityManager.find(Book.class, loanRow.getIsbn());
			book.setAvailability(1);
			BookLoans loan = entityManager.find(BookLoans.class, Integer.parseInt(loanRow.getLoanId()));
			loan.setDatein(Calendar.getInstance());
			entityManager.persist(loan);
			entityManager.persist(book);
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			entityManager.getTransaction().commit();
			entityManager.close();
		}
	}

	public void createBorrower(Borrower borrower) {
		entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(borrower);
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			entityManager.getTransaction().commit();
			entityManager.close();
		}

	}

	public boolean checkIfAlreadySsnExists(String ssn) {
		entityManager = entityManagerFactory.createEntityManager();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		try {
			entityManager.getTransaction().begin();
			CriteriaQuery<Borrower> query = builder.createQuery(Borrower.class);
			Root<Borrower> root = query.from(Borrower.class);
			query.where(builder.like(root.get(Borrower_.ssn), ssn));
			if (!entityManager.createQuery(query).getResultList().isEmpty())
				return true;
			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
		return false;
	}

	public boolean refreshFinesInDB() {
		entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.createNamedQuery("Fines.InsertNewFines").executeUpdate();
			entityManager.createNamedQuery("Fines.UpdateOldFines").executeUpdate();
			entityManager.getTransaction().commit();
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		} finally {
			entityManager.close();
		}
	}

	public ObservableList<FineRow> getFinesForCardNoFromDB(String cardNo) {
		ObservableList<FineRow> fineRows = FXCollections.observableArrayList();
		entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			Borrower borrower = entityManager.find(Borrower.class, Integer.parseInt(cardNo.trim()));
			BookLoans loan;
			Double totalFine = 0.0;
			for (Iterator<BookLoans> iterator = borrower.getBookLoanses().iterator(); iterator.hasNext();) {
				loan = iterator.next();
				if (loan.getFines() != null) {
					fineRows.add(
							new FineRow(loan.getBook().getTitle(), loan.getDueDate().getTime().toString(),
									(loan.getDatein() != null) ? loan.getDatein().getTime().toString()
											: "Not yet checked in",
									loan.getFines().getFineAmt().toString(),
									(loan.getFines().getPaid() == 1) ? "Already Paid" : "Outstanding",
									loan.getBorrower().getCardId(), loan.getLoanId()));
					totalFine += loan.getFines().getFineAmt();
				}
			}
			if (!fineRows.isEmpty())
				fineRows.get(0).total = totalFine;
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			entityManager.getTransaction().commit();
			entityManager.close();
		}
		return fineRows;
	}

	public void payFine(FineRow fineRow) {
		entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			Fines fine = entityManager.find(Fines.class, fineRow.getLoanId());
			fine.setPaid(1L);
			entityManager.persist(fine);
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			entityManager.getTransaction().commit();
			entityManager.close();
		}
	}

	public Users getUser(String username) {
		Users user = null;
		entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			user = entityManager.find(Users.class, username);
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
		return user;
	}
}
