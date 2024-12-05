package it.sicilia.regione.gekoddd.geko.programmazione.web.manager;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategico;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategicoCmdService;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategicoQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.application.ControllerProgrammazioneService;
import it.sicilia.regione.gekoddd.geko.programmazione.application.ManagerProgrammazioneService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettiviCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettiviQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettiviValidator;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoQryService;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


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
@SessionAttributes(types = AssociazObiettivi.class)
@RequestMapping("/managerAssocia")
public class ManagerAssociazObiettiviController  {
    
    @Autowired
    private AssociazObiettiviCmdService associazObiettiviCmdServizi;
    @Autowired
    private AssociazObiettiviQryService associazObiettiviQryServizi;
    @Autowired
    private ObiettivoCmdService objCmdServizi;
    @Autowired
    private ObiettivoStrategicoCmdService obiettiviStrategiciCmdServizi;
    @Autowired
    private ObiettivoStrategicoQryService obiettiviStrategiciQryServizi;
    @Autowired
    private ManagerProgrammazioneService managerServizi;
    
    @Autowired
    private Menu menu;
   
    public ManagerAssociazObiettiviController() { }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }
    
    
    @RequestMapping(value ="{id}/edit/{idIncarico}" , method = RequestMethod.GET )
    public String updateForm(@PathVariable("id") int id, @PathVariable("idIncarico") int idIncarico, Model model) {
        AssociazObiettivi associazObiettivi = associazObiettiviCmdServizi.findById(id); 
        Obiettivo obj = associazObiettivi.getApicale();
        obj.setIdIncaricoApicale(idIncarico); // fa niente se non è apicale
        //
        //final OpPersonaGiuridica dipartimento = userServizi.findDipartimentoOfLoggedUser();
		//if (dipartimento.equals(associazObiettivi.getApicale().getDipartimento())){
	            model.addAttribute("associazObiettivi", associazObiettivi);
	            return "geko/programmazione/manager/formAssociazObiettiviEditManager";
	      //  }
	      //  else return "redirect:/controller/associaPianificazioneStrategicaDipartimentoController/"+associazObiettivi.getApicale().getAnno();
	    }


    @RequestMapping(value ="{id}/edit/{idIncarico}" , params = "update", method =  RequestMethod.PUT )
    public String processUpdateSubmit(@ModelAttribute AssociazObiettivi associazObiettivi, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new AssociazObiettiviValidator().validate(associazObiettivi, result);
		if (result.hasErrors()) {
            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
            return "geko/programmazione/manager/formAssociazObiettiviEditManager";
		}
		else {
            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
            this.managerServizi.managerUpdateAssociazObiettivi(associazObiettivi);
            status.setComplete();
            return "redirect:/dirigente/associaPianificazioneStrategicaIncaricoManager/"+menu.getAnno()+
            		"/"+associazObiettivi.getApicale().getIdIncaricoApicale();
		}
    }
    
    @RequestMapping(value ="{id}/edit/{idIncarico}" , params = "delete", method =  RequestMethod.PUT )
    public String processDeleteSubmit(@ModelAttribute AssociazObiettivi associazObiettivi, 
                                BindingResult result, 
                                SessionStatus status) {
        this.managerServizi.managerDeleteAssociazObiettivi(associazObiettivi);
        //
        return "redirect:/dirigente/associaPianificazioneStrategicaIncaricoManager/"+menu.getAnno()+
        		"/"+associazObiettivi.getApicale().getIdIncaricoApicale();
    }
    
    @RequestMapping(value ="{id}/edit/{idIncarico}" , params = "cancel", method =  RequestMethod.PUT )
    public String processCancelSubmit() {
        return "redirect:/ROLE_MANAGER";
    }

    // ------------------------------------------------------------------------
    // crea AssociazObiettivi
    @RequestMapping(value="new/{idObiettivo}/{idIncarico}", method = RequestMethod.GET)
    public String createAssociazObiettiviGet(@PathVariable("idObiettivo") int idObiettivo, @PathVariable("idIncarico") int idIncarico, Model model) {
        //
        Obiettivo obj = objCmdServizi.findById(idObiettivo);
        obj.setIdIncaricoApicale(idIncarico);
        AssociazObiettivi associazObiettivi = new AssociazObiettivi();
        associazObiettivi.setApicale(obj); 
        List<AssociazObiettivi> listAssocObjEsistenti = associazObiettiviQryServizi.findByApicale(obj);
        Map<Integer,String> mapSelectObjStrateg = obiettiviStrategiciQryServizi.mapSelectObiettiviStrategici(menu.getAnno()); 
        
        //
        for(AssociazObiettivi assoc : listAssocObjEsistenti){
        	mapSelectObjStrateg.remove(assoc.getStrategico().getId());
        }
        //azione.addAssegnazioneToAzione(assegnazione); // che succede se tolgo visto che sono in Get
        model.addAttribute("associazObiettivi", associazObiettivi);
        model.addAttribute("listAssocObjEsistenti",listAssocObjEsistenti);
        model.addAttribute("mapSelectObjStrateg",mapSelectObjStrateg);
        
        return "geko/programmazione/manager/formAssociazObiettiviCreateManager";
    }
    
    @RequestMapping(value="new/{idObiettivo}/{idIncarico}", params = "add", method = RequestMethod.POST)
    public String createAssociazObiettiviPost(@ModelAttribute AssociazObiettivi associazObiettivi, 
                                BindingResult result, 
                                SessionStatus status) {
        //
	new AssociazObiettiviValidator().validate(associazObiettivi, result);
        if (result.hasErrors()) {
            return "geko/programmazione/manager/formAssociazObiettiviCreateManager";
        }
        else {
            ObiettivoStrategico objStrategico = obiettiviStrategiciCmdServizi.findById(associazObiettivi.getIdObiettivoStrategico());
        	//ObiettivoStrategico objStrategico = obiettiviStrategiciServizi.findByDescrizione(associazObiettivi.getStrObiettivoStrategico());
        	//System.out.println("AssociazObiettiviAddForm.processSubmit() cognome="+dip.getCognome());
            associazObiettivi.setStrategico(objStrategico);
            //this.servizi.saveOrUpdateAssociazObiettivi(associazObiettivi);
            this.managerServizi.managerCreateAssociazObiettivi(associazObiettivi);
            status.setComplete();
        
       // return "redirect:/manager/associaPianificazioneStrategicaIncaricoManager/"+
        //menu.getAnno()+"/"+associazObiettivi.getApicale().getIdIncaricoApicale();
            return "redirect:/dirigente/associaPianificazioneStrategicaIncaricoManager/"+menu.getAnno()+
            		"/"+associazObiettivi.getApicale().getIdIncaricoApicale();
        }
    }
    //
    @RequestMapping(value="new/{idObiettivo}/{idIncarico}", params = "cancel",method = RequestMethod.POST)
    public String newCancel() {
    	return "redirect:/ROLE_MANAGER";
    }
    
    // apicale
    /*
 // ------------------------------------------------------------------------
    // crea AssociazObiettivi
    @RequestMapping(value="newApicale/{idObiettivo}", method = RequestMethod.GET)
    public String createAssociazObiettiviApicaleGet(@PathVariable("idObiettivo") int idObiettivo,Model model) {
        //
    	//final OpPersonaGiuridica dipartimento = userServizi.findDipartimentoOfLoggedUser();
        ObiettivoStrategico strategico = obiettiviStrategiciCmdServizi.findById(idObiettivo);
        AssociazObiettivi associazObiettivi = new AssociazObiettivi();
        associazObiettivi.setStrategico(strategico); 
        List<AssociazObiettivi> listAssocObjEsistenti = associazObiettiviQryServizi.findByStrategico(strategico);
        Map<Integer,String> mapSelectObjApicali = objQryServizi.mapSelectObiettiviApicaliByDipartimentoIDAndAnno(menu.getDeptID(), strategico.getAnno()); 
        
        for(AssociazObiettivi assoc : listAssocObjEsistenti){
        	mapSelectObjApicali.remove(assoc.getStrategico().getId());
        }
        //azione.addAssegnazioneToAzione(assegnazione); // che succede se tolgo visto che sono in Get
        model.addAttribute("associazObiettivi", associazObiettivi);
        model.addAttribute("listAssocObjEsistenti",listAssocObjEsistenti);
        model.addAttribute("mapSelectObjApicali",mapSelectObjApicali);
        
        return "geko/programmazione/manager/formAssociazObiettiviApicaliCreateController";
    }
    
    @RequestMapping(value="newApicale/{idObiettivo}", params = "add", method = RequestMethod.POST)
    public String createAssociazObiettiviApicalePost(@ModelAttribute AssociazObiettivi associazObiettivi, 
                                BindingResult result, 
                                SessionStatus status) {
        //
	new AssociazObiettiviValidator().validate(associazObiettivi, result);
        if (result.hasErrors()) {
            return "geko/programmazione/manager/formAssociazObiettiviApicaliCreateController";
        }
        else {
            Obiettivo obj = objCmdServizi.findById(associazObiettivi.getIdObiettivoApicale());
            //System.out.println("AssociazObiettiviAddForm.processSubmit() cognome="+dip.getCognome());
            associazObiettivi.setApicale(obj);
            //this.servizi.saveOrUpdateAssociazObiettivi(associazObiettivi);
            this.managerServizi.controllerCreateAssociazObiettivi(associazObiettivi);
            status.setComplete();
        }
        return "redirect:/manager/associaPianificazioneApicaleDipartimentoController/"+associazObiettivi.getApicale().getAnno();
    }
    //
    @RequestMapping(value="newApicale/{idObiettivo}", params = "cancel",method = RequestMethod.POST)
    public String newApicaleCancel() {
    	return "redirect:/ROLE_MANAGER";
    }
    */
    
    
}//--------------------------
