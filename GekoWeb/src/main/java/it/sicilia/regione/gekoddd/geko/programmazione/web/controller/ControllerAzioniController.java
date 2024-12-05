/**
 * 
 */
package it.sicilia.regione.gekoddd.geko.programmazione.web.controller;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.application.ControllerProgrammazioneService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.AzioneCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.AzioneValidator;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione.CompletamentoEnum;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoCmdService;
import it.sicilia.regione.gekoddd.log.model.evento.Evento;
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

/**
 * @author Cirrito
 *
 */

@Controller
@SessionAttributes(types = Azione.class)
@RequestMapping("/controllerAct")
public class ControllerAzioniController  {
    
	private Log log = LogFactory.getLog(ControllerAzioniController.class);
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
    
    public ControllerAzioniController() { }
    
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
        
        //if (null!=userName) personaFisica = userservizi.getPersonaFisicaByUserName(userName);
        //if (null!=personaFisica){  
            Obiettivo obiettivo = objCmdServizi.findById(idObiettivo);
            Azione azione = new Azione();
            azione.setObiettivo(obiettivo);
            azione.setCompletamento(CompletamentoEnum.DA_VALUTARE);
            obiettivo.addAzioneToObiettivo(azione);
            model.addAttribute("azione", azione);
  
        //}
        return "geko/programmazione/controller/formAzioneCreateController";
        
    }
    
    @RequestMapping(value="new/{idObiettivo}", method = RequestMethod.POST)
    public String processSubmitObiettivoController(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
        //
	new AzioneValidator().validate(azione, result);
	//
        if (result.hasErrors()) {
            return "geko/programmazione/controller/formAzioneCreateController";
        }	
	else {
        this.controllerServizi.controllerCreateAzione(azione);
        status.setComplete();
        return "redirect:/controller/modifyPianificazioneIncaricoController/"+
		azione.getObiettivo().getAnno()+"/"+azione.getIdIncarico();
	}
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
        return "geko/programmazione/controller/formAzioneCreateApicaleDirettaController";
        
    }
    
    @RequestMapping(value="newApicaleDiretta/{idObiettivo}", method = RequestMethod.POST)
    public String createAzioneApicaleDirettaControllerPost(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
        //
	new AzioneValidator().validate(azione, result);
	//
        if (result.hasErrors()) {
            return "geko/programmazione/controller/formAzioneCreateApicaleDirettaController";
        }	
	else {
        Evento event = this.controllerServizi.cmdControllerCreaAzioneApicale(azione);
        if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
				status.setComplete();
				return "redirect:/controller/modifyPianificazioneApicaleDirettaController/"+
				azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();
				}
			else return "redirect:/controller/errCmdController/"+ event.getId();  
           }
    }
    
    
   // ---------------------------
    @RequestMapping(value ="{idAzione}/editCompletamento" , method = RequestMethod.GET )
    public String editCompletamentoGet(@PathVariable("idAzione") int idAzione, Model model) {
        Azione azione = actCmdServizi.findById(idAzione);
        
        model.addAttribute("azione", azione);
        return "geko/valutazione/controller/formAzioneCompletamentoController";
	    
	}
    //
    @RequestMapping(value ="{idAzione}/editCompletamento" , params = "update", method =  RequestMethod.PUT )
    public String UpdateRisultatoSubmit(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new AzioneValidator().validate(azione, result);
		if (result.hasErrors()) {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
	            return "geko/valutazione/controller/formAzioneCompletamentoController";
		}
		else {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
	            this.controllerServizi.controllerUpdateCompletamentoAzione(azione);
	            status.setComplete();
	            return "redirect:/controllerValut/modifyValutazioneIncaricoController/"+
	    		azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();
		}
    }
    
    //
    @RequestMapping(value ="{idAzione}/editCompletamento" , params = "cancel", method =  RequestMethod.PUT )
    public String CancelRisultatoSubmit() {
        return "redirect:/ROLE_CONTROLLER";
    }
    
  
    
    
    // ------------- Risultato
    @RequestMapping(value ="{idAzione}/editRisultatoApicale" , method = RequestMethod.GET )
    public String editRisultatoGet(@PathVariable("idAzione") int idAzione, Model model) {
        Azione azione = actCmdServizi.findById(idAzione);
          
        log.info("{idAzione}/editRisultatoApicale id Azione: "+idAzione+" - idIncarico: "+azione.getIdIncarico());
        //
        if (azione.getIdIncarico() == menu.getIncaricoApicaleDept().idIncarico){
                 model.addAttribute("azione", azione);
	            //model.addAttribute("assegnazioni", assegnazioni); // da togliere
	            return "geko/programmazione/controller/formAzioneRisultatoApicaleController";
	        }
	        else return "redirect:/ROLE_CONTROLLER";
	    }
    //
    @RequestMapping(value ="{idAzione}/editRisultatoApicale" , params = "update", method =  RequestMethod.PUT )
    public String editRisultatoUpdatePut(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new AzioneValidator().validate(azione, result);
		if (result.hasErrors()) {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
			return "geko/programmazione/controller/formAzioneRisultatoApicaleController";
		}
		else {
				log.info("{idAzione}/editRisultatoApicale PUT");
				this.controllerServizi.controllerUpdateRisultatoApicaleAzione(azione);
	            status.setComplete();
	            return "redirect:/controllerRend/modifyRendicontazioneIncaricoApicaleController/"+ azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID(); 
		}
    }
    //
    @RequestMapping(value ="{idAzione}/editRisultatoApicale" , params = "cancel", method =  RequestMethod.PUT )
    public String editRisultatoCancelPut() {
        return "redirect:/ROLE_CONTROLLER";
    }
    
    
    
 // ---------------------------
    @RequestMapping(value ="{idAzione}/editPeso" , method = RequestMethod.GET )
    public String updatePesoForm(@PathVariable("idAzione") int idAzione, Model model) {
        Azione azione = actCmdServizi.findById(idAzione);
        
            model.addAttribute("azione", azione);
            return "geko/programmazione/controller/formAzionePesoController";
	    
	}
    //
    @RequestMapping(value ="{idAzione}/editPeso" , params = "update", method =  RequestMethod.PUT )
    public String UpdatePesoSubmit(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new AzioneValidator().validate(azione, result);
		if (result.hasErrors()) {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
	            return "geko/programmazione/controller/formAzionePesoController";
		}
		else {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
	            this.controllerServizi.controllerUpdatePesoAzione(azione);
	            status.setComplete();
	            return "redirect:/controller/modifyProgrammazioneIncaricoController/"+
	    		azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();
		}
    }
    
    //
    @RequestMapping(value ="{idAzione}/editPeso" , params = "cancel", method =  RequestMethod.PUT )
    public String CancelPesoSubmit() {
        return "redirect:/ROLE_CONTROLLER";
    }
    
    
    // azione generica su cui aggiungere descrizione proposta o azione di obiettivo proposto in cui mettere tutto
    @RequestMapping(value ="{idAzione}/edit" , method = RequestMethod.GET )
    public String updateForm(@PathVariable("idAzione") int idAzione, Model model) {
        Azione azione = actCmdServizi.findById(idAzione);
        //
        model.addAttribute("azione", azione);
	    return "geko/programmazione/controller/formAzioneEditController";   
	}
    //
    @RequestMapping(value ="{idAzione}/edit" , params = "update", method =  RequestMethod.PUT )
    public String processUpdateSubmit(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new AzioneValidator().validate(azione, result);
		if (result.hasErrors()) {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
	            return "geko/programmazione/controller/formAzioneEditController";
		}
		else {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
	            this.controllerServizi.controllerUpdateAzione(azione);
	            status.setComplete();
	            return "redirect:/controller/modifyPianificazioneIncaricoController/"+
        		azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();
		}
    }

    @RequestMapping(value ="{idAzione}/edit" , params = "delete", method =  RequestMethod.PUT)
    public String processDeleteSubmit(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
        this.controllerServizi.controllerDeleteAzione(azione);
        return "redirect:/controller/modifyPianificazioneIncaricoController/"+
		azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();
    }
 
    
    @RequestMapping(value ="{idAzione}/edit" , params = "cancel", method =  RequestMethod.PUT )
    public String processCancelSubmit(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
    	return "redirect:/ROLE_CONTROLLER";
	
    }

    // editare scadenza e peso apicale e apicale si o no
    @RequestMapping(value ="{idAzione}/editApicaleSubordinata/{idIncaricoApicale}" , method = RequestMethod.GET )
    public String editApicaleSubordinataGet(@PathVariable("idAzione") int idAzione,@PathVariable("idIncaricoApicale") int idIncaricoApicale, Model model) {
        Azione azione = actCmdServizi.findById(idAzione);
        azione.setIdIncaricoApicale(idIncaricoApicale);
        //
        model.addAttribute("azione", azione);
	    return "geko/programmazione/controller/formAzioneEditApicaleSubordinataController";
	}
    
    //
    @RequestMapping(value ="{idAzione}/editApicaleSubordinata/{idIncaricoApicale}" , params = "update", method =  RequestMethod.PUT )
    public String editApicaleSubordinataPut(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new AzioneValidator().validate(azione, result);
		if (result.hasErrors()) {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
	            return "geko/programmazione/controller/formAzioneEditApicaleSubordinataController";
		}
		else {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
	            this.controllerServizi.controllerUpdateAzioneApicale(azione);
	            status.setComplete();
	            //modifyPianificazioneCompletaIncaricoController/2017/29
	            return "redirect:/controller/modifyPianificazioneApicaleSubordinataController/"+azione.getAnno()+"/"+azione.getIdIncaricoApicale();
		}
    }   
    
    
    @RequestMapping(value ="{idAzione}/editApicaleSubordinata/{idIncaricoApicale}" , params = "cancel", method =  RequestMethod.PUT )
    public String editApicaleSubordinataCancel(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
    	return "redirect:/ROLE_CONTROLLER";
	
    }

 // ---------------------------------------------- Apicale -------------------
    @RequestMapping(value ="{idAzione}/editApicaleDiretta" , method = RequestMethod.GET )
    public String updateApicaleForm(@PathVariable("idAzione") int idAzione, Model model) {
    	//log.info("idAzione}/editApicaleDiretta id Azione: "+idAzione);
    	
    	Azione azione = actCmdServizi.findById(idAzione);
    	log.info("idAzione}/editApicaleDiretta id Azione: "+idAzione+" - idIncarico: "+azione.getIdIncarico());
        //
	            model.addAttribute("azione", azione);
	            return "geko/programmazione/controller/formAzioneEditApicaleDirettaController";
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
	            return "geko/programmazione/controller/formAzioneEditApicaleDirettaController";
		}
		else {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
	            this.controllerServizi.controllerUpdateAzioneApicale(azione);
	            status.setComplete();
	            return "redirect:/controller/modifyPianificazioneApicaleDirettaController/"+
				azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();
		}
    }
    
    @RequestMapping(value ="{idAzione}/editApicaleDiretta" , params = "delete", method =  RequestMethod.PUT)
    public String processApicaleDeleteSubmit(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
        this.controllerServizi.controllerDeleteAzione(azione);
        return "redirect:/controller/modifyPianificazioneApicaleDirettaController/"+azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();
        
    }
    
    @RequestMapping(value ="{idAzione}/editApicaleDiretta/{idIncaricoApicale}" , params = "cancel", method =  RequestMethod.PUT )
    public String processApicaleCancelSubmit(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
    	return "redirect:/controller/modifyPianificazioneApicaleDirettaController/"+azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();  
    }
    
    @RequestMapping(value ="{idAzione}/editRichiesto/{idIncarico}" , method = RequestMethod.GET )
    public String updateRichiestoForm(@PathVariable("idAzione") int idAzione, Model model) {
        Azione azione = actCmdServizi.findById(idAzione);
        //
        
        model.addAttribute("azione", azione);
        //model.addAttribute("assegnazioni", assegnazioni);
        return "geko/programmazione/controller/formAzioneEditRichiestoController";
	   
	}
    //
    @RequestMapping(value ="{idAzione}/editRichiesto" , params = "update", method =  RequestMethod.PUT )
    public String processUpdateRichiestoSubmit(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new AzioneValidator().validate(azione, result);
		if (result.hasErrors()) {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
	            return "geko/programmazione/controller/formAzioneEditRichiestoController";
		}
		else {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
	            this.controllerServizi.controllerUpdateAzione(azione);
	            status.setComplete();
	            return "redirect:/controller/modifyPianificazioneIncaricoController/"+
	    		azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();
		}
    }
    
    @RequestMapping(value ="{idAzione}/editRichiesto" , params = "delete", method =  RequestMethod.PUT)
    public String processRichiestoDeleteSubmit(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
        this.controllerServizi.controllerDeleteAzione(azione);
        return "redirect:/controller/modifyPianificazioneIncaricoController/"+
		azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();
    }
    
    
    
    @RequestMapping(value ="{idAzione}/editRichiesto" , params = "cancel", method =  RequestMethod.PUT )
    public String processRichiestoCancelSubmit(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
    	return "redirect:/controller/modifyPianificazioneIncaricoController/"+
	    		azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();
	
    }

    // rendi tassativa
    @RequestMapping(value ="{idAzione}/rendiTassativa" , method = RequestMethod.GET )
    public String rendiTassativaGet(@PathVariable("idAzione") int idAzione, Model model) {
        Azione azione = actCmdServizi.findById(idAzione);
        //
        model.addAttribute("azione", azione);
	    return "geko/programmazione/controller/formAzioneRendiTassativaController";   
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
	            return "geko/programmazione/controller/formAzioneRendiTassativaController";
		}
		else {
            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
			azione.setTassativa(true);
            this.controllerServizi.controllerUpdateAzione(azione);
            status.setComplete();
            return "redirect:/controller/modifyPianificazioneIncaricoController/"+
    		azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();
		}
    }

    @RequestMapping(value ="{idAzione}/rendiTassativa" , params = "cancel", method =  RequestMethod.PUT )
    public String rendiTassativaCancel(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
    	return "redirect:/ROLE_CONTROLLER";	
    }
    
    // togli tassativa
    @RequestMapping(value ="{idAzione}/togliTassativa" , method = RequestMethod.GET )
    public String togliTassativaGet(@PathVariable("idAzione") int idAzione, Model model) {
        Azione azione = actCmdServizi.findById(idAzione);
        //
        model.addAttribute("azione", azione);
	    return "geko/programmazione/controller/formAzioneTogliTassativaController";   
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
	            return "geko/programmazione/controller/formAzioneRendiTassativaController";
		}
		else {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
			azione.setTassativa(false);    
			this.controllerServizi.controllerUpdateAzione(azione);
            status.setComplete();
            return "redirect:/controller/modifyPianificazioneIncaricoController/"+
    		azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();
		}
    }

    @RequestMapping(value ="{idAzione}/togliTassativa" , params = "cancel", method =  RequestMethod.PUT )
    public String togliTassativaCancel(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
    	return "redirect:/ROLE_CONTROLLER";	
    }
    
} // -------------------------------------------------------------
