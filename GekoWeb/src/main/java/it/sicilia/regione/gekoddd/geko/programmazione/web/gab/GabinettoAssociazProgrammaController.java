package it.sicilia.regione.gekoddd.geko.programmazione.web.gab;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.application.GabinettoProgrammazioneService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgramma;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgrammaCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgrammaQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgrammaValidator;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.programma.Programma;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.programma.ProgrammaCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.programma.ProgrammaQryService;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
@SessionAttributes(types = AssociazProgramma.class)
@RequestMapping("/gabinettoAssociaProgramma")
public class GabinettoAssociazProgrammaController  {
	
	private Log log = LogFactory.getLog(GabinettoAssociazProgrammaController.class);
	
    @Autowired
    private AssociazProgrammaCmdService associazProgrammaCmdServizi;
    @Autowired
    private AssociazProgrammaQryService associazProgrammaQryServizi;
    @Autowired
    private ObiettivoCmdService objCmdServizi;
    @Autowired
    private ObiettivoQryService objQryServizi;
    @Autowired
    private ProgrammaCmdService programmaCmdServizi;
    @Autowired
    private ProgrammaQryService programmaQryServizi;
    @Autowired
    private GabinettoProgrammazioneService gabinettoServizi;
    
    @Autowired
    private Menu menu;
   
    public GabinettoAssociazProgrammaController() { }
    
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
        AssociazProgramma associazProgramma = associazProgrammaCmdServizi.findById(id); 
        Obiettivo obj = associazProgramma.getApicale();
        obj.setIdIncaricoApicale(idIncaricoApicale);
        Map<Integer,String> mapSelectProgramma = programmaQryServizi.mapSelectProgramma(); 
        //
        model.addAttribute("associazProgramma", associazProgramma);
        model.addAttribute("mapSelectProgramma",mapSelectProgramma);
        return "geko/programmazione/gab/formAssociazProgrammaEditGabinetto";
	}

    // PUT - update
    @RequestMapping(value ="{id}/edit/{idIncaricoApicale}" , params = "update", method =  RequestMethod.PUT )
    public String processUpdateSubmit(@ModelAttribute AssociazProgramma associazProgramma, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuGabinetto.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new AssociazProgrammaValidator().validate(associazProgramma, result);
		if (result.hasErrors()) {
            //System.out.println("ManagerMenuGabinetto.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
            return "geko/programmazione/gab/formAssociazProgrammaEditGabinetto";
		}
		else {
			Programma programma = programmaCmdServizi.findById(associazProgramma.getIdProgrammaScelto());
        	associazProgramma.setProgramma(programma);
            this.gabinettoServizi.gabinettoUpdateAssociazProgramma(associazProgramma);
            status.setComplete();
            //associaProgrammaIncaricoApicaleGab
            return "redirect:/gabProg/associaProgrammaIncaricoApicaleGab/"+
            menu.getAnno()+"/"+associazProgramma.getApicale().getIdIncaricoApicale();
		}
    }
    
    // PUT - delete
    @RequestMapping(value ="{id}/edit/{idIncaricoApicale}" , params = "delete", method =  RequestMethod.PUT )
    public String processDeleteSubmit(@ModelAttribute AssociazProgramma associazProgramma, 
                                BindingResult result, 
                                SessionStatus status) {
        this.gabinettoServizi.gabinettoDeleteAssociazProgramma(associazProgramma);
        //
        return "redirect:/gabProg/associaProgrammaIncaricoApicaleGab/"+
        menu.getAnno()+"/"+associazProgramma.getApicale().getIdIncaricoApicale();  }
    
    @RequestMapping(value ="{id}/edit/{idIncaricoApicale}" , params = "cancel", method =  RequestMethod.PUT )
    public String processCancelSubmit() {
        return "redirect:/ROLE_GABINETTO";
    }

    
    // ----------------- Crea nuova Associazione ------------------------------------------------------------------------
    // GET
    @RequestMapping(value="new/{idObiettivo}/{idIncaricoApicale}", method = RequestMethod.GET)
    public String createAssociazProgrammaGet(@PathVariable("idObiettivo") int idObiettivo,
    		@PathVariable("idIncaricoApicale") int idIncaricoApicale, Model model) {
        //
        Obiettivo apicale = objCmdServizi.findById(idObiettivo);
        apicale.setIdIncaricoApicale(idIncaricoApicale);
        AssociazProgramma associazProgramma = new AssociazProgramma();
        associazProgramma.setApicale(apicale); 
        List<AssociazProgramma> listAssocPrgEsistenti = associazProgrammaQryServizi.findByApicale(apicale);
        Map<Integer,String> mapSelectProgramma = programmaQryServizi.mapSelectProgramma(); 
        
        //
        for(AssociazProgramma assoc : listAssocPrgEsistenti){
        	mapSelectProgramma.remove(assoc.getProgramma().getId());
        }
        //azione.addAssegnazioneToAzione(assegnazione); // che succede se tolgo visto che sono in Get
        
        model.addAttribute("associazProgramma", associazProgramma);
        model.addAttribute("listAssocPrgEsistenti",listAssocPrgEsistenti);
        model.addAttribute("mapSelectProgramma",mapSelectProgramma);
        
        return "geko/programmazione/gab/formAssociazProgrammaCreateGabinetto";
    }
    
    // POST - Add
    @RequestMapping(value="new/{idObiettivo}/{idIncaricoApicale}", params = "add", method = RequestMethod.POST)
    public String createAssociazProgrammaPost(@ModelAttribute AssociazProgramma associazProgramma, 
                                BindingResult result, 
                                SessionStatus status) {
        //
	new AssociazProgrammaValidator().validate(associazProgramma, result);
        if (result.hasErrors()) {
            return "geko/programmazione/gab/formAssociazProgrammaCreateGabinetto";
        }
        else {
        	log.info("createAssociazProgrammaPost IdProgrammaScelto = "+ associazProgramma.getIdProgrammaScelto());
            Programma programma = programmaCmdServizi.findById(associazProgramma.getIdProgrammaScelto());
            log.info("createAssociazProgrammaPost IdProgramma = "+ programma.getId());
        	associazProgramma.setProgramma(programma);        	
            this.gabinettoServizi.gabinettoCreateAssociazProgramma(associazProgramma);
            status.setComplete();
        
        return "redirect:/gabProg/associaProgrammaIncaricoApicaleGab/"+
        associazProgramma.getApicale().getAnno()+"/"+associazProgramma.getApicale().getIdIncaricoApicale();
        }
    }
    
    // POST - cancel
    @RequestMapping(value="new/{idObiettivo}/{idIncaricoApicale}", params = "cancel",method = RequestMethod.POST)
    public String newCancel() {
    	return "redirect:/ROLE_GABINETTO";
    }
    
   
    
    
   
    
    
    
}//--------------------------
