package it.sicilia.regione.gekoddd.geko.valutazione.domain.criticValut;



import javax.persistence.*;

import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione;

@Entity
@Table(name="criticvalut")
public class CriticValut {
    
    @Id
    @GeneratedValue
    private Integer id;
    //
    private String descrizione;
    private String proposte;
    private String indicazioni;
    //
    @ManyToOne
    @JoinColumn(name="idValutazione")
    private Valutazione valutazione;
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

    public Valutazione getValutazione() {
        return valutazione;
    }

    public void setValutazione(Valutazione valutazione) {
        this.valutazione = valutazione;
    }
  
    public Integer getIncaricoID() {return this.valutazione.getIncaricoID();}
  
} // -----------------------------------------------------