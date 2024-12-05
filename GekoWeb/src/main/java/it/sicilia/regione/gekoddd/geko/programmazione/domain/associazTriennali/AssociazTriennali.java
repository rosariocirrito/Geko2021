package it.sicilia.regione.gekoddd.geko.programmazione.domain.associazTriennali;


import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoPluriennale.ObiettivoPluriennale;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.*;
import javax.persistence.*;

import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategico;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;


/**
 * The persistent class for the op_persona_afferenza database table.
 * 
 */
@Entity
@Table(name="associazTriennali")
public class AssociazTriennali  {
	
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    private Integer id;

    //bi-directional many-to-one association
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="idApicale")
    private Obiettivo apicale;
    
    
    //bi-directional many-to-one association
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="idTriennale")
    private ObiettivoPluriennale triennale;
    
    //bi-directional many-to-one association
    //@ManyToOne(fetch=FetchType.EAGER)
    //@JoinColumn(name="idProgramma")
    //private Programma programma;

    transient private int idTriennale;
    transient private int idApicale;
    transient private String strTriennale;
    transient private String responsabile;
    
    // ------------------------------------------------------------------------
	
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public Obiettivo getApicale() {
        return apicale;
    }

    public void setApicale(Obiettivo apicale) {
        this.apicale = apicale;
    }

    public ObiettivoPluriennale getTriennale() {
        return triennale;
    }

    public void setTriennale(ObiettivoPluriennale triennale) {
        this.triennale = triennale;
    }

    public int getIdTriennale() {
        return idTriennale;
    }

    public void setIdTriennale(int idTriennale) {
        this.idTriennale = idTriennale;
    }

    public int getIdApicale() {
        return idApicale;
    }

    public void setIdApicale(int idApicale) {
        this.idApicale = idApicale;
    }

    public String getStrTriennale() {
        return strTriennale;
    }

    public void setStrTriennale(String strTriennale) {
        this.strTriennale = strTriennale;
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
		AssociazTriennali other = (AssociazTriennali) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getResponsabile() {
		return getApicale().getResponsabile();
	}

	public void setResponsabile(String responsabile) {
		this.responsabile = responsabile;
	}
    
   
    
	
	
}
