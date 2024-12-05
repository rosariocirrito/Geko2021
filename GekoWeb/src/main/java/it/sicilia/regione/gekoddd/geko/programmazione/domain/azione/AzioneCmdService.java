package it.sicilia.regione.gekoddd.geko.programmazione.domain.azione;

public interface AzioneCmdService {
	
	// Find a contact with details by id
	public Azione findById(Integer id);
	
	// Insert or update a contact	
	public Azione save(Azione azione);
	
	// Delete a contact	
	public void delete(Azione azione);
	
	//-----------------------------------
		
}
