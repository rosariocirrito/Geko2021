package it.sicilia.regione.gekoddd.geko.acl.dto;


import java.util.*;

//import it.sicilia.regione.gekoddd.organiko.domain.entity.OpPersonaGiuridica;
//import it.sicilia.regione.gekoddd.organiko.domain.entity.OpPersonaGiuridica.OpPersonaGiuridicaStatoEnum;

public class PersonaGiuridicaGekoDTO {
	
    public static final long serialVersionUID = 1L;
    public enum OpPersonaGiuridicaStatoEnum{
		CANCELLATO,
		ATTIVO,
		NASCOSTO
    }
    
    private Integer idPersona;
    private String denominazione;
    private String codice;
    private String competenze;
    private OpPersonaGiuridicaStatoEnum stato;
    private Date dataCambioStato;
    private Date dataInserimento;
    private Integer pgTipoID;
    private Integer pgPadreID;
    private Integer pgDeptID;
    private boolean dipartimento;
    private boolean cancellata;
    private String responsabile;

    /*
    public PersonaGiuridicaGekoDTO(OpPersonaGiuridica pg) {
    	this.idPersona = pg.getIdPersona();
    	this.denominazione = pg.getDenominazione();
    	this.codice = pg.getCodice()  ;
    	this.competenze = pg.getCompetenze()  ;
    	this.stato = pg.getStato()  ;
    	this.dataCambioStato = pg.getDataCambioStato()  ;
    	this.dataInserimento  = pg.getDataInserimento()  ;
    	this.pgTipoID  = pg.getOpPersonaGiuridicaTipo().getIdopPersonaGiuridicaTipo()  ;
    	this.pgPadreID  = pg.getOpPersonaGiuridica().getIdPersona()  ;
    	this.dipartimento = pg.isDipartimento();
    	this.cancellata = pg.isCancellata();
    	this.pgDeptID = pg.getDipartimento().getIdPersona();
    	//this.responsabile = pg.getResponsabile().getStringa();
    }
*/
    public PersonaGiuridicaGekoDTO() {}
	public Integer getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getCompetenze() {
		return competenze;
	}

	public void setCompetenze(String competenze) {
		this.competenze = competenze;
	}

	public OpPersonaGiuridicaStatoEnum getStato() {
		return stato;
	}

	public void setStato(OpPersonaGiuridicaStatoEnum stato) {
		this.stato = stato;
	}

	public Date getDataCambioStato() {
		return dataCambioStato;
	}

	public void setDataCambioStato(Date dataCambioStato) {
		this.dataCambioStato = dataCambioStato;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Integer getPgTipoID() {
		return pgTipoID;
	}

	public void setPgTipoID(Integer pgTipoID) {
		this.pgTipoID = pgTipoID;
	}

	public Integer getPgPadreID() {
		return pgPadreID;
	}

	public void setPgPadreID(Integer pgPadreID) {
		this.pgPadreID = pgPadreID;
	}

	public Integer getPgDeptID() {
		return pgDeptID;
	}

	public void setPgDeptID(Integer pgDeptID) {
		this.pgDeptID = pgDeptID;
	}

	public boolean isDipartimento() {
		return dipartimento;
	}

	public void setDipartimento(boolean dipartimento) {
		this.dipartimento = dipartimento;
	}

	public boolean isCancellata() {
		return cancellata;
	}

	public void setCancellata(boolean cancellata) {
		this.cancellata = cancellata;
	}

	public String getResponsabile() {
		return responsabile;
	}

	public void setResponsabile(String responsabile) {
		this.responsabile = responsabile;
	}

	

   
} // -------------------------------------------------------

