/**
 * 
 */
package it.sicilia.regione.gekoddd.geko.pianificazione.web.supergab;

import it.sicilia.regione.gekoddd.geko.pianificazione.application.SuperGabinettoPianificazioneService;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategica;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategicaCmdService;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategico;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategicoCmdService;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategicoValidator;
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
@SessionAttributes(types = ObiettivoStrategico.class)
@RequestMapping("/superGabObiettivoStrategico")
public class SuperGabObiettivoStrategicoController  {
    
	private Log log = LogFactory.getLog(SuperGabObiettivoStrategicoController.class);
	//
	@Autowired
    private AreaStrategicaCmdService areaStrategicaCmdServizi;
    @Autowired
    private ObiettivoStrategicoCmdService obiettivoStrategicoCmdServizi;
    @Autowired
    private SuperGabinettoPianificazioneService superGabinettoServizi;
    
    //@Autowired
    //private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    public SuperGabObiettivoStrategicoController() { }
    
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
    @RequestMapping(value="newObiettivoStrategico/{idArea}", method = RequestMethod.GET)
    public String createObiettivoStrategicoGet(@PathVariable("idArea") int idArea, Model model) {
    	AreaStrategica areaStrategica = areaStrategicaCmdServizi.findById(idArea);
    	log.info("createObiettivoStrategicoGet");
        //
    	ObiettivoStrategico obiettivoStrategico = new ObiettivoStrategico();
    	obiettivoStrategico.setAreaStrategica(areaStrategica);
    	obiettivoStrategico.setAnno(menu.getAnno());
        model.addAttribute(obiettivoStrategico);
        String view = "geko2019/pianificazione/supergab/obiettivoStrategico/formObiettivoStrategicoCreaSuperGabinetto";
        return view;
        
    }
    //
    @RequestMapping(value="newObiettivoStrategico/{idArea}", params = "add", method = RequestMethod.POST)
    public String creatObiettivoStrategicoPost(@ModelAttribute ObiettivoStrategico obiettivoStrategico,
                                BindingResult result, 
                                SessionStatus status) {
        //
		new ObiettivoStrategicoValidator().validate(obiettivoStrategico, result);
		//
        if (result.hasErrors()) {
        	String view = "geko/pianificazione/supergab/obiettivoStrategico/formObiettivoStrategicoCreaSuperGabinetto";
            return view;
        }	
		else {
			    //log.info("createObiettivoStrategicoPost con id="+obiettivoStrategico.getId());
	            Evento event = this.superGabinettoServizi.cmdSuperGabinettoCreateObiettivoStrategico(obiettivoStrategico);
	            if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
	            	status.setComplete();
	            	return "redirect:/superGabPianificazione/modifyDirettivaSuperGab/"+menu.getAnno(); 
		            
	    			}
	    			
	    		else return "redirect:/geko/programmazione/supergab/errCmd/"+ event.getId();  
	           }
    }
    
    //
    @RequestMapping(value="newObiettivoStrategico/{idArea}", params = "cancel", method = RequestMethod.POST)
    public String createObiettivoStrategicoCancel(@ModelAttribute ObiettivoStrategico obiettivoStrategico,
            BindingResult result, 
            SessionStatus status) {
    	status.setComplete();
		return "redirect:/ROLE_SUPERGABINETTO"; 	
    }
    
    
    
    
 // azione generica su cui aggiungere descrizione proposta o azione di obiettivo proposto in cui mettere tutto
    @RequestMapping(value ="{id}/edit" , method = RequestMethod.GET )
    public String updateForm(@PathVariable("id") int id, Model model) {
    	ObiettivoStrategico obiettivoStrategico = obiettivoStrategicoCmdServizi.findById(id);
    	 //
    	model.addAttribute(obiettivoStrategico);
    	String view = "geko2019/pianificazione/supergab/obiettivoStrategico/formObiettivoStrategicoUpdateSuperGabinetto";
        return view; 
	}
    //
    @RequestMapping(value ="{id}/edit" , params = "update", method =  RequestMethod.PUT )
    public String processUpdateSubmit(@ModelAttribute ObiettivoStrategico obiettivoStrategico, 
                                BindingResult result, 
                                SessionStatus status) {
    	new ObiettivoStrategicoValidator().validate(obiettivoStrategico, result);
		if (result.hasErrors()) {
			String view = "geko/pianificazione/supergab/obiettivoStrategico/formObiettivoStrategicoUpdateSuperGabinetto";
	        return view; 
		}
		else {
			Evento event = this.superGabinettoServizi.cmdSuperGabinettoUpdateObiettivoStrategico(obiettivoStrategico);
            if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
            	status.setComplete();
            	return "redirect:/superGabPianificazione/modifyDirettivaSuperGab/"+menu.getAnno();
	            
    			}
    		else return "redirect:/geko/programmazione/supergab/errCmd/"+ event.getId();  
        }
	}
    

    @RequestMapping(value ="{id}/edit" , params = "delete", method =  RequestMethod.PUT)
    public String processDeleteSubmit(@ModelAttribute ObiettivoStrategico obiettivoStrategico, 
                                BindingResult result, 
                                SessionStatus status) {
    	Evento event = this.superGabinettoServizi.cmdSuperGabinettoDeleteObiettivoStrategico(obiettivoStrategico);
    	if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
        	status.setComplete();
        	return "redirect:/superGabPianificazione/modifyPianificazioneTriennaleSuperGab/"+menu.getAnno();
            
			}
		else return "redirect:/geko/programmazione/supergab/errCmd/"+ event.getId();   
    }
 
    
    @RequestMapping(value ="{id}/edit" , params = "cancel", method =  RequestMethod.PUT )
    public String processCancelSubmit(@ModelAttribute ObiettivoStrategico obiettivoStrategico, 
                                BindingResult result, 
                                SessionStatus status) {
    	return "redirect:/ROLE_SUPERGABINETTO";
	
    }
 
    
} // --------------------------------------------------------------
