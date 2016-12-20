package de.hsb.webapp.bc.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Size;

/**
 * With this class you can create shelves for the users.
 * 
 * @author Thomas Schrul, Michael Günster, Andre Schriever
 *
 */
@NamedQuery(name = "SelectShelf", query = "Select s from Shelf s")
@Entity
public class Shelf implements Serializable {

	// Start ---Declaration of variables---

	/**
	 * "The serialization runtime associates with each serializable class a
	 * version number, called a serialVersionUID, which is used during
	 * deserialization to verify that the sender and receiver of a serialized
	 * object have loaded classes for that object that are compatible with
	 * respect to serialization" -
	 * https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
	 */
	private static final long serialVersionUID = 6948098563899039003L;

	/**
	 * Shelf ID is the primary key for a shelf. A UUID will be generated
	 * automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer sid;

	/**
	 * The user who belongs to the shelf.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	/**
	 * Name of the shelf.
	 */
	@Size(min = 2, max = 40)
	private String name;

	/**
	 * A shelf may have many books and a book may appear in different shelves.
	 * They will be stored in a collection.
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Book> books;

	// End ---Declaration of variables---

	/**
	 * Empty constructor.
	 */
	public Shelf() {
		super();
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
	 *            List with books.
	 */
	public void setBooks(List<Book> books) {
		this.books = books;
	}

	/**
	 * Gets the ID of the current shelf.
	 * 
	 * @return ID of the shelf.
	 */
	public Integer getSid() {
		return sid;
	}

	/**
	 * Sets the ID of the current shelf.
	 * 
	 * @param sid
	 *            New ID for shelf.
	 */
	public void setSid(Integer sid) {
		this.sid = sid;
	}

	/**
	 * Gets the user of the shelf.
	 * 
	 * @return Shelf's
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user of the shelf.
	 * 
	 * @param user
	 *            New user.
	 */
	public void setUser(User user) {
		this.user = user;
	}

	// End ---Getter & Setter---
}
