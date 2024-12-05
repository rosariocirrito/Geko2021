package it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgramma;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
//import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.command.Command;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione.ObiettivoAssegnazione;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivobiettivo.OivObiettivo;

/**
 *
 * @author Cirrito
 */
@Entity
@Table(name="obiettivo")
public class Obiettivo {
    @Id
    @GeneratedValue
    private Integer idObiettivo;

    private int anno;
    private String codice;
    private String descrizione;
    private String descrizioneProp;
    private String note; 
    private boolean apicale;
    private int peso;
    private int pesoApicale;
    
    @Column(name="idIncarico")
    private Integer incaricoID;
    
    transient private boolean critico;
    transient private List<Azione> azioniCritiche;
    transient private List<Azione> azioniOiv;
    transient private float punteggio;
    transient private float punteggioApicale;
    transient private boolean nuovo;

    transient private Date dataUltima;
    
    transient private Date dataUltimaApicale;
    
    transient private String indicatore;
    transient private String valObiettivo;
    transient private int IdIncaricoScelta;
    transient private boolean apicaleDiretto;
    transient private int idIncaricoApicale; 
    
    transient private List<Command> allowedCommands;
    transient private List<Command> guiCommands;
    transient private boolean rendicontabile;
    transient private boolean valutabile;
    transient private int pesoAzioni;
    
    transient private List<IncaricoGeko> incarichiDirig = new ArrayList<IncaricoGeko>();
    transient private int incaricoPadreID; // per i pop
       
    public enum TipoEnum{
        DIRIGENZIALE,   // 0
        POS_ORGAN, // 1
        STRUTTURA, // 2
        COMPARTO   // 3
    }

    public enum StatoApprovEnum{
    	INTERLOCUTORIO,
        PROPOSTO,
        RICHIESTO,
        VALIDATO,
        RESPINTO,
        ANNULLATO,
        DEFINITIVO,		
        RIVISTO,		// forse obsoleto
        PROPOSTOGAB,	// necessario per apicale
        RICHIESTOGAB,
        CONCORDATO 		// necessario per apicale
    }
    
    public enum StatoRealizEnum{
        DA_REALIZZARE,
        RIS_PARZIALE,
        REALIZZATO,
        MANCATO
    }
    
    public enum StatoValutazEnum{
        DA_VALUTARE,
        PARZIALE,
        POSITIVA,
        NEGATIVA,
    }
    
    
    @Enumerated
    private TipoEnum tipo;
    
    @Enumerated
    private StatoApprovEnum statoApprov;
    
    @Enumerated
    private StatoRealizEnum statoRealiz;
    
    @Enumerated
    private StatoValutazEnum statoValut;
    
        
    // bi-directional one-to-many association 
    // many Azione to one Obiettivo 
    // owner side lato inverso
    
    // su Spring 3 pag 331
    //@OneToMany(mappedBy = "obiettivo", cascade=CascadeType.ALL)
    // occorre forse definire il Cascade su MySql
    // comunque mi da errore su orphanRemoval=true
    // impostare il metodo addAzioneToObiettivo()
    
    @OneToMany(mappedBy = "obiettivo",fetch = FetchType.EAGER)
    private Set<Azione> azioni = new HashSet<Azione>();
    
    @OneToMany(mappedBy = "apicale",fetch = FetchType.LAZY)
    private Set<AssociazObiettivi> associazObiettivi = new HashSet<AssociazObiettivi>();
    
    
    transient private List<AssociazProgramma> associazProgramma = new ArrayList<AssociazProgramma>();
    
    @OneToMany(mappedBy = "obiettivo",fetch = FetchType.LAZY)
    private Set<OivObiettivo> oivObiettivo = new HashSet<OivObiettivo>();
    
    
    
    
    
    // bi-directional many-to-one association 
    // many Obiettivi to one Struttura 
    // owner side lato diretto
    //@ManyToOne
    //@JoinColumn(name="idStruttura")
    //private OpPersonaGiuridica struttura;
    
 // bi-directional many-to-one association 
    // many Obiettivi to one Struttura 
    // owner side lato diretto
   // @ManyToOne
   // @JoinColumn(name="idIncarico")
    transient private IncaricoGeko incarico;
    transient private PersonaFisicaGeko responsabileIncarico;
    transient private PersonaGiuridicaGeko strutturaIncarico;
    transient private String order;
    transient private String orderStru;
    transient private String responsabile;
    transient private List<ObiettivoAssegnazione> assegnazioni = new ArrayList<ObiettivoAssegnazione>();
    
   
    
    //       
    public Obiettivo() {}   
          
    public Integer getIncaricoID() {return incaricoID;}
	public void setIncaricoID(Integer incaricoID) {this.incaricoID = incaricoID;}



	public boolean isNuovo() {
	return (this.idObiettivo == null);
    }
    
    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    
    public String getDescrizioneProp() {
		return descrizioneProp;
	}

	public void setDescrizioneProp(String descrizioneProp) {
		this.descrizioneProp = descrizioneProp;
	}

	public String getNote() {
		if (this.note !=null && this.note.length() > 0) return note;
		else return "";
		}

    public void setNote(String note) {
        this.note = note;
    }


    public Integer getIdObiettivo() {
        return idObiettivo;
    }

    public void setIdObiettivo(Integer idobiettivo) {
        this.idObiettivo = idobiettivo;
    }


	@Override
    public boolean equals(Object o){
	boolean check = false;
	if(o instanceof Obiettivo){
            if(((Obiettivo)o).idObiettivo == null ){
            if(idObiettivo == null)
                check = true;
            }else if(((Obiettivo)o).idObiettivo.equals(idObiettivo))
		check = true;
            }
            return check;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (this.idObiettivo != null ? this.idObiettivo.hashCode() : 0);
        return hash;
    }
    
    protected Set<Azione> getAzioniInternal() {
        return azioni;
    }
    
    public List<Azione> getAzioni() {
        List<Azione> sortedAzioni = new ArrayList<Azione>(this.getAzioniInternal());
        PropertyComparator.sort(sortedAzioni, new MutableSortDefinition("order",true,true));
        return sortedAzioni;
    }
    
    public List<Azione> getAzioniCritiche() {
        List<Azione> sortedAzioniCritiche = new ArrayList<Azione>();
        for(Azione act : this.getAzioniInternal()){
        	if (act.isCritico()) {
        		sortedAzioniCritiche.add(act);
        	}
        }
        PropertyComparator.sort(sortedAzioniCritiche, new MutableSortDefinition("order",true,true));
        return sortedAzioniCritiche;
    }
    
    public List<Azione> getAzioniOiv() {
        List<Azione> sortedAzioniOiv = new ArrayList<Azione>();
        for(Azione act : this.getAzioniInternal()){
        	if (act.isOiv()) {
        		sortedAzioniOiv.add(act);
        	}
        }
        PropertyComparator.sort(sortedAzioniOiv, new MutableSortDefinition("order",true,true));
        return sortedAzioniOiv;
    }


    public void setAzioni(Set<Azione> azioni) {
        this.azioni = azioni;
    }
    
    // vedi Spring Persistence with Hibernate Beginning p.72 Kindle
    // � un metodo conveniente per le associazioni bidirezionali
    // perch� i riferimenti sono settati su entrambi i lati dell'associazione
    
    // suggerito anche da ProSpring 3 pag.331
    
    public boolean addAzioneToObiettivo(Azione azione){
        // sul lato azione
        azione.setObiettivo(this);
        // sul lato obiettivo
        return this.getAzioniInternal().add(azione);
    }
    
    public boolean removeAzioneFromObiettivo(Azione azione){
        // sul lato obiettivo
        return this.getAzioniInternal().remove(azione);
    }
    
    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public StatoApprovEnum getStatoApprov() {
        return statoApprov;
    }

    public void setStatoApprov(StatoApprovEnum statoApprov) {
        this.statoApprov = statoApprov;
    }
    
    public void managerPropone() {
        this.setStatoApprov(Obiettivo.StatoApprovEnum.PROPOSTO);
    }
    
    public void compartoPropone() {
        this.setStatoApprov(Obiettivo.StatoApprovEnum.PROPOSTO);
    }
    
    public void controllerRendeProposto() {
        this.setStatoApprov(Obiettivo.StatoApprovEnum.PROPOSTO);
    }
    
    public void controllerPropone() {
        this.setStatoApprov(Obiettivo.StatoApprovEnum.PROPOSTOGAB);
    }
    
    public void controllerConcorda() {
        this.setStatoApprov(Obiettivo.StatoApprovEnum.CONCORDATO);
    }
    
    public void gabinettoConcorda() {
        this.setStatoApprov(Obiettivo.StatoApprovEnum.CONCORDATO);
    }
    
    public void managerRendiInterlocutorio() {
        this.setStatoApprov(Obiettivo.StatoApprovEnum.INTERLOCUTORIO);
    }
    public void compartoRendiInterlocutorio() {
        this.setStatoApprov(Obiettivo.StatoApprovEnum.INTERLOCUTORIO);
    }
    
    public void managerAccettaDefinitivamente() {
        this.setStatoApprov(Obiettivo.StatoApprovEnum.DEFINITIVO);
    }
    public void compartoAccettaDefinitivamente() {
        this.setStatoApprov(Obiettivo.StatoApprovEnum.DEFINITIVO);
    }

    public void controllerRendiInterlocutorio() {
        this.setStatoApprov(Obiettivo.StatoApprovEnum.INTERLOCUTORIO);
    }
    
    public StatoRealizEnum getStatoRealiz() {
        return statoRealiz;
    }

    public void setStatoRealiz(StatoRealizEnum statoRealiz) {
        this.statoRealiz = statoRealiz;
    }

    public StatoValutazEnum getStatoValut() {
        return statoValut;
    }

    public void setStatoValut(StatoValutazEnum statoValut) {
        this.statoValut = statoValut;
    }

   

	public TipoEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoEnum tipo) {
        this.tipo = tipo;
    }

    
    /*
    public void setStruttura(OpPersonaGiuridica struttura) {
        this.struttura = struttura;
    }
    */


    public IncaricoGeko getIncarico() {
			return incarico;
	}

	public void setIncarico(IncaricoGeko incarico) {
		this.incarico = incarico;
	}

	

    public void setPeso(int peso) {
        this.peso = peso;
    }

    

	public String getOrder() {
		String order ="";
		if (this.getIncarico() != null) order = order + this.getIncarico().denominazioneStruttura;
		order = order + this.tipo.ordinal() * 10000 +this.codice+this.getIdObiettivo();
		return order;
	}

	public boolean isApicale() {
		/*
		boolean apic = false;
		if (this.apicale) apic = true;
		if (this.getPesoApicale()>0) apic = true;
		return apic;
		*/
		return this.apicale;
	}

	public boolean isApicaleDiretto() {
		if (this.apicale && this.incarico.incaricoDipartimentale)		
		return true;
		else
		return false;
	}
	
	public void setApicale(boolean apicale) {
		this.apicale = apicale;
	}
    
	public boolean isCritico() {
		boolean crit = false;
		List<Azione> azioniCritiche = this.getAzioniCritiche(); 
		return (!azioniCritiche.isEmpty()); // da sistemare
	}

	public Set<AssociazObiettivi> getAssociazObiettiviInternal() {
		return associazObiettivi;
	}

	public void setAssociazObiettivi(Set<AssociazObiettivi> associazObiettivi) {
		this.associazObiettivi = associazObiettivi;
	}

	public List<AssociazObiettivi> getAssociazObiettivi() {
        List<AssociazObiettivi> sortedAssociazioni = new ArrayList<AssociazObiettivi>(this.getAssociazObiettiviInternal());
        //PropertyComparator.sort(sortedAssociazioni, new MutableSortDefinition("denominazione",true,true));
        return sortedAssociazioni;
    }
	
		
	public List<AssociazProgramma> getAssociazProgramma() {
		return associazProgramma;
	}


	public void setAssociazProgramma(List<AssociazProgramma> associazProgramma) {
		this.associazProgramma = associazProgramma;
	}



	@Override
	public Obiettivo clone()  {
            Obiettivo obiettivo = new Obiettivo();
            obiettivo.setCodice(this.codice);
            obiettivo.setDescrizione(this.descrizione);
            obiettivo.setDescrizioneProp("");
            if (!note.isEmpty())obiettivo.setNote(this.note);
            else obiettivo.setNote("");
            obiettivo.setTipo(this.tipo);
            obiettivo.setApicale(this.apicale);
            obiettivo.setStatoApprov(Obiettivo.StatoApprovEnum.INTERLOCUTORIO);
            //obiettivo.setStruttura(this.struttura);
            obiettivo.setAnno(this.anno+1);
            obiettivo.setStatoRealiz(Obiettivo.StatoRealizEnum.DA_REALIZZARE);
            obiettivo.setStatoValut(Obiettivo.StatoValutazEnum.DA_VALUTARE);
            //obiettivo.setPriorita(Obiettivo.PrioritaEnum.BASSA);
            obiettivo.setIncaricoID(this.incaricoID);
            obiettivo.setPeso(this.peso);
            obiettivo.setPesoApicale(this.pesoApicale);
            return obiettivo;
	}

	
	public Obiettivo cloneStessoAnno()  {
    		Obiettivo obiettivo = new Obiettivo();
    		obiettivo.setCodice(this.codice);
    		obiettivo.setDescrizione(this.descrizione);
    		obiettivo.setDescrizioneProp("");
    		if (!note.isEmpty())obiettivo.setNote(this.note);
    		else obiettivo.setNote("");
    		obiettivo.setTipo(this.tipo);
    		obiettivo.setApicale(this.apicale);
    		obiettivo.setStatoApprov(Obiettivo.StatoApprovEnum.INTERLOCUTORIO);
            //obiettivo.setStruttura(this.struttura);
            obiettivo.setAnno(this.anno);
            obiettivo.setStatoRealiz(Obiettivo.StatoRealizEnum.DA_REALIZZARE);
            obiettivo.setStatoValut(Obiettivo.StatoValutazEnum.DA_VALUTARE);
            //obiettivo.setPriorita(Obiettivo.PrioritaEnum.BASSA);
            obiettivo.setPeso(this.peso);
            obiettivo.setPesoApicale(this.pesoApicale);
    		return obiettivo;
	}
	

	public String getIndicatore() {
		List<Azione> azioni = this.getAzioni();
		String ultimoIndicatore="";
		for(Azione act : azioni){
			ultimoIndicatore = act.getIndicatore();
		}
		return ultimoIndicatore;
	}

	public String getValObiettivo() {
		List<Azione> azioni = this.getAzioni();
		String ultimoValObiettivo="";
		for(Azione act : azioni){
			ultimoValObiettivo = act.getProdotti();
		}
		return ultimoValObiettivo;
	}

	
	// ______________________ Business methods _________________________________________________
	
	
   
    
    // 1. ----------------  metodi associati all'incarico
	
	
	
	//
	
	public Date getDataUltima() {
		List<Azione> azioni = this.getAzioni();
		Date dataUltimaAzioni=null;
		for(Azione act : azioni){
			dataUltimaAzioni = act.getScadenza();
		}
		return dataUltimaAzioni;
	}
	
	public Date getDataUltimaApicale() {
		List<Azione> azioni = this.getAzioni();
		Date dataUltimaAzioni=null;
		for(Azione act : azioni){
			dataUltimaAzioni = act.getScadenzaApicale();
		}
		return dataUltimaAzioni;
	}
	
	// 
	public int getPeso() {
		return this.peso;
	}
	
	
	
//	2. getPesoApicale()
	public int getPesoApicale() {
		/*
    	int pesoAzioni = 0;
    	//
	    for (Azione act : this.getAzioni()){
	    	pesoAzioni += act.getPesoApicale();
	    }
	    if (pesoAzioni == 100) return this.pesoApicale;
		if (this.getAnno() >= 2019) return this.pesoApicale;
		else return pesoAzioni;
		*/
		return this.pesoApicale;
	}
	
	
	public void setPesoApicale(int pesoApicale) {
		this.pesoApicale = pesoApicale;
	}



	//
	public float getPunteggio() {	
            int pesoAzioni = 0;
            float punteggioAct=0;
            // itero sulle azioni
            for (Azione act : this.getAzioni()){
                    pesoAzioni += act.getPeso();
                    punteggioAct += act.getPunteggio();
            }
            // calcolo il punteggio
            if (pesoAzioni == 100) return (float)(pesoAzioni*punteggioAct*this.peso)/10000;
            else return 0;
        }

	//
	public float getPunteggioApicale() {		
		// SMVP2021
		int pesoAzioniApicali = 0;
    	float punteggioActApicale=0;
    	// itero sulle azioni
    	for (Azione act : this.getAzioni()){
    		pesoAzioniApicali += act.getPesoApicale();
    		punteggioActApicale += act.getPunteggioApicale();
    	}
    	// calcolo il punteggio
    	if (pesoAzioniApicali == 100) return (float)(pesoAzioniApicali*punteggioActApicale*this.peso)/10000;
    	else return 0;
    }

	public int getIdIncaricoScelta() {
		return IdIncaricoScelta;
	}

	public void setIdIncaricoScelta(int idIncaricoScelta) {
		IdIncaricoScelta = idIncaricoScelta;
	}

	@Override
	public String toString() {
		return "Obiettivo [idObiettivo=" + idObiettivo + ", anno=" + anno
				+ ", codice=" + codice + ", descrizione=" + descrizione
				+ ", note=" + note + ", apicale=" + apicale + ", peso=" + peso
				+ ", pesoApicale=" + pesoApicale + ", tipo=" + tipo
				+ "]";
	}



	public PersonaFisicaGeko getResponsabileIncarico() {return responsabileIncarico;}

	public void setResponsabileIncarico(PersonaFisicaGeko responsabileIncarico) {
		this.responsabileIncarico = responsabileIncarico;
	}



	public PersonaGiuridicaGeko getStrutturaIncarico() {return strutturaIncarico;}

	public void setStrutturaIncarico(PersonaGiuridicaGeko strutturaIncarico) {
		this.strutturaIncarico = strutturaIncarico;
	}

	public int getIdIncaricoApicale() {	return idIncaricoApicale;}

	public void setIdIncaricoApicale(int idIncaricoApicale) {
		this.idIncaricoApicale = idIncaricoApicale;
	}



	public List<Command> getAllowedCommands() {
		List<Command> allowed = new ArrayList<Command>();	
		if (this.tipo.equals(Obiettivo.TipoEnum.DIRIGENZIALE)){
			switch(this.statoApprov) {		 
			    case INTERLOCUTORIO:
			    	allowed.add(new Command("MANAGER","PROPONI"));
			    	allowed.add(new Command("MANAGER","MODIFICA"));
			    	allowed.add(new Command("MANAGER","ELIMINA"));
			    	allowed.add(new Command("MANAGER","CREA AZIONE"));		    	
			    	//		    			    	
			    	if(this.isApicaleDiretto()){
			    		allowed.add(new Command("CONTROLLER","PROPONIGAB"));
			    		allowed.add(new Command("CONTROLLER","EDIT APICALE"));
			    		allowed.add(new Command("CONTROLLER","CREA AZIONE APICALE"));
			    		//
			    		allowed.add(new Command("GABINETTO","EDIT APICALE"));
				    	allowed.add(new Command("GABINETTO","CREA AZIONE APICALE"));
			    	}
			    	else{
			    		allowed.add(new Command("CONTROLLER","RICHIEDI"));
			    		allowed.add(new Command("CONTROLLER","RENDI PROPOSTO"));
			    		allowed.add(new Command("CONTROLLER","MODIFICA"));
				    	allowed.add(new Command("CONTROLLER","ELIMINA"));
				    	allowed.add(new Command("CONTROLLER","CREA AZIONE"));
				    	allowed.add(new Command("CONTROLLER","VALIDA"));
			    	}		    		
			        break;
			 
			    case PROPOSTO:
			    	if(this.isApicaleDiretto()){
			    		allowed.add(new Command("CONTROLLER","PROPONIGAB"));
			    		allowed.add(new Command("CONTROLLER","MODIFICA"));
			    		allowed.add(new Command("CONTROLLER","RENDI INTERLOCUTORIO"));
			    	} else {
			    		allowed.add(new Command("CONTROLLER","MODIFICA"));
			    		allowed.add(new Command("CONTROLLER","RIVEDI"));
				    	allowed.add(new Command("CONTROLLER","VALIDA"));
				    	allowed.add(new Command("CONTROLLER","RESPINGI"));
				    	allowed.add(new Command("CONTROLLER","RENDI INTERLOCUTORIO"));
			    	}
			    		
			        break;
			        
			    case PROPOSTOGAB:
			    	if(this.isApicaleDiretto()){
			    		allowed.add(new Command("GABINETTO","CONCORDA"));
			    		allowed.add(new Command("CONTROLLER","MODIFICA"));
			    		allowed.add(new Command("CONTROLLER","RENDI INTERLOCUTORIO"));
			    		
			    	} 
			    		
			        break;   
			        
			    case    RICHIESTO:
			    	allowed.add(new Command("MANAGER","DEFINISCI"));
			    	allowed.add(new Command("CONTROLLER","MODIFICA"));
			    	allowed.add(new Command("CONTROLLER","ELIMINA"));
			    	allowed.add(new Command("CONTROLLER","ANNULLA"));
			    	allowed.add(new Command("CONTROLLER","CREA AZIONE"));
			    	allowed.add(new Command("CONTROLLER","RENDI INTERLOCUTORIO"));
			        allowed.add(new Command("CONTROLLER","CLONA DIR ANNO+1"));
                                break;
			        
			    case    RICHIESTOGAB:
			    	allowed.add(new Command("CONTROLLER","CONCORDA"));
			    	//
			    	allowed.add(new Command("GABINETTO","RENDI INTERLOCUTORIO"));
			    	allowed.add(new Command("GABINETTO","EDIT APICALE"));
			    	allowed.add(new Command("GABINETTO","CREA AZIONE APICALE"));
			        break;    
			        
			    case    VALIDATO:
			    	allowed.add(new Command("MANAGER","DEFINISCI"));
			    	allowed.add(new Command("CONTROLLER","RENDI INTERLOCUTORIO"));
			    	allowed.add(new Command("CONTROLLER","ANNULLA"));
			    	if (this.apicale) {
			    		allowed.add(new Command("CONTROLLER","PROPONIGAB"));
		    			allowed.add(new Command("CONTROLLER","EDIT APICALE"));
		    			allowed.add(new Command("CONTROLLER","TOGLI APICALE"));
		    			allowed.add(new Command("GABINETTO","CONCORDA"));
		    			allowed.add(new Command("CONTROLLER","RENDI INTERLOCUTORIO"));
		    		}
			    	//else allowed.add(new Command("CONTROLLER","RENDI APICALE"));
			        break;
			        
			    case    RESPINTO:
			    	allowed.add(new Command("CONTROLLER","RENDI INTERLOCUTORIO"));
			    	allowed.add(new Command("CONTROLLER","ANNULLA"));
			        break;
			        
			    case    ANNULLATO:
			    	allowed.add(new Command("CONTROLLER","RENDI INTERLOCUTORIO"));
			        break;
			        
			    case    DEFINITIVO:
			    	allowed.add(new Command("CONTROLLER","RIVEDI"));
			    	allowed.add(new Command("CONTROLLER","ANNULLA"));
		    		if (this.apicale) {
		    			allowed.add(new Command("CONTROLLER","PROPONIGAB"));
		    			allowed.add(new Command("CONTROLLER","EDIT APICALE"));
		    			allowed.add(new Command("CONTROLLER","TOGLI APICALE"));
		    			allowed.add(new Command("GABINETTO","CONCORDA"));
		    		}
		    		//else allowed.add(new Command("CONTROLLER","RENDI APICALE")); 	
			    	
			        break;
			        
			    case    RIVISTO:
			    	allowed.add(new Command("MANAGER","RENDI INTERLOCUTORIO"));
			    	allowed.add(new Command("CONTROLLER","RENDI INTERLOCUTORIO"));
			    	allowed.add(new Command("CONTROLLER","VALIDA"));
			        break;  
			    
			    case    CONCORDATO:
			    	allowed.add(new Command("GABINETTO","RENDI INTERLOCUTORIO"));
			    	allowed.add(new Command("CONTROLLER","CLONA ANNO+1"));
			        break;
			    //default:
			}
		}
		
		
		// ---------------------
		if (this.tipo.equals(Obiettivo.TipoEnum.STRUTTURA)){
			switch(this.statoApprov) {		 
			    case INTERLOCUTORIO:
			    	allowed.add(new Command("MANAGER","RENDI DEFINITIVO"));
			    	allowed.add(new Command("MANAGER","MODIFICA COMPARTO"));
			    	allowed.add(new Command("MANAGER","ELIMINA"));
			    	allowed.add(new Command("MANAGER","CREA AZIONE"));		   
			    	break;
			    
			    
			    
			    case    DEFINITIVO:
			    	allowed.add(new Command("MANAGER","RENDI INTERLOCUTORIO"));  			    					    	
			        break;
			      //default:
			}			
		}
		
		// ----------------
		if (this.tipo.equals(Obiettivo.TipoEnum.POS_ORGAN)){
			switch(this.statoApprov) {		 
			    case INTERLOCUTORIO:
			    	allowed.add(new Command("RESP_POP","PROPONI"));
			    	allowed.add(new Command("RESP_POP","MODIFICA"));
			    	allowed.add(new Command("RESP_POP","ELIMINA"));
			    	allowed.add(new Command("RESP_POP","CREA AZIONE"));		
			    	//
			    	allowed.add(new Command("MANAGER","RENDI DEFINITIVO"));
			    	allowed.add(new Command("MANAGER","MODIFICA"));
			    	allowed.add(new Command("MANAGER","ELIMINA"));
			    	allowed.add(new Command("MANAGER","CREA AZIONE"));		   
			    	break;
			    case    RICHIESTO:
			    	allowed.add(new Command("MANAGER","RENDI DEFINITIVO"));
			    	allowed.add(new Command("MANAGER","MODIFICA"));
			    	allowed.add(new Command("MANAGER","ELIMINA"));
			    	allowed.add(new Command("MANAGER","ANNULLA"));
			    	allowed.add(new Command("MANAGER","CREA AZIONE"));
			    	allowed.add(new Command("MANAGER","RENDI INTERLOCUTORIO"));
			        break;
			    case    PROPOSTO:
			    	allowed.add(new Command("MANAGER","RENDI DEFINITIVO"));			    	
			    	allowed.add(new Command("MANAGER","RENDI INTERLOCUTORIO"));
			        break;
			    	//		
			    case    DEFINITIVO:
			    	allowed.add(new Command("MANAGER","RENDI INTERLOCUTORIO"));		    					    	
			        break;
			      //default:
			}			
		}
		//
		return allowed;
	}



	public List<Command> getGuiCommands() {
		return guiCommands;
	}



	public void setGuiCommands(List<Command> guiCommands) {
		this.guiCommands = guiCommands;
	}



	public Set<OivObiettivo> getOivObiettivo() {
		return oivObiettivo;
	}



	public void setOivObiettivo(Set<OivObiettivo> oivObiettivo) {
		this.oivObiettivo = oivObiettivo;
	}



	public boolean isRendicontabile() {
		if (this.statoApprov.equals(Obiettivo.StatoApprovEnum.PROPOSTO) ||
			this.statoApprov.equals(Obiettivo.StatoApprovEnum.VALIDATO) ||
			this.statoApprov.equals(Obiettivo.StatoApprovEnum.DEFINITIVO) ||
			this.statoApprov.equals(Obiettivo.StatoApprovEnum.RICHIESTO) ||
			this.statoApprov.equals(Obiettivo.StatoApprovEnum.PROPOSTOGAB) ||
			this.statoApprov.equals(Obiettivo.StatoApprovEnum.RICHIESTOGAB) ||
			this.statoApprov.equals(Obiettivo.StatoApprovEnum.CONCORDATO) 
				) return true;
		else return false;
	}



	public boolean isValutabile() {return valutabile;}
	public String getResponsabile() {return responsabile;}

	public void setResponsabile(String responsabile) {this.responsabile = responsabile;}

	public int getPesoAzioni() {
		int pesoAzioni = 0;
		for (Azione act : this.getAzioni()){
			pesoAzioni += act.getPeso();
	    }
		//
		return pesoAzioni;
	}

	
	// Pro Spring 3 pag 331
    public List<ObiettivoAssegnazione> getAssegnazioni() {
    	/*
        SortedSet<ObiettivoAssegnazione> sortByNome = new TreeSet<ObiettivoAssegnazione>(new
                Comparator<ObiettivoAssegnazione>()
        {
            public int compare(ObiettivoAssegnazione a, ObiettivoAssegnazione b)
            {
            	
                String nomeA = "a"; // da correggere a.getOrder();
                String nomeB = "b"; // b.getOpPersonaFisica().getOrder();
                return nomeA.compareTo(nomeB);
            }
        });
        sortByNome.addAll(assegnazioni);  
        return sortByNome;
        */
    	return this.assegnazioni;
    }

    
    public void setAssegnazioni(List<ObiettivoAssegnazione> assegnazioni) {
        this.assegnazioni = assegnazioni;
    }

    
	public List<IncaricoGeko> getIncarichiDirig() {
		return incarichiDirig;
	}

	public void setIncarichiDirig(List<IncaricoGeko> incarichiDirig) {
		this.incarichiDirig = incarichiDirig;
	}

	public int getIncaricoPadreID() {
		return incaricoPadreID;
	}

	public void setIncaricoPadreID(int incaricoPadreID) {
		this.incaricoPadreID = incaricoPadreID;
	}

} // ------------------------------------------------
