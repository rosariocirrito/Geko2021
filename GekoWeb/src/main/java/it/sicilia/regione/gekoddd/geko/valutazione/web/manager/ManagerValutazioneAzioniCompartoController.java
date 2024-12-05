package it.sicilia.regione.gekoddd.geko.valutazione.web.manager;


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
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazioneCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazioneValidator;
import it.sicilia.regione.gekoddd.geko.programmazione.web.manager.ManagerCompOrgController;
import it.sicilia.regione.gekoddd.geko.valutazione.application.ManagerValutazioneService;
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
@RequestMapping("/dirigenteValutazioneAzioneComparto")
public class ManagerValutazioneAzioniCompartoController  {
    
	private static final Logger log = LoggerFactory.getLogger(ManagerValutazioneAzioniCompartoController.class);
    
	@Autowired
    private ValutazioneCompartoCmdService valutazioneCompartoServizi;
	@Autowired
    private AzioneAssegnazioneCmdService actAssCmdServizi;
    @Autowired
    private ManagerValutazioneService managerServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    public ManagerValutazioneAzioniCompartoController() { }
    
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
	    return "geko/valutazione/manager/formAzioneAssegnazioneEditValutazioneManager";
	        
	    }

    @RequestMapping(value ="{id}/editValutazione" , params = "update", method =  RequestMethod.PUT )
    public String processUpdateValutazioneSubmit(@ModelAttribute AzioneAssegnazione azioneAssegnazione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new AzioneAssegnazioneValidator().validate(azioneAssegnazione, result);
		if (result.hasErrors()) {
            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
            return "geko/valutazione/manager/formAzioneAssegnazioneEditValutazioneManager";
		}
		else {
            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
            
			this.managerServizi.managerUpdateAzioneAssegnazioneValutazione(azioneAssegnazione);
            status.setComplete();
            return "redirect:/dirigenteVal/modifyDipendentiAssegnazioniValutazione/"
            + azioneAssegnazione.getAzione().getObiettivo().getAnno()
            +"/"+menu.getIncarico().getIdIncarico();           
		}
    }
    
    @RequestMapping(value ="{id}/editValutazione" , params = "cancel", method =  RequestMethod.PUT )
    public String processCancelValutazioneSubmit() {
        return "redirect:/ROLE_MANAGER";
    }
    
    
} // ---------