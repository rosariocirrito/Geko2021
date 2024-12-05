/**
 * 
 */
package it.sicilia.regione.gekoddd.geko.rendicontazione.web.manager;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.AzioneCmdService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.application.ControllerRendicontazioneService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.criticita.Criticita;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.criticita.CriticitaCmdService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.criticita.CriticitaValidator;

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
@SessionAttributes(types = Criticita.class)
@RequestMapping("/dirigenteCritic")
public class ManagerCriticitaController  {
    
    
	@Autowired
    private CriticitaCmdService critCmdServizi;
	@Autowired
    private AzioneCmdService actCmdServizi;
    @Autowired
    private ControllerRendicontazioneService controllerServizi;
    
    
    
    public ManagerCriticitaController() { }
    
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
    
    // ------------------------- Criticit� su azione--------------------------------------------
    @RequestMapping(value ="{id}/edit" , method = RequestMethod.GET )
    public String updateForm(@PathVariable("id") int id, Model model) {
        Criticita criticita = critCmdServizi.findById(id);
        //
        model.addAttribute("criticita", criticita);
        return "geko/rendicontazione/manager/formCriticitaEditManager";
    }
    //
    @RequestMapping(value ="{id}/edit" , params = "update", method =  RequestMethod.PUT )
    public String processUpdateSubmit(@ModelAttribute Criticita criticita, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new CriticitaValidator().validate(criticita, result);
		if (result.hasErrors()) {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
	            return "geko/rendicontazione/manager/formCriticitaEditManager";
	            
		}
		else {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
	            this.controllerServizi.controllerUpdateCriticita(criticita);
	            status.setComplete();
	            return "redirect:/dirigente/modifyRendicontazioneIncaricoManager/"+
	            criticita.getAzione().getObiettivo().getAnno()+"/"+criticita.getIncaricoID(); 
		}
    }
    
    @RequestMapping(value ="{id}/edit" , params = "elimina", method =  RequestMethod.PUT )
    public String processDeleteSubmit(@ModelAttribute Criticita criticita, 
                                BindingResult result, 
                                SessionStatus status) {
    	//System.out.println("ControllerCriticitaController.processDeleteSubmit() da cancellare critic con id"+criticita.getId());
    	
        this.controllerServizi.controllerDeleteCriticita(criticita);
        
        return "redirect:/ROLE_MANAGER";
    }
    
    @RequestMapping(value ="{id}/edit" , params = "cancel", method =  RequestMethod.PUT )
    public String processCancelSubmit(@ModelAttribute Criticita criticita, 
                                BindingResult result, 
                                SessionStatus status) {
        
        
            return "redirect:/ROLE_MANAGER";
	
    }

    
    // crea criticita su azione
    @RequestMapping(value="new/{idAzione}", method = RequestMethod.GET)
    public String createCriticitaController(@PathVariable("idAzione") int idAzione,Model model) {
        //final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        //OpPersonaFisica personaFisica = null;
        //if (null!=userName) personaFisica = userservizi.getPersonaFisicaByUserName(userName);
        //if (null!=personaFisica){  
            Azione azione = actCmdServizi.findById(idAzione);
            Criticita criticita = new Criticita();
            criticita.setIndicazioni("");
            criticita.setAzione(azione);
            azione.addCriticitaToAzione(criticita);
            model.addAttribute("criticita", criticita);
  
        //}
        return "geko/rendicontazione/manager/formCriticitaCreateManager";
        
    }
    
    @RequestMapping(value="new/{idAzione}", method = RequestMethod.POST)
    public String processSubmitCriticitaController(@ModelAttribute Criticita criticita, 
                                BindingResult result, 
                                SessionStatus status) {
        //
	new CriticitaValidator().validate(criticita, result);
	//
        if (result.hasErrors()) {
            return "geko/rendicontazione/manager/formCriticitaCreateManager";
        }	
	else {
            this.controllerServizi.controllerCreateCriticita(criticita);
            status.setComplete();
            return "redirect:/dirigente/modifyRendicontazioneIncaricoManager/"+
            criticita.getAzione().getObiettivo().getAnno()+"/"+criticita.getIncaricoID(); 
	}
    }
    
    
    
} // ---------