package it.sicilia.regione.gekoddd.geko.rendicontazione.infrastructure;

import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.criticita.Criticita;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.criticita.CriticitaQryService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.criticita.CriticitaRepository;

import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("criticitaQryService")
@Repository
@Transactional
public class CriticitaServiceQryImpl implements CriticitaQryService {
	
	private Log log = LogFactory.getLog(CriticitaServiceQryImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private CriticitaRepository repo;
	
	

}
