package it.sicilia.regione.gekoddd.geko.pianificazione.web.supergab;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.pianificazione.application.SuperGabinettoPianificazioneService;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategica;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategicaQryService;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategico;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategicoQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione.CompletamentoEnum;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione.ObiettivoAssegnazione;
import it.sicilia.regione.gekoddd.log.model.evento.Evento;
import it.sicilia.regione.gekoddd.log.model.evento.EventoService;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author Cirrito
 *
 */

@Controller
@RequestMapping("/superGabPianificazione")
public class SuperGabPianificazioneListController  {
	
	private Log log = LogFactory.getLog(SuperGabPianificazioneListController.class);
	
	//
   
	@Autowired
    private ObiettivoStrategicoQryService objStratServizi;
    @Autowired
    private AreaStrategicaQryService areaStrategicaQryServizi;
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    
    @Autowired
    private SuperGabinettoPianificazioneService superGabinettoServizi;
    
    @Autowired
    private Menu menu;
   
    //
    public SuperGabPianificazioneListController() { }
    
    //
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
  
  //-----------------------Direttiva --------------------
    @RequestMapping(value="listDirettivaSuperGab/{anno}", method = RequestMethod.GET)
    public String listDirettivaIndirSuperGabinetto(@PathVariable("anno") int anno, Model model) {
    	List<AreaStrategica> listAreeStrategiche = areaStrategicaQryServizi.findByAnno(anno);
    	//
        for (AreaStrategica area : listAreeStrategiche){
        	List<ObiettivoStrategico> lstobjstr = objStratServizi.findByAreaStrategicaAndAnno(area, anno);
        	area.setObiettiviStrategici(lstobjstr);
        	/*
        	if(!lstobjstr.isEmpty()){
        		for(ObiettivoStrategico stra : lstobjstr){
        			if(!stra.getAssociazObiettiviApicali().isEmpty()){
        				for(AssociazObiettivi assoc : stra.getAssociazObiettiviApicali()){
            				Obiettivo apicale = assoc.getApicale();
            				IncaricoGeko inc = fromOrganikoServizi.findIncaricoById(apicale.getIncaricoID());
            				apicale.setResponsabile(inc.getDenominazioneDipartimento()
            						+" / "+inc.getDenominazioneStruttura()+ " - "+inc.getResponsabile()); 
            			}
        			}
        		}
        	}	
        	*/
        }
        //
    	model.addAttribute("listAreeStrategiche", listAreeStrategiche); 
        model.addAttribute("anno", anno);
        //
        String pageName = "geko2019/pianificazione/listDirettivaIndir";
        model.addAttribute("pageName", pageName);
        return pageName;
    }  
    
  //-----------------------Direttiva --------------------
    @RequestMapping(value="listDirettivaAmminSuperGab/{anno}", method = RequestMethod.GET)
    public String listDirettivaAmminSuperGabinetto(@PathVariable("anno") int anno, Model model) {
    	List<AreaStrategica> listAreeStrategiche = areaStrategicaQryServizi.findByAnno(anno);
    	//
        for (AreaStrategica area : listAreeStrategiche){
        	List<ObiettivoStrategico> lstobjstr = objStratServizi.findByAreaStrategicaAndAnno(area, anno);
        	area.setObiettiviStrategici(lstobjstr);
        	
        	if(!lstobjstr.isEmpty()){
        		for(ObiettivoStrategico stra : lstobjstr){
        			if(!stra.getAssociazObiettiviApicali().isEmpty()){
        				for(AssociazObiettivi assoc : stra.getAssociazObiettiviApicali()){
            				Obiettivo apicale = assoc.getApicale();
            				IncaricoGeko inc = fromOrganikoServizi.findIncaricoById(apicale.getIncaricoID());
            				apicale.setResponsabile(inc.getDenominazioneDipartimento()
            					+" / "+inc.denominazioneStruttura
                                                + " - "+inc.responsabile); 
            			}
        			}
        		}
        	}	
        }
        //
    	model.addAttribute("listAreeStrategiche", listAreeStrategiche); 
        model.addAttribute("anno", anno);
        //
        String pageName = "geko2019/pianificazione/listDirettivaAmmin";
        model.addAttribute("pageName", pageName);
        return pageName;
    }  
    
    @RequestMapping(value="modifyDirettivaSuperGab/{anno}", method = RequestMethod.GET)
    public String modifyDirettivaSuperGabinetto(@PathVariable("anno") int anno, Model model) {
    	List<AreaStrategica> listAreeStrategiche = areaStrategicaQryServizi.findByAnno(anno);
    	for (AreaStrategica area : listAreeStrategiche){
        	List<ObiettivoStrategico> lstobjstr = objStratServizi.findByAreaStrategicaAndAnno(area, anno);
        	area.setObiettiviStrategici(lstobjstr);
    	}
        model.addAttribute("listAreeStrategiche", listAreeStrategiche); 
        model.addAttribute("anno", anno);
        //
        String pageName = "geko2019/pianificazione/supergab/modifyDirettivaSuperGabinetto";
        model.addAttribute("pageName", pageName);
        return pageName;
    }  
    
  //-----------------------PianificazioneTriennale --------------------
    @RequestMapping(value="listPianificazioneTriennaleSuperGab/{anno}", method = RequestMethod.GET)
    public String listPianificazioneTriennaleSuperGabinetto(@PathVariable("anno") int anno, Model model) {
    	List<AreaStrategica> listAreeStrategiche = areaStrategicaQryServizi.findByAnno(anno);
    	int annoInizio = anno;
    	int annoFine = anno+3;
    	 //
    	model.addAttribute("listAreeStrategiche", listAreeStrategiche); 
        model.addAttribute("anno", anno);
        model.addAttribute("annoInizio", annoInizio);
        model.addAttribute("annoFine", annoFine);
        //
        String pageName = "geko2019/pianificazione/listPianificazioneTriennale";
        model.addAttribute("pageName", pageName);
        return pageName;
    }  
    
    @RequestMapping(value="modifyPianificazioneTriennaleSuperGab/{anno}", method = RequestMethod.GET)
    public String modifyPianificazioneTriennaleSuperGabinetto(@PathVariable("anno") int anno, Model model) {
    	List<AreaStrategica> listAreeStrategiche = areaStrategicaQryServizi.findByAnno(anno);
    	
    	
        model.addAttribute("listAreeStrategiche", listAreeStrategiche); 
        model.addAttribute("anno", anno);
        //
        String pageName = "geko2019/pianificazione/supergab/modifyPianificazioneTriennaleSuperGabinetto";
        model.addAttribute("pageName", pageName);
        return pageName;
    }  
    
    
    //lista obiettivi e azioni e dipendenti in base al nome del dirigente con assegnazioni
    @RequestMapping(value="clonaDirettivaIndirizzoSuperGab/{anno}", method = RequestMethod.GET)
    public String clonaDirettivaIndirGet(@PathVariable("anno") int anno, Model model) {
    	List<AreaStrategica> listAreeStrategiche = areaStrategicaQryServizi.findByAnno(anno);
    	//
        for (AreaStrategica area : listAreeStrategiche){
        	List<ObiettivoStrategico> lstobjstr = objStratServizi.findByAreaStrategicaAndAnno(area, anno);
        	area.setObiettiviStrategici(lstobjstr);
        	
        	if(!lstobjstr.isEmpty()){
        		for(ObiettivoStrategico stra : lstobjstr){
        			ObiettivoStrategico newStra = stra.clone();
        			Evento event = this.superGabinettoServizi.cmdSuperGabinettoCreateObiettivoStrategico(newStra);    	            
        		}
        	}	
        }
    	menu.setAnno(anno +1);
    	    	  
	    //
	    
        String routePath = "redirect:/superGabPianificazione/modifyDirettivaSuperGab/";
	    String routeName = routePath+ +menu.getAnno(); 
        return routeName; 
   
    }
  
    
 
    
} // ---------------------------------------------
