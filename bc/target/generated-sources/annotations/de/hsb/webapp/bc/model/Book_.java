package de.hsb.webapp.bc.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Book.class)
public abstract class Book_ {

	public static volatile SingularAttribute<Book, Author> author;
	public static volatile SingularAttribute<Book, Date> release;
	public static volatile SingularAttribute<Book, String> isbn;
	public static volatile SingularAttribute<Book, GenreType> genre;
	public static volatile SingularAttribute<Book, Integer> bid;
	public static volatile SingularAttribute<Book, String> title;

}

