/**
 * 
 */
package it.sicilia.regione.gekoddd.geko.valutazione.web.controller;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.criticita.Criticita;
import it.sicilia.regione.gekoddd.geko.valutazione.application.ControllerValutazioneService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.criticValut.CriticValut;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.criticValut.CriticValutCmdService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.criticValut.CriticValutValidator;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.ValutazioneCmdService;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author Cirrito
 *
 */

@Controller
@SessionAttributes(types = CriticValut.class)
@RequestMapping("/controllerCriticValut")
public class ControllerCriticValutController  {
    
    
	@Autowired
    private CriticValutCmdService critValutCmdServizi;
	@Autowired
    private ValutazioneCmdService valutCmdServizi;
    @Autowired
    private ControllerValutazioneService controllerServizi;
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    public ControllerCriticValutController() { }
    
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
    
    // display a single criticita information (cfr. Pro Spring 3 chp 17 p.621)
    /*
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") int id, Model model) {
        Criticita criticita = critServizi.findById(id);
        model.addAttribute(criticita);
	return "controller/azioneControllerShow";
    }
    //
     * 
     */
   
    // -------------------------- Criticit� su valutazione -------------------------------------------
    @RequestMapping(value ="{id}/edit" , method = RequestMethod.GET )
    public String updateCriticForm(@PathVariable("id") int id, Model model) {
        CriticValut criticita = critValutCmdServizi.findById(id);
        //
        model.addAttribute("criticita", criticita);
        return "controller/formCriticValutController";
    }
    //
    @RequestMapping(value ="{id}/edit" , params = "update", method =  RequestMethod.PUT )
    public String processUpdateCriticSubmit(@ModelAttribute CriticValut criticita, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new CriticValutValidator().validate(criticita, result);
		if (result.hasErrors()) {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
	            return "controller/formCriticValutController";
		}
		else {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
	            this.controllerServizi.controllerUpdateCriticValut(criticita);
	            status.setComplete();
	            return "redirect:/controller/modifyIndicazioniAltraStrutturaController/"+criticita.getValutazione().getAnno()+"/"+criticita.getIncaricoID(); 
	        	}
    }
    
    @RequestMapping(value ="{id}/edit" , params = "delete", method =  RequestMethod.PUT )
    public String processDeleteCriticSubmit(@ModelAttribute CriticValut criticita, 
                                BindingResult result, 
                                SessionStatus status) {
        this.controllerServizi.controllerDeleteCriticValut(criticita);
        
        return "redirect:/controller/modifyIndicazioniAltraStrutturaController/"+criticita.getValutazione().getAnno()+"/"+criticita.getIncaricoID(); 
    	 }
    
    @RequestMapping(value ="{idAzione}/edit" , params = "cancel", method =  RequestMethod.PUT )
    public String processCancelCriticSubmit(@ModelAttribute Criticita criticita, 
                                BindingResult result, 
                                SessionStatus status) {
        
        
            return "redirect:/ROLE_CONTROLLER";
	
    }

    
    // crea criticita su valutazione
    @RequestMapping(value="new/{idValutazione}", method = RequestMethod.GET)
    public String createCriticitaValutController(@PathVariable("idValutazione") int idValutazione,Model model) {
        //final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        //OpPersonaFisica personaFisica = null;
        //if (null!=userName) personaFisica = userservizi.getPersonaFisicaByUserName(userName);
        //if (null!=personaFisica){  
            Valutazione valutazione = valutCmdServizi.findById(idValutazione);
            CriticValut criticita = new CriticValut();
            criticita.setValutazione(valutazione);
            valutazione.addCriticitaToValutazione(criticita);
            model.addAttribute("criticita", criticita);
  
        //}
        return "controller/formCriticValutController";
        
    }
    
    @RequestMapping(value="new/{idValutazione}", method = RequestMethod.POST)
    public String processSubmitCriticitaValutController(@ModelAttribute CriticValut criticita, 
                                BindingResult result, 
                                SessionStatus status) {
        //
	new CriticValutValidator().validate(criticita, result);
	//
        if (result.hasErrors()) {
            return "controller/formCriticValutController";
        }	
	else {
            this.controllerServizi.controllerCreateCriticValut(criticita);
            status.setComplete();
            return "redirect:/controller/modifyIndicazioniAltraStrutturaController/"+criticita.getValutazione().getAnno()+"/"+criticita.getIncaricoID(); 
	
	}
    }
    
    
    
} // ---------