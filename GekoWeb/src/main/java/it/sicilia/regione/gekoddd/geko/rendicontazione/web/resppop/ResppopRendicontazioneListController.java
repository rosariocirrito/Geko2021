package it.sicilia.regione.gekoddd.geko.rendicontazione.web.resppop;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategicaQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.application.RespPopProgrammazioneService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazioneQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoQryService;
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
@RequestMapping("/respPop")
public class ResppopRendicontazioneListController  {
	//
    @Autowired
    private ObiettivoQryService objQryServizi;
    @Autowired
    private AzioneAssegnazioneQryService azAssServizi;
    @Autowired
    private ValutazioneCompartoQryService valutazioneCompartoServizi;
    @Autowired
    private AreaStrategicaQryService areaStrategicaQryServizi;
    @Autowired
    private RespPopProgrammazioneService managerServizi;
    @Autowired
    private EventoService evtServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    
    //
    public ResppopRendicontazioneListController() { }
    
    //
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
 
    
  

    //-----------------------Rendicontazione ---------------------------------
    
    
   
    
    
    
    // ------------------------- Rendicontazione -----------------------
    // lista obiettivi e azioni e criticita in base al nome del dirigente
    @RequestMapping(value="listRendicontazionePopIncarico/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listRendicontazioneIncaricoManagerGet(@PathVariable("anno") int anno,
    		@PathVariable("idIncarico") int idIncarico, Model model) {
    	
    	//	    
	    final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
        final PersonaGiuridicaGeko struttura = fromOrganikoServizi.findPersonaGiuridicaById(incarico.getPgID());
        final List<Obiettivo> listObiettivi = objQryServizi.findObiettiviPopSenzaPersoneByIncaricoIDAndAnno(incarico.getIdIncarico(), anno);
 	        if (null != incarico) {
	    	model.addAttribute("struttura", incarico.denominazioneStruttura);
	    	model.addAttribute("responsabile", incarico.responsabile);
	        model.addAttribute("anno", anno);
	        model.addAttribute("listObiettivi", listObiettivi);
	        model.addAttribute("incarico", incarico);
	        return "geko/rendicontazione/resppop/listRendicontazioneIncaricoPop";
	    }
	    else return "geko/programmazione/manager/errNoResponsabileStruttura";    	
    }
    
    //
 // lista obiettivi e azioni e dipendenti per consentirne la modifica
    @RequestMapping(value="modifyRendicontazionePopIncarico/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String modifyRendicontazioneIncaricoManagerGet(@PathVariable("anno") int anno, 
    		@PathVariable("idIncarico") int idIncarico, Model model) {
    	
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
        final PersonaGiuridicaGeko struttura = fromOrganikoServizi.findPersonaGiuridicaById(incarico.getPgID());
        final List<Obiettivo> listObiettivi = objQryServizi.findObiettiviPopSenzaPersoneByIncaricoIDAndAnno(incarico.getIdIncarico(), anno);
 	    
    	if (null != incarico) {
	    	model.addAttribute("struttura", incarico.denominazioneStruttura);
	    	model.addAttribute("responsabile", incarico.responsabile);
	        model.addAttribute("anno", anno);
	        model.addAttribute("listObiettivi", listObiettivi);
	        model.addAttribute("idIncarico", idIncarico);
	        model.addAttribute("incarico", incarico);
	        return "geko/rendicontazione/resppop/modifyRendicontazioneIncaricoPop";
	    }
	    else return "geko/programmazione/manager/errNoResponsabileStruttura"; 
    }
    	
    
   
} // ---------------------------------------------
