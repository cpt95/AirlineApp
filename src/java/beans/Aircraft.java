/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.ByteArrayInputStream;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Brana
 */
public class Aircraft {
    private int id;
    private String tip, ime;
    private byte[] img;
    private StreamedContent simg;

    public StreamedContent getSimg() {
        return simg;
    }

    public void setSimg(StreamedContent simg) {
        this.simg = simg;
    }

    public Aircraft() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
        this.simg = new DefaultStreamedContent(new ByteArrayInputStream(img));
    }

    public Aircraft(String tip, String ime, byte[] img) {
        this.tip = tip;
        this.ime = ime;
        this.img = img;
    }
    
}
