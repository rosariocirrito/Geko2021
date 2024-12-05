/*
 * FromSecurityQryServiceImpl è l'implementazione della API FromSecurityQryService
 * 
 */

package it.sicilia.regione.gekoddd.geko.acl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.sicilia.regione.gekoddd.geko.acl.dto.UserDTO;
import it.sicilia.regione.gekoddd.geko.acl.model.UserGeko;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("fromSecurityQryService")
public class FromSecurityQryServiceImpl implements FromSecurityQryService {
	@Value("${msAutentiko.url}")
	private String serverMsAutentikoQueryIP;
	
	private Log log = LogFactory.getLog(FromSecurityQryServiceImpl.class);
	
	private final String gekoAppl = "geko";
	
	@Override
	public UserGeko findByPfIDAndAppl(Integer pfID, String appl) {
		UserGeko user = null;
		log.info("findByPfIDRest:"+pfID);
		RestTemplate restTemplate = new RestTemplate();
		String resourceUri = "http://"+serverMsAutentikoQueryIP+"/api/findUserByPfIDAndAppl/"+pfID+"/"+gekoAppl;
		log.info(resourceUri);
		UserDTO userDTO = restTemplate.getForObject(resourceUri, UserDTO.class,pfID,gekoAppl);//securityQryService.findByPfID(pfID);
		user = new UserGeko(userDTO);
		return user;
	}

	@Override
	public UserGeko findUserByUsernameAndAppl(String userName, String appl) {
		UserGeko user = null;
		log.info("findUserByUsernameRest:"+userName);
		RestTemplate restTemplate = new RestTemplate();
		String resourceUri = "http://"+serverMsAutentikoQueryIP+"/api/findUserByUsernameAndAppl/"+userName+"/"+gekoAppl;
		log.info(resourceUri);
		UserDTO userDTO = restTemplate.getForObject(resourceUri, UserDTO.class,userName,gekoAppl);//securityQryService.findByPfID(pfID);
		user = new UserGeko(userDTO);
		
		return user;
	}

	@Override
	public UserGeko chgPwd(Integer id, String oldPwd, String newPwd) {
		UserGeko user = null;
		log.info("chgPwdRest :"+id+"/"+oldPwd+"/"+newPwd);
		RestTemplate restTemplate = new RestTemplate();
		String resourceUri = "http://"+serverMsAutentikoQueryIP+"/api/chgPwd/"+id+"/"+oldPwd+"/"+newPwd;
		log.info("chgPwd() resourceUri"+resourceUri);
		UserDTO userDTO = restTemplate.getForObject(resourceUri, UserDTO.class,id,oldPwd,newPwd);//securityQryService.findByPfID(pfID);
		user = new UserGeko(userDTO);
		
		return user;
	}
	
} //------------------
