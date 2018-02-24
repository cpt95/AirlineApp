/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validate;

import java.util.Date;
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
@FacesValidator("ageValidator")
public class AgeValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Date birth = (Date) value;
        birth.setYear(birth.getYear() + 18);
        Date today = new Date();
        
        if(birth.after(today)){
            String message = "You have to be at least 18 year old";
            throw new ValidatorException(new FacesMessage(message));
        }
    }
    
}
