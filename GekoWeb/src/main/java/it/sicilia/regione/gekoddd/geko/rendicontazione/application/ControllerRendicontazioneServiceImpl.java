package it.sicilia.regione.gekoddd.geko.rendicontazione.application;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.AzioneCmdService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.criticita.Criticita;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.criticita.CriticitaCmdService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.Documento;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.DocumentoCmdService;
import it.sicilia.regione.gekoddd.log.model.journal.Journal;
import it.sicilia.regione.gekoddd.log.model.journal.JournalService;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Cirrito
 */
@Service("ControllerRendicontazioneService")
public class ControllerRendicontazioneServiceImpl implements ControllerRendicontazioneService{

	private Log log = LogFactory.getLog(ControllerRendicontazioneServiceImpl.class);
	
	@Autowired
    private CriticitaCmdService critCmdServizi;
    @Autowired
    private JournalService journalServizi;
    @Autowired
    private DocumentoCmdService docCmdServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    @Autowired
    private AzioneCmdService actCmdServizi;
    
// ------- Casi d'uso del controller -----------------------------------------
   
    // -------------------------------------------------------------------------------
    @Transactional
    @Secured({"ROLE_CONTROLLER"})
    @Override
    public void controllerCreateCriticita(Criticita criticita) {
    	
        	critCmdServizi.save(criticita);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("crea Criticita con id: "+criticita.getId()+ ", "+criticita.getDescrizione());
	        journal.setDove("Criticita");
	        journal.setIdObj(criticita.getId());
	        journal.setPerche("controllerCreateCriticita");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        
    }
    
    @Transactional
    @Secured({"ROLE_CONTROLLER"})
    @Override
    public void controllerUpdateCriticita(Criticita criticita) {
    	
    		critCmdServizi.save(criticita);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("update Criticita con id: "+criticita.getId()+ ", "+criticita.getDescrizione());
	        journal.setDove("Criticita");
	        journal.setIdObj(criticita.getId());
	        journal.setPerche("controllerUpdateCriticita");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        
    }
    
    @Transactional
    @Secured({"ROLE_CONTROLLER"})
    @Override
    public void controllerDeleteCriticita(Criticita criticita) {
    		//
    		critCmdServizi.delete(criticita);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("delete Criticita con id: "+criticita.getId()+ ", "+criticita.getDescrizione());
	        journal.setDove("Criticita");
	        journal.setIdObj(criticita.getId());
	        journal.setPerche("controllerDeleteCriticita");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);     
    }
    

    
 // documento
    @Transactional
    @Secured({"ROLE_CONTROLLER"})
    public void controllerCreateDocumento(Documento documento) {
    	//final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(documento.getIncaricoID());
    	//final Integer dipartimentoID = (fromOrganikoServizi.findDipartimentoByPersonaFisicaID(incarico.pfID)).idPersona;;
        
        	docCmdServizi.save(documento);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("crea Documento con id: "+documento.getId()+ ", "+documento.getDescrizione());
	        journal.setDove("Documento");
	        journal.setIdObj(documento.getId());
	        journal.setPerche("controllerCreateDocumento");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
    }

    @Transactional
    @Secured({"ROLE_CONTROLLER"})
    public void controllerUpdateDocumento(Documento documento) {
    	
        	docCmdServizi.save(documento);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("update Documento con id: "+documento.getId()+ ", "+documento.getDescrizione());
	        journal.setDove("Documento");
	        journal.setIdObj(documento.getId());
	        journal.setPerche("controllerUpdateDocumento");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
    }

    @Transactional
    @Secured({"ROLE_CONTROLLER"})
    public void controllerDeleteDocumento(Documento documento) {
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("delete Documento con id: "+documento.getId()+ ", "+documento.getDescrizione());
	        journal.setDove("Documento");
	        journal.setIdObj(documento.getId());
	        journal.setPerche("controllerDeleteDocumento");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
	        docCmdServizi.delete(documento);
    }

    @Transactional
    @Secured({"ROLE_CONTROLLER"})
    public void controllerDeleteDocumentoApicale(Documento documento) {
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("delete Documento Apicalecon id: "+documento.getId()+ ", "+documento.getDescrizione());
	        journal.setDove("Documento");
	        journal.setIdObj(documento.getId());
	        journal.setPerche("controllerDeleteDocumentoApicale");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
	        docCmdServizi.delete(documento);
    }
    
	@Override
	@Transactional
    @Secured({"ROLE_CONTROLLER"})
	public void controllerEditaRisultato(Azione azione) {
	        	actCmdServizi.save(azione);
	        	//
		        Journal journal = new Journal();
		        journal.setChi(menu.getChi());
		        journal.setCosa("controllerEditaRisultato Azione Apicale con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
		        journal.setDove("Azione");
		        journal.setIdObj(azione.getIdAzione());
		        journal.setPerche("controllerEditaRisultato");
		        journal.setQuando(new Date());
		        journalServizi.save(journal);
	}
    
} // ---------------------------------------------------------------------------------
