package de.hsb.webapp.bc.controller;

import java.io.Serializable;
import java.util.GregorianCalendar;
import javax.annotation.PostConstruct;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import de.hsb.webapp.bc.model.Author;
import de.hsb.webapp.bc.model.Book;
import de.hsb.webapp.bc.model.GenreType;
import de.hsb.webapp.bc.model.Shelf;

/**
 * With this class you will handle the books actions.
 * 
 * @author Thomas Schrul, Michael Günster, Andre Schriever
 *
 */
@ManagedBean(name = "booksHandler")
@SessionScoped
public class BooksHandler implements Serializable {

	/**
	 * "The serialization runtime associates with each serializable class a
	 * version number, called a serialVersionUID, which is used during
	 * deserialization to verify that the sender and receiver of a serialized
	 * object have loaded classes for that object that are compatible with
	 * respect to serialization" -
	 * https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
	 */
	private static final long serialVersionUID = -4704269543724333039L;

	/**
	 * Entity Manger for the books.
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * User transactions for the books.
	 */
	@Resource
	private UserTransaction utx;

	/**
	 * Stores all books.
	 */
	private DataModel<Book> books;

	private DataModel<Shelf> shelves;

	private DataModel<Author> authors;

	/**
	 * Remembers the current book.
	 */
	private Book rememberBook = new Book();

	/**
	 * Remembers the current Author.
	 */
	private Author rememberAuthor = new Author();

	/**
	 * Initializes the data model with some books.
	 */
	@PostConstruct
	public void init() {
		try {
			utx.begin();
			books = new ListDataModel<Book>();
			books.setWrappedData(em.createNamedQuery("SelectBook").getResultList());
			shelves = new ListDataModel<Shelf>();
			shelves.setWrappedData(em.createNamedQuery("SelectShelf").getResultList());
			authors = new ListDataModel<Author>();
			authors.setWrappedData(em.createNamedQuery("SelectAuthor").getResultList());
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add a new book.
	 * 
	 * @return XHTML page for adding a new book.
	 */
	public String newBook() {
		rememberBook = new Book();
		return "addBook";
	}

	/**
	 * Deletes a book from database.
	 * 
	 * @return XHTML page.
	 */
	public String deleteBook() {
		rememberBook = books.getRowData();
		try {
			utx.begin();
			rememberBook = em.merge(rememberBook);
			em.remove(rememberBook);
			books.setWrappedData(em.createNamedQuery("SelectBook").getResultList());
			authors.setWrappedData(em.createNamedQuery("SelectAuthor").getResultList());
			shelves.setWrappedData(em.createNamedQuery("SelectShelf").getResultList());
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "showBooksOverview";
	}

	/**
	 * Edit an existing book.
	 * 
	 * @return XHTML page for editing a book.
	 */
	public String editBook() {
		rememberBook = books.getRowData();
		return "addBook";
	}

	/**
	 * Saves the new book or the changes of it.
	 * 
	 * @return XHTML page where all books are listed.
	 */
	public String saveBook() {
		try {
			utx.begin();
			rememberBook = em.merge(rememberBook);
			books.setWrappedData(em.createNamedQuery("SelectBook").getResultList());
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "showBooksOverview";
	}


	/**
	 * Cancel process for adding/editing a book.
	 * 
	 * @return XHTML page where all books are listed.
	 */
	public String cancelEditOrAddBook() {
		return "showBooksOverview";
	}

	/**
	 * Gets all genre types.
	 * 
	 * @return Genre values.
	 */
	public GenreType[] getGenreValues() {
		return GenreType.values();
	}

	/**
	 * Gets data model of the books.
	 * 
	 * @return Books data model.
	 */
	public DataModel<Book> getBooks() {
		return books;
	}

	/**
	 * Sets the books data model.
	 * 
	 * @param books
	 *            Books data model.
	 */
	public void setBooks(DataModel<Book> books) {
		this.books = books;
	}

	/**
	 * Gets the current book.
	 * 
	 * @return Current book.
	 */
	public Book getRememberBook() {
		return rememberBook;
	}

	/**
	 * Sets the current book.
	 * 
	 * @param rememberBook
	 *            New book.
	 */
	public void setRememberBook(Book rememberBook) {
		this.rememberBook = rememberBook;
	}

	/**
	 * Gets the current author.
	 * 
	 * @return Current author.
	 */
	public Author getRememberAuthor() {
		return rememberAuthor;
	}

	/**
	 * Sets the current author.
	 * 
	 * @param rememberAuthor
	 *            New author.
	 */
	public void setRememberAuthor(Author rememberAuthor) {
		this.rememberAuthor = rememberAuthor;
	}

	public DataModel<Shelf> getShelves() {
		return shelves;
	}

	public void setShelves(DataModel<Shelf> shelves) {
		this.shelves = shelves;
	}

	public DataModel<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(DataModel<Author> authors) {
		this.authors = authors;
	}

}
