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
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazioneCmdService;
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
@SessionAttributes(types = ValutazioneComparto.class)
@RequestMapping("/controllerValutazioneComparto")
public class ControllerValutazioneCompartoController  {
    
	private static final Logger log = LoggerFactory.getLogger(ControllerValutazioneCompartoController.class);
    
	@Autowired
    private ValutazioneCompartoCmdService valutazioneCompartoServizi;
    @Autowired
    private ControllerValutazioneService controllerServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    public ControllerValutazioneCompartoController() { }
    
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
    
    @RequestMapping(value ="{id}/edit" , method = RequestMethod.GET )
    public String getValutazioneCompartoEdit(@PathVariable("id") int id, Model model) {
    	ValutazioneComparto valutazioneComparto = valutazioneCompartoServizi.findById(id);
    	PersonaFisicaGeko dip = fromOrganikoServizi.findPersonaFisicaById(valutazioneComparto.getPfID());
        //
        model.addAttribute("valutazioneComparto", valutazioneComparto);
        if(dip.isCatAB()) return "geko/valutazione/controller/formValutazioneCompartoABEditController";
        else return "geko/valutazione/controller/formValutazioneCompartoCDEditController";
    }
    
    
    @RequestMapping(value ="{id}/edit" , params = "update", method =  RequestMethod.PUT )
    public String putValutazioneCompartoEdit(@ModelAttribute ValutazioneComparto valutazioneComparto, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ControllerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new ValutazioneCompartoValidator().validate(valutazioneComparto, result);
        PersonaFisicaGeko dip = fromOrganikoServizi.findPersonaFisicaById(valutazioneComparto.getPfID());
		if (result.hasErrors()) {
			if(dip.isCatAB()) return "geko/valutazione/controller/formValutazioneCompartoABEditController";
	        else return "geko/valutazione/controller/formValutazioneCompartoCDEditController";
		}
		else {
			log.info("putValutazioneCompartoEdit() valut: "+valutazioneComparto.toString());
            this.controllerServizi.controllerUpdateValutazioneComparto(valutazioneComparto);
            status.setComplete();
            int incId = valutazioneComparto.getIncaricoID();
            IncaricoGeko inc = fromOrganikoServizi.findIncaricoById(incId);
            if(inc.incaricoDipartimentale) {
            	return "redirect:/controllerValut/modifyDipendentiAssegnazioniValutazione/"+ valutazioneComparto.getAnno()
        		+"/"+valutazioneComparto.getIncaricoID();
            }
            else {
            	return "redirect:/controllerValut/modifyValutazioneStrutturaManager/"
                        + valutazioneComparto.getAnno()
                        +"/"+incId; 
            	}
            
		}
    }
    
    @RequestMapping(value ="{id}/edit" , params = "delete", method =  RequestMethod.PUT )
    public String processDeleteSubmit(@ModelAttribute ValutazioneComparto valutazioneComparto, 
                                BindingResult result, 
                                SessionStatus status) {
        this.controllerServizi.controllerDeleteValutazioneComparto(valutazioneComparto);
        return "redirect:/controllerValut/modifyDipendentiAssegnazioniValutazione/"+ valutazioneComparto.getAnno()
        		+"/"+valutazioneComparto.getIncaricoID();
    }
    
    @RequestMapping(value ="{id}/edit" , params = "cancel", method =  RequestMethod.PUT )
    public String processCancelSubmit(@ModelAttribute ValutazioneComparto valutazioneComparto, 
                                BindingResult result, 
                                SessionStatus status) {
        
            return "redirect:/ROLE_CONTROLLER";
	
    }

    
    // crea Valutazioni prestazioni su dipendente
    @RequestMapping(value="new/{idDipendente}/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String createValutPrestazioniController(@PathVariable("idDipendente") int idDipendente, 
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico,
    		Model model) {
        //final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        //OpPersonaFisica personaFisica = null;
        //if (null!=userName) personaFisica = userservizi.getPersonaFisicaByUserName(userName);
        //if (null!=personaFisica){  
            ValutazioneComparto valutazioneComparto = new ValutazioneComparto();
            PersonaFisicaGeko dip = fromOrganikoServizi.findPersonaFisicaById(idDipendente);            
            valutazioneComparto.setAnno(anno);
            valutazioneComparto.setPfID(idDipendente);
            valutazioneComparto.setIncaricoID(idIncarico);
            log.info("getNewComportOrgan() "+valutazioneComparto.toString());
            if(dip.isCatAB()){
        		log.info("getNewComportOrganController() valutazione comparto dip. AB");
        		// 1.
        		valutazioneComparto.setCompetSvolgAttivAss(20);
        		valutazioneComparto.setCompetSvolgAttivVal(ValutazioneEnum.DA_VALUTARE);
        		
        		// 2.
        		valutazioneComparto.setAdattContextLavAss(20);
        		valutazioneComparto.setAdattContextLavVal(ValutazioneEnum.DA_VALUTARE);
        		
        		// 4.
        		valutazioneComparto.setCapacAssolvCompitiAss(20);
        		valutazioneComparto.setCapacAssolvCompitiVal(ValutazioneEnum.DA_VALUTARE);
        		
        		// 3.
        		valutazioneComparto.setInnovazAss(0);
        		valutazioneComparto.setInnovazVal(ValutazioneEnum.DA_VALUTARE);
        		// 5.
        		valutazioneComparto.setOrgLavAss(0);
        		valutazioneComparto.setOrgLavVal(ValutazioneEnum.DA_VALUTARE);  
        		//
        		model.addAttribute("valutazioneComparto", valutazioneComparto);
        		return "geko/valutazione/controller/formValutazioneCompartoABCreateController";
        	}
        	else {
        		log.info("getNewComportOrganController() valutazione comparto dip. non AB");
        		// 1.
        		valutazioneComparto.setCompetSvolgAttivAss(8);
        		valutazioneComparto.setCompetSvolgAttivVal(ValutazioneEnum.DA_VALUTARE);
        		
        		// 2.
        		valutazioneComparto.setAdattContextLavAss(8);
        		valutazioneComparto.setAdattContextLavVal(ValutazioneEnum.DA_VALUTARE);
        		
                // 4.
        		valutazioneComparto.setCapacAssolvCompitiAss(8);
        		valutazioneComparto.setCapacAssolvCompitiVal(ValutazioneEnum.DA_VALUTARE);
                // 3.
        		valutazioneComparto.setInnovazAss(8);
        		valutazioneComparto.setInnovazVal(ValutazioneEnum.DA_VALUTARE);
                // 5.
        		valutazioneComparto.setOrgLavAss(8);
        		valutazioneComparto.setOrgLavVal(ValutazioneEnum.DA_VALUTARE);
                //
        		model.addAttribute("valutazioneComparto", valutazioneComparto);
        		return "geko/valutazione/controller/formValutazioneCompartoCDCreateController";
            }
        	
            
            
  
        //}
        
        
    }
    
    @RequestMapping(value="new/{idDipendente}/{anno}/{idIncarico}", params = "add", method = RequestMethod.POST)
    public String processSubmitValutPrestazioniController(@ModelAttribute ValutazioneComparto valutazioneComparto, 
                                BindingResult result, 
                                SessionStatus status) {
        //
		new ValutazioneCompartoValidator().validate(valutazioneComparto, result);
		PersonaFisicaGeko dip = fromOrganikoServizi.findPersonaFisicaById(valutazioneComparto.getPfID()); 
		//
	        if (result.hasErrors()) {
	        	if(dip.isCatAB()){
	        		return "controller/formValutazioneCompartoABCreateController";
	            }
	        	else{
	        		return "controller/formValutazioneCompartoCDCreateController";
	        	}
	        }	
		else {
	            this.controllerServizi.controllerCreateValutazioneComparto(valutazioneComparto);
	            status.setComplete();
	            return "redirect:/controllerValut/modifyDipendentiAssegnazioniValutazione/"+ valutazioneComparto.getAnno()+"/"+valutazioneComparto.getIncaricoID();
		}
    }
    
    @RequestMapping(value ="new/{idDipendente}/{anno}/{idIncarico}", params = "cancel", method =  RequestMethod.POST )
    public String processCancelSubmit(
                                BindingResult result, 
                                SessionStatus status) {
        
        
            return "redirect:/ROLE_CONTROLLER";
	
    }
    
    
} // ---------