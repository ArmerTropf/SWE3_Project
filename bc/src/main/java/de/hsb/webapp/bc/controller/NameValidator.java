package de.hsb.webapp.bc.controller;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
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
@FacesValidator(value = "nameValidator")
public class NameValidator implements javax.faces.validator.Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String name = String.valueOf(value);
		FacesMessage message;
		for (int i = 0; i < name.length(); i++)
			if (!name.matches("[A-Za-z]+")) {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ungültiger Name",
						"Es dürfen nur lateinische Buchstaben enthalten sein!");
				throw new ValidatorException(message);
			}
	}
}
