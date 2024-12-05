package it.sicilia.regione.gekoddd.geko.valutazione.web.controller;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.application.ControllerProgrammazioneService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazioneCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazioneValidator;
import it.sicilia.regione.gekoddd.geko.programmazione.web.controller.ControllerCompOrgController;
import it.sicilia.regione.gekoddd.geko.valutazione.application.ControllerValutazioneService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneComparto;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneCompartoValidator;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneComparto.ValutazioneEnum;
import it.sicilia.regione.gekoddd.geko.valutazione.infrastructure.ValutazioneCompartoCmdService;
import it.sicilia.regione.gekoddd.session.domain.Menu;

/**
 * @author Cirrito
 *
 */

@Controller
@SessionAttributes(types = AzioneAssegnazione.class)
@RequestMapping("/controllerValutazioneAzioneComparto")
public class ControllerValutazioneAzioniCompartoController  {
    
	private static final Logger log = LoggerFactory.getLogger(ControllerValutazioneAzioniCompartoController.class);
    
	@Autowired
    private AzioneAssegnazioneCmdService actAssCmdServizi;
    @Autowired
    private ControllerProgrammazioneService controllerProgrServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    
    @Autowired
    private Menu menu;
    
    public ControllerValutazioneAzioniCompartoController() { }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }
     
    // ***************************************************************************************
    @RequestMapping(value ="{id}/editValutazione" , method = RequestMethod.GET )
    public String updateValutazioneForm(@PathVariable("id") int id, Model model) {
        AzioneAssegnazione azioneAssegnazione = actAssCmdServizi.findById(id); 
        //
		model.addAttribute("azioneAssegnazione", azioneAssegnazione);
	    return "geko/valutazione/controller/formAzioneAssegnazioneEditValutazioneController";
	        
	    }

    @RequestMapping(value ="{id}/editValutazione" , params = "update", method =  RequestMethod.PUT )
    public String processUpdateValutazioneSubmit(@ModelAttribute AzioneAssegnazione azioneAssegnazione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ControllerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new AzioneAssegnazioneValidator().validate(azioneAssegnazione, result);
		if (result.hasErrors()) {
            //System.out.println("ControllerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
			log.info("AzioneAssegnazioneValidator() errors");
			return "geko/valutazione/controller/formAzioneAssegnazioneEditValutazioneController";
		}
		else {
            //System.out.println("ControllerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
			log.info("AzioneAssegnazioneValidator() ok");
			this.controllerProgrServizi.cmdControllerUpdateAzioneAssegnazione(azioneAssegnazione);
            status.setComplete();
            int incId = azioneAssegnazione.getAzione().getObiettivo().getIncaricoID();
            IncaricoGeko inc = fromOrganikoServizi.findIncaricoById(incId);
            if(inc.incaricoDipartimentale) {
            	return "redirect:/controllerValut/modifyDipendentiAssegnazioniValutazione/"
                        + azioneAssegnazione.getAzione().getObiettivo().getAnno()
                        +"/"+incId; 
            }
            else {
            	return "redirect:/controllerValut/modifyValutazioneStrutturaManager/"
                        + azioneAssegnazione.getAzione().getObiettivo().getAnno()
                        +"/"+incId; 
            }
                      
		}
    }
    
    @RequestMapping(value ="{id}/editValutazione" , params = "cancel", method =  RequestMethod.PUT )
    public String processCancelValutazioneSubmit() {
        return "redirect:/ROLE_CONTROLLER";
    }
    
    
} // ---------