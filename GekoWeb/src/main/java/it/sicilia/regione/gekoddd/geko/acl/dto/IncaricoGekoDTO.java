package it.sicilia.regione.gekoddd.geko.acl.dto;


//import java.time.LocalDate;
import java.util.Date;


public final class IncaricoGekoDTO {
	
    // ------------------- campi da msOrganikoKt ---------------------------------------
    
    /*
    val idIncarico: Int
    val dataInizio: Date
    val dataFine: Date
    val pfID: Int
    val pgID: Int
    val interim: Boolean

    val responsabile: String
    val codiceStruttura: String
    val denominazioneStruttura: String
    val competenzeStruttura: String
    val idDept: Int
    val denominazioneDipartimento: String
    val incaricoDipartimentale: Boolean
    val incaricoDirigenziale: Boolean
    val incaricoPop: Boolean
    val carica: String
    */

    public Integer idIncarico;
    public Date dataInizio;
    public Date dataFine;
    public int pfID;
    public int pgID;
    public boolean interim;
    
    public String responsabile;
    public String codiceStruttura;
    public String denominazioneStruttura;
    public String competenzeStruttura;
    public int idDept;
    public String denominazioneDipartimento;
    public boolean incaricoDipartimentale;
    public boolean incaricoDirigenziale;
    public boolean incaricoPop;
    public String carica;
    
    public IncaricoGekoDTO() {	}

    /*
    public Integer getIdIncarico() {return idIncarico;}
    public Date getDataInizio() {return dataInizio;}
    public Date getDataFine() {return dataFine;}
    public int getPfID() {return pfID;}
    public int getPgID() {return pgID;}
    public boolean isInterim() {return interim;}
    public String getResponsabile() {return responsabile;}
    public String getCodiceStruttura() {return codiceStruttura;}
    public String getDenominazioneStruttura() {return denominazioneStruttura;}    
    public String getCompetenzeStruttura() {return competenzeStruttura;}
    public int getIdDept() {return idDept;}
    public String getDenominazioneDipartimento() {return denominazioneDipartimento;}
    public boolean isIncaricoDipartimentale() {return incaricoDipartimentale;}
    public boolean isIncaricoDirigenziale() {return incaricoDirigenziale;}
    public boolean isIncaricoPop() {return incaricoPop;}
    public String getCarica() {return carica;}
    */
} // ----------------

