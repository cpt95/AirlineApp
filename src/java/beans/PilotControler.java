/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import db.Aerodromi;
import db.AvioKompanije;
import db.Gejtoviiterminali;
import db.Korisnici;
import db.Letovi;
import db.Letoviradari;
import db.Licence;
import db.Radarskicentri;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
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
@Named(value = "pctrl")
@SessionScoped
public class PilotControler implements Serializable {

    private int c, selectFD;
    private double speed, dist;
    private boolean emgD = true;
    private String lic, typ, selectG, selectE, emgG;
    private Radarskicentri selectedR;
    private Letovi selectF, selectL;
    private Letoviradari selectLR;
    private Gejtoviiterminali selectGT;
    private List<String> listG, listE, gejtEmg;
    private Map<String, Aerodromi> mapaAr;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public String getSelectG() {
        return selectG;
    }

    public void setSelectG(String selectG) {
        this.selectG = selectG;
    }

    public List<String> getListG() {
        return listG;
    }

    public void setListG(List<String> listG) {
        this.listG = listG;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getLic() {
        return lic;
    }

    public void setLic(String lic) {
        this.lic = lic;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public List<String> getListE() {
        return listE;
    }

    public void setListE(List<String> listE) {
        this.listE = listE;
    }

    public String getSelectE() {
        return selectE;
    }

    public void setSelectE(String selectE) {
        this.selectE = selectE;
    }

    public boolean isEmgD() {
        return emgD;
    }

    public void setEmgD(boolean emgD) {
        this.emgD = emgD;
    }

    public String getEmgG() {
        return emgG;
    }

    public void setEmgG(String emgG) {
        this.emgG = emgG;
    }

    public List<String> getGejtEmg() {
        return gejtEmg;
    }

    public void setGejtEmg(List<String> gejtEmg) {
        this.gejtEmg = gejtEmg;
    }

    public String first() {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Transaction tr = s.beginTransaction();
        FacesContext x = FacesContext.getCurrentInstance();
        Korisnici temp = (Korisnici) x.getExternalContext().getSessionMap().get("user");
        Korisnici k = (Korisnici) s.load(Korisnici.class, temp.getKorIme());
        Licence l = new Licence();
        l.setKorisnici(k);
        l.setLic(lic);
        l.setTip(typ);
        k.setPrviLogin(0);
        s.saveOrUpdate(k);
        s.save(l);
        s.flush();
        tr.commit();
        return "pilot";
    }

    public void change(Letovi x, int a) {
        selectFD = a;
        selectF = x;
        listG = new ArrayList<>();
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        Gejtoviiterminali gt = null;
        if (a == 2) {
            gt = (Gejtoviiterminali) s.get(Gejtoviiterminali.class, x.getGejtoviiterminaliByArrgejt().getGejt());
        } else if (a == 1) {
            gt = (Gejtoviiterminali) s.get(Gejtoviiterminali.class, x.getGejtoviiterminaliByDepgejt().getGejt());
        }
        Criteria cr = s.createCriteria(Gejtoviiterminali.class);
        cr.add(Restrictions.eq("aerodromi", gt.getAerodromi()));
        List<Gejtoviiterminali> gtl = cr.list();
        for (int i = 0; i < gtl.size(); i++) {
            listG.add(gtl.get(i).getGejt());
        }
        s.close();
        RequestContext.getCurrentInstance().update("formY:ngt");
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('gateDialog').show();");
    }

    public void submit() {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        List<Letovi> ll = (List<Letovi>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ff");
        Letovi l = null;
        String sql = "";
        selectGT = (Gejtoviiterminali) s.get(Gejtoviiterminali.class, selectG);
        for (int i = 0; i < ll.size(); i++) {
            if (ll.get(i) == selectF) {
                if (selectFD == 1) {
                    ll.get(i).setGejtoviiterminaliByDepgejt(selectGT);
                    sql = "UPDATE\n"
                            + "  `letovi`\n"
                            + "SET\n"
                            + "  `depgejt` = '" + selectGT.getGejt() + "'\n"
                            + "WHERE\n"
                            + " id = " + ll.get(i).getId();
                } else if (selectFD == 2) {
                    sql = "UPDATE\n"
                            + "  `letovi`\n"
                            + "SET\n"
                            + "  `arrgejt` = '" + selectGT.getGejt() + "'\n"
                            + "WHERE\n"
                            + " id = " + ll.get(i).getId();
                    ll.get(i).setGejtoviiterminaliByArrgejt(selectGT);
                }
                l = ll.get(i);
            }
        }

        s.createSQLQuery(sql).executeUpdate();
        s.getTransaction().commit();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ff", ll);
        RequestContext.getCurrentInstance().update("FlightsDT");
        RequestContext.getCurrentInstance().execute("PF('gateDialog').hide();");
    }

    public void chg() {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Transaction tr = s.beginTransaction();
        AvioKompanije a = (AvioKompanije) s.load(AvioKompanije.class, c);
        FacesContext x = FacesContext.getCurrentInstance();
        Korisnici temp = (Korisnici) x.getExternalContext().getSessionMap().get("user");
        Korisnici k = (Korisnici) s.load(Korisnici.class, temp.getKorIme());
        k.setAvioKompanije(a);
        s.saveOrUpdate(k);
        s.flush();
        tr.commit();
    }

    public void cncl(Letovi x) {
        String sql = "UPDATE letovi SET status = 'canceled' WHERE id = " + x.getId();
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Transaction tr = s.beginTransaction();
        s.createSQLQuery(sql).executeUpdate();
        tr.commit();
    }

    public void upd(Radarskicentri x) {
        Letovi l = (Letovi) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("active");
        selectedR = x;
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Criteria cr = s.createCriteria(Letoviradari.class);
        cr.add(Restrictions.eq("letovi", l));
        cr.add(Restrictions.eq("radarskicentri", x));
        List<Letoviradari> lr = cr.list();
        selectLR = lr.get(0);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('updDialog').show();");
    }

    public void emg(Letovi x) {
        selectL = x;
        listE = new ArrayList<>();
        mapaAr = new HashMap<>();
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Criteria cr = sf.openSession().createCriteria(Aerodromi.class);
        List<Aerodromi> ar = cr.list();
        for (Aerodromi ar1 : ar) {
            if (ar1 != x.getGejtoviiterminaliByArrgejt().getAerodromi()) {
                String str = ar1.getNaziv() + " (" + ar1.getIata() + ")";
                listE.add(str);
                mapaAr.put(str, ar1);
            }
        }
        RequestContext.getCurrentInstance().update("formQ:emgl");
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('emgDialog').show();");
    }

    public void emgergency() {
        Date d = new Date();
        Timestamp ts = new Timestamp(d.getTime());
        String sql = "UPDATE letovi SET urggejt = '" + emgG + "', status = 'emg', vremeSletanja = '" + ts + "' WHERE id = " + selectL.getId();
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Transaction tr = s.beginTransaction();
        s.createSQLQuery(sql).executeUpdate();
        tr.commit();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('emgDialog').hide();");
    }

    public void updF() {
        int rem = (int) Math.ceil(dist / speed);
        String sql = "UPDATE letoviradari SET brzina = '" + speed + "', ostatak = '" + dist + "' WHERE id = " + selectLR.getId();
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Transaction tr = s.beginTransaction();
        s.createSQLQuery(sql).executeUpdate();
        tr.commit();
        Date d = new Date();
        Timestamp ts = new Timestamp(d.getTime() + TimeUnit.MINUTES.toMillis(rem));
        Letovi l = (Letovi) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("active");
        String sql2 = "UPDATE letovi SET ocekivanoVreme = '" + ts + "' WHERE id = " + l.getId();
        SessionFactory sf2 = new Configuration().configure().buildSessionFactory();
        Session s2 = sf2.openSession();
        Transaction tr2 = s2.beginTransaction();
        s2.createSQLQuery(sql2).executeUpdate();
        tr2.commit();
        s.close();
        s2.close();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('updDialog').hide();");
    }

    public void ld(Letovi x) {
        Date d = new Date();
        Timestamp ts = new Timestamp(d.getTime());
        String sql = "UPDATE letovi SET status = 'lended',vremeSletanja='" + ts + "' WHERE id = " + x.getId();
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Transaction tr = s.beginTransaction();
        s.createSQLQuery(sql).executeUpdate();
        tr.commit();
    }

    public void ptl(Letovi x) {
        String sql = "UPDATE letovi SET status = 'ptl' WHERE id = " + x.getId();
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Transaction tr = s.beginTransaction();
        s.createSQLQuery(sql).executeUpdate();
        tr.commit();
    }

    public void tkf(Letovi x) {
        String sql = "UPDATE letovi SET status = 'act' WHERE id = " + x.getId();
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Transaction tr = s.beginTransaction();
        s.createSQLQuery(sql).executeUpdate();
        tr.commit();
    }

    public void selEmg(final AjaxBehaviorEvent event) {
        Aerodromi ar = mapaAr.get(selectE);
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        String sql = "SELECT\n"
                + "  G.gejt\n"
                + "FROM\n"
                + "  aerodromi A,\n"
                + "  gejtoviiterminali G\n"
                + "WHERE\n"
                + "  A.IATA = G.IATA AND A.IATA = '" + ar.getIata() + "'";
        gejtEmg = (List<String>) s.createSQLQuery(sql).list();
        emgD = false;
    }
}
