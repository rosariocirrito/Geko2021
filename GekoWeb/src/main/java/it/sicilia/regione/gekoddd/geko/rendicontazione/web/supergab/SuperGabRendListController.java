package it.sicilia.regione.gekoddd.geko.rendicontazione.web.supergab;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategicaQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoQryService;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Cirrito
 *
 */

@Controller
@RequestMapping("/superGabRend")
public class SuperGabRendListController  {
	
	private Log log = LogFactory.getLog(SuperGabRendListController.class);
	
	//
    @Autowired
    private ObiettivoQryService objServizi;
    
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    //
    public SuperGabRendListController() { }
    
    //
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
 // lista obiettivi e azioni apicali
    @RequestMapping(value="listRendicontazioneIncaricoApicaleSuperGab/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listRendicontazioneIncaricoApicaleSepicosGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	log.info("listRendicontazioneIncaricoApicaleSuperGab");
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettiviApicali = objServizi.findObiettiviApicaliDirettiAndSubordinatiByIncaricoIDAndAnno(idIncarico, anno);
    	//
    	model.addAttribute("dipartimento", incarico.denominazioneStruttura);
    	model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("anno", anno);
        model.addAttribute("listObiettivi", listObiettiviApicali);
        
        if (null != incarico) {
        	
            return "geko/rendicontazione/supergab/listRendicontazioneIncaricoApicaleSuperGab";
        }
        else return "redirect:/ROLE_SUPERGABINETTO";
    }
    
 // --------- scadenzario --------------------------
    @RequestMapping(value ="scadenze/{anno}/{idIncarico}" , method = RequestMethod.GET )
    
    public String scadenze(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico,Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettivi = objServizi.findObiettiviApicaliDirettiAndSubordinatiByIncaricoIDAndAnno(idIncarico, anno);
    	model.addAttribute("struttura", incarico.denominazioneStruttura);
    	model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("anno", anno);
        model.addAttribute("listObiettivi", listObiettivi);
        //
        //
        List<Azione> lstAzioniDept = new ArrayList<Azione>();
        for(Obiettivo obj : listObiettivi){
        	IncaricoGeko incaricoObj = fromOrganikoServizi.findIncaricoById(obj.getIncaricoID());
        	PersonaFisicaGeko responsabile = fromOrganikoServizi.findPersonaFisicaById(incaricoObj.pfID);
        	List<Azione> lstAzioniObj = obj.getAzioni();
        	for(Azione act : lstAzioniObj){
        		act.setDirigente(responsabile);
        	}
        	lstAzioniDept.addAll(lstAzioniObj);
        }
        PropertyComparator.sort(lstAzioniDept, new MutableSortDefinition("scadenzaApicale",true,true));
        model.addAttribute("listAzioni",lstAzioniDept);
    	//
        return "geko/rendicontazione/supergab/listScadenzeAzioniSuperGabinetto";
        
	}
   
    
    
 
} // ---------------------------------------------
