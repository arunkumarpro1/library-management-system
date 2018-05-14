package application.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Authors.class)
public abstract class Authors_ {

	public static volatile SetAttribute<Authors, Book> books;
	public static volatile SingularAttribute<Authors, String> name;
	public static volatile SingularAttribute<Authors, Integer> authorId;

}

