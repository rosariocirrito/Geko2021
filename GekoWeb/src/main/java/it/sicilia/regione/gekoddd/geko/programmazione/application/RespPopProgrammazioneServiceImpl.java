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

@Service("RespPopProgrammazioneService")
public class RespPopProgrammazioneServiceImpl implements RespPopProgrammazioneService{

	private static final Logger log = LoggerFactory.getLogger(RespPopProgrammazioneServiceImpl.class);
	
	@Autowired
    private ObiettivoCmdService objCmdServizi;
	@Autowired
    private AzioneCmdService actCmdServizi;
    @Autowired
    private JournalService journalServizi;
    @Autowired
    private EventoService evtServizi;

    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    // ------- Casi d'uso del manager -----------------------------------------
    
    // 1. Crea Obiettivo (con azione di default)
    
    
 // 1. Crea Obiettivo (con azione di default)
    @Override
    @Transactional
    @Secured({"ROLE_RESP_POP"})
    public Evento cmdPopCreateObiettivo(Obiettivo obiettivo) {
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
        journal.setPerche("respPopCreateObiettivo");
        journal.setQuando(new Date());
        journalServizi.save(journal);
        
        final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
    			"cmdPopCreateObiettivo", 
    			"obiettivo creato: "+obiettivo.toString(),
    			Evento.TipoEnum.SUCCESS,
    			"Obiettivo",
    			obiettivo.getIdObiettivo());
        evtServizi.save(newEvtOk);
        return newEvtOk;    	
    }
    
 
    // 2. Modifica Obiettivo 
    // aggiorna obiettivo solo se � nello stato INTERLOCUTORIO
    @Override
    @Transactional
    @Secured({"ROLE_RESP_POP"})
    public void popUpdateObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    	
        	objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("Update Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("respPopUpdateObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
         
        
    }
    
    
    // 3. Cancella Obiettivo
    @Override
    @Transactional
    @Secured({"ROLE_RESP_POP"})
    public Evento cmdPopDeleteObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    	if (menu.getPfID() < incarico.pfID || menu.getPfID() > incarico.pfID){
	    	final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdPopDeleteObiettivo", 
	    			"utente loggato :"+menu.getChi()+" e pop: "+incarico.getResponsabile()+" dell'incarico non corrispondono",
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
	    			"cmdPopDeleteObiettivo", 
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
            journal.setPerche("managerDeleteObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
            //
    		final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
	    			"cmdPopDeleteObiettivo", 
	    			"obiettivo cancellato: "+obiettivo.toString(),
	    			Evento.TipoEnum.SUCCESS,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
    		evtServizi.save(newEvtOk);
    		return newEvtOk;
        }
    }
    
    // 4. Proponi Obiettivo
    @Override
    @Transactional
    @Secured({"ROLE_RESP_POP"})
    public Evento cmdPopProponeObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    	if (menu.getPfID() < incarico.pfID || menu.getPfID() > incarico.pfID){
	    	final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdPopProponiObiettivo", 
	    			"utente loggato :"+menu.getChi()+" e manager: "+incarico.getResponsabile()+" dell'incarico non corrispondono",
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
	    			"cmdPopProponiObiettivo", 
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
            journal.setPerche("respPOPProponeObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
            //
    		final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
	    			"cmdPopProponiObiettivo", 
	    			"obiettivo proposto: "+obiettivo.toString(),
	    			Evento.TipoEnum.SUCCESS,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
    		evtServizi.save(newEvtOk);
    		return newEvtOk;
    	}
    }
    
    // 5. Accetta definitivamente Obiettivo
    @Override
    @Transactional
    @Secured({"ROLE_RESP_POP"})
    public Evento cmdPopAccettaDefinitivamenteObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    	if (menu.getPfID() < incarico.pfID || menu.getPfID() > incarico.pfID){
	    	final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdPopAccettaDefinitivamenteObiettivo", 
	    			"utente loggato :"+menu.getChi()+" e manager: "+incarico.getResponsabile()+" dell'incarico non corrispondono",
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
	    			"cmdPopAccettaDefinitivamenteObiettivo", 
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
            journal.setPerche("respPOPAccettaDefinitivamenteObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
            //
            final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
	    			"cmdPopAccettaDefinitivamenteObiettivo", 
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
    @Override
    @Transactional
    @Secured({"ROLE_RESP_POP"})
    public Evento cmdPopCreateAzione(Azione azione) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(azione.getIncaricoID());
    	//
    	if (menu.getPfID() < incarico.pfID || menu.getPfID() > incarico.pfID){
	    	final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdPopCreateAzione", 
	    			"utente loggato :"+menu.getChi()+" e manager: "+incarico.getResponsabile()+" dell'incarico non corrispondono",
	    			Evento.TipoEnum.ERROR,
	    			"Azione su Obj con ID",0);
	    	evtServizi.save(newEvtErr);
	    	return newEvtErr;
    	}
    	else if (!azione.getObiettivo().getStatoApprov().equals(Obiettivo.StatoApprovEnum.INTERLOCUTORIO)
    			&& !azione.getObiettivo().getStatoApprov().equals(Obiettivo.StatoApprovEnum.PROPOSTO)
    			){
    		final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdPopCreateAzione", 
	    			"lo stato dell'obiettivo � :"+azione.getObiettivo().getStatoApprov()+" mentre e' richiesto che sia INTERLOCUTORIO o PROPOSTO",
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
	        journal.setPerche("respPOPCreateAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
	        //
	        final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
    			"cmdPopCreateAzione", 
    			"azione creata: "+azione.toString(),
    			Evento.TipoEnum.SUCCESS,"Azione",
    			azione.getIdAzione());
	    	evtServizi.save(newEvtOk);
	        return newEvtOk;
    	}
    }
    
   
    @Override
    @Transactional
    @Secured({"ROLE_RESP_POP"})
    public void popUpdateAzione(Azione azione) {
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
	        journal.setPerche("respPOPUpdateAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
    	//}
    }
    
    @Transactional
    @Secured({"ROLE_RESP_POP"})
    public Evento cmdPopDeleteAzione(Azione azione) {
    	//
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(azione.getIncaricoID());
    	//
    	if (menu.getPfID() < incarico.pfID || menu.getPfID() > incarico.pfID){
	    	final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdPopDeleteAzione", 
	    			"utente loggato :"+menu.getChi()+" e manager: "+incarico.getResponsabile()+" dell'incarico non corrispondono",
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
	    			"cmdPopDeleteAzione", 
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
	        journal.setPerche("respPOPDeleteAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
	        //
	        final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
    			"cmdPopDeleteAzione", 
    			"azione cancellata: "+azione.toString(),
    			Evento.TipoEnum.SUCCESS,"Azione",
    			azione.getIdAzione());
	    	evtServizi.save(newEvtOk);
	        return newEvtOk;
    	}
    }
    
    @Override
    @Transactional
    @Secured({"ROLE_RESP_POP"})
    public void popUpdateRisultatoAzione(Azione azione) {
    	if (azione.getIncaricoID() == menu.getIdIncaricoScelta()
    			&&
    		azione.getObiettivo().isRendicontabile())
    		
            //System.out.println("PopServiceImpl.managerUpdateRisultatoAzione() id azione="+azione.getIdAzione());
        	actCmdServizi.save(azione);
	    	Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("UpdateRisultato Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("respPOPUpdateRisultatoAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
    }
    
   
    
   

	// 4. Proponi Obiettivo
    @Override
    @Transactional
    @Secured({"ROLE_RESP_POP"})
    public Evento cmdPopRendiInterlocutorioObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    	if (menu.getPfID() < incarico.pfID || menu.getPfID() > incarico.pfID){
	    	final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdPopRendiInterlocutorioObiettivo", 
	    			"utente loggato :"+menu.getChi()+" e manager: "+incarico.getResponsabile()+" dell'incarico non corrispondono",
	    			Evento.TipoEnum.ERROR,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
	    	evtServizi.save(newEvtErr);
	    	return newEvtErr;
    	}
    	else if (
    			obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.PROPOSTO) ||	
    			obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.RIVISTO) ||
    			!obiettivo.getTipo().equals(Obiettivo.TipoEnum.DIRIGENZIALE)
    			) {
    		obiettivo.managerRendiInterlocutorio();
    		objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("Rendi Interlocutorio Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("respPOPRendiInterlocutorio");
            journal.setQuando(new Date());
            journalServizi.save(journal);
            //
    		final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
	    			"cmdPopRendiInterlocutorioObiettivo", 
	    			"obiettivo: "+obiettivo.toString(),
	    			Evento.TipoEnum.SUCCESS,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
    		evtServizi.save(newEvtOk);
    		return newEvtOk;
    	}
    	else{
    		final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdPopRendiInterlocutorioObiettivo", 
	    			"lo stato dell'obiettivo è :"+obiettivo.getStatoApprov()+" mentre è richiesto PROPOSTO o RIVISTO",
	    			Evento.TipoEnum.ERROR,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
	    	evtServizi.save(newEvtErr);
	    	return newEvtErr;
    	}
    }
    

	@Override
	@Transactional
    @Secured({"ROLE_RESP_POP"})
	public Evento cmdPopDefPopObiettivo(Obiettivo obiettivo) {
		final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	//
    	if (menu.getPfID() < incarico.pfID || menu.getPfID() > incarico.pfID){
	    	final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdPopDefPopObiettivo", 
	    			"utente loggato :"+menu.getChi()+" e manager: "+incarico.getResponsabile()+" dell'incarico non corrispondono",
	    			Evento.TipoEnum.ERROR,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
	    	evtServizi.save(newEvtErr);
	    	return newEvtErr;
    	}
    	else if (!obiettivo.getTipo().equals(Obiettivo.TipoEnum.POS_ORGAN)) {
    		final Evento newEvtErr = Evento.createEvt(menu.getChi(), 
	    			"cmdPopDefCompartoObiettivo", 
	    			"il tipo di obiettivo è :"+obiettivo.getTipo()+" mentre è richiesto POP",
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
            journal.setCosa("DefPopObiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("respPOPDefCompartoObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
            //
            final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
	    			"cmdPopDefPopObiettivo", 
	    			"obiettivo accettato: "+obiettivo.toString(),
	    			Evento.TipoEnum.SUCCESS,
	    			"Obiettivo",
	    			obiettivo.getIdObiettivo());
            evtServizi.save(newEvtOk);
    		return newEvtOk;
        }  
	}
    
} // -------------------------------------------------------

