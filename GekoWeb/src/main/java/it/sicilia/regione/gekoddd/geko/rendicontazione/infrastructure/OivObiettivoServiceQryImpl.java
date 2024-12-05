package it.sicilia.regione.gekoddd.geko.rendicontazione.infrastructure;

import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivobiettivo.OivObiettivoQryService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivobiettivo.OivObiettivoRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("oivObiettivoQryService")
@Repository
@Transactional
public class OivObiettivoServiceQryImpl implements OivObiettivoQryService {
	
	private Log log = LogFactory.getLog(OivObiettivoServiceQryImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private OivObiettivoRepository repo;
	
	

}
