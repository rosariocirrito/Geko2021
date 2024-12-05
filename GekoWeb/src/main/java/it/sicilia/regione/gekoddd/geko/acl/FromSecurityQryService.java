/*
 * FromSecurityQryService è la API di interfacciamento con il microservizio msAutentikoQry
 * 
 */

package it.sicilia.regione.gekoddd.geko.acl;

import it.sicilia.regione.gekoddd.geko.acl.model.UserGeko;

public interface FromSecurityQryService {
	UserGeko findUserByUsernameAndAppl(String userName, String appl);
	UserGeko findByPfIDAndAppl(Integer pfID, String appl);
	UserGeko chgPwd(Integer id, String oldPwd, String newPwd);
}
