package it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoPluriennale;


import java.util.List;
import java.util.Map;

public interface ObiettivoPluriennaleQryService {

	List<ObiettivoPluriennale> findObiettiviPluriennaliByIncaricoIDAndAnno(Integer incaricoID, int anno);
	
	Map<Integer,String> mapSelectObiettiviPluriennaliByIncaricoIDAndAnno(Integer incaricoID, int anno);	
	
}
