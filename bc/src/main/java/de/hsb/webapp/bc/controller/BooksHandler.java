package de.hsb.webapp.bc.controller;

import java.util.GregorianCalendar;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
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

/**
 * With this class you will handle the books actions.
 * 
 * @author Thomas Schrul, Michael Günster, Andre Schriever
 *
 */
@SessionScoped
@ManagedBean(name = "booksHandler")
public class BooksHandler {

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
		} catch (NotSupportedException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		em.persist(new Book("Metal Gear Solid - Der offizielle Roman zum Konami-Game-Hit von Hideo Kojima",
				"9783833217418", new Author("Raymond", "Benson"), GenreType.THRILLER,
				new GregorianCalendar(2008, 8, 1).getTime()));
		em.persist(new Book("Metro 2033", "9783453529687", new Author("Dmitry", "Glukhovsky"),
				GenreType.SCIENCE_FICTION, new GregorianCalendar(2012, 11, 12).getTime()));
		books = new ListDataModel<Book>();
		books.setWrappedData(em.createNamedQuery("SelectBook").getResultList());
		try {
			utx.commit();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (RollbackException e) {
			e.printStackTrace();
		} catch (HeuristicMixedException e) {
			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
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
		} catch (NotSupportedException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		rememberBook = em.merge(rememberBook);
		em.remove(rememberBook);
		books.setWrappedData(em.createNamedQuery("SelectBook").getResultList());
		try {
			utx.commit();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (RollbackException e) {
			e.printStackTrace();
		} catch (HeuristicMixedException e) {
			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return "XHTML page where to return";
	}

	/**
	 * Edit an existing book.
	 * 
	 * @return XHTML page for editing a book.
	 */
	public String editBook() {
		rememberBook = books.getRowData();
		return "XHTML page for new book";
	}

	/**
	 * Add a new book.
	 * 
	 * @return XHTML page for adding a new book.
	 */
	public String newBook() {
		rememberBook = new Book();
		return "XHTML page for new book";
	}

	/**
	 * Edit an existing author.
	 * 
	 * @return XHTML page for editing an author.
	 */
	public String editAuthor() {
		rememberBook = books.getRowData();
		rememberAuthor = rememberBook.getAuthor();
		return "XHTML page for editing an author";
	}

	/**
	 * Cancel process for adding/editing a book.
	 * 
	 * @return XHTML page where all books are listed.
	 */
	public String cancelEditOrAddBook() {
		return "XHTML page to book list";
	}

	/**
	 * Cancel process for editing an author.
	 * 
	 * @return XHTML page where all books are listed.
	 */
	public String cancelEditAuthor() {
		return "XHTML page to book list";
	}

	/**
	 * Saves the new book or the changes of it.
	 * 
	 * @return XHTML page where all books are listed.
	 */
	public String saveBook() {
		try {
			utx.begin();
		} catch (NotSupportedException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		rememberBook = em.merge(rememberBook);
		em.persist(rememberBook);
		books.setWrappedData(em.createNamedQuery("SelectBook").getResultList());
		try {
			utx.commit();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (RollbackException e) {
			e.printStackTrace();
		} catch (HeuristicMixedException e) {
			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return "xhtml page with list of all books";
	}

	/**
	 * Saves the new book or the changes of it.
	 * 
	 * @return XHTML page where all books are listed.
	 */
	public String saveAuthor() {
		rememberBook.setAuthor(rememberAuthor);
		try {
			utx.begin();
		} catch (NotSupportedException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		rememberBook = em.merge(rememberBook);
		rememberAuthor = em.merge(rememberAuthor);
		em.persist(rememberBook);
		em.persist(rememberAuthor);
		books.setWrappedData(em.createNamedQuery("SelectKunden").getResultList());
		try {
			utx.commit();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (RollbackException e) {
			e.printStackTrace();
		} catch (HeuristicMixedException e) {
			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return "XHTML page with list off all books";
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
}
