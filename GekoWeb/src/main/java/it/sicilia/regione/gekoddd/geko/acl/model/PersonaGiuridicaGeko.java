package it.sicilia.regione.gekoddd.geko.acl.model;

import it.sicilia.regione.gekoddd.geko.acl.dto.PersonaGiuridicaGekoDTO;
import it.sicilia.regione.gekoddd.geko.acl.dto.PersonaGiuridicaGekoDTO.OpPersonaGiuridicaStatoEnum;
//import it.sicilia.regione.gekoddd.organiko.domain.entity.OpPersonaGiuridica.OpPersonaGiuridicaStatoEnum;
//import it.sicilia.regione.gekoddd.organiko.hs.pl.PersonaGiuridicaDTO;

import java.util.*;

public class PersonaGiuridicaGeko  {
	
    public static final long serialVersionUID = 1L;
    
    public Integer idPersona;
    public final String denominazione;
    public final String codice;
    public final String competenze;
    public final OpPersonaGiuridicaStatoEnum stato;
    public final Date dataCambioStato;
    public final Date dataInserimento;
    public final Integer pgTipoID;
    public final Integer pgPadreID;
    public final Integer pgDeptID;
    public final boolean dipartimento;
    public final boolean cancellata;
    public final String responsabile;
    
    // da iniettare
    private List<String> nomiDipendenti;
    private List<PersonaGiuridicaGeko> struttureAttive;

    /*
    public PersonaGiuridicaGeko(PersonaGiuridicaDTO pg) {
    	this.idPersona = pg.idPersona;
    	this.denominazione = pg.denominazione;
    	this.codice = pg.codice;
    	this.competenze = pg.competenze;
    	this.stato = pg.stato  ;
    	this.dataCambioStato = pg.dataCambioStato;
    	this.dataInserimento  = pg.dataInserimento;
    	this.pgTipoID  = pg.pgTipoID  ;
    	this.pgPadreID  = pg.pgPadreID ;
    	this.pgDeptID = pg.pgDeptID;
    	this.dipartimento = pg.dipartimento;
    	this.cancellata = pg.cancellata;
    	this.responsabile = pg.responsabile;
    }
    */
    
    public PersonaGiuridicaGeko(PersonaGiuridicaGekoDTO pg) {
    	this.idPersona = pg.getIdPersona();
    	this.denominazione = pg.getDenominazione();
    	this.codice = pg.getCodice();
    	this.competenze = pg.getCompetenze();
    	this.stato = pg.getStato()  ;
    	this.dataCambioStato = pg.getDataCambioStato();
    	this.dataInserimento  = pg.getDataInserimento();
    	this.pgTipoID  = pg.getPgTipoID()  ;
    	this.pgPadreID  = pg.getPgPadreID() ;
    	this.pgDeptID = pg.getPgDeptID();
    	this.dipartimento = pg.isDipartimento();
    	this.cancellata = pg.isCancellata();
    	this.responsabile = pg.getResponsabile();
    }

	public String getDenominazione() {return denominazione;}
	public String getCompetenze() {return competenze;}

	public boolean isDipartimento() {return dipartimento;}
	public boolean isCancellata() {return cancellata;}

	public Integer getPgPadreID() {return pgPadreID;}
	public Integer getPgDeptID() {return pgDeptID;}
	public String getResponsabile() {return responsabile;}

	public String getCodice() {	return codice;}

	public List<String> getNomiDipendenti() {
		return nomiDipendenti;
	}
	//
	public void setNomiDipendenti(List<PersonaFisicaGeko> dipendenti) {
		List<String> nomi = new ArrayList<String>();
		for(PersonaFisicaGeko dip : dipendenti){
			nomi.add(dip.getCognomeNome());
		}
		this.nomiDipendenti = nomi;
	}

	public List<PersonaGiuridicaGeko> getStruttureAttive() {
		return struttureAttive;
	}

	public void setStruttureAttive(List<PersonaGiuridicaGeko> struttureAttive) {
		this.struttureAttive = struttureAttive;
	}

	public Integer getIdPersona() {
		return idPersona;
	}
	
   
} // -------------------------------------------------------

