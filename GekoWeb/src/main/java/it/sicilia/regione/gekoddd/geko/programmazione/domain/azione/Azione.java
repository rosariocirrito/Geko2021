package it.sicilia.regione.gekoddd.geko.programmazione.domain.azione;

import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.command.Command;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.criticita.Criticita;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.Documento;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivazione.OivAzione;

import java.util.*;

import javax.persistence.*;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

/**
 *
 * @author Cirrito
 */
@Entity
@Table(name="azione")
    public class Azione {
    @Id
    @GeneratedValue
    private Integer idAzione;
    private String denominazione;
    private String descrizione;
    private String descrizioneProp;
    private String note;  
      
    private String prodotti;
    transient String valoreObj; // al posto di prodotti
    
    private String indicatore;
    
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date scadenza;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date scadenzaApicale;
    private int peso;
    private String risultato;
    private int pesoApicale;
    private boolean tassativa;
    
    public enum CompletamentoEnum{
        ALTO, 			/* 1 */
        MEDIO, 			/* 0,7 */
        BASSO, 			/* 0,5 */
        NULLO,			/* 0,0 */
        DA_VALUTARE,
        INCAR_VARIATO
    }
    
    @Enumerated
    private CompletamentoEnum completamento;
    
    transient private boolean nuovo;
    transient private float punteggio; 
    transient private float punteggioApicale;
    transient private boolean critico;
    transient private boolean oiv;
    transient private String order;
    transient private int idIncarico; 
    transient private int idIncaricoApicale; 
    transient private int incaricoPadreID; // per i pop
    
    @ManyToOne
    @JoinColumn(name="obiettivoId")
    private Obiettivo obiettivo;
    
    // su Spring 3 pag 331
    //@OneToMany(mappedBy = "obiettivo", cascade=CascadeType.ALL)
    // occorre forse definire il Cascade su MySql
    // comunque mi da errore su orphanRemoval=true
    // impostare il metodo addAzioneToObiettivo()
    
    @OneToMany(mappedBy = "azione",fetch = FetchType.LAZY)
    private Set<AzioneAssegnazione> assegnazioni = new HashSet<AzioneAssegnazione>();
    
    @OneToMany(mappedBy = "azione",fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<Documento> documenti = new HashSet();
    
    @OneToMany(mappedBy = "azione",fetch = FetchType.LAZY)
    private Set<Criticita> criticita = new HashSet<Criticita>();
    
    @OneToMany(mappedBy = "azione",fetch = FetchType.LAZY)
    private Set<OivAzione> oivAzione = new HashSet<OivAzione>();
    
    transient private int anno;
    transient private PersonaFisicaGeko dirigente;
    
    transient private List<Command> allowedCommands;
    transient private List<Command> guiCommands;
    
    public Azione() {}

    public boolean isNuovo() {
	return (this.idAzione == null);
    }
    
    // bi-directional many-to-one association 
    // many Azione to one Obiettivo 
    // owner side lato diretto
    public Obiettivo getObiettivo() {
        return obiettivo;
    }

    public void setObiettivo(Obiettivo obiettivo) {
        this.obiettivo = obiettivo;
    }
    

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
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
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    

    public String getProdotti() {
        return prodotti;
    }

    public void setProdotti(String prodotti) {
        this.prodotti = prodotti;
    }

    
    public String getValoreObj() {
    	this.valoreObj = this.prodotti;
		return valoreObj;
	}

	public void setValoreObj(String valoreObj) {
		this.valoreObj = valoreObj;
		this.prodotti = valoreObj;
	}

	public String getIndicatore() {
		return indicatore;
	}

	public void setIndicatore(String indicatore) {
		this.indicatore = indicatore;
	}

    public String getRisultato() {
    	if (null == risultato) return "";
        return risultato;
    }

    public void setRisultato(String risultato) {
        this.risultato = risultato;
    }

	public Date getScadenza() {
		if (null != this.scadenza) return scadenza;
    	else return this.scadenzaApicale;
    }

    public void setScadenza(Date scadenza) {
        this.scadenza = scadenza;
    }

    public Date getScadenzaApicale() {
    	if (null != this.scadenzaApicale) return scadenzaApicale;
    	else return this.scadenza;
	}

	public void setScadenzaApicale(Date scadenzaApicale) {
		this.scadenzaApicale = scadenzaApicale;
	}

	public Integer getIdAzione() {
        return idAzione;
    }

    public void setIdAzione(Integer idAzione) {
        this.idAzione = idAzione;
    }

    public int getPeso() {
    	 return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public boolean isTassativa() {
		return tassativa;
	}

	public void setTassativa(boolean tassativa) {
		this.tassativa = tassativa;
	}

	public CompletamentoEnum getCompletamento() {
        return completamento;
    }

    public void setCompletamento(CompletamentoEnum completamento) {
        this.completamento = completamento;
    }

    public float getPunteggio() {
    	int valutFactor=0;
        switch (this.completamento)
        {
	        case ALTO:
		        valutFactor= 100;
		        break;
	        case MEDIO:
		        valutFactor= 70;
		        break;
	        case BASSO:
		        valutFactor= 50;
		        break;
	        case NULLO:
		        valutFactor= 0;
		        break;
	        case INCAR_VARIATO:
		        valutFactor= 0;
		        break;
        }
        this.punteggio = (float)(this.peso * valutFactor) / 100;
        return punteggio;
    }
    
    public float getPunteggioApicale() {
    	int valutFactor=0;
        switch (this.completamento)
        {
	        case ALTO:
		        valutFactor= 100;
		        break;
	        case MEDIO:
		        valutFactor= 70;
		        break;
	        case BASSO:
		        valutFactor= 50;
		        break;
	        case NULLO:
		        valutFactor= 0;
		        break;
	        case INCAR_VARIATO:
		        valutFactor= 0;
		        break;
        }
        this.punteggioApicale = (float)(this.getPesoApicale() * valutFactor) / 100;
        return punteggioApicale;
    }
    
    
   
    
    @Override
    public boolean equals(Object o){
	boolean check = false;
	if(o instanceof Azione){
            if(((Azione)o).idAzione == null ){
            if(idAzione == null)
                check = true;
            }else if(((Azione)o).idAzione.equals(idAzione))
		check = true;
            }
            return check;
    }

    
	@Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.idAzione != null ? this.idAzione.hashCode() : 0);
        return hash;
    }

    
    // Pro Spring 3 pag 331
    public Set<AzioneAssegnazione> getAssegnazioni() {
        SortedSet<AzioneAssegnazione> sortByNome = new TreeSet<AzioneAssegnazione>(new
                Comparator<AzioneAssegnazione>()
        {
            public int compare(AzioneAssegnazione a, AzioneAssegnazione b)
            {
            	
                String nomeA = "a"; // da correggere a.getOrder();
                String nomeB = "b"; // b.getOpPersonaFisica().getOrder();
                return nomeA.compareTo(nomeB);
            }
        });
        sortByNome.addAll(assegnazioni);  
        return sortByNome;
    }

    public void setAssegnazioni(Set<AzioneAssegnazione> assegnazioni) {
        this.assegnazioni = assegnazioni;
    }

    public void addAssegnazioneToAzione(AzioneAssegnazione assegnazione){
        assegnazione.setAzione(this);
        assegnazioni.add(assegnazione);
    }
    
    public void removeAssegnazioneFromAzione(AzioneAssegnazione assegnazione){
        this.getAssegnazioni().remove(assegnazione);
    }
    
    // Pro Spring 3 pag 331
    public Set<Documento> getDocumentiInternal() {
        return documenti;
    }
    
    public List<Documento> getDocumenti() {
        List<Documento> sortedDocumenti = new ArrayList<Documento>(this.getDocumentiInternal());
        PropertyComparator.sort(sortedDocumenti, new MutableSortDefinition("id",true,true));
        return sortedDocumenti;
    }
    
    //
    public void setDocumenti(Set<Documento> documenti) {
        this.documenti = documenti;
    }
    //
    public void addDocumentoToAzione(Documento documento){
        documento.setAzione(this);
        this.getDocumenti().add(documento);
    }
    //
    public void removeDocumentoFromAzione(Documento documento){
        this.getDocumenti().remove(documento);
    }
    
    // Pro Spring 3 pag 331
    public Set<Criticita> getCriticita() {
        return criticita;
    }
    //
    public void setCriticita(Set<Criticita> criticita) {
        this.criticita = criticita;
    }
    //
    public void addCriticitaToAzione(Criticita criticita){
        criticita.setAzione(this);
        this.getCriticita().add(criticita);
    }
    //
    public void removeCriticitaFromAzione(Criticita criticita){
        this.getCriticita().remove(criticita);
    }

	public int getAnno() {
		return this.obiettivo.getAnno();
	}

	public boolean isCritico() {
		List<Criticita> critic = new ArrayList<Criticita>(this.getCriticita());
		return (!critic.isEmpty());
	}
	
	public boolean isOiv() {
		List<OivAzione> listOiv = new ArrayList<OivAzione>(this.getOivAzione());
		return (!listOiv.isEmpty());
	}

	public int getPesoApicale() {
		if (this.pesoApicale >0) return pesoApicale;
    	else return this.peso;
	}

	public void setPesoApicale(int pesoApicale) {
		this.pesoApicale = pesoApicale;
	}

	public String getOrder() {
		/*
		String DATE_FORMAT_NOW = "yyyy-MM-dd";
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	    String stringDate =sdf.format(this.scadenza);
	    */
		String order= "";
		if (null!=this.scadenza) order += this.scadenza.toString() ;
	    order += this.denominazione;
		return order;
	}
	
	public String getOrderApicale() {
		/*
		String DATE_FORMAT_NOW = "yyyy-MM-dd";
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	    String stringDate =sdf.format(this.scadenza);
	    */
		String order= "";
		if (null!=this.scadenzaApicale) order += this.scadenza.toString() ;
	    order += this.denominazione;
		return order;
	}

	@Override
	public Azione clone()  {
    	Azione azione = new Azione();
    	azione.setDenominazione(this.denominazione);
    	azione.setDescrizione(this.descrizione);
    	azione.setDescrizioneProp("");
    	if (!note.isEmpty())azione.setNote(this.note);
		else azione.setNote("");
        azione.setProdotti(this.prodotti);
        azione.setIndicatore(this.indicatore);
        azione.setPeso(0);  
        azione.setRisultato("");
        azione.setPeso(this.peso);
        azione.setPesoApicale(this.pesoApicale);
        azione.setCompletamento(CompletamentoEnum.DA_VALUTARE);
        // aggiorno la scadenza
        // Cay Horstmann vol 1 pag 109
        GregorianCalendar calendar = new GregorianCalendar();
        if (this.scadenza != null){
	        calendar.setTime(this.scadenza); //  
	        calendar.add(Calendar.YEAR, 1);
	        Date newScadenza = calendar.getTime();
	        azione.setScadenza(newScadenza);
        }
        if (this.scadenzaApicale != null){
	        calendar.setTime(this.scadenzaApicale); //  
	        calendar.add(Calendar.YEAR, 1);
	        Date newScadenzaApicale = calendar.getTime();
	        azione.setScadenzaApicale(newScadenzaApicale);
        }
        //
    	return azione;
	}
	
	
	public Azione cloneStessoAnno()  {
    	Azione azione = new Azione();
    	azione.setDenominazione(this.denominazione);
    	azione.setDescrizione(this.descrizione);
    	azione.setDescrizioneProp("");
    	if (!note.isEmpty())azione.setNote(this.note);
		else azione.setNote("");
        azione.setProdotti(this.prodotti);
        azione.setIndicatore(this.indicatore);
        azione.setPeso(this.peso);  
        azione.setRisultato("");
        azione.setPesoApicale(this.getPesoApicale());
        azione.setScadenza(this.scadenza);
        azione.setScadenzaApicale(this.scadenzaApicale);
        //
    	return azione;
	}

	public int getIdIncarico() {
		return obiettivo.getIncaricoID();
	}

	public void setIdIncarico(int idIncarico) {
		this.idIncarico = idIncarico;
	}

	
	public int getIdIncaricoApicale() {
		return idIncaricoApicale;
	}

	public void setIdIncaricoApicale(int idIncaricoApicale) {
		this.idIncaricoApicale = idIncaricoApicale;
	}

	@Override
	public String toString() {
		return "Azione [idAzione=" + idAzione + ", denominazione="
				+ denominazione + ", descrizione=" + descrizione + "]";
	}
	
	public Integer getIncaricoID() {return this.getObiettivo().getIncaricoID();}

	public PersonaFisicaGeko getDirigente() {
		return dirigente;
	}

	public void setDirigente(PersonaFisicaGeko dirigente) {
		this.dirigente = dirigente;
	}
	
	public List<Command> getAllowedCommands() {
		List<Command> allowed = new ArrayList<Command>();
		//
		if (this.obiettivo.getTipo().equals(Obiettivo.TipoEnum.DIRIGENZIALE)){
		switch(this.obiettivo.getStatoApprov()) {		
		    case INTERLOCUTORIO:
		    	//
		    	allowed.add(new Command("MANAGER","MODIFICA"));
		    	allowed.add(new Command("MANAGER","ELIMINA"));
		    	allowed.add(new Command("MANAGER","RENDI TASSATIVA"));
		    	allowed.add(new Command("MANAGER","TOGLI TASSATIVA"));
		    	
		    	if (this.obiettivo.isApicale()){
		    		allowed.add(new Command("CONTROLLER","MODIFICA APICALE"));
			    	allowed.add(new Command("CONTROLLER","ELIMINA APICALE"));
			    	allowed.add(new Command("GABINETTO","MODIFICA APICALE"));
			    	allowed.add(new Command("GABINETTO","ELIMINA APICALE"));
		    	}
		    	else{
			    	allowed.add(new Command("CONTROLLER","MODIFICA"));
			    	allowed.add(new Command("CONTROLLER","ELIMINA"));
		    	}
		    	allowed.add(new Command("CONTROLLER","RENDI TASSATIVA"));
		    	allowed.add(new Command("CONTROLLER","TOGLI TASSATIVA"));
		        break;
		 
		    case PROPOSTO:
		    	
		        break;
		        
		    case    RICHIESTO:
		    	allowed.add(new Command("CONTROLLER","MODIFICA"));
		    	allowed.add(new Command("CONTROLLER","ELIMINA"));
		    	allowed.add(new Command("CONTROLLER","RENDI TASSATIVA"));
		    	allowed.add(new Command("CONTROLLER","TOGLI TASSATIVA"));
		        break;
		        
		    case    VALIDATO:
		    	
		        break;
		        
		    case    RESPINTO:
		    	
		        break;
		        
		    case    ANNULLATO:
		        //...
		        break;
		        
		    case    DEFINITIVO:
		    	
		    	
		        break;
		        
		    case    RIVISTO:
		    	allowed.add(new Command("MANAGER","MODIFICA"));
		    	allowed.add(new Command("MANAGER","ELIMINA"));
		    	allowed.add(new Command("MANAGER","RENDI TASSATIVA"));
		    	allowed.add(new Command("MANAGER","TOGLI TASSATIVA"));
		        break;  
		    
		    //default:
		        
		      
		    case    PROPOSTOGAB: // necessario per apicale
		    	// allowed.add(new Command("CONTROLLER","MODIFICA"));
		        break;
		        
		    case    RICHIESTOGAB:
		    	allowed.add(new Command("GABINETTO","MODIFICA APICALE"));
		    	allowed.add(new Command("GABINETTO","ELIMINA APICALE"));
		    	allowed.add(new Command("GABINETTO","RENDI TASSATIVA"));
		    	allowed.add(new Command("GABINETTO","TOGLI TASSATIVA"));
		        break;
		        
		    case    CONCORDATO: // necessario per apicale
		        //...
		        break;
		        
			} 			
		} // fine if
		
		if (this.obiettivo.getTipo().equals(Obiettivo.TipoEnum.POS_ORGAN)){
			switch(this.obiettivo.getStatoApprov()) {		
			    case INTERLOCUTORIO:
			    	//
			    	allowed.add(new Command("RESP_POP","MODIFICA"));
			    	allowed.add(new Command("RESP_POP","ELIMINA"));		
			    	allowed.add(new Command("MANAGER","MODIFICA"));
			    	allowed.add(new Command("MANAGER","ELIMINA"));	
			        break;
			        
			    case PROPOSTO:			    	
			        break;
			        
			    case RICHIESTO:	
			    	allowed.add(new Command("MANAGER","MODIFICA"));
			    	allowed.add(new Command("MANAGER","ELIMINA"));	
			        break;
			        
			    case    VALIDATO:			    	
			        break;
			        
			    case    RESPINTO:			    	
			        break;
			        
			    case    ANNULLATO:
			        //...
			        break;
			        
			    case    DEFINITIVO:   				    	
			        break;		
				} 			
			} // fine if
			
		if (this.obiettivo.getTipo().equals(Obiettivo.TipoEnum.STRUTTURA)){
			switch(this.obiettivo.getStatoApprov()) {		
			    case INTERLOCUTORIO:
			    	//
			    	allowed.add(new Command("MANAGER","MODIFICA"));
			    	allowed.add(new Command("MANAGER","ELIMINA"));				    	
			        break;			        
			}
		}
		if (this.obiettivo.getTipo().equals(Obiettivo.TipoEnum.COMPARTO)){
			switch(this.obiettivo.getStatoApprov()) {		
			    case INTERLOCUTORIO:
			    	//
			    	allowed.add(new Command("MANAGER","MODIFICA"));
			    	allowed.add(new Command("MANAGER","ELIMINA"));				    	
			        break;			        
			}
		}
		return allowed;
	}

	public Set<OivAzione> getOivAzione() {
		return oivAzione;
	}

	public void setOivazione(Set<OivAzione> oivAzione) {
		this.oivAzione = oivAzione;
	}

	public List<Command> getGuiCommands() {
		return guiCommands;
	}

	public void setGuiCommands(List<Command> guiCommands) {
		this.guiCommands = guiCommands;
	}

	public int getIncaricoPadreID() {
		return this.incaricoPadreID;
	}

	public void setIncaricoPadreID(int incaricoPadreID) {
		this.incaricoPadreID = incaricoPadreID;
	}

	
	
} // ------------------------------------------------------------------


