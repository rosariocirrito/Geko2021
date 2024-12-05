package it.sicilia.regione.gekoddd.geko.programmazione.infrastructure.associazProgramma;

import java.util.ArrayList;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgramma;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgrammaQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgrammaRepository;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.programma.Programma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("associazProgrammaQryService")
@Repository
@Transactional
public class AssociazProgrammaQryServiceImpl implements AssociazProgrammaQryService {
	
	private Log log = LogFactory.getLog(AssociazProgrammaQryServiceImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private AssociazProgrammaRepository repo;
	
	
	@Override
	@Transactional(readOnly=true)
	public List<AssociazProgramma> findByApicale(Obiettivo apicale) {
		return repo.findByApicale(apicale);
	}

	@Override
	@Transactional(readOnly=true)
	public List<AssociazProgramma> findByProgramma(Programma programma) {
		return repo.findByProgramma(programma);
	}
	
	
	
}
