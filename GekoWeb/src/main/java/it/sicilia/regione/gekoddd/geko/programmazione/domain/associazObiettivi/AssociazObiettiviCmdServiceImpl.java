package it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettiviCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettiviRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("associazObiettiviCmdService")
@Repository
@Transactional
public class AssociazObiettiviCmdServiceImpl implements AssociazObiettiviCmdService {
	
	private Log log = LogFactory.getLog(AssociazObiettiviCmdServiceImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private AssociazObiettiviRepository repo;
	
	@Transactional(readOnly=true)
	public AssociazObiettivi findById(Integer id) {
		return repo.findOne(id);
	}

	public AssociazObiettivi save(AssociazObiettivi arg0) {
		if (arg0.getId() == null) { // Insert 
			log.info("Inserting new associazObiettivi");
		} else {                       // Update 
			log.info("Updating existing associazObiettivi");
		}
		log.info("associazObiettivi saved with id: " + arg0.getId());
		return repo.save(arg0);
	}

	public void delete(AssociazObiettivi arg0) {
		log.info("associazObiettivi with id: " + arg0.getId() + " deleted successfully");
		repo.delete(arg0);
		
	}
	
}
