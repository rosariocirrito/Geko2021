package it.sicilia.regione.gekoddd.geko.valutazione.web.oiv;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.valutazione.application.OivValutazioneService;
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
@RequestMapping("/oivValutazione")
public class OivValutazioneController  {
    
    
	@Autowired
    private ValutazioneCmdService valutazioneCmdServizi;
    @Autowired
    private OivValutazioneService oivServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    
    @Autowired
    private Menu menu;
    
    
    public OivValutazioneController() { }
    
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
    
   
    
 // ------------------------ Peso Piano Lavoro -------ok sepicos ------------------------------------------------------
    @RequestMapping(value ="{id}/editPesoPianoLavoro/{idIncarico}" , method = RequestMethod.GET )
    public String updateFormeditPesoPianoLavoro(@PathVariable("id") int id, @PathVariable("idIncarico") int idIncarico, Model model) {
    	Valutazione valutazione = valutazioneCmdServizi.findById(id);
    	IncaricoGeko inc = fromOrganikoServizi.findIncaricoById(idIncarico);
    	valutazione.setIncarico(inc);
        //
        model.addAttribute("valutazione", valutazione);
        return "geko/valutazione/oiv/formPesoPianoLavoroEditOiv";
    }
    @RequestMapping(value ="{id}/editPesoPianoLavoro/{idIncarico}" , params = "update", method =  RequestMethod.PUT )
    public String processUpdateSubmiteditPesoPianoLavoro(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new ValutazioneValidator().validate(valutazione, result, menu.getAnno());
		if (result.hasErrors()) {
            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
            return "geko/valutazione/oiv/formPesoPianoLavoroEditOiv";
		}
		else {
            System.out.println("ControllerValutazioneController.processUpdateSubmit() no errors =");
            this.oivServizi.oivUpdateValutazione(valutazione);
            status.setComplete();
            return "redirect:/oivValut/modifyValutazioneIncaricoApicaleOiv/"+valutazione.getAnno()
                    +"/"+valutazione.getIncarico().getIdIncarico();
		}
    }
    @RequestMapping(value ="{id}/editPesoPianoLavoro/{idIncarico}" , params = "cancel", method =  RequestMethod.PUT )
    public String processCancelSubmitediteditPesoPianoLavoro(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        
    	return "redirect:/oivValut/modifyValutazioneIncaricoApicaleOiv/"+valutazione.getAnno()
                +"/"+valutazione.getIncarico().getIdIncarico();
	
    }
    
    
   
    
 // ------------------------ Valutazione Piano Lavoro -------Ok sepicos ------------------------------------------------------
    @RequestMapping(value ="{id}/editValutPianoLavoro/{idIncarico}" , method = RequestMethod.GET )
    public String updateFormeditValutPianoLavoro(@PathVariable("id") int id, @PathVariable("idIncarico") int idIncarico, 
    		Model model) {
    	Valutazione valutazione = valutazioneCmdServizi.findById(id);
    	IncaricoGeko inc = fromOrganikoServizi.findIncaricoById(idIncarico);
    	valutazione.setIncarico(inc);
        //
        model.addAttribute("valutazione", valutazione);
        return "geko/valutazione/oiv/formValutPianoLavoroEditOiv";
    }
    @RequestMapping(value ="{id}/editValutPianoLavoro/{idIncarico}" , params = "update", method =  RequestMethod.PUT )
    public String processUpdateSubmiteditValutPianoLavoro(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new ValutazioneValidator().validate(valutazione, result, menu.getAnno());
		if (result.hasErrors()) {
            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
			return "geko/valutazione/oiv/formValutPianoLavoroEditOiv";
		}
		else {
            //System.out.println("ControllerValutazioneController.processUpdateSubmit() no errors =");
            this.oivServizi.oivUpdateValutazione(valutazione);
            status.setComplete();
            /*
            return "redirect:/sepicos/modifyValutazioniDipartimentoController/"+valutazione.getAnno();
            //+"/"+valutazione.getIncarico().getIdStruttura();
             * 
             */
            return "redirect:/oivValut/modifyValutazioneIncaricoApicaleOiv/"+valutazione.getAnno()
                    +"/"+valutazione.getIncarico().getIdIncarico();
		}
    }
    @RequestMapping(value ="{id}/editValutPianoLavoro/{idIncarico}" , params = "cancel", method =  RequestMethod.PUT )
    public String processCancelSubmiteditValutPianoLavoro(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        
    	return "redirect:/sepicos/modifyValutazioneIncaricoApicaleSepicos/"+valutazione.getAnno()
                +"/"+valutazione.getIncarico().getIdIncarico();
	
    }
    
    
 // --------------------------- comportamenti organizzativi
 // crea Comportamento Organizzativo su incarico
    @RequestMapping(value="new/{idIncarico}/{anno}", method = RequestMethod.GET)
    public String createComportOrganController(
    		@PathVariable("idIncarico") int idIncarico, @PathVariable("anno") int anno, Model model) {
        
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
        return "geko/valutazione/oiv/formComportamentiOrganizzativiCreateOiv";
        
    }
    
    @RequestMapping(value="new/{idIncarico}/{anno}", method = RequestMethod.POST)
    public String processSubmitComportOrganController(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        //
		new ValutazioneValidator().validate(valutazione, result, menu.getAnno());
		//
	        if (result.hasErrors()) {
	        	return "geko/valutazione/oiv/formComportamentiOrganizzativiCreateOiv";
	        }	
		else {
	            this.oivServizi.oivCreateValutazioneCompOrg(valutazione);
	            status.setComplete();
	            return "redirect:/oivValut/modifyValutazioneIncaricoApicaleOiv/"+valutazione.getAnno()+"/"+valutazione.getIncaricoID();
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
        return "geko/valutazione/oiv/formPesiComportOrganEditOiv";
        
    }
    @RequestMapping(value ="{id}/editPesiComportOrgan" , params = "update", method =  RequestMethod.PUT )
    public String processUpdateSubmitPesiComportOrgan(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new ValutazioneValidator().validate(valutazione, result, menu.getAnno());
		if (result.hasErrors()) {
            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
			return "geko/valutazione/oiv/formPesiComportOrganEditOiv";
		}
		else {
            //System.out.println("ControllerValutazioneController.processUpdateSubmit() no errors =");
            this.oivServizi.oivUpdateValutazioneCompOrg(valutazione);
            status.setComplete();
            return "redirect:/oivValut/modifyValutazioneIncaricoApicaleOiv/"+valutazione.getAnno()
                    +"/"+valutazione.getIncaricoID();
		}
    }
    
    @RequestMapping(value ="{id}/editPesiComportOrgan" , params = "cancel", method =  RequestMethod.PUT )
    public String processCancelSubmiteditPesiComportOrgan(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        
    	 return "redirect:/oivValut/modifyValutazioneIncaricoApicaleOiv/"+valutazione.getAnno()
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
        return "geko/valutazione/oiv/formValutComportOrganEditOiv";
        
    }
    @RequestMapping(value ="{id}/editValutComportOrgan" , params = "update", method =  RequestMethod.PUT )
    public String processUpdateSubmitValutComportOrgan(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new ValutazioneValidator().validate(valutazione, result, menu.getAnno());
		if (result.hasErrors()) {
            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
			return "geko/valutazione/oiv/formValutComportOrganEditOiv";
		}
		else {
            //System.out.println("ControllerValutazioneController.processUpdateSubmit() no errors =");
            this.oivServizi.oivUpdateValutazioneCompOrg(valutazione);
            status.setComplete();
            /*
            return "redirect:/controller/modifyValutazioneDipartimentaleController/"+valutazione.getAnno();
            //+"/"+valutazione.getIncarico().getIdStruttura();
            */
            return "redirect:/oivValut/modifyValutazioneIncaricoApicaleOiv/"+valutazione.getAnno()
                    +"/"+valutazione.getIncaricoID();
		}
    }
    @RequestMapping(value ="{id}/editValutComportOrgan" , params = "cancel", method =  RequestMethod.PUT )
    public String processCancelSubmitValutComportOrgan(@ModelAttribute Valutazione valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        
    	return "redirect:/oivValut/modifyValutazioneIncaricoApicaleOiv/"+valutazione.getAnno()
                +"/"+valutazione.getIncaricoID();
	
    }
    
 
    // ------------------ apicali -----------------------
    
   
    
    
    
   
} // ---------