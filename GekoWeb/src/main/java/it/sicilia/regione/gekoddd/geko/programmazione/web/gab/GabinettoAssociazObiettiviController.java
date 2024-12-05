package it.sicilia.regione.gekoddd.geko.programmazione.web.gab;

import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategico;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategicoCmdService;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategicoQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.application.GabinettoProgrammazioneService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettiviCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettiviQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettiviValidator;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoCmdService;
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
@RequestMapping("/gabinettoAssocia")
public class GabinettoAssociazObiettiviController  {
    
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
    private GabinettoProgrammazioneService gabinettoServizi;
    
    @Autowired
    private Menu menu;
   
    public GabinettoAssociazObiettiviController() { }
    
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
    
    // ----------------- Modifica Associazione ------------------------------------------------------------------------
    // GET
    @RequestMapping(value ="{id}/edit/{idIncaricoApicale}" , method = RequestMethod.GET )
    public String updateForm(@PathVariable("id") int id, @PathVariable("idIncaricoApicale") int idIncaricoApicale, Model model) {
        AssociazObiettivi associazObiettivi = associazObiettiviCmdServizi.findById(id); 
        Obiettivo obj = associazObiettivi.getApicale();
        obj.setIdIncaricoApicale(idIncaricoApicale);
        Map<Integer,String> mapSelectObjStrateg = obiettiviStrategiciQryServizi.mapSelectObiettiviStrategici(menu.getAnno()); 
        //
        //final OpPersonaGiuridica dipartimento = userServizi.findDipartimentoOfLoggedUser();
		//if (dipartimento.equals(associazObiettivi.getApicale().getDipartimento())){
	            model.addAttribute("associazObiettivi", associazObiettivi);
	            model.addAttribute("mapSelectObjStrateg",mapSelectObjStrateg);
	            return "geko/programmazione/gab/formAssociazObiettiviEditGabinetto";
	      //  }
	      //  else return "redirect:/controller/associaPianificazioneStrategicaDipartimentoController/"+associazObiettivi.getApicale().getAnno();
	}

    // PUT - update
    @RequestMapping(value ="{id}/edit/{idIncaricoApicale}" , params = "update", method =  RequestMethod.PUT )
    public String processUpdateSubmit(@ModelAttribute AssociazObiettivi associazObiettivi, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new AssociazObiettiviValidator().validate(associazObiettivi, result);
		if (result.hasErrors()) {
            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
            return "geko/programmazione/gab/formAssociazObiettiviEditGabinetto";
		}
		else {
			ObiettivoStrategico objStrategico = obiettiviStrategiciCmdServizi.findById(associazObiettivi.getIdObiettivoStrategico());
        	associazObiettivi.setStrategico(objStrategico);
            this.gabinettoServizi.gabinettoUpdateAssociazObiettivi(associazObiettivi);
            status.setComplete();
            return "redirect:/gabProg/associaPianificazioneStrategicaIncaricoApicaleGab/"+
            menu.getAnno()+"/"+associazObiettivi.getApicale().getIdIncaricoApicale();
		}
    }
    
    // PUT - delete
    @RequestMapping(value ="{id}/edit/{idIncaricoApicale}" , params = "delete", method =  RequestMethod.PUT )
    public String processDeleteSubmit(@ModelAttribute AssociazObiettivi associazObiettivi, 
                                BindingResult result, 
                                SessionStatus status) {
        this.gabinettoServizi.gabinettoDeleteAssociazObiettivi(associazObiettivi);
        //
        return "redirect:/gabProg/associaPianificazioneStrategicaIncaricoApicaleGab/"+
        menu.getAnno()+"/"+associazObiettivi.getApicale().getIdIncaricoApicale();  }
    
    @RequestMapping(value ="{id}/edit/{idIncaricoApicale}" , params = "cancel", method =  RequestMethod.PUT )
    public String processCancelSubmit() {
        return "redirect:/ROLE_GABINETTO";
    }

    
    // ----------------- Crea nuova Associazione ------------------------------------------------------------------------
    // GET
    @RequestMapping(value="new/{idObiettivo}/{idIncaricoApicale}", method = RequestMethod.GET)
    public String createAssociazObiettiviGet(@PathVariable("idObiettivo") int idObiettivo,
    		@PathVariable("idIncaricoApicale") int idIncaricoApicale, Model model) {
        //
        Obiettivo apicale = objCmdServizi.findById(idObiettivo);
        apicale.setIdIncaricoApicale(idIncaricoApicale);
        AssociazObiettivi associazObiettivi = new AssociazObiettivi();
        associazObiettivi.setApicale(apicale); 
        List<AssociazObiettivi> listAssocObjEsistenti = associazObiettiviQryServizi.findByApicale(apicale);
        Map<Integer,String> mapSelectObjStrateg = obiettiviStrategiciQryServizi.mapSelectObiettiviStrategici(menu.getAnno()); 
        
        //
        for(AssociazObiettivi assoc : listAssocObjEsistenti){
        	mapSelectObjStrateg.remove(assoc.getStrategico().getId());
        }
        //azione.addAssegnazioneToAzione(assegnazione); // che succede se tolgo visto che sono in Get
        model.addAttribute("idIncaricoApicale", idIncaricoApicale);
        model.addAttribute("associazObiettivi", associazObiettivi);
        model.addAttribute("listAssocObjEsistenti",listAssocObjEsistenti);
        model.addAttribute("mapSelectObjStrateg",mapSelectObjStrateg);
        
        return "geko/programmazione/gab/formAssociazObiettiviCreateGabinetto";
    }
    
    // POST - Add
    @RequestMapping(value="new/{idObiettivo}/{idIncaricoApicale}", params = "add", method = RequestMethod.POST)
    public String createAssociazObiettiviPost(@ModelAttribute AssociazObiettivi associazObiettivi, 
                                BindingResult result, 
                                SessionStatus status) {
        //
	new AssociazObiettiviValidator().validate(associazObiettivi, result);
        if (result.hasErrors()) {
            return "geko/programmazione/gab/formAssociazObiettiviCreateGabinetto";
        }
        else {
            ObiettivoStrategico objStrategico = obiettiviStrategiciCmdServizi.findById(associazObiettivi.getIdObiettivoStrategico());
        	associazObiettivi.setStrategico(objStrategico);
            //this.servizi.saveOrUpdateAssociazObiettivi(associazObiettivi);
            this.gabinettoServizi.gabinettoCreateAssociazObiettivi(associazObiettivi);
            status.setComplete();
        //associaPianificazioneStrategicaIncaricoApicaleGabinetto
        return "redirect:/gabProg/associaPianificazioneStrategicaIncaricoApicaleGab/"+
        associazObiettivi.getApicale().getAnno()+"/"+associazObiettivi.getApicale().getIdIncaricoApicale();
        }
    }
    
    // POST - cancel
    @RequestMapping(value="new/{idObiettivo}/{idIncaricoApicale}", params = "cancel",method = RequestMethod.POST)
    public String newCancel() {
    	return "redirect:/ROLE_GABINETTO";
    }
    
      
    
    /* obs
 // ------------------------------------------------------------------------
   // apicale
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
        
        return "geko/programmazione/controller/formAssociazObiettiviApicaliCreateController";
    }
    
    @RequestMapping(value="newApicale/{idObiettivo}", params = "add", method = RequestMethod.POST)
    public String createAssociazObiettiviApicalePost(@ModelAttribute AssociazObiettivi associazObiettivi, 
                                BindingResult result, 
                                SessionStatus status) {
        //
	new AssociazObiettiviValidator().validate(associazObiettivi, result);
        if (result.hasErrors()) {
            return "geko/programmazione/controller/formAssociazObiettiviApicaliCreateController";
        }
        else {
            Obiettivo obj = objCmdServizi.findById(associazObiettivi.getIdObiettivoApicale());
            //System.out.println("AssociazObiettiviAddForm.processSubmit() cognome="+dip.getCognome());
            associazObiettivi.setApicale(obj);
            //this.servizi.saveOrUpdateAssociazObiettivi(associazObiettivi);
            this.controllerServizi.controllerCreateAssociazObiettivi(associazObiettivi);
            status.setComplete();
        }
        return "redirect:/controller/associaPianificazioneApicaleDipartimentoController/"+associazObiettivi.getApicale().getAnno();
    }
    //
    @RequestMapping(value="newApicale/{idObiettivo}", params = "cancel",method = RequestMethod.POST)
    public String newApicaleCancel() {
    	return "redirect:/ROLE_CONTROLLER";
    }*/
    
    
    
}//--------------------------
