package application.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Fines.class)
public abstract class Fines_ {

	public static volatile SingularAttribute<Fines, BookLoans> bookLoans;
	public static volatile SingularAttribute<Fines, Long> paid;
	public static volatile SingularAttribute<Fines, Double> fineAmt;
	public static volatile SingularAttribute<Fines, Integer> loanId;

}

