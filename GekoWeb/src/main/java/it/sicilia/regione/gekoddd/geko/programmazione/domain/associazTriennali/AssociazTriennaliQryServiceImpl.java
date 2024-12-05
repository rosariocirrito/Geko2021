package it.sicilia.regione.gekoddd.geko.programmazione.domain.associazTriennali;

import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoPluriennale.ObiettivoPluriennale;
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


@Service("associazTriennaliQryService")
@Repository
@Transactional
public class AssociazTriennaliQryServiceImpl implements AssociazTriennaliQryService {
	
	private Log log = LogFactory.getLog(AssociazTriennaliQryServiceImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private AssociazTriennaliRepository repo;
	
	
	
	@Transactional(readOnly=true)
        @Override
	public List<AssociazTriennali> findByApicale(Obiettivo apicale) {
		return repo.findByApicale(apicale);
	}

	@Transactional(readOnly=true)
        @Override
	public List<AssociazTriennali> findByTriennale(ObiettivoPluriennale triennale) {
		return repo.findByTriennale(triennale);
	}
	
	
	
}
