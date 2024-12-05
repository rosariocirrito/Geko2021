package it.sicilia.regione.gekoddd.session.domain;


import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;


@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Menu {
	
	public final String appl = "geko";
	//
	private int anno;
	private int oldAnno;
	
	//
	private PersonaFisicaGeko persona;
	private List<PersonaFisicaGeko> dipendentiDept;
	//
	private PersonaGiuridicaGeko struttura, altraStruttura, dipartimento, altroDipartimento, assessorato;
	//
	private List<PersonaGiuridicaGeko> strutture;
	private List<PersonaGiuridicaGeko> dipartimenti;
	private List<PersonaGiuridicaGeko> assessorati;
	//
	private IncaricoGeko incarico;
	private IncaricoGeko incaricoDept;
	private IncaricoGeko incaricoApicaleDept;
	private IncaricoGeko incaricoApicaleAss_to;
	private IncaricoGeko incaricoApicaleAmm_ne;
	//
	private List<IncaricoGeko> incarichi;
        private List<IncaricoGeko> incarichiPop;
	private List<IncaricoGeko> incarichiDept;
	private List<IncaricoGeko> incarichiApicaliDept;
	private List<IncaricoGeko> incarichiApicaliAss_to;
	private List<IncaricoGeko> incarichiApicaliAmm_ne;
	
	// scelte
	private int idStrutturaScelta;
	private int idDipartimentoScelta;
	private int idAssessoratoScelta;
	//
	private int idIncaricoGekoScelta;
	private int idIncaricoGekoDeptScelta;
	private int idIncaricoGekoApicaleDeptScelta;
	private int idIncaricoGekoApicaleAss_toScelta;
	private int idIncaricoGekoApicaleAmm_neScelta;
	
	//
	private int giorniHistory = 3;
	private String chi = "";
	
	private String cognome;
	private String matricola;
	
	public Integer pfID;
	public Integer pgID;
	public Integer deptID;
	private Integer assID;
	
	private String oldRuolo;
	
	public Menu(){}


	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		if (anno >= 2019 && anno <2022) this.anno = anno;
		else this.anno = 2021;
	}

	public PersonaGiuridicaGeko getStruttura() {
		return struttura;
	}

	public void setStruttura(PersonaGiuridicaGeko struttura) {
		this.struttura = struttura;
	}

	public PersonaGiuridicaGeko getAltraStruttura() {
		return altraStruttura;
	}

	public void setAltraStruttura(PersonaGiuridicaGeko altraStruttura) {
		this.altraStruttura = altraStruttura;
	}

	
	public PersonaGiuridicaGeko getAltroDipartimento() {
		return altroDipartimento;
	}

	public void setAltroDipartimento(PersonaGiuridicaGeko altroDipartimento) {
		this.altroDipartimento = altroDipartimento;
	}

	public PersonaGiuridicaGeko getDipartimento() {
		return dipartimento;
	}

	public void setDipartimento(PersonaGiuridicaGeko dipartimento) {
		this.dipartimento = dipartimento;
	}

	
	public PersonaGiuridicaGeko getAssessorato() {
		return assessorato;
	}


	public void setAssessorato(PersonaGiuridicaGeko assessorato) {
		this.assessorato = assessorato;
	}


	public PersonaFisicaGeko getPersona() {
		return persona;
	}

	public void setPersona(PersonaFisicaGeko utente) {
		this.persona = utente;
	}

	public List<PersonaGiuridicaGeko> getStrutture() {
		return strutture;
	}

	public void setStrutture(List<PersonaGiuridicaGeko> strutture) {
		this.strutture = strutture;
	}

	public List<PersonaGiuridicaGeko> getDipartimenti() {
		List<PersonaGiuridicaGeko> dipartimentiOrdinati = new ArrayList(this.dipartimenti);
		PropertyComparator.sort(dipartimentiOrdinati, new MutableSortDefinition("denominazione",true,true));
		return dipartimentiOrdinati;
	}

	public void setDipartimenti(List<PersonaGiuridicaGeko> dipartimenti) {
		this.dipartimenti = dipartimenti;
	}

	public int getIdStrutturaScelta() {
		return idStrutturaScelta;
	}

	public void setIdStrutturaScelta(int idStrutturaScelta) {
		this.idStrutturaScelta = idStrutturaScelta;
	}

	
	public int getIdDipartimentoScelta() {
		return idDipartimentoScelta;
	}

	public void setIdDipartimentoScelta(int idDipartimentoScelta) {
		this.idDipartimentoScelta = idDipartimentoScelta;
	}

	public int getGiorniHistory() {
		return giorniHistory;
	}

	public void setGiorniHistory(int giorniHistory) {
		if (giorniHistory <1) giorniHistory=1;
		this.giorniHistory = giorniHistory;
	}

	public List<IncaricoGeko> getIncarichi() {
		return incarichi;
	}

	public void setIncarichi(List<IncaricoGeko> incarichi) {
		this.incarichi = incarichi;
	}

	public int getIdAssessoratoScelta() {
		return idAssessoratoScelta;
	}

	public void setIdAssessoratoScelta(int idAssessoratoScelta) {
		this.idAssessoratoScelta = idAssessoratoScelta;
	}
	
	

	public IncaricoGeko getIncarico() {
		return incarico;
	}

	public void setIncarico(IncaricoGeko incarico) {
		this.incarico = incarico;
	}
	
	

	public IncaricoGeko getIncaricoDept() {
		return incaricoDept;
	}

	public void setIncaricoDept(IncaricoGeko incaricoDept) {
		this.incaricoDept = incaricoDept;
	}

	
	public IncaricoGeko getIncaricoApicaleDept() {
		return incaricoApicaleDept;
	}

	public void setIncaricoApicaleDept(IncaricoGeko incaricoApicaleDept) {
		this.incaricoApicaleDept = incaricoApicaleDept;
	}

	public int getIdIncaricoScelta() {
		return idIncaricoGekoScelta;
	}

	public void setIdIncaricoScelta(int idIncaricoGekoScelta) {
		this.idIncaricoGekoScelta = idIncaricoGekoScelta;
	}
	
	// -----------------------------

	public List<IncaricoGeko> getIncarichiDept() {
		return incarichiDept;
	}

	public void setIncarichiDept(List<IncaricoGeko> incarichiDept) {
		this.incarichiDept = incarichiDept;
	}

	public List<IncaricoGeko> getIncarichiApicaliDept() {
		return incarichiApicaliDept;
	}

	public void setIncarichiApicaliDept(List<IncaricoGeko> incarichiApicaliDept) {
		this.incarichiApicaliDept = incarichiApicaliDept;
	}

	public int getIdIncaricoDeptScelta() {
		return idIncaricoGekoDeptScelta;
	}

	public void setIdIncaricoDeptScelta(int idIncaricoGekoDeptScelta) {
		this.idIncaricoGekoDeptScelta = idIncaricoGekoDeptScelta;
	}

	public int getIdIncaricoApicaleDeptScelta() {
		return idIncaricoGekoApicaleDeptScelta;
	}

	public void setIdIncaricoApicaleDeptScelta(int idIncaricoGekoApicaleDeptScelta) {
		this.idIncaricoGekoApicaleDeptScelta = idIncaricoGekoApicaleDeptScelta;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getMatricola() {
		return matricola;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

	public String getChi() {
		return chi;
	}

	public void setChi(String chi) {
		this.chi = chi;
		//System.out.println("menu.chi impostato a: "+chi);
	}


	public Integer getPfID() {
		return pfID;
	}


	public void setPfID(Integer pfID) {
		this.pfID = pfID;
	}


	public Integer getPgID() {
		return pgID;
	}


	public void setPgID(Integer pgID) {
		this.pgID = pgID;
	}


	public Integer getDeptID() {
		return deptID;
	}


	public void setDeptID(Integer deptID) {
		this.deptID = deptID;
	}

	

	public Integer getAssID() {
		return assID;
	}


	public void setAssID(Integer assID) {
		this.assID = assID;
	}


	public int getOldAnno() {
		return oldAnno;
	}


	public void setOldAnno(int oldAnno) {
		this.oldAnno = oldAnno;
	}


	public String getOldRuolo() {
		return oldRuolo;
	}


	public void setOldRuolo(String oldRuolo) {
		this.oldRuolo = oldRuolo;
	}


	public List<IncaricoGeko> getIncarichiApicaliAss_to() {
		return incarichiApicaliAss_to;
	}


	public void setIncarichiApicaliAss_to(List<IncaricoGeko> incarichiApicaliAss_to) {
		this.incarichiApicaliAss_to = incarichiApicaliAss_to;
	}


	public List<IncaricoGeko> getIncarichiApicaliAmm_ne() {
		return incarichiApicaliAmm_ne;
	}


	public void setIncarichiApicaliAmm_ne(List<IncaricoGeko> incarichiApicaliAmm_ne) {
		this.incarichiApicaliAmm_ne = incarichiApicaliAmm_ne;
	}


	public IncaricoGeko getIncaricoApicaleAss_to() {
		return incaricoApicaleAss_to;
	}


	public void setIncaricoApicaleAss_to(IncaricoGeko incaricoApicaleAss_to) {
		this.incaricoApicaleAss_to = incaricoApicaleAss_to;
	}


	public IncaricoGeko getIncaricoApicaleAmm_ne() {
		return incaricoApicaleAmm_ne;
	}


	public void setIncaricoApicaleAmm_ne(IncaricoGeko incaricoApicaleAmm_ne) {
		this.incaricoApicaleAmm_ne = incaricoApicaleAmm_ne;
	}


	public int getIdIncaricoGekoApicaleAss_toScelta() {
		return idIncaricoGekoApicaleAss_toScelta;
	}


	public void setIdIncaricoGekoApicaleAss_toScelta(int idIncaricoGekoApicaleAss_toScelta) {
		this.idIncaricoGekoApicaleAss_toScelta = idIncaricoGekoApicaleAss_toScelta;
	}


	public int getIdIncaricoGekoApicaleAmm_neScelta() {
		return idIncaricoGekoApicaleAmm_neScelta;
	}


	public void setIdIncaricoGekoApicaleAmm_neScelta(int idIncaricoGekoApicaleAmm_neScelta) {
		this.idIncaricoGekoApicaleAmm_neScelta = idIncaricoGekoApicaleAmm_neScelta;
	}


	public List<PersonaGiuridicaGeko> getAssessorati() {
		return assessorati;
	}


	public void setAssessorati(List<PersonaGiuridicaGeko> assessorati) {
		this.assessorati = assessorati;
	}


	public List<PersonaFisicaGeko> getDipendentiDept() {
		return dipendentiDept;
	}


	public void setDipendentiDept(List<PersonaFisicaGeko> dipendentiDept) {
		this.dipendentiDept = dipendentiDept;
	}

    public List<IncaricoGeko> getIncarichiPop() {
        return incarichiPop;
    }

    public void setIncarichiPop(List<IncaricoGeko> incarichiPop) {
        this.incarichiPop = incarichiPop;
    }
	
	
	
} // -------------
