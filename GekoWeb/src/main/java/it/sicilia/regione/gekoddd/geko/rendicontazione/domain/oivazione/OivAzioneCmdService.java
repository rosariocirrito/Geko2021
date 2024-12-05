package it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivazione;


import java.util.List;

public interface OivAzioneCmdService {

	// Find by id
	public OivAzione findById(Integer id);
	
	// Insert or update 
	public OivAzione save(OivAzione arg0);
	
	// Delete 
	public void delete(OivAzione arg0);
	
	//-----------------------------------
		
}
