package it.sicilia.regione.gekoddd.geko.programmazione.web.manager;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategica;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategicaQryService;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategico;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategicoQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.application.ManagerProgrammazioneService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione.CompletamentoEnum;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazioneQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.command.Command;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione.ObiettivoAssegnazioneQryService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.ValutazioneQryService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneComparto;
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
@RequestMapping("/dirigente")
public class ManagerProgrListController  {
	
	private static final Logger log = LoggerFactory.getLogger(ManagerProgrListController.class);
	//
    @Autowired
    private ObiettivoQryService objQryServizi;
    @Autowired
    private AzioneAssegnazioneQryService azAssServizi;  
    @Autowired
    private AreaStrategicaQryService areaStrategicaQryServizi;
    @Autowired
    private ManagerProgrammazioneService managerServizi;
    @Autowired
    private EventoService evtServizi;
    @Autowired
    private ObiettivoStrategicoQryService objStratServizi;
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private ValutazioneCompartoQryService valutazioneCompartoServizi;
    @Autowired
    private ValutazioneQryService valutazioneQryServizi;
    @Autowired
    private Menu menu;
    
    
    //
    public ManagerProgrListController() { }
    
    //
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
 
    // ___________________ Avvisi _______________________________
    //
    @RequestMapping(value="listAvvisiManager", method = RequestMethod.GET)
    public String listDirettivaController(Model model) {return "geko/programmazione/manager/listAvvisiManager";}  
    
    // ___________________ Incarichi _______________________________
    // lista incarichi
    @RequestMapping(value="listIncarichiManager/{idIncarico}", method = RequestMethod.GET)
    public String listIncarichiManagerGet(@PathVariable("idIncarico") int idIncarico, Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
        List<IncaricoGeko> listIncarichi = new ArrayList<IncaricoGeko>(fromOrganikoServizi.findIncarichiByDirigenteIDAndAnno(manager.idPersona, menu.getAnno()));
        //
        if (null!=manager){
            model.addAttribute("manager", manager);
            model.addAttribute("listIncarichi", listIncarichi);
        }
        return "geko/programmazione/manager/listIncarichiManager";
    }
   
    // ___________________ Programmazione _______________________________
    
    private List<Command> listAvailableObjCommands(Obiettivo obj){
    	List<Command> allowed = new ArrayList<Command>();
    	List<Command> objCmds = obj.getAllowedCommands();
    	final String role = "MANAGER";
    	//log.info("listAvailableCommands for obj"+obj.getDescrizione());
    	//
    	if(null!=objCmds && !objCmds.isEmpty()){
    	for (Command cmd : objCmds){
    		//log.info("cmd role: "+cmd.getRole()+" cmd label: "+cmd.getLabel());
    		if (cmd.getRole().equals(role)){
    			//log.info("role ok "+" cmd label: "+cmd.getLabel());

    			switch(cmd.getLabel()) {
    			
    			case "RENDI INTERLOCUTORIO":
    				if(obj.getTipo().equals(Obiettivo.TipoEnum.POS_ORGAN)) cmd.setUri("/dirigenteObj/"+obj.getIdObiettivo()+"/interPop/"+obj.getIncaricoPadreID());
    				else cmd.setUri("/dirigenteObj/"+obj.getIdObiettivo()+"/rendiInterlocutorio/"+obj.getIncaricoID());
    				break;
    				
    			case "DEFINISCI":
    				cmd.setUri("/dirigenteObj/"+obj.getIdObiettivo()+"/accetta/"+obj.getIncaricoID());
    				break;
    				
    			// obiettivo comparto
    			
    				
    			case "RENDI DEFINITIVO":
    				if(obj.getTipo().equals(Obiettivo.TipoEnum.POS_ORGAN)) cmd.setUri("/dirigenteObj/"+obj.getIdObiettivo()+"/defPop/"+obj.getIncaricoPadreID());
    				else cmd.setUri("/dirigenteObj/"+obj.getIdObiettivo()+"/defComparto/"+obj.getIncaricoID());
    				break;
    				 
    			case "PROPONI":
    				cmd.setUri("/dirigenteObj/"+obj.getIdObiettivo()+"/proponi/"+obj.getIncaricoID());
    				break;
    			
    			case "MODIFICA":
    				cmd.setUri("/dirigenteObj/"+obj.getIdObiettivo()+"/edit/"+obj.getIncaricoID());
    				break;
    			case "ELIMINA":
    				cmd.setUri("/dirigenteObj/"+obj.getIdObiettivo()+"/edit/"+obj.getIncaricoID());
    				break;
    			case "CREA AZIONE":
    				if(obj.getTipo().equals(Obiettivo.TipoEnum.POS_ORGAN)) cmd.setUri("/dirigenteAct/new/"+obj.getIdObiettivo()+"/"+obj.getIncaricoPadreID());
    				else cmd.setUri("/dirigenteAct/new/"+obj.getIdObiettivo());
    				break;
    			case "MODIFICA COMPARTO":
    				cmd.setUri("/dirigenteObj/"+obj.getIdObiettivo()+"/editComparto/"+obj.getIncaricoID());
    				break;
    			case "MODIFICA POP":
    				cmd.setUri("/dirigenteObj/"+obj.getIdObiettivo()+"/popEdit/"+obj.getIncaricoPadreID());
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
    	final String role = "MANAGER";
    	//log.info("listAvailableCommands for obj"+obj.getDescrizione());
    	//
    	if(null!=actCmds && !actCmds.isEmpty()){
	    	for (Command cmd : actCmds){
	    		//log.info("cmd role: "+cmd.getRole()+" cmd label: "+cmd.getLabel());
	    		if (cmd.getRole().equals(role)){
	    			//log.info("role ok "+" cmd label: "+cmd.getLabel());
	
	    			switch(cmd.getLabel()) {
	    			
	    			case "MODIFICA":
	    				if(act.getObiettivo().getTipo().equals(Obiettivo.TipoEnum.POS_ORGAN))
	    					cmd.setUri("/dirigenteAct/"+act.getIdAzione()+"/edit/"+act.getObiettivo().getIncaricoPadreID());
	    				else cmd.setUri("/dirigenteAct/"+act.getIdAzione()+"/edit/"+act.getIncaricoID());
	    				break;
	    			case "ELIMINA":
	    				if(act.getObiettivo().getTipo().equals(Obiettivo.TipoEnum.POS_ORGAN))
	    					cmd.setUri("/dirigenteAct/"+act.getIdAzione()+"/edit/"+act.getObiettivo().getIncaricoPadreID());
	    				else cmd.setUri("/dirigenteAct/"+act.getIdAzione()+"/edit/"+act.getIncaricoID());
	    				break;
	    			case "RENDI TASSATIVA":
	    				cmd.setUri("/dirigenteAct/"+act.getIdAzione()+"/rendiTassativa");
	    				break;
	    			case "TOGLI TASSATIVA":
	    				cmd.setUri("/dirigenteAct/"+act.getIdAzione()+"/togliTassativa");
	    				break;	
	    			
	    			//log.info("questa uri? "+cmd.getUri());
	    			} // switch
	    			allowed.add(cmd);
	    		} // if
	    	} // for
    	}
    	return allowed;
    }
    
    
    
    
    // lista obiettivi e azioni e dipendenti in base al nome del dirigente con assegnazioni
    @RequestMapping(value="duplicaProgrammazioneIncarico/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String duplicaProgrammazioneIncaricoGet(@PathVariable("anno") int anno, 
    		@PathVariable("idIncarico") int idIncarico, Model model) {
       
	    final List<Obiettivo> listObiettivi = objQryServizi.findObiettiviTotaliByIncaricoIDAndAnno(idIncarico,anno);
        // --------------------------------
    	if (!listObiettivi.isEmpty()) {
            	// clona gli obiettivi su anno successivo
            	for(Obiettivo obj : listObiettivi){
            		if (!obj.getTipo().equals(Obiettivo.TipoEnum.DIRIGENZIALE)  ){
            			Obiettivo obiettivo = obj.clone();
            			// creo in modo da avere la sua Id
            			log.info("duplicaProgrammazioneIncarico() crea nuovo obiettivo");
            			this.managerServizi.managerDuplicaObiettivo(obiettivo);
            			//System.out.println("ManagerListController.duplicaPianificazioneManagerGet() obiettivo con id "+ obj.getIdObiettivo() +" clonato con nuova id " + obiettivo.getIdObiettivo());
            			// clona le azioni
            			for(Azione act : obj.getAzioni()){
            				Azione azione = act.clone();
            				log.info("duplicaProgrammazioneIncarico() crea nuova azione da "+azione.getDenominazione());
            		        azione.setObiettivo(obiettivo);
            		        azione.setCompletamento(CompletamentoEnum.DA_VALUTARE);
            		        //obiettivo.addAzioneToObiettivo(azione);
            		        this.managerServizi.managerDuplicaAzione(azione);
            		        // clona le assegnazioni
            		        for(AzioneAssegnazione assegn : act.getAssegnazioni()){
            		        	AzioneAssegnazione assegnazione = assegn.clone();
            		        	log.info("duplicaProgrammazioneIncarico() crea nuova azioneAssegnazione");
            		        	assegnazione.setAzione(azione);
            		        	//azione.addAssegnazioneToAzione(assegnazione);
            		        	this.managerServizi.managerDuplicaAzioneAssegnazione(assegnazione);
            		        } // fine for assegnazioni
            			} // fine for azioni
            		} // fine if
            	} 
            	
            }
        
    	return "redirect:/dirigente/modifyProgrammazioneIncarico/" + (anno+1)+"/"+idIncarico;
    }   
    
    // lista dipendenti della struttura e sottostruttura
    @RequestMapping(value="listDipendenti/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listDipendentiGet(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
        //
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
        model.addAttribute("anno", anno);
        model.addAttribute("servizio", incarico.denominazioneStruttura);
        
        //
        return "geko/programmazione/manager/listDipendentiStruttura"; // rivedere jsp
    }
    
    
    
   
    
		
    //-----------------------Programmazione --------------------


    
	
    

    
   
    //-----------------------Direttiva --------------------
    // lista compatta pianificazione obiettivi e azioni del dipartimento
    @RequestMapping(value="listDirettivaManager/{anno}", method = RequestMethod.GET)
    public String listDirettivaManager(@PathVariable("anno") int anno, Model model) {
    	List<AreaStrategica> listAreeStrategiche = areaStrategicaQryServizi.findByAnno(anno);
    	//
        for (AreaStrategica area : listAreeStrategiche){
        	List<ObiettivoStrategico> lstobjstr = objStratServizi.findByAreaStrategicaAndAnno(area, anno);
        	area.setObiettiviStrategici(lstobjstr);
        	if(!lstobjstr.isEmpty()){
        		for(ObiettivoStrategico stra : lstobjstr){
        			if(!stra.getAssociazObiettivi().isEmpty()){
        				for(AssociazObiettivi assoc : stra.getAssociazObiettivi()){
            				Obiettivo apicale = assoc.getApicale();
            				IncaricoGeko inc = fromOrganikoServizi.findIncaricoById(apicale.getIncaricoID());
            				apicale.setResponsabile(inc.getDenominazioneDipartimento()
            						+" / "+inc.getDenominazioneStruttura()+ " - "+inc.getResponsabile()); 
            			}
        			}
        		}
        	}
        		
        }
        
    	model.addAttribute("listAreeStrategiche", listAreeStrategiche); 
        model.addAttribute("anno", anno);
        //
        String pageName = "geko2019/pianificazione/listDirettiva";
        model.addAttribute("pageName", pageName);
        return pageName;
    }  
    
 // lista valutazione dipendenti della struttura e sottostruttura
    @RequestMapping(value="listCompartoProgrammazione/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listDipendentiAssegnazioniProgrammazioneGet(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico,Model model) {
        //
       	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	final PersonaGiuridicaGeko struttura = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);
        if (null != manager) {
        	List<PersonaFisicaGeko> listDipendenti = fromOrganikoServizi.findDipendentiStrictByStrutturaIDAndAnno(incarico.pgID,anno);
        	//struttura.getDipendenti();
        	listDipendenti.remove(manager);
        	//
        	Map<PersonaFisicaGeko,List<AzioneAssegnazione>> mapDipendentiAssegnazioni = new LinkedHashMap<PersonaFisicaGeko,List<AzioneAssegnazione>>();
        	Map<PersonaFisicaGeko,List<ValutazioneComparto>> mapDipendentiValutazioneComparto = new LinkedHashMap<PersonaFisicaGeko,List<ValutazioneComparto>>();
        	if (listDipendenti!= null && !listDipendenti.isEmpty()){
	            //
	            for(PersonaFisicaGeko pf : listDipendenti){
	            	//pf.setAnno(anno);
	            	pf.setIncaricoValutazioneID(idIncarico);
	            	//
	            	List<AzioneAssegnazione> assegnazioni = azAssServizi.findByPfIDAndIncaricoIDAndAnno(pf.idPersona, idIncarico, anno);
	            	if (!assegnazioni.isEmpty()) {
	            		pf.setAssegnazioni(assegnazioni);
	            		mapDipendentiAssegnazioni.put(pf, assegnazioni);
	            	}
	            	//
	            	List<ValutazioneComparto> valutazioni = valutazioneCompartoServizi.findByPfIDAndIncaricoIDAndAnno(pf.idPersona, incarico.idIncarico, anno);
		        	if (valutazioni!=null && !valutazioni.isEmpty()) {
		        		pf.setValutazioni(valutazioni);
		            	mapDipendentiValutazioneComparto.put(pf, valutazioni);
		        	}
		        	else {
		        		List<ValutazioneComparto> emptyList = new ArrayList<ValutazioneComparto>();
		        		pf.setValutazioni(emptyList);
		            	mapDipendentiValutazioneComparto.put(pf, emptyList);
		        	}
	            	//System.out.println("listDipendentiAssegnazioniProgrammazioneGet() pf= "+pf.stringa);
	            }
        	}
        //
        model.addAttribute("mapDipendentiAssegnazioni", mapDipendentiAssegnazioni);  
        model.addAttribute("mapDipendentiValutazioneComparto", mapDipendentiValutazioneComparto);
        model.addAttribute("struttura", struttura);
        model.addAttribute("anno", anno);
        } 
		        
		return "geko/programmazione/manager/listProgrammazioneCompartoManager";
		   
    } // fine metodo listDipendentiAssegnazioniProgrammazioneGet
    
    
 // lista assegnazione dipendenti agli obiettivi della struttura 
    @RequestMapping(value="modifyCompartoProgrammazione/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String modifyDipendentiAssegnazioniProgrammazioneGet(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico,Model model) {
    	// lista valutazione dipendenti della struttura e sottostruttura
          
        	//
        	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
        	final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
        	final PersonaGiuridicaGeko struttura = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);
            if (null != manager) {
            	List<PersonaFisicaGeko> listDipendenti = fromOrganikoServizi.findDipendentiStrictByStrutturaIDAndAnno(incarico.pgID,anno);
            	//struttura.getDipendenti();
            	listDipendenti.remove(manager);
            	//
            	Map<PersonaFisicaGeko,List<AzioneAssegnazione>> mapDipendentiAssegnazioni = new LinkedHashMap<PersonaFisicaGeko,List<AzioneAssegnazione>>();
            	Map<PersonaFisicaGeko,List<ValutazioneComparto>> mapDipendentiValutazioneComparto = new LinkedHashMap<PersonaFisicaGeko,List<ValutazioneComparto>>();
            	if (listDipendenti!= null && !listDipendenti.isEmpty()){
		            //
		            for(PersonaFisicaGeko pf : listDipendenti){
		            	//pf.setAnno(anno);
		            	pf.setIncaricoValutazioneID(idIncarico);
		            	//
		            	List<AzioneAssegnazione> assegnazioni = azAssServizi.findByPfIDAndIncaricoIDAndAnno(pf.idPersona, idIncarico, anno);
		            	if (!assegnazioni.isEmpty()) {
		            		pf.setAssegnazioni(assegnazioni);
		            		mapDipendentiAssegnazioni.put(pf, assegnazioni);
		            	}
		            	//
		            	List<ValutazioneComparto> valutazioni = valutazioneCompartoServizi.findByPfIDAndIncaricoIDAndAnno(pf.idPersona, incarico.idIncarico, anno);
			        	if (valutazioni!=null && !valutazioni.isEmpty()) {
			        		pf.setValutazioni(valutazioni);
			            	mapDipendentiValutazioneComparto.put(pf, valutazioni);
			        	}
			        	else {
			        		List<ValutazioneComparto> emptyList = new ArrayList<ValutazioneComparto>();
			        		pf.setValutazioni(emptyList);
			            	mapDipendentiValutazioneComparto.put(pf, emptyList);
			        	}
		            	//System.out.println("listDipendentiAssegnazioniProgrammazioneGet() pf= "+pf.stringa);
		            }
            	}
            //
            model.addAttribute("mapDipendentiAssegnazioni", mapDipendentiAssegnazioni);  
            model.addAttribute("mapDipendentiValutazioneComparto", mapDipendentiValutazioneComparto);
            model.addAttribute("struttura", struttura);
            model.addAttribute("anno", anno);
            //
            } 
            return "geko/programmazione/manager/modifyProgrammazioneCompartoManager";
        }
    	
    
       
    // ------- DDD ---------------
    // 1. Programmazione
 // 1.1 lista obiettivi e azioni e dipendenti per consentirne la modifica
    @RequestMapping(value="modifyProgrammazioneIncarico/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String modifyProgrammazioneIncaricoGet(@PathVariable("anno") int anno,
    		@PathVariable("idIncarico") int idIncarico,
    		Model model) {
    	if (idIncarico == menu.getIdIncaricoScelta()){
        	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
	       if (null != incarico) {
	    	   List<Obiettivo> listObiettivi = objQryServizi.findObiettiviDirigenzialiConPersoneByIncaricoIDAndAnno(idIncarico, anno);
	    	   for (Obiettivo obj : listObiettivi){
	    		   obj.setIncarico(incarico);
	    		   List<Command> cmds = this.listAvailableObjCommands(obj);
		    		obj.setGuiCommands(cmds);
		    		for(Azione act: obj.getAzioni()){
		    			List<Command> actcmds = this.listAvailableActCommands(act);
			    		act.setGuiCommands(actcmds);
		    		}
	    	   }
	    	   
		    	model.addAttribute("struttura", incarico.denominazioneStruttura);
		    	model.addAttribute("responsabile", incarico.responsabile);
		        model.addAttribute("anno", anno);
		        model.addAttribute("listObiettivi", listObiettivi);
		        model.addAttribute("idIncarico", idIncarico);
		        return "geko/programmazione/manager/modifyProgrammazioneIncaricoManager";
		    }
	       else return "geko/programmazione/manager/errNoResponsabileStruttura";
	    }
    	else {
    		return "geko/programmazione/manager/pageNAManager";
    	}
        
        
    } // fine metodo modifyProgrammazioneIncaricoGet
    
    // 1. Programmazione
    // 1.1 lista obiettivi e azioni e dipendenti per consentirne la modifica
       @RequestMapping(value="modifyObjCompartoProgrammazione/{anno}/{idIncarico}", method = RequestMethod.GET)
       public String modifyObjCompartoGet(@PathVariable("anno") int anno,
       		@PathVariable("idIncarico") int idIncarico,
       		Model model) {
       	if (idIncarico == menu.getIdIncaricoScelta()){
           	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
   	       if (null != incarico) {
   	    	   List<Obiettivo> listObiettivi = objQryServizi.findObiettiviCompartoConPersoneByIncaricoIDAndAnno(idIncarico, anno);
   	    	   for (Obiettivo obj : listObiettivi){
   	    		   List<Command> cmds = this.listAvailableObjCommands(obj);
   		    		obj.setGuiCommands(cmds);
   		    		for(Azione act: obj.getAzioni()){
   		    			List<Command> actcmds = this.listAvailableActCommands(act);
   			    		act.setGuiCommands(actcmds);
   		    		}
   	    	   }
   	    	   
   		    	model.addAttribute("struttura", incarico.denominazioneStruttura);
   		    	model.addAttribute("responsabile", incarico.responsabile);
   		        model.addAttribute("anno", anno);
   		        model.addAttribute("listObiettivi", listObiettivi);
   		        model.addAttribute("idIncarico", idIncarico);
   		        return "geko/programmazione/manager/modifyObjCompartoManager";
   		    }
   	       else return "geko/programmazione/manager/errNoResponsabileStruttura";
   	    }
       	else {
       		return "geko/programmazione/manager/pageNAManager";
       	}
       } // fine metodo modifyProgrammazioneIncaricoGet
       
    // 1.1 lista obiettivi e azioni e dipendenti per consentirne la modifica
    @RequestMapping(value="listProgrammazionePopIncarico/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listProgrammazionePopIncaricoGet(@PathVariable("anno") int anno,
       		@PathVariable("idIncarico") int idIncarico,	
                Model model) {
    	//
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
        //log.info("incarico dirigente con Id:" + incarico.getOrder());
        final List<IncaricoGeko> lstIncPop = fromOrganikoServizi.findIncarichiPopByIntermediaIDAndAnno(incarico.pgID,anno);
        //
       
        if(!lstIncPop.equals(null) && !lstIncPop.isEmpty()) {
            for (IncaricoGeko incPop : lstIncPop) {
                //log.info("incarico POP con Id:" + incPop.getOrder());                 
                //
                final List<Obiettivo> listObiettivi = objQryServizi.findObiettiviPopSenzaPersoneByIncaricoIDAndAnno(incPop.getIdIncarico(), anno);
                final List<Valutazione> lstValutPop = valutazioneQryServizi.findByIncaricoIDAndAnno(incPop.getIdIncarico(), anno); // una sola in realt�        	
                //	
                if(!listObiettivi.equals(null) && !listObiettivi.isEmpty()) {                           
                    for (Obiettivo obj : listObiettivi){
                        obj.setIncarico(incPop);
                        obj.setIncaricoPadreID(idIncarico); 
                    }			    	
                    incPop.setObiettivi(listObiettivi);
                }
                //	
                if (lstValutPop!=null && !lstValutPop.isEmpty()) {
                    incPop.setValutazioni(lstValutPop);
                }                 
            } // end for 
        }// end if lst empty   

        
        model.addAttribute("struttura", incarico.denominazioneStruttura);
        model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("incarico", incarico);
        model.addAttribute("listIncarichiPop", lstIncPop);
        model.addAttribute("anno", anno);
        final String pageName = "geko/programmazione/manager/listProgrammazioneIncarichiPopManager";
        model.addAttribute("pageName", pageName); 
            //
        return pageName;  
        
        //return "redirect:/ROLE_MANAGER";
    } // fine metodo modifyProgrammazioneIncaricoGet
    
    // 1.1 lista obiettivi e azioni e dipendenti per consentirne la modifica
    @RequestMapping(value="modifyProgrammazionePopIncarico/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String modifyProgrammazionePopIncaricoGet(@PathVariable("anno") int anno,
       		@PathVariable("idIncarico") int idIncarico,	
                Model model) {
    	//
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
        log.info("incarico dirigente con Id:" + incarico.getOrder());
        final List<IncaricoGeko> lstIncPop = fromOrganikoServizi.findIncarichiPopByIntermediaIDAndAnno(incarico.pgID,anno);
        //
       
        if(!lstIncPop.equals(null) && !lstIncPop.isEmpty()) {
            for (IncaricoGeko incPop : lstIncPop) {
                log.info("incarico POP con Id:" + incPop.getOrder());                 
                //
                final List<Obiettivo> listObiettivi = objQryServizi.findObiettiviPopSenzaPersoneByIncaricoIDAndAnno(incPop.getIdIncarico(), anno);
                final List<Valutazione> lstValutPop = valutazioneQryServizi.findByIncaricoIDAndAnno(incPop.getIdIncarico(), anno); // una sola in realt�        	
                //	
                if(!listObiettivi.equals(null) && !listObiettivi.isEmpty()) {                           
                    for (Obiettivo obj : listObiettivi){
                        obj.setIncarico(incPop);
                        obj.setIncaricoPadreID(idIncarico);
                        List<Command> cmds = this.listAvailableObjCommands(obj);
                        obj.setGuiCommands(cmds);
                        for(Azione act: obj.getAzioni()){
                            List<Command> actcmds = this.listAvailableActCommands(act);
                            act.setGuiCommands(actcmds);
                        }
                    }			    	
                    incPop.setObiettivi(listObiettivi);
                }
                //	
                if (lstValutPop!=null && !lstValutPop.isEmpty()) {
                    incPop.setValutazioni(lstValutPop);
                }                 
            } // end for 
        }// end if lst empty   

        
        model.addAttribute("struttura", incarico.denominazioneStruttura);
        model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("incarico", incarico);
        model.addAttribute("listIncarichiPop", lstIncPop);
        model.addAttribute("anno", anno);
        final String pageName = "geko/programmazione/manager/modifyProgrammazioneIncarichiPopManager";
        model.addAttribute("pageName", pageName); 
            //
        return pageName;  
        
        //return "redirect:/ROLE_MANAGER";
    } // fine metodo modifyProgrammazioneIncaricoGet
    
    
    
    // 
    @RequestMapping(value="associaCompartoProgrammazioneDirigenzialeIncarico/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String associaCompartoProgrammazioneDirigenzialeIncaricoGet(@PathVariable("anno") int anno,
    		@PathVariable("idIncarico") int idIncarico,
    		Model model) {
    	if (idIncarico == menu.getIdIncaricoScelta()){
        	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
	       if (null != incarico) {
	    	   List<Obiettivo> listObiettivi = objQryServizi.findObiettiviDirigenzialiConPersoneByIncaricoIDAndAnno(idIncarico, anno); //.findObiettiviTotaliConPersoneByIncaricoIDAndAnno(idIncarico,anno);
	    	   for (Obiettivo obj : listObiettivi){
	    		   List<Command> cmds = this.listAvailableObjCommands(obj);
		    		obj.setGuiCommands(cmds);
		    		for(Azione act: obj.getAzioni()){
		    			List<Command> actcmds = this.listAvailableActCommands(act);
			    		act.setGuiCommands(actcmds);
		    		}
	    	   }
	    	   
		    	model.addAttribute("struttura", incarico.denominazioneStruttura);
		    	model.addAttribute("responsabile", incarico.responsabile);
		        model.addAttribute("anno", anno);
		        model.addAttribute("listObiettivi", listObiettivi);
		        model.addAttribute("idIncarico", idIncarico);
		        return "geko/programmazione/manager/associaCompartoProgrammazioneIncaricoManager";
		    }
	       else return "geko/programmazione/manager/errNoResponsabileStruttura";
	    }
    	else {
    		return "geko/programmazione/manager/pageNAManager";
    	}
    } // fine metodo modifyProgrammazioneIncaricoGet
    
    
    // 1.2 lista obiettivi e azioni e dipendenti in base al dirigente con assegnazioni
    @RequestMapping(value="listProgrammazioneIncarico/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String ProgrammazioneIncaricoGet(@PathVariable("anno") int anno, 
    		@PathVariable("idIncarico") int idIncarico, Model model) {
    	//
    	if (idIncarico == menu.getIdIncaricoScelta()){
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	//
	    if (null != incarico) {
	    	//
	    	final List<Obiettivo> listObiettivi = objQryServizi.findObiettiviTotaliConPersoneByIncaricoIDAndAnno(idIncarico,anno);
	    	//
	    	model.addAttribute("struttura", incarico.getDenominazioneStruttura());
	    	model.addAttribute("competenze", incarico.competenzeStruttura);
	    	model.addAttribute("responsabile", incarico.responsabile);
	        model.addAttribute("anno", anno);
	        model.addAttribute("listObiettivi", listObiettivi);
	        model.addAttribute("idIncarico", idIncarico);
	        return "geko/programmazione/manager/listPianificazioneIncaricoManager";
	    }
	    else return "geko/programmazione/manager/errNoResponsabileStruttura";
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
    
 // lista pianificazione obiettivi e azioni del dipartimento
    @RequestMapping(value="listPianificazioneDipartimentoManager/{anno}", method = RequestMethod.GET)
    public String listPianificazioneDipartimentoManager(@PathVariable("anno") int anno, Model model) {
    	Integer dipartimentoID = menu.getDeptID();
    	Map<IncaricoGeko,List<Obiettivo>> mapObiettiviDept = objQryServizi.mapObiettiviDipartimentoIDAndAnno(dipartimentoID, anno);
        //final OpPersonaGiuridica dipartimento = userServizi.findDipartimentoOfLoggedUser();
        //dipartimento.setAnno(anno);
        //if (null!=dipartimento){
        	model.addAttribute("dipartimento", menu.getDipartimento());
        	//Map<Incarico,List<Obiettivo>> mapObiettiviDept = objServizi.mapObiettiviDipartimentoAndAnno(dipartimento, anno);
        	model.addAttribute("mapObiettiviDept", mapObiettiviDept); 
            model.addAttribute("anno", anno);
        //}
        return "geko/programmazione/manager/listPianificazioneDipartimentoManager"; // il controller lo fa con List invece che map vedi pianific compatta
    }
    
 // 1.3.1 lista pianificazione obiettivi e azioni del dipartimento
    @RequestMapping(value="associaPianificazioneStrategicaIncaricoManager/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String associaPianificazioneStrategicaIncaricoManagerGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettiviPrioritari = objQryServizi.findObiettiviPrioritariByIncaricoIDAndAnno(idIncarico, anno);
    	//
    	model.addAttribute("idIncarico", idIncarico);
    	model.addAttribute("struttura", incarico.denominazioneStruttura);
    	model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("anno", anno);
        model.addAttribute("listObiettivi", listObiettiviPrioritari);
        return "geko/programmazione/manager/associaPianificazioneStrategicaIncaricoManager";  
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
    // -------------------------------------------------------------
    @RequestMapping(value="sintesiPianificazioneDipartimentoManager/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String sintesiPianificazioneDipartimentoController(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico,Model model) {
    	List<IncaricoGeko> listIncarichi = new ArrayList<IncaricoGeko>();
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettiviDiretti = objQryServizi.findObiettiviApicaliDirettiByIncaricoIDAndAnno(idIncarico, anno);
    	incarico.setObiettivi(listObiettiviDiretti);
    	listIncarichi.add(incarico);
        //
        List<IncaricoGeko> listIncarichiDept = fromOrganikoServizi.findIncarichiByDipartimentoIDAndAnno(incarico.pgID, anno);
        for(IncaricoGeko inc : listIncarichiDept){
        	List<Obiettivo> lstObjs = objQryServizi.findObiettiviDirigenzialiSenzaPersoneByIncaricoIDAndAnno(inc.idIncarico, anno);
        	inc.setObiettivi(lstObjs);
        }
        listIncarichi.addAll(listIncarichiDept);
        model.addAttribute("dipartimento", incarico.denominazioneStruttura);
        model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("incarico", incarico);
        model.addAttribute("listIncarichiDept", listIncarichi);
        model.addAttribute("anno", anno);
        model.addAttribute("numero", listIncarichi.size());
        model.addAttribute("pgID", incarico.pgID);
        return "geko/programmazione/manager/sintesiPianificazioneAnnoManager";
    }
    
    // --------------------------------------------------------------
    @RequestMapping(value="listPianificazioneAltroIncaricoManager/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listPianificazioneAltroIncaricoManagerControllerGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	if (null != incarico) {
	    	//final List<Obiettivo> listObiettivi = incarico.getObiettiviTotAnno();
	    	final List<Obiettivo> listObiettivi = objQryServizi.findObiettiviTotaliConPersoneByIncaricoIDAndAnno(idIncarico,anno);
	    	model.addAttribute("struttura", incarico.getDenominazioneStruttura());
	    	model.addAttribute("competenze", incarico.competenzeStruttura);
	    	model.addAttribute("responsabile", incarico.responsabile);
	        model.addAttribute("anno", anno);
	        model.addAttribute("listObiettivi", listObiettivi);
            return "geko/programmazione/manager/listPianificazioneAltroIncaricoManager";
        }
        else return "redirect:/ROLE_MANAGER";
    }
    
    
   
   
} // ---------------------------------------------
