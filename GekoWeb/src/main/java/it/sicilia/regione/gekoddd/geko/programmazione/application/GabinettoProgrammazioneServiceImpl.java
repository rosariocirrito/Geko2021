package it.sicilia.regione.gekoddd.geko.programmazione.application;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettiviCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgramma;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgrammaCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoCmdService;
import it.sicilia.regione.gekoddd.log.model.evento.Evento;
import it.sicilia.regione.gekoddd.log.model.evento.EventoService;
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
@Service("GabinettoProgrammazioneService")
public class GabinettoProgrammazioneServiceImpl implements GabinettoProgrammazioneService{

	private Log log = LogFactory.getLog(GabinettoProgrammazioneServiceImpl.class);
	
	@Autowired
    private ObiettivoCmdService objCmdServizi;
	
	@Autowired
    private AssociazObiettiviCmdService associazObiettiviServizi;
	@Autowired
    private AssociazProgrammaCmdService associazProgrammaServizi;
	
    @Autowired
    private JournalService journalServizi;
    @Autowired
    private EventoService evtServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    

	
   
    
// ------- Casi d'uso del controller -----------------------------------------
    
    
   
    // 1.b Richiede Obiettivo 
    // richiedi obiettivo solo se � nello stato Interlocutorio
    @Transactional
    @Secured({"ROLE_GABINETTO"})
    @Override
    public void gabinettoRichiedeObiettivo(Obiettivo obiettivo) {
    	
        
            objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("richiede Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("gabinettoRichiedeObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
       
    }
    
   
    // 2. Modifica Obiettivo 
    // aggiorna obiettivo solo se � nello stato INTERLOCUTORIO, PROPOSTO o RICHIESTO
    @Transactional
    @Secured({"ROLE_GABINETTO"})
    @Override
    public void gabinettoUpdateObiettivo(Obiettivo obiettivo) {
    	
        //if (menu.getDipartimento().idPersona==dipartimentoID){
        	objCmdServizi.save(obiettivo);
        	Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("Update Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("gabinettoUpdateObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
        //}  
    }
    
    @Override
	@Transactional
    @Secured({"ROLE_GABINETTO"})
	public Evento cmdGabinettoConcordaObiettivoApicale(Obiettivo obiettivo) {
		final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    		obiettivo.gabinettoConcorda();
    		objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("Gabinetto concorda Obiettivo Apicale con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("gabinettoConcordaObiettivoApicale");
            journal.setQuando(new Date());
            journalServizi.save(journal);
            //
    		final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
	    			"cmdGabinettoConcordaObiettivoApicale", 
	    			"obiettivo apicale: "+obiettivo.toString(),
	    			Evento.TipoEnum.SUCCESS,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
    		evtServizi.save(newEvtOk);
    		return newEvtOk;
	}
    
 // 6. Respingi Obiettivo 
    // valida obiettivo solo se � nello stato PROPOSTO
    @Transactional
    @Secured({"ROLE_GABINETTO"})
    @Override
    public void gabinettoRespingeObiettivo(Obiettivo obiettivo) {
    	    
            objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("respingi Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("gabinettoRespingiObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
    }  
   
    @Override
    @Transactional
    @Secured({"ROLE_GABINETTO"})
    public void gabinettoDeleteObiettivo(Obiettivo obiettivo) {
    	
        /*if ( obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.INTERLOCUTORIO) ||
        		obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.RICHIESTO)
        		)  {*/
        	objCmdServizi.delete(obiettivo);
        	Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("delete Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("gabinettoDeleteObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
        //}  
    }
    
    //
    @Transactional
    @Secured({"ROLE_GABINETTO"})
    @Override
    public void gabinettoCreateAssociazObiettivi(AssociazObiettivi associazObiettivi) {
        
        	associazObiettiviServizi.save(associazObiettivi);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Create AssociazObiettivi con id: "+associazObiettivi.getId()
	        		);
	        journal.setDove("AssociazObiettivi");
	        journal.setIdObj(associazObiettivi.getId());
	        journal.setPerche("controllerCreateAssociazObiettivi");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        
    }

    @Transactional
    @Secured({"ROLE_GABINETTO"})
    @Override
    public void gabinettoUpdateAssociazObiettivi(AssociazObiettivi associazObiettivi) {
        
        	associazObiettiviServizi.save(associazObiettivi);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Update AssociazObiettivi con id: "+associazObiettivi.getId()
	        		);
	        journal.setDove("AssociazObiettivi");
	        journal.setIdObj(associazObiettivi.getId());
	        journal.setPerche("controllerUpdateAssociazObiettivi");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        
    }

    @Transactional
    @Secured({"ROLE_GABINETTO"})
    @Override
    public void gabinettoDeleteAssociazObiettivi(AssociazObiettivi associazObiettivi) {
    	
    		associazObiettiviServizi.delete(associazObiettivi);
        
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Delete AssociazObiettivi con id: "+associazObiettivi.getId()
	        		);
	        journal.setDove("AssociazObiettivi");
	        journal.setIdObj(associazObiettivi.getId());
	        journal.setPerche("controllerDeleteAssociazObiettivi");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        
    }

    //
    @Override
    @Transactional
    @Secured({"ROLE_GABINETTO"})
    public void gabinettoCreateAssociazProgramma(AssociazProgramma associazProgramma) {
        
        	associazProgrammaServizi.save(associazProgramma);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Create AssociazProgramma con id: "+associazProgramma.getId()
	        		);
	        journal.setDove("AssociazProgramma");
	        journal.setIdObj(associazProgramma.getId());
	        journal.setPerche("controllerCreateAssociazProgramma");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        
    }

    @Override
    @Transactional
    @Secured({"ROLE_GABINETTO"})
    public void gabinettoUpdateAssociazProgramma(AssociazProgramma associazProgramma) {
        
    	associazProgrammaServizi.save(associazProgramma);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Update AssociazProgramma con id: "+associazProgramma.getId()
	        		);
	        journal.setDove("AssociazProgramma");
	        journal.setIdObj(associazProgramma.getId());
	        journal.setPerche("controllerCreateAssociazProgramma");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        
    }

    @Override
    @Transactional
    @Secured({"ROLE_GABINETTO"})
    public void gabinettoDeleteAssociazProgramma(AssociazProgramma associazProgramma) {
    	
    	associazProgrammaServizi.delete(associazProgramma);
        
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Delete AssociazProgramma con id: "+associazProgramma.getId());
	        journal.setDove("AssociazProgramma");
	        journal.setIdObj(associazProgramma.getId());
	        journal.setPerche("controllerCreateAssociazProgramma");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        
    }
    
    @Override
	@Transactional
    @Secured({"ROLE_GABINETTO"})
    public Evento cmdGabinettoRendiInterlocutorioObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    	
    		//
    		obiettivo.controllerRendiInterlocutorio();
    		objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("Rendi Interlocutorio Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("controllerRendiInterlocutorio");
            journal.setQuando(new Date());
            journalServizi.save(journal);
            //
    		final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
	    			"cmdControllerRendiInterlocutorioObiettivo", 
	    			"obiettivo: "+obiettivo.toString(),
	    			Evento.TipoEnum.SUCCESS,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
    		evtServizi.save(newEvtOk);
    		return newEvtOk;
    	}
   
   
} // ---------------------------------------------------------------------------------
