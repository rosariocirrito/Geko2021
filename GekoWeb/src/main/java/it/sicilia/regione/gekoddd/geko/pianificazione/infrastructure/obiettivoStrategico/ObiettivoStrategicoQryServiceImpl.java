package it.sicilia.regione.gekoddd.geko.pianificazione.infrastructure.obiettivoStrategico;

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

import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategica;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategico;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategicoQryService;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategicoRepository;


@Service("obiettivoStrategicoQryService")
@Repository
@Transactional
public class ObiettivoStrategicoQryServiceImpl implements ObiettivoStrategicoQryService {
	
	private Log log = LogFactory.getLog(ObiettivoStrategicoQryServiceImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private ObiettivoStrategicoRepository repo;
	
	
	

	public Map<Integer, String> mapSelectObiettiviStrategici(int anno) {	
		List<ObiettivoStrategico> lista = new ArrayList<ObiettivoStrategico>();
		Map<Integer, String> mapSelect = new LinkedHashMap<Integer, String>();
		for(ObiettivoStrategico obj : repo.findAll()){
			if(obj.getAnno() == anno){
				lista.add(obj);
			}
		}
		PropertyComparator.sort(lista, new MutableSortDefinition("codice",true,true));
		for(ObiettivoStrategico obj : lista){
			String str=""+obj.getCodice()+" - ";
			if (obj.getDescrizione().length()>100) 	str += obj.getDescrizione().substring(0,100)+ "...";
			else str += obj.getDescrizione();
			//
			mapSelect.put(obj.getId(), str);
		}
		
		return mapSelect;
	}




	@Override
	public List<ObiettivoStrategico> findByAreaStrategicaAndAnno(AreaStrategica area, int anno) {
		List<ObiettivoStrategico> lista = repo.findByAreaStrategicaAndAnno(area, anno);
		PropertyComparator.sort(lista, new MutableSortDefinition("codice",true,true));
		return lista;
	}

	

}
