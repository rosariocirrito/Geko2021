package it.sicilia.regione.gekoddd.session.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.FromSecurityQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.UserGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazioneQryService;
import it.sicilia.regione.gekoddd.log.model.journal.JournalService;
import it.sicilia.regione.gekoddd.session.domain.Menu;
import it.sicilia.regione.gekoddd.session.domain.MenuValidator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes(types = Menu.class)
public class MenuController {
    
    private Log log = LogFactory.getLog(MenuController.class);	
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    
    @Autowired
    private FromSecurityQryService fromSecurityServizi;
    
    @Autowired
    AzioneAssegnazioneQryService azioneAssegnazioneQryServizi;
    
    @Autowired
    private JournalService journalServizi;
    
    @Autowired
    private Menu menu;
    
    @ModelAttribute("menu")
    public Menu getMenu(){
    	return menu;
    }
   
    
    public MenuController() {
    }
    
    
    //
    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String mainMenu(Model model) {
    	log.info("menu GET");
    	final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
    	log.info("menu userName: "+userName);
    	final UserGeko user = fromSecurityServizi.findUserByUsernameAndAppl(userName, menu.appl);//fromSecurityService. .findUserByUsername(userName);
    	if (user == null){
    		return"common/errNoUser";
    	}
    	
    	// persona
    	menu.setPfID(user.getPfID());
    	log.info("menu GET -> find persona");
    	final PersonaFisicaGeko persona =  fromOrganikoServizi.findPersonaFisicaById(user.getPfID());
    	if (persona == null){
    		return"common/errNoPersona";
    	}
    	menu.setPersona(persona);
    	menu.setChi(persona.stringa);
    	System.out.println("persona = "+persona.stringa + "pfID: "+persona.idPersona + "pgID: "+persona.getPgID());
    	menu.setPgID(persona.getPgID());
    	
    	// struttura
    	log.info("menu GET -> find struttura");
    	final PersonaGiuridicaGeko struttura = fromOrganikoServizi.findPersonaGiuridicaById(persona.getPgID());
    	if (struttura == null){
    		return"common/errNoStruttura";
    	}
    	menu.setStruttura(struttura);
    	
    	// dipartimento
    	log.info("menu GET -> find dipartimento");
    	if (struttura.isDipartimento()){ // struttura e dipartimento coincidono
    		menu.setDipartimento(struttura);
    		menu.setDeptID(struttura.idPersona);
    	} else {
        	final PersonaGiuridicaGeko dipartimento = fromOrganikoServizi.findDipartimentoByPersonaFisicaID(persona.idPersona);
        	if (dipartimento == null){
        		return"common/errNoDipartimento";
        	}
        	menu.setDipartimento(dipartimento);
        	menu.setDeptID(dipartimento.idPersona);
    	}
    	
    	// assessorato
    	log.info("menu GET -> find assessorato");
    	PersonaGiuridicaGeko assessorato = fromOrganikoServizi.findAssessoratoByPersonaFisicaID(persona.idPersona);
    	if (assessorato == null){
    		if (struttura.idPersona == 41) assessorato = fromOrganikoServizi.findPersonaGiuridicaById(41);     
    		else return"common/errNoAssessorato";
    	}
    	menu.setAssessorato(assessorato);
    	menu.setAssID(assessorato.idPersona);
    	//final String application = "geko";
    	List<String> ruoliUtente= user.getRoles();
    	model.addAttribute("persona",menu.getPersona());
    	model.addAttribute("struttura",menu.getStruttura().getDenominazione());
    	model.addAttribute("dipartimento",menu.getDipartimento().getDenominazione());
    	model.addAttribute("assessorato",menu.getAssessorato().getDenominazione());
    	model.addAttribute("ruoliUtente",ruoliUtente);
    	
    	if (menu.getOldAnno()== 0 && menu.getAnno()==0){
	    	Calendar now = Calendar.getInstance();
	    	int anno = now.get(Calendar.YEAR)-1;
	    	menu.setAnno(anno);
    	}
    	if (menu.getAnno()> 2021){
            menu.setAnno(2021);
        }  
        
    	log.info("menu GET -> visualizza menu/mainmenu.jsp per scegliere il ruolo");
        return "menu/mainmenu";
    }
    
    @RequestMapping(value = "menu", params = "update", method =  RequestMethod.POST)
    public String updateRuolo(@ModelAttribute Menu menu, 
            BindingResult result, 
            SessionStatus status) {
    	//new MenuValidator().validate(menu, result);
    	menu.setOldAnno(menu.getAnno());
		return "redirect:/menu";
    }
    
    
    
    
    // --------------------- GET ---------------------------------------------
    @RequestMapping(value = "{ruolo}", method = RequestMethod.GET)
    public String findMenu(@PathVariable("ruolo") String ruolo, Model model) {
    	log.info("ruolo GET:"+ruolo);
    	menu.setChi(SecurityContextHolder.getContext().getAuthentication().getName());
    	// anno
    	if (menu.getAnno()==0){
	    	Calendar now = Calendar.getInstance();
	    	int anno = now.get(Calendar.YEAR);
	    	menu.setAnno(anno);
	    	menu.setOldAnno(0);	    	
    	}
        if (menu.getAnno()> 2022){
            menu.setAnno(2022);
        } 
    	// comuni a tutti: persona, struttura, dipartimento
    	if(menu.getPersona() == null) {   
            log.info("ruolo -->menu.getPersona() == null per cui aggiorno i campi");            
            // persona
            final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            final UserGeko user = fromSecurityServizi.findUserByUsernameAndAppl(userName, menu.appl);
            //            
            log.info("ruolo -> find persona");
            final PersonaFisicaGeko persona =  fromOrganikoServizi.findPersonaFisicaById(user.getPfID());
            menu.setPfID(user.getPfID());
            menu.setPersona(persona);
            	    	
    	}
    	model.addAttribute("persona",menu.getPersona());
    	//
    	if(menu.getStruttura() == null) { 
            log.info("ruolo --> menu.getStruttura() == null");
            // struttura
            log.info("ruolo -> find struttura");
            final PersonaGiuridicaGeko struttura = fromOrganikoServizi.findPersonaGiuridicaById(menu.getPgID());
            menu.setStruttura(struttura);	 
            menu.setPgID(struttura.idPersona);
            if (null == menu.getAltraStruttura()) menu.setAltraStruttura(struttura);
    	}	
    	model.addAttribute("struttura",menu.getStruttura());
    	//
    	if(menu.getDipartimento() == null) { 
            log.info("ruolo --> menu.getDipartimento() == null");
            // dipartimento
            log.info("ruolo -> find dipartimento");
            final PersonaGiuridicaGeko dipartimento = fromOrganikoServizi.findDipartimentoByPersonaFisicaID(menu.getPfID());
            menu.setDipartimento(dipartimento);
            menu.setDeptID(dipartimento.idPersona);
            if (null == menu.getAltroDipartimento()) menu.setAltroDipartimento(dipartimento);
    	}
    	model.addAttribute("dipartimento",menu.getDipartimento());
    	//
    	if(menu.getAssessorato() == null) { 
            log.info("ruolo --> menu.getAssessorato() == null");
            // dipartimento
            log.info("ruolo -> find getAssessorato");
            final PersonaGiuridicaGeko assessorato = fromOrganikoServizi.findAssessoratoByPersonaFisicaID(menu.getPfID());
            menu.setAssessorato(assessorato);
            menu.setAssID(assessorato.idPersona);
    	}
    	model.addAttribute("assessorato",menu.getAssessorato());
    	
    	// ------------ MANAGER GET --------------------------------------------
    	if (ruolo.equals("ROLE_MANAGER")){
            Integer dipartimentoIncaricoID = 0;
            boolean trovato = false;    		
            //
            if (menu.getAnno()!=menu.getOldAnno() || menu.getOldRuolo()!=ruolo){
                log.info("ROLE_MANAGER --> anno variato!");    			
                //
                log.info("ROLE_MANAGER --> findIncarichiDirigente");
                List<IncaricoGeko> incarichi = new ArrayList<IncaricoGeko>();
                if (menu.getPersona().isDirigente()){
                    log.info("è dirigente!");
                    incarichi = fromOrganikoServizi.findIncarichiByDirigenteIDAndAnno(menu.getPersona().idPersona, menu.getAnno());
                }
                else {
                    log.info("non è dirigente!"); // funzionario in sostituz manager
                    incarichi = fromOrganikoServizi.findIncarichiByStrutturaIDAndAnno(menu.getStruttura().getIdPersona(), menu.getAnno());    				
                }
                //
                if (null!=incarichi && !incarichi.isEmpty()) {menu.setIncarichi(incarichi);}
                else return "common/errNoIncarichiDirigente"; 
            }
            /*
            // cerco eventuali incarichi pop va in errore se non li trova
            log.info("ROLE_MANAGER --> findIncarichiPopDirigente");
            List<IncaricoGeko> incarichiPop = new ArrayList<IncaricoGeko>();
            if (menu.getPersona().isDirigente()){    				
                incarichiPop = fromOrganikoServizi.findIncarichiPopByIntermediaIDAndAnno(menu.getStruttura().getIdPersona(), menu.getAnno());    				
            }
            if (null!=incarichiPop && !incarichiPop.isEmpty()) {menu.setIncarichiPop(incarichiPop);}     	
            
            */
            log.info("ROLE_MANAGER --> findIncarichiApicaliDipartimento");
            final List<IncaricoGeko> incarichiApicaliDept = fromOrganikoServizi.findIncarichiApicaliByDipartimentoIDAndAnno(menu.getDeptID(), menu.getAnno());
            if (null!=incarichiApicaliDept && !incarichiApicaliDept.isEmpty()) {
                menu.setIncarichiApicaliDept(incarichiApicaliDept);
            }
            else {
                log.info("ROLE_MANAGER --> findIncarichiApicaliDipartimentoIncarico");
                List<IncaricoGeko> incarichiApicaliDeptIncarico = fromOrganikoServizi.findIncarichiApicaliByDipartimentoIDAndAnno(dipartimentoIncaricoID, menu.getAnno());
                if (null!=incarichiApicaliDeptIncarico && !incarichiApicaliDeptIncarico.isEmpty()) {
                    menu.setIncarichiApicaliDept(incarichiApicaliDeptIncarico);
                }
                else {return"common/errNoIncarichiApicaliDipartimento"; }
            }    			
            //
            menu.setOldAnno(menu.getAnno());                   	
            //
            if (null ==menu.getIncarico()){
                int i =1;
                for(IncaricoGeko incarico: menu.getIncarichi()){
                    if (i==1) {
                            menu.setIncarico(incarico);
                            menu.setIdIncaricoScelta(incarico.getIdIncarico());
                    }
                    i++;
                }
            }
            model.addAttribute("incarichi",menu.getIncarichi());
            model.addAttribute("incarichiDept",menu.getIncarichiDept());
            model.addAttribute("incarichiApicaliDept",menu.getIncarichiApicaliDept());        	
            //
            if (null ==menu.getIncaricoApicaleDept()){
                log.info("ROLE_MANAGER --> null ==menu.getIncaricoApicaleDept()");
                int i =1;
                for(IncaricoGeko incaricoApicale: menu.getIncarichiApicaliDept()){
                    if (i==1) {
                        menu.setIncaricoApicaleDept(incaricoApicale);	// getIdIncaricoApicaleDeptScelta}
                        menu.setIdIncaricoDeptScelta(incaricoApicale.getIdIncarico());
                    }
                    i++;
                }  	
            } 
    	}// end if role_manager
    	
    	
    	// ROLE_RESP_POP GET ---------------------------------------------------
    	if (ruolo.equals("ROLE_RESP_POP")){    		
        //
        if (menu.getAnno()!=menu.getOldAnno() || menu.getOldRuolo()!=ruolo){
            log.info("ROLE_RESP_POP --> anno variato!");    			
            //
            log.info("ROLE_RESP_POP --> findIncarichiPopStruttura");
            List<IncaricoGeko> incarichi = fromOrganikoServizi.findIncarichiByDirigenteIDAndAnno(menu.getPersona().getIdPersona(), menu.getAnno());    				
            if (null!=incarichi && !incarichi.isEmpty()) menu.setIncarichi(incarichi);
            else return"common/errNoIncarichiPop";     			
            //
            menu.setOldAnno(menu.getAnno());
        }
        model.addAttribute("incarichi",menu.getIncarichi());
        //
        if (null ==menu.getIncarico()){
            int i =1;
            for(IncaricoGeko incarico: menu.getIncarichi()){
                if (i==1) {
                        menu.setIncarico(incarico);
                        menu.setIdIncaricoScelta(incarico.getIdIncarico());
                }
                i++;
            }
        } 	
    }// end if role_resp_pop
    	
    	
    	// COMPARTO GET ---------------------------------------------------
    	if (ruolo.equals("ROLE_COMPARTO")){
    		Integer dipartimentoIncaricoID = 0;
    		boolean trovato = false;
    		//
    		if (menu.getAnno()!=menu.getOldAnno() || menu.getOldRuolo()!=ruolo){
    			log.info("ROLE_COMPARTO --> anno variato!");
    			
    			//
    			log.info("ROLE_COMPARTO --> findIncarichiDirigente");
    			List<IncaricoGeko> incarichi = new ArrayList<IncaricoGeko>();
    			List<AzioneAssegnazione> assegnazioni = azioneAssegnazioneQryServizi.findByPfIDAndAnno(menu.getPersona().idPersona, menu.getAnno());
    			if (!assegnazioni.isEmpty() && (assegnazioni != null)){
    				log.info("ROLE_COMPARTO --> assegnazioni trovate");
	    			for (AzioneAssegnazione aa : assegnazioni){
	    				IncaricoGeko inc = fromOrganikoServizi.findIncaricoById(aa.getIncaricoID());
	    				if (inc != null && !incarichi.contains(inc)) incarichi.add(inc);
	    			}
    			} else {
    				log.info("ROLE_COMPARTO --> assegnazioni non trovate per :"+menu.getPersona().getIdPersona() + "anno: "+ menu.getAnno());
    			}
    			//
	        	if (null!=incarichi && !incarichi.isEmpty()) menu.setIncarichi(incarichi);
	        	
	        	//
	        	menu.setOldAnno(menu.getAnno());
    		}
        	model.addAttribute("incarichi",menu.getIncarichi());
        	//        	
    	}// end if role_comparto

    	// CONTROLLER GET --------------------------------------------------------
    	if (ruolo.equals("ROLE_CONTROLLER")){
    		log.info("ruolo ROLE_CONTROLLER");
    		//
    		//System.out.println("MenuController() anno= "+menu.getAnno()+"oldAnno= "+menu.getOldAnno());
    		if (menu.getAnno()!=menu.getOldAnno() || menu.getOldRuolo()!=ruolo){
    			log.info("ROLE_CONTROLLER --> anno variato!");
    			//
    			log.info("ROLE_CONTROLLER --> findIncarichiDipartimento");
    			List<IncaricoGeko> incarichiDept = fromOrganikoServizi.findIncarichiByDipartimentoIDAndAnno(menu.getDeptID(), menu.getAnno());
    			//
    			if (null!=incarichiDept && !incarichiDept.isEmpty()) menu.setIncarichiDept(incarichiDept);
    			
    			//
    			log.info("ROLE_CONTROLLER --> findIncarichiApicaliDipartimento");
    			List<Integer> incarichiApicaliDeptIDs = new ArrayList<Integer>();
    			List<IncaricoGeko> incarichiApicaliDept = fromOrganikoServizi.findIncarichiApicaliByDipartimentoIDAndAnno(menu.getDeptID(), menu.getAnno());
    			for (IncaricoGeko incarico: incarichiApicaliDept){
    				log.info("incaricoApicaleDept: "+incarico.toString()); 
    				incarichiApicaliDeptIDs.add(incarico.getIdIncarico());
    			}
    			// per l'interim di Bologna
    			if (menu.getPersona().isDirigente()){
	    			List<IncaricoGeko> incarichiDeptPers = fromOrganikoServizi.findIncarichiByDirigenteIDAndAnno(menu.getPfID(), menu.getAnno());
	    			if (incarichiDeptPers != null && !incarichiDeptPers.isEmpty()){
	    				for (IncaricoGeko incarico: incarichiDeptPers){
	    					if (!incarichiApicaliDeptIDs.contains(incarico.getIdIncarico()) && incarico.incaricoDipartimentale){ 
	    						log.info("incaricoApicaleDeptPers: "+incarico.toString()); 	
	    						incarichiApicaliDept.add(incarico);
	    					}    					
	    				}    				 
	    			}
    			}
    			if (null!=incarichiApicaliDept && !incarichiApicaliDept.isEmpty()) menu.setIncarichiApicaliDept(incarichiApicaliDept);
    			
            	//
	        	menu.setOldAnno(menu.getAnno());
    		}
    		// 
        	model.addAttribute("incarichiDept",menu.getIncarichiDept());
        	model.addAttribute("incarichiApicaliDept",menu.getIncarichiApicaliDept());
        	
        	//
        	if (null ==menu.getIncaricoDept()){
        		log.info("ROLE_CONTROLLER --> null ==menu.getIncaricoDept()");
        		final List<IncaricoGeko> incarichiDept = fromOrganikoServizi.findIncarichiByDipartimentoIDAndAnno(menu.getDeptID(), menu.getAnno());
        		int i =1;
        		//for(IncaricoGeko incarico: incarichiDept){//
        		if (incarichiDept != null && !incarichiDept.isEmpty()){
	        		for(IncaricoGeko incarico: menu.getIncarichiDept()){
	        			if (i==1) {
	        				menu.setIncaricoDept(incarico);	
	        				menu.setIdIncaricoDeptScelta(incarico.getIdIncarico());
	        			}
	        			i++;
	        		}
        		}
    		}
        	//
        	if (null ==menu.getIncaricoApicaleDept()){
        		log.info("ROLE_CONTROLLER --> null ==menu.getIncaricoApicaleDept()");
        		int i =1;
        		for(IncaricoGeko incaricoApicale: menu.getIncarichiApicaliDept()){
        			if (i==1) {
        				menu.setIncaricoApicaleDept(incaricoApicale);	// getIdIncaricoApicaleDeptScelta}
        			    menu.setIdIncaricoDeptScelta(incaricoApicale.getIdIncarico());
        			}
        			i++;
        		}
    		}
    	} // end if role_controller
    	
    	
    	// ROLE_SUPERCONTROLLER GET ------------------------------------------------------------------
    	if (ruolo.equals("ROLE_SUPERCONTROLLER")){
    		//
    		List<PersonaGiuridicaGeko> assessorati = fromOrganikoServizi.listAssessoratiAndAnno(menu.getAnno());
    		if (null!=assessorati && !assessorati.isEmpty()) menu.setAssessorati(assessorati);
            //
    		List<IncaricoGeko> incarichiApicaliDept = fromOrganikoServizi.findIncarichiApicaliByAssessoratoIDAndAnno(menu.getAssID(), menu.getAnno());
    		if (null!=incarichiApicaliDept && !incarichiApicaliDept.isEmpty()){
    			menu.setIncarichiApicaliAmm_ne(incarichiApicaliDept);
    			model.addAttribute("incarichiApicaliAmm_ne",incarichiApicaliDept);
    		}
    		//List<IncaricoGeko> incarichiDept = fromOrganikoServizi.findIncarichiRestByDipartimentoIDAndAnno(menu.getDeptID(), menu.getAnno());
    		List<IncaricoGeko> incarichiDept = fromOrganikoServizi.findIncarichiByDipartimentoIDAndAnno(menu.getDeptID(), menu.getAnno());
    		
    		//log.info("ROLE_SUPERCONTROLLER menu.getDeptID ="+ menu.getDeptID()+" anno"+menu.getAnno());
    		
			menu.setIncarichiDept(incarichiDept);
			model.addAttribute("incarichiDept",incarichiDept);
    		//}
    		
    	}
    	
    	
    	// ROLE_OIV GET --------------------------------------------------
    	if (ruolo.equals("ROLE_OIV")){ 
    		//System.out.println("MenuController() anno= "+menu.getAnno()+"oldAnno= "+menu.getOldAnno());
    		if (menu.getAnno()!=menu.getOldAnno() || menu.getOldRuolo()!=ruolo){
    			log.info("ROLE_OIV --> anno variato!");
    			//
    			final List<PersonaGiuridicaGeko> assessorati = fromOrganikoServizi.listAssessoratiAndAnno(menu.getAnno());
            	menu.setAssessorati(assessorati);
    			//
            	List<IncaricoGeko> incarichiApicaliDept = fromOrganikoServizi.findIncarichiApicaliByAssessoratoIDAndAnno(menu.getAssID(), menu.getAnno());
    			if (null!=incarichiApicaliDept && !incarichiApicaliDept.isEmpty())	menu.setIncarichiApicaliAmm_ne(incarichiApicaliDept);
	        	menu.setOldAnno(menu.getAnno());
    		}
        	model.addAttribute("incarichiApicaliAmm_ne",menu.getIncarichiApicaliAmm_ne());
        	
        	//
        	if (null ==menu.getIncaricoApicaleAmm_ne()){
        		log.info("ROLE_OIV --> null ==menu.getIncaricoApicaleDept()");
        		int i =1;
        		for(IncaricoGeko incaricoApicale: menu.getIncarichiApicaliAmm_ne()){
        			if (i==1) {
        				menu.setIncaricoApicaleAmm_ne(incaricoApicale);	// getIdIncaricoApicaleDeptScelta}
        			    menu.setIdIncaricoGekoApicaleAmm_neScelta(incaricoApicale.getIdIncarico());
        			}
        			i++;
        		}
    		}
    		
    	} // end OIV
    	
    	
    	// ROLE_GABINETTO GET --------------------------------------------------
    	if (ruolo.equals("ROLE_GABINETTO")){   		
        	
        	//System.out.println("MenuController() anno= "+menu.getAnno()+"oldAnno= "+menu.getOldAnno());
    		if (menu.getAnno()!=menu.getOldAnno() || menu.getOldRuolo()!=ruolo){
    			log.info("ROLE_GABINETTO --> anno variato!");
    			//
    			//final List<PersonaGiuridicaGeko> dipartimenti = fromOrganikoServizi.listDipartimentiByAssessoratoIDAndAnno(menu.getAssID(), menu.getAnno());
            	//menu.setDipartimenti(dipartimenti);
            	//
    			List<IncaricoGeko> incarichiApicaliDept = fromOrganikoServizi.findIncarichiApicaliByAssessoratoIDAndAnno(menu.getAssID(), menu.getAnno());
    			if (null!=incarichiApicaliDept && !incarichiApicaliDept.isEmpty())	menu.setIncarichiApicaliAmm_ne(incarichiApicaliDept);
	        	menu.setOldAnno(menu.getAnno());
    		}
    		//   menu.getDipartimento().getSubIncarichiAnno();
        	model.addAttribute("incarichiApicaliAmm_ne",menu.getIncarichiApicaliAmm_ne());
        	
        	//
        	if (null ==menu.getIncaricoApicaleAmm_ne()){
        		log.info("ROLE_GABINETTO --> null ==menu.getIncaricoApicaleDept()");
        		int i =1;
        		//final List<IncaricoGeko> incarichiApicaliDept = fromOrganikoServizi.findIncarichiApicaliByDipartimentoIDAndAnno(menu.getDeptID(), menu.getAnno());     		
        		//for(IncaricoGeko incaricoApicale: incarichiApicaliDept){
        		for(IncaricoGeko incaricoApicale: menu.getIncarichiApicaliAmm_ne()){
        			if (i==1) {
        				menu.setIncaricoApicaleAmm_ne(incaricoApicale);	// getIdIncaricoApicaleDeptScelta}
        			    menu.setIdIncaricoGekoApicaleAmm_neScelta(incaricoApicale.getIdIncarico());
        			}
        			i++;
        		}
    		}
    	}
    	
    	// ROLE_SUPERGABINETTO GET --------------------------------------------------
    	if (ruolo.equals("ROLE_SUPERGABINETTO")){
    		
        	
    		//System.out.println("MenuController() anno= "+menu.getAnno()+"oldAnno= "+menu.getOldAnno());
    		if (menu.getAnno()!=menu.getOldAnno() || menu.getOldRuolo()!=ruolo){
    			log.info("ROLE_SUPERGABINETTO --> anno variato!");
    			//
    			final List<PersonaGiuridicaGeko> assessorati = fromOrganikoServizi.listAssessoratiAndAnno(menu.getAnno());
            	menu.setAssessorati(assessorati);
    			//
            	List<IncaricoGeko> incarichiApicaliDept = fromOrganikoServizi.findIncarichiApicaliByAssessoratoIDAndAnno(menu.getAssID(), menu.getAnno());
    			if (null!=incarichiApicaliDept && !incarichiApicaliDept.isEmpty())	menu.setIncarichiApicaliAmm_ne(incarichiApicaliDept);
	        	menu.setOldAnno(menu.getAnno());
    		}
        	model.addAttribute("incarichiApicaliAmm_ne",menu.getIncarichiApicaliAmm_ne());
        	
        	//
        	if (null ==menu.getIncaricoApicaleAmm_ne()){
        		log.info("ROLE_SUPERGABINETTO --> null ==menu.getIncaricoApicaleDept()");
        		int i =1;
        		for(IncaricoGeko incaricoApicale: menu.getIncarichiApicaliAmm_ne()){
        			if (i==1) {
        				menu.setIncaricoApicaleAmm_ne(incaricoApicale);	// getIdIncaricoApicaleDeptScelta}
        			    menu.setIdIncaricoGekoApicaleAmm_neScelta(incaricoApicale.getIdIncarico());
        			}
        			i++;
        		}
    		}
    		
    	} // end ROLE_SUPERGABINETTO
    	
    	// ---------
    	menu.setOldRuolo(ruolo);
    	return "menu/"+ruolo;
    }
    
    // *********************************************************************************************
    //
    @RequestMapping(value = "{ruolo}", params = "update", method =  RequestMethod.POST)
    public String updateRuolo(@PathVariable("ruolo") String ruolo, @ModelAttribute Menu menu, 
            BindingResult result, SessionStatus status) {
    	new MenuValidator().validate(menu, result);
        if (result.hasErrors()) { return "redirect:/menu";}
        else {
            // ROLE_MANAGER POST ---------------------------------------------------
            if (ruolo.equals("ROLE_MANAGER")){
                if (0!=menu.getIdIncaricoScelta()){
                    IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(menu.getIdIncaricoScelta());//incarichiServizi.findById(menu.getIdIncaricoScelta());
                    menu.setIncarico(incarico);			
                }
                if (0!=menu.getIdIncaricoDeptScelta()){
                    IncaricoGeko incaricoDept = fromOrganikoServizi.findIncaricoById(menu.getIdIncaricoDeptScelta());//(menu.getIdIncaricoDeptScelta());
                    menu.setIncaricoDept(incaricoDept);	
                }
                if (0!=menu.getIdIncaricoApicaleDeptScelta()){
                    IncaricoGeko incaricoApicaleDept = fromOrganikoServizi.findIncaricoById(menu.getIdIncaricoApicaleDeptScelta());
                    menu.setIncaricoApicaleDept(incaricoApicaleDept);
                }
            }
			
            // ROLE_COMPARTO POST ---------------------------------------------------
            if (ruolo.equals("ROLE_COMPARTO")){
                if (0!=menu.getIdIncaricoScelta()){
                    IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(menu.getIdIncaricoScelta());//incarichiServizi.findById(menu.getIdIncaricoScelta());
                    menu.setIncarico(incarico);			
                }							
            }			
            // ROLE_CONTROLLER POST ---------------------------------------------------
            if (ruolo.equals("ROLE_CONTROLLER")){
                if (0!=menu.getIdIncaricoDeptScelta()){
                    IncaricoGeko incaricoDept = fromOrganikoServizi.findIncaricoById(menu.getIdIncaricoDeptScelta());//(menu.getIdIncaricoDeptScelta());
                    menu.setIncaricoDept(incaricoDept);
                }
                if (0!=menu.getIdIncaricoApicaleDeptScelta()){
                    IncaricoGeko incaricoApicaleDept = fromOrganikoServizi.findIncaricoById(menu.getIdIncaricoApicaleDeptScelta());
                    menu.setIncaricoApicaleDept(incaricoApicaleDept);
                }
            }
            // ROLE_OIV POST ---------------------------------------------------
            if (ruolo.equals("ROLE_OIV")){
                if (0!=menu.getIdAssessoratoScelta()){
                    PersonaGiuridicaGeko assto = fromOrganikoServizi.findPersonaGiuridicaById(menu.getIdAssessoratoScelta());
                    menu.setAssessorato(assto);
                    menu.setAssID(menu.getIdAssessoratoScelta());
                    log.info("assto scelto = "+assto.getCodice());
                }
                if (0!=menu.getIdIncaricoGekoApicaleAmm_neScelta()){
                    IncaricoGeko incaricoApicale = fromOrganikoServizi.findIncaricoById(menu.getIdIncaricoGekoApicaleAmm_neScelta());
                    menu.setIncaricoApicaleAmm_ne(incaricoApicale);	
                    log.info("incarico dept= "+incaricoApicale.getStringa());
                }
            }

            // ROLE_GABINETTO POST ---------------------------------------------------
            if (ruolo.equals("ROLE_GABINETTO")){
                    
                    if (0!=menu.getIdIncaricoGekoApicaleAmm_neScelta()){
                            IncaricoGeko incaricoApicale = fromOrganikoServizi.findIncaricoById(menu.getIdIncaricoGekoApicaleAmm_neScelta());
                            menu.setIncaricoApicaleAmm_ne(incaricoApicale);	
                            log.info("incarico dept= "+incaricoApicale.getStringa());
                    }

            }
			
			// ROLE_SUPERCONTROLLER POST ---------------------------------------------------
			if (ruolo.equals("ROLE_SUPERCONTROLLER")){
				menu.setChi(SecurityContextHolder.getContext().getAuthentication().getName());
				
				if (0!=menu.getIdAssessoratoScelta()){
					PersonaGiuridicaGeko assto = fromOrganikoServizi.findPersonaGiuridicaById(menu.getIdAssessoratoScelta());
					menu.setAssessorato(assto);
					menu.setAssID(menu.getIdAssessoratoScelta());
					log.info("assto scelto = "+assto.getCodice());
				}
				
				
				if (0!=menu.getIdIncaricoGekoApicaleAmm_neScelta()){
					IncaricoGeko incaricoApicale = fromOrganikoServizi.findIncaricoById(menu.getIdIncaricoGekoApicaleAmm_neScelta());
					menu.setIncaricoApicaleAmm_ne(incaricoApicale);	
					menu.setDeptID(incaricoApicale.pgID);
					log.info("incarico apicale= "+ incaricoApicale.getStringa()+ " deptID= "+incaricoApicale.pgID);
				}
				
				if (0!=menu.getIdIncaricoDeptScelta()){
					IncaricoGeko incaricoDept = fromOrganikoServizi.findIncaricoById(menu.getIdIncaricoDeptScelta());//(menu.getIdIncaricoDeptScelta());
					menu.setIncaricoDept(incaricoDept);	
					log.info("incarico dept= "+incaricoDept.getStringa());
				}
			}
			
			// ROLE_SUPERGABINETTO POST ---------------------------------------------------
			if (ruolo.equals("ROLE_SUPERGABINETTO")){
				menu.setChi(SecurityContextHolder.getContext().getAuthentication().getName());
				
				if (0!=menu.getIdAssessoratoScelta()){
					PersonaGiuridicaGeko assto = fromOrganikoServizi.findPersonaGiuridicaById(menu.getIdAssessoratoScelta());
					menu.setAssessorato(assto);
					menu.setAssID(menu.getIdAssessoratoScelta());
					log.info("assto scelto = "+assto.getCodice());
				}
				
				
				if (0!=menu.getIdIncaricoGekoApicaleAmm_neScelta()){
					IncaricoGeko incaricoApicale = fromOrganikoServizi.findIncaricoById(menu.getIdIncaricoGekoApicaleAmm_neScelta());
					menu.setIncaricoApicaleAmm_ne(incaricoApicale);	
					log.info("incarico dept= "+incaricoApicale.getStringa());
				}
			}
			
			//
			status.setComplete();
			return "redirect:/"+ruolo; // rilancio la GET
		}
    }
    //	
    @RequestMapping(value = "{ruolo}", params = "logout", method =  RequestMethod.POST)
    public String logout() {
    	//
	SecurityContextHolder.clearContext();
    return "redirect:/login";
    }
    
    
    
   

} // --------------------
