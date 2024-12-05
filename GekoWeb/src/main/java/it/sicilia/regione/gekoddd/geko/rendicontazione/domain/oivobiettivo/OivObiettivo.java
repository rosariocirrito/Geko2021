package it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivobiettivo;


import javax.persistence.*;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;

@Entity
@Table(name="oivobiettivo")
public class OivObiettivo {
    
    @Id
    @GeneratedValue
    private Integer id;
    //
    private String memo;
    private String proposte;
    private String valutazioni;
    //
    @ManyToOne
    @JoinColumn(name="idObiettivo")
    private Obiettivo obiettivo;
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
    
    
    
    //

    public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	

	public String getProposte() {
		return proposte;
	}

	public void setProposte(String proposte) {
		this.proposte = proposte;
	}

	public String getValutazioni() {
		return valutazioni;
	}

	public void setValutazioni(String valutazioni) {
		this.valutazioni = valutazioni;
	}

	

	public Obiettivo getObiettivo() {
		return obiettivo;
	}

	public void setObiettivo(Obiettivo obiettivo) {
		this.obiettivo = obiettivo;
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
		OivObiettivo other = (OivObiettivo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
  
	public Integer getIncaricoID() {return this.getObiettivo().getIncaricoID();}
  
} // -----------------------------------------------------