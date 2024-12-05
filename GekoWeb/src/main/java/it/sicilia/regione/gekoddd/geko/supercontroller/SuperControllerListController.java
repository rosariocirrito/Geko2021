package it.sicilia.regione.gekoddd.geko.supercontroller;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoQryService;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import java.text.SimpleDateFormat;
import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * @author Cirrito
 *
 */

@Controller
@RequestMapping("/superController")
public class SuperControllerListController  {
	//
    @Autowired
    private ObiettivoQryService objServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    //
    public SuperControllerListController() { }
    
    //
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    

   /*
    
    // --------------------------- User --------------------------------------------
    // lista utenti
    @RequestMapping(value="listUtenti", method = RequestMethod.GET)
    public String listUtenti(Model model) {
        Map<OpPersonaGiuridica,List<User>> mapListUtenti = new HashMap();
        //
        OpPersonaGiuridica dipartimento = userServizi.findDipartimentoOfLoggedUser();
        mapListUtenti = userServizi.mapUtentiOfStructure(dipartimento);
        model.addAttribute("mapListUtenti", mapListUtenti);    
        model.addAttribute("nomeStruttura", dipartimento.getDenominazione());
        
        //
        return "administrator/listUtentiAdministrator";
    }
    // create utenti di un dipartimento
    @RequestMapping(value="createUtentiDipartimento", method = RequestMethod.GET)
    public String modifyUtentiDipartimentoGet(Model model) {
    	final OpPersonaGiuridica dipartimento = userServizi.findDipartimentoOfLoggedUser();
    	List<OpPersonaGiuridica> strutture = pgServizi.listSubStrutture(dipartimento);
        //
        model.addAttribute("strutture", strutture);   
        model.addAttribute("dipartimento", dipartimento);
        //
        return "administrator/createUtentiDipartimentoAdministrator";
    }
    
 // modifica utenti
    @RequestMapping(value="modifyUtentiDipartimento", method = RequestMethod.GET)
    public String modifyUtentiDipartimento(Model model) {
        Map<OpPersonaGiuridica,List<User>> mapListUtenti = new HashMap();
        //
        OpPersonaGiuridica dipartimento = userServizi.findDipartimentoOfLoggedUser();
        mapListUtenti = userServizi.mapUtentiOfStructure(dipartimento);
        model.addAttribute("mapListUtenti", mapListUtenti);    
        model.addAttribute("nomeStruttura", dipartimento.getDenominazione());
        
        //
        return "administrator/modifyUtentiAdministrator";
    }
    
    // modifica autorizzazioni
    @RequestMapping(value="modifyAuthUtenti", method = RequestMethod.GET)
    public String modifyAuthUtenti(Model model) {
        Map<OpPersonaGiuridica,List<User>> mapListUtenti = new HashMap();
        //
        OpPersonaGiuridica dipartimento = userServizi.findDipartimentoOfLoggedUser();
        mapListUtenti = userServizi.mapUtentiOfStructure(dipartimento);
        model.addAttribute("mapListUtenti", mapListUtenti);    
        model.addAttribute("nomeStruttura", dipartimento.getDenominazione());
        
        //
        return "administrator/modifyAuthUtentiAdministrator";
    }
    */
    
  //-----------------------Pianificazione --------------------
    /*
    @RequestMapping(value="listPianificazioneSuperController/{anno}", method = RequestMethod.GET)
    public String listPianificazioneSuperAdministrator(@PathVariable("anno") int anno, Model model) {
    	//
    	Map<OpPersonaGiuridica,List<Obiettivo>> mapObiettiviReg = objServizi.mapObiettiviRegionalibyAnno(anno);
        model.addAttribute("mapObiettiviReg", mapObiettiviReg); 
        model.addAttribute("anno", anno);
        //
        return "supercontroller/listPianificazioneAnnoSuperController";
    }  
    
    // ------------------------------------
    @RequestMapping(value="sintesiPianificazioneSuperController/{anno}", method = RequestMethod.GET)
    public String sintesiPianificazioneSuperAdministrator(@PathVariable("anno") int anno, Model model) {
    	//
    	Map<OpPersonaGiuridica,List<Obiettivo>> mapObiettiviReg = objServizi.mapObiettiviRegionalibyAnno(anno);
        model.addAttribute("mapObiettiviReg", mapObiettiviReg); 
        model.addAttribute("anno", anno);
        //
        return "supercontroller/sintesiPianificazioneAnnoSuperController";
    }  
    */
    // -----------------------------------
    //
    @RequestMapping(value="listPianificazioneAltroDipartimentoSuperController/{anno}/{id}", method = RequestMethod.GET)
    public String listPianificazioneAltroDipartimentoSuperAdministrator(
    		@PathVariable("anno") int anno,
    		@PathVariable("id") int idPersona,
    		 Model model) {
        final PersonaGiuridicaGeko dipartimento = fromOrganikoServizi.findPersonaGiuridicaById(idPersona);
        if (null!=dipartimento){
        	final Map<IncaricoGeko,List<Obiettivo>> mapObiettiviDept = objServizi.mapObiettiviDipartimentoIDAndAnno(menu.getIdDipartimentoScelta(), anno);
        	model.addAttribute("dipartimento", dipartimento);
        	model.addAttribute("mapObiettiviDept", mapObiettiviDept); 
            model.addAttribute("anno", anno);
        }
        return "supercontroller/listPianificazioneDipartimentoAnnoSuperController";
    }  
    
    
   
    
    //-----------------------Rendicontazione ---------------------------------
    //
    
    // lista rendicontazione del dipartimento
    @RequestMapping(value="listRendicontazioneAltroDipartimentoSuperController/{anno}/{id}", method = RequestMethod.GET)
    public String listRendicontazioneDipartimentoAdministrator(
    		@PathVariable("anno") int anno,
    		@PathVariable("id") int idPersona,
    		Model model) {
    	final PersonaGiuridicaGeko dipartimento = fromOrganikoServizi.findPersonaGiuridicaById(idPersona);
        if (null!=dipartimento){
        	model.addAttribute("dipartimento", dipartimento);
        	//Map<OpPersonaGiuridica,List<Obiettivo>> mapObiettiviDept = objServizi.mapObiettiviOfDipartimentoAndAnno(dipartimento, anno);
        	final Map<IncaricoGeko,List<Obiettivo>> mapObiettiviDept = objServizi.mapObiettiviDipartimentoIDAndAnno(menu.getIdDipartimentoScelta(), anno);
        	model.addAttribute("mapObiettiviDept", mapObiettiviDept); 
            model.addAttribute("anno", anno);
        }
        return "supercontroller/listRendicontazioneDipartimentoAnnoSuperController";
    }
    /*
    
    //
    // lista rendicontazione e azioni di altra struttura 
    @RequestMapping(value="listRendicontazioneAltraStrutturaAdministrator/{anno}/{idPersona}", method = RequestMethod.GET)
    public String listRendicontazioneOtherStructureAdministrator(
    		@PathVariable("anno") int anno, @PathVariable("idPersona") int idPersona, Model model) {
    	List<Obiettivo> obiettivi = new ArrayList();
        OpPersonaGiuridica struttura = pgServizi.findById(idPersona);
        //
        final String userName = ((OpPersonaFisica)struttura.getResponsabile()).toString();
        List<Obiettivo> listObiettivi;
        //
        model.addAttribute("userName", userName);
        model.addAttribute("anno", anno);
        if (null!=struttura){
            model.addAttribute("nomeStruttura", struttura.getDenominazione());
            listObiettivi = objServizi.findByStrutturaAndAnno(struttura,anno); 
            //listObiettivi = struttura.getObiettivi(); 
            model.addAttribute("listObiettivi", listObiettivi);
        }
        //
        return "administrator/listRendicontazioneAltraStrutturaAdministrator";
    }
    
 
    
    */
 
} // ---------------------------------------------
