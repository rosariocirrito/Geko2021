package it.sicilia.regione.gekoddd.geko.programmazione.infrastructure.programma;


import it.sicilia.regione.gekoddd.geko.programmazione.domain.programma.Programma;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.programma.ProgrammaCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.programma.ProgrammaRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("programmaCmdService")
@Repository
@Transactional
public class ProgrammaCmdServiceImpl implements ProgrammaCmdService{
	
	private Log log = LogFactory.getLog(ProgrammaCmdServiceImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private ProgrammaRepository repo;
	
	
	@Transactional(readOnly=true)
	public Programma findById(Integer id) {
		return repo.findOne(id);
	}

	@Transactional
	public Programma save(Programma missione) {
		if (missione.getId() == null) { 
			// Insert 
			log.info("Inserting new missione");
			
		} else {                       
			// Update 
			log.info("Updating existing missione");
		}
		log.info("missione saved with id: " + missione.getId());
		return repo.save(missione);
	}

	@Transactional
	public void delete(Programma missione) {
		repo.delete(missione);
		log.info("Programma with id: " + missione.getId() + " deleted successfully");
		
	}
	


} // --------------------------------
