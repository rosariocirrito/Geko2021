package it.sicilia.regione.gekoddd.geko.programmazione.web.controller;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.application.ControllerProgrammazioneService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione.CompletamentoEnum;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.AzioneCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoValidator;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione.ObiettivoAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione.ObiettivoAssegnazioneQryService;
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
@SessionAttributes(types = IncaricoGeko.class)
@RequestMapping("/controllerIncarico")
public class ControllerIncaricoController  {
    
	private Log log = LogFactory.getLog(ControllerIncaricoController.class);
	//
    @Autowired
    private ObiettivoCmdService objCmdServizi;
    @Autowired
    private ObiettivoQryService objQryServizi;    
    @Autowired
    private ObiettivoAssegnazioneQryService objAssegnazioniQryServizi;        
    @Autowired
    private AzioneCmdService actCmdServizi;
    @Autowired
    private ControllerProgrammazioneService controllerServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    public ControllerIncaricoController() { }
    
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
    
    
    // ------------------------------------------------------------
 // lista obiettivi e azioni e dipendenti in base al nome del dirigente con assegnazioni
    @RequestMapping(value="clonaPianificazioneIncaricoController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String clonaProgrammazioneIncaricoGet(@PathVariable("anno") int anno, 
    		@PathVariable("idIncarico") int idIncarico, Model model) {
    	
    	IncaricoGeko incaricoGeko = fromOrganikoServizi.findIncaricoById(idIncarico);
    	//incarico.setAnno(anno);
    	
	    model.addAttribute("incaricoGeko", incaricoGeko);
	    /*    	
	    final List<IncaricoGeko> listaIncarichiDept = menu.getIncarichiDept();
	    List<IncaricoGeko> listaIncarichi = new ArrayList<IncaricoGeko>();
	    for(IncaricoGeko inc : listaIncarichiDept){
	    	int annoFine = inc.getDataFine().getYear()+1900;
	    	// log.info("annoFineIncaricoDaClonare:"+annoFine);
	    	if (annoFine == menu.getAnno()) {
	    		listaIncarichi.add(inc);
	    	}
	    }*/
	    incaricoGeko.setListaIncarichiDaClonare(menu.getIncarichiDept());        
	    //
	    String viewPath = "geko/programmazione/controller/incarico/";
	    String viewName = viewPath+ "formIncaricoClonaController"; 
        return viewName; 
    }
   
    
  
    @RequestMapping(value ="clonaPianificazioneIncaricoController/{anno}/{idIncarico}" , params = "update", method =  RequestMethod.PUT )
    public String clonaProgrammazioneIncaricoPut(@ModelAttribute IncaricoGeko incaricoGeko, 
                                BindingResult result, 
                                SessionStatus status) {    	
        final IncaricoGeko incaricoSorgente = fromOrganikoServizi.findIncaricoById(incaricoGeko.getIdIncaricoDaClonare());
		if (incaricoSorgente != null)	{
			log.info("clonaPianificazioneIncaricoControllerPut() incaricoSorgente: "+incaricoSorgente.getStringa());	   
			final List<Obiettivo> listObiettivi = objQryServizi.findObiettiviPrioritariByIncaricoIDAndAnno(incaricoSorgente.idIncarico,menu.getAnno());
		
        // --------------------------------
    	if (!listObiettivi.isEmpty()) {
            	// clona gli obiettivi su 
            	for(Obiettivo obj : listObiettivi){
                    Obiettivo obiettivo = obj.cloneStessoAnno(); // metodo di Obiettivo
                    // creo in modo da avere la sua Id
                    obiettivo.setIncaricoID(incaricoGeko.idIncarico);
                    controllerServizi.controllerClonaObiettivo(obiettivo);
                    //System.out.println("clonaProgrammazioneIncaricoPut() obiettivo con id "+ obj.getIdObiettivo() +" clonato con nuova id " + obiettivo.getIdObiettivo());
                    for(Azione act : obj.getAzioni()){
                            Azione azione = act.cloneStessoAnno();      	
                    azione.setObiettivo(obiettivo);
                    azione.setCompletamento(CompletamentoEnum.DA_VALUTARE);
                    actCmdServizi.save(azione);
                    //System.out.println("clonaProgrammazioneIncaricoPut() Azione con id "+ azione.getIdAzione() +" clonato con nuova id " + azione.getIdAzione());
                    } // fine for azioni
            	} 
            	
            }
	        
		}
            log.info("Cloning existing Incarico");
            status.setComplete();
            String routePath = "redirect:/controller/modifyPianificazioneIncaricoController/";
    	    String routeName = routePath+ +menu.getAnno()+"/"+incaricoGeko.idIncarico; 
            return routeName; 
	}
    
    
    @RequestMapping(value ="clonaPianificazioneIncaricoController/{anno}/{idIncarico}" , params = "cancel", method =  RequestMethod.PUT )
    public String clonaProgrammazioneIncarico_Cancel(@ModelAttribute IncaricoGeko incarico, 
                                BindingResult result, 
                                SessionStatus status) {
    	return "redirect:/ROLE_CONTROLLER";
    }

       
    // ________________________________ nuovi metodi per incarico_______________________________________________________________
    //  1. proponi obiettivo a struttura
        //
        @RequestMapping(value="richiediApicale/{idIncarico}/{idObjApic}", method = RequestMethod.GET)
        public String richiediApicaleGet( 
        		@PathVariable("idIncarico") int idIncarico, 
        		@PathVariable("idObjApic") int idObjApic, 
        		Model model) {
        	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	    final PersonaFisicaGeko responsabile = fromOrganikoServizi.findPersonaFisicaById(incarico.getPfID());
    	    Obiettivo obiettivoApicale = objCmdServizi.findById(idObjApic);
    	    if (null != responsabile) {
    	    	Obiettivo obiettivo = obiettivoApicale.cloneStessoAnno();
    	    	obiettivo.setIncaricoID(incarico.idIncarico);
    	    	obiettivo.setAnno(menu.getAnno());
    	    	obiettivo.setTipo(TipoEnum.DIRIGENZIALE);
            	obiettivo.setPeso(obiettivo.getPesoApicale());
            	obiettivo.setStatoApprov(Obiettivo.StatoApprovEnum.RICHIESTO);
            	obiettivo.setApicale(false);
            	obiettivo.setNote(obiettivo.getNote()+ " da Apicale"); 
            	log.info("Obiettivo richiesto: " + obiettivo.getCodice() + " ad Incarico:"+incarico.toString());
            	controllerServizi.controllerRichiedeObiettivo(obiettivo);
    			//System.out.println("clonaProgrammazioneIncaricoPut() obiettivo con id "+ obj.getIdObiettivo() +" clonato con nuova id " + obiettivo.getIdObiettivo());
    			// clona le azioni
    			for(Azione act : obiettivoApicale.getAzioni()){
    				Azione azione = act.cloneStessoAnno();
    				Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"),Locale.ITALY);
    				calendar.setTime(act.getScadenzaApicale());
    				calendar.add(Calendar.DATE, -15); 
    				Date date = calendar.getTime();
    				azione.setPeso(act.getPesoApicale());
    				azione.setScadenza(date);
    		        azione.setObiettivo(obiettivo);
    		        azione.setCompletamento(CompletamentoEnum.DA_VALUTARE);
    		        obiettivo.addAzioneToObiettivo(azione);
    		        actCmdServizi.save(azione);
    		        //System.out.println("clonaProgrammazioneIncaricoPut() Azione con id "+ azione.getIdAzione() +" clonato con nuova id " + azione.getIdAzione());
    			} // fine for azioni
           return "redirect:/controller/modifyPianificazioneIncaricoController/"+menu.getAnno()+"/"+incarico.idIncarico;
        }
        //
    	    return "redirect:/ROLE_CONTROLLER";
        }
        
        
        
        

    
} // --------------------------------------------------------------
