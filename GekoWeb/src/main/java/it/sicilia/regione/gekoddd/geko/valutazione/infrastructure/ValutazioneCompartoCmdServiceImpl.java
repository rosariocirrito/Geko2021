package it.sicilia.regione.gekoddd.geko.valutazione.infrastructure;

import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneComparto;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneCompartoRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("valutazioneCompartoCmdService")
@Repository
@Transactional
public class ValutazioneCompartoCmdServiceImpl implements ValutazioneCompartoCmdService {
	
	private Log log = LogFactory.getLog(ValutazioneCompartoCmdServiceImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private ValutazioneCompartoRepository repo;

	@Transactional(readOnly=true)
	public ValutazioneComparto findById(Integer id) {
		return repo.findOne(id);
	}

	@Transactional
	public ValutazioneComparto save(ValutazioneComparto arg0) {
		if (arg0.getId() == null) { // Insert 
			log.info("Inserting new Valutazione");
		} else {                       // Update 
			log.info("Updating existing Valutazione");
		}
		log.info("Valutazione saved with id: " + arg0.getId());
		return repo.save(arg0);
	}

	@Transactional
	public void delete(ValutazioneComparto arg0) {
		log.info("Valutazione with id: " + arg0.getId() + " deleted successfully");
		repo.delete(arg0);
		
	}

} 
