package it.sicilia.regione.gekoddd.geko.programmazione.web.resppop;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategicoQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.application.RespPopProgrammazioneService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.command.Command;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoQryService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.ValutazioneQryService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneCompartoQryService;
import it.sicilia.regione.gekoddd.log.model.evento.Evento;
import it.sicilia.regione.gekoddd.log.model.evento.EventoService;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * @author Cirrito
 *
 */

@Controller
@RequestMapping("/respPop")
public class ResppopProgrListController  {
	
    private static final Logger log = LoggerFactory.getLogger(ResppopProgrListController.class);
	//
    @Autowired
    private ObiettivoQryService objQryServizi;    
    @Autowired
    private EventoService evtServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    //@Autowired
    //private ValutazioneCompartoQryService valutazioneCompartoServizi;
    //@Autowired
    //private ValutazioneQryService valutazioneQryServizi;
    @Autowired
    private Menu menu;
    
    
    //
    public ResppopProgrListController() { }
    
    //
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }   
   
    // ___________________ Programmazione _______________________________
    
    private List<Command> listAvailableObjCommands(Obiettivo obj){
    	List<Command> allowed = new ArrayList<Command>();
    	List<Command> objCmds = obj.getAllowedCommands();
    	final String role = "RESP_POP";
    	//log.info("listAvailableCommands for obj"+obj.getDescrizione());
    	//
    	if(null!=objCmds && !objCmds.isEmpty()){
    	for (Command cmd : objCmds){
    		//log.info("cmd role: "+cmd.getRole()+" cmd label: "+cmd.getLabel());
    		if (cmd.getRole().equals(role)){
    			//log.info("role ok "+" cmd label: "+cmd.getLabel());

    			switch(cmd.getLabel()) {
    			
    			case "RENDI INTERLOCUTORIO":
    				cmd.setUri("/resppopObj/"+obj.getIdObiettivo()+"/rendiInterlocutorio/"+obj.getIncaricoID());
    				break;
    				
    			case "DEFINISCI":
    				cmd.setUri("/resppopObj/"+obj.getIdObiettivo()+"/accetta/"+obj.getIncaricoID());
    				break;
    				
    			// obiettivo comparto
    			case "RENDI DEFINITIVO":
    				cmd.setUri("/resppopObj/"+obj.getIdObiettivo()+"/defComparto/"+obj.getIncaricoID());
    				break;
    				 
    			case "PROPONI":
    				cmd.setUri("/resppopObj/"+obj.getIdObiettivo()+"/proponi/"+obj.getIncaricoID());
    				break;
    			
    			case "MODIFICA":
    				cmd.setUri("/resppopObj/"+obj.getIdObiettivo()+"/edit/"+obj.getIncaricoID());
    				break;
    			case "ELIMINA":
    				cmd.setUri("/resppopObj/"+obj.getIdObiettivo()+"/edit/"+obj.getIncaricoID());
    				break;
    			case "CREA AZIONE":
    				cmd.setUri("/resppopAct/new/"+obj.getIdObiettivo());
    				break;
    			case "MODIFICA COMPARTO":
    				cmd.setUri("/resppopObj/"+obj.getIdObiettivo()+"/editComparto/"+obj.getIncaricoID());
    				break;
    			case "MODIFICA POP":
    				cmd.setUri("/resppopObj/"+obj.getIdObiettivo()+"/popEdit/"+obj.getIncaricoPadreID());
    				break;
    			}
    			
    			//log.info("questa uri? "+cmd.getUri());
    			allowed.add(cmd);
    		}
    		
    	}
    	} 
    	return allowed;
    }
    
    private List<Command> listAvailableActCommands(Azione act){
    	List<Command> allowed = new ArrayList<Command>();
    	List<Command> actCmds = act.getAllowedCommands();
    	final String role = "RESP_POP";
    	//log.info("listAvailableCommands for obj"+obj.getDescrizione());
    	//
    	if(null!=actCmds && !actCmds.isEmpty()){
	    	for (Command cmd : actCmds){
	    		//log.info("cmd role: "+cmd.getRole()+" cmd label: "+cmd.getLabel());
	    		if (cmd.getRole().equals(role)){
	    			//log.info("role ok "+" cmd label: "+cmd.getLabel());
	
	    			switch(cmd.getLabel()) {
	    			
	    			case "MODIFICA":
	    				cmd.setUri("/resppopAct/"+act.getIdAzione()+"/edit/"+act.getIncaricoID());
	    				break;
	    			case "ELIMINA":
	    				cmd.setUri("/resppopAct/"+act.getIdAzione()+"/edit/"+act.getIncaricoID());
	    				break;
	    			
	    			//log.info("questa uri? "+cmd.getUri());
	    			} // switch
	    			allowed.add(cmd);
	    		} // if
	    	} // for
    	}
    	return allowed;
    }
    
   
	       
    // ------- DDD ---------------
    // 1. Programmazione
 // 1.1 lista obiettivi e azioni e dipendenti per consentirne la modifica
    @RequestMapping(value="modifyProgrammazionePopIncarico/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String modifyProgrammazionePopIncaricoGet(@PathVariable("anno") int anno,
    		@PathVariable("idIncarico") int idIncarico,
    		Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
        //final PersonaGiuridicaGeko struttura = fromOrganikoServizi.findPersonaGiuridicaById(incarico.getPgID());
        final List<Obiettivo> listObiettivi = objQryServizi.findObiettiviPopSenzaPersoneByIncaricoIDAndAnno(incarico.getIdIncarico(), anno);
 	//	  
	if (null != incarico) {
            for (Obiettivo obj : listObiettivi){
                    obj.setIncarico(incarico);
                    List<Command> cmds = this.listAvailableObjCommands(obj);
                    obj.setGuiCommands(cmds);
                    for(Azione act: obj.getAzioni()){
                            List<Command> actcmds = this.listAvailableActCommands(act);
                            act.setGuiCommands(actcmds);
                    }
            }
    	    //
            model.addAttribute("struttura", incarico.denominazioneStruttura);
            model.addAttribute("responsabile", incarico.responsabile);
            model.addAttribute("anno", anno);
            model.addAttribute("listObiettivi", listObiettivi);
            model.addAttribute("idIncarico", idIncarico);
            return "geko/programmazione/resppop/modifyProgrammazioneIncaricoPop";
        }
        else return "geko/programmazione/manager/errNoResponsabileStruttura";		    
    } // fine metodo modifyProgrammazioneIncaricoGet
    
 
    // 1.2 lista obiettivi e azioni e dipendenti in base al dirigente con assegnazioni
    @RequestMapping(value="listProgrammazionePopIncarico/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listProgrammazionePopIncaricoGet(@PathVariable("anno") int anno, 
    		@PathVariable("idIncarico") int idIncarico, Model model) {
    	//
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
        //final PersonaGiuridicaGeko struttura = fromOrganikoServizi.findPersonaGiuridicaById(incarico.getPgID());
        final List<Obiettivo> listObiettivi = objQryServizi.findObiettiviPopSenzaPersoneByIncaricoIDAndAnno(incarico.getIdIncarico(), anno);    
    	//
	    if (null != incarico) {
	    	//
	    	model.addAttribute("struttura", incarico.getDenominazioneStruttura());
	    	model.addAttribute("competenze", incarico.competenzeStruttura);
	    	model.addAttribute("responsabile", incarico.responsabile);
	        model.addAttribute("anno", anno);
	        model.addAttribute("listObiettivi", listObiettivi);
	        model.addAttribute("idIncarico", idIncarico);
	        return "geko/programmazione/resppop/listProgrammazioneIncaricoPop";
	    }	    
    	else return "geko/programmazione/manager/pageNAManager";
    }
    
 // 1.3 lista obiettivi e azioni e dipendenti in base al dirigente senza assegnazioni
    @RequestMapping(value="listCompattaProgrammazioneIncarico/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listCompattaProgrammazioneIncaricoGet(@PathVariable("anno") int anno, 
    		@PathVariable("idIncarico") int idIncarico,
    		Model model) {
    	//
    	if (idIncarico == menu.getIdIncaricoScelta()){
        	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
        	//
    	    if (null != incarico) {
	    	//final List<Obiettivo> listObiettivi = incarico.getObiettiviTotAnno();
	    	final List<Obiettivo> listObiettivi = objQryServizi.findObiettiviPrioritariByIncaricoIDAndAnno(idIncarico,anno);
	    	model.addAttribute("struttura", incarico.getDenominazioneStruttura());
	    	model.addAttribute("responsabile", incarico.responsabile);
	        model.addAttribute("anno", anno);
	        model.addAttribute("listObiettivi", listObiettivi);
	        return "geko/programmazione/manager/listProgrammazioneCompattaIncaricoManager";
	    }
	    else return "geko/programmazione/manager/errNoResponsabileStruttura";
    	}
    	else return "geko/programmazione/manager/pageNAManager";
    }
    

    
 // visualizzazione errore nel comando
    @RequestMapping(value="errCmdManager/{idEvento}", method = RequestMethod.GET)
    public String errCmdManagerGet(@PathVariable("idEvento") int idEvento, Model model) {
        //
    	final Evento evento = evtServizi.findById(idEvento);
        model.addAttribute("evento", evento); 
        //
        final String viewName = "errComandoManager";
        model.addAttribute("viewName", viewName); 
        //
        return "geko/programmazione/manager/"+viewName;
    }
    
    
   
    
    
   
   
} // ---------------------------------------------
