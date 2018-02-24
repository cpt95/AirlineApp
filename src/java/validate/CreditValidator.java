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
@FacesValidator("credValidator")
public class CreditValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String cred = (String) value;
        boolean f = true;
        for (int i = 0; i < cred.length(); i++) {
            if (!isDigit(cred.charAt(i))) {
                f = false;
            }
        }
        if (cred.length() != 16) {
            f = false;
        }
        if (!f) {
            String message = "Please enter 16 digits";
            throw new ValidatorException(new FacesMessage(message));
        }
    }

}
