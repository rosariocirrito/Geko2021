/**
 * 
 */
package it.sicilia.regione.gekoddd.geko.programmazione.web.resppop;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.application.ManagerProgrammazioneService;
import it.sicilia.regione.gekoddd.geko.programmazione.application.RespPopProgrammazioneService;
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
@RequestMapping("/resppopAct")
public class ResppopAzioniController  {
    
	private static final Logger log = LoggerFactory.getLogger(ResppopAzioniController.class);
	//
	@Autowired
    private ObiettivoCmdService objCmdServizi;
    @Autowired
    private AzioneCmdService actCmdServizi;
    @Autowired
    private RespPopProgrammazioneService respPopServizi;
    
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    public ResppopAzioniController() { }
    
   
    
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
        azione.setIncaricoPadreID(idIncarico);
        //if (menu.getIdIncaricoScelta() == azione.getIncaricoID()){ // la seconda path � inutile
            model.addAttribute("azione", azione);
            return "geko/programmazione/resppop/formAzioneEditPop";
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
			return "geko/programmazione/resppop/formAzioneEditPop";
		}
		else {		
	        //
			this.respPopServizi.popUpdateAzione(azione);
	        //    
            status.setComplete();
            //return "redirect:/dirigenteObj/" + azione.getObiettivo().getIdObiettivo();
            return "redirect:/respPop/modifyProgrammazionePopIncarico/"
			    + azione.getObiettivo().getAnno()+"/"+azione.getIncaricoID();	
			
		}
    }
    //
    @RequestMapping(value ="{idAzione}/edit/{idIncarico}" , params = "delete", method =  RequestMethod.PUT )
    public String processDeleteSubmit(@ModelAttribute Azione azione, 
                                BindingResult result, 
                                SessionStatus status) {
        final Evento event = this.respPopServizi.cmdPopDeleteAzione(azione);
		
        if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
			status.setComplete();
			return "redirect:/respPop/modifyProgrammazionePopIncarico/"
		    + azione.getObiettivo().getAnno()+"/"+azione.getIncaricoID();	
		}
		else return "redirect:/dirigente/errCmdManager/"+ event.getId();  
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
        return "geko/programmazione/resppop/formAzioneCreatePop";
        
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
	        	return "geko/programmazione/resppop/formAzioneCreatePop";
	        }	
		else {
	            // this.managerServizi.managerCreateAzione(azione);
			final Evento event = this.respPopServizi.cmdPopCreateAzione(azione);
			if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
				status.setComplete();
				return "redirect:/respPop/modifyProgrammazionePopIncarico/"
	            + azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID(); 
				}
			else return "redirect:/dirigente/errCmdManager/"+ event.getId();  
		}
    }
    //
    @RequestMapping(value="new/{idObiettivo}", params = "cancel",method = RequestMethod.POST)
    public String newCancel() {
    	return "redirect:/ROLE_RESP_POP";
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
	            return "geko/rendicontazione/resppop/formAzioneRisultatoPop";
	        
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
	            this.respPopServizi.popUpdateRisultatoAzione(azione);
	            status.setComplete();
	            return "redirect:/respPop/modifyRendicontazionePopIncarico/"
	            + azione.getObiettivo().getAnno()+"/"+azione.getObiettivo().getIncaricoID();  
		}
    }
    //
    @RequestMapping(value ="{idAzione}/editRisultato/{idIncarico}" , params = "cancel", method =  RequestMethod.PUT )
    public String CancelRisultatoSubmit() {
        return "redirect:/ROLE_RESP_POP";
    }
    
    
} // ---------------------------------------
