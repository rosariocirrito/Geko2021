package it.sicilia.regione.gekoddd.geko.valutazione.web.manager;

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

import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/dirigenteVal")
public class ManagerValutazioneListController  {
	
    private static final Logger log = LoggerFactory.getLogger(ManagerValutazioneListController.class);
    @Autowired
    private ObiettivoQryService objQryServizi;
	//
    @Autowired
    private AzioneAssegnazioneQryService azAssServizi;
    @Autowired
    private ValutazioneCompartoQryService valutazioneCompartoServizi;
    @Autowired
    private ValutazioneQryService valutazioneQryServizi;
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    //
    public ManagerValutazioneListController() { }
    
    //
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
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
        List<PersonaFisicaGeko> listDipendenti = fromOrganikoServizi.findDipendentiGlobalByStrutturaIDAndAnno(incarico.pgID, anno); 
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
        return "geko/valutazione/manager/listValutazioneCompartoManager";
    }
    
    
    
 // modifica valutazione dipendenti della struttura e sottostruttura
    @RequestMapping(value="modifyDipendentiAssegnazioniValutazione/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String modifyDipendentiAssegnazioniValutazioneIncaricoGet(@PathVariable("anno") int anno, 
    		@PathVariable("idIncarico") int idIncarico,	Model model) {
        //
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	final PersonaFisicaGeko responsabile = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
        List<PersonaFisicaGeko> listDipendenti = fromOrganikoServizi.findDipendentiStrictByStrutturaIDAndAnno(incarico.pgID, anno); 
        // listDipendenti.remove(responsabile);
        Map<PersonaFisicaGeko,List<AzioneAssegnazione>> mapDipendentiAssegnazioni = new LinkedHashMap<PersonaFisicaGeko,List<AzioneAssegnazione>>();
        Map<PersonaFisicaGeko,List<ValutazioneComparto>> mapDipendentiValutazioneComparto = new LinkedHashMap<PersonaFisicaGeko,List<ValutazioneComparto>>();
        if (listDipendenti!= null && !listDipendenti.isEmpty()){
	        for(PersonaFisicaGeko pf : listDipendenti){
	        	pf.setAnno(anno);
	        	pf.setIncaricoValutazioneID(incarico.getIdIncarico());
	        	List<AzioneAssegnazione> assegnazioni = azAssServizi.findByPfIDAndIncaricoIDAndAnno(pf.idPersona, incarico.idIncarico, anno);
	        	
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
        
        model.addAttribute("mapDipendentiAssegnazioni", mapDipendentiAssegnazioni); 
        model.addAttribute("mapDipendentiValutazioneComparto", mapDipendentiValutazioneComparto); 
        model.addAttribute("struttura", incarico.getDenominazioneStruttura());
        model.addAttribute("anno", anno);
        model.addAttribute("idIncarico", idIncarico);
        //
        return "geko/valutazione/manager/modifyValutazioneCompartoManager";
    }
    
    
 // 1.1 lista obiettivi e azioni e dipendenti per consentirne la modifica
    @RequestMapping(value="modifyValutazionePopIncarico/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String modifyValutazionePopIncaricoGet(@PathVariable("anno") int anno,
    		@PathVariable("idIncarico") int idIncarico,	Model model) {
 	   //
 	   final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
        final PersonaGiuridicaGeko struttura = fromOrganikoServizi.findPersonaGiuridicaById(incarico.getPgID());
        List<IncaricoGeko> lstIncPop = new ArrayList<IncaricoGeko>();
        lstIncPop = fromOrganikoServizi.findIncarichiPopByIntermediaIDAndAnno(struttura.getIdPersona(),anno);
        //
        if(!lstIncPop.equals(null) && !lstIncPop.isEmpty()) {
     	   for (IncaricoGeko incPop : lstIncPop) {
	   	       if (null != incPop) {
	   	    	   final List<Obiettivo> listObiettivi = objQryServizi.findObiettiviPopSenzaPersoneByIncaricoIDAndAnno(incPop.getIdIncarico(), anno);
	   	    	   final List<Valutazione> lstValutPop = valutazioneQryServizi.findByIncaricoIDAndAnno(incPop.getIdIncarico(), anno); // una sola in realt�        	
	   	    	   //	
	   	    	   for (Obiettivo obj : listObiettivi){
	   	    		obj.setIncarico(incPop);
	   	    		obj.setIncaricoPadreID(idIncarico);
	   	    	   }		    	
		        	incPop.setObiettivi(listObiettivi);
		        	//	
		        	if (lstValutPop!=null && !lstValutPop.isEmpty()) {
		            	incPop.setValutazioni(lstValutPop);
		            }              	       
	   	       } // end if
     	   } // end for 
         
        //
         model.addAttribute("struttura", incarico.denominazioneStruttura);
	        model.addAttribute("responsabile", incarico.responsabile);
	        model.addAttribute("incarico", incarico);
	        model.addAttribute("listIncarichiPop", lstIncPop);
	        model.addAttribute("anno", anno);
	        
	        final String pageName = "geko/valutazione/manager/modifyValutazioneIncarichiPopManager";
	        model.addAttribute("pageName", pageName); 
	        //
         return pageName; 
    }// end if lst empty  
    else return "common/errNoIncarichiPop";
    } // fine metodo modifyProgrammazioneIncaricoGet
 
    // --------
 // 1.1 lista obiettivi e azioni e dipendenti per consentirne la modifica
    @RequestMapping(value="listValutazionePopIncarico/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listValutazionePopIncaricoGet(@PathVariable("anno") int anno,
    		@PathVariable("idIncarico") int idIncarico,	Model model) {
 	   //
 	   final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
        final PersonaGiuridicaGeko struttura = fromOrganikoServizi.findPersonaGiuridicaById(incarico.getPgID());
        List<IncaricoGeko> lstIncPop = new ArrayList<IncaricoGeko>();
        lstIncPop = fromOrganikoServizi.findIncarichiPopByIntermediaIDAndAnno(struttura.getIdPersona(),anno);
        //
        if(!lstIncPop.equals(null) && !lstIncPop.isEmpty()) {
     	   for (IncaricoGeko incPop : lstIncPop) {
	   	       if (null != incPop) {
	   	    	   final List<Obiettivo> listObiettivi = objQryServizi.findObiettiviPopSenzaPersoneByIncaricoIDAndAnno(incPop.getIdIncarico(), anno);
	   	    	   final List<Valutazione> lstValutPop = valutazioneQryServizi.findByIncaricoIDAndAnno(incPop.getIdIncarico(), anno); // una sola in realt�        	
	   	    	   //	
	   	    	   for (Obiettivo obj : listObiettivi){
	   	    		obj.setIncarico(incPop);
	   	    		obj.setIncaricoPadreID(idIncarico);
	   	    	   }		    	
		        	incPop.setObiettivi(listObiettivi);
		        	//	
		        	if (lstValutPop!=null && !lstValutPop.isEmpty()) {
		            	incPop.setValutazioni(lstValutPop);
		            }              	       
	   	       } // end if
     	   } // end for 
         
        //
         model.addAttribute("struttura", incarico.denominazioneStruttura);
	        model.addAttribute("responsabile", incarico.responsabile);
	        model.addAttribute("incarico", incarico);
	        model.addAttribute("listIncarichiPop", lstIncPop);
	        model.addAttribute("anno", anno);
	        
	        final String pageName = "geko/valutazione/manager/listValutazioneIncarichiPopManager";
	        model.addAttribute("pageName", pageName); 
	        //
         return pageName; 
        }// end if lst empty  
        else return "common/errNoIncarichiPop";
    } // fine metodo listProgrammazioneIncaricoGet
 
 
 
} // ---------------------------------------------
