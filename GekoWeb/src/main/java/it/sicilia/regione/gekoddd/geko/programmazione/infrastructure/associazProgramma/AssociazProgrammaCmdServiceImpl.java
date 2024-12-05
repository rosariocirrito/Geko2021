package it.sicilia.regione.gekoddd.geko.programmazione.infrastructure.associazProgramma;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgramma;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgrammaCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgrammaRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("associazProgrammaCmdService")
@Repository
@Transactional
public class AssociazProgrammaCmdServiceImpl implements AssociazProgrammaCmdService {
	
	private Log log = LogFactory.getLog(AssociazProgrammaCmdServiceImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private AssociazProgrammaRepository repo;
	
	@Transactional(readOnly=true)
	public AssociazProgramma findById(Integer id) {
		return repo.findOne(id);
	}

	public AssociazProgramma save(AssociazProgramma arg0) {
		if (arg0.getId() == null) { // Insert 
			log.info("Inserting new AssociazProgramma");
		} else {                       // Update 
			log.info("Updating existing AssociazProgramma");
			log.info("associazProgramma saved with id: " + arg0.getId());
		}
		
		return repo.save(arg0);
	}

	public void delete(AssociazProgramma arg0) {
		log.info("AssociazProgramma with id: " + arg0.getId() + " deleted successfully");
		repo.delete(arg0);
		
	}
	
}
