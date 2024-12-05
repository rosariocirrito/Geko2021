package it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma;


public interface AssociazProgrammaCmdService {
	
	// Find by id
	public AssociazProgramma findById(Integer id);
	
	// Insert or update a contact	
	public AssociazProgramma save(AssociazProgramma associazProgramma);
	
	// Delete a contact	
	public void delete(AssociazProgramma associazProgramma);
	
	//-----------------------------------
	
}
