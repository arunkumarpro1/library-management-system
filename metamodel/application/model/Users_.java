package application.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Users.class)
public abstract class Users_ {

	public static volatile SingularAttribute<Users, String> password;
	public static volatile SingularAttribute<Users, Long> role;
	public static volatile SingularAttribute<Users, String> userName;
	public static volatile SingularAttribute<Users, Integer> userId;

}

