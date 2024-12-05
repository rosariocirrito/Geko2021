package it.sicilia.regione.gekoddd.geko.rendicontazione.web.manager;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategica;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategicaQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.application.ManagerProgrammazioneService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione.CompletamentoEnum;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazioneQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoQryService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneComparto;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneCompartoQryService;
import it.sicilia.regione.gekoddd.log.model.evento.Evento;
import it.sicilia.regione.gekoddd.log.model.evento.EventoService;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import java.text.SimpleDateFormat;
import java.util.*;


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
public class ManagerRendicontazioneListController  {
	//
    @Autowired
    private ObiettivoQryService objServizi;
    @Autowired
    private AzioneAssegnazioneQryService azAssServizi;
    @Autowired
    private ValutazioneCompartoQryService valutazioneCompartoServizi;
    @Autowired
    private AreaStrategicaQryService areaStrategicaQryServizi;
    @Autowired
    private ManagerProgrammazioneService managerServizi;
    @Autowired
    private EventoService evtServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    
    //
    public ManagerRendicontazioneListController() { }
    
    //
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
 
    
  

    //-----------------------Rendicontazione ---------------------------------
    
    
    //
 // lista pianificazione obiettivi e azioni del dipartimento
    @RequestMapping(value="listRendicontazioneDipartimentoManager/{anno}", method = RequestMethod.GET)
    public String listRendicontazioneDipartimentoManager(@PathVariable("anno") int anno, Model model) {
        if (null!=menu.getDipartimento()){
        	model.addAttribute("dipartimento", menu.getDipartimento().getDenominazione());
        	Map<IncaricoGeko,List<Obiettivo>> mapObiettiviDept = objServizi.mapObiettiviDipartimentoIDAndAnno(menu.getDeptID(), anno);
            model.addAttribute("mapObiettiviDept", mapObiettiviDept); 
            model.addAttribute("anno", anno);
        }
        return "geko/rendicontazione/manager/listRendicontazioneDipartimentoManager";
    }
    
    
    
    // ------------------------- Rendicontazione -----------------------
    // lista obiettivi e azioni e criticita in base al nome del dirigente
    @RequestMapping(value="listRendicontazioneIncaricoManager/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listRendicontazioneIncaricoManagerGet(@PathVariable("anno") int anno,
    		@PathVariable("idIncarico") int idIncarico, Model model) {
    	
    	//	    
	    final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
	    if (null != incarico) {
	    	final List<Obiettivo> listObiettivi = objServizi.findObiettiviTotaliByIncaricoIDAndAnno(idIncarico, anno);
	    	model.addAttribute("struttura", incarico.denominazioneStruttura);
	    	model.addAttribute("responsabile", incarico.responsabile);
	        model.addAttribute("anno", anno);
	        model.addAttribute("listObiettivi", listObiettivi);
	        model.addAttribute("incarico", incarico);
	        return "geko/rendicontazione/manager/listRendicontazioneIncaricoManager";
	    }
	    else return "geko/programmazione/manager/errNoResponsabileStruttura";    	
    }
    
    //
 // lista obiettivi e azioni e dipendenti per consentirne la modifica
    @RequestMapping(value="modifyRendicontazioneIncaricoManager/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String modifyRendicontazioneIncaricoManagerGet(@PathVariable("anno") int anno, 
    		@PathVariable("idIncarico") int idIncarico, Model model) {
    	
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	if (null != incarico) {
	    	final List<Obiettivo> listObiettivi = objServizi.findObiettiviTotaliByIncaricoIDAndAnno(idIncarico, anno);
	    	model.addAttribute("struttura", incarico.denominazioneStruttura);
	    	model.addAttribute("responsabile", incarico.responsabile);
	        model.addAttribute("anno", anno);
	        model.addAttribute("listObiettivi", listObiettivi);
	        model.addAttribute("idIncarico", idIncarico);
	        model.addAttribute("incarico", incarico);
	        return "geko/rendicontazione/manager/modifyRendicontazioneIncaricoManager";
	    }
	    else return "geko/programmazione/manager/errNoResponsabileStruttura";   
    	
    }
    
    // 1.1 lista obiettivi e azioni e dipendenti per consentirne la modifica
    @RequestMapping(value="listRendicontazionePopIncarico/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listRendicontazionePopIncaricoGet(@PathVariable("anno") int anno,
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
                final List<Obiettivo> listObiettivi = objServizi.findObiettiviPopSenzaPersoneByIncaricoIDAndAnno(incPop.getIdIncarico(), anno);
                 //	
                if(!listObiettivi.equals(null) && !listObiettivi.isEmpty()) {                           
                    for (Obiettivo obj : listObiettivi){
                        obj.setIncarico(incPop);
                        obj.setIncaricoPadreID(idIncarico); 
                    }			    	
                    incPop.setObiettivi(listObiettivi);
                }
                //	
                               
            } // end for 
        }// end if lst empty   

        
        model.addAttribute("struttura", incarico.denominazioneStruttura);
        model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("incarico", incarico);
        model.addAttribute("listIncarichiPop", lstIncPop);
        model.addAttribute("anno", anno);
        final String pageName = "geko/rendicontazione/manager/listRendicontazioneIncarichiPopManager";
        model.addAttribute("pageName", pageName); 
            //
        return pageName;  
        
        //return "redirect:/ROLE_MANAGER";
    } // fine metodo modifyProgrammazioneIncaricoGet
    
    	
    // --------- scadenzario --------------------------
    @RequestMapping(value ="scadenze/{anno}/{idIncarico}" , method = RequestMethod.GET )
    
    public String scadenze(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico,Model model) {
    	
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettivi = objServizi.findObiettiviTotaliByIncaricoIDAndAnno(idIncarico, anno);
    	model.addAttribute("struttura", incarico.denominazioneStruttura);
    	model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("anno", anno);
        model.addAttribute("listObiettivi", listObiettivi);
        //
        List<Azione> lstAzioni = new ArrayList<Azione>();
        for(Obiettivo obj : listObiettivi){
        	
        	lstAzioni.addAll(obj.getAzioni());
        }
        PropertyComparator.sort(lstAzioni, new MutableSortDefinition("scadenza",true,true));
        model.addAttribute("listAzioni",lstAzioni);
    	//
    	
        return "geko/rendicontazione/manager/listScadenzeAzioniManager";
        
	}
    
    
    // --------------------------------------
 // lista rendicontazione obiettivi e azioni del dipartimento
    @RequestMapping(value="listRendicontazioneDipartimentaleManager/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listRendicontazioneDipartimentaleController(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettivi = objServizi.findObiettiviDirettiAndSubordinatiByIncaricoIDAndAnno(idIncarico, anno);
    	model.addAttribute("struttura", incarico.denominazioneStruttura);
    	model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("anno", anno);
        model.addAttribute("listObiettivi", listObiettivi);
        return "geko/rendicontazione/manager/listRendicontazioneDipartimentaleManager";
    }
    // ---------------------------
 // -------------------------- Rendicontazione ----------------------------
    // 2.5.3 lista rendicontazione di incarico
    @RequestMapping(value="listRendicontazioneAltroIncaricoManager/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listRendicontazioneOtherStructureController(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, 
    		Model model) {
    	
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	if (null != incarico) {
	    	//final List<Obiettivo> listObiettivi = incarico.getObiettiviTotAnno();
	    	final List<Obiettivo> listObiettivi = objServizi.findObiettiviTotaliConPersoneByIncaricoIDAndAnno(idIncarico,anno);
	    	model.addAttribute("struttura", incarico.getDenominazioneStruttura());
	    	model.addAttribute("competenze", incarico.competenzeStruttura);
	    	model.addAttribute("responsabile", incarico.responsabile);
	        model.addAttribute("anno", anno);
	        model.addAttribute("listObiettivi", listObiettivi);
            model.addAttribute("incarico", incarico);
        //
        return "geko/rendicontazione/manager/listRendicontazioneAltroIncaricoManager";
        }
        else return "redirect:/ROLE_MANAGER";
    }
} // ---------------------------------------------
