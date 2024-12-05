package it.sicilia.regione.gekoddd.geko.programmazione.web.controller;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.FromSecurityQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.UserGeko;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategica;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategicaQryService;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoPluriennale.ObiettivoPluriennale;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoPluriennale.ObiettivoPluriennaleQryService;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategico;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategicoQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgramma;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgrammaQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazioneQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.command.Command;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoQryService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.ValutazioneQryService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneComparto;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneCompartoQryService;
import it.sicilia.regione.gekoddd.log.model.evento.Evento;
import it.sicilia.regione.gekoddd.log.model.evento.EventoService;
import it.sicilia.regione.gekoddd.log.model.journal.Journal;
import it.sicilia.regione.gekoddd.log.model.journal.JournalService;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import java.text.SimpleDateFormat;
import java.util.*;

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
@RequestMapping("/controller")
public class ControllerProgrListController  {
    
	private Log log = LogFactory.getLog(ControllerProgrListController.class);
	//
    @Autowired
    private ObiettivoQryService objServizi;
    @Autowired
    private ObiettivoStrategicoQryService objStratServizi;
    //@Autowired
    private ObiettivoPluriennaleQryService objPlurServizi;
    @Autowired
    private EventoService evtServizi;
    @Autowired
    private JournalService journalServizi;
    @Autowired
    private AreaStrategicaQryService areaStrategicaQryServizi;
    
    @Autowired
    private AssociazProgrammaQryService associazProgrammaServizi;
    @Autowired
    private AzioneAssegnazioneQryService azAssServizi;
    @Autowired
    private ValutazioneCompartoQryService valutazioneCompartoServizi;
    
    @Autowired
    private ValutazioneQryService valutazioneDirigServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    
    @Autowired
    private FromSecurityQryService fromSecurityServizi;
    
    @Autowired
    private Menu menu;
    
    //
    public ControllerProgrListController() { }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
    
    
    
  //-----------------------Programmazione --------------------
    
    private List<Command> listAvailableObjCommands(Obiettivo obj){
    	List<Command> allowed = new ArrayList<Command>();
    	List<Command> objCmds = obj.getAllowedCommands();
    	final String role = "CONTROLLER";
    	//log.info("listAvailableCommands for obj"+obj.getDescrizione());
    	//
    	if(null!=objCmds && !objCmds.isEmpty()){
    	for (Command cmd : objCmds){
    		//log.info("cmd role: "+cmd.getRole()+" cmd label: "+cmd.getLabel());
    		if (cmd.getRole().equals(role)){
    			//log.info("role ok "+" cmd label: "+cmd.getLabel());

    			switch(cmd.getLabel()) {
    			
    			case "ANNULLA":
    				cmd.setUri("/controllerObj/"+obj.getIdObiettivo()+"/annulla");
    				break;	
    				
    			case "EDIT APICALE":
    				cmd.setUri("/controllerObj/"+obj.getIdObiettivo()+"/editApicale");
    				break;
    				
    			case "ELIMINA":
    				cmd.setUri("/controllerObj/"+obj.getIdObiettivo()+"/edit");
    				break;
    			case "MODIFICA":
    				cmd.setUri("/controllerObj/"+obj.getIdObiettivo()+"/edit");
    				break;
    				
    			case "PROPONIGAB":
    				cmd.setUri("/controllerObj/"+obj.getIdObiettivo()+"/proponiGab");
    				break;
    				
    			case "RENDI APICALE":
    				cmd.setUri("/controllerObj/"+obj.getIdObiettivo()+"/apicale");
    				break;
    				
    			case "RENDI INTERLOCUTORIO":
    				cmd.setUri("/controllerObj/"+obj.getIdObiettivo()+"/rendiInterlocutorio");
    				break;
    				
    			case "RENDI PROPOSTO":
    				cmd.setUri("/controllerObj/"+obj.getIdObiettivo()+"/rendiProposto");
    				break;
    				
    			case "RESPINGI":
    				cmd.setUri("/controllerObj/"+obj.getIdObiettivo()+"/respinge");
    				break;
    				
    			case "RICHIEDI":
    				cmd.setUri("/controllerObj/new/"+menu.getAnno()+"/"+obj.getIncaricoID());
    				break;
    				
    			case "RIVEDI":
    				cmd.setUri("/controllerObj/"+obj.getIdObiettivo()+"/rivede");
    				break;
    			
    			case "TOGLI APICALE":
    				cmd.setUri("/controllerObj/"+obj.getIdObiettivo()+"/apicale");
    				break;
    			
    			case "VALIDA":
    				cmd.setUri("/controllerObj/"+obj.getIdObiettivo()+"/valida");
    				break;
    				
    			case "CONCORDA":
    				cmd.setUri("/controllerObj/"+obj.getIdObiettivo()+"/concorda");
    				break;	
    				
    			case "CLONA ANNO+1":
    				cmd.setUri("/controllerObj/"+obj.getIdObiettivo()+"/clona+1");
    				break;	
                                
                        case "CLONA DIR ANNO+1":
                        cmd.setUri("/controllerObj/"+obj.getIdObiettivo()+"/clonaDir+1");
                        break;
    				
    			case "CREA AZIONE":
    				cmd.setUri("/controllerAct/new/"+obj.getIdObiettivo());
    				break;
    				
    			case "CREA AZIONE APICALE":
    				cmd.setUri("/controllerAct/newApicaleDiretta/"+obj.getIdObiettivo());
    				break;
    				
    			
    			}	
    			
    			//
    			allowed.add(cmd);
    		}
    		
    	}
    	} 
    	return allowed;
    }
    
    private List<Command> listAvailableActCommands(Azione act){
    	List<Command> allowed = new ArrayList<Command>();
    	List<Command> actCmds = act.getAllowedCommands();
    	final String role = "CONTROLLER";
    	//log.info("listAvailableCommands for obj"+obj.getDescrizione());
    	//
    	if(null!=actCmds && !actCmds.isEmpty()){
	    	for (Command cmd : actCmds){
	    		//log.info("cmd role: "+cmd.getRole()+" cmd label: "+cmd.getLabel());
	    		if (cmd.getRole().equals(role)){
	    			//log.info("role ok "+" cmd label: "+cmd.getLabel());
	
	    			switch(cmd.getLabel()) {
	    			
	    			case "MODIFICA":
	    				cmd.setUri("/controllerAct/"+act.getIdAzione()+"/edit");
	    				break;
	    			case "ELIMINA":
	    				cmd.setUri("/controllerAct/"+act.getIdAzione()+"/edit");
	    				break;
	    			case "MODIFICA RICHIESTO":
	    				cmd.setUri("/controllerAct/"+act.getIdAzione()+"/editRichiesto");
	    				break;
	    			case "RENDI TASSATIVA":
	    				cmd.setUri("/controllerAct/"+act.getIdAzione()+"/rendiTassativa");
	    				break;
	    			case "TOGLI TASSATIVA":
	    				cmd.setUri("/controllerAct/"+act.getIdAzione()+"/togliTassativa");
	    				break;	
	    			case "MODIFICA APICALE":
	    				cmd.setUri("/controllerAct/"+act.getIdAzione()+"/editApicaleDiretta");
	    				break;
	    			case "ELIMINA APICALE":
	    				cmd.setUri("/controllerAct/"+act.getIdAzione()+"/editApicaleDiretta");
	    				break;	
	    			//log.info("questa uri? "+cmd.getUri());
	    			} // switch
	    			allowed.add(cmd);
	    		} // if
	    	} // for
    	}
    	return allowed;
    } // fine metodo listAvailableActCommands
    
    
    // ------------- Journal -----------------------
    
    // lista strutture / dipendenti // da mettere solo al superadministrator li dà tutti!!!
    @RequestMapping(value="listJournalAllController/{giorniHistory}/{idIncarico}", method = RequestMethod.GET)
    public String listJournalAllController(@PathVariable("giorniHistory") int giorniHistory, 
    		@PathVariable("idIncarico") int idIncarico,
    		Model model) {
    	List<Journal> journals = new ArrayList<Journal>();
    	int anno = menu.getAnno();
    	//
    	Calendar cal = Calendar.getInstance();   // GregorianCalendar
    	// An Easier way to print the timestamp by getting a Date instance
        Date date = cal.getTime();
        // Manipulating Dates
        Calendar calTemp;
        calTemp = (Calendar) cal.clone();
        calTemp.add(Calendar.DAY_OF_YEAR, -giorniHistory);
        // An Easier way to print the timestamp by getting a Date instance
        Date da = calTemp.getTime();
        //
    	List<IncaricoGeko> listIncarichi = new ArrayList<IncaricoGeko>();
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettiviDiretti = objServizi.findObiettiviApicaliDirettiByIncaricoIDAndAnno(idIncarico, anno);
    	incarico.setObiettivi(listObiettiviDiretti);
    	listIncarichi.add(incarico);
        //
        List<IncaricoGeko> listIncarichiDept = fromOrganikoServizi.findIncarichiByDipartimentoIDAndAnno(incarico.pgID, anno);
        for(IncaricoGeko inc : listIncarichiDept){
        	List<Obiettivo> lstObjs = objServizi.findObiettiviTotaliByIncaricoIDAndAnno(inc.idIncarico, anno);
        	inc.setObiettivi(lstObjs);
        }
        listIncarichi.addAll(listIncarichiDept);
    	
    	
        for(IncaricoGeko inc2 : listIncarichi){
        	final UserGeko user = fromSecurityServizi.findByPfIDAndAppl(inc2.pfID,"geko");
            journals.addAll(journalServizi.findByChiAndQuandoAfter(user.getUsername(),da));
        }
        //
        //
        model.addAttribute("journals", journals);    
        model.addAttribute("giorni",giorniHistory);
        //
        return "geko/programmazione/controller/listJournalAllController";
    }
    
 // ------------- Journal -----------------------
    // lista strutture / dipendenti 
    @RequestMapping(value="listJournalUserController/{giorniHistory}/{idIncarico}", method = RequestMethod.GET)
    public String listJournalUserController(@PathVariable("giorniHistory") int giorniHistory, 
    		@PathVariable("idIncarico") int idIncarico,
    		Model model) {
    	//
    	Calendar cal = Calendar.getInstance();   // GregorianCalendar
    	// An Easier way to print the timestamp by getting a Date instance
        Date date = cal.getTime();
        // Manipulating Dates
        Calendar calTemp;
        calTemp = (Calendar) cal.clone();
        calTemp.add(Calendar.DAY_OF_YEAR, -giorniHistory);
        // An Easier way to print the timestamp by getting a Date instance
        Date da = calTemp.getTime();
        //
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	final UserGeko user = fromSecurityServizi.findByPfIDAndAppl(incarico.pfID,"geko");
        List<Journal> journals = journalServizi.findByChiAndQuandoAfter(user.getUsername(),da);
        //
        model.addAttribute("chi", user.getUsername());  
        model.addAttribute("journals", journals);    
        model.addAttribute("giorni",giorniHistory);
        //
        return "geko/programmazione/controller/listJournalAllController";
    }
    

    
    
  //-----------------------Direttiva --------------------
    // lista compatta pianificazione obiettivi e azioni del dipartimento
    @RequestMapping(value="listDirettivaController/{anno}", method = RequestMethod.GET)
    public String listDirettivaController(@PathVariable("anno") int anno, Model model) {
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
    
 
    

  //-----------------------Avvisi --------------------
    // lista compatta pianificazione obiettivi e azioni del dipartimento
    @RequestMapping(value="listAvvisiController", method = RequestMethod.GET)
    public String listAvvisiGet(Model model) {
        
        //
        return "geko/programmazione/controller/listAvvisiController";
    }  
   
    // __________________ metodi nuovi su incarico _____________________________
    //
    
    
    // -------------------------- pianificazione ----------------------
    
    
 // -------------------------- pianificazione ----------------------
    
   
    
    //
   
   

    
 
    
    
    // ------------------------------------------ 1. Programmazione ---------------------------------------------------------------
    
    // 1.1 -----------------------------
    // 1.1.1 sintesi pianificazione obiettivi e azioni del dipartimento
    @RequestMapping(value="sintesiPianificazioneDipartimentoController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String sintesiPianificazioneDipartimentoController(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico,Model model) {
    	List<IncaricoGeko> listIncarichi = new ArrayList<IncaricoGeko>();
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettiviDiretti = objServizi.findObiettiviApicaliDirettiByIncaricoIDAndAnno(idIncarico, anno);
    	incarico.setObiettivi(listObiettiviDiretti);
    	listIncarichi.add(incarico);
        //
        List<IncaricoGeko> listIncarichiDept = fromOrganikoServizi.findIncarichiByDipartimentoIDAndAnno(incarico.pgID, anno);
        for(IncaricoGeko inc : listIncarichiDept){
            List<Obiettivo> lstObjs = objServizi.findObiettiviTotaliByIncaricoIDAndAnno(inc.idIncarico, anno);
            if(!lstObjs.equals(null) && !lstObjs.isEmpty()) { 
                for (Obiettivo obj : lstObjs){
                    obj.setIncarico(inc);
                }
            }
            inc.setObiettivi(lstObjs);
        }
        
        
        listIncarichi.addAll(listIncarichiDept);
        model.addAttribute("dipartimento", incarico.denominazioneStruttura);
        model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("incarico", incarico);
        model.addAttribute("listIncarichiDept", listIncarichi);
        model.addAttribute("anno", anno);
        model.addAttribute("numero", listIncarichi.size());
        model.addAttribute("pgID", incarico.pgID);
        return "geko/programmazione/controller/sintesiPianificazioneAnnoController";
        //return "prova";
    }  
    
    // 1.1.2 lista compatta pianificazione obiettivi e azioni del dipartimento
    @RequestMapping(value="listCompattaProgrammazioneDipartimentoController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listCompattaProgrammazioneDipartimentoController(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	List<IncaricoGeko> listIncarichi = new ArrayList<IncaricoGeko>();
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettiviDiretti = objServizi.findObiettiviApicaliDirettiByIncaricoIDAndAnno(idIncarico, anno);
    	incarico.setObiettivi(listObiettiviDiretti);
    	listIncarichi.add(incarico);
        //
        List<IncaricoGeko> listIncarichiDept = fromOrganikoServizi.findIncarichiByDipartimentoIDAndAnno(incarico.pgID, anno);
        for(IncaricoGeko inc : listIncarichiDept){
        	List<Obiettivo> lstObjs = objServizi.findObiettiviTotaliByIncaricoIDAndAnno(inc.idIncarico, anno);
        	if(lstObjs !=null && !lstObjs.isEmpty()) inc.setObiettivi(lstObjs);
        }
        listIncarichi.addAll(listIncarichiDept);
        model.addAttribute("dipartimento", incarico.denominazioneStruttura);
        model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("incarico", incarico);
        model.addAttribute("listIncarichiDept", listIncarichi);
        model.addAttribute("anno", anno);
        return "geko/programmazione/controller/listProgrammazioneCompattaDipartimentoController";
    }  
	
	// 1.1.2b lista compatta pianificazione obiettivi e azioni del dipartimento
    @RequestMapping(value="listCompattaProgrammazionePopDipartimentoController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listCompattaProgrammazionePopDipartimentoController(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	List<IncaricoGeko> listIncarichi = new ArrayList<IncaricoGeko>();
        final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);    	        
//
        List<IncaricoGeko> listIncarichiDept = fromOrganikoServizi.findIncarichiPopByDipartimentoIDAndAnno(incarico.pgID, anno);
        for(IncaricoGeko inc : listIncarichiDept){
        	List<Obiettivo> lstObjs = objServizi.findObiettiviTotaliByIncaricoIDAndAnno(inc.idIncarico, anno);
			if(lstObjs !=null && !lstObjs.isEmpty())inc.setObiettivi(lstObjs);
        }
        listIncarichi.addAll(listIncarichiDept);
        model.addAttribute("dipartimento", incarico.denominazioneStruttura);
        model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("incarico", incarico);
        model.addAttribute("listIncarichiDept", listIncarichi);
        model.addAttribute("anno", anno);
        return "geko/programmazione/controller/listProgrammazioneCompattaPopDipartimentoController";
    }  
    
    // 1.1.3 lista obiettivi e azioni apicali
    @RequestMapping(value="listPianificazioneCompletaIncaricoController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listPianificazioneCompletaIncaricoControllerGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	
    	List<IncaricoGeko> listIncarichi = new ArrayList<IncaricoGeko>();
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettiviDiretti = objServizi.findObiettiviApicaliDirettiByIncaricoIDAndAnno(idIncarico, anno);
    	incarico.setObiettivi(listObiettiviDiretti);
    	listIncarichi.add(incarico);
        //
        List<IncaricoGeko> listIncarichiDept = fromOrganikoServizi.findIncarichiByDipartimentoIDAndAnno(incarico.pgID, anno);
        for(IncaricoGeko inc : listIncarichiDept){
        	final List<Obiettivo> lstObjs = objServizi.findObiettiviTotaliByIncaricoIDAndAnno(inc.idIncarico, anno);
        	inc.setObiettivi(lstObjs);
        	//
			List<Valutazione> lstValutDirig = valutazioneDirigServizi.findByIncaricoIDAndAnno(inc.idIncarico, anno); // una sola in realt�
			
			if (lstValutDirig!=null && !lstValutDirig.isEmpty()) {
				inc.setValutazioni(lstValutDirig);
			}
			else {
				List<Valutazione> emptyList = new ArrayList<Valutazione>();
				inc.setValutazioni(lstValutDirig);
			}
        	
        }
        listIncarichi.addAll(listIncarichiDept);
        
        model.addAttribute("dipartimento", incarico.denominazioneStruttura);
        model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("incarico", incarico);
        model.addAttribute("listIncarichiDept", listIncarichi);
        model.addAttribute("anno", anno);
            
        return "geko/programmazione/controller/listPianificazioneTotaleIncaricoController";
       
    }
    
    
 // 1.1.4 modifica pianificazione obiettivi e azioni del dipartimento
    @RequestMapping(value="modifyPianificazioneCompletaIncaricoController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String modifyPianificazioneDipartimentoController(@PathVariable("anno") int anno,@PathVariable("idIncarico") int idIncarico, Model model) {
    	List<IncaricoGeko> listIncarichi = new ArrayList<IncaricoGeko>();
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettiviDiretti = objServizi.findObiettiviApicaliDirettiByIncaricoIDAndAnno(idIncarico, anno);
    	incarico.setObiettivi(listObiettiviDiretti);
    	if(anno>2018){
    		List<Valutazione> lstValutDirig = valutazioneDirigServizi.findByIncaricoIDAndAnno(incarico.idIncarico, anno); // una sola in realt�
        	
        	if (lstValutDirig!=null && !lstValutDirig.isEmpty()) {
        		incarico.setValutazioni(lstValutDirig);
        	}
        	else {
        		List<Valutazione> emptyList = new ArrayList<Valutazione>();
        		incarico.setValutazioni(lstValutDirig);
        	}
    	}
    	listIncarichi.add(incarico);
        //
        List<IncaricoGeko> listIncarichiDept = fromOrganikoServizi.findIncarichiByDipartimentoIDAndAnno(incarico.pgID, anno);
        for(IncaricoGeko inc : listIncarichiDept){
        	List<Obiettivo> lstObjs = objServizi.findObiettiviTotaliByIncaricoIDAndAnno(inc.idIncarico, anno);
        	for (Obiettivo obj : lstObjs){
        		obj.setIncarico(inc);
	    		List<Command> cmds = this.listAvailableObjCommands(obj);
	    		obj.setGuiCommands(cmds);
	    		for(Azione act: obj.getAzioni()){
	    			List<Command> actcmds = this.listAvailableActCommands(act);
		    		act.setGuiCommands(actcmds);
	    		}
	    	}
        	inc.setObiettivi(lstObjs);
        	//
			List<Valutazione> lstValutDirig = valutazioneDirigServizi.findByIncaricoIDAndAnno(inc.idIncarico, anno); // una sola in realt�
			if (lstValutDirig!=null && !lstValutDirig.isEmpty()) {
				inc.setValutazioni(lstValutDirig);
			}

        }
        listIncarichi.addAll(listIncarichiDept);
        model.addAttribute("dipartimento", incarico.denominazioneStruttura);
        model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("incarico", incarico);
        model.addAttribute("listIncarichiDept", listIncarichi);
        model.addAttribute("anno", anno);
        
        final String pageName = "geko/programmazione/controller/modifyPianificazioneTotaleIncaricoController";
        model.addAttribute("pageName", pageName); 
        //
        return pageName;
        
    }  
    
    // 1.1. fine --------------------------
    
    // 1.2. ------------------------------------
    
    
    // 1.2.2 lista obiettivi e azioni apicali
    @RequestMapping(value="listPianificazioneApicaleIncaricoController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listPianificazioneApicaleIncaricoControllerGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettiviApicali = objServizi.findObiettiviApicaliDirettiAndSubordinatiByIncaricoIDAndAnno(idIncarico, anno);
    	//
    	model.addAttribute("dipartimento", incarico.denominazioneStruttura);
    	model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("anno", anno);
        model.addAttribute("listObiettivi", listObiettiviApicali);
        return "geko/programmazione/controller/listPianificazioneIncaricoApicaleController";
    }
    
 // 1.2.3 modifica pianificazione apicale diretta
    @RequestMapping(value="modifyPianificazioneApicaleDirettaController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String modifyPianificazioneApicaleDirettaControllerGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	//        
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> lstObjs = objServizi.findObiettiviApicaliDirettiConPersoneByIncaricoIDAndAnno(idIncarico, anno);
    	for (Obiettivo obj : lstObjs){
    		obj.setIncarico(incarico);
    		List<Command> cmds = this.listAvailableObjCommands(obj);
    		obj.setGuiCommands(cmds);
    		for(Azione act: obj.getAzioni()){
    			List<Command> actcmds = this.listAvailableActCommands(act);
	    		act.setGuiCommands(actcmds);
    		}
    	}
    	incarico.setObiettivi(lstObjs);
    	if(anno>2018){
    		List<Valutazione> lstValutDirig = valutazioneDirigServizi.findByIncaricoIDAndAnno(incarico.idIncarico, anno); // una sola in realt�
        	
        	if (lstValutDirig!=null && !lstValutDirig.isEmpty()) {
        		incarico.setValutazioni(lstValutDirig);
        	}
        	else {
        		List<Valutazione> emptyList = new ArrayList<Valutazione>();
        		incarico.setValutazioni(lstValutDirig);
        	}
    	}
    	
        //
    	model.addAttribute("idIncaricoApicale", idIncarico);
    	model.addAttribute("dipartimento", incarico.denominazioneStruttura);
        model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("incarico", incarico);
        model.addAttribute("anno", anno);
        model.addAttribute("listObiettivi", lstObjs);
        return "geko/programmazione/controller/modifyPianificazioneIncaricoApicaleDirettoController";
    }  
    
    // 1.2.4 modifica pianificazione apicale subordinata
    @RequestMapping(value="modifyPianificazioneApicaleSubordinataController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String modifyPianificazioneApicaleSubordinataControllerGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	List<IncaricoGeko> listIncarichi = new ArrayList<IncaricoGeko>();
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
        //
    	final List<Obiettivo> lstObjs = objServizi.findObiettiviApicaliSubordinatiByIncaricoIDAndAnno(idIncarico, anno);
        for(Obiettivo obj : lstObjs){
        	obj.setIncarico(incarico);List<Command> cmds = this.listAvailableObjCommands(obj);
    		obj.setGuiCommands(cmds);
    		for(Azione act: obj.getAzioni()){
    			List<Command> actcmds = this.listAvailableActCommands(act);
	    		act.setGuiCommands(actcmds);
    		}
        	IncaricoGeko inc = fromOrganikoServizi.findIncaricoById(obj.getIncaricoID());
        	listIncarichi.add(inc);
        }
        for (IncaricoGeko inc : listIncarichi){
        	List<Obiettivo> lstObjIncarico = objServizi.findObiettiviTotaliByIncaricoIDAndAnno(inc.idIncarico, anno);
        	List<Obiettivo> lstObjApicali = new ArrayList<Obiettivo>();
        	for(Obiettivo obj: lstObjIncarico){
        		obj.setIncarico(inc);
        		if(obj.isApicale()) lstObjApicali.add(obj);
        	}
        	inc.setObiettivi(lstObjApicali);
        }
        model.addAttribute("dipartimento", incarico.denominazioneStruttura);
        model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("incarico", incarico);
        model.addAttribute("listIncarichiDept", listIncarichi);
        model.addAttribute("anno", anno);
        model.addAttribute("idIncaricoApicale", idIncarico);
        return "geko/programmazione/controller/modifyPianificazioneIncaricoApicaleSubordinatoController";
    }  
        
    // 1.3 Associazione
 
    // 1.3.1 lista pianificazione obiettivi e azioni del dipartimento
    @RequestMapping(value="associaPianificazioneStrategicaIncaricoApicaleController/{anno}/{idIncaricoApicale}", method = RequestMethod.GET)
    public String associaPianificazioneStrategicaIncaricoApicaleControllerGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncaricoApicale") int idIncaricoApicale, Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncaricoApicale);
    	List<Obiettivo> listObiettiviApicali = objServizi.findObiettiviApicaliDirettiByIncaricoIDAndAnno(idIncaricoApicale, anno);
    	//
    	model.addAttribute("idIncaricoApicale", idIncaricoApicale);
    	model.addAttribute("struttura", incarico.denominazioneStruttura);
    	model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("anno", anno);
        model.addAttribute("listObiettivi", listObiettiviApicali);
        return "geko/programmazione/controller/associaPianificazioneStrategicaIncaricoApicaleController";  
    }  
    
 // 1.3.1 lista pianificazione obiettivi e azioni del dipartimento
    @RequestMapping(value="associaProgrammaIncaricoApicaleController/{anno}/{idIncaricoApicale}", method = RequestMethod.GET)
    public String associaProgrammaIncaricoApicaleControllerGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncaricoApicale") int idIncaricoApicale, Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncaricoApicale);
    	List<Obiettivo> listObiettiviApicali = objServizi.findObiettiviApicaliDirettiByIncaricoIDAndAnno(idIncaricoApicale, anno);
    	//
    	for (Obiettivo obj : listObiettiviApicali){
    		List<AssociazProgramma> lista = associazProgrammaServizi.findByApicale(obj);
    		obj.setAssociazProgramma(lista);
    	}
    	model.addAttribute("idIncaricoApicale", idIncaricoApicale);
    	model.addAttribute("struttura", incarico.denominazioneStruttura);
    	model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("anno", anno);
        model.addAttribute("listObiettivi", listObiettiviApicali);
        return "geko/programmazione/controller/associaProgrammaIncaricoApicaleController";  
    }
    
    // 1.5.2 modifica obiettivi e azioni di un incarico manageriale 
    @RequestMapping(value="modifyPianificazioneIncaricoController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String modifyPianificazioneIncaricoController(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {    	     	 
    	//
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	if (null != incarico) {
	    	//final List<Obiettivo> listObiettivi = incarico.getObiettiviTotAnno();
	    	//List<Obiettivo> listObiettivi = objServizi.findObiettiviPrioritariByIncaricoIDAndAnno(idIncarico, anno);
    		List<Obiettivo> listObiettivi = objServizi.findObiettiviDirigenzialiConPersoneByIncaricoIDAndAnno(idIncarico, anno);//30/04/2020
    		for (Obiettivo obj : listObiettivi){
	    		obj.setIncarico(incarico);
	    		List<Command> cmds = this.listAvailableObjCommands(obj);
	    		obj.setGuiCommands(cmds);
	    		for(Azione act: obj.getAzioni()){
	    			List<Command> actcmds = this.listAvailableActCommands(act);
		    		act.setGuiCommands(actcmds);
	    		}
	    	}
	    	//
        	
    		List<Valutazione> lstValutDirig = valutazioneDirigServizi.findByIncaricoIDAndAnno(incarico.idIncarico, anno); // una sola in realt�
        	
        	if (lstValutDirig!=null && !lstValutDirig.isEmpty()) {
        		incarico.setValutazioni(lstValutDirig);
        	}
        	else {
        		List<Valutazione> emptyList = new ArrayList<Valutazione>();
        		incarico.setValutazioni(lstValutDirig);
        	}
        	
	    	model.addAttribute("struttura", incarico.getDenominazioneStruttura());
	    	model.addAttribute("competenze", incarico.competenzeStruttura);
	    	model.addAttribute("responsabile", incarico.responsabile);
	        model.addAttribute("anno", anno);
	        model.addAttribute("listObiettivi", listObiettivi);
	        model.addAttribute("incarico", incarico);
	        model.addAttribute("idIncaricoApicale", menu.getIdIncaricoApicaleDeptScelta());
    	}
    	final String pageName = "geko/programmazione/controller/modifyPianificazioneIncaricoGuiController";
        model.addAttribute("pageName", pageName); 
        //
        return pageName;
    }
    
    // controller/richiediApicaleIncaricoController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}/${menu.incaricoDept.idIncarico}">
    @RequestMapping(value="richiediApicaleIncaricoController/{anno}/{idIncaricoApic}/{idIncarico}", method = RequestMethod.GET)
    public String richiediApicaleIncaricoControllerGet(
    		@PathVariable("anno") int anno, 
    		@PathVariable("idIncaricoApic") int idIncaricoApic,
    		@PathVariable("idIncarico") int idIncarico,    		
    		Model model) {
    	//
    	final IncaricoGeko incaricoApic = fromOrganikoServizi.findIncaricoById(idIncaricoApic);
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List <IncaricoGeko> incarichiDept = menu.getIncarichiDept();
    	List<Obiettivo> lstObjs = objServizi.findObiettiviApicaliDirettiConPersoneByIncaricoIDAndAnno(idIncaricoApic, anno);
    	//
    	for (Obiettivo obj : lstObjs){
    		List<IncaricoGeko> listaIncxObj = new ArrayList<IncaricoGeko>();
    		obj.setIncarico(incarico);
    		List<Command> cmds = new ArrayList<Command>();
    		Command cmd = new Command("CONTROLLER","RICHIEDI");
    		cmd.setUri("/controllerIncarico/richiediApicale/"+incarico.getIdIncarico()); // con jsp aggiungo objApic
    		cmds.add(cmd);
    		obj.setGuiCommands(cmds); 
    		//
    		List<Obiettivo> listaObjCodice = objServizi.findByCodiceAndAnno(obj.getCodice(),anno);
    		for (Obiettivo obj2 : listaObjCodice){
    			IncaricoGeko inc2 = fromOrganikoServizi.findIncaricoById(obj2.getIncaricoID());
    			if (incarichiDept.contains(inc2)) listaIncxObj.add(inc2);
    		}
    		obj.setIncarichiDirig(listaIncxObj);
    	}
    	incaricoApic.setObiettivi(lstObjs);   	
        //
    	model.addAttribute("idIncaricoApicale", idIncarico);
    	model.addAttribute("dipartimento", incaricoApic.denominazioneStruttura);
        model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("incaricoApic", incaricoApic);
        model.addAttribute("incarico", incarico);
        model.addAttribute("anno", anno);
        model.addAttribute("listObiettivi", lstObjs);
        // 
        final String pageName = "geko/programmazione/controller/richiediApicaleIncaricoController";
        model.addAttribute("pageName", pageName); 
        //
        return pageName;
    }  
    
    
 // 1.5.3 lista obiettivi e azioni di altra struttura 
    @RequestMapping(value="listPianificazioneIncaricoController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listPianificazioneOtherStructureControllerGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	//
    	List<Valutazione> lstValutDirig = valutazioneDirigServizi.findByIncaricoIDAndAnno(incarico.idIncarico, anno); // una sola in realt�
    	if (lstValutDirig!=null && !lstValutDirig.isEmpty()) {
    		incarico.setValutazioni(lstValutDirig);
    	}
    	else {
    		List<Valutazione> emptyList = new ArrayList<Valutazione>();
    		incarico.setValutazioni(lstValutDirig);
    	}
    	if (null != incarico) {
	    	//final List<Obiettivo> listObiettivi = incarico.getObiettiviTotAnno();
	    	//final List<Obiettivo> listObiettivi = objServizi.findObiettiviTotaliByIncaricoIDAndAnno(idIncarico,anno);
	    	final List<Obiettivo> listObiettivi = objServizi.findObiettiviPrioritariByIncaricoIDAndAnno(idIncarico, anno);
	    	model.addAttribute("struttura", incarico.getDenominazioneStruttura());
	    	model.addAttribute("competenze", incarico.competenzeStruttura);
	    	model.addAttribute("responsabile", incarico.responsabile);
	        model.addAttribute("anno", anno);
	        model.addAttribute("listObiettivi", listObiettivi);
	        model.addAttribute("incarico", incarico);
            return "geko/programmazione/controller/listPianificazioneIncaricoController";
        }
        else return "redirect:/ROLE_CONTROLLER";
    }
    
    
    // 1.5.4 lista obiettivi e azioni di altra struttura 
    @RequestMapping(value="navigaPianificazioneIncaricoController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String navigaPianificazioneOtherStructureControllerGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	//
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	final PersonaGiuridicaGeko dept = fromOrganikoServizi.findDipartimentoByPersonaFisicaID(incarico.pfID);
    	final List<IncaricoGeko> listIncarichiDept = menu.getIncarichiDept();//fromOrganikoServizi.findIncarichiByDipartimentoIDAndAnno(dept.idPersona, anno);
    	if (null != incarico) {
	    	//final List<Obiettivo> listObiettivi = incarico.getObiettiviTotAnno();
	    	//final List<Obiettivo> listObiettivi = objServizi.findObiettiviTotaliByIncaricoIDAndAnno(idIncarico,anno);
	    	final List<Obiettivo> listObiettivi = objServizi.findObiettiviPrioritariByIncaricoIDAndAnno(idIncarico, anno);
        	model.addAttribute("incarico", incarico);
            model.addAttribute("anno", anno);
            model.addAttribute("listObiettivi", listObiettivi);
            model.addAttribute("listIncarichiDept", listIncarichiDept);
            return "geko/programmazione/controller/navigaPianificazioneIncaricoController";
        }
        else return "redirect:/ROLE_CONTROLLER";
    }
    
 // visualizzazione errore nel comando
    @RequestMapping(value="errCmdController/{idEvento}", method = RequestMethod.GET)
    public String errCmdManagerGet(@PathVariable("idEvento") int idEvento, Model model) {
        //
    	final Evento evento = evtServizi.findById(idEvento);
        model.addAttribute("evento", evento); 
        //
        final String viewName = "errComandoController";
        model.addAttribute("viewName", viewName); 
        //
        return "geko/programmazione/controller/"+viewName;
    }
    
 // modifica assegnazione dipendenti agli obiettivi della struttura 
    @RequestMapping(value="modifyCompartoProgrammazione/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String modifyDipendentiAssegnazioniProgrammazioneGet(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico,Model model) {
    	// lista valutazione dipendenti della struttura e sottostruttura
          
        	//
        	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
        	final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
        	final PersonaGiuridicaGeko struttura = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);
        	
            if (null != manager) {
            	log.info("modifyCompartoProgrammazione dirigente= "+manager.cognome);
            	log.info("modifyCompartoProgrammazione struttura= "+struttura.codice);
            	List<PersonaFisicaGeko> listDipendenti = fromOrganikoServizi.findDipendentiByDipartimentoIDAndAnno(incarico.pgID,anno);
            	//struttura.getDipendenti();
            	log.info("modifyCompartoProgrammazione listDipendenti.size= "+listDipendenti.size());
            	listDipendenti.remove(manager);
            	//
            	Map<PersonaFisicaGeko,List<AzioneAssegnazione>> mapDipendentiAssegnazioni = new LinkedHashMap<PersonaFisicaGeko,List<AzioneAssegnazione>>();
            	Map<PersonaFisicaGeko,List<ValutazioneComparto>> mapDipendentiValutazioneComparto = new LinkedHashMap<PersonaFisicaGeko,List<ValutazioneComparto>>();
            	if (listDipendenti!= null && !listDipendenti.isEmpty()){
		            //
		            for(PersonaFisicaGeko pf : listDipendenti){
		            	//pf.setAnno(anno);
		            	pf.setIncaricoValutazioneID(idIncarico);
		            	//
		            	List<AzioneAssegnazione> assegnazioni = azAssServizi.findByPfIDAndIncaricoIDAndAnno(pf.idPersona, idIncarico, anno);
		            	if (!assegnazioni.isEmpty()) {
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
			        		pf.setValutazioni(emptyList);
			            	mapDipendentiValutazioneComparto.put(pf, emptyList);
			        	}
		            	//System.out.println("listDipendentiAssegnazioniProgrammazioneGet() pf= "+pf.stringa);
		            }
            	}
            //
            model.addAttribute("mapDipendentiAssegnazioni", mapDipendentiAssegnazioni);  
            model.addAttribute("mapDipendentiValutazioneComparto", mapDipendentiValutazioneComparto);
            model.addAttribute("struttura", struttura);
            model.addAttribute("anno", anno);
            //
            } 
            return "geko/programmazione/controller/modifyProgrammazioneCompartoController";
        }
    	
 // modifica assegnazione dipendenti agli obiettivi della struttura 
    @RequestMapping(value="listCompartoProgrammazione/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String listDipendentiAssegnazioniProgrammazioneGet(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico,Model model) {
    	// lista valutazione dipendenti della struttura e sottostruttura
          
        	//
        	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
        	final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
        	final PersonaGiuridicaGeko struttura = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);
        	
            if (null != manager) {
            	log.info("modifyCompartoProgrammazione dirigente= "+manager.cognome);
            	log.info("modifyCompartoProgrammazione struttura= "+struttura.codice);
            	List<PersonaFisicaGeko> listDipendenti = fromOrganikoServizi.findDipendentiByDipartimentoIDAndAnno(incarico.pgID,anno);
            	//struttura.getDipendenti();
            	log.info("modifyCompartoProgrammazione listDipendenti.size= "+listDipendenti.size());
            	listDipendenti.remove(manager);
            	//
            	Map<PersonaFisicaGeko,List<AzioneAssegnazione>> mapDipendentiAssegnazioni = new LinkedHashMap<PersonaFisicaGeko,List<AzioneAssegnazione>>();
            	Map<PersonaFisicaGeko,List<ValutazioneComparto>> mapDipendentiValutazioneComparto = new LinkedHashMap<PersonaFisicaGeko,List<ValutazioneComparto>>();
            	if (listDipendenti!= null && !listDipendenti.isEmpty()){
		            //
		            for(PersonaFisicaGeko pf : listDipendenti){
		            	//pf.setAnno(anno);
		            	pf.setIncaricoValutazioneID(idIncarico);
		            	//
		            	List<AzioneAssegnazione> assegnazioni = azAssServizi.findByPfIDAndIncaricoIDAndAnno(pf.idPersona, idIncarico, anno);
		            	if (!assegnazioni.isEmpty()) {
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
			        		pf.setValutazioni(emptyList);
			            	mapDipendentiValutazioneComparto.put(pf, emptyList);
			        	}
		            	//System.out.println("listDipendentiAssegnazioniProgrammazioneGet() pf= "+pf.stringa);
		            }
            	}
            //
            model.addAttribute("mapDipendentiAssegnazioni", mapDipendentiAssegnazioni);  
            model.addAttribute("mapDipendentiValutazioneComparto", mapDipendentiValutazioneComparto);
            model.addAttribute("struttura", struttura);
            model.addAttribute("anno", anno);
            //
            } 
            return "geko/programmazione/controller/listProgrammazioneCompartoController";
        }
    
    private List<Command> listAvailableObjPlurCommands(ObiettivoPluriennale obj){
    	//List<Command> allowed = new ArrayList<Command>();
    	List<Command> objCmds = obj.getAllowedCommands();
    	
        return objCmds;
    }
    
    // 
    @RequestMapping(value="modifyProgrTriennaleController/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String modifyProgrTriennaleControllerGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	//        
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<ObiettivoPluriennale> lstObjs = objPlurServizi.findObiettiviPluriennaliByIncaricoIDAndAnno(idIncarico, anno);
    	for (ObiettivoPluriennale obj : lstObjs){
    		obj.setIncaricoID(idIncarico);    
    		List<Command> cmds = this.listAvailableObjPlurCommands(obj);
    		obj.setGuiCommands(cmds);                
    	}
        //
    	model.addAttribute("idIncaricoApicale", idIncarico);
    	model.addAttribute("dipartimento", incarico.denominazioneStruttura);
        model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("incarico", incarico);
        model.addAttribute("anno", anno);
        model.addAttribute("listObiettivi", lstObjs);
        return "geko/programmazione/controller/modifyProgrTriennaleController";
    }  
} // ------------------------------------

