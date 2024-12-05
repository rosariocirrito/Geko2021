package it.sicilia.regione.gekoddd.security.infrastructure;

import it.sicilia.regione.gekoddd.security.domain.entity.AuthorityTypeSecur;
import it.sicilia.regione.gekoddd.security.domain.repository.AuthorityTypeRepository;
import it.sicilia.regione.gekoddd.security.domain.services.AuthorityTypeService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("authorityTypeService")
@Repository
@Transactional
public class AuthorityTypeServiceImpl implements AuthorityTypeService {
	
	private Log log = LogFactory.getLog(AuthorityTypeServiceImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private AuthorityTypeRepository repo;

	@Transactional(readOnly=true)
	public AuthorityTypeSecur findById(Integer id) {
		return repo.findOne(id);
	}

	public AuthorityTypeSecur save(AuthorityTypeSecur arg0) {
		if (arg0.getIdauthorityType() == null) { // Insert 
			log.info("Inserting new AuthorityType");
		} else {                       // Update 
			log.info("Updating existing AuthorityType");
		}
		log.info("authorityType saved with id: " + arg0.getIdauthorityType());
		return repo.save(arg0);
	}

	public void delete(AuthorityTypeSecur arg0) {
		log.info("authorityType with id: " + arg0.getIdauthorityType() + " deleted successfully");
		repo.delete(arg0);
		
	}

}
