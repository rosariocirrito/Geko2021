/**
 * 
 */
package it.sicilia.regione.gekoddd.geko.rendicontazione.web.oiv;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoCmdService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.application.OivRendicontazioneService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivobiettivo.OivObiettivo;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivobiettivo.OivObiettivoCmdService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivobiettivo.OivObiettivoValidator;

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
@SessionAttributes(types = OivObiettivo.class)
@RequestMapping("/oivOivObiettivo")
public class OivObiettivoController  {
    
    
	@Autowired
    private OivObiettivoCmdService oivObiettivoCmdServizi;
	@Autowired
    private ObiettivoCmdService objCmdServizi;
    @Autowired
    private OivRendicontazioneService oivServizi;
    
    /*
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    */
    
    public OivObiettivoController() { }
    
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
    
    // display a single oivObiettivo information (cfr. Pro Spring 3 chp 17 p.621)
    /*
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") int id, Model model) {
        OivObiettivo oivObiettivo = critServizi.findById(id);
        model.addAttribute(oivObiettivo);
	return "controller/obiettivoOivShow";
    }
    //
     * 
     */
    
    // ------------------------- Criticit� su obiettivo--------------------------------------------
    @RequestMapping(value ="{id}/edit" , method = RequestMethod.GET )
    public String updateForm(@PathVariable("id") int id, Model model) {
        OivObiettivo oivObiettivo = oivObiettivoCmdServizi.findById(id);
        //
        model.addAttribute("oivObiettivo", oivObiettivo);
        return "geko/rendicontazione/controller/formOivObiettivoEditOiv";
    }
    //
    @RequestMapping(value ="{id}/edit" , params = "update", method =  RequestMethod.PUT )
    public String processUpdateSubmit(@ModelAttribute OivObiettivo oivObiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuOiv.processUpdateSubmit() obj="+obiettivo.getDenominobiettivo());
        new OivObiettivoValidator().validate(oivObiettivo, result);
		if (result.hasErrors()) {
	            //System.out.println("ManagerMenuOiv.processUpdateSubmit() has errors="+obiettivo.getDenominobiettivo());
	            return "geko/rendicontazione/controller/formOivObiettivoEditOiv";
		}
		else {
	            //System.out.println("ManagerMenuOiv.processUpdateSubmit() no errors="+obiettivo.getDenominobiettivo());
	            this.oivServizi.oivUpdateOivObiettivo(oivObiettivo);
	            status.setComplete();
	            return "redirect:/controllerRend/modifyIndicazioniIncaricoOiv/"+
	            oivObiettivo.getObiettivo().getAnno()+"/"+oivObiettivo.getIncaricoID(); 
		}
    }
    
    @RequestMapping(value ="{id}/edit" , params = "elimina", method =  RequestMethod.PUT )
    public String processDeleteSubmit(@ModelAttribute OivObiettivo oivObiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
    	//System.out.println("OivOivObiettivoOiv.processDeleteSubmit() da cancellare critic con id"+oivObiettivo.getId());
    	
        this.oivServizi.oivDeleteOivObiettivo(oivObiettivo);
        
        return "redirect:/ROLE_CONTROLLER";
    }
    
    @RequestMapping(value ="{id}/edit" , params = "cancel", method =  RequestMethod.PUT )
    public String processCancelSubmit(@ModelAttribute OivObiettivo oivObiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
        
        
            return "redirect:/ROLE_CONTROLLER";
	
    }

    
    // crea oivObiettivo su obiettivo
    @RequestMapping(value="new/{idObiettivo}", method = RequestMethod.GET)
    public String createOivObiettivoOiv(@PathVariable("idObiettivo") int idObiettivo,Model model) {
        //final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        //OpPersonaFisica personaFisica = null;
        //if (null!=userName) personaFisica = userservizi.getPersonaFisicaByUserName(userName);
        //if (null!=personaFisica){  
            Obiettivo obiettivo = objCmdServizi.findById(idObiettivo);
            OivObiettivo oivObiettivo = new OivObiettivo();
            oivObiettivo.setObiettivo(obiettivo);
            //obiettivo.addOivObiettivoToObiettivo(oivObiettivo);
            model.addAttribute("oivObiettivo", oivObiettivo);
  
        //}
        return "geko/rendicontazione/oiv/formOivObiettivoCreateOiv";
        
    }
    
    @RequestMapping(value="new/{idObiettivo}", method = RequestMethod.POST)
    public String processSubmitOivObiettivoOiv(@ModelAttribute OivObiettivo oivObiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
        //
	new OivObiettivoValidator().validate(oivObiettivo, result);
	//
        if (result.hasErrors()) {
            return "geko/rendicontazione/oiv/formOivObiettivoCreateOiv";
        }	
	else {
            this.oivServizi.oivCreateOivObiettivo(oivObiettivo);
            status.setComplete();
            return "redirect:/controllerRend/modifyIndicazioniIncaricoOiv/"+
            oivObiettivo.getObiettivo().getAnno()+"/"+oivObiettivo.getIncaricoID(); 
	}
    }
    
    
    
} // ---------