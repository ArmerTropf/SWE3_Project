package de.hsb.webapp.bc.controller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

/**
 * This class validates the names of the user and authors.
 * 
 * @author Thomas Schrul, Michael Günster, Andre Schriever
 *
 */
@FacesValidator(value = "isbnValidator")
public class NameValidator implements javax.faces.validator.Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		// TODO Auto-generated method stub

	}

}
