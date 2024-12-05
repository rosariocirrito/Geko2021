/**
 * 
 */
package it.sicilia.regione.gekoddd.geko.rendicontazione.web.oiv;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.AzioneCmdService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.application.OivRendicontazioneService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivazione.OivAzione;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivazione.OivAzioneCmdService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivazione.OivAzioneValidator;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
@SessionAttributes(types = OivAzione.class)
@RequestMapping("/oivOivAzione")
public class OivAzioneController  {
    
	private Log log = LogFactory.getLog(OivAzioneController.class);
    
	@Autowired
    private OivAzioneCmdService oivAzioneCmdServizi;
	@Autowired
    private AzioneCmdService actCmdServizi;
    @Autowired
    private OivRendicontazioneService oivServizi;
    
    /*
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    */
    
    public OivAzioneController() { }
    
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
    
    // display a single oivAzione information (cfr. Pro Spring 3 chp 17 p.621)
    /*
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") int id, Model model) {
        OivAzione oivAzione = critServizi.findById(id);
        model.addAttribute(oivAzione);
	return "controller/azioneControllerShow";
    }
    //
     * 
     */
    
    // -------------------------  su azione--------------------------------------------
    @RequestMapping(value ="{id}/edit" , method = RequestMethod.GET )
    public String updateForm(@PathVariable("id") int id, Model model) {
    	log.info("Edit Get id"+id);
        OivAzione oivAzione = oivAzioneCmdServizi.findById(id);
        //
        model.addAttribute("oivAzione", oivAzione);
        return "geko/rendicontazione/oiv/formOivAzioneEditOiv";
    }
    //
    @RequestMapping(value ="{id}/edit" , params = "update", method =  RequestMethod.PUT )
    public String processUpdateSubmit(@ModelAttribute OivAzione oivAzione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new OivAzioneValidator().validate(oivAzione, result);
		if (result.hasErrors()) {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
			return "geko/rendicontazione/oiv/formOivAzioneEditOiv";
		}
		else {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
			log.info("Edit put id"+oivAzione.getId());
	            this.oivServizi.oivUpdateOivAzione(oivAzione);
	            status.setComplete();
	            return "redirect:/oivRend/modifyIndicazioniIncaricoApicaleOiv/"+
	            oivAzione.getAzione().getObiettivo().getAnno()+"/"+oivAzione.getIncaricoID(); 
		}
    }
    
    @RequestMapping(value ="{id}/edit" , params = "elimina", method =  RequestMethod.PUT )
    public String processDeleteSubmit(@ModelAttribute OivAzione oivAzione, 
                                BindingResult result, 
                                SessionStatus status) {
    	//System.out.println("ControllerOivAzioneController.processDeleteSubmit() da cancellare critic con id"+oivAzione.getId());
    	
        this.oivServizi.oivDeleteOivAzione(oivAzione);
        
        return "redirect:/ROLE_OIV";
    }
    
    @RequestMapping(value ="{id}/edit" , params = "cancel", method =  RequestMethod.PUT )
    public String processCancelSubmit(@ModelAttribute OivAzione oivAzione, 
                                BindingResult result, 
                                SessionStatus status) {
        
        
            return "redirect:/ROLE_OIV";
	
    }

    
    // crea oivAzione su azione
    @RequestMapping(value="new/{idAzione}", method = RequestMethod.GET)
    public String createOivAzioneController(@PathVariable("idAzione") int idAzione,Model model) {
        //final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        //OpPersonaFisica personaFisica = null;
        //if (null!=userName) personaFisica = userservizi.getPersonaFisicaByUserName(userName);
        //if (null!=personaFisica){  
            Azione azione = actCmdServizi.findById(idAzione);
            OivAzione oivAzione = new OivAzione();
            oivAzione.setAzione(azione);
            //azione.addOivAzioneToAzione(oivAzione);
            model.addAttribute("oivAzione", oivAzione);
  
        //}
        return "geko/rendicontazione/oiv/formOivAzioneCreateOiv";
        
    }
    
    @RequestMapping(value="new/{idAzione}", method = RequestMethod.POST)
    public String processSubmitOivAzioneController(@ModelAttribute OivAzione oivAzione, 
                                BindingResult result, 
                                SessionStatus status) {
        //
	new OivAzioneValidator().validate(oivAzione, result);
	//
        if (result.hasErrors()) {
        	return "geko/rendicontazione/oiv/formOivAzioneCreateOiv";
        }	
	else {
            this.oivServizi.oivCreateOivAzione(oivAzione);
            status.setComplete();
            return "redirect:/oivRend/modifyIndicazioniIncaricoApicaleOiv/"+
            oivAzione.getAzione().getObiettivo().getAnno()+"/"+oivAzione.getIncaricoID(); 
	}
    }
    
    
    // Valutazioni OIV
    
    @RequestMapping(value ="{id}/editValutOIV" , method = RequestMethod.GET )
    public String editValutOIVGet(@PathVariable("id") int id, Model model) {
    	log.info("Edit Get id"+id);
        OivAzione oivAzione = oivAzioneCmdServizi.findById(id);
        //
        model.addAttribute("oivAzione", oivAzione);
        return "geko/valutazione/oiv/formOivAzioneValutOIVEditOiv";
    }
    //
    @RequestMapping(value ="{id}/editValutOIV" , params = "update", method =  RequestMethod.PUT )
    public String editValutOIVPutUpdate(@ModelAttribute OivAzione oivAzione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new OivAzioneValidator().validate(oivAzione, result);
		if (result.hasErrors()) {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
			return "geko/valutazione/oiv/formOivAzioneValutOIVEditOiv";
		}
		else {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
			log.info("Edit put id"+oivAzione.getId());
	            this.oivServizi.oivUpdateOivAzione(oivAzione);
	            status.setComplete();
	            return "redirect:/oivValut/modifyValutazioneIncaricoApicaleOiv/"+
	            oivAzione.getAzione().getObiettivo().getAnno()+"/"+oivAzione.getIncaricoID(); 
		}
    }
    
    @RequestMapping(value ="{id}/editValutOIV" , params = "elimina", method =  RequestMethod.PUT )
    public String editValutOIVPutDelete(@ModelAttribute OivAzione oivAzione, 
                                BindingResult result, 
                                SessionStatus status) {
    	//System.out.println("ControllerOivAzioneController.processDeleteSubmit() da cancellare critic con id"+oivAzione.getId());
    	
        this.oivServizi.oivDeleteOivAzione(oivAzione);
        
        return "redirect:/oivValut/modifyValutazioneIncaricoApicaleOiv/"+
        oivAzione.getAzione().getObiettivo().getAnno()+"/"+oivAzione.getIncaricoID(); 
    }
    
    @RequestMapping(value ="{id}/editValutOIV" , params = "cancel", method =  RequestMethod.PUT )
    public String editValutOIVPutCancel(@ModelAttribute OivAzione oivAzione, 
                                BindingResult result, 
                                SessionStatus status) {
        
    	return "redirect:/oivValut/modifyValutazioneIncaricoApicaleOiv/"+
	            oivAzione.getAzione().getObiettivo().getAnno()+"/"+oivAzione.getIncaricoID(); 
	
    }

    
    
    //
 // crea oivAzione su azione
    @RequestMapping(value="newValutOIV/{idAzione}", method = RequestMethod.GET)
    public String createOivAzioneValutOIVController(@PathVariable("idAzione") int idAzione,Model model) {
        //final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        //OpPersonaFisica personaFisica = null;
        //if (null!=userName) personaFisica = userservizi.getPersonaFisicaByUserName(userName);
        //if (null!=personaFisica){  
            Azione azione = actCmdServizi.findById(idAzione);
            OivAzione oivAzione = new OivAzione();
            oivAzione.setAzione(azione);
            //azione.addOivAzioneToAzione(oivAzione);
            model.addAttribute("oivAzione", oivAzione);
  
        //}
        return "geko/valutazione/oiv/formOivAzioneValutOIVCreateOiv";
        
    }
    
    @RequestMapping(value="newValutOIV/{idAzione}", method = RequestMethod.POST)
    public String processSubmitOivAzioneValutOIVController(@ModelAttribute OivAzione oivAzione, 
                                BindingResult result, 
                                SessionStatus status) {
        //
	new OivAzioneValidator().validate(oivAzione, result);
	//
        if (result.hasErrors()) {
        	return "geko/valutazione/oiv/formOivAzioneValutOIVCreateOiv";
        }	
	else {
            this.oivServizi.oivCreateOivAzione(oivAzione);
            status.setComplete();
            return "redirect:/oivValut/modifyValutazioneIncaricoApicaleOiv/"+
            oivAzione.getAzione().getObiettivo().getAnno()+"/"+oivAzione.getIncaricoID(); 
            
		}
    }
    
    
    
} // ---------