package it.sicilia.regione.gekoddd.geko.rendicontazione.domain.criticita;


import javax.persistence.*;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;

@Entity
@Table(name="criticita")
public class Criticita {
    
    @Id
    @GeneratedValue
    private Integer id;
    //
    private String descrizione;
    private String proposte;
    private String indicazioni;
    //
    @ManyToOne
    @JoinColumn(name="idAzione")
    private Azione azione;
    //
    transient private boolean nuovo;
    
    // ----------------------------------
    
    public boolean isNuovo() {
	return (this.id == null);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getIndicazioni() {
        return indicazioni;
    }

    public void setIndicazioni(String indicazioni) {
        this.indicazioni = indicazioni;
    }

    public String getProposte() {
        return proposte;
    }

    public void setProposte(String proposte) {
        this.proposte = proposte;
    }
    
    //

    public Azione getAzione() {
        return azione;
    }

    public void setAzione(Azione azione) {
        this.azione = azione;
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
		Criticita other = (Criticita) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
  
	public Integer getIncaricoID() {return this.getAzione().getObiettivo().getIncaricoID();}
  
} // -----------------------------------------------------