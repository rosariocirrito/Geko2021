package it.sicilia.regione.gekoddd.geko.programmazione.web.resppop;



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
import it.sicilia.regione.gekoddd.geko.programmazione.application.RespPopProgrammazioneService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo.TipoEnum;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoValidator;
import it.sicilia.regione.gekoddd.log.model.evento.Evento;
import it.sicilia.regione.gekoddd.session.domain.Menu;

/**
 * @author Cirrito
 *
 */

@Controller
@SessionAttributes(types = Obiettivo.class)
@RequestMapping("/resppopObj")
public class ResppopObiettiviController  {
	
	//
    @Autowired
    private ObiettivoCmdService objCmdServizi;
    @Autowired
    private RespPopProgrammazioneService respPopServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    
    public ResppopObiettiviController() { }
    
    private static final Logger log = LoggerFactory.getLogger(ResppopObiettiviController.class);
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("idObiettivo");
    } 
    
    // 2.- 3. Update/Delete  (se Interlocutorio )----------------------------
    @RequestMapping(value ="{idObiettivo}/edit/{idIncarico}" , method = RequestMethod.GET )
    //@SessionAttributes(types = Obiettivo.class)
    public String updateForm(@PathVariable("idObiettivo") int idObiettivo,  @PathVariable("idIncarico") int idIncarico,
                            Model model) {
        Obiettivo obiettivo = objCmdServizi.findById(idObiettivo); // la seconda path � inutile
        model.addAttribute(obiettivo);
        return "geko/programmazione/resppop/formObiettivoEditPop";       
    }
    
    //
    @RequestMapping(value ="{idObiettivo}/edit/{idIncarico}" , params = "update", method =  RequestMethod.PUT ) 
    public String updateForm(@ModelAttribute Obiettivo obiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new ObiettivoValidator().validate(obiettivo, result);
		if (result.hasErrors()) {
	           return "geko/programmazione/resppop/formObiettivoEditPop";
		}
		else {
            this.respPopServizi.popUpdateObiettivo(obiettivo);
            status.setComplete();
            return "redirect:/respPop/modifyProgrammazionePopIncarico/"
            + obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();
		}
    }
    // 
    @RequestMapping(value ="{idObiettivo}/edit/{idIncarico}" , params = "delete", method =  RequestMethod.PUT )
    public String editDeleteForm(@ModelAttribute Obiettivo obiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
        final Evento event = this.respPopServizi.cmdPopDeleteObiettivo(obiettivo);
		if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
			status.setComplete();
			return "redirect:/ROLE_RESP_POP"; 
            }
		else return "redirect:/respPop/errCmdManager/"+ event.getId(); 
    }
    
    //
    @RequestMapping(value ="{idObiettivo}/edit/{idIncarico}" , params = "addAction", method =  RequestMethod.PUT )
    public String addActionForm(@ModelAttribute Obiettivo obiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
        return "redirect:/resppopAct/new/"+obiettivo.getIdObiettivo();
	
    }
    //
    @RequestMapping(value ="{idObiettivo}/edit/{idIncarico}" , params = "cancel", method =  RequestMethod.PUT )
    public String editCancel(@ModelAttribute Obiettivo obiettivo, 
            BindingResult result, 
            SessionStatus status) {
    	status.setComplete();
    	return "redirect:/ROLE_RESP_POP";
    }
 // --------- fine Update/Delete ------------------------------

    
    
    // --- 4 proponi ---------------------------------------
    @RequestMapping(value ="{idObiettivo}/proponi/{idIncarico}" , method = RequestMethod.GET )
    //@SessionAttributes(types = Obiettivo.class)
    public String proponiGet(@PathVariable("idObiettivo") int idObiettivo, @PathVariable("idIncarico") int idIncarico,
                            Model model) {
    	Obiettivo obiettivo = objCmdServizi.findById(idObiettivo); // la seconda path � inutile
     	obiettivo.setAnno(menu.getAnno());
        model.addAttribute(obiettivo);
        return "geko/programmazione/resppop/formObiettivoProponiPop";
    }
    //
    @RequestMapping(value ="{idObiettivo}/proponi/{idIncarico}" , params = "update", method =  RequestMethod.PUT )
    public String proponiUpdatePut(@ModelAttribute Obiettivo obiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new ObiettivoValidator().validate(obiettivo, result);
		if (result.hasErrors()) {
            return "geko/programmazione/resppop/formObiettivoProponiPop";
		}
		else {
            final Evento event = this.respPopServizi.cmdPopProponeObiettivo(obiettivo);
			if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
				status.setComplete();
				return "redirect:/respPop/modifyProgrammazionePopIncarico/"
		            + obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();
 
            } else return "redirect:/ROLE_RESP_POP";
		}  
    }
    
    //
    @RequestMapping(value ="{idObiettivo}/proponi/{idIncarico}" , params = "cancel", method =  RequestMethod.PUT )
    public String proponiCancelPut(@ModelAttribute Obiettivo obiettivo, 
            BindingResult result, 
            SessionStatus status) {
    	status.setComplete();
    	return "redirect:/ROLE_RESP_POP";
    }
    

	//--- 5 accetta e rendi definitivo ------------------------------
	@RequestMapping(value ="{idObiettivo}/accetta/{idIncarico}" , method = RequestMethod.GET )
	//@SessionAttributes(types = Obiettivo.class)
	public String updateAccettaForm(@PathVariable("idObiettivo") int idObiettivo, @PathVariable("idIncarico") int idIncarico,
	                        Model model) {
		Obiettivo obiettivo = objCmdServizi.findById(idObiettivo); // la seconda path � inutile
        if (menu.getIdIncaricoScelta() == obiettivo.getIncaricoID()){
	        model.addAttribute(obiettivo);
	        return "geko/programmazione/resppop/formObiettivoAccettaPop";
	    }
	    else return "redirect:/ROLE_RESP_POP";
	}
	//
	@RequestMapping(value ="{idObiettivo}/accetta/{idIncarico}" , params = "update", method =  RequestMethod.PUT )
	public String updateAccettaForm(@ModelAttribute Obiettivo obiettivo, 
	                            BindingResult result, 
	                            SessionStatus status) {
	    //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
	    new ObiettivoValidator().validate(obiettivo, result);
		if (result.hasErrors()) {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
	            return "geko/programmazione/resppop/formObiettivoAccettaPop";
		}
		else {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
	            //this.respPopServizi.managerAccettaDefinitivamenteObiettivo(obiettivo);
	            final Evento event = this.respPopServizi.cmdPopAccettaDefinitivamenteObiettivo(obiettivo);
				if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
					status.setComplete();
		            return "redirect:/respPop/modifyProgrammazionePopIncarico/"
		            + obiettivo.getAnno()+"/"+obiettivo.getIncaricoID(); 
		            }
				else return "redirect:/resppop/errCmdManager/"+ event.getId(); 
	            
	            
		}
	}
	//
	@RequestMapping(value ="{idObiettivo}/accetta/{idIncarico}" , params = "cancel", method =  RequestMethod.PUT )
	public String cancelAccettaForm(@ModelAttribute Obiettivo obiettivo, 
	        BindingResult result, 
	        SessionStatus status) {
		status.setComplete();
		return "redirect:/ROLE_RESP_POP";
	}

	// --- rendiInterlocutorio ---------------------------------------
    @RequestMapping(value ="{idObiettivo}/rendiInterlocutorio/{idIncarico}" , method = RequestMethod.GET )
    //@SessionAttributes(types = Obiettivo.class)
    public String rendiInterlocutorioGet(@PathVariable("idObiettivo") int idObiettivo, @PathVariable("idIncarico") int idIncarico,
                            Model model) {
    	Obiettivo obiettivo = objCmdServizi.findById(idObiettivo); // la seconda path � inutile
        if (menu.getIdIncaricoScelta() == obiettivo.getIncaricoID()){
            model.addAttribute(obiettivo);
            return "geko/programmazione/resppop/formObiettivoRendiInterlocutorioPop";
        }
        else return "redirect:/ROLE_RESP_POP";
    }
    //
    @RequestMapping(value ="{idObiettivo}/rendiInterlocutorio/{idIncarico}" , params = "update", method =  RequestMethod.PUT )
    public String rendiInterlocutorioUpdatePut(@ModelAttribute Obiettivo obiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new ObiettivoValidator().validate(obiettivo, result);
		if (result.hasErrors()) {
            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
            return "geko/programmazione/resppop/formObiettivoRendiInterlocutorioPop";
		}
		else {
            //System.out.println("ManagerObiettiviController.proponiUpdatePut() no errors="+obiettivo.getDescrizione());
			final Evento event = this.respPopServizi.cmdPopRendiInterlocutorioObiettivo(obiettivo);
			if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
				status.setComplete();
				return "redirect:/respPop/modifyProgrammazionePopIncarico/"
		            + obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();
			}
			else return "redirect:/resppop/errCmdManager/"+ event.getId(); 
            }
    }
    //
    @RequestMapping(value ="{idObiettivo}/rendiInterlocutorio/{idIncarico}" , params = "cancel", method =  RequestMethod.PUT )
    public String rendiInterlocutorioCancelPut(@ModelAttribute Obiettivo obiettivo, 
            BindingResult result, 
            SessionStatus status) {
    	status.setComplete();
    	return "redirect:/ROLE_RESP_POP";
    }
    

	
	// ---------------- CASI D'USO MANAGER vedi ManagerService ----------------------------
    //  1. crea obiettivo 
    @RequestMapping(value="newInc/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String newObjGet(@PathVariable("anno") int anno,
    		@PathVariable("idIncarico") int idIncarico, Model model) {
    		//        
	    	Obiettivo obiettivo = new Obiettivo();
            //obiettivo.setStruttura(struttura);
            obiettivo.setAnno(anno);
            obiettivo.setStatoRealiz(Obiettivo.StatoRealizEnum.DA_REALIZZARE);
            obiettivo.setStatoValut(Obiettivo.StatoValutazEnum.DA_VALUTARE);
            obiettivo.setIncaricoID(idIncarico);
        	obiettivo.setTipo(TipoEnum.POS_ORGAN);        	
            model.addAttribute(obiettivo);
            return "geko/programmazione/resppop/formObiettivoCreatePop";	   
    }
    
    //
    @RequestMapping(value="newInc/{anno}/{idIncarico}", params = "add",method = RequestMethod.POST)
    public String createObjFormAdd(@ModelAttribute Obiettivo obiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
	        //
		new ObiettivoValidator().validate(obiettivo, result);
		//
	        if (result.hasErrors()) {
	        	return "geko/programmazione/resppop/formObiettivoCreatePop";
	        }	
		else {
	            //this.respPopServizi.managerCreateObiettivo(obiettivo);logout
			
				final Evento event = this.respPopServizi.cmdPopCreateObiettivo(obiettivo);
				if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
		            status.setComplete();
		            return "redirect:/respPop/modifyProgrammazionePopIncarico/"
		            + obiettivo.getAnno()+"/"+obiettivo.getIncaricoID(); 
					}
				else return "redirect:/resppop/errCmdManager/"+ event.getId(); 
		}
    }
    
    //
    @RequestMapping(value="newInc/{anno}/{idIncarico}", params = "cancel",method = RequestMethod.POST)
    public String createObjFormCancel() {
    	return "redirect:/ROLE_RESP_POP";
    }
 
    
  	
}// ----------------------------------------------------------

