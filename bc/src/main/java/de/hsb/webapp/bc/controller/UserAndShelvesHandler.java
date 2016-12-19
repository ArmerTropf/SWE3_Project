package de.hsb.webapp.bc.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import de.hsb.webapp.bc.model.Book;
import de.hsb.webapp.bc.model.Shelf;
import de.hsb.webapp.bc.model.User;

/**
 * With this class you will handle the user and shelves actions.
 * 
 * @author Thomas Schrul, Michael Günster, Andre Schriever
 *
 */
@ManagedBean(name = "userAndShelvesHandler")
@SessionScoped
public class UserAndShelvesHandler implements Serializable {

	/**
	 * "The serialization runtime associates with each serializable class a
	 * version number, called a serialVersionUID, which is used during
	 * deserialization to verify that the sender and receiver of a serialized
	 * object have loaded classes for that object that are compatible with
	 * respect to serialization" -
	 * https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
	 */
	private static final long serialVersionUID = -6973592033673957562L;

	/**
	 * Entity Manger for the user and shelves.
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * User transactions for the user and shelves.
	 */
	@Resource
	private UserTransaction utx;

	/**
	 * Stores all user.
	 */
	private DataModel<User> user;

	/**
	 * Stores all shelves.
	 */
	private DataModel<Shelf> shelves;

	/**
	 * Remembers the current user.
	 */
	private User rememberUser = new User();

	/**
	 * Remembers the current shelf.
	 */
	private Shelf rememberShelf = new Shelf();

	/**
	 * Remembers the current book.
	 */
	private Book rememberBook = new Book();

	/**
	 * Stores the books of the current shelf.
	 */
	private List<Book> myBooks;

	// User u = new User("Schrul", "Thomas", "12345", "tschrul", true); // just
	// for
	// testing:
	// default
	// user
	// (because
	// of no
	// login)


	/**
	 * Initializes the data model with some user for the first use.
	 */
	@PostConstruct
	public void init() {
		try {
			// rememberUser = u; // just for testing
			utx.begin();
			// em.persist(new User("Guenster", "Michael", "12345", "mguenster",
			// true));
			// em.persist(u);
			user = new ListDataModel<User>();
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			shelves = new ListDataModel<Shelf>();
			shelves.setWrappedData(em.createNamedQuery("SelectShelf").getResultList());
			System.out.println("Init USER erstellt");
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new shelf.
	 * 
	 * @return
	 */
	public void newShelf() {
		rememberUser = user.getRowData();
		// rememberUser = u; //just for testing
		rememberShelf = new Shelf();

		if (rememberUser.getShelves() == null) {
			rememberUser.setShelves(new ArrayList<Shelf>());
		}
	}

	/**
	 * Edits an existing shelf.
	 * 
	 * @return
	 */
	public String editShelf(Shelf shelf) {
		rememberUser = user.getRowData();
		// rememberUser = u;
		// rememberShelf = shelves.getRowData();
		rememberShelf = shelf;
		return "shelfList?faces-redirect=true";
	}

	/**
	 * Saves changes to the current shelf and adds a new shelf to the shelves
	 * list of the user if the shelf is new.
	 * 
	 * @return
	 */
	public String saveShelf() {
		if (rememberUser.getShelves() == null) {
			rememberUser.setShelves(new ArrayList<Shelf>());
		}
		if (rememberShelf.getSid() == null) {
			try {
				rememberUser.getShelves().add(rememberShelf);
				utx.begin();
				em.persist(rememberShelf);
				user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
				shelves.setWrappedData(em.createNamedQuery("SelectShelf").getResultList());
				utx.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				utx.begin();
				rememberShelf = em.merge(rememberShelf);
				shelves.setWrappedData(em.createNamedQuery("SelectShelf").getResultList());
				user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
				utx.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		newShelf();
		return "shelfList?faces-redirect=true";
	}

	/**
	 * Deletes the shelf from the current user and the database.
	 * 
	 * @return
	 */
	public String deleteShelf(Shelf shelf) {
		rememberUser = user.getRowData();
		// rememberUser = u;
		// rememberShelf = shelves.getRowData();
		rememberShelf = shelf;
		rememberUser.getShelves().remove(rememberShelf);

		try {
			utx.begin();
			rememberShelf = em.merge(rememberShelf);
			em.remove(rememberShelf);

			// rememberUser = em.merge(rememberUser);
			shelves.setWrappedData(em.createNamedQuery("SelectShelf").getResultList());
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		newShelf();
		return "shelfList?faces-redirect=true";

	}

	/**
	 * Stores the current selected shelf and it's books for listing the books in
	 * the website.
	 * 
	 * @param shelf
	 *            Selected shelf.
	 */
	public void showMyBooks(Shelf shelf) {
		rememberShelf = shelf;
		myBooks = shelf.getBooks();
	}

	/**
	 * Creates a new user object for rememberUSer.
	 */
	public void newUser() {
		System.out.println("");
		rememberUser = new User();
	}

	/**
	 * Gets the current user to edit.
	 * 
	 * @return String "userAdministration" which is the redirect to
	 *         userAdministration.xhtml
	 */
	public String editUser() {
		rememberUser = user.getRowData();
		return "showUser?faces-redirect=true";
	}

	/**
	 * Saves rememberUser from entity manager an set it into user (datamodel).
	 * 
	 * @return String "userAdministration" which is the redirect to
	 *         userAdministration.xhtml.
	 */
	public String saveUser() {
		try {
			utx.begin();
			rememberUser = em.merge(rememberUser);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		newUser();
		return "showUser?faces-redirect=true";
	}

	public String saveUser(String title, String message) {
		
		if (rememberUser.getUid() == null) {

			try {
				utx.begin();
			} catch (Exception e) {
				e.printStackTrace();
			}
			rememberUser = em.merge(rememberUser);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			Query query = em.createNamedQuery("SelectUserLogins").setParameter("login", rememberUser.getLogin());
			
			if (!query.getResultList().isEmpty()) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, title, message);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				try {
					utx.commit();

				} catch (Exception e) {
					e.printStackTrace();
				}
				return "showUser";
			} else {
				rememberUser = em.merge(rememberUser);
				em.persist(rememberUser);
				user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
				try {
					utx.commit();
				} catch (Exception e) {
					e.printStackTrace();
				}
				newUser();
				System.out.println("USER wurde neu erstellt");
				return "showUser";
			}
		}
		else
		{
			try {
				utx.begin();
				rememberUser = em.merge(rememberUser);
				user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
				utx.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			newUser();
			return "showUser?faces-redirect=true";
		
			
		}
		
	}

	/**
	 * Deletes rememberUser from user (datamodel).
	 * 
	 * @return String "userAdministration" which is the redirect to
	 *         userAdministration.xhtml.
	 */
	public String deleteUser() {
		rememberUser = user.getRowData();
		try {
			utx.begin();
			rememberUser = em.merge(rememberUser);
			em.remove(rememberUser);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			shelves.setWrappedData(em.createNamedQuery("SelectShelf").getResultList());
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		newUser();
		return "userList?faces-redirect=true";
	}

	/**
	 * 
	 * ======= Cancels the user registration process.
	 * 
	 * @return String "login" which is the redirect to login.xhtml.
	 */
	public String cancelUserRegistration() {
		return "login";
	}

	public String saveUserRegistration(String title, String message) {
		try {
			utx.begin();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Query query = em.createNamedQuery("SelectUserLogins").setParameter("login", rememberUser.getLogin());
		if (!query.getResultList().isEmpty()) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, title, message);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			try {
				utx.commit();
				;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "registerUser";
		} else {
			rememberUser = em.merge(rememberUser);
			em.persist(rememberUser);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			try {
				utx.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			newUser();
			return "login";
		}
	}

	/**
	 * Adds a book to the current shelf. A book list will be created for the
	 * shelf first, if the shelf does not has one.
	 * 
	 * @param book
	 *            Book to add.
	 * @return
	 */
	public String addBookToShelf(Book book) {
		rememberBook = book;
		rememberShelf = shelves.getRowData();
		if (rememberShelf.getBooks() == null) {
			rememberShelf.setBooks(new ArrayList<Book>());
		}
		rememberShelf.getBooks().add(rememberBook);
		try {
			utx.begin();
			rememberShelf = em.merge(rememberShelf);
			shelves.setWrappedData(em.createNamedQuery("SelectShelf").getResultList());
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "shelfList?faces-redirect=true";
	}

	/**
	 * Deletes a book from the current shelf.
	 * 
	 * 
	 * @param book
	 *            Book to delete
	 * @return
	 */

	public String deleteBookFromShelf(Book book) {
		rememberBook = book;
		// rememberShelf = shelves.getRowData();
		// rememberShelf.getBooks().remove(rememberBook);
		myBooks.remove(rememberBook);
		try {
			utx.begin();
			rememberShelf = em.merge(rememberShelf);
			shelves.setWrappedData(em.createNamedQuery("SelectShelf").getResultList());
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "shelfList?faces-redirect=true";
	}

	// /**
	// * Cancels adding or editing process of a user account.
	// *
	// * @return String "userAdministration" which is the redirect to
	// * userAdministration.xhtml
	// */
	// public String cancelUser() {
	// return "userAdministration";
	// }

	// /**
	// * Cancels shelf editing mode.
	// *
	// * @return
	// */
	// public String cancelShelf() {
	// return "XHTML";
	// }

	/**
	 * Gets user list (datamodel)
	 * 
	 * @return datamodel with users.
	 */
	public DataModel<User> getUser() {
		return user;
	}

	/**
	 * Sets the user's datamodel.
	 * 
	 * @param user
	 *            Datamodel for user.
	 */
	public void setUser(DataModel<User> user) {
		this.user = user;
	}

	/**
	 * Gets the rememberUser object.
	 * 
	 * @return Current rememberUser.
	 */
	public User getRememberUser() {
		return rememberUser;
	}

	/**
	 * Sets the rememberUser.
	 * 
	 * @param rememberUser
	 *            Current user.
	 */
	public void setRememberUser(User rememberUser) {
		this.rememberUser = rememberUser;
	}

	/**
	 * Gets shelf list (datamodel).
	 * 
	 * @return Datamodel with shelves.
	 */
	public DataModel<Shelf> getShelves() {
		return shelves;
	}

	/**
	 * Sets the datamodel for shelves.
	 * 
	 * @param shelves
	 *            New datamodel for shelves.
	 */
	public void setShelves(DataModel<Shelf> shelves) {
		this.shelves = shelves;
	}

	/**
	 * Gets the current rememberShelf.
	 * 
	 * @return Current shelf.
	 */
	public Shelf getRememberShelf() {
		return rememberShelf;
	}

	/**
	 * Sets the rememberShelf object.
	 * 
	 * @param rememberShelf
	 *            New shelf.
	 */
	public void setRememberShelf(Shelf rememberShelf) {
		this.rememberShelf = rememberShelf;
	}

	/**
	 * Gets the rememberBook object.
	 * 
	 * @return Current book.
	 */
	public Book getRememberBook() {
		return rememberBook;
	}

	/**
	 * Sets the rememberBook object.
	 * 
	 * @param rememberBook
	 *            New book.
	 */
	public void setRememberBook(Book rememberBook) {
		this.rememberBook = rememberBook;
	}

	/**
	 * Gets the list of books of the current shelf.
	 * 
	 * @return List of books.
	 */
	public List<Book> getMyBooks() {
		return myBooks;
	}

	/**
	 * Sets the list of books of the current shelf.
	 * 
	 * @param myBooks
	 *            New list of books.
	 */
	public void setMyBooks(List<Book> myBooks) {
		this.myBooks = myBooks;
	}

	public void toMainPage(User newUser) {
		this.rememberUser = newUser;
	}
}
