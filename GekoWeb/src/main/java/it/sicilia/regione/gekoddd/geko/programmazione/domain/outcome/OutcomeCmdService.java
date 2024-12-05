package it.sicilia.regione.gekoddd.geko.programmazione.domain.outcome;

public interface OutcomeCmdService {
	
	// Find a contact with details by id
	public Outcome findById(Integer id);
	
	// Insert or update a contact	
	public Outcome save(Outcome outcome);
	
	// Delete a contact	
	public void delete(Outcome outcome);
	
	//-----------------------------------
		
}
