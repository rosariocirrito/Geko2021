package it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione.ObiettivoAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione.ObiettivoAssegnazioneCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione.ObiettivoAssegnazioneRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("obiettivoAssegnazioneCmdService")
@Repository
@Transactional
public class ObiettivoAssegnazioneCmdServiceImpl implements ObiettivoAssegnazioneCmdService {
	
	private Log log = LogFactory.getLog(ObiettivoAssegnazioneCmdServiceImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private ObiettivoAssegnazioneRepository repo;

	@Transactional(readOnly=true)
	public ObiettivoAssegnazione findById(Integer id) {
		return repo.findOne(id);
	}

	@Transactional
	public ObiettivoAssegnazione save(ObiettivoAssegnazione arg0) {
		if (arg0.getId() == null) { // Insert 
			log.info("Inserting new obiettivoAssegnazione");
		} else {                       // Update 
			log.info("Updating existing obiettivoAssegnazione");
		}
		log.info("obiettivoAssegnazione saved with id: " + arg0.getId());
		return repo.save(arg0);
	}

	@Transactional
	public void delete(ObiettivoAssegnazione arg0) {
		log.info("obiettivoAssegnazione with id: " + arg0.getId() + " deleted successfully");
		repo.delete(arg0);
		
	}
	
}
