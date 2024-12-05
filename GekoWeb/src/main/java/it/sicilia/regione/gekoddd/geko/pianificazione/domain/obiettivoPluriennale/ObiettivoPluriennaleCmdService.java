package it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoPluriennale;

public interface ObiettivoPluriennaleCmdService {

	// Find a contact with details by id
	public ObiettivoPluriennale findById(Integer id);
	
	// Insert or update a contact	
	public ObiettivoPluriennale save(ObiettivoPluriennale obiettivoPluriennale);
	
	// Delete a contact	
	public void delete(ObiettivoPluriennale obiettivoPluriennale);
	
}
