package it.sicilia.regione.gekoddd.security.infrastructure;

import it.sicilia.regione.gekoddd.security.domain.entity.AuthoritySecur;
import it.sicilia.regione.gekoddd.security.domain.repository.AuthorityRepository;
import it.sicilia.regione.gekoddd.security.domain.services.AuthorityCmdService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("authorityCmdService")
@Repository
@Transactional
public class AuthorityCmdServiceImpl implements AuthorityCmdService {
	
	private Log log = LogFactory.getLog(AuthorityCmdServiceImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private AuthorityRepository repo;
	
	

	@Transactional(readOnly=true)
	public AuthoritySecur findById(Integer id) {
		return repo.findOne(id);
	}

	public AuthoritySecur save(AuthoritySecur arg0) {
		if (arg0.getId() == null) { // Insert 
			log.info("Inserting new Authority");
		} else {                       // Update 
			log.info("Updating existing Authority");
		}
		log.info("authority saved with id: " + arg0.getId());
		return repo.save(arg0);
	}

	public void delete(AuthoritySecur arg0) {
		log.info("authority with id: " + arg0.getId() + " deleted successfully");
		repo.delete(arg0);
		
	}

}
