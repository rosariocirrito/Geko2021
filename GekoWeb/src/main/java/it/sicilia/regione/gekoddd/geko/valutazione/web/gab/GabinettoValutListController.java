package it.sicilia.regione.gekoddd.geko.valutazione.web.gab;



import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazioneQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoQryService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.ValutazioneQryService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneComparto;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneCompartoQryService;
import it.sicilia.regione.gekoddd.geko.valutazione.web.manager.ManagerValutazioneListController;
import it.sicilia.regione.gekoddd.session.domain.Menu;

/**
 * @author Cirrito
 *
 */

@Controller
@RequestMapping("/gabValut")
public class GabinettoValutListController  {
	
	private static final Logger log = LoggerFactory.getLogger(GabinettoValutListController.class);
    
	//
    @Autowired
    private ObiettivoQryService objServizi;
    
    
    @Autowired
    private ValutazioneQryService valutazioneDirigServizi;
    
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    
    @Autowired
    private Menu menu;
    
    //
    public GabinettoValutListController() { }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
   
    
  // 3----------------------- Valutazione --------------------
    // 3.1 lista valutazioni del dipartimento
    @RequestMapping(value="listValutazioneApicaleGabinetto/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listValutazioneApicaleGabinettoQuery(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico,Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	//final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	final PersonaGiuridicaGeko dept = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);
    	List<Valutazione> lstValutDirig = valutazioneDirigServizi.findByIncaricoIDAndAnno(idIncarico, anno); // una sola in realt�
        incarico.setValutazioni(lstValutDirig);
    	//
        model.addAttribute("anno", anno);
        model.addAttribute("incarico", incarico);
        model.addAttribute("lstValutDirig", lstValutDirig);
        model.addAttribute("dipartimento", dept);
        //
        return "geko/valutazione/gabinetto/listValutazioneApicaleGabinetto";
    }
    
    // 3.2 modifica valutazioni del dipartimento
    @RequestMapping(value="modifyValutazioneApicaleGabinetto/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String modifyValutazioniDipartimentoQuery(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico,Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	//final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	final PersonaGiuridicaGeko dept = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);
    	List<Valutazione> lstValutDirig = valutazioneDirigServizi.findByIncaricoIDAndAnno(idIncarico, anno); // una sola in realt�
        incarico.setValutazioni(lstValutDirig);
    	//
        model.addAttribute("anno", anno);
        model.addAttribute("incarico", incarico);
        model.addAttribute("lstValutDirig", lstValutDirig);
        model.addAttribute("dipartimento", dept);
        return "geko/valutazione/gabinetto/modifyValutazioneApicaleGabinetto";
    }
    
 
    
 // -------------------------- pianificazione ----------------------
    // 3.5 lista obiettivi e azioni di altra struttura 
    @RequestMapping(value="navigaValutazioneIncaricoController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String navigaValutazioneIncaricoControllerGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	//
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	final PersonaGiuridicaGeko dept = fromOrganikoServizi.findDipartimentoByPersonaFisicaID(incarico.pfID);
    	final List<IncaricoGeko> listIncarichiDept = fromOrganikoServizi.findIncarichiByDipartimentoIDAndAnno(dept.idPersona, anno);
    	model.addAttribute("listIncarichiDept",listIncarichiDept);
    	
    	
    	List<Obiettivo> lstObjs = objServizi.findObiettiviPrioritariByIncaricoIDAndAnno(incarico.idIncarico, anno);
    	incarico.setObiettivi(lstObjs);
    	List<Valutazione> lstValutDirig = valutazioneDirigServizi.findByIncaricoIDAndAnno(idIncarico, anno); // una sola in realt�
    	incarico.setValutazioni(lstValutDirig);
        
        if (null != incarico.responsabile) {
        	model.addAttribute("incarico", incarico);
            model.addAttribute("anno", anno);
            model.addAttribute("listObiettivi", lstObjs);
            return "geko/valutazione/controller/navigaValutazioneIncaricoController";
        }
        else return "redirect:/ROLE_CONTROLLER";
    }
    
 
    
 
    
} // ------------------------------------

