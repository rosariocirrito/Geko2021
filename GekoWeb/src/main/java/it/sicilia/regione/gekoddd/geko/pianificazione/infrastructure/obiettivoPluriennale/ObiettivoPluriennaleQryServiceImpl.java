package it.sicilia.regione.gekoddd.geko.pianificazione.infrastructure.obiettivoPluriennale;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoPluriennale.ObiettivoPluriennale;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoPluriennale.ObiettivoPluriennaleQryService;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoPluriennale.ObiettivoPluriennaleRepository;


@Service("obiettivoPluriennaleQryService")
@Repository
@Transactional
public class ObiettivoPluriennaleQryServiceImpl implements ObiettivoPluriennaleQryService {
	
	private Log log = LogFactory.getLog(ObiettivoPluriennaleQryServiceImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private ObiettivoPluriennaleRepository repo;
	
	
	
	@Override
	public Map<Integer, String> mapSelectObiettiviPluriennaliByIncaricoIDAndAnno(Integer incaricoID, int anno) {
		List<ObiettivoPluriennale> lista = new ArrayList<ObiettivoPluriennale>();
		List<ObiettivoPluriennale> listaTot = repo.findByIncaricoID(incaricoID);
		Map<Integer, String> mapSelect = new LinkedHashMap<Integer, String>();
		for(ObiettivoPluriennale obj : listaTot){
			if((obj.getAnnoInz() <= anno) && (obj.getAnnoFin() >= anno)){
				lista.add(obj);
			}
		}
		PropertyComparator.sort(lista, new MutableSortDefinition("codice",true,true));
		for(ObiettivoPluriennale obj : lista){
			String str="";
			if (obj.getDescrizione().length()>100) 	str += obj.getDescrizione().substring(0,100)+ "...";
			else str += obj.getDescrizione();
			//
			mapSelect.put(obj.getId(), str);
		}
		
		return mapSelect;
	}



	@Override
	public List<ObiettivoPluriennale> findObiettiviPluriennaliByIncaricoIDAndAnno(Integer incaricoID, int anno) {
		List<ObiettivoPluriennale> listaTot = repo.findByIncaricoID(incaricoID);
		List<ObiettivoPluriennale> lista = new ArrayList<ObiettivoPluriennale>();
		for(ObiettivoPluriennale obj : listaTot){
			if((obj.getAnnoInz() <= anno) && (obj.getAnnoFin() >= anno)){
				lista.add(obj);
			}
		}
		return lista;
	}


	/*

	@Override
	public List<ObiettivoPluriennale> findByAreaStrategicaAndAnno(AreaStrategica area, int anno) {
		List<ObiettivoPluriennale> lista = repo.findByAreaStrategicaAndAnno(area, anno);
		PropertyComparator.sort(lista, new MutableSortDefinition("codice",true,true));
		return lista;
	}

	*/

}
