/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import db.Avioni;
import db.Iznajmljivanje;
import db.Korisnici;
import db.Letovi;
import db.Letoviradari;
import db.Proizvodjaci;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polyline;

/**
 *
 * @author Brana
 */
@Named(value = "wctrl")
@SessionScoped
public class WorkerControler implements Serializable {

    private WorkLet w;
    private MapModel polylineModel;
    private boolean modelD = true, sc = false;
    private List<String> mods, mm;
    private String m, md = "";
    private int cap, off;
    private Date rent1 = null, rent2 = null;
    private Map<String, Proizvodjaci> manuff;
    private List<Rent> srR;
    private Avioni selected;
    private List<Izn> iznL;
    private List<Letoviradari> lrl;

    public List<Izn> getIznL() {
        return iznL;
    }

    public void setIznL(List<Izn> iznL) {
        this.iznL = iznL;
    }

    public int getOff() {
        return off;
    }

    public void setOff(int off) {
        this.off = off;
    }

    public boolean isModelD() {
        return modelD;
    }

    public void setModelD(boolean modelD) {
        this.modelD = modelD;
    }

    public List<String> getMods() {
        return mods;
    }

    public void setMods(List<String> mods) {
        this.mods = mods;
    }

    public List<String> getMm() {
        return mm;
    }

    public void setMm(List<String> mm) {
        this.mm = mm;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public List<Letoviradari> getLrl() {
        return lrl;
    }

    public void setLrl(List<Letoviradari> lrl) {
        this.lrl = lrl;
    }

    public String getMd() {
        return md;
    }

    public void setMd(String md) {
        this.md = md;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public Date getRent1() {
        return rent1;
    }

    public void setRent1(Date rent1) {
        this.rent1 = rent1;
    }

    public Date getRent2() {
        return rent2;
    }

    public void setRent2(Date rent2) {
        this.rent2 = rent2;
    }

    public WorkLet getW() {
        return w;
    }

    public void setW(WorkLet w) {
        this.w = w;
    }

    public WorkerControler() {
    }

    public MapModel getPolylineModel() {
        return polylineModel;
    }

    public String back() {
        return "worker.xhtml";
    }

    public void add(int id) {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Transaction tr = s.beginTransaction();
        Korisnici k = (Korisnici) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        Avioni a = (Avioni) s.get(Avioni.class, id);
        a.setAvioKompanijeByIdKompanije(k.getAvioKompanije());
        s.saveOrUpdate(a);
        s.flush();
        tr.commit();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Aircraft is successfully added!"));
    }

    public void onRowSelect(SelectEvent event) {
        SessionFactory sf2 = new Configuration().configure().buildSessionFactory();
        Session s2 = sf2.openSession();
        String sql1 = "SELECT\n"
                + "  A.duzina,\n"
                + "  A.sirina, A.grad\n"
                + "FROM\n"
                + "  radarskicentri A,\n"
                + "  letoviradari B\n"
                + "WHERE\n"
                + "  A.id = B.idradara AND B.idleta = " + w.getId();

        List<Object[]> o = s2.createSQLQuery(sql1).list();
        polylineModel = new DefaultMapModel();
        Polyline polyline = new Polyline();
        LatLng coord;
        for (int i = 0; i < o.size(); i++) {
            BigDecimal bd1 = (BigDecimal) o.get(i)[0];
            BigDecimal bd2 = (BigDecimal) o.get(i)[1];
            coord = new LatLng(bd1.doubleValue(), bd2.doubleValue());
            polyline.getPaths().add(coord);
            polylineModel.addOverlay(new Marker(coord, o.get(i)[2].toString()));
        }
        polyline.setStrokeWeight(10);
        polyline.setStrokeColor("#FF9900");
        polyline.setStrokeOpacity(0.7);
        lrl = new ArrayList<>();
        Letovi l = (Letovi) s2.get(Letovi.class, w.getId());
        Criteria cr = s2.createCriteria(Letoviradari.class);
        cr.add(Restrictions.eq("letovi", l));
        List<Letoviradari> lrlp = cr.list();
        for (Letoviradari lrl1 : lrlp) {
            if(lrl1.getRadarskicentri().getGrad().equals(l.getGejtoviiterminaliByDepgejt().getAerodromi().getGrad())
                    && lrl1.getRadarskicentri().getZemlja().equals(l.getGejtoviiterminaliByDepgejt().getAerodromi().getZemlja())){}
            else if(lrl1.getRadarskicentri().getGrad().equals(l.getGejtoviiterminaliByArrgejt().getAerodromi().getGrad())
                    && lrl1.getRadarskicentri().getZemlja().equals(l.getGejtoviiterminaliByArrgejt().getAerodromi().getZemlja())){}
            else lrl.add(lrl1);
        }
        polylineModel.addOverlay(polyline);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/details.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(WorkerControler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void selM(final AjaxBehaviorEvent event) {
        Proizvodjaci p = manuff.get(m);
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        String sql = "SELECT DISTINCT\n"
                + "  naziv\n"
                + "FROM\n"
                + "  avioni\n"
                + "WHERE\n"
                + "  proizvodjac = " + p.getId();
        mods = (List<String>) s.createSQLQuery(sql).list();
        modelD = false;
        RequestContext.getCurrentInstance().update("formX:model");
    }

    public String rent() {
        iznL = new ArrayList<>();
        mm = new ArrayList<>();
        manuff = new HashMap<>();
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Korisnici k = (Korisnici) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        Criteria cr1 = s.createCriteria(Proizvodjaci.class);
        List<Proizvodjaci> manuf = cr1.list();
        for (int i = 0; i < manuf.size(); i++) {
            String x = manuf.get(i).getIme();
            mm.add(x);
            manuff.put(x, manuf.get(i));
        }
        Criteria cr2 = s.createCriteria(Iznajmljivanje.class);
        List<Iznajmljivanje> iz = cr2.list();
        for (int i = 0; i < iz.size(); i++) {
            Avioni a = iz.get(i).getAvioni();
            if (a.getAvioKompanijeByIdKompanije().getId() == k.getAvioKompanije().getId()) {
                Izn in = new Izn();
                in.setA(a.getNaziv());
                in.setK(iz.get(i).getAvioKompanije().getNaziv());
                in.setP(iz.get(i).getPonuda());
                in.setId(iz.get(i).getId());
                in.setIdA(a.getId());
                in.setAk(k.getAvioKompanije());
                iznL.add(in);
            }
        }
        return "rent.xhtml";
    }

    public boolean isSc() {
        return sc;
    }

    public void setSc(boolean sc) {
        this.sc = sc;
    }

    public List<Rent> getSrR() {
        return srR;
    }

    public void setSrR(List<Rent> srR) {
        this.srR = srR;
    }

    public void search() {
        sc = true;
        srR = new ArrayList<>();
        SessionFactory sf2 = new Configuration().configure().buildSessionFactory();
        Session s2 = sf2.openSession();
        String sql = "SELECT\n"
                + "  A.naziv AS airp,\n"
                + "  A.id,\n"
                + "  A.idKompanije,\n"
                + "  C.naziv AS komp\n"
                + "FROM\n"
                + "  avioni A,\n"
                + "  `avio-kompanije` C\n"
                + "WHERE\n"
                + " A.idKompanije = C.id AND A.proizvodjac = " + manuff.get(m).getId()
                + " AND A.maxPutnika > " + cap + " AND A.iznajmljen IS NULL";
        List<Object[]> o = s2.createSQLQuery(sql).list();
        s2.close();
        for (int i = 0; i < o.size(); i++) {
            Session s1 = sf2.openSession();
            String sql1 = "SELECT\n"
                    + "  vremePoletanja\n"
                    + "FROM\n"
                    + "  letovi\n"
                    + "WHERE\n"
                    + "  idAviona = " + (int) o.get(i)[1];
            List<Date> d = (List<Date>) s1.createSQLQuery(sql1).list();
            if (!d.isEmpty()) {
                boolean free = true;
                for (int j = 0; j < d.size(); j++) {
                    if (d.get(j).after(rent1) && d.get(j).before(rent2)) {
                        free = false;
                    }
                }
                if (free) {
                    if ("".equals(md)) {
                        Rent rr = new Rent(o.get(i)[0].toString(), o.get(i)[3].toString());
                        rr.setId((int) o.get(i)[1]);
                        srR.add(rr);
                    } else {
                        if (o.get(i)[0].toString().equals(md)) {
                            Rent rr = new Rent(o.get(i)[0].toString(), o.get(i)[3].toString());
                            rr.setId((int) o.get(i)[1]);
                            srR.add(rr);
                        }
                    }
                }
            }
        }
        RequestContext.getCurrentInstance().update("formX:rsf");
    }

    public void res(Rent r) {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        selected = (Avioni) s.get(Avioni.class, r.getId());
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('offDialog').show();");
    }

    public String submit6() {
        Korisnici k = (Korisnici) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        Iznajmljivanje i = new Iznajmljivanje();
        i.setAvioni(selected);
        i.setPonuda(off);
        i.setAvioKompanije(k.getAvioKompanije());
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Transaction tr = s.beginTransaction();
        s.save(i);
        s.flush();
        tr.commit();
        s.close();
        return "worker.xhtml";
    }

    public void app(Izn iz) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Offer successfully accepted!"));
    }
}
