package it.sicilia.regione.gekoddd.geko.programmazione.infrastructure.outcome;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.AzioneCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.AzioneRepository;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.outcome.Outcome;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.outcome.OutcomeCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.outcome.OutcomeRepository;

import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("outcomeCmdService")
@Repository
@Transactional
public class OutcomeServiceCmdImpl implements OutcomeCmdService {
	
	private Log log = LogFactory.getLog(OutcomeServiceCmdImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private OutcomeRepository repo;
	

	@Transactional(readOnly=true)
	public Outcome findById(Integer id) {
		return repo.findOne(id);
	}

	@Transactional
	public Outcome save(Outcome outcome) {
		if (outcome.getId() == null) { // Insert 
			log.info("Inserting new Outcome");
		} else {                       // Update 
			log.info("Updating existing Outcome");
		}
		log.info("Outcome saved with id: " + outcome.getId());
		return repo.save(outcome);
	}

	@Transactional
	public void delete(Outcome outcome) {
		log.info("outcome with id: " + outcome.getId() + " deleted successfully");
		repo.delete(outcome);
		
	}

}
