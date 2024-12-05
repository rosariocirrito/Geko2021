package it.sicilia.regione.gekoddd.geko.rendicontazione.application;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.Documento;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.DocumentoCmdService;
import it.sicilia.regione.gekoddd.log.model.journal.Journal;
import it.sicilia.regione.gekoddd.log.model.journal.JournalService;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ManagerRendicontazioneService")
public class ManagerRendicontazioneServiceImpl implements ManagerRendicontazioneService{
	
	private Log log = LogFactory.getLog(ManagerRendicontazioneServiceImpl.class);

	@Autowired
    private DocumentoCmdService docCmdServizi;
    @Autowired
    private JournalService journalServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    // ------- Casi d'uso del manager -----------------------------------------
    
    

    // -------------- AZIONI ------------------------------------------------------------------
    
    // documento
    @Transactional
    //@Secured({"ROLE_MANAGER"})
    public void managerCreateDocumento(Documento documento) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(documento.getIncaricoID());
    	//
    	log.info("menu.getIdIncaricoScelta()"+menu.getIdIncaricoScelta()+" / "+"documento.getIncaricoID()"+documento.getIncaricoID());
    	
        	docCmdServizi.save(documento);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("crea Documento con id: "+documento.getId()+ ", "+documento.getDescrizione());
	        journal.setDove("Documento");
	        journal.setIdObj(documento.getId());
	        journal.setPerche("managerCreateDocumento");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
       
    }

    @Transactional
    //@Secured({"ROLE_MANAGER"})
    public void managerUpdateDocumento(Documento documento) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(documento.getIncaricoID());
    	//
    	log.info("menu.getIdIncaricoScelta()"+menu.getIdIncaricoScelta()+" / "+"documento.getIncaricoID()"+documento.getIncaricoID());
    	
        	docCmdServizi.save(documento);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("update Documento con id: "+documento.getId()+ ", "+documento.getDescrizione());
	        journal.setDove("Documento");
	        journal.setIdObj(documento.getId());
	        journal.setPerche("managerUpdateDocumento");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
      
    }

    @Transactional
    //@Secured({"ROLE_MANAGER"})
    public void managerDeleteDocumento(Documento documento) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(documento.getIncaricoID());
    	//
    	log.info("menu.getIdIncaricoScelta()"+menu.getIdIncaricoScelta()+" / "+"documento.getIncaricoID()"+documento.getIncaricoID());
    	
        	docCmdServizi.delete(documento);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("delete Documento con id: "+documento.getId()+ ", "+documento.getDescrizione());
	        journal.setDove("Documento");
	        journal.setIdObj(0);
	        journal.setPerche("managerDeleteDocumento");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        
    }
    
    
 
    
} // -------------------------------------------------------

