package it.sicilia.regione.gekoddd.geko.rendicontazione.infrastructure;

import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.criticita.Criticita;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.criticita.CriticitaCmdService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.criticita.CriticitaRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("criticitaCmdService")
@Repository
@Transactional
public class CriticitaServiceCmdImpl implements CriticitaCmdService {
	
	private Log log = LogFactory.getLog(CriticitaServiceCmdImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private CriticitaRepository repo;

	@Transactional(readOnly=true)
	public Criticita findById(Integer id) {
		return repo.findOne(id);
	}

	@Transactional
	public Criticita save(Criticita arg0) {
		if (arg0.getId() == null) { // Insert 
			log.info("Inserting new Criticita");
		} else {                       // Update 
			log.info("Updating existing Criticita");
		}
		log.info("Criticita saved with id: " + arg0.getId());
		return repo.save(arg0);
	}

	@Transactional
	public void delete(Criticita arg0) {
		log.info("Criticita with id: " + arg0.getId() + " deleted successfully");
		System.out.println("Criticita with id: " + arg0.getId() + " deleted successfully (forse)");
		repo.delete(arg0);
		
	}

}
