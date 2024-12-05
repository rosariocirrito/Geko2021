package it.sicilia.regione.gekoddd.security.domain.services;

import it.sicilia.regione.gekoddd.security.domain.entity.AuthoritySecur;

public interface AuthorityCmdService {

	// Find  by id
	public AuthoritySecur findById(Integer id);
	
	// Insert or update a contact	
	public AuthoritySecur save(AuthoritySecur authority);
	
	// Delete a contact	
	public void delete(AuthoritySecur authority);
	
	//-----------------------------------
		
}
