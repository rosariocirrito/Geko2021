package it.sicilia.regione.gekoddd.geko.pianificazione.infrastructure.obiettivoPluriennale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoPluriennale.ObiettivoPluriennale;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoPluriennale.ObiettivoPluriennaleCmdService;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoPluriennale.ObiettivoPluriennaleRepository;


@Service("obiettivoPluriennaleCmdService")
@Repository
@Transactional
public class ObiettivoPluriennaleCmdServiceImpl implements ObiettivoPluriennaleCmdService {
	
	private Log log = LogFactory.getLog(ObiettivoPluriennaleCmdServiceImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private ObiettivoPluriennaleRepository repo;
	
	

	@Transactional(readOnly=true)
	public ObiettivoPluriennale findById(Integer id) {
		return repo.findOne(id);
	}

	@Transactional
	public ObiettivoPluriennale save(ObiettivoPluriennale obiettivoPluriennale) {
		if (obiettivoPluriennale.getId() == null) { // Insert 
			log.info("Inserting new obiettivoPluriennale");
		} else {                       // Update 
			log.info("Updating existing obiettivoPluriennale");
		}
		log.info("obiettivoPluriennale saved with id: " + obiettivoPluriennale.getId());
		return repo.save(obiettivoPluriennale);
	}

	@Transactional
	public void delete(ObiettivoPluriennale obiettivoPluriennale) {
		log.info("obiettivoPluriennale with id: " + obiettivoPluriennale.getId() + " deleted successfully");
		repo.delete(obiettivoPluriennale);
		
	}


}
