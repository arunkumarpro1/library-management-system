package application.model;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BookLoans.class)
public abstract class BookLoans_ {

	public static volatile SingularAttribute<BookLoans, Calendar> datein;
	public static volatile SingularAttribute<BookLoans, Book> book;
	public static volatile SingularAttribute<BookLoans, Calendar> dateOut;
	public static volatile SingularAttribute<BookLoans, Calendar> dueDate;
	public static volatile SingularAttribute<BookLoans, Fines> fines;
	public static volatile SingularAttribute<BookLoans, Borrower> borrower;
	public static volatile SingularAttribute<BookLoans, Integer> loanId;

}

