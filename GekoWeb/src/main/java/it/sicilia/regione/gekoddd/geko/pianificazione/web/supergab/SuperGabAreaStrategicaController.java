/**
 * 
 */
package it.sicilia.regione.gekoddd.geko.pianificazione.web.supergab;

import it.sicilia.regione.gekoddd.geko.pianificazione.application.SuperGabinettoPianificazioneService;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategica;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategicaCmdService;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategicaValidator;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.AzioneValidator;
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
@SessionAttributes(types = AreaStrategica.class)
@RequestMapping("/superGabAreaStrat")
public class SuperGabAreaStrategicaController  {
    
	private Log log = LogFactory.getLog(SuperGabAreaStrategicaController.class);
	//
    @Autowired
    private AreaStrategicaCmdService areaStratCmdServizi;
    @Autowired
    private SuperGabinettoPianificazioneService superGabinettoServizi;
    
    //@Autowired
    //private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    public SuperGabAreaStrategicaController() { }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("idO");
    }
    
    //
 // ---------------- CASI D'USO  ----------------------------
    
    
//  
    //
    @RequestMapping(value="newAreaStrat", method = RequestMethod.GET)
    public String createAreaStrategicaGet(Model model) {
    	
        //
    	AreaStrategica areaStrategica = new AreaStrategica();
        areaStrategica.setAnno(menu.getAnno());
        //areaStrategica.setCodiceAss(menu.getAssessorato().getCodice());
        model.addAttribute(areaStrategica);
        //String view = "geko/programmazione/supergab/areaStrategica/formAreaStrategicaCreaSuperGabinetto";
        String view = "geko2019/pianificazione/supergab/areaStrategica/formAreaStrategicaCreaSuperGabinetto";
        
        return view;
        
    }
    //
    @RequestMapping(value="newAreaStrat", params = "add", method = RequestMethod.POST)
    public String createAreaStrategicaPost(@ModelAttribute AreaStrategica areaStrategica,
                                BindingResult result, 
                                SessionStatus status) {
        //
		new AreaStrategicaValidator().validate(areaStrategica, result);
		//
        if (result.hasErrors()) {
        	String view = "gek2019o/pianificazione/supergab/areaStrategica/formAreaStrategicaCreaSuperGabinetto";
            return view;
        }	
		else {
	            Evento event = this.superGabinettoServizi.cmdSuperGabinettoCreateAreaStrategica(areaStrategica);
	            if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
	            	status.setComplete();
		            return "redirect:/superGabPianificazione/modifyPianificazioneTriennaleSuperGab/"+menu.getAnno();  
		            
	    			}
	    		else return "redirect:/geko/programmazione/supergab/errCmd/"+ event.getId();  
	           }
    }
    
    //
    @RequestMapping(value="newAreaStrat", params = "cancel", method = RequestMethod.POST)
    public String createAreaStrategicaCancel(@ModelAttribute AreaStrategica areaStrategica,
            BindingResult result, 
            SessionStatus status) {
    	status.setComplete();
		return "redirect:/ROLE_SUPERGABINETTO"; 	
    }
    
 // azione generica su cui aggiungere descrizione proposta o azione di obiettivo proposto in cui mettere tutto
    @RequestMapping(value ="{id}/edit" , method = RequestMethod.GET )
    public String updateForm(@PathVariable("id") int id, Model model) {
    	AreaStrategica areaStrategica = areaStratCmdServizi.findById(id);
    	//if (areaStrategica.getCodiceAss() == null) areaStrategica.setCodiceAss(menu.getAssessorato().getCodice());
        //
    	model.addAttribute(areaStrategica);
    	String view = "geko2019/pianificazione/supergab/areaStrategica/formAreaStrategicaUpdateSuperGabinetto";
        return view; 
	}
    //
    @RequestMapping(value ="{id}/edit" , params = "update", method =  RequestMethod.POST )
    public String processUpdateSubmit(@ModelAttribute AreaStrategica areaStrategica, 
                                BindingResult result, 
                                SessionStatus status) {
    	new AreaStrategicaValidator().validate(areaStrategica, result);
		if (result.hasErrors()) {
			String view = "geko2019/pianificazione/supergab/areaStrategica/formAreaStrategicaUpdateSuperGabinetto";
	        return view; 
		}
		else {
			Evento event = this.superGabinettoServizi.cmdSuperGabinettoUpdateAreaStrategica(areaStrategica);
            if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
            	status.setComplete();
            	return "redirect:/superGabPianificazione/modifyPianificazioneTriennaleSuperGab/"+menu.getAnno();
	            
    			}
    		else return "redirect:/geko/programmazione/supergab/errCmd/"+ event.getId();  
        }
	}
    

    @RequestMapping(value ="{id}/edit" , params = "delete", method =  RequestMethod.POST)
    public String processDeleteSubmit(@ModelAttribute AreaStrategica areaStrategica, 
                                BindingResult result, 
                                SessionStatus status) {
    	Evento event = this.superGabinettoServizi.cmdSuperGabinettoDeleteAreaStrategica(areaStrategica);
    	if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
        	status.setComplete();
        	return "redirect:/superGabPianificazione/modifyPianificazioneTriennaleSuperGab/"+menu.getAnno(); 
            
			}
		else return "redirect:/geko/programmazione/supergab/errCmd/"+ event.getId();   
    }
 
    
    @RequestMapping(value ="{id}/edit" , params = "cancel", method =  RequestMethod.POST )
    public String processCancelSubmit(@ModelAttribute AreaStrategica areaStrategica, 
                                BindingResult result, 
                                SessionStatus status) {
    	return "redirect:/ROLE_SUPERGABINETTO";
	
    }
 
    
} // --------------------------------------------------------------
