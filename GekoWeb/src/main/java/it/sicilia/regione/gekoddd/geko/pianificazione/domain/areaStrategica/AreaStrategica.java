package it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategico;

/**
 *
 * @author Cirrito
 */
@Entity
@Table(name="areastrategica")
public class AreaStrategica {
    @Id
    @GeneratedValue
    private Integer id;

    transient private int anno;
    private int annoInizio;
    private int annoFine;
    private String codice;
    private String descrizione;
    //private String codiceAss;
    
   
    
    //@OneToMany(mappedBy = "areaStrategica",fetch = FetchType.EAGER)
    //private Set<ObiettivoStrategico> obiettiviStrategici = new HashSet<ObiettivoStrategico>();
    
    // da popolare in base all'anno
    transient private List<ObiettivoStrategico> obiettiviStrategici = new ArrayList<ObiettivoStrategico>();
    
    //private transient String order;
    
    //       
    public AreaStrategica() {}   
        
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
    
    

	
/*
	public String getCodiceAss() {
		return codiceAss;
	}

	public void setCodiceAss(String codiceAss) {
		this.codiceAss = codiceAss;
	}
	*/

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
		AreaStrategica other = (AreaStrategica) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
   
    public int getAnno() {
        return anno;
    }

    public List<ObiettivoStrategico> getObiettiviStrategici() {
		return obiettiviStrategici;
	}

	public void setObiettiviStrategici(List<ObiettivoStrategico> obiettiviStrategici) {
		this.obiettiviStrategici = obiettiviStrategici;
	}

	public void setAnno(int anno) {
        this.anno = anno;
    }
    
    

	public int getAnnoInizio() {
		return annoInizio;
	}

	public void setAnnoInizio(int annoInizio) {
		this.annoInizio = annoInizio;
	}

	public int getAnnoFine() {
		return annoFine;
	}

	public void setAnnoFine(int annoFine) {
		this.annoFine = annoFine;
	}

	@Override
	public String toString() {
		if(this.descrizione.length()<32) return this.codice+ "-"+this.descrizione+"/";
		else return this.codice+ "-"+this.descrizione.substring(0, 32)+".../";
	}

    
    
	
    
} ///
