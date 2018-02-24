/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import db.Aerodromi;
import db.AvioKompanije;
import db.Avioni;
import db.Gejtoviiterminali;
import db.Gostirezervacije;
import db.Korisnici;
import db.Letovi;
import db.Letoviradari;
import db.Licence;
import db.Osobljeleta;
import db.Proizvodjaci;
import db.Radarskicentri;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Brana
 */
@Named(value = "adctrl")
@SessionScoped
public class AdminControler implements Serializable {

    private Korisnici selectedG;
    private String iata, name, avion, grad, zem, nameA, licA, depA, arrA, depG;
    private int term, pist, id, cap, duration;
    private List<Terminal> items;
    private String naziv, zemlj, email, web, adr, n, newL, typ, mm, arrG;
    private boolean arrD = true, depD = true, ch, stj, pil;
    private Map<String, Integer> mapa;
    private Map<String, Avioni> mapaA;
    private Map<String, String> mapaS, mapaP;
    private Map<String, Radarskicentri> mapaR;
    private List<String> avioni, stjj, pill, rad;
    private String[] selectedS;
    private String[] selectedP, selectedR;
    private double lat, lng;

    public List<String> getRad() {
        return rad;
    }

    public void setRad(List<String> rad) {
        this.rad = rad;
    }

    public String[] getSelectedR() {
        return selectedR;
    }

    public void setSelectedR(String[] selectedR) {
        this.selectedR = selectedR;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String[] getSelectedS() {
        return selectedS;
    }

    public void setSelectedS(String[] selectedS) {
        this.selectedS = selectedS;
    }

    public String[] getSelectedP() {
        return selectedP;
    }

    public void setSelectedP(String[] selectedP) {
        this.selectedP = selectedP;
    }

    public List<String> getStjj() {
        return stjj;
    }

    public void setStjj(List<String> stjj) {
        this.stjj = stjj;
    }

    public List<String> getPill() {
        return pill;
    }

    public void setPill(List<String> pill) {
        this.pill = pill;
    }

    public String getAvion() {
        return avion;
    }

    public void setAvion(String avion) {
        this.avion = avion;
    }

    public boolean isStj() {
        return stj;
    }

    public void setStj(boolean stj) {
        this.stj = stj;
    }

    public boolean isPil() {
        return pil;
    }

    public void setPil(boolean pil) {
        this.pil = pil;
    }

    public List<String> getAvioni() {
        return avioni;
    }

    public void setAvioni(List<String> avioni) {
        this.avioni = avioni;
    }

    public boolean isCh() {
        return ch;
    }

    public void setCh(boolean ch) {
        this.ch = ch;
    }
    private Date dtD;

    public Date getDtD() {
        return dtD;
    }

    public void setDtD(Date dtD) {
        this.dtD = dtD;
    }

    public String getDepG() {
        return depG;
    }

    public void setDepG(String depG) {
        this.depG = depG;
    }

    public String getArrG() {
        return arrG;
    }

    public void setArrG(String arrG) {
        this.arrG = arrG;
    }

    public boolean isArrD() {
        return arrD;
    }

    public void setArrD(boolean arrD) {
        this.arrD = arrD;
    }

    public boolean isDepD() {
        return depD;
    }

    public void setDepD(boolean depD) {
        this.depD = depD;
    }

    public String getDepA() {
        return depA;
    }

    public void setDepA(String depA) {
        this.depA = depA;
    }

    public String getArrA() {
        return arrA;
    }

    public void setArrA(String arrA) {
        this.arrA = arrA;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }
    private List<String> types, m;
    private List<String> airports, gejtArr, gejtDep;

    public List<String> getGejtArr() {
        return gejtArr;
    }

    public void setGejtArr(List<String> gejtArr) {
        this.gejtArr = gejtArr;
    }

    public List<String> getGejtDep() {
        return gejtDep;
    }

    public void setGejtDep(List<String> gejtDep) {
        this.gejtDep = gejtDep;
    }

    public List<String> getAirports() {
        return airports;
    }

    public void setAirports(List<String> airports) {
        this.airports = airports;
    }
    private Proizvodjaci manu;
    private List<Proizvodjaci> list;
    private UploadedFile img;

    public String getNameA() {
        return nameA;
    }

    public void setNameA(String nameA) {
        this.nameA = nameA;
    }

    public String getLicA() {
        return licA;
    }

    public void setLicA(String licA) {
        this.licA = licA;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public UploadedFile getImg() {
        return img;
    }

    public void setImg(UploadedFile img) {
        this.img = img;
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public List<String> getM() {
        return m;
    }

    public void setM(List<String> m) {
        this.m = m;
    }

    public String getNewL() {
        return newL;
    }

    public void setNewL(String newL) {
        this.newL = newL;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    private boolean chg = false;

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getZemlj() {
        return zemlj;
    }

    public void setZemlj(String zemlj) {
        this.zemlj = zemlj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public List<Terminal> getItems() {
        return items;
    }

    public void setItems(List<Terminal> items) {
        this.items = items;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getPist() {
        return pist;
    }

    public void setPist(int pist) {
        this.pist = pist;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getZem() {
        return zem;
    }

    public void setZem(String zem) {
        this.zem = zem;
    }

    public Korisnici getSelectedG() {
        return selectedG;
    }

    public void setSelectedG(Korisnici selectedG) {
        this.selectedG = selectedG;
    }

    public void approve() {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Transaction tr = s.beginTransaction();
        String sql = "UPDATE korisnici SET odobreno = 1 WHERE korIme='" + selectedG.getKorIme() + "'";
        s.createSQLQuery(sql).executeUpdate();
        s.flush();
        tr.commit();
    }

    public String addA() {
        return "airport.xhtml";
    }

    public String addAr() {
        return "company.xhtml";
    }

    public String addAc() {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        String sql = "SELECT DISTINCT\n"
                + "  tipAviona\n"
                + "FROM\n"
                + "  avioni\n"
                + "WHERE\n"
                + "  1";
        types = (List<String>) s.createSQLQuery(sql).list();
        Query query = s.createQuery("from Proizvodjaci");
        list = query.list();
        m = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            m.add(list.get(i).getIme());
        }
        return "plane.xhtml";
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String addAf() {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        String sql = "SELECT\n"
                + "  naziv\n"
                + "FROM\n"
                + "  aerodromi\n"
                + "WHERE\n"
                + "  1";
        airports = (List<String>) s.createSQLQuery(sql).list();
        return "flight.xhtml";
    }

    public void selDep(final AjaxBehaviorEvent event) {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        String sql = "SELECT\n"
                + "  G.gejt\n"
                + "FROM\n"
                + "  aerodromi A,\n"
                + "  gejtoviiterminali G\n"
                + "WHERE\n"
                + "  A.IATA = G.IATA AND A.naziv = '" + depA + "'";
        gejtDep = (List<String>) s.createSQLQuery(sql).list();
        depD = false;
        RequestContext.getCurrentInstance().update("formX:depG");
    }

    public void selSt(final AjaxBehaviorEvent event) {
        if (selectedS.length < 3 || selectedS.length > 5) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Please select between 3 and 5 flight attendants"));
        }
    }

    public void selPil(final AjaxBehaviorEvent event) {
        if (selectedP.length > 2) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Please select no more than 2 pilots"));
        }
    }

    public void selRad(final AjaxBehaviorEvent event) {
        if (selectedR.length < 1 || selectedR.length > 3) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Please select between 1 and 3 radars"));
        }
    }

    public void selAv(final AjaxBehaviorEvent event) {
        mapaS = new HashMap<>();
        mapaP = new HashMap<>();
        pill = new ArrayList<>();
        stjj = new ArrayList<>();
        int id1 = mapa.get(avion);
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Criteria cr1 = s.createCriteria(Korisnici.class);
        cr1.add(Restrictions.eq("pozicija", "pilot"));
        Criteria cr2 = s.createCriteria(Korisnici.class);
        cr2.add(Restrictions.eq("pozicija", "stjuardesa"));
        List<Korisnici> pilo = cr1.list();
        List<Korisnici> stjua = cr2.list();
        for (int i = 0; i < pilo.size(); i++) {
            if (pilo.get(i).getAvioKompanije().getId() == id1) {
                String x = pilo.get(i).getIme() + " " + pilo.get(i).getPrezime() + " (" + pilo.get(i).getKorIme() + ")";
                pill.add(x);
                mapaP.put(x, pilo.get(i).getKorIme());
            }
        }
        for (int i = 0; i < stjua.size(); i++) {
            if (stjua.get(i).getAvioKompanije().getId() == id1) {
                String x = stjua.get(i).getIme() + " " + stjua.get(i).getPrezime() + " (" + stjua.get(i).getKorIme() + ")";
                stjj.add(x);
                mapaS.put(x, stjua.get(i).getKorIme());
            }
        }
        stj = pil = true;
        RequestContext.getCurrentInstance().update("formX:pil");
        RequestContext.getCurrentInstance().update("formX:stj");
    }

    public void selArr(final AjaxBehaviorEvent event) {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        String sql = "SELECT\n"
                + "  G.gejt\n"
                + "FROM\n"
                + "  aerodromi A,\n"
                + "  gejtoviiterminali G\n"
                + "WHERE\n"
                + "  A.IATA = G.IATA AND A.naziv = '" + arrA + "'";
        gejtArr = (List<String>) s.createSQLQuery(sql).list();
        arrD = false;
        RequestContext.getCurrentInstance().update("formX:arrG");
    }

    public String addAl() {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        String sql = "SELECT\n"
                + "  *\n"
                + "FROM\n"
                + "  korisnici\n"
                + "WHERE\n"
                + "  pozicija = 'pilot'";
        List<Object[]> o = s.createSQLQuery(sql).list();
        List<String> korS = new ArrayList<>();
        for (int i = 0; i < o.size(); i++) {
            String x = o.get(i)[1] + " " + o.get(i)[2] + " (" + o.get(i)[0] + ")";
            korS.add(x);
        }
        FacesContext c = FacesContext.getCurrentInstance();
        c.getExternalContext().getSessionMap().put("userLS", korS);

        return "licence.xhtml";
    }

    public void termC(AjaxBehaviorEvent event) {
        items = new ArrayList<>();
        for (int i = 0; i < term; i++) {
            items.add(new Terminal(i, ""));
        }
        RequestContext.getCurrentInstance().update("formX:itms");
    }

    public void nameC(AjaxBehaviorEvent event) {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        String sql = "SELECT * FROM `avio-kompanije` WHERE naziv = '" + naziv + "'";
        List<Object[]> o = s.createSQLQuery(sql).list();
        if (o != null) {
            zemlj = o.get(0)[3].toString();
            email = o.get(0)[5].toString();
            web = o.get(0)[4].toString();
            adr = o.get(0)[2].toString();
            chg = true;
            id = (int) o.get(0)[0];
        }
        RequestContext.getCurrentInstance().update("formA:adresa");
        RequestContext.getCurrentInstance().update("formA:zem");
        RequestContext.getCurrentInstance().update("formA:web");
        RequestContext.getCurrentInstance().update("formA:email");
    }

    public String submit() {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Transaction tr = s.beginTransaction();
        Aerodromi a = new Aerodromi();
        a.setIata(iata);
        a.setBrojPisti(pist);
        a.setNaziv(name);
        a.setZemlja(zem);
        a.setGrad(grad);
        s.save(a);
        s.flush();
        tr.commit();
        for (int i = 0; i < items.size(); i++) {
            String[] ar = items.get(i).getGejtovi().split(" ");
            for (int j = 0; j < ar.length; j++) {
                Gejtoviiterminali gt = new Gejtoviiterminali();
                gt.setTerminal(i + 1);
                gt.setGejt(ar[j]);
                gt.setAerodromi(a);
                SessionFactory sf2 = new Configuration().configure().buildSessionFactory();
                Session s2 = sf2.openSession();
                Transaction tr2 = s2.beginTransaction();
                s2.save(gt);
                s2.flush();
                tr2.commit();
            }
        }
        String sql = "SELECT\n"
                + "  *\n"
                + "FROM\n"
                + "  radarskicentri\n"
                + "WHERE\n"
                + "  grad = '" + grad + "' AND zemlja = '" + zem + "'";
        List<Object[]> o = s.createSQLQuery(sql).list();
        if (o.isEmpty()) {
            Radarskicentri rs = new Radarskicentri();
            rs.setDuzina(BigDecimal.valueOf(lat));
            rs.setSirina(BigDecimal.valueOf(lng));
            rs.setZemlja(zem);
            rs.setGrad(grad);
            SessionFactory sf2 = new Configuration().configure().buildSessionFactory();
            Session s2 = sf2.openSession();
            Transaction tr2 = s2.beginTransaction();
            s2.save(rs);
            s2.flush();
            tr2.commit();
        }

        return "admin.xhtml";
    }

    public String submit2() {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Transaction tr = s.beginTransaction();
        int maxID = 0;
        if (chg) {
            AvioKompanije k = (AvioKompanije) s.load(AvioKompanije.class, id);
            k.setAdresa(adr);
            k.setEmail(email);
            k.setNaziv(naziv);
            k.setZemlja(zemlj);
            s.saveOrUpdate(k);
            s.flush();
            tr.commit();
        } else {
            Criteria criteria = s.createCriteria(AvioKompanije.class).setProjection(Projections.max("id"));
            maxID = (int) criteria.uniqueResult();
            AvioKompanije k = new AvioKompanije();
            k.setId(maxID + 1);
            k.setAdresa(adr);
            k.setEmail(email);
            k.setNaziv(naziv);
            k.setZemlja(zemlj);
            k.setWebadresa(web);
            s.save(k);
            s.flush();
            tr.commit();
        }
        adr = email = naziv = zemlj = web = "";
        return "admin.xhtml";
    }

    public String submit3() {
        String usr = n.substring(n.indexOf('(') + 1, n.indexOf(')'));
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Korisnici k = (Korisnici) s.load(Korisnici.class, usr);
        Licence l = new Licence();
        l.setKorisnici(k);
        l.setLic(newL.substring(2));
        l.setTip(newL.substring(0, 2));
        Transaction tr = s.beginTransaction();
        s.save(l);
        s.flush();
        tr.commit();
        return "admin.xhtml";
    }

    public String submit4() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIme().equals(mm)) {
                manu = list.get(i);
            }
        }
        byte[] bFile = new byte[(int) img.getSize()];

        try (InputStream fileInputStream = img.getInputstream()) {
            fileInputStream.read(bFile);
        } catch (IOException e) {
        }
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Transaction tr = s.beginTransaction();
        Avioni a = new Avioni();
        a.setProizvodjaci(manu);
        a.setTipAviona(typ);
        a.setImage(bFile);
        a.setLicenca(licA);
        a.setMaxPutnika(cap);
        a.setNaziv(nameA);
        s.save(a);
        s.flush();
        tr.commit();
        return "admin.xhtml";
    }

    public String submit5() {
        return "admin.xhtml";
    }

    public String next() {
        avioni = new ArrayList<>();
        stj = false;
        pil = false;
        mapaA = new HashMap<>();
        mapa = new HashMap<>();
        if (!ch) {
            SessionFactory sf = new Configuration().configure().buildSessionFactory();
            Session s = sf.openSession();
            Criteria cr1 = s.createCriteria(Avioni.class);
            cr1.add(Restrictions.isNull("avioKompanijeByIznajmljen"));
            cr1.add(Restrictions.isNotNull("avioKompanijeByIdKompanije"));
            List<Avioni> ar1 = cr1.list();
            for (int i = 0; i < ar1.size(); i++) {
                String x = ar1.get(i).getNaziv();
                AvioKompanije ak = (AvioKompanije) s.get(AvioKompanije.class, ar1.get(i).getAvioKompanijeByIdKompanije().getId());
                x += " (" + ak.getNaziv() + ")";
                avioni.add(x);
                mapaA.put(x, ar1.get(i));
                mapa.put(x, ak.getId());
            }
            Criteria cr2 = s.createCriteria(Avioni.class);
            cr2.add(Restrictions.isNotNull("avioKompanijeByIznajmljen"));
            cr1.add(Restrictions.isNotNull("avioKompanijeByIdKompanije"));
            List<Avioni> ar2 = cr2.list();
            for (int i = 0; i < ar2.size(); i++) {
                String x = ar2.get(i).getNaziv();
                AvioKompanije ak = (AvioKompanije) s.get(AvioKompanije.class, ar2.get(i).getAvioKompanijeByIznajmljen().getId());
                x += " (" + ak.getNaziv() + ")";
                avioni.add(x);
                mapaA.put(x, ar2.get(i));
                mapa.put(x, ak.getId());
            }
        } else {
            SessionFactory sf = new Configuration().configure().buildSessionFactory();
            Session s = sf.openSession();
            Criteria cr1 = s.createCriteria(Avioni.class);
            cr1.add(Restrictions.isNotNull("avioKompanijeByIznajmljen"));
            cr1.add(Restrictions.isNotNull("avioKompanijeByIdKompanije"));
            List<Avioni> ar1 = cr1.list();
            for (int i = 0; i < ar1.size(); i++) {
                String x = ar1.get(i).getNaziv();
                AvioKompanije ak = (AvioKompanije) s.get(AvioKompanije.class, ar1.get(i).getAvioKompanijeByIznajmljen().getId());
                x += " (" + ak.getNaziv() + ")";
                avioni.add(x);
                mapa.put(x, ak.getId());
                mapaA.put(x, ar1.get(i));
            }
        }
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Criteria cr1 = s.createCriteria(Radarskicentri.class);
        List<Radarskicentri> rc = cr1.list();
        mapaR = new HashMap<>();
        rad = new ArrayList<>();
        for (int i = 0; i < rc.size(); i++) {
            String x = rc.get(i).getGrad() + ", " + rc.get(i).getZemlja();
            rad.add(x);
            mapaR.put(x, rc.get(i));
        }
        return "flightd.xhtml";
    }

    public String flight() {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Transaction tr = s.beginTransaction();
        List<Date> dates = new ArrayList<>();

        Criteria criteria = s
                .createCriteria(db.Letovi.class)
                .setProjection(Projections.max("id"));
        Integer maxId = (Integer) criteria.uniqueResult();
        int idd = maxId + 1;
        dates.add(dtD);
        if (!ch) {
            long mils = dtD.getTime();
            int mth = dtD.getMonth();
            int cmth = mth;
            while (cmth == mth) {
                mils += TimeUnit.DAYS.toMillis(7);
                Date dd = new Date(mils);
                cmth = dd.getMonth();
                if (cmth == mth) {
                    dates.add(dd);
                }
            }
        }
        Gejtoviiterminali g1 = (Gejtoviiterminali) s.get(Gejtoviiterminali.class, depG);
        Gejtoviiterminali g2 = (Gejtoviiterminali) s.get(Gejtoviiterminali.class, arrG);
        if (ch) {
            db.Letovi l = new db.Letovi();
            l.setId(maxId + 1);
            l.setGejtoviiterminaliByArrgejt(g2);
            l.setGejtoviiterminaliByDepgejt(g1);
            l.setAvioni(mapaA.get(avion));
            l.setGejtoviiterminaliByUrggejt(null);
            l.setStatus("N/A");
            l.setTrajanjeLeta(duration);
            l.setVremePoletanja(dtD);
            long milis = dtD.getTime() + duration * 60000;
            Date dtA = new Date(milis);
            l.setPlaniranoVreme(dtA);
            l.setOcekivanoVreme(dtA);
            l.setVremeSletanja(null);
            s.save(l);
            s.flush();
            tr.commit();
            if (!tr.wasCommitted()) {
                tr.commit();
            }

            for (String selectedR1 : selectedR) {
                SessionFactory sf2 = new Configuration().configure().buildSessionFactory();
                Session s2 = sf2.openSession();
                Transaction tr2 = s2.beginTransaction();
                Letovi let = (Letovi) s2.get(db.Letovi.class, maxId + 1);
                Radarskicentri r = mapaR.get(selectedR1);
                Letoviradari lr = new Letoviradari();
                lr.setRadarskicentri(r);
                lr.setLetovi(let);
                s2.save(lr);
                s2.flush();
                tr2.commit();
            }

            for (String selectedP1 : selectedP) {
                SessionFactory sf2 = new Configuration().configure().buildSessionFactory();
                Session s2 = sf2.openSession();
                Transaction tr2 = s2.beginTransaction();
                String ime = mapaP.get(selectedP1);
                Korisnici k = (Korisnici) s2.get(Korisnici.class, ime);
                Letovi let = (Letovi) s2.get(db.Letovi.class, maxId + 1);
                Osobljeleta ol = new Osobljeleta();
                ol.setLetovi(let);
                ol.setKorisnici(k);
                s2.save(ol);
                s2.flush();
                tr2.commit();
            }
            for (String selectedS1 : selectedS) {
                SessionFactory sf2 = new Configuration().configure().buildSessionFactory();
                Session s2 = sf2.openSession();
                Transaction tr2 = s2.beginTransaction();
                String ime = mapaS.get(selectedS1);
                Korisnici k = (Korisnici) s2.get(Korisnici.class, ime);
                Letovi let = (Letovi) s2.get(db.Letovi.class, maxId + 1);
                Osobljeleta ol = new Osobljeleta();
                ol.setLetovi(let);
                ol.setKorisnici(k);
                s2.save(ol);
                s2.flush();
                tr2.commit();
            }
        } else {
            for (int i = 0; i < dates.size(); i++) {
                SessionFactory sf3 = new Configuration().configure().buildSessionFactory();
                Session s3 = sf3.openSession();
                Transaction tr3 = s3.beginTransaction();
                db.Letovi l = new db.Letovi();
                l.setId(idd);
                l.setGejtoviiterminaliByArrgejt(g2);
                l.setGejtoviiterminaliByDepgejt(g1);
                l.setAvioni(mapaA.get(avion));
                l.setGejtoviiterminaliByUrggejt(null);
                l.setStatus("N/A");
                l.setTrajanjeLeta(duration);
                l.setVremePoletanja(dates.get(i));
                long milis = dates.get(i).getTime() + duration * 60000;
                Date dtA = new Date(milis);
                l.setPlaniranoVreme(dtA);
                l.setOcekivanoVreme(dtA);
                l.setVremeSletanja(null);
                s3.save(l);
                s3.flush();
                tr3.commit();
                if (!tr3.wasCommitted()) {
                    tr3.commit();
                }

                for (String selectedP1 : selectedP) {
                    SessionFactory sf2 = new Configuration().configure().buildSessionFactory();
                    Session s2 = sf2.openSession();
                    Transaction tr2 = s2.beginTransaction();
                    String ime = mapaP.get(selectedP1);
                    Korisnici k = (Korisnici) s2.get(Korisnici.class, ime);
                    Letovi let = (Letovi) s2.get(db.Letovi.class, idd);
                    Osobljeleta ol = new Osobljeleta();
                    ol.setLetovi(let);
                    ol.setKorisnici(k);
                    s2.save(ol);
                    s2.flush();
                    tr2.commit();
                }
                for (String selectedS1 : selectedS) {
                    SessionFactory sf2 = new Configuration().configure().buildSessionFactory();
                    Session s2 = sf2.openSession();
                    Transaction tr2 = s2.beginTransaction();
                    String ime = mapaS.get(selectedS1);
                    Korisnici k = (Korisnici) s2.get(Korisnici.class, ime);
                    Letovi let = (Letovi) s2.get(db.Letovi.class, idd);
                    Osobljeleta ol = new Osobljeleta();
                    ol.setLetovi(let);
                    ol.setKorisnici(k);
                    s2.save(ol);
                    s2.flush();
                    tr2.commit();
                }

                for (String selectedR1 : selectedR) {
                    SessionFactory sf2 = new Configuration().configure().buildSessionFactory();
                    Session s2 = sf2.openSession();
                    Transaction tr2 = s2.beginTransaction();
                    Letovi let = (Letovi) s2.get(db.Letovi.class, idd);
                    Radarskicentri r = mapaR.get(selectedR1);
                    Letoviradari lr = new Letoviradari();
                    lr.setRadarskicentri(r);
                    lr.setLetovi(let);
                    s2.save(lr);
                    s2.flush();
                    tr2.commit();
                }
                idd++;
            }
        }
        return "admin.xhtml";
    }
}
