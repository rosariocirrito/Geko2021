package it.sicilia.regione.gekoddd.geko.rendicontazione.domain.criticita;


import java.util.List;

public interface CriticitaCmdService {

	// Find by id
	public Criticita findById(Integer id);
	
	// Insert or update 
	public Criticita save(Criticita arg0);
	
	// Delete 
	public void delete(Criticita arg0);
	
	//-----------------------------------
		
}
