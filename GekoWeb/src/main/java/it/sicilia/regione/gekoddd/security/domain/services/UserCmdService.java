package it.sicilia.regione.gekoddd.security.domain.services;

import it.sicilia.regione.gekoddd.geko.acl.model.UserGeko;

public interface UserCmdService {

	
	
	
	//-----------------------------------
	//
	void changePwd (UserGeko user, String pwd);
	void resetPwd (UserGeko user);
	
}
