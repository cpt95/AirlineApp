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
public class Terminal {
    private int id;
    private String gejtovi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGejtovi() {
        return gejtovi;
    }

    public void setGejtovi(String gejtovi) {
        this.gejtovi = gejtovi;
    }

    public Terminal() {
    }

    public Terminal(int id, String gejtovi) {
        this.id = id;
        this.gejtovi = gejtovi;
    }
    
}
