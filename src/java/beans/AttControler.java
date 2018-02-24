/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author Brana
 */
@Named(value = "actrl")
@SessionScoped
public class AttControler implements Serializable {

    private AttLet a, b;

    public AttLet getA() {
        return a;
    }

    public void setA(AttLet a) {
        this.a = a;
    }

    public AttLet getB() {
        return b;
    }

    public void setB(AttLet b) {
        this.b = b;
    }
    
    public AttControler() {
    }
    
}
