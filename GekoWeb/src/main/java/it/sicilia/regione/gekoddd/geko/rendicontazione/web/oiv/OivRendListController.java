package it.sicilia.regione.gekoddd.geko.rendicontazione.web.oiv;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategica;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategicaQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.web.controller.ControllerProgrListController;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Cirrito
 *
 */

@Controller
@RequestMapping("/oivRend")
public class OivRendListController  {
	
	private Log log = LogFactory.getLog(OivRendListController.class);
	
	//
    @Autowired
    private ObiettivoQryService objServizi;
    
    @Autowired
    private AreaStrategicaQryService areaStrategicaQryServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    //
    public OivRendListController() { }
    
    //
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
 // lista obiettivi e azioni apicali
    @RequestMapping(value="listRendicontazioneIncaricoApicaleOiv/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listRendicontazioneIncaricoApicaleSepicosGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	log.info("listRendicontazioneIncaricoApicaleOiv");
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettiviApicali = objServizi.findObiettiviApicaliDirettiAndSubordinatiByIncaricoIDAndAnno(idIncarico, anno);
    	//
    	model.addAttribute("dipartimento", incarico.denominazioneStruttura);
    	model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("anno", anno);
        model.addAttribute("listObiettivi", listObiettiviApicali);
        
        if (null != incarico) {
        	
            return "geko/rendicontazione/oiv/listRendicontazioneIncaricoApicaleOiv";
        }
        else return "redirect:/ROLE_OIV";
    }
    
    // ----------------------------------------------------
    //  
    @RequestMapping(value="listIndicazioniIncaricoApicaleOiv/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listIndicazioniIncaricoController(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	//
        if (null != incarico) {
        	final List<Obiettivo> listObiettiviApicali = objServizi.findObiettiviApicaliDirettiAndSubordinatiByIncaricoIDAndAnno(idIncarico, anno);
        	model.addAttribute("struttura", incarico.getDenominazioneStruttura());
        	model.addAttribute("responsabile", incarico.responsabile);
            model.addAttribute("anno", anno);
            model.addAttribute("listObiettivi", listObiettiviApicali);
            model.addAttribute("incarico", incarico);
        //
        return "geko/rendicontazione/oiv/listIndicazioniIncaricoApicaleOiv";
        }
        else return "redirect:/ROLE_OIV";
    }
    
    
    // modifica rendicontazione di altra struttura 
    @RequestMapping(value="modifyIndicazioniIncaricoApicaleOiv/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String modifyIndicazioniIncaricoController(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	if (null != incarico) {
	    	//final List<Obiettivo> listObiettivi = incarico.getObiettiviTotAnno();
	    	final List<Obiettivo> listObiettiviApicali = objServizi.findObiettiviApicaliDirettiAndSubordinatiByIncaricoIDAndAnno(idIncarico, anno);
	    	model.addAttribute("struttura", incarico.getDenominazioneStruttura());
	    	model.addAttribute("competenze", incarico.competenzeStruttura);
	    	model.addAttribute("responsabile", incarico.responsabile);
	        model.addAttribute("anno", anno);
	        model.addAttribute("listObiettivi", listObiettiviApicali);
            model.addAttribute("incarico", incarico);
        //
        return "geko/rendicontazione/oiv/modifyIndicazioniIncaricoApicaleOiv";
        }
        else return "redirect:/ROLE_OIV";
    }
    
 
} // ---------------------------------------------
