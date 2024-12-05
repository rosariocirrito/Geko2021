package it.sicilia.regione.gekoddd.geko.programmazione.application;



import java.util.Date;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettiviCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.AzioneCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione.CompletamentoEnum;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazioneCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione.ObiettivoAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione.ObiettivoAssegnazioneCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo.TipoEnum;
import it.sicilia.regione.gekoddd.geko.programmazione.web.manager.ManagerAssegnazioniController;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.Documento;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.DocumentoCmdService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneComparto;
import it.sicilia.regione.gekoddd.geko.valutazione.infrastructure.ValutazioneCompartoCmdService;
import it.sicilia.regione.gekoddd.log.model.evento.Evento;
import it.sicilia.regione.gekoddd.log.model.evento.EventoService;
import it.sicilia.regione.gekoddd.log.model.journal.Journal;
import it.sicilia.regione.gekoddd.log.model.journal.JournalService;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ManagerProgrammazioneService")
public class ManagerProgrammazioneServiceImpl implements ManagerProgrammazioneService{

	private static final Logger log = LoggerFactory.getLogger(ManagerProgrammazioneServiceImpl.class);
	
	@Autowired
    private ObiettivoCmdService objCmdServizi;
	@Autowired
    private AzioneCmdService actCmdServizi;
	@Autowired
    private AzioneAssegnazioneCmdService actAssServizi;
	@Autowired
    private ObiettivoAssegnazioneCmdService objAssServizi;
    @Autowired
    private JournalService journalServizi;
    @Autowired
    private EventoService evtServizi;
    @Autowired
    private AssociazObiettiviCmdService associazObiettiviServizi;
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    // ------- Casi d'uso del manager -----------------------------------------
    
    // 1. Crea Obiettivo (con azione di default)
    
    
 // 1. Crea Obiettivo (con azione di default)
    @Transactional
    @Secured({"ROLE_MANAGER"})
    public Evento cmdManagerCreateObiettivo(Obiettivo obiettivo) {
        //
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    	
    	//
    	objCmdServizi.save(obiettivo);
        //
    	Azione defaultAzione = new Azione();
    	defaultAzione.setObiettivo(obiettivo);
    	defaultAzione.setDenominazione(obiettivo.getCodice()+"_");
    	defaultAzione.setDescrizione("azione di default per "+obiettivo.getCodice());
    	
    	int anno = obiettivo.getAnno()-1900;
        Date dateScad =  new Date(anno,11,31);
    	defaultAzione.setScadenza(dateScad);
    	
    	defaultAzione.setPeso(0);
    	defaultAzione.setProdotti("?");
    	defaultAzione.setCompletamento(CompletamentoEnum.DA_VALUTARE);
    	actCmdServizi.save(defaultAzione);
    	Journal journal = new Journal();
        journal.setChi(menu.getChi());
        journal.setCosa("Create Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
        journal.setDove("Obiettivo");
        journal.setIdObj(obiettivo.getIdObiettivo());
        journal.setPerche("managerCreateObiettivo");
        journal.setQuando(new Date());
        journalServizi.save(journal);
        
        final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
    			"cmdManagerCreateObiettivo", 
    			"obiettivo creato: "+obiettivo.toString(),
    			Evento.TipoEnum.SUCCESS,
    			"Obiettivo",
    			obiettivo.getIdObiettivo());
        evtServizi.save(newEvtOk);
        return newEvtOk;    	
    }
    
 // 1. Crea Obiettivo (con azione di default)
    @Transactional
    @Secured({"ROLE_MANAGER"})
    public void managerDuplicaObiettivo(Obiettivo obiettivo) {
        //
    	objCmdServizi.save(obiettivo);
        //
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    	Journal journal = new Journal();
        journal.setChi(menu.getChi());
        journal.setCosa("Duplica Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
        journal.setDove("Obiettivo");
        journal.setIdObj(obiettivo.getIdObiettivo());
        journal.setPerche("managerDuplicaObiettivo");
        journal.setQuando(new Date());
        journalServizi.save(journal);
        
    }
    
    // 2. Modifica Obiettivo 
    // aggiorna obiettivo solo se � nello stato INTERLOCUTORIO
    @Transactional
    @Secured({"ROLE_MANAGER"})
    public void managerUpdateObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    	
        	objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("Update Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("managerUpdateObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
         
        
    }
    /*
 // 2. Modifica Obiettivo 
    // aggiorna obiettivo solo se � nello stato INTERLOCUTORIO
    @Transactional
    @Secured({"ROLE_MANAGER"})
    public void managerUpdateIncaricoObiettivo(Obiettivo obiettivo) {
        //System.out.println("ManagerServiceImpl.managerUpdateObiettivo() obj="+obiettivo.getDenominazione());
        //System.out.println("ManagerServiceImpl.managerUpdateObiettivo() struttura="+obiettivo.getStruttura());
        if (obiettivo.getIncarico().getResponsabile().equals(userServizi.findPersonaOfLoggedUser())){
        	objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("Update Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("managerUpdateIncaricoObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
        }  
        
    }*/
    
    // 3. Cancella Obiettivo
    @Transactional
    @Secured({"ROLE_MANAGER"})
    public Evento cmdManagerDeleteObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    	
    		objCmdServizi.delete(obiettivo);
        	Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("Delete Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("managerDeleteObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
            //
    		final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
	    			"cmdManagerDeleteObiettivo", 
	    			"obiettivo cancellato: "+obiettivo.toString(),
	    			Evento.TipoEnum.SUCCESS,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
    		evtServizi.save(newEvtOk);
    		return newEvtOk;
        
    }
    
    // 4. Proponi Obiettivo
    @Transactional
    @Secured({"ROLE_MANAGER"})
    public Evento cmdManagerProponeObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    	if (menu.getPfID() < incarico.pfID || menu.getPfID() > incarico.pfID){
	    	final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdManagerProponiObiettivo", 
	    			"utente loggato :"+menu.getChi()+" e manager: "+incarico.responsabile+" dell'incarico non corrispondono",
	    			Evento.TipoEnum.ERROR,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
	    	evtServizi.save(newEvtErr);
	    	return newEvtErr;
    	}
    	else if (!(obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.INTERLOCUTORIO) ||
        		obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.DEFINITIVO) 	
        		)) {
    		final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdManagerProponiObiettivo", 
	    			"lo stato dell'obiettivo � :"+obiettivo.getStatoApprov()+" mentre è richiesto INTERLOCUTORIO o DEFINITIVO",
	    			Evento.TipoEnum.ERROR,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
	    	evtServizi.save(newEvtErr);
	    	return newEvtErr;
    	}
    	else{
    		obiettivo.managerPropone();
    		objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("Proponi Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("managerProponiObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
            //
    		final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
	    			"cmdManagerProponiObiettivo", 
	    			"obiettivo proposto: "+obiettivo.toString(),
	    			Evento.TipoEnum.SUCCESS,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
    		evtServizi.save(newEvtOk);
    		return newEvtOk;
    	}
    }
    
    // 5. Accetta definitivamente Obiettivo
    @Transactional
    @Secured({"ROLE_MANAGER"})
    public Evento cmdManagerAccettaDefinitivamenteObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    	if (menu.getPfID() < incarico.pfID || menu.getPfID() > incarico.pfID){
	    	final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdManagerAccettaDefinitivamenteObiettivo", 
	    			"utente loggato :"+menu.getChi()+" e manager: "+incarico.responsabile+" dell'incarico non corrispondono",
	    			Evento.TipoEnum.ERROR,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
	    	evtServizi.save(newEvtErr);
	    	return newEvtErr;
    	}
    	else if (!(obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.RICHIESTO) || 
                obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.VALIDATO) ||
                obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.RIVISTO)	
        		)) {
    		final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdManagerAccettaDefinitivamenteObiettivo", 
	    			"lo stato dell'obiettivo � :"+obiettivo.getStatoApprov()+" mentre � richiesto RICHIESTO o VALIDATO o RIVISTO",
	    			Evento.TipoEnum.ERROR,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
	    	evtServizi.save(newEvtErr);
	    	return newEvtErr;
    	}
    	
    	else{	
        	obiettivo.managerAccettaDefinitivamente();
        	objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("AccettaDefinitivamenteObiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("managerAccettaDefinitivamenteObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
            //
            final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
	    			"cmdManagerAccettaDefinitivamenteObiettivo", 
	    			"obiettivo accettato: "+obiettivo.toString(),
	    			Evento.TipoEnum.SUCCESS,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
            evtServizi.save(newEvtOk);
    		return newEvtOk;
        }  
    }
    

    // -------------- AZIONI ------------------------------------------------------------------
    

    //
    @Transactional
    @Secured({"ROLE_MANAGER"})
    public Evento cmdManagerCreateAzione(Azione azione) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(azione.getIncaricoID());
    	//
    	
        	actCmdServizi.save(azione);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Create Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("managerCreateAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
	        //
	        final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
    			"cmdManagerCreateAzione", 
    			"azione creata: "+azione.toString(),
    			Evento.TipoEnum.SUCCESS,"Azione",
    			azione.getIdAzione());
	    	evtServizi.save(newEvtOk);
	        return newEvtOk;
    	
    }
    
    @Transactional
    @Secured({"ROLE_MANAGER"})
    public void managerDuplicaAzione(Azione azione) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(azione.getIncaricoID());
    	//
    	//if (menu.getPfID() < incarico.pfID || menu.getPfID() > incarico.pfID){
        	actCmdServizi.save(azione);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Duplica Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("managerDuplicaAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        //}
    }
    
    @Transactional
    @Secured({"ROLE_MANAGER"})
    public void managerUpdateAzione(Azione azione) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(azione.getIncaricoID());
    	//
    	//if ((menu.getPfID() < incarico.pfID || menu.getPfID() > incarico.pfID)
    	//		&& 
        //    (azione.getObiettivo().getStatoApprov().equals(Obiettivo.StatoApprovEnum.INTERLOCUTORIO) 
    	//		)){
        	actCmdServizi.save(azione);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Update Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("managerUpdateAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
    	//}
    }

    /*
    @Transactional
    @Secured({"ROLE_MANAGER"})
    public void managerDeleteAzione(Azione azione) {
    	final OpPersonaFisica dirigente = azione.getObiettivo().getIncarico().getOpPersonaFisica();
    	if (dirigente.equals(userServizi.findPersonaOfLoggedUser()) &&
                azione.getObiettivo().getStatoApprov().equals(Obiettivo.StatoApprovEnum.INTERLOCUTORIO)|| 
                azione.getObiettivo().getStatoApprov().equals(Obiettivo.StatoApprovEnum.PROPOSTO)
    			)
    		actCmdServizi.delete(azione);
	    	Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Delete Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("managerDeleteAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
    }*/
    
    @Transactional
    @Secured({"ROLE_MANAGER"})
    public Evento cmdManagerDeleteAzione(Azione azione) {
    	
    		actCmdServizi.delete(azione);
	    	Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Delete Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("managerDeleteAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
	        //
	        final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
    			"cmdManagerDeleteAzione", 
    			"azione cancellata: "+azione.toString(),
    			Evento.TipoEnum.SUCCESS,"Azione",
    			azione.getIdAzione());
	    	evtServizi.save(newEvtOk);
	        return newEvtOk;
    	
    }
    
    @Transactional
    @Secured({"ROLE_MANAGER"})
    public void managerUpdateRisultatoAzione(Azione azione) {
    	if (azione.getIncaricoID() == menu.getIdIncaricoScelta()
    			&&
    		azione.getObiettivo().isRendicontabile())
    		
            //System.out.println("ManagerServiceImpl.managerUpdateRisultatoAzione() id azione="+azione.getIdAzione());
        	actCmdServizi.save(azione);
	    	Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("UpdateRisultato Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("managerUpdateRisultatoAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
    }
    
    // --------------- AzioneAssegnazione --------------------------------
    //
    @Transactional
    @Secured({"ROLE_MANAGER"})
    public void managerCreateAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione) {
    	if (azioneAssegnazione.getIncaricoID() == menu.getIdIncaricoScelta()){
        	//log.info("managerCreateAzioneAssegnazione() ...");
    		actAssServizi.save(azioneAssegnazione);
        	IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(azioneAssegnazione.getIncaricoID());
        	PersonaFisicaGeko dipendente = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Create AzioneAssegnazione con id: "+azioneAssegnazione.getId()+ 
	        		", "+azioneAssegnazione.getAzione().getDenominazione()+
	        		", "+dipendente.getCognomeNome()
	        		);
	        journal.setDove("AzioneAssegnazione");
	        journal.setIdObj(0);
	        journal.setPerche("managerCreateAzioneAssegnazione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        }
    }
    
    //
    @Transactional
    @Secured({"ROLE_MANAGER"})
    public void managerDuplicaAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione) {
    	//if (azioneAssegnazione.getIncaricoID() == menu.getIdIncaricoScelta()){
        	actAssServizi.save(azioneAssegnazione);
        	IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(azioneAssegnazione.getIncaricoID());
        	PersonaFisicaGeko dipendente = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Duplica AzioneAssegnazione con id: "+azioneAssegnazione.getId()+ 
	        		", "+azioneAssegnazione.getAzione().getDenominazione()+
	        		", "+dipendente.getCognomeNome()
	        		);
	        journal.setDove("AzioneAssegnazione");
	        journal.setIdObj(azioneAssegnazione.getId());
	        journal.setPerche("managerDuplicaAzioneAssegnazione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        //}
    }

    @Transactional
    @Secured({"ROLE_MANAGER"})
    public Evento cmdManagerUpdateAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione) {
    	IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(azioneAssegnazione.getIncaricoID());
    	PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	if (azioneAssegnazione.getIncaricoID() != menu.getIdIncaricoScelta()){
	    	final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdManagerUpdateAzioneAssegnazione", 
	    			"utente loggato :"+menu.getChi()+" e manager: "+manager+" dell'azione assegnazione non corrispondono",
	    			Evento.TipoEnum.ERROR,
	    			"AzioneAssegnazione",
	    			azioneAssegnazione.getId());
	    	evtServizi.save(newEvtErr);
	    	return newEvtErr;
    	}
    	else {
        	
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
    }

    @Transactional
    @Secured({"ROLE_MANAGER"})
    public void managerDeleteAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione) {
    	if (azioneAssegnazione.getIncaricoID() == menu.getIdIncaricoScelta()){
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
    }
    
   

	@Override
	public void managerCreateAssociazObiettivi(AssociazObiettivi associazObiettivi) {
		associazObiettiviServizi.save(associazObiettivi);
        Journal journal = new Journal();
        journal.setChi(menu.getChi());
        journal.setCosa("Create AssociazObiettivi con id: "+associazObiettivi.getId()
        		);
        journal.setDove("AssociazObiettivi");
        journal.setIdObj(associazObiettivi.getId());
        journal.setPerche("managerCreateAssociazObiettivi");
        journal.setQuando(new Date());
        journalServizi.save(journal);
		
	}

	@Override
	public void managerUpdateAssociazObiettivi(AssociazObiettivi associazObiettivi) {
		associazObiettiviServizi.save(associazObiettivi);
        Journal journal = new Journal();
        journal.setChi(menu.getChi());
        journal.setCosa("Update AssociazObiettivi con id: "+associazObiettivi.getId()
        		);
        journal.setDove("AssociazObiettivi");
        journal.setIdObj(associazObiettivi.getId());
        journal.setPerche("managerUpdateAssociazObiettivi");
        journal.setQuando(new Date());
        journalServizi.save(journal);
	}

	@Override
	public void managerDeleteAssociazObiettivi(AssociazObiettivi associazObiettivi) {
		associazObiettiviServizi.delete(associazObiettivi);
        
        Journal journal = new Journal();
        journal.setChi(menu.getChi());
        journal.setCosa("Delete AssociazObiettivi con id: "+associazObiettivi.getId()
        		);
        journal.setDove("AssociazObiettivi");
        journal.setIdObj(associazObiettivi.getId());
        journal.setPerche("managerDeleteAssociazObiettivi");
        journal.setQuando(new Date());
        journalServizi.save(journal);
	}
    
	// 4. Proponi Obiettivo
    @Transactional
    @Secured({"ROLE_MANAGER"})
    public Evento cmdManagerRendiInterlocutorioObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
		obiettivo.managerRendiInterlocutorio();
		objCmdServizi.save(obiettivo);
        Journal journal = new Journal();
        journal.setChi(menu.getChi());
        journal.setCosa("Rendi Interlocutorio Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
        journal.setDove("Obiettivo");
        journal.setIdObj(obiettivo.getIdObiettivo());
        journal.setPerche("managerRendiInterlocutorio");
        journal.setQuando(new Date());
        journalServizi.save(journal);
        //
		final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
    			"cmdManagerRendiInterlocutorioObiettivo", 
    			"obiettivo: "+obiettivo.toString(),
    			Evento.TipoEnum.SUCCESS,
    			"Obiettivo",
    			obiettivo.getIdObiettivo());
		evtServizi.save(newEvtOk);
		return newEvtOk;
    }
    

 // --------------- ObiettivoAssegnazione --------------------------------
    //
    @Transactional
    @Secured({"ROLE_MANAGER"})
    public void managerCreateObiettivoAssegnazione(ObiettivoAssegnazione obiettivoAssegnazione) {
    	
    	//log.info("managerCreateAzioneAssegnazione() ...");
		objAssServizi.save(obiettivoAssegnazione);
    	PersonaFisicaGeko dipendente = fromOrganikoServizi.findPersonaFisicaById(obiettivoAssegnazione.getIdPersona());
        Journal journal = new Journal();
        journal.setChi(menu.getChi());
        journal.setCosa("Create ObiettivoAssegnazione con id: "+obiettivoAssegnazione.getId()+ 
        		", "+dipendente.getCognomeNome()
        		);
        journal.setDove("ObiettivoAssegnazione");
        journal.setIdObj(0);
        journal.setPerche("managerCreateAzioneAssegnazione");
        journal.setQuando(new Date());
        journalServizi.save(journal);
       
    }
    
    //
    @Transactional
    @Secured({"ROLE_MANAGER"})
    public void managerDuplicaObiettivoAssegnazione(ObiettivoAssegnazione obiettivoAssegnazione) {
    	//if (azioneAssegnazione.getIncaricoID() == menu.getIdIncaricoScelta()){
        	objAssServizi.save(obiettivoAssegnazione);
        	PersonaFisicaGeko dipendente = fromOrganikoServizi.findPersonaFisicaById(obiettivoAssegnazione.getIdPersona());
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Duplica ObiettivoAssegnazione con id: "+obiettivoAssegnazione.getId()+ 
        		", "+dipendente.getCognomeNome()
        		);
	        journal.setDove("ObiettivoAssegnazione");
	        journal.setIdObj(obiettivoAssegnazione.getIdObiettivo());
	        journal.setPerche("managerDuplicaAzioneAssegnazione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        //}
    }

    @Transactional
    @Secured({"ROLE_MANAGER"})
    public Evento cmdManagerUpdateObiettivoAssegnazione(ObiettivoAssegnazione obiettivoAssegnazione) {
        	objAssServizi.save(obiettivoAssegnazione);
        	PersonaFisicaGeko dipendente = fromOrganikoServizi.findPersonaFisicaById(obiettivoAssegnazione.getIdPersona());
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Update ObiettivoAssegnazione con id: "+obiettivoAssegnazione.getId()+ 
        		", "+dipendente.getCognomeNome()
        		);
	        journal.setDove("ObiettivoAssegnazione");
	        journal.setIdObj(obiettivoAssegnazione.getIdObiettivo());
	        journal.setPerche("managerUpdateAzioneAssegnazione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
	        
	        final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
	    			"cmdManagerUpdateAzioneAssegnazione", 
	    			"azione assegnazione modificata: "+obiettivoAssegnazione.toString(),
	    			Evento.TipoEnum.SUCCESS,"Azione",
	    			obiettivoAssegnazione.getId());
		    	evtServizi.save(newEvtOk);
		        return newEvtOk;
       
    }

    @Transactional
    @Secured({"ROLE_MANAGER"})
    public void managerDeleteObiettivoAssegnazione(ObiettivoAssegnazione obiettivoAssegnazione) {
    	
        	objAssServizi.delete(obiettivoAssegnazione);
        	PersonaFisicaGeko dipendente = fromOrganikoServizi.findPersonaFisicaById(obiettivoAssegnazione.getIdPersona());
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Delete ObiettivoAssegnazione con id: "+obiettivoAssegnazione.getId()+ 
        		", "+dipendente.getCognomeNome()
        		);
	        journal.setDove("ObiettivoAssegnazione");
	        journal.setIdObj(obiettivoAssegnazione.getIdObiettivo());	        
	        journal.setPerche("managerDeleteAzioneAssegnazione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
       
    }

	@Override
	public Evento cmdManagerDefCompartoObiettivo(Obiettivo obiettivo) {
		final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    	if (menu.getPfID() < incarico.pfID || menu.getPfID() > incarico.pfID){
	    	final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdManagerDefCompartoObiettivo", 
	    			"utente loggato :"+menu.getChi()+" e manager: "+incarico.responsabile+" dell'incarico non corrispondono",
	    			Evento.TipoEnum.ERROR,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
	    	evtServizi.save(newEvtErr);
	    	return newEvtErr;
    	}
    	else if (obiettivo.getTipo().equals(Obiettivo.TipoEnum.DIRIGENZIALE)) {
    		final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdManagerDefCompartoObiettivo", 
	    			"il tipo di obiettivo è :"+obiettivo.getTipo()+" mentre è richiesto STRUTTURA",
	    			Evento.TipoEnum.ERROR,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
	    	evtServizi.save(newEvtErr);
	    	return newEvtErr;
    	}
    	
    	else{	
        	obiettivo.managerAccettaDefinitivamente();
        	objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("DefCompartoObiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("managerDefCompartoObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
            //
            final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
	    			"cmdManagerDefCompartoObiettivo", 
	    			"obiettivo accettato: "+obiettivo.toString(),
	    			Evento.TipoEnum.SUCCESS,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
            evtServizi.save(newEvtOk);
    		return newEvtOk;
        }  
	}
    
	@Override
	public Evento cmdManagerDefPopObiettivo(Obiettivo obiettivo) {
		final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    	
        	obiettivo.managerAccettaDefinitivamente();
        	objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("DefPopObiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("managerDefPopObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
            //
            final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
	    			"cmdManagerDefPopObiettivo", 
	    			"obiettivo accettato: "+obiettivo.toString(),
	    			Evento.TipoEnum.SUCCESS,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
            evtServizi.save(newEvtOk);
    		return newEvtOk;
        
	}
    /*
    @Transactional
    @Secured({"ROLE_MANAGER"})
    public void managerUpdateAzioneAssegnazioneValutazione(AzioneAssegnazione azioneAssegnazione) {
        //if (azioneAssegnazione.getAzione().getObiettivo().getIncarico().getOpPersonaFisica().equals(userServizi.findPersonaOfLoggedUser())){
        log.info("managerUpdateAzioneAssegnazioneValutazione");
    	if (azioneAssegnazione.getIncaricoID() == menu.getIdIncaricoScelta()){
        	actAssServizi.save(azioneAssegnazione);
        	IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(azioneAssegnazione.getIncaricoID());
        	PersonaFisicaGeko dipendente = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Update AzioneAssegnazione con id: "+azioneAssegnazione.getId()+ 
	        		", "+azioneAssegnazione.getAzione().getDenominazione()+
	        		", "+dipendente.getCognomeNome()
	        		);
	        journal.setDove("AzioneAssegnazione");
	        journal.setIdObj(azioneAssegnazione.getId());
	        journal.setPerche("managerUpdateAzioneAssegnazioneValutazione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        }
    }
*/
	
    
    
} // -------------------------------------------------------

