package it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico;

import java.util.*;

import javax.persistence.*;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategica;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.outcome.Outcome;


/**
 *
 * @author Cirrito
 */
@Entity
@Table(name="obiettivostrategico")
    public class ObiettivoStrategico {
    @Id
    @GeneratedValue
    private Integer id;
    private String codice;
    private String descrizione;
    private String note;
    private int anno;
    
   
    
    @ManyToOne
    @JoinColumn(name="idArea")
    private AreaStrategica areaStrategica;
    
    // su Spring 3 pag 331
    //@OneToMany(mappedBy = "obiettivo", cascade=CascadeType.ALL)
    // occorre forse definire il Cascade su MySql
    // comunque mi da errore su orphanRemoval=true
    // impostare il metodo addAzioneToObiettivo()
    
    @OneToMany(mappedBy = "strategico",fetch = FetchType.LAZY)
    private Set<AssociazObiettivi> associazObiettivi = new HashSet<AssociazObiettivi>();
    
    @OneToMany(mappedBy = "obiettivoStrategico",fetch = FetchType.LAZY)
    private Set<Outcome> outcomes = new HashSet<Outcome>();
    
   
    transient private String order;
    
    //
    public ObiettivoStrategico() {}

    public boolean isNuovo() {
	return (this.id == null);
    }
    
    // bi-directional many-to-one association 
    // many Azione to one Obiettivo 
    // owner side lato diretto
    
    
    public String getDescrizione() {
        return descrizione;
    }

    public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    
    public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	  

	public AreaStrategica getAreaStrategica() {
		return areaStrategica;
	}

	public void setAreaStrategica(AreaStrategica areaStrategica) {
		this.areaStrategica = areaStrategica;
	}

	

	

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ObiettivoStrategico other = (ObiettivoStrategico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

	public Set<AssociazObiettivi> getAssociazObiettiviInternal() {
		return associazObiettivi;
	}

	public void setAssociazObiettivi(Set<AssociazObiettivi> associazObiettivi) {
		this.associazObiettivi = associazObiettivi;
	}

	public List<AssociazObiettivi> getAssociazObiettivi() {
        List<AssociazObiettivi> sortedAssociazioni = new ArrayList<AssociazObiettivi>(this.getAssociazObiettiviInternal());
        PropertyComparator.sort(sortedAssociazioni, new MutableSortDefinition("responsabile",true,true));
        return sortedAssociazioni;
    }

	public List<AssociazObiettivi> getAssociazObiettiviApicali() {
        List<AssociazObiettivi> associazioniTotali = new ArrayList<AssociazObiettivi>(this.getAssociazObiettiviInternal());
        List<AssociazObiettivi> sortedAssociazioni = new ArrayList<AssociazObiettivi>();
        for (AssociazObiettivi assoc : associazioniTotali){
        	if(assoc.getApicale().isApicale()){
        		sortedAssociazioni.add(assoc);
        	}
        }
        PropertyComparator.sort(sortedAssociazioni, new MutableSortDefinition("responsabile",true,true));
        return sortedAssociazioni;
    }

	@Override
	public String toString() {
		if(this.descrizione.length()<64) return this.descrizione;
		else return this.descrizione.substring(0, 64)+"...";
	}

	public String getOrder() {
		return this.toString();
	}

	protected Set<Outcome> getOutcomesInternal() {
        return outcomes;
    }
	public List<Outcome> getOutcomes() {
        List<Outcome> sortedOutcomes = new ArrayList<Outcome>(this.getOutcomesInternal());
        PropertyComparator.sort(sortedOutcomes, new MutableSortDefinition("descrizione",true,true));
        return sortedOutcomes;
    }


	@Override
	public ObiettivoStrategico clone()  {
		/*
		 private Integer id;
    	private String codice;
    	private String descrizione;
    	private String note;
    	private int anno;
    	@JoinColumn(name="idArea")
		 */
		ObiettivoStrategico obiettivo = new ObiettivoStrategico();
		obiettivo.setCodice(this.codice);
		obiettivo.setDescrizione(this.descrizione);    		
		if (!note.isEmpty())obiettivo.setNote(this.note);
		else obiettivo.setNote("");
		obiettivo.setAnno(this.anno+1);         
        obiettivo.setAreaStrategica(this.areaStrategica);
		return obiettivo;
	}
	
    
} // ------------------------------------------------------------------


