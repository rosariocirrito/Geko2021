package it.sicilia.regione.gekoddd.geko.acl.model;

import it.sicilia.regione.gekoddd.geko.acl.dto.PersonaFisicaGekoDTO;
import it.sicilia.regione.gekoddd.geko.acl.dto.PersonaFisicaGekoDTO.OpPersonaFisicaStatoEnum;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione.ObiettivoAssegnazione;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneComparto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 
 */

public class PersonaFisicaGeko {
	public static final long serialVersionUID = 1L;
	
	public final Integer idPersona;
	public final String cognome;
	public final String nome;
	public final String stringa;
	public final String matricola;
	public final String categoria;
    public String getCategoria() {
		return categoria;
	}

	@Enumerated
    public final OpPersonaFisicaStatoEnum stato;
    public final Integer pfTipoID;
	public final Integer pgID;
	public final boolean dirigente;
	public final boolean dirigenteApicale;
	
	
	private List<AzioneAssegnazione> assegnazioni = new ArrayList<AzioneAssegnazione>();
	private List<ObiettivoAssegnazione> obiettivoAssegnazioni = new ArrayList<ObiettivoAssegnazione>();
	 
	private List<ValutazioneComparto> valutazioni = new ArrayList<ValutazioneComparto>();
	
	private Integer incaricoValutazioneID;
	private int anno;
	
	private int nrAssegnazioniAnno;
	
	private int pesoAzioniOK;
	
	private Log log = LogFactory.getLog(PersonaFisicaGeko.class);
	
	private boolean catAB;
    
    // ------------------------ metodi ------------
	public PersonaFisicaGeko(PersonaFisicaGekoDTO pf) {
		this.idPersona = pf.getIdPersona();
		this.cognome = pf.getCognome();
		this.nome = pf.getNome();
		this.stringa = pf.getStringa();
		this.matricola = pf.getMatricola();
		this.categoria = pf.getCategoria();
		this.stato = pf.getStato();
		this.pfTipoID = pf.getPfTipoID();
		this.pgID = pf.getPgID();
		this.dirigente = pf.isDirigente();
		//log.info("dirigente? = "+pf.isDirigente());
		this.dirigenteApicale = pf.isDirigenteApicale();		
	}

	// ------------------------ metodi ------------
	
	public String getCognome() {return cognome;}
	public String getNome() {return nome;}
	public String getStringa() {return stringa;}
	

	public Integer getIdPersona() {
		return idPersona;
	}

	public Integer getPgID() {return pgID;}

	public String getOrder(){
        //return this.cognome + " " + this.nome + " " + this.opPersonaFisicaTipo.getDescrizione() ;
    	return this.getCognomeNome();
    }
	

    public String getCognomeNome(){return this.cognome + " " + this.nome + " " ;}

	public boolean isDirigente() {return dirigente;}
	public boolean isDirigenteApicale() {return dirigenteApicale;}
    
	 public List<AzioneAssegnazione> getAssegnazioni() {
	        return assegnazioni;
	    }

	    public void setAssegnazioni(List<AzioneAssegnazione> assegnazioni) {
	        this.assegnazioni = assegnazioni;
	    }
	    
	    public void addAssegnazioneToPersonaFisica(AzioneAssegnazione assegnazione){
	        assegnazione.setPfID(this.idPersona);
	        this.getAssegnazioni().add(assegnazione);
	    }
	    
	    public void removeAssegnazioneFromPersonaFisica(AzioneAssegnazione assegnazione){
	        this.getAssegnazioni().remove(assegnazione);
	    }
	    
	    public int getPesoAssegnazioni() {
	    	//log.info("getPesoAssegnazioni() "+" anno:"+anno);
			int peso = 0;
			for (AzioneAssegnazione aa : this.getAssegnazioni()){
				peso += aa.getPeso();
			}
			return peso;
		}
	    
	    public int getPesoObiettivoAssegnazioni() {
	    	//log.info("getPesoAssegnazioni() "+" anno:"+anno);
			int peso = 0;
			for (ObiettivoAssegnazione aa : this.getObiettivoAssegnazioni()){
				peso += aa.getPeso();
			}
			return peso;
		}
	    
	    /*
	    public int getPesoObiettiviOK() {
	    	
	    	int peso=0;
	    	if (this.categoria.equals("A")){
	    		peso=40;
	    	}
	    	if (this.categoria.equals("B")){
	    		peso=40;
	    	}
	    	if (this.categoria.equals("C")){
	    		peso=60;
	    	}
	    	if (this.categoria.equals("D")){
	    		peso=60;
	    	}
	    	return peso;
	    }
	    */
	    
	    public int getPesoAzioniOK() {
	    	
	    	int peso=0;
	    	if (this.categoria.equals("A")){
	    		peso=40;
	    	}
	    	if (this.categoria.equals("B")){
	    		peso=40;
	    	}
	    	if (this.categoria.equals("C")){
	    		peso=60;
	    	}
	    	if (this.categoria.equals("D")){
	    		peso=60;
	    	}
	    	return peso;
	    }

		public double getPunteggioAssegnazioni() {
			double punteggio = 0;
			for (AzioneAssegnazione aa : this.getAssegnazioni()){
				//if(aa.getAnno()== this.anno && aa.getIncaricoID().equals(this.incaricoValutazioneID)) punteggio += aa.getPunteggio();
				punteggio += aa.getPunteggio();
			}
			return punteggio;
		}

		public Integer getIncaricoValutazioneID() {
			return incaricoValutazioneID;
		}

		public void setIncaricoValutazioneID(Integer incaricoValutazioneID) {
			this.incaricoValutazioneID = incaricoValutazioneID;
		}

		public int getAnno() {
			return anno;
		}

		public void setAnno(int anno) {
			this.anno = anno;
		}

		public List<ValutazioneComparto> getValutazioni() {
			return valutazioni;
		}

		public void setValutazioni(List<ValutazioneComparto> valutazioni) {
			this.valutazioni = valutazioni;
		}

		public int getNrAssegnazioniAnno() {
			return nrAssegnazioniAnno;
		}

		public void setNrAssegnazioniAnno(int nrAssegnazioniAnno) {
			this.nrAssegnazioniAnno = nrAssegnazioniAnno;
		}

		public List<ObiettivoAssegnazione> getObiettivoAssegnazioni() {
			return obiettivoAssegnazioni;
		}

		public void setObiettivoAssegnazioni(List<ObiettivoAssegnazione> obiettivoAssegnazioni) {
			this.obiettivoAssegnazioni = obiettivoAssegnazioni;
		}

		public boolean isCatAB() {
			if (this.categoria.equals("A") || this.categoria.equals("B") ) return true;
			else return false;
		}

		

		
		
		
} // ------------------------------------------------------------
