package it.sicilia.regione.gekoddd.geko.programmazione.web.oiv;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategica;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategicaQryService;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategico;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategicoQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.web.controller.ControllerProgrListController;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.ValutazioneQryService;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
@RequestMapping("/oivProg")
public class OivProgrListController  {
	
	private Log log = LogFactory.getLog(OivProgrListController.class);
	
	//
    @Autowired
    private ObiettivoQryService objServizi;
    @Autowired
    private ObiettivoStrategicoQryService objStratServizi;
    
    @Autowired
    private AreaStrategicaQryService areaStrategicaQryServizi;
    
    @Autowired
    private ValutazioneQryService valutazioneDirigServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    //
    public OivProgrListController() { }
    
    //
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
 
 
  //-----------------------Direttiva --------------------
    // lista compatta pianificazione obiettivi e azioni del dipartimento
    @RequestMapping(value="listDirettivaOiv/{anno}", method = RequestMethod.GET)
    public String listDirettivaOiv(@PathVariable("anno") int anno, Model model) {
    	List<AreaStrategica> listAreeStrategiche = areaStrategicaQryServizi.findByAnno(anno);
    	//
        for (AreaStrategica area : listAreeStrategiche){
        	List<ObiettivoStrategico> lstobjstr = objStratServizi.findByAreaStrategicaAndAnno(area, anno);
        	area.setObiettiviStrategici(lstobjstr);
        	if(!lstobjstr.isEmpty()){
        		for(ObiettivoStrategico stra : lstobjstr){
        			if(!stra.getAssociazObiettivi().isEmpty()){
        				for(AssociazObiettivi assoc : stra.getAssociazObiettivi()){
            				Obiettivo apicale = assoc.getApicale();
            				IncaricoGeko inc = fromOrganikoServizi.findIncaricoById(apicale.getIncaricoID());
            				apicale.setResponsabile(inc.getDenominazioneDipartimento()
            						+" / "+inc.getDenominazioneStruttura()+ " - "+inc.getResponsabile()); 
            			}
        			}
        		}
        	}
        		
        }
        
    	model.addAttribute("listAreeStrategiche", listAreeStrategiche); 
        model.addAttribute("anno", anno);
        //
        String pageName = "geko2019/pianificazione/listDirettiva";
        model.addAttribute("pageName", pageName);
        return pageName;
    }  
    
 // 1.2.2 lista obiettivi e azioni apicali
    @RequestMapping(value="listPianificazioneApicaleIncaricoOiv/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listPianificazioneApicaleIncaricoControllerGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	//List<Obiettivo> listObiettiviApicali = objServizi.findObiettiviApicaliDirettiAndSubordinatiByIncaricoIDAndAnno(idIncarico, anno);
    	List<Obiettivo> listObiettiviApicali = objServizi.findObiettiviApicaliDirettiByIncaricoIDAndAnno(idIncarico, anno);    	
    	incarico.setObiettivi(listObiettiviApicali);
    	if(anno>2018){
    		List<Valutazione> lstValutDirig = valutazioneDirigServizi.findByIncaricoIDAndAnno(idIncarico, anno); // una sola in realtà
        	//
        	if (lstValutDirig!=null && !lstValutDirig.isEmpty()) {
        		incarico.setValutazioni(lstValutDirig);
        	}
        	else {
        		List<Valutazione> emptyList = new ArrayList<Valutazione>();
        		incarico.setValutazioni(lstValutDirig);
        	}
    	}
    	//
    	model.addAttribute("incarico", incarico);
    	model.addAttribute("dipartimento", incarico.denominazioneStruttura);
    	model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("anno", anno);
        model.addAttribute("listObiettivi", listObiettiviApicali);
        return "geko/programmazione/oiv/listPianificazioneIncaricoApicaleOiv";
    }
    
    
 // lista obiettivi e azioni apicali
    @RequestMapping(value="listPianificazioneIncaricoApicaleOiv/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listPianificazioneIncaricoApicaleControllerGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	log.info("listPianificazioneIncaricoApicaleOiv");
    	
       	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettiviApicali = objServizi.findObiettiviApicaliDirettiAndSubordinatiByIncaricoIDAndAnno(idIncarico, anno);
    	//
    	model.addAttribute("dipartimento", incarico.denominazioneStruttura);
    	model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("anno", anno);
        model.addAttribute("listObiettivi", listObiettiviApicali);
        if (null != incarico) {
            return "geko/programmazione/oiv/listPianificazioneIncaricoApicaleOiv";
        }
        else return "redirect:/ROLE_OIV";
    }
    
 
    
 
    
} // ---------------------------------------------
