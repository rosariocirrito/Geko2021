package it.sicilia.regione.gekoddd.geko.programmazione.infrastructure.programma;

import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategico;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.programma.Programma;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.programma.ProgrammaQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.programma.ProgrammaRepository;
import it.sicilia.regione.gekoddd.geko.programmazione.web.controller.ControllerProgrListController;

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


@Service("programmaQryService")
@Repository
@Transactional
public class ProgrammaQryServiceImpl implements ProgrammaQryService{

	private Log log = LogFactory.getLog(ProgrammaQryServiceImpl.class);
	
	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private ProgrammaRepository repo;

	@Transactional(readOnly=true)
	public List<Programma> findAll() {
		return (List<Programma>) repo.findAll();
	}

	@Override
	public Map<Integer, String> mapSelectProgramma() {
		List<Programma> lista = this.findAll();
		PropertyComparator.sort(lista, new MutableSortDefinition("codice",true,true));
		Map<Integer, String> mapSelect = new LinkedHashMap<Integer, String>();
		//		
		for(Programma prg : lista){
			String str=""+prg.getCodice()+" - ";
			if (prg.getDescrizione().length()>100) 	str += prg.getDescrizione().substring(0,100)+ "...";
			else str += prg.getDescrizione();
			//
			log.info("key: "+prg.getId()+" value: "+str);
			mapSelect.put(prg.getId(), str);
		}
		
		return mapSelect;
	}

} // --------------------------------
