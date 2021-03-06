package db;
// Generated Aug 30, 2017 1:08:20 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Avioni generated by hbm2java
 */
public class Avioni  implements java.io.Serializable {


     private Integer id;
     private AvioKompanije avioKompanijeByIznajmljen;
     private AvioKompanije avioKompanijeByIdKompanije;
     private Proizvodjaci proizvodjaci;
     private String naziv;
     private String tipAviona;
     private int maxPutnika;
     private String licenca;
     private byte[] image;
     private Set iznajmljivanjes = new HashSet(0);
     private Set letovis = new HashSet(0);

    public Avioni() {
    }

	
    public Avioni(Proizvodjaci proizvodjaci, String naziv, String tipAviona, int maxPutnika, String licenca, byte[] image) {
        this.proizvodjaci = proizvodjaci;
        this.naziv = naziv;
        this.tipAviona = tipAviona;
        this.maxPutnika = maxPutnika;
        this.licenca = licenca;
        this.image = image;
    }
    public Avioni(AvioKompanije avioKompanijeByIznajmljen, AvioKompanije avioKompanijeByIdKompanije, Proizvodjaci proizvodjaci, String naziv, String tipAviona, int maxPutnika, String licenca, byte[] image, Set iznajmljivanjes, Set letovis) {
       this.avioKompanijeByIznajmljen = avioKompanijeByIznajmljen;
       this.avioKompanijeByIdKompanije = avioKompanijeByIdKompanije;
       this.proizvodjaci = proizvodjaci;
       this.naziv = naziv;
       this.tipAviona = tipAviona;
       this.maxPutnika = maxPutnika;
       this.licenca = licenca;
       this.image = image;
       this.iznajmljivanjes = iznajmljivanjes;
       this.letovis = letovis;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public AvioKompanije getAvioKompanijeByIznajmljen() {
        return this.avioKompanijeByIznajmljen;
    }
    
    public void setAvioKompanijeByIznajmljen(AvioKompanije avioKompanijeByIznajmljen) {
        this.avioKompanijeByIznajmljen = avioKompanijeByIznajmljen;
    }
    public AvioKompanije getAvioKompanijeByIdKompanije() {
        return this.avioKompanijeByIdKompanije;
    }
    
    public void setAvioKompanijeByIdKompanije(AvioKompanije avioKompanijeByIdKompanije) {
        this.avioKompanijeByIdKompanije = avioKompanijeByIdKompanije;
    }
    public Proizvodjaci getProizvodjaci() {
        return this.proizvodjaci;
    }
    
    public void setProizvodjaci(Proizvodjaci proizvodjaci) {
        this.proizvodjaci = proizvodjaci;
    }
    public String getNaziv() {
        return this.naziv;
    }
    
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    public String getTipAviona() {
        return this.tipAviona;
    }
    
    public void setTipAviona(String tipAviona) {
        this.tipAviona = tipAviona;
    }
    public int getMaxPutnika() {
        return this.maxPutnika;
    }
    
    public void setMaxPutnika(int maxPutnika) {
        this.maxPutnika = maxPutnika;
    }
    public String getLicenca() {
        return this.licenca;
    }
    
    public void setLicenca(String licenca) {
        this.licenca = licenca;
    }
    public byte[] getImage() {
        return this.image;
    }
    
    public void setImage(byte[] image) {
        this.image = image;
    }
    public Set getIznajmljivanjes() {
        return this.iznajmljivanjes;
    }
    
    public void setIznajmljivanjes(Set iznajmljivanjes) {
        this.iznajmljivanjes = iznajmljivanjes;
    }
    public Set getLetovis() {
        return this.letovis;
    }
    
    public void setLetovis(Set letovis) {
        this.letovis = letovis;
    }




}


