package de.hsb.webapp.bc.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

	/**
	 * Stores all shelves.
	 */
	private DataModel<Shelf> shelves;

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
	 * Selected shelf of the current user.
	 */
	private Shelf myShelf;

	/**
	 * Initializes the data model with some books.
	 */
	@PostConstruct
	public void init() {
		try {
			System.out.println("BOOKSHANDLER_INIT");
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
	 * Creates a new book.
	 */
	public void newBook() {
		rememberBook = new Book();
	}

	/**
	 * Edit an existing book.
	 * 
	 * @return XHTML page for editing a book.
	 */
	public String editBook() {
		rememberBook = books.getRowData();
		return "showBooks?faces-redirect=true";
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
			shelves.setWrappedData(em.createNamedQuery("SelectShelf").getResultList());
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
	 * @return XHTML page where all books are listed.
	 */
	public String saveBook() {
		try {
			utx.begin();
			rememberBook = em.merge(rememberBook);
			books.setWrappedData(em.createNamedQuery("SelectBook").getResultList());
			shelves.setWrappedData(em.createNamedQuery("SelectShelf").getResultList());
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
	 * @return XHTML page where all books are listed.
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
	 * @return
	 */
	public String editAuthor() {
		rememberAuthor = authors.getRowData();
		return "manageAuthors?faces-redirect=true";
	}

	/**
	 * Saves the current author.
	 * 
	 * @return
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
	 * Deletes the current author.
	 * 
	 * @return
	 */
	public String deleteAuthor() {
		rememberAuthor = authors.getRowData();
		try {
			utx.begin();
			rememberAuthor = em.merge(rememberAuthor);
			em.remove(rememberAuthor);
			books.setWrappedData(em.createNamedQuery("SelectBook").getResultList());
			authors.setWrappedData(em.createNamedQuery("SelectAuthor").getResultList());
			shelves.setWrappedData(em.createNamedQuery("SelectShelf").getResultList());
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
	 * @return
	 */
	public String cancelAuthor() {
		return "showBooks";
	}

	/**
	 * Gets the current selected shelf of the user.
	 * 
	 * @param shelf
	 */
	public void showAllBooks(Shelf shelf) {
		myShelf = shelf;
	}

	/**
	 * Adds a book to the current shelf. Will create a book list for the shelf
	 * first, if the shelf does not have a book list.
	 * 
	 * @param book
	 *            Book to add.
	 * @return
	 */
	public String addBookToShelf(Book book) {
		rememberBook = book;
		if (myShelf.getBooks() == null) {
			myShelf.setBooks(new ArrayList<Book>());
		}
		myShelf.getBooks().add(rememberBook);
		try {
			utx.begin();
			myShelf = em.merge(myShelf);
			shelves.setWrappedData(em.createNamedQuery("SelectShelf").getResultList());
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "shelfList?faces-redirect=true";
	}

	/**
	 * Gets
	 * 
	 * @return
	 */
	public Shelf getMyShelf() {
		return myShelf;
	}

	public void setMyShelf(Shelf rememberShelf) {
		this.myShelf = rememberShelf;
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
	 * Gets the shelves datamodel.
	 * 
	 * @return Datamodel of the shelves.
	 */
	public DataModel<Shelf> getShelves() {
		return shelves;
	}

	/**
	 * Sets the shelves datamodel.
	 * 
	 * @param shelves
	 *            New datamodel for schelves.
	 */
	public void setShelves(DataModel<Shelf> shelves) {
		this.shelves = shelves;
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
