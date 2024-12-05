package it.sicilia.regione.gekoddd.geko.acl.dto;


import javax.persistence.*;

//import it.sicilia.regione.gekoddd.organiko.domain.entity.OpPersonaFisica;
//import it.sicilia.regione.gekoddd.organiko.domain.entity.OpPersonaFisica.OpPersonaFisicaStatoEnum;

/**
 * The persistent class for the op_persona_fisica database table.
 * 
 */

public class PersonaFisicaGekoDTO {
	public static final long serialVersionUID = 1L;
	
	 public enum OpPersonaFisicaStatoEnum {
	        ATTIVO, 
		CANCELLATO, // Cancellato e non visibile in anagrafica
		TRASFERITO, 
		PENSIONTATO, 
		LICENZIATO, 
		NASCOSTO // Visibile solo da particolari utenti
	    }
    
	private Integer idPersona;
	private String cognome;
	private String nome;
	private String stringa;
	private String matricola;
	private String categoria;
	
    @Enumerated
    private OpPersonaFisicaStatoEnum stato;
    private Integer pfTipoID;
    private Integer pgID;
    private boolean dirigente;
    private boolean dirigenteApicale;
    
 // ------------------------ metodi ------------
    public PersonaFisicaGekoDTO() {	}
    
	public Integer getIdPersona() {
		return idPersona;
	}
	
	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getStringa() {
		return stringa;
	}
	public void setStringa(String stringa) {
		this.stringa = stringa;
	}
	public String getMatricola() {
		return matricola;
	}
	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}
	public OpPersonaFisicaStatoEnum getStato() {
		return stato;
	}
	public void setStato(OpPersonaFisicaStatoEnum stato) {
		this.stato = stato;
	}
	public Integer getPfTipoID() {
		return pfTipoID;
	}
	public void setPfTipoID(Integer pfTipoID) {
		this.pfTipoID = pfTipoID;
	}
	public Integer getPgID() {
		return pgID;
	}
	public void setPgID(Integer pgID) {
		this.pgID = pgID;
	}
	public boolean isDirigente() {
		return dirigente;
	}
	public void setDirigente(boolean dirigente) {
		this.dirigente = dirigente;
	}
	public boolean isDirigenteApicale() {
		return dirigenteApicale;
	}
	public void setDirigenteApicale(boolean dirigenteApicale) {
		this.dirigenteApicale = dirigenteApicale;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
    
    
	
        
   
} // ------------------------------------------------------------
