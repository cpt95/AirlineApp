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
public class AttLet {
    private String naziv;
    private int t, c;
    private String poletanje;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public String getPoletanje() {
        return poletanje;
    }

    public void setPoletanje(String poletanje) {
        this.poletanje = poletanje;
    }

    public AttLet() {
    }

    public AttLet(String naziv, int t, int c, String poletanje) {
        this.naziv = naziv;
        this.t = t;
        this.c = c;
        this.poletanje = poletanje;
    }
    
    
}
