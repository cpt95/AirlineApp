/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import db.AvioKompanije;

/**
 *
 * @author Brana
 */
public class Izn {

    private String a, k;
    private int p, id, idA;
    private AvioKompanije ak;

    public AvioKompanije getAk() {
        return ak;
    }

    public void setAk(AvioKompanije ak) {
        this.ak = ak;
    }

    Izn() {
        a = k = "";
        p = id = idA = 0;
        ak = null;
    }

    public int getIdA() {
        return idA;
    }

    public void setIdA(int idA) {
        this.idA = idA;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Izn(String a, String k, int p) {
        this.a = a;
        this.k = k;
        this.p = p;
    }

}
