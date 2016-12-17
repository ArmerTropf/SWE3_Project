package de.hsb.webapp.bc.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.*;

import de.hsb.webapp.bc.model.Book;
import de.hsb.webapp.bc.model.GenreType;
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
	 * Initializes the data model with some user for the first use.
	 */
	@PostConstruct
	public void init() {
		try {
			utx.begin();
			em.persist(new User("Guenster", "Michael", "12345", "mguenster", true));
			em.persist(new User("Schrul", "Thomas", "12345", "tschrul", true));
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
	public String newShelf() {
		rememberUser = user.getRowData();
		System.out.println("NEW USER"+ rememberUser.getLogin());
		rememberShelf = new Shelf();


		if (rememberUser.getShelves() == null) {
			rememberUser.setShelves(new ArrayList<Shelf>());
		}
		return "addShelf";
	}

	/**
	 * Edits an existing shelf.
	 * 
	 * @return
	 */
	public String editShelf() {
		rememberUser = user.getRowData();
		rememberShelf = shelves.getRowData();
		
		return "sdsdsd";
	}

	/**
	 * Saves changes to the current shelf and adds a new shelf to the shelves
	 * list of the user if the shelf is new.
	 * 
	 * @return
	 */
	public String saveShelf() {
		if (rememberShelf.getSid() == null) {
			try {
				rememberUser.getShelves().add(rememberShelf);
							
				utx.begin();
				rememberUser = em.merge(rememberUser);
//				em.persist(arg0);

				
				user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
				shelves.setWrappedData(em.createNamedQuery("SelectShelf").getResultList());
				utx.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				System.out.println("getSID not null");
				utx.begin();
				rememberShelf = em.merge(rememberShelf);
				user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
				shelves.setWrappedData(em.createNamedQuery("SelectShelf").getResultList());
				utx.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "showOwnLibrary";
	}

	/**
	 * Deletes the shelf from the current user and the database.
	 * 
	 * @return
	 */
	public String deleteShelf() {
		rememberUser = user.getRowData();
		rememberShelf = shelves.getRowData();
		rememberUser.getShelves().remove(rememberShelf);

		try {
			utx.begin();
			rememberShelf = em.merge(rememberShelf);
			em.remove(rememberShelf);
			rememberUser = em.merge(rememberUser);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			shelves.setWrappedData(em.createNamedQuery("SelectShelf").getResultList());
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Delete Shelf");
		return "showOwnLibrary";
	}

	/**
	 * Cancels shelf editing mode.
	 * 
	 * @return
	 */
	public String cancelShelf() {
		return "showOwnLibrary";
	}

	/**
	 * Prepare remeberUser for first use. initialize with userobject.
	 * 
	 * @return String "addUser" which is the redirect to addUser.xhtml.
	 */
	public String newUser() {
		rememberUser = new User();
		return "addUser";
	}

	/**
	 * Edit rememberUser to edit
	 * 
	 * @return String "userAdministration" which is the redirect to
	 *         userAdministration.xhtml
	 */
	public String editUser() {
		rememberUser = user.getRowData();
		return "addUser";
	}

	/**
	 * Saves rememberUser from entity manager an set it into user (datamodel).
	 * 
	 * @return String "userAdministration" which is the redirect to
	 *         userAdministration.xhtml.
	 */
	public String saveUser() {
		try {
			System.out.println("fick dich in arsch");
			utx.begin();
			rememberUser = em.merge(rememberUser);
	
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "showOwnLibrary";
	}

	/**
	 * Deletes rememberUser from Entitymanager an set it into user (Datamodel).
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
		return "userAdministration";
	}

	/**
	 * Cancels adding or editing process of a user account.
	 * 
	 * @return String "userAdministration" which is the redirect to
	 *         userAdministration.xhtml
	 */
	public String cancelUser() {
		return "showOwnLibrary";
	}

	/**
	 * Cancels the user registration.
	 * 
	 * @return
	 */
	public String cancelUserRegistration() {
		return "login";
	}

	/**
	 * Adds a book to the current shelf.
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

		return "XHTML";
	}

	/**
	 * Deletes a book from the current shelf.
	 * 
	 * @param book
	 *            Book to delete
	 * @return
	 */
	public String deleteBookFromShelf(Book book) {
		rememberBook = book;
		rememberShelf = shelves.getRowData();
		rememberShelf.getBooks().remove(rememberBook);
		try {
			utx.begin();
			rememberShelf = em.merge(rememberShelf);
			shelves.setWrappedData(em.createNamedQuery("SelectShelf").getResultList());
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "XHTML";
	}

	/**
	 * Gets the current userlsit (datamodel)
	 * 
	 * @return datamodel with users.
	 */
	public DataModel<User> getUser() {
		return user;
	}

	/**
	 * Sets the user into datamodel.
	 * 
	 * @param DataModel<User>
	 *            user Set datamodel for user.
	 */
	public void setUser(DataModel<User> user) {
		this.user = user;
	}

	/**
	 * Gets the rememberdUser object.
	 * 
	 * @return Current rememberUser.
	 */
	public User getrememberUser() {
		return rememberUser;
	}

	/**
	 * Sets the rememberUser.
	 * 
	 * @param rememberUser
	 */
	public void setrememberUser(User rememberUser) {
		this.rememberUser = rememberUser;
	}

	public DataModel<Shelf> getShelves() {
		return shelves;
	}

	public void setShelves(DataModel<Shelf> shelves) {
		this.shelves = shelves;
	}

	public User getRememberUser() {
		return rememberUser;
	}

	public void setRememberUser(User rememberUser) {
		this.rememberUser = rememberUser;
	}

	public Shelf getRememberShelf() {
		return rememberShelf;
	}

	public void setRememberShelf(Shelf rememberShelf) {
		this.rememberShelf = rememberShelf;
	}

	public Book getRememberBook() {
		return rememberBook;
	}

	public void setRememberBook(Book rememberBook) {
		this.rememberBook = rememberBook;
	}
//*************** GÜNSTER
//****************************************************************************
	private String username;
    
    private String password;
    
    User loggedInUser = new User();
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
	
	 public String login() 
	    {
	    	FacesMessage message = null;
	    	
	    	System.out.println(this.username);
	    	System.out.println(this.password);
	    		
	      try {
				utx.begin();
			} catch (NotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		  	Query query = em.createNamedQuery("User.findByName")
		  			.setParameter("login", this.username)
		  			.setParameter("password", this.password);
		  	//Laesst beim abmelden eine erneute anmeldung zu
		  	query.setMaxResults(1);
		  	
		  	if(query.getResultList().isEmpty())
		  	{
		  		System.out.println("Name oder passord falsch nutte");
		  		
		  		message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
		  		FacesContext.getCurrentInstance().addMessage(null, message);
		  		
		  		try {
					utx.commit();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RollbackException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (HeuristicMixedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (HeuristicRollbackException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SystemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	  
		  		
		  		
		  		return "login";
		  	}
		  	else 
		  	{
		  		
		  		
		  		
		  		loggedInUser = (User) query.getSingleResult();
		  	  	this.rememberUser = loggedInUser;
		  	  	

		  	  	////	  	  	System.out.println(rememberUser.getShelves().get(0).getName());////	  	  	System.out.println(rememberUser.getShelves().get(1).getName());////	  	  	System.out.println(rememberUser.getShelves().get(2).getName());////	  	  	rememberUser.getShelves().get(0).getBooks().add(new Book("regal0buch1", "123456", GenreType.FANTASY, new GregorianCalendar(2010, 9, 26).getTime()));////	  	  	rememberUser.getShelves().get(0).getBooks().add(new Book("regal0buch2", "123456", GenreType.FANTASY, new GregorianCalendar(2010, 9, 26).getTime()));////	  	  	rememberUser.getShelves().get(1).getBooks().add(new Book("regal1buch1", "123456", GenreType.FANTASY, new GregorianCalendar(2010, 9, 26).getTime()));////	  	  	rememberUser.getShelves().get(1).getBooks().add(new Book("regal1buch2", "123456", GenreType.FANTASY, new GregorianCalendar(2010, 9, 26).getTime()));////	  	  	rememberUser.getShelves().get(2).getBooks().add(new Book("regal2buch1", "123456", GenreType.FANTASY, new GregorianCalendar(2010, 9, 26).getTime()));

		  	  	rememberUser = em.merge(rememberUser);
		  	  	em.persist(rememberUser);

		  	  	
		  	  	message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", this.username);
		  	  	FacesContext.getCurrentInstance().addMessage(null, message);
		  	  	
		  	  	
		  	  try {
					utx.commit();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RollbackException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (HeuristicMixedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (HeuristicRollbackException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SystemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	  	
		  	  	
		  	
		  	  
		  	  	return "showOwnLibrary";
		  	}
	  
	    } 
	 
	 private String userIsNoAdmin;
		private String userIsLoggedIn;
		
		public String getUserIsNoAdmin() {
			return userIsNoAdmin;
		}

		public void setUserIsNoAdmin(String userIsNoAdmin) {
			this.userIsNoAdmin = userIsNoAdmin;
		}

		public String getUserIsLoggedIn() {
			return userIsLoggedIn;
		}

		public void setUserIsLoggedIn(String userIsLoggedIn) {
			this.userIsLoggedIn = userIsLoggedIn;
		}
	 
	 public String logout()
		{
//			rememberUser = null;
//			return "login";
		 FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			setUserIsNoAdmin("true");
			setUserIsLoggedIn("false");
			return "login.jsf?faces-redirect=true";
	}
	 
	

}
