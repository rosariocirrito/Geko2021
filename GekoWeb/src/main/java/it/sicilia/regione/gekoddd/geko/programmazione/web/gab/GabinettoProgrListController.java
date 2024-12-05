package it.sicilia.regione.gekoddd.geko.programmazione.web.gab;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategica;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategicaQryService;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategico;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategicoQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgramma;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgrammaQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.command.Command;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.missione.Missione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.missione.MissioneQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoQryService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.ValutazioneQryService;
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
@RequestMapping("/gabProg")
public class GabinettoProgrListController  {
	
	private Log log = LogFactory.getLog(GabinettoProgrListController.class);
	
	//
	@Autowired
    private ObiettivoStrategicoQryService objStratServizi;
	
    @Autowired
    private ObiettivoQryService objServizi;
    
    @Autowired
    private AreaStrategicaQryService areaStrategicaQryServizi;
    
    @Autowired
    private MissioneQryService missioneQryServizi;
    
    @Autowired
    private ValutazioneQryService valutazioneDirigServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    
    @Autowired
    private AssociazProgrammaQryService associazProgrammaServizi;
    
    @Autowired
    private Menu menu;
    
    @Autowired
    private EventoService evtServizi;
    //
    public GabinettoProgrListController() { }
    
    //
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
    // metodi privati ----------------------------------------------------------------
    private List<Command> listAvailableObjCommands(Obiettivo obj){
    	List<Command> allowed = new ArrayList<Command>();
    	List<Command> objCmds = obj.getAllowedCommands();
    	final String role = "GABINETTO";
    	//log.info("listAvailableCommands for obj"+obj.getDescrizione());
    	//
    	if(null!=objCmds && !objCmds.isEmpty()){
    	for (Command cmd : objCmds){
    		//log.info("cmd role: "+cmd.getRole()+" cmd label: "+cmd.getLabel());
    		if (cmd.getRole().equals(role)){
    			//log.info("role ok "+" cmd label: "+cmd.getLabel());

    			switch(cmd.getLabel()) {
    			
    			case "CONCORDA":
    				cmd.setUri("/gabinettoObj/"+obj.getIdObiettivo()+"/concordaApicale");
    				break;	
    			
    			case "EDIT APICALE":
    				cmd.setUri("/gabinettoObj/"+obj.getIdObiettivo()+"/editApicale");
    				break;
    			
    			case "RENDI INTERLOCUTORIO":
    				cmd.setUri("/gabinettoObj/"+obj.getIdObiettivo()+"/rendiInterlocutorio");
    				break;	
    			
    			case "CREA AZIONE APICALE":
    				cmd.setUri("/gabinettoAct/newApicaleDiretta/"+obj.getIdObiettivo());
    				break;
    			/*
    				
    			case "RESPINGI":
    				cmd.setUri("/controllerObj/"+obj.getIdObiettivo()+"/respinge");
    				break;
    				
    			case "RICHIEDI":
    				cmd.setUri("/controllerObj/new/"+menu.getAnno()+"/"+obj.getIncaricoID());
    				break;
    				
    			*/
    				
    			
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
    	final String role = "GABINETTO";
    	//log.info("listAvailableCommands for obj"+obj.getDescrizione());
    	//
    	if(null!=actCmds && !actCmds.isEmpty()){
	    	for (Command cmd : actCmds){
	    		//log.info("cmd role: "+cmd.getRole()+" cmd label: "+cmd.getLabel());
	    		if (cmd.getRole().equals(role)){
	    			//log.info("role ok "+" cmd label: "+cmd.getLabel());
	
	    			
	    			switch(cmd.getLabel()) {
	    			
	    			case "RENDI TASSATIVA":
	    				cmd.setUri("/gabinettoAct/"+act.getIdAzione()+"/rendiTassativa");
	    				break;
	    			case "TOGLI TASSATIVA":
	    				cmd.setUri("/gabinettoAct/"+act.getIdAzione()+"/togliTassativa");
	    				break;	
	    			case "MODIFICA APICALE":
	    				cmd.setUri("/gabinettoAct/"+act.getIdAzione()+"/editApicaleDiretta");
	    				break;
	    			case "ELIMINA APICALE":
	    				cmd.setUri("/gabinettoAct/"+act.getIdAzione()+"/editApicaleDiretta");
	    				break;	
	    			//log.info("questa uri? "+cmd.getUri());
	    			} // switch
	    			
	    			allowed.add(cmd);
	    		} // if
	    	} // for
    	}
    	return allowed;
    } // fine metodo listAvailableActCommands 
    
    // fine metodi privati--------------------------------
    
    
 
    //-----------------------Direttiva --------------------
    // lista compatta pianificazione obiettivi e azioni del dipartimento
    @RequestMapping(value="listDirettivaGab/{anno}", method = RequestMethod.GET)
    public String listDirettivaSepicos(@PathVariable("anno") int anno, Model model) {
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
        //
    	model.addAttribute("listAreeStrategiche", listAreeStrategiche); 
        model.addAttribute("anno", anno);
        //
        String pageName = "geko2019/pianificazione/listDirettiva";
        model.addAttribute("pageName", pageName);
        return pageName;
    }  
    
    
    
    // lista obiettivi e azioni apicali
    @RequestMapping(value="listPianificazioneApicaleIncaricoGab/{anno}/{idIncarico}", method = RequestMethod.GET)
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
        if (null!= listObiettiviApicali && !listObiettiviApicali.isEmpty())
        model.addAttribute("listObiettivi", listObiettiviApicali);
        //
        String pageName = "geko/programmazione/gab/listPianificazioneIncaricoApicaleGab";
        model.addAttribute("pageName", pageName);
        return pageName;
    }
    
    // gestisce programmazione
    @RequestMapping(value="modifyPianificazioneApicaleIncaricoGab/{anno}/{idIncarico}", method = RequestMethod.GET)
    public String modifyPianificazioneApicaleIncaricoControllerGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, Model model) {
    	/*
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettiviApicali = objServizi.findObiettiviApicaliDirettiAndSubordinatiByIncaricoIDAndAnno(idIncarico, anno);
    	
    	//
    	model.addAttribute("dipartimento", incarico.denominazioneStruttura);
    	model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("anno", anno);
        if (null!= listObiettiviApicali && !listObiettiviApicali.isEmpty())
        model.addAttribute("listObiettivi", listObiettiviApicali);
        //
        */
        
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> lstObjs = objServizi.findObiettiviApicaliDirettiByIncaricoIDAndAnno(idIncarico, anno);
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
    	//    	
    	String pageName = "geko/programmazione/gab/modifyPianificazioneIncaricoApicaleGab";
        model.addAttribute("pageName", pageName);
        return pageName;
    }
    
    
    
    
    
    // 1.3 Associazione
    
    // 1.3.1 lista pianificazione obiettivi e azioni del dipartimento
    // http://localhost:9000/gabProg/associaPianificazioneStrategicaIncaricoApicaleGabinetto/2019/5423
    @RequestMapping(value="associaPianificazioneStrategicaIncaricoApicaleGab/{anno}/{idIncaricoApicale}", method = RequestMethod.GET)
    public String associaPianificazioneStrategicaIncaricoApicaleGabGet(
    		@PathVariable("anno") int anno, @PathVariable("idIncaricoApicale") int idIncaricoApicale, Model model) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncaricoApicale);
    	List<Obiettivo> listObiettiviApicali = objServizi.findObiettiviApicaliDirettiByIncaricoIDAndAnno(idIncaricoApicale, anno);
    	//
    	model.addAttribute("idIncaricoApicale", idIncaricoApicale);
    	model.addAttribute("struttura", incarico.denominazioneStruttura);
    	model.addAttribute("responsabile", incarico.responsabile);
        model.addAttribute("anno", anno);
        model.addAttribute("listObiettivi", listObiettiviApicali);
        return "geko/programmazione/gab/associaPianificazioneStrategicaIncaricoApicaleGabinetto";  
    }  
    
    // 1.3.1 lista pianificazione obiettivi e azioni del dipartimento
    @RequestMapping(value="associaProgrammaIncaricoApicaleGab/{anno}/{idIncaricoApicale}", method = RequestMethod.GET)
    public String associaProgrammaIncaricoApicaleGabGet(
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
        model.addAttribute("anno",anno);
        model.addAttribute("listObiettivi", listObiettiviApicali);
        return "geko/programmazione/gab/associaProgrammaIncaricoApicaleGabinetto";  
    }
    
    
    
    
    // metodi forse non usati
    // lista compatta pianificazione obiettivi e azioni del dipartimento
    @RequestMapping(value="listMissioni", method = RequestMethod.GET)
    public String listMissioniGet(Model model) {
        List<Missione> listMissioni = missioneQryServizi.findAll();
        model.addAttribute("listMissioni", listMissioni); 
        //
        String pageName = "geko/programmazione/listMissioni";
        model.addAttribute("pageName", pageName);
        return pageName;
    } 
 
 // visualizzazione errore nel comando
    @RequestMapping(value="errCmdManager/{idEvento}", method = RequestMethod.GET)
    public String errCmdManagerGet(@PathVariable("idEvento") int idEvento, Model model) {
        //
    	final Evento evento = evtServizi.findById(idEvento);
        model.addAttribute("evento", evento); 
        //
        final String viewName = "errComandoManager";
        model.addAttribute("viewName", viewName); 
        //
        return "geko/programmazione/gabinetto/"+viewName;
    }
    
} // ---------------------------------------------
