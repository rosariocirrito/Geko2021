package it.sicilia.regione.gekoddd.geko.rendicontazione.web.controller;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategicaQryService;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategicoQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazioneQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoQryService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneCompartoQryService;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import java.text.SimpleDateFormat;
import java.util.*;


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
@RequestMapping("/controllerRend")
public class ControllerRendListController  {
    
	//
    @Autowired
    private ObiettivoQryService objServizi;
   
    @Autowired
    private AreaStrategicaQryService areaStrategicaQryServizi;
    @Autowired
    private ObiettivoStrategicoQryService obiettiviStrategiciServizi;
    @Autowired
    private AzioneAssegnazioneQryService azAssServizi;
    @Autowired
    private ValutazioneCompartoQryService valutazioneCompartoServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    //
    public ControllerRendListController() { }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
    
   
  //-----------------------Rendicontazione ---------------------------------
   

    
 // lista rendicontazione obiettivi e azioni del dipartimento
    @RequestMapping(value="listRendicontazioneDipartimentaleController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listRendicontazioneDipartimentaleController(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettivi = objServizi.findObiettiviSubordinatiByIncaricoIDAndAnno(idIncarico, anno);
    	model.addAttribute("struttura", incarico.denominazioneStruttura);
    	model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("anno", anno);
        model.addAttribute("listObiettivi", listObiettivi);
        return "geko/rendicontazione/controller/listRendicontazioneDipartimentaleController";
    }
    
 // lista rendicontazione obiettivi e azioni del dipartimento
    @RequestMapping(value="listRendicontazioneDipartimentalePrioritariaController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listRendicontazioneDipartimentalePrioritariaController(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	final List<Obiettivo> listObiettivi = objServizi.findObiettiviDirettiAndSubordinatiByIncaricoIDAndAnno(idIncarico, anno);
    	List<Obiettivo> listObiettiviPrio = new ArrayList<Obiettivo>();
    	for(Obiettivo obj : listObiettivi){
    		if (obj.getTipo().equals(Obiettivo.TipoEnum.DIRIGENZIALE)) listObiettiviPrio.add(obj);
    	}
    	model.addAttribute("struttura", incarico.denominazioneStruttura);
    	model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("anno", anno);
        model.addAttribute("listObiettivi", listObiettiviPrio);
        return "geko/rendicontazione/controller/listRendicontazionePrioritariaDipartimentaleController";
    }
    
 // lista obiettivi e azioni apicali
    @RequestMapping(value="listRendicontazioneIncaricoApicaleController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listRendicontazioneIncaricoApicaleControllerGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettiviApicali = objServizi.findObiettiviApicaliDirettiAndSubordinatiByIncaricoIDAndAnno(idIncarico, anno);
    	//
    	model.addAttribute("struttura", incarico.denominazioneStruttura);
    	model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("anno", anno);
        model.addAttribute("listObiettivi", listObiettiviApicali);
        return "geko/rendicontazione/controller/listRendicontazioneIncaricoApicaleController";
    }
   
    @RequestMapping(value="modifyRendicontazioneGlobaleIncaricoApicaleController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String modifyRendicontazioneIncaricoApicaleControllerGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettiviApicali = objServizi.findObiettiviApicaliDirettiAndSubordinatiByIncaricoIDAndAnno(idIncarico, anno);
    	//
    	model.addAttribute("struttura", incarico.denominazioneStruttura);
    	model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("anno", anno);
        model.addAttribute("listObiettiviDept", listObiettiviApicali);
        return "geko/rendicontazione/controller/modifyRendicontazioneGlobaleIncaricoApicaleController";
    }
    
    @RequestMapping(value="verifyRendicontazioneDipartimentaleController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String verifyRendicontazioneDipartimentaleControllerGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettivi = objServizi.findObiettiviSubordinatiByIncaricoIDAndAnno(idIncarico, anno);
    	//
    	model.addAttribute("struttura", incarico.denominazioneStruttura);
    	model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("anno", anno);
        model.addAttribute("listObiettivi", listObiettivi);
        return "geko/rendicontazione/controller/verifyRendicontazioneDipartimentaleController";
    }
    
 // lista obiettivi e azioni apicali
    @RequestMapping(value="listRendicontazioneDirettaIncaricoApicaleController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listRendicontazioneDirettaIncaricoApicaleControllerGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettiviApicali = objServizi.findObiettiviApicaliDirettiByIncaricoIDAndAnno(idIncarico, anno);
    	//
    	model.addAttribute("struttura", incarico.denominazioneStruttura);
    	model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("anno", anno);
        model.addAttribute("listObiettivi", listObiettiviApicali);
        return "geko/rendicontazione/controller/listRendicontazioneDirettaIncaricoApicaleController";
    }
    
 // lista rendicontazione obiettivi e azioni del dipartimento
    @RequestMapping(value="modifyRendicontazioneIncaricoApicaleController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String modifyRendicontazioneIncaricoApicaleController(@PathVariable("anno") int anno,@PathVariable("idIncarico") int idIncarico, Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
        
    	List<Obiettivo> listObiettiviApicali = objServizi.findObiettiviApicaliDirettiAndSubordinatiByIncaricoIDAndAnno(idIncarico, anno);
    	model.addAttribute("struttura", incarico.denominazioneStruttura);
    	model.addAttribute("responsabile", incarico.responsabile);
            model.addAttribute("listObiettivi", listObiettiviApicali); 
            model.addAttribute("anno", anno);
        
        return "geko/rendicontazione/controller/modifyRendicontazioneIncaricoApicaleController";
    }

   
    // -------------------------- Rendicontazione ----------------------------
    // 2.5.3 lista rendicontazione di incarico
    @RequestMapping(value="listRendicontazioneIncaricoController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listRendicontazioneOtherStructureController(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, 
    		Model model) {
    	
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	if (null != incarico) {
	    	//final List<Obiettivo> listObiettivi = incarico.getObiettiviTotAnno();
	    	final List<Obiettivo> listObiettivi = objServizi.findObiettiviTotaliConPersoneByIncaricoIDAndAnno(idIncarico,anno);
	    	model.addAttribute("struttura", incarico.getDenominazioneStruttura());
	    	model.addAttribute("competenze", incarico.competenzeStruttura);
	    	model.addAttribute("responsabile", incarico.responsabile);
	        model.addAttribute("anno", anno);
	        model.addAttribute("listObiettivi", listObiettivi);
            model.addAttribute("incarico", incarico);
        //
        return "geko/rendicontazione/controller/listRendicontazioneIncaricoController";
        }
        else return "redirect:/ROLE_CONTROLLER";
    }
    
 // 2.5.3 lista rendicontazione di incarico
    @RequestMapping(value="deleteRendicontazioneIncaricoController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String deleteRendicontazioneOtherStructureController(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, 
    		Model model) {
    	
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	if (null != incarico) {
	    	//final List<Obiettivo> listObiettivi = incarico.getObiettiviTotAnno();
	    	final List<Obiettivo> listObiettivi = objServizi.findObiettiviTotaliConPersoneByIncaricoIDAndAnno(idIncarico,anno);
	    	model.addAttribute("struttura", incarico.getDenominazioneStruttura());
	    	model.addAttribute("competenze", incarico.competenzeStruttura);
	    	model.addAttribute("responsabile", incarico.responsabile);
	        model.addAttribute("anno", anno);
	        model.addAttribute("listObiettivi", listObiettivi);
            model.addAttribute("incarico", incarico);
        //
        return "geko/rendicontazione/controller/deleteRendicontazioneIncaricoController";
        }
        else return "redirect:/ROLE_CONTROLLER";
    }
    
 // lista obiettivi e azioni di altra struttura 
    @RequestMapping(value="navigaRendicontazioneIncaricoController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String navigaRendicontazioneIncaricoControllerGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	//
    	/*
    	final OpPersonaGiuridica dipartimento = userServizi.findDipartimentoOfLoggedUser();
    	dipartimento.setAnno(anno);
    	final List<Incarico> listIncarichiDept = dipartimento.getSubIncarichiAnno();
    	
    	model.addAttribute("listIncarichiDept",listIncarichiDept);
    	
    	final Incarico incarico = incServizi.findById(idIncarico);
        incarico.setAnno(anno);
        OpPersonaGiuridica struttura = incarico.getOpPersonaGiuridica();
        struttura.setAnno(anno);
        OpPersonaFisica responsabile = incarico.getOpPersonaFisica();
        responsabile.setAnno(anno);
        if (null != responsabile) {
        	final List<Obiettivo> listObiettivi = incarico.getObiettiviTotAnno();
        	model.addAttribute("incarico", incarico);
            model.addAttribute("anno", anno);
            model.addAttribute("listObiettivi", listObiettivi);
            */
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	final PersonaGiuridicaGeko dept = fromOrganikoServizi.findDipartimentoByPersonaFisicaID(incarico.pfID);
    	final List<IncaricoGeko> listIncarichiDept = fromOrganikoServizi.findIncarichiByDipartimentoIDAndAnno(dept.idPersona, anno);
    	if (null != incarico) {
	    	//final List<Obiettivo> listObiettivi = incarico.getObiettiviTotAnno();
	    	final List<Obiettivo> listObiettivi = objServizi.findObiettiviTotaliConPersoneByIncaricoIDAndAnno(idIncarico,anno);
        	model.addAttribute("incarico", incarico);
            model.addAttribute("anno", anno);
            model.addAttribute("listObiettivi", listObiettivi);
            model.addAttribute("listIncarichiDept", listIncarichiDept);
            return "geko/rendicontazione/controller/navigaRendicontazioneIncaricoController";
        }
        else return "redirect:/ROLE_CONTROLLER";
    }
    
    
  //-----------------------Indicazioni ---------------------------------
    /*
    // lista rendicontazione obiettivi e azioni del dipartimento
    @RequestMapping(value="listIndicazioniDipartimentoController/{anno}", method = RequestMethod.GET)
    public String listIndicazioniDipartimentoController(@PathVariable("anno") int anno, Model model) {/*
        final OpPersonaGiuridica dipartimento = userServizi.findDipartimentoOfLoggedUser();
        if (null!=dipartimento){
        	dipartimento.setAnno(anno);
        	model.addAttribute("dipartimento", dipartimento);
        	Map<Incarico,List<Obiettivo>> mapObiettiviDept = objServizi.mapObiettiviIndicazioniDipartimentoAndAnno(dipartimento, anno);
            model.addAttribute("mapObiettiviDept", mapObiettiviDept); 
            model.addAttribute("anno", anno);
        }
        return "geko/programmazione/controller/listIndicazioniDipartimentoController";
    }
    
    //
   
    
 // lista rendicontazione obiettivi e azioni del dipartimento
    @RequestMapping(value="modifyIndicazioniDipartimentoController/{anno}", method = RequestMethod.GET)
    public String modifyIndicazioniDipartimentoController(@PathVariable("anno") int anno, Model model) {/*
        final OpPersonaGiuridica dipartimento = userServizi.findDipartimentoOfLoggedUser();
        if (null!=dipartimento){
        	dipartimento.setAnno(anno);
        	model.addAttribute("dipartimento", dipartimento);
        	//Map<OpPersonaGiuridica,List<Obiettivo>> mapObiettiviDept = objServizi.mapObiettiviOfDipartimentoAndAnno(dipartimento, anno);
        	Map<Incarico,List<Obiettivo>> mapObiettiviDept = objServizi.mapObiettiviIndicazioniDipartimentoAndAnno(dipartimento, anno);    
        	model.addAttribute("mapObiettiviDept", mapObiettiviDept); 
            model.addAttribute("anno", anno);
        }
        return "geko/programmazione/controller/modifyIndicazioniDipartimentoController";
    }
    
    */
    
    // lista rendicontazione di altra struttura 
    @RequestMapping(value="listIndicazioniIncaricoController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listIndicazioniIncaricoController(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	//
    	
        if (null != incarico) {
        	final List<Obiettivo> listObiettivi = objServizi.findObiettiviTotaliConPersoneByIncaricoIDAndAnno(idIncarico,anno);
        	model.addAttribute("struttura", incarico.getDenominazioneStruttura());
        	model.addAttribute("responsabile", incarico.responsabile);
            model.addAttribute("anno", anno);
            model.addAttribute("listObiettivi", listObiettivi);
            model.addAttribute("incarico", incarico);
        //
        return "geko/rendicontazione/controller/listIndicazioniIncaricoController";
        }
        else return "redirect:/ROLE_CONTROLLER";
    }
    
    
    // modifica rendicontazione di altra struttura 
    @RequestMapping(value="modifyIndicazioniIncaricoController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String modifyIndicazioniIncaricoController(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	if (null != incarico) {
	    	//final List<Obiettivo> listObiettivi = incarico.getObiettiviTotAnno();
	    	final List<Obiettivo> listObiettivi = objServizi.findObiettiviTotaliConPersoneByIncaricoIDAndAnno(idIncarico,anno);
	    	model.addAttribute("struttura", incarico.getDenominazioneStruttura());
	    	model.addAttribute("competenze", incarico.competenzeStruttura);
	    	model.addAttribute("responsabile", incarico.responsabile);
	        model.addAttribute("anno", anno);
	        model.addAttribute("listObiettivi", listObiettivi);
            model.addAttribute("incarico", incarico);
        //
        return "geko/rendicontazione/controller/modifyIndicazioniIncaricoController";
        }
        else return "redirect:/ROLE_CONTROLLER";
    }
    
    
    // --------- scadenzario --------------------------
    @RequestMapping(value ="scadenze/{anno}/{idIncarico}" , method = RequestMethod.GET )
    
    public String scadenze(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico,Model model) {
    	
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettivi = objServizi.findObiettiviDirettiAndSubordinatiByIncaricoIDAndAnno(idIncarico, anno);
    	model.addAttribute("struttura", incarico.denominazioneStruttura);
    	model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("anno", anno);
        model.addAttribute("listObiettivi", listObiettivi);
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
        PropertyComparator.sort(lstAzioniDept, new MutableSortDefinition("scadenza",true,true));
        model.addAttribute("listAzioni",lstAzioniDept);
    	//
    	
        return "geko/rendicontazione/controller/listScadenzeAzioniController";
        
	}
    
} // ------------------------------------

