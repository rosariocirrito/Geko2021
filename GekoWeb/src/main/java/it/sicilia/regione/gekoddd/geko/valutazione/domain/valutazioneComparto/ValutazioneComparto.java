package it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto;

import javax.persistence.*;

import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;

/**
 *
 * @author Cirrito
 */
@Entity
@Table(name="valutazioneComparto")
public class ValutazioneComparto {
    
    @Id
    @GeneratedValue
    private Integer id;
    //

    @Column(name="idDipendente")
    private Integer pfID; // FK
    
    @Column(name="idIncarico")
    private Integer incaricoID;// FK
    
    transient private PersonaFisicaGeko pf;
    transient private IncaricoGeko incarico;
    
    private int anno;
    
    // sono quelle dei comportamenti organizzativi
    public enum ValutazioneEnum{
        ECCELLENTE,
        BUONO,
        SUFFICIENTE,
        INSUFFICIENTE,
        DA_VALUTARE
    }
    
    // per tutti aggiustare i campi del dbase.
    // 1.
    private int competSvolgAttivAss;
    @Enumerated
    private ValutazioneEnum competSvolgAttivVal;
    private transient double competSvolgAttivPunteggio;
    // 2.
    private int adattContextLavAss;
    @Enumerated
    private ValutazioneEnum adattContextLavVal;
    private transient double adattContextLavPunteggio;
    // 4.
    private int capacAssolvCompitiAss;
    @Enumerated
    private ValutazioneEnum capacAssolvCompitiVal;
    private transient double capacAssolvCompitiPunteggio;
    
    // altre 2 per C e D
    // 3.
    private int innovazAss;
    @Enumerated
    private ValutazioneEnum innovazVal;
    private transient double innovazPunteggio;
    
    // 5.
    private int orgLavAss;
    @Enumerated
    private ValutazioneEnum orgLavVal;
    private transient double orgLavPunteggio;
    
    // forse vecchie
    private int complessDifficoltaAss;
    @Enumerated
    private ValutazioneEnum complessDifficoltaVal;
    private transient double complessDifficPunteggio;
    //
    private int competProfAss;
    @Enumerated
    private ValutazioneEnum competProfVal;
    private transient double competProfPunteggio;
    //
    
    //      
    private String annot1,annot2, annot3;
    
    private transient int totPeso;
    private transient double totPunteggio;
    //
    transient private boolean nuovo;
    transient private int Idincarico;
    
    // ----------------------------------
    
    public boolean isNuovo() {return (this.id == null);}

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    //
    public int getAnno() {return anno;}
	public void setAnno(int anno) {this.anno = anno;}    

	
	
	
	

	
	public String getAnnot1() {return annot1;}
	public void setAnnot1(String annot1) {this.annot1 = annot1;}
	public String getAnnot2() {return annot2;}
	public void setAnnot2(String annot2) {this.annot2 = annot2;}
	public String getAnnot3() {return annot3;}
	public void setAnnot3(String annot3) {this.annot3 = annot3;}

	public Integer getPfID() {return pfID;}
	public void setPfID(Integer pfID) {this.pfID = pfID;}
	public PersonaFisicaGeko getPf() {return pf;}
	public void setPf(PersonaFisicaGeko pf) {this.pf = pf;}

	public Integer getIncaricoID() {return incaricoID;}
	public void setIncaricoID(Integer incaricoID) {this.incaricoID = incaricoID;}
	public IncaricoGeko getIncarico() {return incarico;}
	public void setIncarico(IncaricoGeko incarico) {this.incarico = incarico;}	
	public int getIdincarico() {return Idincarico;}
	public void setIdincarico(int idincarico) {Idincarico = idincarico;}
	
	
	// 1. CompetSvolgAttiv
	public int getCompetSvolgAttivAss() {return competSvolgAttivAss;}
	public void setCompetSvolgAttivAss(int competSvolgAttivAss) {this.competSvolgAttivAss = competSvolgAttivAss;}
	public ValutazioneEnum getCompetSvolgAttivVal() {return competSvolgAttivVal;}
	public void setCompetSvolgAttivVal(ValutazioneEnum competSvolgAttivVal) {this.competSvolgAttivVal = competSvolgAttivVal;}
	public double getCompetSvolgAttivPunteggio() {
		double valutFactor=0;
        switch (this.competSvolgAttivVal)
        {
	        case ECCELLENTE:
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
        competSvolgAttivPunteggio = valutFactor*competSvolgAttivAss; 
		return competSvolgAttivPunteggio;
	}
	public void setCompetSvolgAttivPunteggio(double competSvolgAttivPunteggio) {this.competSvolgAttivPunteggio = competSvolgAttivPunteggio;}

	// 2. AdattContextLav
	public int getAdattContextLavAss() {return adattContextLavAss;}
	public void setAdattContextLavAss(int adattContextLavAss) {this.adattContextLavAss = adattContextLavAss;}
	public ValutazioneEnum getAdattContextLavVal() {return adattContextLavVal;}
	public void setAdattContextLavVal(ValutazioneEnum adattContextLavVal) {this.adattContextLavVal = adattContextLavVal;}
	public double getAdattContextLavPunteggio() {
		double valutFactor=0;
        switch (this.adattContextLavVal)
        {
	        case ECCELLENTE:
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
        adattContextLavPunteggio = valutFactor*adattContextLavAss; 
		return adattContextLavPunteggio;
	}
	public void setAdattContextLavPunteggio(double adattContextLavPunteggio) {this.adattContextLavPunteggio = adattContextLavPunteggio;}

	// 3. innovaz 
	public void setInnovazPunteggio(double innovazPunteggio) {this.innovazPunteggio = innovazPunteggio;}
	public int getInnovazAss() {return innovazAss;}
	public void setInnovazAss(int innovazAss) {this.innovazAss = innovazAss;}
	public ValutazioneEnum getInnovazVal() {return innovazVal;}
	public void setInnovazVal(ValutazioneEnum innovazVal) {this.innovazVal = innovazVal;}
	public double getInnovazPunteggio() {
		double valutFactor=0;
        switch (this.innovazVal)
        {
	        case ECCELLENTE:
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
        innovazPunteggio = valutFactor*innovazAss; 
		return innovazPunteggio;
	}
	
		// 4. CapacAssolvCompiti
	public int getCapacAssolvCompitiAss() {return capacAssolvCompitiAss;}
	public void setCapacAssolvCompitiAss(int capacAssolvCompitiAss) {this.capacAssolvCompitiAss = capacAssolvCompitiAss;}
	public ValutazioneEnum getCapacAssolvCompitiVal() {return capacAssolvCompitiVal;}
	public void setCapacAssolvCompitiVal(ValutazioneEnum capacAssolvCompitiVal) {this.capacAssolvCompitiVal = capacAssolvCompitiVal;}
	public double getCapacAssolvCompitiPunteggio() {
		double valutFactor=0;
        switch (this.capacAssolvCompitiVal)
        {
	        case ECCELLENTE:
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
        capacAssolvCompitiPunteggio = valutFactor*capacAssolvCompitiAss; 
		return capacAssolvCompitiPunteggio;
	}
	public void setCapacAssolvCompitiPunteggio(double capacAssolvCompitiPunteggio) {this.capacAssolvCompitiPunteggio = capacAssolvCompitiPunteggio;}

	// 5. OrgLav solo C e D 
	public int getOrgLavAss() {return orgLavAss;}
	public void setOrgLavAss(int orgLavAss) {this.orgLavAss = orgLavAss;}
	public ValutazioneEnum getOrgLavVal() {return orgLavVal;}
	public void setOrgLavVal(ValutazioneEnum orgLavVal) {this.orgLavVal = orgLavVal;}
	public double getOrgLavPunteggio() {
		double valutFactor=0;
        switch (this.orgLavVal)
        {
	        case ECCELLENTE:
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
        orgLavPunteggio = valutFactor*orgLavAss; 
		return orgLavPunteggio;
	}
	public void setOrgLavPunteggio(double orgLavPunteggio) {this.orgLavPunteggio = orgLavPunteggio;}
	
	// ??
	
	

	// totale pesi
	 //
    public int getTotPeso() {
    	int totP=0;
    	totP += this.competSvolgAttivAss;
    	totP += this.adattContextLavAss;
    	totP += this.capacAssolvCompitiAss;    	
    	totP += this.innovazAss;	
    	totP += this.orgLavAss;	
		return totP;
	}
	public void setTotPeso(int totPeso) {this.totPeso = totPeso;}

	// totale punteggi
	public double getTotPunteggio() {		
		double totPunteggio=0;
		totPunteggio += this.getCompetSvolgAttivPunteggio(); 			// 1.
		totPunteggio += this.getAdattContextLavPunteggio();				// 2.
		totPunteggio += this.getInnovazPunteggio();	// 3.
		totPunteggio += this.getCapacAssolvCompitiPunteggio();			// 4.
		totPunteggio += this.getOrgLavPunteggio();	// 5.
		return totPunteggio;
	}
	public void setTotPunteggio(double totPunteggio) {this.totPunteggio = totPunteggio;}

	@Override
	public String toString() {
		return "ValutazioneComparto [id=" + id + ", competSvolgAttivAss=" + competSvolgAttivAss
				+ ", adattContextLavAss=" + adattContextLavAss + ", capacAssolvCompitiAss=" + capacAssolvCompitiAss
				+ ", innovazAss=" + innovazAss + ", orgLavAss=" + orgLavAss + "]";
	}

	
} // ---------------------------------------------
