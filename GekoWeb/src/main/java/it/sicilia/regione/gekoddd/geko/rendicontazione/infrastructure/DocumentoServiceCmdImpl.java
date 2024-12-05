package it.sicilia.regione.gekoddd.geko.rendicontazione.infrastructure;


import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.Documento;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.DocumentoCmdService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.DocumentoRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("documentoCmdService")
@Repository
@Transactional
public class DocumentoServiceCmdImpl implements DocumentoCmdService {
	
	private Log log = LogFactory.getLog(DocumentoServiceCmdImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private DocumentoRepository repo;

	@Transactional(readOnly=true)
	public Documento findById(Integer id) {
		return repo.findOne(id);
	}

	@Transactional
	public Documento save(Documento arg0) {
		if (arg0.getId() == null) { // Insert 
			log.info("Inserting new Documento");
		} else {                       // Update 
			log.info("Updating existing Documento");
		}
		log.info("Documento saved with id: " + arg0.getId());
		return repo.save(arg0);
	}

	@Transactional
	public void delete(Documento arg0) {
		log.info("Documento with id: " + arg0.getId() + " deleted successfully, davvero? ");
		repo.delete(arg0);
		
	}

}
