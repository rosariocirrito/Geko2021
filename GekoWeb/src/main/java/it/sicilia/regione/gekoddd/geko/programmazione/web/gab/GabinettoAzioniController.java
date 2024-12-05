/**
 * GabinettoAzioniController.java è la classe controller per gestire le azioni
 * da parte dell'utente con ROLE_GABINETTO
 */

package it.sicilia.regione.gekoddd.geko.programmazione.web.gab;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.application.ControllerProgrammazioneService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.AzioneCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.AzioneValidator;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione.CompletamentoEnum;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoCmdService;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;


@Controller
@SessionAttributes(types = Azione.class)
@RequestMapping("/gabinettoAct")
public class GabinettoAzioniController  {
    
	private Log log = LogFactory.getLog(GabinettoAzioniController.class);
	//
	@Autowired
    private ObiettivoCmdService objCmdServizi;
    @Autowired
    private AzioneCmdService actCmdServizi;
    @Autowired
    private ControllerProgrammazioneService controllerServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    public GabinettoAzioniController() { }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("idAzione");
    }
    
   
    // crea azione su obiettivo proposto
    @RequestMapping(value="new/{idObiettivo}", method = RequestMethod.GET)
    public String createAzioneController(@PathVariable("idObiettivo") int idObiettivo,Model model) {
                
        Obiettivo obiettivo = objCmdServizi.findById(idObiettivo);
        Azione azione = new Azione();
        azione.setObiettivo(obiettivo);
        azione.setCompletamento(CompletamentoEnum.DA_VALUTARE);
        obiettivo.addAzioneToObiettivo(azione);
        model.addAttribute("azione", azione);
 
        return "geko/programmazione/gab/formAzioneCreateGabinetto";       
    }
    
    
    
 // crea azione su obiettivo proposto
    @RequestMapping(value="newApicaleDiretta/{idObiettivo}", method = RequestMethod.GET)
    public String createAzioneApicaleDirettaControllerGet(@PathVariable("idObiettivo") int idObiettivo,Model model) {
        
        //if (null!=userName) personaFisica = userservizi.getPersonaFisicaByUserName(userName);
        //if (null!=personaFisica){  
            Obiettivo obiettivo = objCmdServizi.findById(idObiettivo);
            Azione azione = new Azione();
            azione.setObiettivo(obiettivo);
            azione.setCompletamento(CompletamentoEnum.DA_VALUTARE);
            obiettivo.addAzioneToObiettivo(azione);
            model.addAttribute("azione", azione);
  
        //}
        return "geko/programmazione/gab/formAzioneCreateApicaleDirettaGabinetto";
        
    }
    
    @RequestMapping(value="newApicaleDiretta/{idObiettivo}", method = RequestMethod.POST)
    public String createAzioneApicaleDirettaControllerPost(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
        //
	new AzioneValidator().validate(azione, result);
	//
        if (result.hasErrors()) {
            return "geko/programmazione/gab/formAzioneCreateApicaleDirettaGabinetto";
        }	
	else {
        this.controllerServizi.controllerCreateAzione(azione);
        status.setComplete();
        return "redirect:/gabProg/modifyPianificazioneApicaleIncaricoGab/"+
		azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();
	}
    }
    
 // ---------------------------------------------- Apicale -------------------
    @RequestMapping(value ="{idAzione}/editApicaleDiretta" , method = RequestMethod.GET )
    public String updateApicaleForm(@PathVariable("idAzione") int idAzione, Model model) {
    	//log.info("idAzione}/editApicaleDiretta id Azione: "+idAzione);
    	
    	Azione azione = actCmdServizi.findById(idAzione);
    	log.info("idAzione}/editApicaleDiretta id Azione: "+idAzione+" - idIncarico: "+azione.getIdIncarico());
        //
        model.addAttribute("azione", azione);
        return "geko/programmazione/gab/formAzioneEditApicaleDirettaGabinetto";
	}
    //
    @RequestMapping(value ="{idAzione}/editApicaleDiretta" , params = "update", method =  RequestMethod.PUT )
    public String processApicaleUpdateSubmit(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new AzioneValidator().validate(azione, result);
		if (result.hasErrors()) {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
	            return "geko/programmazione/gab/formAzioneEditApicaleDirettaGabinetto";
		}
		else {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
	            this.controllerServizi.controllerUpdateAzioneApicale(azione);
	            status.setComplete();
	            //return "redirect:/gab/modifyPianificazioneApicaleDipartimentoController/"+azione.getObiettivo().getAnno(); 
	            return "redirect:/gabProg/modifyPianificazioneApicaleIncaricoGab/"+
	            azione.getObiettivo().getAnno()+"/"+azione.getIdIncarico();      
		}
    }
    
    @RequestMapping(value ="{idAzione}/editApicaleDiretta" , params = "delete", method =  RequestMethod.PUT)
    public String processApicaleDeleteSubmit(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
        this.controllerServizi.controllerDeleteAzione(azione);
        return "redirect:/gabProg/modifyPianificazioneApicaleIncaricoGab/"+
        		azione.getObiettivo().getAnno()+"/"+azione.getIdIncarico();    
        
    }
    
    @RequestMapping(value ="{idAzione}/editApicaleDiretta/{idIncaricoApicale}" , params = "cancel", method =  RequestMethod.PUT )
    public String processApicaleCancelSubmit(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
    	return "redirect:/gabProg/modifyPianificazioneApicaleIncaricoGab/"+
        azione.getObiettivo().getAnno()+"/"+azione.getIdIncarico();  
    }
    
    
 // rendi tassativa
    @RequestMapping(value ="{idAzione}/rendiTassativa" , method = RequestMethod.GET )
    public String rendiTassativaGet(@PathVariable("idAzione") int idAzione, Model model) {
        Azione azione = actCmdServizi.findById(idAzione);
        //
        model.addAttribute("azione", azione);
	    return "geko/programmazione/gab/formAzioneRendiTassativaGabinetto";   
	}
    //
    @RequestMapping(value ="{idAzione}/rendiTassativa" , params = "update", method =  RequestMethod.PUT )
    public String rendiTassativaPut(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new AzioneValidator().validate(azione, result);
		if (result.hasErrors()) {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
	            return "geko/programmazione/gab/formAzioneRendiTassativaGabinetto";
		}
		else {
            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
			azione.setTassativa(true);
            this.controllerServizi.controllerUpdateAzione(azione);
            status.setComplete();
            return "redirect:/gabProg/modifyPianificazioneApicaleIncaricoGab/"+
    		azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();
		}
    }

    @RequestMapping(value ="{idAzione}/rendiTassativa" , params = "cancel", method =  RequestMethod.PUT )
    public String rendiTassativaCancel(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
    	return "redirect:/ROLE_GABINETTO";	
    }
    
    // togli tassativa
    @RequestMapping(value ="{idAzione}/togliTassativa" , method = RequestMethod.GET )
    public String togliTassativaGet(@PathVariable("idAzione") int idAzione, Model model) {
        Azione azione = actCmdServizi.findById(idAzione);
        //
        model.addAttribute("azione", azione);
	    return "geko/programmazione/gab/formAzioneTogliTassativaGabinetto";   
	}
    //
    @RequestMapping(value ="{idAzione}/togliTassativa" , params = "update", method =  RequestMethod.PUT )
    public String togliTassativaPut(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new AzioneValidator().validate(azione, result);
		if (result.hasErrors()) {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
	            return "geko/programmazione/gab/formAzioneRendiTassativaGabinetto";
		}
		else {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
			azione.setTassativa(false);    
			this.controllerServizi.controllerUpdateAzione(azione);
            status.setComplete();
            return "redirect:/gabProg/modifyPianificazioneApicaleIncaricoGab/"+
    		azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();
		}
    }

    @RequestMapping(value ="{idAzione}/togliTassativa" , params = "cancel", method =  RequestMethod.PUT )
    public String togliTassativaCancel(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
    	return "redirect:/ROLE_GABINETTO";	
    }
    
} // -------------------------------------------------------------
