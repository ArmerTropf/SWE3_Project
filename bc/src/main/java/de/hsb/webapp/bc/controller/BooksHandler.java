package de.hsb.webapp.bc.controller;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import de.hsb.webapp.bc.model.Author;
import de.hsb.webapp.bc.model.Book;
import de.hsb.webapp.bc.model.GenreType;

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

	/**
	 * Stores all authors.
	 */
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
			Author a1 = new Author("Joanne K.", "Rowling"), a2 = new Author("Bram", "Stoker"),
					a3 = new Author("Mary", "Shelley");
			em.persist(a1);
			em.persist(a2);
			em.persist(a3);
			Book b1 = new Book("Frankenstein", "978-3866473768", GenreType.SCIENCE_FICTION,
					new GregorianCalendar(2009, 02, 27).getTime());
			b1.setAuthor(a3);
			Book b2 = new Book("Dracula", "978-3866472938", GenreType.HORROR,
					new GregorianCalendar(2008, 10, 01).getTime());
			b2.setAuthor(a2);
			Book b3 = new Book("Harry Potter und der Stein der Weisen", "978-3551354013", GenreType.FANTASY,
					new GregorianCalendar(2005, 01, 01).getTime());
			b3.setAuthor(a1);
			em.persist(b1);
			em.persist(b2);
			em.persist(b3);
			books = new ListDataModel<Book>();
			books.setWrappedData(em.createNamedQuery("SelectBook").getResultList());
			authors = new ListDataModel<Author>();
			authors.setWrappedData(em.createNamedQuery("SelectAuthor").getResultList());
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new book.
	 */
	public void newBook() {
		rememberBook = new Book();
	}

	/**
	 * Edit an existing book.
	 * 
	 * @return redirected "showBooks" page with the book list.
	 */
	public String editBook() {
		rememberBook = books.getRowData();
		return "showBooks?faces-redirect=true";
	}

	/**
	 * Deletes a book from database.
	 * 
	 * @return redirected "showBooks" page with the book list.
	 */
	public String deleteBook() {
		rememberBook = books.getRowData();
		try {
			utx.begin();
			rememberBook = em.merge(rememberBook);
			em.remove(rememberBook);
			books.setWrappedData(em.createNamedQuery("SelectBook").getResultList());
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		newBook();
		return "showBooks?faces-redirect=true";

	}

	/**
	 * Saves the new book or the changes of it.
	 * 
	 * @return redirected "showBooks" page with the book list.
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
		newBook();
		return "showBooks?faces-redirect=true";
	}

	/**
	 * Cancel process for adding/editing a book.
	 *
	 * @return redirected "showBooks" page with the book list.
	 */
	public String cancelEditOrAddBook() {
		newBook();
		return "showBooks?faces-redirect=true";
	}

	/**
	 * Creates a new author.
	 */
	public void newAuthor() {
		rememberAuthor = new Author();
	}

	/**
	 * Gets the current author to edit.
	 * 
	 * @return redirected "manageAuthors" page with the author list.
	 */
	public String editAuthor() {
		rememberAuthor = authors.getRowData();
		return "manageAuthors?faces-redirect=true";
	}

	/**
	 * Saves the current author.
	 * 
	 * @return redirected "manageAuthors" page with the author list.
	 */
	public String saveAuthor() {
		try {
			utx.begin();
			rememberAuthor = em.merge(rememberAuthor);
			authors.setWrappedData(em.createNamedQuery("SelectAuthor").getResultList());
			books.setWrappedData(em.createNamedQuery("SelectBook").getResultList());
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		newAuthor();
		return "manageAuthors?faces-redirect=true";
	}

	/**
	 * Deletes the author and all references in the books, which are pointing to
	 * this author.
	 * 
	 * @return redirected "manageAuthors" page with the author list.
	 */
	public String deleteAuthor() {
		rememberAuthor = authors.getRowData();
		try {
			utx.begin();
			Query q = em.createQuery("Select b from Book b where b.author.firstname = :firstname")
					.setParameter("firstname", rememberAuthor.getFirstname());
			List<Book> b = q.getResultList();
			for (Book x : b) {
				rememberBook = x;
				rememberBook.setAuthor(null);
				rememberBook = em.merge(rememberBook);
			}
			rememberAuthor = em.merge(rememberAuthor);
			em.remove(rememberAuthor);
			books.setWrappedData(em.createNamedQuery("SelectBook").getResultList());
			authors.setWrappedData(em.createNamedQuery("SelectAuthor").getResultList());
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		newAuthor();
		return "manageAuthors?faces-redirect=true";
	}

	/**
	 * Cancels the manging author mode.
	 * 
	 * @return "showBooks" which is the page with the book list.
	 */
	public String cancelAuthor() {
		return "showBooks";
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

	/**
	 * Gets the authors datamodel.
	 * 
	 * @return Datamodel of the authors.
	 */
	public DataModel<Author> getAuthors() {
		return authors;
	}

	/**
	 * Sets the datamodel for authors.
	 * 
	 * @param authors
	 *            New datamodel for authors.
	 */
	public void setAuthors(DataModel<Author> authors) {
		this.authors = authors;
	}
}
