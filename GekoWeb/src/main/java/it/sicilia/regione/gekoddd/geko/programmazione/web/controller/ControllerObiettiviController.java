package it.sicilia.regione.gekoddd.geko.programmazione.web.controller;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.application.ControllerProgrammazioneService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.AzioneCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoValidator;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo.TipoEnum;
import it.sicilia.regione.gekoddd.log.model.evento.Evento;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import java.text.SimpleDateFormat;
import java.util.*;

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
@SessionAttributes(types = Obiettivo.class)
@RequestMapping("/controllerObj")
public class ControllerObiettiviController  {
    
    private Log log = LogFactory.getLog(ControllerObiettiviController.class);
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
    
    public ControllerObiettiviController() { }
    
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
    
    //
 // ---------------- CASI D'USO CONTROLLER vedi ControllerService ----------------------------
    
    //
    @RequestMapping(value ="{idObiettivo}/valuta" , method = RequestMethod.GET )
    //@SessionAttributes(types = Obiettivo.class)
    public String updateValutaForm(@PathVariable("idObiettivo") int idObiettivo,
                            Model model) {
    	Obiettivo obiettivo = objCmdServizi.findById(idObiettivo);
        if (obiettivo.getIncaricoID() == menu.getIdIncaricoScelta()){
            model.addAttribute(obiettivo);
            return "geko/programmazione/controller/formObiettivoValutaController";
        }
        else return "redirect:/ROLE_CONTROLLER";
	    }
    //
    @RequestMapping(value ="{idObiettivo}/valuta" , params = "update", method =  RequestMethod.PUT )
    public String updateValutaForm(@ModelAttribute Obiettivo obiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ControllerObiettiviController.updateForm() obj="+obiettivo.getDenominazione());
        //new ObiettivoValidator().validate(obiettivo, result);
		if (result.hasErrors()) {
            //System.out.println("ControllerObiettiviController.updateForm() has errors="+obiettivo.getDenominazione());
            return "geko/programmazione/controller/formObiettivoValutaController";
		}
		else {
            //System.out.println("ControllerObiettiviController.updateForm() no errors="+obiettivo.getDenominazione());
            this.controllerServizi.controllerValutaObiettivo(obiettivo);
            status.setComplete();
            return "redirect:/controller/modifyValutazioneAltraStrutturaController/"+ obiettivo.getAnno()+"/"+menu.getIdIncaricoScelta();  
            }
    }
    //
    @RequestMapping(value ="{idAzione}/valuta" , params = "cancel", method =  RequestMethod.PUT )
    public String CancelValutaForm() {
        return "redirect:/ROLE_CONTROLLER";
    }
    
    
//  8. crea obiettivo apicale diretto
    //
    @RequestMapping(value="newDip/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String createObjApicaleGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	
        //
        Obiettivo obiettivo = new Obiettivo();
        obiettivo.setTipo(Obiettivo.TipoEnum.DIRIGENZIALE);
        obiettivo.setIncaricoID(idIncarico);
        obiettivo.setAnno(anno);
        obiettivo.setPeso(10);
        obiettivo.setStatoRealiz(Obiettivo.StatoRealizEnum.DA_REALIZZARE);
        obiettivo.setStatoValut(Obiettivo.StatoValutazEnum.DA_VALUTARE);
        if (menu.getAnno()>=2019) {
    		obiettivo.setTipo(TipoEnum.DIRIGENZIALE);
    	}
        obiettivo.setApicale(true);
        model.addAttribute(obiettivo);
        return "geko/programmazione/controller/formObiettivoApicaleCreaController";
        
    }
    //
    @RequestMapping(value="newDip/{anno}/{idIncarico}", params = "add", method = RequestMethod.POST)
    public String createObjApicalePost(@ModelAttribute Obiettivo obiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
        //
		new ObiettivoValidator().validate(obiettivo, result);
		//
        if (result.hasErrors()) {
            return "geko/programmazione/controller/formObiettivoApicaleCreaController";
        }	
		else {
				obiettivo.setPesoApicale(obiettivo.getPeso());
	            Evento event = this.controllerServizi.cmdControllerCreaObiettivoApicale(obiettivo);
	            if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
	            	status.setComplete();
		            return "redirect:/controller/modifyPianificazioneApicaleDirettaController/"+obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();  
		            
	    			}
	    		else return "redirect:/controller/errCmdController/"+ event.getId();  
	           }
    }
    
    //
    @RequestMapping(value="newDip/{anno}/{idIncarico}", params = "cancel", method = RequestMethod.POST)
    public String createObjApicalePostCancel(@ModelAttribute Obiettivo obiettivo, 
            BindingResult result, 
            SessionStatus status) {
    	status.setComplete();
		return "redirect:/ROLE_CONTROLLER"; 	
    }
    
 // 2.- 3. Update/Delete Apicale ----------------------------
    @RequestMapping(value ="{idObiettivo}/editApicale" , method = RequestMethod.GET )
    //@SessionAttributes(types = Obiettivo.class)
    public String objEditApicaleGet(@PathVariable("idObiettivo") int idObiettivo, Model model) {
    	Obiettivo obiettivo = objCmdServizi.findById(idObiettivo);
    	//       
        model.addAttribute(obiettivo);
        return "geko/programmazione/controller/formObiettivoApicaleEditController";        
    }
    	
    //
    @RequestMapping(value ="{idObiettivo}/editApicale" , params = "update", method =  RequestMethod.PUT )
    public String objEditApicaleUpdatePut(@ModelAttribute Obiettivo obiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ControllerObiettiviController.updateForm() obj="+obiettivo.getDenominazione());
        new ObiettivoValidator().validate(obiettivo, result);
        
        if (result.hasErrors()) {
            //System.out.println("ControllerObiettiviController.updateForm() has errors="+obiettivo.getDenominazione());
            return "geko/programmazione/controller/formObiettivoApicaleEditController";
        }
        else {
            //System.out.println("ControllerObiettiviController.updateForm() no errors="+obiettivo.getDenominazione());
        	obiettivo.setPesoApicale(obiettivo.getPeso());
            controllerServizi.controllerUpdateObiettivoApicale(obiettivo);
            status.setComplete();
            //return "redirect:/controller/modifyPianificazioneApicaleDipartimentoController/"+ obiettivo.getAnno();}
            return "redirect:/controller/modifyPianificazioneApicaleDirettaController/"+obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();  
        }
    }
    //
    @RequestMapping(value ="{idObiettivo}/editApicale" , params = "delete", method =  RequestMethod.PUT )
    public String objEditApicaleDeletePut(@ModelAttribute Obiettivo obiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
        controllerServizi.controllerDeleteObiettivo(obiettivo);
        status.setComplete();
        return "redirect:/controller/modifyPianificazioneApicaleDirettaController/"+obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();  
        }
    //
    @RequestMapping(value ="{idObiettivo}/editApicale" , params = "addAction", method =  RequestMethod.PUT )
    public String addActionApicaleForm(@ModelAttribute Obiettivo obiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
        return "redirect:/controllerAct/new/"+obiettivo.getIdObiettivo();
	
    }
    //
    @RequestMapping(value ="{idObiettivo}/editApicale" , params = "cancel", method =  RequestMethod.PUT )
    public String editApicaleCancel(@ModelAttribute Obiettivo obiettivo, 
            BindingResult result, 
            SessionStatus status) {
    	status.setComplete();
    	return "redirect:/controller/modifyPianificazioneApicaleDirettaController/"+obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();  
        
    }
    
    // ________________________________ nuovi metodi per incarico_______________________________________________________________
//  1. proponi obiettivo a struttura
    //
    @RequestMapping(value="new/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String createForm(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
	    final PersonaFisicaGeko responsabile = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
	    if (null != responsabile) {
	    	Obiettivo obiettivo = new Obiettivo();
            //obiettivo.setStruttura(struttura);
            obiettivo.setAnno(anno);
            obiettivo.setStatoApprov(Obiettivo.StatoApprovEnum.RICHIESTO);
            obiettivo.setStatoRealiz(Obiettivo.StatoRealizEnum.DA_REALIZZARE);
            obiettivo.setStatoValut(Obiettivo.StatoValutazEnum.DA_VALUTARE);
            obiettivo.setIncaricoID(idIncarico);
            if (menu.getAnno()>=2019) {
        		obiettivo.setTipo(TipoEnum.DIRIGENZIALE);
        	}
            model.addAttribute(obiettivo);
        return "geko/programmazione/controller/formObiettivoRichiedeController";
	    }
    	else return "redirect:/ROLE_CONTROLLER";
    }
    //
    @RequestMapping(value="new/{anno}/{idIncarico}", params = "add", method = RequestMethod.POST)
    public String create(@ModelAttribute Obiettivo obiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
        //
		new ObiettivoValidator().validate(obiettivo, result);
		//
        if (result.hasErrors()) {
            return "geko/programmazione/controller/formObiettivoRichiedeController";
        }	
		else {
            this.controllerServizi.controllerProponeObiettivo(obiettivo);
            status.setComplete();
            return "redirect:/controller/modifyPianificazioneIncaricoController/"+ obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();  
             
		}
    }
    //
    //
    @RequestMapping(value="new/{anno}/{idIncarico}", params = "cancel", method = RequestMethod.POST)
    public String newCancel(@ModelAttribute Obiettivo obiettivo, 
            BindingResult result, 
            SessionStatus status) {
    	status.setComplete();
		return "redirect:/ROLE_CONTROLLER"; 	
    }
    
    
    
    // 2.- 3. Update/Delete  (se Interlocutorio-Proposto-Richiesto)----------------------------
    @RequestMapping(value ="{idObiettivo}/edit" , method = RequestMethod.GET )
    //@SessionAttributes(types = Obiettivo.class)
    public String updateForm(@PathVariable("idObiettivo") int idObiettivo, 
                            Model model) {
    	Obiettivo obiettivo = objCmdServizi.findById(idObiettivo);//
        model.addAttribute(obiettivo);
        return "geko/programmazione/controller/formObiettivoEditController";
        
    }
    //
    @RequestMapping(value ="{idObiettivo}/edit" , params = "update", method =  RequestMethod.PUT )
    public String updateForm(@ModelAttribute Obiettivo obiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ControllerObiettiviController.updateForm() obj="+obiettivo.getDenominazione());
        new ObiettivoValidator().validate(obiettivo, result);
        if (result.hasErrors()) {
            //System.out.println("ControllerObiettiviController.updateForm() has errors="+obiettivo.getDenominazione());
            return "geko/programmazione/controller/formObiettivoEditController";
        }
        else {
            //System.out.println("ControllerObiettiviController.updateForm() no errors="+obiettivo.getDenominazione());
            controllerServizi.controllerUpdateObiettivo(obiettivo);
            status.setComplete();
            return "redirect:/controller/modifyPianificazioneIncaricoController/"+ obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();  
        }
    }
    //
    @RequestMapping(value ="{idObiettivo}/edit" , params = "delete", method =  RequestMethod.PUT )
    public String deleteForm(@ModelAttribute Obiettivo obiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
        controllerServizi.controllerDeleteObiettivo(obiettivo);
        status.setComplete();
        return "redirect:/controller/modifyPianificazioneIncaricoController/"+ obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();   
   }
    //
    @RequestMapping(value ="{idObiettivo}/edit" , params = "addAction", method =  RequestMethod.PUT )
    public String addActionForm(@ModelAttribute Obiettivo obiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
        return "redirect:/controllerAct/new/"+obiettivo.getIdObiettivo();
	
    }
    //
    @RequestMapping(value ="{idObiettivo}/edit" , params = "cancel", method =  RequestMethod.PUT )
    public String editCancel(@ModelAttribute Obiettivo obiettivo, 
            BindingResult result, 
            SessionStatus status) {
    	status.setComplete();
    	return "redirect:/ROLE_CONTROLLER";
    }
    
 // 1.b Richiede (se Interlocutorio)----------------------------
    //
    @RequestMapping(value ="{idObiettivo}/richiede" , method = RequestMethod.GET )
    //@SessionAttributes(types = Obiettivo.class)
    public String richiedeGet(@PathVariable("idObiettivo") int idObiettivo, 
                            Model model) {
        Obiettivo obiettivo = objCmdServizi.findById(idObiettivo);//
        model.addAttribute(obiettivo);
        return "geko/programmazione/controller/formObiettivoRichiedeController";
       
    }
        
    //
    @RequestMapping(value ="{idObiettivo}/richiede" , params = "update", method =  RequestMethod.PUT )
    public String richiedeUpdatePut(@ModelAttribute Obiettivo obiettivo,
                                BindingResult result, 
                                SessionStatus status) {
        if (result.hasErrors()) {
	            //System.out.println("ControllerObiettiviController.updateForm() has errors="+obiettivo.getDenominazione());
	            return "geko/programmazione/controller/formObiettivoRichiedeController";
		}
		else {
	            //System.out.println("ControllerObiettiviController.updateForm() no errors="+obiettivo.getDenominazione());
	            this.controllerServizi.controllerRichiedeObiettivo(obiettivo);
	            status.setComplete();
	            return "redirect:/controller/modifyPianificazioneIncaricoController/"+ obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();  
	            }
    }
    //
    @RequestMapping(value ="{idObiettivo}/richiede" , params = "cancel", method =  RequestMethod.PUT )
    public String richiedeCancelForm() {
        return "redirect:/ROLE_CONTROLLER";
    }
    
    // 4. Valida (se Proposto)----------------------------
    //
    @RequestMapping(value ="{idObiettivo}/valida" , method = RequestMethod.GET )
    //@SessionAttributes(types = Obiettivo.class)
    public String updateValidaForm(@PathVariable("idObiettivo") int idObiettivo,
                            Model model) {
    	Obiettivo obiettivo = objCmdServizi.findById(idObiettivo);//
        model.addAttribute(obiettivo);
        if (obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.INTERLOCUTORIO) ||
        		obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.PROPOSTO) ||
        		obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.RIVISTO)){
            model.addAttribute(obiettivo);
            return "geko/programmazione/controller/formObiettivoValidaController";
        }
        else return "redirect:/ROLE_CONTROLLER";
	        
	    }
    //
    @RequestMapping(value ="{idObiettivo}/valida" , params = "update", method =  RequestMethod.PUT )
    public String updateValidaForm(@ModelAttribute Obiettivo obiettivo,
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ControllerObiettiviController.updateForm() obj="+obiettivo.getDenominazione());
        //new ObiettivoValidator().validate(obiettivo, result);
		if (result.hasErrors()) {
	            //System.out.println("ControllerObiettiviController.updateForm() has errors="+obiettivo.getDenominazione());
	            return "geko/programmazione/controller/formObiettivoValidaController";
		}
		else {
	            //System.out.println("ControllerObiettiviController.updateForm() no errors="+obiettivo.getDenominazione());
	            this.controllerServizi.controllerValidaObiettivo(obiettivo);
	            status.setComplete();
	            return "redirect:/controller/modifyPianificazioneIncaricoController/"+ obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();  
	            }
    }
    //
    @RequestMapping(value ="{idObiettivo}/valida" , params = "cancel", method =  RequestMethod.PUT )
    public String CancelValidaForm() {
        return "redirect:/ROLE_CONTROLLER";
    }
    
    // 5. Rivedi (se Proposto)----------------------------
    //
    @RequestMapping(value ="{idObiettivo}/rivede" , method = RequestMethod.GET )
    //@SessionAttributes(types = Obiettivo.class)
    public String rivediGet(@PathVariable("idObiettivo") int idObiettivo,
                            Model model) {
    	Obiettivo obiettivo = objCmdServizi.findById(idObiettivo);//
        model.addAttribute(obiettivo);
        if (obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.PROPOSTO) ||
        		obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.DEFINITIVO)
        		){
            model.addAttribute(obiettivo);
            return "geko/programmazione/controller/formObiettivoRivedeController";
        }
        else return "redirect:/ROLE_CONTROLLER";
	        
	    }
    //
    @RequestMapping(value ="{idObiettivo}/rivede" , params = "update", method =  RequestMethod.PUT )
    public String rivediUpdatePut(@ModelAttribute Obiettivo obiettivo,
                                BindingResult result, 
                                SessionStatus status) {
        if (result.hasErrors()) {
            return "geko/programmazione/controller/formObiettivoRivedeController";
		}
		else {
            this.controllerServizi.controllerRivedeObiettivo(obiettivo);
            status.setComplete();
            return "redirect:/controller/modifyPianificazioneApicaleDirettaController/"+ obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();    
	    }
    }
    //
    @RequestMapping(value ="{idObiettivo}/rivede" , params = "cancel", method =  RequestMethod.PUT )
    public String rivediCancelPut() {
        return "redirect:/ROLE_CONTROLLER";
    }
    
 // 6. Respingi (se Proposto)----------------------------
    //
    @RequestMapping(value ="{idObiettivo}/respinge" , method = RequestMethod.GET )
    //@SessionAttributes(types = Obiettivo.class)
    public String respingeGet(@PathVariable("idObiettivo") int idObiettivo, @PathVariable("idIncarico") int idIncarico,
                            Model model) {
    	Obiettivo obiettivo = objCmdServizi.findById(idObiettivo);//
        model.addAttribute(obiettivo);
        if (obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.PROPOSTO)){
	            model.addAttribute(obiettivo);
	            return "geko/programmazione/controller/formObiettivoRespingeController";
	        }
        else return "redirect:/ROLE_CONTROLLER";
	        
	    }
    //
    @RequestMapping(value ="{idObiettivo}/respinge" , params = "update", method =  RequestMethod.PUT )
    public String respingeUpdatePut(@ModelAttribute Obiettivo obiettivo,
                                BindingResult result, 
                                SessionStatus status) {
        if (result.hasErrors()) {
            return "geko/programmazione/controller/formObiettivoRespingeController";
		}
		else {
            this.controllerServizi.controllerRespingeObiettivo(obiettivo);
            status.setComplete();
            return "redirect:/controller/modifyPianificazioneIncaricoController/"+ obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();    
    	}
    }
    //
    @RequestMapping(value ="{idObiettivo}/respinge" , params = "cancel", method =  RequestMethod.PUT )
    public String respingeCancelPut() {
        return "redirect:/ROLE_CONTROLLER";
    }
    
 // 7. Annulla (se Definitivo)----------------------------
    //
    @RequestMapping(value ="{idObiettivo}/annulla" , method = RequestMethod.GET )
    //@SessionAttributes(types = Obiettivo.class)
    public String annullaGet(@PathVariable("idObiettivo") int idObiettivo, 
                            Model model) {
    	Obiettivo obiettivo = objCmdServizi.findById(idObiettivo);//
        model.addAttribute(obiettivo);
        //if (obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.DEFINITIVO) ){
            model.addAttribute(obiettivo);
            return "geko/programmazione/controller/formObiettivoAnnullaController";
        //}
        //else return "redirect:/ROLE_CONTROLLER";
	        
	    }
    //
    @RequestMapping(value ="{idObiettivo}/annulla" , params = "update", method =  RequestMethod.PUT )
    public String annullaUpdatePut(@ModelAttribute Obiettivo obiettivo,
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ControllerObiettiviController.updateForm() obj="+obiettivo.getDenominazione());
        //new ObiettivoValidator().validate(obiettivo, result);
		if (result.hasErrors()) {
	            //System.out.println("ControllerObiettiviController.updateForm() has errors="+obiettivo.getDenominazione());
	            return "geko/programmazione/controller/formObiettivoAnnullaController";
		}
		else {
	            //System.out.println("ControllerObiettiviController.updateForm() no errors="+obiettivo.getDenominazione());
            this.controllerServizi.controllerAnnullaObiettivo(obiettivo);
            status.setComplete();
            return "redirect:/controller/modifyPianificazioneIncaricoController/"+ obiettivo.getAnno()+"/"+obiettivo.getIncaricoID(); 
        }
    }
    //
    @RequestMapping(value ="{idObiettivo}/annulla" , params = "cancel", method =  RequestMethod.PUT )
    public String annullaCancelPut() {
        return "redirect:/ROLE_CONTROLLER";
    }
    
 // 8. Rende apicale (se Definitivo)----------------------------
    //
    @RequestMapping(value ="{idObiettivo}/apicale" , method = RequestMethod.GET )
    //@SessionAttributes(types = Obiettivo.class)
    public String updateApicaleForm(@PathVariable("idObiettivo") int idObiettivo, 
                            Model model) {
    	Obiettivo obiettivo = objCmdServizi.findById(idObiettivo);//
    	model.addAttribute(obiettivo);
        
        return "geko/programmazione/controller/formObiettivoApicaleController";
    }
    //
    @RequestMapping(value ="{idObiettivo}/apicale" , params = "update", method =  RequestMethod.PUT )
    public String updateApicaleForm(@ModelAttribute Obiettivo obiettivo,
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ControllerObiettiviController.updateForm() obj="+obiettivo.getDenominazione());
        //new ObiettivoValidator().validate(obiettivo, result);
		if (result.hasErrors()) {
	            //System.out.println("ControllerObiettiviController.updateForm() has errors="+obiettivo.getDenominazione());
	            return "geko/programmazione/controller/formObiettivoApicaleController";
		}
		else {
            //System.out.println("ControllerObiettiviController.updateForm() no errors="+obiettivo.getDenominazione());
            this.controllerServizi.controllerRendiApicaleObiettivo(obiettivo);
            status.setComplete();
            return "redirect:/controller/modifyPianificazioneIncaricoController/"+ obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();   
        }
    }

    //
    @RequestMapping(value ="{idObiettivo}/apicale" , params = "cancel", method =  RequestMethod.PUT )
    public String CancelApicaleForm() {
        return "redirect:/ROLE_CONTROLLER";
    }
    
    
 // /controllerIncarico/${obj.id}/assegnaIncarico
    
    // ----------------------- assegna Incarico ----------------------------------------
       @RequestMapping(value ="{id}/assegnaIncarico/{anno}" , method = RequestMethod.GET )
       public String assegnaIncaricoGET(@PathVariable("id") int id, @PathVariable("anno") int anno, Model model) {
       	final Obiettivo obiettivo = objCmdServizi.findById(id);
       	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
       	final List<IncaricoGeko> incarichiAnno = fromOrganikoServizi.findIncarichiByStrutturaIDAndAnno(incarico.pgID, anno);
     		
           model.addAttribute("obiettivo", obiettivo);
           //
           model.addAttribute("incarichiAnno", incarichiAnno);
           return "geko/programmazione/controller/formObiettivoAssegnaIncaricoController";
           
       }
       
       
       @RequestMapping(value ="{id}/assegnaIncarico/{anno}" , params = "update", method =  RequestMethod.PUT )
       public String assegnaIncaricoPut(@ModelAttribute Obiettivo obiettivo, 
                                   BindingResult result, 
                                   SessionStatus status) {
           //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
           new ObiettivoValidator().validate(obiettivo, result);
   		if (result.hasErrors()) {
   	            return "geko/programmazione/controller/formObiettivoAssegnaIncaricoController";
   		}
   		else {
   			IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIdIncaricoScelta());
   			obiettivo.setIncarico(incarico);
   			this.controllerServizi.controllerUpdateIncaricoObiettivo(obiettivo);
               status.setComplete();          
               return "redirect:/controller/sintesiPianificazioneOrfaniDipartimentoController/"+ obiettivo.getAnno();
   		}
       }
       
       @RequestMapping(value ="{id}/assegnaIncarico/{anno}" , params = "cancel", method =  RequestMethod.PUT )
       public String assegnaIncaricoCancel(@ModelAttribute IncaricoGeko incarico, 
                                   BindingResult result, 
                                   SessionStatus status) {
       	return "redirect:/ROLE_CONTROLLER";
       }

       
    // --- rendiInterlocutorio ---------------------------------------
       @RequestMapping(value ="{idObiettivo}/rendiInterlocutorio" , method = RequestMethod.GET )
       //@SessionAttributes(types = Obiettivo.class)
       public String rendiInterlocutorioGet(@PathVariable("idObiettivo") int idObiettivo, 
                               Model model) {
       	Obiettivo obiettivo = objCmdServizi.findById(idObiettivo); //
       	//log.info("menu.getIdIncaricoDeptScelta(): "+menu.getIdIncaricoDeptScelta()+" obiettivo.getIncaricoID(): "+obiettivo.getIncaricoID());
           //if (menu.getIdIncaricoDeptScelta() == obiettivo.getIncaricoID()){
               model.addAttribute(obiettivo);
               return "geko/programmazione/controller/formObiettivoRendiInterlocutorioController";
           //}
           //else return "redirect:/ROLE_CONTROLLER";
       }
       //
       @RequestMapping(value ="{idObiettivo}/rendiInterlocutorio" , params = "update", method =  RequestMethod.PUT )
       public String rendiInterlocutorioUpdatePut(@ModelAttribute Obiettivo obiettivo, 
                                   BindingResult result, 
                                   SessionStatus status) {
           //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
           new ObiettivoValidator().validate(obiettivo, result);
   		if (result.hasErrors()) {
               //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
               return "geko/programmazione/controller/formObiettivoRendiInterlocutorioController";
   		}
   		else {
               //System.out.println("ManagerObiettiviController.proponiUpdatePut() no errors="+obiettivo.getDescrizione());
   			final Evento event = this.controllerServizi.cmdControllerRendiInterlocutorioObiettivo(obiettivo);
   			if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
   				status.setComplete();
   				if (obiettivo.isApicaleDiretto()){
   					return "redirect:/controller/modifyPianificazioneApicaleDirettaController/"+ obiettivo.getAnno()+"/"+obiettivo.getIncaricoID(); 
   		   		}
   				else return "redirect:/controller/modifyPianificazioneIncaricoController/"+ obiettivo.getAnno()+"/"+obiettivo.getIncaricoID(); 
   	   			}
   			else return "redirect:/controller/errCmdController/"+ event.getId();  
               }
       }
       //
       @RequestMapping(value ="{idObiettivo}/rendiInterlocutorio" , params = "cancel", method =  RequestMethod.PUT )
       public String rendiInterlocutorioCancelPut(@ModelAttribute Obiettivo obiettivo, 
               BindingResult result, 
               SessionStatus status) {
       	status.setComplete();
       	return "redirect:/ROLE_CONTROLLER";
       }
       
       //
    // --- rendiProposto ---------------------------------------
       @RequestMapping(value ="{idObiettivo}/rendiProposto" , method = RequestMethod.GET )
       //@SessionAttributes(types = Obiettivo.class)
       public String rendiPropostoGet(@PathVariable("idObiettivo") int idObiettivo, 
                               Model model) {
       	Obiettivo obiettivo = objCmdServizi.findById(idObiettivo); //
       	//log.info("menu.getIdIncaricoDeptScelta(): "+menu.getIdIncaricoDeptScelta()+" obiettivo.getIncaricoID(): "+obiettivo.getIncaricoID());
           //if (menu.getIdIncaricoDeptScelta() == obiettivo.getIncaricoID()){
               model.addAttribute(obiettivo);
               return "geko/programmazione/controller/formObiettivoRendiPropostoController";
           //}
           //else return "redirect:/ROLE_CONTROLLER";
       }
       //
       @RequestMapping(value ="{idObiettivo}/rendiProposto" , params = "update", method =  RequestMethod.PUT )
       public String rendiPropostoUpdatePut(@ModelAttribute Obiettivo obiettivo, 
                                   BindingResult result, 
                                   SessionStatus status) {
           //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
           new ObiettivoValidator().validate(obiettivo, result);
   		if (result.hasErrors()) {
               //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
               return "geko/programmazione/controller/formObiettivoRendiPropostoController";
   		}
   		else {
               //System.out.println("ManagerObiettiviController.proponiUpdatePut() no errors="+obiettivo.getDescrizione());
   			final Evento event = this.controllerServizi.cmdControllerRendiPropostoObiettivo(obiettivo);
   			if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
   				status.setComplete();
   	            return "redirect:/controller/modifyPianificazioneIncaricoController/"+ obiettivo.getAnno()+"/"+obiettivo.getIncaricoID(); 
   			
   				}
   			else return "redirect:/controller/errCmdController/"+ event.getId();  
               }
       }
       //
       @RequestMapping(value ="{idObiettivo}/rendiProposto" , params = "cancel", method =  RequestMethod.PUT )
       public String rendiPropostoCancelPut(@ModelAttribute Obiettivo obiettivo, 
               BindingResult result, 
               SessionStatus status) {
       	status.setComplete();
       	return "redirect:/ROLE_CONTROLLER";
       }
       

    // --- 4 proponi se apicale ---------------------------------------
       @RequestMapping(value ="{idObiettivo}/proponiGab" , method = RequestMethod.GET )
       //@SessionAttributes(types = Obiettivo.class)
       public String proponiGet(@PathVariable("idObiettivo") int idObiettivo,
                               Model model) {
       	Obiettivo obiettivo = objCmdServizi.findById(idObiettivo); // la seconda path � inutile
           
               model.addAttribute(obiettivo);
               return "geko/programmazione/controller/formObiettivoProponiController";
           
       }
       //
       @RequestMapping(value ="{idObiettivo}/proponiGab" , params = "update", method =  RequestMethod.PUT )
       public String proponiUpdatePut(@ModelAttribute Obiettivo obiettivo, 
                                   BindingResult result, 
                                   SessionStatus status) {
           new ObiettivoValidator().validate(obiettivo, result);
   		if (result.hasErrors()) {
   			return "geko/programmazione/controller/formObiettivoProponiController";
   		}
   		else {
               //System.out.println("ManagerObiettiviController.proponiUpdatePut() no errors="+obiettivo.getDescrizione());
   			final Evento event = this.controllerServizi.cmdControllerProponeObiettivo(obiettivo);
   			if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
   				status.setComplete();
   	            return "redirect:/controller/modifyPianificazioneApicaleDirettaController/"+ obiettivo.getAnno()+"/"+obiettivo.getIncaricoID(); 
   			
   				}
   			else return "redirect:/controller/errCmdController/"+ event.getId();  
               }
       }
       //
       @RequestMapping(value ="{idObiettivo}/proponiGab" , params = "cancel", method =  RequestMethod.PUT )
       public String proponiCancelPut(@ModelAttribute Obiettivo obiettivo, 
               BindingResult result, 
               SessionStatus status) {
       	status.setComplete();
       	return "redirect:/ROLE_CONTROLLER";
       }
       
       // 4. Valida (se Proposto)----------------------------
       //
       @RequestMapping(value ="{idObiettivo}/concorda" , method = RequestMethod.GET )
       //@SessionAttributes(types = Obiettivo.class)
       public String concordaGet(@PathVariable("idObiettivo") int idObiettivo,
                               Model model) {
       	Obiettivo obiettivo = objCmdServizi.findById(idObiettivo);//
           model.addAttribute(obiettivo);
           
               model.addAttribute(obiettivo);
               return "geko/programmazione/controller/formObiettivoConcordaController";
           
   	        
   	    }
       //
       @RequestMapping(value ="{idObiettivo}/concorda" , params = "update", method =  RequestMethod.PUT )
       public String concordaPut(@ModelAttribute Obiettivo obiettivo,
                                   BindingResult result, 
                                   SessionStatus status) {
           //System.out.println("ControllerObiettiviController.updateForm() obj="+obiettivo.getDenominazione());
           //new ObiettivoValidator().validate(obiettivo, result);
   		if (result.hasErrors()) {
   	            //System.out.println("ControllerObiettiviController.updateForm() has errors="+obiettivo.getDenominazione());
   	            return "geko/programmazione/controller/formObiettivoConcordaController";
   		}
   		else {
   	            //System.out.println("ControllerObiettiviController.updateForm() no errors="+obiettivo.getDenominazione());
   	            this.controllerServizi.cmdControllerConcordaObiettivoApicale(obiettivo);
   	            status.setComplete();
   	            return "redirect:/controller/modifyPianificazioneIncaricoController/"+ obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();  
   	            }
       }
       //
       @RequestMapping(value ="{idObiettivo}/concorda" , params = "cancel", method =  RequestMethod.PUT )
       public String CancelConcordaForm() {
           return "redirect:/ROLE_CONTROLLER";
       }
       
       
        // -----clona obiettivo -----------------
        @RequestMapping(value="{idObiettivo}/clona+1", method = RequestMethod.GET)
        public String clonaObiettivoGet(@PathVariable("idObiettivo") int idObiettivo, Model model) {
       	
            Obiettivo obiettivo = objCmdServizi.findById(idObiettivo);
   	    model.addAttribute("obiettivo", obiettivo);
   	    //    	
   	    return "geko/programmazione/controller/formObiettivoApicaleClonaController";   
   	    //
       }
      
       
       // ------------------------------------------------------------------------------------------------------------------------
       @RequestMapping(value ="{idObiettivo}/clona+1" , params = "add", method =  RequestMethod.PUT )
       public String clonaObiettivoPut(@ModelAttribute Obiettivo obiettivo, BindingResult result, SessionStatus status) {
            //System.out.println("ControllerObiettiviController.updateForm() obj="+obiettivo.getDenominazione());
            new ObiettivoValidator().validate(obiettivo, result);
            if (result.hasErrors()) {
            //System.out.println("ControllerObiettiviController.updateForm() has errors="+obiettivo.getDenominazione());
            return "geko/programmazione/controller/formObiettivoApicaleClonaController";
            }
            else {                
                // metodo di Obiettivo
                // creo in modo da avere la sua Id
                Obiettivo newObiettivo = obiettivo.clone();
                objCmdServizi.save(newObiettivo);
                System.out.println("clonaProgrammazioneIncaricoPut() obiettivo con id "
                        + obiettivo.getIdObiettivo() +" clonato con nuova id " + newObiettivo.getIdObiettivo());
                //
                for(Azione act : obiettivo.getAzioni()){                    
                    Azione azione = act.clone();      	
                    azione.setObiettivo(newObiettivo);
                    azione.setCompletamento(Azione.CompletamentoEnum.DA_VALUTARE);
                    actCmdServizi.save(azione);                    
                System.out.println("clonaProgrammazioneIncaricoPut() Azione con id "+ act.getIdAzione() +" clonato con nuova id " + azione.getIdAzione());
                } // fine for azioni
                
                menu.setAnno(menu.getAnno()+1);
                return "redirect:/controller/modifyPianificazioneApicaleDirettaController/"+menu.getAnno()+"/"+obiettivo.getIncaricoID();  
            }
	}
       
       @RequestMapping(value ="{idObiettivo}/clona+1" , params = "cancel", method =  RequestMethod.PUT )
       public String clonaProgrammazioneIncarico_Cancel(@ModelAttribute Obiettivo obiettivo, BindingResult result, SessionStatus status) {
       	return "redirect:/ROLE_CONTROLLER";
       }

          
       // ---------- Obiettivi comparto ------------------------------------------
       @RequestMapping(value="newComparto/{anno}/{idIncarico}", method = RequestMethod.GET)
       public String newCompartoGet(@PathVariable("anno") int anno,
    		   @PathVariable("idIncarico") int idIncarico, Model model) {
			//          
			Obiettivo obiettivo = new Obiettivo();
			//obiettivo.setStruttura(struttura);
			obiettivo.setAnno(anno);
			obiettivo.setStatoRealiz(Obiettivo.StatoRealizEnum.DA_REALIZZARE);
			obiettivo.setStatoValut(Obiettivo.StatoValutazEnum.DA_VALUTARE);
			obiettivo.setIncaricoID(idIncarico);
			obiettivo.setTipo(TipoEnum.STRUTTURA);       
            model.addAttribute(obiettivo);
            return "geko/programmazione/controller/formObiettivoCompartoCreateController";   	    
       }
       
       //
       @RequestMapping(value="newComparto/{anno}/{idPersona}", params = "add",method = RequestMethod.POST)
       public String newCompartoAdd(@ModelAttribute Obiettivo obiettivo, 
                                   BindingResult result, 
                                   SessionStatus status) {
   	        //
   		new ObiettivoValidator().validate(obiettivo, result);
   		//
   	        if (result.hasErrors()) {
   	        	log.info("newCompartoGet.create() errors");
   	            return "geko/programmazione/controller/formObiettivoCompartoCreateController";
   	        }	
   		else {
   	            
   			return "redirect:/ROLE_CONTROLLER"; // da completare
   			/*
   			//this.managerServizi.managerCreateObiettivo(obiettivo);
   				//final Evento event = this.controllerServizi.cmd   .createObiettivo(obiettivo);
   				if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
   		            status.setComplete();
   						return "redirect:/dirigente/modifyObjCompartoProgrammazione/"
   					    + obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();	
   					
   					}
   				else return "redirect:/dirigente/errCmdManager/"+ event.getId(); 
   				*/
   		}
   		
       }
       
       //
       @RequestMapping(value="newComparto/{anno}/{idPersona}", params = "cancel",method = RequestMethod.POST)
       public String newCompartoCancel() {
       	return "redirect:/ROLE_CONTROLLER";
       }
       
    // -----------------------------------
       // -----clona obiettivo -----------------
        @RequestMapping(value="{idObiettivo}/clonaDir+1", method = RequestMethod.GET)
        public String clonaDirObiettivoGet(@PathVariable("idObiettivo") int idObiettivo, Model model) {
       	
            Obiettivo obiettivo = objCmdServizi.findById(idObiettivo);
   	    model.addAttribute("obiettivo", obiettivo);
   	    //    	
   	    return "geko/programmazione/controller/formObiettivoDirigenzialeClonaController";   
   	    //
       }
            // ------------------------------------------------------------------------------------------------------------------------
       @RequestMapping(value ="{idObiettivo}/clonaDir+1" , params = "add", method =  RequestMethod.PUT )
       public String clonaDirObiettivoPut(@ModelAttribute Obiettivo obiettivo, BindingResult result, SessionStatus status) {
            //System.out.println("ControllerObiettiviController.updateForm() obj="+obiettivo.getDenominazione());
            new ObiettivoValidator().validate(obiettivo, result);
            if (result.hasErrors()) {
            //System.out.println("ControllerObiettiviController.updateForm() has errors="+obiettivo.getDenominazione());
            return "geko/programmazione/controller/formObiettivoDirigenzialeClonaController";
            }
            else {                
                // metodo di Obiettivo
                // creo in modo da avere la sua Id
                Obiettivo newObiettivo = obiettivo.clone();
                objCmdServizi.save(newObiettivo);
                System.out.println("clonaProgrammazioneIncaricoPut() obiettivo con id "
                        + obiettivo.getIdObiettivo() +" clonato con nuova id " + newObiettivo.getIdObiettivo());
                //
                for(Azione act : obiettivo.getAzioni()){                    
                    Azione azione = act.clone();      	
                    azione.setObiettivo(newObiettivo);
                    azione.setCompletamento(Azione.CompletamentoEnum.DA_VALUTARE);
                    actCmdServizi.save(azione);                    
                System.out.println("clonaProgrammazioneIncaricoPut() Azione con id "+ act.getIdAzione() +" clonato con nuova id " + azione.getIdAzione());
                } // fine for azioni
                
                menu.setAnno(menu.getAnno()+1);
                return "redirect:/controller/modifyPianificazioneIncaricoController/"+menu.getAnno()+"/"+obiettivo.getIncaricoID();  
            }
	}
       
       @RequestMapping(value ="{idObiettivo}/clonaDir+1" , params = "cancel", method =  RequestMethod.PUT )
       public String clonaDirProgrammazioneIncarico_Cancel(@ModelAttribute Obiettivo obiettivo, BindingResult result, SessionStatus status) {
       	return "redirect:/ROLE_CONTROLLER";
       }
       
} // --------------------------------------------------------------
