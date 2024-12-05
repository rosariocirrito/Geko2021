package it.sicilia.regione.gekoddd.geko.programmazione.domain.missione;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.programma.Programma;

/**
 *
 * @author Cirrito
 */
@Entity
@Table(name="missione")
public class Missione {
    @Id
    @GeneratedValue
    private Integer id;

    private String codice;
    private String descrizione;
    
    
    // bi-directional one-to-many association 
    // many PrioritaPolitica to one Obiettivo 
    // owner side lato inverso
    
    // su Spring 3 pag 331
    //@OneToMany(mappedBy = "obiettivo", cascade=CascadeType.ALL)
    // occorre forse definire il Cascade su MySql
    // comunque mi da errore su orphanRemoval=true
    // impostare il metodo addAzioneToObiettivo()
    
    @OneToMany(mappedBy = "missione",fetch = FetchType.LAZY)
    private Set<Programma> programmi = new HashSet<Programma>();
    
    
    
    
    
    //private transient String order;
    
    //       
    public Missione() {}   
        
    public boolean isNuovo() {
	return (this.id == null);
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
    
    

	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
		Missione other = (Missione) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	protected Set<Programma> getProgrammiInternal() {
        return programmi;
    }
    
    public List<Programma> getProgrammi() {
        List<Programma> sortedProgrammi = new ArrayList<Programma>(this.getProgrammiInternal());
        PropertyComparator.sort(sortedProgrammi, new MutableSortDefinition("id",true,true));
        return sortedProgrammi;
    }
    

    public void setProgrammi(Set<Programma> programmi) {
        this.programmi = programmi;
    }
    
    

	@Override
	public String toString() {
		if(this.descrizione.length()<32) return this.codice+ "-"+this.descrizione+"/";
		else return this.codice+ "-"+this.descrizione.substring(0, 32)+".../";
	}

    
    
	
    
} ///
