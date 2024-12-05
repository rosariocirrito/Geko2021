package it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo;

import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;

import java.util.List;
import java.util.Map;

public interface ObiettivoQryService {

	
	//-----------------------------------
	
	// CQRS
	List<Obiettivo> findObiettiviTotaliConPersoneByIncaricoIDAndAnno(Integer incaricoID, int anno); // devo iniettare pfGeko e pgGeko con i campi che servono!!!
	List<Obiettivo> findObiettiviApicaliDirettiConPersoneByIncaricoIDAndAnno(Integer incaricoID, int anno);
	
	List<Obiettivo> findObiettiviTotaliByIncaricoIDAndAnno(Integer incaricoID, int anno); // devo iniettare pfGeko e pgGeko con i campi che servono!!!
	List<Obiettivo> findObiettiviPrioritariByIncaricoIDAndAnno(Integer incaricoID, int anno);
	List<Obiettivo> findObiettiviDirigenzialiConPersoneByIncaricoIDAndAnno(Integer incaricoID, int anno); // devo iniettare pfGeko e pgGeko con i campi che servono!!!
	List<Obiettivo> findObiettiviDirigenzialiSenzaPersoneByIncaricoIDAndAnno(Integer incaricoID, int anno); // non devo iniettare pfGeko e pgGeko con i campi che servono!!!
	
	List<Obiettivo> findObiettiviPopSenzaPersoneByIncaricoIDAndAnno(Integer incaricoID, int anno);
	List<Obiettivo> findObiettiviCompartoConPersoneByIncaricoIDAndAnno(Integer incaricoID, int anno); // devo iniettare pfGeko e pgGeko con i campi che servono!!!
	
	
	List<Obiettivo> findObiettiviDirettiByIncaricoIDAndAnno(Integer incaricoID, int anno);
	List<Obiettivo> findObiettiviApicaliDirettiByIncaricoIDAndAnno(Integer incaricoID, int anno);
	
	List<Obiettivo> findObiettiviSubordinatiByIncaricoIDAndAnno(Integer incaricoID, int anno);
	List<Obiettivo> findObiettiviApicaliSubordinatiByIncaricoIDAndAnno(Integer incaricoID, int anno);
	
	List<Obiettivo> findObiettiviDirettiAndSubordinatiByIncaricoIDAndAnno(Integer incaricoID, int anno);
	List<Obiettivo> findObiettiviApicaliDirettiAndSubordinatiByIncaricoIDAndAnno(Integer incaricoID, int anno);
	
	Map<IncaricoGeko,List<Obiettivo>> mapObiettiviDipartimentoIDAndAnno(Integer dipartimentoID, int anno);
	Map<IncaricoGeko,List<Obiettivo>> mapObiettiviApicaliDipartimentoIDAndAnno(Integer dipartimentoID, int anno);
	
	Map<Integer,String> mapSelectObiettiviApicaliByDipartimentoIDAndAnno(Integer dipartimentoID, int anno);
	
	List<Obiettivo> findByCodiceAndAnno(String codice , int anno);
}
