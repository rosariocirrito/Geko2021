package it.sicilia.regione.gekoddd.geko.acl.model;

import it.sicilia.regione.gekoddd.geko.acl.dto.IncaricoGekoDTO;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo.StatoApprovEnum;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneComparto;

import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;


public final class IncaricoGeko  {
	
    // ------------------- campi da Rest ---------------------------------------
    /*
    val idIncarico: Int
    val dataInizio: Date
    val dataFine: Date
    val pfID: Int
    val pgID: Int
    val interim: Boolean
    
    val responsabile: String
    val codiceStruttura: String
    val denominazioneStruttura: String
    val competenzeStruttura: String
    val idDept: Int
    val denominazioneDipartimento: String
    val incaricoDipartimentale: Boolean
    val incaricoDirigenziale: Boolean
    val incaricoPop: Boolean
    val carica: String
    */
    public final Integer idIncarico;
    public final Date dataInizio;
    public final Date dataFine;
    public final int pfID;
    public final int pgID;
    public final boolean interim;   
    
    public final String responsabile;
    public final String codiceStruttura;
    public final String denominazioneStruttura;
    public final String competenzeStruttura;
    public final int idDept;
    public final int deptID; // per compatibilità purtropp
    public final String denominazioneDipartimento;
    public final boolean incaricoDipartimentale;
    public final boolean incaricoDirigenziale;
    public final boolean incaricoPop;
    public final String carica;    
    
    private List<Valutazione> valutazioni = new ArrayList<Valutazione>();
    private Set<ValutazioneComparto> valutazioniComparto = new HashSet<ValutazioneComparto>();
    private List<Obiettivo> obiettivi = new ArrayList<Obiettivo>();
    // liste obiettivi di un incarico in un anno specifico
    transient private List<Obiettivo> obiettiviPrioritariAnno;
    transient private List<Obiettivo> obiettiviTotAnno;
    transient private List<Obiettivo> obiettiviValidiAnno;
    transient private List<Obiettivo> obiettiviApicaliAnno;
    transient private List<Obiettivo> obiettiviApicaliDirettiAnno;
    transient private List<Obiettivo> obiettiviCriticiAnno;
    
    transient private int totPesoObiettivi;
    transient private int totPesoApicaleObiettivi;
    transient private float totPunteggioObiettivi;
    transient private float totPunteggioApicaleObiettivi;
    transient private String order;
    transient private String strDataInizio;
    transient private String strDataFine;
    
    transient private int idIncaricoDaClonare;
    transient private List<IncaricoGeko> listaIncarichiDaClonare;
    
    public IncaricoGeko(IncaricoGekoDTO inc){
    	this.idIncarico = inc.idIncarico;
        this.dataInizio =inc.dataInizio;
        this.dataFine = inc.dataFine;
        this.pfID = inc.pfID;
        this.pgID = inc.pgID;
        this.interim =inc.interim;       
        
        this.responsabile = inc.responsabile;
        this.codiceStruttura = inc.codiceStruttura;
        this.denominazioneStruttura = inc.denominazioneStruttura;
        this.competenzeStruttura = inc.competenzeStruttura;
        this.idDept = inc.idDept;
        this.deptID = inc.idDept;
        this.denominazioneDipartimento = inc.denominazioneDipartimento;
        this.incaricoDipartimentale = inc.incaricoDipartimentale;
        this.incaricoDirigenziale = inc.incaricoDirigenziale;
        this.incaricoPop = inc.incaricoPop;
        this.carica = inc.carica; 
    }
    
    public String getOrder() {
        String order ="";
        if (this.codiceStruttura != null) order = this.denominazioneStruttura + order  ;
        order = order + this.responsabile;
        return order;
    }

    public Integer getIdIncarico() { // per compatibilità
        return idIncarico;
    }
    public Date getDataInizio(){ return dataInizio;}
    public Date getDataFine() {return dataFine;}
    public String getDenominazioneStruttura() { // per compatibilità
         return this.denominazioneStruttura;
     }
    public String getCodiceStruttura() {
        return codiceStruttura;
    }
    public String getCompetenzeStruttura() {
        return competenzeStruttura;
    }

    public boolean isInterim() {
        return interim;
    }

    public boolean isIncaricoDipartimentale() {
        return incaricoDipartimentale;
    }

    public boolean isIncaricoDirigenziale() {
        return incaricoDirigenziale;
    }

    public boolean isIncaricoPop() {
        return incaricoPop;
    }

    public int getDeptID() {
        return deptID;
    }

    public int getIdDept() {
        return idDept;
    }

    public List<Valutazione> getValutazioni() {
        // eventuali sort qui
        return valutazioni;
    } 
    public void setValutazioni(List<Valutazione> valutazioni) {
            this.valutazioni = valutazioni;
    }

    public List<Obiettivo> getObiettivi() {
            List<Obiettivo> sortedObiettivi = new ArrayList<Obiettivo>(this.obiettivi);
            PropertyComparator.sort(sortedObiettivi, new MutableSortDefinition("order",true,true));
            return sortedObiettivi;
    }
	
    public void setObiettivi(List<Obiettivo> obiettivi) {
            this.obiettivi = obiettivi;
    }
	
    public List<Obiettivo> getObiettiviPrio() {
    	List<Obiettivo> lista = new ArrayList<Obiettivo>();
    	for (Obiettivo obj : this.getObiettivi()){
            if (obj.getTipo().equals(Obiettivo.TipoEnum.DIRIGENZIALE)) lista.add(obj);
            if (obj.getTipo().equals(Obiettivo.TipoEnum.POS_ORGAN)) lista.add(obj);
    	}    	
        PropertyComparator.sort(lista, new MutableSortDefinition("order",true,true));// tipo + idobiettivo
        return lista;
    }	
	
    public List<Obiettivo> getObiettiviPrioritariAnno(int anno) {
        List<Obiettivo> lista = new ArrayList<Obiettivo>();
        for (Obiettivo obj : this.getObiettiviPrio()){
            if (obj.getAnno() == anno) lista.add(obj);
        }  
        PropertyComparator.sort(lista, new MutableSortDefinition("order",true,true));// tipo + idobiettivo
        return getObiettiviPrio();
    }
	
    public List<Obiettivo> getObiettiviTotAnno(int anno) {
        List<Obiettivo> lista = new ArrayList<Obiettivo>();
        for (Obiettivo obj : this.getObiettivi()){
            if (obj.getAnno() == anno) lista.add(obj);
        }    	
        PropertyComparator.sort(lista, new MutableSortDefinition("order",true,true));// tipo + idobiettivo
        return lista;
    }
	
    public List<Obiettivo> getObiettiviCriticiAnno(int anno) {
    	List<Obiettivo> lista = new ArrayList<Obiettivo>();
    	for (Obiettivo obj : this.getObiettivi()){
            if (obj.getAnno() == anno && obj.isCritico()) lista.add(obj);
    	}
        PropertyComparator.sort(lista, new MutableSortDefinition("order",true,true));// tipo + idobiettivo
	return lista;
    }

    public List<Obiettivo> getObiettiviValidiAnno(int anno) {
        List<Obiettivo> lista = new ArrayList<Obiettivo>();
        for (Obiettivo obj : this.getObiettiviTotAnno(anno)){
            if (obj.getStatoApprov() != StatoApprovEnum.ANNULLATO) lista.add(obj);
        }
        PropertyComparator.sort(lista, new MutableSortDefinition("order",true,true));// tipo + idobiettivo
        return lista;
    }
	
    public List<Obiettivo> getObiettiviApicaliAnno(int anno) {
        List<Obiettivo> lista = new ArrayList<Obiettivo>();
        for (Obiettivo obj : this.getObiettiviValidiAnno(anno)){
            if (obj.isApicale()) lista.add(obj);
    	}
        PropertyComparator.sort(lista, new MutableSortDefinition("order",true,true));// tipo + idobiettivo
	return lista;
    }
	
    public List<Obiettivo> getObiettiviApicaliDirettiAnno(int anno) {
        List<Obiettivo> lista = new ArrayList<Obiettivo>();
        for (Obiettivo obj : this.getObiettiviApicaliAnno(anno)){
            if (obj.getIncarico().incaricoDipartimentale) lista.add(obj);
        }
        PropertyComparator.sort(lista, new MutableSortDefinition("order",true,true));// tipo + idobiettivo
        return lista;
    }
	
    public int getTotPesoObiettivi() {
    	int totPeso = 0;
    	//System.out.println("Incarico.getTotPesoObiettivi() totpesoiniziale="+totPeso);
    	//System.out.println("Incarico.getTotPesoObiettivi() anno="+anno);
    	for(Obiettivo obj : this.getObiettiviPrio()){
            totPeso += obj.getPeso();
            //System.out.println("Incarico.getTotPesoObiettivi() obj="+obj.getCodice()+"peso"+obj.getPeso());
            //System.out.println("Incarico.getTotPesoObiettivi() totpeso="+totPeso);
    	}
    	//System.out.println("Incarico.getTotPesoObiettivi() totpesofinale="+totPeso);
	return totPeso;
    }

    public int getTotPesoApicaleObiettivi() {
    	int totPeso = 0;
    	//System.out.println("Incarico.getTotPesoObiettivi() totpesoiniziale="+totPeso);
    	//System.out.println("Incarico.getTotPesoObiettivi() anno="+anno);
    	for(Obiettivo obj : this.getObiettiviPrio()){
            totPeso += obj.getPesoApicale();
            //System.out.println("Incarico.getTotPesoObiettivi() obj="+obj.getCodice()+"peso"+obj.getPeso());
            //System.out.println("Incarico.getTotPesoObiettivi() totpeso="+totPeso);
    	}
    	//System.out.println("Incarico.getTotPesoObiettivi() totpesofinale="+totPeso);
	return totPeso;
    }
	
    public float getTotPunteggioObiettivi() {
        float totPunteggio = 0;
        for(Obiettivo obj : this.getObiettiviPrio()){
                totPunteggio += obj.getPunteggio();
        }
        return totPunteggio;
    }
    
    public float getTotPunteggioApicaleObiettivi() {
        float totPunteggioApicale = 0;
        //System.out.println("Incarico.getTotPesoObiettivi() totpesoiniziale="+totPeso);
        //System.out.println("Incarico.getTotPesoObiettivi() anno="+anno);
        for(Obiettivo obj : this.getObiettiviPrio()){
            totPunteggioApicale += obj.getPunteggioApicale();
            //System.out.println("Incarico.getTotPesoObiettivi() obj="+obj.getCodice()+"peso"+obj.getPeso());
            //System.out.println("Incarico.getTotPesoObiettivi() totpeso="+totPeso);
        }
        //System.out.println("Incarico.getTotPesoObiettivi() totpesofinale="+totPeso);
        return totPunteggioApicale;
    }
	
	
	public Set<ValutazioneComparto> getValutazioniComparto() {
		return valutazioniComparto;
	}
	public void setValutazioniComparto(Set<ValutazioneComparto> valutazioniComparto) {
		this.valutazioniComparto = valutazioniComparto;
	}
   
	public String getStringa(){
            String stringa = this.denominazioneStruttura;
            if (this.interim) stringa+=" INTERIM ";
            stringa += " - "+this.responsabile;
            stringa += " da: "+this.getStrDataInizio()+ " a: "+this.getStrDataFine();
            return stringa;
	}


	@Override
	public String toString() {
		return "IncaricoGeko [interim=" + interim +
                        ", responsabile=" + responsabile + 
                        ", codiceStruttura="
			+ codiceStruttura + "]";
	}



	public String getCarica() {return carica;}


	public String getDenominazioneDipartimento() {return denominazioneDipartimento;}



	public String getStrDataInizio() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // creo l'oggetto
	    String dataStr = sdf.format(this.dataInizio);
		return dataStr;
	}


	public String getStrDataFine() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // creo l'oggetto
	    String dataStr = sdf.format(this.dataFine);
		return dataStr;
	}

	public int getPfID() {return pfID;}
	public int getPgID() {return pgID;}
        public String getResponsabile() {return responsabile;}


	public int getIdIncaricoDaClonare() {return idIncaricoDaClonare;}
	public void setIdIncaricoDaClonare(int idIncaricoDaClonare) {
		this.idIncaricoDaClonare = idIncaricoDaClonare;
	}

	public List<IncaricoGeko> getListaIncarichiDaClonare() {
		return listaIncarichiDaClonare;
	}
	public void setListaIncarichiDaClonare(List<IncaricoGeko> listaIncarichiDaClonare) {
		this.listaIncarichiDaClonare = listaIncarichiDaClonare;
	}
	
	
} // -----------IncaricoGeko-----

