
package it.sicilia.regione.gekoddd.geko.programmazione.web.gab;

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
@RequestMapping("/gabinettoCompOrg")
public class GabinettoCompOrgController  {
    
    
	@Autowired
    private ValutazioneCmdService valutazioneCmdServizi;
    @Autowired
    private GabinettoValutazioneService gabinettoServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    
    public GabinettoCompOrgController() { }
    
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
    	valutazione.setAnno(anno);
        valutazione.setPdlAss(10);
        valutazione.setPdlVal(ValutazioneDirigEnum.DA_VALUTARE);
        valutazione.setAnalProgrAss(10);
        valutazione.setAnalProgrVal(ValutazioneDirigEnum.DA_VALUTARE);
        valutazione.setRelazCoordAss(10);
        valutazione.setRelazCoordVal(ValutazioneDirigEnum.DA_VALUTARE);
        valutazione.setGestRealAss(0);
        valutazione.setGestRealVal(ValutazioneDirigEnum.DA_VALUTARE);
        model.addAttribute("valutazione", valutazione);
  
        //}
        return "geko/valutazione/gabinetto/form2019ValutazioneCreateGabinetto";
    }
    
    @RequestMapping(value="new/{idIncarico}/{anno}", method = RequestMethod.POST)
    public String processSubmitComportOrganController(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        //
		new ValutazioneValidator().validate(valutazione, result, menu.getAnno());
		//
	        if (result.hasErrors()) {
	            return "geko/valutazione/gabinetto/form2019ValutazioneCreateGabinetto";
	        }	
		else {
	            this.gabinettoServizi.gabinettoCreateValutazioneCompOrg(valutazione);
	            status.setComplete();
	            return "redirect:/gabProg/modifyPianificazioneApicaleIncaricoGab/"+valutazione.getAnno()+"/"+valutazione.getIncaricoID();
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
        return "geko/valutazione/gabinetto/form2019PesiComportOrganEditGabinetto";
        
    }
    @RequestMapping(value ="{id}/editPesiComportOrgan" , params = "update", method =  RequestMethod.PUT )
    public String processUpdateSubmitPesiComportOrgan(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new ValutazioneValidator().validate(valutazione, result, menu.getAnno());
		if (result.hasErrors()) {
            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
            return "geko/valutazione/controller/form2019PesiComportOrganEditController";
		}
		else {
            //System.out.println("ControllerValutazioneController.processUpdateSubmit() no errors =");
            this.gabinettoServizi.gabinettoUpdateValutazioneCompOrg(valutazione);
            status.setComplete();
            return "redirect:/gabProg/modifyPianificazioneApicaleIncaricoGab/"+valutazione.getAnno()
                    +"/"+valutazione.getIncaricoID();
		}
    }
    
    @RequestMapping(value ="{id}/editPesiComportOrgan" , params = "cancel", method =  RequestMethod.PUT )
    public String processCancelSubmiteditPesiComportOrgan(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        
    	return "redirect:/gabProg/modifyPianificazioneApicaleIncaricoGab/"+valutazione.getAnno()
    			+"/"+valutazione.getIncaricoID();
	
    }
    
 
    
    
    
   /*
    
 // crea Comportamento Organizzativo su incarico
    @RequestMapping(value="newApicale/{idIncarico}/{anno}", method = RequestMethod.GET)
    public String createComportOrganApicaleController(
    		@PathVariable("idIncarico") int idIncarico, @PathVariable("anno") int anno, Model model) {
        //final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        //OpPersonaFisica personaFisica = null;
        //if (null!=userName) personaFisica = userservizi.getPersonaFisicaByUserName(userName);
        //if (null!=personaFisica){  
    	//Menu menu = Menu.getInstance();
        //Incarico incarico = incaricoServizi.findById(idIncarico);
        Valutazione valutazione = new Valutazione();
        valutazione.setIncaricoID(idIncarico);
        IncaricoGeko inc = fromOrganikoServizi.findIncaricoById(valutazione.getIncaricoID());
        valutazione.setIncarico(inc);
        //valutazione.setAnno(menu.getAnno());
        valutazione.setAnno(anno);
        valutazione.setPdlAss(20);
        valutazione.setPdlVal(PianoLavoroEnum.DA_VALUTARE);
        valutazione.setAnalProgrAss(5);
        valutazione.setAnalProgrVal(ValutazioneDirigEnum.DA_VALUTARE);
        valutazione.setRelazCoordAss(5);
        valutazione.setRelazCoordVal(ValutazioneDirigEnum.DA_VALUTARE);
        valutazione.setGestRealAss(5);
        valutazione.setGestRealVal(ValutazioneDirigEnum.DA_VALUTARE);
        //incarico.addValutazioneToIncarico(valutazione);
        model.addAttribute("valutazione", valutazione);
  
        //}
        return "controller/formValutazioneCreateController";
        
    }
    
    @RequestMapping(value="newApicale/{idIncarico}/{anno}", method = RequestMethod.POST)
    public String processSubmitComportOrganApicaleController(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        //
		new ValutazioneValidator().validate(valutazione, result);
		//
	        if (result.hasErrors()) {
	            return "controller/formValutazioneCreateController";
	        }	
		else {
	            this.controllerServizi.controllerCreateValutazione(valutazione);
	            status.setComplete();
	            return "redirect:/controllerValut/modifyValutazioneApicaleController/"+valutazione.getAnno();
		}
    }
    
    // ------------------ apicali -----------------------
    
    @RequestMapping(value ="{id}/editApicalePesoPianoLavoro" , method = RequestMethod.GET )
    public String editApicalePesoPianoLavoroGet(@PathVariable("id") int id, Model model) {
    	Valutazione valutazione = valutazioneCmdServizi.findById(id);
    	IncaricoGeko inc = fromOrganikoServizi.findIncaricoById(valutazione.getIncaricoID());
        valutazione.setIncarico(inc);
        //
        model.addAttribute("valutazione", valutazione);
        return "controller/formPesoPianoLavoroEditController";
        
    }
    @RequestMapping(value ="{id}/editApicalePesoPianoLavoro" , params = "update", method =  RequestMethod.PUT )
    public String editApicalePesoPianoLavoroPut(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new ValutazioneValidator().validate(valutazione, result);
		if (result.hasErrors()) {
            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
            return "controller/formPesoPianoLavoroEditController";
		}
		else {
            System.out.println("ControllerValutazioneController.processUpdateSubmit() no errors =");
            this.controllerServizi.controllerUpdateValutazione(valutazione);
            status.setComplete();
            return "redirect:/controllerValut/modifyValutazioneApicaleController/"+valutazione.getAnno();
		}
    }
    @RequestMapping(value ="{id}/editApicalePesoPianoLavoro" , params = "cancel", method =  RequestMethod.PUT )
    public String editApicalePesoPianoLavoroCancel(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        
    	return "redirect:/controllerValut/modifyValutazioneApicaleController/"+valutazione.getAnno();
	
    }
    
    
    // ------------------------- Pesi Comportamenti Organizzativi -------------------------------------------------
    @RequestMapping(value ="{id}/editApicalePesiComportOrgan" , method = RequestMethod.GET )
    public String editApicalePesiComportOrganGet(@PathVariable("id") int id, Model model) {
    	Valutazione valutazione = valutazioneCmdServizi.findById(id);
    	IncaricoGeko inc = fromOrganikoServizi.findIncaricoById(valutazione.getIncaricoID());
        valutazione.setIncarico(inc);
        //
        model.addAttribute("valutazione", valutazione);
        return "controller/formPesiComportOrganEditController";
        
    }
    @RequestMapping(value ="{id}/editApicalePesiComportOrgan" , params = "update", method =  RequestMethod.PUT )
    public String editApicalePesiComportOrganPut(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new ValutazioneValidator().validate(valutazione, result);
		if (result.hasErrors()) {
            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
            return "controller/formPesiComportOrganEditController";
		}
		else {
            System.out.println("ControllerValutazioneController.processUpdateSubmit() no errors =");
            this.controllerServizi.controllerUpdateValutazione(valutazione);
            status.setComplete();
            return "redirect:/controllerValut/modifyValutazioneApicaleController/"+valutazione.getAnno();
        	
		}
    }
    @RequestMapping(value ="{id}/editApicalePesiComportOrgan" , params = "cancel", method =  RequestMethod.PUT )
    public String editApicalePesiComportOrganCancel(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        
    	return "redirect:/controllerValut/modifyValutazioneApicaleController/"+valutazione.getAnno();
    	
    }
    
    */
} // ---------