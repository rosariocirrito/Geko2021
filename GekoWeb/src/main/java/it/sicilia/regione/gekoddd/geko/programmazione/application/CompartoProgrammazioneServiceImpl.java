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

@Service("CompartoProgrammazioneService")
public class CompartoProgrammazioneServiceImpl implements CompartoProgrammazioneService{

	private static final Logger log = LoggerFactory.getLogger(CompartoProgrammazioneServiceImpl.class);
	
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
    
    // ------- Casi d'uso del comparto -----------------------------------------
    
    // 1. Crea Obiettivo (con azione di default)
    
    
 // 1. Crea Obiettivo (con azione di default)
    @Transactional
    @Secured({"ROLE_COMPARTO"})
    @Override
    public Evento cmdCompartoCreateObiettivo(Obiettivo obiettivo) {
        //
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    	if (menu.getPfID() < incarico.pfID || menu.getPfID() > incarico.pfID){
	    	final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdCompartoCreateObiettivo", 
	    			"utente loggato pfid:"+menu.getPfID()+" e comparto con incarico pfid: "+incarico.pfID+" dell'incarico non corrispondono",
	    			Evento.TipoEnum.ERROR,
	    			"Obiettivo nuovo",0);
	    	evtServizi.save(newEvtErr);
	    	return newEvtErr;
    	}
    	else{
    		
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
        journal.setPerche("compartoCreateObiettivo");
        journal.setQuando(new Date());
        journalServizi.save(journal);
        
        final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
    			"cmdCompartoCreateObiettivo", 
    			"obiettivo creato: "+obiettivo.toString(),
    			Evento.TipoEnum.SUCCESS,
    			"Obiettivo",
    			obiettivo.getIdObiettivo());
        evtServizi.save(newEvtOk);
        return newEvtOk;
    	}
    	
    }
    

    
    // 2. Modifica Obiettivo 
    // aggiorna obiettivo solo se � nello stato INTERLOCUTORIO
    @Transactional
    @Secured({"ROLE_COMPARTO"})
    @Override
    public void compartoUpdateObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    	
        	objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("Update Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("compartoUpdateObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
         
        
    }
   
    
    // 3. Cancella Obiettivo
    @Transactional
    @Secured({"ROLE_COMPARTO"})
    @Override
    public Evento cmdCompartoDeleteObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    	if (menu.getPfID() < incarico.pfID || menu.getPfID() > incarico.pfID){
	    	final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdCompartoDeleteObiettivo", 
	    			"utente loggato :"+menu.getChi()+" e comparto: "+incarico.responsabile+" dell'incarico non corrispondono",
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
	    			"cmdCompartoDeleteObiettivo", 
	    			"lo stato dell'obiettivo � :"+obiettivo.getStatoApprov()+" mentre è richiesto INTERLOCUTORIO o DEFINITIVO",
	    			Evento.TipoEnum.ERROR,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
	    	evtServizi.save(newEvtErr);
	    	return newEvtErr;
    	}
    	else{
    	
    		objCmdServizi.delete(obiettivo);
        	Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("Delete Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("compartoDeleteObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
            //
    		final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
	    			"cmdCompartoDeleteObiettivo", 
	    			"obiettivo cancellato: "+obiettivo.toString(),
	    			Evento.TipoEnum.SUCCESS,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
    		evtServizi.save(newEvtOk);
    		return newEvtOk;
        }
    }
    
    // 4. Proponi Obiettivo
    @Transactional
    @Secured({"ROLE_COMPARTO"})
    @Override
    public Evento cmdCompartoProponeObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    	if (menu.getPfID() < incarico.pfID || menu.getPfID() > incarico.pfID){
	    	final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdCompartoProponiObiettivo", 
	    			"utente loggato :"+menu.getChi()+" e comparto: "+incarico.responsabile+" dell'incarico non corrispondono",
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
	    			"cmdCompartoProponiObiettivo", 
	    			"lo stato dell'obiettivo � :"+obiettivo.getStatoApprov()+" mentre è richiesto INTERLOCUTORIO o DEFINITIVO",
	    			Evento.TipoEnum.ERROR,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
	    	evtServizi.save(newEvtErr);
	    	return newEvtErr;
    	}
    	else{
    		obiettivo.compartoPropone();
    		objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("Proponi Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("compartoProponiObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
            //
    		final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
	    			"cmdCompartoProponiObiettivo", 
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
    @Secured({"ROLE_COMPARTO"})
    @Override
    public Evento cmdCompartoAccettaDefinitivamenteObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    	if (menu.getPfID() < incarico.pfID || menu.getPfID() > incarico.pfID){
	    	final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdCompartoAccettaDefinitivamenteObiettivo", 
	    			"utente loggato :"+menu.getChi()+" e comparto: "+incarico.responsabile+" dell'incarico non corrispondono",
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
	    			"cmdCompartoAccettaDefinitivamenteObiettivo", 
	    			"lo stato dell'obiettivo � :"+obiettivo.getStatoApprov()+" mentre � richiesto RICHIESTO o VALIDATO o RIVISTO",
	    			Evento.TipoEnum.ERROR,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
	    	evtServizi.save(newEvtErr);
	    	return newEvtErr;
    	}
    	
    	else{	
        	obiettivo.compartoAccettaDefinitivamente();
        	objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("AccettaDefinitivamenteObiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("compartoAccettaDefinitivamenteObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
            //
            final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
	    			"cmdCompartoAccettaDefinitivamenteObiettivo", 
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
    @Secured({"ROLE_COMPARTO"})
    @Override
    public Evento cmdCompartoCreateAzione(Azione azione) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(azione.getIncaricoID());
    	//
    	if (menu.getPfID() < incarico.pfID || menu.getPfID() > incarico.pfID){
	    	final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdCompartoCreateAzione", 
	    			"utente loggato :"+menu.getChi()+" e comparto: "+incarico.responsabile+" dell'incarico non corrispondono",
	    			Evento.TipoEnum.ERROR,
	    			"Azione su Obj con ID",0);
	    	evtServizi.save(newEvtErr);
	    	return newEvtErr;
    	}
    	else if (!azione.getObiettivo().getStatoApprov().equals(Obiettivo.StatoApprovEnum.INTERLOCUTORIO)
    			&& !azione.getObiettivo().getStatoApprov().equals(Obiettivo.StatoApprovEnum.PROPOSTO)
    			){
    		final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdCompartoCreateAzione", 
	    			"lo stato dell'obiettivo � :"+azione.getObiettivo().getStatoApprov()+" mentre � richiesto che sia INTERLOCUTORIO o PROPOSTO",
	    			Evento.TipoEnum.ERROR,
	    			"Azione su Obj con ID",azione.getObiettivo().getIdObiettivo());
	    	evtServizi.save(newEvtErr);
	    	return newEvtErr;
    	}
    	else {
        	actCmdServizi.save(azione);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Create Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("compartoCreateAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
	        //
	        final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
    			"cmdCompartoCreateAzione", 
    			"azione creata: "+azione.toString(),
    			Evento.TipoEnum.SUCCESS,"Azione",
    			azione.getIdAzione());
	    	evtServizi.save(newEvtOk);
	        return newEvtOk;
    	}
    }
    
   
    
    @Transactional
    @Secured({"ROLE_COMPARTO"})
    @Override
    public void compartoUpdateAzione(Azione azione) {
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
	        journal.setPerche("compartoUpdateAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
    	//}
    }

    /*
    @Transactional
    @Secured({"ROLE_COMPARTO"})
    public void compartoDeleteAzione(Azione azione) {
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
	        journal.setPerche("compartoDeleteAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
    }*/
    
    @Transactional
    @Secured({"ROLE_COMPARTO"})
    public Evento cmdCompartoDeleteAzione(Azione azione) {
    	//
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(azione.getIncaricoID());
    	//
    	if (menu.getPfID() < incarico.pfID || menu.getPfID() > incarico.pfID){
	    	final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdCompartoDeleteAzione", 
	    			"utente loggato :"+menu.getChi()+" e comparto: "+incarico.responsabile+" dell'incarico non corrispondono",
	    			Evento.TipoEnum.ERROR,
	    			"Azione",
	    			azione.getIdAzione());
	    	evtServizi.save(newEvtErr);
	    	return newEvtErr;
    	}
    	else if (!(azione.getObiettivo().getStatoApprov().equals(Obiettivo.StatoApprovEnum.INTERLOCUTORIO)|| 
                	azione.getObiettivo().getStatoApprov().equals(Obiettivo.StatoApprovEnum.PROPOSTO)
                )){
    		final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdCompartoDeleteAzione", 
	    			"lo stato dell'obiettivo � :"+azione.getObiettivo().getStatoApprov()+" mentre � richiesto che sia INTERLOCUTORIO o PROPOSTO ",
	    			Evento.TipoEnum.ERROR,"Azione",
	    			azione.getIdAzione());
	    	evtServizi.save(newEvtErr);
	    	return newEvtErr;
    	}
    	else {
    		actCmdServizi.delete(azione);
	    	Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Delete Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("compartoDeleteAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
	        //
	        final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
    			"cmdCompartoDeleteAzione", 
    			"azione cancellata: "+azione.toString(),
    			Evento.TipoEnum.SUCCESS,"Azione",
    			azione.getIdAzione());
	    	evtServizi.save(newEvtOk);
	        return newEvtOk;
    	}
    }
    
    @Transactional
    @Secured({"ROLE_COMPARTO"})
    public void compartoUpdateRisultatoAzione(Azione azione) {
    	if (azione.getIncaricoID() == menu.getIdIncaricoScelta()
    			&&
    		azione.getObiettivo().isRendicontabile())
    		
            //System.out.println("ManagerServiceImpl.compartoUpdateRisultatoAzione() id azione="+azione.getIdAzione());
        	actCmdServizi.save(azione);
	    	Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("UpdateRisultato Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("compartoUpdateRisultatoAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
    }
    
    // --------------- AzioneAssegnazione --------------------------------
    //
    @Transactional
    @Secured({"ROLE_COMPARTO"})
    public void compartoCreateAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione) {
    	if (azioneAssegnazione.getIncaricoID() == menu.getIdIncaricoScelta()){
        	//log.info("compartoCreateAzioneAssegnazione() ...");
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
	        journal.setPerche("compartoCreateAzioneAssegnazione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        }
    }
    
    //
    @Transactional
    @Secured({"ROLE_COMPARTO"})
    public void compartoDuplicaAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione) {
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
	        journal.setPerche("compartoDuplicaAzioneAssegnazione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        //}
    }

    @Transactional
    @Secured({"ROLE_COMPARTO"})
    public Evento cmdCompartoUpdateAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione) {
    	IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(azioneAssegnazione.getIncaricoID());
    	PersonaFisicaGeko comparto = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	if (azioneAssegnazione.getIncaricoID() != menu.getIdIncaricoScelta()){
	    	final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdCompartoUpdateAzioneAssegnazione", 
	    			"utente loggato :"+menu.getChi()+" e comparto: "+comparto+" dell'azione assegnazione non corrispondono",
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
	        journal.setPerche("compartoUpdateAzioneAssegnazione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
	        
	        final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
	    			"cmdCompartoUpdateAzioneAssegnazione", 
	    			"azione assegnazione modificata: "+azioneAssegnazione.toString(),
	    			Evento.TipoEnum.SUCCESS,"Azione",
	    			azioneAssegnazione.getId());
		    	evtServizi.save(newEvtOk);
		        return newEvtOk;
        }
    }

    @Transactional
    @Secured({"ROLE_COMPARTO"})
    public void compartoDeleteAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione) {
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
	        journal.setPerche("compartoDeleteAzioneAssegnazione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        }
    }
    
    @Transactional
    @Secured({"ROLE_COMPARTO"})
    public void compartoUpdateAzioneAssegnazioneValutazione(AzioneAssegnazione azioneAssegnazione) {
        //if (azioneAssegnazione.getAzione().getObiettivo().getIncarico().getOpPersonaFisica().equals(userServizi.findPersonaOfLoggedUser())){
        log.info("compartoUpdateAzioneAssegnazioneValutazione");
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
	        journal.setPerche("compartoUpdateAzioneAssegnazioneValutazione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        }
    }

	
	

	
    
	// 4. Proponi Obiettivo
    @Transactional
    @Secured({"ROLE_COMPARTO"})
    public Evento cmdCompartoRendiInterlocutorioObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    	if (menu.getPfID() < incarico.pfID || menu.getPfID() > incarico.pfID){
	    	final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdCompartoRendiInterlocutorioObiettivo", 
	    			"utente loggato :"+menu.getChi()+" e comparto: "+incarico.responsabile+" dell'incarico non corrispondono",
	    			Evento.TipoEnum.ERROR,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
	    	evtServizi.save(newEvtErr);
	    	return newEvtErr;
    	}
    	else if (
    			obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.PROPOSTO) ||	
    			obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.RIVISTO)	
    			) {
    		obiettivo.compartoRendiInterlocutorio();
    		objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("Rendi Interlocutorio Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("compartoRendiInterlocutorio");
            journal.setQuando(new Date());
            journalServizi.save(journal);
            //
    		final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
	    			"cmdCompartoRendiInterlocutorioObiettivo", 
	    			"obiettivo: "+obiettivo.toString(),
	    			Evento.TipoEnum.SUCCESS,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
    		evtServizi.save(newEvtOk);
    		return newEvtOk;
    	}
    	else{
    		final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdCompartoRendiInterlocutorioObiettivo", 
	    			"lo stato dell'obiettivo è :"+obiettivo.getStatoApprov()+" mentre è richiesto PROPOSTO o RIVISTO",
	    			Evento.TipoEnum.ERROR,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
	    	evtServizi.save(newEvtErr);
	    	return newEvtErr;
    	}
    }
    

 
    
    
    //
    

   
    
    
} // -------------------------------------------------------

