package it.sicilia.regione.gekoddd.geko.pianificazione.infrastructure.areaStrategica;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategica;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategicaQryService;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategicaRepository;


@Service("areaStrategicaQryService")
@Repository
@Transactional
public class AreaStrategicaQryServiceImpl implements AreaStrategicaQryService{

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private AreaStrategicaRepository repo;
	
	
	

	@Transactional(readOnly=true)
	public List<AreaStrategica> findByAnno(int anno) {
		int annoMinus1 = anno -1;
		int annoPlus1 = anno +1;
		List<AreaStrategica> finale = repo.findByAnnoInizioLessThanAndAnnoFineGreaterThan(annoPlus1, annoMinus1);
		
		return finale;
	}

} // --------------------------------
