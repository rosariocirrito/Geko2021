package it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione.ValutazioneEnum;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("azioneAssegnazioneCmdService")
@Repository
@Transactional
public class AzioneAssegnazioneCmdServiceImpl implements AzioneAssegnazioneCmdService {
	
	private Log log = LogFactory.getLog(AzioneAssegnazioneCmdServiceImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private AzioneAssegnazioneRepository repo;

	@Transactional(readOnly=true)
	public AzioneAssegnazione findById(Integer id) {
		return repo.findOne(id);
	}

	@Transactional
	public AzioneAssegnazione save(AzioneAssegnazione arg0) {
		if (arg0.getId() == null) { // Insert 
			log.info("Inserting new azioneAssegnazione");
			arg0.setValutazione(ValutazioneEnum.DA_VALUTARE);
		} else {                       // Update 
			log.info("Updating existing azioneAssegnazione");
		}
		log.info("azioneAssegnazione saved with id: " + arg0.getId());
		return repo.save(arg0);
	}

	@Transactional
	public void delete(AzioneAssegnazione arg0) {
		log.info("azioneAssegnazione with id: " + arg0.getId() + " deleted successfully");
		repo.delete(arg0);
		
	}
	
}
