package it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoPluriennale;

import java.util.*;

import javax.persistence.*;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategica;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.command.Command;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.outcome.Outcome;


/**
 *
 * @author Cirrito
 */
@Entity
@Table(name="obiettivoPluriennale")
    public class ObiettivoPluriennale {
    @Id
    @GeneratedValue
    private Integer id;
    private String descrizione;
    private String note;
    private int annoInz;
    private int annoFin;
    @Column(name="idIncarico")
    private Integer incaricoID;
    
    transient private List<Command> allowedCommands;
    transient private List<Command> guiCommands;
   
    /*
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
    */
   
    transient private String order;
    
    //
    public ObiettivoPluriennale() {}

    public boolean isNuovo() {
	return (this.id == null);
    }
    
    // bi-directional many-to-one association 
    // many Azione to one Obiettivo 
    // owner side lato diretto
    
    
    public String getDescrizione() {
        return descrizione;
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

	  

	
	public int getAnnoInz() {
		return annoInz;
	}

	public void setAnnoInz(int annoInz) {
		this.annoInz = annoInz;
	}

	public int getAnnoFin() {
		return annoFin;
	}

	public void setAnnoFin(int annoFin) {
		this.annoFin = annoFin;
	}
	

	public Integer getIncaricoID() {
		return incaricoID;
	}

	public void setIncaricoID(Integer incaricoID) {
		this.incaricoID = incaricoID;
	}

	/*

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
*/
	@Override
	public String toString() {
		if(this.descrizione.length()<64) return this.descrizione;
		else return this.descrizione.substring(0, 64)+"...";
	}

	public String getOrder() {
		return this.toString();
	}

	
    public List<Command> getAllowedCommands() {
        List<Command> allowed = new ArrayList<Command>();	
	allowed.add(new Command("CONTROLLER","MODIFICA"));
        return allowed;
    }
    
    public List<Command> getGuiCommands() {
	return guiCommands;
    }
    
    public void setGuiCommands(List<Command> guiCommands) {
            this.guiCommands = guiCommands;
    }
    
} // ------------------------------------------------------------------


