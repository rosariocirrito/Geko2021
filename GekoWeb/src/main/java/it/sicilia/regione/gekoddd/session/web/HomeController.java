package it.sicilia.regione.gekoddd.session.web;


import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! the client locale is "+ locale.toString());
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("message", "prova messaggio");
		
		/*
		//
		List<Obiettivo> objs = objservice.findByAnno(2012); 
		for(Obiettivo obj: objs){
			System.out.println("Obiettivo id= "+obj.getIdObiettivo()+" denom.= "+obj.getDenominazione());
		}
		
		System.out.println("---------------------------");
		
		List<Azione> acts = actservice.findAll(); 
		for(Azione act: acts){
			System.out.println("Azione id= "+act.getIdAzione()+" denom.= "+act.getDenominazione());
		}
		
		System.out.println("---------------------------");
		
		List<OpPersonaFisica> pfs = pfservice.findAll(); 
		for(OpPersonaFisica pf: pfs){
			System.out.println("OpPersonaFisica id= "+pf.getIdPersona()+" str= "+pf.toString());
		}
		
		System.out.println("---------------------------");
		
		List<OpPersonaGiuridica> pgs = pgservice.findAll(); 
		for(OpPersonaGiuridica pg: pgs){
			System.out.println("OpPersonaGiuridica id= "+pg.getIdPersona()+" str= "+pg.toString());
		}
		
		System.out.println("---------------------------");
		
		List<AzioneAssegnazione> actasss = actassservice.findAll(); 
		for(AzioneAssegnazione actass: actasss){
			System.out.println("AzioneAssegnazione id= "+actass.getId());
		}
		
		//
		System.out.println("---------------------------");
		
		List<ComportOrgan> items = coservice.findAll(); 
		for(ComportOrgan item: items){
			System.out.println("ComportOrgan id= "+item.getId());
		}
		
		//
		System.out.println("---------------------------");
		
		List<Criticita> items = critservice.findAll(); 
		for(Criticita item: items){
			System.out.println("Criticita id= "+item.getId()+" descr: "+item.getDescrizione());
		}
		
		//
		System.out.println("---------------------------");
		
		List<Documento> items2 = docservice.findAll(); 
		for(Documento item: items2){
			System.out.println("Documento id= "+item.getId()+" descr: "+item.getDescrizione());
		}
		//
		System.out.println("---------------------------");
		
		List<User> items3 = userservice.findAll(); 
		for(User item: items3){
			System.out.println("User id= "+item.getIdusers()+" username: "+item.getUsername());
		}
		*/
		return "home";
		
	}
	
}
