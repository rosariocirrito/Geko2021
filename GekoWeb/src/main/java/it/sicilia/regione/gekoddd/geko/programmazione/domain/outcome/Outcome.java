package it.sicilia.regione.gekoddd.geko.programmazione.domain.outcome;

import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategico;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.command.Command;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo.TipoEnum;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.criticita.Criticita;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.Documento;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivazione.OivAzione;

import java.util.*;

import javax.persistence.*;



/**
 *
 * @author Cirrito
 */
@Entity
@Table(name="outcome")
    public class Outcome {
    @Id
    @GeneratedValue
    private Integer id;
    private String descrizione;
    private String outinz;
    private String outfin;
    private String fonte;
    
    @ManyToOne
    @JoinColumn(name="idObjStrat")
    private ObiettivoStrategico obiettivoStrategico;
    
    // su Spring 3 pag 331
    //@OneToMany(mappedBy = "obiettivo", cascade=CascadeType.ALL)
    // occorre forse definire il Cascade su MySql
    // comunque mi da errore su orphanRemoval=true
    // impostare il metodo addAzioneToObiettivo()
    
    
    transient private int anno;
    
    
    
    public Outcome() {}

    
    
    public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
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

    
   
    
    public String getOutinz() {
		return outinz;
	}



	public void setOutinz(String outinz) {
		this.outinz = outinz;
	}



	public String getOutfin() {
		return outfin;
	}



	public void setOutfin(String outfin) {
		this.outfin = outfin;
	}



	public String getFonte() {
		return fonte;
	}



	public void setFonte(String fonte) {
		this.fonte = fonte;
	}

	public ObiettivoStrategico getObiettivoStrategico() {
		return obiettivoStrategico;
	}



	public void setObiettivoStrategico(ObiettivoStrategico obiettivoStrategico) {
		this.obiettivoStrategico = obiettivoStrategico;
	}

	@Override
    public boolean equals(Object o){
	boolean check = false;
	if(o instanceof Outcome){
            if(((Outcome)o).id == null ){
            if(id == null)
                check = true;
            }else if(((Outcome)o).id.equals(id))
		check = true;
            }
            return check;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }


	public int getAnno() {
		return this.obiettivoStrategico.getAnno();
	}



	@Override
	public String toString() {
		return "Outcome [descrizione=" + descrizione + "]";
	}

	

	
	
} // ------------------------------------------------------------------


