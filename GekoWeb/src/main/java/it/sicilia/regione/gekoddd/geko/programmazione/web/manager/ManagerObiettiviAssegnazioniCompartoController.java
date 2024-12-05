/**
 * 
 */
package it.sicilia.regione.gekoddd.geko.programmazione.web.manager;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.application.ManagerProgrammazioneService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione.ObiettivoAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione.ObiettivoAssegnazioneCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione.ObiettivoAssegnazioneQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione.ObiettivoAssegnazioneValidator;
import it.sicilia.regione.gekoddd.log.model.evento.Evento;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
@SessionAttributes(types = ObiettivoAssegnazione.class)
@RequestMapping("/dirigenteObjAssegn")
public class ManagerObiettiviAssegnazioniCompartoController  {
    
	private static final Logger log = LoggerFactory.getLogger(ManagerObiettiviAssegnazioniCompartoController.class);
	
    //@Autowired
    private ObiettivoCmdService objCmdServizi;
    @Autowired
    private ObiettivoAssegnazioneCmdService objAssCmdServizi;
    @Autowired
    private ObiettivoAssegnazioneQryService objAssQryServizi;
    @Autowired
    private ManagerProgrammazioneService managerServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
   
    public ManagerObiettiviAssegnazioniCompartoController() { }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
        dataBinder.setDisallowedFields("opPersonaFisica");
    }
    
    /*
    // display a single azione information (cfr. Pro Spring 3 chp 17 p.621)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") int id, Model model) {
        AzioneAssegnazione azioneAssegnazione = actAssServizi.findById(id);  
        model.addAttribute(azioneAssegnazione);
	return "geko/programmazione/manager/formAzioneAssegnazione";
    }
    //
     * 
     */
    
    @RequestMapping(value ="{id}/edit/{idIncarico}" , method = RequestMethod.GET )
    public String updateForm(@PathVariable("id") int id, @PathVariable("idIncarico") int idIncarico, Model model) {
    	ObiettivoAssegnazione obiettivoAssegnazione = objAssCmdServizi.findById(id); 
    	obiettivoAssegnazione.setIdIncarico(idIncarico);
        //
		model.addAttribute("obiettivoAssegnazione", obiettivoAssegnazione);
	    return "geko/programmazione/manager/formObiettivoAssegnazioneEditManager";
	    }


    @RequestMapping(value ="{id}/edit/{idIncarico}" , params = "update", method =  RequestMethod.PUT )
    public String processUpdateSubmit(@ModelAttribute ObiettivoAssegnazione obiettivoAssegnazione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new ObiettivoAssegnazioneValidator().validate(obiettivoAssegnazione, result);
		if (result.hasErrors()) {
            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
            return "geko/programmazione/manager/formObiettivoAssegnazioneEditManager";
		}
		else {
            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
			final Evento event = this.managerServizi.cmdManagerUpdateObiettivoAssegnazione(obiettivoAssegnazione);
			if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
	            status.setComplete();
	            return "redirect:/dirigente/modifyDipendentiAssegnazioniProgrammazione/"
	            + obiettivoAssegnazione.getAnno()
	            +"/"+obiettivoAssegnazione.getIdIncarico(); 
			}
			else return "redirect:/dirigente/errCmdManager/"+ event.getId(); 
		}
		 
    }
    
    @RequestMapping(value ="{id}/edit/{idIncarico}" , params = "delete", method =  RequestMethod.PUT )
    public String processDeleteSubmit(@ModelAttribute ObiettivoAssegnazione obiettivoAssegnazione, 
                                BindingResult result, 
                                SessionStatus status) {
        this.managerServizi.managerDeleteObiettivoAssegnazione(obiettivoAssegnazione);
        //
        return "redirect:/dirigente/modifyProgrammazioneIncarico/"
        + obiettivoAssegnazione.getAnno()
        +"/"+obiettivoAssegnazione.getIdIncarico(); 
    }
    
    @RequestMapping(value ="{id}/edit/{idIncarico}" , params = "cancel", method =  RequestMethod.PUT )
    public String processCancelSubmit() {
        return "redirect:/ROLE_MANAGER";
    }

    
    // crea obiettivoassegnazione -----------------------------------------------------------------------------------------------
    @RequestMapping(value="new/{idObiettivo}/{idIncarico}", method = RequestMethod.GET)
    public String createAzioneAssegnazioneManager(@PathVariable("idObiettivo") int idObiettivo,@PathVariable("idIncarico") int idIncarico,
    		Model model, HttpServletRequest request) {
        //
    	IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
        ObiettivoAssegnazione obiettivoAssegnazione = new ObiettivoAssegnazione();
        
    	obiettivoAssegnazione.setAnno(menu.getAnno());
    	obiettivoAssegnazione.setIdObiettivo(idObiettivo);
    	obiettivoAssegnazione.setIdIncarico(idIncarico);
    	obiettivoAssegnazione.setIdStruttura(incarico.getPgID());
    	
    	List<PersonaFisicaGeko> dipEsistenti = new ArrayList<PersonaFisicaGeko>();
    	List<PersonaFisicaGeko> dipDisponibili = new ArrayList<PersonaFisicaGeko>();
    	
        List<ObiettivoAssegnazione> assEsistenti = objAssQryServizi.findByIdObiettivo(idObiettivo);
        if(!assEsistenti.isEmpty()){
	        for(ObiettivoAssegnazione aa : assEsistenti){
	        	PersonaFisicaGeko pf = fromOrganikoServizi.findPersonaFisicaById(aa.getIdPersona());
	        	dipEsistenti.add(pf);
	        }
        }
        //List<PersonaFisicaGeko> dipDipendentiStruttura = fromOrganikoServizi.findDipendentiGlobalByStrutturaIDAndAnno(obiettivoAssegnazione.getIdStruttura(),menu.getAnno());  
        List<PersonaFisicaGeko> dipDipendentiStruttura = fromOrganikoServizi.findDipendentiStrictByStrutturaIDAndAnno(obiettivoAssegnazione.getIdStruttura(),menu.getAnno());
        if(!dipDipendentiStruttura.isEmpty()){
        	dipDisponibili.addAll(dipDipendentiStruttura); 
        	for(PersonaFisicaGeko dipEsist : dipEsistenti){
                dipDisponibili.remove(dipEsist);
            }            
        }
       
        model.addAttribute("obiettivoAssegnazione", obiettivoAssegnazione);
        model.addAttribute("listaEsistenti",dipEsistenti);
        model.addAttribute("listaDisponibili",dipDisponibili);
        model.addAttribute("idIncarico",idIncarico);
        return "geko/programmazione/manager/formObiettivoAssegnazioneCreateManager";
    }
    
    @RequestMapping(value="new/{idObiettivo}/{idIncarico}", params = "add", method = RequestMethod.POST)
    public String processSubmitObiettivoManager(@ModelAttribute ObiettivoAssegnazione obiettivoAssegnazione, 
                                BindingResult result, 
                                SessionStatus status) {
        //
		new ObiettivoAssegnazioneValidator().validate(obiettivoAssegnazione, result);
	        if (result.hasErrors()) {
	        	log.error("errore di validazione su obiettivo assegnazione");
	            return "geko/programmazione/manager/formObiettivoAssegnazioneCreateManager";
	        }
	        else {
	        	log.info("managerAssegnazioniController () new/{idAzione}, params = add .idDipScelta= "+obiettivoAssegnazione.getIdPersona());
	        	obiettivoAssegnazione.setIdPersona(obiettivoAssegnazione.getIdPersona());//.setOpPersonaFisica(dip);
	            this.managerServizi.managerCreateObiettivoAssegnazione(obiettivoAssegnazione);
	            status.setComplete();
	        }
	        log.info("...... ");
        
        return "redirect:/dirigente/modifyProgrammazioneIncarico/"
        + obiettivoAssegnazione.getAnno()
        +"/"+obiettivoAssegnazione.getIdIncarico();
        
    }
        
    //
    @RequestMapping(value="new/{idObiettivo}/{idIncarico}", params = "cancel",method = RequestMethod.POST)
    public String newCancel() {
    	return "redirect:/ROLE_MANAGER";
    }
    
    
    
    
}//--------------------------
