package it.sicilia.regione.gekoddd.geko.valutazione.application;


import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.AzioneCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazioneCmdService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.ValutazioneCmdService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneComparto;
import it.sicilia.regione.gekoddd.geko.valutazione.infrastructure.ValutazioneCompartoCmdService;
import it.sicilia.regione.gekoddd.log.model.journal.Journal;
import it.sicilia.regione.gekoddd.log.model.journal.JournalService;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ManagerValutazioneServiceService")
public class ManagerValutazioneServiceImpl implements ManagerValutazioneService{

	private static final Logger log = LoggerFactory.getLogger(ManagerValutazioneServiceImpl.class);
	
    @Autowired
    private JournalService journalServizi;
    @Autowired
    private ValutazioneCmdService valutazioneCmdServizi;
    @Autowired
    private ValutazioneCompartoCmdService valutazioneCompartoCmdServizi;

    @Autowired
    private AzioneAssegnazioneCmdService actAssCmdServizi;
    @Autowired
    private AzioneCmdService actCmdServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    // ------- Casi d'uso del manager -----------------------------------------
    
    
    
 // -----------------------------------------------------------------------
    @Transactional
    @Secured({"ROLE_MANAGER"})
    public void managerCreateValutazioneComparto(ValutazioneComparto valutazioneComparto) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(valutazioneComparto.getIncaricoID());
    	final PersonaFisicaGeko pf = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	final PersonaGiuridicaGeko pg = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);
        if (menu.getIdIncaricoScelta() == incarico.getIdIncarico()){
    		valutazioneCompartoCmdServizi.save(valutazioneComparto);
	    	Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("crea ValutazioneComparto con id: "+valutazioneComparto.getId()+ 
	        		", pf: "+pf.getCognomeNome()+
	        		", pg: "+pg.getDenominazione());
	        journal.setDove("ValutazioneComparto");
	        journal.setIdObj(valutazioneComparto.getId());
	        journal.setPerche("controllerCreateValutazioneComparto");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        }
    }
    
    @Transactional
    @Secured({"ROLE_MANAGER"})
    public void managerUpdateValutazioneComparto(ValutazioneComparto valutazioneComparto) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(valutazioneComparto.getIncaricoID());
    	final PersonaFisicaGeko pf = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	final PersonaGiuridicaGeko pg = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);
        if (menu.getIdIncaricoScelta()== incarico.getIdIncarico()){
    	valutazioneCompartoCmdServizi.save(valutazioneComparto);
	    	Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("update ValutazioneComparto con id: "+valutazioneComparto.getId()+ 
	        		", pf: "+pf.getCognomeNome()+
	        		", pg: "+pg.getDenominazione());
	        journal.setDove("ValutazioneComparto");
	        journal.setIdObj(valutazioneComparto.getId());
	        journal.setPerche("controllerUpdateValutazioneComparto");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        }
    }
    
    @Transactional
    @Secured({"ROLE_MANAGER"})
    public void managerDeleteValutazioneComparto(ValutazioneComparto valutazioneComparto) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(valutazioneComparto.getIncaricoID());
    	final Integer strutturaID = incarico.pgID;
    	final PersonaFisicaGeko pf = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	final PersonaGiuridicaGeko pg = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);
        if (menu.getIdIncaricoScelta()== incarico.getIdIncarico()){
    	valutazioneCompartoCmdServizi.delete(valutazioneComparto);
    		Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("delete ValutazioneComparto con id: "+valutazioneComparto.getId()+ 
	        		", pf: "+pf.getCognomeNome()+
	        		", pg: "+pg.getDenominazione());
	        journal.setDove("ValutazioneComparto");
	        journal.setIdObj(valutazioneComparto.getId());
	        journal.setPerche("controllerDeleteValutazioneComparto");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        }
    }
    
    @Override
    @Transactional
    @Secured({"ROLE_MANAGER"})
    public void managerUpdateAzioneAssegnazioneValutazione(AzioneAssegnazione azioneAssegnazione) {
        //if (azioneAssegnazione.getAzione().getObiettivo().getIncarico().getOpPersonaFisica().equals(userServizi.findPersonaOfLoggedUser())){
        log.info("managerUpdateAzioneAssegnazioneValutazione");

    		actAssCmdServizi.save(azioneAssegnazione);
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

	@Override
	public void managerCreateValutazionePop(Valutazione valutazione) {
		final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(valutazione.getIncaricoID());
    	final PersonaFisicaGeko pf = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	final PersonaGiuridicaGeko pg = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);
        	valutazioneCmdServizi.save(valutazione);
	    	Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("crea Valutazione Pop con id: "+valutazione.getId()+ 
	        		", pf: "+pf.getCognomeNome()+
	        		", pg: "+pg.getDenominazione());
	        journal.setDove("ValutazioneComparto");
	        journal.setIdObj(valutazione.getId());
	        journal.setPerche("managerCreateValutazionePop");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);        
	}

	@Override
	public void managerUpdateValutazionePop(Valutazione valutazione) {
		final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(valutazione.getIncaricoID());
    	final PersonaFisicaGeko pf = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	final PersonaGiuridicaGeko pg = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);
    	valutazioneCmdServizi.save(valutazione);
	    	Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("update ValutazionePop con id: "+valutazione.getId()+ 
	        		", pf: "+pf.getCognomeNome()+
	        		", pg: "+pg.getDenominazione());
	        journal.setDove("ValutazioneComparto");
	        journal.setIdObj(valutazione.getId());
	        journal.setPerche("controllmanagerValutazionePop");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);        
	}

	@Override
	public void managerDeleteValutazionePop(Valutazione valutazione) {
		final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(valutazione.getIncaricoID());
    	final Integer strutturaID = incarico.pgID;
    	final PersonaFisicaGeko pf = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	final PersonaGiuridicaGeko pg = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);        
    	valutazioneCmdServizi.delete(valutazione);
    		Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("delete ValutazioneComparto con id: "+valutazione.getId()+ 
	        		", pf: "+pf.getCognomeNome()+
	        		", pg: "+pg.getDenominazione());
	        journal.setDove("ValutazioneComparto");
	        journal.setIdObj(valutazione.getId());
	        journal.setPerche("controllerDeleteValutazioneComparto");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);        
	}
    
	@Transactional
    @Override
    @Secured({"ROLE_MANAGER"})
    public void managerUpdateCompletamentoAzione(Azione azione) {
    	
        	actCmdServizi.save(azione);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Completamento Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("managerUpdateCompletamentoAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        
    }
	
	@Transactional
    @Override
    @Secured({"ROLE_MANAGER"})
    public void managerUpdateValutazione(Valutazione valutazione) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(valutazione.getIncaricoID());
    	//final Integer dipartimentoID = (fromOrganikoServizi.findDipartimentoByPersonaFisicaID(incarico.pfID)).idPersona;;
    	final PersonaFisicaGeko pf = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	final PersonaGiuridicaGeko pg = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);
        //if (menu.getDipartimento().idPersona==dipartimentoID){
    		valutazioneCmdServizi.save(valutazione);
	    	Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("update Valutazione con id: "+valutazione.getId()+ 
	        		", pf: "+pf.getCognomeNome()+
	        		", pg: "+pg.getDenominazione());
	        journal.setDove("Valutazione");
	        journal.setIdObj(valutazione.getId());
	        journal.setPerche("managerUpdateValutazione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
    	//}
    }
	
} // -------------------------------------------------------

