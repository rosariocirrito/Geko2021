package it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi;

import java.util.ArrayList;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategico;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettiviQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettiviRepository;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("associazObiettiviQryService")
@Repository
@Transactional
public class AssociazObiettiviQryServiceImpl implements AssociazObiettiviQryService {
	
	private Log log = LogFactory.getLog(AssociazObiettiviQryServiceImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private AssociazObiettiviRepository repo;
	
	
	
	@Transactional(readOnly=true)
	public List<AssociazObiettivi> findByApicale(Obiettivo apicale) {
		return repo.findByApicale(apicale);
	}

	@Transactional(readOnly=true)
	public List<AssociazObiettivi> findByStrategico(ObiettivoStrategico strategico) {
		return repo.findByStrategico(strategico);
	}
	
	
	
}
