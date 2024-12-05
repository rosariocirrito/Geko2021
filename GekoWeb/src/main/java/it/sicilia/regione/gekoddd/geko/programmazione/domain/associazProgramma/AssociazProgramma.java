package it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma;


import javax.persistence.*;

import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategico;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.programma.Programma;


/**
 * The persistent class for the op_persona_afferenza database table.
 * 
 */
@Entity
@Table(name="associazProgramma")
public class AssociazProgramma  {
	
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
    @JoinColumn(name="idProgramma")
    private Programma programma;
    
    transient int idProgrammaScelto;

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

	public Programma getProgramma() {
		return programma;
	}

	public void setProgramma(Programma programma) {
		this.programma = programma;
	}

	
	public int getIdProgrammaScelto() {
		return idProgrammaScelto;
	}

	public void setIdProgrammaScelto(int idProgrammaScelto) {
		this.idProgrammaScelto = idProgrammaScelto;
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
		AssociazProgramma other = (AssociazProgramma) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
    
      
    
	
    
} // fine classe
