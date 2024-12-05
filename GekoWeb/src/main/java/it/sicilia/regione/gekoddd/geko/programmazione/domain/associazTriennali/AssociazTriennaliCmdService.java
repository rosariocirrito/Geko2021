package it.sicilia.regione.gekoddd.geko.programmazione.domain.associazTriennali;


public interface AssociazTriennaliCmdService {
	
	// Find by id
	public AssociazTriennali findById(Integer id);
	
	// Insert or update 
	public AssociazTriennali save(AssociazTriennali associazTriennali);
	
	// Delete
	public void delete(AssociazTriennali associazTriennali);
	
	//-----------------------------------
	
}
