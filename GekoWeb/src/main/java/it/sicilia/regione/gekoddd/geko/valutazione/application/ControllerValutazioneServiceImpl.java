/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.sicilia.regione.gekoddd.geko.valutazione.application;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.criticValut.CriticValut;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.criticValut.CriticValutCmdService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.ValutazioneCmdService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneComparto;
import it.sicilia.regione.gekoddd.geko.valutazione.infrastructure.ValutazioneCompartoCmdService;
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
@Service("ControllerValutazioneService")
public class ControllerValutazioneServiceImpl implements ControllerValutazioneService{

	private Log log = LogFactory.getLog(ControllerValutazioneServiceImpl.class);
	
	@Autowired
    private ValutazioneCmdService valutazioneCmdServizi;
	@Autowired
    private CriticValutCmdService critValutCmdServizi;
	@Autowired
    private ValutazioneCompartoCmdService valutazioneCompartoCmdServizi;
    @Autowired
    private JournalService journalServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
// ------- Casi d'uso del controller -----------------------------------------
    
    
   
   
    
    // aggiorna il campo stato di valutazione
    // 
   
    
    
    // -----------------------------------------------------------------------
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerCreateValutazione(Valutazione valutazione) {
    	
    		valutazioneCmdServizi.save(valutazione);
	    	Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("crea Valutazione con id: "+valutazione.getId());
	        		
	        journal.setDove("Valutazione");
	        journal.setIdObj(valutazione.getId());
	        journal.setPerche("controllerCreateValutazione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
    	
    }
    
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerUpdateValutazione(Valutazione valutazione) {
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
	        journal.setPerche("controllerUpdateValutazione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
    	//}
    }
    
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerDeleteValutazione(Valutazione valutazione) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(valutazione.getIncaricoID());
    	final Integer dipartimentoID = (fromOrganikoServizi.findDipartimentoByPersonaFisicaID(incarico.pfID)).idPersona;;
    	final PersonaFisicaGeko pf = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	final PersonaGiuridicaGeko pg = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);
        //if (menu.getDipartimento().idPersona==dipartimentoID){	
    		valutazioneCmdServizi.delete(valutazione);
    		Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("delete Valutazione con id: "+valutazione.getId()+ 
	        		", pf: "+pf.getCognomeNome()+
	        		", pg: "+pg.getDenominazione());
	        journal.setDove("Valutazione");
	        journal.setIdObj(valutazione.getId());
	        journal.setPerche("controllerDeleteValutazione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
    	//}
    }
    
 // -----------------------------------------------------------------------
   

 // -------------------------------------------------------------------------------
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerCreateCriticValut(CriticValut criticita) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(criticita.getIncaricoID());
    	final Integer dipartimentoID = (fromOrganikoServizi.findDipartimentoByPersonaFisicaID(incarico.pfID)).idPersona;;
        if (menu.getDipartimento().idPersona==dipartimentoID){
    		critValutCmdServizi.save(criticita);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("crea CriticValut con id: "+criticita.getId()+ ", "+criticita.getDescrizione());
	        journal.setDove("CriticValut");
	        journal.setIdObj(criticita.getId());
	        journal.setPerche("controllerCreateCriticValut");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        }
    }
    
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerUpdateCriticValut(CriticValut criticita) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(criticita.getIncaricoID());
    	final Integer dipartimentoID = (fromOrganikoServizi.findDipartimentoByPersonaFisicaID(incarico.pfID)).idPersona;;
        if (menu.getDipartimento().idPersona==dipartimentoID){
    		critValutCmdServizi.save(criticita);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("update CriticValut con id: "+criticita.getId()+ ", "+criticita.getDescrizione());
	        journal.setDove("CriticValut");
	        journal.setIdObj(criticita.getId());
	        journal.setPerche("controllerUpdateCriticValut");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        }
    }
    
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerDeleteCriticValut(CriticValut criticita) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(criticita.getIncaricoID());
    	final Integer dipartimentoID = (fromOrganikoServizi.findDipartimentoByPersonaFisicaID(incarico.pfID)).idPersona;;
        if (menu.getDipartimento().idPersona==dipartimentoID){
    		critValutCmdServizi.delete(criticita);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("delete Criticita con id: "+criticita.getId()+ ", "+criticita.getDescrizione());
	        journal.setDove("CriticValut");
	        journal.setIdObj(criticita.getId());
	        journal.setPerche("controllerDeleteCriticValut");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
	        
        }
    }
    

   // -------------- comparto
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerCreateValutazioneComparto(ValutazioneComparto valutazioneComparto) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(valutazioneComparto.getIncaricoID());
    	final PersonaFisicaGeko pf = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	final PersonaGiuridicaGeko pg = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);
       
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
    
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerUpdateValutazioneComparto(ValutazioneComparto valutazioneComparto) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(valutazioneComparto.getIncaricoID());
    	final PersonaFisicaGeko pf = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	final PersonaGiuridicaGeko pg = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);
        
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
    
    @Transactional
    @Override
    @Secured({"ROLE_CONTROLLER"})
    public void controllerDeleteValutazioneComparto(ValutazioneComparto valutazioneComparto) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(valutazioneComparto.getIncaricoID());
    	final Integer strutturaID = incarico.pgID;
    	final PersonaFisicaGeko pf = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	final PersonaGiuridicaGeko pg = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);
        
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
    
    
    
} // ---------------------------------------------------------------------------------
