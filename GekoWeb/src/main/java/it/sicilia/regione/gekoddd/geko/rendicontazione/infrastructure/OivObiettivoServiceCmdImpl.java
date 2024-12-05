package it.sicilia.regione.gekoddd.geko.rendicontazione.infrastructure;

import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivobiettivo.OivObiettivo;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivobiettivo.OivObiettivoCmdService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivobiettivo.OivObiettivoRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("oivObiettivoCmdService")
@Repository
@Transactional
public class OivObiettivoServiceCmdImpl implements OivObiettivoCmdService {
	
	private Log log = LogFactory.getLog(OivObiettivoServiceCmdImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private OivObiettivoRepository repo;

	@Transactional(readOnly=true)
	public OivObiettivo findById(Integer id) {
		return repo.findOne(id);
	}

	@Transactional
	public OivObiettivo save(OivObiettivo arg0) {
		if (arg0.getId() == null) { // Insert 
			log.info("Inserting new OivObiettivo");
		} else {                       // Update 
			log.info("Updating existing OivObiettivo");
		}
		log.info("OivObiettivo saved with id: " + arg0.getId());
		return repo.save(arg0);
	}

	@Transactional
	public void delete(OivObiettivo arg0) {
		log.info("OivObiettivo with id: " + arg0.getId() + " deleted successfully");
		System.out.println("OivObiettivo with id: " + arg0.getId() + " deleted successfully (forse)");
		repo.delete(arg0);
		
	}

}
