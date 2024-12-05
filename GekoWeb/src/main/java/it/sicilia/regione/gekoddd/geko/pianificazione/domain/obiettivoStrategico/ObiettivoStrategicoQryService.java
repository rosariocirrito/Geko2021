package it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico;


import java.util.List;
import java.util.Map;

import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategica;

public interface ObiettivoStrategicoQryService {

		// Find a contact with details by id
	//public ObiettivoStrategico findByDescrizione(String descrizione);
	
	//-----------------------------------
	public Map<Integer,String> mapSelectObiettiviStrategici(int anno);	
	public List<ObiettivoStrategico> findByAreaStrategicaAndAnno(AreaStrategica area, int anno); 
}
