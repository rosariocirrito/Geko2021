package it.sicilia.regione.gekoddd.geko.rendicontazione.application;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivazione.OivAzione;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivazione.OivAzioneCmdService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivobiettivo.OivObiettivo;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivobiettivo.OivObiettivoCmdService;
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
@Service("oivRendicontazioneService")
public class OivRendicontazioneServiceImpl implements OivRendicontazioneService{

	private Log log = LogFactory.getLog(OivRendicontazioneServiceImpl.class);
	
	@Autowired
    private OivAzioneCmdService critCmdServizi;
    @Autowired
    private JournalService journalServizi;
    @Autowired
    private OivObiettivoCmdService docCmdServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
// ------- Casi d'uso del controller -----------------------------------------
    
    
    
    
    
   
    
    
    
    // -------------------------------------------------------------------------------
    @Transactional
    @Secured({"ROLE_OIV"})
    public void oivCreateOivAzione(OivAzione oivAzione) {
    	
        	critCmdServizi.save(oivAzione);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("crea OivAzione con id: "+oivAzione.getId());
	        journal.setDove("OivAzione");
	        journal.setIdObj(oivAzione.getId());
	        journal.setPerche("controllerCreateOivAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        
    }
    @Transactional
    @Secured({"ROLE_OIV"})
    public void oivUpdateOivAzione(OivAzione oivAzione) {
    	
    		critCmdServizi.save(oivAzione);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("update OivAzione con id: "+oivAzione.getId()+ ", "+oivAzione.getId());
	        journal.setDove("OivAzione");
	        journal.setIdObj(oivAzione.getId());
	        journal.setPerche("controllerUpdateOivAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        
    }
    @Transactional
    @Secured({"ROLE_OIV"})
    public void oivDeleteOivAzione(OivAzione oivAzione) {
    	
    		critCmdServizi.delete(oivAzione);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("delete OivAzione con id: "+oivAzione.getId()+ ", "+oivAzione.getId());
	        journal.setDove("OivAzione");
	        journal.setIdObj(oivAzione.getId());
	        journal.setPerche("controllerDeleteOivAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
	        
        
    }
    
    
 
    
	
	
    
 // oivObiettivo
    @Transactional
    @Secured({"ROLE_OIV"})
    public void oivCreateOivObiettivo(OivObiettivo oivObiettivo) {
    	//final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(oivObiettivo.getIncaricoID());
    	//final Integer dipartimentoID = (fromOrganikoServizi.findDipartimentoByPersonaFisicaID(incarico.pfID)).idPersona;;
        //if (oivObiettivo.getAzione().getObiettivo().getStatoApprov().equals(Obiettivo.StatoApprovEnum.DEFINITIVO))
        	docCmdServizi.save(oivObiettivo);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("crea OivObiettivo con id: "+oivObiettivo.getId());
	        journal.setDove("OivObiettivo");
	        journal.setIdObj(oivObiettivo.getId());
	        journal.setPerche("controllerCreateOivObiettivo");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
    }

    @Transactional
    @Secured({"ROLE_OIV"})
    public void oivUpdateOivObiettivo(OivObiettivo oivObiettivo) {
    	//if (oivObiettivo.getAzione().getObiettivo().getStatoApprov().equals(Obiettivo.StatoApprovEnum.DEFINITIVO))
        	docCmdServizi.save(oivObiettivo);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("update OivObiettivo con id: "+oivObiettivo.getId());
	        journal.setDove("OivObiettivo");
	        journal.setIdObj(oivObiettivo.getId());
	        journal.setPerche("controllerUpdateOivObiettivo");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
    }

    @Transactional
    @Secured({"ROLE_OIV"})
    public void oivDeleteOivObiettivo(OivObiettivo oivObiettivo) {
    	
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("delete OivObiettivo con id: "+oivObiettivo.getId());
	        journal.setDove("OivObiettivo");
	        journal.setIdObj(oivObiettivo.getId());
	        journal.setPerche("controllerDeleteOivObiettivo");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
	        docCmdServizi.delete(oivObiettivo);
        
    }

    
} // ---------------------------------------------------------------------------------
