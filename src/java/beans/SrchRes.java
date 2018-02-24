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
public class SrchRes {
    private String name;
    private int t, c;
    private boolean returning, povez;
    private int idL, idLR, idLP, idLPR;

    public boolean isReturning() {
        return returning;
    }

    public void setReturning(boolean returning) {
        this.returning = returning;
    }

    public boolean isPovez() {
        return povez;
    }

    public void setPovez(boolean povez) {
        this.povez = povez;
    }

    public int getIdLR() {
        return idLR;
    }

    public void setIdLR(int idLR) {
        this.idLR = idLR;
    }

    public int getIdLP() {
        return idLP;
    }

    public void setIdLP(int idLP) {
        this.idLP = idLP;
    }

    public int getIdLPR() {
        return idLPR;
    }

    public void setIdLPR(int idLPR) {
        this.idLPR = idLPR;
    }

    public int getIdL() {
        return idL;
    }

    public void setIdL(int idL) {
        this.idL = idL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public SrchRes(String name, int t, int c) {
        this.name = name;
        this.t = t;
        this.c = c;
    }

    public SrchRes() {
        name = "";
        t = c = 0;
    }
    
    
}
