package de.hsb.webapp.bc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

/**
 * With this class you can create books.
 * 
 * @author Thomas Schrul, Michael Günster, Andre Schriever
 *
 */
@SuppressWarnings("serial")
@NamedQuery(name = "SelectBook", query = "Select b from Book b")
@Entity
public class Book implements Serializable {

	// Start ---Declaration of variables---

	/**
	 * Book ID is the primary key for a book. A UUID will be generated
	 * automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer bid;

	/**
	 * Title of the book.
	 */
	@Size(min = 2, max = 50)
	private String title;

	/**
	 * ISBN of the book.
	 */
	private int isbn;

	/**
	 * Author of the book. Different books may have the same author.
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	private Author author;

	/**
	 * Genre of the book.
	 */
	private GenreType genre;

	/**
	 * Release date of the book.
	 */
	@Past
	@Temporal(TemporalType.DATE)
	private Date release;

	// End ---Declaration of variables---

	/**
	 * Empty constructor.
	 */
	public Book() {
		super();
		this.author = new Author();
	}

	/**
	 * Constructor using fields to create a book.
	 * 
	 * @param title
	 *            Title of the book.
	 * @param isbn
	 *            ISBN of the book.
	 * @param author
	 *            Author of the book.
	 * @param genre
	 *            Genre of the book.
	 * @param release
	 *            Release date of the book.
	 */
	public Book(String title, int isbn, Author author, GenreType genre, Date release) {
		super();
		this.title = title;
		this.isbn = isbn;
		this.author = author;
		this.genre = genre;
		this.release = release;
	}

	// Start ---Getter & Setter---

	/**
	 * Gets title of the book.
	 * 
	 * @return Book title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the book title.
	 * 
	 * @param title
	 *            New book title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the ISBN of the book.
	 * 
	 * @return ISBN of the book.
	 */
	public int getIsbn() {
		return isbn;
	}

	/**
	 * Sets the ISBN of the current book.
	 * 
	 * @param isbn
	 *            New ISBN.
	 */
	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	/**
	 * Gets the author of the book.
	 * 
	 * @return Book's author.
	 */
	public Author getAuthor() {
		return author;
	}

	/**
	 * Sets the book's author.
	 * 
	 * @param author
	 *            New author.
	 */
	public void setAuthor(Author author) {
		this.author = author;
	}

	/**
	 * Gets the genre of the book.
	 * 
	 * @return Book genre.
	 */
	public GenreType getGenre() {
		return genre;
	}

	/**
	 * Sets the genre of the book.
	 * 
	 * @param genre
	 *            New genre.
	 */
	public void setGenre(GenreType genre) {
		this.genre = genre;
	}

	/**
	 * Gets the release date of the book.
	 * 
	 * @return Release date.
	 */
	public Date getRelease() {
		return release;
	}

	/**
	 * Sets the release date of the book.
	 * 
	 * @param release
	 *            New release date.
	 */
	public void setRelease(Date release) {
		this.release = release;
	}

	// End ---Getter & Setter---
}
