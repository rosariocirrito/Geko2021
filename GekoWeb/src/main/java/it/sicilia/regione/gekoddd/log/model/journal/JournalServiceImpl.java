package it.sicilia.regione.gekoddd.log.model.journal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("journalService")
@Transactional
public class JournalServiceImpl implements JournalService {
	
	private Log log = LogFactory.getLog(JournalServiceImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private JournalRepository repo;
	
	
	

	@Transactional(readOnly=true)
	public Journal findById(Integer id) {
		return repo.findOne(id);
	}

	public Journal save(Journal arg0) {
		if (arg0.getId() == null) { // Insert 
			log.info("Inserting new Journal");
		} else {                       // Update 
			log.info("Updating existing Journal");
		}
		log.info("Journal saved with id: " + arg0.getId());
		return repo.save(arg0);
	}

	public void delete(Journal arg0) {
		log.info("Journal with id: " + arg0.getId() + " deleted successfully");
		repo.delete(arg0);
		
	}

	// -----------------------------------------------------------
	@Transactional(readOnly=true)
	public List<Journal> findByChi(String chi) {
		return repo.findByChi(chi);
	}

	//
	@Transactional(readOnly=true)
	public List<Journal> findByChiAndDove(String chi, String dove) {
		return repo.findByChiAndDove(chi, dove);
	}

	@Transactional(readOnly=true)
	public List<Journal> findByQuandoAfter(Date quando) {
		List<Journal> listaTot = repo.findByQuandoGreaterThanOrderByChiAsc(quando);
		List<Journal> lista = new ArrayList();
    	
		for(Journal item : listaTot){
			lista.add(item);
		}
		return lista;
	}

	public List<Journal> findByChiAndQuandoAfter(String chi, Date quando) {
		return repo.findByChiAndQuandoGreaterThanOrderByChiAsc(chi,quando);
	}

}
