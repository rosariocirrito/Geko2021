package it.sicilia.regione.gekoddd.geko.valutazione.web.controller;



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
import it.sicilia.regione.gekoddd.session.domain.Menu;

/**
 * @author Cirrito
 *
 */

@Controller
@RequestMapping("/controllerValut")
public class ControllerValutListController  {
	
	private static final Logger log = LoggerFactory.getLogger(ControllerValutListController.class);
    
	//
    @Autowired
    private ObiettivoQryService objServizi;
    
    @Autowired
    private ValutazioneQryService valutazioneDirigServizi;   
    
    @Autowired
    private AzioneAssegnazioneQryService azAssServizi;
    @Autowired
    private ValutazioneCompartoQryService valutazioneCompartoServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    
    @Autowired
    private Menu menu;
    
    //
    public ControllerValutListController() { }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
   
    
  // 4----------------------- Valutazione --------------------
    // 4.1 lista valutazioni del dipartimento
    @RequestMapping(value="listValutazioneDipartimentaleController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listValutazioniDipartimentoQuery(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico,Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	//final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	final PersonaGiuridicaGeko dept = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);
    	final List<IncaricoGeko> listIncarichiDept = fromOrganikoServizi.findIncarichiByDipartimentoIDAndAnno(incarico.pgID, anno);
    	for (IncaricoGeko inc : listIncarichiDept){
    		List<Obiettivo> lstObjs = objServizi.findObiettiviPrioritariByIncaricoIDAndAnno(inc.idIncarico, anno);
    		inc.setObiettivi(lstObjs);
        	List<Valutazione> lstValutDirig = valutazioneDirigServizi.findByIncaricoIDAndAnno(inc.idIncarico, anno); // una sola in realt�
        	inc.setValutazioni(lstValutDirig);
    	}
    	//
        model.addAttribute("anno", anno);
        model.addAttribute("listIncarichiDept", listIncarichiDept);
        model.addAttribute("dipartimento", dept);
        //
        String pageName = "geko/valutazione/controller/listValutazioniDipartimentoController";
    	model.addAttribute("pageName", pageName);
        return pageName; 
    }
    
    // 4.2 modifica valutazioni del dipartimento
    @RequestMapping(value="modifyValutazioneDipartimentaleController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String modifyValutazioniDipartimentoQuery(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico,Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	//final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	final PersonaGiuridicaGeko dept = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);
    	final List<IncaricoGeko> listIncarichiDept = fromOrganikoServizi.findIncarichiByDipartimentoIDAndAnno(incarico.pgID, anno);
    	
    	for (IncaricoGeko inc : listIncarichiDept){
    		List<Obiettivo> lstObjs = objServizi.findObiettiviPrioritariByIncaricoIDAndAnno(inc.idIncarico, anno);
    		inc.setObiettivi(lstObjs);
        	List<Valutazione> lstValutDirig = valutazioneDirigServizi.findByIncaricoIDAndAnno(inc.idIncarico, anno); // una sola in realt�
        	
        	if (lstValutDirig!=null && !lstValutDirig.isEmpty()) {
        		inc.setValutazioni(lstValutDirig);
        	}
        	else {
        		List<Valutazione> emptyList = new ArrayList<Valutazione>();
        		inc.setValutazioni(lstValutDirig);
        	}
        	
    	}
    	model.addAttribute("listIncarichiDept", listIncarichiDept);
        model.addAttribute("anno", anno);
        model.addAttribute("dipartimento", dept);
        String pageName = "geko/valutazione/controller/modifyValutazioniDipartimentoController";
    	model.addAttribute("pageName", pageName);
        return pageName; 
    }
    
 
    // 4.3 lista valutazioni di incarico
    @RequestMapping(value="listValutazioneIncaricoController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listValutazioneIncaricoControllerGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> lstObjs = objServizi.findObiettiviPrioritariByIncaricoIDAndAnno(incarico.idIncarico, anno);
    	//incarico.setObiettivi(lstObjs);
    	List<Valutazione> lstValutDirig = valutazioneDirigServizi.findByIncaricoIDAndAnno(idIncarico, anno); // una sola in realt�
    	if (lstValutDirig!=null && !lstValutDirig.isEmpty()) {
    		//
    	}
    	else {
    		lstValutDirig = new ArrayList<Valutazione>();
    	}
    	model.addAttribute("incarico", incarico);
    	model.addAttribute("obiettivi", lstObjs);
    	model.addAttribute("valutazioni", lstValutDirig);
    	model.addAttribute("anno", anno);
        //
    	String pageName = "geko/valutazione/controller/listValutazioneIncaricoController";
    	model.addAttribute("pageName", pageName);
        return pageName;    	 
    }
      
    
    // 4.4 lista obiettivi e azioni di altra struttura 
    @RequestMapping(value="navigaValutazioneIncaricoController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String navigaValutazioneIncaricoControllerGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	//
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	final PersonaGiuridicaGeko dept = fromOrganikoServizi.findDipartimentoByPersonaFisicaID(incarico.pfID);
    	final List<IncaricoGeko> listIncarichiDept = fromOrganikoServizi.findIncarichiByDipartimentoIDAndAnno(dept.idPersona, anno);
    	model.addAttribute("listIncarichiDept",listIncarichiDept);
    	//final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> lstObjs = objServizi.findObiettiviPrioritariByIncaricoIDAndAnno(incarico.idIncarico, anno);
    	log.info("modifyValutazioneIncaricoController lstObjs.size()="+lstObjs.size());
    	//incarico.setObiettivi(lstObjs);
    	List<Valutazione> lstValutDirig = valutazioneDirigServizi.findByIncaricoIDAndAnno(idIncarico, anno); // una sola in realt�
    	
    	if (lstValutDirig!=null && !lstValutDirig.isEmpty()) {
    		//
    	}
    	else {
    		lstValutDirig = new ArrayList<Valutazione>();
    	}
    	
        if (null != incarico.responsabile) {
        	model.addAttribute("incarico", incarico);
            model.addAttribute("anno", anno);
            model.addAttribute("listObiettivi", lstObjs);
            model.addAttribute("obiettivi", lstObjs);
        	model.addAttribute("valutazioni", lstValutDirig);
        	String pageName = "geko/valutazione/controller/navigaValutazioneIncaricoController";
        	model.addAttribute("pageName", pageName);
            return pageName; 
        }
        else return "redirect:/ROLE_CONTROLLER";
    }
    
 // 3.6 modifica valutazioni di altra struttura
    @RequestMapping(value="modifyValutazioneIncaricoController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String modifyValutazioneIncaricoControllerGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> lstObjs = objServizi.findObiettiviPrioritariByIncaricoIDAndAnno(incarico.idIncarico, anno);
    	log.info("modifyValutazioneIncaricoController lstObjs.size()="+lstObjs.size());
    	//incarico.setObiettivi(lstObjs);
    	List<Valutazione> lstValutDirig = valutazioneDirigServizi.findByIncaricoIDAndAnno(idIncarico, anno); // una sola in realt�
    	
    	if (lstValutDirig!=null && !lstValutDirig.isEmpty()) {
    		//
    	}
    	else {
    		lstValutDirig = new ArrayList<Valutazione>();
    	}
    	
    	//incarico.setValutazioni(lstValutDirig);
    	model.addAttribute("incarico", incarico);
    	model.addAttribute("obiettivi", lstObjs);
    	model.addAttribute("valutazioni", lstValutDirig);
    	model.addAttribute("anno", anno);
        //
    	String pageName = "geko/valutazione/controller/modifyValutazioneIncaricoController";
    	model.addAttribute("pageName", pageName);
        return pageName; 	 
    }    
 
    // -------------------------------------
    // modifica valutazione dipendenti della struttura e sottostruttura
    @RequestMapping(value="modifyDipendentiAssegnazioniValutazione/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String modifyDipendentiAssegnazioniValutazioneIncaricoGet(@PathVariable("anno") int anno, 
    		@PathVariable("idIncarico") int idIncarico,	Model model) {
        //
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	final PersonaFisicaGeko responsabile = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	final PersonaGiuridicaGeko struttura = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);
        List<PersonaFisicaGeko> listDipendenti = fromOrganikoServizi.findDipendentiByDipartimentoIDAndAnno(incarico.pgID, anno); 
        
        // listDipendenti.remove(responsabile);
        Map<PersonaFisicaGeko,List<AzioneAssegnazione>> mapDipendentiAssegnazioni = new LinkedHashMap<PersonaFisicaGeko,List<AzioneAssegnazione>>();
        Map<PersonaFisicaGeko,List<ValutazioneComparto>> mapDipendentiValutazioneComparto = new LinkedHashMap<PersonaFisicaGeko,List<ValutazioneComparto>>();
        if (listDipendenti!= null && !listDipendenti.isEmpty()){
	        for(PersonaFisicaGeko pf : listDipendenti){
	        	pf.setAnno(anno);
	        	pf.setIncaricoValutazioneID(idIncarico);
	        	List<AzioneAssegnazione> assegnazioni = azAssServizi.findByPfIDAndIncaricoIDAndAnno(pf.idPersona, idIncarico, anno);
	        	
	        	if (assegnazioni != null && !assegnazioni.isEmpty()){
	        		pf.setAssegnazioni(assegnazioni);
	        		mapDipendentiAssegnazioni.put(pf, assegnazioni);
	        	}
	        	//
	        	List<ValutazioneComparto> valutazioni = valutazioneCompartoServizi.findByPfIDAndIncaricoIDAndAnno(pf.idPersona, incarico.idIncarico, anno);
	        	if (valutazioni!=null && !valutazioni.isEmpty()) {
	        		pf.setValutazioni(valutazioni);
	            	mapDipendentiValutazioneComparto.put(pf, valutazioni);
	        	}
	        	else {
	        		List<ValutazioneComparto> emptyList = new ArrayList<ValutazioneComparto>();
	        		pf.setValutazioni(valutazioni);
	            	mapDipendentiValutazioneComparto.put(pf, valutazioni);
	        	}
	        }
        }
        log.info("struttura = "+incarico.getDenominazioneStruttura());
        model.addAttribute("mapDipendentiAssegnazioni", mapDipendentiAssegnazioni); 
        model.addAttribute("mapDipendentiValutazioneComparto", mapDipendentiValutazioneComparto); 
        model.addAttribute("struttura", incarico.getDenominazioneStruttura());
        model.addAttribute("anno", anno);
        model.addAttribute("idIncarico", idIncarico);
        //
        return "geko/valutazione/controller/modifyValutazioneCompartoController";
    }
    
    // lista valutazione dipendenti della struttura e sottostruttura
    @RequestMapping(value="listDipendentiAssegnazioniValutazione/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listDipendentiAssegnazioniValutazioneGet(@PathVariable("anno") int anno, 
    		@PathVariable("idIncarico") int idIncarico,
    		Model model) {
        //
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	final PersonaFisicaGeko responsabile = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	log.info("responsabile =" +responsabile.stringa+"con IDIncarico="+incarico.idIncarico);
		//
        List<PersonaFisicaGeko> listDipendenti = fromOrganikoServizi.findDipendentiByDipartimentoIDAndAnno(incarico.pgID, anno); 
        //listDipendenti.remove(responsabile);
        //
        Map<PersonaFisicaGeko,List<AzioneAssegnazione>> mapDipendentiAssegnazioni = new LinkedHashMap<PersonaFisicaGeko,List<AzioneAssegnazione>>();
        Map<PersonaFisicaGeko,List<ValutazioneComparto>> mapDipendentiValutazioneComparto = new LinkedHashMap<PersonaFisicaGeko,List<ValutazioneComparto>>();
        if (listDipendenti!= null && !listDipendenti.isEmpty()){
        for(PersonaFisicaGeko pf : listDipendenti){
        	log.info("pf =" +pf.stringa+"con ID"+pf.idPersona);
        	pf.setAnno(anno);
        	pf.setIncaricoValutazioneID(incarico.getIdIncarico());
        	//
        	// List<AzioneAssegnazione> assegnazioni = azAssServizi.findByPfIDAndIncaricoIDAndAnno(pfID, incaricoID, anno);
        	List<AzioneAssegnazione> assegnazioni = azAssServizi.findByPfIDAndIncaricoIDAndAnno(pf.idPersona, incarico.idIncarico, anno);
        	
        	if (assegnazioni!=null && !assegnazioni.isEmpty()) {
        		pf.setAssegnazioni(assegnazioni);
        		mapDipendentiAssegnazioni.put(pf, assegnazioni);
        	}
        	//
        	List<ValutazioneComparto> valutazioni = valutazioneCompartoServizi.findByPfIDAndIncaricoIDAndAnno(pf.idPersona, incarico.idIncarico, anno);
        	if (valutazioni!=null && !valutazioni.isEmpty()) {
        		pf.setValutazioni(valutazioni);
        	    mapDipendentiValutazioneComparto.put(pf, valutazioni);
        	}
        	
        }
        }
        //
        model.addAttribute("mapDipendentiAssegnazioni", mapDipendentiAssegnazioni);  
        model.addAttribute("mapDipendentiValutazioneComparto", mapDipendentiValutazioneComparto);  
        model.addAttribute("struttura", incarico.denominazioneStruttura);
        model.addAttribute("anno", anno);
        //
        return "geko/valutazione/controller/listValutazioneCompartoController";
    }
    
    
    
 // modifica valutazione dipendenti della struttura e sottostruttura
    @RequestMapping(value="modifyValutazioneStrutturaManager/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String modifyValutazioneStrutturaManagerGet(@PathVariable("anno") int anno, 
    		@PathVariable("idIncarico") int idIncarico,	Model model) {
        //
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	final PersonaFisicaGeko responsabile = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	final PersonaGiuridicaGeko struttura = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);
    	List<PersonaFisicaGeko> listDipendenti = fromOrganikoServizi.findDipendentiStrictByStrutturaIDAndAnno(incarico.pgID, anno);
		listDipendenti.remove(responsabile);
        Map<PersonaFisicaGeko,List<AzioneAssegnazione>> mapDipendentiAssegnazioni = new LinkedHashMap<PersonaFisicaGeko,List<AzioneAssegnazione>>();
        Map<PersonaFisicaGeko,List<ValutazioneComparto>> mapDipendentiValutazioneComparto = new LinkedHashMap<PersonaFisicaGeko,List<ValutazioneComparto>>();
        if (listDipendenti!= null && !listDipendenti.isEmpty()){
	        for(PersonaFisicaGeko pf : listDipendenti){
	        	pf.setAnno(anno);
	        	pf.setIncaricoValutazioneID(idIncarico);
	        	List<AzioneAssegnazione> assegnazioni = azAssServizi.findByPfIDAndIncaricoIDAndAnno(pf.idPersona, idIncarico, anno);
	        	
	        	if (assegnazioni != null && !assegnazioni.isEmpty()){
	        		pf.setAssegnazioni(assegnazioni);
	        		mapDipendentiAssegnazioni.put(pf, assegnazioni);
	        	}
	        	//
	        	List<ValutazioneComparto> valutazioni = valutazioneCompartoServizi.findByPfIDAndIncaricoIDAndAnno(pf.idPersona, incarico.idIncarico, anno);
	        	if (valutazioni!=null && !valutazioni.isEmpty()) {
	        		pf.setValutazioni(valutazioni);
	            	mapDipendentiValutazioneComparto.put(pf, valutazioni);
	        	}
	        	else {
	        		List<ValutazioneComparto> emptyList = new ArrayList<ValutazioneComparto>();
	        		pf.setValutazioni(valutazioni);
	            	mapDipendentiValutazioneComparto.put(pf, valutazioni);
	        	}
	        }
        }
        log.info("struttura = "+incarico.getDenominazioneStruttura());
        model.addAttribute("mapDipendentiAssegnazioni", mapDipendentiAssegnazioni); 
        model.addAttribute("mapDipendentiValutazioneComparto", mapDipendentiValutazioneComparto); 
        model.addAttribute("struttura", incarico.getDenominazioneStruttura());
        model.addAttribute("anno", anno);
        model.addAttribute("idIncarico", idIncarico);
        //
        return "geko/valutazione/controller/modifyValutazioneCompartoController";
    }
    
    // lista valutazione dipendenti della struttura e sottostruttura
    @RequestMapping(value="listValutazioneStrutturaManager/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listValutazioneStrutturaManagerGet(@PathVariable("anno") int anno, 
    		@PathVariable("idIncarico") int idIncarico,
    		Model model) {
        //
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	final PersonaFisicaGeko responsabile = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	final PersonaGiuridicaGeko struttura = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);
    	List<PersonaFisicaGeko> listDipendenti = fromOrganikoServizi.findDipendentiStrictByStrutturaIDAndAnno(incarico.pgID, anno);
		listDipendenti.remove(responsabile);
        //
        Map<PersonaFisicaGeko,List<AzioneAssegnazione>> mapDipendentiAssegnazioni = new LinkedHashMap<PersonaFisicaGeko,List<AzioneAssegnazione>>();
        Map<PersonaFisicaGeko,List<ValutazioneComparto>> mapDipendentiValutazioneComparto = new LinkedHashMap<PersonaFisicaGeko,List<ValutazioneComparto>>();
        if (listDipendenti!= null && !listDipendenti.isEmpty()){
        for(PersonaFisicaGeko pf : listDipendenti){
        	log.info("pf =" +pf.stringa+"con ID"+pf.idPersona);
        	pf.setAnno(anno);
        	pf.setIncaricoValutazioneID(incarico.getIdIncarico());
        	//
        	// List<AzioneAssegnazione> assegnazioni = azAssServizi.findByPfIDAndIncaricoIDAndAnno(pfID, incaricoID, anno);
        	List<AzioneAssegnazione> assegnazioni = azAssServizi.findByPfIDAndIncaricoIDAndAnno(pf.idPersona, incarico.idIncarico, anno);
        	
        	if (assegnazioni!=null && !assegnazioni.isEmpty()) {
        		pf.setAssegnazioni(assegnazioni);
        		mapDipendentiAssegnazioni.put(pf, assegnazioni);
        	}
        	//
        	List<ValutazioneComparto> valutazioni = valutazioneCompartoServizi.findByPfIDAndIncaricoIDAndAnno(pf.idPersona, incarico.idIncarico, anno);
        	if (valutazioni!=null && !valutazioni.isEmpty()) {
        		pf.setValutazioni(valutazioni);
        	    mapDipendentiValutazioneComparto.put(pf, valutazioni);
        	}
        	
        }
        }
        //
        model.addAttribute("mapDipendentiAssegnazioni", mapDipendentiAssegnazioni);  
        model.addAttribute("mapDipendentiValutazioneComparto", mapDipendentiValutazioneComparto);  
        model.addAttribute("struttura", incarico.denominazioneStruttura);
        model.addAttribute("anno", anno);
        //
        return "geko/valutazione/controller/listValutazioneCompartoController";
    }
    
    
    
    
} // ------------------------------------

