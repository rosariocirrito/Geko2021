package it.sicilia.regione.gekoddd.geko.programmazione.web.gab;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.application.GabinettoProgrammazioneService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoValidator;
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
@RequestMapping("/gabinettoObj")
public class GabinettoObiettiviController  {
    
	private Log log = LogFactory.getLog(GabinettoObiettiviController.class);
	//
    @Autowired
    private ObiettivoCmdService objCmdServizi;
    @Autowired
    private GabinettoProgrammazioneService gabServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    public GabinettoObiettiviController() { }
    
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
    
    //
    @RequestMapping(value ="{idObiettivo}/concordaApicale" , method = RequestMethod.GET )
    //@SessionAttributes(types = Obiettivo.class)
    public String concordaApicaleFormGet(@PathVariable("idObiettivo") int idObiettivo,
                            Model model) {
    	Obiettivo obiettivo = objCmdServizi.findById(idObiettivo);
        //if (obiettivo.getIncaricoID() == menu.getIdIncaricoGekoApicaleAmm_neScelta()){
            model.addAttribute(obiettivo);
            return "geko/programmazione/gab/formObiettivoConcordaApicaleGab";
        //}
        //else return "redirect:/ROLE_GABINETTO";
	    }
    //
    @RequestMapping(value ="{idObiettivo}/concordaApicale" , params = "update", method =  RequestMethod.PUT )
    public String concordaApicaleFormPut(@ModelAttribute Obiettivo obiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ControllerObiettiviController.updateForm() obj="+obiettivo.getDenominazione());
        //new ObiettivoValidator().validate(obiettivo, result);
		if (result.hasErrors()) {
            //System.out.println("ControllerObiettiviController.updateForm() has errors="+obiettivo.getDenominazione());
			return "geko/programmazione/gab/formObiettivoConcordaApicaleGab";
		}
		else {
            //System.out.println("ControllerObiettiviController.updateForm() no errors="+obiettivo.getDenominazione());
            this.gabServizi.cmdGabinettoConcordaObiettivoApicale(obiettivo);
            status.setComplete();
            return "redirect:/gabProg/modifyPianificazioneApicaleIncaricoGab/"+ obiettivo.getAnno()+"/"+menu.getIncaricoApicaleAmm_ne().getIdIncarico();  
            }
    }
    //
    @RequestMapping(value ="{idObiettivo}/concordaApicale" , params = "cancel", method =  RequestMethod.PUT )
    public String concordaApicaleFormCancel() {
        return "redirect:/ROLE_GABINETTO";
    }
    
    
    
 // ---------------- CASI D'USO CONTROLLER vedi ControllerService ----------------------------
    
   
    
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
        obiettivo.setStatoRealiz(Obiettivo.StatoRealizEnum.REALIZZATO);
        obiettivo.setStatoValut(Obiettivo.StatoValutazEnum.DA_VALUTARE);
        obiettivo.setApicale(true);
        model.addAttribute(obiettivo);
        return "geko/programmazione/gab/formObiettivoApicaleCreaGabinetto";
        
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
            return "geko/programmazione/gab/formObiettivoApicaleCreaGabinetto";
        }	
		else {
	            this.gabServizi.gabinettoRichiedeObiettivo(obiettivo);
	            status.setComplete();
	            return "redirect:/gabProg/modifyPianificazioneApicaleIncaricoGab/"+obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();  
	             
		}
    }
    //
    //
    @RequestMapping(value="newDip/{anno}/{idIncarico}", params = "cancel", method = RequestMethod.POST)
    public String createObjApicalePostCancel(@ModelAttribute Obiettivo obiettivo, 
            BindingResult result, 
            SessionStatus status) {
    	status.setComplete();
		return "redirect:/ROLE_GABINETTO"; 	
    }
    
    
 // 2.- 3. Update/Delete Apicale ----------------------------
    @RequestMapping(value ="{idObiettivo}/editApicale" , method = RequestMethod.GET )
    //@SessionAttributes(types = Obiettivo.class)
    public String objEditApicaleGet(@PathVariable("idObiettivo") int idObiettivo, Model model) {
    	Obiettivo obiettivo = objCmdServizi.findById(idObiettivo);//
        
            model.addAttribute(obiettivo);
            return "geko/programmazione/gab/formObiettivoApicaleEditGabinetto";
        
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
            return "geko/programmazione/gab/formObiettivoApicaleEditGabinetto";
        }
        else {
            //System.out.println("ControllerObiettiviController.updateForm() no errors="+obiettivo.getDenominazione());
            gabServizi.gabinettoUpdateObiettivo(obiettivo);
            status.setComplete();
            //return "redirect:/gabinetto/modifyPianificazioneApicaleDipartimentoController/"+ obiettivo.getAnno();}
            return "redirect:/gabProg/modifyPianificazioneApicaleIncaricoGab/"+obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();  
        }
    }
    
    @RequestMapping(value ="{idObiettivo}/editApicale" , params = "delete", method =  RequestMethod.PUT )
    public String objEditApicaleDeletePut(@ModelAttribute Obiettivo obiettivo, 
                                BindingResult result, 
                                SessionStatus status) {
    	gabServizi.gabinettoDeleteObiettivo(obiettivo);
        status.setComplete();
        return "redirect:/gabProg/modifyPianificazioneApicaleIncaricoGab/"+obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();  
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
    	return "redirect:/gabProg/modifyPianificazioneApicaleIncaricoGab/"+obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();  
        
    }
    
   
    
   
   
    

    
    
 // 6. Respingi (se Proposto)----------------------------
    //
    @RequestMapping(value ="{idObiettivo}/respinge" , method = RequestMethod.GET )
    //@SessionAttributes(types = Obiettivo.class)
    public String respingeGet(@PathVariable("idObiettivo") int idObiettivo, @PathVariable("idIncarico") int idIncarico,
                            Model model) {
    	Obiettivo obiettivo = objCmdServizi.findById(idObiettivo);//
        model.addAttribute(obiettivo);
        model.addAttribute(obiettivo);
	    return "geko/programmazione/gabinetto/formObiettivoRespingeGabinetto";
	    }
    //
    @RequestMapping(value ="{idObiettivo}/respinge" , params = "update", method =  RequestMethod.PUT )
    public String respingeUpdatePut(@ModelAttribute Obiettivo obiettivo,
                                BindingResult result, 
                                SessionStatus status) {
        if (result.hasErrors()) {
            return "geko/programmazione/gabinetto/formObiettivoRespingeController";
		}
		else {
            this.gabServizi.gabinettoRespingeObiettivo(obiettivo);
            status.setComplete();
            return "redirect:/gabProg/modifyPianificazioneApicaleIncaricoGab/"+ obiettivo.getAnno()+"/"+obiettivo.getIncaricoID();    
    	}
    }
    //
    @RequestMapping(value ="{idObiettivo}/respinge" , params = "cancel", method =  RequestMethod.PUT )
    public String respingeCancelPut() {
        return "redirect:/ROLE_GABINETTO";
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
            return "geko/programmazione/gab/formObiettivoRendiInterlocutorioGabinetto";
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
            return "geko/programmazione/gab/formObiettivoRendiInterlocutorioGabinetto";
		}
		else {
            //System.out.println("ManagerObiettiviController.proponiUpdatePut() no errors="+obiettivo.getDescrizione());
			final Evento event = this.gabServizi.cmdGabinettoRendiInterlocutorioObiettivo(obiettivo);
			if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
				status.setComplete();
				return "redirect:/gabProg/modifyPianificazioneApicaleIncaricoGab/"+
						obiettivo.getAnno()+"/"+obiettivo.getIncaricoID(); 
			
				}
			else return "redirect:/gabinetto/errCmdController/"+ event.getId();  
            }
    }
    //
    @RequestMapping(value ="{idObiettivo}/rendiInterlocutorio" , params = "cancel", method =  RequestMethod.PUT )
    public String rendiInterlocutorioCancelPut(@ModelAttribute Obiettivo obiettivo, 
            BindingResult result, 
            SessionStatus status) {
    	status.setComplete();
    	return "redirect:/ROLE_GABINETTO";
    }
    
   
    
} // --------------------------------------------------------------
