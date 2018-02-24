/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validate;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import static java.lang.Character.isLowerCase;
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
@FacesValidator("passValidator")
public class passValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String pass = (String) value;
        boolean bigOne = false, smallThree = false, len = false,
                num = false, spec = false, start = false, cont = true;
        if(pass.length()>=8 && pass.length() <= 10) len = true;
        int small = 0;
        char c = pass.charAt(0);
        char b1 = 0;
        char b2 = 0;
        if(isLetter(c)) start = true;
        for (int i = 0; i < pass.length(); i++) {
            c = pass.charAt(i);
            if(isLowerCase(c)) small++;
            else bigOne = true;
            if(isDigit(c)) num = true;
            if(!isDigit(c) && !isLetter(c)) spec = true;
            if(c == b1 && c == b2) cont = false;
            b2 = b1;
            b1 = c;
        }
        if(small >= 3) smallThree = true;
        if(!bigOne || !smallThree || !len || !num || !spec || !start || !cont){
            String message = "Password has to be in correct format";
            throw new ValidatorException(new FacesMessage(message));
        }
    }

}
