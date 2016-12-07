package de.hsb.webapp.bc.controller;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

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
	 * 
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
	
	public GenreType[] getGenreValues() 
	{
		return GenreType.values();
	}
}
