package it.sicilia.regione.gekoddd.geko.programmazione.infrastructure.missione;


import it.sicilia.regione.gekoddd.geko.programmazione.domain.missione.Missione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.missione.MissioneCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.missione.MissioneRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("missioneCmdService")
@Repository
@Transactional
public class MissioneCmdServiceImpl implements MissioneCmdService{
	
	private Log log = LogFactory.getLog(MissioneCmdServiceImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private MissioneRepository repo;
	
	
	@Transactional(readOnly=true)
	public Missione findById(Integer id) {
		return repo.findOne(id);
	}

	@Transactional
	public Missione save(Missione missione) {
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
	public void delete(Missione missione) {
		repo.delete(missione);
		log.info("Missione with id: " + missione.getId() + " deleted successfully");
		
	}
	


} // --------------------------------
