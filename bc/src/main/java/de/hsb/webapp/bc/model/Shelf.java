package de.hsb.webapp.bc.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Size;

/**
 * With this class you can create shelves for the users.
 * 
 * @author Thomas Schrul, Michael Günster, Andre Schriever
 *
 */
@SuppressWarnings("serial")
@NamedQuery(name = "SelectShelf", query = "Select s from Shelf s")
@Entity
public class Shelf implements Serializable {

	// Start ---Declaration of variables---

	/**
	 * Shelf ID is the primary key for a shelf. A UUID will be generated
	 * automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer sid;

	/**
	 * Name of the shelf.
	 */
	@Size(min = 2, max = 40)
	private String name;

	/**
	 * A shelf may have many books and a book may appear in different shelves.
	 * They will be stored in a collection.
	 */
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Book> books;

	// End ---Declaration of variables---

	/**
	 * Empty constructor.
	 */
	public Shelf() {
		super();
		this.books = new Vector<Book>();
	}

	/**
	 * Constructor using fields to create a shelf. Use this constructor to
	 * create a shelf without books at the beginning.
	 * 
	 * @param name
	 *            Name of the shelf.
	 */
	public Shelf(String name) {
		super();
		this.name = name;
		this.books = new Vector<Book>();
	}

	/**
	 * Constructor using fields to create a shelf. Use this constructor for
	 * adding a list of books to the shelf directly.
	 * 
	 * @param name
	 *            Name of the shelf.
	 * @param books
	 *            Collection of books.
	 */
	public Shelf(String name, List<Book> books) {
		super();
		this.name = name;
		this.books = books;
	}

	// Start ---Getter & Setter---

	/**
	 * Gets the name of the shelf.
	 * 
	 * @return Current shelf name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the shelf.
	 * 
	 * @param name
	 *            Define new shelf name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets all books stored in the shelf.
	 * 
	 * @return All books of the current shelf.
	 */
	public List<Book> getBooks() {
		return books;
	}

	/**
	 * Sets the books into the shelf.
	 * 
	 * @param books
	 *            Collection with books.
	 */
	public void setBooks(List<Book> books) {
		this.books = books;
	}

	// End ---Getter & Setter---
}
