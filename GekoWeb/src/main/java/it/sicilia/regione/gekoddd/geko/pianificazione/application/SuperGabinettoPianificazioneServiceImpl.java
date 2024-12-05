package it.sicilia.regione.gekoddd.geko.pianificazione.application;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategica;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategicaCmdService;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategico;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategicoCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.outcome.Outcome;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.outcome.OutcomeCmdService;
import it.sicilia.regione.gekoddd.log.model.evento.Evento;
import it.sicilia.regione.gekoddd.log.model.evento.EventoService;
import it.sicilia.regione.gekoddd.log.model.journal.Journal;
import it.sicilia.regione.gekoddd.log.model.journal.JournalService;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Cirrito
 */
@Service("superGabinettoPianificazioneService")
public class SuperGabinettoPianificazioneServiceImpl implements SuperGabinettoPianificazioneService{

	private Log log = LogFactory.getLog(SuperGabinettoPianificazioneServiceImpl.class);
	
	//
    @Autowired
    private AreaStrategicaCmdService areaStratCmdServizi;
   
    @Autowired
    private ObiettivoStrategicoCmdService obiettivoStrategicoCmdServizi;
    
    @Autowired
    private OutcomeCmdService outcomeCmdServizi;
	
    @Autowired
    private JournalService journalServizi;
    @Autowired
    private EventoService evtServizi;
    
    @Autowired
    private Menu menu;
    
// ------- Casi d'uso -----------------------------------------

	@Override
	@Transactional
    @Secured({"ROLE_SUPERGABINETTO"})
	public Evento cmdSuperGabinettoCreateAreaStrategica(AreaStrategica areaStrategica) {
		areaStratCmdServizi.save(areaStrategica);
		Journal journal = new Journal();
        journal.setChi(menu.getChi());
        journal.setCosa("Create Area Strategica con id: "+areaStrategica.getId()+ ", "+areaStrategica.getDescrizione());
        journal.setDove("AreaStrategica");
        journal.setIdObj(areaStrategica.getId());
        journal.setPerche("superGabinettoCreateAreaStrategica");
        journal.setQuando(new Date());
        journalServizi.save(journal);
        //
		final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
    			"cmdSuperGabinettoCreateAreaStrategica", 
    			"areaStrategica: "+areaStrategica.toString(),
    			Evento.TipoEnum.SUCCESS,
    			"AreaStrategica",
    			areaStrategica.getId());
		evtServizi.save(newEvtOk);
		return newEvtOk;
	}
    
	@Override
	@Transactional
    @Secured({"ROLE_SUPERGABINETTO"})
	public Evento cmdSuperGabinettoUpdateAreaStrategica(AreaStrategica areaStrategica) {
		areaStratCmdServizi.save(areaStrategica);
		Journal journal = new Journal();
        journal.setChi(menu.getChi());
        journal.setCosa("Update Area Strategica con id: "+areaStrategica.getId()+ ", "+areaStrategica.getDescrizione());
        journal.setDove("AreaStrategica");
        journal.setIdObj(areaStrategica.getId());
        journal.setPerche("superGabinettoUpdateAreaStrategica");
        journal.setQuando(new Date());
        journalServizi.save(journal);
        //
		final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
    			"cmdSuperGabinettoUpdateAreaStrategica", 
    			"areaStrategica: "+areaStrategica.toString(),
    			Evento.TipoEnum.SUCCESS,
    			"AreaStrategica",
    			areaStrategica.getId());
		evtServizi.save(newEvtOk);
		return newEvtOk;
	}
	
	@Override
	@Transactional
    @Secured({"ROLE_SUPERGABINETTO"})
	public Evento cmdSuperGabinettoDeleteAreaStrategica(AreaStrategica areaStrategica) {
		areaStratCmdServizi.delete(areaStrategica);
		Journal journal = new Journal();
        journal.setChi(menu.getChi());
        journal.setCosa("Delete Area Strategica con id: "+areaStrategica.getId()+ ", "+areaStrategica.getDescrizione());
        journal.setDove("AreaStrategica");
        journal.setIdObj(areaStrategica.getId());
        journal.setPerche("superGabinettoDeleteAreaStrategica");
        journal.setQuando(new Date());
        journalServizi.save(journal);
        //
		final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
    			"cmdSuperGabinettoDeleteAreaStrategica", 
    			"areaStrategica: "+areaStrategica.toString(),
    			Evento.TipoEnum.SUCCESS,
    			"AreaStrategica",
    			areaStrategica.getId());
		evtServizi.save(newEvtOk);
		return newEvtOk;
	}
    
    
	
	// ----------------------------------------------------------------------------------------
	@Override
	@Transactional
    @Secured({"ROLE_SUPERGABINETTO"})
	public Evento cmdSuperGabinettoCreateObiettivoStrategico(ObiettivoStrategico obiettivoStrategico) {
		obiettivoStrategicoCmdServizi.save(obiettivoStrategico);
		Journal journal = new Journal();
        journal.setChi(menu.getChi());
        journal.setCosa("Create ObiettivoStrategico con id: "+obiettivoStrategico.getId()+ ", "+obiettivoStrategico.getDescrizione());
        journal.setDove("ObiettivoStrategico");
        journal.setIdObj(obiettivoStrategico.getId());
        journal.setPerche("superGabinettoCreateObiettivoStrategico");
        journal.setQuando(new Date());
        journalServizi.save(journal);
        //
		final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
    			"cmdSuperGabinettoCreateObiettivoStrategico", 
    			"obiettivoStrategico: "+obiettivoStrategico.toString(),
    			Evento.TipoEnum.SUCCESS,
    			"ObiettivoStrategico",
    			obiettivoStrategico.getId());
		evtServizi.save(newEvtOk);
		return newEvtOk;
	}
    
	@Override
	@Transactional
    @Secured({"ROLE_SUPERGABINETTO"})
	public Evento cmdSuperGabinettoUpdateObiettivoStrategico(ObiettivoStrategico obiettivoStrategico) {
		obiettivoStrategicoCmdServizi.save(obiettivoStrategico);
		Journal journal = new Journal();
        journal.setChi(menu.getChi());
        journal.setCosa("Update ObiettivoStrategico con id: "+obiettivoStrategico.getId()+ ", "+obiettivoStrategico.getDescrizione());
        journal.setDove("ObiettivoStrategico");
        journal.setIdObj(obiettivoStrategico.getId());
        journal.setPerche("superGabinettoUpdateObiettivoStrategico");
        journal.setQuando(new Date());
        journalServizi.save(journal);
        //
		final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
    			"cmdSuperGabinettoUpdateObiettivoStrategico", 
    			"obiettivoStrategico: "+obiettivoStrategico.toString(),
    			Evento.TipoEnum.SUCCESS,
    			"ObiettivoStrategico",
    			obiettivoStrategico.getId());
		evtServizi.save(newEvtOk);
		return newEvtOk;
	}
	
	@Override
	@Transactional
    @Secured({"ROLE_SUPERGABINETTO"})
	public Evento cmdSuperGabinettoDeleteObiettivoStrategico(ObiettivoStrategico obiettivoStrategico) {
		obiettivoStrategicoCmdServizi.delete(obiettivoStrategico);
		Journal journal = new Journal();
        journal.setChi(menu.getChi());
        journal.setCosa("Delete ObiettivoStrategico con id: "+obiettivoStrategico.getId()+ ", "+obiettivoStrategico.getDescrizione());
        journal.setDove("ObiettivoStrategico");
        journal.setIdObj(obiettivoStrategico.getId());
        journal.setPerche("superGabinettoDeleteObiettivoStrategico");
        journal.setQuando(new Date());
        journalServizi.save(journal);
        //
		final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
    			"cmdSuperGabinettoDeleteObiettivoStrategico", 
    			"obiettivoStrategico: "+obiettivoStrategico.toString(),
    			Evento.TipoEnum.SUCCESS,
    			"ObiettivoStrategico",
    			obiettivoStrategico.getId());
		evtServizi.save(newEvtOk);
		return newEvtOk;
	}
	
	// ----------------------------------------------------------------------------------------
		
		
		// ----------------------------------------------------------------------------------------
				@Override
				@Transactional
			    @Secured({"ROLE_SUPERGABINETTO"})
				public Evento cmdSuperGabinettoCreateOutcome(Outcome outcome) {
					outcomeCmdServizi.save(outcome);
					Journal journal = new Journal();
			        journal.setChi(menu.getChi());
			        journal.setCosa("Create Outcome con id: "+outcome.getId()+ ", "+outcome.getDescrizione());
			        journal.setDove("Outcome");
			        journal.setIdObj(outcome.getId());
			        journal.setPerche("superGabinettoCreateOutcome");
			        journal.setQuando(new Date());
			        journalServizi.save(journal);
			        //
					final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
			    			"cmdSuperGabinettoCreateOutcome", 
			    			"outcome: "+outcome.toString(),
			    			Evento.TipoEnum.SUCCESS,
			    			"cmdSuperGabinettoCreateOutcome",
			    			outcome.getId());
					evtServizi.save(newEvtOk);
					return newEvtOk;
				}
			    
				@Override
				@Transactional
			    @Secured({"ROLE_SUPERGABINETTO"})
				public Evento cmdSuperGabinettoUpdateOutcome(Outcome outcome) {
					outcomeCmdServizi.save(outcome);
					Journal journal = new Journal();
			        journal.setChi(menu.getChi());
			        journal.setCosa("Update outcome con id: "+outcome.getId()+ ", "+outcome.getDescrizione());
			        journal.setDove("outcome");
			        journal.setIdObj(outcome.getId());
			        journal.setPerche("cmdSuperGabinettoUpdateOutcome");
			        journal.setQuando(new Date());
			        journalServizi.save(journal);
			        //
					final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
			    			"cmdSuperGabinettoUpdateOutcome", 
			    			"outcome: "+outcome.toString(),
			    			Evento.TipoEnum.SUCCESS,
			    			"Outcome",
			    			outcome.getId());
					evtServizi.save(newEvtOk);
					return newEvtOk;
				}
				
				@Override
				@Transactional
			    @Secured({"ROLE_SUPERGABINETTO"})
				public Evento cmdSuperGabinettoDeleteOutcome(Outcome outcome) {
					outcomeCmdServizi.delete(outcome);
					Journal journal = new Journal();
			        journal.setChi(menu.getChi());
			        journal.setCosa("Delete Outcome con id: "+outcome.getId()+ ", "+outcome.getDescrizione());
			        journal.setDove("Outcome");
			        journal.setIdObj(outcome.getId());
			        journal.setPerche("superGabinettoDeleteOutcome");
			        journal.setQuando(new Date());
			        journalServizi.save(journal);
			        //
					final Evento newEvtOk = Evento.createEvt(menu.getChi(), 
			    			"cmdSuperGabinettoDeleteOutcome", 
			    			"outcome: "+outcome.toString(),
			    			Evento.TipoEnum.SUCCESS,
			    			"Outcome",
			    			outcome.getId());
					evtServizi.save(newEvtOk);
					return newEvtOk;
				}
				
} // ---------------------------------------------------------------------------------
