package it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivobiettivo;


import java.util.List;

public interface OivObiettivoCmdService {

	// Find by id
	public OivObiettivo findById(Integer id);
	
	// Insert or update 
	public OivObiettivo save(OivObiettivo arg0);
	
	// Delete 
	public void delete(OivObiettivo arg0);
	
	//-----------------------------------
		
}
