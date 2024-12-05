package it.sicilia.regione.gekoddd.geko.programmazione.application;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettiviCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgramma;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgrammaCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.AzioneCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione.CompletamentoEnum;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazioneCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione.ObiettivoAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione.ObiettivoAssegnazioneCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.programma.Programma;
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
@Service("ControllerProgrammazioneService")
public class ControllerProgrammazioneServiceImpl implements ControllerProgrammazioneService{

	private Log log = LogFactory.getLog(ControllerProgrammazioneServiceImpl.class);
	
	@Autowired
    private ObiettivoCmdService objCmdServizi;
	
	@Autowired
    private AzioneCmdService actCmdServizi;
	@Autowired
    private AzioneAssegnazioneCmdService actAssCmdServizi;
	@Autowired
    private ObiettivoAssegnazioneCmdService objAssCmdServizi;
    @Autowired
    private AssociazObiettiviCmdService associazObiettiviServizi;
    @Autowired
    private AssociazProgrammaCmdService associazProgrammaServizi;
    @Autowired
    private AzioneAssegnazioneCmdService actAssServizi;
    
    @Autowired
    private JournalService journalServizi;
    @Autowired
    private EventoService evtServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
// ------- Casi d'uso del controller -----------------------------------------
    
    // 1. Crea Obiettivo (con azione di default)
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerProponeObiettivo(Obiettivo obiettivo) {
    	
        //if (menu.getDipartimento().idPersona==dipartimentoID){
            objCmdServizi.save(obiettivo);
            //
        	Azione defaultAzione = new Azione();
        	defaultAzione.setObiettivo(obiettivo);
        	defaultAzione.setDenominazione(obiettivo.getCodice()+"_");
        	defaultAzione.setDescrizione("azione di default per "+obiettivo.getCodice());
        	defaultAzione.setPeso(0);
        	defaultAzione.setProdotti("?");
        	defaultAzione.setCompletamento(CompletamentoEnum.DA_VALUTARE);
        	actCmdServizi.save(defaultAzione);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("add Obiettivo proposto con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("controllerProponiObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
       // }
    }
    
    // 1.b Richiede Obiettivo 
    // richiedi obiettivo solo se � nello stato Interlocutorio
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerRichiedeObiettivo(Obiettivo obiettivo) {
    	
        //if ( obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.INTERLOCUTORIO)){
        //	obiettivo.setStatoApprov(Obiettivo.StatoApprovEnum.RICHIESTO);
            objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("richiede Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("controllerRichiedeObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
       // }  
    }
    
    // 2. Modifica Obiettivo 
    // aggiorna obiettivo solo se � nello stato INTERLOCUTORIO, PROPOSTO o RICHIESTO
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerUpdateObiettivo(Obiettivo obiettivo) {
    	
        //if (menu.getDipartimento().idPersona==dipartimentoID){
        	objCmdServizi.save(obiettivo);
        	Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("Update Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("controllerUpdateObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
        //}  
    }
    
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerUpdateIncaricoObiettivo(Obiettivo obiettivo) {
    	
       
        	objCmdServizi.save(obiettivo);
        	Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("Update Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("controllerUpdateIncaricoObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
          
    }
    
 // 2. Modifica Obiettivo 
    // aggiorna obiettivo solo se � nello stato INTERLOCUTORIO, PROPOSTO o RICHIESTO
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerUpdateObiettivoApicale(Obiettivo obiettivo) {
    	
        //if (menu.getDipartimento().idPersona==dipartimentoID            ){
        	objCmdServizi.save(obiettivo);
        	Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("Update Obiettivo Apicale con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("controllerUpdateObiettivoApicale");
            journal.setQuando(new Date());
            journalServizi.save(journal);
        //}  
    }
    // 3. Cancella Obiettivo 
    // aggiorna obiettivo solo se � nello stato INTERLOCUTORIO o RICHIESTO
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerDeleteObiettivo(Obiettivo obiettivo) {
    	
        /*if ( obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.INTERLOCUTORIO) ||
        		obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.RICHIESTO)
        		)  {*/
        	objCmdServizi.delete(obiettivo);
        	Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("delete Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("controllerDeleteObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
        //}  
    }
    
    // 4. Valida Obiettivo 
    // valida obiettivo solo se � nello stato PROPOSTO
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerValidaObiettivo(Obiettivo obiettivo) {
    	
        //if (menu.getDipartimento().idPersona==dipartimentoID){
        	obiettivo.setStatoApprov(Obiettivo.StatoApprovEnum.VALIDATO);
            objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("valida Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("controllerValidaObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
        //}  
    }
    
    // 5. Rivedi Obiettivo 
    // valida obiettivo solo se � nello stato PROPOSTO
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerRivedeObiettivo(Obiettivo obiettivo) {
    	
        	obiettivo.setStatoApprov(Obiettivo.StatoApprovEnum.RIVISTO);
            objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("rivedi Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("controllerRivediObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
          
    }
    
    // 6. Respingi Obiettivo 
    // valida obiettivo solo se � nello stato PROPOSTO
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerRespingeObiettivo(Obiettivo obiettivo) {
    	
       /* if (obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.PROPOSTO) ){*/
        	obiettivo.setStatoApprov(Obiettivo.StatoApprovEnum.RESPINTO);
            objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("respingi Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("controllerRespingiObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
        //}  
    }
    
    // 7. Annulla Obiettivo 
    // valida obiettivo solo se � nello stato DEFINITIVO
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerAnnullaObiettivo(Obiettivo obiettivo) {
    	
        /*if (obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.DEFINITIVO) 
            ){*/
        	obiettivo.setStatoApprov(Obiettivo.StatoApprovEnum.ANNULLATO);
        	obiettivo.setPeso(0);
            objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("annulla Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("controllerAnnullaObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
        //}  
    }
    
    // aggiorna il campo stato di realizzazione
    // 
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerRealizzaObiettivo(Obiettivo obiettivo) {
    	
        //if (            obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.DEFINITIVO) ){
            objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("aggiorna stato realizzazione di Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("controllerRealizzaObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
        //}  
    }
    
    // aggiorna il campo stato di valutazione
    // 
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerValutaObiettivo(Obiettivo obiettivo) {
    	
        //if (            obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.DEFINITIVO) ){
            objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("aggiorna stato valutazione Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("controllerValutaObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
        //}  
    }
    
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerRendiApicaleObiettivo(Obiettivo obiettivo) {
    	
        
            objCmdServizi.save(obiettivo);
            if (!obiettivo.isApicale()){
            	List<Azione> azioni = obiettivo.getAzioni();
            	for (Azione act:azioni){
            		act.setPesoApicale(0);
            		actCmdServizi.save(act);
            	}
            }
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("aggiorna apicale Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("controllerRendiApicaleObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
         
		
	}

    
    // -----------------------------------------------
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerCreateAzione(Azione azione) {
    	/*final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(azione.getIncaricoID());
    	final Integer dipartimentoID = (fromOrganikoServizi.findDipartimentoByPersonaFisicaID(incarico.pfID)).idPersona;;
        if (menu.getDipartimento().idPersona==dipartimentoID){*/
        	actCmdServizi.save(azione);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("crea Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("controllerCreateAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        //}
    }
    
 // -----------------------------------------------
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public Evento cmdControllerCreaAzioneApicale(Azione azione) {
    	 if (menu.getIncaricoApicaleDept().idIncarico.equals(azione.getIncaricoID())){ // equals non == perché Integer
        	actCmdServizi.save(azione);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("crea Azione Apicale con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("controllerCreateAzioneApicale");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
	        //
            final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
    			"cmdControllerCreaAzioneApicale", 
    			"azione apicale creato: "+azione.toString(),
    			Evento.TipoEnum.SUCCESS,
    			"Azione",
    			azione.getIdAzione());
            	evtServizi.save(newEvtOk);
            return newEvtOk;
        } else {
    		final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdControllerCreaAzioneApicale", 
	    			"menu.getIncaricoApicaleDept().getIdIncarico().equals(azione.getIncaricoID():"+
	    					menu.getIncaricoApicaleDept().idIncarico+" / "+
	    					azione.getIncaricoID(),
	    			Evento.TipoEnum.ERROR,
	    			"Azione",
	    			0);
	    	evtServizi.save(newEvtErr);
	    	return newEvtErr;
    }
    }

    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerUpdateAzione(Azione azione) {
    	/*
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(azione.getIncaricoID());
    	final Integer dipartimentoID = (fromOrganikoServizi.findDipartimentoByPersonaFisicaID(incarico.pfID)).idPersona;
        if (menu.getDipartimento().idPersona==dipartimentoID && (
    			azione.getObiettivo().getStatoApprov().equals(Obiettivo.StatoApprovEnum.INTERLOCUTORIO) ||
        		azione.getObiettivo().getStatoApprov().equals(Obiettivo.StatoApprovEnum.RICHIESTO) ||
        		azione.getObiettivo().isApicale()
        		)){
        		*/
        	actCmdServizi.save(azione);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Update Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("controllerUpdateAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
       /* }*/
    }
    
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerUpdateAzioneApicale(Azione azione) {
    	 //if (menu.getDipartimento().idPersona==dipartimentoID ){
        	actCmdServizi.save(azione);
        	//
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Update Azione Apicale con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("controllerUpdateAzioneApicale");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
       // }
    }

    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerDeleteAzione(Azione azione) {
    	
        
        	actCmdServizi.delete(azione);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Delete Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("controllerDeleteAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        
    }

    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerUpdateCompletamentoAzione(Azione azione) {
    	
        	actCmdServizi.save(azione);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Completamento Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("controllerUpdateCompletamentoAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        
    }
    
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerUpdatePesoAzione(Azione azione) {
    	
        	actCmdServizi.save(azione);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Aggiornamento Peso Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("controllerUpdatePesoAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        
    }
    
    // -------------------------------------------------------------------------------
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerUpdatePesoApicaleAzione(Azione azione) {
    	   	
        	actCmdServizi.save(azione);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Aggiornamento Peso Apicale Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("controllerUpdatePesoApicaleAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        
    }
    
    
    // --------------------- AssociazObiettivi -----------------------
    //
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerCreateAssociazObiettivi(AssociazObiettivi associazObiettivi) {
        
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
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerUpdateAssociazObiettivi(AssociazObiettivi associazObiettivi) {
        
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
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerDeleteAssociazObiettivi(AssociazObiettivi associazObiettivi) {
    	
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

    
	
	// --------------- AzioneAssegnazione --------------------------------
    //
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerCreateAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione) {
    	/*
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(azioneAssegnazione.getIncaricoID());
    	final Integer dipartimentoID = (fromOrganikoServizi.findDipartimentoByPersonaFisicaID(incarico.pfID)).idPersona;;
        if ((menu.getDipartimento().idPersona==dipartimentoID)		
        		&& (
        		azioneAssegnazione.getAzione().getObiettivo().getStatoApprov().equals(Obiettivo.StatoApprovEnum.INTERLOCUTORIO) ||
        		azioneAssegnazione.getAzione().getObiettivo().getStatoApprov().equals(Obiettivo.StatoApprovEnum.RICHIESTO)
        		)
        	)*/
        	actAssCmdServizi.save(azioneAssegnazione);
        	PersonaFisicaGeko dip = fromOrganikoServizi.findPersonaFisicaById(azioneAssegnazione.getPfID());
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Create AzioneAssegnazione con id: "+azioneAssegnazione.getId()+ 
	        		", "+azioneAssegnazione.getAzione().getDenominazione()+
	        		", "+dip.getCognomeNome()
	        		);
	        journal.setDove("AzioneAssegnazione");
	        journal.setIdObj(azioneAssegnazione.getId());
	        journal.setPerche("controllerCreateAzioneAssegnazione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
    }

    
    
    
    @Transactional
    @Secured({"ROLE_CONTROLLER"})
    public void controllerUpdateRisultatoApicaleAzione(Azione azione) {
    	//final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(azione.getIncaricoID());
    	//final Integer dipartimentoID = (fromOrganikoServizi.findDipartimentoByPersonaFisicaID(incarico.pfID)).idPersona;;
        
    		
            //System.out.println("ManagerServiceImpl.controllerUpdateRisultatoAzione() id azione="+azione.getIdAzione());
        	actCmdServizi.save(azione);
	    	Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("UpdateRisultato Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("controllerUpdateRisultatoAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        
    }
    
    
 
   

    @Transactional
    @Secured({"ROLE_CONTROLLER"})
	public void controllerClonaObiettivo(Obiettivo obj) {
        log.info("Cloning existing Obiettivo: "+ obj.getDescrizione());
         //
        // creo in modo da avere la sua Id
        Obiettivo objClonato = obj.clone();
        objCmdServizi.save(objClonato);
        
        //
    	Journal journal = new Journal();
        journal.setChi(menu.getChi());
        journal.setCosa("Clona Obiettivo con id: "+obj.getIdObiettivo()+ ", "+obj.getDescrizione());
        journal.setDove("Obiettivo");
        journal.setIdObj(obj.getIdObiettivo());
        journal.setPerche("controllerClonaObiettivo");
        journal.setQuando(new Date());
        journalServizi.save(journal);
		
	}

    @Transactional
    @Secured({"ROLE_CONTROLLER"})
	public void controllerClonaAzione(Azione azione) {
		log.info("Cloning existing Azione: "+azione.getDescrizione());
		actCmdServizi.save(azione);
        Journal journal = new Journal();
        journal.setChi(menu.getChi());
        journal.setCosa("Clona Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
        journal.setDove("Azione");
        journal.setIdObj(azione.getIdAzione());
        journal.setPerche("controllerClonaAzione");
        journal.setQuando(new Date());
        journalServizi.save(journal);
		
	}

    

    @Transactional
    @Secured({"ROLE_CONTROLLER"})
	public void controllerUpdateScadenzaApicaleAzione(Azione azione) {
		actCmdServizi.save(azione);
        Journal journal = new Journal();
        journal.setChi(menu.getChi());
        journal.setCosa("Aggiornamento Scadenza Apicale Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
        journal.setDove("Azione");
        journal.setIdObj(azione.getIdAzione());
        journal.setPerche("controllerUpdateScadenzaApicaleAzione");
        journal.setQuando(new Date());
        journalServizi.save(journal);
		
	}

	@Override
	@Transactional
    @Secured({"ROLE_CONTROLLER"})
    public Evento cmdControllerRendiInterlocutorioObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    	if (
			obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.PROPOSTO ) ||
			obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.PROPOSTOGAB) ||
			obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.RICHIESTO) ||
			obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.RIVISTO) ||
			obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.ANNULLATO) ||
	    	obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.VALIDATO)  	
    		) {
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
    	
    	else{
    		final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"ControllerProgrammazioneServiceImpl.cmdControllerRendiInterlocutorioObiettivo()", 
	    			"lo stato dell'obiettivo è:"+obiettivo.getStatoApprov()+" mentre è richiesto PROPOSTO, PROPOSTOGAB,RICHIESTO,RIVISTO o VALIDATO",
	    			Evento.TipoEnum.ERROR,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
	    	evtServizi.save(newEvtErr);
	    	return newEvtErr;
    }
	}
    
	//
	@Override
	@Transactional
    @Secured({"ROLE_CONTROLLER"})
    public Evento cmdControllerRendiPropostoObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    	
    		//
    		obiettivo.controllerRendeProposto();
    		objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("Rende proposto Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("controllerRendeProposto");
            journal.setQuando(new Date());
            journalServizi.save(journal);
            //
    		final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
	    			"cmdControllerRendePropostoObiettivo", 
	    			"obiettivo: "+obiettivo.toString(),
	    			Evento.TipoEnum.SUCCESS,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
    		evtServizi.save(newEvtOk);
    		return newEvtOk;
    	
    
	}
    
	
	// 4. Proponi Obiettivo
    @Transactional
    @Secured({"ROLE_CONTROLLER"})
    public Evento cmdControllerProponeObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    		obiettivo.controllerPropone();
    		objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("Proponi Obiettivo Apicale con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("controllerProponiObiettivoApicale");
            journal.setQuando(new Date());
            journalServizi.save(journal);
            //
    		final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
	    			"cmdControllerProponiObiettivo", 
	    			"obiettivo proposto: "+obiettivo.toString(),
	    			Evento.TipoEnum.SUCCESS,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
    		evtServizi.save(newEvtOk);
    		return newEvtOk;
    	
    }
    
    @Transactional
    @Secured({"ROLE_CONTROLLER"})
	public Evento cmdControllerCreaObiettivoApicale(Obiettivo obiettivo) {
		
    	log.info("cmdControllerCreaObiettivoApicale() ");
        if (menu.getIncaricoApicaleDept().idIncarico.equals(obiettivo.getIncaricoID())){ // equals non == perché Integer
            objCmdServizi.save(obiettivo);
            //
        	Azione defaultAzione = new Azione();
        	defaultAzione.setObiettivo(obiettivo);
        	defaultAzione.setDenominazione("_???_");
        	defaultAzione.setDescrizione("azione di default ");
        	defaultAzione.setPeso(0);
        	defaultAzione.setProdotti("?");
        	defaultAzione.setCompletamento(CompletamentoEnum.DA_VALUTARE);
        	actCmdServizi.save(defaultAzione);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("Crea Obiettivo Apicale con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("controllerCreaObiettivoApicale");
            journal.setQuando(new Date());
            journalServizi.save(journal);
       
            //
            final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
    			"cmdControllerCreaObiettivoApicale", 
    			"obiettivo apicale creato: "+obiettivo.toString(),
    			Evento.TipoEnum.SUCCESS,
    			"Obiettivo",
    			obiettivo.getIdObiettivo());
            	evtServizi.save(newEvtOk);
            return newEvtOk;
        } 
        else{
		final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
    			"cmdControllerCreaObiettivoApicale", 
    			"menu.getIncaricoApicaleDept() ha ID:"+menu.getIncaricoApicaleDept().idIncarico+
    			    " mentre l'incarico obiettivo ha ID:"+ obiettivo.getIncaricoID(),
    			Evento.TipoEnum.ERROR,
    			"Obiettivo",
    			0);
    			evtServizi.save(newEvtErr);
    			return newEvtErr;
        }
    }
    
    
 // --------------------- AssociazObiettivi -----------------------
    //
    @Override
    @Transactional
    @Secured({"ROLE_CONTROLLER"})
    public void controllerCreateAssociazProgramma(AssociazProgramma associazProgramma) {
        
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
    @Secured({"ROLE_CONTROLLER"})
    public void controllerUpdateAssociazProgramma(AssociazProgramma associazProgramma) {
        
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
    @Secured({"ROLE_CONTROLLER"})
    public void controllerDeleteAssociazProgramma(AssociazProgramma associazProgramma) {
    	
    	associazProgrammaServizi.delete(associazProgramma);
        
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Delete AssociazProgramma con id: "+associazProgramma.getId()
	        		);
	        journal.setDove("AssociazProgramma");
	        journal.setIdObj(associazProgramma.getId());
	        journal.setPerche("controllerCreateAssociazProgramma");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        
    }
    
    @Override
	@Transactional
    @Secured({"ROLE_CONTROLLER"})
	public Evento cmdControllerConcordaObiettivoApicale(Obiettivo obiettivo) {
		final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    		obiettivo.controllerConcorda();
    		objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("Controller concorda Obiettivo Apicale con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("controllerConcordaObiettivoApicale");
            journal.setQuando(new Date());
            journalServizi.save(journal);
            //
    		final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
	    			"cmdControllerConcordaObiettivoApicale", 
	    			"obiettivo apicale: "+obiettivo.toString(),
	    			Evento.TipoEnum.SUCCESS,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
    		evtServizi.save(newEvtOk);
    		return newEvtOk;
	}

    
    
// --------------------------------------------------
    //
    
    //
   

    @Transactional
    @Secured({"ROLE_CONTROLLER"})
    public Evento cmdControllerUpdateAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione) {
    	IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(azioneAssegnazione.getIncaricoID());
    	PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
      	
        	actAssServizi.save(azioneAssegnazione);
        	PersonaFisicaGeko dipendente = fromOrganikoServizi.findPersonaFisicaById(azioneAssegnazione.getPfID());
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Update AzioneAssegnazione con id: "+azioneAssegnazione.getId()+ 
	        		", "+azioneAssegnazione.getAzione().getDenominazione()+
	        		", "+dipendente.getCognomeNome()
	        		);
	        journal.setDove("AzioneAssegnazione");
	        journal.setIdObj(azioneAssegnazione.getId());
	        journal.setPerche("managerUpdateAzioneAssegnazione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
	        
	        final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
	    			"cmdManagerUpdateAzioneAssegnazione", 
	    			"azione assegnazione modificata: "+azioneAssegnazione.toString(),
	    			Evento.TipoEnum.SUCCESS,"Azione",
	    			azioneAssegnazione.getId());
		    	evtServizi.save(newEvtOk);
		        return newEvtOk;
        
    }

    @Transactional
    @Secured({"ROLE_CONTROLLER"})
    public void controllerDeleteAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione) {
    	
        	actAssServizi.delete(azioneAssegnazione);
        	IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(azioneAssegnazione.getIncaricoID());
        	PersonaFisicaGeko dipendente = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Delete AzioneAssegnazione con id: "+azioneAssegnazione.getId()+ 
	        		", "+azioneAssegnazione.getAzione().getDenominazione()+
	        		", "+dipendente.getCognomeNome()
	        		);
	        journal.setDove("AzioneAssegnazione");
	        journal.setIdObj(azioneAssegnazione.getId());
	        journal.setPerche("managerDeleteAzioneAssegnazione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
       
    }

	@Override
	public void controllerDuplicaAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione) {
		// TODO Auto-generated method stub
		
	}
    
   
    
    
} // ---------------------------------------------------------------------------------
