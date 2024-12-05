/**
 * 
 */
package it.sicilia.regione.gekoddd.geko.programmazione.web.controller;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.application.ControllerProgrammazioneService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.AzioneCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazioneCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazioneValidator;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoCmdService;
import it.sicilia.regione.gekoddd.log.model.evento.Evento;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
@SessionAttributes(types = AzioneAssegnazione.class)
@RequestMapping("/controllerAssegn")
public class ControllerAssegnazioniController  {
    
	private static final Logger log = LoggerFactory.getLogger(ControllerAssegnazioniController.class);
	
    @Autowired
    private AzioneCmdService actCmdServizi;
    @Autowired
    private AzioneAssegnazioneCmdService actAssCmdServizi;
    @Autowired
    private ControllerProgrammazioneService controllerServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    
    @Autowired
    private Menu menu;
   
    public ControllerAssegnazioniController() { }
    
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
	return "geko/programmazione/controller/formAzioneAssegnazione";
    }
    //
     * 
     */
    
    @RequestMapping(value ="{id}/edit" , method = RequestMethod.GET )
    public String updateForm(@PathVariable("id") int id, Model model) {
        AzioneAssegnazione azioneAssegnazione = actAssCmdServizi.findById(id); 
        PersonaFisicaGeko dipendente = fromOrganikoServizi.findPersonaFisicaById(azioneAssegnazione.getPfID());
        azioneAssegnazione.setOpPersonaFisica(dipendente);
        //		
        model.addAttribute("azioneAssegnazione", azioneAssegnazione);
        return "geko/programmazione/controller/formAzioneAssegnazioneEditController";
    }

    @RequestMapping(value ="{id}/edit" , params = "update", method =  RequestMethod.PUT )
    public String processUpdateSubmit(@ModelAttribute AzioneAssegnazione azioneAssegnazione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        log.info("processUpdateSubmit azioneAssegnazione peso"+azioneAssegnazione.getPeso());
    	new AzioneAssegnazioneValidator().validate(azioneAssegnazione, result);
		if (result.hasErrors()) {
            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
            return "geko/programmazione/controller/formAzioneAssegnazioneEditController";
		}
		else {
            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
			final Evento event = this.controllerServizi.cmdControllerUpdateAzioneAssegnazione(azioneAssegnazione);
			if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
	            status.setComplete();
	            String returnPage="";
	            if(azioneAssegnazione.getAzione().getObiettivo().isApicale()) {
	            	returnPage+= "redirect:/controller/modifyPianificazioneApicaleDirettaController";
	            }
	            else {
	            	returnPage+= "redirect:/controller/modifyPianificazioneIncaricoController";
	            }
	            returnPage += "/"+ azioneAssegnazione.getAzione().getObiettivo().getAnno()
        	            +"/"+azioneAssegnazione.getAzione().getObiettivo().getIncaricoID(); 
	            return returnPage;
			}
			else return "redirect:/controller/errCmdManager/"+ event.getId(); 
		}
		 
    }
    
    @RequestMapping(value ="{id}/edit" , params = "delete", method =  RequestMethod.PUT )
    public String processDeleteSubmit(@ModelAttribute AzioneAssegnazione azioneAssegnazione, 
                                BindingResult result, 
                                SessionStatus status) {
        this.controllerServizi.controllerDeleteAzioneAssegnazione(azioneAssegnazione);
        //
        String returnPage="";
        if(azioneAssegnazione.getAzione().getObiettivo().isApicale()) {
        	returnPage+= "redirect:/controller/modifyPianificazioneApicaleDirettaController";
        }
        else {
        	returnPage+= "redirect:/controller/modifyPianificazioneIncaricoController";
        }
        returnPage += "/"+ azioneAssegnazione.getAzione().getObiettivo().getAnno()
	            +"/"+azioneAssegnazione.getAzione().getObiettivo().getIncaricoID(); 
        return returnPage;
    }
    
    @RequestMapping(value ="{id}/edit" , params = "cancel", method =  RequestMethod.PUT )
    public String processCancelSubmit() {
        return "redirect:/ROLE_MANAGER";
    }

    
    // crea azioneassegnazione
    @RequestMapping(value="new/{idAzione}", method = RequestMethod.GET)
    public String createAzioneAssegnazioneManager(@PathVariable("idAzione") int idAzione,Model model, HttpServletRequest request) {
        //
    	
        Azione azione = actCmdServizi.findById(idAzione);
        AzioneAssegnazione assegnazione = new AzioneAssegnazione();
        int anno = menu.getAnno();
        log.info("anno = "+anno);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");	
    	Calendar calendar = new GregorianCalendar(anno,0,01);
    	Date datein = calendar.getTime();
        //log.info("createAzioneAssegnazioneManager()"+datein.toString());
        
        //Date datein = new Date(anno,0,1);
        Date datefin = azione.getScadenza();
        //assegnazione.setDataInizio(datein);
        //assegnazione.setDataFine(datefin);
        //log.info("scadenza = "+datefin.toString());
        assegnazione.setAzione(azione);
        
        List<AzioneAssegnazione> assEsistenti = new ArrayList(azione.getAssegnazioni());
        List<PersonaFisicaGeko> dipEsistenti = new ArrayList<PersonaFisicaGeko>();//servizi.findPersoneFisicheOfAzione(azione);
        IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(azione.getIncaricoID());
        //log.info("incarico = "+incarico.toString());
        PersonaGiuridicaGeko struttura = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);
        List<PersonaFisicaGeko> dipStrutturaAnno = fromOrganikoServizi.findDipendentiStrictByStrutturaIDAndAnno(struttura.idPersona,menu.getAnno());
        //
        if(!assEsistenti.isEmpty()){
        	for(AzioneAssegnazione aa : assEsistenti){
        		//log.info("aa = "+aa.toString());
            	PersonaFisicaGeko pf = fromOrganikoServizi.findPersonaFisicaById(aa.getPfID());
            	//log.info("pf esistente = "+pf.toString());
            	dipEsistenti.add(pf);
            }
        }
        List<PersonaFisicaGeko> dipDisponibili = new LinkedList<PersonaFisicaGeko>();
        //
        for (PersonaFisicaGeko dip : dipStrutturaAnno) {
        	if (!dipEsistenti.contains(dip) ) dipDisponibili.add(dip);
        }
        //log.info("dipTotali nr: " +dipDisponibili.size());
        PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.getPfID());
        dipDisponibili.remove(manager);
        //log.info("dipTotali senza manager nr: " +dipDisponibili.size());
        //log.info("cerco dipendenti della struttura con id:"+struttura.idPersona+" per l'anno:"+anno+" della azione:"+azione.getIdAzione());
        //
        dipDisponibili.removeAll(dipEsistenti);
        
        //azione.addAssegnazioneToAzione(assegnazione); // che succede se tolgo visto che sono in Get
        model.addAttribute("azioneAssegnazione", assegnazione);
        model.addAttribute("listaEsistenti",dipEsistenti);
        model.addAttribute("listaDisponibili",dipDisponibili);
        
        return "geko/programmazione/controller/formAzioneAssegnazioneCreateController";
    }
    
    @RequestMapping(value="new/{idAzione}", params = "add", method = RequestMethod.POST)
    public String processSubmitObiettivoManager(@ModelAttribute AzioneAssegnazione azioneAssegnazione, 
                                BindingResult result, 
                                SessionStatus status) {
        //
		new AzioneAssegnazioneValidator().validate(azioneAssegnazione, result);
	        if (result.hasErrors()) {
	        	log.error("errore di validazione su azione assegnazione");
	            return "geko/programmazione/controller/formAzioneAssegnazioneCreateController";
	        }
	        else {
	        	log.info("controllerAssegnazioniController () new/{idAzione}, params = add .idDipScelta= "+azioneAssegnazione.getIdDip());
	            azioneAssegnazione.setPfID(azioneAssegnazione.getIdDip());//.setOpPersonaFisica(dip);
	            this.controllerServizi.controllerCreateAzioneAssegnazione(azioneAssegnazione);
	            status.setComplete();
	            String returnPage="";
	            if(azioneAssegnazione.getAzione().getObiettivo().isApicale()) {
	            	returnPage+= "redirect:/controller/modifyPianificazioneApicaleDirettaController";
	            }
	            else {
	            	returnPage+= "redirect:/controller/modifyPianificazioneIncaricoController";
	            }
	            returnPage += "/"+ azioneAssegnazione.getAzione().getObiettivo().getAnno()
        	            +"/"+azioneAssegnazione.getAzione().getObiettivo().getIncaricoID(); 
	            return returnPage;
	        }	    
    }
    
    
    //
    @RequestMapping(value="new/{idAzione}", params = "cancel",method = RequestMethod.POST)
    public String newCancel() {
    	return "redirect:/ROLE_CONTROLLER";
    }
    
    
    
   
    // ------------------------------------------------
    @RequestMapping(value ="{id}/editComparto" , method = RequestMethod.GET )
    public String editCompartoGet(@PathVariable("id") int id, Model model) {
        AzioneAssegnazione azioneAssegnazione = actAssCmdServizi.findById(id); 
        PersonaFisicaGeko dipendente = fromOrganikoServizi.findPersonaFisicaById(azioneAssegnazione.getPfID());
        azioneAssegnazione.setOpPersonaFisica(dipendente);
        //
        model.addAttribute("azioneAssegnazione", azioneAssegnazione);
        return "geko/programmazione/controller/formAzioneAssegnazioneEditController";
    }


    @RequestMapping(value ="{id}/editComparto" , params = "update", method =  RequestMethod.PUT )
    public String editCompartoPut(@ModelAttribute AzioneAssegnazione azioneAssegnazione, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
    	log.info("editCompartoPut azioneAssegnazione peso"+azioneAssegnazione.getPeso());
        new AzioneAssegnazioneValidator().validate(azioneAssegnazione, result);
		if (result.hasErrors()) {
            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
            return "geko/programmazione/controller/formAzioneAssegnazioneEditController";
		}
		else {
            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
			final Evento event = this.controllerServizi.cmdControllerUpdateAzioneAssegnazione(azioneAssegnazione);
			if(event.getTipo().equals(Evento.TipoEnum.SUCCESS)){
            status.setComplete();
            //return "redirect:/controller/modifyProgrammazioneIncarico/"
            return "redirect:/controller/modifyPianificazioneApicaleDirettaController/"
            + azioneAssegnazione.getAzione().getObiettivo().getAnno()
            +"/"+azioneAssegnazione.getAzione().getObiettivo().getIncaricoID(); 
			}
			else return "redirect:/controller/errCmdController/"+ event.getId();
		}
    }
    
    @RequestMapping(value ="{id}/editComparto" , params = "delete", method =  RequestMethod.PUT )
    public String editCompartoDel(@ModelAttribute AzioneAssegnazione azioneAssegnazione, 
                                BindingResult result, 
                                SessionStatus status) {
        this.controllerServizi.controllerDeleteAzioneAssegnazione(azioneAssegnazione);
        //
        return "redirect:/controller/modifyPianificazioneApicaleDirettaController/"
        + azioneAssegnazione.getAzione().getObiettivo().getAnno()
        +"/"+azioneAssegnazione.getAzione().getObiettivo().getIncaricoID(); 
    }
    
    @RequestMapping(value ="{id}/editComparto" , params = "cancel", method =  RequestMethod.PUT )
    public String editCompartoCanc() {
        return "redirect:/ROLE_CONTROLLER";
    }

    // ----------------------------------------
   



}//--------------------------
