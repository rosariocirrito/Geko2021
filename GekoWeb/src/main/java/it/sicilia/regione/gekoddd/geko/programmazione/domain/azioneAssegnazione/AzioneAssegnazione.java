package it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione;

import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.*;


@Entity
@Table(name="azione_assegnazione")
public class AzioneAssegnazione {
	
	
	
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    private Integer id;

    /*
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="DATA_INIZIO")
    private Date dataInizio;

    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="DATA_FINE")
    private Date dataFine;
    */
    
    @Column(name="PESO")
    private int peso;

    
    
    //bi-directional many-to-one association to Azione
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ID_AZIONE")
    private Azione azione;

    //bi-directional many-to-one association to OpPersonaFisica
    //@ManyToOne(fetch=FetchType.EAGER)
    //@JoinColumn(name="ID_PERSONA")
    
    @Column(name="ID_PERSONA")
    private Integer pfID;
    
    transient PersonaFisicaGeko opPersonaFisica;
    
    transient private int idDip;
    transient private int anno;
    
    public enum ValutazioneEnum{
        ALTO,
        MEDIO,
        BASSO,
        NULLO,
        DA_VALUTARE
    }
    
    @Enumerated
    private ValutazioneEnum valutazione;
    
    transient private IncaricoGeko incarico;
    transient private double punteggio;
    
    private String annotazioni;
    
    
    //
    transient private int annoinz;
    transient private int annofin;
	
	transient private String nomeDipendente;
	
    //
	/*
    public int getAnnoinz() {
    	if (null!=this.dataInizio) return this.dataInizio.getYear();
    	else return 2000;
	}
	public int getAnnofin() {
		if (null!=this.dataFine) return this.dataFine.getYear();
    	else return 2000;
	}
    //
    */
    
    
    // ------------------------------------------------------------------------
    
    
    transient private boolean nuovo;

    public int getIdDip() {
		return idDip;
	}
	public void setIdDip(int idDip) {
		this.idDip = idDip;
	}
	public boolean isNuovo() {
        return (this.id == null);
    }

    public AzioneAssegnazione() {    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    
    /*
    public Date getDataInizio() {return this.dataInizio;}
    public void setDataInizio(Date dataInizio) {this.dataInizio = dataInizio;}

    public Date getDataFine() {return this.dataFine;}
    public void setDataFine(Date dataFine) {this.dataFine = dataFine;}
    */
    

    public Integer getPfID() {
		return pfID;
	}
	public void setPfID(Integer pfID) {
		this.pfID = pfID;
	}
	public int getPeso() {
    	 return peso;    	
    	}
    
    public void setPeso(int peso) {this.peso = peso;}
    
    
    
    public Azione getAzione() {return azione;}
    public void setAzione(Azione azione) {this.azione = azione;}
    
    
    public int getAnno() {
		return this.azione.getAnno();
	}


	public ValutazioneEnum getValutazione() {
		return valutazione;
	}

	public void setValutazione(ValutazioneEnum valutazione) {
		this.valutazione = valutazione;
	}
	
	public double getPunteggio() {
		double valutFactor=0;
        switch (valutazione)
        {
	        case ALTO:
		        valutFactor= 1;
		        break;
	        case MEDIO:
		        valutFactor= 0.7;
		        break;
	        case BASSO:
		        valutFactor= 0.5;
		        break;
	        case NULLO:
		        valutFactor= 0.0;
		        break;
	        case DA_VALUTARE:
		        valutFactor= 0.0;
		        break;
		        
        }
        punteggio = valutFactor*this.getPeso(); 
        return punteggio;
    }

	
	public String getAnnotazioni() {
		return annotazioni;
	}

	public void setAnnotazioni(String annotazioni) {
		this.annotazioni = annotazioni;
	}

	
	@Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AzioneAssegnazione other = (AzioneAssegnazione) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
	
	
    @Override
	public AzioneAssegnazione clone()  {
    	AzioneAssegnazione assegn = new AzioneAssegnazione();
    	assegn.setPfID(this.pfID);
    	assegn.setPeso(peso);
    	
    	// Cay Horstmann vol 1 pag 109
    	/*
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(this.dataInizio); //  
        calendar.add(Calendar.YEAR, 1);
        Date newDatainizio = calendar.getTime();
    	assegn.setDataInizio(newDatainizio);
    	//
    	calendar.setTime(this.dataFine); //  
        calendar.add(Calendar.YEAR, 1);
        Date newDataFine = calendar.getTime();
    	assegn.setDataFine(newDataFine);
    	*/
    	//
    	return assegn;
	}
    
    public AzioneAssegnazione cloneStessoAnno()  {
    	AzioneAssegnazione assegn = new AzioneAssegnazione();
    	assegn.setPfID(this.pfID);
    	assegn.setPeso(this.peso);
    	//assegn.setDataInizio(this.dataInizio);
    	//
    	//assegn.setDataFine(this.dataFine);
    	//
    	return assegn;
	}
    
	
	@Override
	public String toString() {
		return "AzioneAssegnazione [azione=" + azione + ", pfID="
				+ this.pfID + "]";
	}
	
    
	public Integer getIncaricoID() {return this.getAzione().getIncaricoID();}
	
	public PersonaFisicaGeko getOpPersonaFisica() {
		return opPersonaFisica;
	}
	public void setOpPersonaFisica(PersonaFisicaGeko opPersonaFisica) {
		this.opPersonaFisica = opPersonaFisica;
	}
	
	
	
	
}
