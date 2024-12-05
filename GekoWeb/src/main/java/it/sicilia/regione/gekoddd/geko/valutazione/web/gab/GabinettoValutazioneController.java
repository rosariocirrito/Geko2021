
package it.sicilia.regione.gekoddd.geko.valutazione.web.gab;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.valutazione.application.GabinettoValutazioneService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.ValutazioneCmdService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.ValutazioneValidator;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione.ValutazioneDirigEnum;
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
@SessionAttributes(types = Valutazione.class)
@RequestMapping("/gabinettoValutazione")
public class GabinettoValutazioneController  {
    
    
	@Autowired
    private ValutazioneCmdService valutazioneCmdServizi;
    @Autowired
    private GabinettoValutazioneService gabinettoServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    
    public GabinettoValutazioneController() { }
    
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
    
    // display a single information (cfr. Pro Spring 3 chp 17 p.621)
   /* @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") int id, Model model) {
    	Valutazione valutazione = valutazioneCmdServizi.findById(id);
        model.addAttribute(valutazione);
        return "redirect:/controllerIncarico/" +valutazione.getIncarico().getIdIncarico();
    }*/
    /*
    @RequestMapping(value ="{id}/edit" , method = RequestMethod.GET )
    public String updateForm(@PathVariable("id") int id, Model model) {
    	Valutazione valutazione = valutazioneCmdServizi.findById(id);
        //
        model.addAttribute("valutazione", valutazione);
        return "gabinetto/formValutazioneController";
        
    }
    
    
    @RequestMapping(value ="{id}/edit" , params = "update", method =  RequestMethod.PUT )
    public String processUpdateSubmit(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new ValutazioneValidator().validate(valutazione, result);
		if (result.hasErrors()) {
            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
            return "controller/formValutazioneController";
		}
		else {
            System.out.println("ControllerValutazioneController.processUpdateSubmit() no errors =");
            this.gabinettoServizi.gabinettoUpdateValutazioneCompOrg(valutazione);
            status.setComplete();
            return "redirect:/controllerIncarico/" + valutazione.getIncarico().getIdIncarico();
            
		}
    }
    
    
    
    @RequestMapping(value ="{id}/edit" , params = "cancel", method =  RequestMethod.POST )
    public String processCancelSubmit(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        
        
            return "redirect:/ROLE_GABINETTO";
	
    }

    */
    
    // crea Comportamento Organizzativo su incarico
    @RequestMapping(value="new/{idIncarico}/{anno}", method = RequestMethod.GET)
    public String createComportOrganController(
    		@PathVariable("idIncarico") int idIncarico, @PathVariable("anno") int anno, Model model) {
        //final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        //OpPersonaFisica personaFisica = null;
        //if (null!=userName) personaFisica = userservizi.getPersonaFisicaByUserName(userName);
        //if (null!=personaFisica){  
    	//Menu menu = Menu.getInstance();
        //Incarico incarico = incaricoServizi.findById(idIncarico);
        Valutazione valutazione = new Valutazione();
        valutazione.setIncaricoID(idIncarico);
        IncaricoGeko inc = fromOrganikoServizi.findIncaricoById(idIncarico);
        valutazione.setIncarico(inc);
        //valutazione.setAnno(menu.getAnno());
        valutazione.setAnno(anno);
        valutazione.setPdlAss(20);
        valutazione.setPdlVal(ValutazioneDirigEnum.DA_VALUTARE);
        valutazione.setAnalProgrAss(8);
        valutazione.setAnalProgrVal(ValutazioneDirigEnum.DA_VALUTARE);
        valutazione.setRelazCoordAss(7);
        valutazione.setRelazCoordVal(ValutazioneDirigEnum.DA_VALUTARE);
        valutazione.setGestRealAss(5);
        valutazione.setGestRealVal(ValutazioneDirigEnum.DA_VALUTARE);
        model.addAttribute("valutazione", valutazione);
  
        //}
        return "geko/valutazione/gabinetto/formValutazioneCreateGabinetto";
        
    }
    
    @RequestMapping(value="new/{idIncarico}/{anno}", method = RequestMethod.POST)
    public String processSubmitComportOrganController(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        //
		new ValutazioneValidator().validate(valutazione, result, menu.getAnno());
		//
	        if (result.hasErrors()) {
	        	return "geko/valutazione/gabinetto/formValutazioneCreateGabinetto";
	        }	
		else {
	            this.gabinettoServizi.gabinettoCreateValutazioneCompOrg(valutazione);
	            status.setComplete();
	            return "redirect:/gabValut/modifyValutazioneApicaleGabinetto/"+valutazione.getAnno()+"/"+valutazione.getIncaricoID();
		}
    }
    
    
    
 
    
    // ------------------------- Pesi Comportamenti Organizzativi -------------------------------------------------
    @RequestMapping(value ="{id}/editPesiComportOrgan" , method = RequestMethod.GET )
    public String updateFormPesiComportOrgan(@PathVariable("id") int id, Model model) {
    	Valutazione valutazione = valutazioneCmdServizi.findById(id);
    	IncaricoGeko inc = fromOrganikoServizi.findIncaricoById(valutazione.getIncaricoID());
        valutazione.setIncarico(inc);
        //
        model.addAttribute("valutazione", valutazione);
        return "geko/valutazione/gabinetto/formPesiComportOrganEditGabinetto";
        
    }
    @RequestMapping(value ="{id}/editPesiComportOrgan" , params = "update", method =  RequestMethod.PUT )
    public String processUpdateSubmitPesiComportOrgan(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new ValutazioneValidator().validate(valutazione, result, menu.getAnno());
		if (result.hasErrors()) {
            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
			return "geko/valutazione/gabinetto/formPesiComportOrganEditGabinetto";
		}
		else {
            //System.out.println("ControllerValutazioneController.processUpdateSubmit() no errors =");
            this.gabinettoServizi.gabinettoUpdateValutazioneCompOrg(valutazione);
            status.setComplete();
            return "redirect:/gabValut/modifyValutazioneApicaleGabinetto/"+valutazione.getAnno()
                    +"/"+valutazione.getIncaricoID();
		}
    }
    
    @RequestMapping(value ="{id}/editPesiComportOrgan" , params = "cancel", method =  RequestMethod.PUT )
    public String processCancelSubmiteditPesiComportOrgan(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        
    		return "redirect:/gabValut/modifyValutazioneApicaleGabinetto/"+valutazione.getAnno()
    			+"/"+valutazione.getIncaricoID();
	
    }
    
     
    // ------------------------- Valutazione Comportamenti Organizzativi -------------------------------------------------
    @RequestMapping(value ="{id}/editValutComportOrgan" , method = RequestMethod.GET )
    public String updateFormValutComportOrgan(@PathVariable("id") int id, Model model) {
    	Valutazione valutazione = valutazioneCmdServizi.findById(id);
    	IncaricoGeko inc = fromOrganikoServizi.findIncaricoById(valutazione.getIncaricoID());
        valutazione.setIncarico(inc);
        //
        model.addAttribute("valutazione", valutazione);
        return "geko/valutazione/gabinetto/formValutComportOrganEditGabinetto";
        
    }
    @RequestMapping(value ="{id}/editValutComportOrgan" , params = "update", method =  RequestMethod.PUT )
    public String processUpdateSubmitValutComportOrgan(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new ValutazioneValidator().validate(valutazione, result, menu.getAnno());
		if (result.hasErrors()) {
            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
			return "geko/valutazione/gabinetto/formValutComportOrganEditGabinetto";
		}
		else {
            //System.out.println("ControllerValutazioneController.processUpdateSubmit() no errors =");
            this.gabinettoServizi.gabinettoUpdateValutazioneCompOrg(valutazione);
            status.setComplete();
            /*
            return "redirect:/controller/modifyValutazioneDipartimentaleController/"+valutazione.getAnno();
            //+"/"+valutazione.getIncarico().getIdStruttura();
            */
            return "redirect:/gabValut/modifyValutazioneApicaleGabinetto/"+valutazione.getAnno()
                    +"/"+valutazione.getIncaricoID();
		}
    }
    @RequestMapping(value ="{id}/editValutComportOrgan" , params = "cancel", method =  RequestMethod.PUT )
    public String processCancelSubmitValutComportOrgan(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        
    	return "redirect:/gabValut/modifyValutazioneApicaleGabinetto/"+valutazione.getAnno()
                +"/"+valutazione.getIncaricoID();
	
    }
    
 
    // ------------------ apicali -----------------------
    
   
    
    
    
    // ------------------------- Pesi Comportamenti Organizzativi -------------------------------------------------
    @RequestMapping(value ="{id}/editApicalePesiComportOrgan" , method = RequestMethod.GET )
    public String editApicalePesiComportOrganGet(@PathVariable("id") int id, Model model) {
    	Valutazione valutazione = valutazioneCmdServizi.findById(id);
    	IncaricoGeko inc = fromOrganikoServizi.findIncaricoById(valutazione.getIncaricoID());
        valutazione.setIncarico(inc);
        //
        model.addAttribute("valutazione", valutazione);
        return "gabValut/formPesiComportOrganEditGabinetto";
    }
    
    @RequestMapping(value ="{id}/editApicalePesiComportOrgan" , params = "update", method =  RequestMethod.PUT )
    public String editApicalePesiComportOrganPut(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new ValutazioneValidator().validate(valutazione, result, menu.getAnno());
		if (result.hasErrors()) {
            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
            return "gabValut/formPesiComportOrganEditGabinetto";
		}
		else {
            System.out.println("ControllerValutazioneController.processUpdateSubmit() no errors =");
            this.gabinettoServizi.gabinettoUpdateValutazioneCompOrg(valutazione);
            status.setComplete();
            return "redirect:/gabValut/modifyValutazioneApicaleGabinetto/"+valutazione.getAnno();
        	
		}
    }
    
    @RequestMapping(value ="{id}/editApicalePesiComportOrgan" , params = "cancel", method =  RequestMethod.PUT )
    public String editApicalePesiComportOrganCancel(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        
    	return "redirect:/gabValut/modifyValutazioneApicaleGabinetto/"+valutazione.getAnno();
    	
    }
    
    
} // ---------