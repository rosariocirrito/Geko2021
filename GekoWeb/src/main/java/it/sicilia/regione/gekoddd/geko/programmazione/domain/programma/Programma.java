package it.sicilia.regione.gekoddd.geko.programmazione.domain.programma;

import java.util.*;

import javax.persistence.*;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.missione.Missione;

@Entity
@Table(name="programma")
    public class Programma {
    @Id
    @GeneratedValue
    private Integer id;
    private String codice;
    private String descrizione;
    
    @ManyToOne
    @JoinColumn(name="idMissione")
    private Missione missione;
    
    // su Spring 3 pag 331
    //@OneToMany(mappedBy = "obiettivo", cascade=CascadeType.ALL)
    // occorre forse definire il Cascade su MySql
    // comunque mi da errore su orphanRemoval=true
    // impostare il metodo addAzioneToObiettivo()
    
   
    

    
    public Programma() {}

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

    

    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
    
    // Pro Spring 3 pag 331
   

	public Missione getMissione() {
		return missione;
	}

	public void setMissione(Missione missione) {
		this.missione = missione;
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
		Programma other = (Programma) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

// ----------------------------------------------------------------------------
    
    
    
    public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	

	@Override
	public String toString() {
		return "Programma [descrizione=" + descrizione + "]";
	}
    
    // vedi Spring Persistence with Hibernate Beginning p.72 Kindle
    // � un metodo conveniente per le associazioni bidirezionali
    // perch� i riferimenti sono settati su entrambi i lati dell'associazione
    
    // suggerito anche da ProSpring 3 pag.331
    
    
    
    // ---------------------------------------------------------------------------------
    
    
    
} // ------------------------------------------------------------------


