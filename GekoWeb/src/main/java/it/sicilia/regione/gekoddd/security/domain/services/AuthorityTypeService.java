package it.sicilia.regione.gekoddd.security.domain.services;

import it.sicilia.regione.gekoddd.security.domain.entity.AuthorityTypeSecur;

public interface AuthorityTypeService {

	// Find all 
	//public List<AuthorityType> findAll();
	
	// Find  by id
	public AuthorityTypeSecur findById(Integer id);
	
	// Insert or update a contact	
	public AuthorityTypeSecur save(AuthorityTypeSecur authorityType);
	
	// Delete a contact	
	public void delete(AuthorityTypeSecur authorityType);
	
	//-----------------------------------
		
}
