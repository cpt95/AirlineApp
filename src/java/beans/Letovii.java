/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author Brana
 */
public class Letovii {
    private String grad1, grad2;
    private int slobodnaMesta, trajanje;

    public Letovii(String grad1, String grad2, int trajanje, int slobodnaMesta) {
        this.grad1 = grad1;
        this.grad2 = grad2;
        this.trajanje = trajanje;
        this.slobodnaMesta = slobodnaMesta;
    }
    

    public Letovii() {
        grad1 = grad2;
        trajanje = slobodnaMesta = 0;
    }

    public String getGrad1() {
        return grad1;
    }

    public void setGrad1(String grad1) {
        this.grad1 = grad1;
    }

    public String getGrad2() {
        return grad2;
    }

    public void setGrad2(String grad2) {
        this.grad2 = grad2;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public int getSlobodnaMesta() {
        return slobodnaMesta;
    }

    public void setSlobodnaMesta(int slobodnaMesta) {
        this.slobodnaMesta = slobodnaMesta;
    }
    
    public void print (){
        System.out.println(grad1);
        System.out.println(grad2);
        System.out.println(trajanje);
        System.out.println(slobodnaMesta);
    }
    
}
