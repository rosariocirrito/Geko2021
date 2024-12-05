package it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione;

public interface ValutazioneCmdService {
	
	// Find a contact with details by id
	public Valutazione findById(Integer id);
	
	// Insert or update a contact	
	public Valutazione save(Valutazione arg0);
	
	// Delete a contact	
	public void delete(Valutazione arg0);
	
	//-----------------------------------
		
}
