package it.sicilia.regione.gekoddd.geko.programmazione.web.manager;



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
import it.sicilia.regione.gekoddd.geko.programmazione.application.ManagerProgrammazioneService;
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
@RequestMapping("/dirigenteObj")
public class ManagerObiettiviController  {
	
	//
    @Autowired
    private ObiettivoCmdService objCmdServizi;
    @Autowired
    private ManagerProgrammazioneService managerServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    
    public ManagerObiettiviController() { }
    
    private static final Logger log = LoggerFactory.getLogger(ManagerObiettiviController.class);
    
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
        return "geko/programmazione/manager/formObiettivoEditManager";
       
    }
    
    //
    @RequestMapping(value ="{idObiettivo}/edit/{idIncarico}" , params = "update", method =  RequestMethod.PUT ) 
    public String updateForm(@ModelAttribute Obiettivo obiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new ObiettivoValidator().validate(obiettivo, result);
		if (result.hasErrors()) {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
	            return "geko/programmazione/manager/formObiettivoEditManager";
		}
		else {
            this.managerServizi.managerUpdateObiettivo(obiettivo);
            status.setComplete();
            //return "redirect:/dirigenteObj/" + obiettivo.getIdObiettivo();
            if( obiettivo.getTipo().equals(Obiettivo.TipoEnum.DIRIGENZIALE)) {			
	            return "redirect:/dirigente/modifyProgrammazioneIncarico/"
	            + obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();
			} else {
				return "redirect:/dirigente/modifyObjCompartoProgrammazione/"
			    + obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();	
			}
		}
    }
    // 
    @RequestMapping(value ="{idObiettivo}/edit/{idIncarico}" , params = "delete", method =  RequestMethod.PUT )
    public String editDeleteForm(@ModelAttribute Obiettivo obiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
        final Evento event = this.managerServizi.cmdManagerDeleteObiettivo(obiettivo);
		if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
			status.setComplete();
			return "redirect:/ROLE_MANAGER"; 
            }
		else return "redirect:/dirigente/errCmdManager/"+ event.getId(); 
    }
    
    //
    @RequestMapping(value ="{idObiettivo}/edit/{idIncarico}" , params = "addAction", method =  RequestMethod.PUT )
    public String addActionForm(@ModelAttribute Obiettivo obiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
        return "redirect:/dirigenteAct/new/"+obiettivo.getIdObiettivo();
	
    }
    //
    @RequestMapping(value ="{idObiettivo}/edit/{idIncarico}" , params = "cancel", method =  RequestMethod.PUT )
    public String editCancel(@ModelAttribute Obiettivo obiettivo, 
            BindingResult result, 
            SessionStatus status) {
    	status.setComplete();
    	return "redirect:/ROLE_MANAGER";
    }
 // --------- fine Update/Delete ------------------------------

    
    
    // --- 4 proponi ---------------------------------------
    @RequestMapping(value ="{idObiettivo}/proponi/{idIncarico}" , method = RequestMethod.GET )
    //@SessionAttributes(types = Obiettivo.class)
    public String proponiGet(@PathVariable("idObiettivo") int idObiettivo, @PathVariable("idIncarico") int idIncarico,
                            Model model) {
    	Obiettivo obiettivo = objCmdServizi.findById(idObiettivo); // la seconda path � inutile
        if (menu.getIdIncaricoScelta() == obiettivo.getIncaricoID()){
        	obiettivo.setAnno(menu.getAnno());
            model.addAttribute(obiettivo);
            return "geko/programmazione/manager/formObiettivoProponiManager";
        }
        else return "redirect:/ROLE_MANAGER";
    }
    //
    @RequestMapping(value ="{idObiettivo}/proponi/{idIncarico}" , params = "update", method =  RequestMethod.PUT )
    public String proponiUpdatePut(@ModelAttribute Obiettivo obiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new ObiettivoValidator().validate(obiettivo, result);
		if (result.hasErrors()) {
            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
            return "geko/programmazione/manager/formObiettivoProponiManager";
		}
		else {
            //System.out.println("ManagerObiettiviController.proponiUpdatePut() no errors="+obiettivo.getDescrizione());
			final Evento event = this.managerServizi.cmdManagerProponeObiettivo(obiettivo);
			if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
				status.setComplete();
				if( obiettivo.getTipo().equals(Obiettivo.TipoEnum.DIRIGENZIALE)) {			
		            return "redirect:/dirigente/modifyProgrammazioneIncarico/"
		            + obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();
				} else {
					return "redirect:/dirigente/modifyObjCompartoProgrammazione/"
				    + obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();	
				}
			
				}
			else return "redirect:/dirigente/errCmdManager/"+ event.getId();  
            }
    }
    //
    @RequestMapping(value ="{idObiettivo}/proponi/{idIncarico}" , params = "cancel", method =  RequestMethod.PUT )
    public String proponiCancelPut(@ModelAttribute Obiettivo obiettivo, 
            BindingResult result, 
            SessionStatus status) {
    	status.setComplete();
    	return "redirect:/ROLE_MANAGER";
    }
    

	//--- 5 accetta e rendi definitivo ------------------------------
	@RequestMapping(value ="{idObiettivo}/accetta/{idIncarico}" , method = RequestMethod.GET )
	//@SessionAttributes(types = Obiettivo.class)
	public String updateAccettaForm(@PathVariable("idObiettivo") int idObiettivo, @PathVariable("idIncarico") int idIncarico,
	                        Model model) {
		Obiettivo obiettivo = objCmdServizi.findById(idObiettivo); // la seconda path � inutile
        if (menu.getIdIncaricoScelta() == obiettivo.getIncaricoID()){
	        model.addAttribute(obiettivo);
	        return "geko/programmazione/manager/formObiettivoAccettaManager";
	    }
	    else return "redirect:/ROLE_MANAGER";
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
	            return "geko/programmazione/manager/formObiettivoAccettaManager";
		}
		else {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
	            //this.managerServizi.managerAccettaDefinitivamenteObiettivo(obiettivo);
	            final Evento event = this.managerServizi.cmdManagerAccettaDefinitivamenteObiettivo(obiettivo);
				if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
					status.setComplete();
		            return "redirect:/dirigente/modifyProgrammazioneIncarico/"
		            + obiettivo.getAnno()+"/"+obiettivo.getIncaricoID(); 
		            }
				else return "redirect:/dirigente/errCmdManager/"+ event.getId(); 
	            
	            
		}
	}
	//
	@RequestMapping(value ="{idObiettivo}/accetta/{idIncarico}" , params = "cancel", method =  RequestMethod.PUT )
	public String cancelAccettaForm(@ModelAttribute Obiettivo obiettivo, 
	        BindingResult result, 
	        SessionStatus status) {
		status.setComplete();
		return "redirect:/ROLE_MANAGER";
	}

	// --- rendiInterlocutorio ---------------------------------------
    @RequestMapping(value ="{idObiettivo}/rendiInterlocutorio/{idIncarico}" , method = RequestMethod.GET )
    //@SessionAttributes(types = Obiettivo.class)
    public String rendiInterlocutorioGet(@PathVariable("idObiettivo") int idObiettivo, @PathVariable("idIncarico") int idIncarico,
                            Model model) {
    	Obiettivo obiettivo = objCmdServizi.findById(idObiettivo); // la seconda path � inutile
        if (menu.getIdIncaricoScelta() == obiettivo.getIncaricoID()){
            model.addAttribute(obiettivo);
            return "geko/programmazione/manager/formObiettivoRendiInterlocutorioManager";
        }
        else return "redirect:/ROLE_MANAGER";
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
            return "geko/programmazione/manager/formObiettivoRendiInterlocutorioManager";
		}
		else {
            //System.out.println("ManagerObiettiviController.proponiUpdatePut() no errors="+obiettivo.getDescrizione());
			final Evento event = this.managerServizi.cmdManagerRendiInterlocutorioObiettivo(obiettivo);
			if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
				status.setComplete();
				if( obiettivo.getTipo().equals(Obiettivo.TipoEnum.DIRIGENZIALE)) {			
		            return "redirect:/dirigente/modifyProgrammazioneIncarico/"
		            + obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();
				} else {
					return "redirect:/dirigente/modifyObjCompartoProgrammazione/"
				    + obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();	
				}
			
				}
			else return "redirect:/dirigente/errCmdManager/"+ event.getId();  
            }
    }
    //
    @RequestMapping(value ="{idObiettivo}/rendiInterlocutorio/{idIncarico}" , params = "cancel", method =  RequestMethod.PUT )
    public String rendiInterlocutorioCancelPut(@ModelAttribute Obiettivo obiettivo, 
            BindingResult result, 
            SessionStatus status) {
    	status.setComplete();
    	return "redirect:/ROLE_MANAGER";
    }
    

	
	// ---------------- CASI D'USO MANAGER vedi ManagerService ----------------------------
    //  1. crea obiettivo 
    @RequestMapping(value="newInc/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String newObjGet(@PathVariable("anno") int anno,
    		@PathVariable("idIncarico") int idIncarico, Model model) {
    	//
        if (menu.getIdIncaricoScelta() == idIncarico ){
	    	Obiettivo obiettivo = new Obiettivo();
            //obiettivo.setStruttura(struttura);
            obiettivo.setAnno(anno);
            obiettivo.setStatoRealiz(Obiettivo.StatoRealizEnum.DA_REALIZZARE);
            obiettivo.setStatoValut(Obiettivo.StatoValutazEnum.DA_VALUTARE);
            obiettivo.setIncaricoID(idIncarico);
        	obiettivo.setTipo(TipoEnum.DIRIGENZIALE);        	
            model.addAttribute(obiettivo);
            return "geko/programmazione/manager/formObiettivoCreateManager";
	    }
    	else    return "geko/programmazione/manager/errNoResponsabileStruttura";
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
	        	System.out.println("ManagerObiettiviController.create() errors");
	            return "geko/programmazione/manager/formObiettivoCreateManager";
	        }	
		else {
	            //this.managerServizi.managerCreateObiettivo(obiettivo);
				final Evento event = this.managerServizi.cmdManagerCreateObiettivo(obiettivo);
				if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
		            status.setComplete();
		            return "redirect:/dirigente/modifyProgrammazioneIncarico/"
		            + obiettivo.getAnno()+"/"+obiettivo.getIncaricoID(); 
					}
				else return "redirect:/dirigente/errCmdManager/"+ event.getId(); 
		}
    }
    
    //
    @RequestMapping(value="newInc/{anno}/{idIncarico}", params = "cancel",method = RequestMethod.POST)
    public String createObjFormCancel() {
    	return "redirect:/ROLE_MANAGER";
    }
 
    //------------------------------------------------------------------------------------------------------
    //  1. crea obiettivo di gruppo o individuale
    @RequestMapping(value="newComparto/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String newCompartoGet(@PathVariable("anno") int anno,
    		@PathVariable("idIncarico") int idIncarico, Model model) {
    	//
        if (menu.getIdIncaricoScelta() == idIncarico ){
	    	Obiettivo obiettivo = new Obiettivo();
            //obiettivo.setStruttura(struttura);
            obiettivo.setAnno(anno);
            obiettivo.setStatoRealiz(Obiettivo.StatoRealizEnum.DA_REALIZZARE);
            obiettivo.setStatoValut(Obiettivo.StatoValutazEnum.DA_VALUTARE);
            obiettivo.setIncaricoID(idIncarico);
            obiettivo.setTipo(TipoEnum.STRUTTURA);       
            model.addAttribute(obiettivo);
            return "geko/programmazione/manager/formObiettivoCompartoCreateManager";
	    }
    	else    return "geko/programmazione/manager/errNoResponsabileStruttura";
    }
    
    //
    @RequestMapping(value="newComparto/{anno}/{idIncarico}", params = "add",method = RequestMethod.POST)
    public String newCompartoAdd(@ModelAttribute Obiettivo obiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
	        //
		new ObiettivoValidator().validate(obiettivo, result);
		//
	        if (result.hasErrors()) {
	        	System.out.println("ManagerObiettiviController.create() errors");
	            return "geko/programmazione/manager/formObiettivoCompartoCreateManager";
	        }	
		else {
	            //this.managerServizi.managerCreateObiettivo(obiettivo);
				final Evento event = this.managerServizi.cmdManagerCreateObiettivo(obiettivo);
				if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
		            status.setComplete();
						return "redirect:/dirigente/modifyObjCompartoProgrammazione/"
					    + obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();	
					
					}
				else return "redirect:/dirigente/errCmdManager/"+ event.getId(); 
		}
    }
    
    //
    @RequestMapping(value="newComparto/{anno}/{idIncarico}", params = "cancel",method = RequestMethod.POST)
    public String newCompartoCancel() {
    	return "redirect:/ROLE_MANAGER";
    }
    
    //--- 5 rendi definitivo obiettivo comparto----
	@RequestMapping(value ="{idObiettivo}/defComparto/{idIncarico}" , method = RequestMethod.GET )
	//@SessionAttributes(types = Obiettivo.class)
	public String defCompartoGet(@PathVariable("idObiettivo") int idObiettivo, @PathVariable("idIncarico") int idIncarico,
	                        Model model) {
		Obiettivo obiettivo = objCmdServizi.findById(idObiettivo); // la seconda path � inutile
        
	        model.addAttribute(obiettivo);
	        return "geko/programmazione/manager/formObiettivoCompartoDefManager";
	   
	}
	//
	@RequestMapping(value ="{idObiettivo}/defComparto/{idIncarico}" , params = "update", method =  RequestMethod.PUT )
	public String defCompartoPut(@ModelAttribute Obiettivo obiettivo, 
	                            BindingResult result, 
	                            SessionStatus status) {
	    //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
	    new ObiettivoValidator().validate(obiettivo, result);
		if (result.hasErrors()) {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
	            return "geko/programmazione/manager/formObiettivoCompartoDefManager";
		}
		else {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
	            //this.managerServizi.managerAccettaDefinitivamenteObiettivo(obiettivo);
	            final Evento event = this.managerServizi.cmdManagerDefCompartoObiettivo(obiettivo);
				if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
					status.setComplete();
					if (obiettivo.getTipo().equals(TipoEnum.POS_ORGAN))
						return "redirect:/dirigente/modifyProgrammazionePopIncarico/"
			            + obiettivo.getAnno()+"/"+obiettivo.getIncaricoID(); 
					else
		            return "redirect:/dirigente/modifyObjCompartoProgrammazione/"
		            + obiettivo.getAnno()+"/"+obiettivo.getIncaricoID(); 
		            }
				else return "redirect:/dirigente/errCmdManager/"+ event.getId(); 
	            
	            
		}
	}
	//
	@RequestMapping(value ="{idObiettivo}/defComparto/{idIncarico}" , params = "cancel", method =  RequestMethod.PUT )
	public String defCompartoCancel(@ModelAttribute Obiettivo obiettivo, 
	        BindingResult result, 
	        SessionStatus status) {
		status.setComplete();
		return "redirect:/ROLE_MANAGER";
	}
	
	//----------------------------------------------------------------------------------------------------
	//  richiedi obiettivo pop
    @RequestMapping(value="newIncPop/{anno}/{idIncarico}/{idIncaricoPadre}", method = RequestMethod.GET)
    public String newIncPopGet(@PathVariable("anno") int anno,
    		@PathVariable("idIncarico") int idIncarico, @PathVariable("idIncaricoPadre") int idIncaricoPadre, Model model) {
    	//
	    	Obiettivo obiettivo = new Obiettivo();
            //obiettivo.setStruttura(struttura);
            obiettivo.setAnno(anno);
            obiettivo.setTipo(Obiettivo.TipoEnum.POS_ORGAN);
            obiettivo.setStatoRealiz(Obiettivo.StatoRealizEnum.DA_REALIZZARE);
            obiettivo.setStatoValut(Obiettivo.StatoValutazEnum.DA_VALUTARE);
            obiettivo.setIncaricoID(idIncarico);
            obiettivo.setIncaricoPadreID(idIncaricoPadre); 	
            model.addAttribute(obiettivo);
            return "geko/programmazione/manager/formObiettivoPopCreateManager";	    
    }
    
    //
    @RequestMapping(value="newIncPop/{anno}/{idIncarico}/{idIncaricoPadre}", params = "add",method = RequestMethod.POST)
    public String newIncPopPost(@ModelAttribute Obiettivo obiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
	        //
		new ObiettivoValidator().validate(obiettivo, result);
		//
	        if (result.hasErrors()) {
	        	System.out.println("ManagerObiettiviController.create() errors");
	            return "geko/programmazione/manager/formObiettivoPopCreateManager";
	        }	
		else {
	            //this.managerServizi.managerCreateObiettivo(obiettivo);
				final Evento event = this.managerServizi.cmdManagerCreateObiettivo(obiettivo);
				if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
		            status.setComplete();
		            return "redirect:/dirigente/modifyProgrammazionePopIncarico/"
		            + obiettivo.getAnno()+"/{idIncaricoPadre}"; 
					}
				else return "redirect:/dirigente/errCmdManager/"+ event.getId(); 
		}
    }
    
    //
    @RequestMapping(value="newIncPop/{anno}/{idIncarico}/{idIncaricoPadre}", params = "cancel",method = RequestMethod.POST)
    public String newIncPopGetCancel() {
    	return "redirect:/ROLE_MANAGER";
    }
 
  //--- 5 rendi definitivo obiettivo comparto------------------------------
  	@RequestMapping(value ="{idObiettivo}/defPop/{idIncaricoPadre}" , method = RequestMethod.GET )
  	//@SessionAttributes(types = Obiettivo.class)
  	public String defPopGet(@PathVariable("idObiettivo") int idObiettivo, 
  			@PathVariable("idIncaricoPadre") int idIncaricoPadre,
  	                        Model model) {
  		Obiettivo obiettivo = objCmdServizi.findById(idObiettivo);
  		obiettivo.setIncaricoPadreID(idIncaricoPadre);
        model.addAttribute(obiettivo);
  	    return "geko/programmazione/manager/formObiettivoPopDefManager";
  	}
  	//
  	@RequestMapping(value ="{idObiettivo}/defPop/{idIncaricoPadre}" , params = "update", method =  RequestMethod.PUT )
  	public String defPopPut(@ModelAttribute Obiettivo obiettivo, 
  	                            BindingResult result, 
  	                            SessionStatus status) {
  	    //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
  	    new ObiettivoValidator().validate(obiettivo, result);
  		if (result.hasErrors()) {
  	            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
  	            return "geko/programmazione/manager/formObiettivoPopDefManager";
  		}
  		else {
  	            final Evento event = this.managerServizi.cmdManagerDefPopObiettivo(obiettivo);
  				if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
  					status.setComplete();
  					return "redirect:/dirigente/modifyProgrammazionePopIncarico/"
		            + obiettivo.getAnno()+"/"+obiettivo.getIncaricoPadreID(); 
  		            }
  				else return "redirect:/dirigente/errCmdManager/"+ event.getId();              
  		}
  	}
  	//
  	@RequestMapping(value ="{idObiettivo}/defPop/{idIncaricoPadre}" , params = "cancel", method =  RequestMethod.PUT )
  	public String defPopCancel(@ModelAttribute Obiettivo obiettivo, 
  	        BindingResult result, 
  	        SessionStatus status) {
  		status.setComplete();
  		return "redirect:/ROLE_MANAGER";
  	}
  	
  	//   edit pop
  	
  	 @RequestMapping(value ="{idObiettivo}/popEdit/{idIncaricoPadre}" , method = RequestMethod.GET )
     //@SessionAttributes(types = Obiettivo.class)
     public String editPopGet(@PathVariable("idObiettivo") int idObiettivo, 
    		 @PathVariable("idIncaricoPadre") int idIncaricoPadre,
    		 Model model) {
         Obiettivo obiettivo = objCmdServizi.findById(idObiettivo); 
         obiettivo.setIncaricoPadreID(idIncaricoPadre);
         model.addAttribute(obiettivo);
         return "geko/programmazione/manager/formObiettivoPopEditManager";
        
     }
     
     //
     @RequestMapping(value ="{idObiettivo}/popEdit/{idIncaricoPadre}" , params = "update", method =  RequestMethod.PUT ) 
     public String editPopPut(@ModelAttribute Obiettivo obiettivo, 
                                 BindingResult result, 
                                 SessionStatus status) {
         //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
         new ObiettivoValidator().validate(obiettivo, result);
 		if (result.hasErrors()) {
 	            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
 	            return "geko/programmazione/manager/formObiettivoPopEditManager";
 		}
 		else {
             this.managerServizi.managerUpdateObiettivo(obiettivo);
             status.setComplete();
             //return "redirect:/dirigenteObj/" + obiettivo.getIdObiettivo();
             return "redirect:/dirigente/modifyProgrammazionePopIncarico/"+
	            + obiettivo.getAnno()+"/"+obiettivo.getIncaricoPadreID(); 
 		}
     }
     // 
     @RequestMapping(value ="{idObiettivo}/popEdit/{idIncaricoPadre}" , params = "delete", method =  RequestMethod.PUT )
     public String popeditDeleteForm(@ModelAttribute Obiettivo obiettivo, 
                                 BindingResult result, 
                                 SessionStatus status) {
         final Evento event = this.managerServizi.cmdManagerDeleteObiettivo(obiettivo);
 		if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
 			status.setComplete();
 			return "redirect:/ROLE_MANAGER"; 
             }
 		else return "redirect:/dirigente/errCmdManager/"+ event.getId(); 
     }
     
     //
     @RequestMapping(value ="{idObiettivo}/popEdit/{idIncaricoPadre}" , params = "addAction", method =  RequestMethod.PUT )
     public String popaddActionForm(@ModelAttribute Obiettivo obiettivo, 
                                 BindingResult result, 
                                 SessionStatus status) {
         return "redirect:/dirigenteAct/new/"+obiettivo.getIdObiettivo();
 	
     }
     //
     @RequestMapping(value ="{idObiettivo}/popEdit/{idIncaricoPadre}" , params = "cancel", method =  RequestMethod.PUT )
     public String popeditCancel(@ModelAttribute Obiettivo obiettivo, 
             BindingResult result, 
             SessionStatus status) {
     	status.setComplete();
     	return "redirect:/ROLE_MANAGER";
     }
     
     
     // interlocutorio pop
  // --- rendiInterlocutorio ---------------------------------------
     @RequestMapping(value ="{idObiettivo}/interPop/{idIncaricoPadre}" , method = RequestMethod.GET )
     //@SessionAttributes(types = Obiettivo.class)
     public String interPopGet(@PathVariable("idObiettivo") int idObiettivo, 
    		 @PathVariable("idIncaricoPadre") int idIncaricoPadre,
                             Model model) {
     	Obiettivo obiettivo = objCmdServizi.findById(idObiettivo); // la seconda path � inutile
     	obiettivo.setIncaricoPadreID(idIncaricoPadre);
        model.addAttribute(obiettivo);
        return "geko/programmazione/manager/formObiettivoRendiInterlocutorioManager";         
     }
     //
     @RequestMapping(value ="{idObiettivo}/interPop/{idIncaricoPadre}" , params = "update", method =  RequestMethod.PUT )
     public String interPopUpdatePut(@ModelAttribute Obiettivo obiettivo, 
                                 BindingResult result, 
                                 SessionStatus status) {
         //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
         new ObiettivoValidator().validate(obiettivo, result);
 		if (result.hasErrors()) {
             //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
             return "geko/programmazione/manager/formObiettivoRendiInterlocutorioManager";
 		}
 		else {
             //System.out.println("ManagerObiettiviController.proponiUpdatePut() no errors="+obiettivo.getDescrizione());
 			final Evento event = this.managerServizi.cmdManagerRendiInterlocutorioObiettivo(obiettivo);
 			if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
 				status.setComplete();
 				return "redirect:/dirigente/modifyProgrammazionePopIncarico/"
 		            + obiettivo.getAnno()+"/"+obiettivo.getIncaricoPadreID();
 			}
 			else return "redirect:/ROLE_MANAGER";
 		}
     }
     //
     @RequestMapping(value ="{idObiettivo}/interPop/{idIncaricoPadre}" , params = "cancel", method =  RequestMethod.PUT )
     public String interPopCancelPut(@ModelAttribute Obiettivo obiettivo, 
             BindingResult result, 
             SessionStatus status) {
     	status.setComplete();
     	return "redirect:/ROLE_MANAGER";
     }
     

     
     
     
}// ----------------------------------------------------------

