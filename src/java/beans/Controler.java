/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import db.Aerodromi;
import db.Avioni;
import db.Korisnici;
import db.Letovi;
import db.Letoviradari;
import db.Osobljeleta;
import db.Radarskicentri;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.faces.context.FacesContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Brana
 */
@Named(value = "cntrl")
@SessionScoped
public class Controler implements Serializable {

    private String errormsg;
    private String usr;
    private String pass;
    private String tip;
    private String newPass;
    private List<AttLet> letovi;
    private StreamedContent slika;
    private byte[] im;

    public byte[] getIm() {
        return im;
    }

    public void setIm(byte[] im) {
        this.im = im;
    }

    public StreamedContent getSlika() {
        return slika;
    }

    public void setSlika(StreamedContent slika) {
        this.slika = slika;
    }

    public List<AttLet> getLetovi() {
        return letovi;
    }

    public void setLetovi(List<AttLet> letovi) {
        this.letovi = letovi;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
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

    public String logIn() throws IOException {
        if ("".equals(usr)) {
            errormsg = "Input all required data";
            RequestContext.getCurrentInstance().update("formX:error");
            return "index";
        }
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Korisnici k = (Korisnici) s.load(Korisnici.class, usr); //pitaj da l' je get null, pa cast
        if (!k.getLozinka().equals(pass)) {
            errormsg = "Incorrect password";
            RequestContext.getCurrentInstance().update("formX:error");
            return "index";
        }
        FacesContext c = FacesContext.getCurrentInstance();
        c.getExternalContext().getSessionMap().put("user", k);
        if ("pilot".equals(k.getPozicija())) {
            if (k.getPrviLogin() == 1) {
                String sql = "SELECT DISTINCT licenca FROM avioni";
                SessionFactory sf2 = new Configuration().configure().buildSessionFactory();
                Session s2 = sf2.openSession();
                List<String> lic = (List<String>) s2.createSQLQuery(sql).list();
                c.getExternalContext().getSessionMap().put("lic", lic);
                return "pilotf";
            } else {

                java.util.Date dd = new java.util.Date();
                long milis = dd.getTime();
                milis += TimeUnit.MINUTES.toMillis(30);
                Date dt1 = new Date(milis);
                milis += TimeUnit.DAYS.toMillis(1);
                Date dt2 = new Date(milis);

                SessionFactory sf2 = new Configuration().configure().buildSessionFactory();
                Session s2 = sf2.openSession();
                Criteria cr1 = s2.createCriteria(Letovi.class);
                cr1.add(Restrictions.ge("vremePoletanja", dt1));
                cr1.add(Restrictions.lt("vremePoletanja", dt2));
                List<Letovi> l = cr1.list();
                List<Letovi> l1 = new ArrayList<>();
                for (int i = 0; i < l.size(); i++) {
                    Criteria cr2 = s2.createCriteria(Osobljeleta.class);
                    cr2.add(Restrictions.eq("letovi", l.get(i)));
                    List<Osobljeleta> ol = cr2.list();
                    boolean exists = false;
                    for (int j = 0; j < ol.size(); j++) {
                        if (ol.get(j).getKorisnici().getKorIme().equals(usr)) {
                            exists = true;
                        }
                    }
                    if (exists) {
                        l1.add(l.get(i));
                    }
                }
                c.getExternalContext().getSessionMap().put("ff", l1);

                String sql = "SELECT\n"
                        + "  L.id\n"
                        + "FROM\n"
                        + "  letovi L,\n"
                        + "  osobljeleta O\n"
                        + "WHERE\n"
                        + "  L.id = O.idLeta AND (L.status = 'act' OR L.status = 'ptl') AND O.idKorisnika = '" + usr + "'";
                List<Integer> o = (List<Integer>) s.createSQLQuery(sql).list();
                if (!o.isEmpty()) {
                    int id = o.get(0);
                    Letovi ac = (Letovi) s.get(Letovi.class, id);
                    Criteria cr3 = s.createCriteria(Letoviradari.class);
                    cr3.add(Restrictions.eq("letovi", ac));
                    List<Letoviradari> lr = cr3.list();
                    List<Radarskicentri> rr = new ArrayList<>();
                    Aerodromi a1 = ac.getGejtoviiterminaliByDepgejt().getAerodromi();
                    Aerodromi a2 = ac.getGejtoviiterminaliByArrgejt().getAerodromi();
                    for (Letoviradari lr1 : lr) {
                        if (!lr1.getRadarskicentri().getGrad().equals(a1.getGrad())
                                && !lr1.getRadarskicentri().getGrad().equals(a2.getGrad())
                                && !lr1.getRadarskicentri().getZemlja().equals(a1.getZemlja())
                                && !lr1.getRadarskicentri().getZemlja().equals(a2.getZemlja())) {
                            rr.add(lr1.getRadarskicentri());
                        }
                    }
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("active", ac);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("activeRR", rr);
                    return "pilot";
                } else {
                    java.util.Date d1 = new java.util.Date();
                    long m = d1.getTime();
                    m -= TimeUnit.MINUTES.toMillis(15);
                    Date d = new Date(m);
                    Criteria cr = s.createCriteria(Letovi.class);
                    cr.add(Restrictions.gt("vremePoletanja", d));
                    List<Letovi> ll = cr.list();
                    Letovi nextt = ll.get(0);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("next", nextt);
                    return "pilot";
                }
            }
        }
        if ("admin".equals(k.getPozicija()) && "ad".equals(tip)) {
            SessionFactory sf2 = new Configuration().configure().buildSessionFactory();
            Session s2 = sf2.openSession();
            /*String sql = "SELECT\n"
                    + "  A.grad AS grad1,\n"
                    + "  B.grad AS grad2,\n"
                    + "  T.ImePrezime,\n"
                    + "  T.brojPasosa,\n"
                    + "  T.brojKartice,\n"
                    + "  T.id\n"
                    + "FROM\n"
                    + "  gostirezervacije T,\n"
                    + "  aerodromi A,\n"
                    + "  aerodromi B,\n"
                    + "  letovi C\n"
                    + "INNER JOIN\n"
                    + "  gejtoviiterminali G\n"
                    + "ON\n"
                    + "  (C.depgejt = G.gejt)\n"
                    + "INNER JOIN\n"
                    + "  gejtoviiterminali D\n"
                    + "ON\n"
                    + "  (C.arrgejt = D.gejt)\n"
                    + "WHERE\n"
                    + "  C.id = T.idLeta AND a.IATA = g.IATA AND b.IATA = d.IATA AND T.odobreno = 0";
            List<Object[]> o = s2.createSQLQuery(sql).list();
            List<GostLet> g = new ArrayList<>();
            for (int i = 0; i < o.size(); i++) {
                GostLet gost = new GostLet(o.get(i)[0].toString() + " - " + o.get(i)[1].toString(), o.get(i)[2].toString(), o.get(i)[3].toString(), o.get(i)[4].toString());
                gost.setId((int) o.get(i)[5]);
                g.add(gost);
            }*/
            Criteria cr = s2.createCriteria(Korisnici.class);
            cr.add(Restrictions.eq("odobreno", 0));
            List<Korisnici> kor = cr.list();
            c.getExternalContext().getSessionMap().put("kor", kor);
            return "admin.xhtml";
        }
        if ("stjuardesa".equals(k.getPozicija())) {
            List<AttLet> a = new ArrayList<>();
            List<AttLet> b = new ArrayList<>();
            SessionFactory sf2 = new Configuration().configure().buildSessionFactory();
            Session s2 = sf2.openSession();
            Criteria cr = s2.createCriteria(Osobljeleta.class);
            List<Osobljeleta> ols = cr.list();
            int i = 0;
            for (Osobljeleta ol : ols) {
                if (ol.getKorisnici().getKorIme().equals(usr)) {
                    Letovi l = ol.getLetovi();
                    AttLet al = new AttLet();
                    al.setId(i);
                    al.setPoletanje(l.getVremePoletanja().toString());
                    al.setT(l.getTrajanjeLeta());
                    al.setNaziv(l.getGejtoviiterminaliByDepgejt().getAerodromi().getGrad() + " - " + l.getGejtoviiterminaliByArrgejt().getAerodromi().getGrad());
                    al.setC(l.getAvioni().getMaxPutnika());
                    java.util.Date d = new java.util.Date();
                    if (l.getVremePoletanja().before(d)) {
                        a.add(al);
                    } else {
                        b.add(al);
                    }
                    i++;
                }
            }
            c.getExternalContext().getSessionMap().put("att1", a);
            c.getExternalContext().getSessionMap().put("att2", b);
            return "attendant.xhtml";
        }
        if ("radnik".equals(k.getPozicija()) && "rd".equals(tip)) {
            List<WorkLet> w = new ArrayList<>();
            SessionFactory sf2 = new Configuration().configure().buildSessionFactory();
            Session s2 = sf2.openSession();
            Criteria cr = s2.createCriteria(Letovi.class);
            java.util.Date d = new java.util.Date();
            d.setHours(0);
            d.setMinutes(0);
            d.setSeconds(0);
            java.util.Date d2 = new java.util.Date(d.getTime() - TimeUnit.DAYS.toMillis(1));
            cr.add(Restrictions.ge("vremePoletanja", d2));
            List<Letovi> lds = cr.list();
            int i = 0;
            for (Letovi ld : lds) {
                WorkLet wl = new WorkLet();
                wl.setId(ld.getId());
                if (ld.getVremeSletanja() != null) {
                    wl.setLdVreme(ld.getVremeSletanja().toString());
                    wl.setUrgVreme(ld.getVremeSletanja().toString());
                    if (ld.getGejtoviiterminaliByUrggejt() != null) {
                        wl.setUrgA(ld.getGejtoviiterminaliByUrggejt().getAerodromi().getGrad());
                    }
                }
                wl.setNaziv(ld.getGejtoviiterminaliByDepgejt().getAerodromi().getGrad() + " - " + ld.getGejtoviiterminaliByArrgejt().getAerodromi().getGrad());
                wl.setStatus(ld.getStatus());
                wl.setVreme(ld.getVremePoletanja().toString());
                wl.setWid(i);
                if (!ld.getPlaniranoVreme().before(d) && (ld.getAvioni().getAvioKompanijeByIdKompanije().getId() == k.getAvioKompanije().getId()
                        || ld.getAvioni().getAvioKompanijeByIznajmljen().getId() == k.getAvioKompanije().getId())) {
                    w.add(wl);
                }
                i++;
            }

            Criteria cr2 = s2.createCriteria(Avioni.class);
            cr2.add(Restrictions.isNull("avioKompanijeByIdKompanije"));
            List<Avioni> ar = cr2.list();
            c.getExternalContext().getSessionMap().put("craft", ar);
            c.getExternalContext().getSessionMap().put("work", w);
            s2.close();
            s.close();
            return "worker.xhtml";
        }
        errormsg = "Error!";

        RequestContext.getCurrentInstance()
                .update("formX:error");

        return "index.xhtml";
    }

    public String skip() {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Criteria cr = s.createCriteria(Aerodromi.class);
        List<Aerodromi> ar = cr.list();
        Map<String, Aerodromi> mapAR = new HashMap<>();
        List<String> avail = new ArrayList<>();
        for (Aerodromi arr : ar) {
            String x = arr.getGrad() + " (" + arr.getNaziv() + ", " + arr.getIata() + ")";
            avail.add(x);
            mapAR.put(x, arr);
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("airports", avail);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("mapA", mapAR);
        return "guest";
    }

    public String chgPass() {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Transaction tr = s.beginTransaction();
        Korisnici k = (Korisnici) s.load(Korisnici.class, usr);
        k.setLozinka(newPass);
        s.saveOrUpdate(k);
        s.flush();
        tr.commit();
        return "index.xhtml";
    }

    public void chngPswd() {
        if ("".equals(usr)) {
            errormsg = "Input all required data";
            RequestContext.getCurrentInstance().update("formX:error");
            return;
        }
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Transaction tr = s.beginTransaction();
        Korisnici k = (Korisnici) s.load(Korisnici.class,
                usr);
        if (!k.getLozinka().equals(pass)) {
            errormsg = "Incorect password";
            RequestContext.getCurrentInstance().update("formX:error");
            return;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('passDialog').show();");
    }

    public String signUp() {
        return "register.xhtml";
    }

    public String logOut() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml";
    }

    public void onload() {
        if (letovi.isEmpty()) {
            SessionFactory sf = new Configuration().configure().buildSessionFactory();
            Session s = sf.openSession();
            Criteria cr = s.createCriteria(Letovi.class);
            java.util.Date d = new java.util.Date();
            d.setHours(0);
            d.setMinutes(0);
            d.setSeconds(0);
            java.util.Date d2 = new java.util.Date(d.getTime() + TimeUnit.DAYS.toMillis(1));
            List<Letovi> lts = cr.list();
            for (Letovi lt : lts) {
                if (lt.getVremePoletanja().getDay() == d.getDay() && lt.getVremePoletanja().getMonth() == d.getMonth() && lt.getVremePoletanja().getYear() == d.getYear()) {
                    AttLet al = new AttLet();
                    al.setNaziv(lt.getGejtoviiterminaliByDepgejt().getAerodromi().getGrad() + " - " + lt.getGejtoviiterminaliByArrgejt().getAerodromi().getGrad());
                    if (Math.abs(lt.getVremePoletanja().getTime() - d.getTime()) < TimeUnit.DAYS.toMillis(1)) {
                        letovi.add(al);
                    }
                } else if (lt.getPlaniranoVreme().getDay() == d.getDay() && lt.getPlaniranoVreme().getMonth() == d.getMonth() && lt.getPlaniranoVreme().getYear() == d.getYear()) {
                    AttLet al = new AttLet();
                    al.setNaziv(lt.getGejtoviiterminaliByDepgejt().getAerodromi().getGrad() + " - " + lt.getGejtoviiterminaliByArrgejt().getAerodromi().getGrad());
                    if (Math.abs(lt.getPlaniranoVreme().getTime() - d.getTime()) < TimeUnit.DAYS.toMillis(1)) {
                        letovi.add(al);
                    }
                }

            }
            /*for (int i = 0; i < o.size(); i++) {
                AttLet al = new AttLet(o.get(i)[0].toString() + " - " + o.get(i)[1].toString(), (int) o.get(i)[2], (int) o.get(i)[3], o.get(i)[4].toString());
                al.setId(i);
                letovi.add(al);
            }*/
            RequestContext.getCurrentInstance().update("formX:formY:Flights1DT");
        }
    }

    public Controler() {
        letovi = new ArrayList<>();
    }

}
