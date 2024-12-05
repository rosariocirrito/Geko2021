package it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione;

import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.*;


@Entity
@Table(name="obiettivo_assegnazione")
public class ObiettivoAssegnazione {
	
	
	
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    private Integer id;
   
    private int idObiettivo;    
    private int idPersona;
    private int idStruttura;
    private int anno;
    private int peso;    
    private String note;
    
    
    transient PersonaFisicaGeko opPersonaFisica;
    transient PersonaGiuridicaGeko opPersonaGiuridica;
    transient Obiettivo obiettivo;
    
    transient private int idDip;    
    transient private double punteggio;    
	transient private String nomeDipendente;
	transient private boolean nuovo;
	transient private int idIncarico; 
    //
    
    // ------------------------------------------------------------------------
    
    
    

    

    public ObiettivoAssegnazione() {    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    

    public int getIdObiettivo() {
		return idObiettivo;
	}
	public void setIdObiettivo(int idObiettivo) {
		this.idObiettivo = idObiettivo;
	}
	
	public int getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}
	public int getIdStruttura() {
		return idStruttura;
	}
	public void setIdStruttura(int idStruttura) {
		this.idStruttura = idStruttura;
	}
	public int getPeso() {
    	return peso;
    }
    public void setPeso(int peso) {this.peso = peso;}
    
    
    
    public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}
	
	public int getIdDip() {
		return idDip;
	}
	public void setIdDip(int idDip) {
		this.idDip = idDip;
	}
	public boolean isNuovo() {
        return (this.id == null);
    }
	
	/*
	public double getPunteggio() {
		double valutFactor=0;
        switch (valutazione)
        {
	        case OTTIMO:
		        valutFactor= 1;
		        break;
	        case BUONO:
		        valutFactor= 0.7;
		        break;
	        case SUFFICIENTE:
		        valutFactor= 0.5;
		        break;
	        case INSUFFICIENTE:
		        valutFactor= 0.2;
		        break;
		        
        }
        punteggio = valutFactor*this.getPeso(); 
        return punteggio;
    }
*/
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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
		ObiettivoAssegnazione other = (ObiettivoAssegnazione) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	public ObiettivoAssegnazione cloneStessoAnno()  {
		
    	ObiettivoAssegnazione assegn = new ObiettivoAssegnazione();
    	/*
    	 	private int idObiettivo;    
    		private int idPersona;
    		private int idStruttura;
    		private int anno;
    		private int peso;    
    		private String note;
    	 */
    	assegn.setIdPersona(this.idPersona);
    	assegn.setIdStruttura(this.idStruttura); // sarà riscritta con la nuova
    	assegn.setAnno(this.anno);
    	assegn.setPeso(this.peso);
    	assegn.setNote(this.note);    
    	//
    	return assegn;
    	
	}
    
		
    
    
	
	
	
	public PersonaFisicaGeko getOpPersonaFisica() {
		return opPersonaFisica;
	}
	@Override
	public String toString() {
		return "ObiettivoAssegnazione [id=" + id + ", idObiettivo=" + idObiettivo + ", idPersona=" + idPersona
				+ ", idStruttura=" + idStruttura + ", anno=" + anno + ", peso=" + peso + ", note=" + note + "]";
	}
	public void setOpPersonaFisica(PersonaFisicaGeko opPersonaFisica) {
		this.opPersonaFisica = opPersonaFisica;
	}

	public int getIdIncarico() {
		return idIncarico;
	}

	public void setIdIncarico(int idIncarico) {
		this.idIncarico = idIncarico;
	}

	public Obiettivo getObiettivo() {
		return obiettivo;
	}

	public void setObiettivo(Obiettivo obiettivo) {
		this.obiettivo = obiettivo;
	}
	
	
	
	
}
