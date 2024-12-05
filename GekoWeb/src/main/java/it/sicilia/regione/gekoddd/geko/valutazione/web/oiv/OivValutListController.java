package it.sicilia.regione.gekoddd.geko.valutazione.web.oiv;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategica;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategicaQryService;
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
@RequestMapping("/oivValut")
public class OivValutListController  {
	
	private Log log = LogFactory.getLog(OivValutListController.class);
	
	//
    @Autowired
    private ObiettivoQryService objServizi;
    
    @Autowired
    private AreaStrategicaQryService areaStrategicaQryServizi;
    
    @Autowired
    private ValutazioneQryService valutazioneDirigServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    //
    public OivValutListController() { }
    
    //
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
 
    
 // modifica valutazioni di altra struttura
    @RequestMapping(value="modifyValutazioneIncaricoApicaleOiv/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String modifyValutazioneIncaricoApicaleSepicosGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	
    	//
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettiviApicali = objServizi.findObiettiviApicaliDirettiAndSubordinatiByIncaricoIDAndAnno(idIncarico, anno);
    	//
    	int totPesoApicaleObiettivi = 0;
    	float totPunteggioApicaleObiettivi = 0;
    	for (Obiettivo obj : listObiettiviApicali){
    		obj.setAnno(anno);
    		totPesoApicaleObiettivi+= obj.getPesoApicale();
    		totPunteggioApicaleObiettivi+= obj.getPunteggioApicale();
    	}
    	//    	
    	List<Valutazione> lstValutDirig = valutazioneDirigServizi.findByIncaricoIDAndAnno(idIncarico, anno); // una sola in realt�
        incarico.setValutazioni(lstValutDirig);
    	//
        model.addAttribute("anno", anno);
        model.addAttribute("incarico", incarico);
    	model.addAttribute("listObiettiviApicali", listObiettiviApicali);
    	model.addAttribute("totPesoApicaleObiettivi", totPesoApicaleObiettivi);
    	model.addAttribute("totPunteggioApicaleObiettivi", totPunteggioApicaleObiettivi);
        model.addAttribute("lstValutDirig", lstValutDirig);
        //
        return "geko/valutazione/oiv/modifyValutazioneIncaricoApicaleOiv";    	 
    }
    
 // modifica valutazioni di altra struttura
    @RequestMapping(value="listValutazioneIncaricoApicaleOiv/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listValutazioneIncaricoApicaleSepicosGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	//
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettiviApicali = objServizi.findObiettiviApicaliDirettiAndSubordinatiByIncaricoIDAndAnno(idIncarico, anno);
    	//
    	int totPesoApicaleObiettivi = 0;
    	float totPunteggioApicaleObiettivi = 0;
    	for (Obiettivo obj : listObiettiviApicali){
    		obj.setAnno(anno);
    		totPesoApicaleObiettivi+= obj.getPesoApicale();
    		totPunteggioApicaleObiettivi+= obj.getPunteggioApicale();
    	}
    	//    	
    	List<Valutazione> lstValutDirig = valutazioneDirigServizi.findByIncaricoIDAndAnno(idIncarico, anno); // una sola in realt�
        incarico.setValutazioni(lstValutDirig);
    	//
        model.addAttribute("anno", anno);
        model.addAttribute("incarico", incarico);
    	model.addAttribute("listObiettiviApicali", listObiettiviApicali);
    	model.addAttribute("totPesoApicaleObiettivi", totPesoApicaleObiettivi);
    	model.addAttribute("totPunteggioApicaleObiettivi", totPunteggioApicaleObiettivi);
        model.addAttribute("lstValutDirig", lstValutDirig);
    	//
    	
    	return "geko/valutazione/oiv/listValutazioneIncaricoApicaleOiv";   	 
    }
    
} // ---------------------------------------------
