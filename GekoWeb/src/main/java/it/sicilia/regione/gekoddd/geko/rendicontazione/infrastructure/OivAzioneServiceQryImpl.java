package it.sicilia.regione.gekoddd.geko.rendicontazione.infrastructure;

import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivazione.OivAzione;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivazione.OivAzioneQryService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivazione.OivAzioneRepository;

import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("oivAzioneQryService")
@Repository
@Transactional
public class OivAzioneServiceQryImpl implements OivAzioneQryService {
	
	private Log log = LogFactory.getLog(OivAzioneServiceQryImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private OivAzioneRepository repo;
	
	

}
