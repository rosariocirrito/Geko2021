package it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi;


import javax.persistence.*;

import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategico;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;


/**
 * The persistent class for the op_persona_afferenza database table.
 * 
 */
@Entity
@Table(name="associazObiettivi")
public class AssociazObiettivi  {
	
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
    @JoinColumn(name="idStrategico")
    private ObiettivoStrategico strategico;
    
    //bi-directional many-to-one association
    //@ManyToOne(fetch=FetchType.EAGER)
    //@JoinColumn(name="idProgramma")
    //private Programma programma;

    transient private int idObiettivoStrategico;
    transient private int idObiettivoApicale;
    transient private String strObiettivoStrategico;
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

	public ObiettivoStrategico getStrategico() {
		return strategico;
	}

	public void setStrategico(ObiettivoStrategico strategico) {
		this.strategico = strategico;
	}

	public int getIdObiettivoStrategico() {
		return idObiettivoStrategico;
	}

	public void setIdObiettivoStrategico(int idObiettivoStrategico) {
		this.idObiettivoStrategico = idObiettivoStrategico;
	}

	
	/*
	public Programma getProgramma() {
		return programma;
	}

	public void setProgramma(Programma programma) {
		this.programma = programma;
	}
*/
	public int getIdObiettivoApicale() {
		return idObiettivoApicale;
	}

	public void setIdObiettivoApicale(int idObiettivoApicale) {
		this.idObiettivoApicale = idObiettivoApicale;
	}

	
	public String getStrObiettivoStrategico() {
		return strObiettivoStrategico;
	}

	public void setStrObiettivoStrategico(String strObiettivoStrategico) {
		this.strObiettivoStrategico = strObiettivoStrategico;
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
		AssociazObiettivi other = (AssociazObiettivi) obj;
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
