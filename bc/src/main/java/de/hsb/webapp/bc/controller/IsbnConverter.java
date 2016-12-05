package de.hsb.webapp.bc.controller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

/**
 * This class converts the ISBN of the books.
 * 
 * @author Thomas Schrul, Michael Günster, Andre Schriever
 *
 */
@FacesConverter(value = "isbnConverter")
public class IsbnConverter implements javax.faces.convert.Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

}
