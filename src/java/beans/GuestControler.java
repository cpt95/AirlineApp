/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import db.Aerodromi;
import db.Gostirezervacije;
import db.Letovi;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Brana
 */
@Named(value = "gctrl")
@SessionScoped
public class GuestControler implements Serializable {

    private String radio, from, to, name, password = "", toA, frmA,cred, pass;
    private Date dep, ret;
    private int adult = 1;
    private boolean direct;
    private List<SrchRes> src;
    private boolean show = false;
    private SrchRes selected;

    public String getToA() {
        return toA;
    }

    public void setToA(String toA) {
        this.toA = toA;
    }

    public String getFrmA() {
        return frmA;
    }

    public void setFrmA(String frmA) {
        this.frmA = frmA;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCred() {
        return cred;
    }

    public void setCred(String cred) {
        this.cred = cred;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public SrchRes getSelected() {
        return selected;
    }

    public void setSelected(SrchRes selected) {
        this.selected = selected;
    }

    public boolean isDirect() {
        return direct;
    }

    public void setDirect(boolean direct) {
        this.direct = direct;
    }

    public int getAdult() {
        return adult;
    }

    public void setAdult(int adult) {
        this.adult = adult;
    }

    public Date getDep() {
        return dep;
    }

    public void setDep(Date dep) {
        this.dep = dep;
    }

    public Date getRet() {
        return ret;
    }

    public void setRet(Date ret) {
        this.ret = ret;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getRadio() {
        return radio;
    }

    public List<SrchRes> getSrc() {
        return src;
    }

    public void setSrc(List<SrchRes> src) {
        this.src = src;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }

    public void srch(ActionEvent event) {
        src = new ArrayList<>();
        Map<String, Aerodromi> mapA = (Map<String, Aerodromi>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mapA");
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Aerodromi fromA = mapA.get(frmA);
        Aerodromi toAr = mapA.get(toA);
        Criteria cr = s.createCriteria(Letovi.class);
        List<Letovi> flights = cr.list();
        for (Letovi flight : flights) {
            if (direct) {
                if (flight.getGejtoviiterminaliByDepgejt().getAerodromi().getIata().equals(fromA.getIata())
                        && flight.getGejtoviiterminaliByArrgejt().getAerodromi().getIata().equals(toAr.getIata())
                        && flight.getVremePoletanja().getDay() == dep.getDay()
                        && flight.getVremePoletanja().getMonth() == dep.getMonth()
                        && flight.getVremePoletanja().getYear() == dep.getYear()) {
                    if ("ow".equals(radio)) {
                        SrchRes sr = new SrchRes();
                        sr.setName(fromA.getGrad() + " - " + toAr.getGrad());
                        sr.setT(flight.getTrajanjeLeta());
                        sr.setC(flight.getAvioni().getMaxPutnika());
                        sr.setIdL(flight.getId());
                        src.add(sr);
                    } else if ("tw".equals(radio)) {
                        SrchRes sr = new SrchRes();
                        sr.setIdL(flight.getId());
                        sr.setPovez(false);
                        sr.setReturning(true);
                        boolean returner = false;
                        Criteria cr2 = s.createCriteria(Letovi.class);
                        List<Letovi> retFlights = cr2.list();
                        for (Letovi retFlight : retFlights) {
                            if (retFlight.getGejtoviiterminaliByDepgejt().getAerodromi().getIata().equals(toAr.getIata())
                                    && retFlight.getGejtoviiterminaliByArrgejt().getAerodromi().getIata().equals(fromA.getIata())
                                    && retFlight.getVremePoletanja().getDay() == ret.getDay()
                                    && retFlight.getVremePoletanja().getMonth() == ret.getMonth()
                                    && retFlight.getVremePoletanja().getYear() == ret.getYear()) {
                                returner = true;
                                sr.setIdLR(retFlight.getId());
                                sr.setT(retFlight.getTrajanjeLeta());
                                if (retFlight.getAvioni().getMaxPutnika() < flight.getAvioni().getMaxPutnika()) {
                                    sr.setC(retFlight.getAvioni().getMaxPutnika());
                                } else {
                                    sr.setC(flight.getAvioni().getMaxPutnika());
                                }
                            }
                        }
                        sr.setName(fromA.getGrad() + " - " + toAr.getGrad());
                        if (returner) {
                            src.add(sr);
                        }
                    }
                }
            } else {
                if ("ow".equals(radio)) {
                    if (flight.getGejtoviiterminaliByDepgejt().getAerodromi().getIata().equals(fromA.getIata())
                            && flight.getGejtoviiterminaliByArrgejt().getAerodromi().getIata().equals(toAr.getIata())
                            && flight.getVremePoletanja().getDay() == dep.getDay()
                            && flight.getVremePoletanja().getMonth() == dep.getMonth()
                            && flight.getVremePoletanja().getYear() == dep.getYear()) {
                        SrchRes sr = new SrchRes();
                        sr.setName(fromA.getGrad() + " - " + toAr.getGrad());
                        sr.setT(flight.getTrajanjeLeta());
                        sr.setC(flight.getAvioni().getMaxPutnika());
                        sr.setIdL(flight.getId());
                        src.add(sr);
                    } else if (flight.getGejtoviiterminaliByDepgejt().getAerodromi().getIata().equals(fromA.getIata())
                            && flight.getVremePoletanja().getDay() == dep.getDay()
                            && flight.getVremePoletanja().getMonth() == dep.getMonth()
                            && flight.getVremePoletanja().getYear() == dep.getYear()) {
                        SrchRes sr = new SrchRes();
                        sr.setIdL(flight.getId());
                        sr.setPovez(true);
                        sr.setReturning(false);
                        boolean connecter = false;
                        Date conn = new Date(flight.getPlaniranoVreme().getTime());
                        Criteria cr2 = s.createCriteria(Letovi.class);
                        List<Letovi> connFlights = cr2.list();
                        for (Letovi connFlight : connFlights) {
                            if (connFlight.getGejtoviiterminaliByArrgejt().getAerodromi().getIata().equals(toAr.getIata())
                                    && connFlight.getGejtoviiterminaliByDepgejt().getAerodromi().getIata().equals(flight.getGejtoviiterminaliByArrgejt().getAerodromi().getIata())
                                    && connFlight.getVremePoletanja().getDay() == conn.getDay()
                                    && connFlight.getVremePoletanja().getMonth() == conn.getMonth()
                                    && connFlight.getVremePoletanja().getYear() == conn.getYear()) {
                                connecter = true;
                                sr.setIdLP(connFlight.getId());
                                sr.setT(flight.getTrajanjeLeta() + connFlight.getTrajanjeLeta());
                                sr.setName(fromA.getGrad() + " - " + connFlight.getGejtoviiterminaliByDepgejt().getAerodromi().getGrad() + " - " + toAr.getGrad());
                                if (connFlight.getAvioni().getMaxPutnika() < flight.getAvioni().getMaxPutnika()) {
                                    sr.setC(connFlight.getAvioni().getMaxPutnika());
                                } else {
                                    sr.setC(flight.getAvioni().getMaxPutnika());
                                }
                            }
                        }
                        if (connecter) {
                            src.add(sr);
                        }
                    }
                } else if ("tw".equals(radio)) {
                    if (flight.getGejtoviiterminaliByDepgejt().getAerodromi().getIata().equals(fromA.getIata())
                            && flight.getGejtoviiterminaliByArrgejt().getAerodromi().getIata().equals(toAr.getIata())
                            && flight.getVremePoletanja().getDay() == dep.getDay()
                            && flight.getVremePoletanja().getMonth() == dep.getMonth()
                            && flight.getVremePoletanja().getYear() == dep.getYear()) {
                        SrchRes sr = new SrchRes();
                        sr.setIdL(flight.getId());
                        sr.setPovez(false);
                        sr.setReturning(true);
                        boolean returner = false;
                        Criteria cr2 = s.createCriteria(Letovi.class);
                        List<Letovi> retFlights = cr2.list();
                        for (Letovi retFlight : retFlights) {
                            if (retFlight.getGejtoviiterminaliByDepgejt().getAerodromi().getIata().equals(toAr.getIata())
                                    && retFlight.getGejtoviiterminaliByArrgejt().getAerodromi().getIata().equals(fromA.getIata())
                                    && retFlight.getVremePoletanja().getDay() == ret.getDay()
                                    && retFlight.getVremePoletanja().getMonth() == ret.getMonth()
                                    && retFlight.getVremePoletanja().getYear() == ret.getYear()) {
                                returner = true;
                                sr.setIdLR(retFlight.getId());
                                sr.setT(retFlight.getTrajanjeLeta());
                                if (retFlight.getAvioni().getMaxPutnika() < flight.getAvioni().getMaxPutnika()) {
                                    sr.setC(retFlight.getAvioni().getMaxPutnika());
                                } else {
                                    sr.setC(flight.getAvioni().getMaxPutnika());
                                }
                            }
                        }
                        sr.setName(fromA.getGrad() + " - " + toAr.getGrad());
                        if (returner) {
                            src.add(sr);
                        }
                    } else if (flight.getGejtoviiterminaliByDepgejt().getAerodromi().getIata().equals(fromA.getIata())
                            && flight.getVremePoletanja().getDay() == dep.getDay()
                            && flight.getVremePoletanja().getMonth() == dep.getMonth()
                            && flight.getVremePoletanja().getYear() == dep.getYear()) {
                        SrchRes sr = new SrchRes();
                        sr.setIdL(flight.getId());
                        sr.setPovez(true);
                        sr.setReturning(false);
                        boolean connecter = false;
                        boolean returner = false;
                        Letovi LCF = new Letovi();
                        Date conn = new Date(flight.getPlaniranoVreme().getTime());
                        Criteria cr2 = s.createCriteria(Letovi.class);
                        List<Letovi> connFlights = cr2.list();
                        for (Letovi connFlight : connFlights) {
                            if (connFlight.getGejtoviiterminaliByArrgejt().getAerodromi().getIata().equals(toAr.getIata())
                                    && connFlight.getGejtoviiterminaliByDepgejt().getAerodromi().getIata().equals(flight.getGejtoviiterminaliByArrgejt().getAerodromi().getIata())
                                    && connFlight.getVremePoletanja().getDay() == conn.getDay()
                                    && connFlight.getVremePoletanja().getMonth() == conn.getMonth()
                                    && connFlight.getVremePoletanja().getYear() == conn.getYear()) {
                                connecter = true;
                                LCF = connFlight;
                                sr.setIdLP(connFlight.getId());
                                sr.setT(flight.getTrajanjeLeta() + connFlight.getTrajanjeLeta());
                                sr.setName(fromA.getGrad() + " - " + connFlight.getGejtoviiterminaliByDepgejt().getAerodromi().getGrad() + " - " + toAr.getGrad());
                                if (connFlight.getAvioni().getMaxPutnika() < flight.getAvioni().getMaxPutnika()) {
                                    sr.setC(connFlight.getAvioni().getMaxPutnika());
                                } else {
                                    sr.setC(flight.getAvioni().getMaxPutnika());
                                }
                            }
                        }
                        if (connecter) {
                            Criteria cr3 = s.createCriteria(Letovi.class);
                            List<Letovi> retFlights = cr3.list();

                            for (Letovi retFlight : retFlights) {
                                if (retFlight.getGejtoviiterminaliByDepgejt().getAerodromi().getIata().equals(toAr.getIata())
                                        && retFlight.getGejtoviiterminaliByArrgejt().getAerodromi().getIata().equals(LCF.getGejtoviiterminaliByDepgejt().getAerodromi().getIata())
                                        && retFlight.getVremePoletanja().getDay() == ret.getDay()
                                        && retFlight.getVremePoletanja().getMonth() == ret.getMonth()
                                        && retFlight.getVremePoletanja().getYear() == ret.getYear()) {
                                    Date connRet = new Date(retFlight.getPlaniranoVreme().getTime());
                                    sr.setIdLR(retFlight.getId());
                                    Criteria cr4 = s.createCriteria(Letovi.class);
                                    List<Letovi> connRetFlights = cr4.list();
                                    for (Letovi connRetFlight : connRetFlights) {
                                        if (connRetFlight.getGejtoviiterminaliByDepgejt().getAerodromi().getIata().equals(LCF.getGejtoviiterminaliByDepgejt().getAerodromi().getIata())
                                                && connRetFlight.getGejtoviiterminaliByArrgejt().getAerodromi().getIata().equals(fromA.getIata())
                                                && connRetFlight.getVremePoletanja().getDay() == connRet.getDay()
                                                && connRetFlight.getVremePoletanja().getMonth() == connRet.getMonth()
                                                && connRetFlight.getVremePoletanja().getYear() == connRet.getYear()) {
                                            sr.setIdLPR(connRetFlight.getId());
                                            returner = true;
                                        }
                                    }
                                }
                            }
                            if (returner) {
                                src.add(sr);
                            }
                        }
                    }
                }
            }
        }
        show = true;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public String res(SrchRes x) {
        selected = x;
        System.out.println(x.getName());
        return "guestr";
    }

    public void reserve() {
        
        Random r = new Random();
        String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < 8; i++) {
            password += alphabet.charAt(r.nextInt(alphabet.length()));
        }
        Gostirezervacije gt = new Gostirezervacije();
        gt.setBrojKartice(cred);
        gt.setBrojPasosa(pass);
        gt.setSifra(password);
        gt.setImePrezime(name);
        gt.setOdobreno(true);
        gt.setOdrasli(adult);
        gt.setIdLeta(selected.getIdL());
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s = sf.openSession();
        Transaction tr = s.beginTransaction();
        s.save(gt);
        s.flush();
        tr.commit();
        if (selected.getIdLP() != 0) {
            Gostirezervacije gt1 = new Gostirezervacije();
            gt1.setBrojKartice(cred);
            gt1.setBrojPasosa(pass);
            gt1.setSifra(password);
            gt1.setImePrezime(name);
            gt1.setOdobreno(true);
            gt1.setOdrasli(adult);
            gt1.setIdLeta(selected.getIdLP());
            SessionFactory sf2 = new Configuration().configure().buildSessionFactory();
            Session s2 = sf2.openSession();
            Transaction tr2 = s2.beginTransaction();
            s2.save(gt1);
            s2.flush();
            tr2.commit();
        }
        if (selected.getIdLR()!= 0) {
            Gostirezervacije gt1 = new Gostirezervacije();
            gt1.setBrojKartice(cred);
            gt1.setBrojPasosa(pass);
            gt1.setSifra(password);
            gt1.setImePrezime(name);
            gt1.setOdrasli(adult);
            gt1.setOdobreno(true);
            gt1.setIdLeta(selected.getIdLR());
            SessionFactory sf2 = new Configuration().configure().buildSessionFactory();
            Session s2 = sf2.openSession();
            Transaction tr2 = s2.beginTransaction();
            s2.save(gt1);
            s2.flush();
            tr2.commit();
        }
        if (selected.getIdLPR() != 0) {
            Gostirezervacije gt1 = new Gostirezervacije();
            gt1.setBrojKartice(cred);
            gt1.setBrojPasosa(pass);
            gt1.setSifra(password);
            gt1.setOdrasli(adult);
            gt1.setImePrezime(name);
            gt1.setOdobreno(true);
            gt1.setIdLeta(selected.getIdLPR());
            SessionFactory sf2 = new Configuration().configure().buildSessionFactory();
            Session s2 = sf2.openSession();
            Transaction tr2 = s2.beginTransaction();
            s2.save(gt1);
            s2.flush();
            tr2.commit();
        }
        RequestContext context = RequestContext.getCurrentInstance();
        RequestContext.getCurrentInstance().update("formX:formY:pass");
        context.execute("PF('confDialog').show();");
    }

    public String conf() {
        return "index.xhtml";
    }

}
