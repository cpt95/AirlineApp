package db;
// Generated Aug 30, 2017 1:08:20 PM by Hibernate Tools 4.3.1



/**
 * Iznajmljivanje generated by hbm2java
 */
public class Iznajmljivanje  implements java.io.Serializable {


     private Integer id;
     private AvioKompanije avioKompanije;
     private Avioni avioni;
     private int ponuda;

    public Iznajmljivanje() {
    }

    public Iznajmljivanje(AvioKompanije avioKompanije, Avioni avioni, int ponuda) {
       this.avioKompanije = avioKompanije;
       this.avioni = avioni;
       this.ponuda = ponuda;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public AvioKompanije getAvioKompanije() {
        return this.avioKompanije;
    }
    
    public void setAvioKompanije(AvioKompanije avioKompanije) {
        this.avioKompanije = avioKompanije;
    }
    public Avioni getAvioni() {
        return this.avioni;
    }
    
    public void setAvioni(Avioni avioni) {
        this.avioni = avioni;
    }
    public int getPonuda() {
        return this.ponuda;
    }
    
    public void setPonuda(int ponuda) {
        this.ponuda = ponuda;
    }




}


