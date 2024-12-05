package it.sicilia.regione.gekoddd.session.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.log.model.journal.JournalService;

/**
 * This controller is used to provide functionality for the login page.
 */
@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
    //
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login() {
    	logger.info("LoginController() / get ");
    	return "redirect:/menu";
    }
    
    
    // senza autenticazione
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String autenticate() {
    	logger.info("LoginController() /login ");
        return "login";
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String autenticatePost() {
    	logger.info("LoginController() /login post ");
    	
    	return "redirect:/menu";
    }
    
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
    	logger.info("LoginController() /logout ");
        return "login";
    }
    
    
}