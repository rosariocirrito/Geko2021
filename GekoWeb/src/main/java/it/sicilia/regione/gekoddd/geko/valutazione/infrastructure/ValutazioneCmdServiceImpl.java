package it.sicilia.regione.gekoddd.geko.valutazione.infrastructure;

import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.ValutazioneCmdService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.ValutazioneRepository;

import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("valutazioneCmdService")
@Repository
@Transactional
public class ValutazioneCmdServiceImpl implements ValutazioneCmdService {
	
	private Log log = LogFactory.getLog(ValutazioneCmdServiceImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private ValutazioneRepository repo;
	

	@Transactional(readOnly=true)
	public Valutazione findById(Integer id) {
		return repo.findOne(id);
	}

	public Valutazione save(Valutazione arg0) {
		if (arg0.getId() == null) { // Insert 
			log.info("Inserting new Valutazione");
		} else {                       // Update 
			log.info("Updating existing Valutazione");
		}
		log.info("Valutazione saved with id: " + arg0.getId());
		return repo.save(arg0);
	}

	public void delete(Valutazione arg0) {
		log.info("Valutazione with id: " + arg0.getId() + " deleted successfully");
		repo.delete(arg0);
		
	}

}
