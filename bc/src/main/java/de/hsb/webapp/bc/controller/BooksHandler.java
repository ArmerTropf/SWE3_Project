package de.hsb.webapp.bc.controller;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

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
}
