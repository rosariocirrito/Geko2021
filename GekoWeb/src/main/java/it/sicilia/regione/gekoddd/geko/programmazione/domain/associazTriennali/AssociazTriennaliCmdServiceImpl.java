package it.sicilia.regione.gekoddd.geko.programmazione.domain.associazTriennali;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.*;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettiviCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettiviRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("associazTriennaliCmdService")
@Repository
@Transactional
public class AssociazTriennaliCmdServiceImpl implements AssociazTriennaliCmdService {
	
	private Log log = LogFactory.getLog(AssociazTriennaliCmdServiceImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private AssociazTriennaliRepository repo;
	
	@Transactional(readOnly=true)
	public AssociazTriennali findById(Integer id) {
		return repo.findOne(id);
	}

	public AssociazTriennali save(AssociazTriennali arg0) {
		if (arg0.getId() == null) { // Insert 
			log.info("Inserting new AssociazTriennali");
		} else {                       // Update 
			log.info("Updating existing AssociazTriennali");
		}
		log.info("AssociazTriennali saved with id: " + arg0.getId());
		return repo.save(arg0);
	}

	public void delete(AssociazTriennali arg0) {
		log.info("AssociazTriennali with id: " + arg0.getId() + " deleted successfully");
		repo.delete(arg0);
		
	}
	
}
