package it.sicilia.regione.gekoddd.security.domain.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.sicilia.regione.gekoddd.geko.acl.FromSecurityQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.UserGeko;
import it.sicilia.regione.gekoddd.security.domain.entity.AuthoritySecur;
import it.sicilia.regione.gekoddd.security.domain.entity.AuthorityTypeSecur;
import it.sicilia.regione.gekoddd.security.domain.entity.UserSecur;
import it.sicilia.regione.gekoddd.security.session.config.WebSecurityConfiguration;

/*
 * l'interfaccia  org.springframework.security.core.userdetails.UserDetailsService
 * definisce un solo metodo:  UserDetails loadUserByUsername(String arg0)
 * con il return type: org.springframework.security.core.userdetails.UserDetails;
 * che dobbiamo implementare
 */

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{

	private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	//@Autowired
	//UserQryService userQryService;

	@Autowired
	FromSecurityQryService fromSecurityQryService;
	
	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.info("UserDetailsServiceImpl() username = "+ username);
		
		// convertiamo User del framework sicurezza nel nostro User
		// troviamo lo UserSecur nel database 
		//UserGeko myUser =  userQryService.findUserByUsername(username);
		UserGeko myUser =  fromSecurityQryService.findUserByUsernameAndAppl(username,"geko");
		//if (myUser !=null){
		// costruiamo la lista dei ruoli di questo utente prendendoli dal database
		List<GrantedAuthority> myAuthorities = buildUserAuthority(myUser.getRoles()); // vedi sotto metodo privato
		// costruiamo l'utente per Spring Security associandogli i ruoli
		return buildUserForAuthentication(myUser,myAuthorities); // vedi sotto
		//}
	}
	
	// riscriverlo con Stringa e nel microoservizio filtrare con nome applicazione
	//private List<GrantedAuthority> buildUserAuthority(List<AuthoritySecur> userRoles){
	private List<GrantedAuthority> buildUserAuthority(List<String> userRoles){	
		List<GrantedAuthority> setAuths = new ArrayList<GrantedAuthority>();
		//for (AuthoritySecur role : userRoles){
		for (String role : userRoles){
			/*
			AuthorityTypeSecur roleType= role.getAuthorityType();
			if((null != roleType) && (roleType.getApplication().equals("geko"))){
			setAuths.add(new SimpleGrantedAuthority(roleType.getAuthority()));
			*/
			setAuths.add(new SimpleGrantedAuthority(role));
			log.info("UserDetailsServiceImpl() buildUserAuthority ruolo = "+ role);
			//}
		}
		return setAuths;
	}
	/*
	private User buildUserForAuthentication(UserSecur myUser, List<GrantedAuthority> myAuthorities){
		return new User(myUser.getUsername(),myUser.getPassword(),myUser.isEnabled(),true,true,true,myAuthorities);
	}
	*/
	private User buildUserForAuthentication(UserGeko myUser, List<GrantedAuthority> myAuthorities){
		return new User(myUser.getUsername(),myUser.getPassword(),myUser.getEnabled(),true,true,true,myAuthorities);
	}
}
