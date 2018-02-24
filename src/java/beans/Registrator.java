package beans;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import db.AvioKompanije;
import db.Korisnici;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Brana
 */
@Named(value = "reg")
@SessionScoped
public class Registrator implements Serializable {

    private String errormsg, usr, pass, cpass, name, sname, email, komp, sex, pos;
    private Date birth;

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    
    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCpass() {
        return cpass;
    }

    public void setCpass(String cpass) {
        this.cpass = cpass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKomp() {
        return komp;
    }

    public void setKomp(String komp) {
        this.komp = komp;
    }

    public void register(){
        if (!pass.equals(cpass)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Passwords don't match"));
            return;
        }
        Korisnici k = new Korisnici();
        k.setKorIme(usr);
        k.setLozinka(pass);
        birth.setYear(birth.getYear()-18);
        k.setDatumRodjenja(birth);
        k.setIme(name);
        k.setPrezime(sname);
        k.setPol(sex);
        k.setEmail(email);
        k.setPozicija(pos);
        k.setOdobreno(0);
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        k.setAvioKompanije((AvioKompanije) s.get(AvioKompanije.class, Integer.parseInt(komp)));
        SessionFactory sf2 = new Configuration().configure().buildSessionFactory();
        Session s2 = sf2.openSession();
        Transaction tr = s2.beginTransaction();
        s2.save(k);
        s2.flush();
        tr.commit();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('regDialog').hide();");
    }
    
    public Registrator() {
    }

}
