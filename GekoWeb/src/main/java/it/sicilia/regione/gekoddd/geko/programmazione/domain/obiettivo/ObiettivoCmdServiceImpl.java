package it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.AzioneRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("obiettivoCmdService")
@Repository
@Transactional
public class ObiettivoCmdServiceImpl implements ObiettivoCmdService{
	
	private Log log = LogFactory.getLog(ObiettivoCmdServiceImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private ObiettivoRepository repo;
	@Autowired 
	private AzioneRepository repoAzione;

	@Transactional(readOnly=true)
	public Obiettivo findById(Integer id) {
		return repo.findOne(id);
	}

	@Transactional
	public Obiettivo save(Obiettivo obiettivo) {
		if (obiettivo.getIdObiettivo() == null) { 
			// Insert 
			log.info("Inserting new obiettivo");
			
		} else {                       
			// Update 
			log.info("Updating existing obiettivo");
		}
		log.info("obiettivo saved with id: " + obiettivo.getIdObiettivo());
		return repo.save(obiettivo);
	}

	@Transactional
	public void delete(Obiettivo obiettivo) {
		for (Azione act : obiettivo.getAzioni()) {
			repoAzione.delete(act);
		}
		repo.delete(obiettivo);
		log.info("Obiettivo with id: " + obiettivo.getIdObiettivo() + " deleted successfully");
		
	}

	

	
} // --------------------------------
