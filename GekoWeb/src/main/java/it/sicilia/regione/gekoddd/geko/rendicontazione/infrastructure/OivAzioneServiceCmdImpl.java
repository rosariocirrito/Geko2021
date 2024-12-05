package it.sicilia.regione.gekoddd.geko.rendicontazione.infrastructure;

import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivazione.OivAzione;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivazione.OivAzioneCmdService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivazione.OivAzioneRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("oivAzioneCmdService")
@Repository
@Transactional
public class OivAzioneServiceCmdImpl implements OivAzioneCmdService {
	
	private Log log = LogFactory.getLog(OivAzioneServiceCmdImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private OivAzioneRepository repo;

	@Transactional(readOnly=true)
	public OivAzione findById(Integer id) {
		return repo.findOne(id);
	}

	@Transactional
	public OivAzione save(OivAzione arg0) {
		if (arg0.getId() == null) { // Insert 
			log.info("Inserting new OivAzione");
		} else {                       // Update 
			log.info("Updating existing OivAzione");
		}
		log.info("OivAzione saved with id: " + arg0.getId());
		return repo.save(arg0);
	}

	@Transactional
	public void delete(OivAzione arg0) {
		log.info("OivAzione with id: " + arg0.getId() + " deleted successfully");
		System.out.println("OivAzione with id: " + arg0.getId() + " deleted successfully (forse)");
		repo.delete(arg0);
		
	}

}
