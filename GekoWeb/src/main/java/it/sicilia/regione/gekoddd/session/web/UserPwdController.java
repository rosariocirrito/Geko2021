package it.sicilia.regione.gekoddd.session.web;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.sicilia.regione.gekoddd.geko.acl.FromSecurityQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.UserGeko;
import it.sicilia.regione.gekoddd.session.domain.Menu;


@Controller
@SessionAttributes(types = UserGeko.class)
@RequestMapping("/userPwd")
public class UserPwdController {

	private Log log = LogFactory.getLog(UserPwdController.class);
	
	 @Autowired
	 private FromSecurityQryService fromSecurityServizi;
	 
	 @Autowired
	 private Menu menu;
	    
	
	 // ---------------------- Change Pwd --------------------
    //
    @RequestMapping(value = "/changePwd", method = RequestMethod.GET)
    public String changePwd(Model model) {
        final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        //UserSecur user = this.userQryServizi.findUserByUsername(currentUser);
        UserGeko user = fromSecurityServizi.findUserByUsernameAndAppl(userName, menu.appl);
        user.setOldPassword(user.getPassword());
        model.addAttribute(user);
        log.info("changePwd.GET user"+user.getIdusers()+"/"+user.getOldPassword());
        //System.out.println("L'utente �: "+currentUser);
	return "change-password-form";
        
    }
    @RequestMapping(value = "/changePwd", method = RequestMethod.PUT )
    //public String processSubmit(@RequestParam("password") String password) {
    public String processSubmit(@ModelAttribute UserGeko user, 
            BindingResult result, 
            SessionStatus status) {
    	log.info("changePwd.PUT user"+user.getIdusers()+"/"+user.getOldPassword());
    	log.info("changePwd.PUT user"+user.getIdusers()+"/"+user.getPassword());
        if (user.getPassword().equals(user.getPassword2())){
        	//userCmdServizi.changePwd(user2, user.getPassword());
        	//
        	String hash = "";
            MessageDigest sha1 = null;
            try {  
                sha1 = MessageDigest.getInstance("SHA-1");
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }

            byte[] bytes = sha1.digest(user.getPassword2().getBytes());
            for (byte b:bytes)
            hash += String.format("%02x", b);
        	fromSecurityServizi.chgPwd(user.getIdusers(), user.getOldPassword(), hash);
        }
        else return "change-password-form";
        
        //System.out.println("Cambio Password per: "+name+" pwd: "+password);
        // distrugge la precedente identificazione
        SecurityContextHolder.clearContext();
        return "redirect:/login";
	}
    
    
}
