package it.sicilia.regione.gekoddd.geko.pianificazione.infrastructure.areaStrategica;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategica;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategicaCmdService;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategicaRepository;


@Service("areaStrategicaCmdService")
@Repository
@Transactional
public class AreaStrategicaCmdServiceImpl implements AreaStrategicaCmdService{
	
	private Log log = LogFactory.getLog(AreaStrategicaCmdServiceImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private AreaStrategicaRepository repo;
	
	
	@Transactional(readOnly=true)
	public AreaStrategica findById(Integer id) {
		return repo.findOne(id);
	}

	@Transactional
	public AreaStrategica save(AreaStrategica areaStrategica) {
		if (areaStrategica.getId() == null) { 
			// Insert 
			log.info("Inserting new areaStrategica");
			
		} else {                       
			// Update 
			log.info("Updating existing areaStrategica");
		}
		log.info("areaStrategica saved with id: " + areaStrategica.getId());
		return repo.save(areaStrategica);
	}

	@Transactional
	public void delete(AreaStrategica areaStrategica) {
		repo.delete(areaStrategica);
		log.info("AreaStrategica with id: " + areaStrategica.getId() + " deleted successfully");
		
	}
	


} // --------------------------------
