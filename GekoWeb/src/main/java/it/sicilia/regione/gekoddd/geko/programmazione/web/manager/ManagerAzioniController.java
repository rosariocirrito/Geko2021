/**
 * 
 */
package it.sicilia.regione.gekoddd.geko.programmazione.web.manager;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.application.ManagerProgrammazioneService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.AzioneCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.AzioneValidator;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione.CompletamentoEnum;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoCmdService;
import it.sicilia.regione.gekoddd.geko.valutazione.application.ManagerValutazioneService;
import it.sicilia.regione.gekoddd.log.model.evento.Evento;
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

/**
 * @author Cirrito
 *
 */

@Controller
@SessionAttributes(types = Azione.class)
@RequestMapping("/dirigenteAct")
public class ManagerAzioniController  {
    
	private static final Logger log = LoggerFactory.getLogger(ManagerAzioniController.class);
	//
	@Autowired
    private ObiettivoCmdService objCmdServizi;
    @Autowired
    private AzioneCmdService actCmdServizi;
    @Autowired
    private ManagerProgrammazioneService managerServizi;
    @Autowired
    private ManagerValutazioneService managerValutServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    public ManagerAzioniController() { }
    
   
    
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
    
    
    
    // ----------------- Edit ----------------------------------
    @RequestMapping(value ="{idAzione}/edit/{idIncarico}" , method = RequestMethod.GET )
    public String updateForm(@PathVariable("idAzione") int idAzione, @PathVariable("idIncarico") int idIncarico,
    		Model model) {
        Azione azione = actCmdServizi.findById(idAzione);
        Obiettivo obj = azione.getObiettivo();
        obj.setIncaricoPadreID(idIncarico);
        //if (menu.getIdIncaricoScelta() == azione.getIncaricoID()){ // la seconda path � inutile
            model.addAttribute("azione", azione);
            return "geko/programmazione/manager/formAzioneEditManager";
        //}
        //else return "redirect:/ROLE_MANAGER";
    }
    
    //
    @RequestMapping(value ="{idAzione}/edit/{idIncarico}" , params = "update", method =  RequestMethod.PUT )
    public String processUpdateSubmit(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
    	
        new AzioneValidator().validate(azione, result);
		if (result.hasErrors()) {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
			return "geko/programmazione/manager/formAzioneEditManager";
		}
		else {		
	        //
			this.managerServizi.managerUpdateAzione(azione);
	        //    
            status.setComplete();
            //return "redirect:/dirigenteObj/" + azione.getObiettivo().getIdObiettivo();
            if( azione.getObiettivo().getTipo().equals(Obiettivo.TipoEnum.DIRIGENZIALE)) {			
	            return "redirect:/dirigente/modifyProgrammazioneIncarico/"
	            + azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();
			} else if( azione.getObiettivo().getTipo().equals(Obiettivo.TipoEnum.STRUTTURA)) {
				return "redirect:/dirigente/modifyObjCompartoProgrammazione/"
			    + azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();	
			} else if( azione.getObiettivo().getTipo().equals(Obiettivo.TipoEnum.POS_ORGAN)) {
				return "redirect:/dirigente/modifyProgrammazionePopIncarico/"
			    + azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoPadreID();	
			} 
			else return "redirect:/ROLE_MANAGER";
		}
    }
    //
    @RequestMapping(value ="{idAzione}/edit/{idIncarico}" , params = "delete", method =  RequestMethod.PUT )
    public String processDeleteSubmit(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
        final Evento event = this.managerServizi.cmdManagerDeleteAzione(azione);
		
        if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
			status.setComplete();
			if( azione.getObiettivo().getTipo().equals(Obiettivo.TipoEnum.DIRIGENZIALE)) {			
	            return "redirect:/dirigente/modifyProgrammazioneIncarico/"
	            + azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();
			} 
			else if( azione.getObiettivo().getTipo().equals(Obiettivo.TipoEnum.POS_ORGAN)) {			
	            return "redirect:/dirigente/modifyProgrammazionePopIncarico/"
	            + azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoPadreID();
			}
			else {
				return "redirect:/dirigente/modifyObjCompartoProgrammazione/"
			    + azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();	
			}
		}
		else return "redirect:/dirigente/errCmdManager/"+ event.getId();  
    }
    
    
    //
    @RequestMapping(value ="{idAzione}/edit/{idIncarico}" , params = "addAssegn", method =  RequestMethod.PUT )
    public String addAssegn(@ModelAttribute Azione azione, 
            BindingResult result, 
            SessionStatus status) {
    	return "redirect:/dirigenteAssegn/new/"+azione.getIdAzione();
    }
    //
    @RequestMapping(value ="{idAzione}/edit/{idIncarico}" , params = "cancel", method =  RequestMethod.PUT )
    public String processCancelSubmit() {
        return "redirect:/ROLE_MANAGER";
    }
    // -------------------- fine Edit
    
    // crea azione
    @RequestMapping(value="new/{idObiettivo}", method = RequestMethod.GET)
    public String createAzioneManager(@PathVariable("idObiettivo") int idObiettivo,Model model) {
        Obiettivo obiettivo = objCmdServizi.findById(idObiettivo);
        Azione azione = new Azione();
        azione.setObiettivo(obiettivo);
        azione.setCompletamento(CompletamentoEnum.DA_VALUTARE);
        obiettivo.addAzioneToObiettivo(azione);
        model.addAttribute("azione", azione);
        //
        return "geko/programmazione/manager/formAzioneCreateManager";
        
    }
    //
    @RequestMapping(value="new/{idObiettivo}", params = "add", method = RequestMethod.POST)
    public String processSubmitObiettivoManager(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
        //
		new AzioneValidator().validate(azione, result);
		//
        if (result.hasErrors()) {
        	return "geko/programmazione/manager/formAzioneCreateManager";
        }	
		else {
	            // this.managerServizi.managerCreateAzione(azione);
			final Evento event = this.managerServizi.cmdManagerCreateAzione(azione);
			if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
				status.setComplete();
				if( azione.getObiettivo().getTipo().equals(Obiettivo.TipoEnum.DIRIGENZIALE)) {			
		            return "redirect:/dirigente/modifyProgrammazioneIncarico/"
		            + azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();
				} else if ( azione.getObiettivo().getTipo().equals(Obiettivo.TipoEnum.STRUTTURA)) {
					return "redirect:/dirigente/modifyObjCompartoProgrammazione/"
				    + azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();	
				}
			}
			return "redirect:/dirigente/errCmdManager/"+ event.getId();  
		}
    } // fine metodo
    //
    @RequestMapping(value="new/{idObiettivo}", params = "cancel",method = RequestMethod.POST)
    public String newCancel() {
    	return "redirect:/ROLE_MANAGER";
    }
   
    // ------ azione pop
 // crea azione
    @RequestMapping(value="new/{idObiettivo}/{idIncaricoPadre}", method = RequestMethod.GET)
    public String createAzionePopGet(@PathVariable("idObiettivo") int idObiettivo,
    		@PathVariable("idIncaricoPadre") int idIncaricoPadre,
    		Model model) {
        Obiettivo obiettivo = objCmdServizi.findById(idObiettivo);
        obiettivo.setIncaricoPadreID(idIncaricoPadre);
        Azione azione = new Azione();
        azione.setObiettivo(obiettivo);
        azione.setCompletamento(CompletamentoEnum.DA_VALUTARE);
        obiettivo.addAzioneToObiettivo(azione);
        model.addAttribute("azione", azione);
        //
        return "geko/programmazione/manager/formAzioneCreateManager";
        
    }
    //
    @RequestMapping(value="new/{idObiettivo}/{idIncaricoPadre}", params = "add", method = RequestMethod.POST)
    public String createAzionePopPost(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
        //
		new AzioneValidator().validate(azione, result);
		//
	    if (result.hasErrors()) {
	        	return "geko/programmazione/manager/formAzioneCreateManager";
	        }	
		else {
	            // this.managerServizi.managerCreateAzione(azione);
			final Evento event = this.managerServizi.cmdManagerCreateAzione(azione);
			if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
				status.setComplete();
				return "redirect:/dirigente/modifyProgrammazionePopIncarico/"
	            + azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoPadreID(); 
			}
			else return "redirect:/dirigente/errCmdManager/"+ event.getId();  
		}
    }
    
    //
    @RequestMapping(value="new/{idObiettivo}/{idIncaricoPadre}", params = "cancel",method = RequestMethod.POST)
    public String newpopCancel() {
    	return "redirect:/ROLE_MANAGER";
    }
    
    
    
    // ------------- Risultato
    @RequestMapping(value ="{idAzione}/editRisultato/{idIncarico}" , method = RequestMethod.GET )
    public String updateRisultatoForm(@PathVariable("idAzione") int idAzione, @PathVariable("idIncarico") int idIncarico,
    		Model model) {
        Azione azione = actCmdServizi.findById(idAzione);
        azione.setIdIncarico(idIncarico);
        //Map<AzioneAssegnazione,OpPersonaFisica> assegnazioni = servizi.mapAzioneAssegnazioniOfAzione(azione);
        //
        
	            model.addAttribute("azione", azione);
	            //model.addAttribute("assegnazioni", assegnazioni); // da togliere
	            return "geko/rendicontazione/manager/formAzioneRisultatoManager";
	        
	    }
    //
    @RequestMapping(value ="{idAzione}/editRisultato/{idIncarico}" , params = "update", method =  RequestMethod.PUT )
    public String UpdateRisultatoSubmit(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new AzioneValidator().validate(azione, result);
		if (result.hasErrors()) {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
			return "geko/rendicontazione/manager/formAzioneRisultatoManager";
		}
		else {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors per risultato="+azione.getRisultato());
	            this.managerServizi.managerUpdateRisultatoAzione(azione);
	            status.setComplete();
	            return "redirect:/dirigente/modifyRendicontazioneIncaricoManager/"
	            + azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();  
		}
    }
    //
    @RequestMapping(value ="{idAzione}/editRisultato/{idIncarico}" , params = "cancel", method =  RequestMethod.PUT )
    public String CancelRisultatoSubmit() {
        return "redirect:/ROLE_MANAGER";
    }
    
 // rendi tassativa
    @RequestMapping(value ="{idAzione}/rendiTassativa" , method = RequestMethod.GET )
    public String rendiTassativaGet(@PathVariable("idAzione") int idAzione, Model model) {
        Azione azione = actCmdServizi.findById(idAzione);
        //
        model.addAttribute("azione", azione);
	    return "geko/programmazione/manager/formAzioneRendiTassativaManager";   
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
	            return "geko/programmazione/manager/formAzioneRendiTassativaManager";
		}
		else {
            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
			azione.setTassativa(true);
            this.managerServizi.managerUpdateAzione(azione);
            status.setComplete();
            return "redirect:/dirigente/modifyProgrammazioneIncarico/"+
    		azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();
		}
    }

    @RequestMapping(value ="{idAzione}/rendiTassativa" , params = "cancel", method =  RequestMethod.PUT )
    public String rendiTassativaCancel(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
    	return "redirect:/ROLE_MANAGER";	
    }
    
    // togli tassativa
    @RequestMapping(value ="{idAzione}/togliTassativa" , method = RequestMethod.GET )
    public String togliTassativaGet(@PathVariable("idAzione") int idAzione, Model model) {
        Azione azione = actCmdServizi.findById(idAzione);
        //
        model.addAttribute("azione", azione);
	    return "geko/programmazione/manager/formAzioneTogliTassativaManager";   
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
	            return "geko/programmazione/manager/formAzioneRendiTassativaManager";
		}
		else {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
			azione.setTassativa(false);    
			this.managerServizi.managerUpdateAzione(azione);
            status.setComplete();
            return "redirect:/dirigente/modifyProgrammazioneIncarico/"+
    		azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();
		}
    }

    @RequestMapping(value ="{idAzione}/togliTassativa" , params = "cancel", method =  RequestMethod.PUT )
    public String togliTassativaCancel(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
    	return "redirect:/ROLE_MANAGER";	
    }
    
 // ---------------------------
    @RequestMapping(value ="{idAzione}/editCompletamento" , method = RequestMethod.GET )
    public String editCompletamentoGet(@PathVariable("idAzione") int idAzione, Model model) {
        Azione azione = actCmdServizi.findById(idAzione);
        
        model.addAttribute("azione", azione);
        return "geko/valutazione/manager/formAzioneCompletamentoManager";
	    
	}
    
    //
    @RequestMapping(value ="{idAzione}/editCompletamento" , params = "update", method =  RequestMethod.PUT )
    public String editCompletamentoPut(@ModelAttribute Azione azione, 
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
	            this.managerValutServizi.managerUpdateCompletamentoAzione(azione);
	            status.setComplete();
	            return "redirect:/ROLE_MANAGER";
		}
    }
    
    //
    @RequestMapping(value ="{idAzione}/editCompletamento" , params = "cancel", method =  RequestMethod.PUT )
    public String editCompletamentoCancel() {
        return "redirect:/ROLE_MANAGER";
    }
    
} // ---------------------------------------
