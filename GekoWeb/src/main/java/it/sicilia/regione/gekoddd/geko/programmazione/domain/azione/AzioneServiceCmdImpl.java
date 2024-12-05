package it.sicilia.regione.gekoddd.geko.programmazione.domain.azione;

import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("azioneCmdService")
@Repository
@Transactional
public class AzioneServiceCmdImpl implements AzioneCmdService {
	
	private Log log = LogFactory.getLog(AzioneServiceCmdImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private AzioneRepository repo;
	

	@Transactional(readOnly=true)
	public Azione findById(Integer id) {
		return repo.findOne(id);
	}

	@Transactional
	public Azione save(Azione azione) {
		if (azione.getIdAzione() == null) { // Insert 
			log.info("Inserting new azione");
		} else {                       // Update 
			log.info("Updating existing azione");
		}
		log.info("azione saved with id: " + azione.getIdAzione());
		return repo.save(azione);
	}

	@Transactional
	public void delete(Azione azione) {
		log.info("azione with id: " + azione.getIdAzione() + " deleted successfully");
		repo.delete(azione);
		
	}

}
