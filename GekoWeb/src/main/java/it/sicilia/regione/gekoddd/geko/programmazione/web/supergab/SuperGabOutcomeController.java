/**
 * 
 */
package it.sicilia.regione.gekoddd.geko.programmazione.web.supergab;

import it.sicilia.regione.gekoddd.geko.pianificazione.application.SuperGabinettoPianificazioneService;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategico;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategicoCmdService;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategicoValidator;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.outcome.Outcome;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.outcome.OutcomeCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.outcome.OutcomeValidator;
import it.sicilia.regione.gekoddd.log.model.evento.Evento;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import java.text.SimpleDateFormat;
import java.util.*;

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
@SessionAttributes(types = Outcome.class)
@RequestMapping("/superGabOutcome")
public class SuperGabOutcomeController  {
    
	private Log log = LogFactory.getLog(SuperGabOutcomeController.class);
	//
    @Autowired
    private ObiettivoStrategicoCmdService obiettivoStrategicoCmdServizi;
    @Autowired
    private OutcomeCmdService outcomeCmdServizi;
    @Autowired
    private SuperGabinettoPianificazioneService superGabinettoServizi;
    
    //@Autowired
    //private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    public SuperGabOutcomeController() { }
    
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
    
    //
 // ---------------- CASI D'USO  ----------------------------
    
    
//  
    //
    @RequestMapping(value="newOutcome/{idObjStrat}", method = RequestMethod.GET)
    public String createOutcomeGet(@PathVariable("idObjStrat") int idObjStrat, Model model) {
    	ObiettivoStrategico obiettivoStrategico = obiettivoStrategicoCmdServizi.findById(idObjStrat);
    	log.info("createOutcomeGet su objstrat con id="+idObjStrat);
        //
    	Outcome outcome = new Outcome();
    	outcome.setObiettivoStrategico(obiettivoStrategico);
        model.addAttribute(outcome);
        String view = "geko/programmazione/supergab/outcome/formOutcomeCreaSuperGabinetto";
        return view;
        
    }
    
    //
    @RequestMapping(value="newOutcome/{idObjStrat}", params = "add", method = RequestMethod.POST)
    public String createOutcomePost(@ModelAttribute Outcome outcome,
                                BindingResult result, 
                                SessionStatus status) {
        //
		new OutcomeValidator().validate(outcome, result);
		log.info("createOutcomeGet su objstrat con id="+outcome.getObiettivoStrategico().getId());
		//
        if (result.hasErrors()) {
        	String view = "geko/programmazione/supergab/outcome/formOutcomeCreaSuperGabinetto";
            return view;
        }	
		else {
			    //log.info("createObiettivoStrategicoPost con id="+obiettivoStrategico.getId());
	            Evento event = this.superGabinettoServizi.cmdSuperGabinettoCreateOutcome(outcome);
	            if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
	            	status.setComplete();
		            return "redirect:/superGabProg/modifyDirettivaSuperGab/"+menu.getAnno();  
		            
	    			}
	    			
	    		else return "redirect:/geko/programmazione/supergab/errCmd/"+ event.getId();  
	           }
    }
    
    //
    @RequestMapping(value="newOutcome/{idObjStrat}", params = "cancel", method = RequestMethod.POST)
    public String createObiettivoStrategicoCancel(@ModelAttribute Outcome outcome,
            BindingResult result, 
            SessionStatus status) {
    	status.setComplete();
		return "redirect:/ROLE_SUPERGABINETTO"; 	
    }
    
 // azione generica su cui aggiungere descrizione proposta o azione di obiettivo proposto in cui mettere tutto
    @RequestMapping(value ="{id}/edit" , method = RequestMethod.GET )
    public String updateForm(@PathVariable("id") int id, Model model) {
    	Outcome outcome = outcomeCmdServizi.findById(id);
        //
    	model.addAttribute(outcome);
    	String view = "geko/programmazione/supergab/outcome/formOutcomeUpdateSuperGabinetto";
        return view; 
	}
    //
    @RequestMapping(value ="{id}/edit" , params = "update", method =  RequestMethod.PUT )
    public String processUpdateSubmit(@ModelAttribute Outcome outcome, 
                                BindingResult result, 
                                SessionStatus status) {
    	new OutcomeValidator().validate(outcome, result);
		if (result.hasErrors()) {
			String view = "geko/programmazione/supergab/outcome/formOutcomeUpdateSuperGabinetto";
	        return view; 
		}
		else {
			Evento event = this.superGabinettoServizi.cmdSuperGabinettoUpdateOutcome(outcome);
            if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
            	status.setComplete();
	            return "redirect:/superGabProg/modifyDirettivaSuperGab/"+menu.getAnno();  
	            
    			}
    		else return "redirect:/geko/programmazione/supergab/errCmd/"+ event.getId();  
        }
	}
    

    @RequestMapping(value ="{id}/edit" , params = "delete", method =  RequestMethod.PUT)
    public String processDeleteSubmit(@ModelAttribute Outcome outcome, 
                                BindingResult result, 
                                SessionStatus status) {
    	Evento event = this.superGabinettoServizi.cmdSuperGabinettoDeleteOutcome(outcome);
    	if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
        	status.setComplete();
            return "redirect:/superGabProg/modifyDirettivaSuperGab/"+menu.getAnno();  
            
			}
		else return "redirect:/geko/programmazione/supergab/errCmd/"+ event.getId();   
    }
 
    
    @RequestMapping(value ="{id}/edit" , params = "cancel", method =  RequestMethod.PUT )
    public String processCancelSubmit(@ModelAttribute Outcome outcome, 
                                BindingResult result, 
                                SessionStatus status) {
    	return "redirect:/ROLE_SUPERGABINETTO";
	
    }
 
    
} // --------------------------------------------------------------
