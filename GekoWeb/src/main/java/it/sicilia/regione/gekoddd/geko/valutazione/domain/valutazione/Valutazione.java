package it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione;

import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.criticValut.CriticValut;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneComparto.ValutazioneEnum;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

/**
 *
 * @author Cirrito
 */
@Entity
@Table(name="valutazione")
public class Valutazione {
    
    @Id
    @GeneratedValue
    private Integer id;
    //
    //@ManyToOne
    //@JoinColumn(name="idIncarico")
    //private Incarico incarico; // FK
    
    
    @Column(name="idIncarico")
    private Integer incaricoID;
    
    transient private IncaricoGeko incarico;
    //transient private Integer incDeptId;
    
    private int anno;
    
    
    
    public enum ValutazioneDirigEnum{
        ECCELLENTE,		/* 1 */
        BUONO,			/* 0,7 */
        SUFFICIENTE,	/* 0,5 */
        INSUFFICIENTE,	/* 0,2 */
        DA_VALUTARE,
        
    }
    
    //
    private int analProgrAss;
    @Enumerated
    private ValutazioneDirigEnum analProgrVal;
    
    private transient float analPunteggio;
    //
    private int relazCoordAss;
    
    @Enumerated
    private ValutazioneDirigEnum relazCoordVal;
    private transient float relazPunteggio;
    //
    private int gestRealAss;
    @Enumerated
    private ValutazioneDirigEnum gestRealVal;
    private transient float gestPunteggio;
    //
    private int pdlAss;
    @Enumerated
    private ValutazioneDirigEnum pdlVal;
    private transient float pdlPunteggio;
    //
    //
    private transient int totPeso;
    private transient int totPesoApicale;
    private transient float totPunteggio;
    private transient float totPunteggioApicale;
    //
    transient private boolean nuovo;
    transient private boolean critico;
    
    private transient int c1Peso;
    private transient int c2Peso;
    private transient int c3Peso;
    private transient int c4Peso;
    
    @OneToMany(mappedBy = "valutazione",fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<CriticValut> criticita = new HashSet<CriticValut>();
    
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
    public int getAnalProgrAss() {return analProgrAss;}
    public void setAnalProgrAss(int analProgrAss) {this.analProgrAss = analProgrAss;}

    public ValutazioneDirigEnum getAnalProgrVal() {return analProgrVal;}
	public void setAnalProgrVal(ValutazioneDirigEnum analProgrVal) {this.analProgrVal = analProgrVal;}
	
    public float getAnalPunteggio() {
    	int valutFactor=0;
        switch (this.analProgrVal)
        {
	        case ECCELLENTE:
		        valutFactor= 100;
		        break;
	        case BUONO:
		        valutFactor= 70;
		        break;
	        case SUFFICIENTE:
		        valutFactor= 50;
		        break;
	        case INSUFFICIENTE:
		        valutFactor= 20;
		        break;
	        case DA_VALUTARE:
		        valutFactor= 0;
		        break;   
	         
        }
        this.analPunteggio = (float)(this.analProgrAss * valutFactor) / 100;
		return analPunteggio;
	}

    //
    public int getRelazCoordAss() {return relazCoordAss;}
    public void setRelazCoordAss(int relazCoordAss) {this.relazCoordAss = relazCoordAss;}

    public ValutazioneDirigEnum getRelazCoordVal() {return relazCoordVal;}
	public void setRelazCoordVal(ValutazioneDirigEnum relazCoordVal) {this.relazCoordVal = relazCoordVal;}

	public float getRelazPunteggio() {
		int valutFactor=0;
        switch (this.relazCoordVal)
        {
	        case ECCELLENTE:
		        valutFactor= 100;
		        break;
	        case BUONO:
		        valutFactor= 70;
		        break;
	        case SUFFICIENTE:
		        valutFactor= 50;
		        break;
	        case INSUFFICIENTE:
		        valutFactor= 20;
		        break;
	        case DA_VALUTARE:
		        valutFactor= 0;
		        break;    
	         
        }
        this.relazPunteggio = (float)(this.relazCoordAss * valutFactor) / 100;
		return relazPunteggio;
	}

	//
	public int getGestRealAss() {return gestRealAss;}
    public void setGestRealAss(int gestRealAss) {this.gestRealAss = gestRealAss;}
    
	public ValutazioneDirigEnum getGestRealVal() {return gestRealVal;}
	public void setGestRealVal(ValutazioneDirigEnum gestRealVal) {	this.gestRealVal = gestRealVal;}

	public float getGestPunteggio() {
		int valutFactor=0;
        switch (this.gestRealVal)
        {
	        case ECCELLENTE:
		        valutFactor= 100;
		        break;
	        case BUONO:
		        valutFactor= 70;
		        break;
	        case SUFFICIENTE:
		        valutFactor= 50;
		        break;
	        case INSUFFICIENTE:
		        valutFactor= 20;
		        break;
	        case DA_VALUTARE:
		        valutFactor= 0;
		        break;
	         
        }
        this.gestPunteggio = (float)(this.getGestRealAss() * valutFactor) / 100;
		return gestPunteggio;
	}

	//
	
	
	//
    public int getAnno() {
        return anno;
    }

    public int getPdlAss() {return pdlAss;}
	public void setPdlAss(int pdlAss) {this.pdlAss = pdlAss;}

	public ValutazioneDirigEnum getPdlVal() {return pdlVal;}
	public void setPdlVal(ValutazioneDirigEnum pdlVal) {this.pdlVal = pdlVal;}

	public float getPdlPunteggio() {
		int valutFactor=0;
        switch (this.pdlVal)
        {
	        case ECCELLENTE:
		        valutFactor= 100;
		        break;
	        case BUONO:
		        valutFactor= 70;
		        break;
	        case SUFFICIENTE:
		        valutFactor= 50;
		        break;
	        case INSUFFICIENTE:
		        valutFactor= 20;
		        break;
	        case DA_VALUTARE:
		        valutFactor= 0;
		        break;
        }
        this.pdlPunteggio = (float)(this.pdlAss * valutFactor) / 100;
		return pdlPunteggio;
	}


	public void setAnno(int anno) {
        this.anno = anno;
    }
    

    public IncaricoGeko getIncarico() {
        return incarico;
    }

    public void setIncarico(IncaricoGeko incarico) {
        this.incarico = incarico;
    }
    
    //
    public int getTotPeso() {
    	int totP=0;
    	totP += this.pdlAss;
    	totP += this.analProgrAss;
    	totP += this.relazCoordAss;
    	totP += this.gestRealAss;
    	List<Obiettivo> objPriors = this.getIncarico().getObiettiviPrio();
    	for (Obiettivo obj : objPriors){
    		totP += obj.getPeso();
    	}
		return totP;
	}
    
    public int getTotPesoApicale() {
    	int totP=0;
    	totP += this.pdlAss;
    	totP += this.analProgrAss;
    	totP += this.relazCoordAss;
    	totP += this.gestRealAss;
    	List<Obiettivo> objPriors = this.getIncarico().getObiettiviPrio();
    	for (Obiettivo obj : objPriors){
    		totP += obj.getPesoApicale();
    	}
		return totP;
	}

	public float getTotPunteggio() {
		float totPunteggio=0;
		totPunteggio += this.getPdlPunteggio();
		totPunteggio += this.getAnalPunteggio();
		totPunteggio += this.getRelazPunteggio();
		totPunteggio += this.getGestPunteggio();
    	List<Obiettivo> objPriors = this.getIncarico().getObiettiviPrio();
    	for (Obiettivo obj : objPriors){
    		totPunteggio += obj.getPunteggio();
    	}
		return totPunteggio;
	}
	
	public float getTotPunteggioApicale() {
		float totPunteggio=0;
		totPunteggio += this.getPdlPunteggio();
		totPunteggio += this.getAnalPunteggio();
		totPunteggio += this.getRelazPunteggio();
		totPunteggio += this.getGestPunteggio();
    	List<Obiettivo> objPriors = this.getIncarico().getObiettiviPrio();
    	for (Obiettivo obj : objPriors){
    		totPunteggio += obj.getPunteggioApicale();
    	}
		return totPunteggio;
	}
	
	 public Set<CriticValut> getCriticita() {
	        return criticita;
	    }
	    //
	    public void setCriticita(Set<CriticValut> criticita) {
	        this.criticita = criticita;
	    }
	    //
	    public void addCriticitaToValutazione(CriticValut criticita){
	        criticita.setValutazione(this);
	        this.getCriticita().add(criticita);
	    }
	    //
	    public void removeCriticitaFromValutazione(CriticValut criticita){
	        this.getCriticita().remove(criticita);
	    }
	    
	    public boolean isCritico() {
			List<CriticValut> critic = new ArrayList<CriticValut>(this.getCriticita());
			return (!critic.isEmpty());
		    }
	    
	    public Integer getIncaricoID() {return this.incaricoID;}

		public void setIncaricoID(Integer incaricoID) {
			this.incaricoID = incaricoID;
		}

		public int getC1Peso() {
			return this.gestRealAss;
		}

		public void setC1Peso(int c1Peso) {
			this.gestRealAss = c1Peso;
		}

		public int getC2Peso() {
			return this.analProgrAss;
		}

		public void setC2Peso(int c2Peso) {
			this.analProgrAss = c2Peso;
		}

		public int getC3Peso() {
			return this.relazCoordAss;
		}

		public void setC3Peso(int c3Peso) {
			this.relazCoordAss = c3Peso;
		}

		public int getC4Peso() {
			return this.pdlAss;
		}

		public void setC4Peso(int c4Peso) {
			this.pdlAss = c4Peso;
		}
	    
	    
	    
} // ---------------------------------------------
