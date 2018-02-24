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
public class WorkLet {
    private String naziv, vreme, status, urgA, urgVreme, ldVreme;
    private int id, wid;

    public WorkLet() {
        
    }

    public String getLdVreme() {
        return ldVreme;
    }

    public void setLdVreme(String ldVreme) {
        this.ldVreme = ldVreme;
    }

    public String getUrgA() {
        return urgA;
    }

    public void setUrgA(String urgA) {
        this.urgA = urgA;
    }

    public String getUrgVreme() {
        return urgVreme;
    }

    public void setUrgVreme(String urgVreme) {
        this.urgVreme = urgVreme;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getVreme() {
        return vreme;
    }

    public void setVreme(String vreme) {
        this.vreme = vreme;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WorkLet(String naziv, String vreme, String status, int id) {
        this.naziv = naziv;
        this.vreme = vreme;
        this.status = status;
        this.id = id;
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }
    
    
}
