package it.sicilia.regione.gekoddd.geko.pianificazione.infrastructure.obiettivoStrategico;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategico;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategicoCmdService;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategicoRepository;


@Service("obiettivoStrategicoCmdService")
@Repository
@Transactional
public class ObiettivoStrategicoCmdServiceImpl implements ObiettivoStrategicoCmdService {
	
	private Log log = LogFactory.getLog(ObiettivoStrategicoCmdServiceImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private ObiettivoStrategicoRepository repo;
	
	

	@Transactional(readOnly=true)
	public ObiettivoStrategico findById(Integer id) {
		return repo.findOne(id);
	}

	@Transactional
	public ObiettivoStrategico save(ObiettivoStrategico obiettivoStrategico) {
		if (obiettivoStrategico.getId() == null) { // Insert 
			log.info("Inserting new obiettivoStrategico");
		} else {                       // Update 
			log.info("Updating existing obiettivoStrategico");
		}
		log.info("obiettivoStrategico saved with id: " + obiettivoStrategico.getId());
		return repo.save(obiettivoStrategico);
	}

	@Transactional
	public void delete(ObiettivoStrategico obiettivoStrategico) {
		log.info("obiettivoStrategico with id: " + obiettivoStrategico.getId() + " deleted successfully");
		repo.delete(obiettivoStrategico);
		
	}


}
