package it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo;

public interface ObiettivoCmdService {

	
	
	// Find a contact with details by id
	Obiettivo findById(Integer id);
	
	// Insert or update a contact	
	Obiettivo save(Obiettivo obiettivo);
	
	// Delete a contact	
	void delete(Obiettivo obiettivo);
	
	
}
