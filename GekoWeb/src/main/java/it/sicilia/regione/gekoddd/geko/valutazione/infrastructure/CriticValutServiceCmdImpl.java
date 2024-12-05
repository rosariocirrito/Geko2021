package it.sicilia.regione.gekoddd.geko.valutazione.infrastructure;

import it.sicilia.regione.gekoddd.geko.valutazione.domain.criticValut.CriticValut;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.criticValut.CriticValutCmdService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.criticValut.CriticValutRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("criticValutCmdService")
@Repository
@Transactional
public class CriticValutServiceCmdImpl implements CriticValutCmdService {
	
	private Log log = LogFactory.getLog(CriticValutServiceCmdImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private CriticValutRepository repo;

	@Transactional(readOnly=true)
	public CriticValut findById(Integer id) {
		return repo.findOne(id);
	}

	@Transactional
	public CriticValut save(CriticValut arg0) {
		if (arg0.getId() == null) { // Insert 
			log.info("Inserting new CriticValut");
		} else {                       // Update 
			log.info("Updating existing CriticValut");
		}
		log.info("CriticValut saved with id: " + arg0.getId());
		return repo.save(arg0);
	}

	@Transactional
	public void delete(CriticValut arg0) {
		log.info("CriticValut with id: " + arg0.getId() + " deleted successfully");
		System.out.println("CriticValut with id: " + arg0.getId() + " deleted successfully");
		repo.delete(arg0);
		
	}

}
