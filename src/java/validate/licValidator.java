/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validate;

import static java.lang.Character.isDigit;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Brana
 */
@FacesValidator("licValidator")
public class licValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String lic = (String) value;
        boolean num = true;
        char c;
        for (int i = 0; i < lic.length(); i++) {
            c = lic.charAt(i);
            if(!isDigit(c)) num = false;
        }
        if (lic.length() != 7 || !num) {
            String message = "Enter 7-digit number!";
            throw new ValidatorException(new FacesMessage(message));
        }
    }
}
