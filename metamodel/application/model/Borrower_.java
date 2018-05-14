package application.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Borrower.class)
public abstract class Borrower_ {

	public static volatile SingularAttribute<Borrower, String> firstName;
	public static volatile SingularAttribute<Borrower, String> lastName;
	public static volatile SingularAttribute<Borrower, String> address;
	public static volatile SetAttribute<Borrower, BookLoans> bookLoanses;
	public static volatile SingularAttribute<Borrower, String> city;
	public static volatile SingularAttribute<Borrower, String> phone;
	public static volatile SingularAttribute<Borrower, Integer> cardId;
	public static volatile SingularAttribute<Borrower, String> state;
	public static volatile SingularAttribute<Borrower, String> email;
	public static volatile SingularAttribute<Borrower, String> ssn;

}

