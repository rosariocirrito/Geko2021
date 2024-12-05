
package it.sicilia.regione.gekoddd.geko.programmazione.web.manager;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.valutazione.application.ManagerValutazioneService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneComparto;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneComparto.ValutazioneEnum;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneCompartoValidator;
import it.sicilia.regione.gekoddd.geko.valutazione.infrastructure.ValutazioneCompartoCmdService;
import it.sicilia.regione.gekoddd.geko.valutazione.web.manager.ManagerValutazioneListController;
import it.sicilia.regione.gekoddd.session.domain.Menu;

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



@Controller
@SessionAttributes(types = ValutazioneComparto.class)
@RequestMapping("/dirigenteCompOrg")
public class ManagerCompOrgController  {
    
	private static final Logger log = LoggerFactory.getLogger(ManagerCompOrgController.class);
	
	@Autowired
    private ValutazioneCompartoCmdService valutazioneCmdServizi;
    @Autowired
    private ManagerValutazioneService managerValutazioneServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    
    public ManagerCompOrgController() { }
    
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
    @RequestMapping(value="new/{idPersona}/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String getNewComportOrgan(
    		@PathVariable("idPersona") int idPersona, @PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
        ValutazioneComparto valutazioneComparto = new ValutazioneComparto();
        valutazioneComparto.setIncaricoID(idIncarico);
        PersonaFisicaGeko dip = fromOrganikoServizi.findPersonaFisicaById(idPersona);
        IncaricoGeko inc = fromOrganikoServizi.findIncaricoById(idIncarico);
        valutazioneComparto.setPfID(idPersona);
        valutazioneComparto.setIncarico(inc);
        valutazioneComparto.setAnno(anno);
    	//
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
            
        }
    	log.info("getNewComportOrgan() "+valutazioneComparto.toString());
    	model.addAttribute("valutazioneComparto", valutazioneComparto);
    	if(dip.isCatAB()) return "geko/programmazione/manager/formCompOrgABCompartoCreateManager";        	
        else return "geko/programmazione/manager/formCompOrgCDCompartoCreateManager";
        	
    }
    
    @RequestMapping(value="new/{idPersona}/{anno}/{idIncarico}", params = "add", method = RequestMethod.POST)
    public String postNewComportOrgan(@ModelAttribute ValutazioneComparto valutazioneComparto, 
                                BindingResult result, 
                                SessionStatus status) {
        //
    	PersonaFisicaGeko dip = fromOrganikoServizi.findPersonaFisicaById(valutazioneComparto.getPfID());
    	log.info("postNewComportOrgan() "+valutazioneComparto.toString());
		new ValutazioneCompartoValidator().validate(valutazioneComparto, result);
		//
        if (result.hasErrors()) {
        	if(dip.isCatAB()) return "geko/programmazione/manager/formCompOrgABCompartoCreateManager";	        		
            else return "geko/programmazione/manager/formCompOrgCDCompartoCreateManager";
            	
        }	
		else {
	            this.managerValutazioneServizi.managerCreateValutazioneComparto(valutazioneComparto);
	            status.setComplete();
	            return "redirect:/dirigente/modifyCompartoProgrammazione/"+valutazioneComparto.getAnno()+"/"+valutazioneComparto.getIncaricoID();
		}
    }
    
    @RequestMapping(value="new/{idPersona}/{anno}/{idIncarico}", params = "cancel", method = RequestMethod.POST)
    public String processSubmitComportOrganControllerCancel(@ModelAttribute ValutazioneComparto valutazioneComparto, 
                                BindingResult result, 
                                SessionStatus status) {
        //		
	            return "redirect:/dirigente/modifyCompartoProgrammazione/"+valutazioneComparto.getAnno()+"/"+valutazioneComparto.getIncaricoID();
		
    }
    
 
    
    // ------------------------- Pesi Comportamenti Organizzativi -------------------------------------------------
    @RequestMapping(value ="{id}/editPesiComportOrgan" , method = RequestMethod.GET )
    public String getEditPesiComportOrgan(@PathVariable("id") int id, Model model) {
    	ValutazioneComparto valutazione = valutazioneCmdServizi.findById(id);
    	IncaricoGeko inc = fromOrganikoServizi.findIncaricoById(valutazione.getIncaricoID());
    	PersonaFisicaGeko dip = fromOrganikoServizi.findPersonaFisicaById(valutazione.getPfID());
        valutazione.setIncarico(inc);
        valutazione.setPf(dip);
        //
        log.info("getEditPesiComportOrgan() "+valutazione.toString());
        model.addAttribute("valutazioneComparto", valutazione);
        if(dip.isCatAB()) return "geko/programmazione/manager/formCompOrgABCompartoEditManager";	        		
        else return "geko/programmazione/manager/formCompOrgCDCompartoEditManager";
    }
    @RequestMapping(value ="{id}/editPesiComportOrgan" , params = "update", method =  RequestMethod.PUT )
    public String putEditPesiComportOrgan(@ModelAttribute ValutazioneComparto valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
    	log.info("putEditPesiComportOrgan() "+valutazione.toString());
        new ValutazioneCompartoValidator().validate(valutazione, result);
        PersonaFisicaGeko dip = fromOrganikoServizi.findPersonaFisicaById(valutazione.getPfID());
		if (result.hasErrors()) {
			if(dip.isCatAB()) return "geko/programmazione/manager/formCompOrgABCompartoEditManager";	        		
	        else return "geko/programmazione/manager/formCompOrgCDCompartoEditManager";
		}
		else {
            //System.out.println("ControllerValutazioneController.processUpdateSubmit() no errors =");
            this.managerValutazioneServizi.managerUpdateValutazioneComparto(valutazione);
            status.setComplete();
            return "redirect:/dirigente/modifyCompartoProgrammazione/"+valutazione.getAnno()
                    +"/"+valutazione.getIncaricoID();
		}
    }
    
    @RequestMapping(value ="{id}/editPesiComportOrgan" , params = "cancel", method =  RequestMethod.PUT )
    public String processCancelSubmiteditPesiComportOrgan(@ModelAttribute ValutazioneComparto valutazione, 
                                BindingResult result, 
                                SessionStatus status) {
        
    	return "redirect:/dirigente/modifyCompartoProgrammazione/"+valutazione.getAnno()
    			+"/"+valutazione.getIncaricoID();
	
    }          
   
} // ---------