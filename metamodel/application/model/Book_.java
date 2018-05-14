package application.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Book.class)
public abstract class Book_ {

	public static volatile SingularAttribute<Book, String> cover;
	public static volatile SetAttribute<Book, Authors> authorses;
	public static volatile SingularAttribute<Book, Integer> pages;
	public static volatile SetAttribute<Book, BookLoans> bookLoanses;
	public static volatile SingularAttribute<Book, String> isbn;
	public static volatile SingularAttribute<Book, String> publisher;
	public static volatile SingularAttribute<Book, Integer> availability;
	public static volatile SingularAttribute<Book, String> title;

}

